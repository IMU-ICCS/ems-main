package eu.melodic.upperware.mcts_solver.solver.mcts.tree;

import java.util.List;

public interface Solution extends Comparable<Solution> {
    List<Integer> getAssignment();
    double getUtility();
    int getFailureDepth();
    boolean isFeasible();
}
