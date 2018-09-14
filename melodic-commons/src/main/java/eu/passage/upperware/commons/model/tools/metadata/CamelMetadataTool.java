package eu.passage.upperware.commons.model.tools.metadata;

import camel.metric.impl.MetricVariableImpl;
import camel.mms.MmsObject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.common.util.EList;

import java.util.List;
import java.util.Optional;

@Slf4j
public class CamelMetadataTool {

    public static Optional<MetricVariableImpl> findVariableFor(List<MetricVariableImpl> variables, CamelMetadata camelMetadata) {

        for (MetricVariableImpl variable : variables) {
            boolean hasAnnotation = variable.getMetricTemplate().getAttribute().getAnnotations().stream().anyMatch(mmsObject -> camelMetadata.camelName.equals(mmsObject.getId()));

            if (hasAnnotation) {
                return Optional.of(variable);
            }
        }
        return Optional.empty();
    }

    public static boolean isFromVariable(MetricVariableImpl metricVariable) {
        return isVariableFromGroup(metricVariable, CamelMetadata.VM_LIST);
    }

    public static boolean isFromNodeCandidate(MetricVariableImpl metricVariable) {
        return isVariableFromGroup(metricVariable, CamelMetadata.NC_LIST);
    }

    public static CamelMetadata findVariableType(MetricVariableImpl metricVariable) {
        return findCamelMetaDataType(metricVariable, CamelMetadata.VM_LIST);
    }

    public static CamelMetadata findNodeCandidateAttributeType(MetricVariableImpl metricVariable) {
        return findCamelMetaDataType(metricVariable, CamelMetadata.NC_LIST);
    }

    private static CamelMetadata findCamelMetaDataType(MetricVariableImpl metricVariable, List<CamelMetadata> metadataList) {
        String annotation = getAnnotationOfMetricVariable(metricVariable);
        return metadataList.stream().filter(type -> type.camelName.equals(annotation)).findAny().orElseThrow(
                () -> new IllegalArgumentException("Wrong annotation: " + annotation + " is not a supported type"));
    }

    private static boolean isVariableFromGroup(MetricVariableImpl metricVariable, List<CamelMetadata> metadata) {
        return metricVariable.getMetricTemplate().getAttribute().getAnnotations().stream().anyMatch(mmsObject -> checkAnnotation(mmsObject, metadata));
    }

    private static boolean checkAnnotation(MmsObject mmsObject, List<CamelMetadata> metadata) {
        return metadata.stream().anyMatch(camelMetadata -> camelMetadata.camelName.equals(mmsObject.getId()));
    }

    private static String getAnnotationOfMetricVariable(MetricVariableImpl metricVariable) {
        EList<MmsObject> annotations = metricVariable.getMetricTemplate().getAttribute().getAnnotations();
        if (annotations.isEmpty()) {
            log.warn("Metric Variable {} has not definied annotation, returning empty String", metricVariable.getName());
            return "";
        }
        String annotation = annotations.get(0).getId();
        log.debug("Found annotation {} for metric: {}", metricVariable.getName(), annotation);
        return annotation;
    }
    
}
