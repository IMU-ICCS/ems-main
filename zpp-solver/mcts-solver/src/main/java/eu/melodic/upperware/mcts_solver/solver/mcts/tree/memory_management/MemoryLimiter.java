package eu.melodic.upperware.mcts_solver.solver.mcts.tree.memory_management;

import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Node;

public interface MemoryLimiter {
    int getCount(); // Returns number of nodes in tree.
    void addCount(int count); // Updates current nodes count with 'count'.
    void prune(); // Checks if tree node limit is exceeded and prunes tree if needed. Returns pruned node or null if no nodes were pruned.
    void updateAccessed(Node node); // Marks node as recently accessed.
    boolean fifoFrontIsCorrect();
    Node getFront();
}
