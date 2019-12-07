import CPWrapper.Utils.ExpressionEvaluator;
import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.metamodel.types.BasicTypeEnum;
import eu.paasage.upperware.metamodel.types.NumericValueUpperware;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionEvaluatorTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    public void divideByZeroTest() {
        List<Double> args = new ArrayList<>();
        args.add(1.0);
        args.add(0.0);
        assertThrows(RuntimeException.class, () -> {
            ExpressionEvaluator.evaluateOnOperator(OperatorEnum.DIV, args);
        });
    }

    @Test
    public void operatorEvaluationTest() {
        double a = Math.random();
        double b = Math.random();
        List<Double> args = new ArrayList<>();
        args.add(a);
        args.add(b);
        OperatorEnum[] operators = new OperatorEnum[]{OperatorEnum.PLUS, OperatorEnum.MINUS, OperatorEnum.TIMES,
                                                            OperatorEnum.DIV, OperatorEnum.EQ};
        Double[] results = new Double[]{a+b, a-b, a*b, a/b, a==b ? 1.0 : 0.0};
        for (int i = 0; i < results.length; i++) {
            double res = ExpressionEvaluator.evaluateOnOperator(operators[i], args);
            System.out.println("Wynik: " + res + " " + i + " should be " + results[i]);
            assertTrue(Math.abs(res - results[i]) <= ExpressionEvaluator.PRECISION);
        }
    }

    @Test
    public void numericalPrecisionTest() {
        double eps = 0.01*ExpressionEvaluator.PRECISION;
        double a = Math.random();
        double b = Math.random();
        assertTrue(ExpressionEvaluator.evaluateComparator(ComparatorEnum.EQUAL_TO,a,a+eps));

        assertFalse(ExpressionEvaluator.evaluateComparator(ComparatorEnum.GREATER_THAN, a , a +eps));

        assertTrue(ExpressionEvaluator.evaluateComparator(ComparatorEnum.GREATER_OR_EQUAL_TO, a , a +eps));
    }

    @Test
    public void basicExpressionEvaluationTest() {
        double a = Math.random();
        NumericValueUpperware val = new NumericValueUpperwareImplMockup(a);
        Constant c = new ConstantImplMockup(BasicTypeEnum.DOUBLE, val);
        assertTrue(ExpressionEvaluator.evaluateExpression(c, new HashMap<String, Double>()) == a);

        CpMetric m = new CpMetricImplMockup(BasicTypeEnum.DOUBLE, val);
        assertTrue(ExpressionEvaluator.evaluateExpression(m, new HashMap<String, Double>()) == a);
    }

    @Test
    public void variableEvaluationTest() {
        double a = Math.random();
        String name = "Variable11";
        CpVariable var = new CpVariableImplMockup(name, VariableType.CPU);
        Map<String, Double> vars = new HashMap<>();
        vars.put(name, a);
        assertTrue(ExpressionEvaluator.evaluateExpression(var, vars) == a);
    }

    @Test
    public void composedExpressionEvaluationTest() {
        //((a + b) * c - d) / e;
        double[] vals = new double[] {Math.random(), Math.random(), Math.random(), Math.random(), Math.random()};
        if (vals[4] == 0.0) vals[4] = 2.3;

        double realValue = (((vals[0] + vals[2]) * vals[1]) - vals[3])/vals[4];

        String[] names = new String[]{"Variable", "qwerty", "wewrfdvdfbfdvd"};
        Constant c = new ConstantImplMockup(BasicTypeEnum.DOUBLE, new NumericValueUpperwareImplMockup(vals[0]));
        CpMetric m = new CpMetricImplMockup(BasicTypeEnum.DOUBLE, new NumericValueUpperwareImplMockup(vals[1]));
        CpVariable var1 = new CpVariableImplMockup(names[0], VariableType.CORES);
        CpVariable var2 = new CpVariableImplMockup(names[1], VariableType.CORES);
        CpVariable var3 = new CpVariableImplMockup(names[2], VariableType.CORES);

        EList<NumericExpression> exprs = new BasicEList<>();
        exprs.add(c); exprs.add(var1);
        NumericExpression sum = new ComposedExpressionImplMockup(exprs, OperatorEnum.PLUS);
        exprs = new BasicEList<>();
        exprs.add(sum); exprs.add(m);
        NumericExpression times = new ComposedExpressionImplMockup(exprs, OperatorEnum.TIMES);
        exprs = new BasicEList<>();
        exprs.add(times); exprs.add(var2);
        NumericExpression minus = new ComposedExpressionImplMockup(exprs, OperatorEnum.MINUS);
        exprs = new BasicEList<>();
        exprs.add(minus); exprs.add(var3);
        NumericExpression composed = new ComposedExpressionImplMockup(exprs, OperatorEnum.DIV);

        Map<String, Double> vars = new HashMap<>();
        vars.put(names[0], vals[2]);
        vars.put(names[1], vals[3]);
        vars.put(names[2], vals[4]);
        assertTrue(ExpressionEvaluator.evaluateExpression(composed, vars) == realValue);
    }
}