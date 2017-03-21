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
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.parser.ParseException;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.upperware.adapter.adaptationmanager.REST.ExecInterfacer;
import eu.paasage.upperware.adapter.adaptationmanager.core.Coordinator;
import eu.paasage.upperware.adapter.adaptationmanager.validation.ApplicationController;
import eu.paasage.upperware.adapter.adaptationmanager.validation.MonitorEntity.Type;
import eu.paasage.upperware.plangenerator.model.task.ConfigurationTask;
import eu.paasage.upperware.plangenerator.type.TaskType;

public class InternalComponentInstanceAction implements Action {

	public InternalComponentInstanceAction(DeploymentModel targetModel) {
		this.iCompInstName = (String) targetModel.eGet(targetModel.eClass()
				.getEStructuralFeature("name"));
	}
	
	public InternalComponentInstanceAction(ConfigurationTask task, ExecInterfacer execInterfacer){
		this.task = task;
		this.objParams = task.getJsonModel();
		this.execInterfacer = execInterfacer;
	}

	private String iCompInstName;
	private static final Logger LOGGER = Logger
			.getLogger(InternalComponentInstanceAction.class.getName());

	private JsonObject objParams;
	private ConfigurationTask task;
	private ExecInterfacer execInterfacer;

	public void execute(Map<String, Object> input, Map<String, Object> output) throws ActionError {
		LOGGER.log(Level.INFO, "Application creation action");
		ExecInterfacer execInterfacer = (ExecInterfacer) input
				.get("execInterfacer");
		String applicationId;
		try {
			applicationId = execInterfacer.createApplication(iCompInstName);
		} catch (Exception e) {
			throw new ActionError();
		}
		output.putAll(input);
		output.put("applicationID",applicationId);
	}

