package eu.melodic.upperware.cp_sampler;

import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.cp_sampler.constraint_problem_data.ConstraintProblemData;
import eu.melodic.upperware.cp_sampler.generator.ConstraintEvaluator;
import eu.melodic.upperware.cp_sampler.generator.ConstraintGenerator;
import eu.melodic.upperware.cp_sampler.generator.VariableGenerator;
import eu.melodic.upperware.cp_sampler.node_candidates.NodeCandidatesPool;
import eu.melodic.upperware.cp_sampler.utils.NamesProvider;
import io.github.cloudiator.rest.model.NodeCandidate;
import org.javatuples.Pair;

import java.util.*;
import java.util.stream.IntStream;

public class Sampler {
    private final int COMPONENTS_COUNT = 2;
    private final int MAX_NUMBER_CONSTRAINTS = 5;
    private final int MIN_NUMBER_CONSTRAINTS = 2;
    private final Random random = new Random();

    private int sampleNumberOfConstraints() {
        return MIN_NUMBER_CONSTRAINTS + random.nextInt(MAX_NUMBER_CONSTRAINTS - MIN_NUMBER_CONSTRAINTS);
    }

    public Pair<ConstraintProblemData, NodeCandidates> sample(NodeCandidates nodeCandidates) {
        NodeCandidatesPool nodeCandidatesPool = new NodeCandidatesPool(nodeCandidates);
        VariableGenerator variableGenerator = new VariableGenerator(nodeCandidatesPool, COMPONENTS_COUNT);
        ConstraintEvaluator constraintEvaluator = new ConstraintEvaluator(variableGenerator);
        ConstraintGenerator constraintGenerator = new ConstraintGenerator(constraintEvaluator, variableGenerator);
        ConstraintProblemData constraintProblem = new ConstraintProblemData(variableGenerator.getDomains());
        int constraints = sampleNumberOfConstraints();
        IntStream.range(0, constraints).forEach(number -> constraintProblem.postConstraint(constraintGenerator.generateConstraint()));
        return new Pair<>(constraintProblem, convertNodeCandidatesToSampleProblem(nodeCandidates));
    }

    private NodeCandidates convertNodeCandidatesToSampleProblem(NodeCandidates nodeCandidates) {
        Map<String, Map<Integer, List<NodeCandidate>>> candidates = nodeCandidates.get();
        Map<String, Map<Integer, List<NodeCandidate>>> result = new HashMap<>();
        IntStream.range(0, COMPONENTS_COUNT).forEach(
                component -> {
                    result.put(NamesProvider.getComponentName(component), new HashMap<>());
                    candidates.forEach( (name, mapping) -> mapping.forEach(
                            (provider, machines) -> {
                                if (!result.get(NamesProvider.getComponentName(component)).containsKey(provider)) {
                                    result.get(NamesProvider.getComponentName(component)).put(provider, new ArrayList<>());
                                }
                                result.get(NamesProvider.getComponentName(component)).get(provider).addAll(machines);
                            }));
                }
        );
        return NodeCandidates.of(result);
    }
}