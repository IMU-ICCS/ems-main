/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.brokercep.properties;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@ToString(exclude = {"truststorePassword", "keystorePassword"})
@Configuration
@ConfigurationProperties(prefix = "brokercep")
@PropertySource("file:${MELODIC_CONFIG_DIR}/eu.melodic.event.brokercep.properties")
@Slf4j
public class BrokerCepProperties {
    @Value("${broker-name:broker}")
    private String brokerName;
    @Value("${broker-url:ssl://0.0.0.0:61616}")
    private String brokerUrl;
    @Value("${broker-url-for-consumer:ssl://localhost:61616}")
    private String brokerUrlForConsumer;
    //@Value("${broker-url-for-clients:}")
    @Value("#{ '${brokercep.broker-url-for-clients}'!='' ? '${brokercep.broker-url-for-clients}' : 'ssl://'+T(eu.melodic.event.baguette.server.util.NetUtil).getPublicIpAddress()+':61616' }")
    private String brokerUrlForClients;
    @Value("${broker-url-properties:}")
    private String brokerUrlProperties;
    @Value("${brokercep.ssl.client-auth.required:false}")
    private boolean clientAuthRequired;
    @Value("${connector-port:-1}")
    private int connectorPort;
    @Value("${bypass-local-broker:false}")
    private boolean bypassLocalBroker;

    /*	@Value("${brokercep.ssl.enable:true}")
        private boolean sslEnabled;
    */
    @Value("${brokercep.ssl.truststore.file:}")
    private String truststoreFile;
    @Value("${brokercep.ssl.truststore.type:}")
    private String truststoreType;
    @Value("${brokercep.ssl.truststore.password:}")
    private String truststorePassword;
    @Value("${brokercep.ssl.keystore.file:}")
    private String keystoreFile;
    @Value("${brokercep.ssl.keystore.type:}")
    private String keystoreType;
    @Value("${brokercep.ssl.keystore.password:}")
    private String keystorePassword;

    @Value("${authentication-enabled:false}")
    private boolean authenticationEnabled;
    @Value("${additional-broker-credentials:}")
    private String additionalBrokerCredentials;
    @Value("${authorization-enabled:false}")
    private boolean authorizationEnabled;

    @Value("${broker-persistence-enabled:false}")
    private boolean brokerPersistenceEnabled;
    @Value("${broker-using-jmx:false}")
    private boolean brokerUsingJmx;
    @Value("${broker-advisory-support-enabled:false}")
    private boolean brokerAdvisorySupportEnabled;
    @Value("${broker-using-shutdown-hook:false}")
    private boolean brokerUsingShutdownHook;

    @Value("${broker-enable-statistics:false}")
    private boolean enableStatistics;
    @Value("${broker-populate-jmsx-user-id:false}")
    private boolean populateJmsxUserId;

    @Value("${brokercep.usage.memory.jvm-heap-percentage:-1}")
    private int memoryJvmHeapPercentage;
    @Value("${brokercep.usage.memory.size:-1}")
    private long memorySize;

    @Value("${brokercep-password-encoder:eu.passage.upperware.commons.passwords.IdentityPasswordEncoder}")
    private String passwordEncoder;
}
