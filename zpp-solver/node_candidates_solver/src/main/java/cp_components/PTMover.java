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
}
