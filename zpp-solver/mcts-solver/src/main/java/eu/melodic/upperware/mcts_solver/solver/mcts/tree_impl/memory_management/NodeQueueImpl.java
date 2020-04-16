package eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.memory_management;

import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Node;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.memory_management.NodeQueue;

public class NodeQueueImpl implements NodeQueue {
    private Node front = null;
    private Node back = null;

    // Removes front element of queue and returns it. Does nothing if queue is empty and returns null.
    @Override
    public Node popFront() {
        if (front == null) {
            return null;
        }

        Node toReturn = front;
        front = front.getQueueLinker().getNext();
        toReturn.getQueueLinker().removeFromFifo();

        if (front == null) {
            back = null;
        } else {
            front.getQueueLinker().setPrevious(null);
        }

        return toReturn;
    }

    /*
     Moves node to the back of queue.
     If node was already in queue then its previous occurrence is forgotten and it's added as a new element.
     */
    @Override
    public void pushBack(Node node) {
        if (node.getQueueLinker().isInFifo()) {
            Node previous = node.getQueueLinker().getPrevious();
            Node next = node.getQueueLinker().getNext();

            if (previous != null) {
                previous.getQueueLinker().setNext(next);
            } else { // Current node was front.
                this.front = next;
            }

            if (next != null) {
                next.getQueueLinker().setPrevious(previous);
            } else { // Current node was back.
                this.back = previous;
            }

            node.getQueueLinker().removeFromFifo();
        }

        // Current node is not in fifo.
        if (this.empty()) { // If fifo is empty.
            this.front = this.back = node;
            node.getQueueLinker().addToFifo(null);
        } else {
            back.getQueueLinker().setNext(node);
            node.getQueueLinker().addToFifo(back);
            this.back = node;
        }
    }

    @Override
    public boolean empty() {
        return front == null;
    }
}
