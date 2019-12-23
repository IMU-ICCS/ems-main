/*
 * Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.server;

import eu.melodic.event.brokercep.cep.FunctionDefinition;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Baguette Client Configuration
 */
@Getter
@Setter
@ToString(exclude = {"brokerPassword"})
public class GroupingConfiguration {
    private final String grouping;
    private final Properties config;
    private final Set<String> eventTypes;
    private final Map<String, Set<String>> rules;
    private final Map<String, Set<String>> connections;
    private final Map<String, Double> constants;
    private final Set<FunctionDefinition> functionDefs;
    private final String brokerUsername;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final String brokerPassword;

    public GroupingConfiguration(String grouping, Properties config, BaguetteServer server) {
        this.grouping = grouping;
        this.config = config;
        this.eventTypes = server.getTopicsForGrouping(grouping);
        this.rules = server.getRulesForGrouping(grouping);
        this.connections = server.getTopicConnectionsForGrouping(grouping);
        this.constants = server.getConstants();
        this.functionDefs = server.getFunctionDefinitions();
        this.brokerUsername = server.getBrokerUsername();
        this.brokerPassword = server.getBrokerPassword();
    }

    public Map<String, Object> getConfigurationMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("grouping", grouping);
        map.put("config", config);
        map.put("eventTypes", eventTypes);
        map.put("rules", rules);
        map.put("connections", connections);
        map.put("constants", constants);
        map.put("function-definitions", functionDefs);
        map.put("common-broker-username", brokerUsername);
        map.put("common-broker-password", brokerPassword);
        return map;
    }
}
