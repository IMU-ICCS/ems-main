package eu.melodic.upperware.mcts_solver.solver.mcts.tree;

import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;

import java.util.stream.IntStream;

import static java.util.Collections.max;

@Slf4j
public abstract class Tree {
    protected Node root;
    private Policy policy;
    private MoveProvider moveProvider; // MoveProvider is responsible for both tree search and expansion.
    private final int minDepthSubtreeRemoval;
    public Tree(Policy policy, MoveProvider moveProvider) {
        this.policy = policy;
        this.moveProvider = moveProvider;
        this.minDepthSubtreeRemoval = policy.minDepthSubtreeRemoval();
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
        return solution;
    }

    private void removeSubtreeWithNoSolutions(Node subtreeRoot) {
        log.debug("Removing subtree at depth {}", subtreeRoot.getNodeStatistics().getDepth());
        removeNode(subtreeRoot);
    }

    private void removeNode(Node node) {
        node.getParent().removeChild(node);
        if (node.getParent() != root && node.getParent().getChildrenSize() == 0) {
            removeNode(node.getParent());
        }
    }
}
