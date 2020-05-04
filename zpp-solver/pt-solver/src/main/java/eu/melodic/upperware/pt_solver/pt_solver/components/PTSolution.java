package eu.melodic.upperware.pt_solver.pt_solver.components;
/*
    Search space element for Parallel Tempering
 */
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.jamesframework.core.problems.sol.Solution;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@EqualsAndHashCode
public class PTSolution extends Solution {
    /*
        Value at index i corresponds to i'th variable's value
     */
    @Getter
    private List<Integer> varAssignments;
    @Getter@Setter
    private PTEvaluation utility;

    public PTSolution(List<Integer> varAssignments) {
        this.varAssignments = varAssignments;
        utility = new PTEvaluation(0.0);
    }

    @Override
    public Solution copy() {
        List<Integer> varsClone = new ArrayList<>(varAssignments);
        return new PTSolution(varsClone);
    }

    protected void increaseValue(int variableIndex) {
        varAssignments.set(variableIndex, varAssignments.get(variableIndex) + 1);
    }

    protected void decreaseValue(int variableIndex) {
        varAssignments.set(variableIndex, varAssignments.get(variableIndex) - 1);
    }

}
