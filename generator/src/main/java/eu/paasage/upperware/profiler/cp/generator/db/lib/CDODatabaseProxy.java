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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

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
import eu.paasage.upperware.metamodel.types.typesPaasage.FunctionTypes;
import eu.paasage.upperware.metamodel.types.typesPaasage.Locations;
import eu.paasage.upperware.metamodel.types.typesPaasage.OperatingSystems;
import eu.paasage.upperware.metamodel.types.typesPaasage.ProviderTypes;
import eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage;
import eu.paasage.upperware.profiler.cp.generator.model.lib.PaaSageConfigurationWrapper;
import eu.paasage.upperware.profiler.cp.generator.model.tools.Constants;
import eu.paasage.upperware.profiler.cp.generator.model.tools.FileTool;
import eu.paasage.upperware.profiler.cp.generator.model.tools.ModelTool;
import eu.paasage.upperware.profiler.cp.generator.model.tools.PaasageModelTool;
import fr.inria.paasage.saloon.camel.ProviderModelDecorator;
import fr.inria.paasage.saloon.camel.mapping.MappingListCamel;
import fr.inria.paasage.saloon.camel.mapping.MappingPackage;
import fr.inria.paasage.saloon.camel.ontology.OntologyCamel;
import fr.inria.paasage.saloon.camel.ontology.OntologyPackage;

/**
 * Database proxy for CDO
 * @author danielromero
 *
 */
public class CDODatabaseProxy extends DatabaseProxy 
{
	
	/*
	 * ATTRIBUTES 
	 */
	
	/*
	 * The CDO Client to execute the queries and operations
	 */
	protected CDOClientExtended cdoClient;
	
	/**
	 * The model cloner
	 */
	protected CPCloner cloner; 
	
	/*
	 * The file containing the provider models 
	 */
	protected static String cloudsPropertyFile= null; 
	
	/*
	 * Alternative file containing the provider models 
	 */
	protected static String cloudsPropertyFileCamel= null; 
	
	/*
	 * Singleton Pattern. The Proxy is instanciated just one time.  
	 */
	protected static CDODatabaseProxy proxy;
	
	/*
	 * Mapping related to the provider models. The key is the provider name/id 
	 */
	protected Map<String,ProviderModelDecorator> pmsMap;
	
	
	
	/*
	 * CONSTANTS 
	 */
	public final static String FUNCTION_TYPES_ID="cpGenerator-functionTypes"; 
	
	public final static String OPERATING_SYSTEMS_ID="cpGenerator-operatingSystems"; 
	
	public final static String LOCATIONS_ID="cpGenerator-locations"; 
	
	public final static String PROVIDER_TYPES_ID="cpGenerator-providerTypes"; 
	
	public final static String PROVIDERS="cpGenerator-providers"; 
	
	public final static String PROVIDERS_CAMEL="cpGenerator-providers-camel"; 
	
	public final static String PROVIDER_MAPPINGS="cpGenerator-providerMappings"; 
	
	public final static String PROVIDER_MAPPINGS_CAMEL="cpGenerator-providerMappings-camel"; 
	
	public final static String ONTOLOGY="cpGenerator-ontology"; 
	
	public final static String FUNCTION_TYPES_FILE= "FunctionTypes.xmi"; 
	
	public final static String OPERATING_SYSTEMS_FILE= "OperatingSystems.xmi"; 
	
	public final static String LOCATIONS_FILE= "Locations.xmi"; 
	
	public final static String PROVIDER_TYPES_FILE= "ProviderTypes.xmi"; 
	
	public final static String FUNCTION_TYPES_FILE_PATH= Constants.WAR_CP_DIR_PATH+FUNCTION_TYPES_FILE; 
	
	public final static String OPERATING_SYSTEMS_FILE_PATH= Constants.WAR_CP_DIR_PATH+OPERATING_SYSTEMS_FILE; 
	
	public final static String LOCATIONS_FILE_PATH= Constants.WAR_CP_DIR_PATH+LOCATIONS_FILE; 
	
	public final static String PROVIDER_TYPES_FILE_PATH= Constants.WAR_CP_DIR_PATH+PROVIDER_TYPES_FILE; 
	
	public final static String CDO_SERVER_PATH= "upperware-models/"; 
	
	public static String ONTOLOGY_ID= CDO_SERVER_PATH+"/camelOntology"; 
	
	public final static String FMS_APP_CDO_SERVER_PATH= CDO_SERVER_PATH+"fms/"; 
	

	/*
	 * CONSTRUCTOR 
	 */
	
