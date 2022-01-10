/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.server.coordinator.cluster;

import eu.melodic.event.baguette.server.ClientShellCommand;
import eu.melodic.event.baguette.server.NodeRegistryEntry;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.internal.guava.InetAddresses;

import java.util.Map;
import java.util.UUID;

/**
 * A smarter than default Zone Management Strategy.
 * It groups clients based on domain name, or last byte of IP Address. If neither is available it assigns client
 * in a new zone identified by a random UUID.
 * When a zone contains only one client, no cluster initialization is instructed.
 * When a zone contains exactly two clients, they are both initialized as cluster nodes.
 * If only one client is left in a zone, it is instructed to leave cluster.
 */
@Slf4j
public class AtLeastTwoZoneManagementStrategy implements IZoneManagementStrategy {
    @Override
    public void notPreregisteredNode(ClientShellCommand csc) {
        log.warn("AtLeastTwoZoneManagementStrategy: Unexpected node connected: {} @ {}", csc.getId(), csc.getClientIpAddress());
    }

    @Override
    public void alreadyRegisteredNode(ClientShellCommand csc) {
        log.warn("AtLeastTwoZoneManagementStrategy: Node connection from an already registered IP address: {} @ {}", csc.getId(), csc.getClientIpAddress());
    }

    @Override
    public String getZoneIdFor(ClientShellCommand c) {
        String nodeAddress = c.getClientIpAddress();
        String hostname = c.getClientHostname();
        log.debug("getZoneIdFor: {}:  address: {}", c.getId(), nodeAddress);
        log.debug("getZoneIdFor: {}: hostname: {}", c.getId(), hostname);
        String zoneName = null;
        NodeRegistryEntry entry = c.getNodeRegistryEntry();
        if (entry!=null) {
            String zoneId = getZoneIdFor(c.getNodeRegistryEntry());
            if (StringUtils.isNotBlank(zoneId)) {
                return zoneId;
            }
        }
        if (StringUtils.isNotBlank(hostname) && !InetAddresses.isUriInetAddress(hostname)) {
            int p = hostname.indexOf(".");
            if (p>0)
                zoneName = hostname.substring(p+1);
        }
        if (StringUtils.isBlank(zoneName) && StringUtils.isNotBlank(nodeAddress)) {
            int p = nodeAddress.lastIndexOf(".");
            if (p<0) p = nodeAddress.lastIndexOf(":");
            if (p>0)
                zoneName = nodeAddress.substring(0, p);
        }
        return StringUtils.isBlank(zoneName)
                ? UUID.randomUUID().toString()
                : zoneName.replaceAll("[^A-Za-z0-9_]","_");
    }

    @Override
    public String getZoneIdFor(@NonNull NodeRegistryEntry entry) {
        log.debug("getZoneIdFor: {}: >>>>>>>>>>>>> NRE: {}", entry.getClientId(), entry);
        Map<String, String> preregInfo = entry.getPreregistration();
        log.debug("getZoneIdFor: {}: >>>>>>>>>>>>> Prereg-Info: {}", entry.getClientId(), preregInfo);
        if (preregInfo!=null) {
            String zoneId = preregInfo.get("zone-id");
            log.debug("getZoneIdFor: {}: >>>>>>>>>>>>> Zone-Id: {}", entry.getClientId(), zoneId);
            if (StringUtils.isNotBlank(zoneId)) {
                log.debug("getZoneIdFor: {}: >>>>>>>>>>>>> Found Zone-Id in Prereg-Info: {}", entry.getClientId(), zoneId);
                return zoneId;
            }
        }
        return null;
    }

    @Override
    public synchronized void nodeAdded(ClientShellCommand csc, ClusteringCoordinator coordinator, ClusterZone zone) {
        if (zone.getNodes().size() < 2)
            return;

        if (zone.getNodes().size()==2) {
            // Instruct first node to join cluster first (in fact to initialize it)
            ClientShellCommand firstNode = zone.getNodes().get(0);
            log.info("AtLeastTwoZoneManagementStrategy: First node to join cluster: client={}, zone={}", firstNode.getId(), zone.getId());
            joinToCluster(firstNode, coordinator, zone);
        }

        // Instruct new node to join cluster
        log.info("AtLeastTwoZoneManagementStrategy: Node to join cluster: client={}, zone={}", csc.getId(), zone.getId());
        joinToCluster(csc, coordinator, zone);

        // Instruct aggregator election if at least 2 nodes are present in the zone
        if (zone.getNodes().size()==2) {
            log.info("AtLeastTwoZoneManagementStrategy: Elect aggregator: zone={}", zone.getId());
            coordinator.sleep(5000);
            coordinator.electAggregator(zone);
        }
    }

    private void joinToCluster(ClientShellCommand csc, ClusteringCoordinator coordinator, ClusterZone zone) {
        coordinator.sendClusterKey(csc, zone);
        coordinator.instructClusterJoin(csc, zone, false);

        coordinator.sleep(1000);
        csc.sendCommand("CLUSTER-EXEC broker list");
        //coordinator.sleep(1000);
        //csc.sendCommand("CLUSTER-TEST");
    }

    @Override
    public synchronized void nodeRemoved(ClientShellCommand csc, ClusteringCoordinator coordinator, ClusterZone zone) {
        // Instruct node to leave cluster
        log.info("AtLeastTwoZoneManagementStrategy: Node to leave cluster: client={}, zone={}", csc.getId(), zone.getId());
        coordinator.instructClusterLeave(csc, zone);

        if (zone.getNodes().size()==1) {
            // Instruct last node to leave cluster (and terminate cluster)
            ClientShellCommand lastNode = zone.getNodes().get(0);
            log.info("AtLeastTwoZoneManagementStrategy: Last node to leave cluster: client={}, zone={}", lastNode.getId(), zone.getId());
            coordinator.instructClusterLeave(lastNode, zone);
        }
    }
}
