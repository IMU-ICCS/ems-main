package eu.passage.upperware.commons.model.tools.metadata;

import eu.paasage.upperware.metamodel.cp.VariableType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CamelMetadata {

    CORES("CPU", VariableType.CORES, false, false),
    RAM("RAM", VariableType.RAM, false, false),
    STORAGE("Storage", VariableType.STORAGE, false, false),
    CARDINALITY("Cardinality", VariableType.CARDINALITY, false, false),
    LATITUDE("latitude", VariableType.LATITUDE, false, false),
    LONGITUDE("longitude", VariableType.LONGITUDE, false, false),

    PROVIDER("", VariableType.PROVIDER, false, false),

    PRICE("Cost", null, true, false),

    UNMOVEABLE("Unmoveable", null, false, false),

    AFFINITY_AWARENESS("AffinityAwareness", null, false, true),
    DATA_CENTRE_AWARENESS("DataCentreAwareness", null, false, true),
    SOURCE_AWARENESS("SourceAwareness", null,false, true),
    DLMS_TOTAL_UTILITY("DLMSTotalUtility", null, false, true);

    public String camelName;
    public VariableType variableType;
    public boolean onNodeCandidate;
    public boolean dlmsUtility;


    public static final List<CamelMetadata> VM_LIST = Collections.unmodifiableList(Arrays.asList(CORES, RAM, STORAGE, CARDINALITY, LATITUDE, LONGITUDE));
    public static final List<CamelMetadata> NC_LIST = Collections.unmodifiableList(Collections.singletonList(PRICE));
    public static final List<CamelMetadata> DLMS_LIST = Collections.unmodifiableList(Arrays.asList(AFFINITY_AWARENESS, DATA_CENTRE_AWARENESS, SOURCE_AWARENESS, DLMS_TOTAL_UTILITY));

}
