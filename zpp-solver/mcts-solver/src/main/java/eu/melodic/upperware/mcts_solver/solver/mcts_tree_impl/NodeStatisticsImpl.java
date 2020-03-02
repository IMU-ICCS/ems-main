package eu.melodic.upperware.mcts_solver.solver.mcts_tree_impl;

import eu.melodic.upperware.mcts_solver.solver.mcts_tree.NodeStatistics;
import eu.melodic.upperware.mcts_solver.solver.mcts_tree.Solution;
import lombok.Getter;

public class NodeStatisticsImpl extends NodeStatistics {
    @Getter
    private double averageFailureDepth;
    @Getter
    private double maximalUtility;

    public NodeStatisticsImpl(int parentDepth) {
        super(parentDepth);

        averageFailureDepth = 0.0;
        maximalUtility = 0.0;
    }

    @Override
    public void update(Solution solution) {
        //TODO
    }
}
