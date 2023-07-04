package eu.melodic.event.translate.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@lombok.Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LoadMetricVariable extends MetricVariable {
    public LoadMetricVariable(String name, MetricContext context) {
        setName(name);
        setMetricContext(context);
    }
}
