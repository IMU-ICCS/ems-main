package eu.melodic.upperware.mcts_solver.solver.tree_structure;

import eu.melodic.upperware.mcts_solver.solver.mcts_cp_wrapper.MCTSWrapper;
import eu.melodic.upperware.mcts_solver.solver.utility.PartialAssignment;
import eu.melodic.upperware.mcts_solver.solver.policy.Policy;
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

    public Tree(Policy policy) {
        root = new Node(mctsWrapper);
        solution = new Solution();
        this.policy = policy;
    }

    private void backpropagate(Node current, Solution solution) {
        int maximalFitSize = solution.getMaximalFitSize();
        double utility = solution.getUtility();

        while (current != null) {
            current.update(maximalFitSize, utility);

            current = current.getParent();
        }
    }

    private Solution rollout(Node node, PartialAssignment partialAssignment) {
        return policy.finishAssignment(partialAssignment);
    }

    /* Expansion needs to be done here.
       n - number of potentail children number
       m - number of current children

       lambda * (log(n - m) - 1)


    */
    public Pair<Node, PartialAssignment> search() {
        Node current = root;
        PartialAssignment partialAssignment = new PartialAssignment(mctsWrapper);

        while (current.hasChildren()) {
            if (mctsWrapper.getRandomly(selectorCoefficient)) {
                current = heuristicSelector.select(current.getChildren());
            }
            else {
                current = heuristicSelector.select(current.getChildren());
            }
            partialAssignment.add(current.getNodeStatistics().getValue());
        }
        //TODO
        return null;
    }

    public void runIteration() {
        Pair<Node, PartialAssignment> state = search();
        Node leaf = state.getValue0();
        PartialAssignment partialAssignment = state.getValue1();
        Solution solution = rollout(leaf, partialAssignment);
        backpropagate(leaf, solution);
    }



}
