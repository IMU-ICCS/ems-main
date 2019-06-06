package eu.melodic.upperware.guibackend.controller.process.mapper;

import eu.melodic.upperware.guibackend.controller.process.response.*;
import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.metamodel.cp.impl.ConstraintProblemImpl;
import eu.paasage.upperware.metamodel.types.*;
import eu.passage.upperware.commons.model.tools.CPModelTool;
import org.eclipse.emf.common.util.EList;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CpModelMapper {
    public CpModelResponse mapConstraintProblemToCpModelResponse(ConstraintProblemImpl constraintProblem, String utilityFormula) {
        return CpModelResponse.builder()
                .id(constraintProblem.getId())
                .metrics(mapCpMetricsToResponse(constraintProblem.getCpMetrics()))
                .variables(mapCpVariablesListToResponse(constraintProblem.getCpVariables()))
                .solution(mapSolutionToResponse(CPModelTool.searchLastSolution(constraintProblem.getSolution())))
                .constants(mapConstantsToResponse(constraintProblem.getConstants()))
                .utilityFormula(utilityFormula)
                .build();
    }

    private List<CpItemResponse> mapConstantsToResponse(EList<Constant> constants) {
        return constants.stream()
                .map(constant -> CpItemResponse.builder()
                        .id(constant.getId())
                        .type(constant.getType().getName())
                        .value(mapNumericValueUpperwareToObject(constant.getValue()))
                        .build())
                .collect(Collectors.toList());
    }

    private CpSolutionResponse mapSolutionToResponse(Solution searchLastSolution) {
        return CpSolutionResponse.builder()
                .timestamp(searchLastSolution.getTimestamp())
                .utilityValue(mapNumericValueUpperwareToObject(searchLastSolution.getUtilityValue()))
                .variableValue(mapCpVariablesValueToResponse(searchLastSolution.getVariableValue()))
                .build();
    }

    private List<CpVariableValueResponse> mapCpVariablesValueToResponse(EList<CpVariableValue> variableValue) {
        return variableValue.stream()
                .map(cpVariableValue -> CpVariableValueResponse.builder()
                        .value(mapNumericValueUpperwareToObject(cpVariableValue.getValue()))
                        .variable(mapCpVariableToResponse(cpVariableValue.getVariable()))
                        .build()).collect(Collectors.toList());
    }

    private List<CpVariableResponse> mapCpVariablesListToResponse(EList<CpVariable> cpVariables) {
        return cpVariables.stream()
                .map(this::mapCpVariableToResponse)
                .collect(Collectors.toList());
    }

    private CpVariableResponse mapCpVariableToResponse(CpVariable cpVariable) {
        return CpVariableResponse.builder()
                .id(cpVariable.getId())
                .componentId(cpVariable.getComponentId())
                .variableType(cpVariable.getVariableType().getName())
                .domain(mapCpDomainToResponse(cpVariable.getDomain()))
                .build();
    }

    private CpDomainResponse mapCpDomainToResponse(Domain domain) {
        if (domain instanceof RangeDomain) {
            return CpDomainResponse.builder()
                    .from(mapNumericValueUpperwareToObject(((RangeDomain) domain).getFrom()))
                    .to(mapNumericValueUpperwareToObject(((RangeDomain) domain).getTo()))
                    .type(((RangeDomain) domain).getType().getName())
                    .build();
        } else {
            return CpDomainResponse.builder()
                    .values(mapNumericValueUpperwareListToResponse(((NumericListDomain) domain).getValues()))
                    .type(((NumericListDomain) domain).getType().getName())
                    .build();
        }
    }

    private List<Object> mapNumericValueUpperwareListToResponse(EList<NumericValueUpperware> values) {
        return values.stream()
                .map(this::mapNumericValueUpperwareToObject)
                .collect(Collectors.toList());
    }

    private List<CpItemResponse> mapCpMetricsToResponse(EList<CpMetric> cpMetrics) {
        return cpMetrics.stream()
                .map(cpMetric -> CpItemResponse.builder()
                        .id(cpMetric.getId())
                        .type(cpMetric.getType().toString())
                        .value(mapNumericValueUpperwareToObject(cpMetric.getValue()))
                        .build())
                .collect(Collectors.toList());
    }

    private Object mapNumericValueUpperwareToObject(NumericValueUpperware value) {
        if (value instanceof IntegerValueUpperware) {
            return ((IntegerValueUpperware) value).getValue();
        } else if (value instanceof FloatValueUpperware) {
            return (((FloatValueUpperware) value).getValue());
        } else if (value instanceof DoubleValueUpperware) {
            return ((DoubleValueUpperware) value).getValue();
        } else {
            return ((LongValueUpperware) value).getValue();
        }
    }
}
