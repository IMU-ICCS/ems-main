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

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
public class CPModelTool {

    private static int INITIAL_DEPLOYMENT_ID = -1;


    public static Optional<CpVariable> getVariable(List<CpVariable> variables, VariableType variableType, String appName){
        Objects.requireNonNull(variableType);
        Objects.requireNonNull(appName);

        Predicate<CpVariable> typePredicate = cpVariable -> variableType.equals(cpVariable.getVariableType());
        Predicate<CpVariable> namePredicate = cpVariable -> appName.equals(cpVariable.getComponentId());

        return getFirst(variables, typePredicate.and(namePredicate));
    }

    public static List<CpVariable> getVariables(List<CpVariable> variables, String appName){
        Objects.requireNonNull(appName);

        return variables.stream()
                .filter(variable -> appName.equals(variable.getComponentId()))
                .collect(Collectors.toList());
    }

    public static Map<String, List<CpVariable>> groupByAppName(List<CpVariable> variables){
        return variables.stream().collect(Collectors.groupingBy(CpVariable::getComponentId));
    }

    public static List<String> getComponentNames(List<CpVariable> variables){
        return variables.stream().map(CpVariable::getComponentId).distinct().sorted().collect(Collectors.toList());
    }


    public static Optional<CpVariableValue> getVariableValue(List<CpVariableValue> variables, VariableType variableType, String appName){
        Objects.requireNonNull(variableType);
        Objects.requireNonNull(appName);

        Predicate<CpVariableValue> typePredicate = cpVariableValue -> variableType.equals(cpVariableValue.getVariable().getVariableType());
        Predicate<CpVariableValue> namePredicate = cpVariableValue -> appName.equals(cpVariableValue.getVariable().getComponentId());

        return getFirst(variables, typePredicate.and(namePredicate));
    }

    public static Optional<CpVariableValue> getVariableValue(List<CpVariableValue> variables, VariableType variableType){
        Objects.requireNonNull(variableType);

        return getFirst(variables, cpVariableValue -> variableType.equals(cpVariableValue.getVariable().getVariableType()));
    }

    public static List<CpVariableValue> getVariableValues(List<CpVariableValue> variables, String appName){
        Objects.requireNonNull(appName);

        return variables.stream()
                .filter(variableValue -> appName.equals(variableValue.getVariable().getComponentId()))
                .collect(Collectors.toList());
    }

    public static Map<String, List<CpVariableValue>> groupVariableValuesByAppName(List<CpVariableValue> variables){
        return variables.stream().collect(Collectors.groupingBy(variableValue -> variableValue.getVariable().getComponentId()));
    }

    public static List<String> getComponentNamesFromVariableValues(List<CpVariableValue> variables){
        return variables.stream().map(variableValue -> variableValue.getVariable().getComponentId()).distinct().sorted().collect(Collectors.toList());
    }

    public static Optional<CpVariableValue> getCardinality(List<CpVariableValue> variableValues){
        return getVariableValue(variableValues, VariableType.CARDINALITY);
    }

    public static Optional<CpVariableValue> getProviderId(List<CpVariableValue> variableValues){
        return getVariableValue(variableValues, VariableType.PROVIDER);
    }

    public static Optional<CpVariableValue> getOs(List<CpVariableValue> variableValues){
        return getVariableValue(variableValues, VariableType.OS);
    }

    public static Optional<CpVariableValue> getCores(List<CpVariableValue> variableValues){
        return getVariableValue(variableValues, VariableType.CORES);
    }

    public static Optional<CpVariableValue> getRam(List<CpVariableValue> variableValues){
        return getVariableValue(variableValues, VariableType.RAM);
    }

    public static Optional<CpVariableValue> getStorage(List<CpVariableValue> variableValues){
        return getVariableValue(variableValues, VariableType.STORAGE);
    }

    public static int getIntValue(CpVariableValue variableValue){
        Objects.requireNonNull(variableValue.getValue());
        return ((IntegerValueUpperware) variableValue.getValue()).getValue();
    }

