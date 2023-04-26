package eu.melodic.upperware.utilitygenerator.cdo.cp_model;

import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.PerformanceMetric;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.melodic.upperware.utilitygenerator.evaluator.ConfigurationElement;
import eu.melodic.upperware.utilitygenerator.utility_function.ArgumentConverter;
import eu.melodic.upperware.utilitygenerator.utility_function.UtilityFunction;
import lombok.Getter;
import lombok.Setter;
import org.mariuszgromada.math.mxparser.Argument;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import static eu.melodic.upperware.utilitygenerator.utility_function.ArgumentFactory.createArgument;
import static eu.melodic.upperware.utilitygenerator.utility_function.UtilityFunctionUtils.isInFormula;


public class PerformanceMetricConverter implements ArgumentConverter{

    @Getter
    private Collection<PerformanceMetric> performanceMetrics;
    @Getter
    private Map<String,UtilityFunction> performanceMetricsFormulas;
    // Map <PIName, Value>
    @Setter @Getter
    private Map<String,Double> performanceIndicatorValues;
    private String function;


    public PerformanceMetricConverter(Collection<PerformanceMetric> performanceMetrics, Map<String,UtilityFunction> performanceMetricsFormulas, String function){
        this.performanceMetrics = performanceMetrics;
        this.performanceMetricsFormulas = performanceMetricsFormulas;
        this.function = function;
    }

    @Override
    public Collection<Argument> convertToArguments(Collection<VariableValueDTO> solution, Collection<ConfigurationElement> newConfiguration) {

        return performanceIndicatorValues.keySet().stream()
                .filter(performanceIndicatorName -> isInFormula(function, performanceIndicatorName))
                .map(performanceIndicatorName -> createArgument(performanceIndicatorName, performanceIndicatorValues.get(performanceIndicatorName)))
                .collect(Collectors.toList());
    }

}
