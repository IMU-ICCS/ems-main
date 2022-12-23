package eu.paasage.upperware.profiler.generator.communication;

import org.ow2.proactive.sal.model.NodeCandidate;
import org.ow2.proactive.sal.model.Requirement;

import java.util.List;

public interface ProactiveClientServiceForGenerator {
    List<NodeCandidate> findNodeCandidates(List<Requirement> requirements);
}
