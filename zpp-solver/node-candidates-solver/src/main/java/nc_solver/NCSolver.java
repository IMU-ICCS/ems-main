package nc_solver;

import nc_solver.cp_components.*;
import cp_wrapper.utility_provider.UtilityProvider;
import eu.melodic.cache.NodeCandidates;
import io.github.cloudiator.rest.model.NodeCandidate;
import nc_solver.nc_wrapper.NCWrapper;
import nc_solver.node_candidate.node_candidate_element.GeographicCoordinate;
import nc_solver.node_candidate.NodeCandidatesPool;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import nc_solver.node_candidate.node_candidate_element.VMConfiguration;
import org.jamesframework.core.problems.GenericProblem;
import org.jamesframework.core.problems.Problem;
import org.jamesframework.core.search.algo.ParallelTempering;
import org.jamesframework.core.search.stopcriteria.StopCriterion;

import java.util.List;
import java.util.Map;

public class NCSolver {
    private NCWrapper ncWrapper;
    private Problem<PTSolution> CPProblem;
    private double minTemp;
    private double maxTemp;
    private int numReplicas;
    private ParallelTempering<PTSolution> parallelTemperingSolver;
    private NodeCandidatesPool candidatesPool;

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

    /*
        This method must return something different - PTSolution is only for tests.
     */
    public PTSolution solve(StopCriterion stopCriterion) {
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
