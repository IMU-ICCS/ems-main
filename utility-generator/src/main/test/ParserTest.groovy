import eu.melodic.upperware.utilitygenerator.connection.UtilityFunctionCreator
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


    //addArguments
}
