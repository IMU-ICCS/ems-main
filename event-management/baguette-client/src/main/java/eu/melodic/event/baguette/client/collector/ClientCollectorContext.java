/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.client.collector;

import eu.melodic.event.baguette.client.CommandExecutor;
import eu.melodic.event.brokercep.event.EventMap;
import eu.melodic.event.common.collector.CollectorContext;
import eu.melodic.event.util.ClientConfiguration;
import eu.melodic.event.util.GroupingConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClientCollectorContext implements CollectorContext {
    private final CommandExecutor commandExecutor;

    public Map<String, GroupingConfiguration> getGroupings() {
        return commandExecutor.getGroupings();
    }

    @Override
    public List<ClientConfiguration> getNodeConfigurations() {
        return Collections.singletonList(commandExecutor.getClientConfiguration());
    }

    @Override
    public Set<Serializable> getNodesWithoutClient() {
        return commandExecutor.getClientConfiguration()!=null
                ? commandExecutor.getClientConfiguration().getNodesWithoutClient() : null;
    }

    @Override
    public boolean isAggregator() {
        return commandExecutor.isAggregator();
    }

    @Override
    public boolean sendEvent(String connectionString, String destinationName, EventMap event, boolean createDestination) {
        return commandExecutor.sendEvent(connectionString, destinationName, event, createDestination);
    }
}
