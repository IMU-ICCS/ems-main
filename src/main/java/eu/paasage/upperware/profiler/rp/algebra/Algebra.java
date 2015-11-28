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

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.JexlException;
import org.apache.commons.jexl2.MapContext;

import eu.paasage.upperware.profiler.rp.algebra.exceptions.MissingVariablesException;
import eu.paasage.upperware.profiler.rp.algebra.exceptions.NotSolvableException;
import eu.paasage.upperware.profiler.rp.algebra.exceptions.WrongStatementException;

/**
 * @author hopped
 *
 */
public class Algebra {

	private static final JexlEngine jexl = new JexlEngine();
	private static Algebra INSTANCE;
	static {
		jexl.setCache(512);
		jexl.setLenient(false);
		jexl.setSilent(false);
	}

	private Algebra() {
		// singleton
	}

	public static Algebra getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Algebra();
		}
		return INSTANCE;
	}

	public boolean evaluate(
			String strExpression,
			Map<String, Integer> variables) throws MissingVariablesException, WrongStatementException
	{
		Expression expression = null;
		try {
			expression = jexl.createExpression(strExpression);
		} catch (JexlException.Parsing parsingException) {
			throw new WrongStatementException(parsingException.getMessage());
		}

		JexlContext context = new MapContext();
		for (String variable : variables.keySet()) {
			context.set(variable, variables.get(variable));
		}

		try {
			return (Boolean) expression.evaluate(context);
		} catch (JexlException.Variable jexlException) {
			throw new MissingVariablesException(jexlException.getMessage());
		}
	}

	public List<AlgebraVariable> test(String expression, List<AlgebraVariable> constraints)
			throws MissingVariablesException,
				   WrongStatementException,
				   NotSolvableException
	{
		List<AlgebraVariable> answer = AlgebraVariable.cloneList(constraints);
		boolean solvable = false;
		
		if (answer.size() > 2) {
			String message = "The current implementation does not support more than two variables.";
			throw new UnsupportedOperationException(message);
		}

		int x_lower = Integer.MAX_VALUE;
		int x_upper = -1;
		int y_lower = Integer.MAX_VALUE;
		int y_upper = -1;
		
		int xmin = answer.get(0).getFrom();
		int xmax = answer.get(0).getTo();
		int ymin = answer.get(1).getFrom();
		int ymax = answer.get(1).getTo();
		
		Map<String, Integer> variables = new HashMap<String, Integer>();
		
		for (int i = xmin; i <= xmax; ++i) {
			for (int j = ymin; j <= ymax; ++j) {
				variables.put(answer.get(0).getVariable(), i);
				variables.put(answer.get(1).getVariable(), j);
				
				if (evaluate(expression, variables)) {
					x_lower = Math.min(x_lower, i);
					x_upper = Math.max(x_upper, i);
					y_lower = Math.min(y_lower, j);
					y_upper = Math.max(y_upper, j);
					solvable = true;
				}
			}
		}
		
		if (!solvable) {
			throw new NotSolvableException();
		}
		
		answer.get(0).setFrom(x_lower);
		answer.get(0).setTo(x_upper);
		answer.get(1).setFrom(y_lower);
		answer.get(1).setTo(y_upper);
		
		return answer;
	}
}
