/*
 * Copyright (c) 2014-5 UK Science and Technology Facilities Council
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.plangenerator.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;

import eu.paasage.camel.Application;
import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.Communication;
import eu.paasage.camel.deployment.CommunicationInstance;
import eu.paasage.camel.deployment.Component;
import eu.paasage.camel.deployment.Configuration;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.Hosting;
import eu.paasage.camel.deployment.HostingInstance;
import eu.paasage.camel.deployment.InternalComponent;
import eu.paasage.camel.deployment.InternalComponentInstance;
import eu.paasage.camel.deployment.ProvidedCommunication;
import eu.paasage.camel.deployment.ProvidedCommunicationInstance;
import eu.paasage.camel.deployment.ProvidedHost;
import eu.paasage.camel.deployment.ProvidedHostInstance;
import eu.paasage.camel.deployment.RequiredCommunication;
import eu.paasage.camel.deployment.RequiredCommunicationInstance;
import eu.paasage.camel.deployment.VM;
import eu.paasage.camel.deployment.VMInstance;
import eu.paasage.camel.deployment.VMRequirementSet;
import eu.paasage.camel.deployment.impl.VMImpl;
import eu.paasage.camel.location.CloudLocation;
import eu.paasage.camel.location.Country;
import eu.paasage.camel.location.GeographicalRegion;
import eu.paasage.camel.organisation.CloudCredentials;
import eu.paasage.camel.organisation.CloudProvider;
import eu.paasage.camel.organisation.DataCenter;
import eu.paasage.camel.organisation.Entity;
import eu.paasage.camel.organisation.OrganisationModel;
import eu.paasage.camel.organisation.User;
import eu.paasage.camel.provider.Attribute;
import eu.paasage.camel.provider.Feature;
import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.camel.requirement.HorizontalScaleRequirement;
import eu.paasage.camel.requirement.ImageRequirement;
import eu.paasage.camel.requirement.OSOrImageRequirement;
import eu.paasage.camel.requirement.OSRequirement;
import eu.paasage.camel.scalability.HorizontalScalingAction;
import eu.paasage.camel.scalability.ScalabilityRule;
import eu.paasage.camel.scalability.ScalingAction;
import eu.paasage.camel.type.SingleValue;
/**
 * Utilities for converting {@link eu.paasage.camel.CamelModel <em>CamelModel</em>}
 * {@link org.eclipse.emf.ecore.EObject <em>EObject</em>} into 
 * {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>}.  
 * <p>
 * @author Shirley Crompton
 * org     UK Science and Technology Facilities Council
 */
public final class ModelToJsonConverter {
	/** message logger */
	public static final Logger LOGGER = Logger.getLogger(ModelToJsonConverter.class.getName());

