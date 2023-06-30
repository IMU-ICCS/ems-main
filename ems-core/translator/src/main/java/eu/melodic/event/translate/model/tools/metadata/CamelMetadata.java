// Copied from 'melodic-commons'
// Date: 2022-01-18
package eu.melodic.event.translate.model.tools.metadata;

//XXX:SPLIT: import eu.paasage.upperware.metamodel.cp.VariableType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CamelMetadata {

    CORES("CPU", /*XXX:SPLIT: VariableType.CORES,*/ false),
    RAM("RAM", /*XXX:SPLIT: VariableType.RAM,*/ false),
    STORAGE("Storage", /*XXX:SPLIT: VariableType.STORAGE,*/ false),
    CARDINALITY("Cardinality", /*XXX:SPLIT: VariableType.CARDINALITY,*/ false),
    LATITUDE("latitude", /*XXX:SPLIT: VariableType.LATITUDE,*/ false),
    LONGITUDE("longitude", /*XXX:SPLIT: VariableType.LONGITUDE,*/ false),

    PROVIDER("", /*XXX:SPLIT: VariableType.PROVIDER,*/ false),
    
    PRICE("Cost", /*XXX:SPLIT: null,*/ true),
    UNMOVEABLE("Unmoveable", /*XXX:SPLIT: null,*/ false);
  
    public final String camelName;
//XXX:SPLIT:    public VariableType variableType;
    public final boolean onNodeCandidate;

    public static final List<CamelMetadata> VM_LIST = Collections.unmodifiableList(Arrays.asList(CORES, RAM, STORAGE, CARDINALITY, LATITUDE, LONGITUDE));
    public static final List<CamelMetadata> NC_LIST = Collections.unmodifiableList(Collections.singletonList(PRICE));

}
