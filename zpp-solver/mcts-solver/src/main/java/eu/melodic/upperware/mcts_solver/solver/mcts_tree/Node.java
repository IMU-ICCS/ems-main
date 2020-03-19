package eu.melodic.upperware.mcts_solver.solver.mcts_tree;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


public abstract class Node {
    @Getter
    protected Node parent;
    @Getter
    protected List<Node> children;
    @Getter
    protected int value;
    @Getter
    protected NodeStatistics nodeStatistics;

    public Node (int value) {
        children = new ArrayList<>();
        this.value = value;
    }

    // Called when creating tree.
    public abstract void becomeTreeRoot();

    // Called after creation of a Node in order to add it to a tree.
    public abstract void linkToTree(Node parent);

    public void update(Solution solution) {
        nodeStatistics.update(solution);
    }

    public int childrenSize() {
        return children.size();
    }

    public void visit() {
        nodeStatistics.visit();
    }

    public void addChild(Node child) {
        children.add(child);
    }
}
