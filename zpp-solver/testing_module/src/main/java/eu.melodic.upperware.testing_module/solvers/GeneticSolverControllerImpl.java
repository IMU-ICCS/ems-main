package eu.melodic.upperware.testing_module.solvers;


import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.genetic_solver.runner.GeneticSolverRunner;
import eu.melodic.upperware.testing_module.utils.GeneticSolverParameters;
import eu.melodic.upperware.testing_module.utils.SolverSolutionToStringConverter;
import eu.melodic.upperware.testing_module.utils.UtilityGeneratorMaster;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class GeneticSolverControllerImpl implements SolverController {
    private GeneticSolverParameters geneticSolverParameters;
    private int timeLimit;
    private final static String SOLVER_ID = "GeneticSolver";

    @Override
    public String solve(NodeCandidates nodeCandidates, ConstraintProblem cp, UtilityGeneratorMaster utilityGeneratorMaster, String cpId) {
        log.info("Starting " + SOLVER_ID + " on " + cpId);
        GeneticSolverRunner runner = setParameters(new GeneticSolverRunner());
        List<VariableValueDTO> solution = runner.run(cp, utilityGeneratorMaster.create());
        return solutionToString(new Pair<>(solution, runner.getFinalUtility()), cpId);
    }

    private GeneticSolverRunner setParameters(GeneticSolverRunner runner) {
        runner.setPopulationSize(geneticSolverParameters.getPopulationSize());
        runner.setTimeLimitSeconds(timeLimit);
        runner.setComparatorProbability(geneticSolverParameters.getComparatorProbability());
        runner.setCrossoverProbability(geneticSolverParameters.getCrossoverProbability());
        runner.setGuesses(geneticSolverParameters.getGuesses());
        runner.setMutationProbability(geneticSolverParameters.getMutationProbability());
        runner.setMutatorProbability(geneticSolverParameters.getMutatorProbability());
        return runner;
    }

    private String solutionToString(Pair<List<VariableValueDTO>, Double> solution, String cpId) {
        return SolverSolutionToStringConverter.convertToString(solution, SOLVER_ID, cpId, timeLimit, geneticSolverParameters);
    }
}
