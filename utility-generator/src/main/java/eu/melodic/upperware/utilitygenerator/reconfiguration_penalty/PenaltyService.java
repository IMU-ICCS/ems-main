package eu.melodic.upperware.utilitygenerator.reconfiguration_penalty;

import eu.melodic.upperware.utilitygenerator.evaluator.ConfigurationElement;

import java.util.Collection;

public interface PenaltyService {

    void getPenalty(Collection<ConfigurationElement> actConfiguration, Collection<ConfigurationElement> newConfiguration);
}
