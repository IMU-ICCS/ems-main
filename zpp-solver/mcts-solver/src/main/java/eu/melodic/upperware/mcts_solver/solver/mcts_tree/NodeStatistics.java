package eu.melodic.upperware.mcts_solver.solver.mcts_tree;

public interface NodeStatistics {
    int getVisitCount();
    int getDepth();
    void update(Solution solution); // Updates statistics after finding some path (solution).
    void visit();
    double getEvaluation(NodeStatistics parentStats); // Evaluates node.
}
