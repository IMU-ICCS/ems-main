package eu.melodic.upperware.mcts_solver.solver.mcts_tree_impl;

import eu.melodic.upperware.mcts_solver.solver.mcts_tree.Node;

import java.util.ArrayList;

public class NodeImpl extends Node {

    @Override
    public void linkToTree(Node parent) {
        this.parent = parent;
        children = new ArrayList<>();
        nodeStatistics = new NodeStatisticsImpl(parent == null ? -1 : parent.getNodeStatistics().getDepth());
    }

}
