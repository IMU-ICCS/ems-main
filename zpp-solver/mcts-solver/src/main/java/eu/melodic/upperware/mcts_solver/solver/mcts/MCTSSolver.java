package eu.melodic.upperware.mcts_solver.solver.mcts;

import cp_wrapper.solution.CpSolution;
import eu.melodic.upperware.mcts_solver.solver.mcts.cp_wrapper.MCTSWrapper;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.*;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.*;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;

import java.util.List;

@Slf4j
public class MCTSSolver {
    @Setter
    private double selectorCoefficient;
    private double explorationCoefficient;
    private int iterations;
    private MCTSWrapper mctsWrapper;

    public MCTSSolver(double selectorCoefficient, double explorationCoefficient, int iterations, MCTSWrapper mctsWrapper) {
        this.selectorCoefficient = selectorCoefficient;
        this.explorationCoefficient = explorationCoefficient;
        this.iterations = iterations;
        this.mctsWrapper = mctsWrapper;
    }

    public CpSolution solve() {
        Solution solution = search();
        return new CpSolution(mctsWrapper.assignmentToVariableValueDTOList(solution.getAssignment()), solution.getUtility());
    }

    public Solution search() {
        MoveProvider moveProvider = new MoveProviderImpl(mctsWrapper);
        Policy policy = new RandomPolicyImpl(mctsWrapper);

        NodeStatisticsImpl.setExplorationCoefficient(explorationCoefficient);
        NodeStatisticsImpl.setSelectorCoefficient(selectorCoefficient);
        NodeStatisticsImpl.setMaximalDepth(mctsWrapper.getSize());

        Tree mctsTree = new TreeImpl(policy, moveProvider);

        Solution solution = mctsTree.run(iterations);

        log.info("Found solution with utility: {}. Values: {}.", solution.getUtility(), solution.getAssignment().toString());

        return solution;
    }
}
