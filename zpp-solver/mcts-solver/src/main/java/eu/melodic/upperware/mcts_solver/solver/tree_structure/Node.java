package eu.melodic.upperware.mcts_solver.solver.tree_structure;

import eu.melodic.upperware.mcts_solver.solver.mcts_cp_wrapper.MCTSWrapper;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


public class Node {
    @Getter
    private Node parent;
    @Getter
    private List<Node> children;
    @Getter
    private NodeStatistics nodeStatistics;
    private MCTSWrapper mctsWrapper;

    public Node(Node parent, MCTSWrapper mctsWrapper) {
        this.parent = parent;
        children = new ArrayList<>();
        nodeStatistics = new NodeStatistics(parent == null ? null : parent.getNodeStatistics(), mctsWrapper);
        this.mctsWrapper = mctsWrapper;
    }

    public Node(MCTSWrapper mctsWrapper) {
        this(null, mctsWrapper);
    }

    public void update(int maximalFitSize, double utility) {
        nodeStatistics.update(maximalFitSize, utility);
    }

    public boolean hasChildren() {
        return children.size() > 0;
    }
    //public void addchild/expandchild
}
