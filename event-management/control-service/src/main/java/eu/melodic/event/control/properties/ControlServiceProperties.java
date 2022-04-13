/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.control.properties;

import eu.melodic.event.util.KeystoreAndCertificateProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Slf4j
@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "control")
@PropertySource("file:${MELODIC_CONFIG_DIR}/eu.melodic.event.control.properties")
public class ControlServiceProperties {
    public enum IpSetting {
        DEFAULT_IP,
        PUBLIC_IP
    }

    public enum ExecutionWare {
        CLOUDIATOR, PROACTIVE
    }

    @Value("${control.printBuildInfo:false}")
    private boolean printBuildInfo;
    @Value("${IP_SETTING:PUBLIC_IP}")
    private IpSetting ipSetting;
    @Value("${EXECUTIONWARE:PROACTIVE}")
    private ExecutionWare executionware;

    @Value("${control.upperware-grouping}")
    private String upperwareGrouping;
    @Value("${control.metasolver-configuration-url:}")
    private String metasolverConfigurationUrl;
    @Value("${control.esb-url:}")
    private String esbUrl;

    @Value("${control.preload.camel-model:}")
    private String preloadCamelModel;
    @Value("${control.preload.cp-model:}")
    private String preloadCpModel;

    @Value("${control.skip-translation:false}")
    private boolean skipTranslation;
    @Value("${control.skip-mvv-retrieve:false}")
    private boolean skipMvvRetrieve;
    @Value("${control.skip-broker-cep:false}")
    private boolean skipBrokerCep;
    @Value("${control.skip-baguette:false}")
    private boolean skipBaguette;
    @Value("${control.skip-collectors:false}")
    private boolean skipCollectors;
    @Value("${control.skip-metasolver:false}")
    private boolean skipMetasolver;
    @Value("${control.skip-esb-notification:false}")
    private boolean skipEsbNotification;

    @Value("${control.tc-load-file:}")
    private String tcLoadFile;
    @Value("${control.tc-save-file:}")
    private String tcSaveFile;
    @Value("${control.event-debug-enabled:false}")
    private boolean eventDebugEnabled;

    @Value("${control.exit-allowed:false}")
    private boolean exitAllowed;
    @Value("${control.exit-grace-period:10}") @Min(1)
    private long exitGracePeriod;
    @Value("${control.exit-code:0}")
    private int exitCode;

    @Value("${static.resource.context:/**}")
    private String staticResourceContext;

    @Value("${password-encoder-class:}")
    private String passwordEncoderClass;

    @Value("${info.metrics.update.interval:1000}") @Min(1)
    private long metricsUpdateInterval;
    @Value("${info.client.metrics.update.interval:10000}") @Min(1)
    private long metricsClientUpdateInterval;
    @Value("${info.metrics.stream.update.interval:10}") @Min(1)
    private int metricsStreamUpdateInterval;    // in seconds
    @Value("${info.metrics.stream.event.name:ems-metrics-event}") @NotBlank
    private String metricsStreamEventName;
    @Value("${info.metrics.include.env-vars-with-prefixes: WEBSSH_SERVICE_-^, WEB_ADMIN_!^}")
    private List<String> envVarPrefixes;    // ! at the end means to trim off the prefix; - at the end means to convert '_' to '-';
                                            // ^ at the end means convert to upper case; ~ at the end means convert to lower case;

    // control.ssl.** settings
    private KeystoreAndCertificateProperties ssl;
}
