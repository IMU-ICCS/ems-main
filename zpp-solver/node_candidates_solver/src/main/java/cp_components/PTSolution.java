package cp_components;

import org.jamesframework.core.problems.sol.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PTSolution extends Solution
{
    @Getter @Setter
    private List<Integer> varAssignments;

    public PTSolution(List<Integer> varAssignments) {
        this.varAssignments = varAssignments;
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
