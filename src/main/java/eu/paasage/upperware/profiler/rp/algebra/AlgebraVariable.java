/* 
 * Copyright (C) 2014-2015 University of Stuttgart
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.profiler.rp.algebra;

import java.util.ArrayList;
import java.util.List;

public class AlgebraVariable {

	private String variable;
	private int from;
	private int to;

	public AlgebraVariable(String variable, int from, int to) {
		this.variable = variable;
		this.from = from;
		this.to = to;
	}
	
	public AlgebraVariable(AlgebraVariable other) {
		this.variable = other.variable;
		this.from = other.from;
		this.to = other.to;
	}

	public static List<AlgebraVariable> cloneList(List<AlgebraVariable> list) {
	    List<AlgebraVariable> clonedList = new ArrayList<AlgebraVariable>(list.size());

	    for (AlgebraVariable object : list) {
	        clonedList.add(new AlgebraVariable(object));
	    }

	    return clonedList;
	}

	/**
	 * @return the variable
	 */
	public String getVariable() {
		return variable;
	}

	/**
	 * @return the from
	 */
	public int getFrom() {
		return from;
	}

	/**
	 * @return the to
	 */
	public int getTo() {
		return to;
	}

	/**
	 * @param variable
	 *            name of variable within boolean expression
	 */
	public void setVariable(String variable) {
		this.variable = variable;
	}

	/**
	 * @param from
	 *            lower bound of variable
	 */
	public void setFrom(int from) {
		this.from = from;
	}

	/**
	 * @param to
	 *            upper bound of variable
	 */
	public void setTo(int to) {
		this.to = to;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return variable.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		try {
			AlgebraVariable oa = (AlgebraVariable) o;
			return variable.equals(oa.getVariable());
		} catch (Exception e) {
			return false;
		}
	}

}
