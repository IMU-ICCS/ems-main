package eu.melodic.upperware.mcts_solver.solver.mcts_tree_impl;

import eu.melodic.upperware.mcts_solver.solver.mcts_cp_wrapper.MCTSWrapper;
import eu.melodic.upperware.mcts_solver.solver.mcts_tree.Solution;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SolutionImpl implements Solution {
    // Partial assignment of variables.
    private List<Integer> assignment;
    // Depth at which assigning failed.
    private int failureDepth;
    private boolean feasible;
    private double utility;

    public SolutionImpl(int rolloutDepth, List<Integer> assignment, MCTSWrapper mctsWrapper) {
        this.assignment = assignment;
        this.utility = mctsWrapper.getUtility(assignment);
        this.feasible = mctsWrapper.isFeasible(assignment);
        if (utility == 0.0 || !feasible) {
            this.failureDepth = rolloutDepth;
        }
        else {
            this.failureDepth = assignment.size();
        }
    }

    // Creates an empty solution.
    public SolutionImpl() {
        this.assignment = new ArrayList<>();
        this.feasible = false;
        this.utility = 0;
    }

    @Override
    public int compareTo(Solution other) {
        if (feasible && !other.isFeasible()) {
            return 1;
        }
        else if (!feasible && other.isFeasible()) {
            return -1;
        }

        return (failureDepth != other.getFailureDepth()) ?
                Double.compare(failureDepth, other.getFailureDepth()) :
                Double.compare(utility, other.getUtility());
    }
}
