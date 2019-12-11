package CPComponents;

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
