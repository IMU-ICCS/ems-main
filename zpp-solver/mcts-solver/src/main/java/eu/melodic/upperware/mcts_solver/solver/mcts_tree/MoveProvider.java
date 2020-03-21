package eu.melodic.upperware.mcts_solver.solver.mcts_tree;

import org.javatuples.Pair;

public interface MoveProvider {
    // Method responsible for finding path and expanding tree in an iteration.
    Pair<Node, Path> searchAndExpand(Node root);
}
