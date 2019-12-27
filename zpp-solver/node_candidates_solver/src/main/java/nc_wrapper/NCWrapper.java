package nc_wrapper;

import cp_components.PTEvaluation;
import cp_components.PTSolution;
import cp_wrapper.CPWrapper;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import org.jamesframework.core.problems.objectives.evaluations.Evaluation;
import variable_orderer.ComponentVariableOrderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NCWrapper implements MarginalDomainValuesProvider{
    private CPWrapper cpWrapper;
    private ComponentVariableOrderer variableOrderer;

    public NCWrapper(CPWrapper cpWrapper, ConstraintProblem cp) {
        this.cpWrapper = cpWrapper;
        this.variableOrderer = new ComponentVariableOrderer(cp);
        cpWrapper.setVariableOrdering(variableOrderer);
    }

    public List<String> getComponents() {
        return variableOrderer.getComponents();
    }

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
        if (!variableOrderer.exists(variable)) {
            return 0;
        }
        return cpWrapper.getMaxDomainValue(variable);
    }

    public int getMinValue(int variable) {
        if (!variableOrderer.exists(variable)) {
            return 0;
        }
        return cpWrapper.getMinDomainValue(variable);
    }

    /*
        Generates random (uniform) value for variable @variable
     */
    private int generateRandomValue(int variable, Random random) {
        if (!variableOrderer.exists(variable)) {
            return 0;
        }
        int domainSize = getMaxValue(variable) - getMinValue(variable) + 1;
        int value = random.nextInt(domainSize);
        value += getMinValue(variable);
        return value;
    }

    /*
        Generates random solution to the constraint problem.
        Used to sample starting point for parallel tempering.
     */
    public PTSolution generateRandom(Random random) {
        List<Integer> assignment = new ArrayList<>();
        for (int i = 0; i < variableOrderer.getMaxIndex(); i++) {
            assignment.add(generateRandomValue(i, random));
        }
        return new PTSolution(assignment);
    }
}
