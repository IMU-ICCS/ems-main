/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.converter.camel;

import camel.core.CamelModel;
import camel.deployment.DeploymentTypeModel;
import camel.deployment.SoftwareComponent;
import camel.metric.MetricVariable;
import camel.metric.impl.MetricTypeModelImpl;
import camel.metric.impl.MetricVariableImpl;
import camel.requirement.OptimisationRequirement;
import camel.requirement.RequirementModel;
import camel.requirement.impl.OptimisationRequirementImpl;
import eu.melodic.upperware.utilitygenerator.communication.CDOService;
import eu.melodic.upperware.utilitygenerator.communication.CDOServiceFromFile;
import eu.melodic.upperware.utilitygenerator.communication.CDOServiceImpl;
import eu.melodic.upperware.utilitygenerator.model.function.DLMSUtilityAttribute;
import eu.melodic.upperware.utilitygenerator.model.function.NodeCandidateAttribute;
import eu.paasage.mddb.cdo.client.exp.CDOClientXImpl;
import eu.paasage.mddb.cdo.client.exp.CDOSessionX;
import eu.passage.upperware.commons.model.tools.metadata.CamelMetadata;
import eu.passage.upperware.commons.model.tools.metadata.CamelMetadataTool;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.cdo.view.CDOView;

import java.util.Collection;
import java.util.stream.Collectors;

import static eu.melodic.upperware.utilitygenerator.model.UtilityFunction.isInFormula;
import static eu.passage.upperware.commons.model.tools.metadata.CamelMetadataTool.findDlmsUtilityAttributeType;

@Slf4j
@Getter
public class FromCamelModelConverter {

    private CDOService cdoService;
    private CDOSessionX sessionX;

    private CamelModel model;
    private MetricTypeModelImpl metricModel;
    private Collection<MetricVariableImpl> metricVariables;
    private String utilityFunctionFormula;

    public FromCamelModelConverter(String path, boolean readFromFile) {
        if (readFromFile) {
            this.cdoService = new CDOServiceFromFile();
        } else {
            this.cdoService = new CDOServiceImpl(new CDOClientXImpl());
        }
        log.info("path = {}", path);

        this.sessionX = cdoService.openSession();
        CDOView view = cdoService.openView(sessionX);
        this.model = cdoService.getCamelModel(path, view);
        this.metricModel = (MetricTypeModelImpl) model.getMetricModels().get(0);
        this.metricVariables = metricModel.getMetrics().stream()
                .filter(m -> m instanceof MetricVariable)
                .map(m -> (MetricVariableImpl) m)
                .collect(Collectors.toList());
        this.utilityFunctionFormula = getUtilityFormula();
    }

    public void endWorkWithCamelModel() {
        cdoService.closeSession(sessionX);
    }


    /* variable with NodeCandidateAttribute annotations */
    public Collection<NodeCandidateAttribute> getAttributesOfNodeCandidates() {
        return createNodeCandidatesAttributes(metricVariables.stream()
                .filter(this::isAttributeOfNodeCandidate).collect(Collectors.toList()), false);
    }

    /* variable with NodeCandidateAttribute anotations and current config flag */
    public Collection<NodeCandidateAttribute> getCurrentConfigAttributesOfNodeCandidates() {
        return createNodeCandidatesAttributes(metricVariables.stream()
                .filter(this::isCurrentConfigAttributeOfNodeCandidate).collect(Collectors.toList()), false);
    }

    /* on candidates flag */
    public Collection<NodeCandidateAttribute> getListOfAttributesOfNodeCandidates() {
        return createNodeCandidatesAttributes(metricVariables.stream()
                .filter(this::isListOfAttributesOfNodeCandidates)
                .collect(Collectors.toList()), true);
    }

    public Collection<DLMSUtilityAttribute> getListOfDlmsUtilityAttributes(){
        return metricVariables.stream()
                .filter(this::isDlmsUtilityAttribute)
                .map(variable -> new DLMSUtilityAttribute(variable.getName(), variable.getComponent().getName(), findDlmsUtilityAttributeType(variable)))
                .collect(Collectors.toList());
    }

    /* software components with unmoveable annotation */
    public Collection<String> getUnmoveableComponentNames() {
        return ((DeploymentTypeModel) model.getDeploymentModels().get(0))
                .getSoftwareComponents().stream()
                .filter(this::isUnmoveable)
                .map(SoftwareComponent::getName)
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
                .orElseThrow(() -> new IllegalStateException("Optimization Requirement is obligatory"));

        return optimisationRequirement.getMetricVariable().getFormula();
    }

    private boolean isUnmoveable(SoftwareComponent softwareComponent) {
        return !softwareComponent.getAnnotations().isEmpty()
                && softwareComponent.getAnnotations().get(0).getId().equals(CamelMetadata.UNMOVEABLE.camelName);
    }

    private Collection<NodeCandidateAttribute> createNodeCandidatesAttributes(Collection<MetricVariableImpl> attributes, boolean isList) {
        return attributes.stream()
                .map(attribute -> new NodeCandidateAttribute(attribute.getName(), attribute.getComponent().getName(),
                        CamelMetadataTool.findNodeCandidateAttributeType(attribute), isList))
                .collect(Collectors.toList());
    }

    /* on candidates flag */
    private boolean isListOfAttributesOfNodeCandidates(MetricVariableImpl variable) {
        return CamelMetadataTool.isFromNodeCandidate(variable)
                && isInFormula(utilityFunctionFormula, variable.getName())
                && variable.isOnNodeCandidates();
    }

    /* variable with NodeCandidateAttribute anotations and current config flag */
    private boolean isCurrentConfigAttributeOfNodeCandidate(MetricVariableImpl variable) {
        return CamelMetadataTool.isFromNodeCandidate(variable)
                && isInFormula(utilityFunctionFormula, variable.getName())
                && !variable.isOnNodeCandidates()
                && variable.isCurrentConfiguration();
    }

    /* variable with NodeCandidateAttribute annotations */
    private boolean isAttributeOfNodeCandidate(MetricVariableImpl variable) {
        return CamelMetadataTool.isFromNodeCandidate(variable)
                && isInFormula(utilityFunctionFormula, variable.getName())
                && !variable.isOnNodeCandidates()
                && !variable.isCurrentConfiguration();
    }

    private boolean isDlmsUtilityAttribute(MetricVariableImpl variable){
        return CamelMetadataTool.isFromDlmsUtility(variable)
                && isInFormula(utilityFunctionFormula, variable.getName());
    }
}
