package eu.melodic.upperware.mcts_solver.solver.mcts_tree_impl;

import eu.melodic.upperware.mcts_solver.solver.mcts_tree.Node;
import eu.melodic.upperware.mcts_solver.solver.mcts_tree.NodeStatistics;

import static java.util.Collections.max;

public class NodeImpl extends Node {

    public NodeImpl(int value) {
        super(value);
    }

    public NodeImpl() {
        super(-1); // Dummy value.
        nodeStatistics = new NodeStatisticsImpl(-1);
    }

    @Override
    public void linkToTree(Node parent) {
        this.parent = parent;
        nodeStatistics = new NodeStatisticsImpl(parent.getNodeStatistics().getDepth());
        parent.addChild(this);
    }

    @Override
    public Node getBestChild() {
        return max(children);
    }

    @Override
    public int compareTo(Node other) {
        NodeStatistics stats = nodeStatistics;
        NodeStatistics otherStats = other.getNodeStatistics();
        NodeStatistics parentStats = parent.getNodeStatistics();

        // If node hasn't been visited, then choose it.
        if (stats.getVisitCount() == 0) {
            return 1;
        }
        if (otherStats.getVisitCount() == 0) {
            return -1;
        }

        return Double.compare(getEvaluation(stats, parentStats), getEvaluation(otherStats, parentStats));
    }

    private double getEvaluation(NodeStatistics nodeStats, NodeStatistics parentStats) {
        double selectorCoefficient = NodeStatisticsImpl.getSelectorCoefficient();
        double explorationCoefficient = NodeStatisticsImpl.getExplorationCoefficient();

        return selectorCoefficient * nodeStats.getAverageFailureDepth() +
                (1 - selectorCoefficient) * nodeStats.getMaximalUtility() +
                explorationCoefficient * Math.sqrt(Math.log((double) parentStats.getVisitCount() / (double) nodeStats.getVisitCount()));
    }
}
