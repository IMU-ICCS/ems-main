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
        removeNodeFromQueue(front);

        return toReturn;
    }

    /*
     Moves node to the back of queue.
     If node was already in queue then its previous occurrence is forgotten and it's added as a new element.
     */
    public void pushBack(Node newNode) {
        NodeImpl node = (NodeImpl) newNode;
        if (node.getQueueLinker().isInQueue()) { // If is in queue then remove it from queue for now.
            removeNodeFromQueue(node);
        }

        // Current node is not in queue.
        if (this.empty()) { // If queue is empty.
            this.front = this.back = node;
            node.getQueueLinker().addToQueue(node,null);
        } else {
            node.getQueueLinker().addToQueue(node, back);
            this.back = node;
        }
    }

    public boolean empty() {
        return front == null;
    }

    public void removeNodeFromQueue(NodeImpl node) {
        NodeImpl previous = node.getQueueLinker().getPrevious();
        NodeImpl next = node.getQueueLinker().getNext();

        if (front == node) {
            this.front = next;
        }

        if (back == node) {
            this.back = previous;
        }

        node.getQueueLinker().removeFromQueue();
    }
}
