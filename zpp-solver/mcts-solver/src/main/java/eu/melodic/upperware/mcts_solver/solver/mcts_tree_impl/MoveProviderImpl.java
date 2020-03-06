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
    private double selectorCoefficient;
    private double explorationCoefficient;
    private MCTSWrapper mctsWrapper;

    public MoveProviderImpl(double selectorCoefficient, double explorationCoefficient, MCTSWrapper mctsWrapper) {
        super();

        this.selectorCoefficient = selectorCoefficient;
        this.explorationCoefficient = explorationCoefficient;
        this.mctsWrapper = mctsWrapper;
    }

    private boolean compare(Node left, Node right, Node parent) {
        if (right == null)
            return true;
        if (left == null)
            return false;

        NodeStatisticsImpl leftStats = (NodeStatisticsImpl) left.getNodeStatistics();
        NodeStatisticsImpl rightStats = (NodeStatisticsImpl) right.getNodeStatistics();
        NodeStatisticsImpl parentStats = (NodeStatisticsImpl) parent.getNodeStatistics();

        if (leftStats.getVisitCount() == 0)
            return true;

        if (rightStats.getVisitCount() == 0)
            return false;

        double leftEval = selectorCoefficient * leftStats.getAverageFailureDepth() +
                (1 - selectorCoefficient) * leftStats.getMaximalUtility() +
                explorationCoefficient * Math.sqrt(Math.log((double) parentStats.getVisitCount() / (double) leftStats.getVisitCount()));

        double rightEval = selectorCoefficient * rightStats.getAverageFailureDepth() +
                (1 - selectorCoefficient) * rightStats.getMaximalUtility() +
                explorationCoefficient * Math.sqrt(Math.log((double) parentStats.getVisitCount() / (double) rightStats.getVisitCount()));

        return leftEval <= rightEval;
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
            for (Node child : current.getChildren()) {
                if (compare(child, bestChild, current))
                    bestChild = child;
            }
            depth++;
            path.add(current);
            current = bestChild;
        }

        // TODO need to expand current node

        return new Pair<>(current, path);
    }
}
