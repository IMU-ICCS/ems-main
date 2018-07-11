package eu.melodic.upperware.utilitygenerator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;

import java.util.Arrays;
import java.util.Collection;

@Slf4j
@Getter
@AllArgsConstructor
public class UtilityFunction {

    private Expression function;
    private Argument[] arguments;
    @Setter
    private Collection<Argument> attributesOfNodeCandidates;
    @Setter
    private Collection<Argument> currentConfigs;
    @Setter
    private Collection<Argument> metrics;


    public UtilityFunction(Expression expression, Argument[] arguments){
        this.function = expression;
        this.arguments = arguments;
    }

    public double evaluateFunction(){

        System.out.println(Arrays.toString(function.getMissingUserDefinedArguments()));
        return function.calculate();
    }

}
