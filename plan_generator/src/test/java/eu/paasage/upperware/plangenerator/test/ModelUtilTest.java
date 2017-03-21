/*
 * Copyright (c) 2014-5 UK Science and Technology Facilities Council
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.plangenerator.test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.DeploymentFactory;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.VMRequirementSet;
import eu.paasage.camel.location.Country;
import eu.paasage.camel.location.LocationFactory;
import eu.paasage.camel.location.LocationModel;
import eu.paasage.camel.provider.ProviderFactory;
import eu.paasage.camel.requirement.LocationRequirement;
import eu.paasage.camel.requirement.OSRequirement;
import eu.paasage.camel.requirement.QuantitativeHardwareRequirement;
import eu.paasage.camel.requirement.RequirementFactory;
import eu.paasage.camel.requirement.impl.LocationRequirementImpl;
import eu.paasage.camel.type.BoolValue;
import eu.paasage.camel.type.DoublePrecisionValue;
import eu.paasage.camel.type.EnumerateValue;
import eu.paasage.camel.type.Enumeration;
import eu.paasage.camel.type.FloatsValue;
import eu.paasage.camel.type.IntegerValue;
import eu.paasage.camel.type.StringsValue;
import eu.paasage.camel.type.TypeFactory;
import eu.paasage.upperware.plangenerator.exception.ModelUtilException;
import eu.paasage.upperware.plangenerator.util.ModelUtil;

/**
 * 
 * JUnit test case for {@link eu.paasage.upper.plangenerator.util.ModelUtil <em>ModelUtil</em>} 
 * @author Shirley Crompton
 * org	UK Science and Technology Facilities Council
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ModelUtilTest {
	
	/** Message logger */
	private final static Logger log = Logger.getLogger(ModelUtilTest.class);
	/** Input file */
	private static final String MODEL_FILE = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "Scalarm_full.xmi";
	/** Camel model */
	private static CamelModel cm = null;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		if(cm != null){
			cm = null;
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	/**
	 * test loading a deployment model
	 * @see eu.paasage.upper.plangenerator.util.ModelUitl#loadDeploymentModel
	 */
	@Test
	//@Ignore
	public void testALoadDeploymentModel() {
		log.info("\n...testLoadDeploymentModel....");
		try{
			log.debug("INPUTFILE is " + MODEL_FILE);
			DeploymentModel model = ModelUtil.loadDeploymentModel(MODEL_FILE);
			assertNotNull("Failed to load Scalarm deployment model!",model);
			//
			assertEquals("incorrect deployment model loaded, got " + model.getName(),model.getName(), "ScalarmDeployment");
		}catch (Exception e){
			log.error("error loading  deployment model(" + MODEL_FILE + ") : " + e.getMessage());
			Assert.fail("Failed to load the deployment model!");
		}		
	}
	/**
	 * Test load camel utility method
	 * @see eu.paasage.upper.plangenerator.util.ModelUitl#loadCamelModel
	 */
	@Test
	//@Ignore
	public void testBLoadCamelModel(){
		try{
			log.debug("INPUTFILE is " + MODEL_FILE);
			cm = ModelUtil.loadCamelModel(MODEL_FILE);
			assertNotNull("Failed to load Scalarm camel model!",cm);
			//
			assertEquals("incorrect camel model loaded, got " + cm.getName(),cm.getName(), "ScalarmModel");
			//cm = model;
		}catch (Exception e){
			Assert.fail("Failed to load the camel model " + e.getMessage());
		}	
	}
	/**
	 * Test converting a set of locations into a list of names.
	 * @see eu.paasage.upper.plangenerator.util.ModelUitl#convertLocations
	 * Note that camel is changing the way it maps the location of an VM instance. 
	 * This method will be redundant.
	 */
	@Test
	//@Ignore
	public void testConvertLocation(){
		log.debug("Starting testConvertLocation.... ");
		LocationRequirement lr = RequirementFactory.eINSTANCE.createLocationRequirement();
		lr.setName("TEST_LOC_REC");
		LocationModel locModel = LocationFactory.eINSTANCE.createLocationModel();
		Country germany = LocationFactory.eINSTANCE.createCountry();
		germany.setName("Germany");
		germany.setId("GE");
		Country scotland = LocationFactory.eINSTANCE.createCountry();
		scotland.setName("Scotland");
		scotland.setId("SC");
		locModel.getCountries().add(scotland);
		locModel.getCountries().add(germany);
		lr.getLocations().add(scotland);
		lr.getLocations().add(germany);
		//
		List<String> locations = ModelUtil.convertLocations(lr);
		assertEquals("There should be 2 names!", 2, locations.size());
		//List is a queue
		assertEquals("first location should be Germany!", "GE", locations.get(1));
		assertEquals("second locatin should be Scotland!", "SC", locations.get(0));
	}
	/**
	 * Test combining local and global VMRequirementSets
	 * @see eu.paasage.upper.plangenerator.util.ModelUitl#addGlobalRequirements
	 */
	@Test
	//@Ignore
	public void testDAddGlobalRequirement(){
		log.debug("Starting testAddGlobalRequirement.... ");
		//
		VMRequirementSet global = DeploymentFactory.eINSTANCE.createVMRequirementSet();
		global.setName("Global_requirements");
		OSRequirement osReq = RequirementFactory.eINSTANCE.createOSRequirement();
		osReq.setName("GLOBAL_OS_REQ");
		osReq.setIs64os(true);
		osReq.setOs("Windows");
		QuantitativeHardwareRequirement global_QHR = RequirementFactory.eINSTANCE.createQuantitativeHardwareRequirement();
		global_QHR.setName("GLOBAL_QHW_REQ");
		global_QHR.setMaxCores(8);
		global_QHR.setMinCores(4);
		global_QHR.setMinStorage(1000);
		global.setQuantitativeHardwareRequirement(global_QHR);
		global.setOsOrImageRequirement(osReq);
		//
		VMRequirementSet local = DeploymentFactory.eINSTANCE.createVMRequirementSet();
		global.setName("local_requirements");
		OSRequirement localOsReq = RequirementFactory.eINSTANCE.createOSRequirement();
		localOsReq.setName("LOCAL_OS_REQ");
		localOsReq.setIs64os(true);
		localOsReq.setOs("Ubuntu");
		local.setOsOrImageRequirement(localOsReq);
		//
		VMRequirementSet combined = ModelUtil.addGlobalRequirements(global, local);
		OSRequirement combinedOS = (OSRequirement) combined.getOsOrImageRequirement();
		assertTrue("Failed to overwrite os requirement!", combinedOS.equals(localOsReq));
		QuantitativeHardwareRequirement combined_QHR = combined.getQuantitativeHardwareRequirement();
		assertEquals("Failed to include global hardware requirements (max cores)!", global_QHR.getMaxCores(), combined_QHR.getMaxCores()); 
		assertEquals("Failed to include global hardware requirements (min cores)!", global_QHR.getMinCores(), combined_QHR.getMinCores());
		assertEquals("Failed to include global hardware requirements (min storage)!", global_QHR.getMinStorage(), combined_QHR.getMinStorage());
	}
	/**
	 * Test switching Camel object type.
	 * @see eu.paasage.upper.plangenerator.util.ModelUitl#switchValue
	 */
	@Test
	//@Ignore
	public void testESwitchValues(){
		log.debug("Starting testSwitchValues.... ");
		//
		Enumeration enumeration = TypeFactory.eINSTANCE.createEnumeration();
		EnumerateValue enumerateValue = TypeFactory.eINSTANCE.createEnumerateValue();
		enumerateValue.setName("First");
		enumerateValue.setValue(0);
		enumeration.getValues().add(enumerateValue);
		assertEquals("Failed to switch enumerateValue!", enumerateValue.getName(), ModelUtil.switchValue(enumerateValue));
		//
		IntegerValue integerValue = TypeFactory.eINSTANCE.createIntegerValue();
		integerValue.setValue(100);
		assertEquals("Failed to switch integerValue!", String.valueOf(integerValue.getValue()), ModelUtil.switchValue(integerValue));
		//
		DoublePrecisionValue doubleValue = TypeFactory.eINSTANCE.createDoublePrecisionValue();
		doubleValue.setValue(888.138);
		assertEquals("Failed to switch doubleValue!", String.valueOf(doubleValue.getValue()), ModelUtil.switchValue(doubleValue));
		//
		FloatsValue floatValue = TypeFactory.eINSTANCE.createFloatsValue();
		floatValue.setValue(3.5f);
		assertEquals("Failed to switch floatValue!", String.valueOf(floatValue.getValue()), ModelUtil.switchValue(floatValue));
		//
		StringsValue stringValue = TypeFactory.eINSTANCE.createStringsValue();
		stringValue.setValue("Test String Value"); 
		assertEquals("Failed to switch stringValue!", "Test String Value", ModelUtil.switchValue(stringValue));
		//
		BoolValue boolValue = TypeFactory.eINSTANCE.createBoolValue();
		boolValue.setValue(true);
		assertEquals("Failed to switch boolValue!", String.valueOf(boolValue.isValue()), ModelUtil.switchValue(boolValue));	
	}
	/**
	 * Test getting a count of instances by deployment type objects in a {@link eu.paasage.camel.deployment.DeploymentModel <em>DeploymentModel</em>}
	 * @see eu.paasage.upper.plangenerator.util.ModelUitl#getInstanceCountByTypes
	 */
	//@Ignore
	@Test
	public void testFCountTypeInstancesScalarmFull(){
		log.debug("Starting count instance by type : Scarlam full deployment model.... ");
		//
		try {
			DeploymentModel dm = cm.getDeploymentModels().get(0);
			if(dm != null){
				Map<String,Integer> result = ModelUtil.getInstanceCountByTypes(cm.getDeploymentModels().get(0));
				assertTrue("Incorrect number of instance found for VM type : CoreIntensiveUbuntuGermany!", result.get("CoreIntensiveUbuntuGermany") == 1);
				assertTrue("Incorrect number of instance found for hositng type : ExperimentManagerToCoreIntensiveUbuntuGermany !",result.get("ExperimentManagerToCoreIntensiveUbuntuGermany") == 1);
				assertTrue("Incorrect number of instance found for internal component type : ExperimentManager!",result.get("ExperimentManager") == 1);
				assertTrue("Incorrect number of instance found for internal component type : ExperimentManager!",result.get("StorageManager") == 1);				
			}else{
				log.error("failed to get deployment model....");
			}
		} catch (ModelUtilException e) {
			// 
			Assert.fail("Failed to load the camel model: " + e.getMessage());
		}		
	}
	
	/**
	 * Test getting a count of instances by deployment type objects in a {@link eu.paasage.camel.deployment.DeploymentModel <em>DeploymentModel</em>}
	 * @see eu.paasage.upper.plangenerator.util.ModelUitl#getInstanceCountByTypes
	 */
	@Test
	public void testGCountTypeInstancesBewanFull(){
		log.debug("Starting count instance by type : Bewan full deployment model.... ");
		//
		try {
			CamelModel camel = ModelUtil.loadCamelModel("src" + File.separator + "test" + File.separator + "resources" + File.separator + "bewan-camel.xmi");
			//
			if(camel != null){
				DeploymentModel model = camel.getDeploymentModels().get(1);
				//log.debug("VMInstance count: " + model.getVmInstances().size());
				//log.debug("CompInstance count: " + model.getInternalComponentInstances().size());
				//log.debug("HostingInstance count: " + model.getHostingInstances().size());
				//log.debug("CommInstance count: " + model.getCommunicationInstances().size());
				Map<String,Integer> result = ModelUtil.getInstanceCountByTypes(model);
				assertTrue("Incorrect number of instance found for VM type : MediumComputeLowStorageUbuntu!", result.get("MediumComputeLowStorageUbuntu") == 2);
				assertTrue("Incorrect number of instance found for VM type : MediumComputeLowStorageUbuntu!", result.get("MediumComputeMediumStorageUbuntu") == 1);				
				assertTrue("Incorrect number of instance found for internal component type : WebApplication!",result.get("WebApplication") == 1);
				assertTrue("Incorrect number of instance found for internal component type : DatabaseComp!",result.get("DatabaseComp") == 1);				
				assertTrue("Incorrect number of instance found for internal component type : LoadBalancer!",result.get("LoadBalancer") == 1);				
				assertTrue("Incorrect number of instance found for hosting type : WebApplication_to_ubuntu!",result.get("WebApplication_to_ubuntu") == 1);
				assertTrue("Incorrect number of instance found for hosting type : DatabaseComp_to_ubuntu!",result.get("DatabaseComp_to_ubuntu") == 1);
				assertTrue("Incorrect number of instance found for hosting type : LoadBalancer_to_ubuntu!",result.get("LoadBalancer_to_ubuntu") == 1);
				assertTrue("Incorrect number of instance found for communication type : WEBAPPLICATIONTODATABASECOMPCOMMUNICATION!",result.get("WEBAPPLICATIONTODATABASECOMPCOMMUNICATION") == 1);
				assertTrue("Incorrect number of instance found for communication type : WEBAPPLICATIONTOLOADBALANCERCOMMUNICATION!",result.get("WEBAPPLICATIONTOLOADBALANCERCOMMUNICATION") == 1);								
			}else{
				log.error("failed to get deployment model....");
			}
		} catch (Exception e) {
			// 
			Assert.fail("Exception : " + e.getMessage());
		}
		
		
	}
}
