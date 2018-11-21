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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@ConfigurationProperties(prefix = "brokercep")
@PropertySource("file:${MELODIC_CONFIG_DIR}/eu.melodic.event.brokercep.properties")
@Slf4j
public class BrokerCepProperties {
	@Value("${broker-name:broker}")
	private String brokerName;
	@Value("${broker-url:tcp://localhost:61616}")
	private String brokerUrl;
	@Value("${connector-port:-1}")
	private int connectorPort;
}
