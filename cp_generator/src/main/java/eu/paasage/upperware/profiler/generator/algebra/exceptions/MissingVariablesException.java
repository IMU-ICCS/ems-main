/* 
 * Copyright (C) 2014-2015 University of Stuttgart
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.profiler.generator.algebra.exceptions;

/**
 * @author hopped
 *
 */
public class MissingVariablesException extends Exception {

	/** */
	private static final long serialVersionUID = 4814717039348094164L;

	/**
	 * 
	 * @param errorMessage
	 *            message containing an error message
	 */
	public MissingVariablesException(String errorMessage) {
		super(errorMessage);
	}

}
