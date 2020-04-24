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
    public Node whichNodeToPrune() {
        return accessQueue.getFront();
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
    public Node createNode(Node parent, int value) {
        count++;
        NodeImpl newNode = new NodeImpl(value);
        newNode.linkToTree(parent);
        updateRecentlyAccessedNode(newNode);
        return new NodeImpl(value);
    }

    @Override
    public void removeNodeFromQueue(Node node) {
        count--;
        accessQueue.removeNodeFromQueue((NodeImpl) node);
    }

    // Adds newly created or visited node to queue.
    private void updateRecentlyAccessedNode(Node node) {
        accessQueue.pushBack(node);
    }
}
