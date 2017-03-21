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

//import fr.inria.paasage.metamodel.application.PaasageConfiguration;
import org.eclipse.emf.common.util.EList;

import eu.paasage.upperware.metamodel.application.ApplicationComponent;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.application.Provider;
import eu.paasage.upperware.metamodel.types.typesPaasage.LocationUpperware;

public class TestConstraint {
	/**
	 * @param args
	 */

	public static void main(String[] args) {

        String fileName = "src/main/resources/models/SimpleConfiguration.xmi";
        String constraint = "src/main/resources/models/SimpleConfigurationConstraintProblem.xmi";
        if (args.length >= 1) {
            fileName = args[0];
        }
        System.out.println("Filename = " + fileName);

		// Loading the existing model
		ConfigurationLoad loader = new ConfigurationLoad(fileName);

		PaasageConfiguration myApplication = loader.load();
		// Accessing the model information
		System.out.println(myApplication.getId());

		// Lets see what info the application components has
		for (Iterator<ApplicationComponent> iterator = myApplication
				.getComponents().iterator(); iterator.hasNext();) {
			ApplicationComponent comp = iterator.next();
			System.out.println("Name: " + comp.getCloudMLId());	
							
			EList<LocationUpperware> list = comp.getPreferredLocations();			
			for (int i = 0; i < list.size(); i++) {
			    System.out.println("-- preferred loc: " + list.get(i).getName());
			}
		}

        // for providers
        //System.out.println("Looking at cloud providers");
        Iterator<Provider> it2 = myApplication.getProviders().iterator();
        while (it2.hasNext())
        {
            Provider p = it2.next();
            System.out.println("Provider name: " + p.getId() + " -- location: " + p.getLocation().getName()
                //+ " or " + p.getLocation()
                + " -- type: " + p.getType().getId() );
        }
        
        System.out.println();
        fileName = "src/main/resources/models/Locations.xmi";
        LocationsLoad loc = new LocationsLoad(fileName);
        EList<LocationUpperware> locList = loc.load().getLocations();
        for (int i = 0; i< locList.size(); i++) 
        {
            System.out.println("Provider loc: " + locList.get(i).getName());
        }

        //System.out.println(myApplication.getId());
        //constraintLoad(constraint);
	}

    private static void constraintLoad(String fileName)
    {
        if (fileName == null) {
            return;
        }

        try
        {

        }
        catch (Exception e) {
            System.out.println("Exception error ...");
        }
    }
}

