/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.baguette.server;

import eu.melodic.event.baguette.server.properties.BaguetteServerProperties;
import eu.melodic.event.brokercep.cep.FunctionDefinition;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * Baguette Server
 */
@Slf4j
@Service
public class BaguetteServer {
    @Autowired
    private BaguetteServerProperties config;

    private Sshd server;

    private Map<String, Set<String>> groupingTopicsMap;
    private Map<String, Map<String, Set<String>>> groupingRulesMap;
    private Map<String, Map<String, Set<String>>> topicConnections;
    private Map<String, Double> constants;
    private Set<FunctionDefinition> functionDefinitions;
    private String upperwareGrouping;
    private String upperwareBrokerUrl;
    private String brokerUsername;
    private String brokerPassword;

    // Configuration getter methods
    public Set<String> getGroupingNames() {
        Set<String> groupings = new HashSet<>();
        groupings.addAll(groupingTopicsMap.keySet());
        groupings.addAll(groupingRulesMap.keySet());
        groupings.addAll(topicConnections.keySet());
        // remove upperware grouping (i.e. GLOBAL)
        groupings.remove(upperwareGrouping);
        return groupings;
    }

    public BaguetteServerProperties getConfiguration() {
        return config;
    }

    public Set<String> getTopicsForGrouping(String grouping) {
        return groupingTopicsMap.get(grouping);
    }

    public Map<String, Set<String>> getRulesForGrouping(String grouping) {
        return groupingRulesMap.get(grouping);
    }

    public Map<String, Set<String>> getTopicConnectionsForGrouping(String grouping) {
        return topicConnections.get(grouping);
    }

    public Map<String, Double> getConstants() {
        return constants;
    }

    public Set<FunctionDefinition> getFunctionDefinitions() {
        return functionDefinitions;
    }

    public String getUpperwareBrokerUrl() {
        return upperwareBrokerUrl;
    }

    public String getBrokerUsername() {
        return brokerUsername;
    }

    public String getBrokerPassword() {
        return brokerPassword;
    }

    public String getServerPubkey() { return server.getPublicKey(); }

    public String getServerPubkeyFingerprint() { return server.getPublicKeyFingerprint(); }

    // Server control methods
    public synchronized void startServer(ServerCoordinator coordinator) throws IOException {
        if (server == null) {
            log.info("BaguetteServer.startServer(): Starting SSH server instance...");
            Sshd server = new Sshd();
            server.start(config, coordinator);
            this.server = server;
            log.info("BaguetteServer.startServer(): Starting SSH server instance... done");
        } else {
            log.warn("BaguetteServer.startServer(): An SSH server instance is already running");
        }
    }

    public synchronized void stopServer() throws IOException {
        if (server != null) {
            log.info("BaguetteServer.setServerConfiguration(): stopping running instance of SSH server...");
            server.stop();
            this.server = null;
            log.info("BaguetteServer.setServerConfiguration(): stopping running instance of SSH server... done");
        } else {
            log.warn("BaguetteServer.stop(): No SSH server instance is running");
        }
    }

    public synchronized void restartServer(ServerCoordinator coordinator) throws IOException {
        stopServer();
        startServer(coordinator);
    }

    public synchronized boolean isServerRunning() {
        return server != null;
    }

