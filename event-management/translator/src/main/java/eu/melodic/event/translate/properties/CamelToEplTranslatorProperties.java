/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.translate.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Validated
@Configuration
@ConfigurationProperties
@Slf4j
@PropertySource("file:${MELODIC_CONFIG_DIR}/eu.melodic.event.translator.properties")
public class CamelToEplTranslatorProperties {
    // Translator parameters
    @Value("${translator.sensor-config-annotation}")
    private String sensorConfigurationAnnotation;
    @Value("${translator.sensor-min-interval}")
    private long sensorMinInterval;
    @Value("${translator.sensor-default-interval}")
    private long sensorDefaultInterval;

    @Value("${translator.prune-mvv:true}")
    private boolean pruneMvv;
    @Value("${translator.add-top-level-metrics:true}")
    private boolean addTopLevelMetric;
    @Value("${translator.full-name-pattern}")
    private String fullNamePattern;
    @Value("${translator.formula-check-enabled:true}")
    private boolean formulaCheckEnabled;

    // Translation Results & Graph print/export Switches
    @Value("${translator.print-results:true}")
    private boolean printResults;
    @Value("${dag.export-to-dot.enabled:true}")
    private boolean exportToDotEnabled;
    @Value("${dag.export-to-file.enabled:true}")
    private boolean exportToFileEnabled;

    // Graph rendering parameters
    @Value("${dag.export.path:}")
    private String exportPath;
    @Value("${dag.export.formats:}")
    private String[] exportFormats;
    @Value("${dag.export.image-width:-1}")
    private int exportImageWidth;

    // Active sink types
    @Value("${active-sinks:}")
    private List<String> sinks;

    // Sink type configurations
    private final Map<String,Map<String,String>> sinkConfig = new HashMap<>();

    public Map<String,Map<String,String>> getSinkConfig() {
        return sinkConfig;
    }
}
