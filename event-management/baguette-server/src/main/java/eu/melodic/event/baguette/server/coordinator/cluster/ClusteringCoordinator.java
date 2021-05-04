/*
 * Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.server.coordinator.cluster;

import eu.melodic.event.baguette.server.BaguetteServer;
import eu.melodic.event.baguette.server.ClientShellCommand;
import eu.melodic.event.baguette.server.NodeRegistryEntry;
import eu.melodic.event.baguette.server.coordinator.NoopCoordinator;
import eu.melodic.event.translate.TranslationContext;
import eu.melodic.event.util.GROUPING;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

import static eu.melodic.event.util.GroupingConfiguration.BrokerConnectionConfig;

@Slf4j
public class ClusteringCoordinator extends NoopCoordinator {
    private final static String DEFAULT_ZONE = "default_zone";

    private final Map<String, ClusterZone> topologyMap = new HashMap<>();

    private IZoneManagementStrategy zoneManagementStrategy;
    private int zoneStartPort = 1200;
    private int zoneEndPort = 65535;

    private GROUPING topLevelGrouping;
    private GROUPING aggregatorGrouping;
    private GROUPING lastLevelGrouping;

    @Override
    public boolean isSupported(final TranslationContext _TC) {
        // Check if it is a 3-level architecture
        Set<String> groupings = _TC.getG2R().keySet();
        if (!groupings.contains("GLOBAL")) return false;
        return groupings.size()==3;
    }

    @Override
    public void initialize(final TranslationContext TC, String upperwareGrouping, BaguetteServer server, Runnable callback) {
        if (!isSupported(TC))
            throw new IllegalArgumentException("Passed Translation Context is not supported");

        super.initialize(TC, upperwareGrouping, server, callback);
        List<GROUPING> groupings = TC.getG2R().keySet().stream()
                .map(GROUPING::valueOf)
                .sorted()
                .collect(Collectors.toList());
        log.debug("ClusteringCoordinator.initialize(): Groupings: {}", groupings);
        this.topLevelGrouping = groupings.get(0);
        this.aggregatorGrouping = groupings.get(1);
        this.lastLevelGrouping = groupings.get(2);
        log.info("ClusteringCoordinator.initialize(): Groupings: top-level={}, aggregator={}, last-level={}",
                topLevelGrouping, aggregatorGrouping, lastLevelGrouping);
    }

    @SneakyThrows
    public void setProperties(Map<String, String> zoneConfig) {
        log.debug("Zone configuration: {}", zoneConfig);
        zoneManagementStrategy = zoneConfig.containsKey("zone-management-strategy-class")
                ? (IZoneManagementStrategy) Class.forName(zoneConfig.get("zone-management-strategy-class")).newInstance()
                : new DefaultZoneManagementStrategy();
        zoneStartPort = zoneConfig.containsKey("zone-port-start")
                ? Integer.parseInt(zoneConfig.get("zone-port-start")) : zoneStartPort;
        zoneEndPort = zoneConfig.containsKey("zone-port-end")
                ? Integer.parseInt(zoneConfig.get("zone-port-end")) : zoneEndPort;
    }

    @Override
    public synchronized void register(ClientShellCommand csc) {
        if (!_logInvocation("register", csc, true)) return;

        // Check if client has been preregistered
        NodeRegistryEntry preregEntry = server.getNodeRegistry().getNodeByAddress(csc.getClientIpAddress());
        if (preregEntry==null) {
            log.warn("Non Preregistered node connected: {} @ {}", csc.getId(), csc.getClientIpAddress());
            zoneManagementStrategy.unexpectedNode(csc);
        }

        // Register client
        _do_register(csc);
    }

    @Override
    public synchronized void unregister(ClientShellCommand csc) {
        if (!_logInvocation("unregister", csc, true)) return;
        _do_unregister(csc);
    }

    protected synchronized void _do_register(ClientShellCommand csc) {
        // prepare configuration
        Map<String,BrokerConnectionConfig> connCfgMap = new LinkedHashMap<>();
        BrokerConnectionConfig groupingConn = getUpperwareBrokerConfig(server);
        connCfgMap.put(server.getUpperwareGrouping(), groupingConn);
        log.trace("ClusteringCoordinator: GLOBAL broker config.: {}", groupingConn);

        // collect client configurations per grouping
        for (String groupingName : server.getGroupingNames()) {
            groupingConn = getGroupingBrokerConfig(groupingName, csc);
            connCfgMap.put(groupingName, groupingConn);
            log.trace("ClusteringCoordinator: {} broker config.: {}", groupingName, groupingConn);
        }

        // send grouping configurations to client
        log.info("ClusteringCoordinator: --------------------------------------------------");
        log.info("ClusteringCoordinator: Sending grouping configurations to client {}...\n{}", csc.getId(), connCfgMap);
        sendGroupingConfigurations(connCfgMap, csc, server);
        log.info("ClusteringCoordinator: Sending grouping configurations to client {}... done", csc.getId());
        sleep(500);

        // Set active grouping
        String grouping = lastLevelGrouping.name();
        log.info("ClusteringCoordinator: --------------------------------------------------");
        log.info("ClusteringCoordinator: Setting active grouping of client {}: {}", csc.getId(), grouping);
        csc.setActiveGrouping(grouping);
        log.info("ClusteringCoordinator: --------------------------------------------------");
        sleep(500);

        // Add registered node in topology map
        addNodeInTopology(csc);
    }

    private synchronized void addNodeInTopology(ClientShellCommand csc) {
        // Assign client in a zone
        String zoneId = zoneManagementStrategy.getZoneIdFor(csc);
        log.debug("addNodeInTopology: New client: id={}, address={}, zone-id={}", csc.getId(), csc.getClientIpAddress(), zoneId);
        ClusterZone zone = topologyMap.computeIfAbsent(zoneId, id -> new ClusterZone(id, zoneStartPort, zoneEndPort));
        log.trace("addNodeInTopology: Zone members: BEFORE: {}", zone.getNodes());
        zone.addNode(csc);
        log.trace("addNodeInTopology: Zone members:  AFTER: {}", zone.getNodes());

        // Initialize new client's cluster node address/hostname, port and certificate
        String nodeAddress = csc.getClientIpAddress();
        String nodeHostname = csc.getClientHostname();
        String nodeCanonical = csc.getClientCanonicalHostname();
        int nodePort = zone.getPortForAddress(nodeAddress);
        csc.setClientClusterNodePort(nodePort);
        csc.setClientClusterNodeAddress(nodeAddress);
        csc.setClientClusterNodeHostname(nodeHostname);
        //csc.setClientClusterNodeHostname(nodeCanonical);
        log.debug("addNodeInTopology: New client: Cluster node: address={}, hostname={} // {}, port={}",
                nodeAddress, nodeHostname, nodeCanonical, nodePort);

        // Signal Zone Management Strategy for new client registration
        zoneManagementStrategy.nodeAdded(csc, this, zone);
        log.info("addNodeInTopology: Client added in topology: client={}, address={}", csc.getId(), csc.getClientIpAddress());
    }

    protected synchronized void _do_unregister(ClientShellCommand csc) {
        // Remove node from topology map
        removeNodeFromTopology(csc);
    }

    private synchronized void removeNodeFromTopology(ClientShellCommand csc) {
        // Assign client in a zone
        String zoneId = zoneManagementStrategy.getZoneIdFor(csc);
        ClusterZone zone = topologyMap.get(zoneId);
        if (zone == null) {
            log.warn("removeNodeFromTopology: Not Registered client removed: client={}, address={}", csc.getId(), csc.getClientIpAddress());
        } else {
            log.trace("removeNodeFromTopology: Zone members: BEFORE: {}", zone.getNodes());
            zone.removeNode(csc);
            log.trace("removeNodeFromTopology: Zone members:  AFTER: {}", zone.getNodes());
            zoneManagementStrategy.nodeRemoved(csc, this, zone);
            log.info("removeNodeFromTopology: Client removed from topology: client={}, address={}", csc.getId(), csc.getClientIpAddress());
        }
    }

    // ------------------------------------------------------------------------
    // Methods to be used by Zone Management Strategies
    // ------------------------------------------------------------------------

    void sendClusterKey(ClientShellCommand csc, ClusterZone zoneInfo) {
        csc.sendCommand(String.format("CLUSTER-KEY %s %s %s %s",
                zoneInfo.getClusterKeystoreFile().getName(), zoneInfo.getClusterKeystoreType(),
                zoneInfo.getClusterKeystorePassword(), zoneInfo.getClusterKeystoreBase64()));
    }

    void sendCommandToZone(String command, List<ClientShellCommand> zoneNodes) {
        log.info("sendCommandToZone: Sending command: \"{}\" to zone nodes: {}", command,
                zoneNodes.stream().map(ClientShellCommand::toStringCluster).collect(Collectors.toList()));
        zoneNodes.forEach(c -> c.sendCommand(command));
    }

    void instructClusterJoin(ClientShellCommand csc, ClusterZone zone) {
        List<ClientShellCommand> zoneNodes = zone.getNodes();
        log.debug("instructClusterJoin: Zone members: {}", zoneNodes);

        // Build zone members list
        final List<String> addresses = new ArrayList<>();
        final List<String> hostnames = new ArrayList<>();
        zoneNodes.forEach(c -> {
            if (c!=csc) {
                addresses.add(c.getClientClusterNodeAddress()+":"+c.getClientClusterNodePort());
                hostnames.add(c.getClientClusterNodeHostname()+":"+c.getClientClusterNodePort());
            }
        });
        log.debug("instructClusterJoin: New cluster node nearby members: addresses={}, hostnames={}", addresses, hostnames);

        // Prepare cluster join commands
        String command = String.format("%s  %s:%s:%s  %s:%d  %s",
                zone.getId(),
                topLevelGrouping, aggregatorGrouping, lastLevelGrouping,
                csc.getClientClusterNodeAddress(),
                csc.getClientClusterNodePort(),
                String.join(" ", addresses));
        /*String command =
                zone.getId()+" "
                +topLevelGrouping+":"+aggregatorGrouping+":"+lastLevelGrouping+" "
                +csc.getClientClusterNodeHostname()+":"+csc.getClientClusterNodePort()+" "
                +String.join(" ", hostnames);*/

        // Send cluster join command
        log.debug("instructClusterJoin: Client {} @ {} joins cluster: CLUSTER-JOIN {}", csc.getId(), csc.getClientIpAddress(), command);
        csc.sendCommand("CLUSTER-JOIN "+command);
    }

    void instructClusterLeave(ClientShellCommand csc, ClusterZone zone) {
        // Send cluster leave command
        log.debug("instructClusterLeave: Client {} @ {} leaves cluster: CLUSTER-LEAVE", csc.getId(), csc.getClientIpAddress());
        try {
            csc.sendCommand("CLUSTER-LEAVE");
        } catch (Exception e) {
            // Channel has probably already been closed
            log.warn("instructClusterLeave: EXCEPTION: ", e);
        }
    }

    void electAggregator(ClusterZone zone) {
        sendCommandToZone("CLUSTER-EXEC broker elect", zone.getNodes());
    }
}
