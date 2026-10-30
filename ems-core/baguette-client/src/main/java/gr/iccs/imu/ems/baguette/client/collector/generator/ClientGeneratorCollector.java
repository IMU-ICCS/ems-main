/*
 * Copyright (C) 2017-2026 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package gr.iccs.imu.ems.baguette.client.collector.generator;

import gr.iccs.imu.ems.baguette.client.IClientCollector;
import gr.iccs.imu.ems.baguette.client.collector.ClientCollectorContext;
import gr.iccs.imu.ems.common.collector.CollectorContext;
import gr.iccs.imu.ems.common.collector.generator.GeneratorCollectorProperties;
import gr.iccs.imu.ems.util.EventBus;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Generates measurements
 */
@Slf4j
@Component
public class ClientGeneratorCollector extends gr.iccs.imu.ems.common.collector.generator.GeneratorCollector implements IClientCollector {
    private List<Map<String,Object>> configurations;

    public ClientGeneratorCollector(@NonNull GeneratorCollectorProperties properties,
                                    @NonNull CollectorContext collectorContext,
                                    @NonNull TaskScheduler taskScheduler,
                                    @NonNull EventBus<String, Object, Object> eventBus)
    {
        super("ClientGeneratorCollector", properties, collectorContext, taskScheduler, eventBus);
        if (!(collectorContext instanceof ClientCollectorContext))
            throw new IllegalArgumentException("Invalid CollectorContext provided. Expected: ClientCollectorContext, but got "+collectorContext.getClass().getName());
    }

    @Override
    public void setConfiguration(Object config) {
        log.info("Collectors::{}: setConfiguration: {}", collectorId, config);
        if (config instanceof List sensorConfigList) {
            configurations = sensorConfigList.stream()
                    .filter(o -> o instanceof Map)
                    .filter(map -> ((Map)map).keySet().stream().allMatch(k->k instanceof String))
                    .toList();
            log.info("Collectors::{}: setConfiguration: {}", collectorId, configurations);

            if (! configurations.isEmpty()) {

                applyConfigurations(configurations);

                if (!started) {
                    log.info("Collectors::{}: Starting collector", collectorId);
                    properties.setEnable(true);
                    start();
                } else {
                    log.info("Collectors::{}: Restarting collector", collectorId);
                    stop();
                    start();
                }
            } else {
                if (started) {
                    log.info("Collectors::{}: Stopping collector", collectorId);
                    stop();
                }
                properties.setEnable(false);
                log.info("Collectors::{}: Collector disabled", collectorId);
            }
        } else {
            if (started) {
                log.info("Collectors::{}: Stopping collector (2)", collectorId);
                stop();
            }
            properties.setEnable(false);
            log.info("Collectors::{}: Collector disabled (2)", collectorId);
        }
    }

    private void applyConfigurations(List<Map<String, Object>> configurations) {
        log.info("Collectors::{}: applyConfigurations: Properties BEFORE:\n{}", collectorId, properties);

        Set<String> destinationSet = new HashSet<>();
        AtomicLong delay = new AtomicLong(1000L);

        configurations.forEach(config -> {
            String destination = config.getOrDefault("name", "").toString();
            Object interval = config.getOrDefault("interval", Map.of());
            Object sensorConfig =  config.getOrDefault("configuration", Map.of());

            log.info("Collectors::{}: applyConfigurations: name={}, interval={}, sensor-config={}",
                    collectorId, destination, interval, sensorConfig);

            if (StringUtils.isBlank(destination)) return;
            destinationSet.add(destination);

            if (interval instanceof Map intervalMap) {
                String period = intervalMap.getOrDefault("period", "").toString().trim();
                String unit = intervalMap.getOrDefault("unit", "").toString().trim().toLowerCase();
                if (StringUtils.isNotBlank(period) && StringUtils.isNotBlank(unit)) {
                    Duration duration = getDuration(period, unit);
                    delay.set(duration.toMillis());
                }
            }

            if (sensorConfig instanceof Map sensorConfigMap) {
                String type = sensorConfigMap.getOrDefault("mode", "").toString().trim();
                String valueType = sensorConfigMap.getOrDefault("valueType", "").toString().trim();

                String limitsLower = sensorConfigMap.getOrDefault("limitsLower", "").toString().trim();
                String limitsUpper = sensorConfigMap.getOrDefault("limitsUpper", "").toString().trim();
                String limitsStart = sensorConfigMap.getOrDefault("limitsStart", "").toString().trim();
                String limitsStep = sensorConfigMap.getOrDefault("limitsStep", "").toString().trim();
                String limitsOnOverflow = sensorConfigMap.getOrDefault("limitsOnOverflow", "").toString().trim();
                String limitsMaxCount = sensorConfigMap.getOrDefault("limitsMaxCount", "").toString().trim();

                String format = sensorConfigMap.getOrDefault("format", "").toString().trim();

                if (StringUtils.isNotBlank(type))
                    properties.setType(GeneratorCollectorProperties.GENERATOR_TYPE.valueOf(type));
                if (StringUtils.isNotBlank(valueType))
                    properties.setValueType(GeneratorCollectorProperties.GENERATOR_VALUE_TYPE.valueOf(valueType));
                if (StringUtils.isNotBlank(limitsLower))
                    properties.setLimitsLower(Double.parseDouble(limitsLower));
                if (StringUtils.isNotBlank(limitsUpper))
                    properties.setLimitsUpper(Double.parseDouble(limitsUpper));
                if (StringUtils.isNotBlank(limitsStart))
                    properties.setLimitsStart(Double.parseDouble(limitsStart));
                else
                    properties.setLimitsStart(properties.getLimitsLower());
                if (StringUtils.isNotBlank(limitsStep))
                    properties.setLimitsStep(Double.parseDouble(limitsStep));
                if (StringUtils.isNotBlank(limitsOnOverflow))
                    properties.setLimitsOnOverflow(GeneratorCollectorProperties.GENERATOR_OVERFLOW.valueOf(limitsOnOverflow));
                if (StringUtils.isNotBlank(limitsMaxCount))
                    properties.setLimitsMaxCount(Long.parseLong(limitsMaxCount));

                if (StringUtils.isNotBlank(format))
                    properties.setFormat(new SimpleDateFormat(format));
            }
        });

        properties.setDestinations( destinationSet.stream().toList() );
        properties.setDelay(delay.get());
        properties.setCreateTopic(true);
        log.info("Collectors::{}: applyConfigurations: Properties AFTER:\n{}", collectorId, properties);
    }

    private static Duration getDuration(String period, String unit) {
        long value = Long.parseLong(period);
        return switch (unit) {
            case "ms", "millisecond", "milliseconds" -> Duration.ofMillis(value);
            case "s", "sec", "secs", "second", "seconds" -> Duration.ofSeconds(value);
            case "m", "min", "mins", "minute", "minutes" -> Duration.ofMinutes(value);
            case "h", "hr", "hrs", "hour", "hours" -> Duration.ofHours(value);
            case "d", "day", "days" -> Duration.ofDays(value);
            default -> throw new IllegalArgumentException("Unknown time unit: " + unit);
        };
    }

    @Override
    public synchronized void activeGroupingChanged(String oldGrouping, String newGrouping) {
    }
}
