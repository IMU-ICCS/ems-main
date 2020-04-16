package eu.melodic.upperware.mcts_solver.solver.mcts.tree.memory_management;

import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Node;

// Class responsible for inserting/deleting and moving node inside fifo queue.
public interface QueueLinker {
    boolean isInFifo();
    void addToFifo(Node next);
    void removeFromFifo();
    // Returns neighbour node towards back of queue. Null if there are no next nodes.
    Node getNext();
    // Returns neighbour node towards front of queue. Null if there are no previous nodes.
    Node getPrevious();
    void setNext(Node node);
    void setPrevious(Node node);
}
