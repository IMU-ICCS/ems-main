package eu.melodic.upperware.adapter.extractor;

import camel.deployment.DeploymentInstanceModel;
import eu.melodic.security.authorization.client.extractor.DataExtractor;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.ow2.proactive.sal.model.NodeCandidate;
import org.springframework.stereotype.Service;

@Service
public class RamSetExtractor extends NodeCandidateSupport implements DataExtractor<DeploymentInstanceModel,Set<Long>> {
    @Override
    public String getKey() {
        return "set-of-ram";
    }

    @Override
    public Set<Long> getValue(DeploymentInstanceModel deploymentModel) {
        Map<String, NodeCandidate> nodeCandidateForDeployment = getNodeCandidateForDeployment(deploymentModel);
        return nodeCandidateForDeployment
                .values()
                .stream()
                .map(value -> value.getHardware().getRam())
                .collect(Collectors.toSet());
    }

    @Override
    public Map<String,Set<Long>> getValueMap(DeploymentInstanceModel deploymentModel) {
        return null;
    }
}
