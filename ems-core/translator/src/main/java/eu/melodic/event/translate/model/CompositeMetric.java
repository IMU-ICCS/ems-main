package eu.melodic.event.translate.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@lombok.Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CompositeMetric extends Metric {
    private String formula;

    private List<Metric> componentMetrics;

    public boolean containsMetric(Metric m) {
        return componentMetrics.contains(m);
    }
}
