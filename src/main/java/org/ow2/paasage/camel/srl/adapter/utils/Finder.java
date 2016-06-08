/*
 * Copyright (C) 2015 University of Ulm.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package org.ow2.paasage.camel.srl.adapter.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Frank on 21.05.2015.
 */
public class Finder {
    public static List<Long> getScalingActionsByEventId(Map<Long, String> map, String eventId) {
        List<Long> result = new ArrayList<>();

        for (Map.Entry<Long, String> entrySet : map.entrySet()) {
            if (entrySet.getValue().equals(eventId)) {
                result.add(entrySet.getKey());
            }
        }

        return result;
    }
}
