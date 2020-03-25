package eu.melodic.upperware.mcts_solver.solver;

import eu.melodic.upperware.mcts_solver.solver.mcts_cp_wrapper.MCTSWrapper;
import eu.melodic.upperware.mcts_solver.solver.mcts_tree.*;
import eu.melodic.upperware.mcts_solver.solver.mcts_tree_impl.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MCTSSolver {
    private double selectorCoefficient;
    private double explorationCoefficient;
    private int iterations;
    private MCTSWrapper mctsWrapper;

    // Constructor for test purposes.
    public MCTSSolver(double selectorCoefficient, double explorationCoefficient, int iterations, MCTSWrapper mctsWrapper) {
        this.selectorCoefficient = selectorCoefficient;
        this.explorationCoefficient = explorationCoefficient;
        this.iterations = iterations;
        this.mctsWrapper = mctsWrapper;
    }

    // Solve method for test purposes.
    public Solution solve() {
        MoveProvider moveProvider = new MoveProviderImpl(mctsWrapper);
        Policy policy = new RandomPolicyImpl(mctsWrapper);

        NodeStatisticsImpl.setExplorationCoefficient(explorationCoefficient);
        NodeStatisticsImpl.setSelectorCoefficient(selectorCoefficient);
        NodeStatisticsImpl.setMaximalDepth(mctsWrapper.getSize());

        Tree mctsTree = new TreeImpl(policy, moveProvider);

        Solution solution = mctsTree.run(iterations);

        log.info("Found solution with utility: {}. Values: {}.", solution.getUtility(), solution.getAssignment().toString());

        return solution;
    }
}
