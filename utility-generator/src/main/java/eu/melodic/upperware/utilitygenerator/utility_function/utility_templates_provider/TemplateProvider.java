package eu.melodic.upperware.utilitygenerator.utility_function.utility_templates_provider;

import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableDTO;
import eu.paasage.upperware.metamodel.cp.VariableType;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import  java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static eu.melodic.upperware.utilitygenerator.utility_function.utility_templates_provider.BasicTemplatesProvider.*;

@Slf4j
public class TemplateProvider {

    public enum AvailableTemplates {
        COST,
        CORES,
        RAM,
        DISK,
        DISTANCE,
        CORES_MIN_MAX,
        DISK_MIN_MAX,
        RAM_MIN_MAX
    };

    public static String getTemplate(Collection<VariableDTO> variablesFromConstraintProblem,
                                     List<Map.Entry<AvailableTemplates, Double>> utilityComponents) {
                return getSum( utilityComponents.stream().map( (template) ->
                                multiply( template.getValue().toString(), getTemplate(variablesFromConstraintProblem, template.getKey()))
                ).collect(Collectors.toList()));
    }

    private static String getTemplate(Collection<VariableDTO> variablesFromConstraintProblem, AvailableTemplates type) {
        switch (type){
            case COST:
                return getOnlyCostUtility(variablesFromConstraintProblem);
            case CORES:
            case RAM:
            case DISK:
                return polynomial(inverse(add(getSumOfGivenTypeTimesCardinality(templateToVariableType(type), variablesFromConstraintProblem), "1.0")));
            case DISTANCE:
                return getDistance(variablesFromConstraintProblem);
            case RAM_MIN_MAX:
            case DISK_MIN_MAX:
            case CORES_MIN_MAX:
                return getMinMaxPenalty(variablesFromConstraintProblem, templateToVariableType(type));
        }
        throw new RuntimeException("Template type " + type.name() + " is not supported yet");
    }

    private static VariableType templateToVariableType(AvailableTemplates type) {
        switch (type) {
            case RAM:
            case RAM_MIN_MAX:
                return VariableType.RAM;
            case DISK:
            case DISK_MIN_MAX:
                return VariableType.STORAGE;
            case CORES:
            case CORES_MIN_MAX:
                return VariableType.CORES;
        }
        throw new RuntimeException("Can't covert template " + type.name() + " to variable type");
    }

    private static String getOnlyCostUtility(Collection<VariableDTO> variablesFromConstraintProblem) {
        return inverse(getCostFormula(variablesFromConstraintProblem));
    }

    private static String getDistance(Collection<VariableDTO> variablesFromConstraintProblem) {
        Collection<String> distances = getDistancesBetweenConsecutiveComponents(variablesFromConstraintProblem);
        distances = distances.stream().map(BasicTemplatesProvider::invExp).collect(Collectors.toList());
        return multiply(getComponentsCount(variablesFromConstraintProblem).toString(), String.join("+", distances));
    }

    private static String getMinMaxPenalty(Collection<VariableDTO> variablesFromConstraintProblem, VariableType type) {
        String maxOfType = getMax(getVariablesOfGivenType(type, variablesFromConstraintProblem));
        String minOfType = getMin(getVariablesOfGivenType(type, variablesFromConstraintProblem));
        return normalizedMinusArcTangens(minus(maxOfType, minOfType));
    }
}