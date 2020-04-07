package eu.melodic.upperware.mcts_solver.solver.mcts.tree.memory_management;

import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Node;

public interface MemoryLimiter {
    void prune(); // Checks if tree node limit is exceeded and prunes tree if needed. Returns pruned node or null if no nodes were pruned.
    void updateRecentlyAccessedNodes(Node startingNode, int numberOfAddedNodes); // Marks node as recently accessed.
}
