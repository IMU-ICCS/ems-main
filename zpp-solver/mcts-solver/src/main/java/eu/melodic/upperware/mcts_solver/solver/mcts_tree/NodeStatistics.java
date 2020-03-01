package eu.melodic.upperware.mcts_solver.solver.mcts_tree;

import eu.melodic.upperware.mcts_solver.solver.mcts_tree_impl.SolutionCP;
import lombok.Getter;

public abstract class NodeStatistics {
    @Getter
    protected int visitCount;
    @Getter
    protected int depth;

    public NodeStatistics(NodeStatistics parentStatistics) {
        visitCount = 0;

        if (parentStatistics == null) {
            depth = 0;
        }
        else {
            depth = parentStatistics.depth + 1;
        }
    }

    public abstract void update(SolutionCP solution);

    public void visit() {
        visitCount++;
    }
}
