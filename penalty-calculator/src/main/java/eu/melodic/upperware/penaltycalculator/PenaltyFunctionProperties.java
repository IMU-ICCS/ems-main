/*
 * Copyright (C) 2017-2020 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.upperware.penaltycalculator;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Data
@ConfigurationProperties
@PropertySource("file:${MELODIC_CONFIG_DIR}/eu.melodic.penalty.properties")
public class PenaltyFunctionProperties {
    // Memcache connection settings
    private String memcacheHost;
    private int memcachePort;

    // InfluxDB connection settings
    private String influxDbHost;
	private int influxDbPort;
	private String influxDbUsername;
	private String influxDbPassword;
	private String influxDbName;

    // Penalty calculation settings
	private boolean skipComponentDeploymentTimes = false;

	// Predefined VM characteristics and startup times
    private final Map<String, VmData> vmData = new LinkedHashMap<>();

    public Map<String, VmData> getVmData() {
        return vmData;
    }

    @Data
    public static class VmData {
        int cores;
        double ram;
        double disk;
        int startupTime;

        public double[] getCharacteristics() {
            double[] x = {cores, ram, disk};
            return x;
        }
    }
}
