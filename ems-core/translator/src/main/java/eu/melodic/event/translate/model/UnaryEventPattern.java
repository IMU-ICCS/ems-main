package eu.melodic.event.translate.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@lombok.Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UnaryEventPattern extends EventPattern {
    private Event event;
    private double occurrenceNum;
    private UnaryPatternOperatorType operator;
}
