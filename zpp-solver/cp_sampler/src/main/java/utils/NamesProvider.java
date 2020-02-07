package utils;

import eu.melodic.upperware.nc_solver.nc_solver.variable_orderer.ComponentVariableOrderer;
import eu.paasage.upperware.metamodel.cp.ComparatorEnum;
import eu.paasage.upperware.metamodel.cp.OperatorEnum;
import eu.paasage.upperware.metamodel.cp.VariableType;

public class NamesProvider {
    public static final int VARIABLES_PER_COMPONENT = 7;
    public static String getComponentName(Integer component) {
        return "C_" + (component).toString();
    }
    public static VariableType mapIndexToType(int var) {
        switch(var) {
            case 0:
                return VariableType.PROVIDER;
            case 1:
                return VariableType.CORES;
            case 2:
                return VariableType.RAM;
            case 3:
                return VariableType.STORAGE;
            case 4:
                return VariableType.LATITUDE;
            case 5:
                return VariableType.LONGITUDE;
            case 6:
                return VariableType.CARDINALITY;
            default:
                throw new RuntimeException("Unsupported Variable type !");
        }
    }

    public static int variableNameToComponent(String name) {
        String comp = name.split("_")[1];
        return Integer.parseInt(comp);
    }

    public static VariableType variableNameToType(String name) {
        String type = name.split("_")[2];
        switch(type) {
            case "provider":
                return VariableType.PROVIDER;
            case "cores":
                return VariableType.CORES;
            case "ram":
                return VariableType.RAM;
            case "storage":
                return VariableType.STORAGE;
            case "latitude":
                return VariableType.LATITUDE;
            case "longitude":
                return VariableType.LONGITUDE;
            case "cardinality":
                return VariableType.CARDINALITY;
            default:
                throw new RuntimeException("Unsupported variable name!");
        }
    }

    public static String variableTypeToString(VariableType type) {
        switch(type) {
            case PROVIDER:
                return "provider";
            case CORES:
                return "cores";
            case RAM:
                return "ram";
            case STORAGE:
                return "storage";
            case LATITUDE:
                return "latitude";
            case LONGITUDE:
                return "longitude";
            case CARDINALITY:
                return "cardinality";
            default:
                throw new RuntimeException("Unsupported Variable type !");
        }
    }

    public static String operatorTypeToName(OperatorEnum operator) {
        switch(operator) {
            case TIMES:
                return "times";
            case MINUS:
                return "minus";
            case PLUS:
                return "plus";
            case EQ:
                return "eq";
        }
        throw new RuntimeException("Unsupported operator type!");
    }

    public static String comparatorTypeToName(ComparatorEnum comparator) {
        switch(comparator) {
            case EQUAL_TO:
                return "equalTo";
            case DIFFERENT:
                return "different";
            case GREATER_OR_EQUAL_TO:
                return "greaterOrEqualTo";
            case GREATER_THAN:
                return "greaterThan";
            case LESS_OR_EQUAL_TO:
                return "lessOrEqualTo";
            case LESS_THAN:
                return "lessThan";
        }
        throw new RuntimeException("Unsupported comparator type");
    }

    public static String getVariableName(Integer var) {
        int component = var/ VARIABLES_PER_COMPONENT;
        return getComponentName(component) + "_"
                + variableTypeToString(mapIndexToType(var % ComponentVariableOrderer.VARIABLES_PER_COMPONENT));
    }
}
