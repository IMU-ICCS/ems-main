/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.client.plugin;

import eu.melodic.event.baguette.client.CommandExecutor;
import eu.melodic.event.baguette.client.Plugin;
import eu.melodic.event.baguette.client.collector.netdata.NetdataCollectorProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * Client-side Self-Healing plugin
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SelfHealingPlugin implements Plugin, InitializingBean {
    private final NetdataCollectorProperties properties;
    private final CommandExecutor commandExecutor;

    private boolean started;

    @Override
    public void afterPropertiesSet() {
        log.debug("SelfHealingPlugin: properties: {}", properties);

    }

    public synchronized void start() {
        // check if already running
        if (started) {
            log.warn("SelfHealingPlugin: Already started");
            return;
        }

        log.info("SelfHealingPlugin: Started");
    }

    public synchronized void stop() {
        if (!started) {
            log.warn("SelfHealingPlugin: Not started");
            return;
        }

        log.info("SelfHealingPlugin: Stopped");
    }
}
