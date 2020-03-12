package eu.melodic.upperware.mcts_solver.solver;

import cp_wrapper.CPWrapper;
import cp_wrapper.utility_provider.UtilityProvider;
import eu.melodic.upperware.mcts_solver.solver.mcts_cp_wrapper.MCTSWrapper;
import eu.melodic.upperware.mcts_solver.solver.mcts_tree.*;
import eu.melodic.upperware.mcts_solver.solver.mcts_tree_impl.*;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
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

    public void run(MCTSWrapper mctsWrapper) {
        MoveProvider moveProvider = new MoveProviderImpl(mctsWrapper);
        Policy policy = new RandomPolicyImpl(mctsWrapper);

        Node root = new NodeImpl(-1);
        root.linkToTree(null);

        NodeStatisticsImpl.setExplorationCoefficient(explorationCoefficient);
        NodeStatisticsImpl.setSelectorCoefficient(selectorCoeffcient);
        NodeStatisticsImpl.setMaximalSize(mctsWrapper.getSize());

        Solution initialSolution = new SolutionImpl();

        Tree mctsTree = new Tree(root, policy, moveProvider, initialSolution);

        solution = (SolutionImpl) mctsTree.run(iterations);

        log.debug("Found solution with utility: " + solution.getUtility());
    }
}
