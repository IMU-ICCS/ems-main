package eu.melodic.upperware.pt_solver.pt_solver.ptcp_wrapper;
/*
    Thin layer on top of CPWrapper class from CPParser package.
 */
import eu.melodic.upperware.pt_solver.pt_solver.components.PTEvaluation;
import eu.melodic.upperware.pt_solver.pt_solver.components.PTSolution;
import cp_wrapper.CPWrapper;
import lombok.AllArgsConstructor;
import org.jamesframework.core.problems.objectives.evaluations.Evaluation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
@AllArgsConstructor
public class PTCPWrapper {
    private CPWrapper cpWrapper;

    public Evaluation evaluate(List<Integer> assignments) {
        if (cpWrapper.checkIfFeasible(assignments)) {
            return new PTEvaluation(cpWrapper.getUtility(assignments));
        } else {
            return new PTEvaluation(0);
        }
    }
    /*
        Returns maximal value of variable @variable
     */
    public int getMaxValue(int variable) {
        return cpWrapper.getMaxDomainValue(variable);
    }

    public int getMinValue(int variable) {
        return cpWrapper.getMinDomainValue(variable);
    }

    /*
        Generates random (uniform) value for variable @variable
     */
    private int generateRandomValue(int variable, Random random) {
        int domainSize = getMaxValue(variable) - getMinValue(variable) + 1;
        int value = random.nextInt(domainSize);
        value += getMinValue(variable);
        return value;
    }

    public int getVariablesCount() {
        return cpWrapper.getVariablesCount();
    }
    /*
        Generates random solution to the constraint problem.
        Used to sample starting point for parallel tempering.
     */
    public PTSolution generateRandom(Random random) {
        List<Integer> assignment = new ArrayList<>();
        for (int i = 0; i < cpWrapper.getVariablesCount(); i++) {
            assignment.add(generateRandomValue(i, random));
        }
        return new PTSolution(assignment);
    }
}
