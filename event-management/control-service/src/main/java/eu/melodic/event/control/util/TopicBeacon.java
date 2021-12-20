/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
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
import eu.melodic.event.translate.TranslationContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.SetUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@EnableScheduling
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
    @Value("${beacon.topics.prediction.rate:60000}")
    private long beaconPredictionRate;
    @Value("${beacon.topics.slo-violation-detector:}")
    private Set<String> beaconSloViolationDetectorTopics;

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
            } catch (Exception e) {
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
        transmitSloViolatorInfo();
        log.debug("Topic Beacon: Completed transmitting info: {}", new Date());
    }

    public void transmitHeartbeat() throws JMSException {
        if (SetUtils.emptyIfNull(beaconHeartbeatTopics).isEmpty()) return;

        String message = "TOPIC BEACON HEARTBEAT "+new Date();
        log.debug("Topic Beacon: Transmitting Heartbeat info: message={}, topics={}", message, beaconHeartbeatTopics);
        sendMessageToTopics(message, beaconHeartbeatTopics);
    }

    public void transmitThresholdInfo() {
        if (SetUtils.emptyIfNull(beaconThresholdTopics).isEmpty()) return;

        if (coordinator.getTranslationContextOfCamelModel(coordinator.getCurrentCamelModelId())==null)
            return;
        coordinator.getTranslationContextOfCamelModel(coordinator.getCurrentCamelModelId())
                .getMetricConstraints()
                .forEach(c -> {
                    String message = gson.toJson(c);
                    log.debug("Topic Beacon: Transmitting Metric Constraint threshold info: message={}, topics={}",message, beaconThresholdTopics);
                    try {
                        sendEventToTopics(message, beaconThresholdTopics);
                    } catch (JMSException e) {
                        log.error("Topic Beacon: EXCEPTION while transmitting Metric Constraint threshold info: message={}, topics={}, exception: ",
                                message, beaconThresholdTopics, e);
                    }
                });
    }

    public void transmitInstanceInfo() throws JMSException {
        if (SetUtils.emptyIfNull(beaconInstanceTopics).isEmpty()) return;

        if (coordinator.getBaguetteServer().isServerRunning()) {
            log.debug("Topic Beacon: Transmitting Instance info: topics={}", beaconInstanceTopics);
            for (NodeRegistryEntry node : coordinator.getBaguetteServer().getNodeRegistry().getNodes()) {
                String nodeName = node.getPreregistration().getOrDefault("name", "");
                String nodeIp = node.getIpAddress();
                //String nodeIp = node.getPreregistration().getOrDefault("ip","");
                String message = gson.toJson(node);
                log.debug("Topic Beacon: Transmitting Instance info for: instance={}, ip-address={}, message={}, topics={}",
                        nodeName, nodeIp, message, beaconInstanceTopics);
                sendEventToTopics(message, beaconInstanceTopics);
            }
        }
    }

    public void transmitPredictionInfo() {
        if (SetUtils.emptyIfNull(beaconPredictionTopics).isEmpty()) return;

        String modelId = coordinator.getCurrentCamelModelId();
        log.trace("Topic Beacon: transmitPredictionInfo: current-camel-model-id: {}", modelId);
        //Set<String> topLevelMetrics = coordinator.getGlobalGroupingMetrics(modelId);
        //log.debug("Topic Beacon: transmitPredictionInfo: DAG Global-Level Metrics: {}", topLevelMetrics);
        Set<TranslationContext.MetricContext> metricContexts = coordinator.getMetricContextsForPrediction(modelId);
        log.debug("Topic Beacon: transmitPredictionInfo: Metric Contexts for prediction: {}", metricContexts);
        if (metricContexts==null)
            return;

        // Convert to Translator-to-Forecasting Methods event format
        List<HashMap<String, Object>> payload = metricContexts.stream().map(s -> {
            HashMap<String, Object> map = new HashMap<>();
            map.put("metric", s.getName());
            map.put("level", 3);
            map.put("publish_rate", s.getSchedule()!=null
                    ? s.getSchedule().getIntervalInMillis() :
                    beaconPredictionRate);
            return map;
        }).collect(Collectors.toList());
        log.debug("Topic Beacon: Transmitting Prediction info: Metric Contexts in event format: {}", payload);

        String eventPayload = gson.toJson(payload);

        log.debug("Topic Beacon: Transmitting Prediction info: event={}, topics={}", eventPayload, beaconPredictionTopics);
        try {
            sendMessageToTopics(eventPayload, beaconPredictionTopics);
        } catch (JMSException e) {
            log.error("Topic Beacon: EXCEPTION while transmitting Prediction info: event={}, topics={}, exception: ",
                    eventPayload, beaconPredictionTopics, e);
        }
    }

    public void transmitSloViolatorInfo() {
        if (SetUtils.emptyIfNull(beaconSloViolationDetectorTopics).isEmpty()) return;

        String modelId = coordinator.getCurrentCamelModelId();
        log.trace("Topic Beacon: transmitSloViolatorInfo: current-camel-model-id: {}", modelId);
        //List<Object> sloMetricDecompositions = coordinator.getSLOMetricDecomposition(modelId);
        Object sloMetricDecompositions = coordinator.getSLOMetricDecomposition(modelId);
        if (sloMetricDecompositions==null)
            return;
        log.debug("Topic Beacon: transmitSloViolatorInfo: SLO metric decompositions: {}", sloMetricDecompositions);

        String eventPayload = gson.toJson(sloMetricDecompositions);

        log.debug("Topic Beacon: Transmitting SLO Violator info: event={}, topics={}", eventPayload, beaconSloViolationDetectorTopics);
        try {
            sendMessageToTopics(eventPayload, beaconSloViolationDetectorTopics);
        } catch (JMSException e) {
            log.error("Topic Beacon: EXCEPTION while transmitting SLO Violator info: event={}, topics={}, exception: ",
                    eventPayload, beaconPredictionTopics, e);
        }
    }

    // ------------------------------------------------------------------------

    private void sendEventToTopics(String message, Set<String> topics) throws JMSException {
        EventMap event = new EventMap(-1);
        event.put("message", message);
        sendMessageToTopics(event, topics);
    }

    private void sendMessageToTopics(Serializable event, Set<String> topics) throws JMSException {
        for (String topicName : topics) {
            log.trace("Topic Beacon: Sending event to topic: event={}, topic={}", event, topicName);
            brokerCepService.publishSerializable(
                    brokerCepService.getBrokerCepProperties().getBrokerUrlForClients(),
                    brokerCepService.getBrokerUsername(),
                    brokerCepService.getBrokerPassword(),
                    topicName,
                    event);
            log.info("Topic Beacon: Event sent to topic: event={}, topic={}", event, topicName);
        }
    }
}
