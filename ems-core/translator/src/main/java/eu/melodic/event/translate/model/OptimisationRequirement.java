package eu.melodic.event.translate.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@lombok.Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OptimisationRequirement extends Requirement {   // SoftRequirement
    private double priority;
    private MetricContext metricContext;
    private MetricVariable metricVariable;
    private boolean minimise;
}
