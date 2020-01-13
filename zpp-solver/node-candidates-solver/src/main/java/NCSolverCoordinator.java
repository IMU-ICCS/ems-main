import cp_components.*;
import cp_wrapper.UtilityProvider;
import eu.melodic.cache.NodeCandidates;
import io.github.cloudiator.rest.model.NodeCandidate;
import nc_wrapper.NCWrapper;
import node_candidate.node_candidate_element.GeographicCoordinate;
import node_candidate.NodeCandidatesPool;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import node_candidate.node_candidate_element.VMConfiguration;
import org.jamesframework.core.problems.GenericProblem;
import org.jamesframework.core.problems.Problem;
import org.jamesframework.core.search.algo.ParallelTempering;
import org.jamesframework.core.search.stopcriteria.StopCriterion;

import java.util.List;
import java.util.Map;

public class NCSolverCoordinator {
    private NCWrapper ncWrapper;
    private Problem<PTSolution> CPProblem;
    private double minTemp;
    private double maxTemp;
    private int numReplicas;
    private ParallelTempering<PTSolution> parallelTemperingSolver;
    private NodeCandidatesPool candidatesPool;

    public NCSolverCoordinator(double minTemp, double maxTemp, int numReplicas, ConstraintProblem cp, UtilityProvider utility, NodeCandidates nodeCandidates) {
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.numReplicas = numReplicas;
        this.ncWrapper = new NCWrapper(cp, utility);
        candidatesPool = new NodeCandidatesPool(ncWrapper, ncWrapper.getComponents());
        ncWrapper.setNodeCandidatesPool(candidatesPool);
        preparePTSolver();
        postNodeCandidates(nodeCandidates);
    }

    private void postNodeCandidates(NodeCandidates nodeCandidates) {
        Map<String, Map<Integer, List<NodeCandidate>>> nodes = nodeCandidates.get();
        for (Map<Integer, List<NodeCandidate>> n : nodes.values()) {
            for (Integer prov : n.keySet()) {
                n.get(prov).stream().forEach((el) -> {
                    candidatesPool.postVMLocation(prov,
                            new GeographicCoordinate(el.getLocation().getGeoLocation().getLatitude(), el.getLocation().getGeoLocation().getLongitude()));
                    candidatesPool.postVMConfiguration(prov,
                            new VMConfiguration(el.getHardware().getCores(), el.getHardware().getRam(), el.getHardware().getDisk()));
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
