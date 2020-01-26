package eu.melodic.upperware.pt_solver.pt_solver.components;

import eu.melodic.upperware.pt_solver.pt_solver.ptcp_wrapper.PTCPWrapper;
import lombok.extern.slf4j.Slf4j;
import org.jamesframework.core.problems.objectives.Objective;
import org.jamesframework.core.problems.objectives.evaluations.Evaluation;
public class PTObjective implements Objective<PTSolution, PTCPWrapper> {
    @Override
    public Evaluation evaluate(PTSolution cpSolution, PTCPWrapper ptcpWrapper) {
        Evaluation evaluation = ptcpWrapper.evaluate(cpSolution.getVarAssignments());
        cpSolution.setUtility(new PTEvaluation(evaluation.getValue()));
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
