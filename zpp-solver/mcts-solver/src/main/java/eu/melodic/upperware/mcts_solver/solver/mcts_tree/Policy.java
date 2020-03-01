package eu.melodic.upperware.mcts_solver.solver.mcts_tree;

import eu.melodic.upperware.mcts_solver.solver.mcts_tree_impl.SolutionCP;

public interface Policy {

    SolutionCP finishPath(Path path);
}
