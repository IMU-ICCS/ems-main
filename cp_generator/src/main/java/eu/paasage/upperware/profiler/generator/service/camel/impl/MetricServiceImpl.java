package eu.paasage.upperware.profiler.generator.service.camel.impl;

import camel.type.PrimitiveType;
import eu.paasage.upperware.metamodel.cp.CpFactory;
import eu.paasage.upperware.metamodel.cp.CpMetric;
import eu.paasage.upperware.metamodel.types.BasicTypeEnum;
import eu.paasage.upperware.profiler.generator.error.GeneratorException;
import eu.paasage.upperware.profiler.generator.service.camel.MetricService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MetricServiceImpl implements MetricService {

    private CpFactory cpFactory;

    @Override
    public CpMetric createDoubleCpMetric(String id) {
        return createMetricVariable(id, BasicTypeEnum.DOUBLE);
    }

    @Override
    public CpMetric createFloatCpMetric(String id) {
        return createMetricVariable(id, BasicTypeEnum.FLOAT);
    }

    @Override
    public CpMetric createIntegerCpMetric(String id) {
        return createMetricVariable(id, BasicTypeEnum.INTEGER);
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

    private CpMetric createMetricVariable(String id, BasicTypeEnum basicTypeEnum) {
        eu.paasage.upperware.metamodel.cp.CpMetric metricVariable = cpFactory.createCpMetric();
        metricVariable.setId(id);
        metricVariable.setType(basicTypeEnum);
        return metricVariable;
    }

}
