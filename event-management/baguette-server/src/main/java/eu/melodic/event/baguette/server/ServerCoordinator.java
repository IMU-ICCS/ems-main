/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.server;

import eu.melodic.event.translate.TranslationContext;
import eu.melodic.event.util.GroupingConfiguration;

import java.util.Map;

import static eu.melodic.event.util.GroupingConfiguration.BrokerConnectionConfig;

public interface ServerCoordinator {
    default boolean isSupported(TranslationContext tc) { return true; }

    void initialize(TranslationContext tc, String upperwareGrouping, BaguetteServer server, Runnable callback);

    default void setProperties(Map<String, String> p) { }

    default boolean processClientInput(ClientShellCommand csc, String line) { return false; }

    BaguetteServer getServer();

    int getPhase();

    default boolean allowAlreadyPreregisteredNode(Map<String,Object> nodeInfo) { return true; }

    default boolean allowAlreadyRegisteredNode(ClientShellCommand csc) { return true; }

    default boolean allowNotPreregisteredNode(ClientShellCommand csc) { return true; }

    default void preregister(NodeRegistryEntry entry) { }

    void register(ClientShellCommand c);

    void unregister(ClientShellCommand c);

    void clientReady(ClientShellCommand c);

    void start();

    void stop();

    default void sendGroupingConfigurations(Map<String,BrokerConnectionConfig> connectionConfigs, ClientShellCommand c, BaguetteServer server) {
        for (String grouping : server.getGroupingNames()) {
            GroupingConfiguration gc = GroupingConfigurationHelper.newGroupingConfiguration(grouping, connectionConfigs, server);
            c.sendGroupingConfiguration(gc);
        }
    }

    default BrokerConnectionConfig getGroupingBrokerConfig(String grouping, ClientShellCommand c) {
        String brokerUrl = c.getClientBrokerUrl();
        String brokerCert = c.getClientCertificate();
        String username = c.getClientBrokerUsername();
        String password = c.getClientBrokerPassword();
        return new BrokerConnectionConfig(grouping, brokerUrl, brokerCert, username, password);
    }
    default BrokerConnectionConfig getUpperwareBrokerConfig(BaguetteServer server) {
        String brokerUrl = server.getUpperwareBrokerUrl();
        String brokerCert = server.getBrokerCepService().getBrokerCertificate();
        String username = server.getBrokerUsername();
        String password = server.getBrokerPassword();
        return new BrokerConnectionConfig(server.getUpperwareGrouping(), brokerUrl, brokerCert, username, password);
    }
}
