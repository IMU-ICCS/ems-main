package eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl;

import eu.melodic.upperware.mcts_solver.solver.mcts.tree.BranchTrimmer;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Node;
import lombok.AllArgsConstructor;

import java.util.*;

@AllArgsConstructor
public class BranchTrimmerImpl implements BranchTrimmer {
    private int visitCountThreshold;
    private double trimmingScale;

    @Override
    public Node trimNode(Node current) {
        List<Node> children = current.getChildren();
        int childrenSize = children.size();

        if (!current.isExpanded() || current.isTrimmed() || current.getNodeStatistics().getVisitCount() < visitCountThreshold * childrenSize) {
            return current;
        }

        int trimmedSize = Math.max((int) (childrenSize * trimmingScale), 1);

        Collections.sort(children);
        children.subList(trimmedSize, childrenSize).clear();

        current.setTrimmed();
        return current;
    }
}
