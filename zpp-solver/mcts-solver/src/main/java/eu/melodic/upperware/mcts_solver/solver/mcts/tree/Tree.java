package eu.melodic.upperware.mcts_solver.solver.mcts.tree;

import eu.melodic.upperware.mcts_solver.solver.mcts.tree.memory_management.MemoryLimiter;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.stream.IntStream;

public abstract class Tree {
    protected Node root;
    private Policy policy;
    private MoveProvider moveProvider; // MoveProvider is responsible for both tree search and expansion.
    private MemoryLimiter memoryLimiter; // Responsible for prevention of memory overflow by limiting tree size.
    private BranchTrimmer branchTrimmer;

    public Tree(Policy policy, MoveProvider moveProvider, MemoryLimiter memoryLimiter, BranchTrimmer branchTrimmer) {
        this.policy = policy;
        this.moveProvider = moveProvider;
        this.memoryLimiter = memoryLimiter;
        this.branchTrimmer = branchTrimmer;
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

    private Pair<Node, Path> searchAndExpand() {
        return moveProvider.searchAndExpand(root);
    }

    private Solution runIteration() {
        Pair<Node, Path> state = searchAndExpand();
        Node leaf = state.getValue0();
        Path path = state.getValue1();

        Solution solution = rollout(path);
        backPropagate(leaf, solution);

        branchTrimmer.trimNodesInPath(leaf);

        memoryLimiter.updateRecentlyAccessedNodes(leaf);
        while (memoryLimiter.shouldPruneTree()) {
            pruneSubTree(memoryLimiter.whichNodeToPrune());
        }

        System.out.println("Tree size is " + countTreeSize(root));

        return solution;
    }

    // Prunes subtree completely. SubRoot's children should be all leaves.
    private void pruneSubTree(Node subRoot) {
        if (subRoot.getParent() == null) { // If is a root then do nothing.
            return;
        }
        memoryLimiter.decreaseCount(subRoot.getChildren().size());
        subRoot.getChildren().clear();
        subRoot.setUnexpanded();
    }

    private int countTreeSize(Node current) {
        int sum = 0;

        for (Node child : current.getChildren()) {
            sum += countTreeSize(child);
        }
        return sum + 1;
    }
}
