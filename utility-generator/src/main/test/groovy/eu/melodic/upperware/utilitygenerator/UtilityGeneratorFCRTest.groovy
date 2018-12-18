package groovy.eu.melodic.upperware.utilitygenerator

import eu.melodic.cache.NodeCandidates
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication
import eu.melodic.upperware.utilitygenerator.model.DTO.IntMetricDTO
import eu.melodic.upperware.utilitygenerator.model.DTO.MetricDTO
import eu.melodic.upperware.utilitygenerator.model.DTO.VariableDTO
import eu.melodic.upperware.utilitygenerator.model.function.Element
import eu.melodic.upperware.utilitygenerator.model.function.IntElement
import eu.melodic.upperware.utilitygenerator.properties.UtilityGeneratorProperties
import eu.paasage.upperware.metamodel.cp.VariableType
import io.github.cloudiator.rest.model.NodeCandidate
import spock.lang.Specification

class UtilityGeneratorFCRTest extends Specification{


    Collection<MetricDTO> metrics = new ArrayList<>()
    NodeCandidates mockNodeCandidates = GroovyMock(NodeCandidates)

    UtilityGeneratorProperties properties = new UtilityGeneratorProperties()

    String cardinalityName = "AppCardinality"
    String actCardinalityName = "AppActCardinality"

    String providerName = "providerName"
    String metricName = "RT_AVG"
    String componentId = "Component_App"
    String dbId = "Component_DB"
    String dbProviderName = "providerNameDB"
    String dbCardinalityName = "DBCardinality"

    String path = "src/main/test/resources/FCR.xmi"


    Collection<VariableDTO> variables = new ArrayList<>()
    Collection<Element> intSolution = new ArrayList<>()
    Collection<IntElement> newConfiguration = new ArrayList<>()

    def setup() {
        NodeCandidate nodeCandidate = GroovyMock(NodeCandidate)
        nodeCandidate.getPrice() >> 10.0
        nodeCandidate.getNodeCandidateType() >> NodeCandidate.NodeCandidateTypeEnum.IAAS
        List<NodeCandidate> list = new ArrayList<>()
        list.add(nodeCandidate)
        Map<Integer, List<NodeCandidate>> nodeCandidatesMap = new HashMap<>()
        nodeCandidatesMap.put(1, list)
        mockNodeCandidates.getCheapest(_, _, _) >> Optional.of(nodeCandidate)
        mockNodeCandidates.get(_) >> nodeCandidatesMap


        variables.add(new VariableDTO(cardinalityName, componentId, VariableType.CARDINALITY))
        variables.add(new VariableDTO(providerName, componentId, VariableType.PROVIDER))
        variables.add(new VariableDTO(dbProviderName, dbId, VariableType.PROVIDER))
        variables.add(new VariableDTO(dbCardinalityName, dbId, VariableType.CARDINALITY))


        intSolution.add(new IntElement(cardinalityName, 3))
        intSolution.add(new IntElement(providerName, 1))
        intSolution.add(new IntElement(dbCardinalityName, 1))
        intSolution.add(new IntElement(dbProviderName, 0))
        metrics.add(new IntMetricDTO(metricName, 40))
        metrics.add(new IntMetricDTO(actCardinalityName, 1))

        properties.setUtilityGenerator(new UtilityGeneratorProperties.UtilityGenerator())
        properties.getUtilityGenerator().setDlmsControllerUrl("")
    }

    def "FCR initial deployment"() {

        given:
        newConfiguration.add(new IntElement(cardinalityName, 2))
        newConfiguration.add(new IntElement(providerName, 1))
        newConfiguration.add(new IntElement(dbCardinalityName, 1))
        newConfiguration.add(new IntElement(dbProviderName, 0))

        UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(path, true, variables, metrics, properties, mockNodeCandidates)

        when:
        double result = utilityGenerator.evaluate(newConfiguration)

        then:
        noExceptionThrown()
        result != 0

    }

    def "FCRnew common reconfiguration test"() {

        given:

        newConfiguration.add(new IntElement(cardinalityName, 2))
        newConfiguration.add(new IntElement(providerName, 1))
        newConfiguration.add(new IntElement(dbCardinalityName, 1))
        newConfiguration.add(new IntElement(dbProviderName, 0))

        UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(path, true, variables, metrics, intSolution, properties, mockNodeCandidates)

        when:
        double result = utilityGenerator.evaluate(newConfiguration)

        then:
        noExceptionThrown()
        result != 0

    }

    def "FCRnew unmoveable component is moved"() {

        given:

        newConfiguration.add(new IntElement(cardinalityName, 2))
        newConfiguration.add(new IntElement(providerName, 1))
        newConfiguration.add(new IntElement(dbCardinalityName, 2))
        newConfiguration.add(new IntElement(dbProviderName, 0))


        UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(path, true, variables, metrics, intSolution, properties, mockNodeCandidates)

        when:
        double result = utilityGenerator.evaluate(newConfiguration)

        then:
        noExceptionThrown()
        result == 0

    }

    def "FCR without unmoveable component - test"() {

        given:
        newConfiguration.add(new IntElement(cardinalityName, 2))
        newConfiguration.add(new IntElement(providerName, 1))
        newConfiguration.add(new IntElement(dbCardinalityName, 2))
        newConfiguration.add(new IntElement(dbProviderName, 0))

        path = "src/main/test/resources/FCRWithoutUnmoveable.xmi"
        UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(path, true, variables, metrics, intSolution, properties, mockNodeCandidates)

        when:
        double result = utilityGenerator.evaluate(newConfiguration)

        then:
        noExceptionThrown()
        result != 0
    }

    def "FCR with dlms utility - test"() {

        given:
        newConfiguration.add(new IntElement(cardinalityName, 2))
        newConfiguration.add(new IntElement(providerName, 1))
        newConfiguration.add(new IntElement(dbCardinalityName, 1))
        newConfiguration.add(new IntElement(dbProviderName, 0))

        path = "src/main/test/resources/FCRwithDLMS.xmi"

        UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(path, true, variables, metrics, intSolution, properties, mockNodeCandidates)

        when:
        double result = utilityGenerator.evaluate(newConfiguration)

        then:
        noExceptionThrown()
        result != 0
    }


}
