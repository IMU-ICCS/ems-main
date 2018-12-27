package groovy.eu.melodic.upperware.utilitygenerator

import eu.melodic.cache.NodeCandidates
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.CPModelHandler
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableDTO
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.solution.IntVariableValueDTO
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.solution.VariableValueDTO
import eu.melodic.upperware.utilitygenerator.properties.UtilityGeneratorProperties
import eu.paasage.upperware.metamodel.cp.VariableType
import io.github.cloudiator.rest.model.NodeCandidate
import spock.lang.Specification

class WithoutMetricModelTest extends Specification{


    NodeCandidates mockNodeCandidates = GroovyMock(NodeCandidates)

    UtilityGeneratorProperties properties = new UtilityGeneratorProperties()

    Collection<IntVariableValueDTO> newConfiguration = new ArrayList<>()
    Collection<IntVariableValueDTO> secondConfiguration = new ArrayList<>()
    Collection<VariableDTO> variables = new ArrayList<>()
    Collection<VariableValueDTO> intSolution = new ArrayList<>()

    String componentAppId = "Component_App"
    String componentDBId = "Component_DB"

    String cardinalityApp = "cardinality_Component_App"
    String cardinalityDB = "cardinality_Component_DB"

    String providerApp = "provider_Component_App"
    String providerDB = "provider_Component_DB"

    CPModelHandler handler, initHandler



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

        properties.setUtilityGenerator(new UtilityGeneratorProperties.UtilityGenerator())
        properties.getUtilityGenerator().setDlmsControllerUrl("")


        newConfiguration.add(new IntVariableValueDTO(cardinalityApp, 2))
        newConfiguration.add(new IntVariableValueDTO(providerApp, 1))
        newConfiguration.add(new IntVariableValueDTO(cardinalityDB, 1))
        newConfiguration.add(new IntVariableValueDTO(providerDB, 0))

        secondConfiguration.add(new IntVariableValueDTO(cardinalityApp, 3))
        secondConfiguration.add(new IntVariableValueDTO(providerApp, 1))
        secondConfiguration.add(new IntVariableValueDTO(cardinalityDB, 1))
        secondConfiguration.add(new IntVariableValueDTO(providerDB, 0))

        variables.add(new VariableDTO(cardinalityApp, componentAppId, VariableType.CARDINALITY))
        variables.add(new VariableDTO(cardinalityDB, componentDBId, VariableType.CARDINALITY))
        variables.add(new VariableDTO(providerApp, componentAppId, VariableType.PROVIDER))
        variables.add(new VariableDTO(providerDB, componentDBId, VariableType.PROVIDER))


        intSolution.add(new IntVariableValueDTO(cardinalityApp, 3))
        intSolution.add(new IntVariableValueDTO(providerApp, 1))
        intSolution.add(new IntVariableValueDTO(cardinalityDB, 1))
        intSolution.add(new IntVariableValueDTO(providerDB, 0))

        initHandler = new CPModelHandler(variables, Collections.emptyList(), mockNodeCandidates)
        handler = new CPModelHandler(variables, Collections.emptyList(), intSolution, mockNodeCandidates)

    }

    def "Without metric model initial deployment test"() {

        given:

        String path = "src/main/test/resources/TwoComponentAppnew.xmi"
        UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(path, true, initHandler, properties)

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
        UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(path, true, initHandler, properties)

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
