package eu.melodic.upperware.adapter.extractor;

import camel.deployment.DeploymentInstanceModel;
import camel.deployment.VMInstance;
import eu.melodic.security.authorization.client.extractor.DataExtractor;
import java.util.HashMap;
import java.util.Map;
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
			vmInstanceCount.merge(vmType, 1, Integer::sum);
		}
        return vmInstanceCount;
    }
	
    @Override
    public Map<String,Map<String,Integer>> getValueMap(DeploymentInstanceModel deploymentModel) {
		return null;
    }
}
