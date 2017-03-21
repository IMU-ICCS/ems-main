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

import eu.paasage.upperware.metamodel.application.PaasageConfiguration;

/**
 * This interface defines services for estimating values 
 * @author danielromero
 *
 */
public interface IDimensionValueEstimator 
{
	/**
	 * Computes a value for providers related to a paasage configuration
	 * @param configuration The paasage configuration
	 */
	public void estimateDimensionValue(PaasageConfiguration configuration); 

}
