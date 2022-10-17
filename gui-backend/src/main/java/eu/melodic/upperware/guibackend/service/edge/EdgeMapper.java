package eu.melodic.upperware.guibackend.service.edge;

import eu.passage.upperware.commons.model.edge.EdgeDefinition;

import eu.passage.upperware.commons.model.edge.EdgeNode;
import eu.passage.upperware.commons.model.internal.*;
import org.activeeon.morphemic.model.IpAddress;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EdgeMapper {
    public org.activeeon.morphemic.model.EdgeDefinition mapEdgeDefinitionToProactive(EdgeDefinition edgeDefinitionForNode) {
        org.activeeon.morphemic.model.EdgeDefinition edgeDefinitionProactive = new org.activeeon.morphemic.model.EdgeDefinition();
        edgeDefinitionProactive.setName(edgeDefinitionForNode.getName());
        edgeDefinitionProactive.setLoginCredential((convertLoginCredential(edgeDefinitionForNode.getLoginCredential())));
        edgeDefinitionProactive.setIpAddresses(convertIpAddresses(edgeDefinitionForNode.getIpAddresses()));
        edgeDefinitionProactive.setNodeProperties(convertNodeProperties(edgeDefinitionForNode.getNodeProperties()));
        edgeDefinitionProactive.setJarURL(edgeDefinitionForNode.getJarURL());
        edgeDefinitionProactive.setScriptURL(edgeDefinitionForNode.getScriptURL());
        edgeDefinitionProactive.setSystemArch(edgeDefinitionForNode.getSystemArch());

        return edgeDefinitionProactive;
    }

    private org.activeeon.morphemic.model.LoginCredential convertLoginCredential(LoginCredential loginCredential) {
        org.activeeon.morphemic.model.LoginCredential loginCredentialProactive = new org.activeeon.morphemic.model.LoginCredential();
        loginCredentialProactive.setUsername(loginCredential.getUsername());
        loginCredentialProactive.setPassword(loginCredential.getPassword());
        loginCredentialProactive.setPrivateKey(loginCredential.getPrivateKey());

        return loginCredentialProactive;
    }

    private List<IpAddress> convertIpAddresses(List<eu.passage.upperware.commons.model.internal.IpAddress> ipAddresses) {
        return ipAddresses.stream()
                .map(this::convertIpAddress)
                .collect(Collectors.toList());
    }

    private org.activeeon.morphemic.model.IpAddress convertIpAddress(eu.passage.upperware.commons.model.internal.IpAddress ipAddress) {
        org.activeeon.morphemic.model.IpAddress ipAddressProactive = new org.activeeon.morphemic.model.IpAddress();
        ipAddressProactive.setIpAddressType(org.activeeon.morphemic.model.IpAddressType.valueOf(ipAddress.getIpAddressType().name()));
        ipAddressProactive.setIpVersion(org.activeeon.morphemic.model.IpVersion.valueOf(ipAddress.getIpVersion().name()));
        ipAddressProactive.setValue(ipAddress.getValue());

        return ipAddressProactive;
    }

    private org.activeeon.morphemic.model.NodeProperties convertNodeProperties(NodeProperties nodeProperties) {
        org.activeeon.morphemic.model.NodeProperties nodePropertiesProactive = new org.activeeon.morphemic.model.NodeProperties();
        nodePropertiesProactive.setDisk(nodeProperties.getDisk());
        nodePropertiesProactive.setMemory(nodeProperties.getMemory());
        nodePropertiesProactive.setNumberOfCores(nodeProperties.getNumberOfCores());
        nodePropertiesProactive.setProviderId(nodeProperties.getProviderId());


        org.activeeon.morphemic.model.OperatingSystem operatingSystem = new org.activeeon.morphemic.model.OperatingSystem();
        operatingSystem.setOperatingSystemArchitecture(org.activeeon.morphemic.model.OperatingSystemArchitecture.valueOf(Objects.requireNonNull(nodeProperties.getOperatingSystem().getOperatingSystemArchitecture()).name()));
        operatingSystem.setOperatingSystemFamily(org.activeeon.morphemic.model.OperatingSystemFamily.valueOf(Objects.requireNonNull(nodeProperties.getOperatingSystem().getOperatingSystemFamily()).name()));
        operatingSystem.setOperatingSystemVersion(nodeProperties.getOperatingSystem().getOperatingSystemVersion());
        nodePropertiesProactive.setOperatingSystem(operatingSystem);

        return nodePropertiesProactive;
    }

    public EdgeNode mapProactiveEdgeNodeToInternal(org.activeeon.morphemic.model.EdgeNode edgeNodeProactive) {
        EdgeNode edgeNode = new EdgeNode();
        edgeNode.setId(edgeNodeProactive.getId());
        edgeNode.setAllocated(edgeNodeProactive.isAllocated());
        edgeNode.setName(edgeNodeProactive.getName());
        edgeNode.setJarURL(edgeNodeProactive.getJarURL());
        edgeNode.setScriptURL(edgeNodeProactive.getScriptURL());
        edgeNode.setSystemArch(edgeNodeProactive.getSystemArch());
        edgeNode.setNodeCandidate(edgeNodeProactive.getNodeCandidate().toString());
        edgeNode.setReason(edgeNodeProactive.getReason());
        edgeNode.setDiagnostic(edgeNodeProactive.getDiagnostic());
        edgeNode.setUserId(edgeNodeProactive.getUserId());
        edgeNode.setIpAddresses(convertProactiveIpAddresses(edgeNodeProactive.getIpAddresses()));
        edgeNode.setLoginCredential(convertProactiveLoginCredential(edgeNodeProactive.getLoginCredential()));
        edgeNode.setNodeProperties(convertProactiveNodeProperties(edgeNodeProactive.getNodeProperties()));

        return edgeNode;
    }

    private List<eu.passage.upperware.commons.model.internal.IpAddress> convertProactiveIpAddresses(List<org.activeeon.morphemic.model.IpAddress> proactiveIpAddresses) {
        return proactiveIpAddresses.stream()
                .map(this::convertProactiveIpAddress)
                .collect(Collectors.toList());
    }

    private eu.passage.upperware.commons.model.internal.IpAddress convertProactiveIpAddress(org.activeeon.morphemic.model.IpAddress proactiveIpAddress) {
        eu.passage.upperware.commons.model.internal.IpAddress ipAddress = new eu.passage.upperware.commons.model.internal.IpAddress();
        ipAddress.setIpAddressType(IpAddressType.valueOf(proactiveIpAddress.getIpAddressType().name()));
        ipAddress.setIpVersion(IpVersion.valueOf(proactiveIpAddress.getIpVersion().name()));
        ipAddress.setValue(proactiveIpAddress.getValue());

        return ipAddress;
    }

    private LoginCredential convertProactiveLoginCredential(org.activeeon.morphemic.model.LoginCredential proactiveLoginCredential) {
        LoginCredential loginCredentialProactive = new LoginCredential();
        loginCredentialProactive.setUsername(proactiveLoginCredential.getUsername());
        loginCredentialProactive.setPassword(proactiveLoginCredential.getPassword());
        loginCredentialProactive.setPrivateKey(proactiveLoginCredential.getPrivateKey());

        return loginCredentialProactive;
    }

    private NodeProperties convertProactiveNodeProperties(org.activeeon.morphemic.model.NodeProperties proactiveNodeProperties) {
        NodeProperties nodePropertiesProactive = new NodeProperties();
        nodePropertiesProactive.setDisk(proactiveNodeProperties.getDisk());
        nodePropertiesProactive.setMemory(proactiveNodeProperties.getMemory());
        nodePropertiesProactive.setNumberOfCores(proactiveNodeProperties.getNumberOfCores());
        nodePropertiesProactive.setProviderId(proactiveNodeProperties.getProviderId());


        OperatingSystem operatingSystem = new OperatingSystem();
        operatingSystem.setOperatingSystemArchitecture(OperatingSystemArchitecture.valueOf(Objects.requireNonNull(proactiveNodeProperties.getOperatingSystem().getOperatingSystemArchitecture()).name()));
        operatingSystem.setOperatingSystemFamily(OperatingSystemFamily.valueOf(Objects.requireNonNull(proactiveNodeProperties.getOperatingSystem().getOperatingSystemFamily()).name()));
        operatingSystem.setOperatingSystemVersion(proactiveNodeProperties.getOperatingSystem().getOperatingSystemVersion());
        nodePropertiesProactive.setOperatingSystem(operatingSystem);

        return nodePropertiesProactive;
    }
}
