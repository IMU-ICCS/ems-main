package node_candidate;

import cp_components.PTMover;

import java.util.List;
import java.util.Map;
import java.util.Random;
//TODO musi miec maksymalne wartosci
public class NodeCandidatesPool {
    private final int variablesPerComponent = 7;

    private enum  MovementType {
        CARDINALITY,
        VM_SPECIFICATION,
        VM_LOCATION,
        PROVIDER
    };

    private List<String> components;
     /*
        Provider -> VM specification
        NodeCandidates must be sorted lexicographically in ascending order
     */
    private Map<Integer,List<NodeCandidate>> nodeCandidates;
    /*
        Provider -> VM location.
        Coordinates must be sorted lexicographically in ascending order
     */
    private Map<Integer, List<GeographicCoordinate>> vmLocations;

    public void postNodeCandidate() {

    }

    private NodeCandidate extractNodeCandidate(int component, List<Integer> assignment) {
        int index = component * variablesPerComponent;
        return new NodeCandidate(
                assignment.get(index + 1), assignment.get(index+2), assignment.get(index+3)
        );
    }

    private GeographicCoordinate extractVMLocation(int component, List<Integer> assignment) {
        int index = component * variablesPerComponent;
        return new GeographicCoordinate(
                assignment.get(index + 5), assignment.get(index+6)
        );
    }

    private MovementType sampleMovementType(Random random) {
        int rand = random.nextInt(variablesPerComponent);
        if (rand == 0) return MovementType.CARDINALITY;
        else if (rand >= 5) return MovementType.VM_LOCATION;
        else if ( rand <= 3) return MovementType.VM_SPECIFICATION;
        else return MovementType.PROVIDER;
    }

    private int extractProvider(int component, List<Integer> assignment) {

    }

    private PTMover sampleVMSpecificationMove(int component, List<Integer> assignment, Random random) {
        NodeCandidate node = extractNodeCandidate(component, assignment);
        // TODO binsearch node and sample one of the neighbours - if any exists
    }

    public PTMover getRandomMove(List<Integer> assignment, Random random) {
        int component = random.nextInt(components.size());

    }
}
