package eu.melodic.upperware.mcts_solver.solver.mcts.tree;

import eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.BranchTrimmerImpl;
import org.javatuples.Pair;

import java.util.stream.IntStream;

import static java.util.Collections.max;

public abstract class Tree {
    protected Node root;
    private Policy policy;
    private MoveProvider moveProvider; // MoveProvider is responsible for both tree search and expansion.
    private BranchTrimmer branchTrimmer; // Responsible for reducing tree size from time to time.

    public Tree(Policy policy, MoveProvider moveProvider, BranchTrimmer branchTrimmer) {
        this.policy = policy;
        this.moveProvider = moveProvider;
        this.branchTrimmer = branchTrimmer;
    }

    public Solution run(int iterations) {
        return IntStream.range(0, iterations)
                .mapToObj(i ->runIteration())
                .max(Solution::compareTo).get();
    }

    // Back propagates calculated solution on path from leaf to root.
    private Node backPropagate(Node startingNode, Solution solution) {
        Node current = startingNode;

        while (current != null) {
            current.update(solution);
            branchTrimmer.trimNode(current);
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
}
