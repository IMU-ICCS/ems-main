package eu.melodic.upperware.mcts_solver.solver.utils.concurrency_utils;

import cp_wrapper.solution.CpSolution;
import lombok.Getter;

public class SolutionBuffer {
    @Getter
    private CpSolution bestSolution = null;

    public synchronized void enqueue(CpSolution solution) {
        if (bestSolution == null || bestSolution.getUtility() < solution.getUtility()) {
            bestSolution = solution;
        }
    }
}
