package cp_wrapper.utils;

import cp_wrapper.utils.constraint.Constraint;
import cp_wrapper.utils.constraint.ConstraintImpl;
import cp_wrapper.utils.numeric_value.implementations.DoubleValue;
import cp_wrapper.utils.numeric_value.NumericValueInterface;
import cp_wrapper.utils.test_utils.mockups.ComposedExpressionImplMockup;
import cp_wrapper.utils.test_utils.mockups.ConstantImplMockup;
import cp_wrapper.utils.test_utils.mockups.CpVariableImplMockup;
import cp_wrapper.utils.test_utils.mockups.NumericValueUpperwareImplMockup;
import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.metamodel.types.BasicTypeEnum;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ConstraintImplTest {
    private static List<CpVariable> variables;
    private static final String[] names = new String[]{"Variable", "qwerty", "wewrfdvdfbfdvd"};
    @BeforeAll
    public static void setup(){
        variables = new ArrayList<>();
        variables.add(new CpVariableImplMockup(names[0], VariableType.CORES));
        variables.add(new CpVariableImplMockup(names[1], VariableType.CORES));
        variables.add(new CpVariableImplMockup(names[2], VariableType.CORES));
    }
    @Test
    public void shouldThrowEmptyVariables(){
        Map<String, NumericValueInterface> emptyVars = new HashMap<>();
        Constraint constraint = new ConstraintImpl(ComparatorEnum.DIFFERENT, variables.get(0), variables.get(1));
        assertThrows(RuntimeException.class, () -> {
            constraint.evaluate(emptyVars);
        });
    }

    @Test
    public void shouldThrowWrongVariables(){
        Map<String, Double> emptyVars = new HashMap<>();
        Constraint constraint = new ConstraintImpl(ComparatorEnum.DIFFERENT, variables.get(0), variables.get(1));
        Map<String, NumericValueInterface> vars = new HashMap<>();
        vars.put(names[1], new DoubleValue(0.00123));
        vars.put(names[2], new DoubleValue(123.345));
        assertThrows(RuntimeException.class, () -> {
            constraint.evaluate(vars);
        });
    }

    @Test
    public void linearConstraintEvaluationTest(){
        Constant c = new ConstantImplMockup(BasicTypeEnum.DOUBLE, new NumericValueUpperwareImplMockup(1.0));

        Map<String, NumericValueInterface> vars = new HashMap<>();
        vars.put(names[0], new DoubleValue(2.0));
        vars.put(names[1], new DoubleValue(0.00123));
        vars.put(names[2], new DoubleValue(123.345));

        EList<NumericExpression> exprs = new BasicEList<>();
        exprs.addAll(variables);
        Expression sum = new ComposedExpressionImplMockup(exprs, OperatorEnum.PLUS);

        Constraint constraint = new ConstraintImpl(ComparatorEnum.GREATER_OR_EQUAL_TO, sum, c);

        assertTrue(constraint.evaluate(vars));

        constraint = new ConstraintImpl(ComparatorEnum.GREATER_OR_EQUAL_TO, c, sum);

        assertFalse(constraint.evaluate(vars));
    }

    @Test
    public void composedConstraintEvaluationTest() {
        BasicEList<NumericExpression> exprs = new BasicEList<>();
        exprs.add(variables.get(0)); exprs.add(variables.get(1));
        NumericExpression times = new ComposedExpressionImplMockup(exprs, OperatorEnum.TIMES);

        exprs = new BasicEList<>(); exprs.add(times); exprs.add(variables.get(2));
        NumericExpression div = new ComposedExpressionImplMockup(exprs, OperatorEnum.DIV);

        exprs = new BasicEList<>(); exprs.addAll(variables);
        NumericExpression sum = new ComposedExpressionImplMockup(exprs, OperatorEnum.PLUS);

        Map<String, NumericValueInterface> vars = new HashMap<>();
        vars.put(names[0], new DoubleValue(2.0));
        vars.put(names[1], new DoubleValue(3.0));
        vars.put(names[2], new DoubleValue(5.0));
        Constraint constraint = new ConstraintImpl(ComparatorEnum.GREATER_OR_EQUAL_TO, div, sum);
        assertFalse(constraint.evaluate(vars));
    }
}