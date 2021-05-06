package eu.melodic.upperware.adapter.extractor;

import camel.deployment.DeploymentInstanceModel;
import eu.melodic.security.authorization.client.extractor.DataExtractor;
import java.util.Map;
import java.util.stream.Collectors;

import org.activeeon.morphemic.model.NodeCandidate;
import org.springframework.stereotype.Service;

@Service
public abstract class PerVmAbstractExtractor<T> extends NodeCandidateSupport implements DataExtractor<DeploymentInstanceModel,Map<String,T>> {
    @Override
    public Map<String,T> getValue(DeploymentInstanceModel deploymentModel) {
        Map<String, NodeCandidate> nodeCandidateForDeployment = getNodeCandidateForDeployment(deploymentModel);
        return nodeCandidateForDeployment
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
					Map.Entry::getKey,
					entry -> extractInfo(entry.getValue())
				));
    }
	
	protected abstract T extractInfo(NodeCandidate nodeCandidate);

    @Override
    public Map<String,Map<String,T>> getValueMap(DeploymentInstanceModel deploymentModel) {
		return null;
    }
}
