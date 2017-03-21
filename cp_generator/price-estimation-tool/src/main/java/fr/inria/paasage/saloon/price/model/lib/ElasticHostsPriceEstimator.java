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
import eu.paasage.camel.provider.Exclusive;
import eu.paasage.camel.provider.Feature;
import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.camel.type.IntegerValue;
import fr.inria.paasage.saloon.price.api.IProviderPriceEstimator;
import fr.inria.paasage.saloon.price.model.tools.ProviderModelTool;

public class ElasticHostsPriceEstimator implements IProviderPriceEstimator 
{

	public static Logger logger= EstimatorsManager.logger;
	
	protected Map<String, Map<String, Map<String,ResourcePrice>>> locationsMap; 
	
	
	public ElasticHostsPriceEstimator()
	{
		locationsMap= new Hashtable<String, Map<String,Map<String,ResourcePrice>>>(); 
	}
	
	
	
	public void loadLocationRates(BufferedReader br) 
	{
		try {
			String line= br.readLine();
			
			String[] infos= line.split(ProviderModelTool.LINE_INFOS_SEPARATOR); 
			
			if(infos.length==2)
			{
				String location= infos[0]; 
				int numberOfResources= Integer.parseInt(infos[1]); 
				
				Map<String, Map<String,ResourcePrice>> resourcesMap= new Hashtable<String, Map<String,ResourcePrice>>(); 
				
				for(int i=0;i<numberOfResources; i++)
				{
					loadResourceRates(br, resourcesMap); 
				}
				
				locationsMap.put(location, resourcesMap); 
				
			}
			else
				logger.error("ElasticHostsPriceEstimator - loadLocationRates - The line "+line+" does not have the correct format!"); 
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		} 
		
	}
	
	
	protected void loadResourceRates(BufferedReader br, Map<String, Map<String,ResourcePrice>> resourcesMap) throws IOException
	{
		//<resource>;<pricingModel>;<quantity>;<price>
		String line= br.readLine(); 
		
		String[] infos= line.split(ProviderModelTool.LINE_INFOS_SEPARATOR); 
		
		
		if(infos.length==4)
		{
			String resource= infos[0]; 
			String pricingModel= infos[1]; 
			int quantity= Integer.parseInt(infos[2]); 
			double price= Double.parseDouble(infos[3]); 
			
			Map<String,ResourcePrice> pricingModelsMap= resourcesMap.get(resource); 
			
			if(pricingModelsMap==null)
			{
				pricingModelsMap= new Hashtable<String, ResourcePrice>(); 
				resourcesMap.put(resource, pricingModelsMap); 
			}
			
			pricingModelsMap.put(pricingModel, new ResourcePrice(quantity, price)); 
			
			logger.debug("ElasticHostsPriceEstimator - loadResourceRates - Rate "+line+" added!"); 
			
		}
		else
			logger.error("ElasticHostsPriceEstimator - loadResourceRates - The line "+line+" does not have the correct format!"); 
		
		
	}

	public double estimatePrice(ProviderModel fm) 
	{
		double price= 0; 
		
		Exclusive pricingModelFeature= (Exclusive) ProviderModelTool.getFeatureByName(fm, ProviderModelTool.PRICING_MODEL_FEATURE); 
		
		logger.debug("ElasticHostsPriceEstimator - estimatePrice - pricingModelFeature name "+pricingModelFeature.getName()); 
		
		Feature selectedFeature= ProviderModelTool.getSelectedFeatureFromList(pricingModelFeature.getVariants()); 
		
		logger.debug("ElasticHostsPriceEstimator - estimatePrice - Selected Price Model "+selectedFeature); 
		
		String pricingModel= ProviderModelTool.HOUR;
		
		if(selectedFeature!=null)
			pricingModel= ProviderModelTool.getPricingModelFromFeatureName(selectedFeature.getName()); 
		
		price=computeResourcesPrice(fm, pricingModel); 

		
		return price;
	}
	
