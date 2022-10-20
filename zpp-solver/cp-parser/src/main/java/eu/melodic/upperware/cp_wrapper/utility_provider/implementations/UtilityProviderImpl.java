package eu.melodic.upperware.cp_wrapper.utility_provider.implementations;

import eu.melodic.upperware.cp_wrapper.utility_provider.UtilityProvider;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.melodic.upperware.utilitygenerator.evaluator.UtilityFunctionEvaluator;
import lombok.AllArgsConstructor;

import java.util.List;
@AllArgsConstructor
public class UtilityProviderImpl implements UtilityProvider {
    private UtilityFunctionEvaluator utility;
    @Override
    public double evaluate(List<VariableValueDTO> result) {
        return utility.evaluate(result);
    }
}
