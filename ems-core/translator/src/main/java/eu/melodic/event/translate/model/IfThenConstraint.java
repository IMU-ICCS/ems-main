package eu.melodic.event.translate.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@lombok.Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class IfThenConstraint extends CompositeConstraint {
    private Constraint ifConstraint;
    private Constraint thenConstraint;
    private Constraint elseConstraint;

    public Constraint getIf() {
        return ifConstraint;
    }

    public Constraint getThen() {
        return thenConstraint;
    }

    public Constraint getElse() {
        return elseConstraint;
    }
}
