/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */


import eu.melodic.cache.NodeCandidates
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication
import eu.melodic.upperware.utilitygenerator.model.DTO.IntMetricDTO
import eu.melodic.upperware.utilitygenerator.model.DTO.MetricDTO
import eu.melodic.upperware.utilitygenerator.model.DTO.VariableDTO
import eu.melodic.upperware.utilitygenerator.model.function.Element
import eu.melodic.upperware.utilitygenerator.model.function.IntElement
import eu.melodic.upperware.utilitygenerator.model.function.RealElement
import eu.paasage.upperware.metamodel.cp.VariableType
import io.github.cloudiator.rest.model.NodeCandidate
import spock.lang.Specification

class ParserTest extends Specification{

    def "FCR test"(){

        given:
        String cardinalityName = "AppCardinality"
        String providerName = "providerName"
        String metricName = "RT_AVG"
        String componentId = "Component_App"
        Collection<VariableDTO> variables = new ArrayList<>();
        variables.add(new VariableDTO(cardinalityName, componentId, VariableType.CARDINALITY))
        variables.add(new VariableDTO(providerName, componentId, VariableType.PROVIDER))

        Collection<MetricDTO> metrics = new ArrayList<>()
        metrics.add(new IntMetricDTO(metricName, 40))

        Collection<Element> deployedSolution = new ArrayList<>()
        deployedSolution.add(new IntElement(cardinalityName, 3))

        NodeCandidate nodeCandidate = GroovyMock(NodeCandidate)
        nodeCandidate.getPrice() >> 10.0
        NodeCandidates nc = GroovyMock(NodeCandidates)
        nc.getCheapest(_, _, _) >> Optional.of(nodeCandidate)


        Collection<IntElement> newConfiguration = new ArrayList<>()
        newConfiguration.add(new IntElement(cardinalityName, 2))
        newConfiguration.add(new IntElement(providerName, 1))

        String path = "/Users/mrozanska/FCRnew.xmi"
        UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication("cdo", path, variables, metrics, deployedSolution, nc)

        when:
        double result = utilityGenerator.evaluate(newConfiguration, new ArrayList<RealElement>())

        then:
        noExceptionThrown();

    }


    def "CRM test"(){

        given:
        String cardinalityName = "SmartDesignCardinality"
        String providerName = "providerName"
        String componentId = "SmartDesign"
        Collection<VariableDTO> variables = new ArrayList<>();
        variables.add(new VariableDTO(cardinalityName, componentId, VariableType.CARDINALITY))
        variables.add(new VariableDTO(providerName, componentId, VariableType.PROVIDER))

        Collection<MetricDTO> metrics = new ArrayList<>()
        Collection<Element> deployedSolution = new ArrayList<>()
        deployedSolution.add(new IntElement(cardinalityName, 3))

        NodeCandidate nodeCandidate = GroovyMock(NodeCandidate)
        nodeCandidate.getPrice() >> 10.0
        NodeCandidates nc = GroovyMock(NodeCandidates)
        nc.getCheapest(_, _, _) >> Optional.of(nodeCandidate)


        Collection<IntElement> newConfiguration = new ArrayList<>()
        newConfiguration.add(new IntElement(cardinalityName, 2))
        newConfiguration.add(new IntElement(providerName, 1))

        String path = "/Users/mrozanska/CRMNewCamelModel.xmi"
        UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication("cdo", path, variables, metrics, deployedSolution, nc)

        when:

        double result = utilityGenerator.evaluate(newConfiguration, new ArrayList<RealElement>())


        then:
        noExceptionThrown();


    }

}
