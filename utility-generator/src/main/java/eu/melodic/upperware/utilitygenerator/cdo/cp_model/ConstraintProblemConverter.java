package eu.melodic.upperware.utilitygenerator.cdo.cp_model;

import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.MetricDTO;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.MetricDTOFactory;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableDTO;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.solution.VariableValueDTO;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.solution.VariableValueDTOFactory;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpMetric;
import eu.paasage.upperware.metamodel.cp.CpVariable;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.common.util.EList;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Slf4j
public class ConstraintProblemConverter {

    private final int INITIAL_DEPLOYMENT_ID = -1;
    @Getter
    private boolean isReconfig;

    private Collection<VariableValueDTO> getActualConfiguration(ConstraintProblem cp) {
        int deployedSolutionId = cp.getDeployedSolutionId();
        if (deployedSolutionId != INITIAL_DEPLOYMENT_ID) {
            isReconfig = true;
            return cp.getSolution().get(deployedSolutionId)
                    .getVariableValue().stream()
                    .map(VariableValueDTOFactory::createElement)
                    .collect(Collectors.toList());
        } else {
            isReconfig = false;
            return Collections.emptyList();
        }
    }

    private Collection<MetricDTO> createMetricsForUG(EList<CpMetric> metrics) {
        log.info("Creating metrics for Utility Generator");
        Collection<MetricDTO> metricDTOS = metrics.stream().map(MetricDTOFactory::createMetricDTO).collect(Collectors.toList());
        log.info("Creating metrics for Utility Generator is finished, number of metrics: {}.", metricDTOS.size());
        return metricDTOS;
    }

    private Collection<VariableDTO> createVariablesForUG(EList<CpVariable> variables) {
        log.info("Creating variables for Utility Generator");
        Collection<VariableDTO> variableDTOS = variables.stream()
                .map(variable -> new VariableDTO(variable.getId(), variable.getComponentId(), variable.getVariableType()))
                .collect(Collectors.toList());
        log.info("Creating variables for Utility Generator is finished, number of variables: {}",variableDTOS.size());
        return variableDTOS;
    }
}
