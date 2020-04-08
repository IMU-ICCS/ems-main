package eu.melodic.upperware.mcts_solver.solver.mcts.tree;

public interface Policy {
    Solution finishPath(Path path);
    /*
    If the policy is not able to finish path below @minDepthSubtreeRemoval - i.e. empty solution is returned
    - then it means that no valid solution exists in the subtree
     */
    int minDepthSubtreeRemoval();
}
