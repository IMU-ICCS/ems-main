package cp_wrapper.utils.variable_orderer;

import eu.paasage.upperware.metamodel.cp.VariableType;

/*
    All variables are referenced through indices. Those indices correspond to
    some variable ordering - which is maintained by VariableOrderer.
 */
public interface VariableOrderer {
    String getNameFromIndex(int var);

    int getIndexFromComponentType(String component, VariableType type);
    /*
        True if @index represents some variable
     */
    boolean exists(int index);
}
