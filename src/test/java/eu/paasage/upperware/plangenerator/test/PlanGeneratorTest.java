/*
 * Copyright (c) 2014-5 UK Science and Technology Facilities Council
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.plangenerator.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.eclipsesource.json.JsonObject;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.mddb.cdo.client.CDOClient;
import eu.paasage.upperware.plangenerator.PlanGenerator;
import eu.paasage.upperware.plangenerator.exception.ModelComparatorException;
import eu.paasage.upperware.plangenerator.exception.PlanGenerationException;
import eu.paasage.upperware.plangenerator.model.Plan;
import eu.paasage.upperware.plangenerator.model.task.ApplicationInstanceTask;
import eu.paasage.upperware.plangenerator.model.task.ApplicationTask;
import eu.paasage.upperware.plangenerator.model.task.CommunicationInstanceTask;
import eu.paasage.upperware.plangenerator.model.task.CommunicationTypeTask;
import eu.paasage.upperware.plangenerator.model.task.ComponentInstanceTask;
import eu.paasage.upperware.plangenerator.model.task.ComponentTypeTask;
import eu.paasage.upperware.plangenerator.model.task.ConfigurationTask;
import eu.paasage.upperware.plangenerator.model.task.HostingInstanceTask;
import eu.paasage.upperware.plangenerator.model.task.HostingTypeTask;
import eu.paasage.upperware.plangenerator.model.task.VMInstanceTask;
import eu.paasage.upperware.plangenerator.model.task.VMTypeTask;
import eu.paasage.upperware.plangenerator.type.TaskType;
import eu.paasage.upperware.plangenerator.util.ModelUtil;
/**
 * JUnit test case for {@link eu.paasage.upper.plangenerator.PlanGenerator <em>PlanGenerator</em>}
 * The test case is input-file specific.  It only valdiates the task generated and the dependencies between them.  
 * It does not validate the information contained in the task.  This is performed by the 
 * {@link ModelToJsonConverterTest <em>ModelToJsonConverterTest</em>}.
 * <p> 
 * @author Shirley Crompton
 * org	UK Science and Technology Facilities Council
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlanGeneratorTest {
	/** logger */
	private final static Logger LOG = Logger.getLogger(PlanGeneratorTest.class);	
	/** target camel xmi file */
	//private static String CURRENT_IN = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "s2dResult.xmi";
	//private static String CURRENT_IN = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "ScalarmModel.xmi"; //kyriakos' version
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
	/** deployment model extracted from the camel xmi file */
	private static DeploymentModel currentDM;
	/** target deployment model extracted from the camel xmi file */
	private static DeploymentModel targetDM;
	/** the plan generator */
	private static PlanGenerator generator;
	/** application task */
	private static ApplicationTask app = null;
	/** application instance task */
	private static ApplicationInstanceTask appInstance = null;
	/** vm type tasks */
	private static List<VMTypeTask> vms = new ArrayList<VMTypeTask>();
	/** vm instance tasks */
	private static List<VMInstanceTask> vmInstances = new ArrayList<VMInstanceTask>();
	/** component type tasks */
	private static List<ComponentTypeTask> components = new ArrayList<ComponentTypeTask>();
	/** component instance tasks */
	private static List<ComponentInstanceTask> compInstances = new ArrayList<ComponentInstanceTask>();
	/** hosting type tasks */
	private static List<HostingTypeTask> hostings = new ArrayList<HostingTypeTask>();
	/** hosting instance tasks */
	private static List<HostingInstanceTask> hostingInstances = new ArrayList<HostingInstanceTask>();
	/** communication type tasks */
	private static List<CommunicationTypeTask> communications = new ArrayList<CommunicationTypeTask>();
	/** communication instance tasks */
	private static List<CommunicationInstanceTask> communicationInstances = new ArrayList<CommunicationInstanceTask>();
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//read the file
		//currentDM = ModelUtil.loadDeploymentModel(CURRENT_IN);	
		//LOG.debug("Loaded current deployment model from : " + CURRENT_IN);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		reinitialise();
	}
	/**
	 * Test building a simple deployment plan for a new deployment.
	 * @see eu.paasage.upper.plangenerator.PlanGenerator#buildSimpleDeploymentPlan
	 */
	//@Ignore
	@Test
	public void testABuildSimpleDeploymentPlan() {
		//some expectations are hardcoded to fit the input model
		loadModel(CUR_CM_FILE, CUR_PM_FILE, null, null);
		currentDM = current.getApplications().get(0).getDeploymentModels().get(0);
		
		generator = new PlanGenerator(true);
		try {
			Plan simplePlan = generator.generate(null, currentDM);	//the current is treated as the target model here
			//there should be 37 tasks (1 app, 1 appIns, 4 IC, 4 ICI, 3 VMS, 4 VMI, 6 COM, 6 COMIns, 4 Hosting, 4 HIn)
			assertTrue("Incorrect number of tasks generated!", simplePlan.getTasks().size() == 37);
			//check tasks
			List<ConfigurationTask> tasks = simplePlan.getTasks();
			switchTasks(tasks);//already tested for size
			//app task
			assertNotNull("Failed to generate the application task!", app);
			assertTrue("app task should not have dependency!", app.getDependencies().isEmpty());
			assertTrue("app task type is incorrect!", app.getTaskType().equals(TaskType.CREATE));
			//app instance task
			assertNotNull("Failed to generate the application instance task!", appInstance);
			assertEquals("appInstance task should have 1 dependency!", 1, appInstance.getDependencies().size());
			assertTrue("appInstance task dependency incorrect!", appInstance.getDependencies().contains(app));
			assertTrue("appInstance task type is incorrect!", appInstance.getTaskType().equals(TaskType.CREATE));
			//vm type tasks
			assertEquals("incorrect number of vmTasks generated!", 3, vms.size());
			for(VMTypeTask vmt : vms){
				assertTrue("incorrect dependency for vm type task(" + vmt.getName() + ")", vmt.getDependencies().isEmpty());
				assertTrue("vm task type is incorrect!", vmt.getTaskType().equals(TaskType.CREATE));
			}
			//vm instance tasks
			assertEquals("incorrect number of vmInstanceTasks generated!", 4, vmInstances.size());
			for(VMInstanceTask vmi : vmInstances){ //there should only be 1
				assertEquals("incorrect dependency for vm instance task(" + vmi.getName() + ")",1, vmi.getDependencies().size()); //depends on type
				assertTrue("vmInstance task type is incorrect!", vmi.getTaskType().equals(TaskType.CREATE));
				VMTypeTask type = (VMTypeTask) vmi.getDependencies().iterator().next();
				if(vmi.getName().startsWith("Core")){
					assertTrue("incorrect type for  vm instance task(" + vmi.getName() + ")", type.getName().equals("CoreIntensiveUbuntuGermany"));
				}else if(vmi.getName().startsWith("CPU")){
					assertTrue("incorrect type for  vm instance task(" + vmi.getName() + ")", type.getName().equals("CPUIntensiveUbuntuGermany"));
				}else if(vmi.getName().startsWith("Storage")){
					assertTrue("incorrect type for  vm instance task(" + vmi.getName() + ")", type.getName().equals("StorageIntensiveUbuntuGermany"));
				}
				
			}
			//Internal Component tasks
			assertEquals("incorrect number of ComponentTypeTasks generated!", 4, components.size());
			for(ComponentTypeTask comp : components){ //there should only be 4				
				assertTrue("component type task(" + comp.getName() + " task type is incorrect!", comp.getTaskType().equals(TaskType.CREATE));
				Set<ConfigurationTask> deps = comp.getDependencies();
				//check each one
				if(comp.getName().equals("ExperimentManager")){
					//3 dependencies
					assertEquals("incorrect dependency for " + comp.getName(), 3, deps.size()); //depends on type, app, mandatory required com
					int matched = 0;
					for(ConfigurationTask task : deps){
						if(task.getName().equals("ScalarmApplication") && (task instanceof ApplicationTask)){
							matched++;
						}else if(task.getName().equals("StorageManager") && (task instanceof ComponentTypeTask)){
							matched++;
						}else if(task.getName().equals("CoreIntensiveUbuntuGermany") && (task instanceof VMTypeTask)){
							matched++;
						}
					}//end for
					assertEquals("only managed to get " + matched + "/3 dependencies for " + comp.getName(),3, matched);
					//
				}else if(comp.getName().equals("InformationService")){
					//2 dependencies
					assertEquals("incorrect dependency for " + comp.getName(), 2, deps.size()); //depends on type, app
					int matched = 0;
					for(ConfigurationTask task : deps){
						if(task.getName().equals("ScalarmApplication") && (task instanceof ApplicationTask)){
							matched++;
						}else if(task.getName().equals("CoreIntensiveUbuntuGermany") && (task instanceof VMTypeTask)){
							matched++;
						}
					}//end for
					assertEquals("only managed to get " + matched + "/2 dependencies for " + comp.getName(),2, matched);
					//
				}else if(comp.getName().equals("SimulationManager")){
					//2 dependencies
					assertEquals("incorrect dependency for " + comp.getName(), 2, deps.size()); //depends on type, app
					int matched = 0;
					for(ConfigurationTask task : deps){
						if(task.getName().equals("ScalarmApplication") && (task instanceof ApplicationTask)){
							matched++;
						}else if(task.getName().equals("CPUIntensiveUbuntuGermany") && (task instanceof VMTypeTask)){
							matched++;
						}
					}//end for
					assertEquals("only managed to get " + matched + "/2 dependencies for " + comp.getName(),2, matched);
					//
				}else if(comp.getName().equals("StorageManager")){
					//2 dependencies
					assertEquals("incorrect dependency for " + comp.getName(), 2, deps.size()); //depends on type, app
					int matched = 0;
					for(ConfigurationTask task : deps){
						if(task.getName().equals("ScalarmApplication") && (task instanceof ApplicationTask)){
							matched++;
						}else if(task.getName().equals("StorageIntensiveUbuntuGermany") && (task instanceof VMTypeTask)){
							matched++;
						}
					}//end for
					assertEquals("only managed to get " + matched + "/2 dependencies for " + comp.getName(),2, matched);
				}
			}
			//Internal component instances, there are 4
			assertEquals("incorrect number of ComponentInstanceTasks generated!", 4, compInstances.size());
			for(ComponentInstanceTask compIns : compInstances){ //there should be 4				
				assertTrue("component instance task(" + compIns.getName() + " task type is incorrect!", compIns.getTaskType().equals(TaskType.CREATE));
				Set<ConfigurationTask> deps = compIns.getDependencies();
				//check each one
				if(compIns.getName().equals("StorageManagerInstance")){
					//3 dependencies
					assertEquals("incorrect dependency for " + compIns.getName(), 3, deps.size()); //depends on type, app instance, VMinstance
					int matched = 0;
					for(ConfigurationTask task : deps){
						if(task.getName().equals("StorageManager") && (task instanceof ComponentTypeTask)){ //type
							matched++;
						}else if(task.getName().equals("ScalarmApplicationInstance") && (task instanceof ApplicationInstanceTask)){
							matched++;
						}else if(task.getName().equals("StorageIntensiveUbuntuGermanyVMInstance") && (task instanceof VMInstanceTask)){
							matched++;
						}
					}//end for
					assertEquals("only managed to get " + matched + "/3 dependencies for " + compIns.getName(), 3, matched);
					//
				}else if(compIns.getName().equals("ExperimentManagerInstance")){
					//4 dependencies
					assertEquals("incorrect dependency for " + compIns.getName(), 4, deps.size()); //depends on type, appIns, VMIns, req com provider ins
					int matched = 0;
					for(ConfigurationTask task : deps){
						if(task.getName().equals("ScalarmApplicationInstance") && (task instanceof ApplicationInstanceTask)){ //app instance
							matched++;
						}else if(task.getName().equals("ExperimentManager") && (task instanceof ComponentTypeTask)){ //type
							matched++;
						}else if(task.getName().equals("CoreIntensiveUbuntuGermanyVMInstance") && (task instanceof VMInstanceTask)){ //vm instance
							matched++;
						}else if(task.getName().equals("StorageManagerInstance") && (task instanceof ComponentInstanceTask)){ //mand com provider
							matched++;
						}
					}//end for
					assertEquals("only managed to get " + matched + "/4 dependencies for " + compIns.getName(), 4, matched);
				}
			}//end for comp instances
			//hosting tasks
			assertEquals("incorrect number of hostingTypeTasks generated!", 4, hostings.size());
			for(HostingTypeTask hosting : hostings){ //there should only be 4
				assertTrue("hosting task(" + hosting.getName() + ") task type is incorrect!", hosting.getTaskType().equals(TaskType.CREATE));
				Set<ConfigurationTask> deps = hosting.getDependencies();
				//
				if(hosting.getName().equals("StorageManagerToStorageIntensiveUbuntuGermany")){
					int matched = 0;
					for(ConfigurationTask task : deps){
						if(task.getName().equals("StorageManager") && task instanceof ComponentTypeTask){
							matched++;
						}else if(task.getName().equals("StorageIntensiveUbuntuGermany") && task instanceof VMTypeTask){
							matched++;
						}
					}//end for config tasks
					assertEquals("only managed to get " + matched + "/2 dependencies for " + hosting.getName(), 2, matched);
					//
				}else if(hosting.getName().equals("ExperimentManagerToCoreIntensiveUbuntuGermany")){
					int matched = 0;
					for(ConfigurationTask task : deps){
						if(task.getName().equals("ExperimentManager") && task instanceof ComponentTypeTask){
							matched++;
						}else if(task.getName().equals("CoreIntensiveUbuntuGermany") && task instanceof VMTypeTask){
							matched++;
						}
					}//end for config tasks
					assertEquals("only managed to get " + matched + "/2 dependencies for " + hosting.getName(), 2, matched);
				}else if(hosting.getName().equals("InformationServiceToCoreIntensiveUbuntuGermany")){
					int matched = 0;
					for(ConfigurationTask task : deps){
						if(task.getName().equals("InformationService") && task instanceof ComponentTypeTask){
							matched++;
						}else if(task.getName().equals("CoreIntensiveUbuntuGermany") && task instanceof VMTypeTask){
							matched++;
						}
					}//end for config tasks
					assertEquals("only managed to get " + matched + "/2 dependencies for " + hosting.getName(), 2, matched);
				}else if(hosting.getName().equals("SimulationManagerToCPUIntensiveUbuntuGermany")){
					int matched = 0;
					for(ConfigurationTask task : deps){
						if(task.getName().equals("SimulationManager") && task instanceof ComponentTypeTask){
							matched++;
						}else if(task.getName().equals("CPUIntensiveUbuntuGermany") && task instanceof VMTypeTask){
							matched++;
						}
					}//end for config tasks
					assertEquals("only managed to get " + matched + "/2 dependencies for " + hosting.getName(), 2, matched);
				}
			}//end for hosting
			//hosting instance, only 4
			assertEquals("incorrect number of hostingInstanceTasks generated!", 4, hostingInstances.size());
			for(HostingInstanceTask hi : hostingInstances){ //there should only be 1 hosting instance
				if(hi.getName().equals("VMtoExperimentManagerHostingInstance")){
					assertEquals("incorrect dependency for hosting instance task(" + hi.getName() + ")",3, hi.getDependencies().size()); //depends on type, provider & consumer instances
					assertTrue("hostingInstance task type is incorrect!", hi.getTaskType().equals(TaskType.CREATE));
					Set<ConfigurationTask> configTasks = hi.getDependencies();
					int matched = 0;
					for(ConfigurationTask ct : configTasks){
						if(ct.getName().equals("ExperimentManagerToCoreIntensiveUbuntuGermany") && ct instanceof HostingTypeTask){ //type
							matched++;
						}else if(ct.getName().equals("ExperimentManagerInstance") && ct instanceof ComponentInstanceTask){ //provider
							matched++;
						}else if(ct.getName().equals("CoreIntensiveUbuntuGermanyVMInstance") && ct instanceof VMInstanceTask){ //provider
							matched++;
						}	
					}//end for config
					assertEquals("only managed to get " + matched + "/3 dependencies for " + hi.getName(), 3, matched);
					
				}
			}//end for hosting instance
			//communication type
			assertEquals("incorrect number of communicationTypeTasks generated!", 6, communications.size());
			for(CommunicationTypeTask communication : communications){ //there should only be 6
				assertTrue("communication type task(" + communication.getName() + ") task type is incorrect!", communication.getTaskType().equals(TaskType.CREATE));
				Set<ConfigurationTask> deps = communication.getDependencies();
				//
				if(communication.getName().equals("StorageManagerToInformationService")){
					int matched = 0;
					for(ConfigurationTask task : deps){ //there should be 2
						if(task.getName().equals("StorageManager") && task instanceof ComponentTypeTask){
							matched++;
						}else if(task.getName().equals("InformationService") && task instanceof ComponentTypeTask){
							matched++;
						}
					}//end for config tasks
					assertEquals("only managed to get " + matched + "/2 dependencies for " + communication.getName(), 2, matched);
					//
				}else if(communication.getName().equals("ExperimentManagerToStorageManager")){
					int matched = 0;
					for(ConfigurationTask task : deps){ //there should be 2
						if(task.getName().equals("StorageManager") && task instanceof ComponentTypeTask){
							matched++;
						}else if(task.getName().equals("ExperimentManager") && task instanceof ComponentTypeTask){
							matched++;
						}
					}//end for config tasks
					assertEquals("only managed to get " + matched + "/2 dependencies for " + communication.getName(), 2, matched);
					//
				}else if(communication.getName().equals("ExperimentManagerToInformationService")){
						int matched = 0;
						for(ConfigurationTask task : deps){ //there should be 2
							if(task.getName().equals("InformationService") && task instanceof ComponentTypeTask){
								matched++;
							}else if(task.getName().equals("ExperimentManager") && task instanceof ComponentTypeTask){
								matched++;
							}
						}//end for config tasks
						assertEquals("only managed to get " + matched + "/2 dependencies for " + communication.getName(), 2, matched);
						//
				}else if(communication.getName().equals("SimulationManagerToStorageManager")){
					int matched = 0;
					for(ConfigurationTask task : deps){ //there should be 2
						if(task.getName().equals("StorageManager") && task instanceof ComponentTypeTask){
							matched++;
						}else if(task.getName().equals("SimulationManager") && task instanceof ComponentTypeTask){
							matched++;
						}
					}//end for config tasks
					assertEquals("only managed to get " + matched + "/2 dependencies for " + communication.getName(), 2, matched);
					//
				}else if(communication.getName().equals("SimulationManagerToExperimentManager")){
					int matched = 0;
					for(ConfigurationTask task : deps){ //there should be 2
						if(task.getName().equals("ExperimentManager") && task instanceof ComponentTypeTask){
							matched++;
						}else if(task.getName().equals("SimulationManager") && task instanceof ComponentTypeTask){
							matched++;
						}
					}//end for config tasks
					assertEquals("only managed to get " + matched + "/2 dependencies for " + communication.getName(), 2, matched);
					//
				}else if(communication.getName().equals("SimulationManagerToInformationService")){
					int matched = 0;
					for(ConfigurationTask task : deps){ //there should be 2
						if(task.getName().equals("InformationService") && task instanceof ComponentTypeTask){
							matched++;
						}else if(task.getName().equals("SimulationManager") && task instanceof ComponentTypeTask){
							matched++;
						}
					}//end for config tasks
					assertEquals("only managed to get " + matched + "/2 dependencies for " + communication.getName(), 2, matched);
					//
				}
			}
			//communication instance, there is only 6
			assertEquals("incorrect number of communicationInstanceTasks generated!", 6, communicationInstances.size());
			for(CommunicationInstanceTask comm : communicationInstances){ //there should only be 6 com instance
				if(comm.getName().equals("ExperimentManagerToStorageManagerInstance")){
					assertEquals("incorrect dependency for communication instance task(" + comm.getName() + ")", 3, comm.getDependencies().size()); //depends on type, provider & consumer instances
					assertTrue("communicationInstance task type is incorrect!", comm.getTaskType().equals(TaskType.CREATE));
					Set<ConfigurationTask> configTasks = comm.getDependencies();
					int matched = 0;
					for(ConfigurationTask ct : configTasks){
						if(ct.getName().equals("ExperimentManagerToStorageManager") && ct instanceof CommunicationTypeTask){ //type
							matched++;
						}else if(ct.getName().equals("ExperimentManagerInstance") && ct instanceof ComponentInstanceTask){ //provider
							matched++;
						}else if(ct.getName().equals("StorageManagerInstance") && ct instanceof ComponentInstanceTask){ //consumer
							matched++;
						}	
					}//end for config
					assertEquals("only managed to get " + matched + "/3 dependencies for " + comm.getName(), 3, matched);
				}
			}//end for comm instance
			//for visual inspection
			//printTasks(simplePlan.getTasks());
			//
		} catch (PlanGenerationException e) {
			fail("Error generating a simple deployment plan :" + e.getMessage());
		} catch (ModelComparatorException me){
			fail("ModelComparatorException generating a simple deployment plan :" + me.getMessage());
		}
	}
	//@Ignore
	@Test
	public void testBBuildRedeploymentPlan() {
		//some expectations are hardcoded to fit the input model
		loadModel(CUR_CM_FILE, CUR_PM_FILE, TAR_CM_FILE, TAR_PM_FILE);
		currentDM = current.getApplications().get(0).getDeploymentModels().get(0);
		targetDM = target.getApplications().get(0).getDeploymentModels().get(0);
		//
		generator = new PlanGenerator(false); //not simple deployment
		try {
			Plan reconfigPlan = generator.generate(currentDM, targetDM);
			if(reconfigPlan != null){
				//printTasks(reconfigPlan.getTasks());
				//
				assertEquals("Incorrect number of tasks generated!", 14, reconfigPlan.getTasks().size());
				//
				//check tasks
				List<ConfigurationTask> tasks = reconfigPlan.getTasks();
				switchTasks(tasks);//already tested for size
				//
				//app instance task
				assertNotNull("Failed to generate the application instance task!", appInstance);
				assertEquals("appInstance task should have 1 dependency!", 1, appInstance.getDependencies().size());//the app task
				assertTrue("appInstance task dependency incorrect!", appInstance.getDependencies().contains(app));
				assertTrue("appInstance task type is incorrect!", appInstance.getTaskType().equals(TaskType.UPDATE));				
				//vm instance tasks
				assertEquals("incorrect number of vmInstanceTasks generated!", 2, vmInstances.size());
				for(VMInstanceTask vmi : vmInstances){ //there should only be 2
					assertTrue("vmInstance task type is incorrect!", vmi.getTaskType().equals(TaskType.CREATE));
					if(vmi.getName().startsWith("Core")){
						assertTrue("incorrect type for  vm instance task(" + vmi.getName() + ")", vmi.getJsonModel().get("type").asString().equals("CoreIntensiveUbuntuGermany"));
					}else if(vmi.getName().startsWith("CPU")){
						assertTrue("incorrect type for  vm instance task(" + vmi.getName() + ")", vmi.getJsonModel().get("type").asString().equals("CPUIntensiveUbuntuGermany"));
					}					
				}
				//internal component 1
				assertEquals("incorrect number of comp type tasks generated!", 1, components.size());
				ComponentTypeTask ctt = components.get(0);
				assertTrue("component type task(" + ctt.getName() + " task type is incorrect!", ctt.getTaskType().equals(TaskType.UPDATE));
				assertTrue("component type task(" + ctt.getName() + " incorrect number of dependency!", ctt.getDependencies().size() == 1);
				assertTrue("component type task(" + ctt.getName() + " incorrect dependency!", ctt.getDependencies().iterator().next().getName().equals("NewScalarmApplication"));
				assertTrue("component type task(" + ctt.getName() + " incorrect dependency type!", ctt.getDependencies().iterator().next() instanceof ApplicationTask);
				//internal component instances 2
				assertEquals("incorrect number of compInstanceTasks generated!", 2, compInstances.size());
				for(ComponentInstanceTask compIns : compInstances){ //there should be 2				
					assertTrue("component instance task(" + compIns.getName() + " task type is incorrect!", compIns.getTaskType().equals(TaskType.CREATE));
					Set<ConfigurationTask> deps = compIns.getDependencies();				
					//check each one
					if(compIns.getName().startsWith("Simulation")){
						//2 dependencies
						assertEquals("incorrect dependency for " + compIns.getName(), 2, deps.size()); //depends VMInstance and appInstance
						int matched = 0;
						for(ConfigurationTask task : deps){
							if(task.getName().equals("NewScalarmApplicationInstance") && (task instanceof ApplicationInstanceTask)){
								matched++;
							}else if(task.getName().equals("CPUIntensiveUbuntuGermanyVMInstance1") && (task instanceof VMInstanceTask)){
								matched++;
							}
						}//end for
						assertEquals("only managed to get " + matched + "/2 dependencies for " + compIns.getName(), 2, matched);
						//
					}else if(compIns.getName().startsWith("Experiment")){
						//3 dependencies
						assertEquals("incorrect dependency for " + compIns.getName(), 3, deps.size()); //depends on appIns, VMIns
						int matched = 0;
						for(ConfigurationTask task : deps){
							if(task.getName().equals("NewScalarmApplicationInstance") && (task instanceof ApplicationInstanceTask)){ //app instance
								matched++;
							}else if(task.getName().equals("CoreIntensiveUbuntuGermanyVMInstance2") && (task instanceof VMInstanceTask)){ //vm instance
								matched++;
							}else if(task.getName().equals("ExperimentManager")){
								matched++;
							}
						}//end for
						assertEquals("only managed to get " + matched + "/3 dependencies for " + compIns.getName(), 3, matched);
					}
				}//end for comp instances
				//hosting instances, only 2
				assertEquals("incorrect number of hostingInstanceTasks generated!", 2, hostingInstances.size());
				for(HostingInstanceTask hi : hostingInstances){ //there should only be 2 hosting instance
					if(hi.getName().equals("VMtoExperimentManagerHostingInstance1")){
						assertEquals("incorrect dependency for hosting instance task(" + hi.getName() + ")",2, hi.getDependencies().size()); //depends on provider &  consumer instances
						assertTrue("hostingInstance task type is incorrect!", hi.getTaskType().equals(TaskType.CREATE));
						Set<ConfigurationTask> configTasks = hi.getDependencies();
						int matched = 0;
						for(ConfigurationTask ct : configTasks){
							if(ct.getName().equals("ExperimentManagerInstance1") && ct instanceof ComponentInstanceTask){ //provider
								matched++;
							}else if(ct.getName().equals("CoreIntensiveUbuntuGermanyVMInstance2") && ct instanceof VMInstanceTask){ //provider
								matched++;
							}	
						}//end for config
						assertEquals("only managed to get " + matched + "/2 dependencies for " + hi.getName(), 2, matched);						
					}else if(hi.getName().equals("VMtoSimulationManagerHostingInstance1")){
						assertEquals("incorrect dependency for hosting instance task(" + hi.getName() + ")",2, hi.getDependencies().size()); //depends on provider &  consumer instances
						assertTrue("hostingInstance task type is incorrect!", hi.getTaskType().equals(TaskType.CREATE));
						Set<ConfigurationTask> configTasks = hi.getDependencies();
						int matched = 0;
						for(ConfigurationTask ct : configTasks){
							if(ct.getName().equals("SimulationManagerInstance1") && ct instanceof ComponentInstanceTask){ //provider
								matched++;
							}else if(ct.getName().equals("CPUIntensiveUbuntuGermanyVMInstance1") && ct instanceof VMInstanceTask){ //provider
								matched++;
							}	
						}//end for config
						assertEquals("only managed to get " + matched + "/2 dependencies for " + hi.getName(), 2, matched);
					}
				}//end for hosting instance
				//communication instance, there is only 5
				assertEquals("incorrect number of communicationInstanceTasks generated!", 5, communicationInstances.size());
				for(CommunicationInstanceTask comm : communicationInstances){ //there should only be 5 com instance
					if(comm.getName().equals("ExperimentManagerToStorageManagerInstance1")){
						assertEquals("incorrect dependency for communication instance task(" + comm.getName() + ")", 1, comm.getDependencies().size()); //depends on consumer instance
						assertTrue("communicationInstance task type is incorrect!", comm.getTaskType().equals(TaskType.CREATE));
						ConfigurationTask task = comm.getDependencies().iterator().next();
						assertTrue("Incorrect dependent task name!",task.getName().equals("ExperimentManagerInstance1"));
					}else if(comm.getName().equals("SimulationManagerToExperimentManagerInstance1")){
						assertEquals("incorrect dependency for communication instance task(" + comm.getName() + ")", 2, comm.getDependencies().size()); //depends on consumer instance
						assertTrue("communicationInstance task type is incorrect!", comm.getTaskType().equals(TaskType.CREATE));
						Set<ConfigurationTask> configTasks = comm.getDependencies();
						int matched = 0;
						for(ConfigurationTask ct : configTasks){
							if(ct.getName().equals("ExperimentManagerInstance1") && ct instanceof ComponentInstanceTask){ //consumer
								matched++;
							}else if(ct.getName().equals("SimulationManagerInstance1") && ct instanceof ComponentInstanceTask){ //provider
								matched++;
							}	
						}//end for config
						assertEquals("only managed to get " + matched + "/2 dependencies for " + comm.getName(), 2, matched);
					}
				}//end for comm instance				
			}else{
				fail("failed to generate a reconfiguration plan with tasks!");
			}			
		} catch (PlanGenerationException e) {
			fail("Error generating a reconfiguration plan :" + e.getMessage());
		} catch (ModelComparatorException me){
			fail("ModelComparatorException generating a reconfiguration plan :" + me.getMessage());
		}
	}	
			
	
	//////////////////////////////////////////////////////////////private methods//////////////////////////////////////////////////////////
	/**
	 * Reinitialise the variables		
	 */
	private static void reinitialise(){
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
		if(currentDM != null){
			currentDM = null;
		}
		if(targetDM != null){
			targetDM = null;
		}
		if(generator != null){
			generator = null;
		}
		if(app != null){
			app = null;
		}
		if(appInstance != null){
			appInstance = null;
		}
		if(!vms.isEmpty()){
			vms.removeAll(vms);
		}
		if(!vmInstances.isEmpty()){
			vmInstances.removeAll(vmInstances);
		}
		if(!components.isEmpty()){
			components.removeAll(components);
		}
		if(!compInstances.isEmpty()){
			compInstances.removeAll(compInstances);
		}
		if(!hostings.isEmpty()){
			hostings.removeAll(hostings);
		}
		if(!hostingInstances.isEmpty()){
			hostingInstances.removeAll(hostingInstances);
		}
		if(!communications.isEmpty()){
			communications.removeAll(communications);
		}
		if(!communicationInstances.isEmpty()){
			communicationInstances.removeAll(communicationInstances);
		}
	}
	/**
	 * Separate the configuration tasks into different types.
	 * <p>
	 * @param tasks	the {@link java.util.List <em>List</em>} of tasks to assign
	 */
	private static void switchTasks(List<ConfigurationTask> tasks){
		//
		for(ConfigurationTask task : tasks){
			if(task instanceof ApplicationTask){
				app = (ApplicationTask) task;
			}else if(task instanceof ApplicationInstanceTask){
				appInstance = (ApplicationInstanceTask) task;
			}else if(task instanceof VMTypeTask){
				vms.add((VMTypeTask) task);
			}else if(task instanceof VMInstanceTask){
				vmInstances.add((VMInstanceTask) task);
			}else if(task instanceof ComponentTypeTask){
				components.add((ComponentTypeTask) task);
			}else if(task instanceof ComponentInstanceTask){
				compInstances.add((ComponentInstanceTask) task);
			}else if(task instanceof HostingTypeTask){
				hostings.add((HostingTypeTask) task);
			}else if(task instanceof HostingInstanceTask){
				hostingInstances.add((HostingInstanceTask) task);
			}else if(task instanceof CommunicationTypeTask){
				communications.add((CommunicationTypeTask) task);
			}else if(task instanceof CommunicationInstanceTask){
				communicationInstances.add((CommunicationInstanceTask) task);
			}
		}
	}
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
		
		try{
			//CDOClient loadModel is a static method
			current = (CamelModel) CDOClient.loadModel(curCModel);
			currentPM = (CamelModel) CDOClient.loadModel(curPModel);
			if(tarCModel != null){
				LOG.debug("target camel model file : " + tarCModel);
				target = (CamelModel) CDOClient.loadModel(tarCModel);
			}
			if(tarPModel != null){
				LOG.debug("target provider model file : " + tarPModel);
				targetPM = (CamelModel) CDOClient.loadModel(tarPModel);
			}
		}catch(Exception e){
			fail("Exception trying to load model files : " + e.getMessage());
		}
	}
	/**
	 * A utility method to print out the list of {@link eu.paasage.upperware.plangenerator.model.task.ConfigurationTask <em>ConfigurationTask</em>}
	 * for visual inspection.
	 * <p>
	 * @param tasks		The {@link eu.paasage.upperware.plangenerator.model.task.ConfigurationTask <em>ConfigurationTask</em>} to print
	 */
	private void printTasks(List<ConfigurationTask> tasks){
		
		if(tasks != null){
			//System.out.println("number of task extracted " + tasks.size());
			LOG.debug("number of task extracted " + tasks.size());
			for(ConfigurationTask task : tasks){
				//exception here
				JsonObject jo = task.getJsonModel();
				if(jo != null){
					System.out.println("task : " + task.getName() + " [" + jo.toString() + "]\n");
					Set<ConfigurationTask> dependencies = task.getDependencies();
					if(dependencies != null){
						int count = 1;
						for(ConfigurationTask cTask : dependencies){
							System.out.println("Dependency " + count + "/" + dependencies.size() + " : " + cTask.getName() + "\n");
							count++;
						}
					}
				}else{
					LOG.debug("task : " + task.getName() + " jsonModel is null!");//applicationTask has no JsonModel!!!
				}
				LOG.debug("-------------------------------------------------------------------------------------");
			}//end for task
		}else{
			LOG.debug("failed to extract tasks!");
		}
	}
}
