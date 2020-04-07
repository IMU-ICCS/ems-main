package eu.melodic.upperware.mcts_solver.solver.mcts.tree.memory_management;

import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Node;

// Class responsible for inserting/deleting and moving node inside fifo queue.
public interface FifoNodeLinker {
    boolean isInFifo();
    void addToFifo(Node next);
    void removeFromFifo();
    Node getNext();
    Node getPrevious();
    void setNext(Node node);
    void setPrevious(Node node);
}
