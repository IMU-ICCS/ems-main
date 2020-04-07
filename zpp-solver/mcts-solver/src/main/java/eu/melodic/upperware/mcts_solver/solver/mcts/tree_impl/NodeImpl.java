package eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl;

import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Node;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.NodeStatistics;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Solution;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.memory_management.FifoNodeLinker;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.memory_management.FifoNodeLinkerImpl;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.max;

@Getter
public class NodeImpl implements Node {
    private Node parent = null;
    private List<Node> children = new ArrayList<>();
    private FifoNodeLinker fifoNodeLinker = new FifoNodeLinkerImpl();
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
    public boolean isExpanded() {
        return nodeStatistics.isExpanded();
    }

    @Override
    public boolean isTrimmed() {
        return nodeStatistics.isTrimmed();
    }

    @Override
    public void setExpanded() {
        nodeStatistics.setExpanded();
    }

    @Override
    public void setUnexpanded() {
        nodeStatistics.setDeExpanded();
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
