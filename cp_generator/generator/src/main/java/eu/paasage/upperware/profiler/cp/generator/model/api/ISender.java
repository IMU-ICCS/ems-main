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

import eu.paasage.upperware.metamodel.application.PaasageConfiguration;

/**
 * This interface defines the services for a sender of the paasage configuration
 * @author danielromero
 *
 */
public interface ISender 
{
	/**
	 * Sends the files (models) related to a paasage configuration
	 * @param pc The paasage configuration
	 */
	public void sendPaasageConfigurationFiles(PaasageConfiguration pc); 
	
	/**
	 * Sends the files (models) related to a paasage configuration
	 * @param id The paasage configuration id
	 */
	public void sendPaasageConfigurationFiles(String id); 

}
