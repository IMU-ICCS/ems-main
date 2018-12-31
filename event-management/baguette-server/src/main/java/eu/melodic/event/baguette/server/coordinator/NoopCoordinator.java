/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.baguette.server.coordinator;

import eu.melodic.event.baguette.server.BaguetteServer;
import eu.melodic.event.baguette.server.ClientShellCommand;
import eu.melodic.event.baguette.server.ServerCoordinator;
import eu.melodic.event.baguette.server.properties.BaguetteServerProperties;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NoopCoordinator implements ServerCoordinator {
    protected BaguetteServer server;
    protected BaguetteServerProperties config;
    protected Runnable callback;
    protected boolean started;

    @Override
    public void initialize(BaguetteServer server, Runnable callback) {
        if (started) return;
        log.info("NoopCoordinator: initialize");
        this.server = server;
        this.config = server.getConfiguration();
        this.callback = callback;
    }

    @Override
    public void start() {
        if (started) return;
        log.info("NoopCoordinator: start");
        started = true;

        if (callback != null) {
            log.info("NoopCoordinator: start: Invoking callback");
            callback.run();
        }
    }

    @Override
    public void stop() {
        if (!started) return;
        log.info("NoopCoordinator: stop");
        started = false;
    }

    public boolean isStarted() {
        return started;
    }

    @Override
    public int getPhase() {
        return -1;
    }

    @Override
    public synchronized void register(ClientShellCommand c) {
        if (!started) return;
        log.info("NoopCoordinator: register: {}", c);
    }

    @Override
    public synchronized void unregister(ClientShellCommand c) {
        if (!started) return;
        log.info("NoopCoordinator: unregister: {}", c);
    }

    @Override
    public synchronized void brokerReady(ClientShellCommand c) {
        if (!started) return;
        log.info("NoopCoordinator: brokerReady: {}", c);
    }

    @Override
    public synchronized void clientReady(ClientShellCommand c) {
        if (!started) return;
        log.info("NoopCoordinator: clientReady: {}", c);
    }
}
