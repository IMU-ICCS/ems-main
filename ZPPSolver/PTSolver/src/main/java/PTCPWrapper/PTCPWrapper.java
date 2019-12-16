package PTCPWrapper;
/*
    Thin layer on top of CPWrapper class from CPParser package.
 */
import CPComponents.PTEvaluation;
import CPComponents.PTSolution;
import CPWrapper.CPWrapper;
import lombok.AllArgsConstructor;
import org.jamesframework.core.problems.objectives.evaluations.Evaluation;
import org.jamesframework.core.problems.sol.RandomSolutionGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
@AllArgsConstructor
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

    private int generateRandomValue(int variable, Random random) {
        int domainSize = getMaxValue(variable) - getMinValue(variable) + 1;
        int value = random.nextInt(domainSize);
        value += getMinValue(variable);
        return value;
    }

    public int getVariablesCount() {
        return cpWrapper.getVariablesCount();
    }

    public PTSolution generateRandom(Random random) {
        List<Integer> assignment = new ArrayList<>();
        for (int i = 0; i < cpWrapper.getVariablesCount(); i++) {
            assignment.add(generateRandomValue(i, random));
        }
        return new PTSolution(assignment);
    }
}
