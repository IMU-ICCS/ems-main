/* 
 * Copyright (C) 2014-2015 University of Stuttgart
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.profiler.rp.algebra;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;

import eu.paasage.upperware.metamodel.cp.ComparisonExpression;
import eu.paasage.upperware.metamodel.cp.Constant;
import eu.paasage.upperware.metamodel.cp.Expression;
import eu.paasage.upperware.metamodel.cp.Variable;
import eu.paasage.upperware.metamodel.types.IntegerValueUpperware;

/**
 * @author hopped
 *
 */
public class Conditionals {
	
	private BiPredicate<Integer, Integer> conditions = null;

	public void addConditions(List<Expression> expressions) {
		for (Expression expression : expressions) {
			// currently allows only ComparisonExpressions
			if (expression instanceof ComparisonExpression) {
				ComparisonExpression ce = (ComparisonExpression) expression;
				// we only support currently two variables (x, y)
				if (ce.getExp2() instanceof Constant) {
					String var = ce.getExp1().getId();
					// e.g. (x, y) -> x >= 2
					if (var.equals("a")) {
						conditions = addCondition_x(conditions, ce);
					// e.g. (x, y) -> y < 6
					} else if (var.equals("b")) {
						conditions = addCondition_y(conditions, ce);
					}
				// second argument is also a variable, e.g., (x, y) -> x < y
				} else if (ce.getExp2() instanceof Variable) {
					conditions = addCondition_xy(conditions, ce);
				}
			}
		}
	}
	
	public void reset() {
		conditions = null;
	}
	
	private BiPredicate<Integer, Integer> addCondition_x(
			BiPredicate<Integer, Integer> conditions,
			ComparisonExpression expression) {
		Constant tmp = (Constant) expression.getExp2();
		int c = Integer.valueOf(((IntegerValueUpperware) tmp.getValue())
				.getValue());

		switch (expression.getComparator()) {
		case DIFFERENT:
			conditions = (x, y) -> x != c;
			break;
		case EQUAL_TO:
			conditions = (x, y) -> x == c;
			break;
		case GREATER_OR_EQUAL_TO:
			conditions = (x, y) -> x >= c;
			break;
		case GREATER_THAN:
			conditions = (x, y) -> x > c;
			break;
		case LESS_OR_EQUAL_TO:
			conditions = (x, y) -> x <= c;
			break;
		case LESS_THAN:
			conditions = (x, y) -> x < c;
			break;
		default:
			break;

		}

		return conditions;
	}

	private BiPredicate<Integer, Integer> addCondition_y(
			BiPredicate<Integer, Integer> conditions,
			ComparisonExpression expression) {
		Constant tmp = (Constant) expression.getExp2();
		int c = Integer.valueOf(((IntegerValueUpperware) tmp.getValue()).getValue());

		switch (expression.getComparator()) {
		case DIFFERENT:
			conditions = (x, y) -> y != c;
			break;
		case EQUAL_TO:
			conditions = (x, y) -> y == c;
			break;
		case GREATER_OR_EQUAL_TO:
			conditions = (x, y) -> y >= c;
			break;
		case GREATER_THAN:
			conditions = (x, y) -> y > c;
			break;
		case LESS_OR_EQUAL_TO:
			conditions = (x, y) -> y <= c;
			break;
		case LESS_THAN:
			conditions = (x, y) -> y < c;
			break;
		default:
			break;

		}

		return conditions;
	}

	private static BiPredicate<Integer, Integer> addCondition_xy(
			BiPredicate<Integer, Integer> conditions,
			ComparisonExpression expression) {

		switch (expression.getComparator()) {
		case DIFFERENT:
			conditions = (x, y) -> x != y;
			break;
		case EQUAL_TO:
			conditions = (x, y) -> x == y;
			break;
		case GREATER_OR_EQUAL_TO:
			conditions = (x, y) -> x >= y;
			break;
		case GREATER_THAN:
			conditions = (x, y) -> x > y;
			break;
		case LESS_OR_EQUAL_TO:
			conditions = (x, y) -> x <= y;
			break;
		case LESS_THAN:
			conditions = (x, y) -> x < y;
			break;
		default:
			break;

		}

		return conditions;
	}

	public Map<String, Integer> validate(int xmin, int xmax, int ymin, int ymax) throws UnsolvableException {
		Map<String, Integer> range = new HashMap<String, Integer>();
		boolean solvable = false;
		
		System.out.println("\n> Validating rules (UtilityFunction)...");

		int x_lower = Integer.MAX_VALUE;
		int x_upper = -1;
		int y_lower = Integer.MAX_VALUE;
		int y_upper = -1;

		for (int i = xmin; i <= xmax; ++i) {
			for (int j = ymin; j <= ymax; ++j) {
				if (conditions.test(i, j)) {
					x_lower = Math.min(x_lower, i);
					x_upper = Math.max(x_upper, i);
					y_lower = Math.min(y_lower, j);
					y_upper = Math.max(y_upper, j);
					solvable = true;
				}
			}

			range.put("x_lower", x_lower);
			range.put("x_upper", x_upper);
			range.put("y_lower", y_lower);
			range.put("y_upper", y_upper);
		}

		if (!solvable) {
			System.out.println("    Constraints not solvable. Please refine them (UtilityFunction)");
			throw new UnsolvableException();
		}
		
		System.out.println("    Solution found:");
		for (String variable : range.keySet()) {
			System.out.println("        " + variable + " = " + range.get(variable));
		}

		return range;
	}

}
