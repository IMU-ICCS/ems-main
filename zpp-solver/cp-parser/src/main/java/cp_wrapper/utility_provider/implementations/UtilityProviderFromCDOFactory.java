package cp_wrapper.utility_provider.implementations;

import cp_wrapper.utility_provider.UtilityProvider;
import cp_wrapper.utility_provider.UtilityProviderFactory;
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication;

import java.util.List;

public class UtilityProviderFromCDOFactory implements UtilityProviderFactory {
    private List<UtilityGeneratorApplication> utilityGenerators;
    private int firstFreeGenerator = 0;

    public UtilityProviderFromCDOFactory(List<UtilityGeneratorApplication> utilityGenerators) {
        this.utilityGenerators = utilityGenerators;
    }

    @Override
    public UtilityProvider create() {
        if (firstFreeGenerator >= utilityGenerators.size()) {
            throw new RuntimeException("Capacity of UtilityProviderFactory has been exceeded!");
        }
        firstFreeGenerator++;
        return new UtilityProviderImpl(utilityGenerators.get(firstFreeGenerator - 1));
    }
}
