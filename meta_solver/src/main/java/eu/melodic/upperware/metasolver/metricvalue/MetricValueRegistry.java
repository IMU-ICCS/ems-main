/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.upperware.metasolver.metricvalue;

import eu.melodic.upperware.metasolver.properties.MetaSolverProperties;
import eu.melodic.upperware.metasolver.util.PredictionHelper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
@EnableScheduling
@RequiredArgsConstructor
public class MetricValueRegistry<T> implements InitializingBean {
    private final HashMap<String, MetricValue<T>> registry = new HashMap<>();
    private final LinkedHashMap<Long,HashMap<String, MetricValue<T>>> predictionRegistry = new LinkedHashMap<>();

    @Getter
    private final Set<String> mvvMetricNames = new HashSet<>();
    private final MetaSolverProperties properties;
    private final PredictionHelper predictionHelper;
    private final TaskScheduler scheduler;


    @Override
    public void afterPropertiesSet() {
        // Setup prediction registry cleanup
        long cleanupRate = properties.getPredictionRegistryCleanupRate();
        if (cleanupRate>0) {
            scheduler.scheduleAtFixedRate(this::runPredictionRegistryClean, cleanupRate);
            log.info("MetricValueRegistry: Prediction Registry cleanup every: {}ms", cleanupRate);
        } else {
            log.warn("MetricValueRegistry: Prediction Registry cleanup is deactivated. Set 'predictionRegistryCleanupRate' property to activate cleanup.");
        }
        log.info("MetricValueRegistry: Prediction Registry cleanup after SCALE event is {}",
                properties.isPredictionRegistryCleanupAfterScaleEvent() ? "activated" : "deactivated");
    }

    public T getMetricValue(String metricName) {
        MetricValue<T> mv = registry.get(metricName);
        if (mv == null) return null;
        return mv.getMetricValue();
    }

    public void setMetricValue(String metricName, T metricValue) {
        setMetricValue(metricName, metricValue, System.currentTimeMillis());
    }

    public void setMetricValue(String metricName, T metricValue, long timestamp) {
        registry.put(metricName, new MetricValue<>(metricValue, timestamp));
    }

    public Map<String, String> getMetricValuesAsMap() {
        HashMap<String, String> mvm = new HashMap<>();
        for (Map.Entry<String, MetricValue<T>> entry : registry.entrySet()) {
            String name = entry.getKey();
            T mv = entry.getValue().getMetricValue();
            String value = mv != null ? mv.toString() : "";
            mvm.put(name, value);
        }
        return mvm;
    }

    // ------------------------------------------------------------------------

    public long approximate(double ts) {
        return predictionHelper.approximatePredictionTime((long)ts);
    }

    public long approximate(long ts) {
        return predictionHelper.approximatePredictionTime(ts);
    }

    public T getPredictedMetricValue(String metricName, long predictionTime) {
        MetricValue<T> mv = predictionRegistry.get(approximate(predictionTime)).get(metricName);
        if (mv == null) return null;
        return mv.getMetricValue();
    }

    public void setPredictedMetricValue(String metricName, T metricValue, long predictionTime) {
        setPredictedMetricValue(metricName, metricValue, System.currentTimeMillis(), predictionTime);
    }

    public void setPredictedMetricValue(String metricName, T metricValue, long timestamp, long predictionTime) {
        String mvName = predictionHelper.getTopicNameForPrediction(metricName);
        predictionRegistry
                .computeIfAbsent(approximate(predictionTime), m->new HashMap<>())
                .put(mvName, new MetricValue<>(metricValue, timestamp));
    }

    public Map<String, String> getPredictedMetricValuesAsMap(long predictionTime) {
        HashMap<String, String> mvm = new HashMap<>();
        Set<Map.Entry<String, MetricValue<T>>> predictionTimeFrame = predictionRegistry.computeIfAbsent(approximate(predictionTime), s -> new HashMap<>()).entrySet();
        for (Map.Entry<String, MetricValue<T>> entry : predictionTimeFrame) {
            String name = entry.getKey();
            T mv = entry.getValue().getMetricValue();
            String value = mv != null ? mv.toString() : "";
            mvm.put(name, value);
        }
        return mvm;
    }

    public List<Long> getPredictionTimes() {
        return new ArrayList<>(predictionRegistry.keySet());
    }

    public synchronized void clearPredictionTimeFramesBefore(long timestamp) {
        log.debug("MetricValueRegistry: Clear predictionTime frames with timestamp before: {}", timestamp);
        log.debug("MetricValueRegistry: Clear predictionTime frames: Frames BEFORE: {}", predictionRegistry.keySet());
        predictionRegistry.entrySet().removeIf(e -> e.getKey() < timestamp);
        log.debug("MetricValueRegistry: Clear predictionTime frames: Frames AFTER: {}", predictionRegistry.keySet());
    }

    private void runPredictionRegistryClean() {
        clearPredictionTimeFramesBefore(System.currentTimeMillis());
    }

    // ------------------------------------------------------------------------

    public void addMvvMetricName(String metricName) {
        this.mvvMetricNames.add(metricName);
    }

    public String toString() {
        return super.toString() + " : " + registry.toString();
    }

    // MetricValue class definition
    private static class MetricValue<T> {
        private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        private T value;
        private long timestamp;

        public MetricValue(T value, long timestamp) {
            this.value = value;
            this.timestamp = timestamp;
        }

        public T getMetricValue() {
            return value;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public String toString() {
            return value + "_@_" + sdf.format(new Date(timestamp));
        }
    }
}
