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

    private void backpropagate(Node current, Solution solution) {
        while (current != null) {
            current.update(solution);
            current = current.getParent();
        }
    }

    private Solution rollout(Path path) {
        return policy.finishPath(path);
    }

    private Pair<Node, Path> searchAndExpand() {
        return moveProvider.searchAndExpand(root);
    }

    private void runIteration() {
        Pair<Node, Path> state = searchAndExpand();
        Node leaf = state.getValue0();
        Path path = state.getValue1();
        Solution solution = rollout(path);
        backpropagate(leaf, solution);
        if (solution.isBetterThan(bestSolution)) {
            bestSolution = solution;
        }
    }

    public Solution run(int iterations) {
        for (int i = 0; i < iterations; i++) {
            runIteration();
        }

        return bestSolution;
    }
}
