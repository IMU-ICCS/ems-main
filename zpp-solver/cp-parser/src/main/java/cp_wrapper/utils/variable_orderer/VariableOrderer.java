package cp_wrapper.utils.variable_orderer;
/*
    All variables are referenced through indices. Those indices correspond to
    some variable ordering - which is maintained by VariableOrderer.
 */
public interface VariableOrderer {
    String getNameFromIndex(int var);
}
