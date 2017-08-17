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

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import eu.passage.upperware.commons.model.tools.ModelTool;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import eu.paasage.camel.type.TypePackage;
import eu.paasage.upperware.metamodel.application.ApplicationComponent;
import eu.paasage.upperware.metamodel.application.ApplicationFactory;
import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.application.Provider;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.types.TypesPackage;
import eu.paasage.upperware.metamodel.types.typesPaasage.FunctionTypes;
import eu.paasage.upperware.metamodel.types.typesPaasage.Locations;
import eu.paasage.upperware.metamodel.types.typesPaasage.OperatingSystems;
import eu.paasage.upperware.metamodel.types.typesPaasage.ProviderTypes;
import eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage;
import eu.paasage.upperware.profiler.cp.generator.db.api.IDatabaseProxy;
import eu.paasage.upperware.profiler.cp.generator.db.lib.CDODatabaseProxy;
import fr.inria.paasage.saloon.camel.mapping.MappingPackage;
import fr.inria.paasage.saloon.camel.ontology.OntologyPackage;

/**
 * This class is a wrapper for a paasage configuration providing auxiliary methods
 * @author danielromero
 *
 */
public class PaaSageConfigurationWrapper 
{
	
	/*
	 * ATTRIBUTES
	 */
	/*
	 * The paasage configuration
	 */
	protected PaasageConfiguration paasageConfiguration;
	
	/*
	 * The operating systems related to the configuration
	 */
	protected OperatingSystems operatingSystems; 
	
	/*
	 * The location related to the configuration
	 */
	protected Locations locations; 
	
	/*
	 * The provider types related to the configuration
	 */
	protected ProviderTypes providerTypes; 
	
	/*
	 * The function types related to the configuration
	 */
	protected FunctionTypes functionTypes; 
	
	
	protected List<ApplicationComponent> componentsWithoutVM; 
	
	
	protected boolean hasUserSolution; 
	
	protected boolean validUserSolution; 
	
	protected boolean hasCorrectHostingRelationships;  
	


	/*
	 * The logger
	 */
	protected Logger logger= GenerationOrchestrator.getLogger(); 
	
	public PaaSageConfigurationWrapper(PaasageConfiguration pc)//, File modelsDir, ResourceSet resSet)
	{
		paasageConfiguration= pc; 
		
		hasUserSolution= false; 
		
		validUserSolution= false; 
		
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
	 * Main method for testing purposes 
	 * @param params Empty array
	 */
	public static void main(String[] params)
	{
		
		ApplicationPackage.eINSTANCE.eClass(); 
		
		TypesPaasagePackage.eINSTANCE.eClass(); 
		
		TypesPackage.eINSTANCE.eClass(); 
		
		CpPackage.eINSTANCE.eClass(); 
		
		OntologyPackage.eINSTANCE.eClass();

		TypePackage.eINSTANCE.eClass();
		MappingPackage.eINSTANCE.eClass();
		
		
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
	    Map<String, Object> m = reg.getExtensionToFactoryMap();
	    m.put("*", new XMIResourceFactoryImpl());
	    
	    
		
		IDatabaseProxy proxy= CDODatabaseProxy.getInstance(); 
		
		ResourceSet resSet= new ResourceSetImpl(); 
		
		File temp= new File("./temp/"); 
		

		
		try {
			File tempAbs= new File(temp.getCanonicalPath()); 
			
			proxy.saveRelatedModels(resSet, tempAbs);
			
			PaasageConfiguration pc= ApplicationFactory.eINSTANCE.createPaasageConfiguration(); 
			
			PaaSageConfigurationWrapper configuration= new PaaSageConfigurationWrapper(pc);//, tempAbs, resSet); 
			
			proxy.loadRelatedModels(resSet, tempAbs, configuration);
			
			Provider prov= ApplicationFactory.eINSTANCE.createProvider(); 
			
			prov.setLocation(configuration.getLocations().getLocations().get(0));
			
			prov.setType(configuration.getProviderTypes().getTypes().get(0));
			
			pc.getProviders().add(prov); 
			
			
			File testFile= new File(tempAbs, "configurationTest.xmi"); 
						
			Resource res= resSet.createResource(URI.createFileURI(testFile.getCanonicalPath())); 
			
			res.getContents().add(pc); 
			
			ModelTool.saveModel(res);
			
		} catch (IOException e) {

			e.printStackTrace();
		}
		

		
		
		
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

	public boolean hasUserSolution() {
		return hasUserSolution;
	}

	public void setHasUserSolution(boolean hasUserSolution) {
		this.hasUserSolution = hasUserSolution;
	}

	public boolean isValidUserSolution() {
		return validUserSolution;
	}

	public void setValidUserSolution(boolean validUserSolution) {
		this.validUserSolution = validUserSolution;
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

	public void setComponentsWithoutVM(
			List<ApplicationComponent> componentsWithoutVM) {
		this.componentsWithoutVM = componentsWithoutVM;
	}
	
	
	
	
}