	public void run(){
		
		if(task.getTaskType()==TaskType.CREATE){
			
			this.iCompInstName = objParams.get("name").asString();
			LOGGER.log(Level.INFO, "Internal Component Instance action (create) thread : name " + iCompInstName);
			
			//Fetching data for linked Application Instance, Application Component & VM Instance 
			
			//getting Camel Name from dependent Actions
			String appliInstCamelName = "";
			String appCompTypeName = "";
			String vmiCamelName = "";
			int appliInstID = -1;
			int appCompTypeID = -1;
			int vmiID = -1;
			
			String iCompInstID = null;
			
			boolean status = true;
			
			try{
				System.out.println("***" + this.toString() + " *** Data/Objects available from its dependencies ");
				//Collection<Object> depActions = Coordinator.getNeighbourDependencies(this);
				Collection<Action> depActions = Coordinator.getDependentActions(this);
				LOGGER.log(Level.INFO, "--------------Breakpoint IntCompInst (Create)--- " + depActions.size());
				for(Object obj : depActions){
					System.out.println("-- " + obj.toString() + " ");
					if(obj.getClass()==ApplicationInstanceAction.class){
						appliInstCamelName = ((ApplicationInstanceAction) obj).getAppInstName();
					}else if(obj.getClass()==VMInstanceAction.class){
						vmiCamelName = ((VMInstanceAction)obj).getVMInstName();
					}else if(obj.getClass()==InternalComponentAction.class){
						appCompTypeName = ((InternalComponentAction)obj).getCompName();
					}
				}
				
				if(appliInstCamelName.equalsIgnoreCase("")){
					appliInstCamelName = dataShare.getApplicationInstanceName_Camel();
				}
				
				if(appCompTypeName.equalsIgnoreCase("")){
					appCompTypeName = objParams.get("type").asString();
				}
				
				LOGGER.log(Level.INFO, "AppInstName: " + appliInstCamelName + " VMInst name: " + vmiCamelName + " IntComp name: " + appCompTypeName);
			} catch(Exception e){/*
				try {
				throw new ActionError();
			} catch (ActionError ae) {
				// TODO Auto-generated catch block
				ae.printStackTrace();
			}*/
				e.printStackTrace();
			}
			
			if(dataShare.addCompInst(iCompInstName, dataShare.getApplicationInstance(appliInstCamelName), dataShare.getEntityLCAppComponent(appCompTypeName), dataShare.getEntityVMInstance(vmiCamelName))){
				
				appliInstID = Integer.parseInt(dataShare.getApplicationInstanceId(appliInstCamelName));
				String appCompTID = dataShare.getAppCompID(appCompTypeName);
				appCompTypeID = Integer.parseInt(appCompTID);
				String vmInstID = dataShare.getEntityVMIid(vmiCamelName);
				vmiID = Integer.parseInt(vmInstID);
				
				LOGGER.log(Level.INFO, "Added Component Instance. Fetched appliInstID " + appliInstID + " appCompTypeID " + appCompTypeID + " vmiID " + vmiID);
				
				//To Do Exec API Call
				//iCompInstID = "/api/instance/" + iCompInstName;//POST using parameters appliInstID, appCompTypeID & vmiID
				iCompInstID = execInterfacer.createInstance(appliInstID, appCompTypeID, vmiID);
				LOGGER.log(Level.INFO, "Created Component Instance : ID " + iCompInstID);
				
/*				int temp = Integer.parseInt(execInterfacer.trimResponseID(iCompInstID));
				boolean stat = false;
				int timeout = 60;
				while((!(stat = execInterfacer.queryStateOKInstance(temp))) && timeout > 0){
					LOGGER.log(Level.INFO, "Waiting 30 secs for operation completion. Instance : ID " + iCompInstID);
					try {
						Thread.sleep(30000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					timeout++;
				}
				
				if(timeout > 0)
					status = true;
				else
					LOGGER.log(Level.WARNING, "Newly created Internal Comp Instance : ID " + iCompInstID + " has ERROR state");*/
				String iciId_string = execInterfacer.trimResponseID(iCompInstID);
				int iciId = Integer.parseInt(iciId_string);
				if(iCompInstID != null && dataShare.setCompInstID(iCompInstName, iciId_string) /*&& stat*/){
					LOGGER.log(Level.INFO, "Stored newly created Internal Comp Instance : ID " + iCompInstID);
					ApplicationController.addEntityToMonitor(Type.instance, iciId);
				}
				else
					LOGGER.log(Level.WARNING, "Could not store newly created Internal Comp Instance : ID " + iCompInstID);
			}

		} else if(task.getTaskType()==TaskType.UPDATE){//updates with the new CAMEL id generated by S2D for the Internal Component Instance in the new Deployment Model
			
			
			this.iCompInstName = objParams.get("name").asString();
			String old_iCompInstName = objParams.get("old_name").asString();
			LOGGER.log(Level.INFO, "Internal Component Instance action (update) thread : name " + iCompInstName);
			
			//Fetching data for linked Application Instance, Application Component & VM Instance 
			
			//getting Camel Name from dependent Actions
			String appliInstCamelName = "not_fetched";
			String appCompTypeName = "not_fetched";
			String vmiCamelName = "not_fetched";
			String old_vmiCamelName = "not_fetched";
			/*int appliInstID = -1;
			int appCompTypeID = -1;
			int vmiID = -1;*/
			
			String iCompInstID = null;
			
			boolean status = true;
			
			try{
				System.out.println("***" + this.toString() + " *** Data/Objects available from its dependencies ");
				//Collection<Object> depActions = Coordinator.getNeighbourDependencies(this);
				Collection<Action> depActions = Coordinator.getDependentActions(this);
				if(depActions != null){
					LOGGER.log(Level.INFO, "--------------Breakpoint IntCompInst (Update)--- " + depActions.size());
					for(Object obj : depActions){
						System.out.println("-- " + obj.toString() + " ");
						if(obj.getClass()==ApplicationInstanceAction.class){
							appliInstCamelName = ((ApplicationInstanceAction) obj).getAppInstName();
						}else if(obj.getClass()==VMInstanceAction.class){
							vmiCamelName = ((VMInstanceAction)obj).getVMInstName();
							old_vmiCamelName = ((VMInstanceAction)obj).getOldVMInstName();
						}else if(obj.getClass()==InternalComponentAction.class){
							appCompTypeName = ((InternalComponentAction)obj).getCompName();
						}
					}
				}
				
				System.out.println("AppInstName: " + appliInstCamelName + " old_VMInst name: " + old_vmiCamelName + " VMInst name: " + vmiCamelName + " IntComp name: " + appCompTypeName);
			} catch(Exception e){/*
				try {
				throw new ActionError();
			} catch (ActionError ae) {
				// TODO Auto-generated catch block
				ae.printStackTrace();
			}*/
				e.printStackTrace();
			}
			
			if(dataShare.updateCompInstName(old_iCompInstName, iCompInstName))
				
				LOGGER.log(Level.INFO, "Mapping for Component Instance name " + old_iCompInstName + " changed to " + this.iCompInstName);
			
			else
				
				LOGGER.log(Level.WARNING, "Mapping for Component Instance name " + old_iCompInstName + " could not be changed to " + this.iCompInstName);
			
			
			/*appliInstID = Integer.parseInt(dataShare.getApplicationInstanceId(appliInstCamelName));
			String appCompTID = dataShare.getAppCompID(appCompTypeName);
			appCompTypeID = Integer.parseInt(appCompTID);
			String vmInstID = dataShare.getEntityVMIid(vmiCamelName);
			vmiID = Integer.parseInt(vmInstID);
			
			LOGGER.log(Level.INFO, "To update Component Instance. Fetched appliInstID " + appliInstID + " appCompTypeID " + appCompTypeID + " vmiID " + vmiID);
			
			iCompInstID = dataShare.getCompInstID(iCompInstName);
			int iCompInstID_temp = Integer.parseInt(iCompInstID);
			
			//To Do Exec API Call
			//iCompInstID = "/api/instance/" + iCompInstName;//POST using parameters appliInstID, appCompTypeID & vmiID
			if(status = execInterfacer.updateInstance(iCompInstID_temp, appliInstID, appCompTypeID, vmiID)){
				LOGGER.log(Level.INFO, "Updated Component Instance : ID " + iCompInstID);
				status = status && dataShare.updateCompInst(iCompInstName, dataShare.getApplicationInstance(appliInstCamelName), dataShare.getEntityLCAppComponent(appCompTypeName), dataShare.getEntityVMInstance(vmiCamelName));
				if(status)
					LOGGER.log(Level.INFO, "Mapping Updated for Component Instance : ID " + iCompInstID);
			}			*/
			
		} else if(task.getTaskType()==TaskType.DELETE){
			
			
			this.iCompInstName = objParams.get("name").asString();
			LOGGER.log(Level.INFO, "Internal Component Instance action (delete) thread : name " + iCompInstName);
			
			//Fetching data for linked Application Instance, Application Component & VM Instance 
			
			//getting Camel Name from dependent Actions
			String appliInstCamelName = "";
			String appCompTypeName = "";
			String vmiCamelName = "";
			int appliInstID = -1;
			int appCompTypeID = -1;
			int vmiID = -1;
			
			String iCompInstID = null;
			
			boolean deleted = true;
			boolean status = false;
			
			try{
				System.out.println("***" + this.toString() + " *** Data/Objects available from its dependencies ");
				//Collection<Object> depActions = Coordinator.getNeighbourDependencies(this);
				Collection<Action> depActions = Coordinator.getDependentActions(this);
				if(depActions != null){
					LOGGER.log(Level.INFO, "--------------Breakpoint IntCompInst (Delete)--- " + depActions.size());
					for(Object obj : depActions){
						System.out.println("-- " + obj.toString() + " ");
						if(obj.getClass()==ApplicationInstanceAction.class){//doesn't depend for deletion, so commenting
							//appliInstCamelName = ((ApplicationInstanceAction) obj).getAppInstName();
						}else if(obj.getClass()==VMInstanceAction.class){//doesn't depend for deletion, so commenting
							//vmiCamelName = ((VMInstanceAction)obj).getVMInstName();
						}else if(obj.getClass()==InternalComponentAction.class){//doesn't depend for deletion, so commenting
							//appCompTypeName = ((InternalComponentAction)obj).getCompName();
						}
					}
				}				
				//System.out.println("AppInstName: " + appliInstCamelName + " VMInst name: " + vmiCamelName + " IntComp name: " + appCompTypeName);
			} catch(Exception e){/*
				try {
				throw new ActionError();
			} catch (ActionError ae) {
				// TODO Auto-generated catch block
				ae.printStackTrace();
			}*/
				e.printStackTrace();
			}
			
			/*appliInstID = Integer.parseInt(dataShare.getApplicationInstanceId(appliInstCamelName));
			String appCompTID = dataShare.getAppCompID(appCompTypeName);
			appCompTypeID = Integer.parseInt(appCompTID);
			String vmInstID = dataShare.getEntityVMIid(vmiCamelName);
			vmiID = Integer.parseInt(vmInstID);
			
			LOGGER.log(Level.INFO, "To delete Component Instance. Fetched appliInstID " + appliInstID + " appCompTypeID " + appCompTypeID + " vmiID " + vmiID);*/
			
			iCompInstID = dataShare.getCompInstID(iCompInstName);
			int iCompInstID_temp = Integer.parseInt(iCompInstID);
			
			//To Do Exec API Call
			//iCompInstID = "/api/instance/" + iCompInstName;//POST using parameters appliInstID, appCompTypeID & vmiID
			try {
				deleted = execInterfacer.deleteInstance(iCompInstID_temp);
			
				int timeout = 60;
				while(deleted && (!(status = execInterfacer.queryStateDeletedInstance(iCompInstID_temp))) && timeout > 0){
					LOGGER.log(Level.INFO, "Waiting 30 secs for deletion operation completion. VM Instance : ID " + iCompInstID_temp);
					try {
						Thread.sleep(30000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					timeout--;
				}
				
				if(deleted && (timeout > 0))
					status = true;
				else
					LOGGER.log(Level.WARNING, "Error deleting Component Instance : ID " + iCompInstID_temp);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			/*LOGGER.log(Level.INFO, "Waiting for 2 mins");
			
			try {
				Thread.sleep(120000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			if(deleted && status){
				LOGGER.log(Level.INFO, "Deleted Component Instance : ID " + iCompInstID);
				deleted = deleted && dataShare.deleteCompInst(iCompInstName);
				if(deleted){
					LOGGER.log(Level.INFO, "Mapping Updated for Component Instance : ID " + iCompInstID);
					
/*					try {
						LOGGER.log(Level.INFO, "Sleeping for two minutes enabling completion of deletion operation");
						Thread.sleep(120000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
				}
			}
		}
	}

	public ConfigurationTask getTask() {
		// TODO Auto-generated method stub
		return this.task;
	}
	
	public String getICompName(){
		return this.iCompInstName;
	}
	
	/**
	 * Might return NULL as optional value
	 * @return provided Communication Instances
	 */
	public JsonArray getProvidedCommInstances(){
		//System.out.println("Provided Comp Name: " + iCompName);
		JsonValue jval;
		/*if(objParams == null)
			System.out.println("NULL!!!!" + iCompName);*/
		return ((jval = objParams.get("providedCommunicationInstances"))!=null? jval.asArray(): null);
	}
	
	public boolean isProvidedComm(String key){
		JsonArray providedComm = getProvidedCommInstances();
		if(providedComm != null){
			Iterator<JsonValue> iterator = providedComm.iterator(); 
			while(iterator.hasNext())
				if(iterator.next().asString().equalsIgnoreCase(key))
					return true;
		}
		return false;
	}
	
	public JsonArray getRequiredCommInstances(){
		//System.out.println("Required Comp Name: " + iCompName);
		return objParams.get("requiredCommunicationInstances").asArray();
	}
	
	public boolean isRequiredComm(String key){
		JsonArray requiredComm = getRequiredCommInstances();
		Iterator<JsonValue> iterator = requiredComm.iterator(); 
		while(iterator.hasNext())
			if(iterator.next().asString().equalsIgnoreCase(key))
				return true;
		return false;
	}
}
