package eu.melodic.upperware.pt_solver.pt_solver;

import eu.melodic.upperware.pt_solver.pt_solver.components.PTNeighbourhood;
import eu.melodic.upperware.pt_solver.pt_solver.components.PTObjective;
import eu.melodic.upperware.pt_solver.pt_solver.components.PTRandomGenerator;
import eu.melodic.upperware.pt_solver.pt_solver.components.PTSolution;
import eu.melodic.upperware.pt_solver.pt_solver.ptcp_wrapper.PTCPWrapper;
import cp_wrapper.CPWrapper;
import cp_wrapper.utility_provider.UtilityProvider;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import lombok.extern.slf4j.Slf4j;
import org.jamesframework.core.problems.GenericProblem;
import org.jamesframework.core.problems.Problem;
import org.jamesframework.core.search.algo.ParallelTempering;
import org.jamesframework.core.search.stopcriteria.StopCriterion;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PTSolver {
    private PTCPWrapper ptcpWrapper;
    private Problem<PTSolution> CPProblem;
    private double minTemp;
    private double maxTemp;
    private int numReplicas;
    private ParallelTempering<PTSolution> parallelTemperingSolver;

    public PTSolver(double minTemp, double maxTemp, int numReplicas, ConstraintProblem cp, UtilityProvider utility) {
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.numReplicas = numReplicas;
        CPWrapper cpWrapper = new CPWrapper();
        cpWrapper.parse(cp, utility);
        this.ptcpWrapper = new PTCPWrapper(cpWrapper);
        preparePTSolver();
    }

    public Pair<List<VariableValueDTO>, Double> solve(StopCriterion stopCriterion) {
        parallelTemperingSolver.addStopCriterion(stopCriterion);
        parallelTemperingSolver.start();

        if(parallelTemperingSolver.getBestSolution() != null) {
            PTSolution bestSolution = parallelTemperingSolver.getBestSolution();
            PTObjective objective = new PTObjective();
            objective.evaluate(bestSolution, ptcpWrapper);
           return new Pair<>(ptcpWrapper.solutionToVariableValueDTOList(bestSolution), bestSolution.getUtility().getValue());
        } else {
            log.info("No valid solution found...");
        }
        throw new RuntimeException("Couldn't find a solution");
    }

    public PTSolution solvePTSolution(StopCriterion stopCriterion) {
        parallelTemperingSolver.addStopCriterion(stopCriterion);
        parallelTemperingSolver.start();

        if(parallelTemperingSolver.getBestSolution() != null) {
            return parallelTemperingSolver.getBestSolution();
        } else {
            log.info("No valid solution found...");
        }
        throw new RuntimeException("Couldn't find a solution");
    }

    private void setMaxMinDomainValues() {
        PTSolution.minVariableValues = new ArrayList<>();
        PTSolution.maxVariableValues = new ArrayList<>();
        for (int i = 0; i < ptcpWrapper.getVariablesCount(); i++) {
            System.out.println("Variable " + i + " " + ptcpWrapper.getMinValue(i) + " " + ptcpWrapper.getMaxValue(i));
            PTSolution.minVariableValues.add(ptcpWrapper.getMinValue(i));
            PTSolution.maxVariableValues.add(ptcpWrapper.getMaxValue(i));
        }
    }
    private void prepareProblem() {
        setMaxMinDomainValues();
        CPProblem = new GenericProblem<>(ptcpWrapper, new PTObjective(), new PTRandomGenerator());
    }

    private void preparePTSolver() {
        prepareProblem();
        parallelTemperingSolver = new ParallelTempering<>(
                CPProblem,
                new PTNeighbourhood(),
                numReplicas, minTemp, maxTemp);
    }
}
