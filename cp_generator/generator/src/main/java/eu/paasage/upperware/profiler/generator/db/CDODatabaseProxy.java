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
import eu.paasage.upperware.metamodel.application.Provider;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.types.TypesPackage;
import eu.paasage.upperware.metamodel.types.typesPaasage.*;
import eu.paasage.upperware.profiler.cp.generator.model.tools.Constants;
import eu.paasage.upperware.profiler.cp.generator.model.tools.PaasageModelTool;
import eu.paasage.upperware.profiler.generator.service.camel.ModelService;
import fr.inria.paasage.saloon.camel.mapping.MappingPackage;
import fr.inria.paasage.saloon.camel.ontology.OntologyPackage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

/**
 * Database proxy for CDO
 */
@Slf4j
@Service
public class CDODatabaseProxy extends DatabaseProxy {

	private final static String FUNCTION_TYPES_ID="cpGenerator-functionTypes";
	private final static String OPERATING_SYSTEMS_ID="cpGenerator-operatingSystems";
	private final static String LOCATIONS_ID="cpGenerator-locations";
	private final static String PROVIDER_TYPES_ID="cpGenerator-providerTypes";

	private static final String FUNCTION_TYPES_FILE = "/model/cp/FunctionTypes.xmi";
	private static final String OPERATING_SYSTEMS_FILE = "/model/cp/OperatingSystems.xmi";
	private static final String LOCATIONS_FILE = "/model/cp/Locations.xmi";
	private static final String PROVIDER_TYPES_FILE = "/model/cp/ProviderTypes.xmi";

	public final static String CDO_SERVER_PATH= "upperware-models/";
	public final static String FMS_APP_CDO_SERVER_PATH= CDO_SERVER_PATH+"fms/";

	private CPCloner cloner;
	private CDOClientExtended cdoClient;
	private ModelService modelService;

	@Autowired
	public CDODatabaseProxy(CDOClientExtended cdoClient, ModelService modelService) {
		this.cdoClient = cdoClient;
		this.modelService = modelService;

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
		return loadFromCdoOrFile(FUNCTION_TYPES_FILE, FUNCTION_TYPES_ID);
	}

	/**
	 * Loads the operating systems from the CDO database
	 */
	public OperatingSystems loadOperatingSystems() {
		log.debug("Loading OperatingSystems");
		return loadFromCdoOrFile(OPERATING_SYSTEMS_FILE, OPERATING_SYSTEMS_ID);
	}

	/**
	 * Load locations from the CDO database
	 */
	public Locations loadLocations() {
		log.debug("Loading Locations");
		return loadFromCdoOrFile(LOCATIONS_FILE, LOCATIONS_ID);
	}

	/**
	 * Loads the provider types.
	 */
	public ProviderTypes loadProviderTypes() {
		log.debug("Loading ProviderTypes");
		return loadFromCdoOrFile(PROVIDER_TYPES_FILE, PROVIDER_TYPES_ID);
	}

	@SuppressWarnings("unchecked")
	private <T extends EObject> T loadFromCdoOrFile(String fileName, String cdoName) {
		List<EObject> providerTypesList = getResourceWithID(cdoName);
		if (CollectionUtils.isEmpty(providerTypesList)) {
			return loadFile(fileName, cdoName);
		} else {
			return (T) getLastElement(providerTypesList);
		}
	}

	@SuppressWarnings("unchecked")
	private <T extends EObject> T loadFile(String fileName, String cdoName){
		T result = null;

		InputStream is = getExistingModelFile(fileName);
		if(is != null) {
			log.debug("Loading file {}", fileName);
			Resource r= modelService.loadModelFromInputStream(fileName,is);
			if (r != null) {
				result = (T) r.getContents().get(0);
				cdoClient.storeModel(result, cdoName);
				log.info("{} loaded from File {} and stored under {}", result.getClass().getSimpleName(), fileName, cdoName);
			} else {
				log.error("File {} exists, but there is problem during file loading. The object will not be loaded!", fileName);
			}
		} else {
			log.error("File {} does not exist. The object will not be loaded!", fileName);
		}
		return result;
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
				result = cloner.cloneModel(id);
				log.info("CDODatabaseProxy - getResourceWithID - The resource "+ id+" does not exist");
			} catch(org.eclipse.emf.cdo.util.InvalidURIException ex) {
				log.debug("CDODatabaseProxy - getResourceWithID - The resource "+ id+" does not exist");
			}
		}
		return result;
	}

	private InputStream getExistingModelFile(String fileName) {
		InputStream fis= null;
		try {
			fis= CDODatabaseProxy.class.getClass().getResourceAsStream(fileName);
			log.debug("Loaded file {}", fileName);
		} catch (Exception e){
			log.error("Could not create InputStream for file {}", fileName);
		}
		return fis;
	}

	public void saveModels(PaasageConfiguration pc, ConstraintProblem cp) {
		String pcId= pc.getId();

		log.debug("CDODatabaseProxy - saveModels - Storing Models ");
		String cpPath = CDO_SERVER_PATH + pcId;
		cdoClient.storeModels(Arrays.asList(pc, cp), cpPath);
		log.debug("CDODatabaseProxy - saveModels - Models stored! ");

		File paasageConfigurationDir= PaasageModelTool.getGenerationDirForPaasageAppConfiguration(pcId);
		File paasageConfigModel= new File(paasageConfigurationDir, Constants.PAASAGE_CONFIGURATION_MODEL_FILE_NAME);
		File cpModel= new File(paasageConfigurationDir, Constants.CP_MODEL_FILE_NAME);

		List<EObject> content = cloner.cloneModel(cpPath);

		log.debug("CDODatabaseProxy - saveModels - Saving file "+paasageConfigModel.getAbsolutePath());
		cdoClient.exportModel(content.get(0), cpModel.getAbsolutePath());
	}

	public void savePM(ProviderModel pm, PaasageConfiguration pc, Provider provider) {
		String providerResourceId = getFMResourceId(pc, provider);
		log.debug("CDODatabaseProxy - savePM - Saving PM Configuration Id {} Provider id {} under id: {}", pc.getId(), provider.getId(), providerResourceId);
		cdoClient.storeModel(pm.eContainer(), FMS_APP_CDO_SERVER_PATH+ providerResourceId);
	}

	private String getFMResourceId(PaasageConfiguration pc, Provider provider) {
		return getFMResourceId(pc.getId(), provider.getId());
	}

	private String getFMResourceId(String pcId, String providerId) {
		return pcId+"/"+providerId;
	}

}
