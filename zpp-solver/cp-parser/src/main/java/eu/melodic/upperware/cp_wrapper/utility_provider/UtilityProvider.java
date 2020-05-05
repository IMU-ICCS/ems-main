package eu.melodic.upperware.cp_wrapper.utility_provider;

import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;

import java.util.List;

public interface UtilityProvider {
    double evaluate(List<VariableValueDTO> result);
}
