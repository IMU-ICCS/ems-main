package nc_solver.cp_components;
/*
    Search space element for Parallel Tempering -
    a full assignment of values to variables
 */
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import nc_solver.node_candidate.node_candidate_element.GeographicCoordinate;
import nc_solver.node_candidate.node_candidate_element.IntegerNodeCandidateElementImpl;
import nc_solver.node_candidate.node_candidate_element.NodeCandidateElementInterface;
import nc_solver.node_candidate.node_candidate_element.VMConfiguration;
import org.jamesframework.core.problems.objectives.evaluations.Evaluation;
import org.jamesframework.core.problems.sol.Solution;

import java.util.*;
@AllArgsConstructor
@EqualsAndHashCode
public class PTSolution extends Solution
{
    public static final int PROVIDER_INDEX = 0;
    public static final int CONFIGURATION_INDEX = 1;
    public static final int LOCATION_INDEX = 2;
    public static final int CARDINALITY_INDEX = 3;
    @Getter @Setter
    /*
        component -> (provider, Cores, Ram, Disk, latitude, longitude, cardinality)
     */
    private Map<Integer, Map<Integer, NodeCandidateElementInterface>> varAssignments;
    @Getter @Setter
    private PTEvaluation utility;

    public PTSolution(Map<Integer, Map<Integer, NodeCandidateElementInterface>> varAssignments) {
        this.varAssignments = varAssignments;
        this.utility = new PTEvaluation(0.0);
    }

    @Override
    public Solution copy() {
        Map<Integer, Map<Integer, NodeCandidateElementInterface>> varsClone = new HashMap<>(varAssignments);
        return new PTSolution(varsClone, new PTEvaluation(this.utility.getValue()));
    }

    public int extractProvider(int component) {
        return ((IntegerNodeCandidateElementImpl) varAssignments.get(component).get(PROVIDER_INDEX)).getValue();
    }

    public VMConfiguration extractVMConfiguration(int component) {
        return (VMConfiguration) varAssignments.get(component).get(CONFIGURATION_INDEX);
    }

    public GeographicCoordinate extractVMLocation(int component) {
        return (GeographicCoordinate) varAssignments.get(component).get(LOCATION_INDEX);
    }

    public int extractCardinality(int component) {
        return ((IntegerNodeCandidateElementImpl) varAssignments.get(component).get(CARDINALITY_INDEX)).getValue();
    }

    public PTSolution updateComponentConfiguration(int component, int provider, VMConfiguration configuration,
                                                   GeographicCoordinate location, int cardinality) {
        PTSolution solution = (PTSolution) this.copy();
        solution.varAssignments.put(component, new HashMap<Integer, NodeCandidateElementInterface>(){{
            put(PROVIDER_INDEX, new IntegerNodeCandidateElementImpl(provider));
            put(CONFIGURATION_INDEX, configuration);
            put(LOCATION_INDEX, location);
            put(CARDINALITY_INDEX, new IntegerNodeCandidateElementImpl(cardinality));
        }});
        return solution;
    }
}
