package eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.memory_management;

import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Node;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.memory_management.QueueLinker;
import lombok.Getter;
import lombok.Setter;

@Getter
public class QueueLinkerImpl implements QueueLinker {
    private boolean isInFifo = false;
    @Setter
    private Node next = null; // Next is a node in back direction.
    @Setter
    private Node previous = null; // Previous is a node in front direction.

    @Override
    public void addToFifo(Node previous) {
        this.isInFifo = true;
        this.previous = previous;
        this.next = null;
    }

    @Override
    public void removeFromFifo() {
        this.isInFifo = false;
        this.next = null;
        this.previous = null;
    }
}
