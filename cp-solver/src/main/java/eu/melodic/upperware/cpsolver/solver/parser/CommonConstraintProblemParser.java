package eu.melodic.upperware.cpsolver.solver.parser;

import eu.melodic.upperware.cpsolver.solver.CPSolverConstants;
import eu.melodic.upperware.cpsolver.solver.parser.creator.*;
import eu.paasage.upperware.metamodel.cp.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.expression.continuous.arithmetic.CArExpression;
import org.chocosolver.solver.expression.continuous.relational.CReExpression;
import org.chocosolver.solver.expression.discrete.arithmetic.ArExpression;
import org.chocosolver.solver.expression.discrete.relational.ReExpression;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.RealVar;
import org.eclipse.emf.common.util.EList;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static eu.melodic.upperware.cpsolver.solver.CPSolverConstants.PRECISION;
import static java.lang.String.format;

@Slf4j
@Service
@AllArgsConstructor
public class CommonConstraintProblemParser implements ConstraintProblemParser {

    private IntVarCreator intVarCreator;
    private IntConstantCreator intConstantCreator;
    private IntMetricCreator intMetricCreator;

    private RealVarCreator realVarCreator;
    private RealConstantCreator realConstantCreator;
    private RealMetricCreator realMetricCreator;

    @Override
    public SolverParsedData parse(ConstraintProblem constraintProblem) {
        SolverData solverData = new SolverData(new Model(format("ConstraintProblem_%s", constraintProblem.getId())));
        parseConstaints(solverData, constraintProblem.getConstraints());
        log.info("SolverParsedData: {}", solverData);

        return solverData.toSolverParsedData();
    }

    private void parseConstaints(SolverData solverData, EList<ComparisonExpression> constraints) {
        for (ComparisonExpression constraint : constraints) {
            boolean intOnly = checkIfIntOnly(constraint);
            if (intOnly) {
                log.info("Constraint {} contains only intVars and will be treated as int constraint", constraint.getId());
                parseIntConstaint(solverData, constraint);
            } else {
                log.info("Constraint {} contains at least one realVar and will be treated as real constraint", constraint.getId());
                parseRealConstaint(solverData, constraint);
            }
        }
    }

    private boolean checkIfIntOnly(ComparisonExpression constraint) {
        return checkIfIntOnly(constraint.getExp1()) && checkIfIntOnly(constraint.getExp2());
    }

    private boolean checkIfIntOnly(Expression expression) {
        if (isConstant(expression)){
            return intConstantCreator.is(expression);
        } else if (isCpVariable(expression)){
            return intVarCreator.is(expression);
        } else if (isCpMetric(expression)) {
            return intMetricCreator.is(expression);
        } else if (isComposedExpression(expression)){
            return checkIfIntOnly((ComposedExpression) expression);
        }
        throw new RuntimeException(format("Unknown Expresssion %s with id: %s", expression.getClass().getSimpleName(), expression.getId()));
    }

    private boolean checkIfIntOnly(ComposedExpression composedExpression) {
        return composedExpression.getExpressions()
                .stream()
                .allMatch(this::checkIfIntOnly);
    }

    private void parseRealConstaint(SolverData solverData, ComparisonExpression constraint) {
        CArExpression expression1 = parseRealExpression(solverData, constraint.getExp1());
        CArExpression expression2 = parseRealExpression(solverData, constraint.getExp2());

        if (isDifferentComparator(constraint.getComparator())){
            //ComparatorEnum.DIFFERENT needs to be used in special way
            getRealComparitionFunction(ComparatorEnum.EQUAL_TO).apply(expression1, expression2).ibex(PRECISION).getOpposite().post();
        } else {
            getRealComparitionFunction(constraint.getComparator()).apply(expression1, expression2).ibex(PRECISION).post();
        }
    }

    private void parseIntConstaint(SolverData solverData, ComparisonExpression constraint) {
        ArExpression arExpression1 = parseIntExpression(solverData, constraint.getExp1());
        ArExpression arExpression2 = parseIntExpression(solverData, constraint.getExp2());

        getIntComparitionFunction(constraint.getComparator()).apply(arExpression1, arExpression2).post();
    }

    private boolean isDifferentComparator(ComparatorEnum comparator){
        return ComparatorEnum.DIFFERENT.equals(comparator);
    }