	/**
	 * Default constructor
	 */
	private CDODatabaseProxy()
	{
		cdoClient = new CDOClientExtended();
		registerPackages();
		
		cloner = new CPCloner(); 
		
		cloudsPropertyFile= "tomcatDatabaseClouds_alt.properties";
		
		cloudsPropertyFileCamel= "tomcatDatabaseClouds_camel.properties";
		
		loadFunctionTypes();
		loadOperatingSystems();
		loadLocations(); 
		loadProviderTypes();
		loadPMs();
		//storePMs();
		
	}
	
	/*
	 * METHODS 
	 */
	
	/**
	 * Registers the package of the used models in the database
	 */
	public void registerPackages()
	{
		cdoClient.registerPackage(ApplicationPackage.eINSTANCE);
		cdoClient.registerPackage(CpPackage.eINSTANCE);
		cdoClient.registerPackage(TypesPackage.eINSTANCE);
		cdoClient.registerPackage(TypesPaasagePackage.eINSTANCE);
		cdoClient.registerPackage(OntologyPackage.eINSTANCE);
		cdoClient.registerPackage(MappingPackage.eINSTANCE);
		cdoClient.registerPackage(TypePackage.eINSTANCE);
		
		//cdoClient.registerPackage(OntologyPackage.eINSTANCE);
		
		//cdoClient.registerPackage(MappingPackage.eINSTANCE);
		
		cdoClient.registerPackage(CamelPackage.eINSTANCE);
		cdoClient.registerPackage(ProviderPackage.eINSTANCE);
		
		cdoClient.registerPackage(OrganisationPackage.eINSTANCE);
		
		cdoClient.registerPackage(DeploymentPackage.eINSTANCE);
		
	}
	
	/**
	 * Returns the instance of the proxy
	 * @return Instance of the proxy
	 */
	public static CDODatabaseProxy getInstance()
	{
		if(proxy==null)
		{
			proxy= new CDODatabaseProxy(); 
		}
				
		return proxy; 
	}
	 
	/**
	 * Retrieves an existing directory containing configuration files for the database 
	 * @return An existing configuration directory
	 */
	public File getExistingConfigPath() //TODO CAN BE REMOVED ?
	{
		File configDir = new File(Constants.CONFIG_FILES_DEFAULT_PATH); 
		
		cloudsPropertyFile= "localDatabaseClouds.properties"; 
		cloudsPropertyFileCamel= "localDatabaseClouds_camel.properties"; 
		
		if(!configDir.isDirectory())
		{
			
			configDir= new File(Constants.WAR_CONFIG_PATH); 
			cloudsPropertyFile= "tomcatDatabaseClouds_alt.properties"; 
			cloudsPropertyFileCamel= "tomcatDatabaseClouds_camel.properties"; 
			
		}
				
		return configDir; 
	}
	
	/**
	 * Load the function types from the CDO database
	 */
	protected void loadFunctionTypes()
	{
		//changeStandardOutput();
		List<EObject> functionTypesList= getResourceWithID(FUNCTION_TYPES_ID); //cdoClient.getResourceContents(FUNCTION_TYPES_ID); 
		
		if(functionTypesList==null || functionTypesList.size()==0)
		{
			InputStream is= getExistingModelFile(FUNCTION_TYPES_FILE, Constants.CP_DIR);
			
			
			//File modelDir= getExistingModelDirectory(); 
			
			//File functionTypesFile= new File(modelDir, "FunctionTypes.xmi");
			
			if(is!=null)
			{
				logger.debug("CDODatabaseProxy - loadFunctionTypes - Registring Function Types! "+ FUNCTION_TYPES_FILE_PATH+ " "+ is);
				
				Resource r= ModelTool.loadModelFromInputStream(FUNCTION_TYPES_FILE_PATH,is); 
				
				functionTypes= (FunctionTypes) r.getContents().get(0); 
				
				cdoClient.storeModel(functionTypes, FUNCTION_TYPES_ID);
				
				logger.debug("CDODatabaseProxy - loadFunctionTypes - FunctionTypes loaded!");
			}
			else
				logger.error("CDODatabaseProxy - loadFunctionTypes - The file FunctionTypes.xmi does not exist. The function types will not be loaded!");
		}
		else
			functionTypes= (FunctionTypes) functionTypesList.get(0); 
		
		//System.setErr(originalErrOutput); 
		
	}
	
