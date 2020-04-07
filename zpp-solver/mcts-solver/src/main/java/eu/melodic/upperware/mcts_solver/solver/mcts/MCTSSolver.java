package eu.melodic.upperware.mcts_solver.solver.mcts;

import cp_wrapper.solution.CpSolution;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.mcts_solver.solver.mcts.cp_wrapper.MCTSWrapper;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.*;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.memory_management.MemoryLimiter;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.*;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.NodeStatisticsImpl;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.memory_management.MemoryLimiterImpl;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.policy.AvailablePolicies;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.policy.CheapestPolicyImpl;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.policy.RandomPolicyImpl;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MCTSSolver {
    @Setter
    private double selectorCoefficient;
    private double explorationCoefficient;
    private int iterations;
    private int nodeCountLimit;
    private MCTSWrapper mctsWrapper;
    private MoveProvider moveProvider;
    private Policy policy;
    private MemoryLimiter memoryLimiter;
    private Tree mctsTree;

    public MCTSSolver(double selectorCoefficient, double explorationCoefficient, int iterations, int nodeCountLimit, MCTSWrapper mctsWrapper, AvailablePolicies policy) {
        this.selectorCoefficient = selectorCoefficient;
        this.explorationCoefficient = explorationCoefficient;
        this.iterations = iterations;
        this.nodeCountLimit = nodeCountLimit;
        this.mctsWrapper = mctsWrapper;
        moveProvider = new MoveProviderImpl(mctsWrapper);
        this.policy = mctsWrapper.createPolicy(policy);
        this.memoryLimiter = new MemoryLimiterImpl(nodeCountLimit);
        updateParameters();
        mctsTree = new TreeImpl(this.policy, moveProvider, memoryLimiter);
    }

    public CpSolution solve() {
        Solution solution = search();
        return new CpSolution(mctsWrapper.assignmentToVariableValueDTOList(solution.getAssignment()), solution.getUtility());
    }

    public Solution search() {
        updateParameters();
        Solution solution = mctsTree.run(iterations);

        log.info("Found solution with utility: {}. Values: {}.", solution.getUtility(), solution.getAssignment().toString());

        return solution;
    }

    private void updateParameters() {
        NodeStatisticsImpl.setExplorationCoefficient(explorationCoefficient);
        NodeStatisticsImpl.setSelectorCoefficient(selectorCoefficient);
        NodeStatisticsImpl.setMaximalDepth(mctsWrapper.getSize());
    }
}
