package eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl;

import eu.melodic.upperware.mcts_solver.solver.mcts.tree.MemoryLimiter;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Node;

public class MemoryLimiterImpl implements MemoryLimiter {
    private int limit;
    private int count;
    private Queue accessQueue = new Queue();

    public MemoryLimiterImpl(int limit) {
        this.limit = limit;
    }

    public boolean shouldPruneTree() {
        return count > limit && !accessQueue.empty();
    }

    public Node whichNodeToPrune() {
        return accessQueue.popFront();
    }

    public void updateRecentlyAccessedNodes(Node startingNode) {
        Node current = startingNode;

        while (current != null) {
            if (current.isExpanded() && current.getParent() != null) { // If current is not leaf or root.
               updateRecentlyAccessedNode(current);
            }
            current = current.getParent();
        }
    }

    public void decreaseCount(int count) {
        this.count -= count;
    }

    public Node createNode(int value) {
        count++;
        return new NodeImpl(value);
    }

    private void updateRecentlyAccessedNode(Node node) {
        accessQueue.pushBack(node);
    }
}
