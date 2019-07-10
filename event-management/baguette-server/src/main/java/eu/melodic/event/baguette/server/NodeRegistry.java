/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.baguette.server;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Node Registry
 */
@Slf4j
@Service
public class NodeRegistry {
    private Map<String,Map<String,Object>> registry = new HashMap<>();

    public synchronized void addNode(Map<String,Object> nodeInfo) {
        String ipAddress = nodeInfo.get("ip").toString();
        registry.put(ipAddress, new HashMap<>(nodeInfo));
        log.debug("NodeRegistry.addNode(): Added info for node at address: {}\nNode info: {}", ipAddress, nodeInfo);
    }

    public synchronized void removeNode(Map<String,Object> nodeInfo) {
        String ipAddress = nodeInfo.get("ip").toString();
        registry.remove(ipAddress);
        log.debug("NodeRegistry.removeNode(): Removed info for node at address: {}", ipAddress);
    }

    public synchronized void clearNodes() {
        registry.clear();
        log.debug("NodeRegistry.clearNodes(): Cleared node info registry");
    }

    public Map<String,Object> getNodeByAddress(String ipAddress) {
        Map<String, Object> info = MapUtils.emptyIfNull(registry.get(ipAddress));
        log.debug("NodeRegistry.getNodeByAddress(): Returning info for node at address: {}\nNode Info: {}", ipAddress, info);
        return info;
    }

    public Collection<String> getNodeAddresses() {
        return registry.keySet();
    }

    public Collection<Map<String,Object>> getNodes() {
        return registry.values();
    }
}
