package cp_wrapper.utils.variable_orderer;

import eu.paasage.upperware.metamodel.cp.CpVariable;
import eu.paasage.upperware.metamodel.cp.VariableType;
import org.javatuples.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class RandomVariableOrderer implements VariableOrderer {
    private Map<Integer, String> indexToVariableName = new HashMap<>();
    private Map<Pair<String, VariableType>, Integer> typeToIndex = new HashMap<>();
    private Random random = new Random();

    public RandomVariableOrderer(Collection<CpVariable> variables) {
        Collection<CpVariable> providerCardinalityVariables = extractCardinalityAndProvider(variables);
        indexToVariableName = orderRandomly(providerCardinalityVariables, 0, indexToVariableName);
        indexToVariableName = orderRandomly(deleteCardinalityAndProvider(variables), providerCardinalityVariables.size(), indexToVariableName);
        HashMap<String, Integer> nameToIndex = new HashMap<>();
        indexToVariableName.forEach((key, value) -> nameToIndex.put(value, key));
        variables.forEach(variable -> typeToIndex.put(new Pair<>(variable.getComponentId(), variable.getVariableType()), nameToIndex.get(variable.getId())));
    }

    @Override
    public String getNameFromIndex(int var) {
        return indexToVariableName.get(var);
    }

    @Override
    public int getIndexFromComponentType(String componentId, VariableType type) {
        if (!typeToIndex.containsKey(new Pair<>(componentId, type))) {
            return -1;
        }
        return typeToIndex.get(new Pair<>(componentId, type));
    }

    @Override
    public boolean exists(int index) {
        return indexToVariableName.size() > index;
    }

    private Collection<CpVariable> extractCardinalityAndProvider(Collection<CpVariable> variables) {
        return variables.stream()
                .filter(variable -> variable.getVariableType() == VariableType.PROVIDER || variable.getVariableType() == VariableType.CARDINALITY)
                .collect(Collectors.toList());
    }

    private Collection<CpVariable> deleteCardinalityAndProvider(Collection<CpVariable> variables) {
        return variables.stream()
                .filter(variable -> variable.getVariableType() != VariableType.PROVIDER && variable.getVariableType() != VariableType.CARDINALITY)
                .collect(Collectors.toList());
    }

    private Map<Integer, String> orderRandomly(Collection<CpVariable> variables, int startingIndex, Map<Integer, String> indexToVariable) {
            List<CpVariable> variablesReordered = new ArrayList<>(variables);
            Collections.shuffle(variablesReordered, random);
            for (int i = 0; i < variablesReordered.size(); i++) {
                indexToVariable.put(i + startingIndex, variablesReordered.get(i).getId());
            }
            return indexToVariable;
    }
}
