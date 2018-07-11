import eu.melodic.cache.NodeCandidates
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication
import eu.melodic.upperware.utilitygenerator.connection.UtilityFunctionCreator
import eu.melodic.upperware.utilitygenerator.model.DTO.IntMetricDTO
import eu.melodic.upperware.utilitygenerator.model.DTO.MetricDTO
import eu.melodic.upperware.utilitygenerator.model.DTO.VariableDTO
import eu.melodic.upperware.utilitygenerator.model.function.Element
import eu.melodic.upperware.utilitygenerator.model.function.IntElement
import eu.paasage.upperware.metamodel.cp.VariableType
import spock.lang.Specification

class ParserTest extends Specification{

    def "second"(){

        String formula = ""//""1 - ((WorkerCost * WorkerCardinality)-min(CandidateCost)) / (6 * max(CandidateCost) - min(CandidateCost))"
        when:
            UtilityFunctionCreator function = new UtilityFunctionCreator()
        function.generateFunction(formula, "/Users/mrozanska/CRM.xmi")
        then:
            noExceptionThrown();

    }


    def "first"(){

        String formula = ""//""1 - ((WorkerCost * WorkerCardinality)-min(CandidateCost)) / (6 * max(CandidateCost) - min(CandidateCost))"
        when:
        UtilityFunctionCreator function = new UtilityFunctionCreator()
        function.generateFunction(formula, "/Users/mrozanska/TrafficSimulationUF.xmi")
        then:
        noExceptionThrown();

    }

    def "FCR test"(){

        given:
        String cardinalityName = "AppCardinality"
        String metricName = "RT_AVG"
        String componentId = "Component_App"
        Collection<VariableDTO> variables = new ArrayList<>();
        variables.add(new VariableDTO(cardinalityName, componentId, VariableType.CARDINALITY))

        Collection<MetricDTO> metrics = new ArrayList<>()
        metrics.add(new IntMetricDTO(metricName, 40))

        Collection<Element> deployedSolution = new ArrayList<>()
        deployedSolution.add(new IntElement(cardinalityName, 3))

        NodeCandidates nc = GroovyMock(NodeCandidates)

        String path = "/Users/mrozanska/FCRnew.xmi"
        UtilityGeneratorApplication utilityGenerator = new UtilityGeneratorApplication("cdo", path, variables, metrics, deployedSolution, GroovyMock(NodeCandidates))

        when:
        double result = utilityGenerator.evaluate(GroovyMock(List))

        then:
        noExceptionThrown();

    }


    //addArguments
}
