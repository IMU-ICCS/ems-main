package eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl;

import eu.melodic.upperware.mcts_solver.solver.mcts.tree.*;

public class TreeImpl extends Tree {

    public TreeImpl(Policy policy, MoveProvider moveProvider, BranchTrimmer branchTrimmer) {
        super(policy, moveProvider, branchTrimmer);
        this.root = new NodeImpl();
    }
}
