/*
 * Copyright (c) 2014 INRIA, INSA Rennes
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.adaptationmanager.validation;

import java.util.logging.Level;
import java.util.logging.Logger;

//import eu.paasage.upperware.adapter.adaptationmanager.plangeneration.Plan;
import eu.paasage.upperware.plangenerator.model.Plan;


public class ValidatorImpl implements IValidator {

	private final static Logger LOGGER = Logger.getLogger(ValidatorImpl.class
			.getName());

	public boolean validate(Plan p) {
//		LOGGER.log(Level.INFO, "Validated plan");
		return true;
	}
}
