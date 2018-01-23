/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 * <p>
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 * <p>
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package eu.passage.upperware.commons.model.tools;

import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.metamodel.types.*;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.common.util.EList;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
public class CPModelTool {

    public static Optional<Variable> getVariable(List<Variable> variables, VariableType variableType, String appName){
        Objects.requireNonNull(variableType);
        Objects.requireNonNull(appName);

        Predicate<Variable> typePredicate = variable -> variableType.equals(variable.getVariableType());
        Predicate<Variable> namePredicate = variable -> appName.equals(variable.getComponentId());

        return getFirst(variables, typePredicate.and(namePredicate));
    }

    public static List<Variable> getVariables(List<Variable> variables, String appName){
        Objects.requireNonNull(appName);

        return variables.stream()
                .filter(variable -> appName.equals(variable.getComponentId()))
                .collect(Collectors.toList());
    }

    public static Map<String, List<Variable>> groupByAppName(List<Variable> variables){
        return variables.stream().collect(Collectors.groupingBy(Variable::getComponentId));
    }

    public static List<String> getComponentNames(List<Variable> variables){
        return variables.stream().map(Variable::getComponentId).distinct().sorted().collect(Collectors.toList());
    }


    public static Optional<VariableValue> getVariableValue(List<VariableValue> variables, VariableType variableType, String appName){
        Objects.requireNonNull(variableType);
        Objects.requireNonNull(appName);

        Predicate<VariableValue> typePredicate = variableValue -> variableType.equals(variableValue.getVariable().getVariableType());
        Predicate<VariableValue> namePredicate = variableValue -> appName.equals(variableValue.getVariable().getComponentId());

        return getFirst(variables, typePredicate.and(namePredicate));
    }

    public static Optional<VariableValue> getVariableValue(List<VariableValue> variables, VariableType variableType){
        Objects.requireNonNull(variableType);

        return getFirst(variables, variableValue -> variableType.equals(variableValue.getVariable().getVariableType()));
    }

    public static List<VariableValue> getVariableValues(List<VariableValue> variables, String appName){
        Objects.requireNonNull(appName);

        return variables.stream()
                .filter(variableValue -> appName.equals(variableValue.getVariable().getComponentId()))
                .collect(Collectors.toList());
    }

    public static Map<String, List<VariableValue>> groupVariableValuesByAppName(List<VariableValue> variables){
        return variables.stream().collect(Collectors.groupingBy(variableValue -> variableValue.getVariable().getComponentId()));
    }

    public static List<String> getComponentNamesFromVariableValues(List<VariableValue> variables){
        return variables.stream().map(variableValue -> variableValue.getVariable().getComponentId()).distinct().sorted().collect(Collectors.toList());
    }

    public static Optional<VariableValue> getCardinality(List<VariableValue> variableValues){
        return getVariableValue(variableValues, VariableType.CARDINALITY);
    }

    public static Optional<VariableValue> getProviderId(List<VariableValue> variableValues){
        return getVariableValue(variableValues, VariableType.PROVIDER);
    }

    public static Optional<VariableValue> getOs(List<VariableValue> variableValues){
        return getVariableValue(variableValues, VariableType.OS);
    }

    public static Optional<VariableValue> getCores(List<VariableValue> variableValues){
        return getVariableValue(variableValues, VariableType.CORES);
    }

    public static Optional<VariableValue> getRam(List<VariableValue> variableValues){
        return getVariableValue(variableValues, VariableType.RAM);
    }

    public static Optional<VariableValue> getStorage(List<VariableValue> variableValues){
        return getVariableValue(variableValues, VariableType.STORAGE);
    }

    public static int getIntValue(VariableValue variableValue){
        Objects.requireNonNull(variableValue.getValue());
        return ((IntegerValueUpperware) variableValue.getValue()).getValue();
    }

    public static long getLongValue(VariableValue variableValue){
        Objects.requireNonNull(variableValue.getValue());
        return ((LongValueUpperware) variableValue.getValue()).getValue();
    }

    public static float getFloatValue(VariableValue variableValue){
        Objects.requireNonNull(variableValue.getValue());
        return ((FloatValueUpperware) variableValue.getValue()).getValue();
    }

