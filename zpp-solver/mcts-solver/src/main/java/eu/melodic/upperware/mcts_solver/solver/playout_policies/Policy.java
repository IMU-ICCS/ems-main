package eu.melodic.upperware.mcts_solver.solver.playout_policies;

import eu.melodic.upperware.mcts_solver.solver.utility.PartialAssignment;
import eu.melodic.upperware.mcts_solver.solver.utility.Solution;

public interface Policy {

    Solution finishAssignment(PartialAssignment partialAssignment);
}
