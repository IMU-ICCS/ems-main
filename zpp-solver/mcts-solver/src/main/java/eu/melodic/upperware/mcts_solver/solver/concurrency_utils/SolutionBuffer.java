package eu.melodic.upperware.mcts_solver.solver.concurrency_utils;

import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import lombok.Getter;
import org.javatuples.Pair;

import java.util.List;

public class SolutionBuffer {
    @Getter
    private Pair<List<VariableValueDTO>, Double> bestSolution = null;

    public synchronized void enqueue(Pair<List<VariableValueDTO>, Double> solution) {
        if (bestSolution == null || bestSolution.getValue1() < solution.getValue1()) {
            bestSolution = solution;
        }
    }
}
