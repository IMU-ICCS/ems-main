package eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.memory_management;

import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Node;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.memory_management.NodeQueue;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.memory_management.MemoryLimiter;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.NodeImpl;

public class MemoryLimiterImpl implements MemoryLimiter {
    private int limit;
    private int count;
    private NodeQueue accessNodeQueue = new NodeQueueImpl();

    public MemoryLimiterImpl(int limit) {
        this.limit = limit;
    }

    @Override
    public boolean shouldPruneTree() {
        return count > limit && !accessNodeQueue.empty();
    }

    @Override
    public Node whichNodeToPrune() {
        return accessNodeQueue.popFront();
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

    private void updateRecentlyAccessedNode(Node node) {
        accessNodeQueue.pushBack(node);
    }
}
