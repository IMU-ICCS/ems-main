package eu.melodic.upperware.mcts_solver.solver.utils;

import cp_wrapper.CPWrapper;
import cp_wrapper.utils.DomainHandler;
import cp_wrapper.utils.numeric_value.implementations.IntegerValue;
import cp_wrapper.utils.numeric_value.implementations.LongValue;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableDTO;
import eu.paasage.upperware.metamodel.cp.Domain;
import eu.paasage.upperware.metamodel.cp.VariableType;
import io.github.cloudiator.rest.model.NodeCandidate;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

public class NodeCandidatesProvider {
    private Map<String, NodeCandidates> componentToNodeCandidates = new HashMap<>();

    public NodeCandidatesProvider(NodeCandidates allCandidates, Collection<VariableDTO> variables, CPWrapper cpWrapper) {
        variables.stream().map(VariableDTO::getComponentId)
                .distinct()
                .forEach(componentId -> componentToNodeCandidates.put(componentId, getNodeCandidatesForComponent(componentId, cpWrapper, variables, allCandidates)));
    }

    public NodeCandidates getNodeCandidates(String componentId) {
        return componentToNodeCandidates.get(componentId);
    }

    private NodeCandidates getNodeCandidatesForComponent(String componentId, CPWrapper cpWrapper, Collection<VariableDTO> variables, NodeCandidates allCandidates) {
        Map<String, Map<Integer, List<NodeCandidate>>> candidates = new HashMap<String, Map<Integer, List<NodeCandidate>>>() {{
            put(componentId, new HashMap<>());
        }};
        allCandidates.get().get(componentId).forEach((provider, nodes) -> {
           if (providerIsInDomain(provider, cpWrapper.getVariableDomain(cpWrapper.getVariableIndexFromComponentAndType(componentId, VariableType.PROVIDER)))) {
               candidates.get(componentId)
                       .put(provider, nodes.stream().filter(node -> candidateIsInDomain(node, cpWrapper, variables, componentId)).collect(Collectors.toList()));
           }
        });
        return NodeCandidates.of(candidates);
    }

    private boolean candidateIsInDomain(NodeCandidate nodeCandidate, CPWrapper cpWrapper, Collection<VariableDTO> variables, String componentId) {
       return variables.stream().filter(variable -> variable.getComponentId().equals(componentId))
               .map(variable -> candidateIsInDomainOfVariable(variable.getType(), cpWrapper.getVariableDomain(cpWrapper.getVariableIndexFromComponentAndType(componentId, variable.getType())), nodeCandidate))
               .reduce(Boolean::logicalAnd).orElse(true);
    }

    private boolean providerIsInDomain(int provider, Domain domain) {
        return DomainHandler.isInDomain(new IntegerValue(provider), domain);
    }

    private boolean candidateIsInDomainOfVariable(VariableType type, Domain domain, NodeCandidate nodeCandidate) {
        if (type == VariableType.CARDINALITY || type == VariableType.PROVIDER) {
            return true;
        } else if (isLocationType(type) && (nodeCandidate.getLocation() == null || nodeCandidate.getLocation().getGeoLocation() == null)) {
            return false;
        } else {
            return DomainHandler.isInDomain( new LongValue(VariableExtractor.getVariableValue(type, nodeCandidate)),domain);
        }
    }

    private boolean isLocationType(VariableType type) {
        return type == VariableType.LATITUDE || type == VariableType.LONGITUDE;
    }
}
