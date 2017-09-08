/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.profiler.cp.generator.db.lib;

import java.io.File;

import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;

import eu.paasage.upperware.metamodel.types.typesPaasage.FunctionTypes;
import eu.paasage.upperware.metamodel.types.typesPaasage.Locations;
import eu.paasage.upperware.metamodel.types.typesPaasage.OperatingSystems;
import eu.paasage.upperware.metamodel.types.typesPaasage.ProviderTypes;
import eu.paasage.upperware.profiler.cp.generator.db.api.IDatabaseProxy;
import eu.paasage.upperware.profiler.cp.generator.model.lib.GenerationOrchestrator;
import eu.paasage.upperware.profiler.cp.generator.model.tools.Constants;

/**
 * Abstract class with default implementation for proxies
 * @author danielromero
 *
 */
@Slf4j
public abstract class DatabaseProxy implements IDatabaseProxy 
{
	
	/*
	 * ATTRIBUTES 
	 */


	/*
	 * List of Operating systems
	 */
	protected OperatingSystems operatingSystems; 
	
	/*
	 * List of locations
	 */
	protected Locations locations; 
	
	/*
	 * List of provider types
	 */
	protected ProviderTypes providerTypes; 
	
	/*
	 * List of function types
	 */
	protected FunctionTypes functionTypes; 
	
	
	/*
	 * METHODS 
	 */
	
	/**
	 * Retrieves an OperatingSystems object that contains a list of available operating systems in the database
	 * @return List of operating systems
	 */
	public OperatingSystems getOperatingSystems() 
	{
		
		return operatingSystems;
	}

	/**
	 * Retrieves a Locations object that contains a list of available locations in the database
	 * @return List of locations
	 */
	public Locations getLocations() {

		return locations;
	}


	/**
	 * Retrieves a ProviderTypes object that contains a list of available provider types in the database
	 * @return List of provider types
	 */
	public ProviderTypes getProviderTypes() 
	{
		return providerTypes;
	}

	/**
	 * Retrieves a FunctionTypes object that contains a list of available function types in the database
	 * @return List of function types
	 */
	public FunctionTypes getFunctionTypes() 
	{
		
		return functionTypes;
	}

	
	/**
	 * Retrieves an existing directory containing models related to locations, function types, operating systems and provider types. 
	 * @return An existing model directory
	 */
	public File getExistingModelDirectory()
	{
		String path= Constants.MODELS_DEFAULT_PATH+"cp";
		
		File file= new File(path); 
		
		
		if(!file.isDirectory())
		{
			path= Constants.WAR_MODEL_PATH+"cp"; 
			
			file= new File(path); 
			
			if(file.isDirectory())
				file= new File(getClass().getClassLoader().getResource(path).getPath()); 
			else
				file= null; 
		}
		
		return file; 
	}
	
	/**
	 * Retrieves an existing directory containing models related to locations, function types, operating systems and provider types. 
	 * @param type The type of the model
	 * @return An existing model directory
	 */
	public File getExistingModelDirectory(String type)
	{
		String path= Constants.MODELS_DEFAULT_PATH+type;
		
		File file= new File(path); 
		
		
		if(!file.isDirectory())
		{
			path= Constants.WAR_MODEL_PATH+type; 
			
			file= new File(path); 
			
			if(file.isDirectory())
				file= new File(getClass().getClassLoader().getResource(path).getPath()); 
			else
				file= null; 
		}
		
		return file; 
	}
}
