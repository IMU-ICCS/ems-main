/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.upperware.metasolver.metricvalue;

import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;

@Getter
@ToString(doNotUseGetters = true)
public class MetricValueEvent extends HashMap<String,Object> {
    //private String metricValue;
    //private String cloudName;
    //private List<String> componentName;
    //private int level;
    //private long timestamp;

    public String getMetricValue() { return getAsString("metricValue"); }

    public String getAsString(String key) {
        Object o = get(key);
        return o!=null ? o.toString() : null;
    }
}
