package CPComponents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jamesframework.core.problems.sol.Solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class PTSolution extends Solution {
    public static List<Integer> minVariableValues;
    public static List<Integer> maxVariableValues;
    @Getter
    private List<Integer> varAssignments;

    protected void increaseValue(int variableIndex) {
        int varValue = varAssignments.get(variableIndex);
        varAssignments.set(variableIndex, varValue + 1);
    }

    protected void decreaseValue(int variableIndex) {
        varAssignments.set(variableIndex, varAssignments.get(variableIndex) - 1);
    }

    public boolean canMoveUp(int var) {
        return PTSolution.maxVariableValues.get(var) > varAssignments.get(var);
    }

    public boolean canMoveDown(int var) {
        return PTSolution.minVariableValues.get(var) < varAssignments.get(var);
    }

    @Override
    public Solution copy() {
        List<Integer> varsClone = new ArrayList<>();
        Collections.copy(varAssignments, varsClone);
        return new PTSolution(varsClone);
    }

    private boolean equals(PTSolution s) {
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
