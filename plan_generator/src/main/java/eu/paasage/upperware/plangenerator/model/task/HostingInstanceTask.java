/*
 * Copyright (c) 2014-5 UK Science and Technology Facilities Council
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.plangenerator.model.task;

import org.apache.log4j.Logger;

import eu.paasage.upperware.plangenerator.type.TaskType;

/**
 * A task to configure a hosting instance between components in the cloud application.
 * <p> 
 * @author  Shirley Crompton (shirley.crompton@stfc.ac.uk)
 * org      UK Science and Technology Facilities Council
 * project  PaaSage
 * @version $Name$ $Revision$ $Date$
 */
public class HostingInstanceTask extends ConfigurationTask {

	/** Message logger */
	private static final Logger LOGGER = Logger.getLogger(HostingInstanceTask.class);
	/**
	/* Construct an instance
	 * <p>
	 * @param iname	the hosting instance name
	 * @param theType the {@link TaskType <em>TaskType</em>}
	 */
	public HostingInstanceTask(String iname, TaskType theType) {
		super(iname, theType);
		LOGGER.info("setting task name to hosting instance name : "  + iname + "...");		 
	
	}
}
