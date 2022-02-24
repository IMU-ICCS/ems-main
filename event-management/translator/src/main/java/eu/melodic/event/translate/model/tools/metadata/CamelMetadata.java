// Copied from 'melodic-commons'
// Date: 2022-01-18
package eu.melodic.event.translate.model.tools.metadata;

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
    LATITUDE("latitude", VariableType.LATITUDE, false),
    LONGITUDE("longitude", VariableType.LONGITUDE, false),

    PROVIDER("", VariableType.PROVIDER, false),
    
    PRICE("Cost", null, true),
    UNMOVEABLE("Unmoveable", null, false);
  
    public String camelName;
    public VariableType variableType;
    public boolean onNodeCandidate;

    public static final List<CamelMetadata> VM_LIST = Collections.unmodifiableList(Arrays.asList(CORES, RAM, STORAGE, CARDINALITY, LATITUDE, LONGITUDE));
    public static final List<CamelMetadata> NC_LIST = Collections.unmodifiableList(Collections.singletonList(PRICE));

}
