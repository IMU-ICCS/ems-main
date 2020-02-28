package eu.melodic.upperware.mcts_solver.solver.selector_strategies;

import eu.melodic.upperware.mcts_solver.solver.tree_structure.Node;

import java.util.Collection;

public interface NodeSelector {

    Node select(Collection<Node> nodes);
}
