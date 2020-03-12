package eu.melodic.upperware.testing_module.solvers;

import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;

public interface SolverController {
    public String solve(NodeCandidates nodeCandidates, ConstraintProblem cp, UtilityGeneratorApplication utilityGenerator, String cpId);
}
