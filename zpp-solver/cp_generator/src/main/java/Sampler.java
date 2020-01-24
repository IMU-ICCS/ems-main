import constraint_problem_data.ConstraintProblemData_;
import eu.melodic.cache.NodeCandidates;
import generator.ConstraintEvaluator;
import generator.ConstraintGenerator;
import generator.VariableGenerator;
import io.github.cloudiator.rest.model.NodeCandidate;
import node_candidates.NodeCandidatesPool;
import org.javatuples.Pair;
import utils.NamesProvider;

import java.util.HashMap;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.*;

public class Sampler {
    private final int COMPONENTS_COUNT = 5;
    private final int MAX_NUMBER_CONSTRAINTS = 20;
    private final int MIN_NUMBER_CONSTRAINTS = 4;
    private final Random random = new Random();

    private int sampleNumberOfConstraints() {
        return MIN_NUMBER_CONSTRAINTS + random.nextInt(MAX_NUMBER_CONSTRAINTS - MIN_NUMBER_CONSTRAINTS);
    }

    public Pair<ConstraintProblemData_, NodeCandidates> sample(NodeCandidates nodeCandidates) {
        NodeCandidatesPool nodeCandidatesPool = new NodeCandidatesPool(nodeCandidates);
        VariableGenerator variableGenerator = new VariableGenerator(nodeCandidatesPool, COMPONENTS_COUNT);
        ConstraintEvaluator constraintEvaluator = new ConstraintEvaluator(variableGenerator, nodeCandidatesPool, COMPONENTS_COUNT);
        ConstraintGenerator constraintGenerator = new ConstraintGenerator(constraintEvaluator, variableGenerator);
        ConstraintProblemData_ constraintProblem = new ConstraintProblemData_(variableGenerator.getDomains());
        int constraints = sampleNumberOfConstraints();
        while (constraints > 0) {
            constraintProblem.postConstraint(constraintGenerator.generateConstraint());
            constraints--;
        }
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
                                if (!result.get(component).containsKey(provider)) {
                                    result.get(component).put(provider, new ArrayList<>());
                                }
                                result.get(component).get(provider).addAll(machines);
                            }));
                }
        );
        return NodeCandidates.of(result);
    }
}