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

import eu.paasage.upperware.metamodel.application.ApplicationComponent;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.types.typesPaasage.FunctionTypes;
import eu.paasage.upperware.metamodel.types.typesPaasage.Locations;
import eu.paasage.upperware.metamodel.types.typesPaasage.OperatingSystems;
import eu.paasage.upperware.metamodel.types.typesPaasage.ProviderTypes;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * This class is a wrapper for a paasage configuration providing auxiliary methods
 * @author danielromero
 *
 */
@Slf4j
public class PaaSageConfigurationWrapper {

	/*
	 * The paasage configuration
	 */
	protected PaasageConfiguration paasageConfiguration;
	
	/*
	 * The operating systems related to the configuration
	 */
	//TODO - to delete
	protected OperatingSystems operatingSystems; 
	
	/*
	 * The location related to the configuration
	 */
	//TODO - to delete
	protected Locations locations; 
	
	/*
	 * The provider types related to the configuration
	 */
	//TODO - to delete
	protected ProviderTypes providerTypes; 
	
	/*
	 * The function types related to the configuration
	 */
	//TODO - to delete
	protected FunctionTypes functionTypes;
	
	protected List<ApplicationComponent> componentsWithoutVM; 

	protected boolean hasCorrectHostingRelationships;


	public PaaSageConfigurationWrapper(PaasageConfiguration pc) {
		paasageConfiguration= pc;
		hasCorrectHostingRelationships= true;
	}
	
	/**
	 * Sets operatingSystems
	 * @param operatingSystems The operating systems
	 */
	public void setOperatingSystems(OperatingSystems operatingSystems) {
		this.operatingSystems = operatingSystems;
	}

	/**
	 * Sets locations
	 * @param locations The locations 
	 */
	public void setLocations(Locations locations) {
		this.locations = locations;
	}

	/**
	 * Sets providerTypes
	 * @param providerTypes The provider types
	 */
	public void setProviderTypes(ProviderTypes providerTypes) {
		this.providerTypes = providerTypes;
	}

	/**
	 * Sets functionTypes
	 * @param functionTypes The function types
	 */
	public void setFunctionTypes(FunctionTypes functionTypes) {
		this.functionTypes = functionTypes;
	}

	/**
	 * 
	 * @return paasageConfiguration
	 */
	public PaasageConfiguration getPaasageConfiguration() {
		return paasageConfiguration;
	}

	/**
	 * 
	 * @return operatingSystems
	 */
	public OperatingSystems getOperatingSystems() {
		return operatingSystems;
	}

	/**
	 * 
	 * @return locations
	 */
	public Locations getLocations() {
		return locations;
	}

	/**
	 * 
	 * @return providerTypes
	 */
	public ProviderTypes getProviderTypes() {
		return providerTypes;
	}

	/**
	 * 
	 * @return functionTypes
	 */
	public FunctionTypes getFunctionTypes() {
		return functionTypes;
	}

	public boolean isHasCorrectHostingRelationships() {
		return hasCorrectHostingRelationships;
	}

	public void setHasCorrectHostingRelationships(
			boolean hasCorrectHostingRelationships) {
		this.hasCorrectHostingRelationships = hasCorrectHostingRelationships;
	}

	public List<ApplicationComponent> getComponentsWithoutVM() {
		return componentsWithoutVM;
	}

	public void setComponentsWithoutVM(List<ApplicationComponent> componentsWithoutVM) {
		this.componentsWithoutVM = componentsWithoutVM;
	}

}
