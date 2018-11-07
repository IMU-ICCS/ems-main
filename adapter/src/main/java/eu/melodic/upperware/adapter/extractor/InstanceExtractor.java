package eu.melodic.upperware.adapter.extractor;

import camel.deployment.DeploymentInstanceModel;
import eu.melodic.security.authorization.client.extractor.DataExtractor;
import io.github.cloudiator.rest.model.NodeCandidate;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class InstanceExtractor extends NodeCandidateSupport implements DataExtractor<DeploymentInstanceModel,Integer> {

    @Override
    public String getKey() {
        return "total-number-of-instances";
    }

    @Override
    public Integer getValue(DeploymentInstanceModel deploymentModel) {
        Map<String, NodeCandidate> nodeCandidateForDeployment = getNodeCandidateForDeployment(deploymentModel);
        return (int)deploymentModel.getVmInstances()
                .stream()
                .count();
    }

    @Override
    public Map<String,Integer> getValueMap(DeploymentInstanceModel deploymentModel) {
        return null;
    }
}
