package CPWrapper;

import CPComponents.PTEvaluation;
import CPComponents.PTSolution;
import org.jamesframework.core.problems.objectives.evaluations.Evaluation;

import java.util.List;
import java.util.Random;

public class PTCPWrapper {

    public Evaluation evaluate(List<Integer> assignments) {
        //TODO Use cpWrapper to check constraint
        //TODO Use CpWrapper to calc utility
        return new PTEvaluation(0);
    }

    public int getMaxValue(int variable) {
        //TODO use CPWrapper - must impement a new method;
        return 0;
    }

    public PTSolution generateRandom(Random random, PTCPWrapper ptcpWrapper) {
        //TODO must use CPWrapper
        return null;
    }
}
