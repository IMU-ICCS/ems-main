/* 
 * Copyright (C) 2014-2015 University of Stuttgart
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.profiler.generator.algebra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
@Setter
public class AlgebraVariable {

	private String variable;
	private int from;
	private int to;

	public AlgebraVariable(AlgebraVariable other) {
		this.variable = other.variable;
		this.from = other.from;
		this.to = other.to;
	}

	public static List<AlgebraVariable> cloneList(List<AlgebraVariable> list) {
		return list.stream().map(AlgebraVariable::new).collect(Collectors.toList());
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
