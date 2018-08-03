package eu.passage.upperware.commons.model.tools.metadata;

import camel.metric.impl.MetricVariableImpl;
import camel.mms.MmsObject;

import java.util.List;
import java.util.Optional;

public class CamelMetadataTool {

    public static Optional<MetricVariableImpl> findVariableFor(List<MetricVariableImpl> variables, CamelMetadata camelMetadata) {

        for (MetricVariableImpl variable : variables) {
            boolean hasAnnotation = variable.getMetricTemplate().getAttribute().getAnnotations().stream().anyMatch(mmsObject -> camelMetadata.camelName.equals(mmsObject.getId()));

            if (hasAnnotation){
                return Optional.of(variable);
            }
        }
        return Optional.empty();
    }

    public static boolean isFromVariable(MetricVariableImpl metricVariable){
        return isVariableFromGroup(metricVariable, CamelMetadata.VM_LIST);
    }

    public static boolean isFromNodeCandidate(MetricVariableImpl metricVariable){
        return isVariableFromGroup(metricVariable, CamelMetadata.NC_LIST);
    }

    private static boolean isVariableFromGroup(MetricVariableImpl metricVariable, List<CamelMetadata> metadata){
        return metricVariable.getMetricTemplate().getAttribute().getAnnotations().stream().anyMatch(mmsObject -> checkAnnotation(mmsObject, metadata));
    }

    private static boolean checkAnnotation(MmsObject mmsObject, List<CamelMetadata> metadata) {
        return metadata.stream().anyMatch(camelMetadata -> camelMetadata.camelName.equals(mmsObject.getId()));
    }

}
