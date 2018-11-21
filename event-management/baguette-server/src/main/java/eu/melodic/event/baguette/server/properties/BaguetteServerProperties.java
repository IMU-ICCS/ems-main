/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.baguette.server.properties;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "baguette.server")
@PropertySource("file:${MELODIC_CONFIG_DIR}/eu.melodic.event.baguette-server.properties")
@Slf4j
public class BaguetteServerProperties {
	@NotNull @Size(min=1, message="Please provide a valid Coordinator class (including package)")
	@Value("${baguette.server.coordinator.class}")
	private String coordinatorClass;
	
	@Value("${baguette.server.registration-window:30000}") @Min(-1)
	private long registrationWindow;
	@Value("${baguette.server.num-of-instances:-1}") @Min(-1)
	private int numberOfInstances;
	@Value("${baguette.server.num-of-segments:-1}") @Min(-1)
	private int NumberOfSegments;
	
	@Value("${baguette.server.port:2222}")
	@Min(value=1, message="Valid server ports are between 1 and 65535. Please prefer ports higher than 1023.")
	@Max(value=65535, message="Valid server ports are between 1 and 65535. Please prefer ports higher than 1023.")
	private int serverPort;
	@Value("${baguette.server.key.file:hostkey.ser}")
	private String serverKeyFile;
	@Value("${baguette.server.heartbeat:false}")
	private boolean heartbeatEnabled;
	@Value("${baguette.server.heartbeat.period:60000}") @Min(-1)
	private long heartbeatPeriod;
	
//XXX: TODO: to-be removed (Server or Coordinator must be able to figure them out automatically)
	@Value("${baguette.server.third-level.address:}")
	private String thirdLevelAddress;
	@Value("${baguette.server.third-level.port:61616}")
	private int thirdLevelPort;

//XXX: TODO: to-be removed (Server or Coordinator must be able to figure them out automatically)
	@Value("${baguette.server.broker.config.template:}")
	private String brokerConfigTemplate;
	@Value("${baguette.server.broker.config.file:}")
	private String brokerConfigFile;
	@Value("${baguette.server.broker.address:}")
	private String brokerAddress;
	@Value("${baguette.server.broker.address-override:}")
	private String brokerAddressOverride;
	@Value("${baguette.server.broker.port:61616}")
	private int brokerPort;

//XXX: TODO: to-be removed (Server or Coordinator must be able to figure them out automatically)
	@Value("${baguette.server.client.config.template:}")
	private String clientConfigTemplate;
	@Value("${baguette.server.client.config.file:}")
	private String clientConfigFile;
	@Value("${baguette.server.client.address-override:}")
	private String clientAddressOverride;
	@Value("${baguette.server.client.port:61616}")
	private int clientPort;

}
