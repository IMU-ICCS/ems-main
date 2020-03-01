package eu.melodic.upperware.mcts_solver.solver.mcts_tree;

import eu.melodic.upperware.mcts_solver.solver.mcts_tree_impl.SolutionCP;
import lombok.Getter;

import java.util.List;


public abstract class Node {
    @Getter
    protected Node parent;
    @Getter
    protected List<Node> children;
    @Getter
    protected int value;
    @Getter
    protected NodeStatistics nodeStatistics;

    // Called after creation of a Node in order to add it to a tree.
    public abstract void linkToTree(Node parent);

    public void update(SolutionCP solution) {
        nodeStatistics.update(solution);
    }
}
