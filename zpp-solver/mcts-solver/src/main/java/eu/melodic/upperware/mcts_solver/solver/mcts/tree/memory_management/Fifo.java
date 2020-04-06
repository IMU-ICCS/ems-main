package eu.melodic.upperware.mcts_solver.solver.mcts.tree.memory_management;

import eu.melodic.upperware.mcts_solver.solver.mcts.tree.Node;

public interface Fifo {
    Node popFront();
    void pushBack(Node node);
    boolean empty();

    int size();
    void printFifo();
    Node getFront();
}
