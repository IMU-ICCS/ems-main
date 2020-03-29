
import cp_wrapper.CPWrapper;
import cp_wrapper.utility_provider.UtilityProvider;
import eu.melodic.upperware.mcts_solver.solver.Methods;
import eu.melodic.upperware.mcts_solver.solver.mcts.MCTSSolver;
import eu.melodic.upperware.mcts_solver.solver.mcts.cp_wrapper.MCTSWrapper;
import eu.paasage.upperware.metamodel.cp.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class MCTSSolverTest {

    @Test
    public void SimpleCPTest() {
        Map<ConstraintProblem, UtilityProvider> problem = Methods.prepareSimpleConstraintProblem();
        CPWrapper cpWrapper = new CPWrapper();
        cpWrapper.parse(problem.keySet().iterator().next(), problem.values().iterator().next());

        MCTSSolver mctsSolver = new MCTSSolver(0.1, 0.5, 150, new MCTSWrapper(cpWrapper));
        List<Integer> assignment = mctsSolver.search().getAssignment();

        List<Double> domain1 = Arrays.asList(1.0,2.0,3.0,4.0,5.0);
        List<Double> domain2 = Arrays.asList(0.5, 1.5, 2.5);
        List<Double> domain3 = Arrays.asList(0.0, 1.0,2.0,3.0,4.0,5.0, 6.0, 7.0, 8.0, 9.0);
        System.out.println("values: " + domain1.get(assignment.get(1))  +  domain2.get(assignment.get(2))+ " "+ domain3.get(assignment.get(0)));
        assertEquals(domain1.get(assignment.get(1)), 5.0, 0.01);
        assertEquals(domain2.get(assignment.get(2)), 2.5, 0.01);
        assertEquals(domain3.get(assignment.get(0)), 9, 0.01);
    }

    @Test
    public void SimpleCPTest2() {
        Map<ConstraintProblem, UtilityProvider> problem = Methods.prepareLessSimpleConstraintProblem();
        CPWrapper cpWrapper = new CPWrapper();
        cpWrapper.parse(problem.keySet().iterator().next(), problem.values().iterator().next());

        MCTSSolver mctsSolver = new MCTSSolver(0.1, 0.8, 500, new MCTSWrapper(cpWrapper));
        List<Integer> assignment = mctsSolver.search().getAssignment();

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

    @Test
    public void multiple() {
        Map<ConstraintProblem, UtilityProvider> problem = Methods.prepareLessSimpleConstraintProblem();
        CPWrapper cpWrapper = new CPWrapper();
        cpWrapper.parse(problem.keySet().iterator().next(), problem.values().iterator().next());
        int passed = 0, total = 0;

        for (int i = 0; i < 1000; i++) {
            MCTSSolver mctsSolver = new MCTSSolver(0.1, 0.8, 5000, new MCTSWrapper(cpWrapper));
            List<Integer> assignment = mctsSolver.search().getAssignment();

            List<Double> domain1 = Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0);
            List<Double> domain2 = Arrays.asList(0.5, 1.5, 2.5, 7.5, 10.0);
            List<Double> domain3 = Arrays.asList(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0);
            List<Double> domain4 = Arrays.asList(1.0, 2.0, 3.0);
            List<Double> domain5 = Arrays.asList(0.5, 7.5, 12.5);

            //assertEquals(assignment.get(0), (Integer) 3);
            //assertEquals(assignment.get(1), (Integer) 9);
            //assertEquals(assignment.get(2), (Integer) 4);
            //assertEquals(assignment.get(3), (Integer) 2);
            //assertEquals(assignment.get(4), (Integer) 2);
            if (assignment.get(0) == 3 && assignment.get(1) == 9 && assignment.get(2) == 4 && assignment.get(3) == 2 && assignment.get(4) == 2)
                passed++;

            total++;
        }
        System.out.println("passed: " + passed + " / total: " + total);
    }
}
