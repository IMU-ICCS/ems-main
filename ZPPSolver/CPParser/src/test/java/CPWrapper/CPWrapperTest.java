package CPWrapper;

import CPWrapper.Mockups.*;
import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.metamodel.types.BasicTypeEnum;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CPWrapperTest {
    private static CPWrapper cpWrapper;
    private static List<String> variables;

    @BeforeAll
    private static void prepareSimpleConstraintProblem() {
        /*
              @var1 in {1,2,3,4,5}
              @var2 in {0.5, 1.5, 2.5}
              @var3 in {0,...,9}
              @const1 = 3

              @constraint1 : @var1 < @var3

              @constraint2 : @var1 * @var2 * @var3 >= @const1;

              @constraint3: @var1 * @var2 >= @var3

              @constraint4: @var3 == @var3
         */
        variables = Arrays.asList(new String[]{"var1", "var2", "var3", "var4", "var5", "var6", "var7", "var8"});
        EList<CpVariable> vars = new BasicEList<>();
        for (int i= 0; i < 3; i++) vars.add(new CpVariableImplMockup(variables.get(i), VariableType.CARDINALITY));
        RangeDomainImpMockup dom1  = new RangeDomainImpMockup();
        RangeDomainImpMockup dom3  = new RangeDomainImpMockup();
        dom1.setFrom(1);dom3.setFrom(0);dom1.setTo(5);dom3.setTo(9);
        NumericListDomainImplMockup dom2 = new NumericListDomainImplMockup();
        dom2.setValues(Arrays.asList(new Double[] {0.5, 1.5, 2.5}));
        List<Domain> domains = Arrays.asList(new Domain[] {dom1, dom2, dom3});
        for (int i = 0; i <3 ; i++ ){
            vars.get(i).setDomain(domains.get(i));
        }

        Constant c = new ConstantImplMockup(BasicTypeEnum.DOUBLE, new NumericValueUpperwareImplMockup(3));

        EList<NumericExpression> exprs = new BasicEList<>();
        exprs.add(vars.get(0)); exprs.add(vars.get(1));
        NumericExpression times = new ComposedExpressionImplMockup(exprs, OperatorEnum.TIMES);
        ConstraintMockup constraint1 = new ConstraintMockup();
        constraint1.setExp1(vars.get(0));constraint1.setExp2(vars.get(2));
        constraint1.setComparator(ComparatorEnum.LESS_THAN);

        ConstraintMockup constraint3 = new ConstraintMockup();
        constraint3.setExp1(times);constraint3.setExp2(vars.get(2));
        constraint3.setComparator(ComparatorEnum.GREATER_OR_EQUAL_TO);

        exprs.add(vars.get(2));
        ConstraintMockup constraint2 = new ConstraintMockup();
        times = new ComposedExpressionImplMockup(exprs, OperatorEnum.TIMES);
        constraint2.setExp1(times);constraint2.setExp2(c);
        constraint2.setComparator(ComparatorEnum.GREATER_OR_EQUAL_TO);

        ConstraintMockup constraint4 = new ConstraintMockup();
        constraint4.setExp1(vars.get(2));constraint4.setExp2(vars.get(2));
        constraint4.setComparator(ComparatorEnum.EQUAL_TO);

        EList<Constant> consts = new BasicEList<>();
        consts.add(c);

        EList<CpVariable> varsE = new BasicEList<>();
        for (int i = 0; i < 3; i++) varsE.add(vars.get(i));

        EList<ComparisonExpression> constraints = new BasicEList<>();
        constraints.addAll(Arrays.asList(new ComparisonExpression[] { constraint1, constraint2, constraint3, constraint4}));

        ConstraintProblem cp = new ConstraintProblemMockup(consts,null, varsE, constraints );

        cpWrapper = new CPWrapper();
        cpWrapper.parse(cp, new UtilityProviderMockup());
    }

    /*
           Variable order is var3, var1, var2, because
           HeuristicVariableOrderer is used.
     */
    @Test
    public void countViolatedConstraintsTest() {

        assertTrue( cpWrapper.countViolatedConstraints(Arrays.asList(new Integer[] {0, 0, 0})) == 2);

        assertTrue( cpWrapper.countViolatedConstraints(Arrays.asList(new Integer[] {0, 0, 2})) == 2);

        assertTrue( cpWrapper.countViolatedConstraints(Arrays.asList(new Integer[] {2, 0, 2})) == 0);

        assertTrue( cpWrapper.countViolatedConstraints(Arrays.asList(new Integer[] {2, 2, 2})) == 1);
    }

    @Test
    public void isFeasibleTest() {
        assertFalse( cpWrapper.isFeasible(Arrays.asList(new Integer[] {0, 0, 0})));

        assertFalse( cpWrapper.isFeasible(Arrays.asList(new Integer[] {0, 0, 2})));

        assertTrue( cpWrapper.isFeasible(Arrays.asList(new Integer[] {2, 0, 2})));
    }

    @Test
    public void getHeuristicEvaluationTest() {
        assertTrue( cpWrapper.getHeuristicEvaluation(Arrays.asList(new Integer[] {2,2,2}), 1) == 2);

        assertTrue( cpWrapper.getHeuristicEvaluation(Arrays.asList(new Integer[] {2,2,2}), 0) == 2);

        assertTrue( cpWrapper.getHeuristicEvaluation(Arrays.asList(new Integer[] {2,2,2}), 0) == 2);

        assertTrue( cpWrapper.getHeuristicEvaluation(Arrays.asList(new Integer[] {5,2,2}), 0) == 0);
    }
}