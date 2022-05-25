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
import eu.melodic.event.control.properties.TopicBeaconProperties;
import eu.melodic.event.translate.TranslationContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
@Service
@EnableScheduling
public class TopicBeacon implements InitializingBean {
    @Autowired
    private TopicBeaconProperties properties;

    @Autowired
    private ControlServiceCoordinator coordinator;
    @Autowired
    private BrokerCepService brokerCepService;
    @Autowired
    private TaskScheduler scheduler;

    private Gson gson;
    private String previousModelId = "";
    private final AtomicLong modelVersion = new AtomicLong(0);

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!properties.isEnabled()) {
            log.warn("Topic Beacon is disabled");
            return;
        }

        // initialize a Gson instance
        gson = new GsonBuilder().disableHtmlEscaping().create();

        // configure and start scheduler
        Date startTime = new Date(System.currentTimeMillis() + properties.getInitialDelay());
        log.debug("Topic Beacon settings: init-delay={}, delay={}, heartbeat-topics={}, threshold-topics={}, instance-topics={}",
                properties.getInitialDelay(), properties.getDelay(), properties.getHeartbeatTopics(), properties.getThresholdTopics(),
                properties.getInstanceTopics());
        scheduler.scheduleWithFixedDelay(() -> {
            try {
                transmitInfo();
            } catch (Exception e) {
                log.error("Topic Beacon: Exception while sending info: ", e);
            }
        }, startTime, properties.getDelay());
        log.info("Topic Beacon started: init-delay={}ms", properties.getInitialDelay());
    }

    public void transmitInfo() throws JMSException {
        log.debug("Topic Beacon: Start transmitting info: {}", new Date());
        updateModelVersion();
        transmitHeartbeat();
        transmitThresholdInfo();
        transmitInstanceInfo();
        transmitPredictionInfo();
        transmitSloViolatorInfo();
        log.debug("Topic Beacon: Completed transmitting info: {}", new Date());
    }

    public void transmitHeartbeat() throws JMSException {
        if (SetUtils.emptyIfNull(properties.getHeartbeatTopics()).isEmpty()) return;

        String message = "TOPIC BEACON HEARTBEAT "+new Date();
        log.debug("Topic Beacon: Transmitting Heartbeat info: message={}, topics={}", message, properties.getHeartbeatTopics());
        sendMessageToTopics(message, properties.getHeartbeatTopics());
    }

    public void transmitThresholdInfo() {
        if (SetUtils.emptyIfNull(properties.getThresholdTopics()).isEmpty()) return;

        if (coordinator.getTranslationContextOfCamelModel(coordinator.getCurrentCamelModelId())==null)
            return;
        coordinator.getTranslationContextOfCamelModel(coordinator.getCurrentCamelModelId())
                .getMetricConstraints()
                .forEach(c -> {
                    String message = gson.toJson(c);
                    log.debug("Topic Beacon: Transmitting Metric Constraint threshold info: message={}, topics={}",message, properties.getThresholdTopics());
                    try {
                        sendEventToTopics(message, properties.getThresholdTopics());
                    } catch (JMSException e) {
                        log.error("Topic Beacon: EXCEPTION while transmitting Metric Constraint threshold info: message={}, topics={}, exception: ",
                                message, properties.getThresholdTopics(), e);
                    }
                });
    }

    public void transmitInstanceInfo() throws JMSException {
        if (SetUtils.emptyIfNull(properties.getInstanceTopics()).isEmpty()) return;

        if (coordinator.getBaguetteServer().isServerRunning()) {
            log.debug("Topic Beacon: Transmitting Instance info: topics={}", properties.getInstanceTopics());
            for (NodeRegistryEntry node : coordinator.getBaguetteServer().getNodeRegistry().getNodes()) {
                String nodeName = node.getPreregistration().getOrDefault("name", "");
                String nodeIp = node.getIpAddress();
                //String nodeIp = node.getPreregistration().getOrDefault("ip","");
                String message = gson.toJson(node);
                log.debug("Topic Beacon: Transmitting Instance info for: instance={}, ip-address={}, message={}, topics={}",
                        nodeName, nodeIp, message, properties.getInstanceTopics());
                sendEventToTopics(message, properties.getInstanceTopics());
            }
        }
    }

    public void transmitPredictionInfo() {
        if (SetUtils.emptyIfNull(properties.getPredictionTopics()).isEmpty()) return;

        String modelId = coordinator.getCurrentCamelModelId();
        log.trace("Topic Beacon: transmitPredictionInfo: current-camel-model-id: {}", modelId);
        //Set<String> topLevelMetrics = coordinator.getGlobalGroupingMetrics(modelId);
        //log.debug("Topic Beacon: transmitPredictionInfo: DAG Global-Level Metrics: {}", topLevelMetrics);
        Set<TranslationContext.MetricContext> metricContexts = coordinator.getMetricContextsForPrediction(modelId);
        log.debug("Topic Beacon: transmitPredictionInfo: Metric Contexts for prediction: {}", metricContexts);

        // Convert to Translator-to-Forecasting Methods event format
        final long currVersion = modelVersion.get();
        List<HashMap<String, Object>> payload = metricContexts.stream().map(s -> {
            HashMap<String, Object> map = new HashMap<>();
            map.put("metric", s.getName());
            map.put("level", 3);
            map.put("version", currVersion);
            map.put("publish_rate", s.getSchedule()!=null
                    ? s.getSchedule().getIntervalInMillis() :
                    properties.getPredictionRate());
            return map;
        }).collect(Collectors.toList());
        log.debug("Topic Beacon: Transmitting Prediction info: Metric Contexts in event format: {}", payload);

        // Skip event sending if payload is empty
        if (payload.size()==0) {
            log.debug("Topic Beacon: transmitSloViolatorInfo: Payload is empty. Not sending event");
            return;
        }

        String eventPayload = gson.toJson(payload);

        log.debug("Topic Beacon: Transmitting Prediction info: event={}, topics={}", eventPayload, properties.getPredictionTopics());
        try {
            sendMessageToTopics(eventPayload, properties.getPredictionTopics());
        } catch (JMSException e) {
            log.error("Topic Beacon: EXCEPTION while transmitting Prediction info: event={}, topics={}, exception: ",
                    eventPayload, properties.getPredictionTopics(), e);
        }
    }

    public void transmitSloViolatorInfo() {
        if (SetUtils.emptyIfNull(properties.getSloViolationDetectorTopics()).isEmpty()) return;

        String modelId = coordinator.getCurrentCamelModelId();
        log.trace("Topic Beacon: transmitSloViolatorInfo: current-camel-model-id: {}", modelId);
        //List<Object> sloMetricDecompositions = coordinator.getSLOMetricDecomposition(modelId);
        Map<String, Object> sloMetricDecompositions = coordinator.getSLOMetricDecomposition(modelId);
        log.debug("Topic Beacon: transmitSloViolatorInfo: SLO metric decompositions: {}", sloMetricDecompositions);

        // Skip event sending if payload is empty
        if (sloMetricDecompositions.get("constraints") == null || ((List) sloMetricDecompositions.get("constraints")).size() == 0) {
            log.debug("Topic Beacon: transmitSloViolatorInfo: Payload is empty. Not sending event");
            return;
        }

        sloMetricDecompositions.put("version", modelVersion.get());
        String eventPayload = gson.toJson(sloMetricDecompositions);

        log.debug("Topic Beacon: Transmitting SLO Violator info: event={}, topics={}", eventPayload, properties.getSloViolationDetectorTopics());
        try {
            sendMessageToTopics(eventPayload, properties.getSloViolationDetectorTopics());
        } catch (JMSException e) {
            log.error("Topic Beacon: EXCEPTION while transmitting SLO Violator info: event={}, topics={}, exception: ",
                    eventPayload, properties.getSloViolationDetectorTopics(), e);
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
                    event,
                    false);
            log.debug("Topic Beacon: Event sent to topic: event={}, topic={}", event, topicName);
        }
    }

    private synchronized boolean updateModelVersion() {
        String modelId = coordinator.getCurrentCamelModelId();
        boolean versionChanged = ! StringUtils.defaultIfBlank(modelId, "").equals(previousModelId);
        log.trace("Topic Beacon: updateModelVersion: previousModelId='{}', modelId='{}', version={}, version-changed={}",
                previousModelId, modelId, modelVersion.get(), versionChanged);
        if (versionChanged) {
            long newVersion = modelVersion.incrementAndGet();
            log.info("Topic Beacon: updateModelVersion: Model changed: {} -> {}, version: {}", previousModelId, modelId, newVersion);
            previousModelId = modelId;
        }
        return versionChanged;
    }
}
