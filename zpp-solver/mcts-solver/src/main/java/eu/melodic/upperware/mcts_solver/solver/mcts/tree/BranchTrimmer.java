package eu.melodic.upperware.mcts_solver.solver.mcts.tree;

public interface BranchTrimmer {
    void trimNodesInPath(Node leaf);
}
