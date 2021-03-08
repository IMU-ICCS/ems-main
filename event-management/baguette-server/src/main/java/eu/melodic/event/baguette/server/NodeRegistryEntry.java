package eu.melodic.event.baguette.server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class NodeRegistryEntry {
    public enum STATE { PREREGISTERED, INSTALLED, REGISTERED };
    private final String ipAddress;
    @Getter private STATE state = null;
    @Getter private Map<String, String> preregistration = new LinkedHashMap<>();
    @Getter private Map<String, String> installation = new LinkedHashMap<>();
    @Getter private Map<String, String> registration = new LinkedHashMap<>();

    public NodeRegistryEntry nodePreregistration(Map<String,Object> nodeInfo) {
        preregistration.clear();
        preregistration.putAll(processMap("", nodeInfo));
//        preregistration.putAll((Map)processMap(nodeInfo));
        state = STATE.PREREGISTERED;
        return this;
    }

    public NodeRegistryEntry nodeInstallation(Map<String,Object> nodeInfo) {
        installation.clear();
        installation.putAll(processMap("", nodeInfo));
        state = STATE.INSTALLED;
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
