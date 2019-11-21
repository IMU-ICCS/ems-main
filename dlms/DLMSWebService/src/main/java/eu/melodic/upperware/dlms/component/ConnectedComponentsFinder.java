package eu.melodic.upperware.dlms.component;

import eu.melodic.models.interfaces.dlms.Configuration;
import eu.melodic.models.interfaces.dlms.LatencyConfiguration;
import eu.melodic.models.interfaces.dlms.LatencyConfigurationImpl;
import eu.melodic.upperware.dlms.camel.ModelConnectionAnalyzer;
import io.github.cloudiator.rest.ApiException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for finding instances connected to a given node and creating a list of them.
 */
@Slf4j
public
class ConnectedComponentsFinder {
    private String agentNodeName;
    private List<String> communicatingComponentsNames;
    private ComponentId comp;

    public ConnectedComponentsFinder(String agentNodeName, ComponentId comp) {
        this.agentNodeName = agentNodeName;
        this.communicatingComponentsNames = new ModelConnectionAnalyzer(agentNodeName).findCommunicatingComponentsNames();
        this.comp = comp;
    }

    // Get agent node location from cloudiator
    private String findNodeLocation(String nodeName) throws ApiException {
        return comp.findNodeLocation(nodeName).orElseGet(() -> {
            log.error("Cloudiator could not find node location for a given agent node: " + nodeName);
            return "ERROR";
        });
    }

    private String findNodeIp(String nodeName) throws ApiException {
        return comp.findNodeIp(nodeName).orElseGet(() -> {
            log.error("Cloudiator could not find node location for a given agent node: " + nodeName);
            return "ERROR";
        });

    }

    public List<Configuration> createConfigurationList() throws ApiException {
        List<Configuration> configs = new ArrayList<>();
        String agentNodeLocation = this.findNodeLocation(this.agentNodeName); // TODO why this line in constructor results in NullPointerException?

        for (String name : this.communicatingComponentsNames) {
            LatencyConfiguration latConf = new LatencyConfigurationImpl();
            latConf.setComponentName(name);
            latConf.setComponentIP(this.findNodeIp(name));
            latConf.setComponentRegion(this.findNodeLocation(name));
            latConf.setAgentRegion(agentNodeLocation);
            configs.add(new Configuration(latConf));
        }

        return configs;
    }
}