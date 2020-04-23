package eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl;

import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Solution;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Node;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.NodeStatistics;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.max;

public class NodeImpl implements Node {
    @Getter
                private Node parent = null;
                @Getter
    private List<Node> children = new ArrayList<>();
    @Getter
    private int value;
    @Getter
    private NodeStatistics nodeStatistics;
    private QueueLinker queueLinker = new QueueLinker();

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
    public void visit() {
        nodeStatistics.markNewVisit();
    }

    @Override
    public int getChildrenSize() {
        return children.size();
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
    public boolean isExpanded() {
        return nodeStatistics.isExpanded();
    }

    @Override
    public void setExpanded() {
        nodeStatistics.setExpanded();
    }

    @Override
    public void setUnexpanded() {
        nodeStatistics.setUnexpanded();
    }

    public void removeChild(Node child) {
        this.children.remove(child);
    }

    @Override
    public void removeChildren() {
        children.clear();
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

        int compare = Double.compare(nodeStatistics.getEvaluation(parent.getNodeStatistics()),
                other.getNodeStatistics().getEvaluation(other.getParent().getNodeStatistics()));
        if (compare != 0) {
            return compare;
        } else {
            return -Integer.compare(value, other.getValue());
        }
    }

    // Fifo functionality.
    protected QueueLinker getQueueLinker() {
        return queueLinker;
    }
}
