package eu.melodic.upperware.utilitygenerator.reconfiguration_penalty;

import eu.melodic.upperware.penaltycalculator.PenaltyFunctionProperties;
import eu.melodic.upperware.penaltycalculator.PenaltyFunctionResult;
import eu.melodic.upperware.utilitygenerator.cdo.camel_model.FromCamelModelExtractor;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.melodic.upperware.utilitygenerator.evaluator.ConfigurationElement;
import eu.melodic.upperware.utilitygenerator.utility_function.ArgumentConverter;
import eu.passage.upperware.commons.model.tools.metadata.CamelMetadata;
import lombok.extern.slf4j.Slf4j;
import org.mariuszgromada.math.mxparser.Argument;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Slf4j
public class PenaltyConverter implements ArgumentConverter {

    private PenaltyService penaltyService;
    private Collection<PenaltyAttribute> penaltyAttributes;
    private Collection<ConfigurationElement> actConfiguration;

    public PenaltyConverter(FromCamelModelExtractor fromCamelModelExtractor, Collection<ConfigurationElement> actConfiguration, PenaltyFunctionProperties penaltyFunctionProperties) {
        this.penaltyService = new PenaltyServiceImpl(penaltyFunctionProperties);
        this.actConfiguration = actConfiguration;
        this.penaltyAttributes = fromCamelModelExtractor.getReconfigurationPenaltyAttributes();
        if (!penaltyAttributes.isEmpty()) {
            log.info("ReconfigurationPenalty attribute: {}", penaltyAttributes);
        }
    }


    @Override
    public Collection<Argument> convertToArguments(Collection<VariableValueDTO> solution, Collection<ConfigurationElement> newConfiguration) {

        PenaltyFunctionResult penaltyResult;

        if (penaltyAttributes.isEmpty()) {
            return Collections.emptyList();
        }
        try {
            penaltyResult = penaltyService.getPenalty(actConfiguration, newConfiguration).orElseThrow(() ->
                new NullPointerException("The value of Reconfiguration Penalty is null.")
            );

        } catch (Exception e) {
            log.warn("There was an error during invoking the Penalty Calculator library, returning 0.0 as a penalty value. The error: {}", e.toString());
            e.printStackTrace();
            penaltyResult = new PenaltyFunctionResult(0.0, 0.0);

        }
        log.info("The value of Reconfiguration Penalty is: penaltyValue = {} and startupTime = {}", penaltyResult.getPenaltyValue(), penaltyResult.getStartupTime());
        PenaltyFunctionResult finalPenaltyResult = penaltyResult;
        return penaltyAttributes.stream()
                .map(attribute -> new Argument(attribute.getName(), getPenaltyValue(attribute, finalPenaltyResult)))
                .collect(Collectors.toList());
    }

    private double getPenaltyValue(PenaltyAttribute attribute, PenaltyFunctionResult penaltyResult){
        if (CamelMetadata.PENALTY.equals(attribute.getType())) {
            return  penaltyResult.getPenaltyValue();
        } else if (CamelMetadata.RECONFIGURATION_TIME.equals(attribute.getType())) {
            return penaltyResult.getStartupTime();
        }
        return 0.0;
    }
}
