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
    private int COMPONENTS_COUNT;
    private int MAX_NUMBER_CONSTRAINTS;
    private int MIN_NUMBER_CONSTRAINTS;
    private final Random random = new Random();

    public Sampler(int componentsCount, int minNumberConstraints, int maxNumberConstraints) {
        this.COMPONENTS_COUNT = componentsCount;
        this.MAX_NUMBER_CONSTRAINTS = maxNumberConstraints;
        this.MIN_NUMBER_CONSTRAINTS = minNumberConstraints;
    }

    private int sampleNumberOfConstraints() {
        return MIN_NUMBER_CONSTRAINTS + random.nextInt(MAX_NUMBER_CONSTRAINTS - MIN_NUMBER_CONSTRAINTS);
    }

    public Pair<ConstraintProblemData, NodeCandidates> sample(NodeCandidates nodeCandidates) {
        int constraints = sampleNumberOfConstraints();
        NodeCandidatesPool nodeCandidatesPool = new NodeCandidatesPool(nodeCandidates);
        VariableGenerator variableGenerator = new VariableGenerator(nodeCandidatesPool, COMPONENTS_COUNT);
        ConstraintEvaluator constraintEvaluator = new ConstraintEvaluator(variableGenerator, constraints);
        ConstraintGenerator constraintGenerator = new ConstraintGenerator(constraintEvaluator, variableGenerator, COMPONENTS_COUNT);
        ConstraintProblemData constraintProblem = new ConstraintProblemData(variableGenerator.getDomains());
        IntStream.range(0, constraints).forEach(number -> constraintProblem.postConstraint(constraintGenerator.generateConstraint()));
        return new Pair<>(constraintProblem, convertNodeCandidatesToSampleProblem(nodeCandidates));
    }

    private List<NodeCandidate> fillEmptyLocations(List<NodeCandidate> nodeCandidates) {
        GeoLocation defaultGeoLocation = new GeoLocation();
        defaultGeoLocation.setLongitude(1.0);
        defaultGeoLocation.setLatitude(1.0);
        nodeCandidates.forEach(node -> {
            if(node.getLocation().getGeoLocation() == null) {
                node.getLocation().setGeoLocation(defaultGeoLocation);
            }
        });
        return nodeCandidates;
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
                                result.get(NamesProvider.getComponentName(component)).get(provider).addAll(fillEmptyLocations(machines));
                            }));
                }
        );
        return NodeCandidates.of(result);
    }
}