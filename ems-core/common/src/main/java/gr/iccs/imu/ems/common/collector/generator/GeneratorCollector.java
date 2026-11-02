/*
 * Copyright (C) 2017-2026 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package gr.iccs.imu.ems.common.collector.generator;

import gr.iccs.imu.ems.api.EmsConstant;
import gr.iccs.imu.ems.brokercep.event.EventMap;
import gr.iccs.imu.ems.common.client.SshClientProperties;
import gr.iccs.imu.ems.common.collector.CollectorContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.TaskScheduler;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Generates measurements. Can be used for testing and debugging
 */
@Slf4j
@RequiredArgsConstructor
public class GeneratorCollector implements InitializingBean, IGeneratorCollector {
    private final static AtomicInteger instanceCounter = new AtomicInteger(0);

    protected final GeneratorCollectorProperties initialProperties;
    protected final TaskScheduler taskScheduler;
    protected final CollectorContext<? extends SshClientProperties> collectorContext;

    protected String collectorId =
            GeneratorCollector.class.getSimpleName() + "-" + instanceCounter.getAndIncrement();
    private final Map<ScheduledFuture<?>, GeneratorCollectorProperties> runningGenerators = new LinkedHashMap<>();

    @Data
    @AllArgsConstructor
    protected static class RunnerContext {
        private long currentCount;
        private Double currentCountValue;
        private GeneratorCollectorProperties properties;
    }

    @Override
    public void afterPropertiesSet() {
        log.debug("Collectors::{}: properties: {}", collectorId, initialProperties);
        if (initialProperties !=null && initialProperties.isEnable()) {
            startGenerator(initialProperties);
        }
    }

    public synchronized ScheduledFuture<?> startGenerator(GeneratorCollectorProperties properties) {
        log.debug("Collectors::{}: Starting Generator: {}", collectorId, properties);
        final RunnerContext context = new RunnerContext(0, 0.0, properties);
        ScheduledFuture<?> task = taskScheduler.scheduleWithFixedDelay(() -> {
            log.debug("Collectors::{}: Running generator for {}: ", collectorId, properties.getDestinations());
            LinkedHashMap<String, Double> values = new LinkedHashMap();
            properties.getDestinations().forEach(destination -> {
                values.put(destination, generateValue(context));
            });
            log.trace("Collectors::{}: Publish Data: {}", collectorId, values);
            publishData(values);
        }, Duration.of(properties.getDelay(), ChronoUnit.MILLIS));
        runningGenerators.put(task, properties);
        return task;
    }

    public synchronized void stopGenerator(ScheduledFuture<?> runner) {
        log.debug("Collectors::{}: Stopping Generator: {}", collectorId, runner);
        runner.cancel(true);
        runningGenerators.remove(runner);
    }

    public synchronized void stopAllGenerators() {
        new HashSet<>(runningGenerators.keySet()).forEach(this::stopGenerator);
        runningGenerators.clear();
    }

    private Double generateValue(RunnerContext context) {
        GeneratorCollectorProperties properties = context.getProperties();
        if (properties.getLimitsMaxCount()>0 && context.getCurrentCount()+1 > properties.getLimitsMaxCount()) {
            log.trace("generateValue: STOP: {} >= {}", context.getCurrentCount(), properties.getLimitsMaxCount());
            return null;
        }

        // if GENERATOR_TYPE.count
        if (properties.getType()==GeneratorCollectorProperties.GENERATOR_TYPE.count) {
            log.trace("generateValue: COUNT");
            Double newCountValue = (context.getCurrentCount()==0)
                ? properties.getLimitsStart()
                : context.getCurrentCountValue() + properties.getLimitsStep();
            if (properties.getLimitsStep()>0 && newCountValue > properties.getLimitsUpper()
                || properties.getLimitsStep()<0 && newCountValue < properties.getLimitsLower())
            {
                newCountValue = switch (properties.getLimitsOnOverflow()) {
                    case round ->
                            newCountValue > properties.getLimitsUpper() ? properties.getLimitsLower() : properties.getLimitsUpper();
                    case reset ->
                            properties.getLimitsStart();
                    case stop -> {
                        stop();
                        yield null;
                    }
                    case reverse -> {
                        properties.setLimitsStep(-1 * properties.getLimitsStep());
                        yield  context.getCurrentCountValue() + properties.getLimitsStep();
                    }
                };
            }
            context.setCurrentCountValue(newCountValue);
            if (context.getCurrentCountValue()==null)
                return null;
            context.setCurrentCount(context.getCurrentCount()+1);
            return asValueType(newCountValue, properties.getValueType());
        }

        // else-if GENERATOR_TYPE.datetime
        if (properties.getType()==GeneratorCollectorProperties.GENERATOR_TYPE.datetime) {
            log.trace("generateValue: DATETIME");
            if (StringUtils.isNotBlank(properties.getZone()))
                properties.getDatetimeFormatter()
                        .setTimeZone(TimeZone.getTimeZone(properties.getZone().trim()));
            String s = properties.getDatetimeFormatter().format(Date.from(Instant.now()));
            double d = Double.parseDouble(s);
            if (properties.getValueType()==GeneratorCollectorProperties.GENERATOR_VALUE_TYPE.Double)
                d = Math.round(d);
            return asValueType(d, properties.getValueType());
        }

        // else GENERATOR_TYPE.random
        log.trace("generateValue: RANDOM");
        double range = properties.getLimitsUpper() - properties.getLimitsLower();
        return asValueType(ThreadLocalRandom.current().nextDouble() * range + properties.getLimitsLower(), properties.getValueType());
    }

    private Double asValueType(double value, GeneratorCollectorProperties.GENERATOR_VALUE_TYPE valueType) {
        log.trace("generateValue: VALUE: {}  ({})", value, valueType);
        return valueType==GeneratorCollectorProperties.GENERATOR_VALUE_TYPE.Int
                ? Math.round(value) : value;
    }

    protected void publishData(Map<String,Double> data) {
        long timestamp = System.currentTimeMillis();
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            String destination = entry.getKey();
            double value = entry.getValue();

            // Create event
            log.debug("Collectors::{}: Publishing event to destination: {}", collectorId, destination);
            EventMap event = new EventMap(value, 1, timestamp);
            sendEvent(destination, destination, event, null, true);
            log.debug("Collectors::{}: Published event to destination: {} :: {}", collectorId, destination, event);

            if (Thread.currentThread().isInterrupted()) break;
        }
    }

    private CollectorContext.PUBLISH_RESULT sendEvent(String metricName, String originalTopic, EventMap event, String nodeAddress, boolean createDestination) {
        event.setEventProperty(EmsConstant.EVENT_PROPERTY_SOURCE_ADDRESS, nodeAddress);
        event.getEventProperties().put(EmsConstant.EVENT_PROPERTY_EFFECTIVE_DESTINATION, metricName);
        event.getEventProperties().put(EmsConstant.EVENT_PROPERTY_ORIGINAL_DESTINATION, originalTopic);
        log.debug("Collectors::{}:    Publishing metric: {}: {}", collectorId, metricName, event.getMetricValue());
        CollectorContext.PUBLISH_RESULT result = collectorContext.sendEvent(null, metricName, event, createDestination);
        log.trace("Collectors::{}:    Publishing metric: {}: {} -> result: {}", collectorId, metricName, event.getMetricValue(), result);
        return result;
    }
}
