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
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import org.apache.log4j.Logger;

import eu.paasage.camel.provider.Alternative;
import eu.paasage.camel.provider.Attribute;
import eu.paasage.camel.provider.Feature;
import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.camel.type.EnumerateValue;
import fr.inria.paasage.saloon.price.api.IProviderPriceEstimator;
import fr.inria.paasage.saloon.price.model.tools.ProviderModelTool;

public class AmazonEC2PriceEstimator implements IProviderPriceEstimator 
{
	
	public static Logger logger= EstimatorsManager.logger;
	
	Map<String, Map<String,Map<String, Double>>> locationMap;
	
	
	public AmazonEC2PriceEstimator()
	{
		locationMap= new Hashtable<String, Map<String,Map<String,Double>>>(); 
	}

	public double estimatePrice(ProviderModel fm) 
	{
		double price= 0; 
		Feature vm= ProviderModelTool.getFeatureByName(fm, ProviderModelTool.VIRTUAL_MACHINE_FEATURE); 
		
		if(vm!=null)
		{
			price=computeVmsPrice(vm, fm); 
		}
		
		return price;
	}
	
	protected double computeVmsPrice(Feature vm, ProviderModel fm)
	{
		logger.debug("AmazonEC2PriceEstimator - computeVmsPrice- Computing the price... ");
		double price= 0; 
		
		Alternative locationGroup= (Alternative) ProviderModelTool.getFeatureByName(fm.getRootFeature(), ProviderModelTool.LOCATION_FEATURE);
		
		if(locationGroup!=null)
		{
			Feature location=ProviderModelTool.getSelectedFeatureFromList(locationGroup.getVariants()); 
			
			//logger.info("AmazonEC2PriceEstimator - computeVmsPrice- Location retrieved: "+location.getName());
			
			if(location!=null)
			{	
				//location.get
				Map<String,Map<String, Double>> sizeMap= locationMap.get(location.getName()); 
				
				if(sizeMap!=null)
				{
					Attribute vmSizeAtt= ProviderModelTool.getAttributeByName(vm, ProviderModelTool.VIRTUAL_MACHINE_SIZE_ATTRIBUTE); 
					String vmSize= ((EnumerateValue) vmSizeAtt.getValue()).getName();  
					
					if(vmSize!=null)
					{
						Map<String, Double> priceMap= sizeMap.get(vmSize); 
						
						if(priceMap!=null)
						{
							Attribute osAtt= ProviderModelTool.getAttributeByName(vm, ProviderModelTool.VIRTUAL_MACHINE_OS_ATTRIBUTE); 
							String os= ((EnumerateValue) osAtt.getValue()).getName();
							
							logger.debug("AmazonEC2PriceEstimator - computeVmsPrice- Retrieved os: "+os);
							
							if(os!=null)
							{
								Double vmPrice= priceMap.get(os);  
								
								logger.debug("AmazonEC2PriceEstimator - computeVmsPrice- Retrieved price: "+vmPrice);
								
								if(vmPrice!=null)
								{
									price= vmPrice.doubleValue()*vm.getFeatureCardinality().getValue(); 
									logger.debug("AmazonEC2PriceEstimator - computeVmsPrice- Computed price: "+price);
								}
								else
									logger.error("AmazonEC2PriceEstimator - computeVmsPrice- The price for the OS "+os+" does not exist. The price will be not computed!");
								
							}
							else
								logger.error("AmazonEC2PriceEstimator - computeVmsPrice- The OS does not exist. The price will be not computed!"); 
							
						}
						else
							logger.error("AmazonEC2PriceEstimator - computeVmsPrice- The prices for the VM size "+ vmSize +" does not exist!"); 
						
						
						
					}
					else
						logger.error("AmazonEC2PriceEstimator - computeVmsPrice- The VM Size value does not exist. The price will be not computed!"); 
				}
				else
					logger.error("AmazonEC2PriceEstimator - computeVmsPrice- The location "+location.getName()+" does not exist. The price will be not computed!"); 
				
			}
			else
				logger.error("AmazonEC2PriceEstimator - computeVmsPrice- The location is not selected. The price will be not computed!"); 
		}
		else
			logger.error("AmazonEC2PriceEstimator - computeVmsPrice- The location feature group does not exist. The price will be not computed!"); 
		
		return price; 
		
	}
	
	
	/** 
	 * Format of the input: 
	 * comments
	 * LocationName;number_of_profiles
	 * vmSize;OS;price
	 * # Indicates comments
	 * Example:	
	 	US East;12  
	 	#Resources- VM in the case of amazon- Size;OS;price_per_hour_euros
	 	M;Ubuntu;0.084
		L;Ubuntu;0.17
		X;Ubuntu;0.33
		XXL;Ubuntu;0.67
		M;WindowsServer;0.13
		L;WindowsServer;0.26
		X;WindowsServer;0.52
		XXL;WindowsServer;1.04
		M;RedHatEnterpriseLinux;0.13
		L;RedHatEnterpriseLinux;0.21
		X;RedHatEnterpriseLinux;0.38
		XXL;RedHatEnterpriseLinux;0.76
	 **/
	public void loadLocationRates(BufferedReader br)
	{
		try 
		{
			//String location = br.readLine().trim();
			
			Map<String,Map<String, Double>> locationSizes= new Hashtable<String, Map<String,Double>>(); 
			
			String line= br.readLine(); 
			
			
			logger.debug("AmazonEC2Price - loadLocationRates - processing line "+line); 
			
			String[] infos= line.split(ProviderModelTool.LINE_INFOS_SEPARATOR); 
			
			if(infos.length==2)
			{
				String location= infos[0]; 
				int numOfProfiles= Integer.parseInt(infos[1]);
				
				
				for(int i=0; i<numOfProfiles;i++)
				{
					line= br.readLine(); 
					loadVMSizeRates(line, locationSizes); 
					
				}
				
				locationMap.put(location, locationSizes); 
				logger.debug("AmazonEC2Price - loadLocationRates - rates for "+location+" location added!"); 
			}
			else
				logger.error("AmazonEC2Price - loadLocationRates - The line "+line+" does not have the correct format. The prices will be not loaded!"); 
			
			
		} 
		catch (IOException e) 
		{
			
			e.printStackTrace();
		} 
		
	}
	
	protected void loadVMSizeRates(String infos, Map<String,Map<String,Double>> locationPrices)
	{
		String[] rateInfos= infos.split(ProviderModelTool.LINE_INFOS_SEPARATOR); 
		
		if(rateInfos.length==3)
		{
			//<vmSize>;<OS>;<price>
			String size= rateInfos[0]; 
			String os= rateInfos[1]; 
			String priceString= rateInfos[2]; 
			Double price= Double.parseDouble(priceString); 
			
			Map<String, Double> osPrices= locationPrices.get(size); 
			if(osPrices==null)
			{
				osPrices= new Hashtable<String, Double>(); 
				locationPrices.put(size, osPrices); 
				
				
			}
			logger.debug("AmazonEC2PriceEstimator - loadVMSizeRates- prices added: "+infos); 
			
			osPrices.put(os, price); 
		}
		else
			logger.error("AmazonEC2PriceEstimator - loadVMSizeRates- The line "+infos+"does not have the correct format!"); 
		
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
