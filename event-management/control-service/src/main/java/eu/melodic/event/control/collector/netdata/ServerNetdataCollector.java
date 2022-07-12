/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.control.collector.netdata;

import eu.melodic.event.common.collector.CollectorContext;
import eu.melodic.event.common.collector.netdata.NetdataCollectorProperties;
import eu.melodic.event.control.collector.Collector;
import eu.melodic.event.control.collector.ServerCollectorContext;
import eu.melodic.event.util.EventBus;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

/**
 * Collects measurements from Netdata http server
 */
@Slf4j
@Component
public class ServerNetdataCollector extends eu.melodic.event.common.collector.netdata.NetdataCollector implements Collector {
    public ServerNetdataCollector(@NonNull NetdataCollectorProperties properties,
                                  @NonNull CollectorContext collectorContext,
                                  @NonNull TaskScheduler taskScheduler,
                                  @NonNull EventBus<String, Object, Object> eventBus)
    {
        super("ServerNetdataCollector", properties, collectorContext, taskScheduler, eventBus);
        if (!(collectorContext instanceof ServerCollectorContext))
            throw new IllegalArgumentException("Invalid CollectorContext provided. Expected: ServerCollectorContext, but got "+collectorContext.getClass().getName());
    }
}
