package eu.melodic.upperware.mcts_solver.solver.mcts.tree;

public interface NodeStatistics {
    int getVisitCount();
    int getDepth();
    void update(Solution solution); // Updates statistics after finding some path (solution).
    void markNewVisit();
    double getEvaluation(NodeStatistics parentStats); // Evaluates node.
    boolean isExpanded();
    boolean isTrimmed();
    void setExpanded();
    void setDeExpanded();
    void setTrimmed();
}
