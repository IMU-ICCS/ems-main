package eu.paasage.upperware.profiler.rp.algebra;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import eu.paasage.upperware.metamodel.cp.ComparatorEnum;
import eu.paasage.upperware.metamodel.cp.Expression;


public class ConditionalsTest {

	@Test
	public void testLessOrEqualsTo() throws UnsolvableException {
		Conditionals simplifier = new Conditionals();
		
		List<Expression> conditions = new ArrayList<Expression>();
		
		int xmin = 1;
		int xmax = 6;
		int ymin = 1;
		int ymax = 4;

		// #db >= 1 && #db <= 6
		conditions.add(ConditionalUtils.createConstraint("db", ComparatorEnum.GREATER_OR_EQUAL_TO, xmin));
		conditions.add(ConditionalUtils.createConstraint("db", ComparatorEnum.LESS_OR_EQUAL_TO, xmax));

		// #ws >= 1 && #ws <= 4
		conditions.add(ConditionalUtils.createConstraint("ws", ComparatorEnum.GREATER_OR_EQUAL_TO, ymin));
		conditions.add(ConditionalUtils.createConstraint("ws", ComparatorEnum.LESS_OR_EQUAL_TO, ymax));

		// #ws < #db
		conditions.add(ConditionalUtils.createRelation("ws", ComparatorEnum.LESS_OR_EQUAL_TO, "db"));

		// add all conditions
		simplifier.addConditions(conditions);
		
		// simplify conditions, and check result
		Map<String, Integer> result = simplifier.validate(xmin, xmax, ymin, ymax);
		assertTrue(result.get("x_lower") == 1);
		assertTrue(result.get("x_upper") == 4);
		assertTrue(result.get("y_lower") == 1);
		assertTrue(result.get("y_upper") == 4);
	}
	
	@Test
	public void testLessThan() throws UnsolvableException {
		Conditionals simplifier = new Conditionals();
		
		List<Expression> conditions = new ArrayList<Expression>();
		
		int xmin = 1;
		int xmax = 6;
		int ymin = 1;
		int ymax = 4;

		// #db >= 1 && #db <= 6
		conditions.add(ConditionalUtils.createConstraint("db", ComparatorEnum.GREATER_OR_EQUAL_TO, xmin));
		conditions.add(ConditionalUtils.createConstraint("db", ComparatorEnum.LESS_OR_EQUAL_TO, xmax));

		// #ws >= 1 && #ws <= 4
		conditions.add(ConditionalUtils.createConstraint("ws", ComparatorEnum.GREATER_OR_EQUAL_TO, ymin));
		conditions.add(ConditionalUtils.createConstraint("ws", ComparatorEnum.LESS_OR_EQUAL_TO, ymax));

		// #ws < #db
		conditions.add(ConditionalUtils.createRelation("ws", ComparatorEnum.LESS_THAN, "db"));

		// add all conditions
		simplifier.addConditions(conditions);
		
		// simplify conditions, and check result
		Map<String, Integer> result = simplifier.validate(xmin, xmax, ymin, ymax);
		assertTrue(result.get("x_lower") == 1);
		assertTrue(result.get("x_upper") == 3);
		assertTrue(result.get("y_lower") == 2);
		assertTrue(result.get("y_upper") == 4);
	}

}
