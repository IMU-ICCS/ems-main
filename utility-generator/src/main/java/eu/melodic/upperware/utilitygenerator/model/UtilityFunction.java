/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Constant;
import org.mariuszgromada.math.mxparser.Expression;

import java.util.Arrays;
import java.util.Collection;

@Slf4j
@Getter
@AllArgsConstructor
public class UtilityFunction {

    private Expression function;
    private Constant[] constants;

    public UtilityFunction(String formula, Collection<Constant> constants){
        this.constants = constants.toArray(new Constant[constants.size()]);
        this.function = new Expression(formula);
    }

    public double evaluateFunction(Collection<Argument> variables){

        variables.forEach(a -> log.info("Argument: {}, {}", a.getArgumentName(), a.getArgumentValue()));
        Arrays.stream(constants).forEach(a -> log.info("Constant: {}, {}", a.getConstantName(), a.getConstantValue()));

        function.addConstants(constants);
        function.addArguments(variables.toArray(new Argument[variables.size()]));

        if (function.getMissingUserDefinedArguments().length > 0){
            throw new IllegalStateException("Not all arguments needed in function "+ function.getExpressionString() +"are set. Missing arguments: " +  Arrays.toString(function.getMissingUserDefinedArguments()));
        }
        double result = function.calculate();
        function.removeAllArguments();
        function.removeAllConstants();
        log.info("result {}", result);
        return result;
    }

    public String getFormula(){
        return function.getExpressionString();
    }

    //before any addition
    public static boolean isInFormula(String formula, String name){
        Expression expression = new Expression(formula);
        String[] arguments = expression.getMissingUserDefinedArguments();
        return Arrays.asList(arguments).contains(name);
    }
}