    // Topology configuration methods
    public synchronized void setTopologyConfiguration(
            Map<String, Set<String>> G2T,
            Map<String, Map<String, Set<String>>> G2R,
            Map<String, Map<String, Set<String>>> topicConnections,
            Map<String, Double> constants,
            Set<FunctionDefinition> functionDefinitions,
            String upperwareGrouping,
            String upperwareBrokerUrl,
            String brokerUsername,
            String brokerPassword)
            throws IOException {
        log.info("BaguetteServer.setTopologyConfiguration(): BEGIN");
        log.info("BaguetteServer.setTopologyConfiguration(): ARGS: Grouping-to-Topics (G2T): {}", G2T);
        log.info("BaguetteServer.setTopologyConfiguration(): ARGS: Grouping-to-Rules (G2R): {}", G2R);
        log.info("BaguetteServer.setTopologyConfiguration(): ARGS: Topic-Connections: {}", topicConnections);
        log.info("BaguetteServer.setTopologyConfiguration(): ARGS: Constants: {}", constants);
        log.info("BaguetteServer.setTopologyConfiguration(): ARGS: Function-Definitions: {}", functionDefinitions);
        log.info("BaguetteServer.setTopologyConfiguration(): ARGS: Upperware-grouping: {}", upperwareGrouping);
        log.info("BaguetteServer.setTopologyConfiguration(): ARGS: Upperware-broker-url: {}", upperwareBrokerUrl);
        log.info("BaguetteServer.setTopologyConfiguration(): ARGS: Broker-credentials: username={}, password={}",
                brokerUsername, config.getPasswordEncoder().encode(brokerPassword));

        // Stop any running instance of SSH server
        stopServer();

        // Set new configuration
        this.groupingTopicsMap = G2T;
        this.groupingRulesMap = G2R;
        this.topicConnections = topicConnections;
        this.constants = constants;
        this.functionDefinitions = functionDefinitions;
        this.upperwareGrouping = upperwareGrouping;
        this.upperwareBrokerUrl = upperwareBrokerUrl;
        this.brokerUsername = brokerUsername;
        this.brokerPassword = brokerPassword;

        log.info("BaguetteServer.setTopologyConfiguration(): Baguette server configuration: {}", config);
        log.info("BaguetteServer.setTopologyConfiguration(): Baguette Server credentials: {}", config.getCredentials());

        // Initialize server coordinator
        log.info("BaguetteServer.setTopologyConfiguration(): Initializing Baguette protocol coordinator...");
        ServerCoordinator coordinator = createServerCoordinator(config.getCoordinatorClass());
        log.info("BaguetteServer.setTopologyConfiguration(): Coordinator: {}", coordinator.getClass().getName());
        coordinator.initialize(
                this,
//XXX: TODO: implement a useful EP Network callback, capable to notify EMS when EPN is ready
                new Runnable() {
                    public void run() {
                        log.info("****************************************");
                        log.info("****  MONITORING TOPOLOGY IS READY  ****");
                        log.info("****************************************");
                    }
                }
        );

        // Start an new instance of SSH server
        startServer(coordinator);

        log.info("BaguetteServer.setTopologyConfiguration(): END");
    }

    protected static ServerCoordinator createServerCoordinator(String classStr) {
        try {
            return (ServerCoordinator) Class.forName(classStr).newInstance();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void sendToActiveClients(String command) {
        server.sendToActiveClients(command);
    }

    public void sendToClient(String clientId, String command) {
        server.sendToClient(clientId, command);
    }

    public void sendConstants(Map<String, Double> constants) {
        server.sendConstants(constants);
    }

    //XXX: TODO: do actual node registration with Server coordinator. More information might be needed or returned.
    public String registerClient(Map<String,Object> nodeInfoMap) {
        log.debug("BaguetteServer.registerClient(): node-info={}", nodeInfoMap);

        HashMap<String,Object> nodeInfo = new HashMap<>();
        nodeInfo.putAll(nodeInfoMap);

        String formatter = getConfiguration().getClientIdFormat();
        String clientId = null;
        if (StringUtils.isBlank(formatter)) {
            log.debug("BaguetteServer.registerClient(): No formatter specified. A random uuid will be returned");
            clientId = UUID.randomUUID().toString();
        } else {
            String escape = Optional.ofNullable(getConfiguration().getClientIdFormatEscape()).orElse("~");
            formatter = formatter.replace(escape,"$");
            log.debug("BaguetteServer.registerClient(): formatter={}", formatter);
            nodeInfo.put("random", UUID.randomUUID().toString());
            clientId = StringSubstitutor.replace(formatter, nodeInfo);
        }

        log.debug("BaguetteServer.registerClient(): client-id={}", clientId);
        return clientId;
    }
}
