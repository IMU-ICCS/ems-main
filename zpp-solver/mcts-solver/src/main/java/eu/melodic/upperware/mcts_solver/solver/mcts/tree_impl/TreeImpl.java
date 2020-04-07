package eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl;

import eu.melodic.upperware.mcts_solver.solver.mcts.tree.*;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.memory_management.MemoryLimiter;

public class TreeImpl extends Tree {

    public TreeImpl(Policy policy, MoveProvider moveProvider, MemoryLimiter memoryLimiter) {
        super(policy, moveProvider, memoryLimiter);
        this.root = new NodeImpl();
    }
}
