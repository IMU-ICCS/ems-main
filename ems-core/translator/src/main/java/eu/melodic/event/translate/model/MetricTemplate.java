package eu.melodic.event.translate.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@lombok.Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MetricTemplate extends Feature {
    private ValueType valueType;
    private short valueDirection;
    private String unit;
    private MeasurableAttribute attribute;
}
