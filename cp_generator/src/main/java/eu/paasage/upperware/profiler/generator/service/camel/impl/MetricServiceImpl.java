package eu.paasage.upperware.profiler.generator.service.camel.impl;

import eu.paasage.upperware.metamodel.cp.CpFactory;
import eu.paasage.upperware.metamodel.cp.MetricVariable;
import eu.paasage.upperware.metamodel.types.BasicTypeEnum;
import eu.paasage.upperware.profiler.generator.service.camel.MetricService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MetricServiceImpl implements MetricService {

    private CpFactory cpFactory;

    @Override
    public MetricVariable createMetricVariable(String id, BasicTypeEnum basicTypeEnum) {
        eu.paasage.upperware.metamodel.cp.MetricVariable metricVariable = cpFactory.createMetricVariable();
        metricVariable.setId(id);
        metricVariable.setType(basicTypeEnum);
        return metricVariable;
    }

    @Override
    public MetricVariable createDoubleMetricVariable(String id) {
        return createMetricVariable(id, BasicTypeEnum.DOUBLE);
    }

    @Override
    public MetricVariable createIntegerMetricVariable(String id) {
        return createMetricVariable(id, BasicTypeEnum.INTEGER);
    }

}
