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
import eu.melodic.upperware.utilitygenerator.evaluator.EvaluatingUtils;
import eu.paasage.upperware.metamodel.cp.VariableType;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CheapestPolicyImpl implements Policy {
    private MCTSWrapper mctsWrapper;
    private Collection<String> components;
    private Collection<VariableDTO> variables;
    private Map<String, Collection<VariableDTO>> componentToVariables = new HashMap<>();

    private final List<VariableType> notRequiredVariableTypes = Arrays.asList(
            VariableType.CORES,
            VariableType.STORAGE,
            VariableType.RAM,
            VariableType.LATITUDE,
            VariableType.LONGITUDE
    );

    private final List<VariableType> requiredVariableTypes = Arrays.asList(
            VariableType.CARDINALITY,
            VariableType.PROVIDER
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
        final int rolloutDepth = assignment.size();
        if (!hasAllRequiredVariables(assignment)) {
            assignment = generatePathForCardinalityAndProvider(assignment);
        }
        final int assignmentDepth = assignment.size();
        assignment = fillAssignmentWithTrivialValues(assignment);
        Collection<ConfigurationElement> cheapestConfiguration = findCheapestConfiguration(assignment);
        if (cheapestConfiguration.isEmpty()) {
            return new SolutionImpl(rolloutDepth);
        } else {
            return configurationToSolution(cheapestConfiguration, assignment, rolloutDepth, assignmentDepth);
        }
    }

    private List<Integer> generatePathForCardinalityAndProvider(List<Integer> assignment) {
        IntStream.range(assignment.size(), getCountOfRequiredVariables())
                .forEach(i -> assignment.add(mctsWrapper.generateRandomValue(i)));
        return assignment;
    }

    private int getCountOfRequiredVariables() {
        return requiredVariableTypes.size() * mctsWrapper.getNumberOfComponents();
    }

    private boolean hasAllRequiredVariables(List<Integer> assignment) {
        return assignment.size() >= getCountOfRequiredVariables();
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

    private List<Integer> fillAssignmentWithTrivialValues(List<Integer> assignment) {
        IntStream.range(assignment.size(), mctsWrapper.getSize())
                .forEach(index -> assignment.add(0));
        return assignment;
    }

    private Collection<ConfigurationElement> getConfigurationForComponent(String componentId, Collection<VariableValueDTO> values) {
        return EvaluatingUtils.convertSolutionToNodeCandidates(componentToVariables.get(componentId), mctsWrapper.getNodeCandidates(componentId), values);
    }

    private Collection<VariableDTO> getVariablesForComponent(String componentId) {
        return variables.stream()
                .filter(variable -> variable.getComponentId()
                        .equals((componentId)))
                .collect(Collectors.toList());
    }

    private Solution configurationToSolution(Collection<ConfigurationElement> configuration, List<Integer> assignment, int rolloutDepth, final int assignmentDepth) {
        configuration
                .forEach(element -> addConfigurationToAssignment(assignment, element, assignmentDepth));

        return new SolutionImpl(rolloutDepth, assignment, mctsWrapper);
    }

    private void addConfigurationToAssignment(List<Integer> assignment, ConfigurationElement configurationElement, final int assignmentDepth) {
       notRequiredVariableTypes
               .forEach(variableType ->
                       addVariableToAssignment(assignment, configurationElement, assignmentDepth, variableType)
               );
    }

    private void addVariableToAssignment(List<Integer> assignment, ConfigurationElement configurationElement, final int assignmentDepth, VariableType type) {
        if (mctsWrapper.variableExistsInCP(configurationElement.getId(), type)) {
            int variableIndex = mctsWrapper.getVariableIndexFromComponentAndType(configurationElement.getId(), type);
            if (variableIndex >= assignmentDepth) {
                assignment.add(
                        variableIndex,
                        mctsWrapper.getIndexFromValue(new LongValue(VariableExtractor.getVariableValue(type, configurationElement)), variableIndex)
                );
            }
        }
    }
}
