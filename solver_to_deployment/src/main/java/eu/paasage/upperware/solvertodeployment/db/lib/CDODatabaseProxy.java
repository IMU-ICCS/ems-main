/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package eu.paasage.upperware.solvertodeployment.db.lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import eu.passage.upperware.commons.model.tools.ModelTool;
import org.apache.log4j.Logger;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.CamelPackage;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.DeploymentPackage;
import eu.paasage.camel.organisation.OrganisationPackage;
import eu.paasage.camel.provider.ProviderPackage;
import eu.paasage.camel.type.TypePackage;
import eu.paasage.mddb.cdo.client.CDOClient;
import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.types.TypesPackage;
import eu.paasage.upperware.metamodel.types.typesPaasage.Locations;
import eu.paasage.upperware.metamodel.types.typesPaasage.OperatingSystems;
import eu.paasage.upperware.metamodel.types.typesPaasage.ProviderTypes;
import eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage;

public class CDODatabaseProxy extends DatabaseProxy {

	public static Logger logger = Logger.getLogger("paasage-std-log");

	/*
	 * ATTRIBUTES
	 */

	/*
	 * The CDO Client to execute the queries and operations
	 */
	protected CDOClient cdoClient;

	/*
	 * The file containing the provider models
	 */
	protected static String cloudsPropertyFile = null;

	/*
	 * Alternative file containing the provider models
	 */
	protected static String cloudsPropertyFileCamel = null;

	/*
	 * Singleton Pattern. The Proxy is instanciated just one time.
	 */
	protected static CDODatabaseProxy proxy;

	/*
	 * CONSTANTS
	 */
	public final static String GENERATION_DIR = "paasage" + File.separator
			+ "configurations" + File.separator;
	public final static String WAR_MODEL_PATH = "std-models/";
	public final static String FUNCTION_TYPES_ID = "cpGenerator-functionTypes";

	public final static String OPERATING_SYSTEMS_ID = "cpGenerator-operatingSystems";

	public final static String LOCATIONS_ID = "cpGenerator-locations";

	public final static String PROVIDER_TYPES_ID = "cpGenerator-providerTypes";

	public final static String PROVIDERS = "cpGenerator-providers";

	public final static String PROVIDERS_CAMEL = "cpGenerator-providers-camel";

	public final static String PROVIDER_MAPPINGS = "cpGenerator-providerMappings";

	public final static String PROVIDER_MAPPINGS_CAMEL = "cpGenerator-providerMappings-camel";

	public final static String FUNCTION_TYPES_FILE = "FunctionTypes.xmi";

	public final static String OPERATING_SYSTEMS_FILE = "OperatingSystems.xmi";

	public final static String LOCATIONS_FILE = "Locations.xmi";

	public final static String PROVIDER_TYPES_FILE = "ProviderTypes.xmi";

	public final static String OPERATING_SYSTEMS_FILE_PATH = WAR_MODEL_PATH
			+ OPERATING_SYSTEMS_FILE;

	public final static String LOCATIONS_FILE_PATH = WAR_MODEL_PATH
			+ LOCATIONS_FILE;

	public final static String PROVIDER_TYPES_FILE_PATH = WAR_MODEL_PATH
			+ PROVIDER_TYPES_FILE;

	public final static String CDO_SERVER_PATH = "";

	/*
	 * CONSTRUCTOR
	 */

	/**
	 * Default constructor
	 */
	public CDODatabaseProxy() {
		cdoClient = new CDOClient();
		registerPackages();

		cloudsPropertyFile = "tomcatDatabaseClouds_alt.properties";

		cloudsPropertyFileCamel = "tomcatDatabaseClouds_camel.properties";

		loadOperatingSystems();
		loadLocations();
		loadProviderTypes();

	}

	/*
	 * METHODS
	 */

	/**
	 * Registers the package of the used models in the database
	 */
	public void registerPackages() {
		cdoClient.registerPackage(ApplicationPackage.eINSTANCE);
		cdoClient.registerPackage(CpPackage.eINSTANCE);
		cdoClient.registerPackage(TypesPackage.eINSTANCE);
		cdoClient.registerPackage(TypesPaasagePackage.eINSTANCE);
		cdoClient.registerPackage(TypePackage.eINSTANCE);

		// cdoClient.registerPackage(OntologyPackage.eINSTANCE);

		// cdoClient.registerPackage(MappingPackage.eINSTANCE);

		cdoClient.registerPackage(CamelPackage.eINSTANCE);
		cdoClient.registerPackage(ProviderPackage.eINSTANCE);

		cdoClient.registerPackage(OrganisationPackage.eINSTANCE);

		cdoClient.registerPackage(DeploymentPackage.eINSTANCE);

	}

	/**
	 * Returns the instance of the proxy
	 * 
	 * @return Instance of the proxy
	 */
	public static CDODatabaseProxy getInstance() {
		if (proxy == null) {
			proxy = new CDODatabaseProxy();
		}

		return proxy;
	}

