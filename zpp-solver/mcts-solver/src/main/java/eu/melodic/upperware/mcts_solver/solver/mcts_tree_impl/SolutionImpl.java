package eu.melodic.upperware.mcts_solver.solver.mcts_tree_impl;

import eu.melodic.upperware.mcts_solver.solver.mcts_cp_wrapper.MCTSWrapper;
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
    // Number of broken constraints.
    @Getter
    private int brokenConstraints;
    // Calculated utility for a feasible solution, 0 for a an unfeasible solution.
    @Getter
    private double utility;

    // Creates empty solution.
    public SolutionImpl() {
        super();

        assignment = new ArrayList<>();
        brokenConstraints = Integer.MAX_VALUE;
        utility = 0;
    }

    public SolutionImpl(int rolloutDepth, List<Integer> assignment, MCTSWrapper mctsWrapper) {
        this.assignment = assignment;
        utility = mctsWrapper.getUtility(assignment);
        brokenConstraints = mctsWrapper.countViolatedConstraints(assignment);
        if (utility == 0.0 || brokenConstraints > 0) {
            failureDepth = rolloutDepth;
        }
        else {
            failureDepth = assignment.size();
        }
    }

    @Override
    public boolean isBetterThan(Solution other) {
        SolutionImpl otherSolution = (SolutionImpl) other;

        if (brokenConstraints > ((SolutionImpl) other).brokenConstraints) {
            return false;
        }
        else if (brokenConstraints < ((SolutionImpl) other).brokenConstraints) {
            return true;
        }

        return failureDepth > otherSolution.failureDepth ||
                (failureDepth == otherSolution.failureDepth && utility > otherSolution.utility);
    }
}
