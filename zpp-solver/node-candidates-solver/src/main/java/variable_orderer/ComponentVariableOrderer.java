package variable_orderer;

import cp_wrapper.utils.variable_orderer.VariableOrderer;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpVariable;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
    Components are ordered non-deterministically.
    Variables are ordered according to pairs
    (componentIndex, VariableTypeOrder). Where VariableTypeOrder is defined in
    VariableTypeOrderer class.
    A word of caution - even if given variable type (for a certain component) does not exist
    in the model, it still affects orders of other variables. For instance
    if we have only one component - A - with variables A_provider, A_cores, A_storage, then
    A_storage will get index 3, even though A_Ram is not present.
 */
public class ComponentVariableOrderer implements VariableOrderer {
    @Getter
    private List<String> components;
    private final int variablesPerComponent = 7;
    private Map<Integer, String> indexToVariableName;

    public ComponentVariableOrderer(ConstraintProblem cp) {
        fillComponents(cp);
        fillIndexToVariable(cp);
    }
    /*
        True if variable with a given index exists in the model
     */
    public boolean exists(int index) {
        return indexToVariableName.get(index) != null;
    }

    private void fillComponents(ConstraintProblem cp) {
        components = new ArrayList<>();
        for (CpVariable var : cp.getCpVariables()) {
            if (!components.contains(var.getComponentId())) {
                components.add(var.getComponentId());
            }
        }
    }

    private int getComponentIndex(String name) {
        for (int i = 0; i < components.size(); i++) {
            if (components.get(i).equals(name)) {
                return variablesPerComponent * i;
            }
        }
        throw new RuntimeException();
    }

    private void fillIndexToVariable(ConstraintProblem cp) {
        indexToVariableName = new HashMap<>();
        for (CpVariable var : cp.getCpVariables()) {
            int componentIndex = getComponentIndex(var.getComponentId());
            int index = componentIndex + VariableTypeOrderer.mapTypeToIndex(var.getVariableType());
            indexToVariableName.put(index, var.getId());
        }
    }

    @Override
    public String getNameFromIndex(int var) {
        return indexToVariableName.get(var);
    }
}
