package groovy.eu.melodic.upperware.utilitygenerator

import eu.melodic.cache.NodeCandidates
import eu.melodic.upperware.penaltycalculator.PenaltyFunctionProperties
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.IntVariableValueDTO
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties
import eu.paasage.upperware.security.authapi.token.JWTService
import io.github.cloudiator.rest.model.NodeCandidate
import spock.lang.Specification

class WithoutMetricModelTest extends Specification{


    NodeCandidates mockNodeCandidates = GroovyMock(NodeCandidates)

    MelodicSecurityProperties securityProperties = new MelodicSecurityProperties()
    PenaltyFunctionProperties properties

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

        properties = GroovyMock(PenaltyFunctionProperties)
        Map<String, String> startupTimes = new HashMap<String, String>()
        startupTimes.put("t1.micro", "50")
        startupTimes.put("t1.small", "100")
        startupTimes.put("t1.xlarge", "120")
        startupTimes.put("t1.medium", "110")
        startupTimes.put("t1.xxlarge", "130")
        startupTimes.put("m1.tiny", "55")
        startupTimes.put("m1.small", "79")
        startupTimes.put("m1.medium", "88")
        startupTimes.put("m1.large", "132")
        startupTimes.put("m1.xlarge", "140")
        startupTimes.put("t1.large", "110")
        properties.getStartupTimes() >> startupTimes
        properties.getStateInfo() >>"1,0.6,0.5;1,1.7,160;4,7.5,850;8,15,1690;7,17.1,420;5,2,350;1,0.5,0.5;1,2.048,10;2,4.096,10;4,8.192,20;8,16.384,40"
        properties.getPort() >> 1234
        properties.getHost() >> "memcachehost"

    }

    def "Without metric model initial deployment test"() {

        given:

        String path = "src/main/test/resources/TwoComponentAppnew.xmi"
        String cpmodelPath = "src/main/test/resources/TwoComponentAppCPModel.xmi"
        UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(path, cpmodelPath, true, mockNodeCandidates, securityProperties, jwtService, properties)

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
        UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(path, cpmodelPath, true, mockNodeCandidates, securityProperties, jwtService, properties)

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
