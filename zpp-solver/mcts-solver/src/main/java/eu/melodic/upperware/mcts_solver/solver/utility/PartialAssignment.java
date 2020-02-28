package eu.melodic.upperware.mcts_solver.solver.utility;

import eu.melodic.upperware.mcts_solver.solver.mcts_cp_wrapper.MCTSWrapper;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class PartialAssignment {
    @Getter
    private List<Integer> assignment;
    private int size;
    private MCTSWrapper mctsWrapper;

    public void add(int value) {
        size++;
        assignment.add(value);
    }

    public void add() {
        int value;

        value = mctsWrapper.generateRandomValue(size);

        size++;
        assignment.add(value);
    }

    public PartialAssignment(MCTSWrapper mctsWrapper) {
        assignment = new ArrayList<>();
        size = 0;
        this.mctsWrapper = mctsWrapper;
    }

}
