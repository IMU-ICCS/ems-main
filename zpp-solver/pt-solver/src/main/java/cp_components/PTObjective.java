package cp_components;

import ptcp_wrapper.PTCPWrapper;
import org.jamesframework.core.problems.objectives.Objective;
import org.jamesframework.core.problems.objectives.evaluations.Evaluation;

public class PTObjective implements Objective<PTSolution, PTCPWrapper> {
    @Override
    public Evaluation evaluate(PTSolution cpSolution, PTCPWrapper ptcpWrapper) {
        return ptcpWrapper.evaluate(cpSolution.getVarAssignments());
    }

    /*
        True if are goal is to minimize the objective
     */
    @Override
    public boolean isMinimizing() {
        return false;
    }
}
