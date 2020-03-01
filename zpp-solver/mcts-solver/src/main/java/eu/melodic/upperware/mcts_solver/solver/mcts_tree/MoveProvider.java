package eu.melodic.upperware.mcts_solver.solver.mcts_tree;

import org.javatuples.Pair;

public interface MoveProvider {

    Pair<Node, Path> searchAndExpand(Node root);
}
