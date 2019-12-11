package CPComponents;

import CPWrapper.PTCPWrapper;
import org.jamesframework.core.problems.objectives.Objective;
import org.jamesframework.core.problems.objectives.evaluations.Evaluation;

public class PTObjective implements Objective<PTSolution, PTCPWrapper> {
    @Override
    public Evaluation evaluate(PTSolution cpSolution, PTCPWrapper ptcpWrapper) {
        return ptcpWrapper.evaluate(cpSolution.getVarAssignments());
    }

    @Override
    public boolean isMinimizing() {
        return false;
    }
}