	/**
	 * Private constructor to avoid unnecessary instantiation of the class
	 */
    private ModelToJsonConverter() {
    }
    /**
     * Generate a {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} representation of a 
     * {@link eu.paasage.camel.deployment.InternalComponent <em>InternalComponent</em>}
     * <p>
     * @param ic	the source {@link eu.paasage.camel.deployment.InternalComponent <em>InternalComponent</em>}
     * @return		the {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} information object
     */
    public static JsonObject convertInternalComponent(InternalComponent ic){
    	JsonObject result = new JsonObject();
    	//
    	LOGGER.debug(" processing internal component: " + ic.getName());
    	//basic metadata
    	result.add("name", ic.getName()); // name
    	//9July15 added objectType as requested by Adapter
    	result.add("objType","internalComponent");
    	//configuration
    	EList<Configuration> resources = ic.getConfigurations(); //need to do it in 2 steps, you may get an empty EList...
		if(resources != null && !resources.isEmpty()){
			Configuration r = resources.get(0);					
			//LOGGER.debug(" just about to call convertConfiguration.... ");
			HashMap<String, String> config = convertConfiguration(r); 
	    	if(!config.isEmpty()){
	    		Set keys = config.keySet();
		        Iterator it = keys.iterator();
		        while(it.hasNext()){
		        	String key = (String) it.next();
		            result.add(key, config.get(key));	            
		        }
	    	}
		}//end if resources != null
		//provided host names
		List<String> phnames = getProvidedHosts(ic.getProvidedHosts());		
		if(!phnames.isEmpty()){
			JsonArray providedHosts = new JsonArray();
			for(String s : phnames){
				providedHosts.add(s);
				
			}
			result.add("providedHosts", providedHosts);
		}
		//required host instance name
		if(ic.getRequiredHost() != null){
			result.add("requiredHost", ic.getRequiredHost().getName());
		}
		//provided communication names
		List<String> providedComs = getProvidedComms(ic.getProvidedCommunications());		
		if(!providedComs.isEmpty()){
			JsonArray providedComNames = new JsonArray();
			for(String s : providedComs){
				providedComNames.add(s);
				
			}
			result.add("providedCommunications", providedComNames);
		}
		//required communications
		List<String> rci = getRequiredComs(ic.getRequiredCommunications());		
		if(!rci.isEmpty()){
			JsonArray RequiredComInstances = new JsonArray();
			for(String s : rci){
				RequiredComInstances.add(s);
				
			}
			result.add("requiredCommunications", RequiredComInstances);
		}
    	return result;
    }
    /**
     * Generate a {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} representation of a 
     * {@link eu.paasage.camel.deployment.InternalComponentInstance <em>InternalComponentInstance</em>}
     * <p> 
     * @param ici	the source {@link eu.paasage.camel.deployment.InternalComponentInstance <em>InternalComponentInstance</em>}
     * @return		the {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} information object
     */
    public static JsonObject convertInternalComponentInstance(InternalComponentInstance ici){
    	JsonObject result = new JsonObject();
    	//
    	LOGGER.debug(" processing : " + ici.getName() + " which is a type of " + ici.getType().getName());
    	//basic metadata
    	result.add("name", ici.getName()); //instance name
    	//9July15 added objectType as requested by Adapter
    	result.add("objType","internalComponentInstance");
    	result.add("type", ici.getType().getName());
    	//configuration is in defined in the InternalComponentType
    	//
//		Not required WP5 gets this directly from CDO server    	
//    	ScalabilityInfo sc = getScalabilityInfoByXRef(comp);
//    	if(sc != null ){
//    		result.add("horizontalScalabilityRule", sc.ruleName);
//    		result.add("horizontalScalabilityActionType", sc.type);
//    		result.add("horizontalScalabilityMinInstance", sc.minInstances);
//    		result.add("horizontalScalabilityMaxInstance", sc.maxInstances);
//    	}
    	//don't know where we can get this yet.  This is related to horizontal scaling
    	//result.add("initialInstance", 1);
    	//
    	//provided host instance names
		List<String> phi = getProvidedHostInstance(ici.getProvidedHostInstances());		
		if(!phi.isEmpty()){
			JsonArray providedHostInstances = new JsonArray();
			for(String s : phi){
				providedHostInstances.add(s);
				
			}
			result.add("providedHostInstances", providedHostInstances);
		}
		//required host instance name
		if(ici.getRequiredHostInstance() != null){
			result.add("requiredHostInstance", ici.getRequiredHostInstance().getName());
		}
		//provided communications
		List<String> pci = getProvidedComInstances(ici.getProvidedCommunicationInstances());		
		if(!pci.isEmpty()){
			JsonArray providedComInstances = new JsonArray();
			for(String s : pci){
				providedComInstances.add(s);
				
			}
			result.add("providedCommunicationInstances", providedComInstances);
		}
		//required communications
		List<String> rci = getRequiredComInstances(ici.getRequiredCommunicationInstances());		
		if(!rci.isEmpty()){
			JsonArray RequiredComInstances = new JsonArray();
			for(String s : rci){
				RequiredComInstances.add(s);
				
			}
			result.add("requiredCommunicationInstances", RequiredComInstances);
		}
		//nested components intended for grouping components into a functional unit, this is not used yet 9/6/15
//		List<String> nestedComps = getNestedComps(comp.getCompositeInternalComponents());
//		if(!nestedComps.isEmpty()){
//			JsonArray nestedIntComps = new JsonArray();
//			for(String s : nestedComps){
//				nestedIntComps.add(s);
//				
//			}
//			result.add("nestedInternalComponent", nestedIntComps);
//		}
    	return result;
    }
    /**
     * Create a {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} representation of a
     * {@link eu.paasage.camel.Application <em>Application</em>} object      	
     * <p>
     * @param app  the source {@link eu.paasage.camel.Application <em>Application</em>} object to convert 
     * @return	a {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} representing a snapshot of the object
     */
    public static JsonObject convertApp(Application app){
    	//
    	JsonObject result = new JsonObject();
    	result.add("name",app.getName()); 
    	//9July15 added objectType as requested by Adapter
    	result.add("objType","application");
    	if(app.getDescription() != null){
    		result.add("description", app.getDescription());
    	}
    	if(app.getVersion() != null){//a String
    		result.add("version", app.getVersion());
    	}
    	//
    	LOGGER.debug("just before getting Owner.....");
    	//aborted changes for S2D, the issue is S2D output is not a self-contained model
//    	String org = null;
//    	Entity owner = (Entity) app.getOwner(); 
//    	LOGGER.debug("owner is : " + owner.getClass().getName());
//    	//org = (String) owner.eGet(owner.eClass().getEStructuralFeature("name"));
//    	LOGGER.debug("owner is : " + org); 
//    	if(owner instanceof OrganisationImpl){
//    		org = ((OrganisationImpl) owner).getName();
//    		LOGGER.debug("owner is : " + org); 
//    	}else if(owner instanceof UserImpl){
//    		LOGGER.debug(" user is : " + ((UserImpl) owner).getName());
//    		//may fail here
//        	LOGGER.debug("the owner's econtainer " + ((UserImpl) owner).eContainer().getClass().getName());
//    	}
//      app owner can be an organisation or a user
    	Entity owner = app.getOwner();
    	String org = "";
    	// 
    	if(owner instanceof OrganisationModel){
    		org = ((OrganisationModel) owner).getName();
    	}else if(owner instanceof User){
    		org = ((OrganisationModel) app.getOwner().eContainer()).getName();
    	}    	
    	if( org != null){
    		result.add("owner", org);
    	}
    	//
    	return result;
    }
    /**
     * Create a {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} model of
     * {@link eu.paasage.camel.Application <em>Application</em>} to represent 
     * a {@link eu.paasage.upperware.plangenerator.model.task.ApplicationInstanceTask <em>ApplicationInstanceTask</em>}     	
     * <p>
     * @param app  the source {@link eu.paasage.camel.Application <em>Application</em>} object to convert 
     * @return	a {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} representing a snapshot of the object
     */
    public static JsonObject convertAppInstance(Application app){
    	//application instance is not a camel object
    	JsonObject result = new JsonObject();
    	result.add("name",app.getName() + "Instance");
    	//9July15 added objectType as requested by Adapter
    	result.add("objType","applicationInstance");
    	result.add("type",app.getName());
    	//
    	return result;
    }
    /**
     * Create a {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} representation of a
     * {@link eu.paasage.camel.deployment.VM <em>VM</em>} object      	
     * <p>
     * @param vm	the source {@link eu.paasage.camel.deployment.VM <em>VM</em>} object to convert
     * @return a {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} representing a snapshot of the object 
     * 	
     */
    public static JsonObject convertVM(VM vm){
    	JsonObject result = new JsonObject();
    	//basic metadata
    	result.add("name", vm.getName()); //name
    	//9July15 added objectType as requested by Adapter
    	result.add("objType","VM");
    	//configuration
    	EList<Configuration> resources = vm.getConfigurations(); //need to do it in 2 steps, you may get an empty EList...
		if(resources != null && !resources.isEmpty()){
			Configuration r = resources.get(0);					
			//LOGGER.debug(" just about to call convertConfiguration.... ");
			HashMap<String, String> config = convertConfiguration(r); 
	    	if(!config.isEmpty()){
	    		Set keys = config.keySet();
		        Iterator it = keys.iterator();
		        while(it.hasNext()){
		        	String key = (String) it.next();
		            result.add(key, config.get(key));	            
		        }
	    	}
		}//end if resources != null    	
    	//
    	LOGGER.debug(" just before getVMQuantitativeSpec....");
		//get vm quantitative specifications and apply globalVMrequirement.  Hardware quantitative requirements are compulsory and must be satisfied, so save  
		HashMap<String, Object> vmQSpec = getVMQuantitativeSpec(vm);
		if(!vmQSpec.isEmpty()){
				Set<Map.Entry<String, Object>> set = vmQSpec.entrySet();
				 for (Map.Entry<String, Object> me : set) {
					 if(me.getValue() instanceof Integer){
						 result.add(me.getKey(), (Integer) me.getValue());
					 }else if(me.getValue() instanceof Double){
						 result.add(me.getKey(), (Double) me.getValue());
					 }else if(me.getValue() instanceof Boolean){
						 result.add(me.getKey(), (Boolean) me.getValue()); 
					 }else if(me.getValue() instanceof String){
						 result.add(me.getKey(), (String) me.getValue());
					 }else if(me.getValue() instanceof Float){		
						 result.add(me.getKey(), (Float) me.getValue());
					 }
			     }//endfor
		}		
    	//provided host names
		List<String> phs = getProvidedHosts(vm.getProvidedHosts());		
		if(!phs.isEmpty()){
			JsonArray providedHosts = new JsonArray();
			for(String s : phs){
				providedHosts.add(s);
				
			}
			result.add("providedHosts", providedHosts);
		}
		//provided communication names
		List<String> pcs = getProvidedComms(vm.getProvidedCommunications());		
		if(!pcs.isEmpty()){
			JsonArray providedComs = new JsonArray();
			for(String s : pcs){
				providedComs.add(s);
				
			}
			result.add("providedCommunications", providedComs);
		}
		//
		return result;
    }
    
