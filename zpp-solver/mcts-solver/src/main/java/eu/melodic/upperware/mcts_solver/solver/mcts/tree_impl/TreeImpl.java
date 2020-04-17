package eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl;

import eu.melodic.upperware.mcts_solver.solver.mcts.tree.MemoryLimiter;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.MoveProvider;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Policy;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Tree;

public class TreeImpl extends Tree {

    public TreeImpl(Policy policy, MoveProvider moveProvider, MemoryLimiter memoryLimiter) {
        super(policy, moveProvider, memoryLimiter);
        this.root = new NodeImpl();
    }
}
