package eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl;

import eu.melodic.upperware.mcts_solver.solver.mcts.cp_wrapper.MCTSWrapper;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Solution;
import lombok.Getter;

import java.util.Collections;
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
        this.feasible = mctsWrapper.isFeasible(assignment);
        this.utility = feasible ? mctsWrapper.getUtility(assignment) : 0.0;
        if (utility == 0.0 || !feasible) {
            this.failureDepth = rolloutDepth;
        }
        else {
            this.failureDepth = assignment.size();
        }
    }

    public SolutionImpl(int rolloutDepth) {
        this.assignment = Collections.emptyList();
        this.failureDepth = rolloutDepth;
        this.utility = 0.0;
        this.feasible = false;
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
