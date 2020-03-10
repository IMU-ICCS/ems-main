package eu.melodic.upperware.mcts_solver.solver.mcts_tree;

public interface Solution {

    boolean isBetterThan(Solution other);
}
