package eu.melodic.upperware.mcts_solver.solver.mcts_tree;

import lombok.Getter;

public abstract class NodeStatistics {
    @Getter
    protected int visitCount;
    @Getter
    protected int depth;

    public NodeStatistics(int parentDepth) {
        visitCount = 0;
        depth = parentDepth + 1;
    }

    // Updates statistics after finding some path (solution).
    public abstract void update(Solution solution);

    public void visit() {
        visitCount++;
    }

    public abstract double getAverageFailureDepth();

    public abstract double getMaximalUtility();
}
