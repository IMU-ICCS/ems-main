/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.inria.paasage.saloon.price.model.tools;

import org.eclipse.emf.common.util.EList;

import eu.paasage.camel.provider.Attribute;
import eu.paasage.camel.provider.Feature;
import eu.paasage.camel.provider.ProviderModel;


public class ProviderModelTool 
{
	
	public static final String VIRTUAL_MACHINE_FEATURE= "Virtual Machine"; 
	
	public static final String VIRTUAL_MACHINE_FEATURE_ALT= "VM"; 
	
	public static final String PRICING_MODEL_FEATURE= "Pricing Model"; 
	
	public static final String LOCATION_FEATURE= "Location"; 
	
	public static final String VIRTUAL_MACHINE_TYPE= "VMType";

	public static final String VIRTUAL_MACHINE_OSVENDORTYPE= "OSVendorType";

	public static final String VIRTUAL_MACHINE_SIZE_ATTRIBUTE= "vmSize";
	
	public static final String VIRTUAL_MACHINE_OS_ATTRIBUTE= "vmOS";
	
	public static final String VIRTUAL_MACHINE_MEMORY_ATTRIBUTE= "vmMemorySize";
	
	public static final String VIRTUAL_MACHINE_STORAGE_ATTRIBUTE= "vmStorage";
	
	public static final String VIRTUAL_MACHINE_CPU_CORE_ATTRIBUTE= "vmCpuCoreMHz";
		
	public static final String LINE_INFOS_SEPARATOR= ";";
	
	public static final String HOUR="hour"; 
	
	public static final String MONTH="month";
	
	public static final String YEAR="year"; 
	
	public static final String PER_HOUR="Per Hour"; 
	
	public static final String PER_MONTH="Per Month";
	
	public static final String PER_YEAR="Per Year"; 
	
	public static Feature getFeatureByName(ProviderModel fm, String name)
	{
		
		return getFeatureByName(fm.getRootFeature(), name); 
	}
	
	public static Feature getFeatureByName(Feature f, String name)
	{
		if(f.getName().equals(name))
			return f; 
		else
		{
			for(Feature temp:f.getSubFeatures())
			{
				Feature found= getFeatureByName(temp, name); 
				
				if(found!=null)
					return found; 
			}
		}
		
		return null; 
	}
	
	public static Attribute getAttributeByName(Feature f, String attributeName)
	{
		for(Attribute att:f.getAttributes())
		{
			if(att.getName().equals(attributeName))
				return att; 
		}
		
		return null; 
	}
	
	
	public static Feature getSelectedFeatureFromList(EList<Feature> features)
	{
		
		for(Feature f: features)
		{
			if(f.getFeatureCardinality().getValue()>0)
				return f; 
		}
		
		return null; 
	}
	
	public static String getPricingModelFromFeatureName(String name)
	{
		String model= HOUR; 
		
		if(name.equals(PER_MONTH))
			model= MONTH; 
		else if (name.equals(PER_YEAR))
			model= YEAR; 
		
		return model; 
	}


}
