/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.node_candidates;

import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.utilitygenerator.cdo.camel_model.FromCamelModelExtractor;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableDTO;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.melodic.upperware.utilitygenerator.evaluator.ConfigurationElement;
import eu.melodic.upperware.utilitygenerator.utility_function.ArgumentConverter;
import eu.paasage.upperware.metamodel.cp.VariableType;
import eu.passage.upperware.commons.model.tools.metadata.CamelMetadata;
import io.github.cloudiator.rest.model.NodeCandidate;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.mariuszgromada.math.mxparser.Argument;

import java.util.Collection;
import java.util.stream.Collectors;

import static eu.melodic.upperware.utilitygenerator.node_candidates.NodeCandidateAttribute.createAttributeName;
import static eu.melodic.upperware.utilitygenerator.utility_function.ArgumentFactory.createArgument;
import static eu.passage.upperware.commons.model.tools.metadata.CamelMetadata.PRICE;

@Slf4j
@Getter
public class NodeCandidatesConverter extends ArgumentConverter {

    @Setter
    private Collection<NodeCandidateAttribute> oneNodeCandidateAttributes;
    private Collection<NodeCandidateAttribute> allNodeCandidatesListAttributes;
    private Collection<NodeCandidateAttribute> currentConfigAttributes;
    private NodeCandidates nodeCandidates;
    private Collection<VariableDTO> variables;

    public NodeCandidatesConverter(FromCamelModelExtractor fromCamelModelExtractor, NodeCandidates nodeCandidates, Collection<VariableDTO> variablesFromConstraintProblem) {
        super();

        this.allNodeCandidatesListAttributes = fromCamelModelExtractor.getListOfAttributesOfNodeCandidates();
        this.currentConfigAttributes = fromCamelModelExtractor.getCurrentConfigAttributesOfNodeCandidates();
        this.nodeCandidates = nodeCandidates;
        this.variables = variablesFromConstraintProblem;
        this.oneNodeCandidateAttributes = fromCamelModelExtractor.getAttributesOfNodeCandidates();
        if (this.oneNodeCandidateAttributes.isEmpty()) {
            this.oneNodeCandidateAttributes = createCostAttributesForAllComponents();
        }
        if (!allNodeCandidatesListAttributes.isEmpty()) {
            log.info("Attributes of list of Node Candidates: {}", allNodeCandidatesListAttributes);
            log.warn("Flag on candidates is not supported in Utility Generator");
        }
        log.info("Attributes of Node Candidates: {}", oneNodeCandidateAttributes);
    }

    @Override
    public Collection<Argument> convertToArguments(Collection<VariableValueDTO> solution, Collection<ConfigurationElement> newConfiguration) {
        return convertAttributes(this.oneNodeCandidateAttributes, newConfiguration);
    }

    public Collection<Argument> convertCurrentConfigAttributesOfNodeCandidates(Collection<ConfigurationElement> configuration) {
        if (configuration.isEmpty()) {
            return setDefaultValuesOfAttributes(this.currentConfigAttributes);
        } else {
            return convertAttributes(this.currentConfigAttributes, configuration);
        }
    }

    private Collection<NodeCandidateAttribute> createCostAttributesForAllComponents() {
        log.info("Creating default cost attributes for all components");
        return this.variables.stream()
                .filter(v -> VariableType.CARDINALITY.equals(v.getType()))
                .map(v -> new NodeCandidateAttribute(createAttributeName(v.getComponentId(), CamelMetadata.PRICE), v.getComponentId(), CamelMetadata.PRICE, false))
                .collect(Collectors.toList());
    }

    private Collection<Argument> setDefaultValuesOfAttributes(Collection<NodeCandidateAttribute> attributes) {
        log.info("It is the initial deployment. Setting values of attributes of Node Candidates to default values");
        return attributes.stream().map(a -> createArgument(a.getName(), 1.0)).collect(Collectors.toList());
    }

    private Collection<Argument> convertAttributes(Collection<NodeCandidateAttribute> nodeCandidateAttributes,
            Collection<ConfigurationElement> newConfiguration) {
        return nodeCandidateAttributes.stream()
                .map(a -> createArgument(a.getName(),
                        getAttributeValue(getNodeCandidate(newConfiguration, a.getComponentId()), a.getType())))
                .collect(Collectors.toList());
    }

    private NodeCandidate getNodeCandidate(Collection<ConfigurationElement> newConfiguration, String componentId) {
        return newConfiguration.stream()
                .filter(configurationElement -> configurationElement.getId().equals(componentId))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Configuration VariableValueDTO for component " + componentId + " is not found"))
                .getNodeCandidate();
    }

    private Number getAttributeValue(NodeCandidate nodeCandidate, CamelMetadata type) {
        if (PRICE.equals(type)) {
            if (NodeCandidate.NodeCandidateTypeEnum.FAAS.equals(nodeCandidate.getNodeCandidateType())) {
                return nodeCandidate.getPricePerInvocation();
            } else if (NodeCandidate.NodeCandidateTypeEnum.IAAS.equals(nodeCandidate.getNodeCandidateType())) {
                return nodeCandidate.getPrice();
            } else {
                throw new IllegalStateException("Type of Node Candidate: " + nodeCandidate.getNodeCandidateType() + "is not supported");
            }
        } else
            throw new IllegalArgumentException("Illegal type of Node Candidate attribute: " + type);
    }

}
