package eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl;

import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Node;

public class Queue {
    private NodeImpl front = null;
    private NodeImpl back = null;

    // Removes front element of queue and returns it. Does nothing if queue is empty and returns null.
    public Node popFront() {
        if (front == null) {
            return null;
        }

        NodeImpl toReturn = front;

        front = (NodeImpl) front.getQueueLinker().getNext();
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
    public void pushBack(Node newNode) {
        NodeImpl node = (NodeImpl) newNode;
        if (node.getQueueLinker().isInQueue()) {
            NodeImpl previous = (NodeImpl) node.getQueueLinker().getPrevious();
            NodeImpl next = (NodeImpl) node.getQueueLinker().getNext();

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

    public boolean empty() {
        return front == null;
    }
}
