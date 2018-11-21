/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.brokercep.test;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter @ToString
@EqualsAndHashCode
public class VisorEvent implements Serializable {
	private final double metricValue;
	private final String vmName;
	private final String cloudName;
	private final String componentName;
	private final int level;
	private final long timestamp;
}