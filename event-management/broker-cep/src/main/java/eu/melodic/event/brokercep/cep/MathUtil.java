/*
 * Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.brokercep.cep;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mariuszgromada.math.mxparser.Constant;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;
import org.mariuszgromada.math.mxparser.mXparser;
import org.mariuszgromada.math.mxparser.parsertokens.FunctionVariadic;
import org.mariuszgromada.math.mxparser.parsertokens.Token;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class MathUtil {
    private static Set<Function> functions = new HashSet<>();
    private static Map<String, Constant> constants = new HashMap<>();

    // ------------------------------------------------------------------------

    public static void addFunctionDefinition(FunctionDefinition functionDef) {
        log.debug("MathUtil: Add new function definition: {}", functionDef);
        String argsStr = String.join(", ", functionDef.getArguments());
        //String defStr = String.format("%(%s) = %s", functionDef.getName(), argsStr, functionDef.getExpression());
        String defStr = functionDef.getName() + "(" + argsStr + ") = " + functionDef.getExpression();
        log.debug("MathUtil: definition-string: {}", defStr);
        Function func = new Function(defStr);
        functions.add(func);
    }

    public static void clearFunctionDefinitions() {
        log.debug("MathUtil: Clear function definitions");
        functions.clear();
    }

    // ------------------------------------------------------------------------

    public static void setConstant(String constantName, double constantValue) {
        log.debug("MathUtil: Set constant: name={}, value={}", constantName, constantValue);
        Constant con = new Constant(constantName, constantValue);
        constants.put(constantName, con);
    }

    public static void setConstants(Map<String, Double> constantsMap) {
        log.debug("MathUtil: Add constants using map: {}", constantsMap);
        //constantsMap.entrySet().stream().forEach(c -> setConstant(c.getKey(), c.getValue()));
        constantsMap.forEach(MathUtil::setConstant);
    }

    public static void clearConstants() {
        log.debug("MathUtil: Clear constants");
        constants.clear();
    }

    // ------------------------------------------------------------------------

    public static List<String> getFormulaArguments(String formula) {
        log.debug("MathUtil: getFormulaArguments: formula={}", formula);
        if (StringUtils.isBlank(formula)) {
            log.debug("MathUtil: getFormulaArguments: Formula is null or empty");
            return null;
        }

        // Create MathParser expression
        Expression e = new Expression(formula);
        //e.setVerboseMode();
        log.trace("MathUtil: getFormulaArguments: expression={}", e.getExpressionString());

        // Add constants
        e.addConstants(new ArrayList(constants.values()));

        // Add functions
        for (Function f : functions) e.addFunctions(f);

        // Get argument names
        boolean lexSyntax = e.checkLexSyntax();
        boolean genSyntax = e.checkSyntax();
        if (log.isTraceEnabled()) {
            log.trace("MathUtil: getFormulaArguments: lexSyntax={}, genSyntax: {}", lexSyntax, genSyntax);
            log.trace("MathUtil: getFormulaArguments: syntax-status={}, error={}", e.getSyntaxStatus(), e.getErrorMessage());
        }

        List<Token> initTokens = e.getCopyOfInitialTokens();
        log.trace("MathUtil: getFormulaArguments: initial-tokens={}", initTokens);
        if (log.isTraceEnabled()) {
            mXparser.consolePrintTokens(initTokens);
        }

        List<String> argNames = initTokens.stream()
                .filter(t -> t.tokenTypeId == Token.NOT_MATCHED)
                .filter(t -> "argument".equals(t.looksLike))
                .map(t -> t.tokenStr)
                .collect(Collectors.toList());
        log.debug("MathUtil: getFormulaArguments: arguments={}", argNames);

        return argNames;
    }

    // ------------------------------------------------------------------------

    public static boolean containsAggregator(String formula) {
        log.debug("MathUtil: containsAggregator: formula={}", formula);
        if (StringUtils.isBlank(formula)) {
            log.debug("MathUtil: containsAggregator: Formula is null or empty");
            return false;
        }

        // Create MathParser expression
        Expression e = new Expression(formula);
        //e.setVerboseMode();
        log.trace("MathUtil: containsAggregator: expression={}", e.getExpressionString());

        // Add constants
        e.addConstants(new ArrayList(constants.values()));

        // Add functions
        for (Function f : functions) e.addFunctions(f);

        // Get argument names
        boolean lexSyntax = e.checkLexSyntax();
        boolean genSyntax = e.checkSyntax();
        log.trace("MathUtil: containsAggregator: lexSyntax={}, genSyntax: {}", lexSyntax, genSyntax);
        log.trace("MathUtil: containsAggregator: syntax-status={}, error={}", e.getSyntaxStatus(), e.getErrorMessage());

        List<Token> initTokens = e.getCopyOfInitialTokens();
        log.trace("MathUtil: containsAggregator: initial-tokens={}", initTokens);
        if (log.isTraceEnabled()) {
            mXparser.consolePrintTokens(initTokens);
        }
        List<String> aggNames = initTokens.stream()
                .filter(t -> t.tokenTypeId == FunctionVariadic.TYPE_ID)
                .map(t -> t.tokenStr)
                .collect(Collectors.toList());
        log.trace("MathUtil: containsAggregator: formula-aggregator-functions: {}", aggNames);

        boolean containsAgg = aggNames.size() > 0;
        if (containsAgg)
            log.debug("MathUtil: containsAggregator: Formula contains aggregators: aggregators={}, formula={}", aggNames, formula);
        else log.debug("MathUtil: containsAggregator: Formula does not contain aggregators: {}", formula);
        return containsAgg;
    }

    // ------------------------------------------------------------------------

    protected final static String[] aggregatorNames = {"iff", "min", "max", "ConFrac", "ConPol", "gcd", "lcm", "add", "multi", "mean", "var", "std", "rList"};

    public static boolean containsAggregatorRegexp(String formula) {
        log.debug("MathUtil: containsAggregatorRegexp: formula={}", formula);
        if (StringUtils.isBlank(formula)) {
            log.debug("MathUtil: containsAggregatorRegexp: Formula is null or empty");
            return false;
        }
        formula = " " + formula;
        for (int i = 0; i < aggregatorNames.length; i++) {
            log.trace("MathUtil: containsAggregatorRegexp: checking aggregator: aggregator={}, formula={}", aggregatorNames[i], formula);
            if (checkPattern(formula, aggregatorNames[i])) {
                log.debug("MathUtil: containsAggregatorRegexp: Formula contains aggregators: aggregator={}, formula={}", aggregatorNames[i], formula);
                return true;
            }
        }
        log.debug("MathUtil: containsAggregatorRegexp: Formula does not contain aggregators: formula={}", formula);
        return false;
    }

    protected static boolean checkPattern(String formula, String aggregatorName) {
        int flags = Pattern.CASE_INSENSITIVE;
        Pattern pat = Pattern.compile(String.format("[^a-zA-Z]%s[^a-zA-Z]", aggregatorName), flags);
        return pat.matcher(formula).find();
    }

    // ------------------------------------------------------------------------

    public static double evalAgg(String formula, Map<String, List<Double>> argsMap) {
        log.debug("MathUtil: evalAgg: input: formula={}, arg-map={}", formula, argsMap);
        int iter = 0;
        for (Map.Entry<String, List<Double>> arg : argsMap.entrySet()) {
            log.debug("MathUtil: evalAgg: iteration #{}: arg={}", iter, arg);
            String argName = arg.getKey();
            List<Double> argValue = arg.getValue();
            log.debug("MathUtil: evalAgg: iteration #{}: arg-name={}, arg-value={}", iter, argName, argValue);
            String valStr = argValue.stream().map(value -> value.toString()).collect(Collectors.joining(", "));
            log.debug("MathUtil: evalAgg: iteration #{}: arg-name={}, arg-value-str={}", iter, argName, valStr);

            formula = formula.replaceAll(argName, valStr);
            iter++;
        }
        log.debug("MathUtil: evalAgg: formula-to-evaluate: {}", formula);

        return eval(formula, new java.util.HashMap<>());
    }

    public static double eval(String formula, Map<String, Double> argsMap) {
        // Create MathParser expression
        Expression e = new Expression(formula);
        //e.setVerboseMode();
        log.debug("MathUtil: formula={}", e.getExpressionString());

        // Add constants
        e.addConstants(new ArrayList(constants.values()));

        // Add functions
        for (Function f : functions) e.addFunctions(f);

        // Get argument names
        boolean lexSyntax = e.checkLexSyntax();
        boolean genSyntax = e.checkSyntax();
        if (log.isTraceEnabled()) {
            log.trace("MathUtil: lexSyntax={}, genSyntax: {}", lexSyntax, genSyntax);
            log.trace("MathUtil: syntax-status={}, error={}", e.getSyntaxStatus(), e.getErrorMessage());
        }

        List<Token> initTokens = e.getCopyOfInitialTokens();
        log.debug("MathUtil: initial-tokens={}", initTokens);
        if (log.isTraceEnabled()) {
            mXparser.consolePrintTokens(initTokens);
        }
        Set<String> argNames = initTokens.stream()
                .filter(t -> t.tokenTypeId == Token.NOT_MATCHED)
                .filter(t -> "argument".equals(t.looksLike))
                .map(t -> t.tokenStr)
                .collect(Collectors.toSet());
        log.debug("MathUtil: initial-token-names: {}", argNames);

        // Define expression arguments with user provided values
        //e.removeAllArguments();
        for (String argName : argNames) {
            try {
                log.debug("MathUtil: Defining Arg: {}", argName);
                double argValue = argsMap.get(argName);
                e.defineArgument(argName, argValue);
                log.debug("MathUtil: Arg: {} = {}", argName, argValue);
            } catch (Exception ex) {
                log.error("MathUtil: Defining Arg: EXCEPTION: arg-name={}, args-map={}", argName, argsMap);
                throw ex;
            }
        }
        genSyntax = e.checkSyntax();
        if (!genSyntax) throw new IllegalArgumentException("Syntax error in expression: " + e.getErrorMessage());

        // Calculate result
        double result = e.calculate();
        log.debug("MathUtil: Result={}, computing-time={}, error={}", result, e.getComputingTime(), e.getErrorMessage());

        return result;
    }
}
