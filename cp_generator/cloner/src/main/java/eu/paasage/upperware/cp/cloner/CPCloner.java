/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.cp.cloner;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import eu.paasage.camel.CamelPackage;
import eu.paasage.camel.deployment.DeploymentPackage;
import eu.paasage.camel.organisation.OrganisationPackage;
import eu.paasage.camel.provider.ProviderPackage;
import eu.paasage.camel.type.TypePackage;
import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.types.TypesPackage;
import eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage;

/**
 * This class clones a model stored in CDO.
 */
public class CPCloner 
{
	
	//CDO
	public final static String CDO_SERVER_PATH= "upperware-models/"; 
	
	//Log
	public static Logger logger= Logger.getLogger("paasage-profiler");
	
	public static CDOClientExtended client= null; 

	public static CDOClientExtended createCDOClient()
	{
		if(client==null)
		{	
			CpPackage.eINSTANCE.eClass();
			TypesPackage.eINSTANCE.eClass(); 
			ApplicationPackage.eINSTANCE.eClass();
			TypesPaasagePackage.eINSTANCE.eClass(); 
			
			Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		    Map<String, Object> m = reg.getExtensionToFactoryMap();
		    m.put("*", new XMIResourceFactoryImpl());
			
			client= new CDOClientExtended(); 
			
			client.registerPackage(CpPackage.eINSTANCE);
			client.registerPackage(TypesPackage.eINSTANCE);
			
			client.registerPackage(ApplicationPackage.eINSTANCE);
			client.registerPackage(TypesPaasagePackage.eINSTANCE);
			
			client.registerPackage(TypePackage.eINSTANCE);
					
			client.registerPackage(CamelPackage.eINSTANCE);
			client.registerPackage(ProviderPackage.eINSTANCE);
			
			client.registerPackage(OrganisationPackage.eINSTANCE);
			
			client.registerPackage(DeploymentPackage.eINSTANCE);
		
		}

		return client; 
	}
	
	/**
	 * Clones and stores in CDO.
	 * @param id  the source identifier
	 * @param copyId  the target identifier
	 */
	public void cloneModel(String id, String copyId) {
		List<EObject> objs = cloneModel(id);
		createCDOClient().storeModelOverwritten(objs, copyId);
	}
	
	/**
	 * Clones and returns a model stored in CDO.
	 * @param id  the source identifier
	 * @return  the cloned model
	 */
	public List<EObject> cloneModel(String id) {
		List<EObject> contents= createCDOClient().getResourceContents(id);
		return contents.stream().map(EcoreUtil::copy).collect(Collectors.toList());
	}
	
	public static void main(String[] args) 
	{
		if(args.length==2)
		{
			CPCloner cpCloner= new CPCloner(); 
			
			cpCloner.cloneModel(args[0], args[1]);
			
			logger.info("CPClonent - main - Model with id "+args[0]+" cloned");
			
			System.exit(0);
			
		}
		else
			logger.error("CPClonent - main - You have to specify the model and copy Ids");

	}

}
