
package groovy.eu.melodic.upperware.utilitygenerator

import eu.melodic.cache.NodeCandidates
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.IntVariableValueDTO
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties
import eu.paasage.upperware.security.authapi.token.JWTService
import org.activeeon.morphemic.model.NodeCandidate
import spock.lang.Specification

class WithoutMetricModelTest extends Specification{


    NodeCandidates mockNodeCandidates = GroovyMock(NodeCandidates)

    MelodicSecurityProperties securityProperties = new MelodicSecurityProperties()

    JWTService jwtService

    Collection<IntVariableValueDTO> newConfiguration = new ArrayList<>()
    Collection<IntVariableValueDTO> secondConfiguration = new ArrayList<>()

    String cardinalityApp = "cardinality_Component_App"
    String cardinalityDB = "cardinality_Component_DB"
    String providerApp = "provider_Component_App"
    String providerDB = "provider_Component_DB"


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



        newConfiguration.add(new IntVariableValueDTO(cardinalityApp, 2))
        newConfiguration.add(new IntVariableValueDTO(providerApp, 1))
        newConfiguration.add(new IntVariableValueDTO(cardinalityDB, 1))
        newConfiguration.add(new IntVariableValueDTO(providerDB, 0))

        secondConfiguration.add(new IntVariableValueDTO(cardinalityApp, 3))
        secondConfiguration.add(new IntVariableValueDTO(providerApp, 1))
        secondConfiguration.add(new IntVariableValueDTO(cardinalityDB, 1))
        secondConfiguration.add(new IntVariableValueDTO(providerDB, 0))

        jwtService = GroovyMock(JWTService)


    }

    def "Without metric model initial deployment test"() {

        given:

        String path = "src/main/test/resources/TwoComponentAppnew.xmi"
        String cpmodelPath = "src/main/test/resources/TwoComponentAppCPModel.xmi"
        UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(path, cpmodelPath, true, mockNodeCandidates, securityProperties, jwtService)

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
        String cpmodelPath = "src/main/test/resources/TwoComponentAppCPModelWithSolution.xmi"
        UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(path, cpmodelPath, true, mockNodeCandidates, securityProperties, jwtService)

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
