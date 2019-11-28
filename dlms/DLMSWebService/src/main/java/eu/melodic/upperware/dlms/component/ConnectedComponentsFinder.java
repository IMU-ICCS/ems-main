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
    private List<String> communicatingComponentsNames;
    private ComponentId comp;
    private String agentNodeLocation;
    private String agentNodeProvider;

    public ConnectedComponentsFinder(String agentNodeName, ComponentId comp) throws ApiException {
        this.communicatingComponentsNames = new ModelConnectionAnalyzer(agentNodeName).findCommunicatingComponentsNames();
        this.comp = comp;
        this.agentNodeLocation = this.findNodeLocation(agentNodeName);
        this.agentNodeProvider = this.findNodeProvider(agentNodeName);
    }

    /**
     * Returns agent node location from cloudiator.
     */
    private String findNodeLocation(String nodeName) throws ApiException {
        return comp.findNodeLocation(nodeName).orElseGet(() -> {
            log.error("Cloudiator could not find node location for a given node: " + nodeName);
            return "ERROR";
        });
    }

    /**
     * Returns agent node ip from cloudiator.
     */
    private String findNodeIp(String nodeName) throws ApiException {
        return comp.findNodeIp(nodeName).orElseGet(() -> {
            log.error("Cloudiator could not find node IP for a given node: " + nodeName);
            return "ERROR";
        });
    }

    private String findNodeProvider(String nodeName) throws ApiException {
        return comp.findNodeProvider(nodeName).orElseGet(() -> {
            log.error("Cloudiator could not find node provider for a given node: " + nodeName);
            return "ERROR";
        });
    }

    /**
     * Creates a list of Configurations: objects storing information about instances connected to the agent.
     */
    public List<Configuration> createConfigurationList() throws ApiException {
        List<Configuration> configs = new ArrayList<>();

        for (String name : this.communicatingComponentsNames) {
            comp.findNodeProvider(name);
            LatencyConfiguration latConf = new LatencyConfigurationImpl();
            latConf.setComponentName(name);
            latConf.setComponentIP(this.findNodeIp(name));
            latConf.setComponentRegion(this.findNodeLocation(name));
            latConf.setAgentRegion(this.agentNodeLocation);
            latConf.setComponentCloud(this.findNodeProvider(name));
            latConf.setAgentCloud(this.agentNodeProvider);
            configs.add(new Configuration(latConf));
        }

        return configs;
    }
}