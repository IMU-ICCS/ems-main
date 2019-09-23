package eu.melodic.cache;

import com.google.gson.Gson;
import eu.melodic.cache.exception.CacheException;
import io.github.cloudiator.rest.model.NodeCandidate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static io.github.cloudiator.rest.model.NodeCandidate.NodeCandidateTypeEnum.FAAS;
import static io.github.cloudiator.rest.model.NodeCandidate.NodeCandidateTypeEnum.IAAS;

/**
 * Created by pszkup on 04.01.18.
 */
@Slf4j
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NodeCandidates implements Serializable {

    private static final BinaryOperator<NodeCandidate> IAAS_NODE_CANDIDATE_SORT_OPERATOR = (a, b) -> a.getPrice() < b.getPrice() ? a : b;
    private static final BinaryOperator<NodeCandidate> FAAS_NODE_CANDIDATE_SORT_OPERATOR = (a, b) -> a.getPricePerInvocation() < b.getPricePerInvocation() ? a : b;

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
        log.debug("Looking for cheapest nodeCandidates - vmName: {}, providerIndex: {}, predicates: {}", vmName, providerIndex, predicates);

        List<NodeCandidate> nodeCandidates = get(vmName, providerIndex, predicates);

        if (CollectionUtils.isEmpty(nodeCandidates)) {
            throw new CacheException(String.format("Could not find matching NodeCandidates for vmName: %s, providerIndex: %s, predicates: %s",
                    vmName, providerIndex, predicates));
        }

        boolean iaasOnly = checkIfOnlyVmTypes(nodeCandidates, IAAS);
        boolean faasOnly = checkIfOnlyVmTypes(nodeCandidates, FAAS);

        BinaryOperator<NodeCandidate> nodeCandidateSortOperator;
        if (iaasOnly && !faasOnly) {
            nodeCandidateSortOperator = IAAS_NODE_CANDIDATE_SORT_OPERATOR;
        } else if (faasOnly && !iaasOnly) {
            nodeCandidateSortOperator = FAAS_NODE_CANDIDATE_SORT_OPERATOR;
        } else {
            throw new CacheException("List contains both FAAS and IAAS NodeCandidates");
        }

        Optional<NodeCandidate> cheapest = get(vmName, providerIndex, predicates).stream().reduce(nodeCandidateSortOperator);

        if (cheapest.isPresent()) {
            log.debug("Cheapest NodeCandidate found!!! {}", new Gson().toJson(cheapest.get()));
        } else {
            log.debug("Cheapest nodeCandidate not found!!!");
        }
        return cheapest;
    }

    private boolean checkIfOnlyVmTypes(List<NodeCandidate> nodeCandidates, NodeCandidate.NodeCandidateTypeEnum ncType) {
        return nodeCandidates.stream().allMatch(nodeCandidate -> ncType.equals(nodeCandidate.getNodeCandidateType()));
    }

    private Predicate<NodeCandidate> createComposedPredicate(Predicate<NodeCandidate>... predicates) {

        if (ArrayUtils.isEmpty(predicates)) {
            return nodeCandidate -> true;
        }

        Predicate<NodeCandidate> result = null;
        for (Predicate<NodeCandidate> predicate : predicates) {
            result = (result == null) ? predicate : result.and(predicate);
        }
        return result;
    }

}
