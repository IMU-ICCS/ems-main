package cp_wrapper;

import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;

import java.util.List;

public interface UtilityProvider {
    double evaluate(List<VariableValueDTO> result);
}
