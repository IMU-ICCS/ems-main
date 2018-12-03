/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.brokerutil.event;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class EventMap extends HashMap<String,Object> implements Serializable {
	public EventMap() { super(); }
	public EventMap(Map<String,Object> map) { super(map); }
	public EventMap(double metricValue, int level, long timestamp) { put("metricValue", metricValue); put("level", level); put("timestamp", timestamp); }
	
	public static String[] getPropertyNames() { return new String[] { "metricValue", "level", "timestamp" }; }
	public static Class[] getPropertyClasses() { return new Class[] { Double.class, Integer.class, Long.class }; }
}