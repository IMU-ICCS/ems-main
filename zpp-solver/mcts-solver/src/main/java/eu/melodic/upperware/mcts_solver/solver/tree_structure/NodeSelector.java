package eu.melodic.upperware.mcts_solver.solver.tree_structure;

import java.util.Collection;

public interface NodeSelector {

    Node select(Collection<Node> nodes);
}
