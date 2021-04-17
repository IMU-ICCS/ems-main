/*
 * Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.util;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Baguette Client Configuration
 */
@Data
@ToString(exclude = {"brokerPassword"})
@Builder
public class GroupingConfiguration implements Serializable {
    @NonNull private final String name;
    @NonNull private final Properties config;
    @NonNull private final Set<String> eventTypeNames;
    @NonNull private final Map<String, Set<String>> rules;
    @NonNull private final Map<String, Set<String>> connections;
    @NonNull private final Set<FunctionDefinition> functionDefinitions;
    @NonNull private Map<String, Double> constants;
    @NonNull private String brokerUsername;
    @NonNull private String brokerPassword;
}