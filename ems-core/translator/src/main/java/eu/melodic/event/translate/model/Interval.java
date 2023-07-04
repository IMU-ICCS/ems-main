package eu.melodic.event.translate.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@lombok.Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
// See: eu.melodic.event.models.interfaces.Interval
public class Interval extends AbstractInterfaceRootObject {
    @ToString
    public enum UnitType { DAYS, HOURS, MINUTES, SECONDS, MILLISECONDS, MICROSECONDS, NANOSECONDS }

    private UnitType unit;
    private int period;
}
