/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.translate.properties;

import eu.melodic.event.util.EmsConstant;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = EmsConstant.EMS_PROPERTIES_PREFIX + "translator")
//@PropertySource("file:${MELODIC_CONFIG_DIR}/eu.melodic.event.translator.properties")
public class CamelToEplTranslatorProperties implements InitializingBean {
    @Override
    public void afterPropertiesSet() {
        log.debug("CamelToEplTranslatorProperties: {}", this);
    }

    // Translator parameters
    private String sensorConfigurationAnnotation;
    private long sensorMinInterval;
    private long sensorDefaultInterval;

    private String leafNodeGrouping;
    private boolean pruneMvv = true;
    private boolean addTopLevelMetrics = true;
    private String fullNamePattern;
    private boolean formulaCheckEnabled = true;

    // Translation Results & Graph print/export Switches
    private boolean printResults = true;
    private Dag dag = new Dag();

    // Load-annotated metric settings
    private String loadMetricAnnotation = "MELODICMetadataSchema.UtilityNotions.UtilityRelatedProperties.Utility.BusyInstanceMetric";
    private String loadMetricVariableFormatter = "BUSY_METRIC_%s";

    @Data
    public static class Dag {
        // Graph rendering/export
        private boolean exportToDotEnabled = true;
        private boolean exportToFileEnabled = true;

        // Graph rendering parameters
        private String exportPath;
        private String[] exportFormats;
        private int exportImageWidth = -1;
    }

    // Active sink types
    private List<String> sinks;

    // Sink type configurations
    private final Map<String,Map<String,String>> sinkConfig = new HashMap<>();

    public Map<String,Map<String,String>> getSinkConfig() {
        return sinkConfig;
    }
}
