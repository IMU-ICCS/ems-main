/*
 * Copyright (c) 2014 INRIA, INSA Rennes
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

import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.upperware.adapter.adaptationmanager.REST.ExecInterfacer;
import eu.paasage.upperware.adapter.adaptationmanager.core.Coordinator;
import eu.paasage.upperware.plangenerator.model.task.ConfigurationTask;

public class ApplicationCreationAction implements Action {

	public ApplicationCreationAction(DeploymentModel targetModel) {
		this.appName = (String) targetModel.eGet(targetModel.eClass()
				.getEStructuralFeature("name"));
	}

	private String appName;
	private static final Logger LOGGER = Logger
			.getLogger(ApplicationCreationAction.class.getName());

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
		try{
			LOGGER.log(Level.INFO, "Application creation action thread");
		/*	ExecInterfacer execInterfacer = (ExecInterfacer) Coordinator.getObject("execInterfacer");
			String applicationId = execInterfacer.createApplication(appName);
			Coordinator.putObject(appName, applicationId);
			Coordinator.printObjects();*/
			
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
		}
	}

	public ConfigurationTask getTask() {
		// TODO Auto-generated method stub
		return null;
	}
}
