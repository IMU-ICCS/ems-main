/*
 * Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.server;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public interface ServerCoordinator {
    void initialize(BaguetteServer server, Runnable callback);

    default void setProperties(Map<String, String> p) { }

    BaguetteServer getServer();

    int getPhase();

    void register(ClientShellCommand c);

    void unregister(ClientShellCommand c);

    void clientReady(ClientShellCommand c);

    void start();

    void stop();

    default void sendGroupingConfigurations(Properties cfg, ClientShellCommand c, BaguetteServer server) {
        for (String grouping : server.getGroupingNames()) {
            GroupingConfiguration gc = new GroupingConfiguration(grouping, cfg, server);
            c.sendGroupingConfiguration(grouping, gc);
        }
    }

    default Map<String,String> getGroupingBrokerConfig(String grouping, ClientShellCommand c) {
        String brokerUrl = c.getClientBrokerUrl();
        String brokerCert = c.getClientCertificate();
        Map<String,String> result = new HashMap<>();
        result.put(grouping, (brokerUrl+"\n"+brokerCert).trim());
        return result;
    }
    default Map<String,String> getUpperwareBrokerConfig(BaguetteServer server) {
        String grouping = server.getUpperwareGrouping();
        String brokerUrl = server.getUpperwareBrokerUrl();
        String brokerCert = server.getBrokerCepService().getBrokerCertificate();
        Map<String,String> result = new HashMap<>();
        result.put(grouping, (brokerUrl+"\n"+brokerCert).trim());
        return result;
    }
}
