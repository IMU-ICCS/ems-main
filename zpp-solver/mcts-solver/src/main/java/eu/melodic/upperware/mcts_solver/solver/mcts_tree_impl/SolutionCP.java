package eu.melodic.upperware.mcts_solver.solver.mcts_tree_impl;

import eu.melodic.upperware.mcts_solver.solver.mcts_tree.Solution;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class SolutionCP extends Solution {
    // A feasible solution to constraint problem, or null for a an unfeasible solution.
    @Getter
    private List<Integer> assignment;
    // Maximal number of variables that solver was able to assign values to.
    @Getter
    private int maximalFitSize;
    // Calculated utility for a feasible solution, 0 for a an unfeasible solution.
    @Getter
    private double utility;
    @Getter
    private boolean feasible;

    // Creates empty solution.
    public SolutionCP() {
        super();

        assignment = null;
        maximalFitSize = 0;
        utility = 0;
        feasible = false;
    }
}
