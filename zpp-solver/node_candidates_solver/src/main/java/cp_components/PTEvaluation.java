package cp_components;

import lombok.AllArgsConstructor;
import org.jamesframework.core.problems.objectives.evaluations.Evaluation;
/*
    Wrapper over double type - required by James library
 */
        import lombok.AllArgsConstructor;
        import org.jamesframework.core.problems.objectives.evaluations.Evaluation;

@AllArgsConstructor
public class PTEvaluation implements Evaluation {
    private double value;
    @Override
    public double getValue() {
        return value;
    }
}
