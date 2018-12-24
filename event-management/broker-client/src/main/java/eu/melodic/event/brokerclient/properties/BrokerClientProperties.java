/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
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
@PropertySource("file:${MELODIC_CONFIG_DIR}/eu.melodic.event.brokerclient.properties")
@Slf4j
public class BrokerClientProperties {
    @Value("${broker-name:broker}")
    private String brokerName;
    @Value("${broker-url:ssl://localhost:61616}")
    private String brokerUrl;
    @Value("${broker-url-properties:}")
    private String brokerUrlProperties;
    @Value("${connector-port:-1}")
    private int connectorPort;

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
    //XXX:DEL-IF: not really needed in client authentication
    @Value("${ssl.client-auth.required:false}")
    private boolean clientAuthRequired;

    @Value("${broker-username:}")
    private String brokerUsername;
    @Value("${broker-passwotd:}")
    private String brokerPassword;

    public BrokerClientProperties() {
        brokerName = "broker";
        brokerUrl = "ssl://localhost:61616}";
        brokerUrlProperties = "";
        connectorPort = -1;

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
        brokerUrl = p.getProperty("brokerclient.broker-url", "ssl://localhost:61616}");
        brokerUrlProperties = p.getProperty("brokerclient.broker-url-properties", "");
        connectorPort = Integer.parseInt(p.getProperty("brokerclient.connector-port", "-1"));

        truststoreFile = p.getProperty("brokerclient.ssl.truststore.file", "");
        truststoreType = p.getProperty("brokerclient.ssl.truststore.type", "");
        truststorePassword = p.getProperty("brokerclient.ssl.truststore.password", "");
        keystoreFile = p.getProperty("brokerclient.ssl.keystore.file", "");
        keystoreType = p.getProperty("brokerclient.ssl.keystore.type", "");
        keystorePassword = p.getProperty("brokerclient.ssl.keystore.password", "");
        clientAuthRequired = Boolean.parseBoolean(p.getProperty("brokerclient.ssl.client-auth.required", "false"));

        brokerUsername = p.getProperty("brokerclient.broker-username", "");
        brokerPassword = p.getProperty("brokerclient.broker-passwotd", "");

        brokerUrlProperties = brokerUrlProperties.replace("${brokerclient.ssl.client-auth.required}", Boolean.toString(clientAuthRequired));
    }
}
