package eu.melodic.upperware.mcts_solver.solver;

import cp_wrapper.CPWrapper;
import cp_wrapper.utility_provider.UtilityProvider;
import eu.melodic.upperware.mcts_solver.solver.mcts.cp_wrapper.MCTSWrapper;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Map;

import static org.junit.Assert.assertEquals;

class MCTSCoordinatorTest {
        private final int NUM_THREADS = 5;
        @Test
        public void simpleCPTest() throws InterruptedException {
            Map<String, Double> realBestSolution = Map.of(
                    "var1", 5.0,
                    "var2", 2.5,
                    "var3", 9.0
            );

            List<MCTSWrapper> mctsWrappers = IntStream.range(0, NUM_THREADS).mapToObj(thread -> {
            Map<ConstraintProblem, UtilityProvider> problem = Methods.prepareSimpleConstraintProblem();
            CPWrapper cpWrapper = new CPWrapper();
            cpWrapper.parse(problem.keySet().iterator().next(), problem.values().iterator().next());
            return new MCTSWrapper(cpWrapper);
            }).collect(Collectors.toList());

            MCTSCoordinator mctsCoordinator = new MCTSCoordinator(NUM_THREADS, 0.001, 0.9, 100);
            Pair<List<VariableValueDTO>, Double> solution  = mctsCoordinator.solve(10, mctsWrappers);

            solution.getValue0().forEach(variable -> {
                assertEquals(java.util.Optional.of(variable.getValue().doubleValue()).orElse(0.0), realBestSolution.get(variable.getName()));
            });
        }

    @Test
    public void simpleCPTest2() throws InterruptedException {

        Map<String, Double> realBestSolution = Map.of(
                "var1", 4.0,
                "var2", 10.0,
                "var3", 9.0,
                "var4", 3.0,
                "var5", 12.5
        );

        List<MCTSWrapper> mctsWrappers = IntStream.range(0, NUM_THREADS).mapToObj(thread -> {
            Map<ConstraintProblem, UtilityProvider> problem = Methods.prepareLessSimpleConstraintProblem();
            CPWrapper cpWrapper = new CPWrapper();
            cpWrapper.parse(problem.keySet().iterator().next(), problem.values().iterator().next());
            return new MCTSWrapper(cpWrapper);
        }).collect(Collectors.toList());

        MCTSCoordinator mctsCoordinator = new MCTSCoordinator(NUM_THREADS, 0.001, 0.9, 100);
        Pair<List<VariableValueDTO>, Double> solution  = mctsCoordinator.solve(10, mctsWrappers);

        solution.getValue0().forEach(variable -> {
            assertEquals(java.util.Optional.of(variable.getValue().doubleValue()).orElse(0.0), realBestSolution.get(variable.getName()));
        });
    }
}