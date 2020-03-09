package eu.melodic.upperware.mcts_solver.solver;

import cp_wrapper.CPWrapper;
import cp_wrapper.utility_provider.UtilityProvider;
import eu.melodic.upperware.mcts_solver.solver.mcts_cp_wrapper.MCTSWrapper;
import eu.melodic.upperware.mcts_solver.solver.mcts_tree.MoveProvider;
import eu.melodic.upperware.mcts_solver.solver.mcts_tree.Node;
import eu.melodic.upperware.mcts_solver.solver.mcts_tree.Policy;
import eu.melodic.upperware.mcts_solver.solver.mcts_tree.Tree;
import eu.melodic.upperware.mcts_solver.solver.mcts_tree_impl.MoveProviderImpl;
import eu.melodic.upperware.mcts_solver.solver.mcts_tree_impl.NodeImpl;
import eu.melodic.upperware.mcts_solver.solver.mcts_tree_impl.RandomPolicyImpl;
import eu.melodic.upperware.mcts_solver.solver.mcts_tree_impl.SolutionImpl;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class MCTSSolver {
    @Setter
    private double selectorCoeffcient;
    @Setter
    private double explorationCoefficient;
    @Getter
    private SolutionImpl solution;
    @Setter
    private int iterations;

    public List<VariableValueDTO> run(ConstraintProblem cp, UtilityProvider utility) {
        CPWrapper cpWrapper = new CPWrapper();
        cpWrapper.parse(cp, utility);
        MCTSWrapper mctsWrapper = new MCTSWrapper(cpWrapper);

        run(mctsWrapper);
        return cpWrapper.assignmentToVariableValueDTOList(solution.getAssignment());
    }

    private void run(MCTSWrapper mctsWrapper) {
        MoveProvider moveProvider = new MoveProviderImpl(selectorCoeffcient, explorationCoefficient, mctsWrapper);
        Policy policy = new RandomPolicyImpl();
        Node root = new NodeImpl(-1);
        Tree mctsTree = new Tree(root, policy, moveProvider);
        solution = (SolutionImpl) mctsTree.run(iterations);
    }
}
