/*
 * Copyright (c) 2014 UK Science and Technology Facilities Council
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.plangenerator.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.upperware.plangenerator.model.task.ConfigurationTask;

/**
 * Configuration plan. 
 * <p>
 * @author Shirley Crompton
 * org     UK Science and Technology Facilities Council
 */
public class Plan {	
	
	/** message logger */
	private static final Logger LOG = Logger.getLogger(Plan.class.getName());
	/** A list of configuration tasks contained in the plan */
	private List<ConfigurationTask> tasks = new ArrayList<ConfigurationTask>();
	/** Name of the cloud application associated with the {@link Plan} **/
	private String appName;
	/** The target deployment model */
	//private DeploymentModel target;
	
	/**
	 * Construct an instance
	 * <p>
	 * @param name	name of the cloud application
	 */
	public Plan(String name){
		this.appName = name;
	}
	
	/**
	 * Construct an instance with the target {@link eu.paasage.camel.deployment.DeploymentModel}
	 * @param target
	
	public Plan(DeploymentModel target){
		this.target = target;
	} */
	
	/**
	 * Not sure if we need this constructor
	 * construct an instance
	 * <p>
	 * @param actions  A {@link java.util.List} of {@link ConfigurationTask} objects
	
	public Plan(ArrayList<ConfigurationTask> tasks) {
		this.tasks = tasks;
	} */
	
	
	/**
	 * Getter for the {@link java.util.List} of {@link ConfigurationTask} objects
	 * @return	{@link java.util.List} of {@link ConfigurationTask} objects
	 */
	public List<ConfigurationTask> getTasks() {
		return tasks;
	}
	/**
	 * Test if this configuration plan contain tasks.
	 * <p>
	 * @return	true if there are tasks, else false
	 */
	public boolean hasTasks(){
		return (!this.tasks.isEmpty());
	}
	/**
	 * Getter for the cloud application name
	 * @return  the application name
	 */
	public String getAppName() {
		return appName;
	}
	/**
	 * Setter for the cloud application name 
	 * @param appName	the application name
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * @return the target {@link eu.paasage.camel.deployment.DeploymentModel <em>DeploymentModel</em>}
	
	public DeploymentModel getTarget() {
		return target;
	} */

	/**
	 * @param target the target {@link eu.paasage.camel.deployment.DeploymentModel <em>DeploymentModel</em>} to set
	 
	public void setTarget(DeploymentModel target) {
		this.target = target;
	}*/
}
