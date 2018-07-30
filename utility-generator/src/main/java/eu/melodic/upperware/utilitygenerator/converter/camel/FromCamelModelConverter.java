/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.converter.camel;

import camel.core.CamelModel;
import camel.metric.CompositeMetric;
import camel.metric.Metric;
import camel.metric.MetricVariable;
import camel.metric.RawMetric;
import camel.metric.impl.MetricTypeModelImpl;
import camel.metric.impl.MetricVariableImpl;
import camel.requirement.OptimisationRequirement;
import camel.requirement.RequirementModel;
import camel.requirement.impl.OptimisationRequirementImpl;
import eu.melodic.upperware.utilitygenerator.communication.CDOService;
import eu.melodic.upperware.utilitygenerator.communication.CDOServiceFromFile;
import eu.melodic.upperware.utilitygenerator.communication.CDOServiceImpl;
import eu.melodic.upperware.utilitygenerator.model.function.NodeCandidateAttribute;
import eu.paasage.mddb.cdo.client.CDOClient;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.cdo.view.CDOView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import static eu.melodic.upperware.utilitygenerator.converter.camel.MappingTypeUtils.*;
import static eu.melodic.upperware.utilitygenerator.model.UtilityFunction.isInFormula;

@Slf4j
@Getter
public class FromCamelModelConverter {

    private CDOService cdoService;
    private CDOView view;

    private CamelModel model;
    private MetricTypeModelImpl metricModel;
    private Collection<MetricVariableImpl> metricVariables;
    private String utilityFunctionFormula;

    public FromCamelModelConverter(String path, boolean readFromFile) {
        if (readFromFile) {
            this.cdoService = new CDOServiceFromFile();
        } else {
            this.cdoService = new CDOServiceImpl(new CDOClient());
        }
        log.info("path = {}", path);

        this.view = cdoService.openView();
        this.model = cdoService.getCamelModel(path, view);

        this.metricModel = (MetricTypeModelImpl) model.getMetricModels().get(0);

        this.metricVariables = new ArrayList<>();
        metricModel.getMetrics().stream()
                .filter(m -> m instanceof MetricVariable)
                .forEach(m -> metricVariables.add((MetricVariableImpl) m));

        log.info("metricVariables size = {}", metricVariables.size());
        this.utilityFunctionFormula = getUtilityFormula();

    }

    public void endWorkWithCamelModel() {
        cdoService.closeView(view);
    }

    /* variables which should be also in CP model */
    public Collection<MetricVariableImpl> getVariablesUsedInFunction() {
        return this.metricVariables.stream()
                .filter(variable -> isInFormula(utilityFunctionFormula, variable.getName())
                        && !variable.isCurrentConfiguration()
                        && hasTypeOfVariable(variable))
                .collect(Collectors.toList());
    }

    /* variables from Constraint Problem with current config flag */
    public Collection<MetricVariableImpl> getCurrentConfigMetricVariablesUsedInFunction() {
        return metricVariables.stream()
                .filter(variable -> variable.isCurrentConfiguration() && hasTypeOfVariable(variable) && isInFormula(utilityFunctionFormula, variable.getName()))
                .collect(Collectors.toList());
    }


    /* raw and composite metrics */
    public Collection<Metric> getMetricsUsedInFunction() {
        return metricModel.getMetrics().stream()
                .filter(m -> (m instanceof RawMetric || m instanceof CompositeMetric) && isInFormula(utilityFunctionFormula, m.getName()))
                .collect(Collectors.toList());
    }


    /* variable with NodeCandidateAttribute annotations */
    //todo - checking if type is good variableType or NodeCandidatesAttributesType
    public Collection<NodeCandidateAttribute> getAttributesOfNodeCandidates() {
        return metricVariables.stream()
                .filter(variable -> hasTypeOfNodeCandidateAttribute(variable)
                        && isInFormula(utilityFunctionFormula, variable.getName())
                        && !variable.isOnNodeCandidates()
                        && !variable.isCurrentConfiguration())
                .map(attribute -> new NodeCandidateAttribute(
                        attribute.getName(), attribute.getComponent().getName(),
                        getNodeCandidateAttributeType(attribute), false))
                .collect(Collectors.toList());

    }
    //todo - to create one method

    /* variable with NodeCandidateAttribute anotations and current config flag */
    public Collection<NodeCandidateAttribute> getCurrentConfigAttributesOfNodeCandidates() {
        return metricVariables.stream()
                .filter(variable -> variable.isCurrentConfiguration()
                        && hasTypeOfNodeCandidateAttribute(variable)
                        && isInFormula(utilityFunctionFormula, variable.getName())
                        && !variable.isOnNodeCandidates())
                .map(attribute -> new NodeCandidateAttribute(
                        attribute.getName(), attribute.getComponent().getName(),
                        getNodeCandidateAttributeType(attribute), false))
                .collect(Collectors.toList());
    }

    /* on candidates flag */ //todo - it may be connected with previous method
    public Collection<NodeCandidateAttribute> getListOfAttributesOfNodeCandidates() {
        return metricVariables.stream()
                .filter(variable -> variable.isOnNodeCandidates() && hasTypeOfNodeCandidateAttribute(variable) && isInFormula(utilityFunctionFormula, variable.getName()))
                .map(attribute -> new NodeCandidateAttribute(
                        attribute.getName(), attribute.getComponent().getName(), getNodeCandidateAttributeType(attribute), true))
                .collect(Collectors.toList());
    }

    /* optimisation requirement - utility function */
    private String getUtilityFormula() {

        RequirementModel requirementModel = model.getRequirementModels().get(0);
        OptimisationRequirement optimisationRequirement = (OptimisationRequirement) requirementModel
                .getRequirements()
                .stream()
                .filter(r -> r instanceof OptimisationRequirementImpl)
                .findAny()
                .orElseThrow(() -> new IllegalStateException("optimization requirement is obligatory"));

        return optimisationRequirement.getMetricVariable().getFormula();
    }
}
