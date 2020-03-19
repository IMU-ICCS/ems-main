package eu.melodic.upperware.mcts_solver.solver.mcts_tree;

import java.util.List;

public abstract class Solution {

    public abstract boolean isBetterThan(Solution other);

    public abstract List<Integer> getAssignment();

    public abstract double getUtility();

    public abstract int getFailureDepth();
}
