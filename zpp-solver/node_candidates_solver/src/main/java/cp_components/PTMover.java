package cp_components;

import lombok.AllArgsConstructor;
import org.jamesframework.core.search.neigh.Move;

import java.util.List;
@AllArgsConstructor
public class PTMover implements Move<PTSolution> {
    private List<Integer> beforeMoveAssignment;
    private List<Integer> afterMoveAssignment;

    @Override
    public void apply(PTSolution ptSolution) {
        ptSolution.setVarAssignments(afterMoveAssignment);
    }

    @Override
    public void undo(PTSolution ptSolution) {
        ptSolution.setVarAssignments(beforeMoveAssignment);
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
