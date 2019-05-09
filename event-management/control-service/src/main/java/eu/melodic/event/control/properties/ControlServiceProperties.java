/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.control.properties;

import eu.melodic.event.util.IKeystoreAndCertificateProperties;
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
public class ControlServiceProperties implements IKeystoreAndCertificateProperties {
    @Value("${control.upperware-grouping}")
    private String upperwareGrouping;
    @Value("${control.metasolver-configuration-url}")
    private String metasolverConfigurationUrl;
    @Value("${control.esb-url}")
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

    @Value("${control.password-encoder}")
    private String passwordEncoderClass;

    @Value("${default-ip-address:}")
    private String defaultIpAddress;
    @Value("${public-ip-address:}")
    private String publicIpAddress;

    @Value("${control.ssl.keystore.file:}")
    private String keystoreFile;
    @Value("${control.ssl.keystore.type:}")
    private String keystoreType;
    @Value("${control.ssl.keystore.password:}")
    private String keystorePassword;
    @Value("${control.ssl.truststore.file:}")
    private String truststoreFile;
    @Value("${control.ssl.truststore.type:}")
    private String truststoreType;
    @Value("${control.ssl.truststore.password:}")
    private String truststorePassword;
    @Value("${control.ssl.certificate.file:}")
    private String certificateFile;

    @Value("${control.ssl.entry.generate:IF-IP-CHANGED}")
    private KEY_ENTRY_GENERATE keyEntryGenerate;
    @Value("${control.ssl.entry.name:ems}")
    private String keyEntryName;
    @Value("${control.ssl.entry.dname:CN=ems,OU=Unknown,O=Unknown,L=Unknown,ST=Unknown,C=Unknown}")
    private String keyEntryDName;
    @Value("${control.ssl.entry.ext-san:dns:localhost,ip:127.0.0.1,ip:%{DEFAULT_IP}%,ip:%{PUBLIC_IP}%}")
    private String keyEntryExtSAN;

    public String getKeyEntryNameValue() { return KeystoreAndCertificateProperties.prepareValue(keyEntryName, publicIpAddress, defaultIpAddress, "127.0.0.1"); }
    public String getKeyEntryDNameValue() { return KeystoreAndCertificateProperties.prepareValue(keyEntryDName, publicIpAddress, defaultIpAddress, "127.0.0.1"); }
    public String getKeyEntryExtSANValue() { return KeystoreAndCertificateProperties.prepareValue(keyEntryExtSAN, publicIpAddress, defaultIpAddress, "127.0.0.1"); }
}
