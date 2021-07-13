/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.upperware.metasolver.metricvalue;

import com.google.gson.Gson;
import eu.melodic.upperware.metasolver.Coordinator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import javax.jms.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MetricValueListener implements MessageListener {

    private Topic topic;
    private String topicName;
    private TopicType type;
    private final boolean isPrediction;
    private double reconfigurationProbabilityThreshold;
    private MetricValueRegistry<Object> registry;
    private Gson gson;
    private Coordinator coordinator;

    public MetricValueListener(Coordinator coordinator, Topic topic, TopicType type, MetricValueRegistry<Object> registry, boolean isPrediction) throws JMSException {
        log.debug("MetricValueListener.<init>: type={}", type);
        this.coordinator = coordinator;
        this.topic = topic;
        this.topicName = topic.getTopicName();
        this.type = type;
        this.registry = registry;
        this.isPrediction = isPrediction;
        this.reconfigurationProbabilityThreshold = coordinator.getMetaSolverProperties().getReconfigurationProbabilityThreshold();
        gson = new Gson();
    }

    public void onMessage(Message message) {
        try {
            log.debug("Listener of topic {}: Received message: {}", topic.getTopicName(), message);
            String metricName = topicName;
            String payload = getPayload(message);
            log.debug("Listener of topic {}: metric={}, type={}, payload={}", topicName, metricName, type, payload);

            switch (type) {
                case MVV:
                    log.debug("Listener of topic {}: Got an MVV event: {}", topicName, payload);
                    processMetricValueEvent(metricName, payload);
                    break;
                case SCALE:
                    log.debug("Listener of topic {}: Got a SCALE event: {}", topicName, payload);
                    processScaleEvent(metricName, payload);
                    break;
                case DEBUG_EVENT:
                    log.debug("Listener of topic {}: Got a DEBUG event: {}", topicName, payload);
                    break;
                default:
                    log.debug("Listener of topic {}: Got a UNKNOWN event: Ignoring it: {}", topicName, message);
            }

        } catch (JMSException e) {
            log.error("Listener of topic {}: EXCEPTION caught: ", topicName, e);
        }
    }

    protected String getPayload(@NonNull Message message) throws JMSException {
        if (message instanceof TextMessage) {
            return ((TextMessage) message).getText();
        } else if (message instanceof ObjectMessage) {
            Object o = ((ObjectMessage) message).getObject();
            return o != null ? o.toString() : null;
        } else if (message instanceof BytesMessage) {
            long len = ((BytesMessage)message).getBodyLength();
            byte[] bytes = new byte[(int)len];
            int n = ((BytesMessage)message).readBytes(bytes);
            return new String(bytes);
        } else if (message instanceof MapMessage) {
            MapMessage mapMesg = (MapMessage)message;
            Enumeration en = mapMesg.getMapNames();
            Map<String,String> map = new HashMap<>();
            while (en.hasMoreElements()) {
                Object key = en.nextElement();
                if (key==null) continue;
                Object val = mapMesg.getObject(key.toString());
                map.put(key.toString(), val!=null ? val.toString() : null);
            }
            return map.toString();
        }
        log.error("getPayload: Unsupported message type: class={}, message={}", message.getClass().getName(), message);
        throw new IllegalArgumentException("Unsupported message type: "+message.getClass().getName());
    }

    protected void processMetricValueEvent(@NonNull String metricName, @NonNull String payload) {
        if (!StringUtils.isEmpty(metricName)) {
            // Extract key-value pairs from message payload
            // ...using MetricValueEvent
            log.debug("Listener of topic {}: Converting event payload to MetricValueEvent instance...", topicName);
            MetricValueEvent event = gson.fromJson(payload, MetricValueEvent.class);
            log.debug("Listener of topic {}: MetricValueEvent instance: {}", topicName, event);

            if (!isPrediction) {
                // Cache Metric Value in registry
                log.debug("Listener of topic {}: Metric registry values BEFORE update: {}", topicName, registry);
                registry.setMetricValue(metricName, event.getMetricValue());
                log.info("Metric Value set: name='{}', value='{}', topic={}", metricName, event.getMetricValue(), topicName);
                log.debug("Listener of topic {}: Metric registry values AFTER update:  {}", topicName, registry);
            } else {
                // Get predictionTime
                long predictionTime = getPredictionTime(event, metricName, payload);

                // Cache Predicted Metric Value in Predictions registry
                log.debug("Listener of topic {}: Prediction Metric registry prediction times BEFORE update: {}", topicName, registry.getPredictionTimes());
                log.debug("Listener of topic {}: Prediction Metric registry values BEFORE update: {}", topicName, registry.getPredictedMetricValuesAsMap(predictionTime));

                registry.setPredictedMetricValue(metricName, event.getMetricValue(), predictionTime);
                log.info("Prediction Metric Value set: name='{}', value='{}', topic={}, predictionTime={}", metricName, event.getMetricValue(), topicName, predictionTime);

                log.debug("Listener of topic {}: Prediction Metric registry prediction times AFTER update: {}", topicName, registry.getPredictionTimes());
                log.debug("Listener of topic {}: Prediction Metric registry values AFTER update:  {}", topicName, registry.getPredictedMetricValuesAsMap(predictionTime));
            }
        } else {
            log.warn("Missing property: 'topic_name'");
        }
    }

    protected void processScaleEvent(@NonNull String metricName, @NonNull String payload) {
		try {
			log.debug("Listener of topic {}: Calling coordinator to start Scaling process...", topicName);
            if (!isPrediction) {
                // Start reconfiguration with the actual metric values set in CP model
                coordinator.requestStartProcessForScaling(false);
            } else {
                // Extract key-value pairs from message payload
                // ...using MetricValueEvent
                log.debug("Listener of topic {}: Converting event payload to MetricValueEvent instance...", topicName);
                MetricValueEvent event = gson.fromJson(payload, MetricValueEvent.class);
                log.debug("Listener of topic {}: MetricValueEvent instance: {}", topicName, event);

                // Get predictionTime and probability
                long predictionTime = getPredictionTime(event, metricName, payload);
                double probability = getProbability(event, metricName, payload);

                // Check if probability is below reconfiguration threshold
                if (probability < reconfigurationProbabilityThreshold) {
                    log.warn("Listener of topic {}: Predicted SCALE event probability is below threshold: {} < {}", topicName, probability, reconfigurationProbabilityThreshold);
                    return;
                }

                // Get predictionTime frame from prediction registry and convert topicNames to MVVs
                Map<String, String> metricValues = registry.getPredictedMetricValuesAsMap(predictionTime);
                log.debug("Listener of topic {}: Predicted metric values (cached): {}", topicName, metricValues);
                //metricValues = convertToMvv(metricValues);
                //log.debug("Listener of topic {}: Predicted metric values (as MVs): {}", topicName, metricValues);

                // Start reconfiguration with the given predicted values set in CP model
                coordinator.requestStartProcessForScaling(false, metricValues);

                // Cleanup older predictionTime frames
                if (coordinator.getMetaSolverProperties().isPredictionRegistryCleanupAfterScaleEvent())
                    registry.clearPredictionTimeFramesBefore(predictionTime);
            }
		} catch (Exception ex) {
            log.error("processScaleEvent: EXCEPTION: ", ex);
		}
    }

    /*private Map<String, String> convertToMvv(Map<String, String> metricValues) {
        return metricValues.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        k -> coordinator.getPredictionHelper().getTopicNameForPrediction(k.getKey()),
                        Map.Entry::getValue));
    }*/

    protected long getPredictionTime(MetricValueEvent event, String metricName, String payload) {
        Object pt = event.get("predictionTime");
        if (pt==null || StringUtils.isBlank(pt.toString()))
            throw new IllegalArgumentException("Received prediction event without 'predictionTime' in Topic: "+metricName+": "+payload);
        return registry.approximate(Double.parseDouble(pt.toString()));
    }

    protected double getProbability(MetricValueEvent event, String metricName, String payload) {
        Object pb = event.get("probability");
        if (pb==null || StringUtils.isBlank(pb.toString()))
            throw new IllegalArgumentException("Received prediction event without 'probability' in Topic: "+metricName+": "+payload);
        return Double.parseDouble(pb.toString());
    }
}
