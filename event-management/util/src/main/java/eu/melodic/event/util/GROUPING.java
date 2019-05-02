/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public enum GROUPING {
    GLOBAL,
    PER_CLOUD,
    PER_REGION,
    PER_ZONE,
    PER_HOST,
    PER_INSTANCE;

    public static List<String> getNames() {
        return Arrays.asList(values()).stream().map(v -> v.name()).collect(Collectors.toList());
    }
}
