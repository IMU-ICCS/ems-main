/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.loadPaaSageInstance;

import java.util.Iterator;

import eu.paasage.upperware.metamodel.application.ApplicationComponent;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;

public class LoadTest {
	/**
	 * @param args
	 */

	public static void main(String[] args) {
		// Loading the existing model
		ConfigurationLoad loader = new ConfigurationLoad(
				"src/main/resources/models/PaasageConfiguration1.xmi");

		PaasageConfiguration myApplication = loader.load();
		// Accessing the model information
		System.out.println(myApplication.getId());

		// Lets see what info the application components has

		for (Iterator<ApplicationComponent> iterator = myApplication
				.getComponents().iterator(); iterator.hasNext();) {
			ApplicationComponent comp = iterator.next();
			System.out.println("Name : " + comp.getCloudMLId());

		}
	}

}
