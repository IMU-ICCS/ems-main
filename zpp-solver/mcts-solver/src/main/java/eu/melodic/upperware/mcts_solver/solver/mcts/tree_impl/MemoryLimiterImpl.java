package eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl;

import eu.melodic.upperware.mcts_solver.solver.mcts.tree.MemoryLimiter;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Node;

public class MemoryLimiterImpl implements MemoryLimiter {
    private int limit;
    private int count = 0;
    private Queue accessQueue = new Queue();

    public MemoryLimiterImpl(int limit) {
        this.limit = limit;
    }

    @Override
    public boolean shouldPruneTree() {
        return count > limit && !accessQueue.empty();
    }

    @Override
    public Node popNodeToPrune() {
        return accessQueue.popFront();
    }

    @Override
    public void updateRecentlyAccessedNodes(Node startingNode) {
        Node current = startingNode;

        while (current != null) {
            if (current.isExpanded() && current.getParent() != null) { // If current is not leaf or root.
               updateRecentlyAccessedNode(current);
            }
            current = current.getParent();
        }
    }

    @Override
    public void decreaseCount(int count) {
        this.count -= count;
    }

    @Override
    public Node createNode(int value) {
        count++;
        return new NodeImpl(value);
    }

    @Override
    public void removeNodeFromQueue(Node node) {
        accessQueue.removeNodeFromQueue((NodeImpl) node);
    }

    private void updateRecentlyAccessedNode(Node node) {
        accessQueue.pushBack(node);
    }
}
