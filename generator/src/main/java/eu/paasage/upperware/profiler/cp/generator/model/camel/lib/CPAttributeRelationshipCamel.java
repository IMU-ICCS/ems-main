/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.profiler.cp.generator.model.camel.lib;

import choco.kernel.model.variables.integer.IntegerVariable;
import eu.paasage.camel.type.SingleValue;;

/**
 * Auxiliar Class. This class define relationship between a value and a Integer Variable in Choco.  
 * @author danielromero
 *
 */
public class CPAttributeRelationshipCamel 
{
	/*
	 * ATTRIBUTES
	 */
	/*
	 * The value
	 */
	protected SingleValue value; 
	
	/*
	 * The choco variable 
	 */
	protected IntegerVariable variable;

	/*
	 * CONSTRUCTOR
	 */
	/**
	 * The choco variable
	 * @param value The value
	 * @param variable The choco variable
	 */
	public CPAttributeRelationshipCamel(SingleValue value, IntegerVariable variable) 
	{
		super();
		this.value = value;
		this.variable = variable;
	}

	/*
	 * METHODS
	 */
	/**
	 * Returns the value
	 * @return The value
	 */
	public SingleValue getValue() {
		return value;
	}

	/**
	 * Returns the variable
	 * @return The variable
	 */
	public IntegerVariable getVariable() {
		return variable;
	} 
	
	

}
