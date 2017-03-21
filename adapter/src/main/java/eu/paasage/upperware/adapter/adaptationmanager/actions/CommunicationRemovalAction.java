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

import eu.paasage.camel.deployment.CommunicationInstance;
import eu.paasage.upperware.adapter.adaptationmanager.REST.ExecInterfacer;
import eu.paasage.upperware.adapter.adaptationmanager.core.Coordinator;
import eu.paasage.upperware.adapter.adaptationmanager.mapping.CamelExecwareMapping;
import eu.paasage.upperware.plangenerator.model.task.ConfigurationTask;

public class CommunicationRemovalAction implements Action {

	private static final Logger LOGGER = Logger
			.getLogger(CommunicationRemovalAction.class.getName());
	CommunicationInstance cmm;

	public CommunicationRemovalAction(CommunicationInstance cmm) {
		this.cmm = cmm;
	}
	
	public String toString(){
		String s = super.toString();
		return s.substring(s.lastIndexOf('.') + 1);
	}

	public void execute(Map<String, Object> input, Map<String, Object> output) throws ActionError {
		LOGGER.log(Level.INFO, "Communication removal for " + cmm.getName());
		ExecInterfacer execInterfacer = (ExecInterfacer) input
				.get("execInterfacer");
		try {
			execInterfacer.removeCommunication(cmm.getName());
		} catch (Exception e) {
			throw new ActionError();
		}
		output.putAll(input);
		output.put(cmm.getName(),"Removed");
	}
	
	static CamelExecwareMapping funct(){
		synchronized (dataShare) {
			dataShare.testFunct();
			return dataShare;
		}
	}
	
	public void run(){
		try{
			LOGGER.log(Level.INFO, "Communication removal thread for " + cmm.getName());
			
			System.out.print("***" + this.toString() + "*** Data/Objects available from its dependencies ");
			Collection<Object> depActions = Coordinator.getNeighbourDependencies(this);
			String depParam = "";
			for(Object obj : depActions){
				System.out.print("-- " + obj.toString());
				depParam += obj.toString();
			}
			System.out.print("\n");
			dataShare.testFunct();
			funct();
			ExecInterfacer execInterfacer = (ExecInterfacer) Coordinator.getHandle("execInterfacer");
			execInterfacer.removeCommunication(depParam);
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
