/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.metasolver.metricvalue;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(doNotUseGetters = true, exclude={"host_name", "component_name", "level"})
public class MetricValueEvent {
	private String metric_value;
	private String[] host_name;
	private String[] component_name;
	private int level;
	private long timestamp;
}
