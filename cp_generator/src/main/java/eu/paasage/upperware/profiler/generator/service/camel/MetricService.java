package eu.paasage.upperware.profiler.generator.service.camel;

import eu.paasage.upperware.metamodel.cp.MetricVariable;
import eu.paasage.upperware.metamodel.types.BasicTypeEnum;

public interface MetricService {

    MetricVariable createMetricVariable(String id, BasicTypeEnum basicTypeEnum);

    MetricVariable createDoubleMetricVariable(String id);

    MetricVariable createIntegerMetricVariable(String id);
}
