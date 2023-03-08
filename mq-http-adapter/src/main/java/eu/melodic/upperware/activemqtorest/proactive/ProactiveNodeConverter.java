package eu.melodic.upperware.activemqtorest.proactive;

import eu.passage.upperware.commons.model.internal.*;
import lombok.extern.slf4j.Slf4j;
import org.ow2.proactive.sal.model.Deployment;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProactiveNodeConverter {

    public List<Node> createNodes(List<Deployment> deployments) {
        return deployments.stream()
                .map(this::createNode)
                .collect(Collectors.toList());
    }

    public Node createNode(Deployment deployment) {
        log.info("ProactiveNodeConverter->createNode deployment: {}", deployment);
        return Node.builder()
                .name(deployment.getNodeName())
                .id(deployment.getInstanceId())
                .nodeProperties(NodeProperties.builder().providerId(deployment.getPaCloud().getCloudProviderName()).build())
                .originId(deployment.getNode().getNodeCandidate().getLocation().getName()+"/"+deployment.getPaCloud().getCloudProviderName())
                .state(decideOnNodeState(deployment))
                .diagnostic(createNodeInfo(deployment))
                .ipAddresses(createIpAddress(deployment))
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

}
