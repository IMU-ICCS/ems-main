package eu.melodic.upperware.utilitygenerator.cdo.cp_model;

import eu.melodic.upperware.utilitygenerator.cdo.CDOService;
import eu.melodic.upperware.utilitygenerator.cdo.CDOServiceFromFile;
import eu.melodic.upperware.utilitygenerator.cdo.CDOServiceImpl;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.*;
import eu.paasage.mddb.cdo.client.exp.CDOClientXImpl;
import eu.paasage.mddb.cdo.client.exp.CDOSessionX;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpMetric;
import eu.paasage.upperware.metamodel.cp.CpVariable;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import static eu.passage.upperware.commons.model.tools.CPModelTool.isInitialDeployment;

@Slf4j
public class ConstraintProblemExtractor {

    private CDOService cdoService;
    private CDOSessionX sessionX;
    private ConstraintProblem model;

    public ConstraintProblemExtractor(String cpModelPath, boolean readFromFile) {
        if (readFromFile) {
            this.cdoService = new CDOServiceFromFile();
        } else {
            this.cdoService = new CDOServiceImpl(new CDOClientXImpl());
        }
        log.info("CP Model path = {}", cpModelPath);

        this.sessionX = cdoService.openSession();
        CDOView view = cdoService.openView(sessionX);

        this.model = cdoService.getConstraintProblem(cpModelPath, view);
    }

    public Collection<VariableDTO> extractVariables() {
        Collection<VariableDTO> variableDTOS = extractVariables(model.getCpVariables());
        log.info("Variables from the Constraint Problem:");
        variableDTOS.forEach(v -> log.info("{}, type: {}, component: {}", v.getId(), v.getType(), v.getComponentId()));
        return variableDTOS;
    }

    public Collection<VariableValueDTO> extractActualConfiguration() {
        return extractActualConfiguration(model);
    }

    public Collection<MetricDTO> extractMetrics() {
        Collection<MetricDTO> metricDTOS = extractMetrics(model.getCpMetrics());
        log.info("Metrics from the Constraint Problem:");
        metricDTOS.forEach(m -> log.info("{} = {}", m.getName(), m.getValue()));
        return metricDTOS;
    }

    public void endWorkWithCPModel() {
        cdoService.closeSession(sessionX);
    }

    private Collection<VariableValueDTO> extractActualConfiguration(ConstraintProblem cp) {
        if (isInitialDeployment(cp)) {
            return Collections.emptyList();
        }
        return cp.getSolution().get(cp.getDeployedSolutionId())
                .getVariableValue().stream()
                .map(VariableValueDTOFactory::createElement)
                .collect(Collectors.toList());

    }

    private Collection<MetricDTO> extractMetrics(EList<CpMetric> metrics) {
        return metrics.stream().map(MetricDTOFactory::createMetricDTO).collect(Collectors.toList());
    }

    private Collection<VariableDTO> extractVariables(EList<CpVariable> variables) {
        return variables.stream()
                .map(variable -> new VariableDTO(variable.getId(), variable.getComponentId(), variable.getVariableType()))
                .collect(Collectors.toList());
    }
}
