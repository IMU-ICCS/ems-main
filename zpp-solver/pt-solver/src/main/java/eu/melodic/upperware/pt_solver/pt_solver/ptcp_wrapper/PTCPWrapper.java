package eu.melodic.upperware.pt_solver.pt_solver.ptcp_wrapper;
/*
    Thin layer on top of CPWrapper class from CPParser package.
 */

import eu.melodic.upperware.cp_wrapper.CPWrapper;
import eu.melodic.upperware.pt_solver.pt_solver.components.PTEvaluation;
import eu.melodic.upperware.pt_solver.pt_solver.components.PTSolution;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import lombok.extern.slf4j.Slf4j;
import org.jamesframework.core.problems.objectives.evaluations.Evaluation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
@Slf4j
public class PTCPWrapper {
    private CPWrapper cpWrapper;

    private List<Integer> minVariableValues = new ArrayList<>();
    private List<Integer> maxVariableValues = new ArrayList<>();

    public PTCPWrapper(CPWrapper cpWrapper) {
        this.cpWrapper = cpWrapper;
        setMaxMinDomainValues();
    }

    public Evaluation evaluate(List<Integer> assignments) {
        log.debug("Evaluating solution " + assignments.toString());
        if (cpWrapper.checkIfFeasible(assignments)) {
            Evaluation evaluation = new PTEvaluation(cpWrapper.getUtility(assignments));
            log.debug("Solution is feasible, utility value: " + evaluation.getValue());
            return evaluation;
        } else {
            log.debug("Solution is not feasible, returning 0");
            return new PTEvaluation(0);
        }
    }
    /*
            True if increasing current @var variable value by one will not exceed
            domain range
         */
    public boolean canMoveUp(PTSolution ptSolution, int var) {
        return maxVariableValues.get(var) > ptSolution.getVarAssignments().get(var);
    }
    /*
        True if decreasing current @var variable value by one will not exceed
        domain range
     */
    public boolean canMoveDown(PTSolution ptSolution, int var) {
        return minVariableValues.get(var) < ptSolution.getVarAssignments().get(var);
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

    public List<VariableValueDTO> solutionToVariableValueDTOList(PTSolution solution) {
        return cpWrapper.assignmentToVariableValueDTOList(solution.getVarAssignments());
    }

    private void setMaxMinDomainValues() {
        for (int i = 0; i < getVariablesCount(); i++) {
            minVariableValues.add(getMinValue(i));
            maxVariableValues.add(getMaxValue(i));
        }
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
