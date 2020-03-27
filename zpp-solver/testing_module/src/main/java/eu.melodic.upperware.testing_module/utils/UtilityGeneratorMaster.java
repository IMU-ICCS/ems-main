package eu.melodic.upperware.testing_module.utils;

import cp_wrapper.utility_provider.UtilityProvider;
import cp_wrapper.utility_provider.UtilityProviderFactory;

public interface UtilityGeneratorMaster extends UtilityProviderFactory {
    public UtilityProvider createParallelUtilityProvider(int size);
}
