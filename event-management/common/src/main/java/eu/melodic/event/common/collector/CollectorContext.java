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
import eu.melodic.event.common.client.SshClient;
import eu.melodic.event.common.client.SshClientProperties;
import eu.melodic.event.util.ClientConfiguration;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public interface CollectorContext<P extends SshClientProperties> {
    enum PUBLISH_RESULT { SENT, SKIPPED, ERROR }

    List<ClientConfiguration> getNodeConfigurations();
    Set<Serializable> getNodesWithoutClient();
    boolean isAggregator();
    PUBLISH_RESULT sendEvent(String connectionString, String destinationName, EventMap event, boolean createDestination);
    default SshClient<P> getSshClient() { return null; }
    default P getSshClientProperties() { return null; }
}
