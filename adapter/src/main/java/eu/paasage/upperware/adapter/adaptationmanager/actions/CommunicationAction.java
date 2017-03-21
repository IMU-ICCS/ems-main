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
//	private boolean actionDone = false;

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
			
			String consumer = null;
			String consCompName = null;
			String consCompID = null;

			//Required port params
			String consPort = null;
			String consPortName = null;
			String consPortID = null;
			String isMandatory = null;
			String requiredPortstartCmd = "null";
			if(objParams.get("requiredPortstartCmd") != null){
				requiredPortstartCmd = objParams.get("requiredPortstartCmd").asString();
				LOGGER.log(Level.INFO, "START cmd : " + requiredPortstartCmd);
			}
			
			//setting the params when not relevant to the public port
			boolean containsOrphan = communicationName.toLowerCase().contains("orphan");
			System.out.println("Consumer for orphan? " + containsOrphan);
			if(!containsOrphan){
				consumer = objParams.get("consumer").asString();
				consPort = objParams.get("consumerPort").toString();
				isMandatory = objParams.get("isMandatory").toString();
			}
			System.out.println("Communication name: " + communicationName);
			
			LOGGER.log(Level.INFO, "provider port consumer port: " + provider + " " + provPort + " " + consumer + " " + consPort);
			
			//check if created forcibly from VMType action
			boolean commTypeExists = false;
			if(dataShare.getCommID(communicationName)!=null){
				commTypeExists = true;
				LOGGER.log(Level.INFO, "Communication exists : " + communicationName);
			}
			
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
			
			//provPortName = "prov-" + provCompName + "-" + provPort;//To put exact name
			//consPortName = "cons-" + consCompName + "-" + consPort;
			provPortName = provider;
			consPortName = consumer;
			
			LOGGER.log(Level.INFO, "provPortName consPortName: " + provPortName + " " + consPortName);
			
			if(communicationName.toLowerCase().contains("orphan")){
				LOGGER.log(Level.INFO, "Orphan port detected : " + communicationName);
				commTypeExists = true;
			}
			
			if(dataShare.existsProvPort(provPortName)){
				
				provPortID = dataShare.getProvPortID(provPortName);
			
			}else if(dataShare.getEntityLCAppComponent(provCompName)!= null && dataShare.addProvPort(provPortName, dataShare.getEntityLCAppComponent(provCompName), provPort)){
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
				
			}else if(dataShare.getEntityLCAppComponent(consCompName) != null && dataShare.addReqPort(consPortName, dataShare.getEntityLCAppComponent(consCompName), consPort, isMandatory, requiredPortstartCmd)){
				consCompID = dataShare.getAppCompID(consCompName);
				//To Do Exec API Call
				//provPortID = "/api/communication/" + communicationName;//POST using parameters consPortName, consCompID, consPort
				consPortID = execInterfacer.createConsumerPort(consPortName, Integer.parseInt(consCompID), Integer.parseInt(consPort), isMandatory, requiredPortstartCmd);
				
				LOGGER.log(Level.INFO, "Created required port : ID " + consPortID);
				
				if(consPortID != null && !consPortID.equals("") && dataShare.setReqPortID(consPortName, consPortID))
					LOGGER.log(Level.INFO, "Stored newly created required port Instance : ID " + consPortID);
				else
					LOGGER.log(Level.WARNING, "Could not store newly created required port Instance : ID " + consPortID);
			}
			
			
			if(!containsOrphan && provPortID != null && consPortID != null && dataShare.addComm(communicationName, dataShare.getEntityProvPort(provPortName), dataShare.getEntityReqPort(consPortName))){

				//To Do Exec API Call
				//commExecID = "/api/communication/" + communicationName;//POST using parameters communicationName, provCompID, consCompID, port
				commExecID = execInterfacer.createCommunication(Integer.parseInt(execInterfacer.trimResponseID(provPortID)), Integer.parseInt(execInterfacer.trimResponseID(consPortID)));
				
				LOGGER.log(Level.INFO, "Created Communication : ID " + commExecID);
				
				if(commExecID != null && !commExecID.equals("") && dataShare.setCommID(communicationName, execInterfacer.trimResponseID(commExecID)))
					LOGGER.log(Level.INFO, "Stored newly created Communication Type Instance : ID " + commExecID);
				else
					LOGGER.log(Level.WARNING, "Could not store newly created Communication Type Instance : ID " + commExecID);
			
			}else if(containsOrphan && provPortID != null && dataShare.addOrphanComm(communicationName, dataShare.getEntityProvPort(provPortName))){

				//To Do Exec API Call
				//commExecID = "/api/communication/" + communicationName;//POST using parameters communicationName, provCompID, consCompID, port
				//commExecID = execInterfacer.createCommunication(Integer.parseInt(execInterfacer.trimResponseID(provPortID)), Integer.parseInt(execInterfacer.trimResponseID(consPortID)));
				
				//LOGGER.log(Level.INFO, "Created Communication : ID " + commExecID);
				
				if(dataShare.setCommID(communicationName, "0"))
					LOGGER.log(Level.INFO, "Stored newly created Orphan Communication Type : " + communicationName);
				else
					LOGGER.log(Level.WARNING, "Could not stored newly created Orphan Communication Type : " + communicationName);
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
			
			
			this.communicationName = objParams.get("name").asString();
			LOGGER.log(Level.INFO, "Communication Type action thread : name " + communicationName);
			
			boolean status = true;
			
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
			
			//Required port params
			String consPort = objParams.get("consumerPort").toString();
			String consPortName = null;
			String consPortID = null;
			String isMandatory = objParams.get("isMandatory").toString();
			String requiredPortstartCmd = objParams.get("requiredPortstartCmd").toString();
			
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
				status = status && execInterfacer.updateProviderPort(Integer.parseInt(provPortID), provPortName, Integer.parseInt(provCompID), Integer.parseInt(provPort));
				dataShare.updateProvPort(provPortName, dataShare.getEntityLCAppComponent(provCompName), provPort);
			
			}else if(dataShare.addProvPort(provPortName, dataShare.getEntityLCAppComponent(provCompName), provPort)){
				provCompID = dataShare.getAppCompID(provCompName);
				//To Do Exec API Call
				//provPortID = "/api/communication/" + communicationName;//POST using parameters provPortName, provCompID, provPort
				provPortID = execInterfacer.createProviderPort(provPortName, Integer.parseInt(provCompID), Integer.parseInt(provPort));
				
				LOGGER.log(Level.INFO, "Created provider port : ID " + provPortID);
				
				if(provPortID != null && !provPortID.equals("") && dataShare.setProvPortID(provPortName, provPortID))
					LOGGER.log(Level.INFO, "Stored newly created provider port Instance : ID " + provPortID);
				else
					LOGGER.log(Level.WARNING, "Could not store newly created provider port Instance : ID " + provPortID);
			}
			
			if(dataShare.existsReqPort(consPortName)){
				
				consPortID = dataShare.getReqPortID(consPortName);
				status = status && execInterfacer.updateConsumerPort(Integer.parseInt(consPortID), consPortName, Integer.parseInt(consCompID), Integer.parseInt(consPort), isMandatory, requiredPortstartCmd);
				dataShare.updateReqPort(consPortName, dataShare.getEntityLCAppComponent(consCompName), consPort, isMandatory, requiredPortstartCmd);
				
			}else if(dataShare.addReqPort(consPortName, dataShare.getEntityLCAppComponent(consCompName), consPort, isMandatory, requiredPortstartCmd)){
				consCompID = dataShare.getAppCompID(consCompName);
				//To Do Exec API Call
				//provPortID = "/api/communication/" + communicationName;//POST using parameters consPortName, consCompID, consPort
				consPortID = execInterfacer.createConsumerPort(consPortName, Integer.parseInt(consCompID), Integer.parseInt(consPort), isMandatory, requiredPortstartCmd);
				
				LOGGER.log(Level.INFO, "Created required port : ID " + consPortID);
				
				if(consPortID != null && !consPortID.equals("") && dataShare.setReqPortID(consPortName, consPortID))
					LOGGER.log(Level.INFO, "Stored newly created required port Instance : ID " + consPortID);
				else
					LOGGER.log(Level.WARNING, "Could not store newly created required port Instance : ID " + consPortID);
			}
			
			
			if(provPortID != null && consPortID != null && dataShare.updateComm(communicationName, dataShare.getEntityProvPort(provPortName), dataShare.getEntityReqPort(consPortName))){
				commExecID = dataShare.getCommID(communicationName);
				//To Do Exec API Call
				//commExecID = "/api/communication/" + communicationName;//POST using parameters communicationName, provCompID, consCompID, port
				status = status && execInterfacer.updateCommunication(Integer.parseInt(commExecID), Integer.parseInt(execInterfacer.trimResponseID(provPortID)), Integer.parseInt(execInterfacer.trimResponseID(consPortID)));
				
				LOGGER.log(Level.INFO, "updated Communication : ID " + commExecID);
				
				if(commExecID != null && !commExecID.equals("") && dataShare.setCommID(communicationName, execInterfacer.trimResponseID(commExecID)))
					LOGGER.log(Level.INFO, "Updated Communication Type Instance : ID " + commExecID);
				else
					LOGGER.log(Level.WARNING, "Could not update Communication Type Instance : ID " + commExecID);
			}
			
		}else if(task.getTaskType()==TaskType.DELETE){
			
			
			this.communicationName = objParams.get("name").asString();
			LOGGER.log(Level.INFO, "Communication Type action (delete) thread : name " + communicationName);
			
			boolean status = true;
			
			boolean isOrphan = communicationName.toLowerCase().contains("orphan");
			System.out.println("Orphan Communication? " + isOrphan);
			
			String commExecID = null;
			
			//String provider = objParams.get("provider").asString();
			String provCompName = null;
			String provCompID = null;
			
			//String provPort = objParams.get("providerPort").toString();
			String provPortName = null;
			String provPortID = null;
			
			//String consumer = objParams.get("consumer").asString();
			String consCompName = null;
			String consCompID = null;
			
			//String consPort = objParams.get("consumerPort").toString();
			String consPortName = null;
			String consPortID = null;
			
			//LOGGER.log(Level.INFO, "provider port consumer port: " + provider + " " + provPort + " " + consumer + " " + consPort);
			
/*			Collection<Action> depActions = Coordinator.getDependentActions(this);
			LOGGER.log(Level.INFO, "--------------Breakpoint CommAct--- " + depActions.size());
			for(Object obj : depActions){
				System.out.println("-- " + obj.toString() + " ");
				if(obj.getClass()==InternalComponentAction.class && ((InternalComponentAction) obj).isProvidedComm(provider)){
					provCompName = ((InternalComponentAction) obj).getCompName();
				}else if(obj.getClass()==InternalComponentAction.class && ((InternalComponentAction) obj).isRequiredComm(consumer)){
					consCompName = ((InternalComponentAction) obj).getCompName();
				}
			}*/
			
			//LOGGER.log(Level.INFO, "providerComp consumerComp: " + provCompName + " " + consCompName);
			
			//provPortName = "prov-" + provCompName + "-" + provPort;
			if(isOrphan)
				provPortName = dataShare.getOrphanCommProvPortName(communicationName);
			else
				provPortName = dataShare.getCommProvPortName(communicationName);
			provPortID = dataShare.getProvPortID(provPortName);
			//provCompID = dataShare.getAppCompID(provCompName);
			
			if(!isOrphan){
				//consPortName = "cons-" + consCompName + "-" + consPort;
				consPortName = dataShare.getCommReqPortName(communicationName);
				consPortID = dataShare.getReqPortID(consPortName);
				//consCompID = dataShare.getAppCompID(consCompName);
				commExecID = dataShare.getCommID(communicationName);
				LOGGER.log(Level.INFO, "provPortName provPortID consPortName consPortID: " + provPortName + " " + provPortID + " " + consPortName + " " + consPortID);
			}else
				LOGGER.log(Level.INFO, "provPortName provPortID: " + provPortName + " " + provPortID);
			
			
			
			try {
				status = status && execInterfacer.deleteProviderPort(Integer.parseInt(execInterfacer.trimResponseID(provPortID)));
				status = status && dataShare.deleteProvPort(provPortName);
				if(!isOrphan){
					status = status && execInterfacer.deleteConsumerPort(Integer.parseInt(execInterfacer.trimResponseID(consPortID)));
					status = status && dataShare.deleteReqPort(consPortName);
					status = status && execInterfacer.deleteCommunication(Integer.parseInt(commExecID));
					//status = status && dataShare.deleteComm(communicationName);
				}
				status = status && dataShare.deleteComm(communicationName);
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
			
			if(status)
				LOGGER.log(Level.INFO, "Deleted Communication Type Instance : ID Name : " + commExecID + " " + communicationName);
			else
				LOGGER.log(Level.WARNING, "Could not completely delete Communication Type Instance : ID Name " + commExecID + communicationName);
		}
//		actionDone = true;
	}

	public ConfigurationTask getTask() {
		// TODO Auto-generated method stub
		return this.task;
	}
	
	public String getCommTypeName(){
		return this.communicationName;
	}
	
/*	public boolean getActionDone(){
		return this.actionDone;
	}*/
}
