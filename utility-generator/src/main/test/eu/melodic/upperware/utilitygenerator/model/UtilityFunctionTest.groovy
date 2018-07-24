package eu.melodic.upperware.utilitygenerator.model

import org.mariuszgromada.math.mxparser.Argument
import org.mariuszgromada.math.mxparser.Constant
import spock.lang.Specification

class UtilityFunctionTest extends Specification{


    UtilityFunction utilityFunction
    String formula


    def setup(){
        formula = "a+b/t"
        Collection<Constant> constants = new ArrayList<>()
        constants.add(new Constant("a", 4))
        utilityFunction = new UtilityFunction(formula, constants)
    }

    def "evaluateFunctionTest"(){
        given:
        Collection<Argument> arguments = new ArrayList<>()
        arguments.add(new Argument("b", 4))
        arguments.add(new Argument("t", 6))

        when:
        double result = utilityFunction.evaluateFunction(arguments)

        then:
        noExceptionThrown()
    }


    def "evaluateFunctionWithoutAllArgumentsTest"(){
        given:
        Collection<Argument> arguments = new ArrayList<>()
        arguments.add(new Argument("b", 4))

        when:
        double result = utilityFunction.evaluateFunction(arguments)

        then:
        thrown(IllegalStateException)

    }

    def "evaluateFunctionWithoutConstantsTest"(){
        given:

        utilityFunction = new UtilityFunction("a+b/t", new ArrayList<>())

        Collection<Argument> arguments = new ArrayList<>()
        arguments.add(new Argument("b", 4))
        arguments.add(new Argument("t", 4))


        when:
        double result = utilityFunction.evaluateFunction(arguments)

        then:
        thrown(IllegalStateException)

    }

    def "isInFormulaFalseTest"(){

        when:
            boolean result = UtilityFunction.isInFormula("ala+maala*i/r", var);

        then:
            !result

        where:
        var << ["a", "ma", "k"]

    }

    def "isInFormulaTrueTest"(){

        when:
        boolean result = UtilityFunction.isInFormula("ala+maala*i/r", var);

        then:
        result

        where:
        var << ["ala", "i", "maala"]

    }
}
