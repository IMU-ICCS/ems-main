package eu.melodic.upperware.mcts_solver.solver.mcts;

import eu.melodic.upperware.cp_wrapper.solution.CpSolution;
import eu.melodic.upperware.mcts_solver.solver.mcts.cp_wrapper.MCTSWrapper;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.*;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.MemoryLimiterImpl;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.MoveProviderImpl;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.NodeStatisticsImpl;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.TreeImpl;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.policy.AvailablePolicies;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MCTSSingleTreeSolver {
    @Setter
    private double selectorCoefficient;
    private double explorationCoefficient;
    private int iterations;
    @Getter
    private MCTSWrapper mctsWrapper;
    private MoveProvider moveProvider;
    private Policy policy;
    private MemoryLimiter memoryLimiter;
    @Getter
    private Tree mctsTree;


    public MCTSSingleTreeSolver(double selectorCoefficient, double explorationCoefficient, int iterations, int nodeCountLimit, MCTSWrapper mctsWrapper, AvailablePolicies policy) {
        this.selectorCoefficient = selectorCoefficient;
        this.explorationCoefficient = explorationCoefficient;
        this.iterations = iterations;
        this.mctsWrapper = mctsWrapper;
        this.memoryLimiter = new MemoryLimiterImpl(nodeCountLimit);
        moveProvider = new MoveProviderImpl(mctsWrapper, memoryLimiter);
        this.policy = mctsWrapper.createPolicy(policy);
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