    /**
     * Generate a snapshot in {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} of a 
     * {@link eu.paasage.camel.deployment.VMInstance <em>VMInstance</em>}
     * <p> 
     * @param vmi	the source {@link eu.paasage.camel.deployment.VMInstance <em>VMInstance</em>}
     * @return		the {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} information object
     */
    public static JsonObject convertVMInstance(VMInstance vmi){
    	
    	JsonObject result = new JsonObject();
    	//    
    	LOGGER.debug(" vmi.getType() is an instance of " + vmi.getType().getClass().getName());
    	VMImpl vm = (VMImpl) vmi.getType(); //a vmi type is a VM
    	LOGGER.debug(" processing : " + vmi.getName() + " which is a type of " + (vm.getName() == null ? "null" : vm.getName()));
    	//basic metadata
    	result.add("name", vmi.getName());
    	//9July15 added objectType as requested by Adapter
    	result.add("objType","vmInstance");
		result.add("type", vmi.getType().getName());		
    	//get vm flavour.  We get this from the ProviderModel produced by the CPGenerator   	
    	Attribute vmType = vmi.getVmType(); //you will always get an instance but its attributes may not be populated!
    	//
		SingleValue typeValue = vmi.getVmTypeValue(); //this need to explicitly cast, see below
		//
		//LOGGER.debug(" just before switchValue...." + typeValue.getClass().getName());
		String valueName = ModelUtil.switchValue(typeValue);
		LOGGER.debug("VMTYPE : " + (vmType.getName() == null ? "null" : vmType.getName()) + ", value name : " + valueName);	
		if(valueName != null && (!valueName.equals("type not supported") || !valueName.equals("null"))){
			LOGGER.debug("VMType valueName : " + valueName);
			result.add(vmType.getName(), valueName);
		}		
		//
	    //LOGGER.debug("just before if(vmType != null....");
		if(vmType.getName() != null && !vmType.getName().equals("null")){	//added 22/7/15 to guard against broken xmi file exported from CDO server
		//get CloudProvider name and location (10/6/15 may be able to optimise it using typeValue.eContainer... check the xmi)	
			HashMap<String, Object> cloudProviderInfo = getCloudProviderInfo(vmType);	//get it from vmType
			if(!cloudProviderInfo.isEmpty()){
				Set keys = cloudProviderInfo.keySet();
		        Iterator it = keys.iterator();
		        while(it.hasNext()){// only 2 types of objects - String, JsonArray
		        	String key = (String) it.next();
		        	if(cloudProviderInfo.get(key) instanceof String){
		        		result.add(key,cloudProviderInfo.get(key).toString());
		        	}else if(cloudProviderInfo.get(key) instanceof JsonArray){
		        		result.add(key, (JsonArray) cloudProviderInfo.get(key));
		        	}
		            LOGGER.debug("Added " + key + ", " +  cloudProviderInfo.get(key) + " to Json model.");
		        }
			}
		}//end if VMType !=null
		//26 Aug 2015 added cloud credentials (currently contain username/password).  These come from the main Camel Model OrganisationModel
		//we need to match on cloud name, the method will check for null		
		String temp = "";
		if(result.get("cloud") != null){ //guard for NPE
			temp = result.get("cloud").asString(); 
		}
		JsonObject credential = getCredentials(temp, vmi);
    	result.add("credential", credential);
		//
		//provided host instance names
		List<String> phi = getProvidedHostInstance(vmi.getProvidedHostInstances());		
		if(!phi.isEmpty()){
			JsonArray providedHostInstances = new JsonArray();
			for(String s : phi){
				providedHostInstances.add(s);
				
			}
			result.add("providedHostInstances", providedHostInstances);
		}
		//provided communication instance names
		List<String> pci = getProvidedComInstances(vmi.getProvidedCommunicationInstances());		
		if(!pci.isEmpty()){
			JsonArray providedComInstances = new JsonArray();
			for(String s : pci){
				providedComInstances.add(s);
				
			}
			result.add("providedCommunicationInstances", providedComInstances);
		}
		//
    	return result;    	
    }
    /**
     * Generate a snapshot in {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} of a 
     * {@link eu.paasage.camel.deployment.Communication <em>Communication</em>}
     * <p> 
     * @param com	the source {@link eu.paasage.camel.deployment.Communication <em>Communication</em>}
     * @return	the {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} information object
     */
    public static JsonObject convertCommunication(Communication com){
    	JsonObject result = new JsonObject();
    	//
    	LOGGER.debug(" processing  communication: " + com.getName() );
    	//basic metadata
    	result.add("name",  com.getName());
    	//9July15 added objectType as requested by Adapter
    	result.add("objType","communication");
    	result.add("communicationType", com.getType().getName());//any,remote,local
    	result.add("isMandatory",com.getRequiredCommunication().isIsMandatory());//req com is Mandatory?
    	result.add("provider", com.getProvidedCommunication().getName());
    	//
    	result.add("providerPort", com.getProvidedCommunication().getPortNumber());//provided port number
    	//configuration
    	HashMap<String, String> phConfig = convertConfiguration(com.getProvidedPortConfiguration());
    	if(!phConfig.isEmpty()){
    		Set keys = phConfig.keySet();
	        Iterator it = keys.iterator();
	        while(it.hasNext()){
	        	String key = "providedPort" + (String) it.next();
	            result.add(key, phConfig.get(key));	            
	        }
    	}
    	result.add("consumer", com.getRequiredCommunication().getName());
    	result.add("consumerPort", com.getRequiredCommunication().getPortNumber());
    	HashMap<String, String> rpConfig = convertConfiguration(com.getRequiredPortConfiguration());
    	if(!rpConfig.isEmpty()){
    		Set keys = rpConfig.keySet();
	        Iterator it = keys.iterator();
	        while(it.hasNext()){
	        	String key = "requiredPort" + (String) it.next();
	            result.add(key, rpConfig.get(key));	            
	        }
    	}
    	return result;
    }
    /**
     * Generate a snapshot in {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} of a 
     * {@link eu.paasage.camel.deployment.CommunicationInstance <em>CommunicationInstance</em>}
     * <p> 
     * @param ci	the source {@link eu.paasage.camel.deployment.CommunicationInstance <em>CommunicationInstance</em>}
     * @return	the {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} information object
     */
    public static JsonObject convertCommunicationInstance(CommunicationInstance ci){
    	JsonObject result = new JsonObject();
    	//    	 
    	LOGGER.debug(" processing com instance : " + ci.getName());
    	//basic metadata
    	result.add("name",  ci.getName());
    	//9July15 added objectType as requested by Adapter
    	result.add("objType","communicationInstance");
    	result.add("providerInstance", ci.getProvidedCommunicationInstance().getName());   	    	
    	result.add("consumerInstance", ci.getRequiredCommunicationInstance().getName());
    	result.add("type", ci.getType().getName());
    	return result;
    }
	/**
	 * Generate a snapshot in {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} of a 
     * {@link eu.paasage.camel.deployment.Hosting <em>Hosting</em>}
     * <p>   
	 * @param hosting	he source {@link eu.paasage.camel.deployment.Hosting <em>Hosting</em>}
	 * @return	the {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} information object
	 */
	public static JsonObject convertHosting(Hosting hosting){
	    	JsonObject result = new JsonObject();
	    	// 
	    	LOGGER.debug(" processing hosting : " + hosting.getName());
	    	//basic metadata
	    	result.add("name", hosting.getName());
	    	//9July15 added objectType as requested by Adapter
	    	result.add("objType","hosting");
	    	result.add("provider", hosting.getProvidedHost().getName());  	
	    	//configuration
	    	HashMap<String, String> phConfig = convertConfiguration(hosting.getProvidedHostConfiguration());
	    	if(!phConfig.isEmpty()){
	    		Set keys = phConfig.keySet();
		        Iterator it = keys.iterator();
		        while(it.hasNext()){
		        	String key = "providedHost" + (String) it.next();
		            result.add(key, phConfig.get(key));	            
		        }
	    	}
	    	result.add("consumer", hosting.getRequiredHost().getName());    	
	    	HashMap<String, String> rhConfig = convertConfiguration(hosting.getRequiredHostConfiguration());
	    	if(!rhConfig.isEmpty()){
	    		Set keys = rhConfig.keySet();
		        Iterator it = keys.iterator();
		        while(it.hasNext()){
		        	String key = "requiredHost" + (String) it.next();
		            result.add(key, rhConfig.get(key));	            
		        }
	    	}
	    	return result;
	    }
    /**
     * Generate a snapshot in {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} of a 
     * {@link eu.paasage.camel.deployment.HostingInstance <em>HostingInstance</em>}
     * <p>
     * @param hi	the source {@link eu.paasage.camel.deployment.HostingInstance <em>HostingInstance</em>}
     * @return	the {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>} information object
     */
    public static JsonObject convertHostingInstance(HostingInstance hi){
    	JsonObject result = new JsonObject();
    	//
    	LOGGER.debug(" processing hosting instance : " + hi.getName());
    	//basic metadata
    	result.add("name", hi.getName()); 
    	//9July15 added objectType as requested by Adapter
    	result.add("objType","hostingInstance");
    	result.add("providerInstance", hi.getProvidedHostInstance().getName());
    	result.add("consumerInstance", hi.getRequiredHostInstance().getName());
    	result.add("type", hi.getType().getName());
    	//debug
    	//LOGGER.debug("...just before returning from convertHostingInstance");
    	return result;
    }
    /**
     * Extract a {@link java.util.List <em>List</em>} of
     * {@link eu.paasage.camel.deployment.InternalComponent <em>InternalComponent</em>} name
     * nested within a {@link eu.paasage.camel.deployment.InternalComponent <em>InternalComponent</em>}
     * <p>
     * @param comps		the containing {@link eu.paasage.camel.deployment.InternalComponent <em>InternalComponent</em>}
     * @return	a list of the extracted component names or an empty list
     */
    public static List<String> getNestedComps(List<InternalComponent> comps){
    	//
    	List<String> icsStr = new ArrayList<String>();
    	if(comps == null){
    		return icsStr;
    	}    	
    	for(InternalComponent nici : comps){
    		icsStr.add(nici.getName());
    	}
    	return icsStr;    	
    }
    /**
     * Extract the name from a {@link java.util.List <em>List</em>} of
     * {@link eu.paasage.camel.deployment.RequiredCommunication <em>RequiredCommunication</em>} 
     * <p>
     * @param rcoms	the {@link java.util.List <em>List</em>} of {@link eu.paasage.camel.deployment.RequiredCommunication <em>RequiredCommunication</em>}
     * @return	a {@link java.util.List <em>List</em>} of names or an empty {@link java.util.List <em>List</em>}
     */
    public static List<String> getRequiredComs(List<RequiredCommunication> rcoms){
    	List<String> rcsStr = new ArrayList<String>();
    	if(rcoms == null){
    		return rcsStr;
    	}    	
    	for(RequiredCommunication rci : rcoms){
    		rcsStr.add(rci.getName());
    	}
    	return rcsStr; 
    }
    /**
     * Extract the name from a {@link java.util.List <em>List</em>} of
     * {@link eu.paasage.camel.deployment.RequiredCommunicationInstance <em>RequiredCommunicationInstance</em>} 
     * <p>
     * @param rcis	the {@link java.util.List <em>List</em>} of {@link eu.paasage.camel.deployment.RequiredCommunicationInstance <em>RequiredCommunicationInstance</em>} 
     * @return a {@link java.util.List <em>List</em>} of names or an empty {@link java.util.List <em>List</em>}
     */
    public static List<String> getRequiredComInstances(List<RequiredCommunicationInstance> rcis){
    	
    	List<String> rcsStr = new ArrayList<String>();
    	if(rcis == null){
    		return rcsStr;
    	}    	
    	for(RequiredCommunicationInstance rci : rcis){
    		rcsStr.add(rci.getName());
    	}
    	return rcsStr;    	
    }
    /**
     * Get the name of a {@link eu.paasage.camel.deployment.ProvidedCommunicationInstance <em>ProvidedCommunicationInstance</em>}
     * <p>
     * @param pcis	a {@link java.util.List <em>List</em>} of {@link eu.paasage.camel.deployment.ProvidedCommunicationInstance <em>ProvidedCommunicationInstance</em>} 				
     * @return		names of the {@link eu.paasage.camel.deployment.ProvidedCommunicationInstance <em>ProvidedCommunicationInstance</em>}
     */
    public static List<String> getProvidedComInstances(List<ProvidedCommunicationInstance> pcis){
    	
    	List<String> pcsStr = new ArrayList<String>();
    	if(pcis == null){
    		return pcsStr;
    	}    	
    	for(ProvidedCommunicationInstance pci : pcis){
    		pcsStr.add(pci.getName());
    	}
    	return pcsStr;
    	
    }
    /**
     * Extract the names from a {@link java.util.List <em>List</em>} of {@link eu.paasage.camel.deployment.ProvidedCommunication <em>ProvidedCommunication</em>}
     * <p>
     * @param pcoms	The {@link java.util.List <em>List</em>} of {@link eu.paasage.camel.deployment.ProvidedCommunication <em>ProvidedCommunication</em>} to convert
     * @return	A {@link java.util.List <em>List</em>} of {@link java.lang.String <em>String</em>}
     */
	 public static List<String> getProvidedComms(List<ProvidedCommunication> pcoms){
	    	
	    	List<String> pcsStr = new ArrayList<String>();
	    	if(pcoms == null){
	    		return pcsStr;
	    	}    	
	    	for(ProvidedCommunication pcom : pcoms){
	    		pcsStr.add(pcom.getName());
	    	}
	    	return pcsStr;
	    	
	    }
    /**
     * Extract the names from a {@link java.util.List <em>List</em>} of {@link eu.paasage.camel.deployment.ProvidedHost <em>ProvidedHost</em>}
     * <p>
     * @param phs	The {@link java.util.List <em>List</em>} of {@link eu.paasage.camel.deployment.ProvidedHost <em>ProvidedHost</em>} to convert
     * @return	A {@link java.util.List <em>List</em>} of {@link java.lang.String <em>String</em>}
     */
    public static List<String> getProvidedHosts(List<ProvidedHost> phs){
    	
    	List<String> pisStr = new ArrayList<String>();
    	if(phs == null){
    		return pisStr;
    	}
    	for(ProvidedHost ph : phs){
    		pisStr.add(ph.getName());
    	}
    	return pisStr;
    	
    }
    /**
     * Extract the names from a {@link java.util.List <em>List</em>} of {@link eu.paasage.camel.deployment.ProvidedHostInstance <em>ProvidedHostInstance</em>}
     * <p>
     * @param phis	The {@link java.util.List <em>List</em>} of {@link eu.paasage.camel.deployment.ProvidedHostInstance <em>ProvidedHostInstance</em>} to convert
     * @return	A {@link java.util.List <em>List</em>} of {@link java.lang.String <em>String</em>}
     */
    public static List<String> getProvidedHostInstance(List<ProvidedHostInstance> phis){
    	
    	List<String> pisStr = new ArrayList<String>();
    	if(phis == null){
    		return pisStr;
    	}
    	for(ProvidedHostInstance phi : phis){
    		pisStr.add(phi.getName());
    	}
    	return pisStr;
    	
    }
    /**
     * Convert a {@link eu.paasage.camel.deployment.Configuration <em>Configuration</em>} to a {@link java.util.HashMap <em>HashMap</em>} object.
     * @param config	the source {@link eu.paasage.camel.deployment.Configuration <em>Configuration</em>}
     * @return	the populated {@link java.util.HashMap <em>HashMap</em>}
     */
    public static HashMap<String, String> convertConfiguration(Configuration config){
    	//LOGGER.debug("just before populating config....");
    	HashMap<String, String> result = new HashMap<String, String>();    	
    	if(config != null){
    		result.put("configName",config.getName());
    		if(config.getDownloadCommand() != null){
    			result.put("downloadCmd", config.getDownloadCommand());
    		}
    		if(config.getConfigureCommand() != null){
    			result.put("configureCmd", config.getConfigureCommand() );
    		}
    		if(config.getInstallCommand() != null){
    			result.put("installCmd", config.getInstallCommand());
    		}
    		if(config.getStartCommand() != null){
    			result.put("startCmd", config.getStartCommand());
    		}
    		if(config.getStopCommand() != null){
    			result.put("stopCmd", config.getStopCommand());
    		}
    		if(config.getUploadCommand() != null){
    			result.put("uploadCmd", config.getUploadCommand());
    		}
    		//LOGGER.debug("just after populating config....");
    	}
    	return result;
    }
    
