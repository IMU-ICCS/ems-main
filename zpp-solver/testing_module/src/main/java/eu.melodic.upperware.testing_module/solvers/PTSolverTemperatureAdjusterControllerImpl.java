package eu.melodic.upperware.testing_module.solvers;

import cp_wrapper.utility_provider.UtilityProvider;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.pt_solver.pt_solver.PTSolver;
import eu.melodic.upperware.testing_module.utils.SolverSolutionToStringConverter;
import eu.melodic.upperware.testing_module.utils.UtilityGeneratorMaster;
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
public class PTSolverTemperatureAdjusterControllerImpl implements SolverController {
    private int numThreads;
    private int timeLimit;
    private final static String SOLVER_ID = "PTSolverTemperatureAdjustment";

    @Override
    public String solve(NodeCandidates nodeCandidates, ConstraintProblem cp, UtilityGeneratorMaster utilityGeneratorMaster, String cpId) {
        log.info("Starting " + SOLVER_ID + " on " + cpId);
        PTSolver solver = new PTSolver(1, 10, numThreads, cp, utilityGeneratorMaster.createParallelUtilityProvider(numThreads));
        try {
            solver.adjustTemperature(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return solutionToString(solver.solve(new MaxRuntime(timeLimit, TimeUnit.SECONDS)), cpId);
    }

    private String solutionToString(Pair<List<VariableValueDTO>, Double> solution, String cpId) {
        return SolverSolutionToStringConverter.convertToString(solution, SOLVER_ID, cpId, timeLimit, numThreads) + "\n";
    }
}
