package eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.policy;

import cp_wrapper.utils.numeric_value.implementations.LongValue;
import eu.melodic.upperware.mcts_solver.solver.mcts.cp_wrapper.MCTSWrapper;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Path;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Policy;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Solution;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.SolutionImpl;
import eu.melodic.upperware.mcts_solver.solver.utils.VariableExtractor;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableDTO;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.melodic.upperware.utilitygenerator.evaluator.ConfigurationElement;
import eu.paasage.upperware.metamodel.cp.VariableType;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static eu.melodic.upperware.utilitygenerator.evaluator.EvaluatingUtils.convertSolutionToNodeCandidates;


public class CheapestPolicyImpl implements Policy {
    private MCTSWrapper mctsWrapper;
    private Collection<String> components;
    private Collection<VariableDTO> variables;
    private Map<String, Collection<VariableDTO>> componentToVariables = new HashMap<>();
    private final List<VariableType> variableTypes = Arrays.asList(
            VariableType.CORES,
            VariableType.STORAGE,
            VariableType.RAM,
            VariableType.LATITUDE,
            VariableType.LONGITUDE
    );

    public CheapestPolicyImpl(MCTSWrapper mctsWrapper) {
        this.mctsWrapper = mctsWrapper;
        this.variables =  mctsWrapper.getVariableDTOCollection();
        this.components = variables.stream().map(VariableDTO::getComponentId).distinct().collect(Collectors.toList());
        components.forEach(component -> componentToVariables.put(component, getVariablesForComponent(component)));
    }

    @Override
    public Solution finishPath(Path path) {
        List<Integer> assignment = path.getPath();
        int rolloutDepth = assignment.size();
        if (!hasAllRequiredVariables(assignment)) {
            assignment = generatePathForCardinalityAndProvider(assignment);
        }
        Collection<ConfigurationElement> cheapestConfiguration = findCheapestConfiguration(assignment);
        if (cheapestConfiguration.isEmpty()) {
            return new SolutionImpl(rolloutDepth, mctsWrapper);
        } else {
            return configurationToSolution(cheapestConfiguration, assignment, rolloutDepth);
        }
    }

    private List<Integer> generatePathForCardinalityAndProvider(List<Integer> assignment) {
        IntStream.range(assignment.size(), getCountOfRequiredVariables())
                .forEach(i -> assignment.add(mctsWrapper.generateRandomValue(i)));
        return assignment;
    }

    private int getCountOfRequiredVariables() {
        return 2*mctsWrapper.getNumberOfComponents();
    }

    private boolean hasAllRequiredVariables(List<Integer> assignment) {
        return assignment.size() >= 2*mctsWrapper.getNumberOfComponents();
    }

    private Collection<ConfigurationElement> findCheapestConfiguration(List<Integer> assignment) {
        Collection<VariableValueDTO> values = mctsWrapper.assignmentToVariableValueDTOList(assignment);
        List<ConfigurationElement> cheapestConfiguration = new ArrayList<>();

        for (String componentId : components) {
            Collection<ConfigurationElement> configuration = getConfigurationForComponent(componentId, values);
            if (configuration.isEmpty()) {
                return Collections.emptyList();
            } else {
                cheapestConfiguration.addAll(configuration);
            }
        }
        return cheapestConfiguration;
    }

    private Collection<ConfigurationElement> getConfigurationForComponent(String componentId, Collection<VariableValueDTO> values) {
        return convertSolutionToNodeCandidates(componentToVariables.get(componentId), mctsWrapper.getNodeCandidates(componentId), values);
    }

    private Collection<VariableDTO> getVariablesForComponent(String componentId) {
        return variables.stream().filter(variable -> variable.getComponentId().equals((componentId))).collect(Collectors.toList());
    }

    private Solution configurationToSolution(Collection<ConfigurationElement> configuration, List<Integer> assignment, int rolloutDepth) {
        Map<Integer, Integer> indexToValue = new HashMap<>();
        IntStream.range(0, assignment.size()).forEach(index->indexToValue.put(index, assignment.get(index)));
        configuration.forEach(element -> addConfigurationToAssignment(indexToValue, element, assignment.size()));
        List<Integer> fullAssignment = Arrays.asList(new Integer[indexToValue.keySet().size()]);
        indexToValue.forEach(fullAssignment::set);
        return new SolutionImpl(rolloutDepth, fullAssignment, mctsWrapper);
    }

    private void addConfigurationToAssignment(Map<Integer, Integer> assignment, ConfigurationElement configurationElement, int assignmentSize) {
       variableTypes.forEach(variableType -> addVariableToAssignment(assignment, configurationElement, assignmentSize, variableType));
    }

    private void addVariableToAssignment(Map<Integer, Integer> assignment, ConfigurationElement configurationElement, int assignmentSize, VariableType type) {
        int variableIndex = mctsWrapper.getVariableIndexFromComponentAndType(configurationElement.getId(), type);
        if (variableIndex >= assignmentSize) assignment.put(variableIndex, mctsWrapper.getIndexFromValue(new LongValue(VariableExtractor.getVariableValue(type, configurationElement)), variableIndex));
    }
}
