/*
 * Copyright (C) 2019 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.penaltycalculator;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Data
@ConfigurationProperties
@PropertySource("file:${MELODIC_CONFIG_DIR}/eu.melodic.penalty.properties")
public class PenaltyFunctionProperties {
    private final Map<String, String> startupTimes = new HashMap<>();

    private String stateInfo;
	private String host;
	private String port;

	private final Map<String,VmData> vmData = new HashMap<>();

	public Map<String,VmData> getVmData() {
	    return vmData;
    }

	@Data
    public static class VmData {
        int cores;
        double ram;
        double disk;
        int startupTime;

        public double[] getX() {
            double[] x = {cores, ram, disk};
            return x;
        }

        public double getY() { return startupTime; }
    }
}