	public double computeResourcesPrice(ProviderModel fm, String pricingModel)
	{
		logger.debug("ElasticHostsPriceEstimator - computeResourcesPrice- Computing the price... ");
		double price= 0; 
		
		Feature vm= ProviderModelTool.getFeatureByName(fm, ProviderModelTool.VIRTUAL_MACHINE_FEATURE); 
		
		if(vm!=null)
		{
			Alternative locationGroup= (Alternative) ProviderModelTool.getFeatureByName(fm.getRootFeature(), ProviderModelTool.LOCATION_FEATURE);
			
			
			if(locationGroup!=null)
			{
				Feature location=ProviderModelTool.getSelectedFeatureFromList(locationGroup.getVariants()); 
				
				if(location!=null)
				{	
					//location.get
					Map<String, Map<String,ResourcePrice>> resourcesMap= locationsMap.get(location.getName()); 
					
					if(resourcesMap!=null)
					{
						Map<String,ResourcePrice> pricingModelsRAMMap= resourcesMap.get("memory");
						
						ResourcePrice ramPrice= pricingModelsRAMMap.get(pricingModel); 
						
						Map<String,ResourcePrice> pricingModelsStorageMap= resourcesMap.get("storage");
						
						ResourcePrice storagePrice= pricingModelsStorageMap.get(pricingModel); 
						
						
						Map<String,ResourcePrice> pricingModelsCPUMap= resourcesMap.get("CPU");
						
						
						ResourcePrice cpuPrice= pricingModelsCPUMap.get(pricingModel); 
						
						
						Attribute memoryAtt= ProviderModelTool.getAttributeByName(vm, ProviderModelTool.VIRTUAL_MACHINE_MEMORY_ATTRIBUTE); 
						
						int memory= ((IntegerValue)(memoryAtt.getValue())).getValue(); 
						
						logger.debug("ElasticHostsPriceEstimator - computeResourcesPrice - Memory "+memory); 
						
						logger.debug("ElasticHostsPriceEstimator - computeResourcesPrice - Memory Price "+ramPrice.getResourcePriceForQuantity(memory)); 
						
						Attribute cpuAtt= ProviderModelTool.getAttributeByName(vm, ProviderModelTool.VIRTUAL_MACHINE_CPU_CORE_ATTRIBUTE); 
						
						int cpu= ((IntegerValue)(cpuAtt.getValue())).getValue(); 
						
						logger.debug("ElasticHostsPriceEstimator - computeResourcesPrice - CPU "+cpu); 
						
						logger.debug("ElasticHostsPriceEstimator - computeResourcesPrice - CPU Price "+cpuPrice.getResourcePriceForQuantity(memory)); 
						
						Attribute storageAtt= ProviderModelTool.getAttributeByName(vm, ProviderModelTool.VIRTUAL_MACHINE_STORAGE_ATTRIBUTE); 
						
						int storage= ((IntegerValue)(storageAtt.getValue())).getValue(); 
						
						logger.debug("ElasticHostsPriceEstimator - computeResourcesPrice - Storage "+storage); 
						
						logger.debug("ElasticHostsPriceEstimator - computeResourcesPrice - Storage Price "+storagePrice.getResourcePriceForQuantity(memory)); 
						
						price= (ramPrice.getResourcePriceForQuantity(memory)+storagePrice.getResourcePriceForQuantity(storage)+cpuPrice.getResourcePriceForQuantity(cpu))*vm.getFeatureCardinality().getValue(); 
						
						
						
					}
					else
						logger.error("ElasticHostsPriceEstimator - computeResourcesPrice- The location "+location.getName()+" does not exist. The price will be not computed!"); 
					
				}
				else
					logger.error("ElasticHostsPriceEstimator - computeResourcesPrice- The location is not selected. The price will be not computed!"); 
			}
			else
				logger.error("ElasticHostsPriceEstimator - computeResourcesPrice- The location feature group does not exist. The price will be not computed!"); 
		}
		else
			logger.error("ElasticHostsPriceEstimator - computeResourcesPrice- The virtual machine feature does not exist. The price will be not computed!");
		
		return price; 
	}

	public double estimatePricePerHour(ProviderModel fm) 
	{
		
		return computeResourcesPrice(fm, ProviderModelTool.HOUR);
	}

	public double estimatePricePerMonth(ProviderModel fm) 
	{
		
		return computeResourcesPrice(fm, ProviderModelTool.MONTH);
	}

	public double estimatePricePerYear(ProviderModel fm) 
	{
		
		return computeResourcesPrice(fm, ProviderModelTool.YEAR);
	}

}
