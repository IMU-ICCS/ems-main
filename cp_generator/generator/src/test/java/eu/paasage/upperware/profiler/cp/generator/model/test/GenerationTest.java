/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.profiler.cp.generator.model.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.CamelPackage;
import eu.paasage.camel.deployment.DeploymentPackage;
import eu.paasage.camel.organisation.OrganisationPackage;
import eu.paasage.camel.provider.ProviderPackage;
import eu.paasage.camel.type.TypePackage;
import eu.paasage.mddb.cdo.client.CDOClient;
import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.types.TypesPackage;
import eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage;
import eu.paasage.upperware.profiler.cp.generator.db.api.IDatabaseProxy;
import eu.paasage.upperware.profiler.cp.generator.db.lib.CDOClientExtended;
import eu.paasage.upperware.profiler.cp.generator.db.lib.CDODatabaseProxy;
import eu.paasage.upperware.profiler.cp.generator.model.lib.GenerationOrchestrator;
//import eu.paasage.upperware.profiler.cp.generator.model.lib.ModelFileInfo;
import eu.paasage.upperware.profiler.cp.generator.model.tools.Constants;
import eu.paasage.upperware.profiler.cp.generator.model.tools.ModelTool;
import eu.paasage.upperware.profiler.cp.generator.model.tools.PaaSagePropertyManager;
import fr.inria.paasage.saloon.camel.mapping.MappingPackage;
import fr.inria.paasage.saloon.camel.ontology.OntologyPackage;

/**
 * Test cases for the camel model
 * @author danielromero
 *
 */
