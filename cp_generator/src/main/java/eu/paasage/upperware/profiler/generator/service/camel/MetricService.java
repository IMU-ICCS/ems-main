package eu.paasage.upperware.profiler.generator.service.camel;

import camel.type.PrimitiveType;
import eu.paasage.upperware.metamodel.cp.CpMetric;

public interface MetricService {

    CpMetric createDoubleCpMetric(String id);

    CpMetric createFloatCpMetric(String id);

    CpMetric createIntegerCpMetric(String id);

    CpMetric createCpMetric(String id, PrimitiveType primitiveType);
}
