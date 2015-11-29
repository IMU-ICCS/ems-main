/* 
 * Copyright (C) 2014-2015 University of Stuttgart
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.profiler.rp.algebra;

import java.util.List;
import java.util.Map;

import eu.paasage.upperware.metamodel.cp.ComparatorEnum;
import eu.paasage.upperware.metamodel.cp.ComparisonExpression;
import eu.paasage.upperware.metamodel.cp.ComposedExpression;
import eu.paasage.upperware.metamodel.cp.Constant;
import eu.paasage.upperware.metamodel.cp.Expression;
import eu.paasage.upperware.metamodel.cp.OperatorEnum;
import eu.paasage.upperware.metamodel.cp.Variable;
import eu.paasage.upperware.metamodel.types.DoubleValueUpperware;
import eu.paasage.upperware.metamodel.types.IntegerValueUpperware;

/**
 * @author hopped
 *
 */
public class ExpressionUtils {

	public static String toString(
			ComparisonExpression expression,
			List<AlgebraVariable> varList,
			Map<AlgebraVariable, Integer> varLeftMap,
			Map<AlgebraVariable, Integer> varRightMap)
	{
		StringBuilder strExpression = new StringBuilder();

		/* handle exp1 */
		strExpression.append(expressionToString(expression.getExp1(), varList, varLeftMap));
		/* handle operator */
		strExpression.append(" ");
		strExpression.append(comparatorToString(expression.getComparator()));
		/* handle exp2 */
		strExpression.append(" ");
		strExpression.append(expressionToString(expression.getExp2(), varList, varRightMap));
		
		return strExpression.toString();
	}
	
	private static String expressionToString(
			Expression exp1,
			List<AlgebraVariable> varList,
			Map<AlgebraVariable, Integer> varMap)
	{
		if (exp1 instanceof ComposedExpression) {
			ComposedExpression composed = (ComposedExpression) exp1;
			for (Expression ex : composed.getExpressions()) {
				if (ex instanceof Variable) {
					Variable var = (Variable) ex;
					String id = var.getId();
					try {
						id = id.split("_")[3];
					} catch (Exception e) {
						System.out.println("[WARNING] Error while reading component name (" + id + ")");
					}
					/*
					 * We have to skip reading the domain range, because
					 * CP generator always generates 0 as minimum value
					int min = 1;
					int max = 1;
					Domain d = var.getDomain();
					if (d != null) {
						if (d instanceof RangeDomain) {
							RangeDomain rd = (RangeDomain) d;
							IntegerValueUpperware n_min = (IntegerValueUpperware) rd.getFrom(); // always 0
							min = n_min.getValue();
							IntegerValueUpperware n_max = (IntegerValueUpperware) rd.getTo();
							max = n_max.getValue();
						}
					}
					*/
					for (AlgebraVariable candidate : varList) {
						if (candidate.getVariable().equals(id)) {
							Integer value = varMap.get(candidate);
							if (value == null) {
								value = 0;
							}
							varMap.put(candidate, ++value);
						}
					}
				}
			}
			String operator = operatorToString(composed.getOperator());
			
			StringBuilder answer = new StringBuilder();
			java.util.Iterator<AlgebraVariable> iter = varMap.keySet().iterator();
			while (iter.hasNext()) {
				AlgebraVariable av = iter.next();
				answer.append(av.getVariable());
				if (iter.hasNext()) {
					answer.append(" ");
					answer.append(operator);
					answer.append(" ");
				}
			}
			return answer.toString();
			
		} else if (exp1 instanceof Constant) {
			Constant c = (Constant) exp1;
			if (c.getValue() instanceof IntegerValueUpperware) {
				IntegerValueUpperware ivu = (IntegerValueUpperware) c.getValue();
				return String.valueOf(ivu.getValue());
			} else if (c.getValue() instanceof DoubleValueUpperware) {
				DoubleValueUpperware dvu = (DoubleValueUpperware) c.getValue();
				return String.valueOf(dvu.getValue());
			} else {
				throw new UnsupportedOperationException();
			}
		} else {
			System.out.println("ERROR: unsupported expression: " + exp1.getId());
			throw new UnsupportedOperationException();
		}
	}
	
	
	public static String operatorToString(OperatorEnum operator) {
		switch (operator) {
		case DIV:
			return "/";
		case MEAN:
			throw new UnsupportedOperationException();
		case MINUS:
			return "-";
		case PLUS:
			return "+";
		case TIMES:
			return "*";
		default:
			throw new UnsupportedOperationException();
		}
	}
	
	public static String comparatorToString(ComparatorEnum comparator) {
		switch (comparator) {
		case DIFFERENT:
			return "!=";
		case EQUAL_TO:
			return "==";
		case GREATER_OR_EQUAL_TO:
			return ">=";
		case GREATER_THAN:
			return ">";
		case LESS_OR_EQUAL_TO:
			return "<=";
		case LESS_THAN:
			return "<";
		default:
			throw new UnsupportedOperationException();
		}
	}

}
