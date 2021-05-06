/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.server;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Node Registry
 */
@Slf4j
@Service
public class NodeRegistry {
    private final Map<String,NodeRegistryEntry> registry = new LinkedHashMap<>();
    @Getter @Setter
    private ServerCoordinator coordinator;

    public synchronized void addNode(Map<String,Object> nodeInfo) {
        String ipAddress = getIpAddressFromNodeInfo(nodeInfo);

        NodeRegistryEntry entry = registry.get(ipAddress);
        if (entry!=null) {
            log.debug("NodeRegistry.addNode(): Node already pre-registered: ip-address={}\nOld Node Info: {}\nNew Node Info: {}",
                    ipAddress, entry, nodeInfo);
            if (coordinator!=null && coordinator.allowAlreadyPreregisteredNode(nodeInfo)) {
                log.info("NodeRegistry.addNode(): PREVIOUS NODE INFO WILL BE OVERWRITTEN: ip-address={}\nOld Node Info: {}\nNew Node Info: {}",
                        ipAddress, entry, nodeInfo);
            } else {
                log.error("NodeRegistry.addNode(): Node already pre-registered and coordinator does not allow new pre-registration requests to overwrite the existing one: ip-address={}\nOld Node Info: {}\nNew Node Info: {}",
                        ipAddress, entry, nodeInfo);
                throw new IllegalStateException("NODE ALREADY PRE-REGISTERED: "+ipAddress);
            }
        }

        entry = new NodeRegistryEntry(ipAddress).nodePreregistration(nodeInfo);
        registry.put(ipAddress, entry);
        log.debug("NodeRegistry.addNode(): Added info for node at address: {}\nNode info: {}", ipAddress, nodeInfo);
    }

    public synchronized void removeNode(NodeRegistryEntry nodeEntry) {
        String ipAddress = nodeEntry.getIpAddress();
        removeNode(ipAddress);
    }

    public synchronized void removeNode(Map<String,Object> nodeInfo) {
        String ipAddress = getIpAddressFromNodeInfo(nodeInfo);
        removeNode(ipAddress);
    }

    public synchronized void removeNode(String ipAddress) {
        registry.remove(ipAddress);
        log.debug("NodeRegistry.removeNode(): Removed info for node at address: {}", ipAddress);
    }

    private String getIpAddressFromNodeInfo(Map<String,Object> nodeInfo) {
        Object value = nodeInfo.get("ip-address");
        if (value==null || StringUtils.isBlank(value.toString())) value = nodeInfo.get("address");
        if (value==null || StringUtils.isBlank(value.toString())) value = nodeInfo.get("ip");
        if (value==null || StringUtils.isBlank(value.toString())) return null;
        return value.toString();
    }

    public synchronized void clearNodes() {
        registry.clear();
        log.debug("NodeRegistry.clearNodes(): Cleared node info registry");
    }

    public NodeRegistryEntry getNodeByAddress(String ipAddress) {
        NodeRegistryEntry entry = registry.get(ipAddress);
        log.debug("NodeRegistry.getNodeByAddress(): Returning info for node at address: {}\nNode Info: {}", ipAddress, entry);
        return entry;
    }

    public Collection<String> getNodeAddresses() {
        return registry.keySet();
    }

    public Collection<NodeRegistryEntry> getNodes() {
        return registry.values();
    }
}
