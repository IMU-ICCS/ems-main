package eu.melodic.upperware.mcts_solver.solver.tree_structure;

import eu.melodic.upperware.mcts_solver.solver.mcts_cp_wrapper.MCTSWrapper;
import lombok.Getter;

public class NodeStatistics {
    @Getter
    private double averageFailureDepth;
    @Getter
    private double maximalUtility;
    @Getter
    private int visitCount;
    @Getter
    private int depth;
    @Getter
    private int value; // Value is (-1) for a root node.

    public NodeStatistics(NodeStatistics parentStatistics, MCTSWrapper mctsWrapper) {
        averageFailureDepth = 0.0;
        maximalUtility = 0.0;
        visitCount = 0;

        if (parentStatistics == null) {
            depth = 0;
            value = -1;
        }
        else {
            depth = parentStatistics.depth + 1;
            value = mctsWrapper.generateRandomValue(depth);
        }
    }

    public void update(int failureDepth, double utility) {
        //TODO
    }

    public void visit() {
        visitCount++;
    }
}