	/**
	 * Loads the operating systems from the CDO database
	 */
	protected void loadOperatingSystems()
	{
		List<EObject> operatingSystemsList= getResourceWithID(OPERATING_SYSTEMS_ID); 
		
		if(operatingSystemsList==null || operatingSystemsList.size()==0)
		{	
			
			InputStream is= getExistingModelFile(OPERATING_SYSTEMS_FILE, Constants.CP_DIR); 
			//File file= new File(getExistingModelDirectory(), "OperatingSystems.xmi"); 
		
			if(is!=null)
			{
				logger.debug("CDODatabaseProxy - loadOperatingSystems - Registring Operating Systems!");
				Resource r= ModelTool.loadModelFromInputStream(OPERATING_SYSTEMS_FILE_PATH, is); 
				
				operatingSystems= (OperatingSystems) r.getContents().get(0); 
				
				cdoClient.storeModel(operatingSystems, OPERATING_SYSTEMS_ID);
				
				logger.debug("CDODatabaseProxy - loadOperatingSystems - Operating systems loaded!");
			}
			else
				logger.error("CDODatabaseProxy - loadOperatingSystems - The file OperatingSystems.xmi does not exist. The operating systems will not be loaded!");
		}
		else
			operatingSystems= (OperatingSystems) operatingSystemsList.get(0); 
		
	}
	
	/**
	 * Load locations from the CDO database
	 */
	protected void loadLocations()
	{
		List<EObject> locationsList= getResourceWithID(LOCATIONS_ID); 
		
		if(locationsList==null || locationsList.size()==0)
		{	
			InputStream is= getExistingModelFile(LOCATIONS_FILE, Constants.CP_DIR); 
			
			System.out.println("IS "+is);
			if(is!=null)
			{
				Resource r= ModelTool.loadModelFromInputStream(LOCATIONS_FILE_PATH, is); 
			
				locations= (Locations) r.getContents().get(0); 
				System.out.println("Locations "+locations);
				cdoClient.storeModel(locations, LOCATIONS_ID);
				System.out.println("Locations stored! ");
				 locationsList= getResourceWithID(LOCATIONS_ID); 
				 System.out.println("Locations Loaded! ");
				logger.debug("CDODatabaseProxy- loadLocations - Locations loaded!");
			}
			else
				logger.error("CDODatabaseProxy - loadLocations - The file locations.xmi does not exist. The locations will not be loaded!");
		}
		//System.out.println("Location list "+locationsList);
		else
		{
			locations= (Locations) locationsList.get(0); 
		}
		
		
	}
	
	/**
	 * Retrieves the resource from the CDO database with the specified id
	 * @param id Id of the resource
	 * @return The list of EObject relagted to the resource. 
	 */
	protected List<EObject> getResourceWithID(String id)
	{
		List<EObject> result= null; 
		
		if(cdoClient.existResource(id))
		{		
			try{
				result= cdoClient.getResourceContents(id); 
				System.out.println("CDODatabaseProxy - getResourceWithID - The resource "+ id+" is loaded!");
			}
			catch(org.eclipse.emf.cdo.util.InvalidURIException ex)
			{
				System.out.println("CDODatabaseProxy - getResourceWithID - The resource "+ id+" does not exist");
				logger.debug("CDODatabaseProxy - getResourceWithID - The resource "+ id+" does not exist");
			}
		}	
		
		return result; 
	}
	
	/**
	 * Loads the provider types. 
	 */
	protected void loadProviderTypes()
	{
		List<EObject> providerTypesList=getResourceWithID(PROVIDER_TYPES_ID); 
				
		if(providerTypesList==null || providerTypesList.size()==0)
		{	
			
			InputStream is= getExistingModelFile(PROVIDER_TYPES_FILE, Constants.CP_DIR); 
		
			if(is!=null)
			{
				Resource r= ModelTool.loadModelFromInputStream(PROVIDER_TYPES_FILE_PATH,is); 
				
				providerTypes= (ProviderTypes) r.getContents().get(0); 
				
				logger.debug("CDODatabaseProxy- Provider Types loaded!");
				
				cdoClient.storeModel(providerTypes, PROVIDER_TYPES_ID);
			}
			else
				logger.error("CDODatabaseProxy- The file ProviderTypes.xmi does not exist. The provider types will not be loaded!");
		}
		else
		{
			providerTypes= (ProviderTypes) providerTypesList.get(0); 
		}
	}
	
	
	/**
	 * Return the CDO client used to access the database
	 * @return The CDO client
	 */
	public CDOClientExtended getCdoClient() 
	{
		return cdoClient;
	}

	/**
	 * Stores the related models (i.e., operating systems, locations, function types and provider types) of a configuration
	 * @param resSet The resource set used to save models
	 * @param dir The directory for saving the models
	 */
	public void saveRelatedModels(ResourceSet resSet, File dir)
	{
		//throw new UnsupportedOperationException(); 
		//DO NOTHING. IT SHOULD save the os, locations, etc. and the relationship with the generated xmi files
	}
	
