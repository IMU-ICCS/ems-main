package eu.melodic.upperware.utilitygenerator.utility_function;

import eu.melodic.upperware.utilitygenerator.cdo.cp_model.solution.VariableValueDTO;
import eu.melodic.upperware.utilitygenerator.evaluator.ConfigurationElement;

import java.util.Collection;

public abstract class ArgumentConverter {


    public abstract Collection<VariableValueDTO> convertToElements(Collection<VariableValueDTO> solution, Collection<ConfigurationElement> newConfiguration);
}
