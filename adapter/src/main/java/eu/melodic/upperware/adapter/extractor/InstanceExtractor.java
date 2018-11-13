package eu.melodic.upperware.adapter.extractor;

import camel.deployment.DeploymentInstanceModel;
import eu.melodic.security.authorization.client.extractor.DataExtractor;
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
		return deploymentModel.getVmInstances().size();
    }

    @Override
    public Map<String,Integer> getValueMap(DeploymentInstanceModel deploymentModel) {
        return null;
    }
}
