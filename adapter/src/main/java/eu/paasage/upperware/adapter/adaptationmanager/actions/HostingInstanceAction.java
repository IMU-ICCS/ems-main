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

public class HostingInstanceAction implements Action {

	public HostingInstanceAction(DeploymentModel targetModel) {
		this.hostingInstName = (String) targetModel.eGet(targetModel.eClass()
				.getEStructuralFeature("name"));
	}
	
	public HostingInstanceAction(ConfigurationTask task, ExecInterfacer execInterfacer){
		this.task = task;
		this.objParams = task.getJsonModel();
		this.execInterfacer = execInterfacer;
	}

	private String hostingInstName;
	private static final Logger LOGGER = Logger
			.getLogger(HostingInstanceAction.class.getName());

	private JsonObject objParams;
	private ConfigurationTask task;
	private ExecInterfacer execInterfacer;

	public void execute(Map<String, Object> input, Map<String, Object> output) throws ActionError {
		LOGGER.log(Level.INFO, "Application creation action");
		ExecInterfacer execInterfacer = (ExecInterfacer) input
				.get("execInterfacer");
		String applicationId;
		try {
			applicationId = execInterfacer.createApplication(hostingInstName);
		} catch (Exception e) {
			throw new ActionError();
		}
		output.putAll(input);
		output.put("applicationID",applicationId);
	}

	public void run(){
		
		if(task.getTaskType()==TaskType.CREATE){
			
			this.hostingInstName = objParams.get("name").asString();
			LOGGER.log(Level.INFO, "Application Instance action thread : name " + hostingInstName);

		} else if(task.getTaskType()==TaskType.UPDATE){
			
		} else if(task.getTaskType()==TaskType.DELETE){
			
		}
	}

	public ConfigurationTask getTask() {
		// TODO Auto-generated method stub
		return this.task;
	}
}