	/**
	 * Stores the cp and application models 
	 * @param pc The PaaSage Configuration Model
	 * @param cp The constraint problem model
	 * @param resSet The resource set to save models
	 */
	public void saveModels(PaasageConfiguration pc, ConstraintProblem cp, ResourceSet resSet)
	{	
		//savedModelsInLocalSystemFile(pc, cp, resSet);
		List<EObject> objects= new ArrayList<EObject>(); 
		
		
		objects.add(pc); 
		objects.add(cp); 
		
		String pcId= pc.getId(); 
		
		cdoClient.storeModels(objects, CDO_SERVER_PATH+pcId);
		
		
		File paasageConfigurationDir= PaasageModelTool.getGenerationDirForPaasageAppConfiguration(pcId); 
		
		File paasageConfigModel= new File(paasageConfigurationDir, Constants.PAASAGE_CONFIGURATION_MODEL_FILE_NAME); 
		
		File cpModel= new File(paasageConfigurationDir, Constants.CP_MODEL_FILE_NAME); 
		
		CDOTransaction view= cdoClient.openTransaction();
		CDOResource resource = view.getResource(CDO_SERVER_PATH+pcId);
		EList<EObject> content = resource.getContents();
		
		logger.debug("CDODatabaseProxy - saveModels - Saving file "+paasageConfigModel.getAbsolutePath());
		
		cdoClient.exportModel(content.get(0), cpModel.getAbsolutePath());
		//pc=(PaasageConfiguration) content.get(1); 
		//view.close();
	}
	
	
	/**
	 * Saves the specified models in the local file system
	 * @param pc The PaaSage configuration model
	 * @param cp The constraint problem model
	 * @param resSet The resource set used to store models
	 */
	protected void savedModelsInLocalSystemFile(PaasageConfiguration pc, ConstraintProblem cp, ResourceSet resSet)
	{
		//Just for testing purposes
		File paasageConfigurationDir= PaasageModelTool.getGenerationDirForPaasageAppConfiguration(pc); 
		
		File paasageConfigModel= new File(paasageConfigurationDir, Constants.PAASAGE_CONFIGURATION_MODEL_FILE_NAME); 
		
		File cpModel= new File(paasageConfigurationDir, Constants.CP_MODEL_FILE_NAME); 
		

		try {	
			Resource paasageConfigurationResource = resSet.createResource(URI.createFileURI(paasageConfigModel.getCanonicalPath())); 
			
			paasageConfigurationResource.getContents().add(pc); 
			
			logger.debug("CDODatabaseProxy - savedModelsInLocalSystemFile - Saving files in dir "+paasageConfigurationDir.getCanonicalPath()); 
			
			ModelTool.saveModel(paasageConfigurationResource);
			
			logger.debug("CDODatabaseProxy - savedModelsInLocalSystemFile - File saved: "+paasageConfigModel.getCanonicalPath()); 
			
			
			Resource cpResource;
			
			
			cpResource = resSet.createResource(URI.createFileURI(cpModel.getCanonicalPath()));
			
			cpResource.getContents().add(cp); 
			
			ModelTool.saveModel(cpResource);
			
			logger.debug("CDODatabaseProxy - savedModelsInLocalSystemFile - File saved: "+cpModel.getAbsolutePath()); 
			
		} catch (IOException e) {
			
			e.printStackTrace();
		} 
	}
	
	/**
	 * Loads the related models (i.e., operating systems, locations, function types and provider types) of a configuration
	 * @param resSet The resource set to load the models
	 * @param dir The directory containing the models
	 * @param wrapper The wrapper of the configuration
	 */
	public void loadRelatedModels(ResourceSet resSet, File dir, PaaSageConfigurationWrapper wrapper)
	{
		//TODO LOAD EVERYTHING AGAIN ??? Check the loaded model in the tests!
		loadFunctionTypes(); 
		
		loadLocations();
		
		loadOperatingSystems();
		
		loadProviderTypes(); 
		
		
		wrapper.setFunctionTypes(functionTypes);
		
		wrapper.setLocations(locations);
		
		wrapper.setOperatingSystems(operatingSystems);
		
		wrapper.setProviderTypes(providerTypes);
		
	}
	
