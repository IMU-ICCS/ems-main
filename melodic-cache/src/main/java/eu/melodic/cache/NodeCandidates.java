package eu.melodic.cache;

import eu.melodic.cache.exception.CacheException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.ow2.proactive.sal.model.NodeCandidate;

import java.io.Serializable;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.ow2.proactive.sal.model.NodeCandidate.NodeCandidateTypeEnum.*;

/**
 * Created by pszkup on 04.01.18.
 */
@Slf4j
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NodeCandidates implements Serializable {

    private static final BinaryOperator<NodeCandidate> IAAS_BYON_NODE_CANDIDATE_SORT_OPERATOR = (a, b) -> a.getPrice() < b.getPrice() ? a : b;
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
            log.debug("Could not find NodeCandidates...");
            return Optional.empty();
        }

        //boolean iaasOrByonOnly = checkIfOnlyVmTypes(nodeCandidates, IAAS) || checkIfOnlyVmTypes(nodeCandidates, BYON);
        boolean iaasOrByonOnly = checkIfOnlyNodeCandidateTypes(nodeCandidates, Arrays.asList(IAAS, BYON, EDGE));
        log.info("NodeCandidates->getCheapest: iaasOrByonOnly: {}", iaasOrByonOnly);
        boolean faasOnly = checkIfOnlyVmTypes(nodeCandidates, FAAS);

        BinaryOperator<NodeCandidate> nodeCandidateSortOperator;
        if (iaasOrByonOnly && !faasOnly) {
            nodeCandidateSortOperator = IAAS_BYON_NODE_CANDIDATE_SORT_OPERATOR;
        } else if (faasOnly && !iaasOrByonOnly) {
            nodeCandidateSortOperator = FAAS_NODE_CANDIDATE_SORT_OPERATOR;
        } else {
            throw new CacheException("List contains both FAAS and IAAS NodeCandidates");
        }

        Optional<NodeCandidate> cheapest = get(vmName, providerIndex, predicates).stream().reduce(nodeCandidateSortOperator);

        if (cheapest.isPresent()) {
            log.debug("Cheapest NodeCandidate found!!! {}", cheapest.get().toString());
        } else {
            log.debug("Cheapest nodeCandidate not found!!!");
        }
        return cheapest;
    }

    private boolean checkIfOnlyVmTypes(List<NodeCandidate> nodeCandidates, NodeCandidate.NodeCandidateTypeEnum ncType) {
        return nodeCandidates.stream().allMatch(nodeCandidate -> ncType.equals(nodeCandidate.getNodeCandidateType()));
    }

    private boolean checkIfOnlyNodeCandidateTypes(List<NodeCandidate> nodeCandidates, List<NodeCandidate.NodeCandidateTypeEnum> ncTypes) {
        log.info("NodeCandidates->checkIfOnlyNodeCandidateTypes: nodeCandidates: {}, ncTypes: {}", nodeCandidates, ncTypes);
        return nodeCandidates.stream().allMatch(nodeCandidate -> ncTypes.stream()
                .anyMatch(nodeCandidateTypeEnum -> nodeCandidateTypeEnum.equals(nodeCandidate.getNodeCandidateType())));
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
