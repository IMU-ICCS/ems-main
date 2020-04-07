package eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.memory_management;

import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Node;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.memory_management.Fifo;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.memory_management.MemoryLimiter;

public class MemoryLimiterImpl implements MemoryLimiter {
    private int limit;
    private int count;
    private Fifo accessFifo = new FifoImpl();

    public MemoryLimiterImpl(int limit) {
        this.limit = limit;
    }

    @Override
    public void prune() {
        while (count > limit && !accessFifo.empty()) {
            pruneLeastVisited();
        }
    }

    @Override
    public void updateRecentlyAccessedNodes(Node startingNode, int numberOfAddedNodes) {
        Node current = startingNode;
        this.count += numberOfAddedNodes;

        while (current != null) {
            if (current.isExpanded() && current.getParent() != null) { // If current is not leaf or root.
               updateRecentlyAccessedNode(current);
            }
            current = current.getParent();
        }
    }

    private void updateRecentlyAccessedNode(Node node) {
        accessFifo.pushBack(node);
    }

    private Node pruneLeastVisited() {
        Node toPrune = accessFifo.popFront();

        pruneSubTree(toPrune);
        return toPrune;
    }

    // SubRoot's children should be all leaves.
    private void pruneSubTree(Node subRoot) {
        if (subRoot.getParent() == null) { // If is a root then do nothing.
            return;
        }

        count -= subRoot.getChildren().size();
        subRoot.getChildren().clear();

        subRoot.setUnexpanded();
    }
}
