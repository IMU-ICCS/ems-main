package eu.melodic.upperware.mcts_solver.solver.tree_structure;

import eu.melodic.upperware.mcts_solver.solver.mcts_cp_wrapper.MCTSWrapper;
import eu.melodic.upperware.mcts_solver.solver.selector_strategies.HeuristicNodeSelector;
import eu.melodic.upperware.mcts_solver.solver.selector_strategies.NodeSelector;
import eu.melodic.upperware.mcts_solver.solver.selector_strategies.UtilityNodeSelector;
import eu.melodic.upperware.mcts_solver.solver.utility.PartialAssignment;
import eu.melodic.upperware.mcts_solver.solver.playout_policies.Policy;
import eu.melodic.upperware.mcts_solver.solver.utility.Solution;
import org.javatuples.Pair;

public class Tree {
    private Node root;
    private Solution solution;
    private Policy policy;
    private NodeSelector heuristicSelector;
    private NodeSelector utilitySelector;
    private double selectorCoefficient;
    private MCTSWrapper mctsWrapper;

    public Tree(Policy policy, HeuristicNodeSelector heuristicSelector, UtilityNodeSelector utilitySelector) {
        root = new Node(mctsWrapper);
        solution = new Solution();
        this.policy = policy;
        this.heuristicSelector = heuristicSelector;
        this.utilitySelector = utilitySelector;
    }

    private void backpropagate(Node current, Solution solution) {
        int maximalFitSize = solution.getMaximalFitSize();
        double utility = solution.getUtility();

        while (current != null) {
            current.update(maximalFitSize, utility);

            current = current.getParent();
        }
    }

    private Solution rollout(PartialAssignment partialAssignment) {
        return policy.finishAssignment(partialAssignment);
    }

    /*
    public Node expand(Node node) {
        //TODO
        return null;
    }*/

    //TODO either expand nodes in expand method or do it while searching tree with search().

    public Pair<Node, PartialAssignment> searchAndExpand() {
        Node current = root;
        PartialAssignment partialAssignment = new PartialAssignment(mctsWrapper);

        while (current.hasAllChildren()) {
            if (mctsWrapper.getRandomly(selectorCoefficient)) {
                current = heuristicSelector.select(current.getChildren());
            }
            else {
                current = heuristicSelector.select(current.getChildren());
            }
            partialAssignment.add(current.getNodeStatistics().getValue());
        }

        return new Pair<>(current, partialAssignment);
    }

    public void runIteration() {
        Pair<Node, PartialAssignment> state = searchAndExpand();
        Node leaf = state.getValue0();
        PartialAssignment partialAssignment = state.getValue1();
        Solution solution = rollout(partialAssignment);
        backpropagate(leaf, solution);
    }
}
