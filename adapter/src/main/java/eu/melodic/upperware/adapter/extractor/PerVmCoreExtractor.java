package eu.melodic.upperware.adapter.extractor;

import io.github.cloudiator.rest.model.NodeCandidate;
import org.springframework.stereotype.Service;

@Service
public class PerVmCoreExtractor extends PerVmAbstractExtractor<Integer> {
    @Override
    public String getKey() {
        return "per-vm-cores";
    }

    @Override
	protected Integer extractInfo(NodeCandidate nodeCandidate) {
		return nodeCandidate.getHardware().getCores();
    }
}
