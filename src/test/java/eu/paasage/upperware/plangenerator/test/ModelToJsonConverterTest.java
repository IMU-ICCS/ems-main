/*
 * Copyright (c) 2014-5 UK Science and Technology Facilities Council
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.plangenerator.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.eclipsesource.json.JsonObject;

import eu.paasage.camel.Application;
import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.Communication;
import eu.paasage.camel.deployment.CommunicationInstance;
import eu.paasage.camel.deployment.Configuration;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.Hosting;
import eu.paasage.camel.deployment.HostingInstance;
import eu.paasage.camel.deployment.InternalComponent;
import eu.paasage.camel.deployment.InternalComponentInstance;
import eu.paasage.camel.deployment.ProvidedCommunication;
import eu.paasage.camel.deployment.ProvidedCommunicationInstance;
import eu.paasage.camel.deployment.ProvidedHost;
import eu.paasage.camel.deployment.ProvidedHostInstance;
import eu.paasage.camel.deployment.RequiredCommunication;
import eu.paasage.camel.deployment.RequiredCommunicationInstance;
import eu.paasage.camel.deployment.VM;
import eu.paasage.camel.deployment.VMInstance;
import eu.paasage.camel.organisation.OrganisationModel;
import eu.paasage.mddb.cdo.client.CDOClient;
import eu.paasage.upperware.plangenerator.exception.ModelUtilException;
import eu.paasage.upperware.plangenerator.util.ModelToJsonConverter;
import eu.paasage.upperware.plangenerator.util.ModelUtil;

/**
 * JUnit test case for {@link eu.paasage.upperware.plangenerator.util.ModelToJsonConverter <em>ModelToJsonConverter</em>} 
 * @author Shirley Crompton
 * org	UK Science and Technology Facilities Council
 *
 */
