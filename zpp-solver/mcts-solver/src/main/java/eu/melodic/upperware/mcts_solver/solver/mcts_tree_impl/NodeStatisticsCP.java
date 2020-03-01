package eu.melodic.upperware.mcts_solver.solver.mcts_tree_impl;

import eu.melodic.upperware.mcts_solver.solver.mcts_tree.NodeStatistics;
import lombok.Getter;

public class NodeStatisticsCP extends NodeStatistics {
    @Getter
    private double averageFailureDepth;
    @Getter
    private double maximalUtility;

    public NodeStatisticsCP(NodeStatistics parentStatistics) {
        super(parentStatistics);

        averageFailureDepth = 0.0;
        maximalUtility = 0.0;
    }

    @Override
    public void update(SolutionCP solution) {
        //TODO
    }
}
