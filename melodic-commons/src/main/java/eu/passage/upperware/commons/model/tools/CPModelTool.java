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
import eu.paasage.upperware.metamodel.cp.impl.RangeDomainImpl;
import eu.paasage.upperware.metamodel.types.*;
import org.eclipse.emf.common.util.EList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class CPModelTool {

    public static Variable getVariable(List<Variable> variables, VariableType variableType, String appName){
        Objects.requireNonNull(variableType);
        Objects.requireNonNull(appName);

        return variables.stream()
                .filter(variable -> variableType.equals(variable.getVariableType()))
                .filter(variable -> appName.equals(variable.getComponentId()))
                .findAny().orElse(null);
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

    public static List<String> getComponentNams(List<Variable> variables){
        return variables.stream().map(Variable::getComponentId).distinct().sorted().collect(Collectors.toList());
    }

    /**
     * Provides string representation of constant - for logging purposes
     * @return string
     */
    public static String toString(Constant cons) {
        String retString = cons.getId() + ": " + CPModelTool.getValueFromNumericValue(cons.getValue()).get(0).toString();
        return retString;

    }

    /**
     * Provides string representation of variable - for logging purposes
     * @return string
     */
    public static String toString(Variable var) {
        String retString = System.lineSeparator() + var.getId() + System.lineSeparator()
                + "  providerId: " + var.getProviderId() + System.lineSeparator()
                + "  vmId " + var.getVmId() + System.lineSeparator()
                + "  osImageId: " + var.getOsImageId() + System.lineSeparator()
                + "  hardwareId: " + var.getHardwareId() + System.lineSeparator()
                + "  domainFrom: " + CPModelTool.getValueFromNumericValue(((RangeDomainImpl) var.getDomain()).getFrom()).get(0).toString()
                + "  domainTo: " + CPModelTool.getValueFromNumericValue(((RangeDomainImpl) var.getDomain()).getTo()).get(0).toString();
        return retString;

    }

    /**
     * Provides string representation of goal - for logging purposes
     * @return string
     */
    public static String toString(Goal goal) {
        return "Goal Type: " + goal.getId() + System.lineSeparator() + toString(goal.getExpression());
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
        } else if (expression instanceof Constant) {
            retString = ((Constant) expression).getId();
        } else if (expression instanceof Variable) {
            retString = ((Variable) expression).getId();
        } else {
            System.out.println("NumericExpresion: " + expression.getClass().toString() + " not yet supported");
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
                NumericValueUpperware valUpp = vv.getValue();

                if (valUpp instanceof IntegerValueUpperware) {
                    IntegerValueUpperware intValUpperware = (IntegerValueUpperware) valUpp;

                    return Integer.toString(intValUpperware.getValue());
                } else if (valUpp instanceof LongValueUpperware) {
                    LongValueUpperware longValUpperware = (LongValueUpperware) valUpp;

                    return Long.toString(longValUpperware.getValue());
                } else if (valUpp instanceof FloatValueUpperware) {
                    FloatValueUpperware floatValUpperware = (FloatValueUpperware) valUpp;

                    return Float.toString(floatValUpperware.getValue());
                } else if (valUpp instanceof DoubleValueUpperware) {
                    DoubleValueUpperware doubleValUpperware = (DoubleValueUpperware) valUpp;

                    return Double.toString(doubleValUpperware.getValue());
                } else
                    System.err.println("ModelTool - assignValue - Unknown type " + var.getClass() + " The value can not be retrieved!");
            } else
                System.err.println("ModelTool - assignValue - Unknown val " + var.getId() + " The value can not be retrieved!");
        } else {
            System.err.println("ModelTool - assignValue - Unknown val " + var.getId() + " The value can not be retrieved!");
        }


        return null;
    }

    public static VariableValue getVariableValueByVariableId(String id, Solution sol) {
        for (VariableValue vv : sol.getVariableValue()) {
            if (vv.getVariable().getId().equals(id)) {
                return vv;
            }
        }

        return null;
    }

    /**
     * Gets the value from a given Numeric Value as a string
     * @param value The value
     * @return A list containing the value (0) and the canonical name of the type class (1)
     */
    public static List<String> getValueFromNumericValue(NumericValueUpperware value) {
        List<String> info = new ArrayList<String>();

        String val = null;

        if (value instanceof IntegerValueUpperware) {
            val = ((IntegerValueUpperware) value).getValue() + "";
            info.add(val);
            info.add(Integer.class.getCanonicalName());
        } else if (value instanceof FloatValueUpperware) {
            val = ((FloatValueUpperware) value).getValue() + "";
            info.add(val);
            info.add(Float.class.getCanonicalName());
        } else if (value instanceof DoubleValueUpperware) {
            val = ((DoubleValueUpperware) value).getValue() + "";
            info.add(val);
            info.add(Double.class.getCanonicalName());
        } else if (value instanceof LongValueUpperware) {
            val = ((LongValueUpperware) value).getValue() + "";
            info.add(val);
            info.add(Long.class.getCanonicalName());
        }

        return info;
    }

    public static Solution searchLastSolution(EList<Solution> solutions) {
        if (solutions.size() > 0) {
            Solution sol = solutions.get(0);

            long solDate = sol.getTimestamp();

            for (int i = 1; i < solutions.size(); i++) {
                Solution temp = solutions.get(i);

                long tempDate = temp.getTimestamp();

                if (tempDate > solDate) {
                    solDate = tempDate;
                    sol = temp;
                }

            }
            return sol;

        }

        return null;
    }

    public static VariableValue searchVariableValue(Solution sol, Variable var) {
        if (sol.getVariableValue() != null)
            for (VariableValue value : sol.getVariableValue()) {
                if (value.getVariable().getId().equals(var.getId()))
                    return value;
            }

        return null;

    }

    public static MetricVariableValue searchMetricValue(Solution sol, MetricVariable var) {
        if (sol.getMetricVariableValue() != null)
            for (MetricVariableValue value : sol.getMetricVariableValue()) {
                if (value.getVariable().getId().equals(var.getId()))
                    return value;
            }

        return null;

    }

    public static MetricVariableValue searchMetricVariableValue(Solution sol, MetricVariable var) {

        for (MetricVariableValue value : sol.getMetricVariableValue()) {
            if (value.getVariable().getId().equals(var.getId()))
                return value;
        }

        return null;

    }

    public static Solution createSolution(ConstraintProblem cp) {
        Solution sol = CpFactory.eINSTANCE.createSolution();
        sol.setTimestamp(System.currentTimeMillis());
        cp.getSolution().add(sol);

        return sol;
    }

}
