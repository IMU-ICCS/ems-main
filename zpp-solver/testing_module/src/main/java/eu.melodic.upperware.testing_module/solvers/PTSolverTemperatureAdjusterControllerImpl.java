package eu.melodic.upperware.testing_module.solvers;

import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.pt_solver.pt_solver.TemperatureAdjuster;
import eu.melodic.upperware.testing_module.utils.PTParameters;
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class PTSolverTemperatureAdjusterControllerImpl implements SolverController {
    private PTParameters ptParameters;
    private int timeLimit;
    private final static String SOLVER_ID = "PTSolver";
    private List<UtilityGeneratorApplication> utilityGeneratorApplicationList;

    @Override
    public String solve(NodeCandidates nodeCandidates, ConstraintProblem cp, UtilityGeneratorApplication utilityGenerator, String cpId) {
        TemperatureAdjuster temperatureAdjuster = new TemperatureAdjuster(timeLimit, ptParameters.getNumThreads(), utilityGeneratorApplicationList, cp);
        return temperatureAdjuster.solve();
    }
}
