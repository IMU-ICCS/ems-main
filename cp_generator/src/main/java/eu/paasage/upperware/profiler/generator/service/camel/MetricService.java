package eu.paasage.upperware.profiler.generator.service.camel;

import camel.metric.Metric;
import camel.type.PrimitiveType;
import eu.paasage.upperware.metamodel.cp.CpMetric;

import java.util.List;
import java.util.Optional;

public interface MetricService {

    CpMetric createDoubleCpMetric(String id);

    CpMetric createFloatCpMetric(String id);

    CpMetric createIntegerCpMetric(String id);

    CpMetric createCpMetric(String id, PrimitiveType primitiveType);

    Optional<CpMetric> getByName(List<CpMetric> metrics, String name);

    CpMetric createCpMetric(Metric metric);
}
