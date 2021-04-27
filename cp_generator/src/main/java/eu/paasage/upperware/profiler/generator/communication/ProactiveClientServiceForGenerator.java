package eu.paasage.upperware.profiler.generator.communication;

import eu.passage.upperware.commons.proactive.client.IProactiveClientServiceConnector;
import org.activeeon.morphemic.model.NodeCandidate;
import org.activeeon.morphemic.model.Requirement;

import java.util.List;

public interface ProactiveClientServiceForGenerator extends IProactiveClientServiceConnector {
    List<NodeCandidate> findNodeCandidates(List<Requirement> requirements);
}
