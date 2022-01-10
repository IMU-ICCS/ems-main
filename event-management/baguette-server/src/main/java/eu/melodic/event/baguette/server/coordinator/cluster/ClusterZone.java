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
import eu.melodic.event.util.KeystoreUtil;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Data
public class ClusterZone implements IClusterZone {
    private final String id;
    private final int startPort;
    private final int endPort;

    @Getter(AccessLevel.NONE)
    private final AtomicInteger currentPort = new AtomicInteger(1200);
    @Getter(AccessLevel.NONE)
    private final Map<String,ClientShellCommand> nodes = new LinkedHashMap<>();
    @Getter(AccessLevel.NONE)
    private final Map<String, Integer> addressPortCache = new HashMap<>();
    @Getter(AccessLevel.NONE)
    private final Map<String, NodeRegistryEntry> nodesWithoutClient = new LinkedHashMap<>();

    private final String clusterId;
    private final String clusterKeystoreBase64;
    private final File clusterKeystoreFile;
    private final String clusterKeystoreType;
    private final String clusterKeystorePassword;
    @Getter @Setter
    private ClientShellCommand aggregator;

    @SneakyThrows
    public ClusterZone(@NotBlank String id, int startPort, int endPort) {
        checkArgs(id, startPort, endPort);
        this.id = id;
        this.startPort = startPort;
        this.endPort = endPort;
        currentPort.set(startPort);

        this.clusterId = RandomStringUtils.randomAlphanumeric(64);
        String fileName = String.format("logs/cluster_%d_%s.p12", System.currentTimeMillis(), id);
        this.clusterKeystoreFile = new File(fileName);
        this.clusterKeystoreType = "JKS";
        this.clusterKeystorePassword = RandomStringUtils.randomAlphanumeric(64);
        log.info("New ClusterZone:  zone: {}", id);
        log.info("                  file: {}", clusterKeystoreFile);
        log.info("                  type: {}", clusterKeystoreType);
        log.debug("              password: {}",
                StringUtils.isNotBlank(clusterKeystorePassword) ? "Provided" : "Not provided");
        this.clusterKeystoreBase64 = KeystoreUtil
                .getKeystore(clusterKeystoreFile.getCanonicalPath(), clusterKeystoreType, clusterKeystorePassword)
                .createIfNotExist()
                .createKeyAndCert(clusterId, "CN=" + clusterId, "")
                .readFileAsBase64();
        log.debug("        Base64 content: {}", clusterKeystoreBase64);
    }

    private void checkArgs(String id, int startPort, int endPort) {
        if (StringUtils.isBlank(id))
            throw new IllegalArgumentException("Zone id cannot be null or blank: zone-id="+id);
        if (startPort<1 || endPort<1 || startPort>65535 || endPort>65535)
            throw new IllegalArgumentException("Zone start/end port must be between 1 and 65535: zone-id="+id+", start="+startPort+", end="+endPort);
        if (startPort > endPort)
            throw new IllegalArgumentException("Zone start port must be less than or equal to end port: zone-id="+id+", start="+startPort+", end="+endPort);
    }

    public int getPortForAddress(String address) {
        return addressPortCache.computeIfAbsent(address, k -> {
            int port = currentPort.incrementAndGet();
            if (port>endPort)
                throw new IllegalStateException("Zone ports exhausted: "+id);
            log.debug("Mapped address-to-port: {} -> {}", address, port);
            return port;
        });
    }

    public void clearAddressToPortCache() {
        addressPortCache.clear();
    }

    // Nodes management
    public void addNode(@NonNull ClientShellCommand csc) {
        synchronized (Objects.requireNonNull(csc)) {
            nodes.put(csc.getClientIpAddress(), csc);
            csc.setClientZone(this);
        }
    }

    public void removeNode(@NonNull ClientShellCommand csc) {
        synchronized (Objects.requireNonNull(csc)) {
            nodes.remove(csc.getClientIpAddress());
            if (csc.getClientZone()==this)
                csc.setClientZone(null);
        }
    }

    public List<ClientShellCommand> getNodes() {
        return new ArrayList<>(nodes.values());
    }

    public ClientShellCommand getNodeByAddress(String address) {
        return nodes.get(address);
    }

    // Nodes-without-Clients management
    public synchronized void addNodeWithoutClient(@NonNull NodeRegistryEntry entry) {
        String address = entry.getIpAddress();
        if (address==null) address = entry.getNodeAddress();
        if (address==null) throw new IllegalArgumentException("Node address not found in Preregistration info");
        nodesWithoutClient.put(address, entry);
    }

    public synchronized void removeNodeWithoutClient(@NonNull NodeRegistryEntry entry) {
        String address = entry.getIpAddress();
        if (address==null) address = entry.getNodeAddress();
        if (address==null) throw new IllegalArgumentException("Node address not found in Preregistration info");
        nodesWithoutClient.remove(address);
    }

    public List<NodeRegistryEntry> getNodesWithoutClient() {
        return new ArrayList<>(nodesWithoutClient.values());
    }

    public NodeRegistryEntry getNodeWithoutClientByAddress(String address) {
        return nodesWithoutClient.get(address);
    }
}