	/**
	 * Verifies if there is a Constraint Configuration model with the specified ID
	 * @param paasageConfigurationId The configuration id
	 * @return true if CP model exists; false if the CP does not exist
	 */
	public boolean existCPModel(String paasageConfigurationId)
	{
		
		CDOView view = cdoClient.openView(); 
		boolean exist= false; 
		try{
			
		
			List<EObject> models=cdoClient.getResourceContents(CDO_SERVER_PATH+paasageConfigurationId, view); 
			
			
			
			if(models!=null)
				
			{
				for(int i=0; i<models.size() && !exist; i++)
					exist= (models.get(i) instanceof ConstraintProblem);   
			}
			
			cdoClient.closeView(view);
		}
		catch(org.eclipse.emf.cdo.util.InvalidURIException ex)
		{
			//Do nothing
		}
		catch(org.eclipse.emf.cdo.common.util.CDOException ex)
		{
			//Do nothing
		}
		
		return exist; 
	}
	
	
	/**
	 * Verifies if there is a PaaSage Configuration model with the specified ID
	 * @param paasageConfigurationId The configuration id
	 * @return true if Paasage configuration model exists; false if the configuration does not exist
	 */
	public boolean existPaaSageConfigurationModel(String paasageConfigurationId)
	{
		List<EObject> models=cdoClient.getResourceContents(CDO_SERVER_PATH+paasageConfigurationId); 
		
		boolean exist= false; 
		
		if(models!=null && models.size()>0)
		{
			exist= (models.get(0) instanceof PaasageConfiguration);   
		}
		
		
		return exist; 
	}
	
	
	/**
	 * Loads the Constraint Problem model with the specified Id
	 * @param paasageConfigurationId The configuration id
	 * @return ConstraintProblem model 
	 */
	public ConstraintProblem loadCPModel(String paasageConfigurationId)
	{
		List<EObject> models=cdoClient.getResourceContents(CDO_SERVER_PATH+paasageConfigurationId); 
		
		ConstraintProblem cp= null; 
		
		for(int i=0; i<models.size();i++)
			if(models.get(i) instanceof ConstraintProblem)
				cp= (ConstraintProblem) models.get(i); 
		
		return cp;
	}
	
	/**
	 * Loads the PaaSage Configuration model with the specified Id
	 * @param paasageConfigurationId The configuration id
	 * @return  PaaSage Configuration model 
	 */
	public PaasageConfiguration loadPaaSageConfigurationModel(String paasageConfigurationId)
	{
		List<EObject> models=cdoClient.getResourceContents(CDO_SERVER_PATH+paasageConfigurationId); 
		
		PaasageConfiguration pc= (PaasageConfiguration) models.get(0); 
		
		return pc; 
	}
	
	/**
	 * Retrieves an axisting model file 
	 * @param fileName The name of the file
	 * @param type The model Type
	 * @return The input stream related to the model file
	 */
	public InputStream getExistingModelFile(String fileName, String type)
	{
		
		InputStream fis= null; 
		
		File modelDir= getExistingModelDirectory(type); 
		
		logger.debug("CDODatabaseProxy - getExistingModelFile - modelDir "+modelDir);
		
		if(modelDir!=null && modelDir.isDirectory())
		{
			try {
				fis= new FileInputStream(new File(modelDir, fileName));
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			} 
		}
		else
		{
			logger.debug("CDODatabaseProxy - getExistingModelFile - loading file "+Constants.WAR_MODEL_PATH+type+File.separator+fileName);
			fis= FileTool.getInputStreamFromFileName(Constants.WAR_MODEL_PATH+type+File.separator+fileName); 
			logger.debug("CDODatabaseProxy - getExistingModelFile - loaded file "+fis);
		}
		
		return fis; 
	}
	
	/**
	 * Stores the provider model 
	 * @param pm The provider model to be stored 
	 * @param pc The configuration related to this provider model
	 * @param provider The provider related to the provider model
	 */
	public void savePM(ProviderModel pm, PaasageConfiguration pc, Provider provider)
	{
		logger.debug("CDODatabaseProxy - savePM - Saving PM Configuration Id "+pc.getId()+" Provider id"+provider.getId());
		logger.debug("CDODatabaseProxy - savePM - Saving PM "+ PaasageModelTool.getFMResourceId(pc, provider));
		cdoClient.storeModel(pm.eContainer(), FMS_APP_CDO_SERVER_PATH+PaasageModelTool.getFMResourceId(pc, provider));
	}
	
	/**
	 * Closes the database session
	 */
	public void closeSession()
	{
		cdoClient.closeSession(); 
	}
	
	/**
	 * Opens the database session
	 */
	/*public void openSession()
	{
		if(cdoClient.getSession().isClosed())
			cdoClient.initSession();
	}//
	


	/**
	 * Retrieves a map containing ProviderModelDecorator objects. Keys are provider's names.   
	 * @return Map with ProviderModelDecorator objects
	 */
	public Map<String, ProviderModelDecorator> getPMsMap() 
	{
		loadPMs();
		
		return pmsMap;
	}


