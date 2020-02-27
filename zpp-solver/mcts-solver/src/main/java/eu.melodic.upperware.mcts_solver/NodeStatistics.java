package eu.melodic.upperware.mcts_solver;

import lombok.Getter;
import lombok.NoArgsConstructor;

//TODO constructor
public class NodeStatistics {
    @Getter
    private double averageFailureDepth;
    @Getter
    private double maximalUtility;
    @Getter
    private int visitCount;
    @Getter
    private int depth;

    public void update(int failureDepth, double utility) {
        //TODO
    }

    public void visit() {
        visitCount++;
    }
}
