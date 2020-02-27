package eu.melodic.upperware.mcts_solver.solver.tree_structure;

import eu.melodic.upperware.mcts_solver.utility.PartialAssignment;
import eu.melodic.upperware.mcts_solver.solver.policy.Policy;
import eu.melodic.upperware.mcts_solver.utility.Solution;
import org.javatuples.Pair;

public class Tree {
    protected Node root;
    protected Solution solution;
    protected Policy policy;

    public Tree(Policy policy) {
        root = new Node();
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

    private Pair<Node, PartialAssignment> search(){
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
