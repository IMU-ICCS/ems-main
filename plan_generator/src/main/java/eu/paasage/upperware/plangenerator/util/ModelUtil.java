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
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonValue;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.CamelPackage;
import eu.paasage.camel.deployment.Communication;
import eu.paasage.camel.deployment.CommunicationInstance;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.Hosting;
import eu.paasage.camel.deployment.HostingInstance;
import eu.paasage.camel.deployment.InternalComponent;
import eu.paasage.camel.deployment.InternalComponentInstance;
import eu.paasage.camel.deployment.ProvidedCommunication;
import eu.paasage.camel.deployment.RequiredCommunication;
import eu.paasage.camel.deployment.RequiredCommunicationInstance;
import eu.paasage.camel.deployment.VM;
import eu.paasage.camel.deployment.VMInstance;
import eu.paasage.camel.deployment.VMRequirementSet;
import eu.paasage.camel.location.Location;
import eu.paasage.camel.requirement.LocationRequirement;
import eu.paasage.camel.type.BoolValue;
import eu.paasage.camel.type.DoublePrecisionValue;
import eu.paasage.camel.type.EnumerateValue;
import eu.paasage.camel.type.FloatsValue;
import eu.paasage.camel.type.IntegerValue;
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
	public static final Logger logger = Logger.getLogger(ModelUtil.class.getName());

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
     * Utility to extract the {@link eu.paasage.camel.deployment.DeploymentModel <em>DeploymentModel</em>}
     * that matches the provided index from a model file
     * <p>
     * @param filePath		full path to the file
     * @param index			index to the required {@link eu.paasage.camel.deployment.DeploymentModel <em>DeploymentModel</em>}
     * @return				the {@link eu.paasage.camel.deployment.DeploymentModel <em>DeploymentModel</em>}
     * @throws Exception	on error
     */
    public static DeploymentModel loadDeploymentModel(String filePath, int index) throws Exception {
		
			CamelModel cm = loadCamelModel(filePath);
			DeploymentModel model = cm.getDeploymentModels().get(index); 
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
		logger.info("LoadedEObjectsource: " + res.getURI());
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
			logger.debug("...JsonArray size is " + asArray.size() + " content: " + asArray.toString());
			//convert				
			Iterator<JsonValue> iter = asArray.iterator();
			while(iter.hasNext()){
				//debug
				//LOGGER.debug("...about to add to list...");					
				String s = iter.next().asString();
				list.add(s);
				logger.debug("...after adding " + s + " to list.");	
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
				logger.debug("matching target " + target + " against " + candidate);
				if(candidate.equals(target)){
					return true;
				}				
			}
			logger.info("No match found for " + target);
		}else{
			logger.info("Nothing to match: target and/or candidates are null!");
		}
		return false;
	}
	/**
	 * Count the instances for each deployment type object.
	 * <p>
	 * @param dm {@link eu.paasage.camel.deployment.DeploymentModel <em>DeploymentModel</em>} to process.
	 * @return A {@link java.util.Map <em>map</em>} of instance count by type.
	 * @throws {@link eu.paasage.upperware.plangenerator.exception.ModelUtilException <em>ModelUtilException</em>} 
	 * 			if {@link eu.paasage.camel.deployment.DeploymentModel <em>DeploymentModel</em>} is null. 
	 */
	public static Map<String, Integer> getInstanceCountByTypes(DeploymentModel dm) throws ModelUtilException{
		Map<String, Integer> result = new Hashtable<String,Integer>();
		if(dm == null){
			throw new ModelUtilException("DeploymentModel is null!");
		}
		//debug
		logger.debug("VMInstance count: " + dm.getVmInstances().size());
		logger.debug("InternalCompInstance count: " + dm.getInternalComponentInstances().size());
		logger.debug("HostingInstance count: " + dm.getHostingInstances().size());
		logger.debug("CommInstance count: " + dm.getCommunicationInstances().size());
		//
		for(VM vm : dm.getVms()){
				Collection<Setting> references = EcoreUtil.UsageCrossReferencer.find(vm, vm.eResource().getResourceSet());			
				logger.debug(vm.getName() + " UsageCrossReferencer size : " + (references == null ? 0 : references.size())); //
				for(Setting setting : references){
					if(setting.getEObject() instanceof VMInstance){
						if(result.containsKey(vm.getName())){
							//debug
							result.put(vm.getName(), (result.get(vm.getName()) + 1)); 
						}else{// no entry
							result.put(vm.getName(),1); //initialise to one
						}
					}
				}//end for setting
		}
		for(InternalComponent comp : dm.getInternalComponents()){		
				Collection<Setting> references = EcoreUtil.UsageCrossReferencer.find(comp, comp.eResource().getResourceSet());			
				logger.debug(comp.getName() + " UsageCrossReferencer size : " + (references == null ? 0 : references.size())); //
				for(Setting setting : references){
					if(setting.getEObject() instanceof InternalComponentInstance){
						if(result.containsKey(comp.getName())){
							result.put(comp.getName(), (result.get(comp.getName()) + 1)); 
						}else{// no entry
							result.put(comp.getName(),1); //initialise to one
						}
					}
				}//end for setting
		}
		for(Hosting hosting : dm.getHostings()){
				Collection<Setting> references = EcoreUtil.UsageCrossReferencer.find(hosting, hosting.eResource().getResourceSet());			
				logger.debug(hosting.getName() + " UsageCrossReferencer size : " + (references == null ? 0 : references.size())); //
				for(Setting setting : references){
					if(setting.getEObject() instanceof HostingInstance){
						if(result.containsKey(hosting.getName())){
							result.put(hosting.getName(), (result.get(hosting.getName()) + 1)); 
						}else{// no entry
							result.put(hosting.getName(),1); //initialise to one
						}
					}
				}//end for setting
			}
		for(Communication comm : dm.getCommunications()){ 
				Collection<Setting> references = EcoreUtil.UsageCrossReferencer.find(comm, comm.eResource().getResourceSet());			
				logger.debug(comm.getName() + " UsageCrossReferencer size : " + (references == null ? 0 : references.size())); //
				for(Setting setting : references){
					if(setting.getEObject() instanceof CommunicationInstance){
						if(result.containsKey(comm.getName())){
							result.put(comm.getName(), (result.get(comm.getName()) + 1)); 
						}else{// no entry
							result.put(comm.getName(),1); //initialise to one
						}
					}
				}//end for setting
		}	
		logger.debug(" Breakdown of instance count by instance:");
		for(String key : result.keySet()){
			logger.debug(key + " : " + result.get(key));
		}				
		return result;
	}
	/**
	 * 
	 * @param dm	{@link eu.paasage.camel.deployment.DeploymentModel <em>DeploymentModel</em>} to process.
	 * @return 		A {@link java.util.Map <em>map</em>} of instance count by type.
	 * @throws 		{@link eu.paasage.upperware.plangenerator.exception.ModelUtilException <em>ModelUtilException</em>} 
	 * 				if {@link eu.paasage.camel.deployment.DeploymentModel <em>DeploymentModel</em>} is null. 
	 */
	public static Map<String, Integer> getHostingInstanceCountByTypes(DeploymentModel dm) throws ModelUtilException{
		Map<String, Integer> result = new Hashtable<String,Integer>();
		if(dm == null){
			throw new ModelUtilException("DeploymentModel is null!");
		}
		//debug
		logger.debug("HostingInstance count: " + dm.getHostingInstances().size());
		//
		for(Hosting hosting : dm.getHostings()){
			Collection<Setting> references = EcoreUtil.UsageCrossReferencer.find(hosting, hosting.eResource().getResourceSet());			
			logger.debug(hosting.getName() + " UsageCrossReferencer size : " + (references == null ? 0 : references.size())); //
			for(Setting setting : references){
				if(setting.getEObject() instanceof HostingInstance){
					if(result.containsKey(hosting.getName())){
						result.put(hosting.getName(), (result.get(hosting.getName()) + 1)); 
					}else{// no entry
						result.put(hosting.getName(),1); //initialise to one
					}
				}
			}//end for setting
		}
		return result;
	}
	/**
	 * Get all {@link eu.paasage.camel.deployment.InternalComponent <em>InternalComponent</em>} that has a mandatory 
	 * {@link eu.paasage.camel.deployment.RequiredCommunication <em>RequiredCommunication</em>}.
	 * <p>
	 * @param ics	A {@link java.util.List <em>List</em>} of {@link eu.paasage.camel.deployment.InternalComponent <em>InternalComponent</em>} to check
	 * @return	A {@link java.util.List <em>List</em>} of {@link eu.paasage.camel.deployment.InternalComponent <em>InternalComponent</em>} that contains
	 * 			mandatory {@link eu.paasage.camel.deployment.RequiredCommunication <em>RequiredCommunication</em>}
	 */
	public static List<InternalComponent> getICwithMandatoryCommunicationRequirement(List<InternalComponent> ics){
		List<InternalComponent> candidateICs = new ArrayList<InternalComponent>();
		
		for(InternalComponent ic : ics){
			List<RequiredCommunication> rcs = ic.getRequiredCommunications();
			if(rcs == null || rcs.isEmpty()){
				logger.debug("No requiredCommunication for " + ic);
			}else{
				for(RequiredCommunication rc : rcs){
					if(rc.isIsMandatory()){
						candidateICs.add(ic);
						break; //it is enough just to find one
					}
				}
			}
		}
		if(!candidateICs.isEmpty()){
			logger.debug("Found " + candidateICs.size() + " internalComponent with mandatory required communication/s...");			
		}
		return candidateICs;
	}
	/**
	 * Get all mandatory {@link eu.paasage.camel.deployment.RequiredCommunication <em>RequiredCommunication</em>} defined in the
	 * target {@link eu.paasage.camel.deployment.InternalComponent <em>InternalComponent</em>}.
	 * <p>
	 * @param ic	target {@link eu.paasage.camel.deployment.InternalComponent <em>InternalComponent</em>}.
	 * @return		a {@link java.util.List <em>List</em>} of mandatory {@link eu.paasage.camel.deployment.RequiredCommunication <em>RequiredCommunication</em>}
	 * 				or an empty {@link java.util.List <em>List</em>}.
	 */
	public static List<RequiredCommunication> getMandatoryReqComms(InternalComponent ic){
		List<RequiredCommunication> rcs = ic.getRequiredCommunications();
		if(rcs.isEmpty()){ //I think camel returns an empty list
			logger.debug("No requiredCommunication for " + ic.getName());
		}else{
			for(RequiredCommunication rc : rcs){
				if(rc.isIsMandatory()){
					rcs.add(rc);
				}
			}
		}//end if required comm
		return rcs;
	}
	/**
	 * Get all the {@link eu.paasage.camel.deployment.InternalComponentInstance <em>InternalComponentInstance</em>} that
	 * provides the target mandatory {@link eu.paasage.camel.deployment.RequiredCommunication <em>RequiredCommunication</em>}.
	 * Only return those communication provider instances that are not also the communication instance consumers.
	 * <p>
	 * @param rci	the target mandatory {@link eu.paasage.camel.deployment.RequiredCommunication <em>RequiredCommunication</em>} object
	 * @param cis	a {@link java.util.List <em>List</em>} of candidate {@link eu.paasage.camel.deployment.CommunicationInstance <em>CommunicationInstance</em>}
	 * @return	a {@link java.util.Set <em>Set</em>} of {@link eu.paasage.camel.deployment.InternalComponentInstance <em>InternalComponentInstance</em>}
	 * 			or an empty {@link java.util.Set <em>Set</em>}
	 */
	@SuppressWarnings("rawtypes")
	public static Set getProviderIC(RequiredCommunicationInstance rci, List<CommunicationInstance> cis){
		Set<InternalComponentInstance> icis = new HashSet<InternalComponentInstance>();//could have more than 1 provider, see LSY-mini 
		for(CommunicationInstance ci : cis){
			if(ci.getRequiredCommunicationInstance().equals(rci)){
				logger.debug("Found provider binding in " + ci.getName() + "for " + rci.getName());
				if(ci.getProvidedCommunicationInstance().eContainer() instanceof InternalComponentInstance  &&
						!ci.getProvidedCommunicationInstance().eContainer().equals(rci.eContainer())){
					icis.add((InternalComponentInstance) ci.getProvidedCommunicationInstance().eContainer());
				}
			}
		}
		return icis;
	}
	
	
	/**
	 * Find the target {@link eu.paasage.camel.deployment.InternalComponent <em>InternalComponent</em>} and check it contains 
	 * a mandatory {@link eu.paasage.camel.deployment.RequiredCommunication <em>RequiredCommunication</em>}.
	 * <p>
	 * @param targetIC	the target {@link eu.paasage.camel.deployment.InternalComponent <em>InternalComponent</em>}
	 * @param ics		the {@link java.util.List <em>List</em>} of {@link eu.paasage.camel.deployment.InternalComponent <em>InternalComponent</em>} 
	 * 					to check
	 * @return			true if it does, else false
	 */
	public static boolean hasMandatoryComm(InternalComponent targetIC, List<InternalComponent> ics){
		boolean result = false;
		for(InternalComponent ic : ics){
			if(ic.equals(targetIC)){ //if it is the same object
				logger.debug("comparing " + ic.getName() + " against target " + targetIC.getName());
				List<RequiredCommunication> rcs = ic.getRequiredCommunications();
				if(rcs == null || rcs.isEmpty()){
					logger.debug("No requiredCommunication for " + ic);
				}else{
					for(RequiredCommunication rc : rcs){
						if(rc.isIsMandatory()){
							result = true;
							break; //it is enough just to find one
						}
					}
				}//end if required comm
				break; //found IC
			}//end if same object			
		}//end for
		return result;
	}
	/*
	public static Set<ProvidedCommunication> getMandatoryProvidedComm(InternalComponent ic, List<Communication> comms){
		Set<ProvidedCommunication> providers = new HashSet<ProvidedCommunication>();
		logger.debug("Checking for mandatory communication provider for " + ic.getName());
		//
		List<RequiredCommunication> rcs = ic.getRequiredCommunications();
		for(RequiredCommunication rc : rcs){
			if(rc.isIsMandatory()){
				//look for the provider, go through the list
				for(Communication comm : comms){
					if(comm.getRequiredCommunication().equals(rc)){
						providers.add(comm.getProvidedCommunication());
					}
				}
			}
		}
		if(!providers.isEmpty()){
			logger.debug("Found " + providers.size() + " mandatory provided communication... ");
		}
		return providers;
	}
	
	public static Set<InternalComponent> getCommProvider(ProvidedCommunication pc, List<InternalComponent> ics){
		
		Set<InternalComponent> candidates = new HashSet<InternalComponent>();
		//
		for(InternalComponent ic : ics){
			List<ProvidedCommunication> pcs = (ic.getProvidedCommunications());
			for(ProvidedCommunication ppc : pcs){
				if(ppc.equals(pc)){
					candidates.add(ic);
				}
			}
		}
		if(!candidates.isEmpty()){
			logger.debug("Found " + candidates.size() + " provider for " + pc.getName());
		}
		return candidates;
	}
	*/
}
