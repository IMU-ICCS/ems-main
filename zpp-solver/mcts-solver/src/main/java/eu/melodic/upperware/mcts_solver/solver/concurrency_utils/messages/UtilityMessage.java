package eu.melodic.upperware.mcts_solver.solver.concurrency_utils.messages;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UtilityMessage implements Message, Comparable {
    private double utility;
    private int pid;

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof UtilityMessage)) {
            return -1;
        } else {
            return Double.compare(this.utility, ((UtilityMessage) o).utility);
        }
    }
}