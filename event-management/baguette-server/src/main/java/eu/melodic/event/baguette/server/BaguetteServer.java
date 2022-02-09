/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.server;

import eu.melodic.event.baguette.server.properties.BaguetteServerProperties;
import eu.melodic.event.brokercep.BrokerCepService;
import eu.melodic.event.translate.TranslationContext;
import eu.melodic.event.util.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.slf4j.event.Level;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Baguette Server
 */
@Slf4j
@Service
public class BaguetteServer implements InitializingBean {
    @Autowired
    private BaguetteServerProperties config;
    @Autowired
    private PasswordUtil passwordUtil;
    @Autowired
    private NodeRegistry nodeRegistry;
    @Autowired
    private EventBus<String,Object,Object> eventBus;

    private Sshd server;

    private Map<String, Set<String>> groupingTopicsMap;
    private Map<String, Map<String, Set<String>>> groupingRulesMap;
    private Map<String, Map<String, Set<String>>> topicConnections;
    private Map<String, Double> constants;
    private Set<FunctionDefinition> functionDefinitions;
    private String upperwareGrouping;
    private String upperwareBrokerUrl;
    private BrokerCepService brokerCepService;

    @Override
    public void afterPropertiesSet() {
        // Generate a new, random username/password pair and add it to provided credentials
        generateUsernamePassword();
    }

    private void generateUsernamePassword() {
        String genUsername = "user-"+UUID.randomUUID();
        String genPassword = RandomStringUtils.randomAlphanumeric(32, 64);
        CredentialsMap credentials = config.getCredentials();
        credentials.put(genUsername, genPassword, true);
        log.info("BaguetteServer.afterPropertiesSet(): Generated new Baguette Server username/password: username={}, password={}", genUsername,
                credentials.getPasswordEncoder()!=null ? credentials.getPasswordEncoder().encode(genPassword) : "****");
    }

    // Configuration getter methods
    public Set<String> getGroupingNames() {
        return getGroupingNames(true);
    }

    public Set<String> getGroupingNames(boolean removeUpperware) {
        Set<String> groupings = new HashSet<>();
        groupings.addAll(groupingTopicsMap.keySet());
        groupings.addAll(groupingRulesMap.keySet());
        groupings.addAll(topicConnections.keySet());
        // remove upperware grouping (i.e. GLOBAL)
        if (removeUpperware) groupings.remove(upperwareGrouping);
        return groupings;
    }

    private List<GROUPING> getGroupingsSorted(boolean removeUpperware, boolean ascending) {
        List<GROUPING> list = getGroupingNames(removeUpperware).stream()
                .map(GROUPING::valueOf)
                .sorted()
                .collect(Collectors.toList());
        if (ascending) Collections.reverse(list);
        return list;
    }

    private List<String> getGroupingNamesSorted(boolean removeUpperware, boolean ascending) {
        return getGroupingsSorted(removeUpperware, ascending).stream()
                .map(GROUPING::name)
                .collect(Collectors.toList());
    }

