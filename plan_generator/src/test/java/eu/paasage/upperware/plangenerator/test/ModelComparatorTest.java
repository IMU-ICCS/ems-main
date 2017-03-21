/*
 * Copyright (c) 2014 UK Science and Technology Facilities Council
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.plangenerator.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.VMInstance;
import eu.paasage.camel.provider.Attribute;
import eu.paasage.camel.type.SingleValue;
import eu.paasage.mddb.cdo.client.CDOClient;
import eu.paasage.upperware.plangenerator.ModelComparator;
import eu.paasage.upperware.plangenerator.util.ModelUtil;

/**
 * JUnit test case for {@link eu.paasage.upper.plangenerator.ModelComparator <em>ModelComparator</em>}
 * The test uses the models exported using the {@link eu.paasage.mddb.cdo.client.CDOClient#exportModelWithRefRec(String)} operation.
 * <p> 
 * @author Shirley Crompton
 * org	UK Science and Technology Facilities Council
 *
 */
@Ignore
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ModelComparatorTest {
	
	/** logger */
	private final static Logger LOG = Logger.getLogger(ModelComparatorTest.class);	
	/** current camel xmi file */
	private static String CUR_CM_FILE = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "test.xmi";
	/** current cross-referenced provider model file */
	private static String CUR_PM_FILE = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "upperware-models_fms_1436444254010_GWDG-DE-1436444254477.xmi"; 
	/** target camel xmi file */
	private static String TAR_CM_FILE = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "test_1_reconfig.xmi";
	/** target cross-referenced provider model file */
	private static String TAR_PM_FILE = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "upperware-models_fms_1.xmi"; 
	/** current camel model loaded from the CUR_* set of files */
	private static CamelModel current, currentPM;
	/** target camel model loaded from the TAR_* set of file */
	private static CamelModel target, targetPM;
	/** the model comparator */
	private static ModelComparator comparator;

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
		if(comparator != null){
			comparator = null;
		}
		if(current != null){
			current = null;
		}
		if(currentPM != null){
			currentPM = null;
		}
		if(target != null){
			target = null;
		}
		if(targetPM != null){
			targetPM = null;
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		if(current != null){
			current = null;
		}
		if(currentPM != null){
			currentPM = null;
		}
		if(target != null){
			target = null;
		}
		if(targetPM != null){
			targetPM = null;
		}
		
	}
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {		
	}
	/**
	 * Test comparing the same model.
	 */
	//@Ignore
	@Test
	public void testACompareSameModels(){
		try{
			loadModel(CUR_CM_FILE, CUR_PM_FILE, CUR_CM_FILE, CUR_PM_FILE);
			assertEquals("Incorrect current camel  application loaded!", "ScalarmApplication", current.getApplications().get(0).getName());
			assertEquals("Incorrect target camel application loaded!", "ScalarmApplication", target.getApplications().get(0).getName()); 
			assertEquals("Incorrect current cloud provider loaded!", "GWDGProvider", current.getProviderModels().get(0).getName());
			assertEquals("Incorrect target cloud provider loaded!","GWDGProvider", target.getProviderModels().get(0).getName());
			assertEquals("Incorrect current camel model version loaded!","v1.2noopt", current.getApplications().get(0).getVersion());
			assertEquals("Incorrect target camel model version loaded!","v1.2noopt", target.getApplications().get(0).getVersion());
			//
			assertEquals("Incorrect number of current VMS!", 3, current.getApplications().get(0).getDeploymentModels().get(0).getVms().size());
			assertEquals("Incorrect number of target VMS!", 3, target.getApplications().get(0).getDeploymentModels().get(0).getVms().size());
			assertEquals("Incorrect number of current VM Instances!", 4, current.getApplications().get(0).getDeploymentModels().get(0).getVmInstances().size());
			assertEquals("Incorrect number of target VM Instances!", 4, target.getApplications().get(0).getDeploymentModels().get(0).getVmInstances().size());
			//	
			assertEquals("Incorrect number of current InternalComponents!", 4, current.getApplications().get(0).getDeploymentModels().get(0).getInternalComponents().size());
			assertEquals("Incorrect number of target InternalComponents!", 4, target.getApplications().get(0).getDeploymentModels().get(0).getInternalComponents().size());
			assertEquals("Incorrect number of current InternalComponent Instances!", 4, current.getApplications().get(0).getDeploymentModels().get(0).getInternalComponentInstances().size());
			assertEquals("Incorrect number of target InternalComponent Instances!", 4, target.getApplications().get(0).getDeploymentModels().get(0).getInternalComponentInstances().size());
			//	
			assertEquals("Incorrect number of current Hostings!", 4, current.getApplications().get(0).getDeploymentModels().get(0).getHostings().size());
			assertEquals("Incorrect number of target Hostings!", 4, target.getApplications().get(0).getDeploymentModels().get(0).getHostings().size());
			assertEquals("Incorrect number of current  Hosting Instances!", 4, current.getApplications().get(0).getDeploymentModels().get(0).getHostingInstances().size());
			assertEquals("Incorrect number of target Hosting Instances!", 4, target.getApplications().get(0).getDeploymentModels().get(0).getHostingInstances().size());
			//
			assertEquals("Incorrect number of current Communications!", 6, current.getApplications().get(0).getDeploymentModels().get(0).getCommunications().size());
			assertEquals("Incorrect number of target Communications!", 6, target.getApplications().get(0).getDeploymentModels().get(0).getCommunications().size());
			assertEquals("Incorrect number of current  Communication Instances!", 6, current.getApplications().get(0).getDeploymentModels().get(0).getCommunicationInstances().size());
			assertEquals("Incorrect number of target Communication Instances!", 6, target.getApplications().get(0).getDeploymentModels().get(0).getCommunicationInstances().size());
			//
			comparator = new ModelComparator(current.getDeploymentModels().get(0), target.getDeploymentModels().get(0));
			comparator.compareModels();
			//
			//remove
			assertEquals(0, comparator.getRemovedVMTypes().size());
			assertEquals(0, comparator.getRemovedInternalComponents().size());
			assertEquals(0, comparator.getRemovedHostings().size());
			assertEquals(0, comparator.getRemovedCommunications().size());
			assertEquals(0, comparator.getRemovedVMInstances().size());
			assertEquals(0, comparator.getRemovedInternalComponentInstances().size());
			assertEquals(0, comparator.getRemovedHostingInstances().size());
			assertEquals(0, comparator.getRemovedComInstances().size());			
			//update
			assertEquals(0, comparator.getUpdatedVMTypes().size());
			assertEquals(0, comparator.getUpdatedInternalComponents().size());
			assertEquals(0, comparator.getUpdatedHostings().size());
			assertEquals(0, comparator.getUpdatedCommunications().size());
			assertEquals(0, comparator.getUpdatedVMInstances().size());
			assertEquals(0, comparator.getUpdatedInternalComponentInstance().size());
			assertEquals(0, comparator.getUpdatedHostingInstances().size());
			assertEquals(0, comparator.getUpdatedComInstances().size());	
			//create
			assertEquals(0, comparator.getAddedVMTypes().size());
			assertEquals(0, comparator.getAddedInternalComponents().size());
			assertEquals(0, comparator.getAddedHostings().size());
			assertEquals(0, comparator.getAddedCommunications().size());
			assertEquals(0, comparator.getAddedVMInstances().size());
			assertEquals(0, comparator.getAddedInternalComponentInstances().size());
			assertEquals(0, comparator.getAddedHostingInstances().size());
			assertEquals(0, comparator.getAddedComInstances().size());
		}catch(Exception mce){
			fail("error comparing models : " + mce.getMessage());
		}
	}
	
	/**
	 * Test comparing two different models.
	 */
	//@Ignore
	@Test
	public void testBCompareModels() {
		//
		try{
			loadModel(CUR_CM_FILE, CUR_PM_FILE, TAR_CM_FILE, TAR_PM_FILE);
			assertEquals("Incorrect current camel  application loaded!", "ScalarmApplication", current.getApplications().get(0).getName());
			assertEquals("Incorrect target camel application loaded!", "NewScalarmApplication", target.getApplications().get(0).getName()); 
			assertEquals("Incorrect current cloud provider loaded!", "GWDGProvider", current.getProviderModels().get(0).getName());
			assertEquals("Incorrect target cloud provider loaded!","GWDGProvider", target.getProviderModels().get(0).getName());
			assertEquals("Incorrect current camel model version loaded!","v1.2noopt", current.getApplications().get(0).getVersion());
			assertEquals("Incorrect target camel model version loaded!","v1.3noopt", target.getApplications().get(0).getVersion());
			//
			assertEquals("Incorrect number of current VMS!", 3, current.getApplications().get(0).getDeploymentModels().get(0).getVms().size());
			assertEquals("Incorrect number of target VMS!", 3, target.getApplications().get(0).getDeploymentModels().get(0).getVms().size());
			assertEquals("Incorrect number of current VM Instances!", 4, current.getApplications().get(0).getDeploymentModels().get(0).getVmInstances().size());
			assertEquals("Incorrect number of target VM Instances!", 6, target.getApplications().get(0).getDeploymentModels().get(0).getVmInstances().size());
			/* no matter how I get the VMInstances, always NPE!  And I am using the model w/n local context, not passing this somewhere else....
			//5Aug2015 debug NPE for VMType and VMTypeValue in Cross-ref'ed ProviderModel
			//List<VMInstance> vmis = target.getApplications().get(0).getDeploymentModels().get(0).getVmInstances();
			//DeploymentModel tc = (DeploymentModel) target.getApplications().get(0).getDeploymentModels().get(0);
			List<VMInstance> vmis = target.getDeploymentModels().get(0).getVmInstances();
			for(VMInstance vmi : vmis){
				testGetVMTypeAndValue(vmi);
			}//end 5Aug2015 debug
			*/
			//	
			assertEquals("Incorrect number of current InternalComponents!", 4, current.getApplications().get(0).getDeploymentModels().get(0).getInternalComponents().size());
			assertEquals("Incorrect number of target InternalComponents!", 4, target.getApplications().get(0).getDeploymentModels().get(0).getInternalComponents().size());
			assertEquals("Incorrect number of current InternalComponent Instances!", 4, current.getApplications().get(0).getDeploymentModels().get(0).getInternalComponentInstances().size());
			assertEquals("Incorrect number of target InternalComponent Instances!", 6, target.getApplications().get(0).getDeploymentModels().get(0).getInternalComponentInstances().size());
			//	
			assertEquals("Incorrect number of current Hostings!", 4, current.getApplications().get(0).getDeploymentModels().get(0).getHostings().size());
			assertEquals("Incorrect number of target Hostings!", 4, target.getApplications().get(0).getDeploymentModels().get(0).getHostings().size());
			assertEquals("Incorrect number of current  Hosting Instances!", 4, current.getApplications().get(0).getDeploymentModels().get(0).getHostingInstances().size());
			assertEquals("Incorrect number of target Hosting Instances!", 6, target.getApplications().get(0).getDeploymentModels().get(0).getHostingInstances().size());
			//
			assertEquals("Incorrect number of current Communications!", 6, current.getApplications().get(0).getDeploymentModels().get(0).getCommunications().size());
			assertEquals("Incorrect number of target Communications!", 6, target.getApplications().get(0).getDeploymentModels().get(0).getCommunications().size());
			assertEquals("Incorrect number of current  Communication Instances!", 6, current.getApplications().get(0).getDeploymentModels().get(0).getCommunicationInstances().size());
			assertEquals("Incorrect number of target Communication Instances!", 11, target.getApplications().get(0).getDeploymentModels().get(0).getCommunicationInstances().size());
			//
			comparator = new ModelComparator(current.getDeploymentModels().get(0), target.getDeploymentModels().get(0));
			comparator.compareModels();
			//comparator.compareInternalComponents(); //ok
			//comparator.compareInternalComponentInstances(); //ok
			//comparator.compareVMTypes(); //ok
			//comparator.compareVMInstances();  //30July15, for some reason, we can't get the vmType and vmTypeValue in the modelComparator context although it works in ModelProxy etc.
			//comparator.compareHostings();
			//comparator.compareHostingInstances();
			//comparator.compareCommunications();
			//comparator.compareCommunicationInstances();
			
			//remove
			assertEquals(0, comparator.getRemovedVMTypes().size());
			assertEquals(0, comparator.getRemovedInternalComponents().size());
			assertEquals(0, comparator.getRemovedHostings().size());
			assertEquals(0, comparator.getRemovedCommunications().size());
			assertEquals(0, comparator.getRemovedVMInstances().size());
			assertEquals(0, comparator.getRemovedInternalComponentInstances().size());
			assertEquals(0, comparator.getRemovedHostingInstances().size());
			assertEquals(0, comparator.getRemovedComInstances().size());			
			//update
			assertEquals(0, comparator.getUpdatedVMTypes().size());
			assertEquals(1, comparator.getUpdatedInternalComponents().size());
			assertEquals(0, comparator.getUpdatedHostings().size());
			assertEquals(0, comparator.getUpdatedCommunications().size());
			assertEquals(0, comparator.getUpdatedVMInstances().size());
			assertEquals(0, comparator.getUpdatedInternalComponentInstance().size());
			assertEquals(0, comparator.getUpdatedHostingInstances().size());
			assertEquals(0, comparator.getUpdatedComInstances().size());	
			//create
			assertEquals(0, comparator.getAddedVMTypes().size());
			assertEquals(0, comparator.getAddedInternalComponents().size());
			assertEquals(0, comparator.getAddedHostings().size());
			assertEquals(0, comparator.getAddedCommunications().size());
			assertEquals(2, comparator.getAddedVMInstances().size());
			assertEquals(2, comparator.getAddedInternalComponentInstances().size());
			assertEquals(2, comparator.getAddedHostingInstances().size());
			assertEquals(5, comparator.getAddedComInstances().size());	
			
		}catch(Exception mce){
			fail("error comparing models : " + mce.getMessage());
		}
	}
	
	////////////////////////////////////////////////////private method/////////////////////////////////////////////
	/**
	 * Use the cdo client to load the models into main memory
	 * <p>
	 * @param curCModel		The current main {@link eu.paasage.camel.CamelModel <em>CamelModel</em>}
	 * @param curPModel		The current {@link eu.paasage.camel.provider.ProviderModel <em>ProviderModel</em>}
	 * @param tarCModel		The target main {@link eu.paasage.camel.CamelModel <em>CamelModel</em>}
	 * @param tarPModel		The target {@link eu.paasage.camel.provider.ProviderModel <em>ProviderModel</em>}
	 */
	private void loadModel(String curCModel, String curPModel, String tarCModel, String tarPModel){
		
		LOG.debug("...loading model files....");
		LOG.debug("current camel model file : " + curCModel);
		LOG.debug("current provider model file : " + curPModel);
		LOG.debug("target camel model file : " + tarCModel);
		LOG.debug("target provider model file : " + tarPModel);
		
		try{
			//CDOClient loadModel is a static method
			current = (CamelModel) CDOClient.loadModel(curCModel);
			currentPM = (CamelModel) CDOClient.loadModel(curPModel);
			target = (CamelModel) CDOClient.loadModel(tarCModel);
			targetPM = (CamelModel) CDOClient.loadModel(tarPModel);
		}catch(Exception e){
			fail("Exception trying to load model files : " + e.getMessage());
		}
	}
	/**
	 * Code to debug the nullpointerexception when getting the VMType and VMTypeValue.
	 * <p>
	 * @param vmi  the {@link eu.paasage.camel.deployment.VMInstance <em>VMInstance</em>} to parse.
	 */
	private void testGetVMTypeAndValue(VMInstance vmi){
		//debug this NPE issue with VMType
		Attribute vmType = vmi.getVmType(); //you will always get an instance but its attributes may not be populated!
		String vmTypeName = (vmType.getName() == null ? "null" : vmType.getName());
		LOG.debug("...debug NPE in VMInstance : vmType is : " + vmTypeName + "....");
		//
		SingleValue vmTypeValue = vmi.getVmTypeValue();
		String valueName = ModelUtil.switchValue(vmTypeValue);
		LOG.debug("...debug NPE in VMInstance : vmTypeValue is : " + (valueName == null ? "null" : valueName));
	}
}
