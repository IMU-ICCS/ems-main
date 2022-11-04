package eu.paasage.upperware.profiler.generator.communication;

import org.activeeon.morphemic.model.NodeCandidate;
import org.activeeon.morphemic.model.Requirement;

import java.util.List;

public interface ProactiveClientServiceForGenerator {
    List<NodeCandidate> findNodeCandidates(List<Requirement> requirements);
}
