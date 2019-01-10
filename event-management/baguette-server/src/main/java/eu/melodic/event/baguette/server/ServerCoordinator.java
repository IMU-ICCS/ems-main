/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.baguette.server;

import java.util.Properties;

public interface ServerCoordinator {
    void initialize(BaguetteServer server, Runnable callback);

    int getPhase();

    void register(ClientShellCommand c);

    void unregister(ClientShellCommand c);

    void brokerReady(ClientShellCommand c);

    void clientReady(ClientShellCommand c);

    void start();

    void stop();

    default public void sendGroupingConfigurations(Properties cfg, ClientShellCommand c, BaguetteServer server) {
        for (String grouping : server.getGroupingNames()) {
            GroupingConfiguration gc = new GroupingConfiguration(grouping, cfg, server);
            c.sendGroupingConfiguration(grouping, gc);
        }
    }
}
