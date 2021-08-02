/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
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
    public final static String[] PROPERTY_NAMES_ARRAY = new String[]{"metricValue", "level", "timestamp"};
    public final static Class[] PROPERTY_CLASSES_ARRAY = new Class[]{Double.class, Integer.class, Long.class};

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

    @Override
    public Object put(String key, Object value) {
        log.info("Event.put: BEGIN: key={}, value={}, value-class={}", key, value, value.getClass().getName());
        if ("metricValue".equals(key) && !(value instanceof Double)) {
            log.info("Event.put: Key={}, Value-class IS NOT DOUBLE: {}", key, value.getClass().getName());
            Double newVal;
            if (value instanceof Number) {
                log.info("Event.put: Key={}, Value-class IS NUMBER: {}", key, value.getClass().getName());
                newVal = ((Number) value).doubleValue();
            } else {
                log.info("Event.put: Key={}, Value-class IS NOT NUMBER: {}", key, value.getClass().getName());
                String s = value.toString();
                log.info("Event.put: Key={}, Value-STR: {}", key, s);
                if (s.charAt(0)=='"' && s.charAt(s.length()-1)=='"'
                        || s.charAt(0)=='\'' && s.charAt(s.length()-1)=='\'')
                    s = s.substring(1, s.length()-1);
                log.info("Event.put: Key={}, Value-STR-without-quotes: {}", key, s);
                newVal = Double.parseDouble(s);
            }
            log.info("Event.put: Key={}, New-Value={}", key, newVal);
            return super.put(key, newVal);
        }
        log.info("Event.put: Key={}, Value-AS-IS={}", key, value);
        return super.put(key, value);
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
        return PROPERTY_NAMES_ARRAY;
    }

    public static Class[] getPropertyClasses() {
        return PROPERTY_CLASSES_ARRAY;
    }

    public String toString() {
        return super.toString();
    }
}