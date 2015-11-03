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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import eu.paasage.camel.type.TypePackage;
import eu.paasage.mddb.cdo.client.CDOClient;
import eu.paasage.upperware.metamodel.application.ApplicationComponent;
import eu.paasage.upperware.metamodel.application.ApplicationFactory;
import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.application.VirtualMachineProfile;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.types.TypesPackage;
import eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage;
import eu.paasage.upperware.profiler.cp.generator.db.api.IDatabaseProxy;
import eu.paasage.upperware.profiler.cp.generator.db.lib.CDODatabaseProxy;
import eu.paasage.upperware.profiler.cp.generator.model.api.IProcessorFactory;
import eu.paasage.upperware.profiler.cp.generator.model.api.ISender;
import eu.paasage.upperware.profiler.cp.generator.model.camel.lib.CamelModelProcessor;
import eu.paasage.upperware.profiler.cp.generator.model.derivator.lib.CPModelDerivator;
import eu.paasage.upperware.profiler.cp.generator.model.tools.Constants;
import eu.paasage.upperware.profiler.cp.generator.model.tools.FileTool;
import eu.paasage.upperware.profiler.cp.generator.model.tools.PaaSagePropertyManager;
import eu.paasage.upperware.profiler.cp.generator.model.tools.PaasageModelTool;
import fr.inria.paasage.saloon.camel.mapping.MappingPackage;
import fr.inria.paasage.saloon.camel.ontology.OntologyPackage;


public class GenerationOrchestrator 
{
	/*
	 * CONSTANTS
	 */
	private static final String FACTORIES_FILE_NAME="factories.properties"; 
	
	private static final String FACTORIES_PROPERTIES_FILE="src"+File.separator+"main"+File.separator+"resources"+File.separator+"config"+File.separator+FACTORIES_FILE_NAME;
	
	private static final String CREATORS_FILE_NAME="functionCreators.properties"; 
	
	private static final String CREATORS_PROPERTIES_FILE="src"+File.separator+"main"+File.separator+"resources"+File.separator+"config"+File.separator+CREATORS_FILE_NAME;
	
		
	private static final String ESTIMATOR_FACTORIES_PROPERTIES_FILE="src"+File.separator+"main"+File.separator+"resources"+File.separator+"config"+File.separator+"estimatorFactories.properties";
	
	private static final String WAR_ESTIMATOR_FACTORIES_PROPERTIES_FILE=Constants.WAR_CONFIG_PATH+"estimatorFactories.properties";
	
	private static final String WAR_LOG4G_PROPERTIES_FILE=Constants.WAR_CONFIG_PATH+"log4j.properties";
	
	private static final String LOG4G_PROPERTIES_FILE="src"+File.separator+"main"+File.separator+"resources"+File.separator+"config"+File.separator+"log4j.properties"; 
	
	private static final String JAR_LOG4G_PROPERTIES_FILE=Constants.JAR_CONFIG_PATH+"log4j.properties";
	
	private static final String JAR_FACTORIES_PROPERTIES_FILE=Constants.JAR_CONFIG_PATH+FACTORIES_FILE_NAME;
	
	private static final String WAR_FACTORIES_PROPERTIES_FILE=Constants.WAR_CONFIG_PATH+FACTORIES_FILE_NAME;
	
	private static final String JAR_CREATORS_PROPERTIES_FILE=Constants.JAR_CONFIG_PATH+CREATORS_FILE_NAME;
	
	private static final String WAR_CREATORS_PROPERTIES_FILE=Constants.WAR_CONFIG_PATH+CREATORS_FILE_NAME;
	
	
	private static final String CLOUDML_PROPERTY_KEY= "cloudML"; 
	
	private static final String CAMEL_PROPERTY_KEY= "camel"; 
	
	private static final String SALOON_ONTOLOGY_PROPERTY_KEY= "saloonOntology"; 
	
	/*
	 * ATTRIBUTES
	 */
	/*
	 * THE LOGGER 
	 */
	private static Logger logger= null;
	
	public static Logger getLogger()
	{
		if(logger==null)
		{	
			logger= Logger.getLogger("paasage-profiler-log");
			configLog();
		}	
			
		return logger; 
		
	}
	
	/*
	 * Map with the factories
	 */
	private Map<String,IProcessorFactory> loaders= new Hashtable<String, IProcessorFactory>(); 
	
	/*
	 * The CP model derivator
	 */
	private CPModelDerivator derivator; 
	
	/*
	 * The RabbitMQ sender
	 */
	private ISender sender; 
	
