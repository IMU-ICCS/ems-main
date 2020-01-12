package cp_components;
/*
    Search space element for Parallel Tempering -
    a full assignment of values to variables
 */
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import node_candidate.GeographicCoordinate;
import node_candidate.VMConfiguration;
import org.jamesframework.core.problems.sol.Solution;
import org.javatuples.Quartet;

import java.util.*;
@AllArgsConstructor
@EqualsAndHashCode
public class PTSolution extends Solution
{
    private final int PROVIDER_INDEX = 0;
    private final int CONFIGURATION_INDEX = 1;
    private final int LOCATION_INDEX = 2;
    private final int CARDINALITY_INDEX = 3;
    @Getter @Setter
    /*
        component -> (provider, Cores, Ram, Disk, latitude, longitude, cardinality)
     */
    private Map<Integer, Quartet<Integer, VMConfiguration, GeographicCoordinate,Integer>> varAssignments;

    @Override
    public Solution copy() {
        Map<Integer, Quartet<Integer, VMConfiguration, GeographicCoordinate,Integer>> varsClone = new HashMap<>(varAssignments);
        return new PTSolution(varsClone);
    }

    public int extractProvider(int component) {
        return (Integer) varAssignments.get(component).getValue(PROVIDER_INDEX);
    }

    public VMConfiguration extractVMConfiguration(int component) {
        return (VMConfiguration) varAssignments.get(component).getValue(CONFIGURATION_INDEX);
    }

    public GeographicCoordinate extractVMLocation(int component) {
        return (GeographicCoordinate) varAssignments.get(component).getValue(LOCATION_INDEX);
    }

    public int extractCardinality(int component) {
        return (Integer) varAssignments.get(component).getValue(CARDINALITY_INDEX);
    }

    public PTSolution updateComponentConfiguration(int component, int provider, VMConfiguration conf,
                                                   GeographicCoordinate loc, int card) {
        PTSolution solution = (PTSolution) this.copy();
        solution.varAssignments.put(component, new Quartet<>(provider, conf, loc, card));
        return solution;
    }
}
