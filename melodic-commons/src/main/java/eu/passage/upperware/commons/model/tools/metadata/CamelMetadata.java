package eu.passage.upperware.commons.model.tools.metadata;

import eu.paasage.upperware.metamodel.cp.VariableType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CamelMetadata {

    CORES("CPU", VariableType.CORES, false),
    RAM("RAM", VariableType.RAM, false),
    STORAGE("Storage", VariableType.STORAGE, false),
    CARDINALITY("Cardinality", VariableType.CARDINALITY, false),

    PROVIDER("", VariableType.PROVIDER, false),

    PRICE("Cost", null, true),

    UNMOVEABLE("Unmoveable", null, false),


    SPARK_CLASS_NAME("ClassName", null, false),
    APP_ARGUMENTS("ApplicationArguments", null, false), //todo w Camelu nie ma adnotacji a w MMS jest taka, spytać Marty
    SPARK_ARGUMENTS("SPARKArguments", null, false),
    SPARK_CONFIGURATION("SPARKConfiguration", null, false);

    public String camelName;
    public VariableType variableType;
    public boolean onNodeCandidate;


    public static final List<CamelMetadata> VM_LIST = Collections.unmodifiableList(Arrays.asList(CORES, RAM, STORAGE, CARDINALITY));
    public static final List<CamelMetadata> NC_LIST = Collections.unmodifiableList(Collections.singletonList(PRICE));

}
