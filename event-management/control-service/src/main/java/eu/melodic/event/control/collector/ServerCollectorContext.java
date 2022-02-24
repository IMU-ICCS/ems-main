/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.control.collector;

import eu.melodic.event.baguette.server.NodeRegistry;
import eu.melodic.event.baguette.server.NodeRegistryEntry;
import eu.melodic.event.brokercep.BrokerCepService;
import eu.melodic.event.brokercep.event.EventMap;
import eu.melodic.event.common.collector.CollectorContext;
import eu.melodic.event.util.ClientConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ServerCollectorContext implements CollectorContext {
    private final NodeRegistry nodeRegistry;
    private final BrokerCepService brokerCepService;

    @Override
    public List<ClientConfiguration> getNodeConfigurations() {
        return null;
    }

    @Override
    public Set<Serializable> getNodesWithoutClient() {
        return nodeRegistry.getCoordinator().supportsAggregators()
                ? Collections.emptySet()
                : nodeRegistry.getNodes().stream()
                    .filter(entry -> entry.getState()== NodeRegistryEntry.STATE.NOT_INSTALLED)
                    .map(NodeRegistryEntry::getIpAddress)
                    .collect(Collectors.toCollection(HashSet::new));
    }

    @Override
    public boolean isAggregator() {
        return true;
    }

    @Override
    @SneakyThrows
    public boolean sendEvent(String connectionString, String destinationName, EventMap event, boolean createDestination) {
        assert(connectionString==null);
        if (createDestination || brokerCepService.destinationExists(destinationName)) {
            brokerCepService.publishEvent(null, destinationName, event);
            return true;
        }
        return false;
    }
}
