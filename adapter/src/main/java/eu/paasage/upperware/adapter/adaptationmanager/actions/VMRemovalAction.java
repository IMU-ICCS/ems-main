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

import eu.paasage.camel.deployment.VMInstance;
import eu.paasage.upperware.adapter.adaptationmanager.REST.ExecInterfacer;
import eu.paasage.upperware.adapter.adaptationmanager.core.Coordinator;
import eu.paasage.upperware.plangenerator.model.task.ConfigurationTask;

public class VMRemovalAction implements Action {

	private static final Logger LOGGER = Logger
			.getLogger(VMRemovalAction.class.getName());
	VMInstance vm;

	public VMRemovalAction(VMInstance vm) {
		this.vm = vm;
	}

	public void execute(Map<String, Object> input, Map<String, Object> output) throws ActionError {
		LOGGER.log(Level.INFO, "VM removal for " + vm.getName());
		ExecInterfacer execInterfacer = (ExecInterfacer) input
				.get("execInterfacer");
		String applicationId = (String) input.get("applicationID");
		try {
			execInterfacer.removeVM(vm.getName());
		} catch (Exception e) {
			throw new ActionError();
		}
		output.putAll(input);
		output.put(vm.getName(),applicationId);
	}
	
	public void run(){
		try{
			LOGGER.log(Level.INFO, "VM removal thread for " + vm.getName());
			
			System.out.print("***" + this.toString() + "*** Data/Objects available from its pendencies ");
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
