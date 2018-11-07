package eu.melodic.upperware.adapter.extractor;

import camel.deployment.DeploymentInstanceModel;
import eu.melodic.security.authorization.client.extractor.DataExtractor;
import io.github.cloudiator.rest.model.NodeCandidate;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class InstanceSetExtractor extends NodeCandidateSupport implements DataExtractor<DeploymentInstanceModel,Set<Integer>> {

    @Override
    public String getKey() {
        return "set-of-instances";
    }

    @Override
    public Set<Integer> getValue(DeploymentInstanceModel deploymentModel) {
        Map<String, NodeCandidate> nodeCandidateForDeployment = getNodeCandidateForDeployment(deploymentModel);
        return deploymentModel.getVmInstances()
                .stream()
				.collect(Collectors.groupingBy(vmi -> vmi.getType(), Collectors.counting()))
				.values()
				.stream()
				.map(c -> new Integer((int)c.longValue()))
                .collect(Collectors.toSet());
    }

    @Override
    public Map<String,Set<Integer>> getValueMap(DeploymentInstanceModel deploymentModel) {
        return null;
    }
}
