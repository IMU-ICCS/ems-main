/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.profiler.cp.generator.model.derivator.api;

import eu.paasage.upperware.profiler.cp.generator.db.api.IDatabaseProxy;

/**
 * This interface defines services for creating estimators
 * @author danielromero
 *
 */
public interface IEstimatorFactory 
{
	/**
	 * Creates an stimator by using a given database proxy
	 * @param database The proxy
	 * @return The Value Estimator
	 */
	public IDimensionValueEstimator createEstimator(IDatabaseProxy database); 

}
