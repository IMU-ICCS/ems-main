/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.server;

import lombok.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class NodeRegistryEntry {
    public enum STATE { PREREGISTERED, INSTALLING, INSTALLED, INSTALL_ERROR,
        WAITING_REGISTRATION, REGISTERED, NOT_REGISTERED, REGISTRATION_ERROR,
        DISCONNECTED
    };
    @Getter private final String ipAddress;
    @Getter private final String clientId;
    @Getter private final BaguetteServer baguetteServer;
    @Getter private STATE state = null;
    @Getter private String reference = UUID.randomUUID().toString();
    @Getter private Map<String, String> preregistration = new LinkedHashMap<>();
    @Getter private Map<String, String> installation = new LinkedHashMap<>();
    @Getter private Map<String, String> registration = new LinkedHashMap<>();

    public void refreshReference() { reference = UUID.randomUUID().toString(); }

    public NodeRegistryEntry nodePreregistration(Map<String,Object> nodeInfo) {
        preregistration.clear();
        preregistration.putAll(processMap("", nodeInfo));
//        preregistration.putAll((Map)processMap(nodeInfo));
        state = STATE.PREREGISTERED;
        return this;
    }

    public NodeRegistryEntry nodeInstalling(Object nodeInfo) {
        installation.clear();
        installation.put("installation-task", nodeInfo.toString());
        state = STATE.INSTALLING;
        return this;
    }

    public NodeRegistryEntry nodeInstallationComplete(Object nodeInfo) {
        installation.put("installation-task-result", "SUCCESS");
        state = STATE.INSTALLED;
        return this;
    }

    public NodeRegistryEntry nodeInstallationError(Object nodeInfo) {
        installation.put("installation-task-result", "ERROR");
        state = STATE.INSTALL_ERROR;
        return this;
    }

    public NodeRegistryEntry nodeRegistration(Map<String,Object> nodeInfo) {
        registration.clear();
        registration.putAll(processMap("", nodeInfo));
        state = STATE.REGISTERED;
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
