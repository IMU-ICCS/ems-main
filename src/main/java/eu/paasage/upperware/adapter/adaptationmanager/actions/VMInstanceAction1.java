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
import eu.paasage.upperware.adapter.adaptationmanager.core.Coordinator;
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
			LOGGER.log(Level.INFO, "VM Instance action thread : name " + vmInstName);
			
			String vmType = objParams.get("type").asString();//getting camel name for vm type
			String imageName, imageID = null, cloudID = null, hardwareID=null, locationID=null, vmt;
			imageName = dataShare.getImageNameFromVMT(vmType);//getting image value for the vmtype
			
			if((vmt=dataShare.getEntityVMTid(vmType))==null){//entity non existant in ExecWare
				//To Do Exec API Call
				String cloudName = objParams.get("cloud").asString();
				LOGGER.log(Level.INFO, "Found cloud name in the deployment model: " + cloudName);
				
				//cloudName = "GWDG";//hack for test
				cloudName = "omistack";//hack for Belgium workshop test
				
				//cloudID = "/api/cloud/"+cloudName;//substitute with API call GET ID
				try {
					/* Changed cloud name to GWDG - so no hacking reqd
					 * System.out.println("Hacking cloud name " + cloudName + " to omistack");
					cloudName = "omistack";*/
					
					String tempCloudID = execInterfacer.getJSONArrayHref(execInterfacer.getClouds(), cloudName);
					
					while(tempCloudID.equalsIgnoreCase("")){
						
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
				String cloudProviderId = objParams.get("locations").asArray().get(0).toString();
				System.out.println("Locations fetched " + objParams.get("locations").asArray().toString());
				
				cloudProviderId = "regionOne";//hack for test
				try {
					locationID = execInterfacer.getSpecificLocation(Integer.parseInt(cloudID), cloudProviderId) + "";
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
				
				//cloudProviderId = "regionOne/acbc4b60-7b03-48bb-8352-faed4410eca3";//hack for test
				cloudProviderId = "RegionOne/4";//hack for Belgium workshop test
				try {
					hardwareID = execInterfacer.getSpecificHardware(Integer.parseInt(cloudID), cloudProviderId) + "";
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
				
				//cloudProviderId = "regionOne/c404edc0-0b8f-4749-bd94-dc65338c41f5";//hack for test
				cloudProviderId = "RegionOne/9c154d9a-fab9-4507-a3d7-21b72d31de97";//hack for Belgium workshop test
				try {
					imageID = execInterfacer.getSpecificImage(Integer.parseInt(cloudID), cloudProviderId, locationID) + "";
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
				
				LOGGER.log(Level.INFO, "Created VM Instance : ID " + vmInstID);
				if(dataShare.setVMIID(this.vmInstName, execInterfacer.trimResponseID(vmInstID)))
					LOGGER.log(Level.INFO, "Stored newly created VM Instance : ID " + vmInstID);
				else
					LOGGER.log(Level.WARNING, "Could not store newly created VM Instance : ID " + vmInstID);
			}

		} else if(task.getTaskType()==TaskType.UPDATE){
			
		} else if(task.getTaskType()==TaskType.DELETE){
			
		}
		
	}

	public ConfigurationTask getTask() {
		// TODO Auto-generated method stub
		return this.task;
	}
	
	public String getVMInstName(){
		return this.vmInstName;
	}
}
