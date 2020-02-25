package eu.melodic.upperware.genetic_solver.runner;

import cp_wrapper.CPWrapper;
import cp_wrapper.utility_provider.UtilityProvider;
import eu.melodic.upperware.genetic_solver.cp_genetic_wrapper.CPGeneticWrapper;
import eu.paasage.upperware.metamodel.cp.*;
import org.junit.Test;
import eu.melodic.upperware.genetic_solver.utility.Methods;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class GeneticSolverRunnerTest {

    @Test
    public void SimpleTestGeneratedByTomek() {
        Map<ConstraintProblem, UtilityProvider> problem = Methods.prepareSimpleConstraintProblem();
        CPWrapper cpWrapper = new CPWrapper();
        cpWrapper.parse(problem.keySet().iterator().next(), problem.values().iterator().next());

        GeneticSolverRunner geneticSolverRunner = new GeneticSolverRunner();
        geneticSolverRunner.setMutatorProbability(0.15);
        geneticSolverRunner.setPopulationSize(100);
        geneticSolverRunner.setIterations(100);
        geneticSolverRunner.setComparatorProbability(0.1);

        List<Integer> assignment = geneticSolverRunner.run(new CPGeneticWrapper(cpWrapper));


        List<Double> domain1 = Arrays.asList(1.0,2.0,3.0,4.0,5.0);
        List<Double> domain2 = Arrays.asList(0.5, 1.5, 2.5);
        List<Double> domain3 = Arrays.asList(0.0, 1.0,2.0,3.0,4.0,5.0, 6.0, 7.0, 8.0, 9.0);
        System.out.println("values: " + domain1.get(assignment.get(1))  +  domain2.get(assignment.get(2))+ " "+ domain3.get(assignment.get(0)));
        assertEquals( domain1.get(assignment.get(1)), 5.0, 0.01);
        assertEquals( domain2.get(assignment.get(2)), 2.5, 0.01);
        assertEquals( domain3.get(assignment.get(0)), 9, 0.01);
    }

    /* Its a fairly easy test aswell and 20s should be enough. */
    @Test
    public void LessSimpleTest() {
        Map<ConstraintProblem, UtilityProvider> problem = Methods.prepareLessSimpleConstraintProblem();
        CPWrapper cpWrapper = new CPWrapper();
        cpWrapper.parse(problem.keySet().iterator().next(), problem.values().iterator().next());

        GeneticSolverRunner geneticSolverRunner = new GeneticSolverRunner();
        geneticSolverRunner.setMutatorProbability(0.1);
        geneticSolverRunner.setPopulationSize(50);
        geneticSolverRunner.setTimeLimitSeconds(20);
        geneticSolverRunner.setComparatorProbability(0.05);

        List<Integer> assignment = geneticSolverRunner.run(new CPGeneticWrapper(cpWrapper));


        List<Double> domain1 = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0);
        List<Double> domain2 = Arrays.asList(0.5, 1.5, 2.5, 7.5, 10.0);
        List<Double> domain3 = Arrays.asList(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0);
        List<Double> domain4 = Arrays.asList(1.0, 2.0, 3.0);
        List<Double> domain5 = Arrays.asList(0.5, 7.5, 12.5);

        assertEquals(assignment.get(0), (Integer) 3);
        assertEquals(assignment.get(1), (Integer) 9);
        assertEquals(assignment.get(2), (Integer) 4);
        assertEquals(assignment.get(3), (Integer) 2);
        assertEquals(assignment.get(4), (Integer) 2);

        System.out.print("values ");
        System.out.print(domain1.get(assignment.get(0)) + " " + domain3.get(assignment.get(1)) + " " +
                domain2.get(assignment.get(2)) + " " + domain4.get(assignment.get(3)) + " " + domain5.get(assignment.get(4)) + " ");


    }
}
