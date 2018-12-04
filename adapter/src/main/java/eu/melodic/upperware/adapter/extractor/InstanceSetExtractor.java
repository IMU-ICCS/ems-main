package eu.melodic.upperware.adapter.extractor;

import camel.deployment.DeploymentInstanceModel;
import camel.deployment.VMInstance;
import eu.melodic.security.authorization.client.extractor.DataExtractor;
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
        return deploymentModel.getVmInstances()
                .stream()
				.collect(Collectors.groupingBy(VMInstance::getType, Collectors.counting()))
				.values()
				.stream()
				.map(c -> (int)c.longValue())
                .collect(Collectors.toSet());
    }

    @Override
    public Map<String,Set<Integer>> getValueMap(DeploymentInstanceModel deploymentModel) {
        return null;
    }
}
