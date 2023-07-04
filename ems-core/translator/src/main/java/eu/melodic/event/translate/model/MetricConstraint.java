package eu.melodic.event.translate.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@lombok.Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MetricConstraint extends UnaryConstraint {
    //XXX:TODO: Try to remove
    private String metric;
    private MetricContext metricContext;
}
