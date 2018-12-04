/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.dlms.metric.receiver.metricvalue;

import lombok.Getter;

@Getter
//@ToString(doNotUseGetters = true, exclude = { "host_name", /* "component_name", */ "level" })
public class MetricValueEventDataCenter {
	private String cloudProvider1;
	private boolean isCp1Public = true;
	private String dataCenter1;
	private String region1;
	private String cloudProvider2;
	private boolean isCp2Public = true;
	private String dataCenter2;
	private String region2;
	private int latencyVal;
	private int bandwidthVal;
	private long timeStamp;
}
