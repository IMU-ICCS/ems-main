package eu.melodic.upperware.adapter.extractor;

import camel.deployment.DeploymentInstanceModel;
import eu.melodic.security.authorization.client.extractor.DataExtractor;
import java.util.Map;

import org.ow2.proactive.sal.model.NodeCandidate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RamExtractor extends NodeCandidateSupport implements DataExtractor<DeploymentInstanceModel,Long> {
    @Override
    public String getKey() {
        return "total-ram";
    }

    @Override
    public Long getValue(DeploymentInstanceModel deploymentModel) {
        Map<String, NodeCandidate> nodeCandidateForDeployment = getNodeCandidateForDeployment(deploymentModel);
        return nodeCandidateForDeployment
                .values()
                .stream()
                .mapToLong(value -> value.getHardware().getRam())
                .sum();
    }

    @Override
    public Map<String,Long> getValueMap(DeploymentInstanceModel deploymentModel) {
        return null;
    }
}
