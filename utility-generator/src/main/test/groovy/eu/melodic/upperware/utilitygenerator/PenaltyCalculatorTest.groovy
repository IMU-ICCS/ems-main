package groovy.eu.melodic.upperware.utilitygenerator

import eu.melodic.upperware.penaltycalculator.PenaltyConfigurationElement
import eu.melodic.upperware.penaltycalculator.PenaltyFunction
import eu.melodic.upperware.penaltycalculator.PenaltyFunctionProperties
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties
import eu.paasage.upperware.security.authapi.token.JWTService
import io.github.cloudiator.rest.model.Hardware
import io.github.cloudiator.rest.model.Location
import io.github.cloudiator.rest.model.NodeCandidate
import spock.lang.Specification

class PenaltyCalculatorTest extends Specification{



    Collection<PenaltyConfigurationElement> actualConfiguration = new ArrayList<>()
    Collection<PenaltyConfigurationElement> proposedConfiguration = new ArrayList<>()

    NodeCandidate smallNC
    NodeCandidate mediumNC

    PenaltyFunctionProperties properties
    MelodicSecurityProperties securityProperties = new MelodicSecurityProperties()
    JWTService jwtService



    def setup() {
        smallNC = new NodeCandidate();
        smallNC.setNodeCandidateType(NodeCandidate.NodeCandidateTypeEnum.IAAS)
        Hardware nCSmallHardware = new Hardware();
        nCSmallHardware.setCores(1);
        nCSmallHardware.setRam(10);
        nCSmallHardware.setDisk(100);
        nCSmallHardware.setLocation(GroovyMock(Location))
        nCSmallHardware.setProviderId("id");
        nCSmallHardware.setName("t1.small")
        smallNC.setHardware(nCSmallHardware);
        smallNC.setPrice(2.0)
        smallNC.setId("t1.small")


        mediumNC = new NodeCandidate();
        mediumNC.setNodeCandidateType(NodeCandidate.NodeCandidateTypeEnum.IAAS)
        Hardware nCmediumHardware = new Hardware();
        nCmediumHardware.setCores(2);
        nCmediumHardware.setRam(20);
        nCmediumHardware.setDisk(500);
        nCmediumHardware.setLocation(GroovyMock(Location))
        nCmediumHardware.setProviderId("id");
        nCmediumHardware.setName("t1.medium")
        mediumNC.setHardware(nCSmallHardware);
        mediumNC.setPrice(5.0)
        mediumNC.setId("t1.medium")

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

        jwtService = GroovyMock(JWTService)


    }

    def "initial test"() {

        given:
        proposedConfiguration.add(new PenaltyConfigurationElement("first", smallNC, 2))
        actualConfiguration.add(new PenaltyConfigurationElement("first", smallNC, 1))

        PenaltyFunction function = new PenaltyFunction()
        function.properties = properties


        when:

        double result = function.evaluatePenaltyFunction(actualConfiguration, proposedConfiguration)

        then:
        noExceptionThrown()
        System.out.println("result = " + result)

    }
}