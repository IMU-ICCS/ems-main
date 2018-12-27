package groovy.eu.melodic.upperware.utilitygenerator

import eu.melodic.cache.NodeCandidates
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.CPModelHandler
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.IntMetricDTO
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.MetricDTO
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableDTO
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.solution.IntVariableValueDTO
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.solution.VariableValueDTO
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
    Collection<VariableValueDTO> intSolution = new ArrayList<>()
    Collection<IntVariableValueDTO> newConfiguration = new ArrayList<>()

    CPModelHandler cpModelHandler, cpModelHandlerInit

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


        intSolution.add(new IntVariableValueDTO(cardinalityName, 2))
        intSolution.add(new IntVariableValueDTO(providerName, 1))
        intSolution.add(new IntVariableValueDTO(dbCardinalityName, 1))
        intSolution.add(new IntVariableValueDTO(dbProviderName, 0))
        metrics.add(new IntMetricDTO(metricName, 40))
        metrics.add(new IntMetricDTO(actCardinalityName, 1))

        properties.setUtilityGenerator(new UtilityGeneratorProperties.UtilityGenerator())
        properties.getUtilityGenerator().setDlmsControllerUrl("")

        cpModelHandler = new CPModelHandler(variables, metrics, intSolution, mockNodeCandidates)
        cpModelHandlerInit = new CPModelHandler(variables, metrics, mockNodeCandidates)
    }

    def "FCR initial deployment"() {

        given:
        newConfiguration.add(new IntVariableValueDTO(cardinalityName, 2))
        newConfiguration.add(new IntVariableValueDTO(providerName, 1))
        newConfiguration.add(new IntVariableValueDTO(dbCardinalityName, 1))
        newConfiguration.add(new IntVariableValueDTO(dbProviderName, 0))

        UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(path, true, cpModelHandlerInit, properties)

        when:
        double result = utilityGenerator.evaluate(newConfiguration)

        then:
        noExceptionThrown()
        result != 0

    }

    def "FCRnew common reconfiguration test"() {

        given:

        newConfiguration.add(new IntVariableValueDTO(cardinalityName, 2))
        newConfiguration.add(new IntVariableValueDTO(providerName, 1))
        newConfiguration.add(new IntVariableValueDTO(dbCardinalityName, 1))
        newConfiguration.add(new IntVariableValueDTO(dbProviderName, 0))

        UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(path, true, cpModelHandler, properties)

        when:
        double result = utilityGenerator.evaluate(newConfiguration)

        then:
        noExceptionThrown()
        result != 0

    }

    def "FCRnew unmoveable component is moved"() {

        given:

        newConfiguration.add(new IntVariableValueDTO(cardinalityName, 2))
        newConfiguration.add(new IntVariableValueDTO(providerName, 1))
        newConfiguration.add(new IntVariableValueDTO(dbCardinalityName, 2))
        newConfiguration.add(new IntVariableValueDTO(dbProviderName, 0))


        UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(path, true, cpModelHandler, properties)

        when:
        double result = utilityGenerator.evaluate(newConfiguration)

        then:
        noExceptionThrown()
        result == 0

    }

    def "FCR without unmoveable component - test"() {

        given:
        newConfiguration.add(new IntVariableValueDTO(cardinalityName, 2))
        newConfiguration.add(new IntVariableValueDTO(providerName, 1))
        newConfiguration.add(new IntVariableValueDTO(dbCardinalityName, 2))
        newConfiguration.add(new IntVariableValueDTO(dbProviderName, 0))

        path = "src/main/test/resources/FCRWithoutUnmoveable.xmi"
        UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(path, true, cpModelHandler, properties)

        when:
        double result = utilityGenerator.evaluate(newConfiguration)

        then:
        noExceptionThrown()
        result != 0
    }

    def "FCR with dlms utility - test"() {

        given:
        newConfiguration.add(new IntVariableValueDTO(cardinalityName, 2))
        newConfiguration.add(new IntVariableValueDTO(providerName, 1))
        newConfiguration.add(new IntVariableValueDTO(dbCardinalityName, 1))
        newConfiguration.add(new IntVariableValueDTO(dbProviderName, 0))

        path = "src/main/test/resources/FCRwithDLMS.xmi"

        UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(path, true, cpModelHandler, properties)

        when:
        double result = utilityGenerator.evaluate(newConfiguration)

        then:
        noExceptionThrown()
        result != 0
    }


}
