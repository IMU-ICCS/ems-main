package groovy.eu.melodic.upperware.utilitygenerator

import eu.melodic.cache.NodeCandidates
import eu.melodic.upperware.penaltycalculator.PenaltyFunctionProperties
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.IntVariableValueDTO
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties
import eu.paasage.upperware.security.authapi.token.JWTService
import io.github.cloudiator.rest.model.GeoLocation
import io.github.cloudiator.rest.model.Hardware
import io.github.cloudiator.rest.model.Location
import io.github.cloudiator.rest.model.NodeCandidate
import spock.lang.Specification

class UtilityGeneratorGenomTest extends Specification{


    NodeCandidates mockNodeCandidates = GroovyMock(NodeCandidates)
    MelodicSecurityProperties securityProperties = new MelodicSecurityProperties()
    PenaltyFunctionProperties properties

    JWTService jwtService

    String cardinalityName = "WorkerCardinality"
    String providerName = "provider_ComponentSparkWorker"
    String coresName = "WorkerCores"
    String latitudeName = "WorkerLatitude"
    String longitudeName = "WorkerLongitude"

    String path = "src/main/test/resources/genom/Genomnew.xmi"
    String cpModelPath = "src/main/test/resources/genom/GenomCPModel.xmi"
    String cpModelWithSolutionPath = "src/main/test/resources/genom/GenomCPModelWithSolution.xmi"


    Collection<VariableValueDTO> newConfiguration = new ArrayList<>()


    def setup() {
        NodeCandidate nodeCandidate = GroovyMock(NodeCandidate)
        nodeCandidate.getPrice() >> 10.0

        Location mockLocation = GroovyMock(Location)
        GeoLocation stubGeoLocation = new GeoLocation()
        stubGeoLocation.setLatitude(4.0 as Double)
        stubGeoLocation.setLongitude(50.0 as Double)
        mockLocation.getGeoLocation() >> stubGeoLocation
        nodeCandidate.getLocation() >> mockLocation

        Hardware mockHardware = GroovyMock(Hardware)
        mockHardware.getCores() >> 2
        nodeCandidate.getHardware() >> mockHardware


        nodeCandidate.getNodeCandidateType() >> NodeCandidate.NodeCandidateTypeEnum.IAAS
        List<NodeCandidate> list = new ArrayList<>()
        list.add(nodeCandidate)
        Map<Integer, List<NodeCandidate>> nodeCandidatesMap = new HashMap<>()
        nodeCandidatesMap.put(1, list)
        mockNodeCandidates.getCheapest(_, _, _) >> Optional.of(nodeCandidate)
        mockNodeCandidates.get(_) >> nodeCandidatesMap

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

    def "Genom initial deployment"() {

        given:
        newConfiguration.add(new IntVariableValueDTO(cardinalityName, 2))
        newConfiguration.add(new IntVariableValueDTO(providerName, 1))
        newConfiguration.add(new IntVariableValueDTO(coresName, 2))
        newConfiguration.add(new IntVariableValueDTO(latitudeName, 10))
        newConfiguration.add(new IntVariableValueDTO(longitudeName, 405 ))


        UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(path, cpModelPath, true, mockNodeCandidates, ugproperties,securityProperties, jwtService, properties)

        when:
        double result = utilityGenerator.evaluate(newConfiguration)

        then:
        noExceptionThrown()
        result != 0

    }

    def "Genom common reconfiguration test"() {

        given:

        newConfiguration.add(new IntVariableValueDTO(cardinalityName, 2))
        newConfiguration.add(new IntVariableValueDTO(providerName, 1))
        newConfiguration.add(new IntVariableValueDTO(coresName, 2))
        newConfiguration.add(new IntVariableValueDTO(latitudeName, 47))
        newConfiguration.add(new IntVariableValueDTO(longitudeName, 509))

        UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(path, cpModelWithSolutionPath, true, mockNodeCandidates, ugproperties, securityProperties, jwtService, properties)

        when:
        double result = utilityGenerator.evaluate(newConfiguration)

        then:
        noExceptionThrown()
        result != 0

    }






}
