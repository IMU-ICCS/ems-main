package eu.melodic.upperware.pt_solver.pt_solver.ptcp_wrapper;
/*
    Thin layer on top of CPWrapper class from CPParser package.
 */
import eu.melodic.upperware.pt_solver.pt_solver.components.PTEvaluation;
import eu.melodic.upperware.pt_solver.pt_solver.components.PTSolution;
import cp_wrapper.CPWrapper;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jamesframework.core.problems.objectives.evaluations.Evaluation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
@AllArgsConstructor
@Slf4j
public class PTCPWrapper {
    private CPWrapper cpWrapper;

    public Evaluation evaluate(List<Integer> assignments) {
        log.debug("Evaluating solution " + assignments.toString());
        if (cpWrapper.checkIfFeasible(assignments)) {
            Evaluation evaluation = new PTEvaluation(cpWrapper.getUtility(assignments));
            log.debug("Solution is feasible, eu.melodic.upperware.genetic_solver.utility value: " + evaluation.getValue());
            return evaluation;
        } else {
            log.debug("Solution is not feasible, returning 0");
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

    public List<VariableValueDTO> solutionToVariableValueDTOList(PTSolution solution) {
        return cpWrapper.assignmentToVariableValueDTOList(solution.getVarAssignments());
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
