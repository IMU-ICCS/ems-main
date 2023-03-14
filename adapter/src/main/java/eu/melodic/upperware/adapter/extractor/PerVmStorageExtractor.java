package eu.melodic.upperware.adapter.extractor;

import org.ow2.proactive.sal.model.NodeCandidate;
import org.springframework.stereotype.Service;

@Service
public class PerVmStorageExtractor extends PerVmAbstractExtractor<Double> {
    @Override
    public String getKey() {
        return "per-vm-storage";
    }

    @Override
	protected Double extractInfo(NodeCandidate nodeCandidate) {
		return nodeCandidate.getHardware().getDisk();
    }
}
