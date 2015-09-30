/*
 * Copyright (c) 2014-5 UK Science and Technology Facilities Council
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.plangenerator.exception;
/**
 * Error associated with the {@link eu.paasage.upperware.plangenerator.model.task.ConfigurationTask <em>ConfigurationTask </em>} object.
 * <p>
 * @author Shirley Crompton
 * org     Science and Technology Facilities Council
 */
public class ConfigurationTaskException extends Exception {
	/**
	 * unique identifier of this error
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Create an instance with a specific error message.
	 * 
	 * @param message Error message to include
	 */
	public ConfigurationTaskException(String message) {
		super(message);
	}

	/**
	 * Create an instance with a specific error message and the
	 * {@link Throwable} cause.
	 * 
	 * @param message error message {@link String}
	 * @param cause {@link Throwable cause}
	 */
	public ConfigurationTaskException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Create an instance with a specific {@link Throwable} cause.
	 * 
	 * @param cause {@link Throwable} cause
	 */
	public ConfigurationTaskException(Throwable cause) {
		super(cause);
	}

	

}