    private String getLowestLevelGroupingName() {
        List<String> list = getGroupingNamesSorted(false, true);
        return list.size()>0 ? list.get(0) : null;
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

    public String getUpperwareGrouping() { return upperwareGrouping; }

    public String getUpperwareBrokerUrl() { return upperwareBrokerUrl; }

    public String getBrokerUsername() { return brokerCepService.getBrokerUsername(); }

    public String getBrokerPassword() { return brokerCepService.getBrokerPassword(); }

    public BrokerCepService getBrokerCepService() { return brokerCepService; }

    public String getServerPubkey() { return server.getPublicKey(); }

    public String getServerPubkeyFingerprint() { return server.getPublicKeyFingerprint(); }

    public NodeRegistry getNodeRegistry() { return nodeRegistry; }

    // Server control methods
    public synchronized void startServer(ServerCoordinator coordinator) throws IOException {
        if (server == null) {
            log.info("BaguetteServer.startServer(): Starting SSH server instance...");
            nodeRegistry.setCoordinator(coordinator);
            Sshd server = new Sshd();
            server.start(config, coordinator, eventBus, nodeRegistry);
            server.setNodeRegistry(getNodeRegistry());
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
            nodeRegistry.setCoordinator(null);
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
            TranslationContext _TC,
            Map<String, Double> constants,
            String upperwareGrouping,
            BrokerCepService brokerCepService)
            throws IOException
    {
        log.info("BaguetteServer.setTopologyConfiguration(): BEGIN");

        // Set new configuration
        this.groupingTopicsMap = _TC.getG2T();
        this.groupingRulesMap = _TC.getG2R();
        this.topicConnections = _TC.getTopicConnections();
        this.constants = constants;
        this.functionDefinitions = _TC.getFunctionDefinitions();
        this.upperwareGrouping = upperwareGrouping;
        this.upperwareBrokerUrl = brokerCepService.getBrokerCepProperties().getBrokerUrlForClients();
        this.brokerCepService = brokerCepService;

        // Print new configuration
        log.info("BaguetteServer.setTopologyConfiguration(): Grouping-to-Topics (G2T): {}", groupingTopicsMap);
        log.info("BaguetteServer.setTopologyConfiguration(): Grouping-to-Rules (G2R): {}", groupingRulesMap);
        log.info("BaguetteServer.setTopologyConfiguration(): Topic-Connections: {}", topicConnections);
        log.info("BaguetteServer.setTopologyConfiguration(): Constants: {}", constants);
        log.info("BaguetteServer.setTopologyConfiguration(): Function-Definitions: {}", functionDefinitions);
        log.info("BaguetteServer.setTopologyConfiguration(): Upperware-grouping: {}", upperwareGrouping);
        log.info("BaguetteServer.setTopologyConfiguration(): Upperware-broker-url: {}", upperwareBrokerUrl);
        log.info("BaguetteServer.setTopologyConfiguration(): Broker-credentials: username={}, password={}",
                brokerCepService.getBrokerUsername(), passwordUtil.encodePassword(brokerCepService.getBrokerPassword()));

        // Stop any running instance of SSH server
        stopServer();

        // Clear node registry
        nodeRegistry.clearNodes();

        log.info("BaguetteServer.setTopologyConfiguration(): Baguette server configuration: {}", config);
        log.info("BaguetteServer.setTopologyConfiguration(): Baguette Server credentials: {}", config.getCredentials());

        // Initialize server coordinator
        log.info("BaguetteServer.setTopologyConfiguration(): Initializing Baguette protocol coordinator...");
        ServerCoordinator coordinator = createServerCoordinator(config, _TC, upperwareGrouping);
        log.info("BaguetteServer.setTopologyConfiguration(): Coordinator: {}", coordinator.getClass().getName());
        coordinator.initialize(
                _TC,
                upperwareGrouping,
                this,
//XXX: TODO: implement a useful EP Network callback, capable to notify EMS when EPN is ready
                () -> {
                    log.info("****************************************");
                    log.info("****  MONITORING TOPOLOGY IS READY  ****");
                    log.info("****************************************");
                }
        );

        // Start an new instance of SSH server
        startServer(coordinator);

        log.info("BaguetteServer.setTopologyConfiguration(): END");
    }

    protected static ServerCoordinator createServerCoordinator(BaguetteServerProperties config, TranslationContext _TC, String upperwareGrouping) {
        // Initialize coordinator class and parameters for backward compatibility
        Class<ServerCoordinator> coordinatorClass = config.getCoordinatorClass();
        Map<String, String> coordinatorParams = config.getCoordinatorParameters();

        // Check if Coordinator Id has been specified (this overrides)
        for (String id : config.getCoordinatorId()) {
            if (StringUtils.isBlank(id))
                throw new IllegalArgumentException("Coordinator Id cannot be null or blank");

            // Get coordinator class and parameters by Id
            BaguetteServerProperties.CoordinatorConfig coordConfig = config.getCoordinatorConfig().get(id);
            if (coordConfig == null)
                throw new IllegalArgumentException("Not found coordinator configuration with id: " + id);
            coordinatorClass = coordConfig.getCoordinatorClass();
            if (coordinatorClass == null)
                throw new IllegalArgumentException("Not found coordinator class in configuration with id: " + id);
            coordinatorParams = coordConfig.getParameters();

            // Initialize coordinator instance
            ServerCoordinator coordinator = createServerCoordinator(id, coordinatorClass, coordinatorParams, _TC, upperwareGrouping);

            if (coordinator != null)
                return coordinator;
            // else try the next coordinator in configuration
        }

        if (coordinatorClass == null)
            throw new IllegalArgumentException("Either coordinator class or configuration id must be specified");

        // Initialize coordinator class and parameters for backward compatibility
        ServerCoordinator coordinator = createServerCoordinator(null, coordinatorClass, coordinatorParams, _TC, upperwareGrouping);
        if (coordinator == null) {
            log.error("No configured coordinator supports Translation Context.\nCoordinator Id's: {}\nDefault coordinator: {}\nTranslation Context:\n{}",
                    config.getCoordinatorId(), coordinatorClass, _TC);
            throw new IllegalArgumentException("No configured coordinator supports Translation Context");
        }
        return coordinator;
    }

    @SneakyThrows
    private static ServerCoordinator createServerCoordinator(String id, Class<ServerCoordinator> coordinatorClass, Map<String,String> coordinatorParams, TranslationContext _TC, String upperwareGrouping) {
        log.debug("createServerCoordinator: Instantiating coordinator with id: {}", id);

        // Initialize coordinator instance
        ServerCoordinator coordinator = coordinatorClass.newInstance();

        // Set coordinator parameters
        coordinator.setProperties(coordinatorParams);

        // Check if coordinator supports this Translation Context
        if (!coordinator.isSupported(_TC)) {
            log.debug("createServerCoordinator: Coordinator does not support Translation Context: id={}", id);
            return null;
        }

        log.debug("createServerCoordinator: Coordinator supports Translation Context: id={}", id);
        return coordinator;
    }

    public void sendToActiveClients(String command) {
        server.sendToActiveClients(command);
    }

    public void sendToClient(String clientId, String command) {
        server.sendToClient(clientId, command);
    }

    public void sendToActiveClusters(String command) {
        server.sendToActiveClusters(command);
    }

    public void sendToCluster(String clusterId, String command) {
        server.sendToCluster(clusterId, command);
    }

    public Object readFromClient(String clientId, String command, Level logLevel) {
        return server.readFromClient(clientId, command, logLevel);
    }

    public List<String> getActiveClients() {
        return ClientShellCommand.getActive().stream()
                .map(c -> {
                    NodeRegistryEntry entry = getNodeRegistryEntryFromClientShellCommand(c);
                    return formatClientList(c, entry);
                })
                .sorted()
                .collect(Collectors.toList());
    }

    public Map<String, Map<String, String>> getActiveClientsMap() {
        return ClientShellCommand.getActive().stream()
                .map(c -> {
                    NodeRegistryEntry entry = getNodeRegistryEntryFromClientShellCommand(c);
                    return prepareClientMap(c, entry);
                })
                .sorted(Comparator.comparing(m -> m.get("id")))
                .collect(Collectors.toMap(m -> m.get("id"), m -> m,
                        (u,v) -> { throw new IllegalStateException(String.format("Duplicate key %s", u)); },
                        LinkedHashMap::new));
    }

    private NodeRegistryEntry getNodeRegistryEntryFromClientShellCommand(ClientShellCommand c) {
        NodeRegistryEntry entry = c.getNodeRegistryEntry();
        if (entry==null)
            entry = getNodeRegistry().getNodeByAddress(c.getClientIpAddress());
        log.debug("getNodeRegistryEntryFromClientShellCommand: CSC ip-address: {}", c.getClientIpAddress());
        log.debug("getNodeRegistryEntryFromClientShellCommand: CSC NR entry: {}", entry!=null ? entry.getPreregistration() : null);
        /*if (entry==null) {
            log.warn("getNodeRegistryEntryFromClientShellCommand: WARN: ** NOT SECURE ** CSC client-id: {}", c.getClientId());
            entry = getNodeRegistry().getNodeByClientId(c.getClientId());
            log.debug("getNodeRegistryEntryFromClientShellCommand: WARN: ** NOT SECURE ** CSC NR entry: {}", entry!=null ? entry.getPreregistration() : null);
        }*/
        return entry;
    }

    public List<String> getNodesWithoutClient() {
        return createClientList(new HashSet<>(Collections.singletonList(NodeRegistryEntry.STATE.NOT_INSTALLED)));
    }

    public Map<String, Map<String, String>> getNodesWithoutClientMap() {
        return createClientMap(new HashSet<>(Collections.singletonList(NodeRegistryEntry.STATE.NOT_INSTALLED)));
    }

    public List<String> getIgnoredNodes() {
        return createClientList(new HashSet<>(Collections.singletonList(NodeRegistryEntry.STATE.IGNORE_NODE)));
    }

    public Map<String, Map<String, String>> getIgnoredNodesMap() {
        return createClientMap(new HashSet<>(Collections.singletonList(NodeRegistryEntry.STATE.IGNORE_NODE)));
    }

    public List<String> getPassiveNodes() {
        return createClientList(new HashSet<>(Arrays.asList(NodeRegistryEntry.STATE.NOT_INSTALLED, NodeRegistryEntry.STATE.IGNORE_NODE)));
    }

    public Map<String, Map<String, String>> getPassiveNodesMap() {
        return createClientMap(new HashSet<>(Arrays.asList(NodeRegistryEntry.STATE.NOT_INSTALLED, NodeRegistryEntry.STATE.IGNORE_NODE)));
    }

    private List<String> createClientList(Set<NodeRegistryEntry.STATE> states) {
        return nodeRegistry.getNodes().stream()
                .filter(entry->states.contains(entry.getState()))
                .map(entry -> {
                    log.debug("createClientList: Node ip-address: {}", entry.getIpAddress());
                    log.debug("createClientList: Node preregistration info: {}", entry.getPreregistration());
                    ClientShellCommand c = getClientShellCommandFromNodeRegistryEntry(entry);
                    return formatClientList(c, entry);
                })
                .sorted()
                .collect(Collectors.toList());
    }

    private Map<String, Map<String, String>> createClientMap(Set<NodeRegistryEntry.STATE> states) {
        return nodeRegistry.getNodes().stream()
                .filter(entry -> states.contains(entry.getState()))
                .sorted(Comparator.comparing(NodeRegistryEntry::getClientId))
                .collect(Collectors.toMap(NodeRegistryEntry::getClientId, entry -> {
                    log.debug("createClientMap: Node ip-address: {}", entry.getIpAddress());
                    log.debug("createClientMap: Node preregistration info: {}", entry.getPreregistration());
                    ClientShellCommand c = getClientShellCommandFromNodeRegistryEntry(entry);
                    return prepareClientMap(c, entry);
                }, (u,v) -> { throw new IllegalStateException(String.format("Duplicate key %s", u)); }, LinkedHashMap::new));
    }

    private ClientShellCommand getClientShellCommandFromNodeRegistryEntry(NodeRegistryEntry entry) {
        return StringUtils.isNotBlank(entry.getIpAddress())
                ? ClientShellCommand.getActiveByIpAddress(entry.getIpAddress()) : null;
    }

    private String formatClientList(ClientShellCommand c, NodeRegistryEntry entry) {
        final StringBuilder sb = new StringBuilder();
        prepareClientMap(c, entry).forEach((k,v)->{
            if ("id".equals(k)) sb.append(v);
            else if ("node-port".equals(k)) sb.append(":").append(v);
            else sb.append(" ").append(v);
        });
        return sb.toString();
    }

    private Map<String, String> prepareClientMap(ClientShellCommand c, NodeRegistryEntry entry) {
        String address = entry!=null ? entry.getIpAddress() : c.getClientIpAddress();
        String hostname = entry!=null ? entry.getHostname() : null;
        if (StringUtils.isBlank(hostname)) {
            if (c!=null)
                hostname = c.getClientClusterNodeHostname();
            if (StringUtils.isBlank(hostname)) {
                try {
                    hostname = InetAddress.getByName(address).getHostName();
                } catch (Exception e) {
                    log.warn("Failed to resolve client hostname from IP address: {}\n", address, e);
                }
            }
            if (StringUtils.isNotBlank(hostname)) {
                if (c!=null) c.setClientClusterNodeHostname(hostname);
                if (entry!=null) entry.setHostname(hostname);
            }
        }
        Map<String,String> properties = new LinkedHashMap<>();
        properties.put("id", c!=null ? c.getId() : entry.getClientId());
        properties.put("ip-address", address);
        properties.put("node-hostname", c!=null ? c.getClientClusterNodeHostname() : hostname);
        properties.put("node-port", Integer.toString(c!=null ? c.getClientClusterNodePort() : -1));
        properties.put("node-status", c!=null ? c.getClientNodeStatus() : null);
        properties.put("node-zone", (entry!=null && entry.getClusterZone()!=null) ? entry.getClusterZone().getId() : null);  //c.getClientZone()!=null ? c.getClientZone().getId() : null
        properties.put("grouping", c!=null ? c.getClientGrouping() : (entry.getState()==NodeRegistryEntry.STATE.NOT_INSTALLED ? getLowestLevelGroupingName() : null));
        properties.put("reference", entry!=null ? entry.getReference() : null);
        properties.put("node-id", c!=null ? c.getClientProperty("node-id") : null);
        properties.put("node-state", entry!=null && entry.getState()!=null ? entry.getState().toString() : null);
        return properties;
    }

    public void sendConstants(Map<String, Double> constants) {
        server.sendConstants(constants);
    }

    public NodeRegistryEntry registerClient(Map<String,Object> nodeInfoMap) throws UnknownHostException {
        log.debug("BaguetteServer.registerClient(): node-info={}", nodeInfoMap);

        Map<String,Object> nodeInfo = new HashMap<>(nodeInfoMap);

        // Create client id
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

        // Add node info into node registry
        return nodeRegistry.addNode(nodeInfo, clientId);
    }
}
