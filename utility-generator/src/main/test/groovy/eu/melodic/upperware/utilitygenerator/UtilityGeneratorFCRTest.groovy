package groovy.eu.melodic.upperware.utilitygenerator

import eu.melodic.cache.NodeCandidates
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.IntVariableValueDTO
import eu.melodic.upperware.utilitygenerator.properties.UtilityGeneratorProperties
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties
import eu.paasage.upperware.security.authapi.token.JWTService
import io.github.cloudiator.rest.model.Hardware
import io.github.cloudiator.rest.model.NodeCandidate
import spock.lang.Specification

class UtilityGeneratorFCRTest extends Specification{


    NodeCandidates mockNodeCandidates = GroovyMock(NodeCandidates)
    UtilityGeneratorProperties utilityGeneratorProperties = new UtilityGeneratorProperties()
    MelodicSecurityProperties securityProperties = new MelodicSecurityProperties()
    JWTService jwtService

    String cardinalityName = "AppCardinality"
    String providerName = "provider_Component_App"

    String dbProviderName = "provider_Component_DB"
    String dbCardinalityName = "cardinality_Component_DB"

    String path = "src/main/test/resources/FCR.xmi"
    String cpModelPath = "src/main/test/resources/FCRCPModelWithSolution.xmi"


    Collection<IntVariableValueDTO> newConfiguration = new ArrayList<>()


    def setup() {
        NodeCandidate nodeCandidate = GroovyMock(NodeCandidate)
        nodeCandidate.getPrice() >> 10.0
        nodeCandidate.getNodeCandidateType() >> NodeCandidate.NodeCandidateTypeEnum.IAAS
        nodeCandidate.getId() >> "t1.micro"

        Hardware nodeCandidateHardware = GroovyMock(Hardware)
        nodeCandidateHardware.getCores() >> 2
        nodeCandidateHardware.getName() >> "t1.micro"
        nodeCandidateHardware.getRam() >> 1000
        nodeCandidateHardware.getDisk() >> 10
        nodeCandidate.getHardware() >> nodeCandidateHardware


        NodeCandidate nodeCandidate2 = GroovyMock(NodeCandidate)
        nodeCandidate2.getPrice() >> 10.0
        nodeCandidate2.getNodeCandidateType() >> NodeCandidate.NodeCandidateTypeEnum.IAAS
        nodeCandidate2.getId() >> "t1.micro"

        Hardware nodeCandidateHardware2 = GroovyMock(Hardware)
        nodeCandidateHardware2.getCores() >> 2
        nodeCandidateHardware2.getName() >> "t1.small"
        nodeCandidateHardware2.getRam() >> 1000
        nodeCandidateHardware2.getDisk() >> 10
        nodeCandidate2.getHardware() >> nodeCandidateHardware2

        List<NodeCandidate> list = new ArrayList<>()
        list.add(nodeCandidate)
        list.add(nodeCandidate2)
        Map<Integer, List<NodeCandidate>> nodeCandidatesMap = new HashMap<>()
        nodeCandidatesMap.put(1, list)
        mockNodeCandidates.getCheapest(_, 1, _) >> Optional.of(nodeCandidate2)
        mockNodeCandidates.getCheapest(_,0, _) >> Optional.of(nodeCandidate)
        mockNodeCandidates.getCheapest(_, _, _) >> Optional.of(nodeCandidate)
        mockNodeCandidates.get(_) >> nodeCandidatesMap

        utilityGeneratorProperties.setUtilityGenerator(new UtilityGeneratorProperties.UtilityGenerator())
        utilityGeneratorProperties.getUtilityGenerator().setDlmsControllerUrl("")

        jwtService = GroovyMock(JWTService)


    }

    def "FCR initial deployment"() {

        given:
        newConfiguration.add(new IntVariableValueDTO(cardinalityName, 2))
        newConfiguration.add(new IntVariableValueDTO(providerName, 1))
        newConfiguration.add(new IntVariableValueDTO(dbCardinalityName, 1))
        newConfiguration.add(new IntVariableValueDTO(dbProviderName, 0))

        UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(path, "src/main/test/resources/FCRCPModel.xmi", true, mockNodeCandidates, utilityGeneratorProperties, securityProperties, jwtService,)

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

        UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(path, cpModelPath, true, mockNodeCandidates, utilityGeneratorProperties, securityProperties, jwtService)

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


        UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(path, cpModelPath, true, mockNodeCandidates, utilityGeneratorProperties, securityProperties, jwtService)

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
        UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(path, cpModelPath,true, mockNodeCandidates, utilityGeneratorProperties, securityProperties, jwtService)

        when:
        double result = utilityGenerator.evaluate(newConfiguration)

        then:
        noExceptionThrown()
        result != 0
    }

    def "FCR without optimisation requirement - test"() {

        given:
        newConfiguration.add(new IntVariableValueDTO(cardinalityName, 2))
        newConfiguration.add(new IntVariableValueDTO(providerName, 1))
        newConfiguration.add(new IntVariableValueDTO(dbCardinalityName, 1))
        newConfiguration.add(new IntVariableValueDTO(dbProviderName, 0))
        newConfiguration.add(new IntVariableValueDTO("cardinality_Component_LB", 1))
        newConfiguration.add(new IntVariableValueDTO("provider_Component_LB", 2))


        path = "src/main/test/resources/FCRwithoutOptimisationRequirement.xmi"
        UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(path, cpModelPath,true, mockNodeCandidates, utilityGeneratorProperties, securityProperties, jwtService)

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
//toupdate
        UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(path, cpModelPath, true, mockNodeCandidates, utilityGeneratorProperties, securityProperties, jwtService)

        when:
        double result = utilityGenerator.evaluate(newConfiguration)

        then:
        noExceptionThrown()
        result != 0
    }


    def "FCR with penalty - test"() {

        given:
        newConfiguration.add(new IntVariableValueDTO(cardinalityName, 2))
        newConfiguration.add(new IntVariableValueDTO(providerName, 0))
        newConfiguration.add(new IntVariableValueDTO(dbCardinalityName, 1))
        newConfiguration.add(new IntVariableValueDTO(dbProviderName, 1))

        path = "src/main/test/resources/FCRwithPenalty.xmi"
        UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(path, cpModelPath, true, mockNodeCandidates, utilityGeneratorProperties, securityProperties, jwtService)

        when:
        double result = utilityGenerator.evaluate(newConfiguration)
        System.out.println("result = " + result)

        then:
        noExceptionThrown()
        result != 0
    }


}
