package eu.melodic.event.translate.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@lombok.Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UnaryConstraint extends Constraint {
    private Date validity;
    private ComparisonOperatorType comparisonOperator;
    private double threshold;
}
