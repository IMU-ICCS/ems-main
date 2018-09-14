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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static eu.melodic.upperware.utilitygenerator.evaluator.EvaluatingUtils.*;
import static eu.melodic.upperware.utilitygenerator.model.function.ElementFactory.createElement;
import static java.util.Objects.isNull;

@Slf4j
@AllArgsConstructor
@Getter
public class NodeCandidatesConverter {


    private Collection<NodeCandidateAttribute> attributes;
    private Collection<NodeCandidateAttribute> listOfAttributes;
    private NodeCandidates nodeCandidates;
    private Collection<VariableDTO> variables;


    public Collection<Element> convertAttributesOfNodeCandidates(Collection<Element> solution) {
        Collection<ConfigurationElement> newConfiguration = convertSolutionToNodeCandidates(solution);

        return attributes.stream()
                .map(a -> createElement(a.getName(),
                        getAttributeOfNodeCandidate(getNodeCandidate(newConfiguration, a.getComponentId()), a.getType())))
                .collect(Collectors.toList());
    }

    public Collection<Element> convertCurrentConfigAttributesOfNodeCandidates(Collection<NodeCandidateAttribute> attributes,
            Collection<Element> deployedSolution) {
        Collection<ConfigurationElement> actualConfiguration = convertSolutionToNodeCandidates(deployedSolution);
        return attributes.stream()
                .map(a -> createElement(a.getName(),
                        getAttributeOfNodeCandidate(getNodeCandidate(actualConfiguration, a.getComponentId()), a.getType())))
                .collect(Collectors.toList());
    }

    public Collection<Element> setDefaultValuesOfAttributes(Collection<NodeCandidateAttribute> attributes) {
        return attributes.stream().map(a -> createElement(a.getName(), 1.0)).collect(Collectors.toList());
    }

    public Collection<Collection<Element>> convertListOfAttributesOfNodeCandidates() {
        return listOfAttributes.stream()
                .map(attribute -> getValuesForOneAttribute(nodeCandidates.get(attribute.getComponentId()), attribute))
                .collect(Collectors.toList());
    }

    //fixme - without getting from cache (if it is possible)
    public boolean doesNodeCandidateForSolutionExist(Collection<Element> solution) {
        return convertSolutionToNodeCandidates(solution) == null;
    }

    private Collection<ConfigurationElement> convertSolutionToNodeCandidates(Collection<Element> solution) {
        log.debug("Converting solution to Node Candidates");

        Collection<ConfigurationElement> newConfiguration = new ArrayList<>();
        Map<String, Integer> cardinalitiesForComponent = getCardinalitiesForComponent(solution, variables);

        for (String componentId : cardinalitiesForComponent.keySet()) {
            log.debug("Converting solution for component {}", componentId);
            int provider = getProviderValue(componentId, variables, solution);
            Predicate<NodeCandidate>[] requirementsForComponent = makePredicatesFromSolution(componentId, solution, variables);
            NodeCandidate theCheapest = nodeCandidates.getCheapest(componentId, provider, requirementsForComponent).orElse(null);

            if (isNull(theCheapest)) {
                log.debug("Node Candidates for component {} with provider {} is not found", componentId, provider);
                return null;
            }
            log.debug("Got the cheapest Node Candidate from component {} with provider {}", componentId, provider);

            //todo - it may be only a pair (Component, Node Candidate)
            newConfiguration.add(new ConfigurationElement(componentId, theCheapest, cardinalitiesForComponent.get(componentId)));
        }
        return newConfiguration;
    }

    private static NodeCandidate getNodeCandidate(Collection<ConfigurationElement> newConfiguration, String componentId) {
        return newConfiguration.stream()
                .filter(configurationElement -> configurationElement.getId().equals(componentId))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Configuration Element for component" + componentId + " is not found"))
                .getNodeCandidate();
    }

    private static Number getAttributeOfNodeCandidate(NodeCandidate nodeCandidate, CamelMetadata type) {
        Number result = null;
        switch (type) {
            case PRICE:
                result = nodeCandidate.getPrice();
                break;
        }
        if (result == null) {
            throw new IllegalArgumentException("Illegal type of Node Candidate attribute: " + type);
        }
        return result;
    }

    private Collection<Element> getValuesForOneAttribute(Map<Integer, List<NodeCandidate>> nodeCandidatesMap, NodeCandidateAttribute attribute) {
        Collection<Element> elements = new ArrayList<>();
        nodeCandidatesMap.values().forEach(list -> elements.addAll(getOneList(list, attribute)));
        return elements;
    }

    private Collection<Element> getOneList(List<NodeCandidate> list, NodeCandidateAttribute attribute) {
        return list.stream()
                .map(nc -> createElement(attribute.getName(), getAttributeOfNodeCandidate(nc, attribute.getType())))
                .collect(Collectors.toList());
    }

}
