/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.inria.paasage.profiler.cp.generator.model.saloon.price.test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import eu.passage.upperware.commons.model.tools.ModelTool;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.paasage.camel.CamelPackage;
import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.camel.type.TypePackage;
import fr.inria.paasage.saloon.camel.mapping.MappingPackage;
import fr.inria.paasage.saloon.camel.ontology.OntologyPackage;
import fr.inria.paasage.saloon.price.model.lib.EstimatorsManager;
import fr.inria.paasage.saloon.price.model.tools.Constants;

public class ElasticHostsEstimatorTests 
{
	
	protected static EstimatorsManager pc; 
	
	protected static ProviderModel configuration; 
	
	
	protected static String ELASTICHOSTS_EXAMPLES_PATH="."+File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"examples"+File.separator+"elasticHosts"+File.separator; 
	
	
	@BeforeClass
	public static void prepareTests()
	{
		//Saloon models
		OntologyPackage.eINSTANCE.eClass();
		CamelPackage.eINSTANCE.eClass();

		TypePackage.eINSTANCE.eClass();
		MappingPackage.eINSTANCE.eClass();
		
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl()); 
		
		
		
		try {
			pc= new EstimatorsManager(new FileInputStream(new File("."+Constants.CONFIG_FILES_DEFAULT_PATH_TEST, "cloudPricingTest.txt")));
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} 
	}
	
	
	protected static void loadConfiguration(String filePath)
	{
		File amazonFMFile= new File(filePath); 
		
		Resource r= ModelTool.loadModel(amazonFMFile);
		
		configuration= (ProviderModel) r.getContents().get(0); 
	}
	
	/**
	 * UK-10 VMs
	 * 20000 CPU-cores
	 * 32768 Memory 
	 * 1862 Storage
	 */
	//@Test
	public void estimatePrice1()
	{
		loadConfiguration(ELASTICHOSTS_EXAMPLES_PATH+"scenario1"+File.separator+"elastichosts.xmi"); 
		double priceHour= pc.estimatePricePerHour(configuration); 
		
		double priceHourExpected= ((0.029*20)+(32768*0.019/512)+(1862*0.012)/60)*10; 
		
		assertEquals("The price per hour is wrong- E1!", priceHourExpected, priceHour,0); 
		
		double priceMonth= pc.estimatePricePerMonth(configuration); 
		
		double priceMonthExpected= ((10.48*20)+(32768*6.99/512)+(1862*0.072))*10;  
		
		assertEquals("The price per month is wrong!", priceMonthExpected, priceMonth,0); 
		
		double priceYear= pc.estimatePricePerYear(configuration); 
		
		double priceYearExpected= ((104.85*20)+(32768*69.9/512)+(1862*0.73))*10;  
		
		assertEquals("The price per year is wrong!", priceYearExpected, priceYear,0); 
	}
	
	/**
	 * US-1 VM
	 * 20000 CPU-cores
	 * 32768 Memory 
	 * 1862 Storage
	 */
	//@Test
	public void estimatePrice2()
	{
		loadConfiguration(ELASTICHOSTS_EXAMPLES_PATH+"scenario2"+File.separator+"elastichosts.xmi"); 
		double priceHour= pc.estimatePricePerHour(configuration); 
		
		double priceHourExpected= ((0.026*20)+(32768*0.018/512)+(1862*0.007/36)); 
		
		assertEquals("The price per hour is wrong!", priceHourExpected, priceHour,0); 
		
		double priceMonth= pc.estimatePricePerMonth(configuration); 
		
		double priceMonthExpected= ((9.45*20)+(32768*6.56/512)+(1862*0.073)); 
		
		assertEquals("The price per month is wrong- E2!", priceMonthExpected, priceMonth,0.01); 
		
		double priceYear= pc.estimatePricePerYear(configuration); 
		
		double priceYearExpected= ((94.47*20)+(32768*65.61/512)+(1862*0.73)); 
		
		assertEquals("The price per year is wrong!", priceYearExpected, priceYear,0); 
	}
	
	/**
	 * Canada-5 VMs
	 * 1000 CPU-cores
	 * 512 Memory 
	 * 1 Storage
	 */
	//@Test
	public void estimatePrice3()
	{
		loadConfiguration(ELASTICHOSTS_EXAMPLES_PATH+"scenario3"+File.separator+"elastichosts.xmi"); 
		double priceHour= pc.estimatePricePerHour(configuration); 
		
		double priceHourExpected= ((0.026)+(0.018)+(0.007/36))*5;
		
		assertEquals("The price per hour is wrong!", priceHourExpected, priceHour,0.01); 
		
		double priceMonth= pc.estimatePricePerMonth(configuration); 
		
		double priceMonthExpected= ((9.45)+(6.56)+(0.073/60))*5; 
		
		assertEquals("The price per month is wrong!", priceMonthExpected, priceMonth,0.01); 
		
		double priceYear= pc.estimatePricePerYear(configuration); 
		
		double priceYearExpected= ((94.47)+(65.61)+(0.73))*5; 
		
		assertEquals("The price per year is wrong!", priceYearExpected, priceYear,5); 
	}

}
