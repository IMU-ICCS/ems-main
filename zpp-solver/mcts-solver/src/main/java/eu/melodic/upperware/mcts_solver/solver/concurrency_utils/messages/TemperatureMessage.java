package eu.melodic.upperware.mcts_solver.solver.concurrency_utils.messages;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TemperatureMessage implements Message{
    private double temperature;
}
