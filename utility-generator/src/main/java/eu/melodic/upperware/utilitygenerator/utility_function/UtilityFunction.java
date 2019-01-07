/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.utility_function;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Constant;
import org.mariuszgromada.math.mxparser.Expression;

import java.util.Arrays;
import java.util.Collection;

@Slf4j
@Getter
public class UtilityFunction {

    private Expression function;
    private Constant[] constants;

    public UtilityFunction(String formula) {
        this.function = new Expression(formula);
    }

    public void setConstants(Collection<Constant> constants){
        this.constants = constants.toArray(new Constant[constants.size()]);
        constants.forEach(c -> log.debug("constant: {}, {}", c.getConstantName(), c.getConstantValue()));
    }

    public double evaluateFunction(Collection<Argument> variables) {
        variables.forEach(a -> log.debug("Argument: {}, {}", a.getArgumentName(), a.getArgumentValue()));
        Arrays.stream(constants).forEach(a -> log.debug("Constant: {}, {}", a.getConstantName(), a.getConstantValue()));

        function.addConstants(constants);
        function.addArguments(variables.toArray(new Argument[variables.size()]));
        if (function.getMissingUserDefinedArguments().length > 0) {
            throw new IllegalStateException("Missing arguments: " + Arrays.toString(function.getMissingUserDefinedArguments()) + " for function " + function.getExpressionString());
        }
        double result = function.calculate();
        if (Double.isNaN(result)){
            log.warn("Result of calculating the utility function is NaN, returning 0");
            result = 0.0;
        }
        function.removeAllArguments();
        function.removeAllConstants();
        log.debug("Utility value: {}", result);
        log.debug("-----------------------");
        return result;
    }

    public String getFormula() {
        return function.getExpressionString();
    }

}
