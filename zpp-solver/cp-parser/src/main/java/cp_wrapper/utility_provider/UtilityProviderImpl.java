package cp_wrapper.utility_provider;

import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import lombok.AllArgsConstructor;

import java.util.List;
@AllArgsConstructor
public class UtilityProviderImpl implements UtilityProvider{
    private UtilityGeneratorApplication utility;
    @Override
    synchronized public double evaluate(List<VariableValueDTO> result) {
        return utility.evaluate(result);
    }
}
