package eu.melodic.upperware.mcts_solver.solver.mcts_tree_impl;

import eu.melodic.upperware.mcts_solver.solver.mcts_cp_wrapper.MCTSWrapper;
import eu.melodic.upperware.mcts_solver.solver.mcts_tree.MoveProvider;
import eu.melodic.upperware.mcts_solver.solver.mcts_tree.Node;
import eu.melodic.upperware.mcts_solver.solver.mcts_tree.Path;
import org.javatuples.Pair;
import java.lang.Math;

import java.util.List;
import java.util.Random;

public class MoveProviderImpl implements MoveProvider {
    private MCTSWrapper mctsWrapper;

    public MoveProviderImpl(MCTSWrapper mctsWrapper) {
        super();

        this.mctsWrapper = mctsWrapper;
    }

    private double getEvaluation(NodeStatisticsImpl nodeStats, NodeStatisticsImpl parentStats) {
        double selectorCoefficient = NodeStatisticsImpl.getSelectorCoefficient();
        double explorationCoefficient = NodeStatisticsImpl.getExplorationCoefficient();

        return selectorCoefficient * nodeStats.getAverageFailureDepth() +
                (1 - selectorCoefficient) * nodeStats.getMaximalUtility() +
                explorationCoefficient * Math.sqrt(Math.log((double) parentStats.getVisitCount() / (double) nodeStats.getVisitCount()));
    }

    private boolean compare(Node left, Node right, Node parent) {
        if (right == null)
            return true;
        if (left == null)
            return false;

        NodeStatisticsImpl leftStats = (NodeStatisticsImpl) left.getNodeStatistics();
        NodeStatisticsImpl rightStats = (NodeStatisticsImpl) right.getNodeStatistics();
        NodeStatisticsImpl parentStats = (NodeStatisticsImpl) parent.getNodeStatistics();

        // If node hasn't been visited, then choose it.
        if (leftStats.getVisitCount() == 0)
            return true;

        if (rightStats.getVisitCount() == 0)
            return false;

        double leftEval = getEvaluation(leftStats, parentStats);

        double rightEval = getEvaluation(leftStats, parentStats);

        return leftEval <= rightEval;
    }

    private void expand(Node toExpand) {
        int depth = toExpand.getNodeStatistics().getDepth();
        if (depth  >= mctsWrapper.getSize()) {
            return;
        }
        if (toExpand.childrenSize() == mctsWrapper.domainSize(depth)) {
            return;
        }

        for (int i = mctsWrapper.getMinDomainValue(depth); i <= mctsWrapper.getMaxDomainValue(depth); i++) {
            Node newNode = new NodeImpl(i);
            newNode.linkToTree(toExpand);
        }
    }

    @Override
    public Pair<Node, Path> searchAndExpand(Node root) {
        Node current = root;
        int depth = 0;
        Path path = new Path();

        path.add(current);

        // While has all available children.
        while (current.childrenSize() == mctsWrapper.domainSize(depth) && depth < mctsWrapper.getSize()) {
            List<Node> children = current.getChildren();
            Node bestChild = null;

            if (children == null) {
                break;
            }

            for (Node child : children) {
                if (compare(child, bestChild, current))
                    bestChild = child;
            }
            depth++;
            path.add(current);
            current = bestChild;
        }

        expand(current);

        return new Pair<>(current, path);
    }
}
