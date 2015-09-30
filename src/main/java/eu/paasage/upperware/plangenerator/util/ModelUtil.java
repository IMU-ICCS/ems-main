/*
 * Copyright (c) 2014-5 UK Science and Technology Facilities Council
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.plangenerator.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonValue;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.CamelPackage;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.VMRequirementSet;
import eu.paasage.camel.location.Location;
import eu.paasage.camel.requirement.ImageRequirement;
import eu.paasage.camel.requirement.LocationRequirement;
import eu.paasage.camel.requirement.OSOrImageRequirement;
import eu.paasage.camel.requirement.OSRequirement;
import eu.paasage.camel.type.BoolValue;
import eu.paasage.camel.type.DoublePrecisionValue;
import eu.paasage.camel.type.EnumerateValue;
import eu.paasage.camel.type.FloatsValue;
import eu.paasage.camel.type.IntegerValue;
import eu.paasage.camel.type.SingleValue;
import eu.paasage.camel.type.StringsValue;
import eu.paasage.upperware.plangenerator.exception.ModelUtilException;

/**
 * Utilities for working with {@link eu.paasage.camel.deployment.DeploymentModel} 
 * <p> 
 * Based on a prototype developed by INRIA, INSA Rennes. 
 * <p>
 * @author Shirley Crompton
 * org     UK Science and Technology Facilities Council
 */
public final class ModelUtil {
	/** message logger */
	public static final Logger LOGGER = Logger.getLogger(ModelUtil.class.getName());

