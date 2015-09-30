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

import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;

public class ConstraintProblemLoad {
	private URI furi;
	private ConstraintProblem constraintProblem;

	public ConstraintProblemLoad() {
		this.furi = null;
		setConstraintProblem(null);
	}

	public ConstraintProblemLoad(String fpath) {
		if (fpath == null){
			this.furi = null;
		} else {
			this.furi = URI.createURI(fpath);
		}
		
		this.constraintProblem = null;
	}

	public URI getURI() {
		return furi;
	}

	public void setURI(String fpath) {
		this.furi = URI.createURI(fpath);
	}

	public ConstraintProblem load() {
		// Initialize the model
		ApplicationPackage.eINSTANCE.eClass();
		// Register the XMI resource factory for the .xmi extension
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("xmi", new XMIResourceFactoryImpl());

		// obtain a new resource set
		ResourceSet resSet = new ResourceSetImpl();
		// get the resource
		Resource resource = resSet.getResource(furi, true);
		// Get the first model element and cast it to the right type
		ConstraintProblem myCP = (ConstraintProblem) resource.getContents()
				.get(0);
		this.constraintProblem = myCP;
		return myCP;
	}

	public ConstraintProblem getConstraintProblem() {
		return constraintProblem;
	}

	public void setConstraintProblem(ConstraintProblem constraintProblem) {
		this.constraintProblem = constraintProblem;
	}

}
