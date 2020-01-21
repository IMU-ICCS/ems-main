package nc_solver.cp_components;

import lombok.extern.slf4j.Slf4j;
import nc_solver.nc_wrapper.NCWrapper;
import org.jamesframework.core.problems.objectives.Objective;
import org.jamesframework.core.problems.objectives.evaluations.Evaluation;
@Slf4j
public class PTObjective implements Objective<PTSolution, NCWrapper> {
    @Override
    public Evaluation evaluate(PTSolution cpSolution, NCWrapper ncWrapper) {
        Evaluation evaluation = ncWrapper.evaluate(cpSolution.getVarAssignments());
        log.info("Solution utility: " + evaluation.getValue());
        cpSolution.setUtility(evaluation);
        return evaluation;
    }

    /*
        True if are goal is to minimize the objective
     */
    @Override
    public boolean isMinimizing() {
        return false;
    }
}
