package cp_wrapper.solution;

import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CpSolution {
    private List<VariableValueDTO> solution;
    private double utility;
}