    public static void printCpModel(ConstraintProblem cp){
        log.info("ConstraintProblem: {}", cp.getId());

        log.info("CONSTANTS:");
        for (Constant constant : cp.getConstants()) {
            log.info(toString(constant));
        }

        log.info("VARIABLES:");
        for (Variable variable : cp.getVariables()) {
            log.info(toString(variable));
        }

        log.info("CONSTRAINTS");
        for(ComparisonExpression ce : cp.getConstraints()){
            log.info(ce.getId() + ": " +CPModelTool.toString(ce));
        }

        log.info("AUX Expressions");
        for(Expression aux : cp.getAuxExpressions()){
            log.info(aux.getId() + ": " +CPModelTool.toString(aux));
        }

        log.info("METRICS");
        for(MetricVariable met : cp.getMetricVariables()){
            log.info(met.getId() + ": " +CPModelTool.toString(met));
        }

        log.info("SOLUTION");
        for(Solution sol : cp.getSolution()){
            log.info("Solution: " + sol.getClass());
        }
    }

    /**
     * Provides string representation of constant - for logging purposes
     * @return string
     */
    public static String toString(Constant cons) {
        return cons.getId() + ": " + CPModelTool.getValueFromNumericValue(cons.getValue()).getLeft();
    }

    /**
     * Provides string representation of variable - for logging purposes
     * @return string
     */
    public static String toString(Variable var) {
        String retString = System.lineSeparator() + var.getId() + System.lineSeparator()
                + "  componentId " + var.getComponentId()+ System.lineSeparator()
                + "  variableType " + var.getVariableType()+ System.lineSeparator()
                + "  vmId " + var.getVmId() + System.lineSeparator()
                + "  domain " + toString(var.getDomain()) + System.lineSeparator();
        return retString;

    }

    private static String toString(Domain domain) {
        if (domain instanceof RangeDomain) {
            return String.format("RangeDomain: (%s, %s)", getValueFromNumericValue(((RangeDomain) domain).getFrom()).getLeft(), getValueFromNumericValue(((RangeDomain) domain).getTo()).getLeft());
        } else if (domain instanceof NumericListDomain) {
            EList<NumericValueUpperware> values = ((NumericListDomain) domain).getValues();
            return values.stream().map(numericValueUpperware -> getValueFromNumericValue(numericValueUpperware).getLeft()).collect(Collectors.joining(", ", "NumericListDomain: {", "}"));
        } else if (domain instanceof NumericDomain) {
            return String.format("NumericDomain: (%s)", getValueFromNumericValue(((NumericDomain) domain).getValue()));
        }
        throw new IllegalArgumentException("Domian unknown: " + domain.getClass().getCanonicalName());
    }

    /**
     * Provides string representation of NumericExpression - for logging purposes
     * @return string
     */
    public static String toString(Expression expression) {
        String retString = "";

        if (expression instanceof ComposedExpression) {
            ComposedExpression composedExp = (ComposedExpression) expression;
            String composedString = "( ";

            for (NumericExpression ne : composedExp.getExpressions()) {
                if (ne.equals(composedExp.getExpressions().get(0))) {
                    composedString = composedString + toString(ne);
                } else {
                    composedString = composedString + " " + composedExp.getOperator().getName() + " " + toString(ne);
                }
            }
            retString = retString + composedString + " )";

        } else if (expression instanceof ComparisonExpression) {
            ComparisonExpression comparisonExp = (ComparisonExpression) expression;
            retString = System.lineSeparator() + "( " + toString(comparisonExp.getExp1()) + " " + comparisonExp.getComparator().getName() + " " + toString(comparisonExp.getExp2()) + " ) ";
        } else if (expression instanceof Constant || expression instanceof Variable) {
            retString = expression.getId();
        } else {
            log.error("NumericExpresion: {} not yet supported", expression.getClass().toString());
        }

        return retString;
    }


