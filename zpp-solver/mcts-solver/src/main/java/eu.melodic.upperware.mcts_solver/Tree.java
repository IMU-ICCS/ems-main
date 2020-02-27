package eu.melodic.upperware.mcts_solver;

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

    public void backpropagation(Node current, Solution solution) {
        int maximalFitSize = solution.getMaximalFitSize();
        double utility = solution.getUtility();

        while (current != null) {
            current.update(maximalFitSize, utility);

            current = current.getParent();
        }
    }

    public Solution rollout(Node node, PartialAssignment partialAssignment) {
        return policy.finishAssignment(partialAssignment);
    }

    public Pair<Node, PartialAssignment> search(){
        //TODO
        return null;
    }


}
