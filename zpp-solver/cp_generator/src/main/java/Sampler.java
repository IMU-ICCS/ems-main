import constraint_problem_data.ConstraintProblemData_;
import eu.melodic.cache.NodeCandidates;
import generator.ConstraintEvaluator;
import generator.ConstraintGenerator;
import generator.VariableGenerator;
import node_candidates.NodeCandidatesPool;

import java.util.Random;

public class Sampler {
    private final int COMPONENTS_COUNT = 5;
    private final int MAX_NUMBER_CONSTRAINTS = 20;
    private final int MIN_NUMBER_CONSTRAINTS = 4;
    private final Random random = new Random();

    private int sampleNumberOfConstraints() {
        return MIN_NUMBER_CONSTRAINTS + random.nextInt(MAX_NUMBER_CONSTRAINTS - MIN_NUMBER_CONSTRAINTS);
    }

    public ConstraintProblemData_ sample(NodeCandidates nodeCandidates) {
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
        return constraintProblem;
    }
}