public class ModelToJsonConverterTest {
	/** logger */
	private final static Logger LOG = Logger.getLogger(ModelToJsonConverterTest.class);	
	/** target camel xmi file */
	//private static String TARGET_IN = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "Scalarm_V2.xmi";
	private static String TARGET_IN = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "ScalarmModel.xmi"; //kyriakos' version
	//updated on 26 August 2015 to use 2 separate files : 1 for main camel model, 1 for the crossed ref'ed provider model
	//private static String TARGET_IN = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "test.xmi"; //
	//private static String TARGET_PM_IN = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "upperware-models_fms_1436444254010_GWDG-DE-1436444254477.xmi"; //
	/** deployment model extracted from the camel xmi file */
	private static DeploymentModel dm;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//read the file
		dm = ModelUtil.loadDeploymentModel(TARGET_IN);
		//CamelModel cModel = (CamelModel) CDOClient.loadModel(TARGET_IN);
		//CamelModel pModel = (CamelModel) CDOClient.loadModel(TARGET_PM_IN);
		//dm = cModel.getDeploymentModels().get(0);
		LOG.debug("Loaded deployment model from : " + TARGET_IN);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		if(dm != null){
			dm = null;
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
	 * Test getting a {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} representation of a 
	 * {@link eu.paasage.camel.Application <em>Application</em>}
	 * <p>
	 * @see eu.paasage.upperware.plangenerator.util.ModelToJsonConverter#convertApp
	 */
	@Test
	public void testConvertApplication() {
		//debug
		LOG.debug("...testing convertApplication....");
		Application app = ((CamelModel) dm.eContainer()).getApplications().get(0);
		//
		JsonObject appObj = ModelToJsonConverter.convertApp(app);
		//
		assertEquals("Application name is not the same!", app.getName(), appObj.get("name").asString());
		//description may be null
		if(app.getDescription() != null){
			assertEquals("Description is not the same!", app.getDescription(), appObj.get("description").asString());
		}
		assertEquals("Version is not the same!", app.getVersion(), appObj.get("version").asString());
		//		
		String org = ((OrganisationModel) app.getOwner().eContainer()).getName();
		assertEquals("Owner is not the same!", org, appObj.get("owner").asString());		
	}
	/**
	 * Test getting a {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} representation of a 
	 * {@link eu.paasage.camel.Application <em>Application</em>} instance object.
	 * <p>
	 * @see eu.paasage.upperware.plangenerator.util.ModelToJsonConverter#convertAppInstance
	 */
	@Test
	public void testConvertAppInstance(){
		//debug
		LOG.debug("...testing convertAppInstance....");
		Application app = ((CamelModel) dm.eContainer()).getApplications().get(0);
		//
		JsonObject appObj = ModelToJsonConverter.convertAppInstance(app);
		//
		assertEquals("AppInstance name is not the same!", app.getName() + "Instance", appObj.get("name").asString());
		assertEquals("Application type is not the same!", app.getName(), appObj.get("type").asString());
	}
	/**
	 * Test getting a {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} representation of a 
	 * {@link eu.paasage.camel.deployment.VM <em>VM</em>} object.
	 * <p>
	 * @see eu.paasage.upperware.plangenerator.util.ModelToJsonConverter#convertVM
	 */
	@Test
	public void testConvertVM(){
		//
		VM vm = dm.getVms().get(0);
		//debug
		LOG.debug("...testing convertVM(" + vm.getName() + "....");
		//
		JsonObject appObj = ModelToJsonConverter.convertVM(vm);
		//
		assertEquals("VM name is not the same!", vm.getName(), appObj.get("name").asString());
		try {
			//configuration			
			Configuration config = null;
	    	EList<Configuration> resources = vm.getConfigurations(); //need to do it in 2 steps, you may get an empty EList...
			if(resources != null && !resources.isEmpty()){
				config = resources.get(0);	
			}//end if resources != null
			if(config != null){
				//check it out
				testConfiguration(config, appObj, "");
			}
			//VMRequirements, hardcode expectations ;)
			assertEquals("os is different!", "Ubuntu", appObj.get("os").asString());
			//assertTrue("is64bt is different!", appObj.get("os64bit").asBoolean());
			//
			List<ProvidedHost> vmphs = vm.getProvidedHosts();
			if(vmphs != null && !vmphs.isEmpty()){
				List<String> names = ModelUtil.convertJsonArrayToList(appObj.get("providedHosts").asArray());
				int counter = 0;
				for(ProvidedHost vmph : vmphs){
					//should have at least 1 provided host
					assertEquals("VM ProvidedHost name is not the same!",vmph.getName(), names.get(counter));
					counter++;
				}
			}
			List<ProvidedCommunication> vmpcs = vm.getProvidedCommunications(); 
			if(vmpcs != null && !vmpcs.isEmpty()){
				List<String> names = ModelUtil.convertJsonArrayToList(appObj.get("providedCommunications").asArray());				
				int counter = 0;				
				for(ProvidedCommunication vmpc : vmpcs){
					//should have at least 1 provided host
					assertEquals("VM ProvidedCom name is not the same!",vmpc.getName(), names.get(counter));					
					counter++;
				}
			}
		} catch (ModelUtilException e) {
			// 
			fail("Error test converting VM : " + e);
		} catch (Exception e) {
			fail("Error test converting VM : " + e);
		}
	}
	/**
	 * Test getting a {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} representation of a 
	 * {@link eu.paasage.camel.deployment.VMInstance <em>VMInstance</em>} object.
	 * <p>
	 * @see eu.paasage.upperware.plangenerator.util.ModelToJsonConverter#convertVMInstance
	 */
	@Test
	public void testConvertVMInstance(){	
		//
		VMInstance vmInstance = dm.getVmInstances().get(0);
		//debug
		LOG.debug("...testing convertVM(" + vmInstance.getName() + "....");
		//
		JsonObject appObj = ModelToJsonConverter.convertVMInstance(vmInstance);
		//debug
		System.out.println("vm instance object : \n" + appObj.toString() + "\n");
		
		//some expectations are hardcoded!
		assertEquals("VMInstance name is not the same!", vmInstance.getName(), appObj.get("name").asString());
		assertEquals("VMInstance type is not the same!", vmInstance.getType().getName(), appObj.get("type").asString());	
		
		//assertEquals("VMInstance vmType is not the same!", "C1.XXLARGE", appObj.get(vmInstance.getVmType().getName()).asString());
		//the following two items are subject to changes in the Camel model
		//13 July 2015 has commented these 2 items out, as they cannot be extracted from the main model file, we need a live model wrapped in a cdo transaction
		//these two items are in the providerProvider produced by the CP Generator
		//assertEquals("VMInstance cloud is not the same!", "GWDG", appObj.get("cloud").asString());
		//assertEquals("VMInstance driver is not the same!", "openstack-nova", appObj.get("driver").asString());
		//assertEquals("VMInstance endpoint is not the same!", "https://api.cloud.gwdg.de:5000/v2.0/", appObj.get("endpoint").asString());
		//assertEquals("VMInstance username is not the same!", "username-gwdg", appObj.get("username").asString());
		//assertEquals("VMInstance user password is not the same!", "password", appObj.get("password").asString());
		try {
			//List<String> locs = ModelUtil.convertJsonArrayToList(appObj.get("locations").asArray());
			//assertEquals("VMInstance locations is not the same!", "Germany", locs.get(0));
			//
			List<ProvidedHostInstance> vmphs = vmInstance.getProvidedHostInstances();
			if(vmphs != null && !vmphs.isEmpty()){
				List<String> names = ModelUtil.convertJsonArrayToList(appObj.get("providedHostInstances").asArray());
				int counter = 0;
				for(ProvidedHostInstance vmph : vmphs){
					//should have at least 1 provided host
					assertEquals("VM ProvidedHostInstance name is not the same!",vmph.getName(), names.get(counter));
					counter++;
				}
			}
			List<ProvidedCommunicationInstance> vmpcs = vmInstance.getProvidedCommunicationInstances(); 
			if(vmpcs != null && !vmpcs.isEmpty()){
				List<String> names = ModelUtil.convertJsonArrayToList(appObj.get("providedCommunicationInstances").asArray());				
				int counter = 0;				
				for(ProvidedCommunicationInstance vmpc : vmpcs){
					//should have at least 1 provided host
					assertEquals("VM ProvidedComInstance name is not the same!",vmpc.getName(), names.get(counter));					
					counter++;
				}
			}
		} catch (ModelUtilException e) {
			// 
			fail("Error test converting VM Instance : " + e);
		}
	}
	/**
	 * Test getting a {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} representation of a 
	 * {@link eu.paasage.camel.deployment.InternalComponent <em>InternalComponent</em>} object.
	 * <p>
	 * @see eu.paasage.upperware.plangenerator.util.ModelToJsonConverter#convertInternalComponent
	 */
	@Test
	public void testConvertInternalComponent(){		
		//
		InternalComponent comp = dm.getInternalComponents().get(0);
		//debug
		LOG.debug("...testing convertInternalComponent(" + comp.getName() + "....");
		//
		JsonObject appObj = ModelToJsonConverter.convertInternalComponent(comp);
		//
		assertEquals("Internal component name is not the same!", comp.getName(), appObj.get("name").asString());
		assertEquals("Internal component required host is not the same!", comp.getRequiredHost().getName(), appObj.get("requiredHost").asString());
		try {
			//configuration			
			Configuration config = null;
	    	EList<Configuration> resources = comp.getConfigurations(); //need to do it in 2 steps, you may get an empty EList...
			if(resources != null && !resources.isEmpty()){
				config = resources.get(0);	
			}//end if resources != null
			if(config != null){
				//check it out
				testConfiguration(config, appObj, "");
			}
			List<ProvidedHost> comphs = comp.getProvidedHosts();
			if(comphs != null && !comphs.isEmpty()){
				List<String> names = ModelUtil.convertJsonArrayToList(appObj.get("providedHosts").asArray());
				int counter = 0;
				for(ProvidedHost compph : comphs){
					//
					assertEquals("internal component ProvidedHost name is not the same!",compph.getName(), names.get(counter));
					counter++;
				}
			}
			List<ProvidedCommunication> comppcs = comp.getProvidedCommunications(); 
			if(comppcs != null && !comppcs.isEmpty()){
				List<String> names = ModelUtil.convertJsonArrayToList(appObj.get("providedCommunications").asArray());				
				int counter = 0;				
				for(ProvidedCommunication comppc : comppcs){
					//
					assertEquals("internal component ProvidedCom name is not the same!",comppc.getName(), names.get(counter));					
					counter++;
				}
			}
			List<RequiredCommunication> comprcs = comp.getRequiredCommunications(); 
			if(comprcs != null && !comprcs.isEmpty()){
				List<String> names = ModelUtil.convertJsonArrayToList(appObj.get("requiredCommunications").asArray());				
				int counter = 0;				
				for(RequiredCommunication comprc : comprcs){
					//should have at least 1
					assertEquals("internal component RequiredCom name is not the same!",comprc.getName(), names.get(counter));					
					counter++;
				}
			}
		} catch (ModelUtilException e) {
			// 
			fail("Error test converting internal component : " + e);
		} catch (Exception e) {
			fail("Error test converting internal component : " + e);
		}
	}
	/**
	 * Test getting a {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} representation of a 
	 * {@link eu.paasage.camel.deployment.InternalComponentInstance <em>InternalComponentInstance</em>} object.
	 * <p>
	 * @see eu.paasage.upperware.plangenerator.util.ModelToJsonConverter#convertInternalComponentInstance
	 */
	@Test
	public void testConvertInternalComponentInstance(){
		//
		InternalComponentInstance ici = dm.getInternalComponentInstances().get(0);
		//debug
		LOG.debug("...testing convertInternalComponentInstance(" + ici.getName() + ")....");
		//
		JsonObject appObj = ModelToJsonConverter.convertInternalComponentInstance(ici);
		//some expectations are hardcoded!
		assertEquals("InternalComponentInstance name is not the same!", ici.getName(), appObj.get("name").asString());
		assertEquals("InternalComponentInstance type is not the same!", ici.getType().getName(), appObj.get("type").asString());
		assertEquals("InternalComponentInstance required host intance is not the same!", ici.getRequiredHostInstance().getName(), appObj.get("requiredHostInstance").asString());
		//
		try {
			List<ProvidedHostInstance> iciphs = ici.getProvidedHostInstances();
			if(iciphs != null && !iciphs.isEmpty()){
				List<String> names = ModelUtil.convertJsonArrayToList(appObj.get("providedHostInstances").asArray());
				int counter = 0;
				for(ProvidedHostInstance iciph : iciphs){
					//
					assertEquals("InternalComponentInstance ProvidedHostInstance name is not the same!",iciph.getName(), names.get(counter));
					counter++;
				}
			}
			List<ProvidedCommunicationInstance> icipcs = ici.getProvidedCommunicationInstances(); 
			if(icipcs != null && !icipcs.isEmpty()){
				List<String> names = ModelUtil.convertJsonArrayToList(appObj.get("providedCommunicationInstances").asArray());				
				int counter = 0;				
				for(ProvidedCommunicationInstance icipc : icipcs){
					//should have at least 1 provided host
					assertEquals("InternalComponentInstance ProvidedComInstance name is not the same!",icipc.getName(), names.get(counter));					
					counter++;
				}
			}
			List<RequiredCommunicationInstance> comprcis = ici.getRequiredCommunicationInstances(); 
			if(comprcis != null && !comprcis.isEmpty()){
				List<String> names = ModelUtil.convertJsonArrayToList(appObj.get("requiredCommunicationInstances").asArray());				
				int counter = 0;				
				for(RequiredCommunicationInstance comprci : comprcis){
					//should have at least 1
					assertEquals("InternalComponentInstance required com instance name is not the same!",comprci.getName(), names.get(counter));					
					counter++;
				}
			}
			
		} catch (ModelUtilException e) {
			// 
			fail("Error test converting InternalComponentInstance : " + e);
		}
	}
	/**
	 * Test getting a {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} representation of a 
	 * {@link eu.paasage.camel.deployment.Hosting <em>Hosting</em>} object.
	 * <p>
	 * @see eu.paasage.upperware.plangenerator.util.ModelToJsonConverter#convertHosting
	 */
	@Test
	public void testConvertHosting(){
		//
		Hosting hosting = dm.getHostings().get(0);
		//debug
		LOG.debug("...testing convertHosting(" + hosting.getName() + "....");
		//
		JsonObject appObj = ModelToJsonConverter.convertHosting(hosting);
		//
		assertEquals("Hosting name is not the same!", hosting.getName(), appObj.get("name").asString());
		assertEquals("Hosting provider is not the same!", hosting.getProvidedHost().getName(), appObj.get("provider").asString());
		assertEquals("Hosting consumer is not the same!", hosting.getRequiredHost().getName(), appObj.get("consumer").asString());
		//
		try {
			//provider configuration			
			Configuration config = hosting.getProvidedHostConfiguration(); 			
			if(config != null){
				//check it out
				testConfiguration(config, appObj, "providedHost");
			}
			//consumer configuration			
			Configuration config1 = hosting.getRequiredHostConfiguration(); 			
			if(config1 != null){
				//check it out
				testConfiguration(config1, appObj, "requiredHost");
			}			
		} catch (ModelUtilException e) {
			// 
			fail("Error test converting VM : " + e);
		} catch (Exception e) {
			fail("Error test converting VM : " + e);
		}
	}
	/**
	 * Test getting a {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} representation of a 
	 * {@link eu.paasage.camel.deployment.HostingInstance <em>HostingInstance</em>} object.
	 * <p>
	 * @see eu.paasage.upperware.plangenerator.util.ModelToJsonConverter#convertHostingInstance
	 */
	@Test
	public void testConvertHostingInstance(){
		//
		HostingInstance hostingInstance = dm.getHostingInstances().get(0);
		//debug
		LOG.debug("...testing convertHostingInstance(" + hostingInstance.getName() + "....");
		//
		JsonObject appObj = ModelToJsonConverter.convertHostingInstance(hostingInstance);
		//
		assertEquals("Hosting instance name is not the same!", hostingInstance.getName(), appObj.get("name").asString());
		assertEquals("Hosting instance type is not the same!", hostingInstance.getType().getName(), appObj.get("type").asString());
		assertEquals("Hosting instance provider instance is not the same!", hostingInstance.getProvidedHostInstance().getName(), appObj.get("providerInstance").asString());
		assertEquals("Hosting instance consumer instance is not the same!", hostingInstance.getRequiredHostInstance().getName(), appObj.get("consumerInstance").asString());
	}
	/**
	 * Test getting a {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} representation of a 
	 * {@link eu.paasage.camel.deployment.CommunicationInstance <em>CommunicationInstance</em>} object.
	 * <p>
	 * @see eu.paasage.upperware.plangenerator.util.ModelToJsonConverter#convertCommunicationInstance
	 */	
	@Test
	public void testConvertCommunicationInstance(){
		//
		CommunicationInstance communicationInstance = dm.getCommunicationInstances().get(0);
		//debug
		LOG.debug("...testing convertCommunicationInstance(" + communicationInstance.getName() + "....");
		//
		JsonObject appObj = ModelToJsonConverter.convertCommunicationInstance(communicationInstance);
		//
		assertEquals("Communication instance name is not the same!", communicationInstance.getName(), appObj.get("name").asString());
		assertEquals("Communication instance type is not the same!", communicationInstance.getType().getName(), appObj.get("type").asString());
		assertEquals("Communication instance provider instance is not the same!", communicationInstance.getProvidedCommunicationInstance().getName(), appObj.get("providerInstance").asString());
		assertEquals("Communication instance consumer instance is not the same!", communicationInstance.getRequiredCommunicationInstance().getName(), appObj.get("consumerInstance").asString());
	}
	/**
	 * Test getting a {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} representation of a 
	 * {@link eu.paasage.camel.deployment.Communication <em>Communication</em>} object.
	 * <p>
	 * @see eu.paasage.upperware.plangenerator.util.ModelToJsonConverter#convertCommunication
	 */
	@Test
	public void testConvertCommunication(){
		//
		Communication communication = dm.getCommunications().get(0);
		//debug
		LOG.debug("...testing convertCommunication(" + communication.getName() + "....");		
		//
		JsonObject appObj = ModelToJsonConverter.convertCommunication(communication);
		//
		assertEquals("Communication name is not the same!", communication.getName(), appObj.get("name").asString());
		assertEquals("Communication provider is not the same!", communication.getProvidedCommunication().getName(), appObj.get("provider").asString());
		assertEquals("Communication consumer is not the same!", communication.getRequiredCommunication().getName(), appObj.get("consumer").asString());
		assertEquals("Communication isMandatory is not the same!", communication.getRequiredCommunication().isIsMandatory(), appObj.get("isMandatory").asBoolean());
		assertEquals("Communication provider port is not the same!", communication.getProvidedCommunication().getPortNumber(), appObj.get("providerPort").asInt());
		assertEquals("Communication consumer port is not the same!", communication.getRequiredCommunication().getPortNumber(), appObj.get("consumerPort").asInt());
		assertEquals("Communication type is not the same!", communication.getType().getName(), appObj.get("communicationType").asString());
		//
		try {
			//provider configuration			
			Configuration config = communication.getProvidedPortConfiguration(); 			
			if(config != null){
				//check it out
				testConfiguration(config, appObj, "providerPort");
			}
			//consumer configuration			
			Configuration config1 = communication.getRequiredPortConfiguration(); 			
			if(config1 != null){
				//check it out
				testConfiguration(config1, appObj, "consumerPort");
			}			
		} catch (ModelUtilException e) {
			// 
//			fail("Error test converting communication(" + communication.getName() + ") : " + e);
		} catch (Exception e) {
			fail("Error test converting communication(" + communication.getName() + ") : " + e);
		}
	}
	//////////////////////////////////////private method//////////////////////////////////////////
	/**
	 * Test if the :{@link eu.paasage.camel.deployment.Configuration <em>Configuration</em>} is represented
	 * correctly in the  {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} model
	 * <p>
	 * @param config 	the source {@link eu.paasage.camel.deployment.Configuration <em>Configuration</em>} to commpare
	 * @param appObj	the {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} model to compare
	 * @param prefix	the prefix to add to the attribute name
	 * 
	 * @throws Exception	on processing error 
	 */
	private void testConfiguration(Configuration config, JsonObject appObj, String prefix) throws Exception {
		//debug
		LOG.debug("...testing configuration(" + config.getName() + "...");
		//
		assertEquals("config name is different!", config.getName(), appObj.get(prefix + "configName").asString());
		//check it out
		if(config.getDownloadCommand() != null){
			assertEquals("downloadCmd is different!", config.getDownloadCommand(), appObj.get(prefix + "downloadCmd").asString());	    			
		}
		if(config.getConfigureCommand() != null){
			assertEquals("configureCmd is different!", config.getConfigureCommand(), appObj.get(prefix + "configureCmd").asString());	    			
		}
		if(config.getInstallCommand() != null){
			assertEquals("installCmd is different!", config.getInstallCommand(), appObj.get(prefix + "installCmd").asString());	    			
		}
		if(config.getStartCommand() != null){
			assertEquals("startCmd is different!", config.getStartCommand(), appObj.get(prefix + "startCmd").asString());
		}
		if(config.getStopCommand() != null){
			assertEquals("stopCmd is different!", config.getStopCommand(), appObj.get(prefix + "stopCmd").asString());
		}
		if(config.getUploadCommand() != null){
			assertEquals("uploadCmd is different!", config.getUploadCommand(), appObj.get(prefix + "uploadCmd").asString());	    			
		}
	}
	
	
}
