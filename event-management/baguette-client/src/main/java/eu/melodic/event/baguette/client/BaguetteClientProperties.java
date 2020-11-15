/*
 * Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.client;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties
@PropertySource("file:${MELODIC_CONFIG_DIR}/baguette-client.properties")
@ToString(exclude = "serverPassword")
public class BaguetteClientProperties {
	private long authTimeout = 60000;
	private long execTimeout = 120000;
	private long retryPeriod = 60000;

	private boolean exitCommandAllowed = false;
	private int killDelay = 5;

	private List<Class<Collector>> collectorClasses;

	private String clientId;
	private String debugFakeIpAddress;

	private String serverAddress = "127.0.0.1";
	private int serverPort = 22;
	private String serverPubkey;
	private String serverFingerprint;

	private String serverUsername;
	private String serverPassword;
}
