package eu.paasage.upperware.profiler.generator.service.camel;

import camel.type.PrimitiveType;
import eu.paasage.upperware.metamodel.cp.MetricVariable;

public interface MetricService {

    MetricVariable createDoubleMetricVariable(String id);

    MetricVariable createFloatMetricVariable(String id);

    MetricVariable createIntegerMetricVariable(String id);

    MetricVariable createMetricVariable(String id, PrimitiveType primitiveType);
}
