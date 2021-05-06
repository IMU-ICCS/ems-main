package eu.melodic.upperware.adapter.extractor;

import camel.deployment.DeploymentInstanceModel;
import eu.melodic.security.authorization.client.extractor.DataExtractor;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.activeeon.morphemic.model.NodeCandidate;
import org.springframework.stereotype.Service;

@Service
public class StorageSetExtractor extends NodeCandidateSupport implements DataExtractor<DeploymentInstanceModel,Set<Double>> {

    @Override
    public String getKey() {
        return "set-of-storage";
    }

    @Override
    public Set<Double> getValue(DeploymentInstanceModel deploymentModel) {
        Map<String, NodeCandidate> nodeCandidateForDeployment = getNodeCandidateForDeployment(deploymentModel);
        return nodeCandidateForDeployment
                .values()
                .stream()
                .map(value -> value.getHardware().getDisk())
                .collect(Collectors.toSet());
    }

    @Override
    public Map<String,Set<Double>> getValueMap(DeploymentInstanceModel deploymentModel) {
        return null;
    }
}
