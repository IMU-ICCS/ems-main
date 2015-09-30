/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.profiler.cp.generator.model.api;

import eu.paasage.upperware.profiler.cp.generator.model.lib.ModelProcessor;


/**
 * This interface defines services offered by the processor factories
 * @author danielromero
 *
 */
public interface IProcessorFactory 
{
	/**
	 * Load the model in the specified path
	 * @param modelPath The path of the model
	 * @return The model processor related to the specified model path
	 */
	public ModelProcessor loadModel(String modelPath); 

}
