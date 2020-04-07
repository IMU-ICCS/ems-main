package eu.melodic.upperware.mcts_solver.solver.mcts;

import cp_wrapper.solution.CpSolution;
import eu.melodic.upperware.mcts_solver.solver.mcts.cp_wrapper.MCTSWrapper;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.*;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.memory_management.MemoryLimiter;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.*;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.NodeStatisticsImpl;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.memory_management.MemoryLimiterImpl;
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

    public MCTSSolver(double selectorCoefficient, double explorationCoefficient, int iterations, int nodeCountLimit, MCTSWrapper mctsWrapper) {
        this.selectorCoefficient = selectorCoefficient;
        this.explorationCoefficient = explorationCoefficient;
        this.iterations = iterations;
        this.nodeCountLimit = nodeCountLimit;
        this.mctsWrapper = mctsWrapper;
    }

    public CpSolution solve() {
        Solution solution = search();
        return new CpSolution(mctsWrapper.assignmentToVariableValueDTOList(solution.getAssignment()), solution.getUtility());
    }

    public Solution search() {
        NodeStatisticsImpl.setExplorationCoefficient(explorationCoefficient);
        NodeStatisticsImpl.setSelectorCoefficient(selectorCoefficient);
        NodeStatisticsImpl.setMaximalDepth(mctsWrapper.getSize());

        Tree mctsTree = new TreeImpl(
                new RandomPolicyImpl(mctsWrapper),
                new MoveProviderImpl(mctsWrapper),
                new MemoryLimiterImpl(nodeCountLimit));

        Solution solution = mctsTree.run(iterations);

        log.info("Found solution with utility: {}. Values: {}.", solution.getUtility(), solution.getAssignment().toString());

        return solution;
    }
}
