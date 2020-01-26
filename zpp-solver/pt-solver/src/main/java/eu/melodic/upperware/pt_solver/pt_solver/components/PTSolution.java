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
    public static List<Integer> minVariableValues;
    public static List<Integer> maxVariableValues;
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
    /*
        True if increasing current @var variable value by one will not exceed
        domain range
     */
    public boolean canMoveUp(int var) {
        return PTSolution.maxVariableValues.get(var) > varAssignments.get(var);
    }
    /*
        True if decreasing current @var variable value by one will not exceed
        domain range
     */
    public boolean canMoveDown(int var) {
        return PTSolution.minVariableValues.get(var) < varAssignments.get(var);
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
