import eu.paasage.upperware.metamodel.types.*;
import eu.paasage.upperware.metamodel.cp.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExpressionEvaluator {
    public static final double PRECISION = 0.1;

    private static double getValueOfNumericInterface(NumericValueUpperware value) {
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

    private static boolean isTwoArgsOperator(OperatorEnum oper) {
        return (oper == OperatorEnum.MINUS || oper == OperatorEnum.DIV || oper == OperatorEnum.EQ);
    }

    public static double evaluateOnOperator(OperatorEnum oper, List<Double> values) {
        if (isTwoArgsOperator(oper) && values.size() != 2) {
            throw new RuntimeException("MINUS, DIV, EQ operators must be evaluated on exactly two arguments");
        }

        switch (oper) {
            case PLUS:
                return values.stream()
                        .reduce((double) 0, (subtotal, element) -> subtotal + element);
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
        //TODO throw
        return 0.0;
    }

    private static double evaluateComposedExpression(ComposedExpression exp, Map<String, Double> variables) {
        List<Double> expressionsValues = exp.getExpressions()
                .stream()
                .map(e -> evaluateExpression(e, variables))
                .collect(Collectors.toList());
        return evaluateOnOperator(exp.getOperator(), expressionsValues);
    }

    public static double evaluateExpression(Expression exp, Map<String, Double> variables) {
            if (isConstant(exp)) {
                return getValueOfNumericInterface(((Constant) exp).getValue());
            } else if (isCpMetric(exp)) {
                return getValueOfNumericInterface(((CpMetric) exp).getValue());
            } else if (isCpVariable(exp)) {
                return variables.get(exp.getId());
            } else if (isComposedExpression(exp)) {

                return evaluateComposedExpression((ComposedExpression) exp, variables);
            }
            //TODO throw
            return 0.0;
    }

    public static boolean evaluateComparator(ComparatorEnum comparator, double argLeft, double argRight) {
        switch (comparator) {
            case GREATER_THAN:
                return argLeft - argRight > ExpressionEvaluator.PRECISION;
            case GREATER_OR_EQUAL_TO:
                return argLeft - argRight > -ExpressionEvaluator.PRECISION;
            case EQUAL_TO:
                return Math.abs(argLeft - argRight) <= ExpressionEvaluator.PRECISION;
            case LESS_OR_EQUAL_TO:
                return argRight - argLeft > -ExpressionEvaluator.PRECISION;
            case LESS_THAN:
                return argRight - argLeft > ExpressionEvaluator.PRECISION;
            case DIFFERENT:
                return Math.abs(argLeft - argRight) > ExpressionEvaluator.PRECISION;

        }
        //TODO throws
        return false;
    }

    protected static boolean isConstant(Expression expression){
        return expression instanceof Constant;
    }

    protected static boolean isCpVariable(Expression expression){
        return expression instanceof CpVariable;
    }

    protected static boolean isCpMetric(Expression expression){
        return expression instanceof CpMetric;
    }

    protected static boolean isComposedExpression(Expression expression){
        return expression instanceof ComposedExpression;
    }
}
