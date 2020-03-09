package eu.melodic.upperware.mcts_solver.solver.mcts_tree_impl;

import eu.melodic.upperware.mcts_solver.solver.mcts_cp_wrapper.MCTSWrapper;
import eu.melodic.upperware.mcts_solver.solver.mcts_tree.Solution;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class SolutionImpl extends Solution {
    // A feasible solution to constraint problem, or null for a an unfeasible solution.
    @Getter
    private List<Integer> assignment;
    // Maximal number of variables that solver was able to assign values to.
    @Getter
    private int maximalFitSize;
    // Calculated utility for a feasible solution, 0 for a an unfeasible solution.
    @Getter
    private double utility;

    // Creates empty solution.
    public SolutionImpl() {
        super();

        assignment = new ArrayList<>();
        maximalFitSize = 0;
        utility = 0;
    }

    public SolutionImpl(int rolloutDepth, List<Integer> assignment, MCTSWrapper mctsWrapper) {
        this.assignment = assignment;
        utility = mctsWrapper.getUtility(assignment);
        if (utility == 0.0 || mctsWrapper.countViolatedConstraints(assignment) > 0) {
            maximalFitSize = rolloutDepth;
        }
        else {
            maximalFitSize = assignment.size();
        }

    }

    public int getSize() {
        return assignment.size();
    }

    @Override
    public boolean isBetterThan(Solution other) {
        SolutionImpl otherSolution = (SolutionImpl) other;

        return maximalFitSize > otherSolution.maximalFitSize ||
                (maximalFitSize == otherSolution.maximalFitSize && utility > otherSolution.utility);
    }
}
