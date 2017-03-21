/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */



package eu.paasage.upperware.solvertodeployment.db.lib;

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
import eu.paasage.upperware.solvertodeployment.db.api.IDatabaseProxy;

public abstract class DatabaseProxy implements IDatabaseProxy {

	// UPPERWARE ELEMENTS -----------------------------------
	
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
	
	
	@Override
	public OperatingSystems getOperatingSystems() {
		// TODO Auto-generated method stub
		return this.operatingSystems;
	}

	@Override
	public Locations getLocations() {
		// TODO Auto-generated method stub
		return this.locations;
	}

	@Override
	public ProviderTypes getProviderTypes() {
		// TODO Auto-generated method stub
		return this.providerTypes;
	}

	@Override
	public FunctionTypes getFunctionTypes() {
		// TODO Auto-generated method stub
		return this.functionTypes;
	}

	@Override
	public void saveRelatedModels(ResourceSet resSet, File dir) {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadRelatedModels(ResourceSet resSet, File dir, PaasageConfiguration wrapper) {
		// TODO Auto-generated method stub

	}

	
	@Override
	public void saveModels(PaasageConfiguration pc, ResourceSet resSet) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean existPaaSageConfigurationModel(String paasageConfigurationId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PaasageConfiguration loadPaaSageConfigurationModel(String paasageConfigurationId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void closeSession() {
		// TODO Auto-generated method stub

	}

	@Override
	public void openSession() {
		// TODO Auto-generated method stub

	}

	@Override
	public ProviderModel loadPM(PaasageConfiguration pc, Provider provider) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CamelModel getCamelModel(String modelPath) {
		// TODO Auto-generated method stub
		return null;
	}

}
