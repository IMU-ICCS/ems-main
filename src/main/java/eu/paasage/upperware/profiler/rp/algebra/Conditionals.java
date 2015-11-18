/**
 * 
 */
package eu.paasage.upperware.profiler.rp.algebra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;

import eu.paasage.upperware.metamodel.cp.ComparatorEnum;
import eu.paasage.upperware.metamodel.cp.ComparisonExpression;
import eu.paasage.upperware.metamodel.cp.Constant;
import eu.paasage.upperware.metamodel.cp.Expression;
import eu.paasage.upperware.metamodel.cp.Variable;
import eu.paasage.upperware.metamodel.cp.impl.CpFactoryImpl;
import eu.paasage.upperware.metamodel.types.IntegerValueUpperware;
import eu.paasage.upperware.metamodel.types.impl.TypesFactoryImpl;

/**
 * @author hopped
 *
 */
public class Conditionals {

	final static CpFactoryImpl factory = new CpFactoryImpl();
	final static TypesFactoryImpl typesFactory = new TypesFactoryImpl();

	public static BiPredicate<Integer, Integer> addCondition_x(
			BiPredicate<Integer, Integer> conditions,
			ComparisonExpression expression) {
		Constant tmp = (Constant) expression.getExp2();
		int c = Integer.valueOf(((IntegerValueUpperware) tmp.getValue()).getValue());

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

	public static BiPredicate<Integer, Integer> addCondition_y(
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

	public static Map<String, Integer> validateDomain(
			BiPredicate<Integer, Integer> conditions, int xmin, int xmax,
			int ymin, int ymax) throws UnsolvableException {
		Map<String, Integer> range = new HashMap<String, Integer>();
		boolean solvable = false;

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
			throw new UnsolvableException();
		}

		return range;
	}

	public static ComparisonExpression createConstraint(String variableName,
			ComparatorEnum comparator, int constant) {
		Variable instances = factory.createVariable();
		instances.setId(variableName);

		Constant c = factory.createConstant();
		IntegerValueUpperware iv = typesFactory.createIntegerValueUpperware();
		iv.setValue(constant);
		c.setValue(iv);

		ComparisonExpression exp = factory.createComparisonExpression();
		exp.setComparator(comparator);
		exp.setExp1(instances);
		exp.setExp2(c);

		return exp;
	}
	
	private static ComparisonExpression createRelation(String leftVariableName, String rightVariableName,
			ComparatorEnum comparator) {
		Variable left = factory.createVariable();
		left.setId(leftVariableName);
		
		Variable right = factory.createVariable();
		right.setId(rightVariableName);
		
		ComparisonExpression exp = factory.createComparisonExpression();
		exp.setComparator(comparator);
		exp.setExp1(left);
		exp.setExp2(right);
		
		return exp;
	}

	public static BiPredicate<Integer, Integer> addCondition(
			String var,
			BiPredicate<Integer, Integer> conditions,
			ComparisonExpression expression
	) {
		if (expression.getExp2() instanceof Constant) {
			if (var.equals("a")) {
				conditions = addCondition_x(conditions, expression);
			} else if (var.equals("b")) {
				conditions = addCondition_y(conditions, expression);
			}
		} else if (expression.getExp2() instanceof Variable) {
			conditions = addCondition_xy(conditions, expression);
		}

		return conditions;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Expression> expressions = new ArrayList<Expression>();

		char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		List<String> variables = new ArrayList<String>();
		for (char c : alphabet) {
			variables.add(String.valueOf(c));
		}

		List<String> userVariables = new ArrayList<String>();
		userVariables.add("db");
		userVariables.add("ws");
		
		int xmin = 1;
		int xmax = 6;
		int ymin = 1;
		int ymax = 4;

		// #db >= 1 && #db <= 6
		expressions.add(createConstraint(userVariables.get(0), ComparatorEnum.GREATER_OR_EQUAL_TO, xmin));
		expressions.add(createConstraint(userVariables.get(0), ComparatorEnum.LESS_OR_EQUAL_TO, xmax));

		// #ws >= 1 && #ws <= 4
		expressions.add(createConstraint(userVariables.get(1), ComparatorEnum.GREATER_OR_EQUAL_TO, ymin));
		expressions.add(createConstraint(userVariables.get(1), ComparatorEnum.LESS_OR_EQUAL_TO, ymax));

		// #ws < #db
		expressions.add(createRelation(userVariables.get(0), userVariables.get(1), ComparatorEnum.LESS_OR_EQUAL_TO));

		Map<String, String> variableMapping = new HashMap<String, String>();
		for (int i = 0; i != userVariables.size(); ++i) {
			variableMapping.put(userVariables.get(i), variables.get(i));
		}

		// add conditions
		BiPredicate<Integer, Integer> conditions = null;
		for (Expression exp : expressions) {
			if (exp instanceof ComparisonExpression) {
				ComparisonExpression ce = (ComparisonExpression) exp;
				String var = variableMapping.get(ce.getExp1().getId());
				conditions = addCondition(var, conditions, ce);				
			}
		}

		try {
			System.out.println("\n> Validating rules (UtilityFunction)...");

			Map<String, Integer> range = validateDomain(conditions, xmin, xmax, ymin, ymax);

			System.out.println("    Solution found:");
			for (String variable : range.keySet()) {
				System.out.println("        " + variable + " = " + range.get(variable));
			}
		} catch (UnsolvableException ue) {
			System.out.println("    No solution found. Please update rules in the CAMEL model (UtilityFunction)");
		}
	}

}
