/*
 * Copyright (C) 2019 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */


package eu.melodic.vassilis.staff;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Data
@ConfigurationProperties
@PropertySource("file:${MELODIC_CONFIG_DIR}/eu.melodic.config.properties")
@Slf4j
public class PenaltyFunctionProperties {
    private final Map<String, String> startupTimes = new HashMap<>();

    private String stateInfo;
	private String host;
	private String port;


}


