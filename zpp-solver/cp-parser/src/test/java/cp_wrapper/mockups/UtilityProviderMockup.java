package cp_wrapper.mockups;

import cp_wrapper.utility_provider.UtilityProvider;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;

import java.util.List;

public class UtilityProviderMockup implements UtilityProvider {

    @Override
    public double evaluate(List<VariableValueDTO> result) {
        return 0;
    }
}
