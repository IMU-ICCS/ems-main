package CPWrapper.Utils;
/*
    All variables are referenced through indices. Those indices correspond to
    some variable ordering - which is maintained by VariableOrderer.
 */
public interface VariableOrderer {
    String indexToVariableName(int var);
}