	/**
	 * Returns the camel ontology 
	 * @return The camel ontology 
	 */
	public OntologyCamel getCamelOntology() 
	{
		
		List<EObject> result= cdoClient.getResourceContents(ONTOLOGY_ID); 
		
		OntologyCamel ontology= (OntologyCamel) result.get(0); 
		
		
		return ontology;
	}
	
	/**
	 * Returns the camel ontology 
	 * @return The camel ontology 
	 */
	public OntologyCamel getCamelOntologyCopy() 
	{
		
		List<EObject> result= cloner.cloneModel(ONTOLOGY_ID); 
		
		OntologyCamel ontology= (OntologyCamel) result.get(0); 
		
		
		return ontology;
	}
	
	/**
	 * Loads the provider models from the database or the file systems
	 */
	protected void loadPMs()
	{
		
		List<EObject> providersList= null;
		
		List<EObject> mappings= null;
		
		pmsMap= new Hashtable<String,ProviderModelDecorator>(); 
		
		
		
		//Only providers defined in the properties file are considered - This restriction is due to saloon and the mapping model
		File cloudsFile = new File(getExistingConfigPath(), cloudsPropertyFileCamel);
			
		logger.debug("CDODatabaseProxy - loadPMs - Property file "+cloudsFile.getAbsolutePath());
			
		{
			
			Properties properties= new Properties(); 
			
			try {
				
				if(cloudsFile.isFile())
					properties.load(new FileReader(cloudsFile));
				else
				{
					InputStream is= FileTool.getInputStreamFromFileName(Constants.WAR_CONFIG_PATH+cloudsPropertyFileCamel); 
					
					logger.debug("CDODatabaseProxy - loadPMs - Property file "+Constants.WAR_CONFIG_PATH+cloudsPropertyFileCamel);
					
					if(is!=null)
						properties.load(is);
				}
					
					
					if(!properties.isEmpty())
					{
						Iterator<Object> it= properties.keySet().iterator(); 
						
						ResourceSet rs= new ResourceSetImpl(); 
						
						InputStream ontologieFile= getExistingModelFile("cloudOntoCamel.xmi", Constants.ONTOLOGY_DIR); 
						
						logger.debug("CDODatabaseProxy - loadPMs - Ontology file "+Constants.WAR_ONTOLOGY_PATH+"cloudOntoCamel.xmi "+ontologieFile);
						
						Resource ontologyResource = ModelTool.loadModelFromInputStream(Constants.WAR_ONTOLOGY_PATH+"cloudOntoCamel.xmi", ontologieFile);
						
						
						cdoClient.storeModel(ontologyResource.getContents().get(0), ONTOLOGY_ID);
						
						ontologieFile= getExistingModelFile("cloudOntoCamel.xmi", Constants.ONTOLOGY_DIR); 
						
						ModelTool.loadModelFromInputStream(rs,Constants.WAR_ONTOLOGY_PATH+"cloudOntoCamel.xmi", ontologieFile); //Required, otherwise it fails when executing with jar
						
						providersList= new ArrayList<EObject>(); 
						
						mappings= new ArrayList<EObject>(); 
						
						while(it.hasNext())
						{
							String cloud= (String) it.next(); 
							
							String line= properties.getProperty(cloud); 
							
							String[] info= line.split(";"); //[0] FM
															//[1] Mapping
							
							if(info.length==2)
							{
								CamelModel pm= null; 
								
								//Try to load the Model from CDO if it exists
								
								if(cdoClient.existResource(FMS_APP_CDO_SERVER_PATH+cloud))
								{
									List<EObject> cloned= cloner.cloneModel(FMS_APP_CDO_SERVER_PATH+cloud); 
									
									pm= (CamelModel) cloned.get(0); 
									
									logger.debug("CDODatabaseProxy- The PM "+cloud+" has been cloaned!");
								}
								else //Load the file according to the properties file
								{
									
									InputStream cloudFile=FileTool.getInputStreamFromFileName(info[0]);
									
									if(cloudFile==null)
										cloudFile = FileTool.getInputStreamFromLocalFile(info[0]); 
									
									if(cloudFile==null)
										logger.warn("CDODatabaseProxy- The FM file "+info[0]+" does not exist. The FM and mapping for "+cloud +" will be not loaded!");
									else
									{
										Resource r= ModelTool.loadModelFromInputStream(rs, info[0], cloudFile); 
										
										pm= (CamelModel) r.getContents().get(0); 
										
										cdoClient.storeModel(pm, FMS_APP_CDO_SERVER_PATH+cloud); 
										
										logger.debug("CDODatabaseProxy- The PM "+cloud+" has been saved!");
										
										cloudFile=FileTool.getInputStreamFromFileName(info[0]);
										if(cloudFile==null)
											cloudFile = FileTool.getInputStreamFromLocalFile(info[0]);
																				
										r= ModelTool.loadModelFromInputStream(rs, info[0], cloudFile); 
										
										pm= (CamelModel) r.getContents().get(0); 
										
									}
										
								}
								
								if(pm!=null) //Only considers the mapping if the provider model exists
								{
									
									
									InputStream mappingFile= FileTool.getInputStreamFromFileName(info[1]);
									
									if(mappingFile==null)
										mappingFile = FileTool.getInputStreamFromLocalFile(info[1]); 
									
									if(mappingFile==null)
										logger.warn("CDODatabaseProxy- The mapping file "+info[1]+" does not exist. The FM and mapping for "+cloud +" will be not loaded!");
									else
									{
										providersList.add(pm); 
										logger.debug("CDODatabaseProxy- The PM "+cloud+" has been loaded!");
										
										Resource r2= ModelTool.loadModelFromInputStream(rs, info[1], mappingFile); 
										
										
										MappingListCamel mappingList= (MappingListCamel) r2.getContents().get(0);  
										
										mappings.add(mappingList); 
										logger.debug("CDODatabaseProxy- The mapping of "+cloud+" has been loaded!");
		
										
										ProviderModelDecorator pmw= new ProviderModelDecorator(cloud,pm.getProviderModels().get(0), mappingList); 
										
										pmsMap.put(cloud, pmw); 
										logger.debug("CDODatabaseProxy- PM decorator of "+cloud+" has been added!");
										
									}
									
									
									
								}
								
							}
							else
								logger.warn("CDODatabaseProxy- There is problem with the format of the file. The FMs and Mappings will be not loaded!");
						}
						
						
						
						//cdoClient.storeModels(providersList, PROVIDERS_CAMEL); //TODO DEAL WITH CROSS REFERENCES PROBLEM 
						
						//cdoClient.storeModelsWithCrossReferences(ontologyResource.getContents(), ONTOLOGY_ID);
						
						//cdoClient.storeModelsWithCrossReferences(rs, PROVIDER_MAPPINGS_CAMEL); //TODO DEAL WITH THIS STORING
						
						//mappings= getResourceWithID(PROVIDER_MAPPINGS_CAMEL);
						
						//providersList=getResourceWithID(PROVIDERS_CAMEL);
						
						/*for(EObject list: mappings)
						{
							MappingListCamel mappingList= (MappingListCamel) list; 
							ProviderModel pm= mappingList.getProviderModel(); 
							
							initMapMapping(pm.getRootFeature().getName(), pm, mappingList);
						}*/
												
					}
					else
						logger.warn("CDODatabaseProxy- The configuration file and "+Constants.WAR_CONFIG_PATH+cloudsPropertyFile +" do not exist!");
					
				} catch (FileNotFoundException e) {
					
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				} 
				
				
				
			}
	
		}
		/*else
		{
			for(EObject list: mappings)
			{
				MappingListCamel mappingList= (MappingListCamel) list; 
				ProviderModel pm= mappingList.getProviderModel(); 
				
				initMapMapping(pm.getRootFeature().getName(), pm, mappingList);
			}
		}*/
		
	

	
	
