/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.inria.paasage.saloon.price.model.lib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import eu.paasage.camel.CamelPackage;
import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.camel.type.TypePackage;
import fr.inria.paasage.saloon.camel.mapping.MappingPackage;
import fr.inria.paasage.saloon.camel.ontology.OntologyPackage;
import fr.inria.paasage.saloon.price.api.IProviderPriceEstimator;
import fr.inria.paasage.saloon.price.model.tools.Constants;
import fr.inria.paasage.saloon.price.model.tools.ProviderModelTool;
import fr.inria.paasage.saloon.price.model.tools.ModelTool;

public class EstimatorsManager 
{
	
	protected Map<String, IProviderPriceEstimator> estimators; 
	
	public static Logger logger= Logger.getLogger("saloon-estimation-tool-log");
	
/*	static{
		configLog(); 
	}*/
	
	public EstimatorsManager()
	{
		try {
			loadPriceEstimators(new FileInputStream(new File("."+Constants.CONFIG_FILES_DEFAULT_PATH+"cloudPricing.txt")));
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} 
	}
	
	public EstimatorsManager(InputStream cloudRatesFile)
	{
		loadPriceEstimators(cloudRatesFile); 
	}
	
	protected static void configLog()
	{
		BasicConfigurator.configure();
		File pFile= new File("src"+File.separator+"main"+File.separator+"resources"+File.separator+"config"+File.separator+"log4j.properties");
		
		if(pFile.isFile())
			PropertyConfigurator.configure(pFile.getAbsolutePath());
		//logger.setLevel(Level.DEBUG);
	}
	
	protected void loadPriceEstimators(InputStream cloudRatesFile)
	{
		estimators= new Hashtable<String, IProviderPriceEstimator>(); 
		
		if(cloudRatesFile!=null)
		{
			try 
			{
				BufferedReader br= new BufferedReader(new InputStreamReader(cloudRatesFile));
				
				String line= br.readLine(); 
				while(line!=null)
				{	logger.debug("EstimatorsManager - loadPriceEstimators - processing line "+line); 
				
					//Provider;class-computing-pricing;number-of-locations
					String[] infos= line.split(ProviderModelTool.LINE_INFOS_SEPARATOR); 
					
					logger.debug("EstimatorsManager - loadPriceEstimators - infos length "+infos.length); 
					
					if(infos.length==3)
					{
						String provider= infos[0]; 
						String className= infos[1]; 
						int numberOfLocations= Integer.parseInt(infos[2]); 
						
						Class clazz= Class.forName(className); 
						
						Constructor defaultConstructor= clazz.getConstructor(new Class[0]);
						
						IProviderPriceEstimator estimator= (IProviderPriceEstimator) defaultConstructor.newInstance(new Object[0]);
						
						for(int i=0; i<numberOfLocations;i++)
							estimator.loadLocationRates(br); 
						
						estimators.put(provider, estimator); 
						line= br.readLine(); 
					}
					else
					{	
						logger.error("EstimatorsManager - loadPriceEstimators - The line "+line+" does not have the correct format. The Estimators will be not loaded!");
						line=null; 
					}	
				}	
				br.close(); 
			} 
			catch (FileNotFoundException e) 
			{
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
			} catch (SecurityException e) {
				
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				
				e.printStackTrace();
			} catch (InstantiationException e) {
				
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				
				e.printStackTrace();
			} 
		}
		else
			logger.error("EstimatorsManager - loadPriceEstimators - The file with rates does not exist. The Estimators will be not loaded!"); 
		
	}
	

	
	public double estimatePricePerHour(ProviderModel configuration)
	{
		double price= 0; 
		
		IProviderPriceEstimator estimator= estimators.get(configuration.getRootFeature().getName()); 
		
		if(estimator!=null)
			price= estimator.estimatePricePerHour(configuration); 
		else
			logger.error("EstimatorsManager - estimatePricePerHour - There is not estimator for the "+configuration.getRootFeature().getName()+" provider!"); 
		
		return price; 
		
	}
	
	public double estimatePricePerMonth(ProviderModel configuration)
	{
		double price= 0; 
		
		IProviderPriceEstimator estimator= estimators.get(configuration.getRootFeature().getName()); 
		
		if(estimator!=null)
			price= estimator.estimatePricePerMonth(configuration); 
		else
			logger.error("EstimatorsManager - estimatePricePerMonth - There is not estimator for the "+configuration.getRootFeature().getName()+" provider!"); 
		
		return price; 
		
	}
	
	public double estimatePricePerYear(ProviderModel configuration)
	{
		double price= 0; 
		
		IProviderPriceEstimator estimator= estimators.get(configuration.getRootFeature().getName()); 
		
		if(estimator!=null)
			price= estimator.estimatePricePerYear(configuration); 
		else
			logger.error("EstimatorsManager - estimatePricePerYear - There is not estimator for the "+configuration.getRootFeature().getName()+" provider!"); 
		
		return price; 
		
	}
	
	public double estimatePrice(ProviderModel configuration)
	{
		double price= 0; 
		
		IProviderPriceEstimator estimator= estimators.get(configuration.getRootFeature().getName()); 
		
		if(estimator!=null)
			price= estimator.estimatePrice(configuration); 
		else
			logger.error("EstimatorsManager - estimatePricePerYear - There is not estimator for the "+configuration.getRootFeature().getName()+" provider!"); 
		
		return price; 
		
	}

	public static void main(String[] args) 
	{
				
		//Saloon models
		OntologyPackage.eINSTANCE.eClass();
		CamelPackage.eINSTANCE.eClass();

		TypePackage.eINSTANCE.eClass();
		MappingPackage.eINSTANCE.eClass();
		
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl()); 
		
		File amazonFMFile= new File("."+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"examples"+File.separator+"scenario3"+File.separator+"amazonEC2.xmi"); 
		
		Resource r= ModelTool.loadModel(amazonFMFile); 
		
		ProviderModel fm= (ProviderModel) r.getContents().get(0); 
		
		EstimatorsManager pc= new EstimatorsManager(); 
		
		double pricePerHour= pc.estimatePricePerHour(fm); 
		
		logger.debug("------- PRICE HOUR "+pricePerHour); 

		double pricePerMonth= pc.estimatePricePerMonth(fm); 
		
		logger.debug("------- PRICE MONTH "+pricePerMonth); 
		
		double pricePerYear= pc.estimatePricePerYear(fm);
		
		logger.debug("------- PRICE YEAR "+pricePerYear); 
		
		
		File elastichostsFMFile= new File("."+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"examples"+File.separator+"scenario3"+File.separator+"elastichosts.xmi"); 
		
		r= ModelTool.loadModel(elastichostsFMFile); 
		
		fm= (ProviderModel) r.getContents().get(0); 
		
		
		pricePerHour= pc.estimatePricePerHour(fm); 
		
		logger.debug("------- PRICE HOUR "+pricePerHour); 

		pricePerMonth= pc.estimatePricePerMonth(fm); 
		
		logger.debug("------- PRICE MONTH "+pricePerMonth); 
		
		pricePerYear= pc.estimatePricePerYear(fm);
		
		logger.debug("------- PRICE YEAR "+pricePerYear); 
		
		pricePerYear= pc.estimatePrice(fm); 
		
		logger.debug("------- PRICE YEAR "+pricePerYear); 
		
		
	}
}


