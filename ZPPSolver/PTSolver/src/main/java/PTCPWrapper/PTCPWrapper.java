package PTCPWrapper;

import CPComponents.PTEvaluation;
import CPComponents.PTSolution;
import CPWrapper.CPWrapper;
import org.jamesframework.core.problems.objectives.evaluations.Evaluation;

import java.util.List;
import java.util.Random;

public class PTCPWrapper {
    private CPWrapper cpWrapper;

    public Evaluation evaluate(List<Integer> assignments) {
        if (cpWrapper.isFeasible(assignments)) {
            return new PTEvaluation(cpWrapper.getUtility(assignments));
        } else {
            return new PTEvaluation(0);
        }
    }

    public int getMaxValue(int variable) {
        return cpWrapper.getMaxDomainValue(variable);
    }

    public int getMinValue(int variable) {
        return cpWrapper.getMinDomainValue(variable);
    }
    public PTSolution generateRandom(Random random) {
        //TODO must use CPWrapper
        return null;
    }
}
