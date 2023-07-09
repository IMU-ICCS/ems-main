package eu.melodic.event.translate.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@lombok.Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CompositeMetric extends Metric {
    private String formula;
    @Builder.Default
    private List<Metric> componentMetrics = new ArrayList<>();

    public boolean containsMetric(Metric m) {
        return componentMetrics.contains(m);
    }
}
