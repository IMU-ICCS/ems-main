package eu.melodic.upperware.nc_solver.nc_solver;

import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.cp_wrapper.utility_provider.UtilityProvider;
import eu.melodic.upperware.cp_wrapper.utils.runtime_limits.RuntimeLimit;
import eu.melodic.upperware.nc_solver.nc_solver.cp_components.PTNeighbourhood;
import eu.melodic.upperware.nc_solver.nc_solver.cp_components.PTObjective;
import eu.melodic.upperware.nc_solver.nc_solver.cp_components.PTRandomGenerator;
import eu.melodic.upperware.nc_solver.nc_solver.cp_components.PTSolution;
import eu.melodic.upperware.nc_solver.nc_solver.nc_wrapper.NCWrapper;
import eu.melodic.upperware.nc_solver.nc_solver.node_candidate.NodeCandidatesPool;
import eu.melodic.upperware.nc_solver.nc_solver.node_candidate.node_candidate_element.GeographicCoordinate;
import eu.melodic.upperware.nc_solver.nc_solver.node_candidate.node_candidate_element.VMConfiguration;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import lombok.extern.slf4j.Slf4j;
import org.activeeon.morphemic.model.NodeCandidate;
import org.jamesframework.core.problems.GenericProblem;
import org.jamesframework.core.problems.Problem;
import org.jamesframework.core.search.algo.ParallelTempering;
import org.jamesframework.core.search.stopcriteria.StopCriterion;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.IntStream;

@Slf4j
public class NCSolver {
    private NCWrapper ncWrapper;
    private Problem<PTSolution> CPProblem;
    private double minTemp;
    private double maxTemp;
    private int numReplicas;
    private ParallelTempering<PTSolution> parallelTemperingSolver;
    private NodeCandidatesPool candidatesPool;
    private final double MAX_TEMPERATURE_DEFAULT = 10.0;
    private final double MIN_TEMPERATURE_DEFAULT = 0.000001;

    public NCSolver(double minTemp, double maxTemp, int numReplicas, ConstraintProblem cp, UtilityProvider utility, NodeCandidates nodeCandidates) {
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.numReplicas = numReplicas;
        this.ncWrapper = new NCWrapper(cp, utility);
        candidatesPool = new NodeCandidatesPool(ncWrapper, ncWrapper.getComponents());
        ncWrapper.setNodeCandidatesPool(candidatesPool);
        preparePTSolver();
        postNodeCandidates(nodeCandidates);
    }

    public Pair<List<VariableValueDTO>, Double> solve(StopCriterion stopCriterion) {
        PTSolution bestSolution = solvePTSolution(stopCriterion);
        PTObjective objective = new PTObjective();
        objective.evaluate(bestSolution, ncWrapper);
        return new Pair<>(ncWrapper.covertSolutionToVariableValueDTO(bestSolution), bestSolution.getUtility().getValue());
    }

    public PTSolution solvePTSolution(StopCriterion stopCriterion) {
        parallelTemperingSolver.addStopCriterion(stopCriterion);
        parallelTemperingSolver.start();

        if(parallelTemperingSolver.getBestSolution() != null){
            return parallelTemperingSolver.getBestSolution();
        } else {
            log.info("No valid solution found...");
        }
        throw new RuntimeException("Couldn't find a solution");
    }

    private VMConfiguration configurationFromNodeCandidate(NodeCandidate nodeCandidate) {
        return new VMConfiguration(
                nodeCandidate.getHardware().getCores(),
                nodeCandidate.getHardware().getRam(), nodeCandidate.getHardware().getDisk().intValue()
        );
    }
    /*
        NodeCandidatesPool assume that whenever provider has some available configurations then it also
        has at least one available location. But if CP model does not contain location then it may happen that
        node candidate have null GeoLocations - in this case we add dummy location with latitude, longitude = 100.
     */
    private GeographicCoordinate locationFromNodeCandidate(NodeCandidate nodeCandidate) {
        if (nodeCandidate.getLocation().getGeoLocation() == null) {
            return new GeographicCoordinate(100, 100);
        } else {
            return new GeographicCoordinate(
                    (long)(100 * nodeCandidate.getLocation().getGeoLocation().getLatitude()),
                    (long)(100 *nodeCandidate.getLocation().getGeoLocation().getLongitude())
            );
        }
    }

    private void postNodeCandidates(NodeCandidates nodeCandidates) {
        Map<String, Map<Integer, List<NodeCandidate>>> nodes = nodeCandidates.get();
        for (Map<Integer, List<NodeCandidate>> n : nodes.values()) {
            for (Integer provider : n.keySet()) {
                n.get(provider).stream().forEach((nodeCandidate) -> {
                    candidatesPool.postVMLocation(provider, locationFromNodeCandidate(nodeCandidate));
                    candidatesPool.postVMConfiguration(provider, configurationFromNodeCandidate(nodeCandidate));
                });
            }
        }
        candidatesPool.initNodeCandidates();
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

    public void adjustTemperature(int timeLimit) throws InterruptedException {
        this.maxTemp = MAX_TEMPERATURE_DEFAULT;
        double meanUtility = estimateMeanUtility(timeLimit);
        log.info("Estimated mean utility is equal to: " + meanUtility);
        this.minTemp = getMinTemperature(meanUtility);
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
            values.add(objective.evaluate(randomGenerator.create(random, ncWrapper), ncWrapper).getValue());
        }
        return values.stream().mapToDouble(value -> value).average().orElse(0.0);
    }

    private double getMinTemperature(double meanUtility) {
        if (meanUtility == 0) {
            return MIN_TEMPERATURE_DEFAULT;
        }
        double tmp = 1/meanUtility * Math.log(1 - Math.pow(0.9, 1/ (double) ncWrapper.getVariableCount()));
        return -1/tmp;
    }
}
