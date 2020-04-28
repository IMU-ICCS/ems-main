package eu.melodic.upperware.mcts_solver.solver.mcts.tree;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;

import java.util.stream.IntStream;

@Slf4j
public abstract class Tree {
    @Getter
    protected Node root;
    private Policy policy;
    private MoveProvider moveProvider; // MoveProvider is responsible for both tree search and expansion.
    private MemoryLimiter memoryLimiter; // Responsible for prevention of memory overflow by limiting tree size.
    private final int minDepthSubtreeRemoval;

    public Tree(Policy policy, MoveProvider moveProvider, MemoryLimiter memoryLimiter) {
        this.policy = policy;
        this.moveProvider = moveProvider;
        this.memoryLimiter = memoryLimiter;
        this.minDepthSubtreeRemoval = policy.minDepthSubtreeRemoval();
    }

    public Solution run(int iterations) {
        return IntStream.range(0, iterations)
                .mapToObj(i -> runIteration())
                .max(Solution::compareTo).get();
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
        memoryLimiter.updateRecentlyAccessedNodes(leaf);

        if (solution.isEmpty() && leaf.getNodeStatistics().getDepth() > minDepthSubtreeRemoval) {
            removeSubtreeWithNoSolutions((leaf));
        }

        while (memoryLimiter.shouldPruneTree()) {
            removeLeaf(memoryLimiter.whichNodeToPrune());
        }

        return solution;
    }

    private void removeSubtreeWithNoSolutions(Node subtreeRoot) {
        log.debug("Removing subtree at depth {}", subtreeRoot.getNodeStatistics().getDepth());
        removeLeaf(subtreeRoot);
    }

    private void removeLeaf(Node node) {
        assert(node.getChildrenSize() == 0);

        // Shouldn't happen if node limit is not very small.
        if (node == root) {
            root.setUnexpanded();
            return;
        }

        node.getParent().removeChild(node);
        memoryLimiter.removeNodeFromQueue(node);

        if (node.getParent().getChildrenSize() == 0) {
            removeLeaf(node.getParent());
        }
    }
}
