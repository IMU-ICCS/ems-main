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

    private boolean printBuildInfo;

    @Value("${IP_SETTING}")
    private IpSetting ipSetting = IpSetting.PUBLIC_IP;
    @Value("${EXECUTIONWARE}")
    private ExecutionWare executionware = ExecutionWare.PROACTIVE;

    private String upperwareGrouping;
    private String metasolverConfigurationUrl;
    private String esbUrl;

    private String preloadCamelModel;
    private String preloadCpModel;

    private boolean skipTranslation;
    private boolean skipMvvRetrieve;
    private boolean skipBrokerCep;
    private boolean skipBaguette;
    private boolean skipCollectors;
    private boolean skipMetasolver;
    private boolean skipEsbNotification;

    private String tcLoadFile;
    private String tcSaveFile;
    private boolean eventDebugEnabled;

    private boolean exitAllowed;
    @Min(1)
    private long exitGracePeriod = 10;
    private int exitCode = 0;

    @Value("${static.resource.context}")
    private String staticResourceContext = "/**";

    @Value("${password-encoder-class:}")
    private String passwordEncoderClass;

    // control.ssl.** settings
    private KeystoreAndCertificateProperties ssl;
}
