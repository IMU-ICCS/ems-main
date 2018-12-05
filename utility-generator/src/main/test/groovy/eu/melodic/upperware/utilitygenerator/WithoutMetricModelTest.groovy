package groovy.eu.melodic.upperware.utilitygenerator

import eu.melodic.cache.NodeCandidates
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication
import eu.melodic.upperware.utilitygenerator.model.DTO.VariableDTO
import eu.melodic.upperware.utilitygenerator.model.function.Element
import eu.melodic.upperware.utilitygenerator.model.function.IntElement
import eu.melodic.upperware.utilitygenerator.properties.UtilityGeneratorProperties
import eu.paasage.upperware.metamodel.cp.VariableType
import io.github.cloudiator.rest.model.NodeCandidate
import spock.lang.Specification

class WithoutMetricModelTest extends Specification{


    NodeCandidates mockNodeCandidates = GroovyMock(NodeCandidates)

    UtilityGeneratorProperties properties = new UtilityGeneratorProperties()

    Collection<IntElement> newConfiguration = new ArrayList<>()
    Collection<IntElement> secondConfiguration = new ArrayList<>()
    Collection<VariableDTO> variables = new ArrayList<>()
    Collection<Element> intSolution = new ArrayList<>()

    String componentAppId = "Component_App"
    String componentDBId = "Component_DB"

    String cardinalityApp = "cardinality_Component_App"
    String cardinalityDB = "cardinality_Component_DB"

    String providerApp = "provider_Component_App"
    String providerDB = "provider_Component_DB"



    def setup() {
        NodeCandidate nodeCandidate = GroovyMock(NodeCandidate)
        nodeCandidate.getPrice() >> 10.0
        List<NodeCandidate> list = new ArrayList<>()
        list.add(nodeCandidate)
        Map<Integer, List<NodeCandidate>> nodeCandidatesMap = new HashMap<>()
        nodeCandidatesMap.put(1, list)
        mockNodeCandidates.getCheapest(_, _, _) >> Optional.of(nodeCandidate)
        mockNodeCandidates.get(_) >> nodeCandidatesMap

        properties.setUtilityGenerator(new UtilityGeneratorProperties.UtilityGenerator())
        properties.getUtilityGenerator().setDlmsControllerUrl("")


        newConfiguration.add(new IntElement(cardinalityApp, 2))
        newConfiguration.add(new IntElement(providerApp, 1))
        newConfiguration.add(new IntElement(cardinalityDB, 1))
        newConfiguration.add(new IntElement(providerDB, 0))

        secondConfiguration.add(new IntElement(cardinalityApp, 3))
        secondConfiguration.add(new IntElement(providerApp, 1))
        secondConfiguration.add(new IntElement(cardinalityDB, 1))
        secondConfiguration.add(new IntElement(providerDB, 0))

        variables.add(new VariableDTO(cardinalityApp, componentAppId, VariableType.CARDINALITY))
        variables.add(new VariableDTO(cardinalityDB, componentDBId, VariableType.CARDINALITY))
        variables.add(new VariableDTO(providerApp, componentAppId, VariableType.PROVIDER))
        variables.add(new VariableDTO(providerDB, componentDBId, VariableType.PROVIDER))


        intSolution.add(new IntElement(cardinalityApp, 3))
        intSolution.add(new IntElement(providerApp, 1))
        intSolution.add(new IntElement(cardinalityDB, 1))
        intSolution.add(new IntElement(providerDB, 0))
    }

    def "Without metric model initial deployment test"() {

        given:

        String path = "src/main/test/resources/TwoComponentAppnew.xmi"
        UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(path, true, variables, Collections.emptyList(), properties, mockNodeCandidates)

        when:
        double result = utilityGenerator.evaluate(newConfiguration)
        double result1 = utilityGenerator.evaluate(secondConfiguration)

        then:
        noExceptionThrown()
        result != 0
        result1 != 0
        result1<result
    }


    def "Without metric model reconfiguration"() {

        given:

        String path = "src/main/test/resources/TwoComponentAppnew.xmi"
        UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(path, true, variables, Collections.emptyList(), intSolution, properties, mockNodeCandidates)

        when:
        double result = utilityGenerator.evaluate(newConfiguration)
        double result1 = utilityGenerator.evaluate(secondConfiguration)

        then:
        noExceptionThrown()
        result != 0
        result1 != 0
        result1<result
    }

}
