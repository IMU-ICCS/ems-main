package eu.melodic.upperware.adapter.extractor;

import camel.deployment.DeploymentInstanceModel;
import io.github.cloudiator.rest.model.NodeCandidate;
import org.springframework.stereotype.Service;

@Service
public class PerVmRamExtractor extends PerVmAbstractExtractor<Long> {
    @Override
    public String getKey() {
        return "per-vm-ram";
    }

    @Override
	protected Long extractInfo(NodeCandidate nodeCandidate) {
		return nodeCandidate.getHardware().getRam();
    }
}
