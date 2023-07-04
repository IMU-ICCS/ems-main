package eu.melodic.event.translate.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@lombok.Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MetricContext extends Feature {
    //XXX:TODO: Try to remove
    @Getter
    private String component;
    private Metric metric;
    private Schedule schedule;
    private ObjectContext objectContext;
}
