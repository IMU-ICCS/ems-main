package eu.paasage.upperware.profiler.generator.service.camel;

import eu.melodic.cloudiator.client.model.NodeCandidate;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;

/**
 * Created by pszkup on 12.12.17.
 */
public interface NodeCandidatesService {

    Map<String, List<NodeCandidate>> groupByProviders(List<NodeCandidate> nodeCandidates);


    @Deprecated
    Pair<Integer, Integer> getRangeForProviders(List<NodeCandidate> nodeCandidates);

    Pair<Long, Long> getRangeForRam(List<NodeCandidate> nodeCandidates);

    Pair<Float, Float> getRangeForStorage(List<NodeCandidate> nodeCandidates);

    Pair<Integer, Integer> getRangeForCores(List<NodeCandidate> nodeCandidates);

    Pair<Integer, Integer> getRangeForOs(List<NodeCandidate> nodeCandidates);


    List<Integer> getValuesForProviders(Map<Integer, List<NodeCandidate>> nodeCandidatesMap);

    List<Long> getValuesForRam(Map<Integer, List<NodeCandidate>> nodeCandidates);

    List<Float> getValuesForStorage(Map<Integer, List<NodeCandidate>> nodeCandidatesMap);

    List<Integer> getValuesForCores(Map<Integer, List<NodeCandidate>> nodeCandidatesMap);

    List<Integer> getValuesForOsFamily(Map<Integer, List<NodeCandidate>> nodeCandidatesMap);

    @Deprecated
    List<Long> getValuesForRam(List<NodeCandidate> nodeCandidates);

    @Deprecated
    List<Float> getValuesForStorage(List<NodeCandidate> nodeCandidates);

    @Deprecated
    List<Integer> getValuesForCores(List<NodeCandidate> nodeCandidates);
}