public class GenerationTest 
{
//	/*
//	 * CONSTANTS
//	 */
//	//private static String CAMEL_FILE_SENS_APP= "."+File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"examples"+File.separator+"sensApp"+File.separator+"SensApp.xmi";
//
//	private static String CAMEL_FILE_SCALARM_FULL= "."+File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"examples"+File.separator+"scalarm"+File.separator+"Scalarm.xmi";
//
//	private static String CAMEL_MODEL_ID_FULL="test/scalarmTest";
//
//	/*
//	 * ATTRIBUTES
//	 */
//	/*
//	 * The generator orchestrator for testing
//	 */
//	private GenerationOrchestrator orchestrator;
//
//
//	/**
//	 * Registers all factories to load camel models
//	 */
//	@BeforeClass
//	public static void registerFactories()
//	{
//		//appFactory= ApplicationFactory.eINSTANCE;
//		ApplicationPackage.eINSTANCE.eClass();
//		TypesPaasagePackage.eINSTANCE.eClass();
//		TypesPackage.eINSTANCE.eClass();
//		CpPackage.eINSTANCE.eClass();
//		OntologyPackage.eINSTANCE.eClass();
//
//		TypePackage.eINSTANCE.eClass();
//		MappingPackage.eINSTANCE.eClass();
//
//		CamelPackage.eINSTANCE.eClass();
//
//		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
//
//		CDOClient cdoClient= new CDOClient();
//
//		cdoClient.registerPackage(ApplicationPackage.eINSTANCE);
//		cdoClient.registerPackage(CpPackage.eINSTANCE);
//		cdoClient.registerPackage(TypesPackage.eINSTANCE);
//		cdoClient.registerPackage(TypesPaasagePackage.eINSTANCE);
//		cdoClient.registerPackage(OntologyPackage.eINSTANCE);
//		cdoClient.registerPackage(MappingPackage.eINSTANCE);
//		cdoClient.registerPackage(TypePackage.eINSTANCE);
//
//		cdoClient.registerPackage(CamelPackage.eINSTANCE);
//		cdoClient.registerPackage(ProviderPackage.eINSTANCE);
//
//		cdoClient.registerPackage(OrganisationPackage.eINSTANCE);
//
//		cdoClient.registerPackage(DeploymentPackage.eINSTANCE);
//
//		cdoClient.closeSession();
//
//	}
//
//	/**
//	 * Stores a camel model in the database
//	 */
//	@Before
//	public void storeCamelModels()
//	{
//		PaaSagePropertyManager.getInstance().addCPGeneratorProperty(Constants.FILE_NAME_SENDER_PROPERTY_NAME, "."+File.separator+"cpGeneratorOutput.txt");
//
//		//Full
//		File camelFileFull=new File(CAMEL_FILE_SCALARM_FULL);
//
//		Resource resFull= ModelTool.loadModel(camelFileFull);
//
//		CamelModel cmFull= (CamelModel) resFull.getContents().get(0);
//
//		CDOClientExtended cdoClient= new CDOClientExtended();
//
//		if(!cdoClient.existResource(CAMEL_MODEL_ID_FULL))
//		{
//			cdoClient.storeModel(cmFull, CAMEL_MODEL_ID_FULL);
//		}
//
//		 orchestrator= new GenerationOrchestrator();
//
//
//	}
//
//	/**
//	 * Verifies that the generated model exists
//	 */
//	@Test
//	public void searchProviderScalarmTest()
//	{
//
//		GenerationOrchestrator orchestrator= new GenerationOrchestrator(); //The object to execute the test
//
//		List<ModelFileInfo> mfinfos= new ArrayList<ModelFileInfo>(); //The list of model files
//
//		ModelFileInfo camelModelFileInfo= new ModelFileInfo(CAMEL_MODEL_ID_FULL, Constants.CAMEL_MODEL_TYPE);
//
//		mfinfos.add(camelModelFileInfo);
//
//		String id= orchestrator.generateCPModel(mfinfos);
//
//		//Check that nothing has been generated
//		Assert.assertTrue("The cp model file does not exist", orchestrator.getDatabase().existCPModel(id));
//	}
//
//	/**
//	 * Verifies providers and components in the generated model
//	 */
//	@Test
//	public void searchProviderScalarmFullTest()
//	{
//
//		GenerationOrchestrator orchestrator= new GenerationOrchestrator(); //The object to execute the test
//
//		List<ModelFileInfo> mfinfos= new ArrayList<ModelFileInfo>(); //The list of model files
//
//		ModelFileInfo camelModelFileInfo= new ModelFileInfo(CAMEL_MODEL_ID_FULL, Constants.CAMEL_MODEL_TYPE);
//
//		mfinfos.add(camelModelFileInfo);
//
//		String id= orchestrator.generateCPModel(mfinfos);
//
//		System.out.println("The id "+id);
//
//		//Check that everything has been generated
//		verifyGeneratedModels(id, orchestrator.getDatabase());
//	}
//
//
//
//	/**
//	 * Checks the generated models
//	 * @param paasageConfigurationID The paasage configuration id
//	 * @param database The proxy
//	 */
//	protected void verifyGeneratedModels(String paasageConfigurationID, IDatabaseProxy database)
//	{
//
//		//Check the existence of the CP Model File
//
//
//		Assert.assertTrue("The cp model file does not exist", database.existCPModel(paasageConfigurationID));
//
//		//Check the existence of paasage configuration file
//
//		Assert.assertTrue("The PaaSage Configuration Model file does not exist",database.existPaaSageConfigurationModel(paasageConfigurationID));
//
//
//		//Check that the Paasage Configuration Model is correct
//
//		PaasageConfiguration pc= database.loadPaaSageConfigurationModel(paasageConfigurationID); //TODO I CAN NOT RETRIEVE BOTH MODELS EVEN IF THEY ARE IN THE DATABASE
//
//		//Providers
//		Assert.assertEquals("The number of providers is not corrected!", 1,pc.getProviders().size());
//
//		//Vms
//		Assert.assertEquals("The number of VMs is not corrected!", 1, pc.getProviders().size());
//
//		//Components
//		Assert.assertEquals("The number of Components is not corrected!", 4, pc.getComponents().size());
//
//
//		//Check the CP Model
//		ConstraintProblem cp= database.loadCPModel(paasageConfigurationID);
//
//		//Constraints
//		Assert.assertEquals("The number of Constraints is not corrected!",13, cp.getConstraints().size());
//
//		//Variables
//		Assert.assertEquals("The number of Varaibles is not corrected!",4, cp.getVariables().size());
//
//	}
//

	
}
