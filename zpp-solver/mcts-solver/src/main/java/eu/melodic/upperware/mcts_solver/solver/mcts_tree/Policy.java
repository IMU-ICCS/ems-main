package eu.melodic.upperware.mcts_solver.solver.mcts_tree;

import eu.melodic.upperware.mcts_solver.solver.mcts_tree_impl.SolutionImpl;

public interface Policy {

    SolutionImpl finishPath(Path path);
}
