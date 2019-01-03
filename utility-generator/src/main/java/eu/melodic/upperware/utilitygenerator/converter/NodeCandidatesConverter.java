/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.converter;

import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.utilitygenerator.model.ConfigurationElement;
import eu.melodic.upperware.utilitygenerator.model.DTO.VariableDTO;
import eu.melodic.upperware.utilitygenerator.model.function.Element;
import eu.melodic.upperware.utilitygenerator.model.function.NodeCandidateAttribute;
import eu.passage.upperware.commons.model.tools.metadata.CamelMetadata;
import io.github.cloudiator.rest.model.NodeCandidate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.stream.Collectors;

import static eu.melodic.upperware.utilitygenerator.model.function.ElementFactory.createElement;
import static eu.passage.upperware.commons.model.tools.metadata.CamelMetadata.PRICE;

@Slf4j
@AllArgsConstructor
@Getter
public class NodeCandidatesConverter extends ArgumentConverter {


    private Collection<NodeCandidateAttribute> oneNodeCandidateAttributes;
    private Collection<NodeCandidateAttribute> allNodeCandidatesListAttributes;
    private NodeCandidates nodeCandidates;
    private Collection<VariableDTO> variables;


    @Override
    public Collection<Element> convertToElements(Collection<Element> solution, Collection<ConfigurationElement> newConfiguration) {
        return convertAttributes(this.oneNodeCandidateAttributes, newConfiguration);
    }

    public static Collection<Element> convertCurrentConfigAttributesOfNodeCandidates(Collection<NodeCandidateAttribute> nodeCandidateAttributes,
            Collection<ConfigurationElement> configuration) {
        if (configuration.isEmpty()) {
            return setDefaultValuesOfAttributes(nodeCandidateAttributes);
        } else {
            return convertAttributes(nodeCandidateAttributes, configuration);
        }
    }

    public static NodeCandidateAttribute findAttributeForComponent(Collection<NodeCandidateAttribute> attributes, String componentId, CamelMetadata type) {
        return attributes.stream()
                .filter(a -> componentId.equals(a.getComponentId()) && type.equals(a.getType()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Attribute with type " + type + "for component " + componentId + " not found"));
    }

    private static Collection<Element> setDefaultValuesOfAttributes(Collection<NodeCandidateAttribute> attributes) {
        log.info("It is the initial deployment. Setting values of attributes of Node Candidates to default values");
        return attributes.stream().map(a -> createElement(a.getName(), 1.0)).collect(Collectors.toList());
    }

    private static Collection<Element> convertAttributes(Collection<NodeCandidateAttribute> nodeCandidateAttributes,
            Collection<ConfigurationElement> newConfiguration) {
        return nodeCandidateAttributes.stream()
                .map(a -> createElement(a.getName(),
                        getAttributeValue(getNodeCandidate(newConfiguration, a.getComponentId()), a.getType())))
                .collect(Collectors.toList());
    }

    private static NodeCandidate getNodeCandidate(Collection<ConfigurationElement> newConfiguration, String componentId) {
        return newConfiguration.stream()
                .filter(configurationElement -> configurationElement.getId().equals(componentId))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Configuration Element for component " + componentId + " is not found"))
                .getNodeCandidate();
    }

    private static Number getAttributeValue(NodeCandidate nodeCandidate, CamelMetadata type) {
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
