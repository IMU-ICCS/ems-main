package utility;

import cp_wrapper.utility_provider.UtilityProvider;
import cp_wrapper.mockups.*;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.metamodel.types.BasicTypeEnum;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Methods {
    // Copied problem from PTSolver.
    public static Map<ConstraintProblem, UtilityProvider> prepareSimpleConstraintProblem() {
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

        List<String> variables = Arrays.asList("var1", "var2", "var3", "var4", "var5", "var6", "var7", "var8");
        EList<CpVariable> vars = new BasicEList<>();
        for (int i= 0; i < 3; i++) vars.add(new CpVariableImplMockup(variables.get(i), VariableType.CARDINALITY));
        RangeDomainImpMockup dom1  = new RangeDomainImpMockup();
        RangeDomainImpMockup dom3  = new RangeDomainImpMockup();
        dom1.setFrom(1);dom3.setFrom(0);dom1.setTo(5);dom3.setTo(9);
        NumericListDomainImplMockup dom2 = new NumericListDomainImplMockup();
        dom2.setValues(Arrays.asList(0.5, 1.5, 2.5));
        List<Domain> domains = Arrays.asList(new Domain[] {dom1, dom2, dom3});
        for (int i = 0; i <3 ; i++ ){
            vars.get(i).setDomain(domains.get(i));
        }

        Constant c = new ConstantImplMockup(BasicTypeEnum.DOUBLE, new NumericValueUpperwareImplMockup(3));

        EList<NumericExpression> exprs = new BasicEList<>();
        exprs.add(vars.get(0)); exprs.add(vars.get(1));
        NumericExpression times = new ComposedExpressionImplMockup(exprs, OperatorEnum.TIMES);
        ComparisonExpressionMockup constraint1 = new ComparisonExpressionMockup();
        constraint1.setExp1(vars.get(0));constraint1.setExp2(vars.get(2));
        constraint1.setComparator(ComparatorEnum.LESS_THAN);

        ComparisonExpressionMockup constraint3 = new ComparisonExpressionMockup();
        constraint3.setExp1(times);constraint3.setExp2(vars.get(2));
        constraint3.setComparator(ComparatorEnum.GREATER_OR_EQUAL_TO);

        exprs.add(vars.get(2));
        ComparisonExpressionMockup constraint2 = new ComparisonExpressionMockup();
        times = new ComposedExpressionImplMockup(exprs, OperatorEnum.TIMES);
        constraint2.setExp1(times);constraint2.setExp2(c);
        constraint2.setComparator(ComparatorEnum.GREATER_OR_EQUAL_TO);

        ComparisonExpressionMockup constraint4 = new ComparisonExpressionMockup();
        constraint4.setExp1(vars.get(2));constraint4.setExp2(vars.get(2));
        constraint4.setComparator(ComparatorEnum.EQUAL_TO);

        EList<Constant> consts = new BasicEList<>();
        consts.add(c);

        EList<CpVariable> varsE = new BasicEList<>();
        for (int i = 0; i < 3; i++) varsE.add(vars.get(i));

        EList<ComparisonExpression> constraints = new BasicEList<>();
        constraints.addAll(Arrays.asList(constraint1, constraint2, constraint3, constraint4));

        ConstraintProblem cp = new ConstraintProblemMockup(consts,null, varsE, constraints );
        return Collections.singletonMap(cp, result -> {
            double sum = 0;
            for (VariableValueDTO v : result) {
                if (v.getValue() instanceof Double) {
                    sum += v.getValue().doubleValue();
                } else {
                    sum += v.getValue().intValue();
                }
            }
            return sum;
        });
    }

    public static Map<ConstraintProblem, UtilityProvider> prepareLessSimpleConstraintProblem() {
        /*
              @var1 in {1,...,10}
              @var2 in {0.5, 1.5, 2.5, 7.5, 10}
              @var3 in {0,...,9}
              @var4 in {1, 2, 3}
              @var5 in {0.5, 7.5, 12.5}
              @const1 = 3
              @const2 = 6

              @constraint1 : @var1 < @var3

              @constraint2 : @var1 * @var2 * @var3 >= @const1;

              @constraint3: @var1 * @var2 >= @var3

              @constraint4: @var3 == @var3

              @constraint5: @var5 * @var1 < @var2 * @const2
         */

        List<String> variables = Arrays.asList("var1", "var2", "var3", "var4", "var5", "var6", "var7", "var8", "var9", "var10", "var11", "var12");
        EList<CpVariable> vars = new BasicEList<>();
        for (int i= 0; i < 5; i++) vars.add(new CpVariableImplMockup(variables.get(i), VariableType.CARDINALITY));
        RangeDomainImpMockup dom1  = new RangeDomainImpMockup();
        RangeDomainImpMockup dom3  = new RangeDomainImpMockup();
        RangeDomainImpMockup dom4  = new RangeDomainImpMockup();
        dom1.setFrom(1); dom1.setTo(10);
        dom3.setFrom(0); dom3.setTo(9);
        dom4.setFrom(1); dom4.setTo(3);
        NumericListDomainImplMockup dom2 = new NumericListDomainImplMockup();
        NumericListDomainImplMockup dom5 = new NumericListDomainImplMockup();
        dom2.setValues(Arrays.asList(0.5, 1.5, 2.5, 7.5, 10.0));
        dom5.setValues(Arrays.asList(0.5, 7.5, 12.5));
        List<Domain> domains = Arrays.asList(new Domain[] {dom1, dom2, dom3, dom4, dom5});
        for (int i = 0; i < 5 ; i++ ){
            vars.get(i).setDomain(domains.get(i));
        }

        Constant c = new ConstantImplMockup(BasicTypeEnum.DOUBLE, new NumericValueUpperwareImplMockup(3));
        Constant c2 = new ConstantImplMockup(BasicTypeEnum.DOUBLE, new NumericValueUpperwareImplMockup(6));

        EList<NumericExpression> exprs = new BasicEList<>();
        exprs.add(vars.get(0)); exprs.add(vars.get(1));
        NumericExpression times = new ComposedExpressionImplMockup(exprs, OperatorEnum.TIMES);
        ComparisonExpressionMockup constraint1 = new ComparisonExpressionMockup();
        constraint1.setExp1(vars.get(0));constraint1.setExp2(vars.get(2));
        constraint1.setComparator(ComparatorEnum.LESS_THAN);

        ComparisonExpressionMockup constraint3 = new ComparisonExpressionMockup();
        constraint3.setExp1(times);constraint3.setExp2(vars.get(2));
        constraint3.setComparator(ComparatorEnum.GREATER_OR_EQUAL_TO);

        exprs.add(vars.get(2));
        ComparisonExpressionMockup constraint2 = new ComparisonExpressionMockup();
        times = new ComposedExpressionImplMockup(exprs, OperatorEnum.TIMES);
        constraint2.setExp1(times);constraint2.setExp2(c);
        constraint2.setComparator(ComparatorEnum.GREATER_OR_EQUAL_TO);

        ComparisonExpressionMockup constraint4 = new ComparisonExpressionMockup();
        constraint4.setExp1(vars.get(2));constraint4.setExp2(vars.get(2));
        constraint4.setComparator(ComparatorEnum.EQUAL_TO);


        EList<NumericExpression> exprs2 = new BasicEList<>();
        EList<NumericExpression> exprs3 = new BasicEList<>();
        exprs2.add(vars.get(0)); exprs2.add(vars.get(4));
        exprs3.add(vars.get(1)); exprs3.add(c2);
        ComparisonExpressionMockup constraint5 = new ComparisonExpressionMockup();
        times = new ComposedExpressionImplMockup(exprs2, OperatorEnum.TIMES);

        constraint5.setExp1(times);
        times = new ComposedExpressionImplMockup(exprs3, OperatorEnum.TIMES);
        constraint5.setExp2(times);
        constraint5.setComparator(ComparatorEnum.LESS_THAN);

        EList<Constant> consts = new BasicEList<>();
        consts.add(c);

        EList<CpVariable> varsE = new BasicEList<>();
        for (int i = 0; i < 5; i++) varsE.add(vars.get(i));

        EList<ComparisonExpression> constraints = new BasicEList<>();
        constraints.addAll(Arrays.asList(constraint1, constraint2, constraint3, constraint4, constraint5));

        ConstraintProblem cp = new ConstraintProblemMockup(consts,null, varsE, constraints );
        return Collections.singletonMap(cp, result -> {
            double sum = 0;
            for (VariableValueDTO v : result) {
                if (v.getValue() instanceof Double) {
                    sum += v.getValue().doubleValue();
                } else {
                    sum += v.getValue().intValue();
                }
            }
            return sum;
        });
    }

}
