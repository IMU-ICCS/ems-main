package eu.melodic.event.translate.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@lombok.Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MetricContext extends Feature {
    private Metric metric;
    private Schedule schedule;
    private ObjectContext objectContext;

    public String getComponentName() {
        if (objectContext==null) return null;
        if (objectContext.getComponent()!=null) return objectContext.getComponent().getName();
        return null;
    }

    public String getDataName() {
        if (objectContext==null) return null;
        if (objectContext.getData()!=null) return objectContext.getData().getName();
        return null;
    }
}
