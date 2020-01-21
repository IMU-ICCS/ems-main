package nc_solver.cp_components;
/*
    Search space element for Parallel Tempering
 */
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jamesframework.core.problems.sol.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class PTSolution extends Solution {
    public static List<Integer> minVariableValues;
    public static List<Integer> maxVariableValues;
    /*
        Value at index i corresponds to i'th variable's value
     */
    @Getter
    private List<Integer> varAssignments;

    protected void increaseValue(int variableIndex) {
        varAssignments.set(variableIndex, varAssignments.get(variableIndex) + 1);
    }

    protected void decreaseValue(int variableIndex) {
        varAssignments.set(variableIndex, varAssignments.get(variableIndex) - 1);
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

    private boolean equals(PTSolution s) {
        if (s.varAssignments.size() != varAssignments.size()) {
            return false;
        }
        for (int i = 0; i < varAssignments.size(); i++) {
            if (s.varAssignments.get(i) != varAssignments.get(i)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        final PTSolution other = (PTSolution) o;
        return equals(other);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(varAssignments);
    }
}