    /**
     * Get the {@link eu.paasage.camel.requirement.QuantitativeHardwareRequirement <em>QuantitativeHardwareRequirement</em>}
     * of a VM
     * <p>
     * @param vm	the VM to query
     * @return	a {@link java.util.HashMap <em>HashMap</em>} containing the information
     */
    public static HashMap<String, Object> getVMQuantitativeSpec(VM vm){
    	//May need to source the info from the VM object if available, needs to see a concrete deployment model example
    	LOGGER.debug("processing vm : " + vm.getName());
    	VMRequirementSet globalReq = ((DeploymentModel) vm.eContainer()).getGlobalVMRequirementSet();
    	VMRequirementSet vmReq = vm.getVmRequirementSet(); //for this component
    	VMRequirementSet allVMReq = ModelUtil.addGlobalRequirements(globalReq, vmReq); //includes global VM requirement set
		return (convertVMRequirementSet(allVMReq));
    }
    /**
     * Get the VM hardware requirements from the {@link eu.paasage.camel.deployment.VMRequirementSet <em>VMRequirementSet</em>}
     * @param vmReq	the VM {@link eu.paasage.camel.deployment.VMRequirementSet <em>VMRequirementSet</em>} to parse
     * @return	a {@link java.util.HashMap <em>HashMap</em>} of the hardware requirements
     */
    public static HashMap<String, Object> convertVMRequirementSet(VMRequirementSet vmReq){
    	//these may have to be sourced from the VM camel element, needs to see a concrete deployment model
    	//QuantitativeRequirement is a hard requirement and must be fulfilled 
    	LOGGER.debug("processing VMRequirement : " + vmReq.getName());
    	HashMap<String, Object> hm = new HashMap<String, Object>();
    	OSOrImageRequirement osReq = vmReq.getOsOrImageRequirement();
		if(osReq != null){
			if(osReq instanceof ImageRequirement){
				hm.put("osImage",((ImageRequirement) osReq).getImageId());
			}else if(osReq instanceof OSRequirement){
				hm.put("os", ((OSRequirement) osReq).getOs());
				hm.put("os64bit", ((OSRequirement) osReq).isIs64os());
			}
		}
		//9June15 Alessandro said we only needs os/image and vmType, the followings should be left to ExecutionWare to decide at runtime
//		QuantitativeHardwareRequirement quantitativeHWReq = vmReq.getQuantitativeHardwareRequirement();
//		if(quantitativeHWReq != null){
//			if(quantitativeHWReq.getMinCores() !=0){
//				hm.put("minCores", quantitativeHWReq.getMinCores());
//			}
//			if(quantitativeHWReq.getMaxCores() != 0 ){
//				hm.put("maxCores", quantitativeHWReq.getMaxCores());
//			}
//			if(quantitativeHWReq.getMinRAM() != 0){
//				hm.put("minRAM",quantitativeHWReq.getMinRAM());
//			}
//			if(quantitativeHWReq.getMaxRAM() != 0){
//				hm.put("maxRAM", quantitativeHWReq.getMaxRAM());
//			}
//			if(quantitativeHWReq.getMinCPU() != 0){
//				hm.put("minCPU", quantitativeHWReq.getMinCPU());
//			}
//			if(quantitativeHWReq.getMaxCPU() != 0){
//				hm.put("maxCPU", quantitativeHWReq.getMaxCPU());
//			}
//			if(quantitativeHWReq.getMinStorage() !=0 ){
//				hm.put("minStorage", quantitativeHWReq.getMinStorage());
//			}
//			if(quantitativeHWReq.getMaxStorage() != 0){
//				hm.put("maxStorage", quantitativeHWReq.getMaxStorage());
//			}			
//		}
    	return hm;
    }    
    /**
     * Get the {@link eu.paasage.camel.organisation.CloudProvider <em>CloudProvider</em>} name and
     * from the VM type {@link eu.paasage.camel.provider.Attribute <em>Attribute</em>} which maps
     * indirectly to a particular {@link eu.paasage.camel.organisation.CloudProvider <em>CloudProvider</em>}
     * <p>
     * @param vmType	the VM type {@link eu.paasage.camel.provider.Attribute <em>Attribute</em>}
     * @return a {@link java.util.HashMap <em>HashMap</em>} containing the information.
     */
    public static HashMap<String, Object> getCloudProviderInfo(Attribute vmType){
    	LOGGER.debug("... just inside getCloudProviderInfo ....");
		//System.out.println("inside getCloudProviderInfo with vmType (cdoid) " + vmType.cdoID().toString());
    	//populate cloud,     	
    	HashMap<String, Object> hm = new HashMap<String, Object>();	   
    	EObject provider = vmType.eContainer().eContainer().eContainer(); //21july15 adjust to current camelModel, needs to go up one more parent 
    	//24July 2015 providerModel.rootFeature.subFeatures.attributes (one of the attributes is VM)
    	//debug
    	//System.out.println("provider is an instance of " + provider.getClass().getName()); //providerModelImpl 21/7/2015
		if(provider instanceof ProviderModel){
			LOGGER.debug("about to cast provider container to ProvderModel....");
			//System.out.println("about to cast provider container to ProvderModel....");
			ProviderModel cloudPM = (ProviderModel) provider;
			//System.out.println("cloudPM is : " + cloudPM.getName());
			//
			hm.put("cloud", cloudPM.getName()); //this is the cloud provider model name
			//test
			//System.out.println("cloudPM parent is :" + cloudPM.eContainer().getClass().getName());
			//22July15 this may only work for model object obtained from a life transaction or both model files are loaded into memory 
			//The ProviderModel in the main xmi is different from the providerModel exported on its own.  The former has no subfeatures!
			EList<Feature> subFeatures = cloudPM.getRootFeature().getSubFeatures();
			if(subFeatures != null && !subFeatures.isEmpty()){
				JsonArray locsStr = new JsonArray();
				for(Feature sf : subFeatures){
					if(sf.getName().equals("Location")){
						EList<Feature> locFeatures = sf.getSubFeatures();
						for(Feature loc : locFeatures){
							locsStr.add(loc.getName());
							LOGGER.debug("the location subfeature is " + loc.getName());
						}
//						Feature locationFeature = sf.getSubFeatures().get(0);{//just use the first one for the moment
//							if(locationFeature.getName() != null){
//								LOGGER.debug("the location subfeature is " + locationFeature.getName());
//								System.out.println("the location subfeature is " + locationFeature.getName());
//								hm.put("region", locationFeature.getName());
//							}
//						}
					}
					hm.put("locations", locsStr);
				}
			}
			//26 August, 2015 get driver and endpoint
			String driver = "";	//can be null
			String endpoint = "";
			EList<Attribute> attrs = cloudPM.getRootFeature().getAttributes(); 
			if(attrs != null && !attrs.isEmpty()){
				for(Attribute attr : attrs){
					LOGGER.debug("CProvider current attribute : " + attr.getName());
					if(attr.getName().equals("Driver")){
						//LOGGER.debug("found Driver attribute.....");	
						//System.out.print("attr valueType is : " + attr.getValueType());
						driver = ModelUtil.switchValue(attr.getValue());	//driver is a StringsValue, method returns isNull if obj is null
						//LOGGER.debug("driver is : " + driver);
					}else if(attr.getName().equals("EndPoint")){
						//LOGGER.debug("found EndPoint attribute.....");
						endpoint = ModelUtil.switchValue(attr.getValue());
						//LOGGER.debug("endpoint is : " + endpoint);
					}
				}
			}
			hm.put("driver", driver);
			hm.put("endpoint", endpoint);
			
			/* commented out on 21/7/2015 as using cross referencer throws illegal op 114 and camel model seems to have changed
			//find the cloud provider
			//debug
	    	//System.out.println("Just before calling EcoreUtil.UsageCrossReferencer.find....");
			Collection<Setting> references = EcoreUtil.UsageCrossReferencer.find(cloudPM, cloudPM.eResource().getResourceSet());	//this throws Illegal opcode: 114		
			LOGGER.debug("UsageCrossReferencer size : " + (references == null ? 0 : references.size())); //only 1 
			//debug
			//System.out.println("just before for(Setting setting : references.....");
			for(Setting setting : references){				
				//LOGGER.debug("setting feature container name  :" + setting.getEStructuralFeature().getContainerClass().getName()); //:eu.paasage.camel.organisation.CloudProvider
				//LOGGER.debug("container feature name : " + setting.getEStructuralFeature().getEContainingClass().getName());  //CloudProvider
				LOGGER.debug("eObject.eContainer class : " + setting.getEObject().eContainer().eClass().getName());//OrganisationModel
				//LOGGER.debug("eObject class : " + setting.getEObject().eClass().getName());
				//
				if(setting.getEObject() instanceof CloudProvider){				 
					CloudProvider cp = (CloudProvider) setting.getEObject(); //cast 
					hm.put("cloud", cp.getName());					
					//find the location
					List<DataCenter> datacenters = (List<DataCenter>) ((OrganisationModel) cp.eContainer()).getDataCentres();
					for(DataCenter dc : datacenters){
						if(dc.getCloudProvider().getProviderModel().equals(cloudPM)){
							if(dc.getLocation() instanceof Country){
								//we are getting the country name here, but getGeographicalRegion returns a EList?? not the parent region for the name
								hm.put("region", (((Country) dc.getLocation()).getName() == null) ? ((Country) dc.getLocation()).getId() : ((Country) dc.getLocation()).getName());								
							}else if(dc.getLocation() instanceof CloudLocation){
								hm.put("cloudLocation",((CloudLocation) dc.getLocation()).getId());
								hm.put("region", (((CloudLocation) dc.getLocation()).getGeographicalRegion().getName() == null ? "null" : ((CloudLocation) dc.getLocation()).getGeographicalRegion().getName()));
							}else if(dc.getLocation() instanceof GeographicalRegion){
								hm.put("region", ((GeographicalRegion) dc.getLocation()).getName());
							}							
						}
					}//end for data center
					////////////////////
					
					LOGGER.debug("cloud provider : " + hm.get("cloud"));
				}//end for setting instanceof CloudProvider
			}//end for setting reference
			*/
		}//end if instanceof ProviderModel
		return hm;    
    }
    
