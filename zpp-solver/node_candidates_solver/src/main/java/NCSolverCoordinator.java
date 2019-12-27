import cp_components.*;
import cp_wrapper.CPWrapper;
import cp_wrapper.UtilityProvider;
import nc_wrapper.NCWrapper;
import node_candidate.NodeCandidatesPool;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import org.jamesframework.core.problems.GenericProblem;
import org.jamesframework.core.problems.Problem;
import org.jamesframework.core.search.algo.ParallelTempering;
import org.jamesframework.core.search.stopcriteria.StopCriterion;

import java.util.ArrayList;
/*
    TODO trzeba dodac dodwanie node candidates do NodeCandidatesPool
 */
public class NCSolverCoordinator {
    private NCWrapper ncWrapper;
    private Problem<PTSolution> CPProblem;
    private double minTemp;
    private double maxTemp;
    private int numReplicas;
    private ParallelTempering<PTSolution> parallelTemperingSolver;
    private NodeCandidatesPool candidatesPool;

    public NCSolverCoordinator(double minTemp, double maxTemp, int numReplicas, ConstraintProblem cp, UtilityProvider utility) {
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.numReplicas = numReplicas;
        CPWrapper cpWrapper = new CPWrapper();
        cpWrapper.parse(cp, utility);
        this.ncWrapper = new NCWrapper(cpWrapper, cp);
        candidatesPool = new NodeCandidatesPool(ncWrapper);
        preparePTSolver();

    }

    /*
        This method must return something different - PTSolution is only for tests.
     */
    public PTSolution solve(StopCriterion stopCriterion) {
        // np new MaxRuntime(timeLimit, TimeUnit.SECONDS)
        parallelTemperingSolver.addStopCriterion(stopCriterion);
        parallelTemperingSolver.start();

        if(parallelTemperingSolver.getBestSolution() != null){
            return parallelTemperingSolver.getBestSolution();
        } else {
            System.out.println("No valid solution found...");
        }
        throw new RuntimeException("Couldn't find a solution");
        //TODO should return solution;
    }

    private void prepareProblem() {
        CPProblem = new GenericProblem<>(ncWrapper, new PTObjective(), new PTRandomGenerator());
    }

    private void preparePTSolver() {
        prepareProblem();
        parallelTemperingSolver = new ParallelTempering<>(
                CPProblem,
                new PTNeighbourhood(candidatesPool),
                numReplicas, minTemp, maxTemp);
    }
}
