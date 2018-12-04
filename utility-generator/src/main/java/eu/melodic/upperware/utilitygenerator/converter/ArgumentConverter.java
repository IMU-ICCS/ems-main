package eu.melodic.upperware.utilitygenerator.converter;

import eu.melodic.upperware.utilitygenerator.model.ConfigurationElement;
import eu.melodic.upperware.utilitygenerator.model.function.Element;

import java.util.Collection;

public abstract class ArgumentConverter {


    public abstract Collection<Element> convertToElements(Collection<Element> solution, Collection<ConfigurationElement> newConfiguration);
}
