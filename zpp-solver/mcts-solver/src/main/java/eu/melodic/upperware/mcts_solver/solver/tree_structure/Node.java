package eu.melodic.upperware.mcts_solver.solver.tree_structure;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


public class Node {
    @Getter
    private Node parent;
    private List<Node> children;
    @Getter
    private NodeStatistics nodeStatistics;

    public Node(Node parent) {
        this.parent = parent;
        children = new ArrayList<>();
        nodeStatistics = new NodeStatistics();
    }

    public Node() {
        this(null);
    }

    public void update(int maximalFitSize, double utility) {
        nodeStatistics.update(maximalFitSize, utility);
    }

    //public void addchild/expandchild
}
