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

public class CommunicationInstanceAction implements Action {

	public CommunicationInstanceAction(DeploymentModel targetModel) {
		this.communicationInstName = (String) targetModel.eGet(targetModel.eClass()
				.getEStructuralFeature("name"));
	}
	
	public CommunicationInstanceAction(ConfigurationTask task, ExecInterfacer execInterfacer){
		this.task = task;
		this.objParams = task.getJsonModel();
		this.execInterfacer = execInterfacer;
	}

	private String communicationInstName;
	private static final Logger LOGGER = Logger
			.getLogger(CommunicationInstanceAction.class.getName());

	private JsonObject objParams;
	private ConfigurationTask task;
	private ExecInterfacer execInterfacer;

	public void execute(Map<String, Object> input, Map<String, Object> output) throws ActionError {
		LOGGER.log(Level.INFO, "Application creation action");
		ExecInterfacer execInterfacer = (ExecInterfacer) input
				.get("execInterfacer");
		String applicationId;
		try {
			applicationId = execInterfacer.createApplication(communicationInstName);
		} catch (Exception e) {
			throw new ActionError();
		}
		output.putAll(input);
		output.put("applicationID",applicationId);
	}

	public void run(){
		
		if(task.getTaskType()==TaskType.CREATE){
			
			this.communicationInstName = objParams.get("name").asString();
			LOGGER.log(Level.INFO, "Communication Instance action thread : name " + communicationInstName);
			
			String commInstExecID = null;

			String commTypeName = objParams.get("type").asString();
			int commTypeID = -1;
			
			String providerInst = objParams.get("providerInstance").asString();
			String provCompInstName = null;
			int provCompInstID = -1;
			
			String consumerInst = objParams.get("consumerInstance").asString();
			String consCompInstName = null;
			int consCompInstID = -1;
			
			LOGGER.log(Level.INFO, "providerInst consumerInst: " + providerInst + " " + consumerInst);
			
			Collection<Action> depActions = Coordinator.getDependentActions(this);
			LOGGER.log(Level.INFO, "--------------Breakpoint CommInstAct--- " + depActions.size());
			for(Object obj : depActions){
				System.out.println("-- " + obj.toString() + " ");
				if(obj.getClass()==InternalComponentInstanceAction.class && ((InternalComponentInstanceAction) obj).isProvidedComm(providerInst)){
					provCompInstName = ((InternalComponentInstanceAction) obj).getICompName();
				}else if(obj.getClass()==InternalComponentInstanceAction.class && ((InternalComponentInstanceAction) obj).isRequiredComm(consumerInst)){
					consCompInstName = ((InternalComponentInstanceAction) obj).getICompName();
				}
			}
			
			LOGGER.log(Level.INFO, "providerCompInst consumerCompInst: " + provCompInstName + " " + consCompInstName);
			
			//if(dataShare.addCommInst(communicationInstName, dataShare.getEntityCommunication(commTypeName), dataShare.getEntityComponentInstance(providerInst), dataShare.getEntityComponentInstance(consumerInst))){
			if(dataShare.addCommInst(communicationInstName, dataShare.getEntityCommunication(commTypeName), dataShare.getEntityComponentInstance(provCompInstName), dataShare.getEntityComponentInstance(consCompInstName))){
				
				commTypeID = Integer.parseInt(dataShare.getCommID(commTypeName));
				String provCompInstTempID = dataShare.getCompInstID(provCompInstName);
				provCompInstID = Integer.parseInt(provCompInstTempID);
				String consCompInstTempID = dataShare.getCompInstID(consCompInstName);
				consCompInstID = Integer.parseInt(consCompInstTempID);
				
				//To Do Exec API Call
				//commInstExecID = "/api/communicationChannel/" + communicationInstName;//POST using parameters commTypeID, provCompInstID, consCompInstID
				commInstExecID = execInterfacer.createCommunicationChannel(provCompInstID, consCompInstID, commTypeID);
				LOGGER.log(Level.INFO, "Created Communication Instance : ID " + commInstExecID);
				
				if(commInstExecID != null && dataShare.setCommInstID(communicationInstName, execInterfacer.trimResponseID(commInstExecID)))
					LOGGER.log(Level.INFO, "Stored newly created Communication Instance entity : ID " + commInstExecID);
				else
					LOGGER.log(Level.WARNING, "Could not store newly created Communication Instance entity : ID " + commInstExecID);
			}

		} else if(task.getTaskType()==TaskType.UPDATE){
			
		} else if(task.getTaskType()==TaskType.DELETE){
			
		}		
	}

	public ConfigurationTask getTask() {
		// TODO Auto-generated method stub
		return this.task;
	}
}
