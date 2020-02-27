package eu.melodic.upperware.mcts_solver;

public interface Policy {

    public Solution finishAssignment(PartialAssignment partialAssignment);
}
