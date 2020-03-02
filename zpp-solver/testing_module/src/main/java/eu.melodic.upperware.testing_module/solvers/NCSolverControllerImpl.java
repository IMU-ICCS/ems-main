package eu.melodic.upperware.testing_module.solvers;

import cp_wrapper.utility_provider.UtilityProviderImpl;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.nc_solver.nc_solver.NCSolver;
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
public class NCSolverControllerImpl implements SolverController {
    private PTParameters ptParameters;
    private int timeLimit;
    private final static String SOLVER_ID = "NCSolver";

    @Override
    public String solve(NodeCandidates nodeCandidates, ConstraintProblem cp, UtilityGeneratorApplication utilityGenerator, String cpId) {
        log.info("Starting " + SOLVER_ID + " on " + cpId);
        NCSolver solver = new NCSolver(ptParameters.getMinTmp(), ptParameters.getMaxTmp(), ptParameters.getNumThreads(), cp, new UtilityProviderImpl(utilityGenerator), nodeCandidates);
        return solutionToString(solver.solve(new MaxRuntime(timeLimit, TimeUnit.SECONDS)), cpId);
    }

    private String solutionToString(Pair<List<VariableValueDTO>, Double> solution, String cpId) {
        return SolverSolutionToStringConverter.convertToString(solution, SOLVER_ID, cpId, timeLimit, ptParameters);
    }
}
