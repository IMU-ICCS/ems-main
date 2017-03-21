/*
 * Copyright (c) 2015 INRIA, INSA Rennes
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.adaptationmanager.actions;

import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.eclipsesource.json.JsonObject;

import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.upperware.adapter.adaptationmanager.REST.ExecInterfacer;
import eu.paasage.upperware.adapter.adaptationmanager.core.Coordinator;
import eu.paasage.upperware.plangenerator.model.task.ConfigurationTask;
import eu.paasage.upperware.plangenerator.type.TaskType;

public class ApplicationInstanceAction implements Action {

	public ApplicationInstanceAction(DeploymentModel targetModel) {
		this.appInstName = (String) targetModel.eGet(targetModel.eClass()
				.getEStructuralFeature("name"));
	}
	
	public ApplicationInstanceAction(ConfigurationTask task, ExecInterfacer execInterfacer){
		this.task = task;
		this.objParams = task.getJsonModel();
		this.execInterfacer = execInterfacer;
	}

	private String appInstName;
	private static final Logger LOGGER = Logger
			.getLogger(ApplicationInstanceAction.class.getName());

	private JsonObject objParams;
	private ConfigurationTask task;
	private ExecInterfacer execInterfacer;

	public void execute(Map<String, Object> input, Map<String, Object> output) throws ActionError {
		LOGGER.log(Level.INFO, "Application creation action");
		ExecInterfacer execInterfacer = (ExecInterfacer) input
				.get("execInterfacer");
		String applicationId;
		try {
			applicationId = execInterfacer.createApplication(appInstName);
		} catch (Exception e) {
			throw new ActionError();
		}
		output.putAll(input);
		output.put("applicationID",applicationId);
	}

	public void run(){
		
		if(task.getTaskType()==TaskType.CREATE){
			
			this.appInstName = objParams.get("name").asString();
			LOGGER.log(Level.INFO, "Application Instance action thread : name " + appInstName);
			
			//LOGGER.log(Level.INFO, "from Application Instance " + dataShare.getApplicationId());
			
			//To Do Exec API Call
			String appType = objParams.get("type").asString();
			String appExecId = dataShare.getApplicationId(appType);
			int appExId = Integer.parseInt(appExecId);
			//Application app = dataShare.getApplication(appType);
			//String execWareAppInstID = "/api/applicationInstance/"+this.appInstName;//substitute with API call
			String execWareAppInstID = execInterfacer.createApplicationInstance(appExId);
			LOGGER.log(Level.INFO, "ExecWare API Action (create App Inst) : " + execWareAppInstID);
			dataShare.createApplicationInstance(dataShare.getApplication(appType), execInterfacer.trimResponseID(execWareAppInstID), appInstName);
			
			
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
				
				dataShare.createApplication(appInstName, appInstName);
				
			} catch(Exception e){
				try {
					throw new ActionError();
				} catch (ActionError ae) {
					// TODO Auto-generated catch block
					ae.printStackTrace();
				}
			}*/

		} else if(task.getTaskType()==TaskType.UPDATE){
			
			this.appInstName = objParams.get("name").asString();
			LOGGER.log(Level.INFO, "Application Instance action thread : name " + appInstName);
			
			//LOGGER.log(Level.INFO, "from Application Instance " + dataShare.getApplicationId());
			
			//To Do Exec API Call
			String appType = objParams.get("type").asString();
			String appExecId = dataShare.getApplicationId(appType);
			int appExId = Integer.parseInt(appExecId);
			//Application app = dataShare.getApplication(appType);
			//String execWareAppInstID = "/api/applicationInstance/"+this.appInstName;//substitute with API call
			String execWareAppInstID = dataShare.getApplicationInstanceId();
			if(execInterfacer.updateApplicationInstance(Integer.parseInt(execWareAppInstID), appExId)){
				LOGGER.log(Level.INFO, "ExecWare API Action (update App Inst) : " + execWareAppInstID);
				dataShare.updateApplicationInstanceName(execWareAppInstID, this.appInstName);
			}
			
		} else if(task.getTaskType()==TaskType.DELETE){
			
			this.appInstName = objParams.get("name").asString();
			LOGGER.log(Level.INFO, "Application Instance action thread : name " + appInstName);
			
			//LOGGER.log(Level.INFO, "from Application Instance " + dataShare.getApplicationId());
			
			//To Do Exec API Call
/*			String appType = objParams.get("type").asString();
			String appExecId = dataShare.getApplicationId(appType);
			int appExId = Integer.parseInt(appExecId);*/
			//Application app = dataShare.getApplication(appType);
			//String execWareAppInstID = "/api/applicationInstance/"+this.appInstName;//substitute with API call
			String execWareAppInstID = dataShare.getApplicationInstanceId();
			if(execInterfacer.deleteApplicationInstance(execWareAppInstID)){
				LOGGER.log(Level.INFO, "ExecWare API Action (delete App Inst) : " + execWareAppInstID);
				dataShare.deleteApplicationInstance(execWareAppInstID);
			}
		}
	}

	public ConfigurationTask getTask() {
		// TODO Auto-generated method stub
		return this.task;
	}
	
	public String getAppInstName(){
		return this.appInstName;
	}
}
