package eu.melodic.upperware.mcts_solver.solver.mcts.tree;

import eu.melodic.upperware.mcts_solver.solver.mcts.tree.memory_management.MemoryLimiter;
import org.javatuples.Triplet;

import java.util.stream.IntStream;

public abstract class Tree {
    protected Node root;
    private Policy policy;
    private MoveProvider moveProvider; // MoveProvider is responsible for both tree search and expansion.
    private MemoryLimiter memoryLimiter; // Responsible for prevention of memory overflow by limiting tree size.

    public Tree(Policy policy, MoveProvider moveProvider, MemoryLimiter memoryLimiter) {
        this.policy = policy;
        this.moveProvider = moveProvider;
        this.memoryLimiter = memoryLimiter;
    }

    public Solution run(int iterations) {
        Solution solution = IntStream.range(0, iterations)
                .mapToObj(i ->runIteration())
                .max(Solution::compareTo).get();
        return solution;
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

    private Triplet<Node, Integer, Path> searchAndExpand() {
        return moveProvider.searchAndExpand(root);
    }

    private Solution runIteration() {
        Triplet<Node, Integer, Path> state = searchAndExpand();
        Node leaf = state.getValue0();
        int numberOfAddedNodes = state.getValue1();
        Path path = state.getValue2();

        Solution solution = rollout(path);
        backPropagate(leaf, solution);
        memoryLimiter.updateRecentlyAccessedNodes(leaf, numberOfAddedNodes);
        memoryLimiter.prune();

        return solution;
    }
}