    private BiFunction<CArExpression, CArExpression, CReExpression> getRealComparitionFunction(ComparatorEnum comparator ) {
        switch (comparator) {
            case GREATER_THAN:
                return CArExpression::gt;
            case LESS_THAN:
                return CArExpression::lt;
            case GREATER_OR_EQUAL_TO:
                return CArExpression::ge;
            case LESS_OR_EQUAL_TO:
                return CArExpression::le;
            case EQUAL_TO:
                return CArExpression::eq;
        }
        throw new RuntimeException(format("Unsupported Comparition operation %s", comparator.getName()));
    }

    private BiFunction<ArExpression, ArExpression, ReExpression> getIntComparitionFunction(ComparatorEnum comparator ) {
        switch (comparator) {
            case GREATER_THAN:
                return ArExpression::gt;
            case LESS_THAN:
                return ArExpression::lt;
            case GREATER_OR_EQUAL_TO:
                return ArExpression::ge;
            case LESS_OR_EQUAL_TO:
                return ArExpression::le;
            case EQUAL_TO:
                return ArExpression::eq;
            case DIFFERENT:
                return ArExpression::ne;
        }
        throw new RuntimeException(format("Unsupported Comparition operation %s", comparator.getName()));
    }

    private CArExpression parseRealExpression(SolverData solverData, Expression expression) {
        if (isConstant(expression)){
            return parseRealConstant(solverData, (Constant) expression);
        } else if (isCpVariable(expression)){
            return parseRealCpVariable(solverData, (CpVariable) expression);
        } else if (isCpMetric(expression)) {
            return parseRealCpMetric(solverData, (CpMetric) expression);
        } else if (isComposedExpression(expression)){
            return parseRealComposedExpression(solverData, (ComposedExpression) expression);
        }
        throw new RuntimeException(format("Unknown Expresssion %s with id: %s", expression.getClass().getSimpleName(), expression.getId()));

    }

    private ArExpression parseIntExpression(SolverData solverData, Expression expression) {
        if (isConstant(expression)){
            return parseIntConstant(solverData, (Constant) expression);
        } else if (isCpVariable(expression)){
            return parseIntCpVariable(solverData, (CpVariable) expression);
        } else if (isCpMetric(expression)) {
            return parseIntCpMetric(solverData, (CpMetric) expression);
        } else if (isComposedExpression(expression)){
            return parseIntComposedExpression(solverData, (ComposedExpression) expression);
        }
        throw new RuntimeException(format("Unknown Expresssion %s with id: %s", expression.getClass().getSimpleName(), expression.getId()));

    }

    private ArExpression parseIntComposedExpression(SolverData solverData, ComposedExpression expression) {
        if (solverData.getAuxExpressions().containsKey(expression.getId())) {
            return solverData.getAuxExpressions().get(expression.getId());
        }

        List<ArExpression> expressions = expression.getExpressions()
                .stream()
                .map(numericExpression -> parseIntExpression(solverData, numericExpression))
                .collect(Collectors.toList());

        ArExpression first = expressions.get(0);
        List<ArExpression> tailExp = expressions.subList(1, expressions.size());

        ArExpression arExpression = getArExpression(first, tailExp, getIntOperatorFunction(expression.getOperator()));
        solverData.getAuxExpressions().put(expression.getId(), arExpression);
        return arExpression;
    }

    private RealVar parseRealCpMetric(SolverData solverData, CpMetric expression) {
        if (intMetricCreator.is(expression)) {
            return intMetricCreator.applyReal(solverData, expression);
        }

        if (realMetricCreator.is(expression)) {
            return realMetricCreator.apply(solverData, expression);
        }
        throw new RuntimeException("Only Integer CpMetrics are supported.");
    }

    private RealVar parseRealCpVariable(SolverData solverData, CpVariable expression) {
        if (intVarCreator.is(expression)) {
            return intVarCreator.applyReal(solverData, expression);
        }

        if (realVarCreator.is(expression)) {
            return realVarCreator.apply(solverData, expression);
        }
        throw new RuntimeException("Only Integer CpVariables are supported.");
    }

