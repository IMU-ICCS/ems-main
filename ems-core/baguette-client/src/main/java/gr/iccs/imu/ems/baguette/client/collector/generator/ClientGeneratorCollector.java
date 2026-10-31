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
import gr.iccs.imu.ems.common.collector.generator.GeneratorCollector;
import gr.iccs.imu.ems.common.collector.generator.GeneratorCollectorProperties;
import gr.iccs.imu.ems.util.EventBus;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.function.Consumer;

/**
 * Generates measurements
 */
@Slf4j
@Component
public class ClientGeneratorCollector extends GeneratorCollector implements IClientCollector {
    private List<Map<String,Object>> configurations;

    public ClientGeneratorCollector(@NonNull GeneratorCollectorProperties properties,
                                    @NonNull CollectorContext collectorContext,
                                    @NonNull TaskScheduler taskScheduler,
                                    @NonNull EventBus<String, Object, Object> eventBus)
    {
        super(properties, taskScheduler, collectorContext);
        collectorId = this.getClass().getSimpleName();
        if (!(collectorContext instanceof ClientCollectorContext))
            throw new IllegalArgumentException("Invalid CollectorContext provided. Expected: ClientCollectorContext, but got "+collectorContext.getClass().getName());
    }

    @Override
    public void setConfiguration(Object config) {
        log.info("Collectors::{}: setConfiguration: {}", collectorId, config);
        if (config instanceof List sensorConfigList) {
            if (sensorConfigList.isEmpty()) {
                log.warn("Collectors::{}: Configuration is empty. Leaving generator as is", collectorId);
                return;
            }

            // Stop running generators
            log.trace("Collectors::{}: Stopping all running generator tasks...", collectorId);
            stopAllGenerators();

            // Process configurations and start new generators
            sensorConfigList.forEach(sensorConfig -> {
                log.trace("Collectors::{}: Processing sensor config: {}", collectorId, sensorConfig);
                if (sensorConfig instanceof Map sensorConfigMap) {
                    GeneratorCollectorProperties properties = new GeneratorCollectorProperties();
                    properties.setEnable(true);
                    properties.setCreateTopic(true);

                    // Destination
                    String name = getString(sensorConfigMap, "name");
                    if (StringUtils.isBlank(name)) return;
                    properties.setDestinations(List.of(name));

                    // Configuration
                    if (sensorConfigMap.get("configuration") instanceof Map configurationMap) {
                        applyConfig(configurationMap, "mode", value ->
                                properties.setType(
                                        GeneratorCollectorProperties.GENERATOR_TYPE.valueOf(value.toLowerCase())));
                        applyConfig(configurationMap, "valueType", value ->
                                properties.setValueType(
                                        GeneratorCollectorProperties.GENERATOR_VALUE_TYPE.valueOf(value)));


                        applyConfig(configurationMap, "limitsLower", value ->
                                properties.setLimitsLower(Double.parseDouble(value)));

                        applyConfig(configurationMap, "limitsUpper", value ->
                                properties.setLimitsUpper(Double.parseDouble(value)));

                        properties.setLimitsStart(properties.getLimitsLower());
                        applyConfig(configurationMap, "limitsStart", value ->
                                properties.setLimitsStart(Double.parseDouble(value)));

                        properties.setLimitsStep(1);
                        applyConfig(configurationMap, "limitsStep", value ->
                                properties.setLimitsStep(Double.parseDouble(value)));

                        properties.setLimitsOnOverflow(GeneratorCollectorProperties.GENERATOR_OVERFLOW.round);
                        applyConfig(configurationMap, "limitsOnOverflow", value ->
                                properties.setLimitsOnOverflow(
                                        GeneratorCollectorProperties.GENERATOR_OVERFLOW.valueOf(value.toLowerCase())));

                        properties.setLimitsMaxCount(-1);
                        applyConfig(configurationMap, "limitsMaxCount", value ->
                                properties.setLimitsMaxCount(Long.parseLong(value)));

                        applyConfig(configurationMap, "datetimeFormatter", value ->
                                properties.setDatetimeFormatter(new SimpleDateFormat(value)));

                        applyConfig(configurationMap, "zone", value ->
                                properties.setZone(TimeZone.getTimeZone(value).getID()));
                    } else {
                        throw new IllegalArgumentException("Sensor 'config' block is missing or not a Map: "
                                + sensorConfigMap.get("configuration").getClass());
                    }

                    // Interval
                    if (sensorConfigMap.get("interval") instanceof Map intervalMap) {
                        long period = Long.parseLong(
                                getString(intervalMap, "period", "30"));
                        String unit = getString(intervalMap, "unit", "milliseconds");

                        properties.setDelay(
                                getDuration(period, unit).toMillis());
                    }

                    log.debug("Collectors::{}: Generator properties: {}", collectorId, properties);

                    // Start generator
                    startGenerator(properties);

                } else {
                    throw new IllegalArgumentException("Sensor configuration is not Map: " + sensorConfig.getClass());
                }
            });
        }
        log.debug("Collectors::{}: setConfiguration: DONE", collectorId);
    }

    private String getString(Map map, String key) {
        return getString(map, key, "");
    }

    private String getString(Map map, String key, String defaultValue) {
        return map.getOrDefault(key, defaultValue).toString().trim();
    }

    private void applyConfig(Map map, String key, Consumer<String> task) {
        String value = getString(map, key);
        if (StringUtils.isNotBlank(value))
            task.accept(value);
    }

    private static Duration getDuration(long period, String unit) {
        unit = unit.toLowerCase();
        return switch (unit) {
            case "ms", "millisecond", "milliseconds" -> Duration.ofMillis(period);
            case "s", "sec", "secs", "second", "seconds" -> Duration.ofSeconds(period);
            case "m", "min", "mins", "minute", "minutes" -> Duration.ofMinutes(period);
            case "h", "hr", "hrs", "hour", "hours" -> Duration.ofHours(period);
            case "d", "day", "days" -> Duration.ofDays(period);
            default -> throw new IllegalArgumentException("Unknown time unit: " + unit);
        };
    }

    @Override
    public synchronized void activeGroupingChanged(String oldGrouping, String newGrouping) {
    }
}
