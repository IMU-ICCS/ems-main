/*
 * Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.brokercep.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@Slf4j
@EqualsAndHashCode(callSuper = false)
public class EventMap extends HashMap<String, Object> implements Serializable {
    public EventMap() {
        super();
    }

    public EventMap(Map<String, Object> map) {
        super(map);
    }

    public EventMap(double metricValue) {
        put("metricValue", metricValue);
        put("timestamp", System.currentTimeMillis());
    }

    public EventMap(double metricValue, long timestamp) {
        put("metricValue", metricValue);
        put("timestamp", timestamp);
    }

    public EventMap(double metricValue, int level, long timestamp) {
        put("metricValue", metricValue);
        put("level", level);
        put("timestamp", timestamp);
    }

    public static EventMap parseEventMap(String s) {
        if (s==null) return null;
        s = s.trim();
        if (s.isEmpty()) return null;
        if (s.startsWith("{") && s.endsWith("}")) s = s.substring(1, s.length()-1).trim();
        String[] pairs = s.split(",");
        EventMap eventMap = new EventMap();
        for (String pair : pairs) {
            String[] kv = pair.split("[:=]", 2);
            eventMap.put(kv[0], kv[1]);
        }
        return eventMap;
    }

    public static String[] getPropertyNames() {
        return new String[]{"metricValue", "level", "timestamp"};
    }

    public static Class[] getPropertyClasses() {
        return new Class[]{Double.class, Integer.class, Long.class};
    }

    public String toString() {
        return super.toString();
    }
}