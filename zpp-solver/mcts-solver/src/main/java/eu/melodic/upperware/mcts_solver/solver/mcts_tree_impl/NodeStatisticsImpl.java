package eu.melodic.upperware.mcts_solver.solver.mcts_tree_impl;

import eu.melodic.upperware.mcts_solver.solver.mcts_tree.NodeStatistics;
import eu.melodic.upperware.mcts_solver.solver.mcts_tree.Solution;
import lombok.Getter;
import lombok.Setter;

@Getter
public class NodeStatisticsImpl implements NodeStatistics {
    @Setter
    private static double selectorCoefficient;
    @Setter
    private static double explorationCoefficient;
    @Setter
    private static int maximalDepth;
    private double averageFailureDepth = 0.0;
    private double maximalUtility = 0.0;
    private int visitCount;
    private int depth;

    public NodeStatisticsImpl(int parentDepth) {
        this.visitCount = 0;
        this.depth = parentDepth + 1;
    }

    @Override
    public void update(Solution solution) {
        double solutionUtility = solution.getUtility();

        if (solutionUtility > maximalUtility) {
            this.maximalUtility = solutionUtility;
        }

        double visitCountDouble = visitCount, failureDepth = solution.getFailureDepth(), maximalDepthDouble = maximalDepth;

        this.averageFailureDepth = (visitCountDouble - 1.0) / visitCountDouble * averageFailureDepth
                + (failureDepth - depth) / (visitCountDouble * maximalDepthDouble);
    }

    @Override
    public void markNewVisit() {
        this.visitCount++;
    }

    @Override
    public double getEvaluation(NodeStatistics parentStats) {
        return selectorCoefficient * averageFailureDepth +
                (1 - selectorCoefficient) * maximalUtility +
                explorationCoefficient * Math.sqrt(Math.log((double) parentStats.getVisitCount() / (double) getVisitCount()));
    }
}
