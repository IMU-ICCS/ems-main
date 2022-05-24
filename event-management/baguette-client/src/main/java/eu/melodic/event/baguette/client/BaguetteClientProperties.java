/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.client;

import eu.melodic.event.common.client.SshClientProperties;
import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties
@PropertySource(value = {
		"file:${MELODIC_CONFIG_DIR}/ems-client.yml",
		"file:${MELODIC_CONFIG_DIR}/ems-client.properties",
		"file:${MELODIC_CONFIG_DIR}/baguette-client.yml",
		"file:${MELODIC_CONFIG_DIR}/baguette-client.properties"
}, ignoreResourceNotFound = true)
@ToString(callSuper = true)
public class BaguetteClientProperties extends SshClientProperties {
	private boolean exitCommandAllowed = false;
	private int killDelay = 5;

	private List<Class<Collector>> collectorClasses;

	private String debugFakeIpAddress;

	private long sendStatisticsDelay = 10000L;
}
