package cp_wrapper.utils.variable_orderer;

import cp_wrapper.utils.ConstraintGraph;
import org.javatuples.Pair;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class HeuristicVariableOrderer implements VariableOrderer {
    private Map<Integer, String> indexToVariableName;

    public HeuristicVariableOrderer(ConstraintGraph graph) {
        indexToVariableName = new HashMap<>();
        orderVariablesHeuristically(graph);
    }

    @Override
    public String getNameFromIndex(int var) {
        return indexToVariableName.get(var);
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
            nameToValue.put(var, new Pair(constraints, eval));
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
            order[0]++;
        });
    }
}
