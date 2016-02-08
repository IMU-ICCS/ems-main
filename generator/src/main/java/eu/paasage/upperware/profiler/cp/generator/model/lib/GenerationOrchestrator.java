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

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.type.TypePackage;
import eu.paasage.mddb.cdo.client.CDOClient;
import eu.paasage.upperware.metamodel.application.ApplicationComponent;
import eu.paasage.upperware.metamodel.application.ApplicationFactory;
import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.types.TypesPackage;
import eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage;
import eu.paasage.upperware.profiler.cp.generator.db.api.IDatabaseProxy;
import eu.paasage.upperware.profiler.cp.generator.db.lib.CDODatabaseProxy;
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
	
	private static final String CREATORS_FILE_NAME="functionCreators.properties"; 
	
	private static final String CREATORS_PROPERTIES_FILE="src"+File.separator+"main"+File.separator+"resources"+File.separator+"config"+File.separator+CREATORS_FILE_NAME;
	
		
	private static final String ESTIMATOR_FACTORIES_PROPERTIES_FILE="src"+File.separator+"main"+File.separator+"resources"+File.separator+"config"+File.separator+"estimatorFactories.properties";
	
	private static final String WAR_ESTIMATOR_FACTORIES_PROPERTIES_FILE=Constants.WAR_CONFIG_PATH+"estimatorFactories.properties";
	
	private static final String WAR_LOG4G_PROPERTIES_FILE=Constants.WAR_CONFIG_PATH+"log4j.properties";
	
	private static final String LOG4G_PROPERTIES_FILE="src"+File.separator+"main"+File.separator+"resources"+File.separator+"config"+File.separator+"log4j.properties"; 
	
	private static final String JAR_LOG4G_PROPERTIES_FILE=Constants.JAR_CONFIG_PATH+"log4j.properties";
	
	private static final String WAR_CREATORS_PROPERTIES_FILE=Constants.WAR_CONFIG_PATH+CREATORS_FILE_NAME;
	
	

	
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
	 * The CP model derivator
	 */
	private CPModelDerivator derivator; 
	
	/*
	 * The Sender
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
	 * Generates the CP model by using the provided model path
	 * @param modelPath The path of the model
	 * @return The id or path of the generate CP Model. It is stored in CDO
	 */
	public String generateCPModel(String modelPath)
	{
		logger.info("************************************CP Generator Model To Solver************************************"); 
		
		CamelModelProcessor camelProcessor= createCamelModelProcessor(modelPath); 
		
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
				ConstraintProblem cp= derivator.derivateConstraintProblem(camelProcessor.getCamelModel(),pc, database); 
				//pc.getId();
				
				logger.debug("** Calling DatabseProxy ");
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
	 * Launch the CP Generator
	 * @param args The CDO id of the model
	 */
	public static void main(String[] args) 
	{	
		if(args.length==2 && !args[0].trim().equals(""))
		{	
			

				try{		        	
		        	GenerationOrchestrator go= new GenerationOrchestrator();
		        	
		        	PaaSagePropertyManager.getInstance().addCPGeneratorProperty(Constants.FILE_NAME_SENDER_PROPERTY_NAME, args[1]);
		        											
					go.generateCPModel(args[0].trim()); 
				}
				catch(Exception e)
				{
					System.out.println("Problems processing the model "+e.getMessage());
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
	 * Creates a CamelModelProcessor by loading a model from CDO
	 * @param modelPath The model path in CDO
	 * @return A CamelModelProcessor containing the model with path modelPath. If the model does not exist, the CamelModelProcessor is invalid.  
	 */
	protected CamelModelProcessor createCamelModelProcessor(String modelPath)
	{
		CamelModelProcessor modelProcessor= new CamelModelProcessor(null); 
		
		modelProcessor.setValid(false); 

		try{
			CamelModel camelModel= database.getCamelModel(modelPath); 
			
			modelProcessor= new CamelModelProcessor(camelModel); 
			modelProcessor.setValid(true);
			
		}
		catch(Exception e)
		{
			GenerationOrchestrator.logger.error("GenerationOrchestrator- createCamelModelProcessor- Problems loading the model with path: "+modelPath); 
		}
		
		
		return modelProcessor;
	}
}
