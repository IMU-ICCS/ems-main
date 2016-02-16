/**
 * Saloon PaasSage
 *  Copyright (C) 2014 INRIA, Universite  Lille 1
 *
 * Contacts: daniel.romero@inria.fr & clement.quinton@inria.fr & laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 03/2014
 */

package fr.inria.paasage.saloon.price.model.lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import org.apache.log4j.Logger;

import eu.paasage.camel.provider.Attribute;
import eu.paasage.camel.provider.Feature;
import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.camel.type.EnumerateValue;
import fr.inria.paasage.saloon.price.api.IProviderPriceEstimator;
import fr.inria.paasage.saloon.price.model.tools.Constants;
import fr.inria.paasage.saloon.price.model.tools.ProviderModelTool;

public class OmistackPriceEstimator implements IProviderPriceEstimator 
{
	
	public static Logger logger= EstimatorsManager.logger;
	
	Map<String,Double> vmsMap;
	
	
	public OmistackPriceEstimator()
	{
		vmsMap= new Hashtable<String, Double>(); 
	}

	public double estimatePrice(ProviderModel fm) 
	{
		double price= 0; 
		Feature vm= ProviderModelTool.getFeatureByName(fm, ProviderModelTool.VIRTUAL_MACHINE_FEATURE); 
		
		if(vm==null)
		{
			vm= ProviderModelTool.getFeatureByName(fm, ProviderModelTool.VIRTUAL_MACHINE_FEATURE_ALT); 
		}
		
		if(vm!=null)
		{
			price=computeVmsPrice(vm); 
		}
		
		return price;
	}
	
	protected double computeVmsPrice(Feature vm)
	{
		logger.debug("OmistackPriceEstimator - computeVmsPrice- Computing the price... ");
		double price= 0; 
		double rate = Constants.DEFAULT_PRICE_VM; 
		
		Attribute vmType= ProviderModelTool.getAttributeByName(vm, "vmType"); 
		
		if(vmType==null)
			vmType= ProviderModelTool.getAttributeByName(vm, "VMType"); 

		
		logger.info("OmistackPriceEstimator - computeVmsPrice- Computing the price for vm: "+((EnumerateValue)vmType.getValue()).getName());
		
		String n1 = ((EnumerateValue)vmType.getValue()).getName();
		
		if(vmsMap.get(n1)!=null)
		{
			rate= vmsMap.get(n1); 
		}
		else
			logger.warn("OmistackPriceEstimator - computeVmsPrice- The rate for vm: "+((EnumerateValue)vmType.getValue()).getName()+ "cannot be found. The default value will be used");

		price =rate*vm.getFeatureCardinality().getValue(); 

		return price; 
		
	}
	
	
	/** 
	 * Format of the input: 
	 * comments
	 * vmSize;price
	 * # Indicates comments
	 * Example:	
		LOWEST;0.084
		LOWER;0.094
		LOW;0.104
		MEDIUM;0.114
		HIGH;0.124
		HIGHER;0,134
		HIGHEST;0,144
	 **/
	public void loadLocationRates(BufferedReader br)
	{
		try 
		{
			
			String line= br.readLine(); 
			
			
			logger.debug("OmistackPriceEstimationTool - loadLocationRates - processing line "+line);
			
			if(!line.startsWith("#"))
			{
				String[] infos= line.split(ProviderModelTool.LINE_INFOS_SEPARATOR); 
				
				if(infos.length==2)
				{
					vmsMap.put(infos[0], Double.parseDouble(infos[1])); 
					
				}
				else
					logger.error("OmistackPriceEstimationTool - loadLocationRates - The line "+line+" does not have the correct format. The price will be not loaded!"); 
			}
			
			
			
			
		} 
		catch (IOException e) 
		{
			
			e.printStackTrace();
		} 
		
	}
	
	

	public double estimatePricePerHour(ProviderModel fm) 
	{
		
		return estimatePrice(fm);
	}

	public double estimatePricePerMonth(ProviderModel fm) 
	{
		
		return estimatePrice(fm)*24*30;
	}

	public double estimatePricePerYear(ProviderModel fm) 
	{
		return estimatePricePerMonth(fm)*12;
	}

}
