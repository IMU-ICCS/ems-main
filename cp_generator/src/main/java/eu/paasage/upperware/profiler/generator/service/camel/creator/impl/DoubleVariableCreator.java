package eu.paasage.upperware.profiler.generator.service.camel.creator.impl;

import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.metamodel.types.NumericValueUpperware;
import eu.paasage.upperware.profiler.generator.service.camel.ConstantService;
import eu.paasage.upperware.profiler.generator.service.camel.ConstraintService;
import eu.paasage.upperware.profiler.generator.service.camel.VariableService;
import eu.paasage.upperware.profiler.generator.service.camel.creator.VariableCreator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DoubleVariableCreator implements VariableCreator<Double> {

    private ConstantService constantService;
    private ConstraintService constraintService;
    private VariableService variableService;

    @Override
    public CpVariable createCpVariable(ConstraintProblem cp, VariableType variableType, String componentName, NumericDomain domain) {
        return createWithRange(() -> variableService.createDoubleCpVariable(variableType, componentName, domain),
                cp, variableType, componentName, unpackDomain(domain));
    }

    @Override
    public CpVariable createCpVariable(ConstraintProblem cp, VariableType variableType, String componentName, NumericDomain domain, String variableName) {
        return createWithRange(() -> variableService.createDoubleCpVariable(variableName, variableType, componentName, domain),
                cp, variableType, componentName, unpackDomain(domain));
    }

    private CpVariable createWithRange(Supplier<CpVariable> supplier, ConstraintProblem cp, VariableType variableType, String componentName, Pair<NumericValueUpperware, NumericValueUpperware> domainRange) {

        CpVariable variable = supplier.get();
        cp.getCpVariables().add(variable);

        Constant minConstant = constantService.createDoubleConstant(domainRange.getLeft(), constantService.getConstantName(variableType, componentName, "min"));
        cp.getConstants().add(minConstant);

        ComparisonExpression minCompariton = constraintService.createComparisonExpression(variable, ComparatorEnum.GREATER_OR_EQUAL_TO, minConstant);
        cp.getConstraints().add(minCompariton);

        Constant maxConstant = constantService.createDoubleConstant(domainRange.getRight(), constantService.getConstantName(variableType, componentName, "max"));
        cp.getConstants().add(maxConstant);

        ComparisonExpression maxComparition = constraintService.createComparisonExpression(variable, ComparatorEnum.LESS_OR_EQUAL_TO, maxConstant);
        cp.getConstraints().add(maxComparition);

        return variable;
    }
}
