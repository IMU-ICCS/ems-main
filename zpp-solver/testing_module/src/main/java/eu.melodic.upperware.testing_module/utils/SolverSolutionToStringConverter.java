package eu.melodic.upperware.testing_module.utils;

import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import org.javatuples.Pair;

import java.util.List;

public class SolverSolutionToStringConverter {
    public static <T>  String convertToString(Pair<List<VariableValueDTO>, Double> solution, String solverId, String cpId, int timeLimit, T solverParameters) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(solverId).append(";");
        stringBuilder.append(cpId).append(";");
        stringBuilder.append(timeLimit).append(";");
        stringBuilder.append(solution.getValue1()).append(";");
        stringBuilder.append(solverParameters.toString());
        solution.getValue0().forEach(variable -> stringBuilder.append(variable.toString() + ";"));
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    public static String convertToString(Pair<List<VariableValueDTO>, Double> solution, String solverId, String cpId, int timeLimit) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(solverId).append(";");
        stringBuilder.append(cpId).append(";");
        stringBuilder.append(timeLimit).append(";");
        stringBuilder.append(solution.getValue1()).append(";");
        solution.getValue0().forEach(variable -> stringBuilder.append(variable.toString() + ";"));
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }
}
