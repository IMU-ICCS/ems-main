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
import eu.melodic.upperware.utilitygenerator.model.function.IntElement;
import eu.melodic.upperware.utilitygenerator.model.function.NodeCandidateAttribute;
import eu.melodic.upperware.utilitygenerator.model.function.RealElement;
import io.github.cloudiator.rest.model.NodeCandidate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static eu.melodic.upperware.utilitygenerator.evaluator.EvaluatingUtils.*;
import static eu.melodic.upperware.utilitygenerator.model.UtilityFunction.isInFormula;
import static java.util.Objects.isNull;

@Slf4j
@AllArgsConstructor
@Getter
public class NodeCandidatesConverter {


    private Collection<NodeCandidateAttribute> attributes;
    private NodeCandidates nodeCandidates;
    private List<VariableDTO> variables;
    
    public Collection<Element> convertAttributesOfNodeCandidates(Collection<IntElement> newConfigurationInt, Collection<RealElement> newConfigurationReal, String formula){

        Collection<ConfigurationElement> newConfiguration = convertSolutionToNodeCandidates(newConfigurationInt, newConfigurationReal);
        Collection<Element> arguments = new ArrayList<>();

        attributes.stream()
                .filter(a -> isInFormula(formula, a.getName()))
                .forEach(a -> arguments.add(new IntElement(a.getName(), getNodeCandidate(newConfiguration, a.getComponentId()).getPrice().intValue())));
        return arguments;
    }

    private Collection<ConfigurationElement> convertSolutionToNodeCandidates(Collection<IntElement> newConfigurationInt, Collection<RealElement> newConfigurationReal) {

        log.debug("Converting solution to Node Candidates");

        Collection<ConfigurationElement> newConfiguration = new ArrayList<>();
        Map<String, Integer> cardinalitiesForComponent = getCardinalitiesForComponent(newConfigurationInt, variables);

        for (String componentId : cardinalitiesForComponent.keySet()) {
            log.debug("Converting solution for component {}", componentId);
            int provider = getProviderValue(componentId, variables, newConfigurationInt);
            Predicate<NodeCandidate>[] requirementsForComponent = makePredicatesFromSolution(componentId, newConfigurationInt, newConfigurationReal, variables);
            NodeCandidate theCheapest = nodeCandidates.getCheapest(componentId, provider, requirementsForComponent).orElse(null);

            if (isNull(theCheapest)) {
                log.debug("Node Candidates for component {} with provider {} is not found", componentId, provider);
                return null;
            }
            log.debug("Got the cheapest Node Candidate from component {} with provider {}", componentId, provider);


            newConfiguration.add(new ConfigurationElement(componentId, theCheapest, cardinalitiesForComponent.get(componentId)));

        }
        return newConfiguration;
    }

    private static NodeCandidate getNodeCandidate(Collection<ConfigurationElement> newConfiguration, String componentId){
        return newConfiguration.stream()
                .filter(configurationElement -> configurationElement.getId().equals(componentId))
                .findAny()
                .orElseThrow(()-> new IllegalStateException("Configuration Element for component" + componentId +" is not found"))
                .getNodeCandidate();
    }
}
