package eu.melodic.upperware.mcts_solver.solver.mcts.cp_wrapper;

import cp_wrapper.CPWrapper;
import cp_wrapper.utils.numeric_value.NumericValueInterface;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Policy;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.policy.AvailablePolicies;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.policy.CheapestPolicyImpl;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.policy.RandomPolicyImpl;
import eu.melodic.upperware.mcts_solver.solver.utils.NodeCandidatesProvider;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableDTO;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.paasage.upperware.metamodel.cp.VariableType;

import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.Random;

@Slf4j
public class MCTSWrapper{
    private final Random random = new Random();
    private CPWrapper cpWrapper;
    private NodeCandidatesProvider nodeCandidatesProvider;

    public MCTSWrapper(CPWrapper cpWrapper, NodeCandidates nodeCandidates) {
        this.cpWrapper = cpWrapper;
        this.nodeCandidatesProvider = new NodeCandidatesProvider(nodeCandidates, cpWrapper.getVariableDTOCollection(), cpWrapper);
    }

    public MCTSWrapper(CPWrapper cpWrapper) {
        this.cpWrapper = cpWrapper;
        this.nodeCandidatesProvider = null;
    }

    public NodeCandidates getNodeCandidates(String componentId) {
        return nodeCandidatesProvider.getNodeCandidates(componentId);
    }

    // Generates random value for variable indexed with index.
    public int generateRandomValue(int index) {
        return random.nextInt(cpWrapper.getMaxDomainValue(index) - cpWrapper.getMinDomainValue(index) + 1)
                + cpWrapper.getMinDomainValue(index);
    }

    // Calculates utility for certain variables assignment.
    public double getUtility(List<Integer> assignments) {
        log.debug("Evaluating solution {}.", assignments.toString());

        double utility = cpWrapper.getUtility(assignments);
        log.debug("Solution is {}. Utility value: {}.", (cpWrapper.checkIfFeasible(assignments) ? "feasible" : "not feasible"), utility);
        return utility;
    }

    // Returns number of variables.
    public int getSize() {
        return cpWrapper.getVariablesCount();
    }

    public int domainSize(int index) {
        return cpWrapper.getMaxDomainValue(index) - cpWrapper.getMinDomainValue(index) + 1;
    }

    public int getMinDomainValue(int index) {
        return cpWrapper.getMinDomainValue(index);
    }

    public int getMaxDomainValue(int index) {
        return cpWrapper.getMaxDomainValue(index);
    }

    public boolean isFeasible(List<Integer> assignments) {
        return cpWrapper.checkIfFeasible(assignments);
    }

    public List<VariableValueDTO> assignmentToVariableValueDTOList(List<Integer> assignments) {
        return cpWrapper.assignmentToVariableValueDTOList(assignments);
    }

    public int getNumberOfComponents() {
        return (int) cpWrapper.getNumberOfComponents();
    }

    public int getIndexFromValue(NumericValueInterface value, int variable) {
        return cpWrapper.getIndexFromValue(value, variable);
    }

    public int getVariableIndexFromComponentAndType(String componentId, VariableType type) {
        return cpWrapper.getVariableIndexFromComponentAndType(componentId, type);
    }

    public Collection<VariableDTO> getVariableDTOCollection() {
        return cpWrapper.getVariableDTOCollection();
    }

    public Policy createPolicy(AvailablePolicies policyType) {
        switch(policyType) {
            case RANDOM_POLICY:
                return new RandomPolicyImpl(this);
            case CHEAPEST_POLICY:
                return new CheapestPolicyImpl(this);
            default:
                throw new RuntimeException("Unsupported policy type!");
        }
    }
}
