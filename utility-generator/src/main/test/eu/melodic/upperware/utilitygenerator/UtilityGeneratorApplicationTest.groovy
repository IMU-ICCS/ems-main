package eu.melodic.upperware.utilitygenerator

import eu.melodic.cache.NodeCandidates
import eu.melodic.upperware.utilitygenerator.model.DTO.IntMetricDTO
import eu.melodic.upperware.utilitygenerator.model.DTO.MetricDTO
import eu.melodic.upperware.utilitygenerator.model.DTO.VariableDTO
import eu.melodic.upperware.utilitygenerator.model.function.Element
import eu.melodic.upperware.utilitygenerator.model.function.IntElement
import eu.paasage.upperware.metamodel.cp.VariableType
import io.github.cloudiator.rest.model.NodeCandidate
import spock.lang.Specification

class UtilityGeneratorApplicationTest extends Specification{


    Collection<MetricDTO> metrics = new ArrayList<>()

    def "FCR test"(){

        given:
        String cardinalityName = "AppCardinality"
        String providerName = "providerName"
        String metricName = "RT_AVG"
        String componentId = "Component_App"
        Collection<VariableDTO> variables = new ArrayList<>();
        variables.add(new VariableDTO(cardinalityName, componentId, VariableType.CARDINALITY))
        variables.add(new VariableDTO(providerName, componentId, VariableType.PROVIDER))


        metrics.add(new IntMetricDTO(metricName, 40))


        Collection<Element> intSolution = new ArrayList<>()
        intSolution.add(new IntElement(cardinalityName, 3))

        NodeCandidate nodeCandidate = GroovyMock(NodeCandidate)
        nodeCandidate.getPrice() >> 10.0
        NodeCandidates nc = GroovyMock(NodeCandidates)
        nc.getCheapest(_, _, _) >> Optional.of(nodeCandidate)



        Collection<IntElement> newConfiguration = new ArrayList<>()
        newConfiguration.add(new IntElement(cardinalityName, 2))
        newConfiguration.add(new IntElement(providerName, 1))


        String path = "/Users/mrozanska/FCRnew.xmi"
        UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication("cdo", path, variables, metrics, intSolution, nc)

        when:
        double result = utilityGenerator.evaluate(newConfiguration)

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

        Collection<Element> intSolution = new ArrayList<>()
        intSolution.add(new IntElement(cardinalityName, 3))

        NodeCandidate nodeCandidate = GroovyMock(NodeCandidate)
        nodeCandidate.getPrice() >> 10.0
        NodeCandidates nc = GroovyMock(NodeCandidates)
        nc.getCheapest(_, _, _) >> Optional.of(nodeCandidate)


        Collection<Element> newConfiguration = new ArrayList<>()
        newConfiguration.add(new IntElement(cardinalityName, 2))
        newConfiguration.add(new IntElement(providerName, 1))


        String path = "/Users/mrozanska/CRMNewCamelModel.xmi"
        UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication("cdo", path, variables, metrics, intSolution, nc)

        when:

        double result = utilityGenerator.evaluate(newConfiguration)


        then:
        noExceptionThrown();


    }


}
