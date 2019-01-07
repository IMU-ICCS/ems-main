/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.translate.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

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

    @Value("${translator.prune-mvv}")
    private boolean pruneMvv;
    @Value("${translator.full-name-pattern}")
    private String fullNamePattern;
    @Value("${translator.formula-check-enabled:true}")
    private boolean formulaCheckEnabled;

    // Graph rendering parameters
    @Value("${dag.export.path:}")
    private String exportPath;
    @Value("${dag.export.formats:}")
    private String[] exportFormats;
    @Value("${dag.export.image-width:-1}")
    private int exportImageWidth;
}