    public static void assignNumericValue(String val, Variable var, Solution solution) {

        VariableValue varValue = CpFactory.eINSTANCE.createVariableValue();

        varValue.setVariable(var);

        NumericValueUpperware theValue = null;

        if (var.getDomain() instanceof BooleanDomain) {
            int intVal = Integer.parseInt(val);

            IntegerValueUpperware intValUpperware = TypesFactory.eINSTANCE.createIntegerValueUpperware();

            intValUpperware.setValue(intVal);

            theValue = intValUpperware;

        } else {
            NumericDomain domain = (NumericDomain) var.getDomain();

            int type = domain.getType().getValue();

            if (type == BasicTypeEnum.INTEGER_VALUE) {
                int intVal = Integer.parseInt(val);

                IntegerValueUpperware intValUpperware = TypesFactory.eINSTANCE.createIntegerValueUpperware();

                intValUpperware.setValue(intVal);

                theValue = intValUpperware;

            } else if (type == BasicTypeEnum.LONG_VALUE) {
                long longVal = Long.parseLong(val);

                LongValueUpperware longValUpperware = TypesFactory.eINSTANCE.createLongValueUpperware();

                longValUpperware.setValue(longVal);

                theValue = longValUpperware;
            } else if (type == BasicTypeEnum.FLOAT_VALUE) {
                float floatVal = Float.parseFloat(val);

                FloatValueUpperware floatValUpperware = TypesFactory.eINSTANCE.createFloatValueUpperware();

                floatValUpperware.setValue(floatVal);

                theValue = floatValUpperware;
            } else if (type == BasicTypeEnum.DOUBLE_VALUE) {
                double doubleVal = Double.parseDouble(val);

                DoubleValueUpperware doubleValUpperware = TypesFactory.eINSTANCE.createDoubleValueUpperware();

                doubleValUpperware.setValue(doubleVal);

                theValue = doubleValUpperware;
            } else
                System.err.println("UpperwareModelTool - assignValue - Unknown type " + type + " The value can not be assigned!");

        }


        if (theValue != null) {
            varValue.setValue(theValue);
            solution.getVariableValue().add(varValue);
        }
    }

    public static String getValueFromVar(Variable var, ConstraintProblem cp) {
        //Gets the last solution

        if (cp.getSolution().size() > 0) {
            Solution sol = searchLastSolution(cp.getSolution());
            VariableValue vv = getVariableValueByVariableId(var.getId(), sol);

            if (vv != null) {
                return getValueFromNumericValue(vv.getValue()).getLeft();
            } else {
                log.error("ModelTool - assignValue - Unknown val {} The value can not be retrieved!", var.getId());
            }
        } else {
            log.error("ModelTool - assignValue - Unknown val {} The value can not be retrieved!", var.getId());
        }
        return null;
    }

    public static Pair<String, String> getValueFromNumericValue(@NonNull NumericValueUpperware value) {
        if (value instanceof IntegerValueUpperware) {
            return Pair.of(Integer.toString(((IntegerValueUpperware) value).getValue()), Integer.class.getCanonicalName());
        } else if (value instanceof FloatValueUpperware) {
            return Pair.of(Float.toString(((FloatValueUpperware) value).getValue()), Float.class.getCanonicalName());
        } else if (value instanceof DoubleValueUpperware) {
            return Pair.of(Double.toString(((DoubleValueUpperware) value).getValue()), Double.class.getCanonicalName());
        } else if (value instanceof LongValueUpperware) {
            return Pair.of(Long.toString(((LongValueUpperware) value).getValue()), Long.class.getCanonicalName());
        }
        throw new IllegalArgumentException("NumericValueUpperware unknown: " + value.getClass().getCanonicalName());
    }

    public static Solution searchLastSolution(EList<Solution> solutions) {
        return getSolutionWithIndex(solutions).getValue();
    }

    private static Pair<Integer, Solution> getSolutionWithIndex(EList<Solution> solutions){
        int resultIndex=-1;
        Solution resultSolution = null;
        long maxTS= -1L;

        List<Solution> notNullSolutions = (List<Solution>) CollectionUtils.emptyIfNull(solutions);

        for(int i =0; i<notNullSolutions.size(); i++) {
            Solution sol = notNullSolutions.get(i);
            long timestamp = sol.getTimestamp();
            if (timestamp == 0) {
                resultIndex = i;
                resultSolution = sol;
                break;
            } else if (timestamp > maxTS) {
                maxTS = timestamp;
                resultIndex=i;
                resultSolution = sol;
            }
        }
        return Pair.of(resultIndex, resultSolution);
    }



    public static VariableValue searchVariableValue(Solution sol, Variable var) {
        return getVariableValueByVariableId(var.getId(), sol);
    }

    public static VariableValue getVariableValueByVariableId(String id, Solution sol) {
        return getFirst(sol.getVariableValue(), vv -> vv.getVariable().getId().equals(id)).orElse(null);
    }

    public static MetricVariableValue searchMetricValue(Solution sol, MetricVariable var) {
        return getFirst(sol.getMetricVariableValue(), value -> value.getVariable().getId().equals(var.getId())).orElse(null);
    }

    private static <T> Optional<T> getFirst(List<T> elements, Predicate<T> predicate) {
        return CollectionUtils.emptyIfNull(elements).stream().filter(predicate).findFirst();
    }

    public static Solution createSolution(ConstraintProblem cp) {
        Solution sol = CpFactory.eINSTANCE.createSolution();
        sol.setTimestamp(System.currentTimeMillis());
        cp.getSolution().add(sol);

        return sol;
    }

}
