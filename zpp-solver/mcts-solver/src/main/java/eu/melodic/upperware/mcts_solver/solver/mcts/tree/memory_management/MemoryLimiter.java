package eu.melodic.upperware.mcts_solver.solver.mcts.tree.memory_management;

import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Node;

public interface MemoryLimiter {
    /*
      Checks if tree node limit is exceeded and prunes tree if needed.
      Returns pruned node or null if no nodes were pruned.
     */
    void prune();
    // Marks nodes as recently accessed. Goes from bottom to root of tree.
    void updateRecentlyAccessedNodes(Node startingNode, int numberOfAddedNodes);
}
