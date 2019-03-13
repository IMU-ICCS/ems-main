/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package eu.melodic.upperware.solvertodeployment.exception;

public class S2DException extends RuntimeException {

	private static final long serialVersionUID = 6637252123704611830L;

	public S2DException(String message) {
		super(message);
	}

	public S2DException(String message, Throwable cause) {
		super(message, cause);
	}

}
