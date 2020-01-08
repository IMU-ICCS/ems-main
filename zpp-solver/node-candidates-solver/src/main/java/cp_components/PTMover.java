package cp_components;

import lombok.AllArgsConstructor;
import org.jamesframework.core.search.neigh.Move;
/*
    Class which abstracts moves in CP search space.
    Here the move means changing full variable assignment from
    @beforeMoveAssignment to @asterMoveAssignment
 */

@AllArgsConstructor
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

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            PTMover m = (PTMover) o;
            return m.afterMoveAssignment.equals(afterMoveAssignment)
                    &&
                    m.beforeMoveAssignment.equals(beforeMoveAssignment);
        }
    }
}
