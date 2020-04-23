package eu.melodic.upperware.mcts_solver.solver.mcts.tree;

public interface MemoryLimiter {
    // Tells tree whether it should cut branch.
    boolean shouldPruneTree();
    // Tells tree which node to prune.
    Node whichNodeToPrune();
    // Marks nodes as recently accessed. Goes from bottom to root of tree.
    void updateRecentlyAccessedNodes(Node startingNode);
    // Tells memory limiter that count nodes have been removed.
    void decreaseCount(int count);
    // Creates node with certain value.
    Node createNode(int value);
    // Removes node from queue, does nothing if it's not in queue.
    void removeNodeFromQueue(Node node);
}