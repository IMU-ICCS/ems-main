package eu.melodic.upperware.utilitygenerator.utility_function.utility_templates_provider;

import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableDTO;
import eu.paasage.upperware.metamodel.cp.VariableType;
import eu.passage.upperware.commons.model.tools.metadata.CamelMetadata;

import java.util.*;
import java.util.stream.Collectors;

import static eu.melodic.upperware.utilitygenerator.node_candidates.NodeCandidateAttribute.createAttributeName;

public class BasicTemplatesProvider {
    static String polynomial(String expr) {
        return "(-(" + add(expr, "1") +"^2 - 1))";
    }
    static String inverse(String expr) {
        return "(1/" + expr + ")";
    }
    static String multiply(String expr, String expr2) {
        return "(" + expr2 + "*" +expr +")";
    }
    static String add(String expr1, String expr2) {
        return "(" + expr1 + "+" + expr2 + ")";
    }
    static String minus(String expr1, String expr2) {
        return "(" + expr1 + "-" + expr2 + ")";
    }
    static String normalizedArcTangens(String expr) {
        return "(" + "(2/pi) * atg(" + expr + "))";
    }
    static String normalizedMinusArcTangens(String expr) {
        return "(" + "(2/pi) * atg(-" + expr + ") + 1)";
    }

    static String invExp(String exp) {
        return "(exp(-" + exp + "))";
    }

    /*
        @expr should be an Latitude / Longitude variable
     */
    static String convertToRadians(String expr) {
        return "((pi/18000)*" + expr + ")";
    }

    static String getSumOfGivenType(VariableType type, Collection<VariableDTO> variablesFromConstraintProblem) {
        Collection<String> variables = getVariablesOfGivenType(type, variablesFromConstraintProblem);
        return "(" + String.join("+", variables) + ")";
    }

    static String getDistanceFormula(String lat1, String longt1, String lat2, String longt2) {
        lat1 = convertToRadians(lat1);
        lat2 = convertToRadians(lat2);
        longt1 = convertToRadians(longt1);
        longt2 = convertToRadians(longt2);
        String X = "( cos(" + lat2 + ")*cos(" + longt2 +") - cos(" + lat1 + ")*cos(" + longt1 + "))";
        String Y = "( cos(" + lat2 + ")*sin(" + longt2 +") - cos(" + lat1 + ")*sin(" + longt1 + "))";
        String Z = "(sin(" + lat2 + ") - sin(" + lat1 +"))";
        return "( (" + "(" + X + ")^2+" + "(" + Y + ")^2+" + "(" + Z + ")^2)^(1/2))";
    }

    static String getMax(Collection<String> expressions) {
        return "(max(" + String.join(",", expressions) + "))";
    }

    static String getMin(Collection<String> expressions) {
        return "(min(" + String.join(",", expressions) + "))";
    }

    static String getProduct(Collection<String> expressions) {
        return "(prod(" + String.join(",", expressions) + "))";
    }

    static String getSum(Collection<String> expressions) {
        return "(sum(" + String.join("+", expressions) + "))";
    }

    static String getSumOfGivenTypeTimesCardinality(VariableType type, Collection<VariableDTO> variablesFromConstraintProblem) {
        Map<String, String> componentToCardinalityVariable = new HashMap<>();
        variablesFromConstraintProblem.stream()
                .filter(v -> type.equals(v.getType()))
                .forEach(variableDTO -> componentToCardinalityVariable.put(variableDTO.getComponentId(), variableDTO.getId()));

        variablesFromConstraintProblem.stream()
                .filter(v -> VariableType.CARDINALITY.equals(v.getType()))
                .forEach(variableDTO -> componentToCardinalityVariable.put(variableDTO.getComponentId(),
                        variableDTO.getId()+ "*" + componentToCardinalityVariable.get(variableDTO.getComponentId())));

        return "(" + String.join("+", componentToCardinalityVariable.values()) + ")";
    }

    static Collection<String> getVariablesOfGivenType(VariableType type, Collection<VariableDTO> variablesFromConstraintProblem) {
        return variablesFromConstraintProblem.stream()
                .filter(v -> type.equals(v.getType()))
                .map(v -> v.getId()).collect(Collectors.toList());
    }

    static String getCostFormula(Collection<VariableDTO> variablesFromConstraintProblem) {
        Collection<String> componentCosts = new ArrayList<>();
        variablesFromConstraintProblem.stream()
                .filter(v -> VariableType.CARDINALITY.equals(v.getType()))
                .forEach(v -> componentCosts.add(v.getId() + "*"
                        + createAttributeName(v.getComponentId(), CamelMetadata.PRICE)));

        return "(" + String.join("+", componentCosts) + ")";
    }

    static List<String> getComponentsNames(Collection<VariableDTO> variablesFromConstraintProblem) {
        return variablesFromConstraintProblem.stream()
                .filter(v -> VariableType.CARDINALITY.equals(v.getType()))
                .map(v -> v.getComponentId())
                .collect(Collectors.toList());
    }
    static Integer getComponentsCount(Collection<VariableDTO> variablesFromConstraintProblem) {
        return getComponentsNames(variablesFromConstraintProblem).size();
    }

    static Collection<String> getDistancesBetweenConsecutiveComponents(Collection<VariableDTO> variablesFromConstraintProblem) {
        List<String> components = getComponentsNames(variablesFromConstraintProblem);

        Map<String, String> componentToLatitude = new HashMap<>();
        Map<String, String> componentToLongitude = new HashMap<>();
        variablesFromConstraintProblem.stream()
                .filter(v -> VariableType.LATITUDE.equals(v.getType())).forEach(v -> componentToLatitude.put(v.getComponentId(), v.getId()));
        variablesFromConstraintProblem.stream()
                .filter(v -> VariableType.LONGITUDE.equals(v.getType())).forEach(v -> componentToLongitude.put(v.getComponentId(), v.getId()));
        Collections.sort(components);

        List<String> result = new ArrayList<>();
        for (int i = 1; i < components.size(); i++) {
            result.add(
                    getDistanceFormula(
                    componentToLatitude.get(components.get(i)), componentToLongitude.get(components.get(i)),
                            componentToLatitude.get(components.get(i-1)), componentToLongitude.get(components.get(i-1))
            ));
        }
        return result;
    }
}
