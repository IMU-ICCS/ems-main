package eu.melodic.upperware.adapter.extractor;

import camel.deployment.DeploymentInstanceModel;
import eu.melodic.security.authorization.client.extractor.*;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class RamExtractor implements DataExtractor<DeploymentInstanceModel,Integer> {
    @Override
    public String getKey() {
        return "total-ram";
    }

    @Override
    public Integer getValue(DeploymentInstanceModel deploymentModel) {
        return 1;
    }

    @Override
    public Map<String,Integer> getValueMap(DeploymentInstanceModel deploymentModel) {
        return null;
    }
}
