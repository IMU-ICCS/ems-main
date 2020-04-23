package eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl;

import lombok.Getter;
import lombok.Setter;

@Getter
public class QueueLinker {
    private boolean isInQueue = false;
    @Setter
    private NodeImpl next = null; // Next is a node in back direction.
    @Setter
    private NodeImpl previous = null; // Previous is a node in front direction.

    public void addToQueue(NodeImpl current, NodeImpl previous) {
        if (previous != null) {
            previous.getQueueLinker().setNext(current);
        }

        this.isInQueue = true;
        this.previous = previous;
        this.next = null;
    }

    public void removeFromQueue() {
        if (previous != null) {
            previous.getQueueLinker().setNext(next);
        }

        if (next != null) {
            next.getQueueLinker().setPrevious(previous);
        }

        this.isInQueue = false;
        this.next = null;
        this.previous = null;
    }
}
