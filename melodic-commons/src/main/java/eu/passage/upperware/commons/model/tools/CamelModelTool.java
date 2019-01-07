package eu.passage.upperware.commons.model.tools;

import camel.core.CamelModel;
import camel.deployment.SoftwareComponent;
import camel.metric.CompositeMetric;
import camel.metric.Metric;
import camel.metric.MetricModel;
import camel.metric.RawMetric;
import camel.metric.impl.MetricTypeModelImpl;
import camel.metric.impl.MetricVariableImpl;
import camel.type.PrimitiveType;
import eu.passage.upperware.commons.model.tools.metadata.CamelMetadata;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.common.util.EList;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CamelModelTool {

    public static Optional<MetricVariableImpl> getVariable(CamelModel camelModel, String variableName) {
        return getVariables(camelModel).stream().filter(metricVariable -> metricVariable.getName().equals(variableName)).findFirst();
    }

    public static Optional<RawMetric> getRawMetric(CamelModel camelModel, String metricName) {
        return getRawMetrics(camelModel).stream().filter(metricVariable -> metricVariable.getName().equals(metricName)).findFirst();
    }

    public static Optional<CompositeMetric> getCompositeMetric(CamelModel camelModel, String metricName) {
        return getCompositeMetrics(camelModel).stream().filter(metricVariable -> metricVariable.getName().equals(metricName)).findFirst();
    }

    private static List<MetricVariableImpl> getVariables(CamelModel camelModel) {
        return getAllMetrics(camelModel)
                .stream()
                .filter(metricModel -> metricModel instanceof MetricVariableImpl)
                .map(metricModel -> (MetricVariableImpl) metricModel)
                .collect(Collectors.toList());
    }

    private List<MetricVariableImpl> getVariables(CamelModel camelModel, String componentName) {
        return getVariables(camelModel)
                .stream()
                .filter(metricVariable -> metricVariable.getComponent() != null && componentName.equals((metricVariable.getComponent()).getName()))
                .collect(Collectors.toList());
    }

    private static List<RawMetric> getRawMetrics(CamelModel camelModel) {
        return getAllMetrics(camelModel)
                .stream()
                .filter(metric -> metric instanceof RawMetric)
                .map(metricModel -> (RawMetric) metricModel)
                .collect(Collectors.toList());
    }

    private static List<CompositeMetric> getCompositeMetrics(CamelModel camelModel) {
        return getAllMetrics(camelModel)
                .stream()
                .filter(metric -> metric instanceof CompositeMetric)
                .map(metricModel -> (CompositeMetric) metricModel)
                .collect(Collectors.toList());
    }

    private static List<Metric> getAllMetrics(CamelModel camelModel){
        EList<MetricModel> metricModels = camelModel.getMetricModels();
        if (CollectionUtils.isEmpty(metricModels)){
            return Collections.emptyList();
        }

        return metricModels.stream()
                .map(metricModel -> ((MetricTypeModelImpl) metricModel).getMetrics())
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public static PrimitiveType getType(Metric metricVariable) {
        return metricVariable.getMetricTemplate().getValueType().getPrimitiveType();
    }

    public static boolean isUnmoveableComponent(SoftwareComponent softwareComponent) {
        return !softwareComponent.getAnnotations().isEmpty()
                && softwareComponent.getAnnotations().get(0).getId().equals(CamelMetadata.UNMOVEABLE.camelName);
    }
}
