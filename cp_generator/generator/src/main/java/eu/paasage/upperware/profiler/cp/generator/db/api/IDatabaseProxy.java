/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.profiler.cp.generator.db.api;

import java.io.File;
import java.util.Map;

import org.eclipse.emf.ecore.resource.ResourceSet;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.application.Provider;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.types.typesPaasage.FunctionTypes;
import eu.paasage.upperware.metamodel.types.typesPaasage.Locations;
import eu.paasage.upperware.metamodel.types.typesPaasage.OperatingSystems;
import eu.paasage.upperware.metamodel.types.typesPaasage.ProviderTypes;
import eu.paasage.upperware.profiler.cp.generator.model.lib.PaaSageConfigurationWrapper;
import fr.inria.paasage.saloon.camel.ProviderModelDecorator;
import fr.inria.paasage.saloon.camel.ontology.OntologyCamel;

/**
 * This interface defines the services offered by a database. 
 * @author danielromero
 *
 */
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
	 * Retrieves a map containing ProviderModelDecorator objects. Keys are provider's names.   
	 * @return Map with ProviderModelDecorator objects
	 */
	public Map<String, ProviderModelDecorator> getPMsMap(); 
	
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
	public void loadRelatedModels(ResourceSet resSet, File dir, PaaSageConfigurationWrapper wrapper); 
	
	/**
	 * Retrieves an existing directory containing configuration files for the database 
	 * @return An existing configuration directory
	 */
	public File getExistingConfigPath(); 
	
	/**
	 * Retrieves an existing directory containing models related to locations, function types, operating systems and provider types. 
	 * @return An existing model directory
	 */
	public File getExistingModelDirectory(); 
	
	/**
	 * Stores the cp and application models 
	 * @param pc The PaaSage Configuration Model
	 * @param cp The constraint problem model
	 * @param resSet The reource set to save models
	 */
	public void saveModels(PaasageConfiguration pc, ConstraintProblem cp, ResourceSet resSet); 
	
	/**
	 * Verifies if there is a Constraint Configuration model with the specified ID
	 * @param paasageConfigurationId The configuration id
	 * @return true if CP model exists; false if the CP does not exist
	 */
	public boolean existCPModel(String paasageConfigurationId); 
	
	/**
	 * Verifies if there is a PaaSage Configuration model with the specified ID
	 * @param paasageConfigurationId The configuration id
	 * @return true if Paasage configuration model exists; false if the configuration does not exist
	 */
	public boolean existPaaSageConfigurationModel(String paasageConfigurationId); 
	
	/**
	 * Loads the Constraint Problem model with the specified Id
	 * @param paasageConfigurationId The configuration id
	 * @return ConstraintProblem model 
	 */
	public ConstraintProblem loadCPModel(String paasageConfigurationId); 
	
	/**
	 * Loads the PaaSage Configuration model with the specified Id
	 * @param paasageConfigurationId The configuration id
	 * @return  PaaSage Configuration model 
	 */
	public PaasageConfiguration loadPaaSageConfigurationModel(String paasageConfigurationId); 
	
	/**
	 * Stores the provider model 
	 * @param fm The provider model to be stored 
	 * @param pc The configuration related to this provider model
	 * @param provider The provider related to the provider model
	 */
	public void savePM(ProviderModel fm, PaasageConfiguration pc, Provider provider); 
	
	/**
	 * Closes the database session
	 */
	public void closeSession(); 
	
	/**
	 * Returns the camel ontology 
	 * @return The camel ontology 
	 */
	public OntologyCamel getCamelOntology(); 
	
	public OntologyCamel getCamelOntologyCopy(); 
	
	/**
	 * Loads a procider model
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
	
	public ProviderModel loadPM(String appId, String providerId);  
	
	public ProviderModel loadPM(String appId, String providerId, String vmId);  

}
