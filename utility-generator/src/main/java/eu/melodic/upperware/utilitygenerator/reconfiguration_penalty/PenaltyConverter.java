package eu.melodic.upperware.utilitygenerator.reconfiguration_penalty;

import eu.melodic.upperware.penaltycalculator.PenaltyFunctionProperties;
import eu.melodic.upperware.penaltycalculator.PenaltyFunctionResult;

import eu.melodic.upperware.utilitygenerator.cdo.camel_model.FromCamelModelExtractor;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.melodic.upperware.utilitygenerator.evaluator.ConfigurationElement;
import eu.melodic.upperware.utilitygenerator.utility_function.ArgumentConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.mariuszgromada.math.mxparser.Argument;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class PenaltyConverter implements ArgumentConverter {

    private PenaltyService penaltyService;
    private String penaltyAttributeName;
    private Collection<ConfigurationElement> actConfiguration;

    public PenaltyConverter(FromCamelModelExtractor fromCamelModelExtractor, Collection<ConfigurationElement> actConfiguration, PenaltyFunctionProperties penaltyFunctionProperties) {
        this.penaltyService = new PenaltyServiceImpl(penaltyFunctionProperties);
        this.actConfiguration = actConfiguration;
        this.penaltyAttributeName = fromCamelModelExtractor.getReconfigurationPenaltyAttribute();
        if (StringUtils.isNotEmpty(penaltyAttributeName)) {
            log.info("ReconfigurationPenalty attribute: {}", penaltyAttributeName);
        }
    }


    @Override
    public Collection<Argument> convertToArguments(Collection<VariableValueDTO> solution, Collection<ConfigurationElement> newConfiguration) {

        double penalty;

        if (StringUtils.isEmpty(penaltyAttributeName)) {
            return Collections.emptyList();
        }
        try {
            PenaltyFunctionResult penaltyResult = penaltyService.getPenalty(actConfiguration, newConfiguration);
            penalty = penaltyResult.getPenaltyValue();

        } catch (Exception e) {
            log.warn("There was an error during invoking the Penalty Calculator library, returning 0 as a penalty value. The error: {}", e.toString());
            e.printStackTrace();
            penalty = 0.0;

        }
        if (Double.isNaN(penalty)) {
            log.warn("The value of Reconfiguration Penalty is NaN, returning 0");
            penalty = 0.0;
        } else {
            log.info("The value of Reconfiguration Penalty: {}", penalty);
        }
        return Stream.of(new Argument(penaltyAttributeName, penalty)).collect(Collectors.toList());
    }
}
