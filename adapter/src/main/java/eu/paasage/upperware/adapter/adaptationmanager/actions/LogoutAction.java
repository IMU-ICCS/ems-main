/*
 * Copyright (c) 2014 INRIA, INSA Rennes
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.adaptationmanager.actions;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import eu.paasage.upperware.adapter.adaptationmanager.REST.ExecInterfacer;
import eu.paasage.upperware.plangenerator.model.task.ConfigurationTask;


public class LogoutAction implements Action {

	private static final Logger LOGGER = Logger.getLogger(LogoutAction.class
			.getName());

	public LogoutAction() {
	}

	public void execute(Map<String, Object> input, Map<String, Object> output) throws ActionError {
		LOGGER.log(Level.INFO, "Logout action");
		ExecInterfacer execInterfacer = (ExecInterfacer) input
				.get("execInterfacer");
		try {
			execInterfacer.logout();
		} catch (Exception e) {
			throw new ActionError();
		}
		output.putAll(input);
	}
	
	public void run(){
		try{
			LOGGER.log(Level.INFO, "Logout action thread");
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
