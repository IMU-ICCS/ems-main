/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.brokercep.properties;

import eu.melodic.event.util.KeystoreAndCertificateProperties;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "brokercep")
@PropertySource("file:${MELODIC_CONFIG_DIR}/eu.melodic.event.brokercep.properties")
public class BrokerCepProperties {

    @Value("${broker-name:broker}")
    private String brokerName;

    // Broker connector URLs
    private List<String> brokerUrl = Collections.singletonList("ssl://0.0.0.0:61616");

    public String getBrokerUrl() { return brokerUrl.get(0); }
    public List<String> getBrokerUrlList() { return brokerUrl; }

    @Value("${broker-url-for-consumer:ssl://127.0.0.1:61616}")
    private String brokerUrlForConsumer;
    @Value("#{ '${brokercep.broker-url-for-clients}'!='' ? '${brokercep.broker-url-for-clients}' : 'ssl://'+T(eu.melodic.event.util.NetUtil).getPublicIpAddress()+':61616' }")
    private String brokerUrlForClients;

    @Value("${connector-port:-1}")
    private int connectorPort;
    @Value("${bypass-local-broker:false}")
    private boolean bypassLocalBroker;

    // brokercep.ssl.** settings
    private KeystoreAndCertificateProperties ssl;

    @Value("${authentication-enabled:false}")
    private boolean authenticationEnabled;
    @ToString.Exclude
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

    private List<MessageInterceptorConfig> messageInterceptors;
    private Map<String,MessageInterceptorSpec> messageInterceptorsSpecs = new HashMap<>();

    @Value("${message-forward-destinations:}#{T(java.util.Collections).emptyList()}")
    private List<ForwardDestinationConfig> messageForwardDestinations;

    @Value("${enable-advisory-watcher:true}")
    private boolean enableAdvisoryWatcher;

    @Value("${brokercep.usage.memory.jvm-heap-percentage:-1}")
    private int memoryJvmHeapPercentage;
    @Value("${brokercep.usage.memory.size:-1}")
    private long memorySize;

    @Data
    public static class MessageInterceptorSpec {
        private String className;
        private List<String> params;
    }
    @Data
    @ToString(callSuper = true)
    public static class MessageInterceptorConfig extends MessageInterceptorSpec {
        private String destination;
    }
    @Data
    public static class ForwardDestinationConfig {
        private String connectionString;
        private String username;
        @ToString.Exclude
        private String password;
    }
}
