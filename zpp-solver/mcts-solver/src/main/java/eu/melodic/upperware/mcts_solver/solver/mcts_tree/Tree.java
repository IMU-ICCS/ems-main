package eu.melodic.upperware.mcts_solver.solver.mcts_tree;

import org.javatuples.Pair;

public abstract class Tree {
    protected Node root;
    private Policy policy;
    private MoveProvider moveProvider; // MoveProvider is responsible for both tree search and expansion.
    protected Solution bestSolution;

    public Tree(Policy policy, MoveProvider moveProvider) {
        this.policy = policy;
        this.moveProvider = moveProvider;
    }

    // Back propagates calculated solution on path from leaf to root.
    private Node backPropagate(Node startingNode, Solution solution) {
        Node current = startingNode;

        while (current != null) {
            current.update(solution);
            current = current.getParent();
        }

        return root;
    }

    private Solution rollout(Path path) {
        return policy.finishPath(path);
    }

    private Pair<Node, Path> searchAndExpand() {
        return moveProvider.searchAndExpand(root);
    }

    private Solution runIteration() {
        Pair<Node, Path> state = searchAndExpand();
        Node leaf = state.getValue0();
        Path path = state.getValue1();
        Solution solution = rollout(path);
        backPropagate(leaf, solution);
        return solution;
    }

    public Solution run(int iterations) {
        for (int i = 0; i < iterations; i++) {
            Solution solution = runIteration();
            if (solution.compareTo(bestSolution) > 0) {
                this.bestSolution = solution;
            }
        }

        return bestSolution;
    }
}
