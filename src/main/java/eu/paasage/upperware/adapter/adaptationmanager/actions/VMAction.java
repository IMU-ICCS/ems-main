/*
 * Copyright (c) 2015 INRIA, INSA Rennes
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.adaptationmanager.actions;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.parser.ParseException;

import com.eclipsesource.json.JsonObject;

import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.upperware.adapter.adaptationmanager.REST.ExecInterfacer;
import eu.paasage.upperware.adapter.adaptationmanager.REST.ExecutionwareError;
import eu.paasage.upperware.adapter.adaptationmanager.core.Coordinator;
import eu.paasage.upperware.adapter.adaptationmanager.mapping.CamelExecwareMapping;
import eu.paasage.upperware.plangenerator.model.task.ConfigurationTask;
import eu.paasage.upperware.plangenerator.type.TaskType;

public class VMAction implements Action {

	public VMAction(DeploymentModel targetModel) {
		this.vmName = (String) targetModel.eGet(targetModel.eClass()
				.getEStructuralFeature("name"));
	}
	
	public VMAction(ConfigurationTask task, ExecInterfacer execInterfacer){
		this.task = task;
		this.objParams = task.getJsonModel();
		this.execInterfacer = execInterfacer;
	}

	private String vmName;
	private static final Logger LOGGER = Logger
			.getLogger(VMAction.class.getName());

	private JsonObject objParams;
	private ConfigurationTask task;
	private ExecInterfacer execInterfacer;

	public void execute(Map<String, Object> input, Map<String, Object> output) throws ActionError {
		LOGGER.log(Level.INFO, "Application creation action");
		ExecInterfacer execInterfacer = (ExecInterfacer) input
				.get("execInterfacer");
		String applicationId;
		try {
			applicationId = execInterfacer.createApplication(vmName);
		} catch (Exception e) {
			throw new ActionError();
		}
		output.putAll(input);
		output.put("applicationID",applicationId);
	}

	public void run(){
		
		if(task.getTaskType()==TaskType.CREATE){
			
			this.vmName = objParams.get("name").asString();
			LOGGER.log(Level.INFO, "VM Type action (create) thread : name " + this.vmName);
			
			/*NO Exec API Call!!
			 * Passing and storing the VM name and image to be created in VMInstanceAction when having cloud, hardware and location params */
			dataShare.addVMT(this.vmName);
			dataShare.addImageNametoVMT(this.vmName, objParams.get("os").asString());
			
			/**
			 * Force dependent VMInstAct to execute
			 */
			System.out.println("***" + this.toString() + " *** Data/Objects available from its dependencies ");
			//Collection<Object> depActions = Coordinator.getNeighbourDependencies(this);
			Collection<Action> depOnActions = Coordinator.getDependentOnActions(this);
			LOGGER.log(Level.INFO, "--------------Breakpoint VMAction (create)--- " + depOnActions.size());
			
			for(Object obj : depOnActions){
				System.out.println("-- " + obj.toString() + " ");
				if(obj.getClass()==VMInstanceAction.class){
					JsonObject params = ((VMInstanceAction) obj).getParams();
					//createVM(params);//replacement for the forced VMInstanceAction run below
					createVMwithWait(params);//replacement for the forced VMInstanceAction run below
					//((VMInstanceAction) obj).run();
				}
			}
			
			//InternalComponentAction should be completed
			for(Object obj : depOnActions){
				System.out.println("-- " + obj.toString() + " ");
				if(obj.getClass()==InternalComponentAction.class){
					LOGGER.log(Level.INFO, "VMAction " + this.vmName + " => InternalComponentAction " + ((InternalComponentAction) obj).toString() + " thread run supressed");
					//((InternalComponentAction) obj).run();
				}
			}

		} else if(task.getTaskType()==TaskType.UPDATE){
			
			boolean status = true;
			
			this.vmName = objParams.get("name").asString();
			LOGGER.log(Level.INFO, "VM Type action (update) thread : name " + this.vmName);
			
			/**
			 * Force dependent VMInstAct to execute deletion before VMType deletion
			 */
			/*System.out.println("***" + this.toString() + " *** Data/Objects available from its dependencies ");
			//Collection<Object> depActions = Coordinator.getNeighbourDependencies(this);
			Collection<Action> depOnActions = Coordinator.getDependentOnActions(this);
			LOGGER.log(Level.INFO, "--------------Breakpoint VMAction (Update)--- " + depOnActions.size());
			
			for(Object obj : depOnActions){
				System.out.println("-- " + obj.toString() + " ");
				if(obj.getClass()==VMInstanceAction.class){
					((VMInstanceAction) obj).run();
					LOGGER.log(Level.INFO, "Forced (update) " + ((VMInstanceAction) obj).getVMInstName() + " to run from " + this.getVMName());
				} else
					status = false;
			}*/
			
		} else if(task.getTaskType()==TaskType.DELETE){
			
			boolean status = true;
			
			this.vmName = objParams.get("name").asString();
			LOGGER.log(Level.INFO, "VM Type action (delete) thread : name " + this.vmName);
			
			boolean deleted = true;		
			String vmt;
				
			vmt = dataShare.getEntityVMTid(vmName);
			try{
				deleted = deleted && execInterfacer.deleteVirtualMachineTemplate(Integer.parseInt(vmt));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(deleted){
				LOGGER.log(Level.INFO, "Deleted VM Template : ID " + vmt);
				if(dataShare.removeVMT(vmName))
					LOGGER.log(Level.INFO, "Removed from Mapper VM Template : ID " + vmt);
				else
					LOGGER.log(Level.WARNING, "Could not remove from Mapper VM Template : ID " + vmt);
			}
			
			/**
			 * Force dependent VMInstAct to execute deletion before VMType deletion
			 */
/*			System.out.println("***" + this.toString() + " *** Data/Objects available from its dependencies ");
			//Collection<Object> depActions = Coordinator.getNeighbourDependencies(this);
			Collection<Action> depOnActions = Coordinator.getDependentOnActions(this);
			LOGGER.log(Level.INFO, "--------------Breakpoint VMAction (Delete)--- " + depOnActions.size());
			
			for(Object obj : depOnActions){
				System.out.println("-- " + obj.toString() + " ");
				if(obj.getClass()==VMInstanceAction.class){
					//((VMInstanceAction) obj).run();
					//LOGGER.log(Level.INFO, "Forced (deletion) " + ((VMInstanceAction) obj).getVMInstName() + " to run from " + this.getVMName());
				} else
					status = false;
			}*/
			
			/*NO Exec API Call!!
			 * Passing and storing the VM name and image to be created in VMInstanceAction when having cloud, hardware and location params */
/*			if(status){//there exists no instance of this VMType
				dataShare.removeVMT(this.vmName);//deleted the VMType
			}*/
		}
	}
	
	private void createVM(JsonObject vmiParams){
		LOGGER.log(Level.INFO, "VMI params: " + vmiParams.toString());
		
		
		//this.vmInstName = vmiParams.get("name").asString();
		//LOGGER.log(Level.INFO, "VM Instance action (creation) thread : name " + vmInstName);
		
		String vmType = vmiParams.get("type").asString();//getting camel name for vm type
		String imageName, imageID = null, cloudID = null, hardwareID=null, locationID=null, vmt;
		imageName = dataShare.getImageNameFromVMT(vmType);//getting image value for the vmtype
		
		//String OSVendorType = "NIX", login = "ubuntu", OSArchitecture = "AMD64", OSVersion = "14.04.2";//putting default values
		//getting values from plan generator
		String OSVendorType = "NIX";//objParams.get("OSVendorType").asString();
		JsonObject defaultCred = (JsonObject) vmiParams.get("defaultCredential");
		String login = "ubuntu";//defaultCred.get("defaultLoginName").asString();
		String imgPass = "";
		String OSArchitecture = vmiParams.get("OSArchitecture").asString();
		String OSVersion = "14.04.2";//default value - to be provided in Model
		
		if(defaultCred.get("defaultLoginName").asString() != null || (!defaultCred.get("defaultLoginName").asString().equalsIgnoreCase(""))){
			login = defaultCred.get("defaultLoginName").asString();
			imgPass = defaultCred.get("defaultLoginPassword").asString();
		}
		
		
		if((vmt=dataShare.getEntityVMTid(vmType))==null){//entity non existant in ExecWare
			//To Do Exec API Call
			String cloudName = vmiParams.get("cloud").asString();
			LOGGER.log(Level.INFO, "Found cloud name in the deployment model: " + cloudName);
			
			//cloudName = "GWDG";//hack for test
			//cloudName = "omistack";//hack for Belgium workshop test
			
			//cloudID = "/api/cloud/"+cloudName;//substitute with API call GET ID
			try {
				/* Changed cloud name to GWDG - so no hacking reqd
				 * System.out.println("Hacking cloud name " + cloudName + " to omistack");
				cloudName = "omistack";*/
				
				String tempCloudID = execInterfacer.getMatchingJSONArrayHref(execInterfacer.getClouds(), cloudName);
				
				if(tempCloudID.equalsIgnoreCase("")){//need to add the cloud provider
					String driver = vmiParams.get("driver").asString();
					String endpoint = vmiParams.get("endpoint").asString();
					String uname = ((JsonObject) vmiParams.get("credential")).get("username").asString();
					String pass = ((JsonObject) vmiParams.get("credential")).get("password").asString();
					
					//fetching cloud credentials located in Property file 
					if(cloudName.equalsIgnoreCase("Flexiant")){
						uname = execInterfacer.getCloudUname("Flexiant");
						pass = execInterfacer.getCloudPass("Flexiant");
						//endpoint = execInterfacer.getCloudEndpoint("Flexiant");
					}else if(cloudName.equalsIgnoreCase("Omistack")){
						uname = execInterfacer.getCloudUname("Omistack");
						pass = execInterfacer.getCloudPass("Omistack");
						//endpoint = execInterfacer.getCloudEndpoint("Omistack");
					}
					
					linkCloudProvToExecWare(cloudName, driver, endpoint, uname, pass);
					
					tempCloudID = execInterfacer.getMatchingJSONArrayHref(execInterfacer.getClouds(), cloudName);
				}
				
				cloudID = execInterfacer.trimResponseID(tempCloudID);
				LOGGER.log(Level.INFO, "Found cloud name: " + cloudName + " id: " + cloudID);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				LOGGER.log(Level.WARNING, "Cloud name: " + cloudName + " not found. IOException");
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				LOGGER.log(Level.WARNING, "Cloud name: " + cloudName + " not found. ParseException");
				e.printStackTrace();
			}
			
			//String cloudUuid = vmiParams.get("region").asString();//"regionOne"
			String cloudProviderIdLocation = vmiParams.get("locations").asArray().get(0).toString();
			System.out.println("Locations fetched " + vmiParams.get("locations").asArray().toString());
			
/*				if(cloudName.equalsIgnoreCase("Flexiant"))
				cloudProviderIdLocation = "\"b15e1545-7ca3-361c-b6a7-b5cf2828cf28\"";*/
			
			try {
				locationID = execInterfacer.getSpecificLocation(Integer.parseInt(cloudID), cloudProviderIdLocation) + "";
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			//locationID = vmiParams.get("cloud").asString();//find the appropriate location ID satisfying cloudID & cloudUuid
			
			//String cloudProviderId = "regionOne/2";//hack for test
			//cloudProviderId = "RegionOne/4";//hack for Belgium workshop test
			
			String hardwCloudProviderId = null;
			//setting cloudProviderId defaults 
/*				if(cloudName.equalsIgnoreCase("Omistack"))
				hardwCloudProviderId = "RegionOne/3";
			else if(cloudName.equalsIgnoreCase("Flexiant"))
				hardwCloudProviderId = "e92bb306-72cd-33a2-a952-908db2f47e98/c59a9066-d2f8-32e0-a227-6d90cbe3c9e2:2aedbbc7-41de-3628-918f-2c909fa81054";*/
			hardwCloudProviderId = vmiParams.get("VMTypeCloudProviderId").asString();
			
			try {
				hardwareID = execInterfacer.getSpecificHardware(Integer.parseInt(cloudID), hardwCloudProviderId) + "";
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//hardwareID = "/api/hardware/3";//find the appropriate hardware ID satisfying cloudID & cloudUuid
			
			String imgCloudProviderId = null;
/*				if(cloudName.equalsIgnoreCase("Omistack"))
				imgCloudProviderId = "RegionOne/9c154d9a-fab9-4507-a3d7-21b72d31de97";
			else if(cloudName.equalsIgnoreCase("Flexiant"))
				imgCloudProviderId = "e92bb306-72cd-33a2-a952-908db2f47e98/d8cee060-e487-34fa-aa8b-9e3fef10eb8c";*/
			imgCloudProviderId = vmiParams.get("VMImageId").asString();


			try {
				imageID = execInterfacer.getSpecificImage(Integer.parseInt(cloudID), imgCloudProviderId/*, locationID*/) + "";
				
				boolean status = execInterfacer.updateOSandLoginForSpecificImage(imageID, OSVendorType, login, imgPass, OSArchitecture, OSVersion);
				
				if(status)
					LOGGER.log(Level.INFO, "Updated OS/Default Login for image " + imageID);
				else
					LOGGER.log(Level.INFO, "NOT updated OS/Default Login for image " + imageID);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//imageID = "/api/image/2";//find the appropriate hardware ID satisfying imageName, cloudID & locationID
			
			//vmt = "/api/vmt/" + vmType;//POST using parameters cloudID, imageID, locationID & hardwareID
			vmt = execInterfacer.createVirtualMachineTemplate(Integer.parseInt(cloudID), Integer.parseInt(imageID), Integer.parseInt(locationID), Integer.parseInt(hardwareID));
			
			LOGGER.log(Level.INFO, "Created VM type " + vmType + " : instance ID " + vmt);
			LOGGER.log(Level.INFO, "IDs cloud location hardware image " + cloudID + " | " + locationID + " | " + hardwareID + " | " + imageID);
			dataShare.setVMTIDs(vmType, execInterfacer.trimResponseID(vmt), cloudID, imageID, locationID, hardwareID);
			
		}else{//the VM template entity already exists
			
			//vmt = dataShare.getEntityVMTid(vmType);
			String[] ids = dataShare.getEntityVMTIDs(vmType);
			cloudID = ids[1];
			imageID = ids[2];
			locationID = ids[3];
			hardwareID = ids[4];
		}
	}
	
	
	private void createVMwithWait(JsonObject vmiParams){
		LOGGER.log(Level.INFO, "VMI params: " + vmiParams.toString());
		
		
		//this.vmInstName = vmiParams.get("name").asString();
		//LOGGER.log(Level.INFO, "VM Instance action (creation) thread : name " + vmInstName);
		
		String vmType = vmiParams.get("type").asString();//getting camel name for vm type
		String imageName, imageID = null, cloudID = null, hardwareID=null, locationID=null, vmt;
		imageName = dataShare.getImageNameFromVMT(vmType);//getting image value for the vmtype
		
		//String OSVendorType = "NIX", login = "ubuntu", OSArchitecture = "AMD64", OSVersion = "14.04.2";//putting default values
		//getting values from plan generator
		//String OSVendorType = "NIX";//vmiParams.get("OSVendorType").asString();
		String OSVendorType = vmiParams.get("OSVendorType").asString();
		if(OSVendorType == null)
			OSVendorType = "NIX";
		JsonObject defaultCred = (JsonObject) vmiParams.get("defaultCredential");
		//String login = "ubuntu";//defaultCred.get("defaultLoginName").asString();
		String login = defaultCred.get("defaultLoginName").asString();
		String imgPass = defaultCred.get("defaultLoginPassword").asString();
		/*if(login == null)
			login = "ubuntu";*/
		
		String OSArchitecture = vmiParams.get("OSArchitecture").asString();
		
		String OSVersion = "14.04.2";//default value - to be provided in Model
		if(OSVendorType.equalsIgnoreCase("WINDOWS"))
			OSVersion = "Server 2012 R2";
		
		
		if((vmt=dataShare.getEntityVMTid(vmType))==null){//entity non existant in ExecWare
			//To Do Exec API Call
			String cloudName = vmiParams.get("cloud").asString();
			LOGGER.log(Level.INFO, "Found cloud name in the deployment model: " + cloudName);
			
			//cloudName = "GWDG";//hack for test
			//cloudName = "omistack";//hack for Belgium workshop test
			
			//cloudID = "/api/cloud/"+cloudName;//substitute with API call GET ID
			try {
				/* Changed cloud name to GWDG - so no hacking reqd
				 * System.out.println("Hacking cloud name " + cloudName + " to omistack");
				cloudName = "omistack";*/
				
				String tempCloudID = execInterfacer.getMatchingJSONArrayHref(execInterfacer.getClouds(), cloudName);
				
				if(tempCloudID.equalsIgnoreCase("")){//need to add the cloud provider
					String driver = vmiParams.get("driver").asString();
					String endpoint = vmiParams.get("endpoint").asString();
					String uname = ((JsonObject) vmiParams.get("credential")).get("username").asString();
					String pass = ((JsonObject) vmiParams.get("credential")).get("password").asString();
					
					//fetching cloud credentials located in Property file 
					if(cloudName.equalsIgnoreCase("Flexiant")){
						uname = execInterfacer.getCloudUname("Flexiant");
						pass = execInterfacer.getCloudPass("Flexiant");
						//endpoint = execInterfacer.getCloudEndpoint("Flexiant");
					}else if(cloudName.equalsIgnoreCase("Omistack")){
						uname = execInterfacer.getCloudUname("Omistack");
						pass = execInterfacer.getCloudPass("Omistack");
						endpoint = execInterfacer.getCloudEndpoint("Omistack");
					}else if(cloudName.equalsIgnoreCase("GWDG")){
						uname = execInterfacer.getCloudUname("GWDG");
						pass = execInterfacer.getCloudPass("GWDG");
						endpoint = execInterfacer.getCloudEndpoint("GWDG");
					}else if(cloudName.equalsIgnoreCase("EC2")){
						uname = execInterfacer.getCloudUname("EC2");
						pass = execInterfacer.getCloudPass("EC2");
						endpoint = execInterfacer.getCloudEndpoint("EC2");
					}
					
					linkCloudProvToExecWare(cloudName, driver, endpoint, uname, pass);
					
					tempCloudID = execInterfacer.getMatchingJSONArrayHref(execInterfacer.getClouds(), cloudName);
				}
				
				cloudID = execInterfacer.trimResponseID(tempCloudID);
				LOGGER.log(Level.INFO, "Found cloud name: " + cloudName + " id: " + cloudID);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				LOGGER.log(Level.WARNING, "Cloud name: " + cloudName + " not found. IOException");
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				LOGGER.log(Level.WARNING, "Cloud name: " + cloudName + " not found. ParseException");
				e.printStackTrace();
			}
			
			//String cloudUuid = vmiParams.get("region").asString();//"regionOne"
			String cloudProviderIdLocation = vmiParams.get("locations").asArray().get(0).toString();//CODE to add - check for all locations in the array
			System.out.println("Locations fetched to deploy " + vmiParams.get("locations").asArray().toString());
			
/*				if(cloudName.equalsIgnoreCase("Flexiant"))
				cloudProviderIdLocation = "\"b15e1545-7ca3-361c-b6a7-b5cf2828cf28\"";*/
			
			try {
				//locationID = execInterfacer.getSpecificLocation(Integer.parseInt(cloudID), cloudProviderIdLocation) + "";
				int temp = 0, retryTimes = 40;
				locationID = (temp = execInterfacer.getSpecificLocation(Integer.parseInt(cloudID), cloudProviderIdLocation)) + "";
				while(temp == -1 && retryTimes > 0){
					LOGGER.log(Level.INFO, "Cloud still updating location. 30 seconds wait");
					Thread.sleep(30000);
					retryTimes--;
					locationID = (temp = execInterfacer.getSpecificLocation(Integer.parseInt(cloudID), cloudProviderIdLocation)) + "";
				}
				if(temp==-1 && retryTimes<=0){
					LOGGER.log(Level.INFO, "Timeout! Cloud could not find location id " + cloudProviderIdLocation +" Please verify. Quitting");
					throw new InterruptedException("Timeout error looking for cloud location id " + cloudProviderIdLocation);
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	catch (InterruptedException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			//locationID = vmiParams.get("cloud").asString();//find the appropriate location ID satisfying cloudID & cloudUuid
			
			//String cloudProviderId = "regionOne/2";//hack for test
			//cloudProviderId = "RegionOne/4";//hack for Belgium workshop test
			
			String hardwCloudProviderId = null;
			//setting cloudProviderId defaults 
/*				if(cloudName.equalsIgnoreCase("Omistack"))
				hardwCloudProviderId = "RegionOne/3";
			else if(cloudName.equalsIgnoreCase("Flexiant"))
				hardwCloudProviderId = "e92bb306-72cd-33a2-a952-908db2f47e98/c59a9066-d2f8-32e0-a227-6d90cbe3c9e2:2aedbbc7-41de-3628-918f-2c909fa81054";*/
			hardwCloudProviderId = vmiParams.get("VMTypeCloudProviderId").asString();
			
			try {
				//hardwareID = execInterfacer.getSpecificHardware(Integer.parseInt(cloudID), hardwCloudProviderId) + "";
				int temp = 0, retryTimes = 40;
				hardwareID = (temp = execInterfacer.getSpecificHardware(Integer.parseInt(cloudID), hardwCloudProviderId)) + "";
				while(temp == -1 && retryTimes > 0){
					LOGGER.log(Level.INFO, "ExecWare still updating hardwares. 30 seconds wait");
					Thread.sleep(30000);
					retryTimes--;
					hardwareID = (temp = execInterfacer.getSpecificHardware(Integer.parseInt(cloudID), hardwCloudProviderId)) + "";
				}
				if(temp==-1 && retryTimes<=0){
					LOGGER.log(Level.INFO, "Timeout! ExecWare could not find hardware id " + hardwCloudProviderId  +" Please verify. Quitting");
					throw new InterruptedException("Timeout error looking for cloud hardware id " + hardwCloudProviderId);
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			//hardwareID = "/api/hardware/3";//find the appropriate hardware ID satisfying cloudID & cloudUuid
			
			String imgCloudProviderId = null;
/*				if(cloudName.equalsIgnoreCase("Omistack"))
				imgCloudProviderId = "RegionOne/9c154d9a-fab9-4507-a3d7-21b72d31de97";
			else if(cloudName.equalsIgnoreCase("Flexiant"))
				imgCloudProviderId = "e92bb306-72cd-33a2-a952-908db2f47e98/d8cee060-e487-34fa-aa8b-9e3fef10eb8c";*/
			imgCloudProviderId = vmiParams.get("VMImageId").asString();


			try {
				//imageID = execInterfacer.getSpecificImage(Integer.parseInt(cloudID), imgCloudProviderId/*, locationID*/) + "";
				
				boolean status;
				
				int temp = 0, retryTimes = 40;
				imageID = (temp = execInterfacer.getSpecificImage(Integer.parseInt(cloudID), imgCloudProviderId/*, locationID*/)) + "";
				while(temp == -1 && retryTimes > 0){
					LOGGER.log(Level.INFO, "ExecWare still updating images. 30 seconds wait");
					Thread.sleep(30000);
					retryTimes--;
					imageID = (temp = execInterfacer.getSpecificImage(Integer.parseInt(cloudID), imgCloudProviderId/*, locationID*/)) + "";
				}
				if(temp==-1 && retryTimes<=0){
					LOGGER.log(Level.INFO, "Timeout! ExecWare could not yet find image id " +  imgCloudProviderId +" Please verify. Quitting");
					throw new InterruptedException("Timeout error looking for cloud image id " + imgCloudProviderId);
				} else
					status = execInterfacer.updateOSandLoginForSpecificImage(imageID, OSVendorType, login, imgPass, OSArchitecture, OSVersion);
				
				//boolean status = execInterfacer.updateOSandLoginForSpecificImage(imageID, OSVendorType, login, OSArchitecture, OSVersion);
				
				if(status)
					LOGGER.log(Level.INFO, "Updated OS/Default Login for image " + imageID);
				else
					LOGGER.log(Level.INFO, "NOT updated OS/Default Login for image " + imageID);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			//imageID = "/api/image/2";//find the appropriate hardware ID satisfying imageName, cloudID & locationID
			
			//vmt = "/api/vmt/" + vmType;//POST using parameters cloudID, imageID, locationID & hardwareID
			vmt = execInterfacer.createVirtualMachineTemplate(Integer.parseInt(cloudID), Integer.parseInt(imageID), Integer.parseInt(locationID), Integer.parseInt(hardwareID));
			
			LOGGER.log(Level.INFO, "Created VM type " + vmType + " : instance ID " + vmt);
			LOGGER.log(Level.INFO, "IDs cloud location hardware image " + cloudID + " | " + locationID + " | " + hardwareID + " | " + imageID);
			dataShare.setVMTIDs(vmType, execInterfacer.trimResponseID(vmt), cloudID, imageID, locationID, hardwareID);
			
		}else{//the VM template entity already exists
			
			//vmt = dataShare.getEntityVMTid(vmType);
			String[] ids = dataShare.getEntityVMTIDs(vmType);
			cloudID = ids[1];
			imageID = ids[2];
			locationID = ids[3];
			hardwareID = ids[4];
		}
	}

	public ConfigurationTask getTask() {
		// TODO Auto-generated method stub
		return this.task;
	}
	
	public String getVMName(){
		return vmName;
	}
	
	private boolean linkCloudProvToExecWare(String cloud, String driver, String endpoint, String uname, String pass){
		boolean status = false;
		
		while(CamelExecwareMapping.isCloudLookupInProgress() == false){
			CamelExecwareMapping.cloudLookingUp();
			
			
			//Check if already added then make status true and return
			String cloudID = null;
			try {
				cloudID = execInterfacer.getJSONArrayHref(execInterfacer.getClouds(), cloud);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(!cloudID.equalsIgnoreCase("")){//cloud added by another thread
				CamelExecwareMapping.CloudLookedUp();
				status = true;
				return status;
			}
			
			String tempAPIid = null, APIid = null, tempCloudId = null, cloudId = null, cloudCred = null;
			
			try {
				tempAPIid = execInterfacer.getJSONArrayHref(execInterfacer.getAPIs(), driver);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(tempAPIid.equalsIgnoreCase("")){
				try {
					tempAPIid = execInterfacer.createAPI(driver);
					APIid = execInterfacer.trimResponseID(tempAPIid);
				} catch (ExecutionwareError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				APIid = execInterfacer.trimResponseID(tempAPIid);
			}
			
			try {
				tempCloudId = execInterfacer.getJSONArrayHref(execInterfacer.getClouds(), cloud);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//default tenant for ExecWare
			Integer tenantId = 1;
			
			if(tempCloudId.equalsIgnoreCase("")){
				try {
					tempCloudId = execInterfacer.createCloud(cloud, endpoint, Integer.parseInt(APIid));
					cloudId = execInterfacer.trimResponseID(tempCloudId);
					cloudCred = execInterfacer.createCloudCredential(uname, pass, Integer.parseInt(cloudId), tenantId);
					if(cloudCred != null || !cloudCred.equalsIgnoreCase("")){
						LOGGER.log(Level.INFO, "Wait for some minutes to make sure ExecutionWare finished the lookup of the cloud-related locations, etc.");
						Thread.sleep(180000);
/*						synchronized (Thread.currentThread()) {
							Thread.currentThread().wait(60000);
						}*/
						CamelExecwareMapping.CloudLookedUp();
						status = true;
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionwareError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return status;
	}
}
