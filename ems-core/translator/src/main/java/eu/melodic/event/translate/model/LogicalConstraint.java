package eu.melodic.event.translate.model;

import eu.melodic.event.translate.dag.DAGNode;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@lombok.Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LogicalConstraint extends CompositeConstraint {
    private LogicalOperatorType logicalOperator;
    private List<Constraint> constraints;

    //XXX:TODO: Try to remove
    private List<DAGNode> constraintNodes;

    public boolean containsConstraint(@NonNull Constraint c) {
        return constraints.contains(c);
    }
}
