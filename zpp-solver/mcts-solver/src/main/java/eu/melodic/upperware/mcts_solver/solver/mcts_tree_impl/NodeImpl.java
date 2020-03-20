package eu.melodic.upperware.mcts_solver.solver.mcts_tree_impl;

import eu.melodic.upperware.mcts_solver.solver.mcts_tree.Node;

public class NodeImpl extends Node {

    public NodeImpl(int value) {
        super(value);
    }

    public NodeImpl() {
        super(-1); // Dummy value.
        nodeStatistics = new NodeStatisticsImpl(-1);
    }

    @Override
    public void linkToTree(Node parent) {
        this.parent = parent;
        nodeStatistics = new NodeStatisticsImpl(parent.getNodeStatistics().getDepth());
        parent.addChild(this);
    }

}