	/**
	 * Loads the operating systems from the CDO database
	 */
	protected void loadOperatingSystems() {
		List<EObject> operatingSystemsList = getResourceWithID(OPERATING_SYSTEMS_ID);

		if (operatingSystemsList == null || operatingSystemsList.size() == 0) {

			InputStream is = getExistingModelFile(OPERATING_SYSTEMS_FILE,
					WAR_MODEL_PATH);
			// File file= new File(getExistingModelDirectory(),
			// "OperatingSystems.xmi");

			if (is != null) {

				Resource r = ModelTool.loadModelFromInputStream(
						OPERATING_SYSTEMS_FILE_PATH, is);

				operatingSystems = (OperatingSystems) r.getContents().get(0);

				cdoClient.storeModel(operatingSystems, OPERATING_SYSTEMS_ID,
						true);

				logger.info("CDODatabaseProxy - loadOperatingSystems - Operating systems loaded!");
			} else
				logger.error("CDODatabaseProxy - loadOperatingSystems - The file OperatingSystems.xmi does not exist. The operating systems will not be loaded!");
		} else
			operatingSystems = (OperatingSystems) operatingSystemsList.get(0);
	}

	/**
	 * Load locations from the CDO database
	 */
	protected void loadLocations() {
		List<EObject> locationsList = getResourceWithID(LOCATIONS_ID);

		if (locationsList == null || locationsList.size() == 0) {
			InputStream is = getExistingModelFile(LOCATIONS_FILE,
					WAR_MODEL_PATH);

			if (is != null) {
				Resource r = ModelTool.loadModelFromInputStream(
						LOCATIONS_FILE_PATH, is);

				locations = (Locations) r.getContents().get(0);

				cdoClient.storeModel(locations, LOCATIONS_ID, true);

				logger.info("CDODatabaseProxy- loadLocations - Locations loaded!");
			} else
				logger.error("CDODatabaseProxy - loadLocations - The file locations.xmi does not exist. The locations will not be loaded!");
		} else {
			locations = (Locations) locationsList.get(0);
		}
	}

	protected List<EObject> getResourceContents(String path) {
		CDOView view = cdoClient.openView();
		CDOResource resource = view.getResource(path);
		EList<EObject> content = resource.getContents();

		List<EObject> qr = new ArrayList<EObject>();
		logger.info("CDOClient - getResourceContents - Retrieved Resource " + resource + " path " + path);

		if (!content.isEmpty()) {
			logger.info("CDOClient - getResourceContents - Resource path " + path + " size " + content.size());

			for (EObject o : content) {
				logger.info("CDOClient - getResourceContents - Content " + o);
				qr.add(o);
			}

		} else
			logger.warn("CDOClient - getResourceContents - Resource path " + path + " is empty ");

		cdoClient.closeView(view);

		return qr;
	}

	/**
	 * Retrieves the resource from the CDO database with the specified id
	 * 
	 * @param id
	 *            Id of the resource
	 * @return The list of EObject relagted to the resource.
	 */
	protected List<EObject> getResourceWithID(String id) {
		List<EObject> result = null;
		try {
			result = getResourceContents(id);
		} catch (Exception ex) {
			logger.warn("CDODatabaseProxy - getResourceWithID - The resource "
					+ id + " does not exist");
		}

		return result;
	}

	/**
	 * Loads the provider types.
	 */
	protected void loadProviderTypes() {
		List<EObject> providerTypesList = getResourceWithID(PROVIDER_TYPES_ID);

		if (providerTypesList == null || providerTypesList.size() == 0) {

			InputStream is = getExistingModelFile(PROVIDER_TYPES_FILE,
					WAR_MODEL_PATH);

			if (is != null) {
				Resource r = ModelTool.loadModelFromInputStream(
						PROVIDER_TYPES_FILE_PATH, is);

				providerTypes = (ProviderTypes) r.getContents().get(0);

				logger.info("CDODatabaseProxy- Provider Types loaded!");

				cdoClient.storeModel(providerTypes, PROVIDER_TYPES_ID, true);
			} else
				logger.error("CDODatabaseProxy- The file ProviderTypes.xmi does not exist. The provider types will not be loaded!");
		} else {
			providerTypes = (ProviderTypes) providerTypesList.get(0);
		}
	}

	/**
	 * Return the CDO client used to access the database
	 * 
	 * @return The CDO client
	 */
	public CDOClient getCdoClient() {
		return cdoClient;
	}

	/**
	 * Stores the related models (i.e., operating systems, locations, function
	 * types and provider types) of a configuration
	 * 
	 * @param resSet
	 *            The resource set used to save models
	 * @param dir
	 *            The directory for saving the models
	 */
	public void saveRelatedModels(ResourceSet resSet, File dir) {
		// throw new UnsupportedOperationException();
		// DO NOTHING. IT SHOULD save the os, locations, etc. and the
		// relationship with the generated xmi files
	}

