/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.profiler.generator.db;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.CamelPackage;
import eu.paasage.camel.deployment.DeploymentPackage;
import eu.paasage.camel.organisation.OrganisationPackage;
import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.camel.provider.ProviderPackage;
import eu.paasage.camel.type.TypePackage;
import eu.paasage.upperware.cp.cloner.CPCloner;
import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.types.TypesPackage;
import eu.paasage.upperware.metamodel.types.typesPaasage.*;
import eu.paasage.upperware.profiler.cp.generator.model.tools.Constants;
import eu.paasage.upperware.profiler.cp.generator.model.tools.PaasageModelTool;
import fr.inria.paasage.saloon.camel.mapping.MappingPackage;
import fr.inria.paasage.saloon.camel.ontology.OntologyPackage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.ecore.EObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

import static eu.passage.upperware.commons.MelodicConstants.*;

/**
 * Database proxy for CDO
 */
@Slf4j
@Service
public class CDODatabaseProxy implements IDatabaseProxy {

	private CPCloner cloner;
	private CDOClientExtended cdoClient;

	@Autowired
	public CDODatabaseProxy(CDOClientExtended cdoClient) {
		this.cdoClient = cdoClient;
		cloner = new CPCloner();

		registerPackages();
	}

	/**
	 * Registers the package of the used models in the database
	 */
	private void registerPackages() {
		cdoClient.registerPackage(ApplicationPackage.eINSTANCE);
		cdoClient.registerPackage(CpPackage.eINSTANCE);
		cdoClient.registerPackage(TypesPackage.eINSTANCE);
		cdoClient.registerPackage(TypesPaasagePackage.eINSTANCE);
		cdoClient.registerPackage(OntologyPackage.eINSTANCE);
		cdoClient.registerPackage(MappingPackage.eINSTANCE);
		cdoClient.registerPackage(TypePackage.eINSTANCE);
		cdoClient.registerPackage(CamelPackage.eINSTANCE);
		cdoClient.registerPackage(ProviderPackage.eINSTANCE);
		cdoClient.registerPackage(OrganisationPackage.eINSTANCE);
		cdoClient.registerPackage(DeploymentPackage.eINSTANCE);
	}

	public CamelModel getCamelModel(String modelPath) {
		List<EObject> camelModels = cloner.cloneModel(modelPath);
		return (CamelModel) getLastElement(camelModels);
	}

	private EObject getLastElement(List<EObject> collection){
		return CollectionUtils.isNotEmpty(collection) ? collection.get(collection.size()-1) : null;
	}

	public ProviderModel loadPM(String cloud) {
		//Try to load the Model from CDO if it exists
		if(cdoClient.existResource(FMS_APP_CDO_SERVER_PATH+cloud)) {
			List<EObject> cloned= cloner.cloneModel(FMS_APP_CDO_SERVER_PATH+cloud);
			CamelModel pm= (CamelModel) cloned.get(cloned.size()-1); //We pick the last added model. It is necessary as it is not possible to delete models from CDO.
			log.debug("CDODatabaseProxy- The PM "+cloud+" has been cloaned!");
			return pm.getProviderModels().get(0);
		}
		return null;
	}

	/**
	 * Loads a provider model
	 *
	 * @param appId      The paasage id
	 * @param providerId The provider id related to the provider model
	 * @return The provider model
	 */
	public ProviderModel loadPM(String appId, String providerId) {

		List<EObject> res = cloner.cloneModel(FMS_APP_CDO_SERVER_PATH + getFMResourceId(appId, providerId));
		CamelModel pm = (CamelModel)getLastElement(res);

		log.debug("CDODatabaseProxy- loadPM- PM " + pm.getProviderModels().get(0).getRootFeature().getName());

		return pm.getProviderModels().get(0);
	}

	/**
	 * Loads a provider model
	 *
	 * @param appId      The application id
	 * @param providerId The provider id related to the provider model
	 * @param vmId       The virtual machine identifier
	 * @return The provider model
	 */
	//TODO - is this used anywhere?
	public ProviderModel loadPM(String appId, String providerId, String vmId) {
		List<EObject> res = cloner.cloneModel(FMS_APP_CDO_SERVER_PATH + getFMResourceId(appId, providerId));

		//The Name of the camel model is the Id of the VM

		CamelModel pm = null;

		log.debug("CDODatabaseProxy- loadPM- VMId " + vmId);

		for (EObject r : res) {
			CamelModel provider = (CamelModel) r;

			log.debug("CDODatabaseProxy- loadPM- Camel Name " + provider.getName());

			if (vmId.contains(provider.getName())) {
				pm = provider;
				break;
			}
		}


		log.debug("CDODatabaseProxy- loadPM- PM " + pm);

		return pm.getProviderModels().get(0);
	}

