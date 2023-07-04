package eu.melodic.event.translate.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@lombok.Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Attribute extends NamedElement {
    private Object value;
    private ValueType valueType;
    private String unit;
    private Object minValue;
    private Object maxValue;
    private boolean minInclusive;
    private boolean maxInclusive;
}
