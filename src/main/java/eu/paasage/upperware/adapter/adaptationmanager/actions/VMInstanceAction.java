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

public class VMInstanceAction implements Action {

	public VMInstanceAction(DeploymentModel targetModel) {
		this.vmInstName = (String) targetModel.eGet(targetModel.eClass()
				.getEStructuralFeature("name"));
	}
	
	public VMInstanceAction(ConfigurationTask task, ExecInterfacer execInterfacer){
		this.task = task;
		this.objParams = task.getJsonModel();
		this.execInterfacer = execInterfacer;
	}

	private String vmInstName;
	private static final Logger LOGGER = Logger
			.getLogger(VMInstanceAction.class.getName());

	private JsonObject objParams;
	private ConfigurationTask task;
	private ExecInterfacer execInterfacer;

	public void execute(Map<String, Object> input, Map<String, Object> output) throws ActionError {
		LOGGER.log(Level.INFO, "Application creation action");
		ExecInterfacer execInterfacer = (ExecInterfacer) input
				.get("execInterfacer");
		String applicationId;
		try {
			applicationId = execInterfacer.createApplication(vmInstName);
		} catch (Exception e) {
			throw new ActionError();
		}
		output.putAll(input);
		output.put("applicationID",applicationId);
	}

	public void run(){
		
		if(task.getTaskType()==TaskType.CREATE){
			
			this.vmInstName = objParams.get("name").asString();
			LOGGER.log(Level.INFO, "VM Instance action (creation) thread : name " + vmInstName);
			
			String vmType = objParams.get("type").asString();//getting camel name for vm type
			String imageName, imageID = null, cloudID = null, hardwareID=null, locationID=null, vmt;
			imageName = dataShare.getImageNameFromVMT(vmType);//getting image value for the vmtype
			
			//String OSVendorType = "NIX", login = "ubuntu", OSArchitecture = "AMD64", OSVersion = "14.04.2";//putting default values
			//getting values from plan generator
			String OSVendorType = "NIX";//objParams.get("OSVendorType").asString();
			JsonObject defaultCred = (JsonObject) objParams.get("defaultCredential");
			String login = "ubuntu";//defaultCred.get("defaultLoginName").asString();
			String OSArchitecture = objParams.get("OSArchitecture").asString();
			String OSVersion = "14.04.2";//default value - to be provided in Model
			
			
			if((vmt=dataShare.getEntityVMTid(vmType))==null){//entity non existant in ExecWare
				//To Do Exec API Call
				String cloudName = objParams.get("cloud").asString();
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
						String driver = objParams.get("driver").asString();
						String endpoint = objParams.get("endpoint").asString();
						String uname = ((JsonObject) objParams.get("credential")).get("username").asString();
						String pass = ((JsonObject) objParams.get("credential")).get("password").asString();
						
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
				
				//String cloudUuid = objParams.get("region").asString();//"regionOne"
				String cloudProviderIdLocation = objParams.get("locations").asArray().get(0).toString();
				System.out.println("Locations fetched " + objParams.get("locations").asArray().toString());
				
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
				//locationID = objParams.get("cloud").asString();//find the appropriate location ID satisfying cloudID & cloudUuid
				
				//String cloudProviderId = "regionOne/2";//hack for test
				//cloudProviderId = "RegionOne/4";//hack for Belgium workshop test
				
				String hardwCloudProviderId = null;
				//setting cloudProviderId defaults 
/*				if(cloudName.equalsIgnoreCase("Omistack"))
					hardwCloudProviderId = "RegionOne/3";
				else if(cloudName.equalsIgnoreCase("Flexiant"))
					hardwCloudProviderId = "e92bb306-72cd-33a2-a952-908db2f47e98/c59a9066-d2f8-32e0-a227-6d90cbe3c9e2:2aedbbc7-41de-3628-918f-2c909fa81054";*/
				hardwCloudProviderId = objParams.get("VMTypeCloudProviderId").asString();
				
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
				imgCloudProviderId = objParams.get("VMImageId").asString();


				try {
					imageID = execInterfacer.getSpecificImage(Integer.parseInt(cloudID), imgCloudProviderId/*, locationID*/) + "";
					
					boolean status = execInterfacer.updateOSandLoginForSpecificImage(imageID, OSVendorType, login, OSArchitecture, OSVersion);
					
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

			//Now steps for VM instance entity
			String vmInstID;
			if(dataShare.getEntityVMInstance(this.vmInstName)==null && dataShare.addVMI(this.vmInstName, vmType)){//the first condition to ensure that the entity is non-existent
				//To Do Exec API Call
				//vmInstID = "/api/virtualMachine/" + vmInstName;//POST using parameters cloudID, imageID, locationID & hardwareID
				vmInstID = execInterfacer.createVirtualMachine(this.vmInstName, Integer.parseInt(cloudID), Integer.parseInt(imageID), Integer.parseInt(hardwareID), Integer.parseInt(locationID));
				
				int temp = Integer.parseInt(execInterfacer.trimResponseID(vmInstID));
				boolean status = false;
				int timeout = 0;
				while((!(status = execInterfacer.queryStateOKVM(temp))) && timeout < 4){
					LOGGER.log(Level.INFO, "Waiting 30 secs for operation completion. VM Instance : ID " + vmInstID);
					try {
						Thread.sleep(30000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					timeout++;
				}
				
				if(timeout >= 4)
					status = true;
				
				LOGGER.log(Level.INFO, "Created VM Instance : ID " + vmInstID);
				if(dataShare.setVMIID(this.vmInstName, execInterfacer.trimResponseID(vmInstID)) && status)
					LOGGER.log(Level.INFO, "Stored newly created VM Instance : ID " + vmInstID);
				else
					LOGGER.log(Level.WARNING, "Could not store newly created VM Instance : ID " + vmInstID);
			}

		} else if(task.getTaskType()==TaskType.UPDATE){//almost same as CREATE except that (if the VM Instance exists use PUT request else POST request)
			
			
			this.vmInstName = objParams.get("name").asString();
			LOGGER.log(Level.INFO, "VM Instance action (updation) thread : name " + vmInstName);
			
			String vmType = objParams.get("type").asString();//getting camel name for vm type
			String imageName, imageID = null, cloudID = null, hardwareID=null, locationID=null, vmt;
			imageName = dataShare.getImageNameFromVMT(vmType);//getting image value for the vmtype
			
			//String OSVendorType = "NIX", login = "ubuntu", OSArchitecture = "AMD64", OSVersion = "14.04.2";//putting default values
			//getting values from plan generator
			String OSVendorType = "NIX";//objParams.get("OSVendorType").asString();
			JsonObject defaultCred = (JsonObject) objParams.get("defaultCredential");
			String login = "ubuntu";//defaultCred.get("defaultLoginName").asString();
			String OSArchitecture = objParams.get("OSArchitecture").asString();
			String OSVersion = "14.04.2";//default value - to be provided in Model
			
			
			if((vmt=dataShare.getEntityVMTid(vmType))==null){//entity non existant in ExecWare
				//To Do Exec API Call
				String cloudName = objParams.get("cloud").asString();
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
						String driver = objParams.get("driver").asString();
						String endpoint = objParams.get("endpoint").asString();
						String uname = ((JsonObject) objParams.get("credential")).get("username").asString();
						String pass = ((JsonObject) objParams.get("credential")).get("password").asString();
						
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
				
				//String cloudUuid = objParams.get("region").asString();//"regionOne"
				String cloudProviderIdLocation = objParams.get("locations").asArray().get(0).toString();
				System.out.println("Locations fetched " + objParams.get("locations").asArray().toString());
				
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
				//locationID = objParams.get("cloud").asString();//find the appropriate location ID satisfying cloudID & cloudUuid
				
				//String cloudProviderId = "regionOne/2";//hack for test
				//cloudProviderId = "RegionOne/4";//hack for Belgium workshop test
				
				String hardwCloudProviderId = null;
				//setting cloudProviderId defaults 
/*				if(cloudName.equalsIgnoreCase("Omistack"))
					hardwCloudProviderId = "RegionOne/3";
				else if(cloudName.equalsIgnoreCase("Flexiant"))
					hardwCloudProviderId = "e92bb306-72cd-33a2-a952-908db2f47e98/c59a9066-d2f8-32e0-a227-6d90cbe3c9e2:2aedbbc7-41de-3628-918f-2c909fa81054";*/
				hardwCloudProviderId = objParams.get("VMTypeCloudProviderId").asString();
				
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
				imgCloudProviderId = objParams.get("VMImageId").asString();


				try {
					imageID = execInterfacer.getSpecificImage(Integer.parseInt(cloudID), imgCloudProviderId/*, locationID*/) + "";
					
					boolean status = execInterfacer.updateOSandLoginForSpecificImage(imageID, OSVendorType, login, OSArchitecture, OSVersion);
					
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

			//Now steps for VM instance entity
			String vmInstID;
			if(dataShare.getEntityVMInstance(this.vmInstName)==null && dataShare.addVMI(this.vmInstName, vmType)){//the first condition to ensure that the entity is non-existent
				//To Do Exec API Call
				//vmInstID = "/api/virtualMachine/" + vmInstName;//POST using parameters cloudID, imageID, locationID & hardwareID
				vmInstID = execInterfacer.createVirtualMachine(this.vmInstName, Integer.parseInt(cloudID), Integer.parseInt(imageID), Integer.parseInt(hardwareID), Integer.parseInt(locationID));
				
				int temp = Integer.parseInt(execInterfacer.trimResponseID(vmInstID));
				boolean status = false;
				int timeout = 0;
				while((!(status = execInterfacer.queryStateOKVM(temp))) && timeout < 4){
					LOGGER.log(Level.INFO, "Waiting 30 secs for operation completion. VM Instance : ID " + vmInstID);
					try {
						Thread.sleep(30000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					timeout++;
				}
				
				if(timeout >= 4)
					status = true;
				
				LOGGER.log(Level.INFO, "Created VM Instance : ID " + vmInstID);
				if(dataShare.setVMIID(this.vmInstName, execInterfacer.trimResponseID(vmInstID)) && status)
					LOGGER.log(Level.INFO, "Stored newly created VM Instance : ID " + vmInstID);
				else
					LOGGER.log(Level.WARNING, "Could not store newly created VM Instance : ID " + vmInstID);
				
			} else if(!(vmInstID = dataShare.getEntityVMIid(this.vmInstName)).equalsIgnoreCase("")){//the VM Instance entity exists but needs to be modified using PUT request
				
				vmInstID = dataShare.getEntityVMIid(this.vmInstName);
				int vmiId = Integer.parseInt(vmInstID);
				int temp = Integer.parseInt(execInterfacer.trimResponseID(vmInstID));
				
				boolean status = false, updated;
				
				//To Do Exec API Call
				//vmInstID = "/api/virtualMachine/VM_id" + vmInstName;//PUT using parameters cloudID, imageID, locationID & hardwareID
				updated = execInterfacer.updateVirtualMachine(vmiId, this.vmInstName, Integer.parseInt(cloudID), Integer.parseInt(imageID), Integer.parseInt(hardwareID), Integer.parseInt(locationID));
				
				int timeout = 0;
				while(updated && (!(status = execInterfacer.queryStateOKVM(temp))) && timeout < 4){
					LOGGER.log(Level.INFO, "Waiting 30 secs for operation completion. VM Instance : ID " + vmInstID);
					try {
						Thread.sleep(30000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					timeout++;
				}
				
				if(timeout >= 4)
					status = true;
				
				if(status)
					LOGGER.log(Level.INFO, "Updated VM Instance : ID " + vmInstID);
				
			}
			
		} else if(task.getTaskType()==TaskType.DELETE){//VMInstance DELETE request
			
			
			this.vmInstName = objParams.get("name").asString();
			LOGGER.log(Level.INFO, "VM Instance action (deletion) thread : name " + vmInstName);
			
			String vmType = objParams.get("type").asString();//getting camel name for vm type
			String imageName, imageID = null, cloudID = null, hardwareID=null, locationID=null, vmt;
			imageName = dataShare.getImageNameFromVMT(vmType);//getting image value for the vmtype
			
			if((vmt=dataShare.getEntityVMTid(vmType))==null){//entity non existant in ExecWare
				//This condition should not exist
			}else{//the VM template entity already exists
				
				//vmt = dataShare.getEntityVMTid(vmType);
				String[] ids = dataShare.getEntityVMTIDs(vmType);
				cloudID = ids[1];
				imageID = ids[2];
				locationID = ids[3];
				hardwareID = ids[4];
			}

			//Now steps for VM instance entity
			String vmInstID;
			if(dataShare.getEntityVMInstance(this.vmInstName)!=null){//the first condition to ensure that the entity is existant
				
				String vmiId = dataShare.getEntityVMIid(this.vmInstName);
				boolean deleted = false;
				
				try {
					deleted = execInterfacer.deleteVirtualMachine(Integer.parseInt(vmiId));
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if(deleted){
					LOGGER.log(Level.INFO, "Deleted VM Instance : ID " + vmiId);
					if(dataShare.deleteVMI(this.vmInstName))
						LOGGER.log(Level.INFO, "Removed from Mapper VM Instance : ID " + vmiId);
					else
						LOGGER.log(Level.WARNING, "Could not remove from Mapper VM Instance : ID " + vmiId);
				}
			}
		}
		
	}

	public ConfigurationTask getTask() {
		// TODO Auto-generated method stub
		return this.task;
	}
	
	public String getVMInstName(){
		return this.vmInstName;
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
