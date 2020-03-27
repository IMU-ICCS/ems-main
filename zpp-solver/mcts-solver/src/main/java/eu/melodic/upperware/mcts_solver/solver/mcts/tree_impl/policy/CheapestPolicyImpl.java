package eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.policy;

import cp_wrapper.utils.numeric_value.implementations.IntegerValue;
import cp_wrapper.utils.numeric_value.implementations.LongValue;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.mcts_solver.solver.mcts.cp_wrapper.MCTSWrapper;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Path;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Policy;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Solution;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.SolutionImpl;
import eu.melodic.upperware.utilitygenerator.evaluator.ConfigurationElement;
import eu.paasage.upperware.metamodel.cp.VariableType;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.stream.IntStream;

import static eu.melodic.upperware.utilitygenerator.evaluator.EvaluatingUtils.convertSolutionToNodeCandidates;

@AllArgsConstructor
public class CheapestPolicyImpl implements Policy {
    private MCTSWrapper mctsWrapper;
    private NodeCandidates nodeCandidates;

    @Override
    public Solution finishPath(Path path) {
        List<Integer> assignment = path.getPath();
        int rolloutDepth = assignment.size();
        int number = mctsWrapper.getNumberOfComponents();
        if (!hasAllRequiredVariables(assignment)) {
            assignment = generatePathForCardinalityProvider(assignment);
        }
        Collection<ConfigurationElement> cheapestConfiguration = findCheapestConfiguration(assignment);
        if (cheapestConfiguration.isEmpty()) {
            return new SolutionImpl(rolloutDepth, mctsWrapper);
        } else {
            return configurationToSolution(cheapestConfiguration, assignment, rolloutDepth);
        }
    }

    private List<Integer> generatePathForCardinalityProvider(List<Integer> assignment) {
        IntStream.range(assignment.size(), mctsWrapper.getNumberOfComponents()*2)
                .forEach(i -> assignment.add(mctsWrapper.generateRandomValue(i)));
        return assignment;
    }

    private boolean hasAllRequiredVariables(List<Integer> assignment) {
        return assignment.size() >= 2*mctsWrapper.getNumberOfComponents();
    }

    private Collection<ConfigurationElement> findCheapestConfiguration(List<Integer> assignment) {
        return convertSolutionToNodeCandidates(mctsWrapper.getVariableDTOCollection(), nodeCandidates, mctsWrapper.assignmentToVariableValueDTOList(assignment));
    }

    private Solution configurationToSolution(Collection<ConfigurationElement> configuration, List<Integer> assignment, int rolloutDepth) {
        Map<Integer, Integer> indexToValue = new HashMap<>();
        IntStream.range(0, assignment.size()).forEach(index->indexToValue.put(index, assignment.get(index)));
        configuration.forEach(element -> addConfigurationToAssignment(indexToValue, element, assignment.size()));
        List<Integer> fullAssignment = Arrays.asList(new Integer[indexToValue.keySet().size()]);;
        indexToValue.forEach(fullAssignment::set);
        return new SolutionImpl(rolloutDepth, fullAssignment, mctsWrapper);
    }

    private void addConfigurationToAssignment(Map<Integer, Integer> assignment, ConfigurationElement configurationElement, int assignmentSize) {
        int coresIndex = mctsWrapper.getVariableIndexFromComponentAndType(configurationElement.getId(), VariableType.CORES);
        int ramIndex = mctsWrapper.getVariableIndexFromComponentAndType(configurationElement.getId(), VariableType.RAM);
        int diskIndex = mctsWrapper.getVariableIndexFromComponentAndType(configurationElement.getId(), VariableType.STORAGE);
        int latitudeIndex = mctsWrapper.getVariableIndexFromComponentAndType(configurationElement.getId(), VariableType.LATITUDE);
        int longitudeIndex = mctsWrapper.getVariableIndexFromComponentAndType(configurationElement.getId(), VariableType.LONGITUDE);
        if (coresIndex >= assignmentSize) assignment.put(coresIndex, mctsWrapper.getIndexFromValue(new IntegerValue(configurationElement.getNodeCandidate().getHardware().getCores()), coresIndex));
        if (ramIndex >= assignmentSize) assignment.put(ramIndex, mctsWrapper.getIndexFromValue(new LongValue(configurationElement.getNodeCandidate().getHardware().getRam()), ramIndex));
        if (diskIndex >= assignmentSize) assignment.put(diskIndex, mctsWrapper.getIndexFromValue(new LongValue(configurationElement.getNodeCandidate().getHardware().getDisk().longValue()), diskIndex));
        if (latitudeIndex >= assignmentSize) assignment.put(latitudeIndex, mctsWrapper.getIndexFromValue(new LongValue((long) (100*configurationElement.getNodeCandidate().getLocation().getGeoLocation().getLatitude())), latitudeIndex));
        if (longitudeIndex >= assignmentSize) assignment.put(latitudeIndex, mctsWrapper.getIndexFromValue(new LongValue((long) (100*configurationElement.getNodeCandidate().getLocation().getGeoLocation().getLongitude())), longitudeIndex));
    }
}