	/**
	 * Stores the application models
	 * 
	 * @param pc
	 *            The PaaSage Configuration Model
	 * @param resSet
	 *            The reource set to save models
	 */
	public void saveModels(PaasageConfiguration pc, ResourceSet resSet) {
		// savedModelsInLocalSystemFile(pc, cp, resSet);
		List<EObject> objects = new ArrayList<EObject>();

		objects.add(pc);

		cdoClient.storeModel(pc, CDO_SERVER_PATH + pc.getId(), true);

		File paasageConfigurationDir = new File(GENERATION_DIR);

		File paasageConfigModel = new File(paasageConfigurationDir,
				"paasageConfigurationModel.xmi");

		CDOTransaction view = cdoClient.openTransaction();
		CDOResource resource = view.getResource(CDO_SERVER_PATH + pc.getId());
		EList<EObject> content = resource.getContents();

		logger.info("CDODatabaseProxy - saveModels - Saving file "
				+ paasageConfigModel.getAbsolutePath());

		cdoClient.exportModel(content.get(0),
				paasageConfigModel.getAbsolutePath());

		view.close();
	}

	

	/**
	 * Verifies if there is a PaaSage Configuration model with the specified ID
	 * 
	 * @param paasageConfigurationId
	 *            The configuration id
	 * @return true if Paasage configuration model exists; false if the
	 *         configuration does not exist
	 */
	public boolean existPaaSageConfigurationModel(String paasageConfigurationId) {
		List<EObject> models = getResourceContents(CDO_SERVER_PATH
				+ paasageConfigurationId);

		boolean exist = false;

		if (models != null && models.size() > 0) {
			exist = (models.get(0) instanceof PaasageConfiguration);
		}

		return exist;
	}

	/**
	 * Loads the PaaSage Configuration model with the specified Id
	 * 
	 * @param paasageConfigurationId
	 *            The configuration id
	 * @return PaaSage Configuration model
	 */
	public PaasageConfiguration loadPaaSageConfigurationModel(
			String paasageConfigurationId) {
		List<EObject> models = getResourceContents(CDO_SERVER_PATH
				+ paasageConfigurationId);

		PaasageConfiguration pc = (PaasageConfiguration) models.get(0);

		return pc;
	}

	/**
	 * Retrieves an axisting model file
	 * 
	 * @param fileName
	 *            The name of the file
	 * @param type
	 *            The model Type
	 * @return The input stream related to the model file
	 */
	public InputStream getExistingModelFile(String fileName, String type) {

		InputStream fis = null;

		File modelDir = new File(WAR_MODEL_PATH);

		logger.info("CDODatabaseProxy - getExistingModelFile - modelDir "
				+ modelDir);

		if (modelDir != null && modelDir.isDirectory()) {
			try {
				fis = new FileInputStream(new File(modelDir, fileName));
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			}
		}

		return fis;
	}

	/**
	 * Closes the database session
	 */
	public void closeSession() {
		cdoClient.closeSession();
	}

	/**
	 * Opens the database session
	 */
	public void openSession() {

	}

	/**
	 * Retrieves the camel model with the specified path
	 * 
	 * @param modelPath
	 *            The model path
	 * @return The camel model
	 */
	public CamelModel getCamelModel(String modelPath) {

		CDOView view = cdoClient.openView();

		EList<EObject> res = view.getResource(modelPath).getContents();
		// TODO THE VIEW REMAINS OPEN

		CamelModel cm = (CamelModel) res.get(0);

		return cm;

	}

	public void saveDeployementModelInto(String camelModeId,
			DeploymentModel deployementModel) {
		CDOTransaction trans = null;
		trans = cdoClient.openTransaction();
		CDOResource resource = trans.getResource(camelModeId);
		EObject obj = resource.getContents().get(0);

		CamelModel camelModel = (CamelModel) obj;
		camelModel.getDeploymentModels().set(0, deployementModel);
		cdoClient.storeModel(camelModel, camelModeId, true);
		trans.close();
	}

	public DeploymentModel loadDeploymentModel(String camelId) {
		CDOTransaction trans = null;
		try {

			trans = cdoClient.openTransaction();
			CDOResource resource = trans.getResource(camelId);
			EObject obj = resource.getContents().get(0);

			CamelModel camelModel = (CamelModel) obj;

			EList<DeploymentModel> deployementModels = camelModel
					.getDeploymentModels();

			if (deployementModels.size() == 0) {
				logger.error("CDODatabaseProxy -loadDeploymentModel No model with ID: "
						+ camelId);
				return null;
			}
			logger.info("CDODatabaseProxy -loadDeploymentModel - loaded model with ID "
					+ camelId);
			return deployementModels.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			// TODO : To close ?
			// if(trans != null)
			// trans.close();
		}

	}

}
