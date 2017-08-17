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

import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.camel.provider.ProviderPackage;
import fr.inria.paasage.saloon.price.model.lib.EstimatorsManager;
import fr.inria.paasage.saloon.price.model.tools.Constants;

public class GwdgEstimatorTests 
{
	protected static EstimatorsManager pc; 
	
	protected static ProviderModel configuration; 
	
	
	protected static String GWDG_EXAMPLES_PATH="."+File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"examples"+File.separator+"gwdg"+File.separator; 
	
	
	@BeforeClass
	public static void prepareTests()
	{
		//Saloon models
		//OntologyPackage.eINSTANCE.eClass();
		//CamelPackage.eINSTANCE.eClass();
		//TypePackage.eINSTANCE.eClass();
		ProviderPackage.eINSTANCE.eClass();
		
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl()); 
				
		try {
			pc= new EstimatorsManager(new FileInputStream(new File("."+Constants.CONFIG_FILES_DEFAULT_PATH_TEST, "cloudPricingTest.txt")));
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} 
	}
	
	
	protected void loadConfiguration(String filePath)
	{
		File gwdgFMFile= new File(filePath); 
		
		Resource r= ModelTool.loadModel(gwdgFMFile);
		
		configuration= (ProviderModel) r.getContents().get(0); 
	}
	
	/**
	 * VM M1.SMALL
	 */
	@Test
	public void estimatePrice1()
	{
		loadConfiguration(GWDG_EXAMPLES_PATH+"scenario1"+File.separator+"gwdgPM.xmi"); 
		double priceHour= pc.estimatePricePerHour(configuration); 
		
		double priceHourExpected= 0.090; 
		
		assertEquals("The price per hour is wrong!", priceHourExpected, priceHour,0); 
		
		double priceMonth= pc.estimatePricePerMonth(configuration); 
		
		double priceMonthExpected= priceHourExpected*24*30; 
		
		assertEquals("The price per month is wrong!", priceMonthExpected, priceMonth,0); 
		
		double priceYear= pc.estimatePricePerYear(configuration); 
		
		double priceYearExpected= priceMonthExpected*12; 
		
		assertEquals("The price per year is wrong!", priceYearExpected, priceYear,0); 
	}
	
	/**
	 * VM M2.MEDIUM
	 */
	@Test
	public void estimatePrice2()
	{
		loadConfiguration(GWDG_EXAMPLES_PATH+"scenario2"+File.separator+"gwdgPM.xmi"); 
		double priceHour= pc.estimatePricePerHour(configuration); 
		
		double priceHourExpected= 0.140*5; 
		
		assertEquals("The price per hour is wrong!", priceHourExpected, priceHour,0); 
		
		double priceMonth= pc.estimatePricePerMonth(configuration); 
		
		double priceMonthExpected= priceHourExpected*24*30; 
		
		assertEquals("The price per month is wrong!", priceMonthExpected, priceMonth,0); 
		
		double priceYear= pc.estimatePricePerYear(configuration); 
		
		double priceYearExpected= priceMonthExpected*12; 
		
		assertEquals("The price per year is wrong!", priceYearExpected, priceYear,0); 
	}
	
	/**
	 * VM C1.XXLARGE
	 */
	@Test
	public void estimatePrice3()
	{
		loadConfiguration(GWDG_EXAMPLES_PATH+"scenario3"+File.separator+"gwdgPM.xmi"); 
		double priceHour= pc.estimatePricePerHour(configuration); 
		
		double priceHourExpected= 0.175*8; 
		
		assertEquals("The price per hour is wrong!", priceHourExpected, priceHour,0); 
		
		double priceMonth= pc.estimatePricePerMonth(configuration); 
		
		double priceMonthExpected= priceHourExpected*24*30; 
		
		assertEquals("The price per month is wrong!", priceMonthExpected, priceMonth,0); 
		
		double priceYear= pc.estimatePricePerYear(configuration); 
		
		double priceYearExpected= priceMonthExpected*12; 
		
		assertEquals("The price per year is wrong!", priceYearExpected, priceYear,0); 
	}
}
