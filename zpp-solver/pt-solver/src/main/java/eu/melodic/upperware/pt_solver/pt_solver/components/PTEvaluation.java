package eu.melodic.upperware.pt_solver.pt_solver.components;
/*
    Wrapper over double type - required by James library
 */
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.jamesframework.core.problems.objectives.evaluations.Evaluation;

@AllArgsConstructor
@EqualsAndHashCode
public class PTEvaluation implements Evaluation {
    private double value;
    @Override
    public double getValue() {
        return value;
    }
}
