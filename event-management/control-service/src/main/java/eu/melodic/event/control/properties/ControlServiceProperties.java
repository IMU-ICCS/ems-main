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

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "control")
@PropertySource("file:${MELODIC_CONFIG_DIR}/eu.melodic.event.control.properties")
@Slf4j
public class ControlServiceProperties {
    public enum IpSetting {
        DEFAULT_IP("%{DEFAULT_IP}%"),
        PUBLIC_IP("%{PUBLIC_IP}%");

        private final String placeholder;

        IpSetting(String placeholder) {
            this.placeholder = placeholder;
        }

        public String toString() {
            return placeholder;
        }
    }

    public enum ExecutionWare {
        CLOUDIATOR, PROACTIVE
    }

    @Value("${dontPrintBuildInfo:true}")
    private boolean printBuildInfo;
    @Value("${IP_SETTING:}")
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

    @Value("${password-encoder-class}")
    private String passwordEncoderClass;

    // control.ssl.** settings
    private KeystoreAndCertificateProperties ssl;
}
