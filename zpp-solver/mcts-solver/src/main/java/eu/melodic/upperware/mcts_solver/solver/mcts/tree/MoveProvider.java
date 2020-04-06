package eu.melodic.upperware.mcts_solver.solver.mcts.tree;

import org.javatuples.Triplet;

public interface MoveProvider {
    // Method responsible for finding path and expanding tree in an iteration.
    Triplet<Node, Integer, Path> searchAndExpand(Node root);
}
