package eu.paasage.upperware.plangenerator.model.task;
import org.apache.log4j.Logger;

import eu.paasage.upperware.plangenerator.type.TaskType;

/*
 * Copyright (c) 2014-5 UK Science and Technology Facilities Council
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

/*
 * Application task for creating a cloud application.  
 * <p> 
 * @author  Shirley Crompton (shirley.crompton@stfc.ac.uk)
 * org      UK Science and Technology Facilities Council
 * project  PaaSage
 * @version $Name$ $Revision$ $Date$
 */
public class ApplicationInstanceTask extends ConfigurationTask {

	/** Message logger */
	private static final Logger LOGGER = Logger.getLogger(ApplicationInstanceTask.class.getName());
	/**
	 * Construct an instance
	 * <p>
	 * @param iname	the application instance name extracted from the target @{link eu.paasage.camel.CameltModel}
	 * @param theType the {@link TaskType <em>TaskType</em>}
	 */
	public ApplicationInstanceTask(String iname, TaskType theType) {
		super(iname, theType);
		LOGGER.info("setting task name to cloud application instance name : "  + iname + " task type to " + theType.toString() + "...");		 
	}
}