	/**
	 * Load the function types from the CDO database
	 */
	public FunctionTypes loadFunctionTypes() {
		log.debug("Loading FunctionTypes");
		return loadFromCdo(FUNCTION_TYPES_ID);
	}

	/**
	 * Loads the operating systems from the CDO database
	 */
	public OperatingSystems loadOperatingSystems() {
		log.debug("Loading OperatingSystems");
		return loadFromCdo(OPERATING_SYSTEMS_ID);
	}

	/**
	 * Load locations from the CDO database
	 */
	public Locations loadLocations() {
		log.debug("Loading Locations");
		return loadFromCdo(LOCATIONS_ID);
	}

	/**
	 * Loads the provider types.
	 */
	public ProviderTypes loadProviderTypes() {
		log.debug("Loading ProviderTypes");
		return loadFromCdo(PROVIDER_TYPES_ID);
	}

	@SuppressWarnings("unchecked")
	private <T extends EObject> T loadFromCdo(String cdoName) {
		List<EObject> providerTypesList = getResourceWithID(cdoName);
		if (!CollectionUtils.isEmpty(providerTypesList)) {
			log.info("Getting resource for {} path from CDO", cdoName);
			return (T) getLastElement(providerTypesList);
		}
		log.warn("Empty resource for {} path.", cdoName);
		return null;
	}

	/**
	 * Retrieves the resource from the CDO database with the specified id
	 * @param id Id of the resource
	 * @return The list of EObject relagted to the resource.
	 */
	protected List<EObject> getResourceWithID(String id) {
		List<EObject> result= null;

		if(cdoClient.existResource(id)) {
			try{
//				result = cloner.cloneModel(id);
				result= cdoClient.getResourceContents(id);
				log.info("CDODatabaseProxy - getResourceWithID - The resource "+ id+" loaded");
			} catch(org.eclipse.emf.cdo.util.InvalidURIException ex) {
				log.info("CDODatabaseProxy - getResourceWithID - The resource "+ id+" does not exist");
			}
		}
		return result;
	}

	public void saveModels(PaasageConfiguration pc, ConstraintProblem cp) {
		String pcId= pc.getId();

		log.debug("CDODatabaseProxy - saveModels - Storing Models ");
		String cpPath = CDO_SERVER_PATH + pcId;
//
		//cdoClient.exportModel(pc, "/logs/cpGenerator_cpm_"+cpPath+".xmi");
//		cdoClient.exportModel(cp, "/logs/cpGenerator_cpm_"+cpPath+".xmi");

		cdoClient.storeModels(Arrays.asList(pc, cp), cpPath);
		log.debug("CDODatabaseProxy - saveModels - Models stored! ");

		File paasageConfigurationDir= PaasageModelTool.getGenerationDirForPaasageAppConfiguration(pcId);
		File paasageConfigModel= new File(paasageConfigurationDir, Constants.PAASAGE_CONFIGURATION_MODEL_FILE_NAME);
		File cpModel= new File(paasageConfigurationDir, Constants.CP_MODEL_FILE_NAME);

		List<EObject> content = cloner.cloneModel(cpPath);

		log.debug("CDODatabaseProxy - saveModels - Saving file "+paasageConfigModel.getAbsolutePath());
		cdoClient.exportModel(content.get(0), cpModel.getAbsolutePath());
	}

	public void savePM(ProviderModel pm, String paasageConfigurationId, String providerId) {
		String providerResourceId = getFMResourceId(paasageConfigurationId, providerId);
		log.info("CDODatabaseProxy - savePM - Saving PM Configuration Id {} Provider id {} under id: {}", paasageConfigurationId, providerId, providerResourceId);
		cdoClient.storeModel(pm.eContainer(), FMS_APP_CDO_SERVER_PATH+ providerResourceId);
		log.info("CDODatabaseProxy - savePM - Saving PM Configuration Id {} Provider id {} under id: {} - saved", paasageConfigurationId, providerId, providerResourceId);
	}

	private String getFMResourceId(String paasageConfigurationId, String providerId) {
		return paasageConfigurationId+"/"+providerId;
	}

}
