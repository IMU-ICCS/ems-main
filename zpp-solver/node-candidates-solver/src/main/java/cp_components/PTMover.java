package cp_components;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.jamesframework.core.search.neigh.Move;
/*
    Class which abstracts moves in CP search space.
    Here the move means changing full variable assignment from
    @beforeMoveAssignment to @asterMoveAssignment
 */

@AllArgsConstructor
@EqualsAndHashCode
public class PTMover implements Move<PTSolution> {
    private PTSolution beforeMoveAssignment;
    private PTSolution afterMoveAssignment;

    @Override
    public void apply(PTSolution ptSolution) {
        ptSolution.setVarAssignments(afterMoveAssignment.getVarAssignments());
    }

    @Override
    public void undo(PTSolution ptSolution) {
        ptSolution.setVarAssignments(beforeMoveAssignment.getVarAssignments());
    }

}
