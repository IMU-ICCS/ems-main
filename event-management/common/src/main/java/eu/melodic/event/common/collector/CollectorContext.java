/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.common.collector;

import eu.melodic.event.brokercep.event.EventMap;
import eu.melodic.event.util.ClientConfiguration;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public interface CollectorContext {
    List<ClientConfiguration> getNodeConfigurations();
    Set<Serializable> getNodesWithoutClient();
    boolean isAggregator();
    boolean sendEvent(String connectionString, String destinationName, EventMap event, boolean createDestination);
}
