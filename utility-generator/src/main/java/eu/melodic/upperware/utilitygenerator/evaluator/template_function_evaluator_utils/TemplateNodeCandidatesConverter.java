package eu.melodic.upperware.utilitygenerator.evaluator.template_function_evaluator_utils;

import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableDTO;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.melodic.upperware.utilitygenerator.evaluator.ConfigurationElement;
import eu.melodic.upperware.utilitygenerator.node_candidates.NodeCandidateAttribute;
import eu.melodic.upperware.utilitygenerator.utility_function.ArgumentConverter;
import eu.paasage.upperware.metamodel.cp.VariableType;
import eu.passage.upperware.commons.model.tools.metadata.CamelMetadata;
import org.activeeon.morphemic.model.NodeCandidate;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.mariuszgromada.math.mxparser.Argument;

import java.util.Collection;
import java.util.stream.Collectors;

import static eu.melodic.upperware.utilitygenerator.node_candidates.NodeCandidateAttribute.createAttributeName;
import static eu.melodic.upperware.utilitygenerator.utility_function.ArgumentFactory.createArgument;
import static eu.passage.upperware.commons.model.tools.metadata.CamelMetadata.PRICE;

@Slf4j
public class TemplateNodeCandidatesConverter implements ArgumentConverter {

    @Getter
    private NodeCandidates nodeCandidates;
    private Collection<VariableDTO> variables;
    private Collection<NodeCandidateAttribute> priceVariables;

    public TemplateNodeCandidatesConverter(NodeCandidates nodeCandidates, Collection<VariableDTO> variablesFromConstraintProblem) {
        this.nodeCandidates = nodeCandidates;
        this.variables = variablesFromConstraintProblem;
        this.priceVariables = createCostAttributesForAllComponents();
    }

    @Override
    public Collection<Argument> convertToArguments(Collection<VariableValueDTO> solution, Collection<ConfigurationElement> newConfiguration) {
        return convertAttributes(this.priceVariables, newConfiguration);
    }

    private Collection<NodeCandidateAttribute> createCostAttributesForAllComponents() {
        log.info("Creating default cost attributes for all components");
        return this.variables.stream()
                .filter(v -> VariableType.CARDINALITY.equals(v.getType()))
                .map(v -> new NodeCandidateAttribute(createAttributeName(v.getComponentId(), CamelMetadata.PRICE), v.getComponentId(), CamelMetadata.PRICE, false))
                .collect(Collectors.toList());
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
                .orElseThrow(() -> new IllegalStateException("Configuration Element for component " + componentId + " is not found" + newConfiguration.toString()))
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
