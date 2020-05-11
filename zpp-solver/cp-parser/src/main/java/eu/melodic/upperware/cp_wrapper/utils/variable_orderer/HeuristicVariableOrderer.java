package eu.melodic.upperware.cp_wrapper.utils.variable_orderer;

import eu.melodic.upperware.cp_wrapper.utils.constraint_graph.ConstraintGraph;
import eu.paasage.upperware.metamodel.cp.CpVariable;
import eu.paasage.upperware.metamodel.cp.VariableType;
import org.javatuples.Pair;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class HeuristicVariableOrderer implements VariableOrderer {
    private Map<Integer, String> indexToVariableName  = new HashMap<>();;
    private Map<String, Integer> nameToIndex  = new HashMap<>();
    private Map<Pair<String, VariableType>, Integer> typeToIndex = new HashMap<>();

    public HeuristicVariableOrderer(ConstraintGraph graph, Collection<CpVariable> variables) {
        orderVariablesHeuristically(graph);
        variables.forEach(variable -> typeToIndex.put(new Pair<>(variable.getComponentId(), variable.getVariableType()), nameToIndex.get(variable.getId())));

    }

    @Override
    public String getNameFromIndex(int var) {
        return indexToVariableName.get(var);
    }

    @Override
    public int getIndexFromComponentType(String componentId, VariableType type) {
        return typeToIndex.get(new Pair<>(componentId, type));
    }

    @Override
    public boolean exists(int index) {
        return indexToVariableName.get(index) != null;
    }

    /*
        For each variable record its number of constraints and number of other variables which
        appear in those constraints.
     */
    private Map<String, Pair<Integer, Integer>> evaluateVariables(ConstraintGraph constraintGraph) {
        Map<String, Pair<Integer, Integer>> nameToValue = new HashMap<>();
        for (String var : constraintGraph.getNodes()) {
            int constraints = constraintGraph.getConstraints(var).size();
            int eval = constraintGraph.getNumberOfNeighbours(var, 1);
            nameToValue.put(var, new Pair<>(constraints, eval));
        }
        return nameToValue;
    }

    private void orderVariablesHeuristically(ConstraintGraph constraintGraph) {
        Map<String, Pair<Integer, Integer>> nameToValue = evaluateVariables(constraintGraph);

        Stream<Map.Entry<String,Pair<Integer, Integer>>> sorted =
                nameToValue.entrySet().stream()
                        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue(
                                (p1, p2) -> {
                                    if (p1.getValue0().equals(p2.getValue0())) {
                                        return p1.getValue1().compareTo(p2.getValue1());
                                    } else {
                                        return p1.getValue0().compareTo(p2.getValue0());
                                    }
                                }
                        )));
        final int[] order = {0};
        sorted.forEachOrdered(p -> {
            indexToVariableName.put(order[0], p.getKey());
            nameToIndex.put(p.getKey(), order[0]);
            order[0]++;
        });
    }
}
