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
        
        if (solution.isEmpty() && leaf.getNodeStatistics().getDepth() > minDepthSubtreeRemoval) {
            removeSubtreeWithNoSolutions((leaf));
        }

        memoryLimiter.updateRecentlyAccessedNodes(leaf);
        while (memoryLimiter.shouldPruneTree()) {
            pruneSubTree(memoryLimiter.whichNodeToPrune());
        }

        return solution;
    }

    // Prunes subtree completely. SubRoot's children should be all leaves.
    private void pruneSubTree(Node subRoot) {
        if (subRoot == root) { // If is a root then do nothing.
            return;
        }
        memoryLimiter.removeNodeFromQueue(subRoot);

        memoryLimiter.decreaseCount(subRoot.getChildrenSize());
        subRoot.removeChildren();
        removeLeaf(subRoot);
    }

    private void removeSubtreeWithNoSolutions(Node subtreeRoot) {
        log.debug("Removing subtree at depth {}", subtreeRoot.getNodeStatistics().getDepth());
        removeLeaf(subtreeRoot);
    }

    private void removeLeaf(Node node) {
        memoryLimiter.decreaseCount(1);
        node.getParent().removeChild(node);

        if (node.getParent() != root && node.getParent().getChildrenSize() == 0) {
            memoryLimiter.removeNodeFromQueue(node.getParent()); // Parent is becoming a leaf and we should remove it from queue.
            removeLeaf(node.getParent());
        }
        // There's a corner case in which root can lose its all children.
        // If node limit is > ~10000 this should never happen.
        else if (node.getParent() == root && root.getChildrenSize() == 0) {
            root.setUnexpanded();
        }
    }
}
