package eu.melodic.upperware.utilitygenerator.reconfiguration_penalty;

import eu.melodic.upperware.penaltycalculator.PenaltyFunction;
import eu.melodic.upperware.penaltycalculator.PenaltyFunctionProperties;
import eu.melodic.upperware.utilitygenerator.evaluator.ConfigurationElement;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
public class PenaltyServiceImpl implements PenaltyService{


    private PenaltyFunction penaltyFunction;

    public PenaltyServiceImpl(PenaltyFunctionProperties properties){
        this.penaltyFunction = new PenaltyFunction();
        if (penaltyFunction.getProperties() == null){
            log.warn("Properties for penaltyFunction are null, using properties from CP Solver");
            penaltyFunction.setProperties(properties);
        }
    }

    @Override
    public double getPenalty(Collection<ConfigurationElement> actConfiguration, Collection<ConfigurationElement> newConfiguration) {

        Collection<eu.melodic.upperware.penaltycalculator.ConfigurationElement> actConfigurationForPenalty = convertToPenaltyConfigurationElement(actConfiguration);
        Collection<eu.melodic.upperware.penaltycalculator.ConfigurationElement> newConfigurationForPenalty = convertToPenaltyConfigurationElement(newConfiguration);

        return penaltyFunction.evaluatePenaltyFunction(actConfigurationForPenalty, newConfigurationForPenalty);
    }


    private Collection<eu.melodic.upperware.penaltycalculator.ConfigurationElement> convertToPenaltyConfigurationElement(Collection<ConfigurationElement> configuration) {
        return configuration.stream()
                .map(element -> new eu.melodic.upperware.penaltycalculator.ConfigurationElement(element.getId(), element.getNodeCandidate(), element.getCardinality()))
                .collect(Collectors.toList());
    }
}
