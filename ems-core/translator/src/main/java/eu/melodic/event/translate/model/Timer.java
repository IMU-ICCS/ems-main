package eu.melodic.event.translate.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@lombok.Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Timer extends Feature {
    private TimerType type;
    private int timeValue;
    private int maxOccurrenceNum;
    private String unit;
}
