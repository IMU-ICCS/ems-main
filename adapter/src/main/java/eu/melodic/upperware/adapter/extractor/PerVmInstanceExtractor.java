package eu.melodic.upperware.adapter.extractor;

import camel.deployment.DeploymentInstanceModel;
import camel.deployment.VMInstance;
import eu.melodic.security.authorization.client.extractor.DataExtractor;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import io.github.cloudiator.rest.model.NodeCandidate;
import org.springframework.stereotype.Service;

@Service
public class PerVmInstanceExtractor extends NodeCandidateSupport implements DataExtractor<DeploymentInstanceModel,Map<String,Integer>> {
    @Override
    public String getKey() {
        return "per-vm-type-instances";
    }

    @Override
    public Map<String,Integer> getValue(DeploymentInstanceModel deploymentModel) {
        Map<String, Integer> vmInstanceCount = new HashMap<>();
		for (VMInstance vmi : deploymentModel.getVmInstances()) {
			String vmType = vmi.getType().getName();
			if (vmInstanceCount.containsKey(vmType)) vmInstanceCount.put(vmType, vmInstanceCount.get(vmType)+1);
			else vmInstanceCount.put(vmType, 1);
		}
        return vmInstanceCount;
    }
	
    @Override
    public Map<String,Map<String,Integer>> getValueMap(DeploymentInstanceModel deploymentModel) {
		return null;
    }
}
