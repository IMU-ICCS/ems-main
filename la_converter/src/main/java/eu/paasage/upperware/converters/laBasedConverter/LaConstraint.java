/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.converters.laBasedConverter;

/**
 * This class represents a LaBasedReasoner Constraint
 * @author danielromero
 *
 */
public class LaConstraint 
{
	/*
	 * Attributes 
	 */
	//The name of the constraint
	public String name; 
	
	//The function name
	public String functionName; 
	
	
	//The epxression rekated to the constraint
	public String expression;

	public LaConstraint(String name, String functionName, String expression) 
	{

		this.name = name;
		this.functionName = functionName;
		this.expression = expression;
	}

	public String getExpression() {
		return expression;
	} 
	
	
	

}
