package groovy.eu.melodic.upperware.utilitygenerator

import eu.melodic.cache.NodeCandidates
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication
import eu.melodic.upperware.utilitygenerator.model.DTO.MetricDTO
import eu.melodic.upperware.utilitygenerator.model.DTO.VariableDTO
import eu.melodic.upperware.utilitygenerator.model.function.Element
import eu.melodic.upperware.utilitygenerator.model.function.IntElement
import eu.paasage.upperware.metamodel.cp.VariableType
import io.github.cloudiator.rest.model.NodeCandidate
import spock.lang.Specification

class UtilityGeneratorCRMTest extends Specification {


    Collection<MetricDTO> metrics = new ArrayList<>()
    NodeCandidates mockNodeCandidates = GroovyMock(NodeCandidates)

    def setup() {
        NodeCandidate nodeCandidate = GroovyMock(NodeCandidate)
        nodeCandidate.getPrice() >> 10.0
        List<NodeCandidate> list = new ArrayList<>()
        list.add(nodeCandidate)
        Map<Integer, List<NodeCandidate>> nodeCandidatesMap = new HashMap<>()
        nodeCandidatesMap.put(1, list)
        mockNodeCandidates.getCheapest(_, _, _) >> Optional.of(nodeCandidate)
        mockNodeCandidates.get(_) >> nodeCandidatesMap
    }

    def "CRMnew test"() {

        given:
        String cardinalityCRMName = "SmartDesignCardinality"
        String providerCRMName = "providerName"
        String componentCRMId = "SmartDesign"
        Collection<VariableDTO> variables = new ArrayList<>()
        variables.add(new VariableDTO(cardinalityCRMName, componentCRMId, VariableType.CARDINALITY))
        variables.add(new VariableDTO(providerCRMName, componentCRMId, VariableType.PROVIDER))

        Collection<Element> intSolution = new ArrayList<>()
        intSolution.add(new IntElement(cardinalityCRMName, 3))
        intSolution.add(new IntElement(providerCRMName, 2))

        Collection<Element> newConfiguration = new ArrayList<>()
        newConfiguration.add(new IntElement(cardinalityCRMName, 2))
        newConfiguration.add(new IntElement(providerCRMName, 1))


        String path = "/Users/mrozanska/CRMNewCamelModel.xmi"
        UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(path, true, variables, metrics, intSolution, mockNodeCandidates)

        when:
        double result = utilityGenerator.evaluate(newConfiguration)


        then:
        noExceptionThrown()
        result != 0
    }
}
