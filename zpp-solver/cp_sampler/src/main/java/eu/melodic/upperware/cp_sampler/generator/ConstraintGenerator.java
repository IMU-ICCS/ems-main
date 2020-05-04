package eu.melodic.upperware.cp_sampler.generator;

import cp_wrapper.utils.numeric_value.implementations.DoubleValue;
import eu.melodic.upperware.cp_sampler.expressions.*;
import eu.melodic.upperware.cp_sampler.utils.priors.Priors;
import eu.paasage.upperware.metamodel.cp.ComparatorEnum;
import eu.paasage.upperware.metamodel.cp.OperatorEnum;
import org.javatuples.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ConstraintGenerator {
    private enum ExpressionSampleType {
        PRODUCT,
        SUM,
        MIXED
    }

    private Map<Integer, ComparatorEnum> comparatorIndex = new HashMap<Integer, ComparatorEnum>(){{
            put(0, ComparatorEnum.GREATER_OR_EQUAL_TO);
            put(1, ComparatorEnum.GREATER_THAN);
            put(2, ComparatorEnum.LESS_OR_EQUAL_TO);
            put(3, ComparatorEnum.LESS_THAN);
            put(4, ComparatorEnum.DIFFERENT);
            put(5, ComparatorEnum.EQUAL_TO);
        }};
    private Random random = new Random();
    private ConstraintEvaluator constraintEvaluator;
    private VariableGenerator variableGenerator;
    private int componentsCount;

    public ConstraintGenerator(ConstraintEvaluator constraintEvaluator, VariableGenerator variableGenerator, int componentsCount) {
        this.variableGenerator = variableGenerator;
        this.constraintEvaluator = constraintEvaluator;
        this.componentsCount = componentsCount;
    }

    public Constraint generateConstraint() {
        Pair<Expression, ExpressionSampleType> exp = sampleExpression();
        ComparatorEnum comparator = sampleComparator(exp.getValue1());
        Expression constant = constraintEvaluator.getConstant(exp.getValue0(), comparator);
        return new Constraint(exp.getValue0(), constant, comparator);
    }

    private OperatorEnum sampleOperator() {
        double unif = random.nextDouble();
        if (unif <= 0.3) {
            return OperatorEnum.PLUS;
        } else if (unif <= 0.6) {
            return OperatorEnum.MINUS;
        } else {
            return OperatorEnum.TIMES;
        }
    }

    private Expression sampleLongSumOrLongProduct(OperatorEnum operator) {
        List<VariableExpression> vars = variableGenerator.sampleVariablesForLongExpression();
        variableGenerator.removeRandomElementsFromList(vars, 3);
        ComposedExpression exp = new ComposedExpression(vars.get(0), vars.get(1), operator);
        for (int i = 2; i < vars.size(); i++) {
            exp = new ComposedExpression(exp, vars.get(i), operator);
        }
        return exp;
    }

    private ConstantExpression sampleConstant() {
        return new ConstantExpression(new DoubleValue(100*random.nextDouble()));
    }

    private Expression sampleTwoArgExpression() {
        OperatorEnum operator = sampleOperator();
        Pair<VariableExpression, VariableExpression> vars = variableGenerator.samplePairOfVariables();
        if (random.nextDouble() > Priors.CONSTANT_IN_TWO_ARGS_PROB) {
            return new ComposedExpression(vars.getValue0(), vars.getValue1(), operator);
        } else {
            return new ComposedExpression(vars.getValue0(), sampleConstant(), operator);
        }
    }

    private Expression sampleVariableExpression() {
        return variableGenerator.sampleVariableExpression();
    }

    private Expression sampleSimpleExpression() {
        if (random.nextDouble() <= Priors.VARIABLE_EXPRESSION_PROB) {
            return sampleVariableExpression();
        } else {
            return sampleTwoArgExpression();
        }
    }

    private Pair<Expression, ExpressionSampleType> sampleLongExpression() {
        if (random.nextBoolean()) {
            return new Pair<>(
                    sampleLongSumOrLongProduct(OperatorEnum.TIMES),
                    ExpressionSampleType.PRODUCT
            );
        } else {
            return new Pair<>(
                    sampleLongSumOrLongProduct(OperatorEnum.PLUS),
                    ExpressionSampleType.SUM
            );
        }
    }
    private Pair<Expression, ExpressionSampleType>  sampleExpression() {
        int expressionsCount = random.nextInt(Priors.MAX_EXPRESSIONS_PER_CONSTRAINT) + 1;
        if (random.nextDouble() <= Priors.LONG_EXPRESSION_PROB && componentsCount > 1) {
            return sampleLongExpression();
        }
        Expression expression = sampleSimpleExpression();
        expressionsCount--;
        while (expressionsCount > 0) {
            expression = new ComposedExpression(expression, sampleSimpleExpression(), sampleOperator());
            expressionsCount--;
        }
        return new Pair<>(expression, ExpressionSampleType.MIXED);
    }

    private ComparatorEnum sampleComparator(ExpressionSampleType type)
    {
        if (type == ExpressionSampleType.MIXED) {
            return comparatorIndex.get(random.nextInt(comparatorIndex.keySet().size() -1));
        } else {
            return comparatorIndex.get(random.nextInt(comparatorIndex.keySet().size()));
        }
    }
}
