package eu.melodic.upperware.mcts_solver.solver.mcts_tree_impl;

import eu.melodic.upperware.mcts_solver.solver.mcts_cp_wrapper.IMCTSWrapper;
import eu.melodic.upperware.mcts_solver.solver.mcts_tree.Solution;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class SolutionImpl implements Solution {
    // A feasible solution to constraint problem, or null for a an unfeasible solution.
    @Getter
    private List<Integer> assignment;
    // Depth at which assigning failed.
    @Getter
    private int failureDepth;
    // Feasible if there are no broken constraints.
    @Getter
    private boolean feasible;
    // Calculated utility for a feasible solution, 0 for a an unfeasible solution.
    @Getter
    private double utility;

    // Creates empty solution.
    public SolutionImpl() {
        super();

        assignment = new ArrayList<>();
        feasible = false;
        utility = 0;
    }

    public SolutionImpl(int rolloutDepth, List<Integer> assignment, IMCTSWrapper mctsWrapper) {
        this.assignment = assignment;
        utility = mctsWrapper.getUtility(assignment);
        feasible = mctsWrapper.isFeasible(assignment);
        if (utility == 0.0 || !feasible) {
            failureDepth = rolloutDepth;
        }
        else {
            failureDepth = assignment.size();
        }
    }

    @Override
    public boolean isBetterThan(Solution other) {
        SolutionImpl otherSolution = (SolutionImpl) other;

        if (feasible && !((SolutionImpl) other).feasible) {
            return true;
        }
        else if (!feasible && ((SolutionImpl) other).feasible) {
            return false;
        }

        return failureDepth > otherSolution.failureDepth ||
                (failureDepth == otherSolution.failureDepth && utility > otherSolution.utility);
    }
}
