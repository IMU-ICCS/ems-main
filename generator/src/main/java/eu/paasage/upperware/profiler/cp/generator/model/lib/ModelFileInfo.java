/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.profiler.cp.generator.model.lib;

/**
 * This class is used for defined the relationship between a model and its type
 * @author danielromero
 *
 */
public class ModelFileInfo 
{
	
	/*
	 * ATTRIBUTES
	 */
	/*
	 * File containing the model
	 */
	private String modelPath; 
	
	/*
	 * Model type 
	 */
	private String modelTypeId;
		
	
	/*
	 * CONSTRUCTOR
	 */
	/**
	 * 
	 * @param modelPath The file containing the model
	 * @param modelTypeId The model type
	 */
	public ModelFileInfo(String modelPath, String modelTypeId) 
	{
		this.modelPath = modelPath;
		this.modelTypeId = modelTypeId;
	}
	

	/*
	 * METHODS
	 */
	/**
	 * 
	 * @return modelPath
	 */
	public String getModelPath() {
		return modelPath;
	}

	/**
	 * 
	 * @return modelTypeId
	 */
	public String getModelTypeId() {
		return modelTypeId;
	} 
	
	
	

}
