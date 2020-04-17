package eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl;

import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Node;
import lombok.Getter;
import lombok.Setter;

@Getter
public class QueueLinker {
    private boolean isInQueue = false;
    @Setter
    private Node next = null; // Next is a node in back direction.
    @Setter
    private Node previous = null; // Previous is a node in front direction.

    public void addToFifo(Node previous) {
        this.isInQueue = true;
        this.previous = previous;
        this.next = null;
    }

    public void removeFromFifo() {
        this.isInQueue = false;
        this.next = null;
        this.previous = null;
    }
}
