package eu.melodic.upperware.mcts_solver.solver.mcts.tree;

import eu.melodic.upperware.mcts_solver.solver.mcts.tree.memory_management.FifoNodeLinker;

import java.util.List;

public interface Node extends Comparable<Node> {
    NodeStatistics getNodeStatistics();
    int getValue();
    Node getParent();
    List<Node> getChildren();
    void linkToTree(Node parent); // Called after creation of a Node in order to add it to a tree.
    Node update(Solution solution); // Updates node based on solution.
    void visit(); // Visits node and registers it in node statistics.
    void addChild(Node child);
    Node getBestChild();
    boolean isExpanded();
    boolean isTrimmed();
    void setExpanded();
    void setUnexpanded();

    // Fifo functionality.
    FifoNodeLinker getFifoNodeLinker();
}
