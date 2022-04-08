/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.server;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.melodic.event.baguette.server.coordinator.cluster.IClusterZone;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class NodeRegistryEntry {
    public enum STATE { PREREGISTERED, IGNORE_NODE, INSTALLING, NOT_INSTALLED, INSTALLED, INSTALL_ERROR,
        WAITING_REGISTRATION, REGISTERED, NOT_REGISTERED, REGISTRATION_ERROR, DISCONNECTED, NODE_FAILED
    };
    @Getter private final String ipAddress;
    @Getter private final String clientId;
    @JsonIgnore
    @Getter private final transient BaguetteServer baguetteServer;
    @Getter private String hostname;
    @Getter private STATE state = null;
    @Getter private Date stateLastUpdate;
    @Getter private String reference = UUID.randomUUID().toString();
    @JsonIgnore
    @Getter private transient Map<String, String> preregistration = new LinkedHashMap<>();
    @JsonIgnore
    @Getter private transient Map<String, String> installation = new LinkedHashMap<>();
    @JsonIgnore
    @Getter private transient Map<String, String> registration = new LinkedHashMap<>();
    @JsonIgnore
    @Getter @Setter private transient IClusterZone clusterZone;

    public String getNodeId() {
        return getPreregistration().get("id");
    }

    public String getNodeAddress() {
        return ipAddress!=null ? ipAddress : getPreregistration().get("address");
    }

    public String getNodeIdOrAddress() {
        return StringUtils.isNotBlank(getNodeId()) ? getNodeId() : getNodeAddress();
    }

    public String getNodeIdAndAddress() {
        return getNodeId()+" @ "+getNodeAddress();
    }

    private void setState(@NonNull STATE s) {
        state = s;
        stateLastUpdate = new Date();
    }

    public void refreshReference() { reference = UUID.randomUUID().toString(); }

    public NodeRegistryEntry nodePreregistration(Map<String,Object> nodeInfo) {
        preregistration.clear();
        preregistration.putAll(processMap("", nodeInfo));
//        preregistration.putAll((Map)processMap(nodeInfo));
        setState(STATE.PREREGISTERED);
        return this;
    }

    public NodeRegistryEntry nodeIgnore(Object nodeInfo) {
        installation.clear();
        installation.put("ignore-node", nodeInfo!=null ? nodeInfo.toString() : null);
        setState(STATE.IGNORE_NODE);
        return this;
    }

    public NodeRegistryEntry nodeInstalling(Object nodeInfo) {
        installation.clear();
        installation.put("installation-task", nodeInfo!=null ? nodeInfo.toString() : "INSTALLING");
        setState(STATE.INSTALLING);
        return this;
    }

    public NodeRegistryEntry nodeNotInstalled(Object nodeInfo) {
        installation.clear();
        installation.put("installation-task-result", nodeInfo!=null ? nodeInfo.toString() : "NOT_INSTALLED");
        setState(STATE.NOT_INSTALLED);
        return this;
    }

    public NodeRegistryEntry nodeInstallationComplete(Object nodeInfo) {
        installation.put("installation-task-result", nodeInfo!=null ? nodeInfo.toString() : "SUCCESS");
        setState(STATE.INSTALLED);
        return this;
    }

    public NodeRegistryEntry nodeInstallationError(Object nodeInfo) {
        installation.put("installation-task-result", nodeInfo!=null ? nodeInfo.toString() : "ERROR");
        setState(STATE.INSTALL_ERROR);
        return this;
    }

    public NodeRegistryEntry nodeRegistration(Map<String,Object> nodeInfo) {
        registration.clear();
        registration.putAll(processMap("", nodeInfo));
        setState(STATE.REGISTERED);
        return this;
    }

    public NodeRegistryEntry nodeFailed(Map<String,Object> failInfo) {
        if (failInfo!=null)
            registration.putAll(processMap("", failInfo));
        setState(STATE.NODE_FAILED);
        return this;
    }

    private Map<String,Object> processMap(Map<String,Object> inMap) {
        Map<String,Object> outMap = new LinkedHashMap<>();
        for (Map.Entry<String,Object> entry : inMap.entrySet()) {
            if (entry.getValue()!=null && entry.getValue() instanceof Map) {
                Map tmpMap = processMap((Map) entry.getValue());
                outMap.put(entry.getKey(), tmpMap);
            } else {
                outMap.put(entry.getKey(), entry.getValue()!=null ? entry.getValue().toString() : null);
            }
        }
        return outMap;
    }

    private Map<String,String> processMap(String prefix, Map<String,Object> inMap) {
        Map<String,String> outMap = new LinkedHashMap<>();
        for (Map.Entry<String,Object> entry : inMap.entrySet()) {
            String newKey = prefix.isEmpty()
                    ? entry.getKey()
                    : (entry.getKey()!=null) ? prefix+"."+entry.getKey() : prefix;
            if (entry.getValue()!=null && entry.getValue() instanceof Map) {
                Map tmpMap = processMap(newKey, (Map) entry.getValue());
                outMap.putAll(tmpMap);
            } else {
                outMap.put(newKey, entry.getValue()!=null ? entry.getValue().toString() : null);
            }
        }
        return outMap;
    }
}
