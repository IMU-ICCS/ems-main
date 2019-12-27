package variable_orderer;

import eu.paasage.upperware.metamodel.cp.VariableType;

public class VariableTypeOrderer {
    public static final int variablesPerComponent = 7;
    public static int mapTypeToIndex(VariableType type) {
        switch(type) {
            case PROVIDER:
                return 0;
            case CORES:
                return 1;
            case RAM:
                return 2;
            case STORAGE:
                return 3;
            case CARDINALITY:
                return 4;
            case LATITUDE:
                return 5;
            case LONGITUDE:
                return 6;
            default:
                throw new RuntimeException("Unsupported Variable type !");
        }
    }
}
