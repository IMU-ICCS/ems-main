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
			LOGGER.log(Level.INFO, "VM Type action thread : name " + this.vmName);
			
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
			LOGGER.log(Level.INFO, "--------------Breakpoint VMAction--- " + depOnActions.size());
			
			for(Object obj : depOnActions){
				System.out.println("-- " + obj.toString() + " ");
				if(obj.getClass()==VMInstanceAction.class){
					((VMInstanceAction) obj).run();
					LOGGER.log(Level.INFO, "Forced " + ((VMInstanceAction) obj).getVMInstName() + " to run from " + this.getVMName());
				}
			}

		} else if(task.getTaskType()==TaskType.UPDATE){
			
		} else if(task.getTaskType()==TaskType.DELETE){
			
		}
		
	}

	public ConfigurationTask getTask() {
		// TODO Auto-generated method stub
		return this.task;
	}
	
	public String getVMName(){
		return vmName;
	}
}
