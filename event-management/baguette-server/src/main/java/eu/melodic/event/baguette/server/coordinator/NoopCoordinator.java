/*
 * Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
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
        if (_logInvocation("initialize", null, false)) return;
        this.server = server;
        this.config = server.getConfiguration();
        this.callback = callback;
    }

    @Override
    public BaguetteServer getServer() {
        return server;
    }

    @Override
    public void start() {
        if (_logInvocation("start", null, false)) return;
        started = true;

        if (callback != null) {
            log.info("{}: start(): Invoking callback", getClass().getSimpleName());
            callback.run();
        }
    }

    @Override
    public void stop() {
        if (!_logInvocation("stop", null, true)) return;
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
        _logInvocation("register", c, true);
    }

    @Override
    public synchronized void unregister(ClientShellCommand c) {
        _logInvocation("unregister", c, true);
    }

    @Override
    public synchronized void clientReady(ClientShellCommand c) {
        _logInvocation("clientReady", c, true);
    }

    protected boolean _logInvocation(String methodName, ClientShellCommand c, boolean checkStarted) {
        String className = getClass().getSimpleName();
        String cscStr =  (c!=null) ? String.format(". CSC: %s", c.toString()) : "";
        if (checkStarted && !started) {
            log.warn("{}: {}(): Coordinator has not been started{}", className, methodName, cscStr);
        } else
        if (!checkStarted && started) {
            log.warn("{}: {}(): Coordinator is already running{}", className, methodName, cscStr);
        } else {
            log.info("{}: {}(): Method invoked{}", className, methodName, cscStr);
        }
        return started;
    }
}
