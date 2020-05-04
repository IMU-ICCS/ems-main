package eu.melodic.upperware.pt_solver.pt_solver;

import cp_wrapper.utils.runtime_limits.RuntimeLimit;
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
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.IntStream;

@Slf4j
public class PTSolver {
    private PTCPWrapper ptcpWrapper;
    private Problem<PTSolution> CPProblem;
    private double minTemp;
    private double maxTemp;
    private int numReplicas;
    private ParallelTempering<PTSolution> parallelTemperingSolver;
    private final double MAX_TEMPERATURE_DEFAULT = 10.0;
    private final double MIN_TEMPERATURE_DEFAULT = 0.000001;

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
        PTSolution bestSolution = solvePTSolution(stopCriterion);
        PTObjective objective = new PTObjective();
        objective.evaluate(bestSolution, ptcpWrapper);
        return new Pair<>(ptcpWrapper.solutionToVariableValueDTOList(bestSolution), bestSolution.getUtility().getValue());
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

    public void adjustTemperature(int timeLimit) throws InterruptedException {
        maxTemp = MAX_TEMPERATURE_DEFAULT;
        double meanUtility = estimateMeanUtility(timeLimit);
        log.info("Estimated mean utility is equal to: " + meanUtility);
        minTemp = getMinTemperature(meanUtility);
        log.info("Setting minimal temperature to: " + minTemp);
    }

    private double estimateMeanUtility(int timeLimit) throws InterruptedException {
        Queue<Double> globalSampleQueue = new ConcurrentLinkedQueue<>();
        List<Thread> threads = new ArrayList<>();
        IntStream.range(0, numReplicas).forEach(threadNumber -> threads.add(new Thread(() -> globalSampleQueue.add(sampleUtilities(timeLimit)))));
        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
        }
        return globalSampleQueue.stream().mapToDouble(d->d).average().orElse(0.0) / numReplicas;
    }

    private double sampleUtilities(int timeLimit) {
        List<Double> values = new ArrayList<>();
        RuntimeLimit runtimeLimit = new RuntimeLimit(timeLimit);
        PTRandomGenerator randomGenerator = new PTRandomGenerator();
        PTObjective objective = new PTObjective();
        Random random = new Random();
        runtimeLimit.startCounting();
        while (!runtimeLimit.limitExceeded()) {
            values.add(objective.evaluate(randomGenerator.create(random, ptcpWrapper), ptcpWrapper).getValue());
        }
        return values.stream().mapToDouble(d->d).average().orElse(0.0);
    }

    private double getMinTemperature(double meanUtility) {
        if (meanUtility == 0) {
            return MIN_TEMPERATURE_DEFAULT;
        }
        double tmp = 1/meanUtility * Math.log(1 - Math.pow(0.9, 1/ (double) ptcpWrapper.getVariablesCount()));
        return -1/tmp;
    }

    private void prepareProblem() {
        CPProblem = new GenericProblem<>(ptcpWrapper, new PTObjective(), new PTRandomGenerator());
    }

    private void preparePTSolver() {
        prepareProblem();
        parallelTemperingSolver = new ParallelTempering<>(
                CPProblem,
                new PTNeighbourhood(ptcpWrapper),
                numReplicas, minTemp, maxTemp);
    }
}
