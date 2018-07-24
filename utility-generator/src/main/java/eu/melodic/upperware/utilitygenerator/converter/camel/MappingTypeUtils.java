/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */
package eu.melodic.upperware.utilitygenerator.converter.camel;

import camel.metric.impl.MetricVariableImpl;
import eu.melodic.upperware.utilitygenerator.model.function.NodeCandidatesAttributesType;
import eu.paasage.upperware.metamodel.cp.VariableType;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
public class MappingTypeUtils {


    public static VariableType getVariableType(MetricVariableImpl metric) {
        String annotation = getAnnotationOfMetricVariable(metric);
        VariableType resultType = findMatchingVariableType(metric)
                .orElseThrow(() -> new IllegalArgumentException("Wrong annotation: " + annotation + "- Melodic does not support that"));
        log.debug("Found annotation: {} for variable: {},  mapped to variable type: {}", annotation, metric.getName(), resultType);
        return resultType;
    }

    static NodeCandidatesAttributesType getNodeCandidateAttributeType(MetricVariableImpl metric) {
        String annotation = getAnnotationOfMetricVariable(metric);
        NodeCandidatesAttributesType resultType = findMatchingNodeCandidatesAttributeType(metric)
                .orElseThrow(() -> new IllegalArgumentException("Wrong annotation: " + annotation + "- Melodic does not support that type"));
        log.debug("Found annotation: {} for variable {}, mapped to node candidate attribute type: {}", annotation, metric.getName(), resultType);
        return resultType;
    }

    static boolean hasTypeOfVariable(MetricVariableImpl metric) {
        Optional<VariableType> optionalType = findMatchingVariableType(metric);
        log.debug("{} is {} a type of variable type", metric.getName(), optionalType.isPresent());
        return optionalType.isPresent();
    }

    static boolean hasTypeOfNodeCandidateAttribute(MetricVariableImpl metric) {
        Optional<NodeCandidatesAttributesType> optionalType = findMatchingNodeCandidatesAttributeType(metric);
        log.debug("{} is {} a type of node candidate attribute", metric.getName(), optionalType.isPresent());
        return optionalType.isPresent();
    }

    private static String getAnnotationOfMetricVariable(MetricVariableImpl metricVariable) {
        String annotation = metricVariable.getMetricTemplate().getAttribute().getAnnotations().get(0).getId();
        log.debug("Found annotation {} for metric: {}", metricVariable.getName(), annotation);
        return annotation;
    }

    private static Optional<NodeCandidatesAttributesType> findMatchingNodeCandidatesAttributeType(MetricVariableImpl metric) {
        String annotation = getAnnotationOfMetricVariable(metric);
        return Stream.of(NodeCandidatesAttributesType.values())
                .filter(type -> annotation.contains(type.name().toLowerCase()))
                .findAny();
    }

    private static Optional<VariableType> findMatchingVariableType(MetricVariableImpl metric) {
        String annotation = getAnnotationOfMetricVariable(metric);
        return Stream.of(VariableType.values())
                .filter(type -> annotation.contains(type.getName()))
                .findAny();
    }


}
