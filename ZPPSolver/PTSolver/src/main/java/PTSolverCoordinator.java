import CPComponents.*;
import CPWrapper.CPWrapper;
import CPWrapper.UtilityProvider;
import PTCPWrapper.PTCPWrapper;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import org.jamesframework.core.problems.GenericProblem;
import org.jamesframework.core.problems.Problem;
import org.jamesframework.core.search.algo.ParallelTempering;
import org.jamesframework.core.search.stopcriteria.StopCriterion;

import java.util.ArrayList;

public class PTSolverCoordinator {
    private PTCPWrapper ptcpWrapper;
    private Problem<PTSolution> CPProblem;
    private double minTemp;
    private double maxTemp;
    private int numReplicas;
    private ParallelTempering<PTSolution> parallelTemperingSolver;

    public PTSolverCoordinator(double minTemp, double maxTemp, int numReplicas, ConstraintProblem cp, UtilityProvider utility) {
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.numReplicas = numReplicas;
        CPWrapper cpWrapper = new CPWrapper();
        cpWrapper.parse(cp, utility);
        this.ptcpWrapper = new PTCPWrapper(cpWrapper);
        preparePTSolver();
    }

    public void solve(StopCriterion stopCriterion) {
        // np new MaxRuntime(timeLimit, TimeUnit.SECONDS)
        parallelTemperingSolver.addStopCriterion(stopCriterion);
        parallelTemperingSolver.start();

        if(parallelTemperingSolver.getBestSolution() != null){
           // TODO
        } else {
            System.out.println("No valid solution found...");
        }
        //TODO should return solution;
    }

    private void setMaxMinDomainValues() {
        PTSolution.minVariableValues = new ArrayList<>();
        PTSolution.maxVariableValues = new ArrayList<>();
        for (int i = 0; i < ptcpWrapper.getVariablesCount(); i++) {
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
