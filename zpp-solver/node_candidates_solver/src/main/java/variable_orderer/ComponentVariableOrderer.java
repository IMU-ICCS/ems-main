package variable_orderer;

import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpVariable;
import eu.paasage.upperware.metamodel.cp.VariableType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static eu.paasage.upperware.metamodel.cp.VariableType.*;


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
        components = new ArrayList<>();
        for (CpVariable var : constraintProblem.getCpVariables()) {
            if (!components.contains(var.getComponentId())) {
                components.add(var.getComponentId());
            }
        }
    }

    @Override
    public String getNameFromIndex(int var) {
        return indexToVariableName.get(var);
    }

    private int getComponentIndex(String name) {
        for (int i = 0; i < components.size(); i++) {
            if (components.get(i).equals(name)) {
                return i;
            }
        }
        throw new RuntimeException();
    }

    private void fillIndexToVariable() {
        for (CpVariable var : constraintProblem.getCpVariables()) {
            int componentIndex = getComponentIndex(var.getComponentId());
            int index = componentIndex + mapTypeToIndex(var.getVariableType()) - 1;
            indexToVariableName.put(index, var.getId());
        }
    }

    private int mapTypeToIndex(VariableType type) {
        switch(type) {
            case CORES:
                return 2;
            case PROVIDER:
                return 1;
            case RAM:
                return 3;
            case STORAGE:
                return 4;
            case CARDINALITY:
                return 5;
            case LATITUDE:
                return 6;
            case LONGITUDE:
                return 7;
            default:
                throw new RuntimeException("Unsupported Variable type !");
        }
    }
}
