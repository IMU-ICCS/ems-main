package eu.melodic.upperware.mcts_solver.solver.mcts_tree_impl;

import eu.melodic.upperware.mcts_solver.solver.mcts_tree.*;

public class TreeImpl extends Tree {

    public TreeImpl(Policy policy, MoveProvider moveProvider) {
        super(policy, moveProvider);
        this.root = new NodeImpl();
    }
}
