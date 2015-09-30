/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.profiler.cp.generator.model.lib;

/**
 * Abstract class the define some default method implementations for model processor
 * @author danielromero
 *
 */
public abstract class ModelProcessor 
{
	/*
	 * ATTRIBUTES
	 */
	
	/*
	 * The valid value
	 */
	protected boolean valid;

	/*
	 * METHODS 
	 */
	/**
	 * Indicates if the processor is valid
	 * @return The valid value
	 */
	public boolean isValid() {
		return valid;
	}

	
	/**
	 * Sets the valid value
	 * @param valid The new valid value
	 */
	public void setValid(boolean valid) {
		this.valid = valid;
	} 
	
	
	/**
	 * Parses the given model
	 * @param pc The PaaSage Configuration to be parsed
	 */
	public abstract void parseModel(PaaSageConfigurationWrapper pc);
	
	
	

}
