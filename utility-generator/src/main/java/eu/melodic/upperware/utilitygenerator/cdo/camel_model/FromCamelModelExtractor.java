/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.cdo.camel_model;

import camel.core.CamelModel;
import camel.deployment.DeploymentTypeModel;
import camel.deployment.SoftwareComponent;
import camel.metric.MetricModel;
import camel.metric.MetricVariable;
import camel.metric.impl.MetricTypeModelImpl;
import camel.metric.impl.MetricVariableImpl;
import camel.requirement.OptimisationRequirement;
import camel.requirement.Requirement;
import camel.requirement.RequirementModel;
import camel.requirement.impl.OptimisationRequirementImpl;
import eu.melodic.upperware.utilitygenerator.cdo.CDOService;
import eu.melodic.upperware.utilitygenerator.cdo.CDOServiceFromFile;
import eu.melodic.upperware.utilitygenerator.cdo.CDOServiceImpl;
import eu.melodic.upperware.utilitygenerator.dlms.DLMSUtilityAttribute;
import eu.melodic.upperware.utilitygenerator.node_candidates.NodeCandidateAttribute;
import eu.melodic.upperware.utilitygenerator.reconfiguration_penalty.PenaltyAttribute;
import eu.paasage.mddb.cdo.client.exp.CDOClientXImpl;
import eu.paasage.mddb.cdo.client.exp.CDOSessionX;
import eu.passage.upperware.commons.model.tools.CamelModelTool;
import eu.passage.upperware.commons.model.tools.CdoTool;
import eu.passage.upperware.commons.model.tools.metadata.CamelMetadataTool;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static eu.melodic.upperware.utilitygenerator.utility_function.UtilityFunctionUtils.isInFormula;
import static eu.passage.upperware.commons.model.tools.metadata.CamelMetadataTool.findDlmsUtilityAttributeType;
import static eu.passage.upperware.commons.model.tools.metadata.CamelMetadataTool.findPenaltyAttributeType;

@Slf4j
public class FromCamelModelExtractor {

    private CDOService cdoService;
    private CDOSessionX sessionX;

    @Getter
    private String camelModelPath;
    private CamelModel model;
    private Collection<MetricVariableImpl> metricVariables;

    @Getter @Setter
    private String utilityFunctionFormula;

    public FromCamelModelExtractor(String path, boolean readFromFile) {
        if (readFromFile) {
            this.cdoService = new CDOServiceFromFile();
        } else {
            this.cdoService = new CDOServiceImpl(new CDOClientXImpl());
        }
        log.info("path = {}", path);

        this.camelModelPath = path;
        this.sessionX = cdoService.openSession();
        CDOView view = cdoService.openView(sessionX);
        this.model = cdoService.getCamelModel(path, view);
        this.metricVariables = extractMetricVariables(model.getMetricModels());
        this.utilityFunctionFormula = getUtilityFormula().orElse(StringUtils.EMPTY);
    }

    private Collection<MetricVariableImpl> extractMetricVariables(EList<MetricModel> metricModels) {
        if (metricModels.isEmpty()) {
            log.warn("Camel Model does not contain any Metric Model");
            return Collections.emptyList();
        }
        return ((MetricTypeModelImpl) CdoTool.getFirstElement(metricModels)).getMetrics().stream()
                .filter(m -> m instanceof MetricVariable)
                .map(m -> (MetricVariableImpl) m)
                .collect(Collectors.toList());
    }

    public void endWorkWithCamelModel() {
        cdoService.closeSession(sessionX);
    }

    /* variable with NodeCandidateAttribute annotations */
    public Collection<NodeCandidateAttribute> getAttributesOfNodeCandidates() {
        return createNodeCandidatesAttributes(filterVariables(this::isAttributeOfNodeCandidate), false);
    }

    /* variable with NodeCandidateAttribute anotations and current config flag */
    public Collection<NodeCandidateAttribute> getCurrentConfigAttributesOfNodeCandidates() {
        return createNodeCandidatesAttributes(filterVariables(this::isCurrentConfigAttributeOfNodeCandidate), false);
    }

    /* on candidates flag */
    public Collection<NodeCandidateAttribute> getListOfAttributesOfNodeCandidates() {
        return createNodeCandidatesAttributes(filterVariables(this::isListOfAttributesOfNodeCandidates), true);
    }

    /* dlms utility type */
    public Collection<DLMSUtilityAttribute> getListOfDlmsUtilityAttributes() {
        return createDlmsUtilityAttributes(filterVariables(this::isDlmsUtilityAttribute));
    }

    /* software components with unmoveable annotation */
    public Collection<String> getUnmoveableComponentNames() {
        return ((DeploymentTypeModel) CdoTool.getFirstElement(model.getDeploymentModels()))
                .getSoftwareComponents().stream()
                .filter(CamelModelTool::isUnmoveableComponent)
                .map(SoftwareComponent::getName)
                .collect(Collectors.toList());
    }


    public Collection<PenaltyAttribute> getReconfigurationPenaltyAttributes() {
        Collection<MetricVariableImpl> reconfigurationPenaltyAttributes = filterVariables(this::isReconfigurationPenaltyAttribute);
        if (reconfigurationPenaltyAttributes.size() == 0)
            log.warn("Reconfiguration penalty is not supported in this version of the Melodic platform");

        return Collections.emptyList();
    }

    /* optimisation requirement - utility function */
    private Optional<String> getUtilityFormula() {
        RequirementModel requirementModel = CdoTool.getFirstElement(model.getRequirementModels());
        Optional<Requirement> optimisationRequirement = requirementModel
                .getRequirements()
                .stream()
                .filter(r -> r instanceof OptimisationRequirementImpl)
                .findAny();
        return optimisationRequirement.map(requirement -> ((OptimisationRequirement) requirement).getMetricVariable().getFormula());
    }

    private Collection<MetricVariableImpl> filterVariables(Predicate<MetricVariableImpl> metricVariablePredicate) {
        return metricVariables.stream()
                .filter(metricVariablePredicate)
                .collect(Collectors.toList());
    }

    private Collection<NodeCandidateAttribute> createNodeCandidatesAttributes(Collection<MetricVariableImpl> attributes, boolean isList) {
        return attributes.stream()
                .map(attribute -> new NodeCandidateAttribute(attribute.getName(), attribute.getComponent().getName(),
                        CamelMetadataTool.findNodeCandidateAttributeType(attribute), isList))
                .collect(Collectors.toList());
    }

    private Collection<DLMSUtilityAttribute> createDlmsUtilityAttributes(Collection<MetricVariableImpl> attributes) {
        return attributes.stream()
                .map(variable -> new DLMSUtilityAttribute(variable.getName(), variable.getComponent().getName(),
                        findDlmsUtilityAttributeType(variable)))
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

    private boolean isDlmsUtilityAttribute(MetricVariableImpl variable) {
        return CamelMetadataTool.isFromDlmsUtility(variable)
                && isInFormula(utilityFunctionFormula, variable.getName());
    }

    private boolean isReconfigurationPenaltyAttribute(MetricVariableImpl variable){
        return CamelMetadataTool.isFromPenalty(variable)
                && isInFormula(utilityFunctionFormula, variable.getName());
    }

}
