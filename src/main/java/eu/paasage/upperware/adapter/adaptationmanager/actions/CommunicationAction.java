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

public class CommunicationAction implements Action {

	public CommunicationAction(DeploymentModel targetModel) {
		this.communicationName = (String) targetModel.eGet(targetModel.eClass()
				.getEStructuralFeature("name"));
	}
	
	public CommunicationAction(ConfigurationTask task, ExecInterfacer execInterfacer){
		this.task = task;
		this.objParams = task.getJsonModel();
		this.execInterfacer = execInterfacer;
	}

	private String communicationName;
	private static final Logger LOGGER = Logger
			.getLogger(CommunicationAction.class.getName());

	private JsonObject objParams;
	private ConfigurationTask task;
	private ExecInterfacer execInterfacer;

	public void execute(Map<String, Object> input, Map<String, Object> output) throws ActionError {
		LOGGER.log(Level.INFO, "Application creation action");
		ExecInterfacer execInterfacer = (ExecInterfacer) input
				.get("execInterfacer");
		String applicationId;
		try {
			applicationId = execInterfacer.createApplication(communicationName);
		} catch (Exception e) {
			throw new ActionError();
		}
		output.putAll(input);
		output.put("applicationID",applicationId);
	}

	public void run(){
		
		if(task.getTaskType()==TaskType.CREATE){
			
			this.communicationName = objParams.get("name").asString();
			LOGGER.log(Level.INFO, "Communication Type action thread : name " + communicationName);
			
			String commExecID = null;
			
			String provider = objParams.get("provider").asString();
			String provCompName = null;
			String provCompID = null;
			
			String provPort = objParams.get("providerPort").toString();
			String provPortName = null;
			String provPortID = null;
			
			String consumer = objParams.get("consumer").asString();
			String consCompName = null;
			String consCompID = null;
			
			String consPort = objParams.get("consumerPort").toString();
			String consPortName = null;
			String consPortID = null;
			
			LOGGER.log(Level.INFO, "provider port consumer port: " + provider + " " + provPort + " " + consumer + " " + consPort);
			
			Collection<Action> depActions = Coordinator.getDependentActions(this);
			LOGGER.log(Level.INFO, "--------------Breakpoint CommAct--- " + depActions.size());
			for(Object obj : depActions){
				System.out.println("-- " + obj.toString() + " ");
				if(obj.getClass()==InternalComponentAction.class && ((InternalComponentAction) obj).isProvidedComm(provider)){
					provCompName = ((InternalComponentAction) obj).getCompName();
				}else if(obj.getClass()==InternalComponentAction.class && ((InternalComponentAction) obj).isRequiredComm(consumer)){
					consCompName = ((InternalComponentAction) obj).getCompName();
				}
			}
			
			LOGGER.log(Level.INFO, "providerComp consumerComp: " + provCompName + " " + consCompName);
			
			provPortName = "prov-" + provCompName + "-" + provPort;
			consPortName = "cons-" + consCompName + "-" + consPort;
			LOGGER.log(Level.INFO, "provPortName consPortName: " + provPortName + " " + consPortName);
			
			if(dataShare.existsProvPort(provPortName)){
				
				provPortID = dataShare.getProvPortID(provPortName);
			
			}else if(dataShare.addProvPort(provPortName, dataShare.getEntityLCAppComponent(provCompName), provPort)){
				provCompID = dataShare.getAppCompID(provCompName);
				//To Do Exec API Call
				//provPortID = "/api/communication/" + communicationName;//POST using parameters provPortName, provCompID, provPort
				provPortID = execInterfacer.createProviderPort(provPortName, Integer.parseInt(provCompID), Integer.parseInt(provPort));
				
				LOGGER.log(Level.INFO, "Created provider port : ID " + provPortID);
				
				if(provPortID != null && !provPortID.equals("")  && dataShare.setProvPortID(provPortName, provPortID))
					LOGGER.log(Level.INFO, "Stored newly created provider port Instance : ID " + provPortID);
				else
					LOGGER.log(Level.WARNING, "Could not store newly created provider port Instance : ID " + provPortID);
			}
			
			if(dataShare.existsReqPort(consPortName)){
				
				consPortID = dataShare.getReqPortID(consPortName);
				
			}else if(dataShare.addReqPort(consPortName, dataShare.getEntityLCAppComponent(consCompName), consPort)){
				consCompID = dataShare.getAppCompID(consCompName);
				//To Do Exec API Call
				//provPortID = "/api/communication/" + communicationName;//POST using parameters consPortName, consCompID, consPort
				consPortID = execInterfacer.createConsumerPort(consPortName, Integer.parseInt(consCompID), Integer.parseInt(consPort));
				
				LOGGER.log(Level.INFO, "Created required port : ID " + consPortID);
				
				if(consPortID != null && !consPortID.equals("") && dataShare.setReqPortID(consPortName, consPortID))
					LOGGER.log(Level.INFO, "Stored newly created required port Instance : ID " + consPortID);
				else
					LOGGER.log(Level.WARNING, "Could not store newly created required port Instance : ID " + consPortID);
			}
			
			
			if(provPortID != null && consPortID != null && dataShare.addComm(communicationName, dataShare.getEntityProvPort(provPortName), dataShare.getEntityReqPort(consPortName))){

				//To Do Exec API Call
				//commExecID = "/api/communication/" + communicationName;//POST using parameters communicationName, provCompID, consCompID, port
				commExecID = execInterfacer.createCommunication(Integer.parseInt(execInterfacer.trimResponseID(provPortID)), Integer.parseInt(execInterfacer.trimResponseID(consPortID)));
				
				LOGGER.log(Level.INFO, "Created Communication : ID " + commExecID);
				
				if(commExecID != null && !commExecID.equals("") && dataShare.setCommID(communicationName, execInterfacer.trimResponseID(commExecID)))
					LOGGER.log(Level.INFO, "Stored newly created Communication Type Instance : ID " + commExecID);
				else
					LOGGER.log(Level.WARNING, "Could not store newly created Communication Type Instance : ID " + commExecID);
			}			
			
			/*if(dataShare.addComm(communicationName, dataShare.getEntityLCAppComponent(provCompName), dataShare.getEntityLCAppComponent(consCompName), provPort, consPort)){
				provCompID = dataShare.getAppCompID(provCompName);
				consCompID = dataShare.getAppCompID(consCompName);
				//To Do Exec API Call
				//commExecID = "/api/communication/" + communicationName;//POST using parameters communicationName, provCompID, consCompID, port
				commExecID = execInterfacer.createCommunication(Integer.parseInt(provCompID), Integer.parseInt(consCompID), Integer.parseInt(provPort));
				
				LOGGER.log(Level.INFO, "Created Communication : ID " + commExecID);
				
				if(commExecID != null && dataShare.setCommID(communicationName, execInterfacer.trimResponseID(commExecID)))
					LOGGER.log(Level.INFO, "Stored newly created Communication Type Instance : ID " + commExecID);
				else
					LOGGER.log(Level.WARNING, "Could not store newly created Communication Type Instance : ID " + commExecID);
			}*/

		}else if(task.getTaskType()==TaskType.UPDATE){
			
		}else if(task.getTaskType()==TaskType.DELETE){
			
		}
		
	}

	public ConfigurationTask getTask() {
		// TODO Auto-generated method stub
		return this.task;
	}
}
