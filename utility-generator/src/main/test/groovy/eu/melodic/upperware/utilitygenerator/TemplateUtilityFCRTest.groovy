package eu.melodic.upperware.utilitygenerator

import eu.melodic.cache.NodeCandidates
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.IntVariableValueDTO
import eu.melodic.upperware.utilitygenerator.properties.UtilityGeneratorProperties
import eu.melodic.upperware.utilitygenerator.utility_function.utility_templates_provider.TemplateProvider
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties
import eu.paasage.upperware.security.authapi.token.JWTService
import io.github.cloudiator.rest.model.Hardware
import io.github.cloudiator.rest.model.NodeCandidate
import java.util.AbstractMap;
import lombok.extern.slf4j.Slf4j
import spock.lang.Specification
@Slf4j
class TemplateUtilityFCRTest extends Specification{


    NodeCandidates mockNodeCandidates = GroovyMock(NodeCandidates)
    UtilityGeneratorProperties utilityGeneratorProperties = new UtilityGeneratorProperties()
    MelodicSecurityProperties securityProperties = new MelodicSecurityProperties()
    JWTService jwtService

    String cardinalityName = "AppCardinality"
    String providerName = "provider_Component_App"
    String coresName = "cores_Component_App"
    String ramName = "ram_Component_App"
    String storageName = "storage_Component_App"


    String dbProviderName = "provider_Component_DB"
    String dbCardinalityName = "cardinality_Component_DB"
    String dbCoresName = "cores_Component_DB"
    String dbRamName = "ram_Component_DB"
    String dbStorageName = "storage_Component_DB"


    String lbProviderName = "provider_Component_LB"
    String lbCardinalityName = "cardinality_Component_LB"
    String lbCoresName = "cores_Component_LB"
    String lbRamName = "ram_Component_LB"
    String lbStorageName = "storage_Component_LB"

    String path = "src/main/test/resources/FCR.xmi"

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

    def "FCR template evaluation test"() {

        given:
        newConfiguration.add(new IntVariableValueDTO(cardinalityName, 2))
        newConfiguration.add(new IntVariableValueDTO(providerName, 1))
        newConfiguration.add(new IntVariableValueDTO(coresName, 2))
        newConfiguration.add(new IntVariableValueDTO(ramName, 1000))
        newConfiguration.add(new IntVariableValueDTO(storageName, 10))

        newConfiguration.add(new IntVariableValueDTO(dbCardinalityName, 1))
        newConfiguration.add(new IntVariableValueDTO(dbProviderName, 0))
        newConfiguration.add(new IntVariableValueDTO(dbCoresName, 2))
        newConfiguration.add(new IntVariableValueDTO(dbRamName, 1000))
        newConfiguration.add(new IntVariableValueDTO(dbStorageName, 10))

        newConfiguration.add(new IntVariableValueDTO(lbCardinalityName, 1))
        newConfiguration.add(new IntVariableValueDTO(lbProviderName, 0))
        newConfiguration.add(new IntVariableValueDTO(lbCoresName, 2))
        newConfiguration.add(new IntVariableValueDTO(lbRamName, 1000))
        newConfiguration.add(new IntVariableValueDTO(lbStorageName, 10))
        UtilityGeneratorApplication utilityGenerator =
                new UtilityGeneratorApplication("src/main/test/resources/FCRForTemplates-CP.xmi",
                        mockNodeCandidates, Arrays.asList(new AbstractMap.SimpleEntry<TemplateProvider.AvailableTemplates, Double>(TemplateProvider.AvailableTemplates.COST, 1.0d)))
        UtilityGeneratorApplication utilityGenerator2 =
                new UtilityGeneratorApplication("src/main/test/resources/FCRForTemplates-CP.xmi",
                        mockNodeCandidates,
                        Arrays.asList(new AbstractMap.SimpleEntry<TemplateProvider.AvailableTemplates, Double>(TemplateProvider.AvailableTemplates.COST, 0.5d),
                                new AbstractMap.SimpleEntry<TemplateProvider.AvailableTemplates, Double>(TemplateProvider.AvailableTemplates.CORES, 0.5d*0.333d),
                                new AbstractMap.SimpleEntry<TemplateProvider.AvailableTemplates, Double>(TemplateProvider.AvailableTemplates.DISK, 0.5d*0.333d),
                                new AbstractMap.SimpleEntry<TemplateProvider.AvailableTemplates, Double>(TemplateProvider.AvailableTemplates.RAM, 0.5d*0.333d)))
        when:
        double result = utilityGenerator.evaluate(newConfiguration)
        double result2 = utilityGenerator2.evaluate(newConfiguration)
        double expectedResult2 = 0.5 * 1/40.0 + 0.5*(-0.333 * (1/(81) -1) -0.333 * (1/(16008001) -1)-0.333 * (1/(1681) -1))

        then:
        noExceptionThrown()
        result == 1/40.0d
        Math.abs(result2 - expectedResult2) <= 0.001
    }

