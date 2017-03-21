/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.profiler.cp.generator.model.camel.deploymentModel.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.net4j.connector.ConnectorException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.CamelPackage;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.DeploymentPackage;
import eu.paasage.camel.organisation.OrganisationPackage;
import eu.paasage.camel.provider.ProviderPackage;
import eu.paasage.camel.type.TypePackage;
import eu.paasage.mddb.cdo.client.CDOClient;
import eu.paasage.upperware.metamodel.application.ApplicationFactory;
import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.types.TypesPackage;
import eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage;
import eu.paasage.upperware.profiler.cp.generator.db.api.IDatabaseProxy;
import eu.paasage.upperware.profiler.cp.generator.db.lib.CDOClientExtended;
import eu.paasage.upperware.profiler.cp.generator.db.lib.CDODatabaseProxy;
import eu.paasage.upperware.profiler.cp.generator.model.camel.lib.DeploymentModelParser;
import eu.paasage.upperware.profiler.cp.generator.model.lib.PaaSageConfigurationWrapper;
import eu.paasage.upperware.profiler.cp.generator.model.tools.ModelTool;
import fr.inria.paasage.saloon.camel.mapping.MappingPackage;
import fr.inria.paasage.saloon.camel.ontology.OntologyPackage;

/**
 * Test cases for the deployment model
 * @author danielromero
 *
 */
public class DeploymentModelTest 
{
	
	/*
	 * CONSTANTS
	 */
	private static String CAMEL_FILE_SCALARM_FULL= "."+File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"examples"+File.separator+"scalarm"+File.separator+"Scalarm.xmi";
	
	private static String CAMEL_MODEL_ID="test/scalarmTest"; 
	
	/*
	 * ATTRIBUTES
	 */
	/*
	 * Passage configuration wrapper for test cases
	 */
	private PaaSageConfigurationWrapper pcw; 
	
	/*
	 * Application factory
	 */
	private static ApplicationFactory appFactory; 
	
	/*
	 * Database factory
	 */
	private static IDatabaseProxy database; 
	
	/*
	 * Deployment model
	 */
	private DeploymentModel pim; 
	
	/*
	 * Default error output
	 */
	private static PrintStream defaultErrOutput; 

	/*
	 * METHODS
	 */
	/**
	 * Registers all the required factories to load camel models
	 */
	@BeforeClass
	public static void registerFactories()
	{
		appFactory= ApplicationFactory.eINSTANCE; 
		
		ApplicationPackage.eINSTANCE.eClass();
		TypesPaasagePackage.eINSTANCE.eClass(); 
		TypesPackage.eINSTANCE.eClass(); 
		CpPackage.eINSTANCE.eClass();
		OntologyPackage.eINSTANCE.eClass();
		TypePackage.eINSTANCE.eClass();
		MappingPackage.eINSTANCE.eClass();
		
		CamelPackage.eINSTANCE.eClass(); 
		
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl()); 

		CDOClient cdoClient= new CDOClient(); 
		
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
		
		cdoClient.closeSession(); 
		

		
	}
	
	/**
	 * Creates a paasage configuration for testing
	 */
	protected void createPaaSageConfiguration()
	{
		
		PaasageConfiguration pc= appFactory.createPaasageConfiguration(); 
		
		ResourceSet resSet= new ResourceSetImpl(); 
		
		
		try{
		
			database= CDODatabaseProxy.getInstance(); 
			
			pcw= new PaaSageConfigurationWrapper(pc);
			
			
			database.loadRelatedModels(resSet, database.getExistingModelDirectory(), pcw);
			
		}
		catch(ConnectorException ex)
		{
			System.out.println("DeploymentModelTest - ERROR - createPaaSageConfiguration - Problems creating the database proxy! ");
		} 
	}
	
	/**
	 * Stores a camel model in the database
	 */
	@Before
	public void storeCamelModel()
	{
		
		File camelFile=new File(CAMEL_FILE_SCALARM_FULL);
		
		Resource res= ModelTool.loadModel(camelFile); 
		
		CamelModel cm= (CamelModel) res.getContents().get(0); 
		
		CDOClientExtended.changeStandardOutputErr();
				
		CDOClientExtended cdoClient= new CDOClientExtended(); 
		
				
		
		if(!cdoClient.existResource(CAMEL_MODEL_ID))
		{
			cdoClient.storeModel(cm, CAMEL_MODEL_ID);
		}
		
		
		cdoClient.closeSession();
		
		createPaaSageConfiguration();
		
	}
	
	/**
	 * Load the Scalarm model. Checks that it exists. 
	 */
	@Test
	public void loadCamelFileScalarmTest()
	{
		//storeCamelModel();
		File xmiFile=new File(CAMEL_FILE_SCALARM_FULL);
		
		if(xmiFile.isFile())
		{
			
			Resource resource= ModelTool.loadModel(xmiFile); 
			
			CamelModel cm= (CamelModel) resource.getContents().get(0); 
			
			Assert.assertNotNull("The model has not been loaded!",cm); 
	
		}
		else
		{
			Assert.fail("The XMI file does not exist!");
		}
	}
	
	/**
	 * Parses the deployment model of Scalarm model 
	 */
	@Test
	public void parsCamelScalarmTest()
	{
		if(database!=null)
		{	
			File xmiFile=new File(CAMEL_FILE_SCALARM_FULL);
			
			DeploymentModelParser dmp= new DeploymentModelParser();
			
			Resource resource= ModelTool.loadModel(xmiFile); 
			
			
			pim= ((CamelModel) resource.getContents().get(0)).getDeploymentModels().get(0); 
			
			dmp.parsePIM(pim, pcw);
						
			PaasageConfiguration pc= pcw.getPaasageConfiguration(); 
			
			//Providers
			Assert.assertEquals("The number of providers is not corrected!", 2,pc.getProviders().size()); 
			
			//Vms
			Assert.assertEquals("The number of VMs is not corrected!", 2, pc.getProviders().size());
			
			//Components
			Assert.assertEquals("The number of Components is not corrected!", 4, pc.getComponents().size());
		}
		else
		{
			Assert.fail("DeploymentModelTest - ERROR - parsCamelSensAppTest - The database proxy is not initilised!");
		}
	}
	
	
}
