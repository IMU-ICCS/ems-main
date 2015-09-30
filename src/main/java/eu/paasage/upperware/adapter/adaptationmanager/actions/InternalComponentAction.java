/*
 * Copyright (c) 2015 INRIA, INSA Rennes
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.adaptationmanager.actions;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.upperware.adapter.adaptationmanager.REST.ExecInterfacer;
import eu.paasage.upperware.adapter.adaptationmanager.core.Coordinator;
import eu.paasage.upperware.plangenerator.model.task.ConfigurationTask;
import eu.paasage.upperware.plangenerator.type.TaskType;

public class InternalComponentAction implements Action {

	public InternalComponentAction(DeploymentModel targetModel) {
		this.iCompName = (String) targetModel.eGet(targetModel.eClass()
				.getEStructuralFeature("name"));
	}
	
	public InternalComponentAction(ConfigurationTask task, ExecInterfacer execInterfacer){
		this.task = task;
		this.objParams = task.getJsonModel();
		this.execInterfacer = execInterfacer;
	}

	private String iCompName;
	
	private static final Logger LOGGER = Logger
			.getLogger(InternalComponentAction.class.getName());

	private JsonObject objParams;
	private ConfigurationTask task;
	private ExecInterfacer execInterfacer;

	public void execute(Map<String, Object> input, Map<String, Object> output) throws ActionError {
		LOGGER.log(Level.INFO, "Application creation action");
		ExecInterfacer execInterfacer = (ExecInterfacer) input
				.get("execInterfacer");
		String applicationId;
		try {
			applicationId = execInterfacer.createApplication(iCompName);
		} catch (Exception e) {
			throw new ActionError();
		}
		output.putAll(input);
		output.put("applicationID",applicationId);
	}

	public void run(){
		
		if(task.getTaskType()==TaskType.CREATE){
			
			this.iCompName = objParams.get("name").asString();
			LOGGER.log(Level.INFO, "Internal Component Type action thread : name " + iCompName);
			
			//Preparation for Lifecycle Comp params
			JsonValue jval;
			String downloadCmd = "", installCmd = "", startCmd = "", StopCmd = "";		
			try{
				if((jval = objParams.get("downloadCmd"))!=null)
					downloadCmd = jval.asString();

				if((jval = objParams.get("installCmd")) != null)
					installCmd = jval.asString();

				if((jval = objParams.get("startCmd")) != null)
					startCmd = jval.asString();

				if((jval = objParams.get("StopCmd"))!=null)
					StopCmd = jval.asString();

				LOGGER.log(Level.INFO, "downloadCmd " + downloadCmd.toString() + " installCmd " + installCmd.toString() + " startCmd " + startCmd.toString() + " StopCmd " + StopCmd.toString());

			} catch(NullPointerException npe){//objParams.get("") throws NullPointerException if key not found
				npe.printStackTrace();
			}
			
			//Fetching data for linked Application & VM Template 
			
			//getting Camel Name from dependent Actions
			String appliCamelName = "";
			int applID = -1;
			String vmtCamelName = "";
			int vmtID = -1;
			String LCcompID = null;
			String LCcompID_temp = null;
			boolean status = false;
			String AppCompID = null;
			
			try{
				System.out.println("***" + this.toString() + " *** Data/Objects available from its dependencies ");
				//Collection<Object> depActions = Coordinator.getNeighbourDependencies(this);
				Collection<Action> depActions = Coordinator.getDependentActions(this);
				LOGGER.log(Level.INFO, "--------------Breakpoint IntComp--- " + depActions.size());
				for(Object obj : depActions){
					System.out.println("-- " + obj.toString() + " ");
					if(obj.getClass()==ApplicationAction.class){
						appliCamelName = ((ApplicationAction) obj).getAppName();
					}else if(obj.getClass()==VMAction.class){
						vmtCamelName = ((VMAction)obj).getVMName();
					}
				}
				
				System.out.println("AppName:: " + appliCamelName + " VMT name: " + vmtCamelName);
				
				//dataShare.getApplication(appliCamelName);//fetching Application object from dependency
				//dataShare.getEntityVMT(vmtCamelName);//fetching VM Template object from dependency
				if(dataShare.addLCAC(iCompName, downloadCmd, installCmd, startCmd, StopCmd, dataShare.getApplication(appliCamelName), dataShare.getEntityVMT(vmtCamelName))){
					//To Do Exec API Call
					//LCcompID = "/api/lifecycleComponent/" + iCompName;//POST using parameters iCompName, downloadCmd, installCmd, startCmd, StopCmd
					LCcompID = execInterfacer.createLifecycleComponent(iCompName, downloadCmd, installCmd, startCmd, StopCmd);
					
					LOGGER.log(Level.INFO, "Created LC Component : ID " + LCcompID);
					LCcompID_temp = execInterfacer.trimResponseID(LCcompID);				
					if(LCcompID != null && dataShare.setLCACID(iCompName, LCcompID_temp)){
						LOGGER.log(Level.INFO, "Stored newly created LC Comp Instance : ID " + LCcompID);
						status = true;
					}else
						LOGGER.log(Level.WARNING, "Could not store newly created LC Comp Instance : ID " + LCcompID);
				} else{
					LCcompID = dataShare.getAppCompID(iCompName);
					LCcompID_temp = execInterfacer.trimResponseID(LCcompID);
					if(LCcompID_temp!= null)
						status = true;
					LOGGER.log(Level.INFO, "Retreived LC Comp Instance " + iCompName + " : ID " + LCcompID);
				}
				
				if(status){
					//To Do Exec API Call
					applID = Integer.parseInt(dataShare.getApplicationId(appliCamelName));
					vmtID = Integer.parseInt(dataShare.getEntityVMTid(vmtCamelName));
					//AppCompID = "/api/ac/" + iCompName;//POST using parameters LCcompID, dataShare.getApplicationId(appliCamelName)
					AppCompID = execInterfacer.createApplicationComponent(applID, Integer.parseInt(LCcompID_temp), vmtID);
					
					if(AppCompID != null && dataShare.setAppCompID(iCompName, execInterfacer.trimResponseID(AppCompID)))
						LOGGER.log(Level.INFO, "Stored newly created App Comp Instance : ID " + AppCompID);
					else
						LOGGER.log(Level.WARNING, "Could not store newly created App Comp Instance : ID " + AppCompID);
				}
				
			} catch(Exception e){/*
				try {
					throw new ActionError();
				} catch (ActionError ae) {
					// TODO Auto-generated catch block
					ae.printStackTrace();
				}*/
				e.printStackTrace();
			}
			
			
			
			//this.task.getDependencies();
			
			
			//dataShare.createApplication(this.iCompName, this.iCompName);
			
	/*		try{
				ExecInterfacer execInterfacer = (ExecInterfacer) Coordinator.getObject("execInterfacer");
				String applicationId = execInterfacer.createApplication(appName);
				Coordinator.putObject(appName, applicationId);
				Coordinator.printObjects();
				
				System.out.print("***" + this.toString() + "*** Data/Objects available from its dependencies ");
				Collection<Object> depActions = Coordinator.getNeighbourDependencies(this);
				for(Object obj : depActions)
					System.out.print("-- " + obj.toString());
				System.out.print("\n");
				
				dataShare.createApplication(appName, appName);
				
			} catch(Exception e){
				try {
					throw new ActionError();
				} catch (ActionError ae) {
					// TODO Auto-generated catch block
					ae.printStackTrace();
				}
			}*/

		} else if(task.getTaskType()==TaskType.UPDATE){
			
		} else if(task.getTaskType()==TaskType.DELETE){
			
		}
		
		
	}

	public ConfigurationTask getTask() {
		// TODO Auto-generated method stub
		return this.task;
	}
	
	public String getCompName(){
		return iCompName;
	}
	
	/**
	 * Might return NULL as optional value
	 * @return provided Communications
	 */
	public JsonArray getProvidedComms(){
		//System.out.println("Provided Comp Name: " + iCompName);
		JsonValue jval;
		/*if(objParams == null)
			System.out.println("NULL!!!!" + iCompName);*/
		return ((jval = objParams.get("providedCommunications"))!=null? jval.asArray(): null);
	}
	
	public boolean isProvidedComm(String key){
		JsonArray providedComm = getProvidedComms();
		if(providedComm != null){
			Iterator<JsonValue> iterator = providedComm.iterator(); 
			while(iterator.hasNext())
				if(iterator.next().asString().equalsIgnoreCase(key))
					return true;
		}
		return false;
	}
	
	public JsonArray getRequiredComms(){
		//System.out.println("Required Comp Name: " + iCompName);
		return objParams.get("requiredCommunications").asArray();
	}
	
	public boolean isRequiredComm(String key){
		JsonArray requiredComm = getRequiredComms();
		Iterator<JsonValue> iterator = requiredComm.iterator(); 
		while(iterator.hasNext())
			if(iterator.next().asString().equalsIgnoreCase(key))
				return true;
		return false;
	}
}
