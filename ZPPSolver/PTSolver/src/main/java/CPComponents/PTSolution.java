package CPComponents;

import lombok.AllArgsConstructor;
import org.jamesframework.core.problems.sol.Solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class PTSolution extends Solution {

    private List<Integer> varAssignments;

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
