package eu.melodic.upperware.mcts_solver.solver.mcts.tree;

public interface MemoryLimiter {
    // Tells tree whether it should cut branch.
    boolean shouldPruneTree();
    // Tells tree which node to prune. Pops a node from queue in process.
    Node popNodeToPrune();
    // Marks nodes as recently accessed. Goes from bottom to root of tree.
    void updateRecentlyAccessedNodes(Node startingNode);
    // Tells memory limiter that count nodes have been removed.
    void decreaseCount(int count);
    // Creates node with certain value.
    Node createNode(int value);
}