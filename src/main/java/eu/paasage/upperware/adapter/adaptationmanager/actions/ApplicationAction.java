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

public class ApplicationAction implements Action {

	public ApplicationAction(DeploymentModel targetModel) {
		this.appName = (String) targetModel.eGet(targetModel.eClass()
				.getEStructuralFeature("name"));
	}
	
	public ApplicationAction(ConfigurationTask task, ExecInterfacer execInterfacer){
		this.task = task;
		System.out.println("Breakpoint0 " + this.task.toString());
		this.objParams = task.getJsonModel();
		this.execInterfacer = execInterfacer;
	}

	private String appName;
	private static final Logger LOGGER = Logger
			.getLogger(ApplicationAction.class.getName());

	private JsonObject objParams;
	private ConfigurationTask task;
	private ExecInterfacer execInterfacer;

	public void execute(Map<String, Object> input, Map<String, Object> output) throws ActionError {
		LOGGER.log(Level.INFO, "Application creation action");
		ExecInterfacer execInterfacer = (ExecInterfacer) input
				.get("execInterfacer");
		String applicationId;
		try {
			applicationId = execInterfacer.createApplication(appName);
		} catch (Exception e) {
			throw new ActionError();
		}
		output.putAll(input);
		output.put("applicationID",applicationId);
	}

	public void run(){
		
		if(task.getTaskType()==TaskType.CREATE){
			
			this.appName = objParams.get("name").asString();
			LOGGER.log(Level.INFO, "Application Type action (create) thread : name " + appName);
			
			//test for passing name parameter to Application Instance
			LOGGER.log(Level.INFO, this.appName);
			
			//To Do Exec API Call
			//String execWareAppID = "/api/application/"+this.appName;//substitute with API call
			String execWareAppID = execInterfacer.createApp(this.appName);
			LOGGER.log(Level.INFO, "ExecWare API Action (create App) : " + execWareAppID);
			
			dataShare.createApplication(execInterfacer.trimResponseID(execWareAppID), this.appName);
			
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
			
			this.appName = objParams.get("name").asString();
			LOGGER.log(Level.INFO, "Application Type action (update) thread : name " + appName);
			
			//test for passing name parameter to Application Instance
			LOGGER.log(Level.INFO, this.appName);
			
			String appID = dataShare.getApplicationId();
			
			try {
				int id = Integer.parseInt(appID);
				
				if(execInterfacer.updateApp(id, this.appName)){
					
					LOGGER.log(Level.INFO, "ExecWare API Action (update App) : " + appID);
					
					dataShare.updateApplication(appID, this.appName);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else if(task.getTaskType()==TaskType.DELETE){
			
			this.appName = objParams.get("name").asString();
			LOGGER.log(Level.INFO, "Application Type action (delete) thread : name " + appName);
			
			//test for passing name parameter to Application Instance
			LOGGER.log(Level.INFO, this.appName);
			
			String appID = dataShare.getApplicationId(this.appName);
			
			try {
				int id = Integer.parseInt(appID);
				
				if(execInterfacer.deleteApp(id)){
					
					LOGGER.log(Level.INFO, "ExecWare API Action (delete App) : " + appID);
					
					dataShare.deleteApplication(this.appName);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return;
	}

	public ConfigurationTask getTask() {
		// TODO Auto-generated method stub
		return this.task;
	}
	
	public String getAppName(){
		return appName;
	}
}
