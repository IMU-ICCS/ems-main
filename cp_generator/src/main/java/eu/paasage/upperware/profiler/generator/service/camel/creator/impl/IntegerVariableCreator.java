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
public class IntegerVariableCreator implements VariableCreator<Integer> {

    private ConstantService constantService;
    private ConstraintService constraintService;
    private VariableService variableService;

    @Override
    public Variable createVariable(ConstraintProblem cp, VariableType variableType, String componentName, NumericDomain domain) {
        return createWithRange(() -> variableService.createIntegerVariable(variableType, componentName, domain),
                cp, variableType, componentName, unpackDomain(domain));
    }

    @Override
    public Variable createVariable(ConstraintProblem cp, VariableType variableType, String componentName, NumericDomain domain, String variableName) {
        return createWithRange(() -> variableService.createIntegerVariable(variableName, variableType, componentName, domain),
                cp, variableType, componentName, unpackDomain(domain));
    }

    private Variable createWithRange(Supplier<Variable> supplier, ConstraintProblem cp, VariableType variableType, String componentName, Pair<NumericValueUpperware, NumericValueUpperware> domainRange) {

        Variable variable = supplier.get();
        cp.getVariables().add(variable);

        Constant minConstant = constantService.createIntegerConstant(domainRange.getLeft(), constantService.getConstantName(variableType, componentName, "min"));
        cp.getConstants().add(minConstant);

        ComparisonExpression minCompariton = constraintService.createComparisonExpression(variable, ComparatorEnum.GREATER_OR_EQUAL_TO, minConstant);
        cp.getConstraints().add(minCompariton);

        Constant maxConstant = constantService.createIntegerConstant(domainRange.getRight(), constantService.getConstantName(variableType, componentName, "max"));
        cp.getConstants().add(maxConstant);

        ComparisonExpression maxComparition = constraintService.createComparisonExpression(variable, ComparatorEnum.LESS_OR_EQUAL_TO, maxConstant);
        cp.getConstraints().add(maxComparition);

        return variable;
    }
}
