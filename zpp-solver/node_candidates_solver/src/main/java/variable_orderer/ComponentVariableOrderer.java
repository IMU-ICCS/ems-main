package variable_orderer;

import cp_wrapper.utils.variable_orderer.VariableOrderer;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpVariable;
import eu.paasage.upperware.metamodel.cp.VariableType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ComponentVariableOrderer implements VariableOrderer {
    private ConstraintProblem constraintProblem;
    private List<String> components;
    private final int variablesPerComponent = 7;
    private Map<Integer, String> indexToVariableName;

    public ComponentVariableOrderer(ConstraintProblem cp) {
        constraintProblem = cp;
        fillIndexToVariable();
    }

    private void fillComponents() {
        components = new ArrayList<String>();
        for (CpVariable var : constraintProblem.getCpVariables()) {
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

    private void fillIndexToVariable() {
        for (CpVariable var : constraintProblem.getCpVariables()) {
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
