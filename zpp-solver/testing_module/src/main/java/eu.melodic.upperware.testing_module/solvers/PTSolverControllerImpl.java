package eu.melodic.upperware.testing_module.solvers;

import cp_wrapper.utility_provider.ParallelUtilityProviderImpl;
import cp_wrapper.utility_provider.UtilityProviderImpl;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.pt_solver.pt_solver.PTSolver;
import eu.melodic.upperware.testing_module.utils.PTParameters;
import eu.melodic.upperware.testing_module.utils.SolverSolutionToStringConverter;
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jamesframework.core.search.stopcriteria.MaxRuntime;
import org.javatuples.Pair;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@AllArgsConstructor
public class PTSolverControllerImpl implements SolverController {
    private PTParameters ptParameters;
    private int timeLimit;
    private final static String SOLVER_ID = "PTSolver";
    private ParallelUtilityProviderImpl utilityProvider;

    @Override
    public String solve(NodeCandidates nodeCandidates, ConstraintProblem cp, UtilityGeneratorApplication utilityGenerator, String cpID) {
        log.info("Starting " + SOLVER_ID + " on " + cpID);
        PTSolver solver = new PTSolver(ptParameters.getMinTmp(), ptParameters.getMaxTmp(), ptParameters.getNumThreads(), cp, utilityProvider);
        return solutionToString(solver.solve(new MaxRuntime(timeLimit, TimeUnit.SECONDS)), cpID);
    }

    private String solutionToString(Pair<List<VariableValueDTO>, Double> solution, String cpId) {
        return SolverSolutionToStringConverter.convertToString(solution, SOLVER_ID, cpId, timeLimit, ptParameters);
    }
}

