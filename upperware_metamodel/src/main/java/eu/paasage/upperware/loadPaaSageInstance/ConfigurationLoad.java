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

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;

public class ConfigurationLoad {
	private URI furi;
	private PaasageConfiguration config;

	public ConfigurationLoad() {
		furi = null;
		config = null;
	}

	public ConfigurationLoad(String fpath) 
	{
		setURI(fpath);
		config = null;
	}

	public URI getURI() {
		return furi;
	}

	public void setURI(String fpath) {
		File file= new File(fpath); 
		furi = URI.createFileURI(file.getAbsolutePath());
		System.out.println(file.getAbsolutePath());
	}

	public PaasageConfiguration load() {
		// Initialize the model
		ApplicationPackage.eINSTANCE.eClass();
 		
		// Register the XMI resource factory for the .xmi extension
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl()); 

		// obtain a new resource set
		ResourceSet resSet = new ResourceSetImpl();
		// get the resource
		System.out.println(furi);
		Resource resource = resSet.getResource(furi, true);
		EcoreUtil.resolveAll(resSet);
		
		try {
			resource.load(null);
			for (Resource.Diagnostic d : resource.getWarnings()) 
            {
                System.out.println(d.toString());
            }
            
            for (Resource.Diagnostic d : resource.getErrors()) 
            {
                System.out.println(d.toString());
            }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Get the first model element and cast it to the right type
		PaasageConfiguration myApplication = (PaasageConfiguration) resource.getContents().get(0);
		this.config = myApplication ;
		return myApplication;
	}

	public PaasageConfiguration getConfig() {
		return config;
	}

	public void setConfig(PaasageConfiguration config) {
		this.config = config;
	}

}
