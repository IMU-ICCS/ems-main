package eu.melodic.upperware.mcts_solver.solver.mcts.tree_impl.memory_management;

import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Node;
import eu.melodic.upperware.mcts_solver.solver.mcts.tree.memory_management.Fifo;
import lombok.Getter;

public class FifoImpl implements Fifo {
    @Getter
    private Node front = null;
    private Node back = null;

    @Override
    public Node popFront() {
        if (front == null) {
            return null;
        }

        Node toReturn = front;
        front = front.getNext();
        toReturn.removeFromFifo();

        if (front == null) {
            back = null;
        }

        return toReturn;
    }

    @Override
    public void pushBack(Node node) {
        if (node.isInFifo()) {
            Node previous = node.getPrevious();
            Node next = node.getNext();

            if (previous != null) {
                previous.setNext(next);
            } else { // Current node was front.
                this.front = next;
            }

            if (next != null) {
                next.setPrevious(previous);
            } else { // Current node was back.
                this.back = previous;
            }

            node.removeFromFifo();
        }

        // Current node is not in fifo.

        if (this.empty()) { // If fifo is empty.
            this.front = this.back = node;
            node.addToFifo(null);
        } else {
            back.setNext(node);
            node.addToFifo(back);
            this.back = node;
        }

    }

    @Override
    public boolean empty() {
        return front == null;
    }

    public void printFifo() {
        Node beg = front;


        System.out.println(beg == null ? "null" : beg.toString());
        assert ((front != null) == (back != null));
        while (beg != back) {
            beg = beg.getNext();

            if (beg == null) {
                continue;
            }
            assert(beg != null);
            System.out.println(beg.toString());
        }
    }

    @Override
    public int size() {
        Node beg = front;
        int size = front != null ? 1 : 0;
        while (beg != back && beg != null) {
            beg = beg.getNext();
            size++;
        }
        return size;
    }
}
