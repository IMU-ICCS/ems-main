/*
 * Copyright (c) 2016 INRIA, INSA Rennes
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.adaptationmanager.actions;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.parser.ParseException;

import com.eclipsesource.json.JsonObject;

import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.upperware.adapter.adaptationmanager.REST.ExecInterfacer;
import eu.paasage.upperware.adapter.adaptationmanager.core.Coordinator;
import eu.paasage.upperware.adapter.adaptationmanager.mapping.CDOUpdater;
import eu.paasage.upperware.adapter.adaptationmanager.mapping.ExecwareInstance;
import eu.paasage.upperware.plangenerator.model.task.ConfigurationTask;
import eu.paasage.upperware.plangenerator.type.TaskType;

public class CDOUpdateAction implements Action {

	public CDOUpdateAction(CDOUpdater cdoUpdater, ExecInterfacer execInterfacer){
		this.cdoUpdater = cdoUpdater;
		LOGGER.log(Level.INFO, 	"CDOUpdateAction thread ");
		this.execInterfacer = execInterfacer;
	}

	private String appName;
	private static final Logger LOGGER = Logger
			.getLogger(CDOUpdateAction.class.getName());

	private CDOUpdater cdoUpdater;
	private ExecInterfacer execInterfacer;

	public void execute(Map<String, Object> input, Map<String, Object> output) throws ActionError {
		LOGGER.log(Level.INFO, "CDOUpdateAction execute block. Does Nothing");
	}

	public void run(){
		
		if(cdoUpdater == null || execInterfacer == null){
			LOGGER.log(Level.SEVERE, "Error! Something is wrong");
			return;
		}
		
		LinkedList<ExecwareInstance> instances = null;
		
		try {
			instances = execInterfacer.getInstancesForCDOUpdate();
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(instances == null || instances.size() == 0){
			LOGGER.log(Level.INFO, "CDOUpdateAction execute block. Nothing to update");
		}else if(cdoUpdater.updateDecision(instances, dataShare)){
			//cdoUpdater.updateCDO(instances);
			int index = cdoUpdater.updateFromMapping(instances, dataShare);
			if(index > -1)
				cdoUpdater.removeFromMapping(instances, dataShare);
		}		
		
		//cdoUpdater.copyDeploymentModelTest(instances);
		
	}

	public ConfigurationTask getTask() {
		// TODO Auto-generated method stub
		return null;//because it does not participate in deployment 
	}
}
