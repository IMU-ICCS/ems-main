package eu.melodic.upperware.mcts_solver.solver.policy;

import eu.melodic.upperware.mcts_solver.solver.utility.PartialAssignment;
import eu.melodic.upperware.mcts_solver.solver.utility.Solution;

public interface Policy {

    Solution finishAssignment(PartialAssignment partialAssignment);
}
