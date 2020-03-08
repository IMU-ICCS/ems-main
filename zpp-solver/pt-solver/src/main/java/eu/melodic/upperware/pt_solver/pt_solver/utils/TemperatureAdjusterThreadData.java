package eu.melodic.upperware.pt_solver.pt_solver.utils;

import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.javatuples.Pair;

import java.util.List;

@Data
@AllArgsConstructor
public class TemperatureAdjusterThreadData {
    private Pair<List<VariableValueDTO>, Double> solution;
    private int temperature;
}
