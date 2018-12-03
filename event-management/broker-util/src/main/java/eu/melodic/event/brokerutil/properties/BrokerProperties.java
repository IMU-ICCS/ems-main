/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.brokerutil.properties;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@ToString(exclude={"truststorePassword","keystorePassword","brokerPassword"})
@Configuration
@ConfigurationProperties(prefix = "brokerutil")
@PropertySource("file:${MELODIC_CONFIG_DIR}/eu.melodic.event.brokerutil.properties")
@Slf4j
public class BrokerProperties {
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
}
