/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */



package eu.paasage.upperware.solvertodeployment.db.api;

import java.io.File;

import org.eclipse.emf.ecore.resource.ResourceSet;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.application.Provider;
import eu.paasage.upperware.metamodel.types.typesPaasage.FunctionTypes;
import eu.paasage.upperware.metamodel.types.typesPaasage.Locations;
import eu.paasage.upperware.metamodel.types.typesPaasage.OperatingSystems;
import eu.paasage.upperware.metamodel.types.typesPaasage.ProviderTypes;



public interface IDatabaseProxy 
{
	
	/**
	 * Retrieves an OperatingSystems object that contains a list of available operating systems in the database
	 * @return List of operating systems
	 */
	public OperatingSystems getOperatingSystems(); 
	
	/**
	 * Retrieves a Locations object that contains a list of available locations in the database
	 * @return List of locations
	 */
	public Locations getLocations(); 

	
	/**
	 * Retrieves a ProviderTypes object that contains a list of available provider types in the database
	 * @return List of provider types
	 */
	public ProviderTypes getProviderTypes(); 
	
	/**
	 * Retrieves a FunctionTypes object that contains a list of available function types in the database
	 * @return List of function types
	 */
	public FunctionTypes getFunctionTypes(); 
	
	/**
	 * Stores the related models (i.e., operating systems, locations, function types and provider types) of a configuration
	 * @param resSet The resource set used to save models
	 * @param dir The directory for saving the models
	 */
	public void saveRelatedModels(ResourceSet resSet, File dir); 
	
	
	/**
	 * Loads the related models (i.e., operating systems, locations, function types and provider types) of a configuration
	 * @param resSet The resource set to load the models
	 * @param dir The directory containing the models
	 * @param wrapper The wrapper of the configuration
	 */
	public void loadRelatedModels(ResourceSet resSet, File dir, PaasageConfiguration wrapper); 
	
	
	public void saveModels(PaasageConfiguration pc, ResourceSet resSet); 
	
		
	/**
	 * Verifies if there is a PaaSage Configuration model with the specified ID
	 * @param paasageConfigurationId The configuration id
	 * @return true if Paasage configuration model exists; false if the configuration does not exist
	 */
	public boolean existPaaSageConfigurationModel(String paasageConfigurationId); 
	
	
	/**
	 * Loads the PaaSage Configuration model with the specified Id
	 * @param paasageConfigurationId The configuration id
	 * @return  PaaSage Configuration model 
	 */
	public PaasageConfiguration loadPaaSageConfigurationModel(String paasageConfigurationId); 
	
	
	
	/**
	 * Closes the database session
	 */
	public void closeSession(); 
	
	/**
	 * Opens the database session
	 */
	public void openSession(); 
	
		
	/**
	 * Loads a provider model
	 * @param pc The paasage configuration related to the provider model
	 * @param provider The provider related to the provider model
	 * @return The provider model
	 */
	public ProviderModel loadPM(PaasageConfiguration pc, Provider provider);

	/**
	 * Retrieves the camel model with the specified path
	 * @param modelPath The model path
	 * @return The camel model
	 */
	public CamelModel getCamelModel(String modelPath); 

}