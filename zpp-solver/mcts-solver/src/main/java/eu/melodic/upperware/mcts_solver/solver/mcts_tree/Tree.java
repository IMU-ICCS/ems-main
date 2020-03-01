package eu.melodic.upperware.mcts_solver.solver.mcts_tree;

import eu.melodic.upperware.mcts_solver.solver.mcts_tree_impl.SolutionCP;
import org.javatuples.Pair;

public class Tree {
    private Node root;
    private Policy policy;
    private MoveProvider moveProvider; // MoveProvider is responsible for both tree search and expansion.
    private SolutionCP bestSolution;

    public Tree(Node root, Policy policy, MoveProvider moveProvider) {
        this.root = root;
        this.policy = policy;
        this.moveProvider = moveProvider;
    }

    private void backpropagate(Node current, SolutionCP solution) {
        while (current != null) {
            current.update(solution);
            current = current.getParent();
        }
    }

    private SolutionCP rollout(Path path) {
        return policy.finishPath(path);
    }

    public Pair<Node, Path> searchAndExpand() {
        return moveProvider.searchAndExpand(root);
    }

    public void runIteration() {
        Pair<Node, Path> state = searchAndExpand();
        Node leaf = state.getValue0();
        Path path = state.getValue1();
        SolutionCP solution = rollout(path);
        backpropagate(leaf, solution);
    }

    public SolutionCP run(int iterations) {
        for (int i = 0; i < iterations; i++) {
            runIteration();
        }

        return bestSolution;
    }
}
