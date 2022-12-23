package eu.melodic.upperware.guibackend.service.byon;

import eu.passage.upperware.commons.model.byon.ByonDefinition;
import eu.passage.upperware.commons.model.byon.ByonNode;
import eu.passage.upperware.commons.model.internal.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ByonMapper {
    public org.ow2.proactive.sal.model.ByonDefinition mapByonDefinitionToProactive(ByonDefinition byonDefinitionForNode) {
        org.ow2.proactive.sal.model.ByonDefinition byonDefinitionProactive = new org.ow2.proactive.sal.model.ByonDefinition();
        byonDefinitionProactive.setName(byonDefinitionForNode.getName());
        byonDefinitionProactive.setLoginCredential((convertLoginCredential(byonDefinitionForNode.getLoginCredential())));
        byonDefinitionProactive.setIpAddresses(convertIpAddresses(byonDefinitionForNode.getIpAddresses()));
        byonDefinitionProactive.setNodeProperties(convertNodeProperties(byonDefinitionForNode.getNodeProperties()));

        return byonDefinitionProactive;
    }

    private org.ow2.proactive.sal.model.LoginCredential convertLoginCredential(LoginCredential loginCredential) {
        org.ow2.proactive.sal.model.LoginCredential loginCredentialProactive = new org.ow2.proactive.sal.model.LoginCredential();
        loginCredentialProactive.setUsername(loginCredential.getUsername());
        loginCredentialProactive.setPassword(loginCredential.getPassword());
        loginCredentialProactive.setPrivateKey(loginCredential.getPrivateKey());

        return loginCredentialProactive;
    }

    private List<org.ow2.proactive.sal.model.IpAddress> convertIpAddresses(List<IpAddress> ipAddresses) {
        return ipAddresses.stream()
                .map(this::convertIpAddress)
                .collect(Collectors.toList());
    }

    private org.ow2.proactive.sal.model.IpAddress convertIpAddress(IpAddress ipAddress) {
        org.ow2.proactive.sal.model.IpAddress ipAddressProactive = new org.ow2.proactive.sal.model.IpAddress();
        ipAddressProactive.setIpAddressType(org.ow2.proactive.sal.model.IpAddressType.valueOf(ipAddress.getIpAddressType().name()));
        ipAddressProactive.setIpVersion(org.ow2.proactive.sal.model.IpVersion.valueOf(ipAddress.getIpVersion().name()));
        ipAddressProactive.setValue(ipAddress.getValue());

        return ipAddressProactive;
    }

    private org.ow2.proactive.sal.model.NodeProperties convertNodeProperties(NodeProperties nodeProperties) {
        org.ow2.proactive.sal.model.NodeProperties nodePropertiesProactive = new org.ow2.proactive.sal.model.NodeProperties();
        nodePropertiesProactive.setDisk(nodeProperties.getDisk());
        nodePropertiesProactive.setMemory(nodeProperties.getMemory());
        nodePropertiesProactive.setNumberOfCores(nodeProperties.getNumberOfCores());
        nodePropertiesProactive.setProviderId(nodeProperties.getProviderId());

        org.ow2.proactive.sal.model.GeoLocation geoLocation = new org.ow2.proactive.sal.model.GeoLocation();
        geoLocation.setCity(nodeProperties.getGeoLocation().getCity());
        geoLocation.setCountry(nodeProperties.getGeoLocation().getCountry());
        geoLocation.setLatitude(nodeProperties.getGeoLocation().getLatitude());
        geoLocation.setLongitude(nodeProperties.getGeoLocation().getLongitude());
        nodePropertiesProactive.setGeoLocation(geoLocation);

        org.ow2.proactive.sal.model.OperatingSystem operatingSystem = new org.ow2.proactive.sal.model.OperatingSystem();
        operatingSystem.setOperatingSystemArchitecture(org.ow2.proactive.sal.model.OperatingSystemArchitecture.valueOf(Objects.requireNonNull(nodeProperties.getOperatingSystem().getOperatingSystemArchitecture()).name()));
        operatingSystem.setOperatingSystemFamily(org.ow2.proactive.sal.model.OperatingSystemFamily.valueOf(Objects.requireNonNull(nodeProperties.getOperatingSystem().getOperatingSystemFamily()).name()));
        operatingSystem.setOperatingSystemVersion(nodeProperties.getOperatingSystem().getOperatingSystemVersion());
        nodePropertiesProactive.setOperatingSystem(operatingSystem);

        return nodePropertiesProactive;
    }

    public ByonNode mapProactiveByonNodeToInternal(org.ow2.proactive.sal.model.ByonNode byonNodeProactive) {
        ByonNode byonNode = new ByonNode();
        byonNode.setId(byonNodeProactive.getId());
        byonNode.setAllocated(byonNodeProactive.isAllocated());
        byonNode.setName(byonNodeProactive.getName());
        byonNode.setNodeCandidate(byonNodeProactive.getNodeCandidate().toString());
        byonNode.setReason(byonNodeProactive.getReason());
        byonNode.setDiagnostic(byonNodeProactive.getDiagnostic());
        byonNode.setUserId(byonNodeProactive.getUserId());
        byonNode.setIpAddresses(convertProactiveIpAddresses(byonNodeProactive.getIpAddresses()));
        byonNode.setLoginCredential(convertProactiveLoginCredential(byonNodeProactive.getLoginCredential()));
        byonNode.setNodeProperties(convertProactiveNodeProperties(byonNodeProactive.getNodeProperties()));

        return byonNode;
    }

    private List<IpAddress> convertProactiveIpAddresses(List<org.ow2.proactive.sal.model.IpAddress> proactiveIpAddresses) {
        return proactiveIpAddresses.stream()
                .map(this::convertProactiveIpAddress)
                .collect(Collectors.toList());
    }

    private IpAddress convertProactiveIpAddress(org.ow2.proactive.sal.model.IpAddress proactiveIpAddress) {
        IpAddress ipAddress = new IpAddress();
        ipAddress.setIpAddressType(IpAddressType.valueOf(proactiveIpAddress.getIpAddressType().name()));
        ipAddress.setIpVersion(IpVersion.valueOf(proactiveIpAddress.getIpVersion().name()));
        ipAddress.setValue(proactiveIpAddress.getValue());

        return ipAddress;
    }

    private LoginCredential convertProactiveLoginCredential(org.ow2.proactive.sal.model.LoginCredential proactiveLoginCredential) {
        LoginCredential loginCredentialProactive = new LoginCredential();
        loginCredentialProactive.setUsername(proactiveLoginCredential.getUsername());
        loginCredentialProactive.setPassword(proactiveLoginCredential.getPassword());
        loginCredentialProactive.setPrivateKey(proactiveLoginCredential.getPrivateKey());

        return loginCredentialProactive;
    }

    private NodeProperties convertProactiveNodeProperties(org.ow2.proactive.sal.model.NodeProperties proactiveNodeProperties) {
        NodeProperties nodePropertiesProactive = new NodeProperties();
        nodePropertiesProactive.setDisk(proactiveNodeProperties.getDisk());
        nodePropertiesProactive.setMemory(proactiveNodeProperties.getMemory());
        nodePropertiesProactive.setNumberOfCores(proactiveNodeProperties.getNumberOfCores());
        nodePropertiesProactive.setProviderId(proactiveNodeProperties.getProviderId());

        GeoLocation geoLocation = new GeoLocation();
        geoLocation.setCity(proactiveNodeProperties.getGeoLocation().getCity());
        geoLocation.setCountry(proactiveNodeProperties.getGeoLocation().getCountry());
        geoLocation.setLatitude(proactiveNodeProperties.getGeoLocation().getLatitude());
        geoLocation.setLongitude(proactiveNodeProperties.getGeoLocation().getLongitude());
        nodePropertiesProactive.setGeoLocation(geoLocation);

        OperatingSystem operatingSystem = new OperatingSystem();
        operatingSystem.setOperatingSystemArchitecture(OperatingSystemArchitecture.valueOf(Objects.requireNonNull(proactiveNodeProperties.getOperatingSystem().getOperatingSystemArchitecture()).name()));
        operatingSystem.setOperatingSystemFamily(OperatingSystemFamily.valueOf(Objects.requireNonNull(proactiveNodeProperties.getOperatingSystem().getOperatingSystemFamily()).name()));
        operatingSystem.setOperatingSystemVersion(proactiveNodeProperties.getOperatingSystem().getOperatingSystemVersion());
        nodePropertiesProactive.setOperatingSystem(operatingSystem);

        return nodePropertiesProactive;
    }
}
