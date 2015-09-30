/*
 * Copyright (c) 2014-5 UK Science and Technology Facilities Council
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.plangenerator.model.task;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import eu.paasage.upperware.plangenerator.type.TaskType;

/**
 * VM instance task.
 * <p> 
 * @author  Shirley Crompton (shirley.crompton@stfc.ac.uk)
 * org      UK Science and Technology Facilities Council
 * project  PaaSage
 * @version $Name$ $Revision$ $Date$
 */
public class VMInstanceTask extends ConfigurationTask {

	/** Message logger */
	private static final Logger LOGGER = Logger.getLogger(VMInstanceTask.class);
	/**
	 * @param iname		the {@link eu.paasage.camel.deployment.VMInstance <em>VMInstance</em>} name
	 * @param theType  	the {@link TaskType <em>TaskType</em>}
	 */
	public VMInstanceTask(String iname, TaskType theType) {
		super(iname, theType);
		LOGGER.info("setting task name to VM instance name : "  + iname + " task type : " + theType.toString() + "...");		 
	
	}
}
