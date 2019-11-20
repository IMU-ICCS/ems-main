package eu.melodic.upperware.dlms.component;

import eu.melodic.models.interfaces.dlms.Configuration;
import eu.melodic.models.interfaces.dlms.LatencyConfiguration;
import eu.melodic.models.interfaces.dlms.LatencyConfigurationImpl;
import eu.melodic.upperware.dlms.camel.ModelConnectionAnalyzer;
import io.github.cloudiator.rest.ApiException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class responsible for finding instances connected to a given node and creating a list of them.
 */
@Slf4j
public
class ConnectedComponentsFinder {
    private String agentNodeName;
    private List<String> communicatingComponentsNames;
    private ComponentId comp;

    public ConnectedComponentsFinder(String agentNodeName, ComponentId comp) throws ApiException {
        this.agentNodeName = agentNodeName;
        this.communicatingComponentsNames = new ModelConnectionAnalyzer(agentNodeName).findCommunicatingComponentsNames();
        this.comp = comp;
    }

    // Get agent node location from cloudiator
    private String findNodeLocation(String nodeName) throws ApiException {
        log.info("Invoking ConnectedComponentsFinder:findNodeLocation for nodeName " + nodeName);
        final Optional<String> agentNodeLocationOptional = this.comp.findNodeLocation(nodeName);
        if (!agentNodeLocationOptional.isPresent()) {
            log.error("Cloudiator could not find node location for a given agent node: " + nodeName);
            return "ERROR";
        }
        return agentNodeLocationOptional.get();
//        return comp.findNodeLocation(nodeName).orElse("ERROR");
    }

    private String findNodeIp(String nodeName) throws ApiException {
        final Optional<String> nodeIpOptional = comp.findNodeIp(nodeName);
        if (!nodeIpOptional.isPresent()) {
            log.error("Cloudiator could not find node ip for a given component: " + nodeName);
            return "ERROR";
        }
        return nodeIpOptional.get();

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