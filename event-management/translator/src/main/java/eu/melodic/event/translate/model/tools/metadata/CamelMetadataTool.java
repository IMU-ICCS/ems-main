// Copied from 'melodic-commons'
// Date: 2022-01-18
package eu.melodic.event.translate.model.tools.metadata;

import camel.core.MeasurableAttribute;
import camel.metric.MetricTemplate;
import camel.metric.impl.MetricVariableImpl;
import camel.mms.MmsObject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.common.util.EList;

import java.util.List;
import java.util.Optional;

@Slf4j
public class CamelMetadataTool {

    public static Optional<MetricVariableImpl> findVariableFor(List<MetricVariableImpl> variables, CamelMetadata camelMetadata) {

        return variables.stream()
                .filter(variable -> !variable.isCurrentConfiguration())
                .filter(variable -> variable.getMetricTemplate()
                        .getAttribute()
                        .getAnnotations()
                            .stream()
                            .anyMatch(mmsObject -> camelMetadata.camelName.equals(mmsObject.getId())))
                .findFirst();
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
        try {
            MetricTemplate tpl = metricVariable.getMetricTemplate();
            MeasurableAttribute att = tpl==null ? null : tpl.getAttribute();
            EList<MmsObject> annList = att==null ? null : att.getAnnotations();
            if (annList==null || annList.size()==0) {
                log.error("isVariableFromGroup: Metric-variable={}, template={}, attribute={}, annotations={}", metricVariable.getName(), tpl, att, annList);
                return false;
            }
            return annList.stream().anyMatch(mmsObject -> checkAnnotation(mmsObject, metadata));
        } catch (Exception e) {
            log.error("isVariableFromGroup: EXCEPTION: Metric-variable={} -- Exception: ", metricVariable.getName(), e);
            throw e;
        }
    }

    private static boolean checkAnnotation(MmsObject mmsObject, List<CamelMetadata> metadata) {
        return metadata.stream().anyMatch(camelMetadata -> camelMetadata.camelName.equals(mmsObject.getId()));
    }

    private static String getAnnotationOfMetricVariable(MetricVariableImpl metricVariable) {
        EList<MmsObject> annotations = metricVariable.getMetricTemplate().getAttribute().getAnnotations();
        if (annotations.isEmpty()) {
            log.error("Metric Variable {} has no annotation defined", metricVariable.getName());
            throw new IllegalArgumentException("Missing annotation in Metric Variable " + metricVariable.getName());
            //log.warn("Metric Variable {} has not defined annotation, returning empty String", metricVariable.getName());
            //return "";
        }
        String annotation = annotations.get(0).getId();
        log.debug("Found annotation {} for metric: {}", metricVariable.getName(), annotation);
        return annotation;
    }
    
}
