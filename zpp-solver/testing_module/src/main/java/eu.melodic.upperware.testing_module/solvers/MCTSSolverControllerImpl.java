package eu.melodic.upperware.testing_module.solvers;

import cp_wrapper.utility_provider.UtilityProviderFactory;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.mcts_solver.solver.MCTSCoordinator;
import eu.melodic.upperware.mcts_solver.solver.mcts.cp_wrapper.MCTSWrapperFactory;
import eu.melodic.upperware.mcts_solver.solver.mcts.cp_wrapper.MCTSWrapperFactoryImpl;
import eu.melodic.upperware.testing_module.utils.MCTSParameters;
import eu.melodic.upperware.testing_module.utils.SolverSolutionToStringConverter;
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import lombok.AllArgsConstructor;
import org.javatuples.Pair;

import java.util.List;

@AllArgsConstructor
public class MCTSSolverControllerImpl implements SolverController {
    private UtilityProviderFactory utilityProviderFactory;
    private MCTSParameters mctsParameters;
    private int timeLimit;
    private final static String SOLVER_ID = "MCTSSolver";

    @Override
    public String solve(NodeCandidates nodeCandidates, ConstraintProblem cp, UtilityGeneratorApplication utilityGenerator, String cpId) {
        MCTSWrapperFactory mctsWrapperFactory = new MCTSWrapperFactoryImpl(utilityProviderFactory, cp);
        MCTSCoordinator mctsCoordinator = new MCTSCoordinator(mctsParameters.getNumThreads(), mctsParameters.getMinTmp(), mctsParameters.getMaxTmp(), mctsParameters.getIterations());
        try {
            return solutionToString(mctsCoordinator.solve(timeLimit, mctsWrapperFactory), cpId);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String solutionToString(Pair<List<VariableValueDTO>, Double> solution, String cpId) {
        return SolverSolutionToStringConverter.convertToString(solution, SOLVER_ID, cpId, timeLimit, mctsParameters);
    }
}
