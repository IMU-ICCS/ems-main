package groovy.eu.melodic.upperware.utilitygenerator

import eu.melodic.cache.NodeCandidates
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.IntVariableValueDTO
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO
import eu.melodic.upperware.utilitygenerator.properties.UtilityGeneratorProperties
import io.github.cloudiator.rest.model.GeoLocation
import io.github.cloudiator.rest.model.Hardware
import io.github.cloudiator.rest.model.Location
import io.github.cloudiator.rest.model.NodeCandidate
import spock.lang.Specification

class UtilityGeneratorGenomTest extends Specification{


    NodeCandidates mockNodeCandidates = GroovyMock(NodeCandidates)
    UtilityGeneratorProperties properties = new UtilityGeneratorProperties()

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

        properties.setUtilityGenerator(new UtilityGeneratorProperties.UtilityGenerator())
        properties.getUtilityGenerator().setDlmsControllerUrl("")
    }

    def "Genom initial deployment"() {

        given:
        newConfiguration.add(new IntVariableValueDTO(cardinalityName, 2))
        newConfiguration.add(new IntVariableValueDTO(providerName, 1))
        newConfiguration.add(new IntVariableValueDTO(coresName, 2))
        newConfiguration.add(new IntVariableValueDTO(latitudeName, 10))
        newConfiguration.add(new IntVariableValueDTO(longitudeName, 405 ))


        UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(path, cpModelPath, true, mockNodeCandidates, properties)

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

        UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication(path, cpModelWithSolutionPath, true, mockNodeCandidates, properties)

        when:
        double result = utilityGenerator.evaluate(newConfiguration)

        then:
        noExceptionThrown()
        result != 0

    }






}
