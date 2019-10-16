/*
 * Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.brokerclient.event;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class EventMap extends HashMap<String, Object> implements Serializable {
    public EventMap() {
        super();
    }

    public EventMap(Map<String, Object> map) {
        super(map);
    }

    public EventMap(double metricValue, int level, long timestamp) {
        put("metricValue", metricValue);
        put("level", level);
        put("timestamp", timestamp);
    }

    public static String[] getPropertyNames() {
        return new String[]{"metricValue", "level", "timestamp"};
    }

    public static Class[] getPropertyClasses() {
        return new Class[]{Double.class, Integer.class, Long.class};
    }
}