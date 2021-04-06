package eu.melodic.upperware.nc_solver.nc_solver.cp_components;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jamesframework.core.problems.objectives.evaluations.Evaluation;
/*
    Wrapper over double type - required by James library
 */
@AllArgsConstructor
@Data
public class PTEvaluation implements Evaluation {
    private double value;
    @Override
    public double getValue() {
        return value;
    }
}
