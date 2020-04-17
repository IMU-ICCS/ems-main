package eu.melodic.upperware.mcts_solver.solver.mcts.tree;

import java.util.List;

public interface Node extends Comparable<Node> {
    NodeStatistics getNodeStatistics();
    int getValue();
    Node getParent();
    List<Node> getChildren();
    void linkToTree(Node parent); // Called after creation of a Node in order to add it to a tree.
    Node update(Solution solution); // Updates node based on solution.
    void visit(); // Visits node and registers it in node statistics.
    int getChildrenSize();
    void addChild(Node child);
    Node getBestChild();
    boolean isExpanded();
    void setExpanded();
    void setUnexpanded();
    void removeChild(Node child);
}
