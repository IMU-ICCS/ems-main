package eu.melodic.upperware.testing_module.solvers;

import cp_wrapper.utility_provider.UtilityProvider;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.testing_module.utils.UtilityGeneratorMaster;
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;

public interface SolverController {
    public String solve(NodeCandidates nodeCandidates, ConstraintProblem cp, UtilityGeneratorMaster utilityGeneratorMaster, String cpId);
}
