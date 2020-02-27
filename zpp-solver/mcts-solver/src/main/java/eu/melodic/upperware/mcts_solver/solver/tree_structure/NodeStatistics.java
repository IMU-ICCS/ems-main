package eu.melodic.upperware.mcts_solver.solver.tree_structure;

import lombok.Getter;

//TODO constructor
public class NodeStatistics {
    @Getter
    private double averageFailureDepth;
    @Getter
    private double maximalUtility;
    @Getter
    private int visitCount;
    @Getter
    private int depth;

    public void update(int failureDepth, double utility) {
        //TODO
    }

    public void visit() {
        visitCount++;
    }
}