    private CArExpression parseRealConstant(SolverData solverData, Constant expression) {
        if (intConstantCreator.is(expression)) {
            return intConstantCreator.applyReal(solverData, expression);
        }

        if (realConstantCreator.is(expression)) {
            return realConstantCreator.apply(solverData, expression);
        }

        throw new RuntimeException("Only Integer Constant are supported.");
    }

    private CArExpression parseRealComposedExpression(SolverData solverData, ComposedExpression expression) {
        if (solverData.getRealAuxExpressions().containsKey(expression.getId())) {
            return solverData.getRealAuxExpressions().get(expression.getId());
        }

        List<CArExpression> expressions = expression.getExpressions()
                .stream()
                .map(numericExpression -> parseRealExpression(solverData, numericExpression))
                .collect(Collectors.toList());

        CArExpression first = expressions.get(0);
        List<CArExpression> tailExp = expressions.subList(1, expressions.size());

        boolean isEqualOperator = isOperatorEqual(expression.getOperator());
        if (isEqualOperator){
            if (tailExp.size() > 1){
                throw new RuntimeException("Equal operation shoud have 2 operants. Current size is: " + tailExp.size()+1);
            }

            CArExpression second = tailExp.get(0);
            first.eq(second).ibex(PRECISION).post();
            return solverData.getModel().realVar("temp_value_1.0", 1.0);
        }

        CArExpression cArExpression = getCArExpression(first, tailExp, getOperatorFunction(expression.getOperator()));
        solverData.getRealAuxExpressions().put(expression.getId(), cArExpression);
        return cArExpression;
    }

    private boolean isOperatorEqual(OperatorEnum operator) {
        return OperatorEnum.EQ.equals(operator);
    }

    private IntVar parseIntCpMetric(SolverData solverData, CpMetric expression) {
        if (intMetricCreator.is(expression)) {
            return intMetricCreator.apply(solverData, expression);
        }
        throw new RuntimeException("Only Integer CpMetrics are supported.");
    }

    private IntVar parseIntCpVariable(SolverData solverData, CpVariable expression) {
        if (intVarCreator.is(expression)) {
            return intVarCreator.apply(solverData, expression);
        }
        throw new RuntimeException("Only Integer CpVariables are supported.");
    }

    private ArExpression parseIntConstant(SolverData solverData, Constant expression) {
        if (intConstantCreator.is(expression)) {
            return intConstantCreator.apply(solverData, expression);
        }

        throw new RuntimeException("Only Integer Constant are supported.");
    }

    private CArExpression getCArExpression(CArExpression first, List<CArExpression> tailExp, BiFunction<CArExpression, CArExpression, CArExpression> addExpr) {
        CArExpression result = first;
        for (CArExpression arExpression : tailExp) {
            result = addExpr.apply(result, arExpression);
        }
        return result;
    }

    private ArExpression getArExpression(ArExpression first, List<ArExpression> tailExp, BiFunction<ArExpression, ArExpression, ArExpression> addExpr) {
        ArExpression result = first;
        for (ArExpression arExpression : tailExp) {
            result = addExpr.apply(result, arExpression);
        }
        return result;
    }

    private BiFunction<CArExpression, CArExpression, CArExpression> getOperatorFunction(OperatorEnum operatorEnum) {
        switch (operatorEnum) {

            case PLUS:
                return CArExpression::add;
            case MINUS:
                return CArExpression::sub;
            case TIMES:
                return CArExpression::mul;
            case DIV:
                return CArExpression::div;
        }
        throw new RuntimeException("Unsupported Operation: " + operatorEnum);
    }

    private BiFunction<ArExpression, ArExpression, ArExpression> getIntOperatorFunction(OperatorEnum operatorEnum) {
        switch (operatorEnum) {

            case PLUS:
                return ArExpression::add;
            case MINUS:
                return ArExpression::sub;
            case TIMES:
                return ArExpression::mul;
            case DIV:
                return ArExpression::div;
            case EQ:
                return ArExpression::eq;
        }
        throw new RuntimeException("Unsupported Operation: " + operatorEnum);
    }

    private boolean isConstant(Expression expression){
        return expression instanceof Constant;
    }

    private boolean isCpVariable(Expression expression){
        return expression instanceof CpVariable;
    }

    private boolean isCpMetric(Expression expression){
        return expression instanceof CpMetric;
    }

    private boolean isComposedExpression(Expression expression){
        return expression instanceof ComposedExpression;
    }

}