    /**
     * Get the owner credentials for the specified cloud.
     * <p>
     * @param cloudName		Name of the target cloud
     * @return				the credentials as a {@link com.eclipsesource.json.JsonObject <em>JsonObject</em>}
     */
    public static JsonObject getCredentials(String cloudName, VMInstance vmi){
    	//System.out.println("... inside get credentials with cloudName: " + cloudName);
    	//
    	JsonObject credentials = new JsonObject();
    	//set default empty strings
		credentials.add("username", "");
		credentials.add("password", "");
		//
    	if(cloudName == null || cloudName.isEmpty()){    		
    		return credentials;
    	}
    	LOGGER.debug("cloud name is : " + cloudName);
    	//go ahead
    	CamelModel model = (CamelModel) vmi.eContainer().eContainer(); //vmi parent is DeployomentModel whose parent is CamelModel
    	Entity owner = model.getApplications().get(0).getOwner(); //get the first one for now
    	EList<CloudCredentials> cc = null;
    	// 
    	if(owner instanceof OrganisationModel){
    		cc = ((OrganisationModel) owner).getUsers().get(0).getCloudCredentials(); //get the first one for now
    	}else if(owner instanceof User){
    		cc = ((User) owner).getCloudCredentials();
    	}    	
    	if(cc != null){    		
    		//:TODO get SSH public/private key strings
    		for(CloudCredentials credo : cc){
    			//needs to compare the cloudprovider name
    			if(credo.getCloudProvider().getName().equals(cloudName)){
    				if(credo.getUsername() != null){ //allows empty string
    					credentials.remove("username");	//there is no update method, has to remove then add
    					credentials.add("username", credo.getUsername());
    					LOGGER.debug("crendential user name : " + credo.getUsername());
    				}
    				if(credo.getPassword() != null){ 
    					credentials.remove("password");
    					credentials.add("password", credo.getPassword());
    					LOGGER.debug("crendential user password : " + credo.getPassword());
    				}
    			}
    		}
    	}
    	return credentials;
    }
    /**
     * Find the horizontal scaling information for a {@link eu.paasage.camel.deployment.InternalComponent <em>InternalComponent</em>}
     * @param internalComponent	the source {@link eu.paasage.camel.deployment.InternalComponent <em>InternalComponent</em>}
     * @return	the retrieved horizontal scaling information
     */
    public static ScalabilityInfo getScalabilityInfoByXRef(Component internalComponent){
		//
		LOGGER.debug("processing : " + internalComponent.getName());
		Collection<Setting> references = EcoreUtil.UsageCrossReferencer.find(internalComponent, internalComponent.eResource().getResourceSet());			
		LOGGER.debug("UsageCrossReferencer size : " + (references == null ? 0 : references.size())); //
		//
		ScalabilityInfo horizontalScalingInfo = new ScalabilityInfo(); //would need more if checking for other types of scaling info
		//
		for(Setting setting : references){
			//
			if(setting.getEObject() instanceof HorizontalScaleRequirement){
				LOGGER.debug("About to cast eObj to HorizontalScaleRequirement ...");		
				HorizontalScaleRequirement hr = (HorizontalScaleRequirement) setting.getEObject(); //cast
				horizontalScalingInfo = new ScalabilityInfo();
				horizontalScalingInfo.maxInstances = hr.getMaxInstances();
				horizontalScalingInfo.minInstances = hr.getMinInstances();				
			}else if(setting.getEObject() instanceof HorizontalScalingAction){
				LOGGER.debug("About to cast eObj to HorizontalScalingAction ...");
				HorizontalScalingAction hscaction = (HorizontalScalingAction) setting.getEObject();//cast
				horizontalScalingInfo.type = hscaction.getType().getLiteral();
				//fnd find the rules
				ScalabilityRule sr = getRuleByActionXRef(hscaction);
				if(sr != null){
					horizontalScalingInfo.ruleName = sr.getName();
					LOGGER.debug("set horizontal scaling rule to : " + horizontalScalingInfo.ruleName);
				}else{
					horizontalScalingInfo.ruleName = "null";
				}
			}
		}//could also check for vertical scaling, optimisation etc.
		return horizontalScalingInfo;
	}
	/**
	 * Find the {@link eu.paasage.camel.scalability.ScalabilityRule <em>ScalabilityRule</em>}
	 * associated with a {@link eu.paasage.camel.scalability.HorizontalScalingAction <em>HorizontalScalingAction</em>}
	 * @param action	the source {@link eu.paasage.camel.scalability.HorizontalScalingAction <em>HorizontalScalingAction</em>}
	 * @return	the retrieved {@link eu.paasage.camel.scalability.HorizontalScalingAction <em>HorizontalScalingAction</em>}
	 */
	public static ScalabilityRule getRuleByActionXRef(ScalingAction action){
		LOGGER.debug("RuleByActionXRef processing : " + action.getName());
		Collection<Setting> references = EcoreUtil.UsageCrossReferencer.find(action, action.eResource().getResourceSet());			
		LOGGER.debug("UsageCrossReferencer size : " + (references == null ? 0 : references.size())); //
		//
		for(Setting setting : references){
			//System.out.println("eObject class : " + setting.getEObject().eClass().getName());
			if(setting.getEObject() instanceof ScalabilityRule){
				//
				LOGGER.debug("getRuleByActionXRef about to return rule ... ");			
				return (ScalabilityRule) setting.getEObject();
			}
		}
		return null;
	}

	/**
	 * Nested class containing basic scalability information for an
	 * {@link eu.paasage.camel.deployment.Component <em>Component</em>}.
	 * <p>
	 * @author Shirley Crompton
	 * org     UK Science and Technology Facilities Council
	 */
	static class ScalabilityInfo {
		String ruleName;
		int maxInstances;
		int minInstances;
		String type;
	}
}
