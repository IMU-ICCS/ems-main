package eu.melodic.upperware.mcts_solver.solver.policy;

import eu.melodic.upperware.mcts_solver.utility.PartialAssignment;
import eu.melodic.upperware.mcts_solver.utility.Solution;

public interface Policy {

    public Solution finishAssignment(PartialAssignment partialAssignment);
}
