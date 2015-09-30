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
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.profiler.cp.generator.db.api.IDatabaseProxy;

/**
 * This interfaces defines services for derivation of constraint problems 
 * @author danielromero
 *
 */
public interface ICPModelDerivator 
{
	
	/**
	 * Derives a constraint problem model for the given configuration
	 * @param configuration The configuration
	 * @param database The database proxt
	 * @return The constraint problem model related to the configuration
	 */
	public ConstraintProblem derivateConstraintProblem(PaasageConfiguration configuration, IDatabaseProxy database); 

}
