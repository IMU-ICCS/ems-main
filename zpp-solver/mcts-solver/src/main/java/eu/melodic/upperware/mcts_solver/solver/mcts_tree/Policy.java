package eu.melodic.upperware.mcts_solver.solver.mcts_tree;

public interface Policy {

    Solution finishPath(Path path);
}
