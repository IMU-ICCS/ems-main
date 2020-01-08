package cp_components;

import nc_wrapper.NCWrapper;
import org.jamesframework.core.problems.objectives.Objective;
import org.jamesframework.core.problems.objectives.evaluations.Evaluation;

public class PTObjective implements Objective<PTSolution, NCWrapper> {
    @Override
    public Evaluation evaluate(PTSolution cpSolution, NCWrapper ncWrapper) {
        return ncWrapper.evaluate(cpSolution.getVarAssignments());
    }

    /*
        True if are goal is to minimize the objective
     */
    @Override
    public boolean isMinimizing() {
        return false;
    }
}
