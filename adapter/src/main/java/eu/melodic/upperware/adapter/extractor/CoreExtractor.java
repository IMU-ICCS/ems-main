package eu.melodic.upperware.adapter.extractor;

import camel.deployment.DeploymentInstanceModel;
import eu.melodic.security.authorization.client.extractor.DataExtractor;
import io.github.cloudiator.rest.model.NodeCandidate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CoreExtractor extends NodeCandidateSupport implements DataExtractor<DeploymentInstanceModel,Integer> {

    @Override
    public String getKey() {
        return "total-number-of-cores";
    }

    @Override
    public Integer getValue(DeploymentInstanceModel deploymentModel) {
        Map<String, NodeCandidate> nodeCandidateForDeployment = getNodeCandidateForDeployment(deploymentModel);
        return nodeCandidateForDeployment
                .values()
                .stream()
                .mapToInt(value -> value.getHardware().getCores())
                .sum();
    }

    @Override
    public Map<String,Integer> getValueMap(DeploymentInstanceModel deploymentModel) {
        return null;
    }
}
