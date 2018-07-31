package eu.paasage.upperware.profiler.generator.service.camel.impl;

import camel.type.PrimitiveType;
import eu.paasage.upperware.metamodel.cp.CpFactory;
import eu.paasage.upperware.metamodel.cp.MetricVariable;
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
    public MetricVariable createDoubleMetricVariable(String id) {
        return createMetricVariable(id, BasicTypeEnum.DOUBLE);
    }

    @Override
    public MetricVariable createFloatMetricVariable(String id) {
        return createMetricVariable(id, BasicTypeEnum.FLOAT);
    }

    @Override
    public MetricVariable createIntegerMetricVariable(String id) {
        return createMetricVariable(id, BasicTypeEnum.INTEGER);
    }

    @Override
    public MetricVariable createMetricVariable(String id, PrimitiveType primitiveType) {
        Objects.requireNonNull(primitiveType, "Could not create Metric for null primitiveType");

        switch (primitiveType) {
            case INT_TYPE:
                return createIntegerMetricVariable(id);
            case FLOAT_TYPE:
                return createDoubleMetricVariable(id);
            case DOUBLE_TYPE:
                return createFloatMetricVariable(id);
        }
        throw new GeneratorException("Could not create Metric for " + primitiveType);
    }

    private MetricVariable createMetricVariable(String id, BasicTypeEnum basicTypeEnum) {
        eu.paasage.upperware.metamodel.cp.MetricVariable metricVariable = cpFactory.createMetricVariable();
        metricVariable.setId(id);
        metricVariable.setType(basicTypeEnum);
        return metricVariable;
    }

}
