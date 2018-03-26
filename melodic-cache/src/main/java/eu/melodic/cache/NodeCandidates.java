package eu.melodic.cache;

import com.google.gson.Gson;
import io.github.cloudiator.rest.model.NodeCandidate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by pszkup on 04.01.18.
 */
@Slf4j
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NodeCandidates implements Serializable {

    //Map<VMName, Map<ProviderIndex, List<NodeCandidate>>>
    private Map<String, Map<Integer, List<NodeCandidate>>> candidates;

    public static NodeCandidates of(Map<String, Map<Integer, List<NodeCandidate>>> candidates){
        return new NodeCandidates(candidates);
    }

    public Map<String, Map<Integer, List<NodeCandidate>>> get(){
        return MapUtils.emptyIfNull(candidates);
    }

    public Map<Integer, List<NodeCandidate>> get(String vmName) {
        return get().getOrDefault(vmName, Collections.emptyMap());
    }

    public List<NodeCandidate> get(String vmName, int providerIndex) {
        return get(vmName).getOrDefault(providerIndex, Collections.emptyList());
    }

    public List<NodeCandidate> get(String vmName, int providerIndex, Predicate<NodeCandidate>... predicates) {
        return get(vmName, providerIndex).stream().filter(createComposedPredicate(predicates)).collect(Collectors.toList());
    }

    public Optional<NodeCandidate> getCheapest(String vmName, int providerIndex, Predicate<NodeCandidate>... predicates) {
        log.info("Looking for cheapest nodeCandidates - vmName: {}, providerIndex: {}, predicates: {}", vmName, providerIndex, predicates);
        Optional<NodeCandidate> cheapest = get(vmName, providerIndex, predicates).stream().reduce((a, b) -> a.getPrice() < b.getPrice() ? a : b);

        if (cheapest.isPresent()) {
            NodeCandidate nodeCandidate = cheapest.get();
            log.info("Cheapest NodeCandidate found!!! {}", new Gson().toJson(nodeCandidate));
        } else {
            log.info("Cheapest nodeCandidate not found!!!");
        }
        return cheapest;
    }

    private Predicate<NodeCandidate> createComposedPredicate(Predicate<NodeCandidate>... predicates) {

        if (ArrayUtils.isEmpty(predicates)) {
            return nodeCandidate -> true;
        }

        Predicate<NodeCandidate> result = null;
        for (Predicate<NodeCandidate> predicate : predicates) {
            if (result == null) {
                result = predicate;
            } else {
                result = result.and(predicate);
            }
        }
        return result;
    }

}
