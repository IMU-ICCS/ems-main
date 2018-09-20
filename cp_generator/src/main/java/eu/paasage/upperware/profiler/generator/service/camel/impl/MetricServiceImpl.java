package eu.paasage.upperware.profiler.generator.service.camel.impl;

import camel.type.PrimitiveType;
import eu.paasage.upperware.metamodel.cp.ComposedExpression;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpFactory;
import eu.paasage.upperware.metamodel.cp.CpMetric;
import eu.paasage.upperware.metamodel.types.BasicTypeEnum;
import eu.paasage.upperware.metamodel.types.NumericValueUpperware;
import eu.paasage.upperware.profiler.generator.error.GeneratorException;
import eu.paasage.upperware.profiler.generator.service.camel.MetricService;
import eu.paasage.upperware.profiler.generator.service.camel.TypesFactoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MetricServiceImpl implements MetricService {

    private CpFactory cpFactory;
    private TypesFactoryService typesFactoryService;

    @Override
    public CpMetric createDoubleCpMetric(String id) {
        return createMetricVariable(id, BasicTypeEnum.DOUBLE, typesFactoryService.getDoubleValueUpperware(1.0d));
    }

    @Override
    public CpMetric createFloatCpMetric(String id) {
        return createMetricVariable(id, BasicTypeEnum.FLOAT, typesFactoryService.getFloatValueUpperware(1.0f));
    }

    @Override
    public CpMetric createIntegerCpMetric(String id) {
        return createMetricVariable(id, BasicTypeEnum.INTEGER, typesFactoryService.getIntegerValueUpperware(1));
    }

    @Override
    public CpMetric createCpMetric(String id, PrimitiveType primitiveType) {
        Objects.requireNonNull(primitiveType, "Could not create CpMetric for null primitiveType");
        switch (primitiveType) {
            case INT_TYPE:
                return createIntegerCpMetric(id);
            case FLOAT_TYPE:
                return createDoubleCpMetric(id);
            case DOUBLE_TYPE:
                return createFloatCpMetric(id);
        }
        throw new GeneratorException("Could not create Metric for " + primitiveType);
    }

    @Override
    public Optional<CpMetric> getByName(List<CpMetric> metrics, String name) {
        return metrics.stream()
                .filter(metric -> metric.getId().equals(name))
                .findFirst();
    }

    private CpMetric createMetricVariable(String id, BasicTypeEnum basicTypeEnum, NumericValueUpperware numericValueUpperware) {
        CpMetric metricVariable = cpFactory.createCpMetric();
        metricVariable.setId(id);
        metricVariable.setType(basicTypeEnum);
        metricVariable.setValue(numericValueUpperware);
        return metricVariable;
    }

}
