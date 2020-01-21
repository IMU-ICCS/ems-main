import nc_solver.cp_components.PTSolution;
import cp_wrapper.UtilityProvider;
import cp_wrapper.mockups.*;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.metamodel.types.BasicTypeEnum;
import io.github.cloudiator.rest.model.GeoLocation;
import io.github.cloudiator.rest.model.Hardware;
import io.github.cloudiator.rest.model.Location;
import io.github.cloudiator.rest.model.NodeCandidate;
import nc_solver.NCSolver;
import nc_solver.node_candidate.node_candidate_element.VMConfiguration;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.jamesframework.core.search.stopcriteria.MaxRuntime;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class NCSolverTest {
    private static Map<ConstraintProblem, UtilityProvider> prepareSimpleOneComponentConstraintProblem() {
        /*
              @var1 in {1,2,3,4,5}
              @var3 in {0, 1, 2}
              @var2 in {0,...,9}
              @const1 = 3

              @constraint1 : @var1 < @var3

              @constraint2 : @var1 * @var2 * @var3 >= @const1;
              @constraint3: @var1 * @var2 >= @var3

              @constraint4: @var3 == @var3
         */
        List<String> variables = Arrays.asList("var1", "var2", "var3");
        EList<CpVariable> vars = new BasicEList<>();
        //Domains:
        RangeDomainImpMockup dom1  = new RangeDomainImpMockup();
        RangeDomainImpMockup dom3  = new RangeDomainImpMockup();
        dom3.setType(BasicTypeEnum.INTEGER); dom1.setType(BasicTypeEnum.INTEGER);
        dom1.setFrom(1);dom3.setFrom(0);dom1.setTo(5);dom3.setTo(9);
        NumericListDomainImplMockup dom2 = new NumericListDomainImplMockup();
        dom2.setIntValues(Arrays.asList(0, 1, 2));
        dom2.setType(BasicTypeEnum.INTEGER);

        vars.add(new CpVariableImplMockup(variables.get(0), VariableType.CORES , dom1, "Component1"));
        vars.add(new CpVariableImplMockup(variables.get(1), VariableType.RAM , dom3, "Component1"));
        vars.add(new CpVariableImplMockup(variables.get(2), VariableType.STORAGE , dom2, "Component1"));

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

    private static NodeCandidates getNodesForSimpleProblem() {
        Map<String, Map<Integer, List<NodeCandidate>>> candidates = new HashMap<>();
        Location loc = new Location(); GeoLocation gl = new GeoLocation(); gl.setLatitude(100.0);gl.setLongitude(100.0);
        loc.setGeoLocation(gl);
        List<Integer> ar1 = Arrays.asList(1,2,3,4,5, 5,5,5,5,2);
        List<Integer> ar3 = Arrays.asList(2, 1, 0, 2, 2, 2, 2, 2, 2, 2);
        List<Integer> ar2 = Arrays.asList(9,0,2,3,4,5,6,7,8,9);
        List<String> names = Arrays.asList("q", "w", "e", "r", "t", "y", "u", "i", "o", "p");
        for (int i = 0; i < ar3.size(); i++) {
            Hardware hd = new Hardware();
            hd.setCores(ar1.get(i));
            hd.setRam((long) ar2.get(i));
            hd.setDisk((double) ar3.get(i));
            NodeCandidate nc = new NodeCandidate();
            nc.setLocation(loc);
            nc.setHardware(hd);
            Map<Integer, List<NodeCandidate>> t = new HashMap<>();
            t.put(0, new ArrayList<>());
            t.get(0).add(nc);
            candidates.put(names.get(i), t);
        }
        return NodeCandidates.of(candidates);
    }

    @Test
    public void simpleOneComponentTest() {
        Map<ConstraintProblem, UtilityProvider> data = prepareSimpleOneComponentConstraintProblem();
        NodeCandidates nc = getNodesForSimpleProblem();
        NCSolver ncSolver = new NCSolver(1, 10, 10, data.keySet().iterator().next(),
                data.values().iterator().next(), nc);
        PTSolution solution = ncSolver.solve(new MaxRuntime(10, TimeUnit.SECONDS));
        assertTrue(solution.extractVMConfiguration(0).equals(new VMConfiguration(1,9,2)));
    }

    private static Map<ConstraintProblem, UtilityProvider> prepareSimpleTwoComponentConstraintProblem() {
        /*
              @var0 in {0, 1} - provider
              @var1 in {1,2,3,4,5}
              @var3 in 10* {0.5, 1.5, 2.5}
              @var2 in {0,...,9}

              @var4 in {0, 1}
              @var5 in {1,2,3,4,5}
              @var7 in 10 * {0.5, 1.5, 2.5}
              @var6 in {0,...,9}

              @const1 = 30

              @constraint1 : @var1 < @var3/10

              @constraint2 : @var1 * @var2 * @var3 >= @const1;
              @constraint3: @var1 * @var2 >= @var3/10

              @constraint4: @var3 == @var3

              @constraint 5:
              @var4 == 1
         */
        List<String> variables = Arrays.asList("var0", "var1", "var2", "var3", "var4", "var5", "var6", "var7");
        EList<CpVariable> vars = new BasicEList<>();
        //Domains:
        RangeDomainImpMockup dom1  = new RangeDomainImpMockup();
        RangeDomainImpMockup dom3  = new RangeDomainImpMockup();
        dom3.setType(BasicTypeEnum.INTEGER); dom1.setType(BasicTypeEnum.INTEGER);
        dom1.setFrom(1);dom3.setFrom(0);dom1.setTo(5);dom3.setTo(9);
        NumericListDomainImplMockup dom2 = new NumericListDomainImplMockup();
        dom2.setIntValues(Arrays.asList(5, 15, 25));
        dom2.setType(BasicTypeEnum.INTEGER);
        RangeDomainImpMockup dom0 = new RangeDomainImpMockup(); dom0.setFrom(0);dom0.setTo(1);
        dom0.setType(BasicTypeEnum.INTEGER);

        vars.add(new CpVariableImplMockup(variables.get(0), VariableType.PROVIDER, dom0, "Component1"));
        vars.add(new CpVariableImplMockup(variables.get(1), VariableType.CORES , dom1, "Component1"));
        vars.add(new CpVariableImplMockup(variables.get(2), VariableType.RAM , dom3, "Component1"));
        vars.add(new CpVariableImplMockup(variables.get(3), VariableType.STORAGE , dom2, "Component1"));
        vars.add(new CpVariableImplMockup(variables.get(4), VariableType.PROVIDER, dom0, "Component2"));
        vars.add(new CpVariableImplMockup(variables.get(5), VariableType.CORES , dom1, "Component2"));
        vars.add(new CpVariableImplMockup(variables.get(6), VariableType.RAM , dom3, "Component2"));
        vars.add(new CpVariableImplMockup(variables.get(7), VariableType.STORAGE , dom2, "Component2"));
        Constant c = new ConstantImplMockup(BasicTypeEnum.DOUBLE, new NumericValueUpperwareImplMockup(30));
        Constant c2 = new ConstantImplMockup(BasicTypeEnum.INTEGER, new NumericValueUpperwareImplMockup(0));
        Constant c3 = new ConstantImplMockup(BasicTypeEnum.DOUBLE, new NumericValueUpperwareImplMockup(0.1));

        EList<NumericExpression> exprs_ = new BasicEList<>();
        exprs_.add(vars.get(3)); exprs_.add(c3);
        NumericExpression storageDiv10 = new ComposedExpressionImplMockup(exprs_, OperatorEnum.TIMES);
        EList<NumericExpression> exprs = new BasicEList<>();
        exprs.add(vars.get(1)); exprs.add(vars.get(2));
        NumericExpression times = new ComposedExpressionImplMockup(exprs, OperatorEnum.TIMES);
        ComparisonExpressionMockup constraint1 = new ComparisonExpressionMockup();
        constraint1.setExp1(vars.get(1));constraint1.setExp2(storageDiv10);
        constraint1.setComparator(ComparatorEnum.LESS_THAN);

        ComparisonExpressionMockup constraint3 = new ComparisonExpressionMockup();
        constraint3.setExp1(times);constraint3.setExp2(storageDiv10);
        constraint3.setComparator(ComparatorEnum.GREATER_OR_EQUAL_TO);

        exprs.add(vars.get(2));
        ComparisonExpressionMockup constraint2 = new ComparisonExpressionMockup();
        times = new ComposedExpressionImplMockup(exprs, OperatorEnum.TIMES);
        constraint2.setExp1(times);constraint2.setExp2(c);
        constraint2.setComparator(ComparatorEnum.GREATER_OR_EQUAL_TO);

        ComparisonExpressionMockup constraint4 = new ComparisonExpressionMockup();
        constraint4.setExp1(vars.get(3));constraint4.setExp2(vars.get(3));
        constraint4.setComparator(ComparatorEnum.EQUAL_TO);

        ComparisonExpressionMockup constraint5 = new ComparisonExpressionMockup();
        constraint5.setExp1(vars.get(4));constraint5.setExp2(c2);
        constraint5.setComparator(ComparatorEnum.EQUAL_TO);

        EList<Constant> consts = new BasicEList<>();
        consts.add(c); consts.add(c2);

        EList<CpVariable> varsE = new BasicEList<>();
        for (int i = 0; i < 8; i++) varsE.add(vars.get(i));

        EList<ComparisonExpression> constraints = new BasicEList<>();
        constraints.addAll(Arrays.asList(constraint1, constraint2, constraint3, constraint4));

        ConstraintProblem cp = new ConstraintProblemMockup(consts,null, varsE, constraints );
        return Collections.singletonMap(cp, result -> {
            double sum = 0;
            int i = 0;
            for (VariableValueDTO v : result) {
                i++;
                if (v.getValue() instanceof Double) {
                    sum += v.getValue().doubleValue();
                } else {
                    sum += v.getValue().intValue();
                }
            }
            return sum;
        });
    }

    private static NodeCandidates getNodesForTwoComponentSimpleProblem() {
        Map<String, Map<Integer, List<NodeCandidate>>> candidates = new HashMap<>();
        Location loc = new Location(); GeoLocation gl = new GeoLocation(); gl.setLatitude(100.0);gl.setLongitude(100.0);
        loc.setGeoLocation(gl);
        List<Integer> ar1 = Arrays.asList(1,2,3,4,5, 5,5,5,5,2);
        List<Integer> ar3 = Arrays.asList(5, 15, 25, 25, 25, 25, 25, 25, 25, 25);
        List<Integer> ar2 = Arrays.asList(0,1,2,3,4,5,6,7,8,9);
        List<String> names = Arrays.asList("q", "w", "e", "r", "t", "y", "u", "i", "o", "p");
        for (int i = 0; i < ar3.size(); i++) {
            Hardware hd = new Hardware();
            hd.setCores(ar1.get(i));
            hd.setRam((long) ar2.get(i));
            hd.setDisk((double) ar3.get(i));
            NodeCandidate nc = new NodeCandidate();
            nc.setLocation(loc);
            nc.setHardware(hd);
            Map<Integer, List<NodeCandidate>> t = new HashMap<>();
            t.put(0, new ArrayList<>());
            t.get(0).add(nc);
            candidates.put(names.get(i), t);
        }

        List<String> names2 = Arrays.asList("q2", "w2", "e2", "r2", "t2", "y2", "u2", "i2", "o2", "p2");
        for (int i = 0; i < 4; i++) {
            Hardware hd = new Hardware();
            hd.setCores(ar1.get(i));
            hd.setRam((long) ar2.get(i));
            hd.setDisk( (double) ar3.get(i));
            NodeCandidate nc = new NodeCandidate();
            nc.setLocation(loc);
            nc.setHardware(hd);
            Map<Integer, List<NodeCandidate>> t = new HashMap<>();
            t.put(1, new ArrayList<>());
            t.get(1).add(nc);
            candidates.put(names2.get(i), t);
        }

        return NodeCandidates.of(candidates);
    }

    @Test
    public void simpleTwoComponentTest() {
        Map<ConstraintProblem, UtilityProvider> data = prepareSimpleTwoComponentConstraintProblem();
        NodeCandidates nc = getNodesForTwoComponentSimpleProblem();
        NCSolver ncSolver = new NCSolver(1, 10, 10, data.keySet().iterator().next(),
                data.values().iterator().next(), nc);
        PTSolution solution = ncSolver.solve(new MaxRuntime(10 ,TimeUnit.SECONDS));

        assertTrue(solution.extractVMConfiguration(0).equals(new VMConfiguration(2,9,25)));
        assertTrue(solution.extractVMConfiguration(1).equals(new VMConfiguration(4,3,25)));
    }

}
