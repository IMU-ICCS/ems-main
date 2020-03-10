package eu.melodic.upperware.mcts_solver.solver.mcts_tree;

import org.javatuples.Pair;

public class Tree {
    private Node root;
    private Policy policy;
    private MoveProvider moveProvider; // MoveProvider is responsible for both tree search and expansion.
    private Solution bestSolution;

    public Tree(Node root, Policy policy, MoveProvider moveProvider, Solution bestSolution) {
        this.root = root;
        this.policy = policy;
        this.moveProvider = moveProvider;
        this.bestSolution = bestSolution;
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
