package eu.melodic.upperware.mcts_solver.solver.mcts_tree;

import lombok.Getter;

@Getter
public abstract class NodeStatistics {
    protected int visitCount;
    protected int depth;

    public NodeStatistics(int parentDepth) {
        this.visitCount = 0;
        this.depth = parentDepth + 1;
    }

    // Updates statistics after finding some path (solution).
    public abstract void update(Solution solution);

    void visit() {
        this.visitCount++;
    }

    public abstract double getEvaluation(NodeStatistics parentStats);
}
