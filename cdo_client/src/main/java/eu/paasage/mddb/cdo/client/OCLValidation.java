/* Copyright (C) 2015 KYRIAKOS KRITIKOS <kritikos@ics.forth.gr> */

/* This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ 
 */

package eu.paasage.mddb.cdo.client;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.ocl.examples.library.oclstdlib.OCLstdlibPackage;
import org.eclipse.ocl.examples.pivot.OCL;
import org.eclipse.ocl.examples.pivot.utilities.PivotEnvironmentFactory;

@Slf4j
public class OCLValidation {

	static {
		initOcl();
	}

	private static void initOcl(){
		System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
		final ResourceSet resourceSet =new ResourceSetImpl();
		org.eclipse.ocl.examples.pivot.OCL.initialize(resourceSet);
		org.eclipse.ocl.examples.pivot.model.OCLstdlib.install();
		org.eclipse.ocl.examples.pivot.delegate.OCLDelegateDomain.initialize(resourceSet);
		org.eclipse.ocl.examples.xtext.completeocl.CompleteOCLStandaloneSetup.doSetup();
		org.eclipse.ocl.examples.xtext.oclinecore.OCLinEcoreStandaloneSetup.doSetup();
		org.eclipse.ocl.examples.xtext.oclstdlib.OCLstdlibStandaloneSetup.doSetup();
		org.eclipse.ocl.examples.domain.utilities.StandaloneProjectMap.getAdapter(resourceSet);
		
		EPackage.Registry registry = new EPackageRegistryImpl();
		registry.put(OCLstdlibPackage.eNS_URI, OCLstdlibPackage.eINSTANCE);
		registry.put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
		PivotEnvironmentFactory environmentFactory = new PivotEnvironmentFactory(registry, null);
		OCL ocl = OCL.newInstance(environmentFactory);
	}
	
	public static boolean validate(EObject obj) throws RuntimeException{
		log.info("VALIDATING EOBJECT: {}", obj);
		Diagnostic diagnostic = Diagnostician.INSTANCE.validate(obj);
		if (diagnostic.getSeverity() != Diagnostic.OK) {
			log.error("VALIDATION ERROR: {}", diagnostic);
			return false;
		}
		return true;
	}

}
