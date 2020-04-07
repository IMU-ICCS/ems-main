package eu.melodic.upperware.mcts_solver.solver.mcts.tree;

public interface Policy {
    Solution finishPath(Path path);
    int minDepthSubtreeRemoval();
}
