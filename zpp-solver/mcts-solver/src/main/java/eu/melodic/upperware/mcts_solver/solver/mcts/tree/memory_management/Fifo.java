package eu.melodic.upperware.mcts_solver.solver.mcts.tree.memory_management;

import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Node;

public interface Fifo {
    // Removes front element of queue and returns it. Does nothing if queue is empty and returns null.
    Node popFront();
    /*
     Moves node to the back of queue.
     If node was already in queue then its previous occurrence is forgotten and it's added as a new element.
     */
    void pushBack(Node node);
    boolean empty();
}
