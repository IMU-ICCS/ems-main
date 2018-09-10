package eu.passage.upperware.commons.model.tools.metadata;

import eu.paasage.upperware.metamodel.cp.VariableType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CamelMetadata {

    CPU("CPU", VariableType.CPU, false),
    CORES("CPU", VariableType.CORES, false),
    RAM("RAM", VariableType.CPU, false),
    STORAGE("Storage", VariableType.STORAGE, false),
    CARDINALITY("m_cardinality", VariableType.CARDINALITY, false),

    PROVIDER("", VariableType.PROVIDER, false),

    PRICE("m_price", null, true);

    public String camelName;
    public VariableType variableType;
    public boolean onNodeCandidate;


    public static final List<CamelMetadata> VM_LIST = Collections.unmodifiableList(Arrays.asList(CPU, CORES, RAM, STORAGE, CARDINALITY));
    public static final List<CamelMetadata> NC_LIST = Collections.unmodifiableList(Collections.singletonList(PRICE));

}
