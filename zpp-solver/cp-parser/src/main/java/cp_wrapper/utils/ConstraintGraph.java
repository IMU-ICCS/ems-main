package cp_wrapper.utils;
/*
    This class implements constraint graph.
    A constraint graph is created from a set of
    variables and constraints (i.e. subsets of the set of variables).
    Each node corresponds to a variable. Two nodes are connected by an edge
    iff there exists a constraint containing both.
 */


import cp_wrapper.utils.constraint.Constraint;
import cp_wrapper.utils.numeric_value_impl.NumericValue;

import java.util.*;

public class ConstraintGraph {
    private Collection<Constraint> constraints;
    private Collection<String> variables;
    /*
           For variable var and natural number d
           @neighbourhoodList[var][d] contains set of variables

     */
    private Map<String, Map<Integer, Set<String>>> neighbourhoodList;
    private Map<String, Collection<Constraint>> variableToConstraint;

    public ConstraintGraph(Collection<Constraint> constraints, Collection<String> vars) {
        this.constraints = constraints;
        this.variables = vars;
        initializeStructures();
        buildNeighbourhoodList();
        buildVariableToConstraintMap();
    }

    private Collection<String> getNeighbours(String node) {
        return neighbourhoodList.get(node).get(1);
    }

    /*
        for variable var, @knownDistances[var] contains set of
        variables from which the distance to var is known.
     */
    private void initializeKnownDistances(Map<String, List<String>> knownDistances) {
        for (String var : variables) {
            knownDistances.put(var, new LinkedList<String>());
            knownDistances.get(var).add(var);
        }
    }

    private void buildVariableToConstraintMap() {
        for (Constraint c : constraints) {
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
            variableToConstraint.put(node, new LinkedList<Constraint>());
        }
    }

    private void calculateNeighbours(Map<String, List<String>> knownDistances) {
        initializeNeighbourhoodList(1);
        for (Constraint constraint : constraints) {
            Collection<String> variables = constraint.getVariableNames();
            for (String var : variables) {
                System.out.println(var);
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

    /*
        This function assumes all distances up to @dist-1 have been calculated.
        For each variable @var, @knownDistances[@var] contains list of variables
        from which distance to @var has already been calculated.
        We iterate through nodes.
            Let n denote some node. For each pair u, v where dist(u,n) == 1
               and dist(v, n) == @dist - 1, if distance between v and u is not known
               set it to be equal to @dist.
        Returns @true if some new shortest path between nodes has been found,
        @false otherwise.
     */
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

    /*
        Counts number of distinct variables which are either involved in
        constraint @c or are a neighbour of some variable which is involved in @c.
     */
    private int countConstraintVariablesAndTheirNeighbours(Constraint c) {
        Set<String> vars = new HashSet<>();
        for (String var : c.getVariableNames()) {
            vars.addAll(neighbourhoodList.get(var).get(1));
        }
        vars.addAll(c.getVariableNames());
        return vars.size();
    }

    /*
        A value of a constraint is defined to be equal to 0 if
        it is satisfied (under assignment @variables), p otherwise.
        Where p is equal to number of distinct variables which are either involved in the constraint or
        or neighbours of some variable involved in the constrained.

        Heuristic evaluation of a variable is defined to be a sum of violated constrains
        which involve the variable.
     */
    public int getVariableHeuristicEvaluation(String variable, Map<String, NumericValue> variables) {
        int result = 0;
        for (Constraint c : variableToConstraint.get(variable)) {
            if (!c.evaluate(variables)) {
                result += countConstraintVariablesAndTheirNeighbours(c) - 1;
            }
        }
        return result;
    }

    public Collection<Constraint> getConstraints(String variable) {
        return variableToConstraint.get(variable);
    }

    public int getNumberOfNeighbours(String node, int distance) {
        return getNeighbours(node, distance).size();
    }

    public Collection<String> getNodes() {
        return variables;
    }
}
