package eu.melodic.upperware.mcts_solver;

import cp_wrapper.CPWrapper;
import cp_wrapper.solution.CpSolution;
import cp_wrapper.utility_provider.UtilityProvider;
import eu.melodic.upperware.mcts_solver.solver.MCTSSolver;
import eu.melodic.upperware.mcts_solver.solver.mcts.cp_wrapper.MCTSWrapper;
import eu.melodic.upperware.mcts_solver.solver.mcts.cp_wrapper.MCTSWrapperFactory;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.policy.AvailablePolicies;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Map;

import static org.junit.Assert.assertEquals;

class MCTSSolverTest {
        private final int NUM_THREADS = 5;
        @Test
        public void simpleCPTest() throws InterruptedException {
            Map<String, Double> realBestSolution = new HashMap<String, Double>() {{
                    put("var1", 5.0);
                    put("var2", 2.5);
                    put("var3", 9.0);
                }};

            List<MCTSWrapper> mctsWrappers = IntStream.range(0, NUM_THREADS).mapToObj(thread -> {
            Map<ConstraintProblem, UtilityProvider> problem = Methods.prepareSimpleConstraintProblem();
            CPWrapper cpWrapper = new CPWrapper();
            cpWrapper.parse(problem.keySet().iterator().next(), problem.values().iterator().next());
            return new MCTSWrapper(cpWrapper, null);
            }).collect(Collectors.toList());

            MCTSCoordinator mctsCoordinator = new MCTSCoordinator(NUM_THREADS, 0.001, 0.9, 100, AvailablePolicies.RANDOM_POLICY, false);
            CpSolution solution  = mctsCoordinator.solve(10, new MCTSWrapperFactory() {
                private int index = -1;
                @Override
                public MCTSWrapper create() {
                    index++;
                    return mctsWrappers.get(index);
                }
            });

            solution.getSolution().forEach(variable -> {
                assertEquals(java.util.Optional.of(variable.getValue().doubleValue()).orElse(0.0), realBestSolution.get(variable.getName()));
            });
        }

    @Test
    public void simpleCPTest2() throws InterruptedException {
        Map<String, Double> realBestSolution = new HashMap<String, Double>() {{
                put("var1", 4.0);
                put("var2", 10.0);
                put("var3", 9.0);
                put("var4", 3.0);
                put("var5", 12.5);
            }};

        List<MCTSWrapper> mctsWrappers = IntStream.range(0, NUM_THREADS).mapToObj(thread -> {
            Map<ConstraintProblem, UtilityProvider> problem = Methods.prepareLessSimpleConstraintProblem();
            CPWrapper cpWrapper = new CPWrapper();
            cpWrapper.parse(problem.keySet().iterator().next(), problem.values().iterator().next());
            return new MCTSWrapper(cpWrapper, null);
        }).collect(Collectors.toList());

<<<<<<< HEAD:zpp-solver/mcts-solver/src/test/java/eu/melodic/upperware/mcts_solver/MCTSSolverTest.java
        MCTSSolver mctsSolver = new MCTSSolver(NUM_THREADS, 0.001, 0.9, 100, AvailablePolicies.RANDOM_POLICY);
        CpSolution solution  = mctsSolver.solve(10, new MCTSWrapperFactory() {
=======
        MCTSCoordinator mctsCoordinator = new MCTSCoordinator(NUM_THREADS, 0.001, 0.9, 100, AvailablePolicies.RANDOM_POLICY, false);
        CpSolution solution  = mctsCoordinator.solve(10, new MCTSWrapperFactory() {
>>>>>>> 8a9b065b6cdee2d97c0d8a8f726c607fd172ae35:zpp-solver/mcts-solver/src/test/java/eu/melodic/upperware/mcts_solver/solver/MCTSCoordinatorTest.java
            private int index = -1;
            @Override
            public MCTSWrapper create() {
                index++;
                return mctsWrappers.get(index);
            }
        });

        solution.getSolution().forEach(variable -> {
            assertEquals(java.util.Optional.of(variable.getValue().doubleValue()).orElse(0.0), realBestSolution.get(variable.getName()));
        });
    }
}