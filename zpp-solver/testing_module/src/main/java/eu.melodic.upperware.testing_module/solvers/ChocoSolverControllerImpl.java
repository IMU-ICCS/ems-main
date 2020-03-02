package eu.melodic.upperware.testing_module.solvers;

import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.cpsolver.solver.CpSolution;
import eu.melodic.upperware.cpsolver.solver.parser.CommonConstraintProblemParser;
import eu.melodic.upperware.cpsolver.solver.parser.ConstraintProblemParser;
import eu.melodic.upperware.cpsolver.solver.parser.SolverParsedData;
import eu.melodic.upperware.cpsolver.solver.parser.creator.IntConstantCreator;
import eu.melodic.upperware.cpsolver.solver.parser.creator.IntMetricCreator;
import eu.melodic.upperware.cpsolver.solver.parser.creator.IntVarCreator;
import eu.melodic.upperware.testing_module.utils.SolverSolutionToStringConverter;
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTOFactory;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@AllArgsConstructor
public class ChocoSolverControllerImpl implements SolverController {
    private int timeLimit;
    private final static String SOLVER_ID = "ChocoSolver";
    @Override
    public String solve(NodeCandidates nodeCandidates, ConstraintProblem cp, UtilityGeneratorApplication utilityGenerator, String cpID) {
        log.info("Starting " + SOLVER_ID + " on " + cpID);
        ConstraintProblemParser constraintProblemParser = new CommonConstraintProblemParser(new IntVarCreator(), new IntConstantCreator(), new IntMetricCreator());
        SolverParsedData solverParsedData = constraintProblemParser.parse(cp);
        List<CpSolution> solutions = solverParsedData.solve(timeLimit);
        double maxUtility = 0.0;
        List<VariableValueDTO> bestSolution = Collections.emptyList();
        for (CpSolution solution : solutions) {
            List<VariableValueDTO> result = convertToVariableValues(solution);
            double utility = utilityGenerator.evaluate(result);
            if (utility > maxUtility) {
                maxUtility = utility;
                bestSolution = result;
            }
        }
        return SolverSolutionToStringConverter.convertToString(new Pair<>(bestSolution, maxUtility), SOLVER_ID, cpID, timeLimit);
    }

    private List<VariableValueDTO> convertToVariableValues(CpSolution solution) {

        List<VariableValueDTO> intPart = solution.getIntVars()
                .values()
                .stream()
                .map(intVar -> VariableValueDTOFactory.createElement(intVar.getName(), intVar.getValue()))
                .collect(Collectors.toList());

        List<VariableValueDTO> realPart = solution.getRealVars()
                .values()
                .stream()
                .map(realVar -> VariableValueDTOFactory.createElement(realVar.getName(), realVar.getUB()))
                .collect(Collectors.toList());

        return Stream.concat(realPart.stream(), intPart.stream()).collect(Collectors.toList());
    }
}