	/**
	 * Private constructor to avoid unnecessary instantiation of the class
	 */
    private ModelUtil() {
    }
    /**
     * Utility to extract the {@link eu.paasage.camel.deployment.DeploymentModel <em>DeploymentModel</em>}
     * from a model file
     * <p>
     * @param filePath		full path to the file
     * @return				the {@link eu.paasage.camel.deployment.DeploymentModel <em>DeploymentModel</em>}
     * @throws Exception	on error
     */
    public static DeploymentModel loadDeploymentModel(String filePath) throws Exception {
		
			CamelModel cm = loadCamelModel(filePath);
			DeploymentModel model = cm.getDeploymentModels().get(0); //get the first one
			return model;
    }
    /**
     * Utility to extract the {@link eu.paasage.camel.CamelModel <em>CamelModel</em>}
     * <p>
     * @param inputFilePath	full path to the file
     * @return				the {@link eu.paasage.camel.CamelModel <em>CamelModel</em>}
     * @throws Exception	on error
     */
    public static CamelModel loadCamelModel(String inputFilePath) throws Exception {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*",		
			new XMIResourceFactoryImpl(){
				public Resource createResource(URI uri){
					XMIResource xmiResource = new XMIResourceImpl(uri);
					return xmiResource;
				}			
			});
		final ResourceSet rs = new ResourceSetImpl();
		rs.getPackageRegistry().put(CamelPackage.eNS_URI, CamelPackage.eINSTANCE);
		Resource res = rs.getResource(URI.createFileURI(inputFilePath),true);
		LOGGER.info("LoadedEObjectsource: " + res.getURI());
		EList<EObject> contents = res.getContents();
		//
		return ((CamelModel) contents.get(0));
	}
    /**
     * Utility to extract the location names from the {@link eu.paasage.camel.requirement.LocationRequirement <em>LocationRequirement</em>}
     * <p>
     * @param locRequirements the source object
     * @return a list of location names
     */
    public static List<String> convertLocations(LocationRequirement locRequirements){
    	List<String> locationNames = new ArrayList<String>();
    	if(locRequirements == null){
    		return locationNames;
    	}    	
    	List<Location> locations = locRequirements.getLocations();
		if(locations != null){
			for(Location loc : locations){
				locationNames.add(loc.getId());
			}
		}
		return locationNames;
    	
    }
    /**
     * Utility to convert a {@link eu.paasage.camel.type.SingleValue <em>SingleValue</em>} object to {@link java.lang.String <em>String</em>} 
     * <p>
     * @param obj	the {@link org.eclipse.emf.ecore.EObject <em>EObject</em>} to switch
     * @return	a {@link java.lang.String <em>String</em>} representation of the object
     */
    public static String switchValue(EObject obj){
    	//LOGGER.debug("just inside switch value....");
		String stringValue = "isNull";
		if(obj == null){
			return stringValue;
		}
		if(obj instanceof EnumerateValue){
			stringValue = ((EnumerateValue) obj).getName(); //the name is the String value, this is the one needed
			
		//}else if(obj instanceof Range){ //this is not applicable
			//stringValue = ((Range) obj).getName(); //range has no name
			//stringValue = String.valueOf(((Range) obj).getLowerLimit().getValue());
			//String ul = ((Range) typeValue).getUpperLimit().getValue().toString();					
		}else if(obj instanceof IntegerValue){
			stringValue = String.valueOf(((IntegerValue) obj).getValue());
		}else if(obj instanceof DoublePrecisionValue){
			stringValue = String.valueOf(((DoublePrecisionValue) obj).getValue());
		}else if(obj instanceof FloatsValue){
			stringValue = String.valueOf(((FloatsValue) obj).getValue());
		}else if(obj instanceof BoolValue){
			stringValue = String.valueOf(((BoolValue) obj).isValue());
			//}else if(obj instanceof SingleValue){
			//stringValue = ((SingleValue) obj).toString();    26 August 15, SingleValue is a base type.  Should be cast to the children type
		}else if(obj instanceof StringsValue){
			stringValue = ((StringsValue) obj).getValue();
    	}else{
			stringValue = "type not supported";//TODO: needs more instanceof
		}
		return stringValue;
	}
    /**
	 * Add the global VM requirements to the local one. The local requirements override the global ones.
	 * <p>
	 * @param global global {@link eu.paasage.camel.deployment.VMRequirementSet <em>VMRequirementSet</em>}
	 * @param local local {@link eu.paasage.camel.deployment.VMRequirementSet <em>VMRequirementSet</em>}
	 * @return the combined {@link eu.paasage.camel.deployment.VMRequirementSet <em>VMRequirementSet</em>}
	 */
	public static VMRequirementSet addGlobalRequirements(VMRequirementSet global, VMRequirementSet local){
		
		if(global != null){
			//local overrides global
			if(global.getLocationRequirement() != null && local.getLocationRequirement() == null){
				local.setLocationRequirement(global.getLocationRequirement());			
			}
			if(global.getOsOrImageRequirement() != null && local.getOsOrImageRequirement() == null){
				local.setOsOrImageRequirement(global.getOsOrImageRequirement());
			}
			if(global.getProviderRequirement() != null && local.getProviderRequirement() == null){
				local.setProviderRequirement(global.getProviderRequirement());
			}
			if(global.getQualitativeHardwareRequirement() != null && local.getQualitativeHardwareRequirement() == null){
				local.setQualitativeHardwareRequirement(global.getQualitativeHardwareRequirement());
			}
			if(global.getQuantitativeHardwareRequirement() != null && local.getQuantitativeHardwareRequirement() == null){
				local.setQuantitativeHardwareRequirement(global.getQuantitativeHardwareRequirement());
			}
		}
		return local;
	}
	/**
	 * Convert a {@link com.eclipsesource.json.JsonArray <em>JsonArray</em>} of {@link java.lang.String <em>String</em>} to a {@link java.util.List <em>List</em>}
	 * <p>
	 * @param asArray	the source {@link com.eclipsesource.json.JsonArray <em>JsonArray</em>}
	 * @return	a {@link java.util.List <em>List</em>} of {@link java.lang.String <em>String</em>} or an empty {@link java.util.List <em>List</em>}
	 * @throws ModelUtilException on processing error 
	 */
	public static List<String> convertJsonArrayToList(JsonArray asArray) throws ModelUtilException  {
		List<String> list = new ArrayList<String>();
		//
		try{
			//caller to guard against null			
			LOGGER.debug("...JsonArray size is " + asArray.size() + " content: " + asArray.toString());
			//convert				
			Iterator<JsonValue> iter = asArray.iterator();
			while(iter.hasNext()){
				//debug
				//LOGGER.debug("...about to add to list...");					
				String s = iter.next().asString();
				list.add(s);
				LOGGER.debug("...after adding " + s + " to list.");	
			}
		}catch(Exception e){
			throw new ModelUtilException("error converting JsonArray to List of String : " + e.getMessage());
		}
		return list;
	}
	/**
	 * Match the target {@link java.lang.String <em>String</em>} against a 
	 * {@link java.util.List <em>List</em>} of {@link java.lang.String <em>String</em>}.
	 * <p>
	 * @param target	the target {@link java.lang.String <em>String</em>} to match
	 * @param candidates	the {@link java.util.List <em>List</em>} of {@link java.lang.String <em>String</em>} to test
	 * @return	true if a match is found, else false.
	 */
	public static boolean matchObjName(String target, List<String> candidates){
		/////
		if(candidates != null && target != null && !target.isEmpty()){//not sure why target is null?
			for(String candidate : candidates){
				LOGGER.debug("matching target " + target + " against " + candidate);
				if(candidate.equals(target)){
					return true;
				}				
			}
			LOGGER.info("No match found for " + target);
		}else{
			LOGGER.info("Nothing to match: target and/or candidates are null!");
		}
		return false;
	}
}
