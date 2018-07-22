/* Copyright (C) 2015 KYRIAKOS KRITIKOS <kritikos@ics.forth.gr> */

/* This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ 
 */

package eu.paasage.mddb.cdo.client;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.ocl.pivot.internal.resource.StandaloneProjectMap;
import org.eclipse.ocl.pivot.internal.utilities.PivotEnvironmentFactory;
import org.eclipse.ocl.pivot.oclstdlib.OCLstdlibPackage;


public class OCLValidation {
	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(OCLValidation.class);

	static {
		initOcl();
	}
	
	public OCLValidation(){
	}
	
	private static void initOcl(){
		System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
		final ResourceSet resourceSet =new ResourceSetImpl();
		org.eclipse.ocl.uml.OCL.initialize(resourceSet);
		org.eclipse.ocl.pivot.model.OCLstdlib.install();
		org.eclipse.ocl.ecore.delegate.OCLDelegateDomain.initialize(resourceSet);
		org.eclipse.ocl.xtext.completeocl.CompleteOCLStandaloneSetup.doSetup();
		org.eclipse.ocl.xtext.oclinecore.OCLinEcoreStandaloneSetup.doSetup();
		org.eclipse.ocl.xtext.oclstdlib.OCLstdlibStandaloneSetup.doSetup();
		StandaloneProjectMap map = StandaloneProjectMap.getAdapter(resourceSet);

		EPackage.Registry registry = new EPackageRegistryImpl();
		registry.put(OCLstdlibPackage.eNS_URI, OCLstdlibPackage.eINSTANCE);
		registry.put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
		PivotEnvironmentFactory environmentFactory = new PivotEnvironmentFactory(map,resourceSet);
		org.eclipse.ocl.pivot.utilities.OCL ocl = org.eclipse.ocl.pivot.utilities.OCL.newInstance(environmentFactory);
	}
	
	public static boolean validate(EObject obj) throws RuntimeException{
		log.info("VALIDATING EOBJECT: " + obj);
		Diagnostic diagnostic = Diagnostician.INSTANCE.validate(obj);
		if (diagnostic.getSeverity() != Diagnostic.OK) {
			log.error("VALIDATION ERROR: " + diagnostic);
			//System.out.println("VALIDATION ERROR: " + diagnostic);
			return false;
		}
		return true;
	}

}
