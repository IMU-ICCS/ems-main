package eu.melodic.upperware.mcts_solver.solver.mcts.tree;

public interface BranchTrimmer {
    /* Tries to trim node. */
    Node trimNode(Node current);
}
