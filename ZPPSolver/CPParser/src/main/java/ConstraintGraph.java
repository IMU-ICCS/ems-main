import java.util.*;

public class ConstraintGraph {
    private Collection<ArConstraint> constraints;
    private Collection<String> variables;
    private Map<String, Map<Integer, Set<String>>> neighbourhoodList;
    private Map<String, Collection<ArConstraint>> variableToConstraint;

    public ConstraintGraph(Collection<ArConstraint> constraints, Collection<String> vars) {
        this.constraints = constraints;
        this.variables = vars;
        initializeStructures();
        buildNeighbourhoodList();
        buildVariableToConstraintMap();
    }

    private Collection<String> getNeighbours(String node) {
        return neighbourhoodList.get(node).get(1);
    }

    private void initializeKnownDistances(Map<String, List<String>> knownDistances) {
        for (String var : variables) {
            knownDistances.put(var, new LinkedList<String>());
            knownDistances.get(var).add(var);
        }
    }

    private void buildVariableToConstraintMap() {
        for (ArConstraint c : constraints) {
            for (String var : c.getVariableNames()) {
                variableToConstraint.get(var).add(c);
            }
        }
    }

    private void initializeNeighbourhoodList(int distance) {
        for (String node : variables) {
            neighbourhoodList.get(node).put(distance, new HashSet<String>());
        }
    }

    private void initializeStructures() {
        neighbourhoodList = new HashMap<>();
        variableToConstraint = new HashMap<>();
        for (String node : variables) {
            neighbourhoodList.put(node, new HashMap<>());
            variableToConstraint.put(node, new LinkedList<ArConstraint>());
        }
    }

    private void calculateNeighbours(Map<String, List<String>> knownDistances) {
        initializeNeighbourhoodList(1);
        for (ArConstraint constraint : constraints) {
            Collection<String> variables = constraint.getVariableNames();
            for (String var : variables) {
                knownDistances.get(var).addAll(variables);
                neighbourhoodList.get(var).get(1).addAll(variables);
                neighbourhoodList.get(var).get(1).remove(var);
            }
        }
    }

    private void buildNeighbourhoodList() {
        Map<String, List<String>> knownDistances = new HashMap<>();
        initializeKnownDistances(knownDistances);
        calculateNeighbours(knownDistances);
        int distance = 1;
        do {
            distance++;
            initializeNeighbourhoodList(distance);
        } while (neighbourhoodSweep(distance, knownDistances));
    }

    private boolean neighbourhoodSweep(int dist, Map<String, List<String>> knownDistances){
        boolean foundPath = false;
        for (String node: variables) {
            Collection<String> distantNeighbours = neighbourhoodList.get(node).get(dist - 1);
            for (String neighbour : getNeighbours(node)) {
                for (String distantNode : distantNeighbours) {
                    if (!knownDistances.get(distantNode).contains(neighbour)) {
                        knownDistances.get(distantNode).add(neighbour);
                        foundPath = true;
                        neighbourhoodList.get(distantNode).get(dist).add(neighbour);
                    }
                }
            }
        }
        return foundPath;
    }

    private Collection<String> getNeighbours(String node, int distance) {
        if (neighbourhoodList.get(node).containsKey(distance)) {
            return neighbourhoodList.get(node).get(distance);
        } else {
            return new LinkedList<String>();
        }
    }

    public Collection<ArConstraint> getConstraints(String variable) {
        return variableToConstraint.get(variable);
    }

    public int getNumberOfNeighbours(String node, int distance) {
        return getNeighbours(node, distance).size();
    }
}
