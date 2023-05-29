package eu.melodic.upperware.adapter.extractor;

import camel.deployment.DeploymentInstanceModel;
import eu.melodic.security.authorization.client.extractor.DataExtractor;

import java.util.Map;

import org.ow2.proactive.sal.model.NodeCandidate;
import org.springframework.stereotype.Service;

@Service
public class StorageExtractor extends NodeCandidateSupport implements DataExtractor<DeploymentInstanceModel,Double> {

    @Override
    public String getKey() {
        return "total-storage";
    }

    @Override
    public Double getValue(DeploymentInstanceModel deploymentModel) {
        Map<String, NodeCandidate> nodeCandidateForDeployment = getNodeCandidateForDeployment(deploymentModel);
        return nodeCandidateForDeployment
                .values()
                .stream()
                .mapToDouble(value -> value.getHardware().getDisk())
                .sum();
    }

    @Override
    public Map<String,Double> getValueMap(DeploymentInstanceModel deploymentModel) {
        return null;
    }
}
