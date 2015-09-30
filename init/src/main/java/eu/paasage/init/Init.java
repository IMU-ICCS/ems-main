/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package eu.paasage.init;

import java.io.File;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.CamelPackage;
import eu.paasage.camel.deployment.DeploymentPackage;
import eu.paasage.camel.organisation.OrganisationPackage;
import eu.paasage.camel.provider.ProviderPackage;
import eu.paasage.camel.type.TypePackage;
import eu.paasage.mddb.cdo.client.CDOClient;
import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.types.TypesPackage;
import eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage;
import fr.inria.paasage.saloon.camel.mapping.MappingPackage;
import fr.inria.paasage.saloon.camel.ontology.OntologyPackage;
import fr.inria.paasage.saloon.price.model.tools.ModelTool;

/**
 * This class provides the functionality to registry a camel model in the CDO Server
 * @author danielromero
 *
 */
public class Init {

	/*
	 * ATTRIBUTES
	 */
	
	/*
	 * The CDO Client 
	 */
	private CDOClient cdoClient= new CDOClient(); 
	
	/*
	 * CONSTRUCTOR
	 */
	/**
	 *  Default constructor
	 */	
	public Init()
	{
		//appFactory= ApplicationFactory.eINSTANCE; 
		System.out.println("Init 1");
		ApplicationPackage.eINSTANCE.eClass();
		System.out.println("Init 2");
		TypesPaasagePackage.eINSTANCE.eClass(); 
		System.out.println("Init 3");
		TypesPackage.eINSTANCE.eClass(); 
		System.out.println("Init 4");
		CpPackage.eINSTANCE.eClass();
		System.out.println("Init 5");
		OntologyPackage.eINSTANCE.eClass();

		System.out.println("Init 6");
		TypePackage.eINSTANCE.eClass();
		MappingPackage.eINSTANCE.eClass();
		
		CamelPackage.eINSTANCE.eClass(); 
		
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl()); 

		registerPackages();
	}
	
	/*
	 * METHODS
	 */
	
	/**
	 * Ggets the cdo client
	 * @return CDO Client
	 */
	public CDOClient getClient()
	{
		return cdoClient;
	}
	
	/**
	 * Registers all the required packages in the CDOClient
	 */
	protected void registerPackages()
	{
		System.out.println("Registering packages");
		cdoClient.registerPackage(ApplicationPackage.eINSTANCE);
		cdoClient.registerPackage(CpPackage.eINSTANCE);
		cdoClient.registerPackage(TypesPackage.eINSTANCE);
		cdoClient.registerPackage(TypesPaasagePackage.eINSTANCE);
		cdoClient.registerPackage(OntologyPackage.eINSTANCE);
		cdoClient.registerPackage(MappingPackage.eINSTANCE);
		cdoClient.registerPackage(TypePackage.eINSTANCE);
				
		cdoClient.registerPackage(CamelPackage.eINSTANCE);
		cdoClient.registerPackage(ProviderPackage.eINSTANCE);
		
		cdoClient.registerPackage(OrganisationPackage.eINSTANCE);
		
		cdoClient.registerPackage(DeploymentPackage.eINSTANCE);
		System.out.println("Packages registered");
		
	}
	
	
	
	/**
	 * Main Method. It triggers the storing of a camel model in the database
	 * @param args [0] The camel model absolute path
	 * 			   [1] The id of the model for the CDO Server
	 */
	public static void main(String[] args) 
	{
		System.out.println("Starting");
		if(args.length==2)
		{
			File xmiFile= new File(args[0]); 
			
			if(xmiFile.isFile())
			{
				
				try
				{
					System.out.println("Creating init");
					Init init= new Init();
					
					System.out.println("Loading file "+xmiFile);
					
					Resource res=ModelTool.loadModel(xmiFile);
					
					System.out.println("File: "+res);
					
					if(res!=null)
					{
						CamelModel model= (CamelModel) res.getContents().get(0);
					
						init.getClient().storeModel(model, args[1], true);
					
						System.out.println("Model Stored!");
						
					}
					else
						System.out.println("The model cannot be loaded!");
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					System.exit(0);
				}
				
			}
			else
			{	
				System.out.println("The file does not exist!");
				System.exit(1);
			}	

		}
		else
		{	
			System.out.println("You have to specify the camel model absolute path and the id of the model for the CDO Server!");
			System.exit(1);
		}	

	}

}
