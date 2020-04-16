package eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.memory_management;

import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Node;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.memory_management.Fifo;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.memory_management.MemoryLimiter;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.NodeImpl;

public class MemoryLimiterImpl implements MemoryLimiter {
    private int limit;
    private int count;
    private Fifo accessFifo = new FifoImpl();

    public MemoryLimiterImpl(int limit) {
        this.limit = limit;
    }

    @Override
    public boolean shouldPruneTree() {
        return count > limit && !accessFifo.empty();
    }

    @Override
    public Node whichNodeToPrune() {
        return accessFifo.popFront();
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
        accessFifo.pushBack(node);
    }
}