    public static long getLongValue(CpVariableValue variableValue){
        Objects.requireNonNull(variableValue.getValue());
        return ((LongValueUpperware) variableValue.getValue()).getValue();
    }

    public static float getFloatValue(CpVariableValue variableValue){
        Objects.requireNonNull(variableValue.getValue());
        return ((FloatValueUpperware) variableValue.getValue()).getValue();
    }

    public static double getDoubleValue(CpVariableValue variableValue){
        Objects.requireNonNull(variableValue.getValue());
        return ((DoubleValueUpperware) variableValue.getValue()).getValue();
    }

    public static void printCpModel(ConstraintProblem cp){
        log.info("ConstraintProblem: {}", cp.getId());

        log.info("CONSTANTS:");
        for (Constant constant : cp.getConstants()) {
            log.info(toString(constant));
        }

        log.info("VARIABLES:");
        for (CpVariable cpVariable : cp.getCpVariables()) {
            log.info(toString(cpVariable));
        }

        log.info("CONSTRAINTS");
        for(ComparisonExpression ce : cp.getConstraints()){
            log.info(ce.getId() + ": " +CPModelTool.toString(ce));
        }

        log.info("AUX EXPRESSIONS");
        for(Expression aux : cp.getAuxExpressions()){
            log.info(aux.getId() + ": " +CPModelTool.toString(aux));
        }

        log.info("METRICS");
        for(CpMetric met : cp.getCpMetrics()){
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
     * Provides string representation of CpVariable - for logging purposes
     * @return string
     */
    public static String toString(CpVariable var) {
        return System.lineSeparator() + var.getId() + System.lineSeparator()
                + "  componentId " + var.getComponentId()+ System.lineSeparator()
                + "  variableType " + var.getVariableType()+ System.lineSeparator()
                + "  domain " + toString(var.getDomain()) + System.lineSeparator();

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
            StringBuilder composedString = new StringBuilder();

            for (NumericExpression ne : composedExp.getExpressions()) {
                if (isFirstExpression(composedExp, ne)) {
                    composedString.append(toString(ne));
                } else {
                    composedString.append(" ").append(composedExp.getOperator().getName()).append(" ").append(toString(ne));
                }
            }
            retString = "(" + composedString + " )";

        } else if (expression instanceof ComparisonExpression) {
            ComparisonExpression comparisonExp = (ComparisonExpression) expression;
            retString = System.lineSeparator() + "( " + toString(comparisonExp.getExp1()) + " " + comparisonExp.getComparator().getName() + " " + toString(comparisonExp.getExp2()) + " ) ";
        } else if (expression instanceof Constant || expression instanceof CpVariable || expression instanceof CpMetric) {
            retString = expression.getId();
        } else {
            log.error("toString method for {} not supported yet", expression.getClass().toString());
        }

        return retString;
    }

    private static boolean isFirstExpression(ComposedExpression composedExp, NumericExpression ne) {
        return ne.equals(composedExp.getExpressions().get(0));
    }

    public static void assignNumericValue(String val, CpVariable var, Solution solution) {

        CpVariableValue varValue = CpFactory.eINSTANCE.createCpVariableValue();

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

    public static String getValueFromVar(CpVariable var, ConstraintProblem cp) {
        //Gets the last solution

        if (cp.getSolution().size() > 0) {
            Solution sol = searchLastSolution(cp.getSolution());
            CpVariableValue vv = getVariableValueByVariableId(var.getId(), sol);

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



    public static CpVariableValue searchVariableValue(Solution sol, CpVariable var) {
        return getVariableValueByVariableId(var.getId(), sol);
    }

    public static CpVariableValue getVariableValueByVariableId(String id, Solution sol) {
        return getFirst(sol.getVariableValue(), vv -> vv.getVariable().getId().equals(id)).orElse(null);
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

    public static boolean isInitialDeployment(ConstraintProblem cp){
        int deployedSolutionId = cp.getDeployedSolutionId();
        return deployedSolutionId == INITIAL_DEPLOYMENT_ID;
    }

}
