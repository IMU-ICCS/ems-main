package eu.melodic.upperware.mcts_solver.utility;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class PartialAssignment {
    @Getter
    private List<Integer> assignment;
    private int size;
    private eu.melodic.upperware.mcts_solver.solver.mcts_cp_wrapper.MCTSWrapper MCTSWrapper;

    public void add(int value) {
        size++;
        assignment.add(value);
    }

    // Cuts assignment at *index* and returns prefix.
    public PartialAssignment cut(int index) {
        //TODO
        return null;
    }
}
