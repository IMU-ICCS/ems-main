package eu.melodic.upperware.mcts_solver.solver.mcts.tree;

import eu.melodic.upperware.mcts_solver.solver.mcts.tree.memory_management.MemoryLimiter;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.util.stream.IntStream;

import static java.util.Collections.max;

public abstract class Tree {
    protected Node root;
    private Policy policy;
    private MoveProvider moveProvider; // MoveProvider is responsible for both tree search and expansion.
    private BranchTrimmer branchTrimmer; // Responsible for reducing tree size from time to time.
    private MemoryLimiter memoryLimiter; // Responsible for prevention of memory overflow by limiting tree size.

    public Tree(Policy policy, MoveProvider moveProvider, BranchTrimmer branchTrimmer, MemoryLimiter memoryLimiter) {
        this.policy = policy;
        this.moveProvider = moveProvider;
        this.branchTrimmer = branchTrimmer;
        this.memoryLimiter = memoryLimiter;
    }

    public Solution run(int iterations) {
        Solution solution = IntStream.range(0, iterations)
                .mapToObj(i ->runIteration())
                .max(Solution::compareTo).get();
        printTree();
        System.out.println("Any memory limiter thinks there are " + memoryLimiter.getCount() + " nodes.");
        return solution;
    }

    private void updateAccessed(Node startingNode, int numberOfAddedNodes) {
        Node current = startingNode;
        memoryLimiter.addCount(numberOfAddedNodes);

        while (current != null) {
            if (current.isExpanded() && current != root) { // If current is not leaf or root.
                memoryLimiter.updateAccessed(current);
            }
            current = current.getParent();
        }
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

        assert(memoryLimiter.fifoFrontIsCorrect());

        Triplet<Node, Integer, Path> state = searchAndExpand();
        Node leaf = state.getValue0();
        int numberOfAddedNodes = state.getValue1();
        Path path = state.getValue2();
        boolean wasCorrect = memoryLimiter.fifoFrontIsCorrect();
        updateAccessed(leaf, numberOfAddedNodes);

        System.out.println(wasCorrect);
        if (!memoryLimiter.fifoFrontIsCorrect()) {
            System.out.println(memoryLimiter.getFront());
        }
        assert(memoryLimiter.fifoFrontIsCorrect());

        Solution solution = rollout(path);

        assert(memoryLimiter.fifoFrontIsCorrect());

        backPropagate(leaf, solution);
        memoryLimiter.prune();

        assert(memoryLimiter.fifoFrontIsCorrect());

        return solution;
    }


    void printTree() {
        int x = printNode(root);
        System.out.println("JEst tyle nodow " + x);
    }

    int printNode(Node node) {
        System.out.println(node);
        int x = 1;

        for (Node child : node.getChildren()) {
            x += printNode(child);
        }

        return x;
    }
}
