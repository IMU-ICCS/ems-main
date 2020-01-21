package eu.melodic.upperware.utilitygenerator.utility_function.utility_templates_provider;

import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableDTO;
import eu.paasage.upperware.metamodel.cp.VariableType;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static eu.melodic.upperware.utilitygenerator.utility_function.utility_templates_provider.BasicTemplatesProvider.*;

@Slf4j
public class TemplateProvider {

    public enum AvailableTemplates {
        ONLY_COST,
        CORES_RAM_DISK_COST,
        CORES_RAM_DISK_COST_DISTANCE,
        CORES_RAM_DISK_COST_DISTANCE_MIN_MAX
    };

    public static String getTemplate(Collection<VariableDTO> variablesFromConstraintProblem,
                                     List<AvailableTemplates> templates, List<Double> templateWeights) {
        if (templates.size() == 1) {
            return getTemplate(variablesFromConstraintProblem, templates.get(0));
        } else {
                return getSum(
                        templates.stream().map(
                        template ->
                                multiply(templateWeights.get(templates.indexOf(template)).toString(),
                                        getTemplate(variablesFromConstraintProblem, template))
                ).collect(Collectors.toList()));
        }
    }

    private static String getTemplate(Collection<VariableDTO> variablesFromConstraintProblem, AvailableTemplates type) {
        switch (type){
            case ONLY_COST:
                return getOnlyCostUtility(variablesFromConstraintProblem);
            case CORES_RAM_DISK_COST:
                return getSimpleUtility(variablesFromConstraintProblem);
            case CORES_RAM_DISK_COST_DISTANCE:
                return getUtilityWithDistanceMinimization(variablesFromConstraintProblem);
            default: //CORES_RAM_DISK_COST_DISTANCE_MIN_MAX
                return getUtilityMinMaxCores(variablesFromConstraintProblem);
        }
    }
    /*
        0.5 * 1/Price + 0.5* (  -0.333*( 1/(CORES + 1)^2 -1 )  -0.333*( 1/(RAM + 1)^2 -1 )  -0.333*( 1/(DISK + 1)^2 -1 ))
     */
    private static String getSimpleUtility(Collection<VariableDTO> variablesFromConstraintProblem) {
        String cost = inverse(getCostFormula(variablesFromConstraintProblem));
        String RAM = getSumOfGivenTypeTimesCardinality(VariableType.RAM, variablesFromConstraintProblem);
        String CORES = getSumOfGivenTypeTimesCardinality(VariableType.CORES, variablesFromConstraintProblem);
        String DISK = getSumOfGivenTypeTimesCardinality(VariableType.STORAGE, variablesFromConstraintProblem);
        String configuration = add(
                multiply(polynomial(inverse(add(DISK, "1.0"))), "0.333"),
                add(
                        multiply(polynomial(inverse(add(CORES, "1.0"))), "0.333"),
                        multiply(polynomial(inverse(add(RAM, "1.0"))), "0.333")
                )
        );
        return add(
                multiply(cost, "0.5"),
                multiply(configuration, "0.5")
        );
    }

    private static String getOnlyCostUtility(Collection<VariableDTO> variablesFromConstraintProblem) {
        return inverse(getCostFormula(variablesFromConstraintProblem));
    }

    private static String getUtilityWithDistanceMinimization(Collection<VariableDTO> variablesFromConstraintProblem) {
        Collection<String> distances = getDistancesBetweenConsecutiveComponents(variablesFromConstraintProblem);
        String utility = getSimpleUtility(variablesFromConstraintProblem);
        distances = distances.stream().map(v -> invExp(v)).collect(Collectors.toList());
        Integer componentsCount = getComponentsCount(variablesFromConstraintProblem);
        return add(
                multiply(utility,"0.7"), multiply(inverse(
                multiply(componentsCount.toString(), String.join("+", distances))
        ), "0.3"));
    }

    private static String getUtilityMinMaxCores(Collection<VariableDTO> variablesFromConstraintProblem) {
        String maxCores = getMax(getVariablesOfGivenType(VariableType.CORES, variablesFromConstraintProblem));
        String minCores = getMin(getVariablesOfGivenType(VariableType.CORES, variablesFromConstraintProblem));
        String coresDifference = normalizedMinusArcTangens(minus(maxCores, minCores));
        Collection<String> distances = getDistancesBetweenConsecutiveComponents(variablesFromConstraintProblem);
        distances = distances.stream().map(v -> invExp(v)).collect(Collectors.toList());
        return add(
                multiply(getSimpleUtility(variablesFromConstraintProblem),"0.5"),
                multiply( "0.5", multiply(getProduct(distances), coresDifference))
        );
    }
}