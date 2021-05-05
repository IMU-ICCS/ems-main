/*
 * Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.control.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eu.melodic.event.baguette.server.NodeRegistryEntry;
import eu.melodic.event.brokercep.BrokerCepService;
import eu.melodic.event.brokercep.event.EventMap;
import eu.melodic.event.control.ControlServiceCoordinator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import java.util.Date;
import java.util.Set;

@Service
@EnableScheduling
@Slf4j
public class TopicBeacon implements InitializingBean {
    // Topic Beacon settings
    @Value("${beacon.enable:true}")
    private boolean beaconEnable;
    @Value("${beacon.initial-delay:60000}")
    private long beaconInitialDelay;
    @Value("${beacon.delay:60000}")
    private long beaconDelay;
    @Value("${beacon.rate:60000}")
    private long beaconRate;
    @Value("${beacon.topics.heartbeat:}")
    private Set<String> beaconHeartbeatTopics;
    @Value("${beacon.topics.threshold:}")
    private Set<String> beaconThresholdTopics;
    @Value("${beacon.topics.instance:}")
    private Set<String> beaconInstanceTopics;
    @Value("${beacon.topics.prediction:}")
    private Set<String> beaconPredictionTopics;

    @Autowired
    private ControlServiceCoordinator coordinator;
    @Autowired
    private BrokerCepService brokerCepService;
    @Autowired
    private TaskScheduler scheduler;

    private Gson gson;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!beaconEnable) {
            log.warn("Topic Beacon is disabled");
            return;
        }

        // initialize a Gson instance
        gson = new GsonBuilder().disableHtmlEscaping().create();

        // configure and start scheduler
        Date startTime = new Date(System.currentTimeMillis()+beaconInitialDelay);
        log.debug("Topic Beacon settings: init-delay={}, delay={}, heartbeat-topics={}, threshold-topics={}, instance-topics={}",
                beaconInitialDelay, beaconDelay, beaconHeartbeatTopics, beaconThresholdTopics, beaconInstanceTopics);
        scheduler.scheduleWithFixedDelay(() -> {
            try {
                transmitInfo();
            } catch (JMSException e) {
                log.error("Topic Beacon: Exception while sending info: ", e);
            }
        }, startTime, beaconDelay);
        log.info("Topic Beacon started: init-delay={}ms", beaconInitialDelay);
    }

    public void transmitInfo() throws JMSException {
        log.debug("Topic Beacon: Start transmitting info: {}", new Date());
        transmitHeartbeat();
        transmitThresholdInfo();
        transmitInstanceInfo();
        transmitPredictionInfo();
        log.debug("Topic Beacon: Completed transmitting info: {}", new Date());
    }

    public void transmitHeartbeat() throws JMSException {
        String message = "TOPIC BEACON HEARTBEAT "+new Date();
        log.debug("Topic Beacon: Transmitting Heartbeat info: message={}, topics={}", message, beaconHeartbeatTopics);
        sendMessageToTopics(message, beaconHeartbeatTopics);
    }

    public void transmitThresholdInfo() {
        if (coordinator.getTranslationContextOfCamelModel(coordinator.getCurrentCamelModelId())==null)
            return;
        coordinator.getTranslationContextOfCamelModel(coordinator.getCurrentCamelModelId())
                .getMetricConstraints()
                .forEach(c -> {
                    String message = gson.toJson(c);
                    log.debug("Topic Beacon: Transmitting Metric Constraint threshold info: message={}, topics={}",message, beaconThresholdTopics);
                    try {
                        sendMessageToTopics(message, beaconThresholdTopics);
                    } catch (JMSException e) {
                        log.error("Topic Beacon: EXCEPTION while transmitting Metric Constraint threshold info: message={}, topics={}, exception: ",
                                message, beaconThresholdTopics, e);
                    }
                });
    }

    public void transmitInstanceInfo() throws JMSException {
        if (coordinator.getBaguetteServer().isServerRunning()) {
            log.debug("Topic Beacon: Transmitting Instance info: topics={}", beaconInstanceTopics);
            for (NodeRegistryEntry node : coordinator.getBaguetteServer().getNodeRegistry().getNodes()) {
                String nodeName = node.getPreregistration().getOrDefault("name", "");
                String nodeIp = node.getIpAddress();
                //String nodeIp = node.getPreregistration().getOrDefault("ip","");
                String message = gson.toJson(node);
                log.debug("Topic Beacon: Transmitting Instance info for: instance={}, ip-address={}, message={}, topics={}",
                        nodeName, nodeIp, message, beaconInstanceTopics);
                sendMessageToTopics(message, beaconInstanceTopics);
            }
        }
    }

    public void transmitPredictionInfo() {
        String modelId = coordinator.getCurrentCamelModelId();
        log.trace("Topic Beacon: transmitPredictionInfo: current-camel-model-id: {}", modelId);
        Set<String> topLevelMetrics = coordinator.getGlobalGroupingMetrics(modelId);
        if (topLevelMetrics==null)
            return;
        log.debug("Topic Beacon: transmitPredictionInfo: DAG Top-Level Metrics: {}", topLevelMetrics);
        EventMap event = new EventMap();
        event.put("metrics_to_predict", topLevelMetrics);

        log.debug("Topic Beacon: Transmitting Prediction info: event={}, topics={}", event, beaconPredictionTopics);
        try {
            sendMessageToTopics(event, beaconPredictionTopics);
        } catch (JMSException e) {
            log.error("Topic Beacon: EXCEPTION while transmitting Prediction info: event={}, topics={}, exception: ",
                    event, beaconPredictionTopics, e);
        }
    }

    private void sendMessageToTopics(String message, Set<String> topics) throws JMSException {
        EventMap event = new EventMap(-1);
        event.put("message", message);
        sendMessageToTopics(event, topics);
    }

    private void sendMessageToTopics(EventMap event, Set<String> topics) throws JMSException {
        for (String topicName : topics) {
            log.trace("Topic Beacon: Sending event to topic: event={}, topic={}", event, topicName);
            brokerCepService.publishEvent(
                    brokerCepService.getBrokerCepProperties().getBrokerUrlForClients(),
                    brokerCepService.getBrokerUsername(),
                    brokerCepService.getBrokerPassword(),
                    topicName,
                    event);
            log.info("Topic Beacon: Event sent to topic: event={}, topic={}", event, topicName);
        }
    }
}
