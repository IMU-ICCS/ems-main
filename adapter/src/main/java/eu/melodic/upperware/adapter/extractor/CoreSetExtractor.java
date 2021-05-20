package eu.melodic.upperware.adapter.extractor;

import camel.deployment.DeploymentInstanceModel;
import eu.melodic.security.authorization.client.extractor.DataExtractor;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.activeeon.morphemic.model.NodeCandidate;
import org.springframework.stereotype.Service;

@Service
public class CoreSetExtractor extends NodeCandidateSupport implements DataExtractor<DeploymentInstanceModel,Set<Integer>> {

    @Override
    public String getKey() {
        return "set-of-cores";
    }

    @Override
    public Set<Integer> getValue(DeploymentInstanceModel deploymentModel) {
        Map<String, NodeCandidate> nodeCandidateForDeployment = getNodeCandidateForDeployment(deploymentModel);
        return nodeCandidateForDeployment
                .values()
                .stream()
                .map(value -> value.getHardware().getCores())
                .collect(Collectors.toSet());
    }

    @Override
    public Map<String,Set<Integer>> getValueMap(DeploymentInstanceModel deploymentModel) {
        return null;
    }
}
