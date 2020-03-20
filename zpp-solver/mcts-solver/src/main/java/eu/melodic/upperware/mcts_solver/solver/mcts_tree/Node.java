package eu.melodic.upperware.mcts_solver.solver.mcts_tree;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class Node implements Comparable<Node>{
    protected Node parent = null;
    protected List<Node> children = new ArrayList<>();
    protected int value;
    protected NodeStatistics nodeStatistics;

    public Node (int value) {
        this.value = value;
    }

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

    public abstract Node getBestChild();
}
