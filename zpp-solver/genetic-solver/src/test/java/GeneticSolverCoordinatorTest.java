import cp_wrapper.CPWrapper;
import cp_wrapper.UtilityProvider;
import eu.paasage.upperware.metamodel.cp.*;
import org.junit.Test;
import utility.Methods;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class GeneticSolverCoordinatorTest {

    @Test
    public void SimpleTestGeneratedByTomek() {
        Map<ConstraintProblem, UtilityProvider> problem = Methods.prepareSimpleConstraintProblem();
        CPWrapper cpWrapper = new CPWrapper();
        cpWrapper.parse(problem.keySet().iterator().next(), problem.values().iterator().next());

        GeneticSolverCoordinator geneticSolverCoordinator = new GeneticSolverCoordinator();
        geneticSolverCoordinator.setMutatorProbability(0.15);
        geneticSolverCoordinator.setPopulationSize(100);
        geneticSolverCoordinator.setIterations(100);
        geneticSolverCoordinator.setComparatorProbability(0.1);

        List<Integer> assignment = geneticSolverCoordinator.run(cpWrapper);


        List<Double> domain1 = Arrays.asList(1.0,2.0,3.0,4.0,5.0);
        List<Double> domain2 = Arrays.asList(0.5, 1.5, 2.5);
        List<Double> domain3 = Arrays.asList(0.0, 1.0,2.0,3.0,4.0,5.0, 6.0, 7.0, 8.0, 9.0);
        System.out.println("values: " + domain1.get(assignment.get(1))  +  domain2.get(assignment.get(2))+ " "+ domain3.get(assignment.get(0)));
        assertEquals((double) domain1.get(assignment.get(1)), 5.0, 0.01);
        assertEquals((double) domain2.get(assignment.get(2)), 2.5, 0.01);
        assertEquals((double) domain3.get(assignment.get(0)), 9, 0.01);
    }
/*
    @Test
    public void LessSimpleTest() {
        Map<ConstraintProblem, UtilityProvider> problem = Methods.prepareLessSimpleConstraintProblem();
        CPWrapper cpWrapper = new CPWrapper();
        cpWrapper.parse(problem.keySet().iterator().next(), problem.values().iterator().next());

        GeneticSolverCoordinator geneticSolverCoordinator = new GeneticSolverCoordinator();
        geneticSolverCoordinator.setMutatorProbability(0.3);
        geneticSolverCoordinator.setPopulationSize(100);
        geneticSolverCoordinator.setIterations(100);
        geneticSolverCoordinator.setComparatorProbability(0.05);

        List<Integer> assignment = geneticSolverCoordinator.run(cpWrapper);


        List<Double> domain1 = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0);
        List<Double> domain2 = Arrays.asList(0.5, 1.5, 2.5, 7.5, 10.0);
        List<Double> domain3 = Arrays.asList(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0);
        List<Double> domain4 = Arrays.asList(1.0, 2.0, 3.0);
        List<Double> domain5 = Arrays.asList(0.5, 7.5, 12.5);

        for (Integer number : assignment) {
            System.out.println(number);
        }

        /*
        System.out.println("values: " + domain1.get(assignment.get(1))  +  domain2.get(assignment.get(2))+ " "+ domain3.get(assignment.get(0)));
        assertEquals((double) domain1.get(assignment.get(1)), 5.0, 0.01);
        assertEquals((double) domain2.get(assignment.get(2)), 2.5, 0.01);
        assertEquals((double) domain3.get(assignment.get(0)), 9, 0.01);*/
    //}
}
