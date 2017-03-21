/*
 * Copyright (c) 2014 UK Science and Technology Facilities Council
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.plangenerator.model.task;

import java.util.HashSet;
import java.util.Set;

import com.eclipsesource.json.JsonObject;

import eu.paasage.upperware.plangenerator.type.TaskType;

/*
 * A task for updating a running cloud application.
 * <p> 
 * @author Shirley Crompton (shirley.crompton@stfc.ac.uk)
 * org     UK Science and Technology Facilities Council
 * project PaaSage
 */
public abstract class ConfigurationTask implements IConfigurationTask {

	/** Unique task name **/
	protected String name;
	/** Task type **/
	protected TaskType taskType;	
	/** JSON representation of properties relevant to setting up the Adapter Action object */
	protected JsonObject jsonModel;
		
	/**
	 * A list of dependent {@link ConfigurationTask} objects within the
	 * configuration plan.  During reconfiguration execution, these tasks must 
	 * be executed before this one.
	 */
	protected Set<ConfigurationTask> dependencies;
	/**
	 * Constructor
	 * <p>
	 * @param iname {@link #name} of this configuration task
	 * @param theType {@link TaskType <em>TaskType</em>}
	 */
	public ConfigurationTask(String iname, TaskType theType){
		this.name = iname;
		this.taskType = theType;
		this.dependencies = new HashSet<ConfigurationTask>();
	}

	/**
	 * Getter for the unique task name
	 * <p> 
	 * @return name 	{@link #name} of this configuration task
	 */
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see eu.paasage.upperware.plangenerator.model.task.IConfigurationTask#getInputs()
	 
	@Override
	public Map<String, Object> getInputs() {
		return inputs;
	}*/

	/* (non-Javadoc)
	 * @see eu.paasage.upperware.plangenerator.model.task.IConfigurationTask#getDependencies()
	 */
	@Override
	public Set<ConfigurationTask> getDependencies() {
		return dependencies;
	}
	
	/* (non-Javadoc)
	 * @see eu.paasage.upperware.plangenerator.model.task.IConfigurationTask#hasDependencies()
	 */
	@Override
	public boolean hasDependencies(){
		return (!dependencies.isEmpty());
	}
	
	/*
	 * (non-Javadoc)
	 * @see eu.paasage.upperware.plangenerator.model.task.IConfigurationTask#getProperties()
	 */
	@Override
	public JsonObject getJsonModel(){
		return jsonModel;
	}
	
	@Override
	public void setJsonModel(JsonObject jsonModel){
		this.jsonModel = jsonModel;
	}
	
	/*
	 * (non-Javadoc)
	 * @see eu.paasage.upperware.plangenerator.model.task.IConfigurationTask#getType()
	 */
	@Override
	public TaskType getTaskType() {
		return taskType;
	}

}
