/**
 * 
 */
package eu.paasage.upperware.plangenerator.test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.eclipsesource.json.JsonObject;

import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.ProvidedCommunicationInstance;
import eu.paasage.camel.deployment.ProvidedHostInstance;
import eu.paasage.camel.deployment.VMInstance;
import eu.paasage.upperware.plangenerator.exception.ModelUtilException;
import eu.paasage.upperware.plangenerator.util.ModelToJsonConverter;
import eu.paasage.upperware.plangenerator.util.ModelUtil;


/**
 * JUnit test case for {@link eu.paasage.upperware.plangenerator.util.ModelToJsonConverter <em>ModelToJsonConverter</em>}
 * Just testing the latest changes to Camel Model regarding cloud provider information. 
 * @author Shirley Crompton
 * org	UK Science and Technology Facilities Council
 *
 */
public class CloudProviderTest {
	/** logger */
	private final static Logger LOG = Logger.getLogger(CloudProviderTest.class);	
	/** target camel xmi file */	
	private static String TARGET_IN = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "FullDeploymentBewan.xmi"; //from Christian	
	/** target camel xmi file */	
	private static String TARGET_IN1 = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "Fl1d1w1.xmi"; //from Christian, 2 deploymentModels
	/** deployment model extracted from the camel xmi file */  
	private static DeploymentModel dm;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//read the file
		dm = ModelUtil.loadDeploymentModel(TARGET_IN);
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
		if(dm != null){
			dm = null;
		}
	}

	/**
	 * Test getting a {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} representation of a 
	 * {@link eu.paasage.camel.deployment.VMInstance <em>VMInstance</em>} object.
	 * <p>
	 * @see eu.paasage.upperware.plangenerator.util.ModelToJsonConverter#convertVMInstance
	 */
	@Test
	//@Ignore
	public void testConvertVMInstance(){	
		try {
			loadModel(TARGET_IN, 0);
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
			
			assertEquals("VMInstance vmType is not the same!", "m1.medium", appObj.get(vmInstance.getVmType().getName()).asString());
			//the following two items are subject to changes in the Camel model
			//
			assertEquals("VMInstance cloud is not the same!", "omistack", appObj.get("cloud").asString());
			assertEquals("VMInstance driver is not the same!", "openstack-nova", appObj.get("driver").asString());
			assertEquals("VMInstance endpoint is not the same!", "http://omistack-beta.e-technik.uni-ulm.de:5000/v2.0", appObj.get("endpoint").asString());
			//assertEquals("VMInstance username is not the same!", "username-gwdg", appObj.get("username").asString());
			//assertEquals("VMInstance user password is not the same!", "password", appObj.get("password").asString());
		
			List<String> locs = ModelUtil.convertJsonArrayToList(appObj.get("locations").asArray());
			assertEquals("VMInstance locations is not the same!", "RegionOne", locs.get(0));
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
		} catch (Exception ex){
			//
			fail("Error test converting VM Instance : " + ex);
		}
	}
	/**
	 * Test getting a {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} representation of a 
	 * {@link eu.paasage.camel.deployment.VMInstance <em>VMInstance</em>} object.
	 * <p>
	 * @see eu.paasage.upperware.plangenerator.util.ModelToJsonConverter#convertVMInstance
	 */
	@Test
	public void testConvertVMInstanceBewan2(){	
		//23Nov2015 this tests also the newly added data items 
		try {
			loadModel(TARGET_IN1, 1);
			//
			VMInstance vmInstance = dm.getVmInstances().get(0);
			//debug
			LOG.debug("...testing convertVM(" + vmInstance.getName() + "...."); //MediumComputeLowStorageUbuntuVMInstance_4
			//
			JsonObject appObj = ModelToJsonConverter.convertVMInstance(vmInstance);
			//debug
			System.out.println("vm instance object : \n" + appObj.toString() + "\n");
			
			//some expectations are hardcoded!
			assertEquals("VMInstance name is not the same!", vmInstance.getName(), appObj.get("name").asString());
			assertEquals("VMInstance type is not the same!", vmInstance.getType().getName(), appObj.get("type").asString());	
			
			assertEquals("VMInstance vmType is not the same!", "e92bb306-72cd-33a2-a952-908db2f47e98/c59a9066-d2f8-32e0-a227-6d90cbe3c9e2:2aedbbc7-41de-3628-918f-2c909fa81054", appObj.get(vmInstance.getVmType().getName()).asString());
			//the following two items are subject to changes in the Camel model
			//
			assertEquals("VMInstance cloud is not the same!", "Flexiant", appObj.get("cloud").asString());
			assertEquals("VMInstance driver is not the same!", "flexiant", appObj.get("driver").asString());
			assertEquals("VMInstance endpoint is not the same!", "https://api.sd1.flexiant.net:4442", appObj.get("endpoint").asString());
			//new data items
			assertEquals("VMInstance VMOS is not the same!", "Ubuntu 14.04 (Cluster Two)", appObj.get("VMOS").asString());
			assertEquals("VMInstance VMImageId is not the same!", "e92bb306-72cd-33a2-a952-908db2f47e98/d8cee060-e487-34fa-aa8b-9e3fef10eb8c", appObj.get("VMImageId").asString());
			assertEquals("VMInstance VMMemory is not the same!", 4096, appObj.get("VMMemory").asInt());
			assertEquals("VMInstance VMStorage is not the same!", 100, appObj.get("VMStorage").asInt());
			assertEquals("VMInstance VMCores is not the same!", 4, appObj.get("VMCores").asInt());
			assertEquals("VMInstance OSArchitecture is not the same!", "AMD64", appObj.get("OSArchitecture").asString());
			assertEquals("VMInstance OSVendorType is not the same!", "1", appObj.get("OSVendorType").asString());
			assertEquals("VMInstance VMTypeCloudProviderId is not the same!", "e92bb306-72cd-33a2-a952-908db2f47e98/c59a9066-d2f8-32e0-a227-6d90cbe3c9e2:2aedbbc7-41de-3628-918f-2c909fa81054", appObj.get("VMTypeCloudProviderId").asString());
			//
			JsonObject defCred = (JsonObject) appObj.get("defaultCredential");
			assertEquals("VMInstance defaultLoginName is not the same!", "\"\"", defCred.get("defaultLoginName").asString());
			assertEquals("VMInstance defaultLoginPassword is not the same!", "\"\"", defCred.get("defaultLoginPassword").asString());
			//updated items
			JsonObject cred = (JsonObject) appObj.get("credential");
			assertEquals("VMInstance credential username is not the same!", "vanraesf", cred.get("username").asString());
			assertEquals("VMInstance credential user password is not the same!", "vanraesf", cred.get("password").asString());
			//
			List<String> locs = ModelUtil.convertJsonArrayToList(appObj.get("locations").asArray());
			assertEquals("VMInstance locations is not the same!", "e92bb306-72cd-33a2-a952-908db2f47e98", locs.get(0));
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
		} catch (Exception ex){
			//
			fail("Error test converting VM Instance : " + ex);
		}
	}
	//////////////////private methods///////////////////////
	/**
	 * Load the required {@link eu.paasage.camel.deployment.DeploymentModel <em>DeploymentModel</em>} from the model file.
	 * <p>
	 * @param file	file path including file name
	 * @param index	index to the required {@link eu.paasage.camel.deployment.DeploymentModel <em>DeploymentModel</em>}
	 * @throws Exception on error
	 */
	private void loadModel(String file, int index) throws Exception{
		//read the file
		dm = ModelUtil.loadDeploymentModel(file, index);
		LOG.debug("Loaded deployment model from : " + file);
		
	}

}
