package eu.melodic.upperware.utilitygenerator.reconfiguration_penalty;

import eu.melodic.upperware.penaltycalculator.PenaltyFunctionResult;
import eu.melodic.upperware.utilitygenerator.evaluator.ConfigurationElement;

import java.util.Collection;
import java.util.Optional;

public interface PenaltyService {

    Optional<PenaltyFunctionResult> getPenalty(Collection<ConfigurationElement> actConfiguration, Collection<ConfigurationElement> newConfiguration);
}
