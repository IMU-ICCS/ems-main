package eu.melodic.upperware.mcts_solver.solver.mcts_tree_impl;

import eu.melodic.upperware.mcts_solver.solver.mcts_tree.NodeStatistics;
import eu.melodic.upperware.mcts_solver.solver.mcts_tree.Solution;
import lombok.Getter;
import lombok.Setter;

@Getter
public class NodeStatisticsImpl extends NodeStatistics {
    @Setter @Getter
    private static double selectorCoefficient;
    @Setter @Getter
    private static double explorationCoefficient;
    @Setter @Getter
    private static int maximalDepth;
    private double averageFailureDepth = 0.0;
    private double maximalUtility = 0.0;


    public NodeStatisticsImpl(int parentDepth) {
        super(parentDepth);
    }

    @Override
    public void update(Solution solution) {
        double solutionUtility = solution.getUtility();

        if (solutionUtility > maximalUtility) {
            maximalUtility = solutionUtility;
        }

        double visitCountDouble = visitCount, failureDepth = solution.getFailureDepth(), maximalDepthDouble = maximalDepth;

        averageFailureDepth = (visitCountDouble - 1.0) / visitCountDouble * averageFailureDepth
                + (failureDepth - depth) / (visitCountDouble * maximalDepthDouble);
    }
}
