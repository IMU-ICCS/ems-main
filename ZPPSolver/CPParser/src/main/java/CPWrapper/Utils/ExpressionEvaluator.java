package CPWrapper.Utils;
/*
    This class is used to parse and evaluate Expression
    interfaces from eu.paasage.upperware.metamodel.cp package
 */

import eu.paasage.upperware.metamodel.types.*;
import eu.paasage.upperware.metamodel.cp.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExpressionEvaluator {
    /*
        Precision used to compare floating point numbers.
        For instance if precision is set to 0.01,
        1 and 1.005 are considered equal.
     */
    public static final double PRECISION = 0.1;

    static double getValueOfNumericInterface(NumericValueUpperware value) {
        if (value instanceof IntegerValueUpperware) {
            return (double)((IntegerValueUpperware) value).getValue();
        } else if (value instanceof LongValueUpperware) {
            return (double)(int)((LongValueUpperware) value).getValue();
        } else if (value instanceof DoubleValueUpperware) {
            return ((DoubleValueUpperware) value).getValue();
        } else if (value instanceof FloatValueUpperware) {
            return (double)((FloatValueUpperware) value).getValue();
        }
        throw new RuntimeException("Unsupported NumericValueUpperware implementation");
    }

    static int getValueOfIntegerNumericInterface(IntegerValueUpperware value) {
        if (value instanceof IntegerValueUpperware) {
            return ((IntegerValueUpperware) value).getValue();
        }
        throw new RuntimeException("Only integer values are supported");
    }

    private static boolean isTwoArgsOperator(OperatorEnum oper) {
        return (oper == OperatorEnum.MINUS || oper == OperatorEnum.DIV || oper == OperatorEnum.EQ);
    }

    static double evaluateOnOperator(OperatorEnum oper, List<Double> values) {
        if (isTwoArgsOperator(oper) && values.size() != 2) {
            throw new RuntimeException("MINUS, DIV, EQ operators must be evaluated on exactly two arguments");
        }

        switch (oper) {
            case PLUS:
                return values.stream()
                        .reduce((double) 0, Double::sum);
            case MINUS:
                return values.get(0) - values.get(1);
            case TIMES:
                return values.stream()
                        .reduce((double) 1, (subtotal, element) -> subtotal * element);
            case DIV:
                if ( values.get(1) == 0.0) {
                    throw new RuntimeException("Division by zero encountered");
                } else {
                    return values.get(0) / values.get(1);
                }
            case EQ:
                double diff = values.get(0) - values.get(1);
                return Math.abs(diff) <= ExpressionEvaluator.PRECISION ? 1.0 : 0.0;
        }
        throw new RuntimeException("Unsupported operation type");
    }

    private static double evaluateComposedExpression(ComposedExpression exp, Map<String, Double> variables) {
        List<Double> expressionsValues = exp.getExpressions()
                .stream()
                .map(e -> evaluateExpression(e, variables))
                .collect(Collectors.toList());
        return evaluateOnOperator(exp.getOperator(), expressionsValues);
    }

    static double evaluateExpression(Expression exp, Map<String, Double> variables) {
            if (isConstant(exp)) {
                return getValueOfNumericInterface(((Constant) exp).getValue());
            } else if (isCpMetric(exp)) {
                return getValueOfNumericInterface(((CpMetric) exp).getValue());
            } else if (isCpVariable(exp)) {
                return variables.get(exp.getId());
            } else if (isComposedExpression(exp)) {
                return evaluateComposedExpression((ComposedExpression) exp, variables);
            }
            throw new RuntimeException("Unsupported Expression type");
    }

    public static boolean evaluateComparator(ComparatorEnum comparator, Expression leftExp, Expression rightExp, Map<String, Double> variables) {
        double leftExpValue = evaluateExpression(leftExp, variables);
        double rightExpValue = evaluateExpression(rightExp, variables);
        return evaluateComparator(comparator, leftExpValue, rightExpValue);
    }

    static boolean evaluateComparator(ComparatorEnum comparator, double argLeft, double argRight) {
        switch (comparator) {
            case GREATER_THAN:
                return argLeft > argRight;
            case GREATER_OR_EQUAL_TO:
                return argLeft - argRight > -ExpressionEvaluator.PRECISION;
            case EQUAL_TO:
                return Math.abs(argLeft - argRight) <= ExpressionEvaluator.PRECISION;
            case LESS_OR_EQUAL_TO:
                return argRight - argLeft > -ExpressionEvaluator.PRECISION;
            case LESS_THAN:
                return argLeft < argRight;
            case DIFFERENT:
                return Math.abs(argLeft - argRight) > ExpressionEvaluator.PRECISION;
        }
        throw new RuntimeException("Unsupported comparator type");
    }

    static boolean isConstant(Expression expression){
        return expression instanceof Constant;
    }

    static boolean isCpVariable(Expression expression){
        return expression instanceof CpVariable;
    }

    static boolean isCpMetric(Expression expression){
        return expression instanceof CpMetric;
    }

    static boolean isComposedExpression(Expression expression){
        return expression instanceof ComposedExpression;
    }
}
