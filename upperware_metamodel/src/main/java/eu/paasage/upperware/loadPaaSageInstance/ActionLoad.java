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

import eu.paasage.upperware.metamodel.types.typesPaasage.ActionTypes;
import eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage;

public class ActionLoad {
	private URI furi;
	private ActionTypes actions;

	public ActionLoad() {
		furi = null;
		actions = null;
	}

	public ActionLoad(String fpath) {
		furi = URI.createURI(fpath);
		actions = load();
	}

	public URI getURI() {
		return furi;
	}

	public void setURI(String fpath) {
		furi = URI.createURI(fpath);
	}

	public ActionTypes load() {
		// Initialize the model
		TypesPaasagePackage.eINSTANCE.eClass();
		// Register the XMI resource factory for the .xmi extension
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("xmi", new XMIResourceFactoryImpl());

		// obtain a new resource set
		ResourceSet resSet = new ResourceSetImpl();
		// get the resource
		Resource resource = resSet.getResource(furi, true);
		// Get the first model element and cast it to the right type
		ActionTypes myActions = (ActionTypes) resource.getContents().get(0);
		return myActions;
	}

	public ActionTypes getActions() {
		return actions;
	}

	public void setActions(ActionTypes actions) {
		this.actions = actions;
	}
}
