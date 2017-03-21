/*
 * Copyright (c) 2014 INRIA, INSA Rennes
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.adaptationmanager.actions;

import java.util.Map;

import eu.paasage.upperware.adapter.adaptationmanager.mapping.CamelExecwareMapping;
import eu.paasage.upperware.plangenerator.model.task.ConfigurationTask;

public interface Action extends Runnable{

	public void execute(Map<String, Object> input, Map<String, Object> output) throws ActionError;

	//Implement this to pass on the data to other dependent actions
	//public Map<String, Object> passdata();
	
	//ConfigurationTask task = null;
	
	ConfigurationTask getTask();
	
	static CamelExecwareMapping dataShare = new CamelExecwareMapping();
}
