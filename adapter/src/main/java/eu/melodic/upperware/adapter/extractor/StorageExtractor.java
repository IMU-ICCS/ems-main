package eu.melodic.upperware.adapter.extractor;

import camel.deployment.DeploymentInstanceModel;
import eu.melodic.security.authorization.client.extractor.*;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class StorageExtractor implements DataExtractor<DeploymentInstanceModel,Double> {
    @Override
    public String getKey() {
        return "total-storage";
    }

    @Override
    public Double getValue(DeploymentInstanceModel deploymentModel) {
        return 1.0;
    }

    @Override
    public Map<String,Double> getValueMap(DeploymentInstanceModel deploymentModel) {
        return null;
    }
}
