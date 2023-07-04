package eu.melodic.event.translate.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@lombok.Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ServiceLevelObjective extends Requirement { // HardRequirement
    private Constraint constraint;
    private Event violationEvent;
}
