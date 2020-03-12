package eu.melodic.upperware.mcts_solver.solver.mcts_tree_impl;

import eu.melodic.upperware.mcts_solver.solver.mcts_tree.Node;
import lombok.AllArgsConstructor;

import java.util.Comparator;

@AllArgsConstructor
public class NodeComparator implements Comparator<Node> {
    private Node parent;

    private double getEvaluation(NodeStatisticsImpl nodeStats, NodeStatisticsImpl parentStats) {
        double selectorCoefficient = NodeStatisticsImpl.getSelectorCoefficient();
        double explorationCoefficient = NodeStatisticsImpl.getExplorationCoefficient();

        return selectorCoefficient * nodeStats.getAverageFailureDepth() +
                (1 - selectorCoefficient) * nodeStats.getMaximalUtility() +
                explorationCoefficient * Math.sqrt(Math.log((double) parentStats.getVisitCount() / (double) nodeStats.getVisitCount()));
    }

    @Override
    public int compare(Node left, Node right) {
        NodeStatisticsImpl leftStats = (NodeStatisticsImpl) left.getNodeStatistics();
        NodeStatisticsImpl rightStats = (NodeStatisticsImpl) right.getNodeStatistics();
        NodeStatisticsImpl parentStats = (NodeStatisticsImpl) parent.getNodeStatistics();

        // If node hasn't been visited, then choose it.
        if (leftStats.getVisitCount() == 0) {
            return 1;
        }
        if (rightStats.getVisitCount() == 0) {
            return -1;
        }

        return Double.compare(getEvaluation(leftStats, parentStats), getEvaluation(rightStats, parentStats));
    }
}
