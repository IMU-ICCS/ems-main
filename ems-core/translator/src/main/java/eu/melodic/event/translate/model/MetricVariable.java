package eu.melodic.event.translate.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@lombok.Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MetricVariable extends Metric {
    private boolean currentConfiguration;
    private Component component;
    private boolean onNodeCandidates;
    private String formula;
    private List<Metric> componentMetrics = new ArrayList<>();
    private MetricContext metricContext;

    public boolean containsMetric(Metric m) {
        return componentMetrics.contains(m);
    }
}
