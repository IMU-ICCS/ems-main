package groovy.eu.melodic.upperware.utilitygenerator

import eu.melodic.cache.NodeCandidates
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.CPModelHandler
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.MetricDTO
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableDTO
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.solution.IntVariableValueDTO
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.solution.VariableValueDTO
import eu.melodic.upperware.utilitygenerator.properties.UtilityGeneratorProperties
import eu.paasage.upperware.metamodel.cp.VariableType
import io.github.cloudiator.rest.model.NodeCandidate
import spock.lang.Specification

class UtilityGeneratorCRMTest extends Specification {


    Collection<MetricDTO> metrics = new ArrayList<>()
    NodeCandidates mockNodeCandidates = GroovyMock(NodeCandidates)

    UtilityGeneratorProperties properties = new UtilityGeneratorProperties()

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

        properties.setUtilityGenerator(new UtilityGeneratorProperties.UtilityGenerator())
        properties.getUtilityGenerator().setDlmsControllerUrl("")
    }

    def "CRMnew test"() {

        given:
        String cardinalityCRMName = "SmartDesignCardinality"
        String providerCRMName = "providerName"
        String componentCRMId = "SmartDesign"
        Collection<VariableDTO> variables = new ArrayList<>()
        variables.add(new VariableDTO(cardinalityCRMName, componentCRMId, VariableType.CARDINALITY))
        variables.add(new VariableDTO(providerCRMName, componentCRMId, VariableType.PROVIDER))

        Collection<VariableValueDTO> intSolution = new ArrayList<>()
        intSolution.add(new IntVariableValueDTO(cardinalityCRMName, 3))
        intSolution.add(new IntVariableValueDTO(providerCRMName, 2))

        Collection<VariableValueDTO> newConfiguration = new ArrayList<>()
        newConfiguration.add(new IntVariableValueDTO(cardinalityCRMName, 2))
        newConfiguration.add(new IntVariableValueDTO(providerCRMName, 1))


        String path = "src/main/test/resources/CRM.xmi"
        cpModelHandler = new CPModelHandler(variables, metrics, intSolution, mockNodeCandidates)
        UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(path, true, cpModelHandler, properties)

        when:
        double result = utilityGenerator.evaluate(newConfiguration)


        then:
        noExceptionThrown()
        result != 0
    }


    def "CRM2 test" (){

        given:
        String cardinalityCRMName = "SmartDesignCardinality"
        String providerCRMName = "providerName"
        String componentCRMId = "SmartDesign"
        Collection<VariableDTO> variables = new ArrayList<>()
        variables.add(new VariableDTO(cardinalityCRMName, componentCRMId, VariableType.CARDINALITY))
        variables.add(new VariableDTO(providerCRMName, componentCRMId, VariableType.PROVIDER))

        Collection<VariableValueDTO> intSolution = new ArrayList<>()
        intSolution.add(new IntVariableValueDTO(cardinalityCRMName, 3))
        intSolution.add(new IntVariableValueDTO(providerCRMName, 2))

        Collection<VariableValueDTO> newConfiguration = new ArrayList<>()
        newConfiguration.add(new IntVariableValueDTO(cardinalityCRMName, 2))
        newConfiguration.add(new IntVariableValueDTO(providerCRMName, 1))


        cpModelHandler = new CPModelHandler(variables, metrics, intSolution, mockNodeCandidates)

        String path = "src/main/test/resources/CRM2.xmi"
        UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(path, true, cpModelHandler, properties)

        when:
        double result = utilityGenerator.evaluate(newConfiguration)


        then:
        noExceptionThrown()
        result != 0

    }
}
