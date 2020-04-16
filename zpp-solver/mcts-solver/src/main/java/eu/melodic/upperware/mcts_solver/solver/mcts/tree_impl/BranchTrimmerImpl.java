package eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl;

import eu.melodic.upperware.mcts_solver.solver.mcts.tree.BranchTrimmer;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Node;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.memory_management.MemoryLimiter;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public class BranchTrimmerImpl implements BranchTrimmer {
    private int visitCountThreshold;
    private double trimmingScale;
    private MemoryLimiter memoryLimiter;

    @Override
    public void trimNodesInPath(Node leaf) {
        Node current = leaf;

        while (current != null) {
            trimNode(current);
            current = current.getParent();
        }
    }

    private Node trimNode(Node current) {
        List<Node> children = current.getChildren();
        int childrenSize = children.size();

        if (!current.isExpanded() || current.isTrimmed() || current.getNodeStatistics().getVisitCount() < visitCountThreshold * childrenSize) {
            return current;
        }

        int trimmedSize = Math.max((int) (childrenSize * trimmingScale), 1);
        memoryLimiter.decreaseCount(trimmedSize);

        Collections.sort(children);
        children.subList(trimmedSize, childrenSize).clear();

        current.setTrimmed();
        return current;
    }
}
