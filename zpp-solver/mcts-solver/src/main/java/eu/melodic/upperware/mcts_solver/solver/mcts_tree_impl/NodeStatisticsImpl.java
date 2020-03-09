package eu.melodic.upperware.mcts_solver.solver.mcts_tree_impl;

import eu.melodic.upperware.mcts_solver.solver.mcts_tree.NodeStatistics;
import eu.melodic.upperware.mcts_solver.solver.mcts_tree.Solution;
import lombok.Getter;
import lombok.Setter;

public class NodeStatisticsImpl extends NodeStatistics {
    @Setter @Getter
    private static double selectorCoefficient;
    @Setter @Getter
    private static double explorationCoefficient;
    @Getter
    private double averageFailureDepth;
    @Getter
    private double maximalUtility;

    public NodeStatisticsImpl(int parentDepth) {
        super(parentDepth);

        averageFailureDepth = 0.0;
        maximalUtility = 0.0;
    }

    @Override
    public void update(Solution solution) {
        double solutionUtility = ((SolutionImpl) solution).getUtility();

        if (solutionUtility > maximalUtility) {
            maximalUtility = solutionUtility;
        }

        // TODO high chance statement below is bugged
        averageFailureDepth = (double) visitCount / (visitCount + 1.0) * averageFailureDepth
                + 1.0 / (visitCount + 1.0) * (((SolutionImpl) solution).getSize() - depth);
    }
}
