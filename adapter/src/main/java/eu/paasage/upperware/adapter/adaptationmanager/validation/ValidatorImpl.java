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

	public long estimatedTimeToNext = 0; // ms
	private final static Logger LOGGER = Logger.getLogger(ValidatorImpl.class
			.getName());
	private static final double alpha = 0.9;
	private long meanTaskDuration; // ms
	private long lastTime = 0; // ns
	private int count = 0;
	
	public ValidatorImpl(int duration) {
		this.meanTaskDuration = duration;
	}

	public ValidatorImpl() {
		this.meanTaskDuration = 0;
	}

	public boolean validate(Plan p) {
		boolean validation = false;
		long now = System.nanoTime();
		long timeFromPrevious = (now - lastTime) / 1000000;
		lastTime = now;

		if (++count <= 2) {
			estimatedTimeToNext=timeFromPrevious;
			validation=true;
		}
		else {
			long predictedPlanDuration = p.getTasks().size() * meanTaskDuration;
			estimatedTimeToNext = (long) (alpha * timeFromPrevious + (1 - alpha)
					* estimatedTimeToNext);
			validation = (predictedPlanDuration <= estimatedTimeToNext);
		}
		LOGGER.log(Level.INFO, "Plan validation:"+validation);
		return validation;
	}
}
