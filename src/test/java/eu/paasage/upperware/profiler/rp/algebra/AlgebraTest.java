package eu.paasage.upperware.profiler.rp.algebra;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import eu.paasage.upperware.profiler.rp.algebra.exceptions.MissingVariablesException;
import eu.paasage.upperware.profiler.rp.algebra.exceptions.NotSolvableException;
import eu.paasage.upperware.profiler.rp.algebra.exceptions.WrongStatementException;

public class AlgebraTest {

	@Test
	public void testTrue() throws MissingVariablesException, WrongStatementException {
		String expression = "x >= 1 && x <= 6 && y >= 1 && y <= 4 && x < y";

		Map<String, Integer> variables = new HashMap<String, Integer>();
		variables.put("x", 2);
		variables.put("y", 3);

		boolean result = Algebra.getInstance().evaluate(expression, variables);
		assertTrue(result);
	}
	
	@Test
	public void testTrue_2() throws MissingVariablesException, WrongStatementException {
		String expression = "x >= 1";

		Map<String, Integer> variables = new HashMap<String, Integer>();
		variables.put("x", 2);

		boolean result = Algebra.getInstance().evaluate(expression, variables);
		assertTrue(result);
	}

	@Test
	public void testTrue_3() throws MissingVariablesException, WrongStatementException {
		String expression = "x > 2 && y > 2 && x + 2*y > z";
		
		Map<String, Integer> variables = new HashMap<String, Integer>();
		variables.put("x", 3);
		variables.put("y", 3);
		variables.put("z", 7);
		
		boolean result = Algebra.getInstance().evaluate(expression, variables);
		assertTrue(result);
	}

	@Test
	public void testFalse() throws MissingVariablesException, WrongStatementException {
		String expression = "x >= 2 && x <= 6 && y >= 2 && y <= 4 && x + y < 2";

		Map<String, Integer> variables = new HashMap<String, Integer>();
		variables.put("x", 2);
		variables.put("y", 3);

		boolean result = Algebra.getInstance().evaluate(expression, variables);
		assertFalse(result);
	}
	
	@Test
	public void testFalse_2() throws MissingVariablesException, WrongStatementException {
		String expression = "x >= 2 && x <= 6 && y >= 2 && y <= 4 && x + y < 2";

		Map<String, Integer> variables = new HashMap<String, Integer>();
		variables.put("x", 2);
		variables.put("y", 1);
		
		boolean result = Algebra.getInstance().evaluate(expression, variables);
		assertFalse(result);
	}

	@Test
	public void testFalse_3() throws MissingVariablesException, WrongStatementException {
		String expression = "x >= 2 && x <= 6 && y >= 2 && y <= 4 && x + y <= 2";
		
		Map<String, Integer> variables = new HashMap<String, Integer>();
		variables.put("x", 1);
		variables.put("y", 1);

		boolean result = Algebra.getInstance().evaluate(expression, variables);
		assertFalse(result);
	}

	@Test(expected = MissingVariablesException.class)
	public void testFail() throws MissingVariablesException, WrongStatementException {
		String expression = "x > 2 && y > 2 && x + 2*y > z";
		
		Map<String, Integer> variables = new HashMap<String, Integer>();
		variables.put("x", 3);
		variables.put("y", 3);
		
		Algebra.getInstance().evaluate(expression, variables);
	}

	@Test(expected = MissingVariablesException.class)
	public void testFail_2() throws MissingVariablesException, WrongStatementException {
		String expression = "x > 2 && y > 2 && x + 2*y > z";
		
		Map<String, Integer> variables = new HashMap<String, Integer>();
		variables.put("x", 3);
		variables.put("y", 3);
		variables.put("a", 3);
		
		Algebra.getInstance().evaluate(expression, variables);
	}

	@Test(expected = WrongStatementException.class)
	public void testFail_3() throws MissingVariablesException, WrongStatementException {
		String expression = "x 2";
		
		Map<String, Integer> variables = new HashMap<String, Integer>();
		variables.put("x", 3);
		variables.put("y", 3);
		variables.put("a", 3);
		
		Algebra.getInstance().evaluate(expression, variables);
	}
	
	@Test
	public void testSimplify() throws MissingVariablesException, WrongStatementException, NotSolvableException {
		String expression = "x >= 2 && x <= 6 && y >= 1 && y <= 4 && x < y";
		
		List<Variable> variables = new ArrayList<Variable>();
		variables.add(0, new Variable("x", 2, 6));
		variables.add(1, new Variable("y", 1, 4));
		
		List<Variable> ranges = Algebra.getInstance().test(expression, variables);
		// 2 <= x <= 3
		assertTrue(ranges.get(0).getFrom() == 2);
		assertTrue(ranges.get(0).getTo() == 3);
		// 3 <= y <= 4
		assertTrue(ranges.get(1).getFrom() == 3);
		assertTrue(ranges.get(1).getTo() == 4);
	}

	@Test
	public void testSimplify_2() throws MissingVariablesException, WrongStatementException, NotSolvableException {
		String expression = "x >= 2 && x <= 6 && y >= 1 && y <= 4 && x <= y";
		
		List<Variable> variables = new ArrayList<Variable>();
		variables.add(0, new Variable("x", 2, 6));
		variables.add(1, new Variable("y", 1, 4));
		
		List<Variable> ranges = Algebra.getInstance().test(expression, variables);
		// 2 <= x <= 3
		assertTrue(ranges.get(0).getFrom() == 2);
		assertTrue(ranges.get(0).getTo() == 4);
		// 3 <= y <= 4
		assertTrue(ranges.get(1).getFrom() == 2);
		assertTrue(ranges.get(1).getTo() == 4);
	}

	@Test
	public void testSimplify_3() throws MissingVariablesException, WrongStatementException, NotSolvableException {
		String expression = "db <= 6 && db >= 2 && ws >= 2 && ws >= 2*db";
		
		List<Variable> variables = new ArrayList<Variable>();
		variables.add(0, new Variable("db", 2, 6));
		variables.add(1, new Variable("ws", 2, 12));
		
		List<Variable> ranges = Algebra.getInstance().test(expression, variables);
		// 2 <= db <= 6
		assertTrue(ranges.get(0).getFrom() == 2);
		assertTrue(ranges.get(0).getTo() == 6);
		// 4 <= ws <= 12
		assertTrue(ranges.get(1).getFrom() == 4);
		assertTrue(ranges.get(1).getTo() == 12);
	}

	@Test(expected = NotSolvableException.class)
	public void testSimplify_4() throws MissingVariablesException, WrongStatementException, NotSolvableException {
		String expression = "db <= 6 && db >= 2 && ws >= 2 && ws >= 2*db && db + ws <= 5";
		
		List<Variable> variables = new ArrayList<Variable>();
		variables.add(0, new Variable("db", 2, 6));
		variables.add(1, new Variable("ws", 2, 12));
		
		Algebra.getInstance().test(expression, variables);
	}
}
