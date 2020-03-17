package eu.melodic.upperware.guibackend.service.byon;

import eu.melodic.upperware.guibackend.model.byon.ByonDefinition;
import io.github.cloudiator.rest.model.IpAddress;
import io.github.cloudiator.rest.model.NewNode;
import io.github.cloudiator.rest.model.NodeProperties;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ByonMapper {
    public NewNode mapByonDefinitionToNewNode(ByonDefinition byonDefinitionForNode) {
        NewNode newNode = new NewNode();
        newNode.setName(byonDefinitionForNode.getName());
        newNode.setLoginCredential(byonDefinitionForNode.getLoginCredential());
        newNode.setIpAddresses(mapGuiIpAddressesToCloudiatorIpAddresses(byonDefinitionForNode.getIpAddresses()));
        newNode.setNodeProperties(mapGuiNodePropertiesToCloudiatorNodeProperties(byonDefinitionForNode.getNodeProperties()));
        return newNode;
    }

    private NodeProperties mapGuiNodePropertiesToCloudiatorNodeProperties(eu.melodic.upperware.guibackend.model.byon.NodeProperties guiNodeProperties) {
        NodeProperties resultNodeProperties = new NodeProperties();
        resultNodeProperties.setProviderId(guiNodeProperties.getProviderId());
        resultNodeProperties.setNumberOfCores(guiNodeProperties.getNumberOfCores());
        resultNodeProperties.setMemory(guiNodeProperties.getMemory());
        resultNodeProperties.setDisk(guiNodeProperties.getDisk());
        resultNodeProperties.setOperatingSystem(guiNodeProperties.getOperatingSystem());
        resultNodeProperties.setGeoLocation(guiNodeProperties.getGeoLocation());
        return resultNodeProperties;
    }

    private List<IpAddress> mapGuiIpAddressesToCloudiatorIpAddresses(List<eu.melodic.upperware.guibackend.model.byon.IpAddress> ipAddresses) {
        return ipAddresses.stream()
                .map(ipAddress -> (IpAddress) ipAddress)
                .collect(Collectors.toList());
    }
}
