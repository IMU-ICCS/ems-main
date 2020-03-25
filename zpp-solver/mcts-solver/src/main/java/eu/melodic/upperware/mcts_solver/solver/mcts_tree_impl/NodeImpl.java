package eu.melodic.upperware.mcts_solver.solver.mcts_tree_impl;

import eu.melodic.upperware.mcts_solver.solver.mcts_tree.Node;
import eu.melodic.upperware.mcts_solver.solver.mcts_tree.NodeStatistics;
import eu.melodic.upperware.mcts_solver.solver.mcts_tree.Solution;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.max;

@Getter
public class NodeImpl implements Node {
    private Node parent = null;
    private List<Node> children = new ArrayList<>();
    private int value;
    private NodeStatistics nodeStatistics;

    public NodeImpl(Integer value) {
        this.value = value;
    }

    public NodeImpl() {
        this.value = -1;
        this.nodeStatistics = new NodeStatisticsImpl(-1);
    }

    @Override
    public void linkToTree(Node parent) {
        this.parent = parent;
        this.nodeStatistics = new NodeStatisticsImpl(parent.getNodeStatistics().getDepth());
        parent.addChild(this);
    }

    @Override
    public Node update(Solution solution) {
        nodeStatistics.update(solution);
        return this;
    }

    @Override
    public int getChildrenSize() {
        return children.size();
    }

    @Override
    public void visit() {
        nodeStatistics.markNewVisit();
    }

    @Override
    public void addChild(Node child) {
        children.add(child);
    }

    @Override
    public Node getBestChild() {
        return max(children);
    }

    @Override
    public int compareTo(Node other) {
        NodeStatistics otherStats = other.getNodeStatistics();

        // If node hasn't been visited, then choose it.
        if (nodeStatistics.getVisitCount() == 0) {
            return 1;
        }
        if (otherStats.getVisitCount() == 0) {
            return -1;
        }

        return Double.compare(nodeStatistics.getEvaluation(parent.getNodeStatistics()),
                other.getNodeStatistics().getEvaluation(other.getParent().getNodeStatistics()));
    }
}
