package eu.melodic.upperware.utilitygenerator.reconfiguration_penalty;

import eu.melodic.upperware.utilitygenerator.evaluator.ConfigurationElement;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
public class PenaltyServiceImpl implements PenaltyService{



    @Override
    public void getPenalty(Collection<ConfigurationElement> actConfiguration, Collection<ConfigurationElement> newConfiguration) {
        log.warn("penalty calculator not supported!!");
    }


    private void convertToPenaltyConfigurationElement(Collection<ConfigurationElement> configuration) {
        log.warn("penalty calculator not supported!!");
    }

}