	protected void storePMs()
	{
		//List<EObject> providersList= getResourceWithID(PROVIDERS_CAMEL);
		
		
			File cloudsFile = new File(getExistingConfigPath(), cloudsPropertyFileCamel);
			
			logger.debug("CDODatabaseProxy - storePMs - Property file "+cloudsFile.getAbsolutePath());
			
			
			Properties properties= new Properties(); 
		
			try {
				
				if(cloudsFile.isFile())
					properties.load(new FileReader(cloudsFile));
				else
				{
					InputStream is= FileTool.getInputStreamFromFileName(Constants.WAR_CONFIG_PATH+cloudsPropertyFileCamel); 
					
					logger.debug("CDODatabaseProxy - storePMs - Property file "+Constants.WAR_CONFIG_PATH+cloudsPropertyFileCamel);
					
					if(is!=null)
						properties.load(is);
				}
				
				
				if(!properties.isEmpty())
				{
					Iterator<Object> it= properties.keySet().iterator(); 
					
					ResourceSet rs= new ResourceSetImpl(); 
					
					while(it.hasNext())
					{
						String cloud= (String) it.next(); 
						
						String line= properties.getProperty(cloud); 
						
						String[] info= line.split(";"); //[0] FM
														//[1] Mapping
						
						if(info.length==2)
						{
							InputStream cloudFile=FileTool.getInputStreamFromFileName(info[0]); 
							
							
							
							if(cloudFile==null)
								cloudFile = FileTool.getInputStreamFromLocalFile(info[0]); 
							
							
							if(cloudFile!=null)
							{
								Resource r= ModelTool.loadModelFromInputStream(rs, info[0], cloudFile); 
								
								CamelModel pm= (CamelModel) r.getContents().get(0); 
								
								
								List<EObject> pmList=getResourceWithID(FMS_APP_CDO_SERVER_PATH+pm.getProviderModels().get(0).getRootFeature().getName());
								
								if(pmList==null || pmList.size()==0)
								{
									cdoClient.storeModel(pm, FMS_APP_CDO_SERVER_PATH+pm.getProviderModels().get(0).getRootFeature().getName());
								}
								
							}
							else if(cloudFile==null)
								logger.warn("CDODatabaseProxy- storePMs - The FM file "+info[0]+" does not exist. The PM for "+cloud +" will be not stored!");
							
							
						}
						else
							logger.warn("CDODatabaseProxy- storePMs -There is problem with the format of the file. The PMs will be not stored!");
					}											
				}
				else
					logger.warn("CDODatabaseProxy- storePMs - The configuration file and "+Constants.WAR_CONFIG_PATH+cloudsPropertyFile +" do not exist!");
				
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
				
		
		
	}
	
	protected void initMapMapping(String cloud, ProviderModel pm, MappingListCamel mappingList)
	{
		ProviderModelDecorator pmw= new ProviderModelDecorator(cloud,pm, mappingList); 
		
		pmsMap.put(cloud, pmw); 
		logger.debug("CDODatabaseProxy- initMapMapping "+cloud+" has been added!");
	}

	/**
	 * Loads a procider model
	 * @param pc The paasage configuration related to the provider model
	 * @param provider The provider related to the provider model
	 * @return The provider model
	 */
	public ProviderModel loadPM(PaasageConfiguration pc, Provider provider) 
	{
		CDOView view= cdoClient.openView();
		EList<EObject> res= view.getResource(FMS_APP_CDO_SERVER_PATH+PaasageModelTool.getFMResourceId(pc, provider)).getContents(); 
		
		CamelModel pm= (CamelModel) res.get(0); 
		
		logger.debug("CDODatabaseProxy- loadPM- PM "+pm.getProviderModels().get(0).getRootFeature().getName());
		
		//cdoClient.closeView(view);//TODO THE VIEW REMAINS OPEN
		
		return pm.getProviderModels().get(0); 
	}


	/**
	 * Retrieves the camel model with the specified path
	 * @param modelPath The model path
	 * @return The camel model
	 */
	public CamelModel getCamelModel(String modelPath) {
		
		CDOView view= cdoClient.openView();
		
		EList<EObject> res;
		try {
			res= view.getResource(modelPath).getContents();
		} catch (Exception e) {
			return null;
		}
		//TODO THE VIEW REMAINS OPEN
		
		CamelModel cm= (CamelModel) res.get(0);
		
		return cm; 
		
	}
	
	/**
	 * Loads a provider model
	 * @param appId The paasage id
	 * @param providerId The provider id related to the provider model
	 * @return The provider model
	 */
	public ProviderModel loadPM(String appId, String providerId) 
	{
		CDOView view= cdoClient.openView();
		EList<EObject> res= view.getResource(FMS_APP_CDO_SERVER_PATH+PaasageModelTool.getFMResourceId(appId, providerId)).getContents(); 
		
		CamelModel pm= (CamelModel) res.get(0); 
		
		logger.debug("CDODatabaseProxy- loadPM- PM "+pm.getProviderModels().get(0).getRootFeature().getName());
		
		return pm.getProviderModels().get(0); 
	}
	
	/**
	 * Loads a provider model
	 * @param appId The application id
	 * @param providerId The provider id related to the provider model
	 * @param vmId The virtual machine identifier 
	 * @return The provider model
	 */
	public ProviderModel loadPM(String appId, String providerId, String vmId) 
	{
		CDOView view= cdoClient.openView();
		EList<EObject> res= view.getResource(FMS_APP_CDO_SERVER_PATH+PaasageModelTool.getFMResourceId(appId, providerId)).getContents(); 
		
		//The Name of the camel model is the Id of the VM
		
		CamelModel pm= null; 
		
		logger.debug("CDODatabaseProxy- loadPM- VMId "+vmId);
		
		for(EObject r:res)
		{
			CamelModel provider= (CamelModel) r; 
			
			logger.debug("CDODatabaseProxy- loadPM- Camel Name "+provider.getName());
			
			if(vmId.contains(provider.getName()))
			{	
				pm= provider; 
				break; 
			}	
		}
		
		
		
		logger.debug("CDODatabaseProxy- loadPM- PM "+pm);
		
		return pm.getProviderModels().get(0); 
	}
	
}
