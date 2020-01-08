package cp_components;
/*
    Search space element for Parallel Tempering -
    a full assignment of values to variables
 */
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import node_candidate.GeographicCoordinate;
import node_candidate.VMConfiguration;
import org.jamesframework.core.problems.sol.Solution;
import org.javatuples.Quartet;

import java.util.*;
@AllArgsConstructor
public class PTSolution extends Solution
{
    @Getter @Setter
    /*
        component -> (provider, Cores, Ram, Disk, latitude, longitude, cardinality)
     */
    private Map<Integer, Quartet<Integer, VMConfiguration, GeographicCoordinate,Integer>> varAssignments;

    public int extractProvider(int component) {
        return varAssignments.get(component).getValue0();
    }

    public VMConfiguration extractVMConfiguration(int component) {
        return (VMConfiguration) varAssignments.get(component).getValue(1);
    }

    public GeographicCoordinate extractVMLocation(int component) {
        return (GeographicCoordinate) varAssignments.get(component).getValue(2);
    }

    public int extractCardinality(int component) {
        return (Integer) varAssignments.get(component).getValue(3);
    }

    public PTSolution updateComponentConfiguration(int component, int provider, VMConfiguration n,
                                                   GeographicCoordinate l, int card) {
        PTSolution sol = (PTSolution) this.copy();
        sol.varAssignments.put(component, new Quartet<>(provider, n, l, card));
        return sol;
    }

    @Override
    public Solution copy() {
        Map<Integer, Quartet<Integer, VMConfiguration, GeographicCoordinate,Integer>> varsClone = new HashMap<>(varAssignments);
        return new PTSolution(varsClone);
    }

    private boolean equals(PTSolution s) {
        if (s.varAssignments.size() != varAssignments.size()) {
            return false;
        }
        for (Integer key : varAssignments.keySet()) {
            if (!s.varAssignments.containsKey(key)
                    || !varAssignments.get(key).equals(s.varAssignments.get(key))) {
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
