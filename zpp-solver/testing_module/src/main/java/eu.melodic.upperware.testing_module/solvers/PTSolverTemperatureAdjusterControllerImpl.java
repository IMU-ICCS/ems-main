package eu.melodic.upperware.testing_module.solvers;

import cp_wrapper.utility_provider.ParallelUtilityProviderImpl;
import cp_wrapper.utility_provider.UtilityProvider;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.pt_solver.pt_solver.TemperatureAdjuster;
import eu.melodic.upperware.testing_module.utils.PTParameters;
import eu.melodic.upperware.testing_module.utils.SolverSolutionToStringConverter;
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import lombok.AllArgsConstructor;
import org.jamesframework.core.search.stopcriteria.MaxRuntime;
import org.javatuples.Pair;

import java.util.List;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
public class PTSolverTemperatureAdjusterControllerImpl implements SolverController {
    private int numThreads;
    private int timeLimit;
    private final static String SOLVER_ID = "PTSolverTemperatureAdjustment";

    @Override
    public String solve(NodeCandidates nodeCandidates, ConstraintProblem cp, UtilityProvider utilityProvider, String cpId) {
        TemperatureAdjuster temperatureAdjuster = new TemperatureAdjuster(timeLimit, numThreads, (ParallelUtilityProviderImpl) utilityProvider, cp);
        Pair<List<VariableValueDTO>, Double> solution = temperatureAdjuster.solve();
        return solutionToString(solution, cpId);
    }

    private String solutionToString(Pair<List<VariableValueDTO>, Double> solution, String cpId) {
        return SolverSolutionToStringConverter.convertToString(solution, SOLVER_ID, cpId, timeLimit, numThreads);
    }
}
