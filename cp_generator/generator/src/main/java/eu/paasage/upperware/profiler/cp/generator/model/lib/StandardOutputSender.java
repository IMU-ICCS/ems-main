/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.profiler.cp.generator.model.lib;

import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.profiler.cp.generator.db.lib.CDODatabaseProxy;
import eu.paasage.upperware.profiler.cp.generator.model.api.ISender;

/**
 * This class provides the functionality to send the id of the generate models to the standar output
 * @author danielromero
 *
 */
public class StandardOutputSender implements ISender {

	@Override
	public void sendPaasageConfigurationFiles(PaasageConfiguration pc) 
	{
		
		System.out.println(CDODatabaseProxy.CDO_SERVER_PATH+pc.getId());
	}
	
	@Override
	public void sendPaasageConfigurationFiles(String id) 
	{
		throw new UnsupportedOperationException(); 
	}

}
