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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.internal.guava.InetAddresses;

import java.util.UUID;

/**
 * The default Zone Management Strategy used when 'zone-management-strategy-class' property is not set.
 * It groups clients based on domain name, or last byte of IP Address. If neither is available it assigns client
 * in a new zone identified by a random UUID.
 * The first client to join a zone will be instructed to start cluster and become aggregator.
 * Subsequent clients will just join the cluster.
 */
@Slf4j
public class DefaultZoneManagementStrategy implements IZoneManagementStrategy {
    @Override
    public void notPreregisteredNode(ClientShellCommand csc) {
        log.warn("DefaultZoneManagementStrategy: Unexpected node connected: {} @ {}", csc.getId(), csc.getClientIpAddress());
    }

    @Override
    public void alreadyRegisteredNode(ClientShellCommand csc) {
        log.warn("DefaultZoneManagementStrategy: Node connection from an already registered IP address: {} @ {}", csc.getId(), csc.getClientIpAddress());
    }

    @Override
    public String getZoneIdFor(ClientShellCommand c) {
        String nodeAddress = c.getClientIpAddress();
        String hostname = c.getClientHostname();
        log.debug("getZoneIdFor: {}:  address: {}", c.getId(), nodeAddress);
        log.debug("getZoneIdFor: {}: hostname: {}", c.getId(), hostname);
        String zoneName = null;
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
    public synchronized void nodeAdded(ClientShellCommand csc, ClusteringCoordinator coordinator, ClusterZone zone) {
        // Instruct new node to join cluster
        log.info("DefaultZoneManagementStrategy: Node to join cluster: client={}, zone={}", csc.getId(), zone.getId());
        joinToCluster(csc, coordinator, zone);
    }

    private void joinToCluster(ClientShellCommand csc, ClusteringCoordinator coordinator, ClusterZone zone) {
        coordinator.sendClusterKey(csc, zone);
        coordinator.instructClusterJoin(csc, zone, true);

        coordinator.sleep(1000);
        csc.sendCommand("CLUSTER-EXEC broker list");
        //coordinator.sleep(1000);
        //csc.sendCommand("CLUSTER-TEST");
    }

    @Override
    public synchronized void nodeRemoved(ClientShellCommand csc, ClusteringCoordinator coordinator, ClusterZone zone) {
        // Instruct node to leave cluster
        log.info("DefaultZoneManagementStrategy: Node to leave cluster: client={}, zone={}", csc.getId(), zone.getId());
        coordinator.instructClusterLeave(csc, zone);
    }
}
