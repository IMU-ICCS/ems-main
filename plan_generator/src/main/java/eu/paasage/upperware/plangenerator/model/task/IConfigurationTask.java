/*
 * Copyright (c) 2014-5 UK Science and Technology Facilities Council
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.plangenerator.model.task;

import java.util.Set;

import com.eclipsesource.json.JsonObject;

import eu.paasage.upperware.plangenerator.type.TaskType;
/*
 * Interface to a @{link ConfigurationTask} which is executed 
 * to update a running cloud deployment.
 * <p> 
 * @author Shirley Crompton
 * org     UK Science and Technology Facilities Council
 * project PaaSage
 */
public interface IConfigurationTask {

	/**
	 * Getter for input parameters
	 * <p>
	 * @return a {@link java.util.Map} of the input parameters
	 * 
	 * 
	 * AdaptationManager will deal with extracting the inputs
	 
	Map<String, Object> getInputs();
	*/

	/**
	 * Getter for the other tasks in the configuration plan that 
     * this task directly depends on.
	 * <p>
	 * @return a @{link java.util.Set} of tasks.
	 */
	Set<ConfigurationTask> getDependencies();
	
	/**
	 * Getter for the name of the configuration task
	 * <p>
	 * @return name 	The name of this configuration task
	 */
	String getName();
	
	/**
	 * Getter for the task type
	 * <p>
	 * @return {@link eu.paasage.upperware.plangenerator.type.TaskType <em>TaskType</em>} enum object
	 */
	TaskType getTaskType();
	
	/**
	 * Validate if this task directly depends on other.
	 * <p>
	 * @return  true if there are dependencies, else false.
	 */
	boolean hasDependencies();

	/**
	 * Getter for the task properties stored in a {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>}
	 * <p>
	 * @return 	a {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} of the task properties
	 */
	JsonObject getJsonModel();
	/**
	 * Setter for the task properties which are stored as a {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>}
	 * <p> 
	 * @param jsonModel	the source {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} 
	 */
	void setJsonModel(JsonObject jsonModel);
	
}