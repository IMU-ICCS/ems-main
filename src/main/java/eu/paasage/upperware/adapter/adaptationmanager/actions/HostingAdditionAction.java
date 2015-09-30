/*
 * Copyright (c) 2014 INRIA, INSA Rennes
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.adaptationmanager.actions;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import eu.paasage.camel.deployment.HostingInstance;
import eu.paasage.upperware.adapter.adaptationmanager.REST.ExecInterfacer;
import eu.paasage.upperware.adapter.adaptationmanager.core.Coordinator;
import eu.paasage.upperware.plangenerator.model.task.ConfigurationTask;

public class HostingAdditionAction implements Action {

	private static final Logger LOGGER = Logger
			.getLogger(HostingAdditionAction.class.getName());
	HostingInstance hosting;

	public HostingAdditionAction(HostingInstance hosting) {
		this.hosting = hosting;
	}

	public String toString(){
		String s = super.toString();
		return s.substring(s.lastIndexOf('.') + 1);
	}
	
	public void execute(Map<String, Object> input, Map<String, Object> output) throws ActionError {
		LOGGER.log(Level.INFO, "Hosting addition for " + hosting.getName());
		ExecInterfacer execInterfacer = (ExecInterfacer) input
				.get("execInterfacer");
		String hostingId=null;
		try {
			hostingId = execInterfacer.addHosting(hosting.getName());
		} catch (Exception e) {
			throw new ActionError();
		}
		output.putAll(input);
		output.put(hosting.getName(), hostingId);
	}
	
	public void run(){
		try{
			LOGGER.log(Level.INFO, "Hosting addition thread for " + hosting.getName());
		/*	ExecInterfacer execInterfacer = (ExecInterfacer) Coordinator.getObject("execInterfacer");
			String hostingId = execInterfacer.addHosting(hosting.getName());
			Coordinator.putObject(hosting.getName(), hosting);
			Coordinator.printObjects();*/
			
			System.out.print("***" + this.toString() + "*** Data/Objects available from its dependencies ");
			Collection<Object> depActions = Coordinator.getNeighbourDependencies(this);
			for(Object obj : depActions)
				System.out.print("-- " + obj.toString());
			System.out.print("\n");
			
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
