package eu.melodic.upperware.mcts_solver.solver.mcts;

import cp_wrapper.solution.CpSolution;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.mcts_solver.solver.mcts.cp_wrapper.MCTSWrapper;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.*;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.*;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.policy.CheapestPolicyImpl;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.policy.RandomPolicyImpl;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MCTSSolver {
    @Setter
    private double selectorCoefficient;
    private double explorationCoefficient;
    private int iterations;
    private MCTSWrapper mctsWrapper;
    //TODO ndoeCandidates hsoudl pribably belogn to the wrapper
    private NodeCandidates nodeCandidates;


    public MCTSSolver(NodeCandidates nodeCandidates, double selectorCoefficient, double explorationCoefficient, int iterations, MCTSWrapper mctsWrapper) {
        this.selectorCoefficient = selectorCoefficient;
        this.explorationCoefficient = explorationCoefficient;
        this.iterations = iterations;
        this.mctsWrapper = mctsWrapper;
        this.nodeCandidates = nodeCandidates;
    }

    public CpSolution solve() {
        Solution solution = search();
        return new CpSolution(mctsWrapper.assignmentToVariableValueDTOList(solution.getAssignment()), solution.getUtility());
    }

    public Solution search() {
        MoveProvider moveProvider = new MoveProviderImpl(mctsWrapper);
        Policy policy = new CheapestPolicyImpl(mctsWrapper, nodeCandidates);//new RandomPolicyImpl(mctsWrapper);

        NodeStatisticsImpl.setExplorationCoefficient(explorationCoefficient);
        NodeStatisticsImpl.setSelectorCoefficient(selectorCoefficient);
        NodeStatisticsImpl.setMaximalDepth(mctsWrapper.getSize());

        Tree mctsTree = new TreeImpl(policy, moveProvider);

        Solution solution = mctsTree.run(iterations);

        log.info("Found solution with utility: {}. Values: {}.", solution.getUtility(), solution.getAssignment().toString());

        return solution;
    }
}
