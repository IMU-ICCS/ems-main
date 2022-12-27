package eu.melodic.upperware.guibackend.domain.converter;

import eu.passage.upperware.commons.model.internal.*;
import lombok.extern.slf4j.Slf4j;
import org.ow2.proactive.sal.model.Deployment;
import org.springframework.lang.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
public class ProactiveNodeConverter implements GenericConverter<Deployment, Node> {
    @Override
    public Node createDomain(@NonNull Deployment external) {
        log.info("ProactiveNodeConverter->createDomain Deployment external: {}", external);
        return Node.builder()
                .name(external.getNodeName())
                .id(external.getInstanceId())
                .nodeProperties(NodeProperties.builder().providerId(external.getPaCloud().getCloudProviderName()).build())
                .originId(external.getNode().getNodeCandidate().getLocation().getName() + "/"+external.getPaCloud().getCloudProviderName())
                .state(decideOnNodeState(external))
                .diagnostic(createNodeInfo(external))
                .ipAddresses(createIpAddress(external))
                .build();
    }

    private List<IpAddress> createIpAddress(Deployment external) {
        if(Objects.isNull(external.getIpAddress())) {
            return Collections.singletonList(IpAddress.builder()
                    .ipAddressType(IpAddressType.PUBLIC_IP)
                    .ipVersion(IpVersion.V4)
                    .value("-.-.-.-")
                    .build());
        }
        return Collections.singletonList(IpAddress.builder()
                .ipAddressType(IpAddressType.valueOf(external.getIpAddress().getIpAddressType().name()))
                .ipVersion(IpVersion.valueOf(external.getIpAddress().getIpVersion().name()))
                .value(external.getIpAddress().getValue())
                .build());
    }

    private String createNodeInfo(Deployment external) {
        return "CloudID: " +
                external.getPaCloud().getCloudID() +
                " | HardwareProviderId: " +
                external.getNode().getNodeCandidate().getHardware().getProviderId() +
                " | ImageProviderId: " +
                external.getNode().getNodeCandidate().getImage().getProviderId() +
                " | LocationName: " +
                external.getNode().getNodeCandidate().getLocation().getName() +
                " | NodeAccessToken: " +
                external.getNodeAccessToken();
    }

    private NodeState decideOnNodeState(Deployment external) {
        if(external.getIsDeployed()) {
            return NodeState.RUNNING;
        }
        return NodeState.UNKNOWN;
    }

    @Override
    public Deployment createExternal(@NonNull Node domain) {
        return null;
    }
}