	/*
	 * The database proxy
	 */
	protected IDatabaseProxy database;
	
	/*
	 * The property manager
	 */
	protected PaaSagePropertyManager propertyManager; 
	
	/*
	 * CONSTRUCTOR
	 */
	/**
	 * The default constructor
	 */
	public GenerationOrchestrator()
	{	
		getLogger(); 
		propertyManager= PaaSagePropertyManager.getInstance(); 
		
		ApplicationPackage.eINSTANCE.eClass();
		CpPackage.eINSTANCE.eClass();
		TypesPackage.eINSTANCE.eClass();
		TypesPaasagePackage.eINSTANCE.eClass();
		OntologyPackage.eINSTANCE.eClass();
		MappingPackage.eINSTANCE.eClass();
		TypePackage.eINSTANCE.eClass();
		
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl()); 
		
		createFactories(); 
		
		database= CDODatabaseProxy.getInstance(); //TODO THIS SHOULD BE READ FROM A CONFIGURATION FILE
		
		InputStream creatorFile= selectExistingCreatorFile(); //selectExistingEstimatorFactoryFile(); 
		
		sender= new FileSystemSender();
		
		if(creatorFile!=null)
			derivator= new CPModelDerivator(creatorFile, database); 
		else
		{
			logger.error("GenerationOrchestrator - The creators file does not exist. The derivator will be not created!");
		}
	}
	
	/*
	 * METHODS
	 */
	/**
	 * Returns the database proxy
	 * @return The database proxy
	 */
	public IDatabaseProxy getDatabase() 
	{
		return database;
	}
	
	/**
	 * Generates the CP model by using the provided model infos
	 * @param modelInfos The list of model infos
	 * @return The id or path of the generate CP Model. It is stored in CDO
	 */
	public String generateCPModel(List<ModelFileInfo> modelInfos)
	{
		logger.info("************************************CP Generator Model To Solver************************************"); 
		List<ModelProcessor> processors= loadModels(modelInfos); 
		
		CamelModelProcessor camelProcessor= getCamelModelProcessorFromList(processors); 
		
		String id=""; 
		
		if(camelProcessor!=null)
		{
			PaasageConfiguration pc= ApplicationFactory.eINSTANCE.createPaasageConfiguration(); 
			
			String appId= camelProcessor.getCamelModel().getName(); //By default the id of the application is the name of the camel model
			
			if(camelProcessor.getCamelModel().getApplications()!=null && camelProcessor.getCamelModel().getApplications().size()>0)
			{
				appId= camelProcessor.getCamelModel().getApplications().get(0).getName(); //The id is the name of the application 
			}
		
			id= PaasageModelTool.generatePaasageAppConfigurationId(appId); 
		
			pc.setId(id); 

		
			File paasageConfigurationDir= PaasageModelTool.getGenerationDirForPaasageAppConfiguration(pc); 
		
			try {
				paasageConfigurationDir= new File(paasageConfigurationDir.getCanonicalPath());
			} catch (IOException e) {
			
				e.printStackTrace();
			} 
		
			ResourceSet resSet= new ResourceSetImpl(); 
			
			database.saveRelatedModels(resSet, paasageConfigurationDir);
			
			PaaSageConfigurationWrapper pcw= new PaaSageConfigurationWrapper(pc);//, paasageConfigurationDir, resSet); 
			
			database.loadRelatedModels(resSet, paasageConfigurationDir, pcw);
					
									
			logger.info("** Calling CamelModel Processor");
			camelProcessor.parseModel(pcw);
				
		
			if(!pcw.hasUserSolution && pcw.getPaasageConfiguration().getProviders().size()>0 && (pcw.getComponentsWithoutVM()==null || pcw.getComponentsWithoutVM().size()==0) && pcw.hasCorrectHostingRelationships)
			{
				logger.info("** Calling CPModelDerivator");
				ConstraintProblem cp= derivator.derivateConstraintProblem(pc, database); 
				//pc.getId();
				
				logger.debug("** Calling DatabseProxy");
				database.saveModels(pc, cp, resSet); 
				logger.debug("** Calling Sender");
				sender.sendPaasageConfigurationFiles(id); 
				
				PrintStream outputFile= System.err; 
				System.setErr(defaultErrOutput);
				outputFile.close();
	
				
				logger.info("** CP Model Id: "+id); 
			}
			else if(pcw.hasUserSolution && pcw.isValidUserSolution())
			{
				logger.info("** The user already provided a solution for the deployment. The CP Model will be not generated!"); 
			}
			else if(pcw.getPaasageConfiguration().getProviders().size()==0)
			{
				logger.info("** There is not a suitable provider. The CP Model will be not generated!"); 
			}
			else if(!pcw.hasCorrectHostingRelationships)
			{
				logger.info("** There are missing hosting relationships in the deployment model. The CP Model will be not generated!"); 
			}
			else if(pcw.getComponentsWithoutVM()!=null && pcw.getComponentsWithoutVM().size()>0)
			{
				logger.info("** There are not suitable providers for the following components: ");
				
				for(ApplicationComponent ac: pcw.getComponentsWithoutVM())
				{
					logger.info("** "+ac.getCloudMLId());
				}
				
				logger.info("** The CP Model will be not generated! ");
				
			}
			
			else
			{
				logger.info("** The user already provided a solution for the deployment but it is not valid. The CP Model will be not generated!"); 
			}
		}
		else
		{
			logger.error("** There is not Processor for Camel Models. The input model can not be processed");
		}
		
		logger.info("****************************************************************************************************"); 
		
		return id; 
		
	}
	
	protected static PrintStream defaultErrOutput= null; 
	/**
	 * Configures the logger
	 */
	protected static void configLog()
	{
		BasicConfigurator.configure();
		InputStream inpFile= selectExistingLog4File(); 
		
		PropertyConfigurator.configure(inpFile);
		logger.setLevel(Level.INFO);
		
		//Configures the CDO Client logger
		Logger templogger = org.apache.log4j.Logger.getLogger(CDOClient.class);
		templogger.setLevel(Level.OFF);
	
		org.apache.log4j.Logger.getRootLogger().setLevel(Level.OFF);
		
		OutputStream output;
		try {
			output = new FileOutputStream("."+File.separator+"system.err.txt");
			
			PrintStream printErr = new PrintStream(output);
			defaultErrOutput= System.err; 
			System.setErr(printErr);
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Searches a existing log4 properties file
	 * @return The input stream of the properties file
	 */
	protected static InputStream selectExistingLog4File()
	{
		
		File pFile= new File(LOG4G_PROPERTIES_FILE);
		
		
		InputStream fis= null; 
		
		if(!pFile.isFile())
		{	
			fis= GenerationOrchestrator.class.getClass().getResourceAsStream(JAR_LOG4G_PROPERTIES_FILE); 
		
			if(fis==null)
				fis= GenerationOrchestrator.class.getClassLoader().getResourceAsStream(WAR_LOG4G_PROPERTIES_FILE); 
		} else
			try {
				fis= new FileInputStream(pFile);
			} catch (FileNotFoundException e) {
				logger.error("GenerationOrchestrator - selectExistingLog4File - Problems loading log4 properties file!"); 
				e.printStackTrace();
			} 	
		
		return fis;
	}
	

	/**
	 * Loads model specified in a list
	 * @param modelInfos The list of models
	 * @return The list of model processors to deal with the loaded models
	 */
	protected List<ModelProcessor> loadModels(List<ModelFileInfo> modelInfos)
	{
		logger.debug("GenerationOrchestrator- Loading models");
		List<ModelProcessor> wrappers= new ArrayList<ModelProcessor>(); 
		for(ModelFileInfo mi: modelInfos)
		{
			IProcessorFactory loader= loaders.get(mi.getModelTypeId()); 
			
			if(loader!=null)
			{

				{	
					ModelProcessor model= loader.loadModel(mi.getModelPath()); //TODO RELATIONSHIP BETWEEN MODELS
					wrappers.add(model); 
					
				}	

				
			}
			else
				logger.warn("GenerationOrchestrator- There is not a loader for "+mi.getModelTypeId()+". The model will be not loaded."); 
			
			
		}
		logger.debug("GenerationOrchestrator- Models Loaded");
		
		return wrappers; 
	}
	
	/**
	 * Creates the factories for different models
	 */
	protected void createFactories()
	{
		//Retrieve from wp3_config.properties
		
		String saloonFactory= propertyManager.getCPGeneratorProperty(SALOON_ONTOLOGY_PROPERTY_KEY); 
		
		String cloudMLFactory= propertyManager.getCPGeneratorProperty(CLOUDML_PROPERTY_KEY); 
		
		String camelFactory= propertyManager.getCPGeneratorProperty(CAMEL_PROPERTY_KEY); 
			
			try {
				
				if(saloonFactory==null || cloudMLFactory== null || camelFactory==null)
				{
				
					InputStream loadersIS= selectExistingFactoryFile();
					
					Properties properties= new Properties(); 
				
				
					properties.load(loadersIS);
									
					if(!properties.isEmpty())
					{
						Iterator<Object> it=properties.keySet().iterator(); 
						
						while(it.hasNext())
						{
							String key= (String) it.next();
							String className= properties.getProperty(key); 
							
							createAndAddLoader(key, className);
							
						
						}
						
						
						
						logger.debug("GenerationOrchestrator- Loaders initialized!"); 
					}
					else
					{
						logger.warn("GenerationOrchestrator- createFactories - The properties file "+Constants.WAR_CONFIG_PATH+FACTORIES_FILE_NAME+" does not exist. The loaders are not initialized!"); 
					}
				}	
				else //The Paasage properties are defined
				{
					//Saloon
					createAndAddLoader(SALOON_ONTOLOGY_PROPERTY_KEY, saloonFactory);
					
					//CloudML
					createAndAddLoader(CLOUDML_PROPERTY_KEY, cloudMLFactory);
					
					createAndAddLoader(CAMEL_PROPERTY_KEY, camelFactory);
				}	
					
			} catch (FileNotFoundException e) {
				logger.warn("GenerationOrchestrator- createFactories - Problems loading the file "+FACTORIES_PROPERTIES_FILE+". The loaders are not initialized!"); 
				e.printStackTrace();
			} catch (IOException e) {
				logger.warn("GenerationOrchestrator- createFactories - Problems loading the file "+FACTORIES_PROPERTIES_FILE+". The loaders are not initialized!"); 
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				logger.warn("GenerationOrchestrator- createFactories - Problems loading the class of one of loaders. All the loaders are not initialized!"); 
				e.printStackTrace();
			} catch (SecurityException e) {
				logger.warn("GenerationOrchestrator- createFactories - Problems loading the constructors of one of loaders. All the loaders are not initialized!"); 
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				logger.warn("GenerationOrchestrator- createFactories - Problems loading the constructors of one of loaders. All the loaders are not initialized!"); 
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				logger.warn("GenerationOrchestrator- createFactories - Problems loading the constructors of one of loaders. All the loaders are not initialized!"); 
				e.printStackTrace();
			} catch (InstantiationException e) {
				logger.warn("GenerationOrchestrator- createFactories - Problems loading the constructors of one of loaders. All the loaders are not initialized!"); 
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				logger.warn("GenerationOrchestrator- createFactories - Problems loading the constructors of one of loaders. All the loaders are not initialized!"); 
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				logger.warn("GenerationOrchestrator- createFactories - Problems loading the constructors of one of loaders. All the loaders are not initialized!"); 
				e.printStackTrace();
			} 
		
		
		
	}
	
	/**
	 * Creates a loader with a given class name 
	 * @param key The model type related to the loader
	 * @param className The class name to instance the loader
	 * @throws ClassNotFoundException Problems loading a class
	 * @throws NoSuchMethodException Problems with the constructor
	 * @throws SecurityException Problems creating the object
	 * @throws InstantiationException Problems creating the object
	 * @throws IllegalAccessException Problems creating the object
	 * @throws IllegalArgumentException Problems creating the object
	 * @throws InvocationTargetException Problems creating the object
	 */
	protected void createAndAddLoader(String key, String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		logger.debug("GenerationOrchestrator - createAndAddLoader - Creating factory "+className); 
		
		Class clazz= Class.forName(className); 
		
		Constructor defaultConstructor= clazz.getConstructor(new Class[0]);
		
		logger.debug("GenerationOrchestrator - createAndAddLoader - Creating factory "+className+" "+defaultConstructor.getParameterTypes().length ); 
		
		IProcessorFactory loader= (IProcessorFactory) defaultConstructor.newInstance(new Object[0]); 
		
		loaders.put(key, loader); 
	}
	
	/**
	 * Prepares an input stream for loading factories 
	 * @return The input stream with information about factories
	 */
	protected InputStream selectExistingFactoryFile()
	{
		File factoryFile= new File(FACTORIES_PROPERTIES_FILE); 
		
		
		InputStream is= null; 
		
		if(!factoryFile.isFile())
		{	
			is= GenerationOrchestrator.class.getClass().getResourceAsStream(JAR_FACTORIES_PROPERTIES_FILE);
			if(is==null)
				is= GenerationOrchestrator.class.getClassLoader().getResourceAsStream(WAR_FACTORIES_PROPERTIES_FILE);
		}	
		else
		{	
			try {
				is= new FileInputStream(factoryFile);
			} catch (FileNotFoundException e) {
				logger.error("GenerationOrchestrator- selectExistingFactoryFile - Problems loading the factories file!"); 
				e.printStackTrace();
			} 	
		}
			
		return is;
	}
	
	/**
	 * Prepares a input stream for loading estimator factories
	 * @return The input stream for loading factories
	 */
	protected InputStream selectExistingEstimatorFactoryFile()
	{
		File estimatorsFile= new File(ESTIMATOR_FACTORIES_PROPERTIES_FILE); 
		
		InputStream is= null; 

		
		if(!estimatorsFile.isFile())
			//estimatorsFile= new File(ESTIMATOR_FACTORIES_PROPERTIES_FILE_ALT); 
			is= FileTool.getInputStreamFromFileName(WAR_ESTIMATOR_FACTORIES_PROPERTIES_FILE); 
			//estimatorsFile= new File(getClass().getResource(WAR_ESTIMATOR_FACTORIES_PROPERTIES_FILE).getPath());
		else
			try {
				is= new FileInputStream(estimatorsFile);
			} catch (FileNotFoundException e) {
				logger.error("GenerationOrchestrator- selectExistingEstimatorFactoryFile - Problems loading the estimators file!"); 
				e.printStackTrace();
			} 
		
		
		
		return is; 
	}
	
	/**
	 * Prepares a input stream for loading estimator factories
	 * @return The input stream for loading factories
	 */
	protected InputStream selectExistingCreatorFile()
	{
		File estimatorsFile= new File(CREATORS_PROPERTIES_FILE); 
		
		InputStream is= null; 

		
		if(!estimatorsFile.isFile())
			is= FileTool.getInputStreamFromFileName(WAR_CREATORS_PROPERTIES_FILE); 

		else
			try {
				is= new FileInputStream(estimatorsFile);
			} catch (FileNotFoundException e) {
				logger.error("GenerationOrchestrator- selectExistingCreatorFile - Problems loading the creators file!"); 
				e.printStackTrace();
			} 
		
		
		
		return is; 
	}
	
	/**
	 * Lunch the CP Genetor
	 * @param args The CDO id of the model
	 */
	public static void main(String[] args) 
	{	
		if(args.length==2 && !args[0].trim().equals(""))
		{	
			

				try{		        	
		        	GenerationOrchestrator go= new GenerationOrchestrator();
		        	
		        	PaaSagePropertyManager.getInstance().addCPGeneratorProperty(Constants.FILE_NAME_SENDER_PROPERTY_NAME, args[1]);
		        					
					
					List<ModelFileInfo> modelInfos=  null; 
						
	
					ModelFileInfo mfi= new ModelFileInfo(args[0].trim(), "camel");
					
					modelInfos= new ArrayList<ModelFileInfo>(); 
					
					modelInfos.add(mfi); 
						
					String paasageConfigID= go.generateCPModel(modelInfos); 
				}	
				finally
				{
					
					System.exit(0);
				}
				
				
			
		}
		else
		{	
			GenerationOrchestrator.logger.error("GenerationOrchestrator- Main- You have to specifiy a valid resouce in CDO containing the Camel Model and a Output file!"); 
			System.exit(1);
		}	
		
	}
	
	/**
	 * Selects an existing directory file
	 * @return The existing temp directory
	 */
	protected static File selectExistingTempDir()
	{
		File tempDir= new File(Constants.TEMP_DIR); 
		
		if(!tempDir.isDirectory())
			try {
				FileTool.createDirectory(tempDir.getAbsolutePath());
			} 
			catch (IOException e) 
			{
				GenerationOrchestrator.logger.error("GenerationOrchestrator- Main- Problems creating the temp dir!"); 
				e.printStackTrace();
			} 
		
		
		return tempDir; 
	}
	
	/**
	 * Verifies if a given file is a zip file by using its extension
	 * @param file The file to verify
	 * @return True if extension of the file is zip
	 */
	protected static boolean isZipFile(File file)
	{
		return file.getName().endsWith(".zip"); 
	}
	
	/**
	 * Searches in a list a processor with type CamelModelProcessor
	 * @param processors The list of processors
	 * @return The camel model processor or null if it does not exist in the list
	 */
	protected CamelModelProcessor getCamelModelProcessorFromList(List<ModelProcessor> processors)
	{
		CamelModelProcessor cmp= null; 
		
		for(int i=0; i<processors.size() && cmp==null; i++)
		{
			ModelProcessor processor= processors.get(i); 
			if(processor instanceof CamelModelProcessor)
				cmp= (CamelModelProcessor) processor; 
				
		}
		
		return cmp; 
		
		
	}
}