    def "FCR template evaluation test with full constructor"() {

        given:
        newConfiguration.add(new IntVariableValueDTO(cardinalityName, 2))
        newConfiguration.add(new IntVariableValueDTO(providerName, 1))
        newConfiguration.add(new IntVariableValueDTO(coresName, 2))
        newConfiguration.add(new IntVariableValueDTO(ramName, 1000))
        newConfiguration.add(new IntVariableValueDTO(storageName, 10))

        newConfiguration.add(new IntVariableValueDTO(dbCardinalityName, 1))
        newConfiguration.add(new IntVariableValueDTO(dbProviderName, 0))
        newConfiguration.add(new IntVariableValueDTO(dbCoresName, 2))
        newConfiguration.add(new IntVariableValueDTO(dbRamName, 1000))
        newConfiguration.add(new IntVariableValueDTO(dbStorageName, 10))

        newConfiguration.add(new IntVariableValueDTO(lbCardinalityName, 1))
        newConfiguration.add(new IntVariableValueDTO(lbProviderName, 0))
        newConfiguration.add(new IntVariableValueDTO(lbCoresName, 2))
        newConfiguration.add(new IntVariableValueDTO(lbRamName, 1000))
        newConfiguration.add(new IntVariableValueDTO(lbStorageName, 10))

        UtilityGeneratorApplication utilityGenerator =
                new UtilityGeneratorApplication(path, "src/main/test/resources/FCRForTemplates-CP.xmi", true,
                        mockNodeCandidates, utilityGeneratorProperties, securityProperties, jwtService,
                        Arrays.asList(new AbstractMap.SimpleEntry<TemplateProvider.AvailableTemplates, Double>(TemplateProvider.AvailableTemplates.COST, 1.0d)))
        UtilityGeneratorApplication utilityGenerator2 =
                new UtilityGeneratorApplication(path, "src/main/test/resources/FCRForTemplates-CP.xmi", true,
                        mockNodeCandidates, utilityGeneratorProperties, securityProperties, jwtService,
                        Arrays.asList(new AbstractMap.SimpleEntry<TemplateProvider.AvailableTemplates, Double>(TemplateProvider.AvailableTemplates.COST, 0.5d),
                                new AbstractMap.SimpleEntry<TemplateProvider.AvailableTemplates, Double>(TemplateProvider.AvailableTemplates.CORES, 0.5d*0.333d),
                                new AbstractMap.SimpleEntry<TemplateProvider.AvailableTemplates, Double>(TemplateProvider.AvailableTemplates.DISK, 0.5d*0.333d),
                                new AbstractMap.SimpleEntry<TemplateProvider.AvailableTemplates, Double>(TemplateProvider.AvailableTemplates.RAM, 0.5d*0.333d)))
        when:
        double result = utilityGenerator.evaluate(newConfiguration)
        double result2 = utilityGenerator2.evaluate(newConfiguration)
        double expectedResult2 = 0.5 * 1/40.0 + 0.5*(-0.333 * (1/(81) -1) -0.333 * (1/(16008001) -1)-0.333 * (1/(1681) -1))

        then:
        noExceptionThrown()
        result == 1/40.0d
        Math.abs(result2 - expectedResult2) <= 0.001
    }
}
