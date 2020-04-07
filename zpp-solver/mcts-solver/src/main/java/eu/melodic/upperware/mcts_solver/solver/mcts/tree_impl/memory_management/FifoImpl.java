package eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.memory_management;

import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Node;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.memory_management.Fifo;

public class FifoImpl implements Fifo {
    private Node front = null;
    private Node back = null;

    // Removes front element of queue and returns it. Does nothing if queue is empty and returns null.
    @Override
    public Node popFront() {
        if (front == null) {
            return null;
        }

        Node toReturn = front;
        front = front.getFifoNodeLinker().getNext();
        toReturn.getFifoNodeLinker().removeFromFifo();

        if (front == null) {
            back = null;
        } else {
            front.getFifoNodeLinker().setPrevious(null);
        }

        return toReturn;
    }

    /*
     Moves node to the back of queue.
     If node was already in queue then its previous occurrence is forgotten and it's added as a new element.
     */
    @Override
    public void pushBack(Node node) {
        if (node.getFifoNodeLinker().isInFifo()) {
            Node previous = node.getFifoNodeLinker().getPrevious();
            Node next = node.getFifoNodeLinker().getNext();

            if (previous != null) {
                previous.getFifoNodeLinker().setNext(next);
            } else { // Current node was front.
                this.front = next;
            }

            if (next != null) {
                next.getFifoNodeLinker().setPrevious(previous);
            } else { // Current node was back.
                this.back = previous;
            }

            node.getFifoNodeLinker().removeFromFifo();
        }

        // Current node is not in fifo.
        if (this.empty()) { // If fifo is empty.
            this.front = this.back = node;
            node.getFifoNodeLinker().addToFifo(null);
        } else {
            back.getFifoNodeLinker().setNext(node);
            node.getFifoNodeLinker().addToFifo(back);
            this.back = node;
        }
    }

    @Override
    public boolean empty() {
        return front == null;
    }
}
