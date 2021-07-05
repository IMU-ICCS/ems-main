/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.brokerclient.properties;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@ToString(exclude = {"truststorePassword", "keystorePassword", "brokerPassword"})
@Configuration
@ConfigurationProperties(prefix = "brokerclient")
//@PropertySource("file:${MELODIC_CONFIG_DIR}/eu.melodic.event.brokerclient.properties")
@Slf4j
public class BrokerClientProperties {
    @Value("${broker-name:broker}")
    private String brokerName;
    @Value("${broker-url:tcp://localhost:61616}")
    private String brokerUrl;
    @Value("${broker-url-properties:}")
    private String brokerUrlProperties;
    @Value("${ssl.client-auth.required:false}")
    private boolean clientAuthRequired;
    @Value("${connector-port:-1}")
    private int connectorPort;
    @Value("${preserve-connection:false}")
    private boolean preserveConnection;

    @Value("${ssl.truststore.file:}")
    private String truststoreFile;
    @Value("${ssl.truststore.type:}")
    private String truststoreType;
    @Value("${ssl.truststore.password:}")
    private String truststorePassword;
    @Value("${ssl.keystore.file:}")
    private String keystoreFile;
    @Value("${ssl.keystore.type:}")
    private String keystoreType;
    @Value("${ssl.keystore.password:}")
    private String keystorePassword;

    @Value("${broker-username:}")
    private String brokerUsername;
    @Value("${broker-password:}")
    private String brokerPassword;

    public BrokerClientProperties() {
        brokerName = "broker";
        brokerUrl = "tcp://localhost:61616";
        brokerUrlProperties = "";
        connectorPort = -1;
        preserveConnection = false;

        truststoreFile = "";
        truststoreType = "";
        truststorePassword = "";
        keystoreFile = "";
        keystoreType = "";
        keystorePassword = "";
        clientAuthRequired = false;

        brokerUsername = "";
        brokerPassword = "";
    }

    public BrokerClientProperties(java.util.Properties p) {
        brokerName = p.getProperty("brokerclient.broker-name", "broker");
        brokerUrl = p.getProperty("brokerclient.broker-url", "tcp://localhost:61616");
        brokerUrlProperties = p.getProperty("brokerclient.broker-url-properties", "");
        connectorPort = Integer.parseInt(p.getProperty("brokerclient.connector-port", "-1"));
        preserveConnection = Boolean.parseBoolean(p.getProperty("brokerclient.preserve-connection", "false"));

        truststoreFile = p.getProperty("brokerclient.ssl.truststore.file", "");
        truststoreType = p.getProperty("brokerclient.ssl.truststore.type", "");
        truststorePassword = p.getProperty("brokerclient.ssl.truststore.password", "");
        keystoreFile = p.getProperty("brokerclient.ssl.keystore.file", "");
        keystoreType = p.getProperty("brokerclient.ssl.keystore.type", "");
        keystorePassword = p.getProperty("brokerclient.ssl.keystore.password", "");
        clientAuthRequired = Boolean.parseBoolean(p.getProperty("brokerclient.ssl.client-auth.required", "false"));

        brokerUsername = p.getProperty("brokerclient.broker-username", "");
        brokerPassword = p.getProperty("brokerclient.broker-password", "");

        brokerUrlProperties = brokerUrlProperties.replace("${brokerclient.ssl.client-auth.required}", Boolean.toString(clientAuthRequired));
    }
}
