/*
 * Copyright (c) 2014 UK Science and Technology Facilities Council
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.plangenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;

import com.eclipsesource.json.JsonArray;

import eu.paasage.camel.deployment.Communication;
import eu.paasage.camel.deployment.CommunicationInstance;
import eu.paasage.camel.deployment.Component;
import eu.paasage.camel.deployment.ComponentInstance;
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
import eu.paasage.camel.provider.Attribute;
import eu.paasage.camel.requirement.LocationRequirement;
import eu.paasage.camel.type.SingleValue;
import eu.paasage.upperware.plangenerator.exception.ModelComparatorException;
import eu.paasage.upperware.plangenerator.util.ModelToJsonConverter;
import eu.paasage.upperware.plangenerator.util.ModelUtil;

/**
 * A class to compare two {@link eu.paasage.camel.CamelModel <em>CamelModel</em>}s
 * focusing on deployment related artifacts.
 * Based on a prototype developed by INRIA, INSA Rennes and the CloudMLModelComparator from 
 * http://cloudml.org.
 * <p> * 
 * @author Shirley Crompton 
 * org UK Science and Technology Facilities Council
 */
@Deprecated
public class ModelComparator {	
	/************************************************************************
	 * Use DeploymentModelComparator (SYC: 25April2016)
	 ************************************************************************/

	/** Message logger */
	private static final Logger logger = Logger.getLogger(ModelComparator.class.getName());
	
	///////////////////////////////VM Type///////////////////////////////////////
	/**
	* A {@link java.util.Map <em>map</em>} of {@link eu.paasage.camel.deployment.VM <em>VM</em>}
	* that satisfy the stated requirements
	*/
	private Map<VM, VM> matchedVMTypes = new Hashtable<VM, VM>();
	/**
	* A {@link java.util.Map <em>map</em>} of {@link eu.paasage.camel.deployment.VM <em>VM</em>}
	* removed in the comparison
	*/
	private List<VM> removedVMTypes = new ArrayList<VM>();
	/**
	* A {@link java.util.Map <em>map</em>} of {@link eu.paasage.camel.deployment.VM <em>VM</em>}
	* added in the comparison
	*/
	private List<VM> addedVMTypes = new ArrayList<VM>();
	
	/**
	* A {@link java.util.Map <em>map</em>} of {@link eu.paasage.camel.deployment.VM <em>VM</em>}
	* with updated attributes
	*/
	private List<VM> updatedVMTypes = new ArrayList<VM>();
		
	
	///////////////////////////////VMInstance///////////////////////////////////////
	/**
	 * A {@link java.util.Map <em>map</em>} of {@link eu.paasage.camel.deployment.VMInstance <em>VMInstance</em>}
	 * that satisfy the stated requirements
	 */
	private Map<VMInstance, VMInstance> matchedVMInstances = new Hashtable<VMInstance, VMInstance>();
	/**
	 * A {@link java.util.Map <em>map</em>} of {@link eu.paasage.camel.deployment.VMInstance <em>VMInstance</em>}
	 * removed in the comparison
	 */
	private List<VMInstance> removedVMInstances = new ArrayList<VMInstance>();
	/**
	 * A {@link java.util.Map <em>map</em>} of {@link eu.paasage.camel.deployment.VMInstance <em>VMInstance</em>}
	 * added in the comparison
	 */
	private List<VMInstance> addedVMInstances = new ArrayList<VMInstance>();
	
	/**
	 * A {@link java.util.Map <em>map</em>} of {@link eu.paasage.camel.deployment.VMInstance <em>VMInstance</em>}
	 * with updated attributes
	*/
	private List<VMInstance> updatedVMInstances = new ArrayList<VMInstance>(); 
	
	////////////////////////////////InternalComponent Type////////////////////////////	
	///LifecycleComponent is the Configuration element in the Component
	/**
	* A {@link java.util.Map <em>map</em>} of matching {@link
	* eu.paasage.camel.deployment.InternalComponent <em>InternalComponent</em>} found by the comparison
	*/
	private Map<InternalComponent, InternalComponent> matchedInternalComponents = new Hashtable<InternalComponent, InternalComponent>();
	/**
	* A {@link java.util.List <em>List</em>} of {@link
	* eu.paasage.camel.deployment.InternalComponent <em>InternalComponent</em>} removed in the
	* comparison
	*/
	private List<InternalComponent> removedInternalComponents = new ArrayList<InternalComponent>();
	/**
	* A {@link java.util.List <em>List</em>} of {@link eu.paasage.camel.deployment.InternalComponent <em>InternalComponent</em>} 
	* added in the comparison
	*/
	private List<InternalComponent> addedInternalComponents = new ArrayList<InternalComponent>();
	/**
	* A {@link java.util.List <em>List</em>} of {@link eu.paasage.camel.deployment.InternalComponent <em>InternalComponent</em>} 
	* with updated attributes
	*/
	private List<InternalComponent> updatedInternalComponents = new ArrayList<InternalComponent>();	
	
	
	////////////////////////////////InternalComponentInstance////////////////////////////	
	/**
	 * A {@link java.util.Map <em>map</em>} of matching {@link
	 * eu.paasage.camel.deployment.InternalComponentInstance <em>InternalComponentInstance</em>} found by the comparison
	 */
	private Map<InternalComponentInstance, InternalComponentInstance> matchedInternalComponentIns = new Hashtable<InternalComponentInstance, InternalComponentInstance>();
	/**
	 * A {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.InternalComponentInstance <em>InternalComponentInstance</em>} removed in the
	 * comparison
	 */
	private List<InternalComponentInstance> removedInternalComponentIns = new ArrayList<InternalComponentInstance>();
	/**
	 * A {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.InternalComponentInstance <em>InternalComponentInstance</em>} added in the
	 * comparison
	 */
	private List<InternalComponentInstance> addedInternalComponentIns = new ArrayList<InternalComponentInstance>();
	/**
	 * A {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.InternalComponentInstance <em>InternalComponentInstance</em>} with updated attributes
	 */
	private List<InternalComponentInstance> updatedInternalComponentIns = new ArrayList<InternalComponentInstance>();	
	
	
	////////////////////////////////HostingInstance////////////////////////////
	///hosting covers component hosting and VM hosting
	/**
	 * A {@link java.util.Map <em>map</em>} of matching {@link
	 * eu.paasage.camel.deployment.HostingInstance <em>HostingInstance</em>} found in the comparison
	 */
	private Map<HostingInstance, HostingInstance> matchedHostingInstances = new Hashtable<HostingInstance, HostingInstance>();
	/**
	 * A {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.HostingInstance <em>HostingInstance</em>} added in the comparison
	 */
	private List<HostingInstance> addedHostingInstances = new ArrayList<HostingInstance>();
	/**
	 * A {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.HostingInstance <em>HostingInstance</em>} removed in the comparison
	 */
	private List<HostingInstance> removedHostingInstances = new ArrayList<HostingInstance>();	
	/**
	 * A {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.HostingInstance <em>HostingInstance</em>} with changed attributes
	 */
	private List<HostingInstance> updatedHostingInstances = new ArrayList<HostingInstance>();
	
	////////////////////////////////Hosting////////////////////////////	
	/**
	 * A {@link java.util.Map <em>map</em>} of matching {@link
	 * eu.paasage.camel.deployment.Hosting <em>Hosting</em>} found in the comparison
	 */
	private Map<Hosting, Hosting> matchedHostings = new Hashtable<Hosting, Hosting>();
	/**
	 * A {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.Hosting <em>Hosting</em>} added in the comparison
	 */
	private List<Hosting> addedHostings = new ArrayList<Hosting>();
	/**
	 * A {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.Hosting <em>Hosting</em>} removed in the comparison
	 */
	private List<Hosting> removedHostings = new ArrayList<Hosting>();
	/**
	 * A {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.Hosting <em>Hosting</em>} with updated attributes
	 */
	private List<Hosting> updatedHostings = new ArrayList<Hosting>();
	
	
	////////////////////////////////CommunicationInstance////////////////////////////	
	//Execution Ware sets communication directly for a component itself
	/**
	 * A {@link java.util.Map <em>map</em>} of matching {@link
	 * eu.paasage.camel.deployment.CommunicationInstance <em>CommunicationInstance</em>} found in the
	 * comparison
	 */
	private Map<CommunicationInstance, CommunicationInstance> matchedComInstances = new Hashtable<CommunicationInstance, CommunicationInstance>();
	/**
	 * A {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.CommunicationInstance <em>CommunicationInstance</em>} added in the
	 * comparison
	 */
	private List<CommunicationInstance> addedComInstances = new ArrayList<CommunicationInstance>();
	/**
	 * A {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.CommunicationInstance <em>CommunicationInstance</em>} removed in the
	 * comparison
	 */
	private List<CommunicationInstance> removedComInstances = new ArrayList<CommunicationInstance>();
	/**
	 * A {@link java.util.List <em>List</em>} {@link
	 * eu.paasage.camel.deployment.CommunicationInstance <em>CommunicationInstance</em>} with updated attributes
	 */
	private List<CommunicationInstance> updatedComInstances = new ArrayList<CommunicationInstance>();
	
	////////////////////////////////Communication type////////////////////////////
	/**
	 * A {@link java.util.Map <em>map</em>} of matching {@link
	 * eu.paasage.camel.deployment.Communication <em>Communication</em>} found in the
	 * comparison
	 */
	private Map<Communication, Communication> matchedCommunications = new Hashtable<Communication, Communication>();
	/**
	 * A {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.Communication <em>Communication</em>} added in the
	 * comparison
	 */
	private List<Communication> addedCommunications = new ArrayList<Communication>();
	/**
	 * A {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.Communication <em>Communication</em>} removed in the
	 * comparison
	 */
	private List<Communication> removedCommunications = new ArrayList<Communication>();
	/**
	 * A {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.Communication <em>Communication</em>} with updated attributes
	 */
	private List<Communication> updatedCommunications = new ArrayList<Communication>();
	
	//////////////////12Jan2016 to cope with the implied orphan communication objects//////////////////W
	/**
	 * A {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.ProvidedCommunication <em>ProvidedCommunication</em>} added in the
	 * comparison
	 */
	private List<ProvidedCommunication> addedOrphanCommunications = new ArrayList<ProvidedCommunication>();
	/**
	 * A {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.ProvidedCommunication <em>ProvidedCommunication</em>} with updated attributes
	 */
	private List<ProvidedCommunication> updatedOrphanCommunications = new ArrayList<ProvidedCommunication>();
	/**
	 * A {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.ProvidedCommunication <em>ProvidedCommunication</em>} removed in the
	 * comparison
	 */
	private List<ProvidedCommunication> removedOrphanCommunications = new ArrayList<ProvidedCommunication>();
	
	
	///////////////////////////////Internal Component Configuration (Lifecycle component)///////////////////
	/**
	 * A {@link java.util.List <em>List</em>} of {@link eu.paasage.camel.deployment.Configuration <em>Configuration</em>}
	 * added in the comparison 
	 
	private List<Configuration> addedConfigurations = new ArrayList<Configuration>();
	/**
	 * A {@link java.util.List <em>List</em>} of {@link eu.paasage.camel.deployment.Configuration <em>Configuration</em>}
	 * removed in the comparison 
	 
	private List<Configuration> removedConfigurations = new ArrayList<Configuration>();
	/**
	 * A {@link java.util.List <em>List</em>} of {@link eu.paasage.camel.deployment.Configuration <em>Configuration</em>}
	 * with changed attributes
	
	private List<Configuration> updatedConfigurations = new ArrayList<Configuration>();
		
	///////////////////////	
	/** The current {@link eu.paasage.camel.deployment.DeploymentModel <em>DeploymentModel</em>} */
	private DeploymentModel currentDM = null;
	/** The target {@link eu.paasage.camel.deployment.DeploymentModel <em>DeploymentModel</em>} */
	private DeploymentModel targetDM = null;

	/** Create an instance 
	 * @param current {@link eu.paasage.camel.deployment.DeploymentModel <em>DeploymentModel</em>}
	 * @param target {@link eu.paasage.camel.deployment.DeploymentModel <em>DeploymentModel</em>}
	 * */
	public ModelComparator(DeploymentModel current, DeploymentModel target) {
		this.currentDM = current;
		this.targetDM = target;
		this.clean();	
	}

	/**
	 * Set the target {@link eu.paasage.camel.deployment.DeploymentModel <em>DeploymentModel</em>} and
	 * reinitialise the result objects
	 * @param target	the target {@link eu.paasage.camel.deployment.DeploymentModel <em>DeploymentModel</em>}
	 */
	public void setTargetModel(DeploymentModel target) {
		this.targetDM = target;
		this.clean();
	}

	/**
	 * Set the target {@link eu.paasage.camel.deployment.DeploymentModel <em>DeploymentModel</em>} and
	 * reinitialise the result objects
	 * @param current 	the current {@link eu.paasage.camel.deployment.DeploymentModel <em>DeploymentModel</em>}
	 */
	public void setCurrentModel(DeploymentModel current) {
		this.currentDM = current;
		this.clean();
	}

	/**
	 * Compare the target {@link eu.paasage.camel.deployment.DeploymentModel <em>DeploymentModel</em>}
	 * to the current {@link eu.paasage.camel.deployment.DeploymentModel <em>DeploymentModel</em>}.
	 * <p>
	 * It is assumed that the model only contains a single {@link eu.paasage.camel.deployment.DeploymentModel}.
	 * @throws ModelComparatorException on processing error
	 */
	public void compareModels() throws ModelComparatorException {
		// compareModels is only called when there are current and target models
		// the processing order is based on the dependencies between the objects
		// debug
		logger.debug("Comparing current(" + currentDM.getName()
				+ ") and target(" + targetDM.getName() + ")");		
		//23July2015 it is assumed that the Application type and instance are being updated

		//compare the hosting types
		compareHostings();
		logger.debug(">> Removed hosting types size :" + removedHostings.size());
		logger.debug(">> Added hosting types  size :" + addedHostings.size());
		logger.debug(">> Updated hosting types size : " + updatedHostings.size());
		// compare the HostingInstance
		compareHostingInstances();
		logger.debug(">> Removed HostingInstances size :" + removedHostingInstances.size());
		logger.debug(">> Added HostingInstances size :" + addedHostingInstances.size());
		logger.debug(">> Updated HostingInstances size :" + updatedHostingInstances.size());				
		//compare the communication types
		compareCommunications();
		logger.debug(">> Removed communication types size :" + removedCommunications.size());
		logger.debug(">> Added communication types  size :" + addedCommunications.size());
		logger.debug(">> Updated communication types size : " + updatedCommunications.size());
		// compare the CommunicationInstance
		compareCommunicationInstances();
		logger.debug(">> Removed Communication Instances size :" + removedComInstances.size());
		logger.debug(">> Added Communications Instances size :" + addedComInstances.size());
		//LOGGER.debug(">> Updated Communications Instances size :" + updatedComInstances.size());
		//compare the VM types
		compareVMTypes();
		logger.debug(">> Removed VM types size :" + removedVMTypes.size());
		logger.debug(">> Added VM types  size :" + addedVMTypes.size());
		logger.debug(">> Updated VM types size : " + updatedVMTypes.size());
		//compare the VMInstance
		compareVMInstances();
		logger.debug(">> Removed VMInstances size :" + removedVMInstances.size());
		logger.debug(">> Added VMInstances  size :" + addedVMInstances.size());
		logger.debug(">> Updated VMInstances size : " + updatedVMInstances.size());		
		compareInternalComponents();
		logger.debug(">> Removed InternalComponents size :" + removedInternalComponents.size());
		logger.debug(">> Added InternalComponents  size :" + addedInternalComponents.size());
		logger.debug(">> Updated InternalComponents size : " + updatedInternalComponents.size());
		//LOGGER.debug(">> Removed Configurations size :" + removedConfigurations.size());
		//LOGGER.debug(">> Added Configurations  size :" + addedConfigurations.size());
		//LOGGER.debug(">> Updated Configurations size : " + updatedConfigurations.size());
		//compare the ComponentInstance
		compareInternalComponentInstances();
		logger.debug(">> Removed internal component instances size : " + removedInternalComponentIns.size());
		logger.debug(">> Added internal component instances size : " + addedInternalComponentIns.size());
		logger.debug(">> Updated internal component instances size : " + updatedInternalComponentIns.size());
		//////////////12Jan2016 process orphan communications.  Must be done AFTER compareInternalComponents()
		compareOrphanCommunications();
		logger.debug(">> Removed OrphanCommunications size :" + removedOrphanCommunications.size());
		logger.debug(">> Added OrphanCommunications  size :" + addedOrphanCommunications.size());
		logger.debug(">> Updated OrphanCommunications size : " + updatedOrphanCommunications.size());
	}
	
	/**
	 * Compares the {@link eu.paasage.camel.deployment.VM <em>VM</em>} between the
	 * target and the current deployment model 
	 */
	public void compareVMTypes(){		
		//VMS in DeploymentModel		
		logger.info(">> Comparing vm types ...");
		Boolean match = false;		
		List<VM> curVMs = this.currentDM.getVms();
		List<VM> tarVMs = this.targetDM.getVms();
		if (curVMs != null && !curVMs.isEmpty()) {
			//System.out.println("currentDM has " + currentVMs.size() + " VMs");
			logger.debug("currentDM has " + curVMs.size() + " VMs");
			if (tarVMs != null && !tarVMs.isEmpty()) {
				logger.debug("targetDM has " + tarVMs.size() + " VMs");
				System.out.println("targetDM has " + tarVMs.size() + " VMs");
				// now compare
				for (VM ni : curVMs) {
					// for each current VM type, match with the target type
					match = false; //re-initialise					
					for (VM ni2 : tarVMs) {
						if (equalVM(ni, ni2)) {	//the current one is found in the target
							match = true;
							logger.debug("adding matched VM: " + ni.getName() + " to matchedVMs");
							matchedVMTypes.put(ni, ni2);	//key = current, value = target
							break; // go to next current VM
						}
					}// end for each target VM
					if (!match) {	 
						// if current one not in target
						logger.debug("adding unmatched VM: " + ni.getName() + " to removedVMs");
						removedVMTypes.add(ni); 
					}
				}// end for each currentVMs
				//now find what need to be added
				addedVMTypes.addAll(tarVMs); // first add all instances in the target
				addedVMTypes.removeAll(matchedVMTypes.values()); // subtract the matched types				
			} else {// no target VM, meaning the current ones are all
					// obsolete
				removedVMTypes.addAll(curVMs);
			}// endif tarVMs is not null...
		}// endif curVMs is not null....
		//now check for updates
		if(!matchedVMTypes.isEmpty()){
			for(Map.Entry<VM, VM> entry : matchedVMTypes.entrySet()){
				if(isUpdatedVM(entry.getKey(),entry.getValue())){
					updatedVMTypes.add(entry.getValue());
				}
	        }
		}
	}
	/**
	 * Compares the {@link eu.paasage.camel.deployment.VMInstance <em>VMInstance</em>} in the
	 * target and the current deployment models
	 * @throws ModelComparatorException on processing error
	 */
	public void compareVMInstances() throws ModelComparatorException {
		logger.info(">> Comparing vm instances ...");
		Boolean match = false;		
		List<VMInstance> currentVMInstances = this.currentDM.getVmInstances(); 
		List<VMInstance> targetVMInstances = this.targetDM.getVmInstances();
		//
		if (currentVMInstances != null && !currentVMInstances.isEmpty()) {
			//System.out.println("currentDM has " + currentVMs.size() + " VMInstances");
			logger.debug("currentDM has " + currentVMInstances.size() + " VMInstances");
			if (targetVMInstances != null && !targetVMInstances.isEmpty()) {
				logger.debug("targetDM has " + targetVMInstances.size() + " VMInstances");
				//System.out.println("targetDM has " + targetVMInstances.size() + " VMInstances");
				// now compare
				for (VMInstance ni : currentVMInstances) {
					// for each current VM instance, match with the target instances
					
					
					//debug this NPE issue with VMType
//					Attribute vmType1 = ni.getVmType(); //you will always get an instance but its attributes may not be populated!
//					String vmTypeName1 = (vmType1.getName() == null ? "null" : vmType1.getName());
//					LOGGER.debug("...debug NPE in VMInstance : ni vmType is : " + vmTypeName1 + "....");
					
					
					match = false; //re-initialise					
					for (VMInstance ni2 : targetVMInstances) {
						
						
						//debug this NPE issue with VMType
//						Attribute vmType2 = ni2.getVmType(); //you will always get an instance but its attributes may not be populated!
//						String vmTypeName2 = (vmType2.getName() == null ? "null" : vmType1.getName());
//						LOGGER.debug("...debug NPE in VMInstance : ni2 vmType is : " + vmTypeName2 + "....");
						
						
						if (equalVMInstance(ni, ni2)) {	//the current one is found in the target
							match = true;
							//LOGGER.debug("adding matched VMinstance: " + ni.getName() + " to matchedVMInstances");
							matchedVMInstances.put(ni, ni2);	//key = current, value = target							
							break; // go to next current VM Instance
						}
					}// end for each target VM Instance
					if (!match) {	 
						// if current one not in target
						//LOGGER.debug("adding unmatched VMinstance: " + ni.getName() + " to removedVMInstances");
						removedVMInstances.add(ni); 
					}
				}// end for each currentVMInstance
				//now find what need to be added
				addedVMInstances.addAll(targetVMInstances); // first add all instances in the target
				//LOGGER.debug(matchedVMInstances.values().size() + " matched VMInstance/s & " + addedVMInstances.size() + " addedVMInstances before removal");
				addedVMInstances.removeAll(matchedVMInstances.values()); //subtract the matched instances
				if(addedVMInstances.size() > 0){
					logger.debug("addedVMInstance : " + addedVMInstances.get(0).getName());
				}
			} else {// no target VMInstance, meaning the current ones are all
					// obsolete
				removedVMInstances.addAll(currentVMInstances);
			}// endif targetVMs is not null...
		}// endif currentVMs is not null....
		//now check for updates
		if(!matchedVMInstances.isEmpty()){
			for(Map.Entry<VMInstance, VMInstance> entry : matchedVMInstances.entrySet()){
				if(isUpdatedVMInstance(entry.getKey(),entry.getValue())){
					//LOGGER.debug("about to add updatedVMInstance(" + entry.getValue().getName());
					updatedVMInstances.add(entry.getValue());
				}
	        }
		}
	}
	/**
	 * Compares the {@link eu.paasage.camel.deployment.Hosting <em>Hosting</em>} in
	 * the target and the current deployment models
	 */
	public void compareHostings(){
		logger.info(">> Comparing Hostings ...");
		Boolean match = false;
		//
		List<Hosting> currentHostings = this.currentDM.getHostings();
		List<Hosting> targetHostings = this.targetDM.getHostings();
		//
		if (currentHostings != null && !currentHostings.isEmpty()) {
			logger.debug("currentDM has " + currentHostings.size() + " Hostings");
			if (targetHostings != null && !targetHostings.isEmpty()) {
				logger.debug("targetDM has " + targetHostings.size() + " Hostings");
				// now compare
				for (Hosting ni : currentHostings) {
					// for each current Hosting, match with the target 
					match = false; //re-initialise
					for (Hosting ni2 : targetHostings) {
						if (equalHosting(ni, ni2)) {
							match = true;
							matchedHostings.put(ni, ni2);
							break; // go to next current Hosting
						}
					}// end for each target Hosting
					if (!match) {
						// if current not in target
						removedHostings.add(ni); 
					}
				}// end for each currentHostings
				addedHostings.addAll(targetHostings); // first add all instances in the target
				addedHostings.removeAll(matchedHostings.values());				
			} else {// no target hosting, meaning the current hostings are all
					// obsolete
				removedHostings.addAll(currentHostings);
			}// endif targetHostings is not null...
		}// endif currentHostings is not null....
		//now check for updates
		if(!matchedHostings.isEmpty()){
			for(Map.Entry<Hosting, Hosting> entry : matchedHostings.entrySet()){
				if(isUpdatedHosting(entry.getKey(),entry.getValue())){
					updatedHostings.add(entry.getValue());
				}
	        }
		}		
	}	
	/**
	 * Compares the {@link eu.paasage.camel.deployment.HostingInstance} in
	 * the target and the current deployment models
	 */
	public void compareHostingInstances() {
		logger.info(">> Comparing Hosting Instances ...");
		Boolean match = false;
		//
		List<HostingInstance> currentHostingIns = this.currentDM.getHostingInstances();
		List<HostingInstance> targetHostingIns = this.targetDM.getHostingInstances();
		//
		if (currentHostingIns != null && !currentHostingIns.isEmpty()) {
			logger.debug("currentDM has " + currentHostingIns.size() + " HostingInstances");
			if (targetHostingIns != null && !targetHostingIns.isEmpty()) {
				logger.debug("targetDM has " + targetHostingIns.size() + " HostingInstances");
				// now compare
				for (HostingInstance ni : currentHostingIns) {
					// for each current Hosting instance, match with the target instances
					match = false; //re-initialise
					for (HostingInstance ni2 : targetHostingIns) {
						if (equalHostingInstance(ni, ni2)) {
							match = true;
							matchedHostingInstances.put(ni, ni2);
							break; // go to next current Hosting
						}
					}// end for each target Hosting
					if (!match) {
						// if current not in target
						removedHostingInstances.add(ni); 
					}
				}// end for each currentHostings
				addedHostingInstances.addAll(targetHostingIns); // first add all instances in the target
				addedHostingInstances.removeAll(matchedHostingInstances.values());				
			} else {// no target hosting, meaning the current hosting instances are all
					// obsolete
				removedHostingInstances.addAll(currentHostingIns);
			}// endif targetHostingIns is not null...
		}// endif currentHostingIns is not null....		
		//now check for updates
		if(!matchedHostingInstances.isEmpty()){
			for(Map.Entry<HostingInstance, HostingInstance> entry : matchedHostingInstances.entrySet()){
				if(isUpdatedHostingInstance(entry.getKey(),entry.getValue())){
					updatedHostingInstances.add(entry.getValue());
				}
	        }
		}
	}	
	/**
	 * Compares the {@link eu.paasage.camel.deployment.Communication <em>Communication</em>}
	 * in the target and the current deployment models
	 */
	public void compareCommunications(){
		logger.info(">> Comparing Communications ...");
		//
		Boolean match = false;
		List<Communication> currentComs = this.currentDM.getCommunications();
		List<Communication> targetComs = this.targetDM.getCommunications();
		//
		if (currentComs != null && !currentComs.isEmpty()) {
			logger.debug("currentDM has " + currentComs.size() + " Communication");
			if (targetComs != null && !targetComs.isEmpty()) {
				logger.debug("targetDM has " + targetComs.size() + " Communication");
				// now compare
				for (Communication ni : currentComs) {
					//LOGGER.debug("current Com: " + ni.getName() + " .....");
					// for each current Communication , match with the target Communication
					match = false; //re-initialise
					for (Communication ni2 : targetComs) {
						//LOGGER.debug("...inside second loop...");
						if (equalCommunication(ni, ni2)) {
							match = true;
							//LOGGER.debug("adding matched Communication: " + ni.getName() + " to matchedComs");
							matchedCommunications.put(ni, ni2);							
							break; // go to next current Communication
						}
					}// end for each target Communication
					if (!match) {
						// if current one not in target
						//LOGGER.debug("adding ummatched Communication: " + ni.getName() + " to unmatchedComs");
						removedCommunications.add(ni); 
					}
				}// end for each current Communication				
				addedCommunications.addAll(targetComs); // first add all Communication in the target
				addedCommunications.removeAll(matchedCommunications.values());
			} else {// no target Communication, meaning the current Communications are all
					// obsolete
				removedCommunications.addAll(currentComs);
			}
		}// endif currentCommunications is not null....
		//now check for updates
		if(!matchedCommunications.isEmpty()){
			for(Map.Entry<Communication, Communication> entry : matchedCommunications.entrySet()){
				if(isUpdatedCommunication(entry.getKey(),entry.getValue())){
					updatedCommunications.add(entry.getValue());	//get the target
				}
	        }
		}
	}
	
	/**
	 * Compares the {@link eu.paasage.camel.deployment.CommunicationInstance <em>CommunicationInstance</em>}
	 * in the target and the current deployment models
	 */
	public void compareCommunicationInstances() {
		//ExecutionWare automatically bootstrap communication when deploying the internal component object
		logger.info(">> Comparing Communication Instances ...");
		//
		Boolean match = false;
		List<CommunicationInstance> currentComIns = this.currentDM.getCommunicationInstances();
		List<CommunicationInstance> targetComIns = this.targetDM.getCommunicationInstances();
		//
		if (currentComIns != null && !currentComIns.isEmpty()) {
			logger.debug("currentDM has " + currentComIns.size() + " CommunicationInstances");
			if (targetComIns != null && !targetComIns.isEmpty()) {
				logger.debug("targetDM has " + targetComIns.size() + " CommunicationInstances");
				// now compare
				for (CommunicationInstance ni : currentComIns) {
					//LOGGER.debug("current ComInstance: " + ni.getName() + " .....");
					// for each current Communication instance, match with the target
					// instances
					match = false; //re-initialise
					for (CommunicationInstance ni2 : targetComIns) {
						//LOGGER.debug("...inside second loop...");
						if (equalCommunicationInstance(ni, ni2)) {
							match = true;
							//LOGGER.debug("adding matched ComInstance: " + ni.getName() + " to matchedComInstances");							
							matchedComInstances.put(ni, ni2);
							break; // go to next current Communication instance
						}
					}// end for each target Communication instance
					if (!match) {
						// if current one not in target
						//LOGGER.debug("adding ummatched Com instance: " + ni.getName() + " to unmatchedComIns");
						removedComInstances.add(ni); 
					}
				}// end for each current Communication				
				addedComInstances.addAll(targetComIns); // first add all instances in the target
				addedComInstances.removeAll(matchedComInstances.values());
			} else {// no target Communication instance, meaning the current Communication instances are all
					// obsolete
				removedComInstances.addAll(currentComIns);
			}
		}// endif currentCommunicationIns is not null....		
		//now check for updates
		if(!matchedComInstances.isEmpty()){
			for(Map.Entry<CommunicationInstance, CommunicationInstance> entry : matchedComInstances.entrySet()){
				if(isUpdatedCommunicationInstance(entry.getKey(),entry.getValue())){
					updatedComInstances.add(entry.getValue());
				}
	        }
		}
	}

	/**
	 * Compares the {@link eu.paasage.camel.deployment.InternalComponent <em>InternalComponent</em>} in the
	 * target and the current deployment models
	 */
	public void compareInternalComponents() {
		logger.info(">> Comparing internal components ...");
		Boolean match = false;	
		List<InternalComponent> currentComps = this.currentDM.getInternalComponents();
		List<InternalComponent> targetComps = this.targetDM.getInternalComponents();
		//
		if (currentComps != null && !currentComps.isEmpty()) {
			logger.debug("currentDM has " + currentComps.size() + " InternalComponents");
			if (targetComps != null && !targetComps.isEmpty()) {
				logger.debug("targetDM has " + targetComps.size() + " InternalComponents");
				// now compare
				for (InternalComponent ni : currentComps) {
					// for each current internal component instance, match with the target instances
					match = false; //re-initialise
					
					for (InternalComponent ni2 : targetComps) {
						if (equalComponent(ni, ni2)) {
							match = true;
							//LOGGER.debug("ic1 : " + ni.getName() + ", ic2 : " + ni2.getName());
							matchedInternalComponents.put(ni, ni2);
							break; // go to next current internal component
						}
					}// end for each target internal component
					if (!match) {
						// current one not in target
						removedInternalComponents.add(ni); 
					}
				}// end for each current internal component
				//LOGGER.debug(matchedInternalComponents.size() + " matchedInternalComponent");
				addedInternalComponents.addAll(targetComps); // first add all instances in the target
				addedInternalComponents.removeAll(matchedInternalComponents.values());				
			} else {// no target internal component, meaning the current internal components are all obsolete
				removedInternalComponents.addAll(currentComps);
			}// endif targetInternalComponents is not null...
		}// endif currentInternalComponents is not null....	
		//now check for updates
		if(!matchedInternalComponents.isEmpty()){
			for(Map.Entry<InternalComponent, InternalComponent> entry : matchedInternalComponents.entrySet()){
				if(isUpdatedInternalComponent(entry.getKey(),entry.getValue())){
					updatedInternalComponents.add(entry.getValue());
				}
	        }
		}
	}
	
	/**
	 * Generate the necessary orphan communication objects.  An orphan communication is one provided by an
	 * {@link eu.paasage.camel.deployment.InternalComponent <em>InternalComponent</em>} object but is not
	 * bond to any {@link eu.paasage.camel.deployment.Communication <em>Communication</em>} objects
	 */
	public void compareOrphanCommunications(){
		logger.info(">> Processing orphan communications ...");
		List<Communication> currentCommTypes = this.currentDM.getCommunications();
		//		
		for(InternalComponent ic : this.removedInternalComponents){
			List<ProvidedCommunication> removedPCs = ic.getProvidedCommunications();
			if(removedPCs != null){
				for(ProvidedCommunication dpc : removedPCs){
					if(isOrphanCom(dpc, currentCommTypes)){
						this.removedOrphanCommunications.add(dpc);
						logger.debug("Added " + dpc.getName() + " to removedOrphanCommunications");
					}
				}
			}
		}
		List<Communication> targetCommTypes = this.targetDM.getCommunications();
		for(InternalComponent ic : this.addedInternalComponents){
			List<ProvidedCommunication> addedPCs = ic.getProvidedCommunications();
			if(addedPCs != null){
				for(ProvidedCommunication apc : addedPCs){
					if(isOrphanCom(apc, targetCommTypes)){
						this.addedOrphanCommunications.add(apc);
						logger.debug("Added " + apc.getName() + " to addedOrphanCommunications");
					}
				}
			}
		}
		for(InternalComponent ic : this.updatedInternalComponents){
			List<ProvidedCommunication> updatedPCs = ic.getProvidedCommunications();
			if(updatedPCs != null){
				for(ProvidedCommunication upc : updatedPCs){
					if(isOrphanCom(upc, targetCommTypes)){//the updated IC comes from the target DeploymentModel
						logger.debug("Found orphan communication(" + upc.getName());
						//we found an orphan, but has this been changed?
						//providedCommunication got name and portNumber attributes.  The parent would be the IC itself, not useful
						//need to find out if the orphan communication itself has changed 
						if(isUpdatedOrphanCommunication(upc)){
							this.updatedOrphanCommunications.add(upc);
							logger.debug("Added " + upc.getName() + " to updatedOrphanCommunications");
						}else{
							logger.debug("Orphan communication has not been changed, no action required ....");
						}
					}
				}
			}
		}
	}
	/**
	 * Compares the {@link eu.paasage.camel.deployment.InternalComponentInstance <em>InternalComponentInstance</em>} in the
	 * target and the current deployment models
	 */
	public void compareInternalComponentInstances() {
		logger.info(">> Comparing internal component instances ...");
		Boolean match = false;	
		List<InternalComponentInstance> currentCompIns = this.currentDM.getInternalComponentInstances();
		List<InternalComponentInstance> targetCompIns = this.targetDM.getInternalComponentInstances();
		//
		if (currentCompIns != null && !currentCompIns.isEmpty()) {
			logger.debug("currentDM has " + currentCompIns.size() + " InternalComponentInstances");
			if (targetCompIns != null && !targetCompIns.isEmpty()) {
				logger.debug("targetDM has " + targetCompIns.size() + " InternalComponentInstances");
				// now compare
				for (InternalComponentInstance ni : currentCompIns) {
					// for each current internal component instance, match with the target instances
					match = false; //re-initialise					
					for (InternalComponentInstance ni2 : targetCompIns) {
						if (equalComponentInstance(ni, ni2)) {
							match = true;
							matchedInternalComponentIns.put(ni, ni2);
							break; // go to next current internal component
						}
					}// end for each target internal component
					if (!match) {
						// current one not in target
						removedInternalComponentIns.add(ni); 
					}
				}// end for each current internal component
				addedInternalComponentIns.addAll(targetCompIns); // first add all instances in the target
				addedInternalComponentIns.removeAll(matchedInternalComponentIns.values());
			} else {// no target internal component, meaning the current internal components are all obsolete
				removedInternalComponentIns.addAll(currentCompIns);
			}// endif targetInternalComponents is not null...
		}// endif currentInternalComponents is not null....	
		if(!matchedInternalComponentIns.isEmpty()){
			for(Map.Entry<InternalComponentInstance, InternalComponentInstance> entry : matchedInternalComponentIns.entrySet()){
				if(isUpdatedInternalComponentIns(entry.getKey(),entry.getValue())){
					updatedInternalComponentIns.add(entry.getValue());
				}
	        }
		}		
	}

	
	
	////////////////////////////////////////GETTERS/////////////////////////////////////////////////

	/**
	 * @return the {@link java.util.Map <em>map</em>} of {@link
	 * eu.paasage.camel.deployment.Communication <em>Communication</em>}
	 */
	public Map<Communication, Communication> getMatchedComms() {
		return this.matchedCommunications;
	}

	/**
	 * Getter for the {@link java.util.List <em>List</em>} of removed {@link
	 * eu.paasage.camel.deployment.InternalComponentInstance <em>InternalComponentInstance</em>}
	 * <p>
	 * 
	 * @return the {@link java.util.List <em>List</em>} of removed {@link
	 *         eu.paasage.camel.deployment.InternalComponentInstance <em>InternalComponentInstance</em>}
	 */
	public List<InternalComponentInstance> getRemovedInternalComponentInstances() {
		return this.removedInternalComponentIns;
	}

	/**
	 * Getter for the {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.InternalComponentInstance <em>InternalComponentInstance</em>} added
	 * 
	 * @return the {@link java.util.List <em>List</em>} of {@link
	 *         eu.paasage.camel.deployment.InternalComponentInstance <em>InternalComponentInstance</em>} added
	 */
	public List<InternalComponentInstance> getAddedInternalComponentInstances() {
		return this.addedInternalComponentIns;
	}
	/**
	 * Getter for the {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.InternalComponentInstance <em>InternalComponentInstance</em>} being updated
	 * 
	 * @return the {@link java.util.List <em>List</em>} of {@link
	 *         eu.paasage.camel.deployment.InternalComponentInstance <em>InternalComponentInstance</em>} being updated
	 */
	public List<InternalComponentInstance> getUpdatedInternalComponentInstance() {
		return this.updatedInternalComponentIns;
	}
	/**
	 * Getter for the {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.VMInstance <em>VMInstance</em>} being updated
	 * 
	 * @return for the {@link java.util.List <em>List</em>} of {@link
	 *         eu.paasage.camel.deployment.VMInstance <em>VMInstance</em>} being updated
	 */
	public List<VMInstance> getUpdatedVMInstances() {
		return this.updatedVMInstances;
	}

	/**
	 * Getter for the {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.VMInstance} removed
	 * 
	 * @return the {@link java.util.List} of {@link
	 *         eu.paasage.camel.deployment.VMInstance} removed
	 */
	public List<VMInstance> getRemovedVMInstances() {
		return this.removedVMInstances;
	}

	/**
	 * Getter for the {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.VMInstance <em>VMInstance</em>} added
	 * 
	 * @return the {@link java.util.List <em>List</em>} of {@link
	 *         eu.paasage.camel.deployment.VMInstance <em>VMInstance</em>} added
	 */
	public List<VMInstance> getAddedVMInstances() {
		return this.addedVMInstances;
	}

	/**
	 * Getter for the {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.HostingInstance <em>HostingInstance</em>} removed
	 * 
	 * @return for the {@link java.util.List} of {@link
	 *         eu.paasage.camel.deployment.HostingInstance <em>HostingInstance</em>} removed
	 */
	public List<HostingInstance> getRemovedHostingInstances() {
		return this.removedHostingInstances;
	}

	/**
	 * Getter for the {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.HostingInstance <em>HostingInstance</em>} added
	 * 
	 * @return the {@link java.util.List <em>List</em>} of {@link
	 *         eu.paasage.camel.deployment.HostingInstance <em>HostingInstance</em>} added
	 */
	public List<HostingInstance> getAddedHostingInstances() {
		return this.addedHostingInstances;
	}
	/**
	 * Getter for the {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.HostingInstance <em>HostingInstance</em>} being updated
	 * 
	 * @return for the {@link java.util.List <em>List</em>} of {@link
	 *         eu.paasage.camel.deployment.HostingInstance <em>HostingInstance</em>} being updated
	 */
	public List<HostingInstance> getUpdatedHostingInstances() {
		return this.updatedHostingInstances;
	}


	/**
	 * Getter for the {@link java.util.Map} of matching {@link
	 * eu.paasage.camel.deployment.HostingInstance <em>HostingInstance</em>}
	 * 
	 * @return the {@link java.util.Map} of matching {@link
	 *         eu.paasage.camel.deployment.HostingInstance <em>HostingInstance</em>}
	
	public Map<HostingInstance, HostingInstance> getMatchedHostings() {
		return this.matchedHostingInstances;
	}

	/**
	 * Getter for the {@link java.util.Map <em>Map</em>} of matching {@link
	 * eu.paasage.camel.deployment.InternalComponentInstance <em>InternalComponentInstance</em>}
	 * 
	 * @return the {@link java.util.Map <em>Map</em>} of matching {@link
	 *         eu.paasage.camel.deployment.InternalComponentInstance <em>InternalComponentInstance</em>}
	 
	public Map<InternalComponentInstance, InternalComponentInstance> getMatchedComponents() {
		return this.matchedInternalComponentIns;
	}

	/**
	 * Getter for the {@link java.util.Map <em>Map</em>} of matching {@link
	 * eu.paasage.camel.deployment.VMInstance}
	 * 
	 * @return the {@link java.util.Map <em>Map</em>} of matching {@link
	 *         eu.paasage.camel.deployment.VMInstance <em>VMInstance</em>}
	 
	public Map<VMInstance, VMInstance> getMatchedVMInstances() {
		return this.matchedVMInstances;
	}

	/**
	 * @return the matched {@link
	 *         eu.paasage.camel.deployment.Communication <em>Communication</em>} 
	 
	public Map<Communication, Communication> getMatchedCommunications() {
		return matchedCommunications;
	}*/

	/**
	 * Getter for the {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.Communication <em>Communication</em>} added
	 * 
	 * @return the {@link java.util.List <em>List</em>} of {@link
	 *         eu.paasage.camel.deployment.Communication <em>Communication</em>} added
	 */
	public List<Communication> getAddedCommunications() {
		return this.addedCommunications;
	}
	/**
	 * Getter for the {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.CommunicationInstance <em>CommunicationInstance</em>} added
	 * 
	 * @return the {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.CommunicationInstance <em>CommunicationInstance</em>}
	 */
	public List<CommunicationInstance> getAddedComInstances() {
		return addedComInstances;
	}

	/**
	 * Getter for the {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.Communication <em>Communication</em>} removed
	 * 
	 * @return for the {@link java.util.List <em>List</em>} of {@link
	 *         eu.paasage.camel.deployment.CommunicationInstance <em>Communication</em>} removed
	 */
	public List<Communication> getRemovedCommunications() {
		return this.removedCommunications;
	}
	/**
	 * Getter for the {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.CommunicationInstance <em>CommunicationInstance</em>} removed
	 * <p>
	 * @return the removed Communication Instances
	 */
	public List<CommunicationInstance> getRemovedComInstances() {
		return removedComInstances;
	}

	/**
	 * Getter for the {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.Communication <em>Communication</em>} being updated
	 * 
	 * @return for the {@link java.util.List <em>List</em>} of {@link
	 *         eu.paasage.camel.deployment.Communication <em>Communication</em>} being updated
	 */
	public List<Communication> getUpdatedCommunications() {
		return this.updatedCommunications;
	}
	
	/**
	 * Getter for the {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.CommunicationInstance <em>CommunicationInstance</em>} being updated
	 * 
	 * @return the {@link eu.paasage.camel.deployment.CommunicationInstance <em>CommunicationInstance</em>}
	 */
	public List<CommunicationInstance> getUpdatedComInstances() {
		return updatedComInstances;
	}	
	/**
	 * Getter for the {@link java.util.List <em>List</em>} of orphan communication objects added
	 * 
	 * @return for the {@link java.util.List <em>List</em>} of orphan communication objects added
	 */
	public List<ProvidedCommunication> getAddedOrphanCommunications() {
		return this.addedOrphanCommunications;
	}
	/**
	 * Getter for the {@link java.util.List <em>List</em>} of orphan communication objects updated
	 * 
	 * @return for the {@link java.util.List <em>List</em>} of orphan communication objects updated
	 */
	public List<ProvidedCommunication> getUpdatedOrphanCommunications() {
		return this.updatedOrphanCommunications;
	}
	/**
	 * Getter for the {@link java.util.List <em>List</em>} of orphan communication objects removed
	 * 
	 * @return for the {@link java.util.List <em>List</em>} of orphan communication objects removed
	 */
	public List<ProvidedCommunication> getRemovedOrphanCommunications() {
		return this.removedOrphanCommunications;
	}
	/**
	 * Getter for the {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.VM <em>VM</em>} removed
	 * 
	 * @return for the {@link java.util.List <em>List</em>} of {@link
	 *         eu.paasage.camel.deployment.VM <em>VM</em>} removed
	 */
	public List<VM> getRemovedVMTypes() {
		return this.removedVMTypes;
	}
	/**
	 * Getter for the {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.VM <em>VM</em>} addeed
	 * 
	 * @return for the {@link java.util.List <em>List</em>} of {@link
	 *         eu.paasage.camel.deployment.VM <em>VM</em>} addeed
	 */
	public List<VM> getAddedVMTypes() {
		return this.addedVMTypes;
	}

	/**
	 * Getter for the {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.VM <em>VM</em>} being updated
	 * 
	 * @return for the {@link java.util.List <em>List</em>} of {@link
	 *         eu.paasage.camel.deployment.VM <em>VM</em>} being updated
	 */
	public List<VM> getUpdatedVMTypes() {
		return this.updatedVMTypes;
	}
	/**
	 * Getter for the {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.InternalComponent <em>InternalComponent</em>} removed
	 * 
	 * @return for the {@link java.util.List <em>List</em>} of {@link
	 *         eu.paasage.camel.deployment.InternalComponent <em>InternalComponent</em>} removed
	 */
	public List<InternalComponent> getRemovedInternalComponents() {
		return this.removedInternalComponents;
	}
	/**
	 * Getter for the {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.InternalComponent <em>InternalComponent</em>} added
	 * 
	 * @return for the {@link java.util.List <em>List</em>} of {@link
	 *         eu.paasage.camel.deployment.InternalComponent <em>InternalComponent</em>} added
	 */
	public List<InternalComponent> getAddedInternalComponents() {
		return this.addedInternalComponents;
	}	
	/**
	 * Getter for the {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.InternalComponent <em>InternalComponent</em>} being updated
	 * 
	 * @return for the {@link java.util.List <em>List</em>} of {@link
	 *         eu.paasage.camel.deployment.InternalComponent <em>InternalComponent</em>} being updated
	 */
	public List<InternalComponent> getUpdatedInternalComponents() {
		return this.updatedInternalComponents;
	}	
	
	/**
	 * Getter for the {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.Hosting <em>Hosting</em>} added
	 * 
	 * @return for the {@link java.util.List <em>List</em>} of {@link
	 *         eu.paasage.camel.deployment.Hosting <em>Hosting</em>} added
	 */
	public List<Hosting> getAddedHostings() {
		return this.addedHostings;
	}	
	/**
	 * Getter for the {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.Hosting <em>Hosting</em>} removed
	 * 
	 * @return for the {@link java.util.List <em>List</em>} of {@link
	 *         eu.paasage.camel.deployment.Hosting <em>Hosting</em>} removed
	 */
	public List<Hosting> getRemovedHostings() {
		return this.removedHostings;
	}
	/**
	 * Getter for the {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.Hosting <em>Hosting</em>} being updated
	 * 
	 * @return for the {@link java.util.List <em>List</em>} of {@link
	 *         eu.paasage.camel.deployment.Hosting <em>Hosting</em>} being updated
	 */
	public List<Hosting> getUpdatedHostings() {
		return this.updatedHostings;
	}

	/**
	 * Getter for the {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.Configuration <em>Configuration</em>} added
	 * 
	 * @return for the {@link java.util.List <em>List</em>} of {@link
	 *         eu.paasage.camel.deployment.Configuration <em>Configuration</em>} added
	 
	public List<Configuration> getAddedConfigurations() {
		return this.addedConfigurations;
	}*/

	/**
	 * Getter for the {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.Configuration <em>Configuration</em>} removed
	 * 
	 * @return for the {@link java.util.List <em>List</em>} of {@link
	 *         eu.paasage.camel.deployment.Configuration <em>Configuration</em>} removed
	 
	public List<Configuration> getRemovedConfigurations() {
		return this.removedConfigurations;
	}*/

	/**
	 * Getter for the {@link java.util.List <em>List</em>} of {@link
	 * eu.paasage.camel.deployment.Configuration <em>Configuration</em>} being updated
	 * 
	 * @return for the {@link java.util.List <em>List</em>} of {@link
	 *         eu.paasage.camel.deployment.Configuration <em>Configuration</em>} being updated
	 
	public List<Configuration> getUpdatedConfigurations() {
		return this.updatedConfigurations;
	}*/
	
	/**
	 * A utiltiy to check if two {@link eu.paasage.camel.deployment.ProvidedCommunication <em>ProvidedCommunication</em>}
	 * object are the same based on comparing name, port number and the parent container's name.
	 * <p>
	 * @param pc1	first {@link eu.paasage.camel.deployment.ProvidedCommunication <em>ProvidedCommunication</em>}
	 * @param pc2	second {@link eu.paasage.camel.deployment.ProvidedCommunication <em>ProvidedCommunication</em>}
	 * @return	true if considered the same, else false
	 */
	public static boolean equalProvidedCommunication(ProvidedCommunication pc1, ProvidedCommunication pc2){
		//1Dec2015, this is a blotch to fix the orphan provided communication objects so that execution ware would work
		//see PlanGenerator.buildSimpleDeploymentPlan for more details
		boolean match = false;
		if(pc1.getName().equals(pc2.getName()) && pc1.getPortNumber() == pc2.getPortNumber()){
			InternalComponent ic1 = (InternalComponent) pc1.eContainer();
			logger.debug("pc1 parent component is: " + ic1);
			InternalComponent ic2 = (InternalComponent) pc2.eContainer();
			logger.debug("pc2 parent component is: " + ic2);
			if(ic1.getName().equals(ic2.getName())){
				match = true;
			}else{
				logger.debug("...different parent component....");
			}
		}
		return match;
	}

	//////////////////////////////////////////////private methods////////////////////////////////////////////////////////
	/**
	 * Reinitialise all the  {@link java.util.List <em>List</em>}
	 */
	private void clean() {
		
		matchedVMTypes.clear();
		removedVMTypes.clear();
		addedVMTypes.clear();
		updatedVMTypes.clear();
		matchedVMInstances.clear();
		removedVMInstances.clear();
		addedVMInstances.clear();
		updatedVMInstances.clear();
		
		matchedInternalComponents.clear();
		removedInternalComponents.clear();
		addedInternalComponents.clear();
		updatedInternalComponents.clear();
		matchedInternalComponentIns.clear();
		removedInternalComponentIns.clear();
		addedInternalComponentIns.clear();
		updatedInternalComponentIns.clear();

		matchedHostings.clear();
		removedHostings.clear();
		addedHostings.clear();
		updatedHostings.clear();
		matchedHostingInstances.clear();
		removedHostingInstances.clear();
		addedHostingInstances.clear();
		updatedHostingInstances.clear();
		
		matchedCommunications.clear();
		removedCommunications.clear();
		addedCommunications.clear();
		updatedCommunications.clear();
		matchedComInstances.clear();
		removedComInstances.clear();
		addedComInstances.clear();
		updatedComInstances.clear();
		
		addedOrphanCommunications.clear();
		removedOrphanCommunications.clear();
		updatedOrphanCommunications.clear();
	}
	
	/**
	 * Compare if two {@link eu.paasage.camel.deployment.VM <em>VM</em>} are same
	 * <p>
	 * @param vm1	first {@link eu.paasage.camel.deployment.VM <em>VM</em>}
	 * @param vm2	second {@link eu.paasage.camel.deployment.VM <em>VM</em>}
	 * @return true if considered the same, else false
	 */
	private boolean equalVM(VM vm1, VM vm2){
			//other changes are considered as update of the same VM		
			//LOGGER.debug("comparing VM type name : " + vm1.getName() + " and " + vm2.getName());
			return vm1.getName().equals(vm2.getName());
	}
	/**
	 * Verify if a {@link eu.paasage.camel.deployment.VM <em>VM</em>} definition
	 * has been updated.
	 * <p>
	 * @param vm1	first {@link eu.paasage.camel.deployment.VM <em>VM</em>}
	 * @param vm2	second {@link eu.paasage.camel.deployment.VM <em>VM</em>}
	 * @return true 	if they are considered different, else false
	 */
	private boolean isUpdatedVM(VM vm1, VM vm2){
		//24July 2015 check os64bit, os or osImage in RequirementSet.  Location is tied to VM flavour -> VMInstance
		VMRequirementSet current = ModelUtil.addGlobalRequirements(this.currentDM.getGlobalVMRequirementSet(),vm1.getVmRequirementSet());
		VMRequirementSet target =  ModelUtil.addGlobalRequirements(this.targetDM.getGlobalVMRequirementSet(),vm2.getVmRequirementSet());
		HashMap<String, Object> currentHM = ModelToJsonConverter.convertVMRequirementSet(current);
		HashMap<String, Object> targetHM = ModelToJsonConverter.convertVMRequirementSet(target);
		for(Map.Entry<String, Object> rec : currentHM.entrySet()){
			if(rec.getKey().equals("osImage")){	//1 record
				if(!((String) rec.getValue()).equals((String) targetHM.get("osImage"))){
					return true;
				}
			}else if(rec.getKey().equals("os")){
				if(!((String) rec.getValue()).equals((String) targetHM.get("os"))){
					return true;
				}
			}/*else if(rec.getKey().equals("os64bit")){
				if((boolean) rec.getValue() != (boolean) targetHM.get("os64bit")){
					return true;
				} 23Nov15 redundant
			}*/
		}
		//24July2015 no need to check them all
//		if(!equalVMRequirementSet(this.currentCamel.getDeploymentModels().get(0).getGlobalVMRequirementSet(),
//		 this.targetCamel.getDeploymentModels().get(0).getGlobalVMRequirementSet(), vm1.getVmRequirementSet(),
//		 vm2.getVmRequirementSet())){
//			 return true;
//		 }		
		//lifecycle component
		//just check the first one for now, not clear if all configs are current		
		if(!equalConfiguration(getConfiguration(vm1), getConfiguration(vm2))){
			return true;
		}
		//}
		//vm are managed externally, no required*
		//provided communication (the names are compared.  The guts will be checked in the com bindings)
		if(!equalProvidedCommunications(vm1.getProvidedCommunications(),vm2.getProvidedCommunications())){
			return true;		
		}
		if(!equalProvidedHosts(vm1.getProvidedHosts(), vm2.getProvidedHosts())){
			return true;
		}		
		return false; //no change
	}
	/**
	 * Verify if all the VM requirements for both the current and target {@link eu.paasage.camel.deployment.VM <em>VM</em>} 
	 * are the same.
	 * <p> 
	 * @param vm1Global	current {@link eu.paasage.camel.deployment.VM <em>VM</em>} global {@link eu.paasage.camel.deployment.VMRequirementSet <em>VMRequirementSet</em>}
	 * @param vm2Global target {@link eu.paasage.camel.deployment.VM <em>VM</em>} global {@link eu.paasage.camel.deployment.VMRequirementSet <em>VMRequirementSet</em>}
	 * @param vm1ReqSet current {@link eu.paasage.camel.deployment.VM <em>VM</em>} {@link eu.paasage.camel.deployment.VMRequirementSet <em>VMRequirementSet</em>}
	 * @param vm2ReqSet target {@link eu.paasage.camel.deployment.VM <em>VM</em>} {@link eu.paasage.camel.deployment.VMRequirementSet <em>VMRequirementSet</em>}
	 * @return true if they are different, else false
	 */
	private boolean equalVMRequirementSet(VMRequirementSet vm1Global, VMRequirementSet vm2Global, VMRequirementSet vm1ReqSet, VMRequirementSet vm2ReqSet){
		
		if(vm1ReqSet == null && vm2ReqSet == null && vm1Global == null && vm2Global == null){
				//nothing to check
				return true;			
		}//check the requirement set, adjust by global requirements if applicable
		addGlobalRequirements(vm1Global, vm1ReqSet);
		addGlobalRequirements(vm2Global, vm2ReqSet);
		return(equalVMRequirements(vm1ReqSet,vm2ReqSet));
	}
	/**
	 * Verify if two {@link eu.paasage.camel.deployment.VMRequirementSet <em>VMRequirementSet</em>} are the same.
	 * <p> 
	 * @param vm1ReqSet first {@link eu.paasage.camel.deployment.VMRequirementSet <em>VMRequirementSet</em>}
	 * @param vm2ReqSet second {@link eu.paasage.camel.deployment.VMRequirementSet <em>VMRequirementSet</em>}
	 * @return true if they are the same, else false
	 */
	private boolean equalVMRequirements(VMRequirementSet vm1ReqSet, VMRequirementSet vm2ReqSet){
		//location requirement		
		if((vm1ReqSet.getLocationRequirement() == null && vm2ReqSet.getLocationRequirement() != null) 
				|| (vm1ReqSet.getLocationRequirement() != null && vm2ReqSet.getLocationRequirement() == null)){
			return false; //if one side is null, different
		}	
		if(vm1ReqSet.getLocationRequirement() != null && vm2ReqSet.getLocationRequirement() != null){
			//check content
			if(!vm1ReqSet.getLocationRequirement().getName().equals(vm2ReqSet.getLocationRequirement().getName())){
				return false;
			}
		}	
		//provider requirement
		if((vm1ReqSet.getProviderRequirement() == null && vm2ReqSet.getProviderRequirement() != null)
			|| (vm1ReqSet.getProviderRequirement() != null && vm2ReqSet.getProviderRequirement() == null)){
			return false;			
		}
		if(vm1ReqSet.getProviderRequirement() != null && vm2ReqSet.getProviderRequirement() != null){
			//check the id
			if(vm1ReqSet.getProviderRequirement().getName() != vm2ReqSet.getProviderRequirement().getName()){
				return false;
			}
		}
		//OsOrImageRequirement
		if((vm1ReqSet.getOsOrImageRequirement() == null && vm2ReqSet.getOsOrImageRequirement() != null)
			|| (vm1ReqSet.getOsOrImageRequirement() != null && vm2ReqSet.getOsOrImageRequirement() == null)){
			return false;			
		}
		if(vm1ReqSet.getOsOrImageRequirement() != null && vm2ReqSet.getOsOrImageRequirement() != null){
			//check the id
			if(vm1ReqSet.getOsOrImageRequirement().getName() != vm2ReqSet.getOsOrImageRequirement().getName()){
				return false;
			}
		}
		//qualityHardwareRequirement
		if((vm1ReqSet.getQualitativeHardwareRequirement() == null && vm2ReqSet.getQualitativeHardwareRequirement() != null)
				|| (vm1ReqSet.getQualitativeHardwareRequirement() != null && vm2ReqSet.getQualitativeHardwareRequirement() == null)){
				return false;			
		}
		if(vm1ReqSet.getQualitativeHardwareRequirement() != null && vm2ReqSet.getQualitativeHardwareRequirement() != null){
			//check the id
			if(vm1ReqSet.getQualitativeHardwareRequirement().getName() != vm2ReqSet.getQualitativeHardwareRequirement().getName()){
				return false;
			}
		}
		//quantitativeHardwareRequirement
		if((vm1ReqSet.getQuantitativeHardwareRequirement() == null && vm2ReqSet.getQuantitativeHardwareRequirement() != null)
				|| (vm1ReqSet.getQuantitativeHardwareRequirement() != null && vm2ReqSet.getQuantitativeHardwareRequirement() == null)){
				return false;			
			}
		if(vm1ReqSet.getQuantitativeHardwareRequirement() != null && vm2ReqSet.getQuantitativeHardwareRequirement() != null){
			//check the id
			if(vm1ReqSet.getQuantitativeHardwareRequirement().getName() != vm2ReqSet.getQuantitativeHardwareRequirement().getName()){
				return false;
			}
		}
		return true; //no difference detected
	}
	/**
	 * Add the global VM requirements to the local one. The local requirements override the global ones.
	 * <p>
	 * @param global global {@link eu.paasage.camel.deployment.VMRequirementSet <em>VMRequirementSet</em>}
	 * @param local local {@link eu.paasage.camel.deployment.VMRequirementSet <em>VMRequirementSet</em>}
	 */
	private void addGlobalRequirements(VMRequirementSet global, VMRequirementSet local){
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
	/**
	 * Check if the two {@link eu.paasage.camel.requirement.LocationRequirement <em>LocationRequirement</em>} is different
	 * <p>
	 * @param lr	first {@link eu.paasage.camel.requirement.LocationRequirement <em>LocationRequirement</em>}
	 * @param lr1	second {@link eu.paasage.camel.requirement.LocationRequirement <em>LocationRequirement</em>}
	 * @return true if considered different, else false
	*/
	private boolean isUpdatedLocations(LocationRequirement lr, LocationRequirement lr1){
		
		List<String> vm1Locs = ModelUtil.convertLocations(lr);
		List<String> vm2Locs = ModelUtil.convertLocations(lr1);
		if(vm1Locs == null && vm2Locs == null){
			return false; //no different
		}
		if(vm1Locs != null && vm2Locs != null && !vm1Locs.isEmpty() && !vm2Locs.isEmpty()){
			if(vm1Locs.size() != vm2Locs.size()){
				return true; //different size
			}else{
				//same size, check content
				for(String name : vm1Locs){
					if(!vm2Locs.contains(name)){
						return true;
					}
				}
			}
		}//1 side is either null or is empty
		return false;
	} 
	

	/**
	 * Compare if two {@link eu.paasage.camel.deployment.VMInstance <em>VMInstance</em>} are equal.
	 * <p>
	 * 
	 * @param ni
	 *            first {@link eu.paasage.camel.deployment.VMInstance <em>VMInstance</em>}
	 * @param ni2
	 *            second {@link eu.paasage.camel.deployment.VMInstance <em>VMInstance</em>}
	 * @return true if they are the same, else false
	 */
	private boolean equalVMInstance(VMInstance ni, VMInstance ni2) {
		//LOGGER.debug("comparing VMInstance name : " + ni.getName() + " and " + ni2.getName());
		//LOGGER.debug("comparing VMInstance type name : " + ni.getType().getName() + " and " + ni2.getType().getName());
		//
		return ni.getName().equals(ni2.getName())
				&& ni.getType().getName().equals(ni2.getType().getName());
	}
	/**
	 * Verify if a {@link eu.paasage.camel.deployment.VMInstance <em>VMInstance</em>} has been updated. 
	 * <p>
	 * @param ni	Current {@link eu.paasage.camel.deployment.VMInstance <em>VMInstance</em>}
	 * @param ni2   Target {@link eu.paasage.camel.deployment.VMInstance <em>VMInstance</em>}
	 * @return true if they are considered to be different, else false
	 * @throws	@{link ModelComparatorException <em>ModelComparatorException</em>} on processing error	
	 */
	private boolean isUpdatedVMInstance(VMInstance ni, VMInstance ni2) throws ModelComparatorException {
		//we won't compare the credentials as they belong to the user, not the VMInstance
		logger.debug("comparing VMInstance name : " + ni.getName() + " and " + ni2.getName());
		//LOGGER.debug("comparing VMInstance type name : " + ni.getType().getName() + " and " + ni2.getType().getName());
		//vmType
		//get vm flavour, you will always get an Attribute instance but its attributes may not be populated!
		Attribute vmType1 = ni.getVmType(); //you will always get an instance but its attributes may not be populated!
		Attribute vmType2 = ni2.getVmType();
		//not sure why we don't get the value here, but we get the values elsewhere?? 30July15
		if(vmType1.getName() == null){
			logger.debug("vmtype1.name is null!");
		}
		if(vmType2.getName() == null){
			logger.debug("vmtype2.name is null!");
		}
		String vmTypeName1 = (vmType1.getName() == null ? "null" : vmType1.getName()); //can't do it in one step, got NPE
		String vmTypeName2 = (vmType2.getName() == null ? "null" : vmType2.getName());
		//
		//30July15, for some reasons, the getVMType and getVMTypeValue doesn work w/n this context, it works elsewhere???e.g. ModelProxy
		if(vmTypeName1 != "null" && vmTypeName2 != "null"){
			//debug
			logger.debug("comparing VM type : " + vmTypeName1 + ":" + vmTypeName2);
			//we try and compare the values
			SingleValue typeValue1 = ni.getVmTypeValue(); //this need to explicitly cast, see below
			SingleValue typeValue2 = ni2.getVmTypeValue(); //this need to explicitly cast, see below
			//typeValue is an enumerateImpl
			//LOGGER.debug(" just before switchValue...." + typeValue1.getClass().getName() + ", " + typeValue2.getClass().getName());
			String valueName1 = ModelUtil.switchValue(typeValue1);//it is an enumerateImpl value, but I get null back
			String valueName2 = ModelUtil.switchValue(typeValue2);
//			String currentVMTypeName = (ni.getVmType().getName() == null ? "null" : ni.getVmType().getName()); 
//			String targetVMTypeName = (ni2.getVmType().getName() == null ? "null" : ni2.getVmType().getName());
//			String currentVMTypeValue = (ni.getVmTypeValue() == null ? "null" : ModelUtil.switchValue(ni.getVmTypeValue()));
//			String targetVMTypeValue = (ni2.getVmTypeValue() == null ? "null" : ModelUtil.switchValue(ni2.getVmTypeValue()));
			//if((!ni.getVmType().getName().equals(ni2.getVmType().getName())) || ni.getVmTypeValue() != ni2.getVmTypeValue()){
			if(!vmTypeName1.equals(vmTypeName2)){
				return true;
			}
			if(valueName1 != null && valueName2 != null){
				logger.debug("comparing VM type value : " + valueName1 + ":" + valueName2);
				if(!valueName1.equals(valueName2)){
					return true;
				}
			}
			//cloud and locations
			HashMap<String, Object> current = ModelToJsonConverter.getCloudProviderInfo(vmType1);
			HashMap<String, Object> target = ModelToJsonConverter.getCloudProviderInfo(vmType2);
			try{
				for(Map.Entry<String, Object> rec : current.entrySet()){
					if(rec.getKey().equals("cloud")){	
						if(!((String) rec.getValue()).equals((String) target.get("cloud"))){
							//LOGGER.debug("vminstance cloud comparison about to return true....");
							return true;
						}
					}else if(rec.getKey().equals("locations")){
						List<String> currentLocs = ModelUtil.convertJsonArrayToList((JsonArray) current.get("locations"));
						List<String> targetLocs = ModelUtil.convertJsonArrayToList((JsonArray) target.get("locations"));
						if(!currentLocs.containsAll(targetLocs)){
							//LOGGER.debug("vminstance location comparison about to return true....");
							return true;
						}
					}else if(rec.getKey().equals("driver")){
						if(!((String) rec.getValue()).equals((String) target.get("driver"))){							
							return true;
						}
					}else if(rec.getKey().equals("endpoint")){
						if(!((String) rec.getValue()).equals((String) target.get("endpoint"))){							
							return true;
						}
					}
				}
			}catch(Exception e){
				throw new ModelComparatorException("Error comparing vm instance flavour " + e.getMessage());
			}
		}//30July2015 end if vmType is null 
		//VMs are managed externally, required* not supported
		//hosting and com instances should be base on the VMType definition.  Only names are checked.
		if(!equalProvidedHostInstances(ni.getProvidedHostInstances(), ni2.getProvidedHostInstances())){
			//LOGGER.debug("vminstance equalProvidedHostInstances about to return true....");
			return true;
		}
		if(!equalProvidedCommunicationInstances(ni.getProvidedCommunicationInstances(), ni2.getProvidedCommunicationInstances())){
			//LOGGER.debug("vminstance equalProvidedCommunicationInstances about to return true....");
			return true;
		}
		return false;
	}
	/**
	 * Compare if an {@link eu.paasage.camel.deployment.InternalComponent <em>eu.paasage.camel.deployment.InternalComponent</em>} definition 
	 * has been updated.
	 * <p>
	 * @param ni	first {@link eu.paasage.camel.deployment.InternalComponent <em>eu.paasage.camel.deployment.InternalComponent</em>}
	 * @param ni2	second {@link eu.paasage.camel.deployment.InternalComponent <em>eu.paasage.camel.deployment.InternalComponent</em>}
	 * @return	true if the item is considered updated, else false
	 */
	private boolean isUpdatedInternalComponent(InternalComponent ni, InternalComponent ni2){
		//check required host name, only one.  The guts are checked when comparing the hosting bindings		
		if((!ni.getRequiredHost().getName().equals(ni2.getRequiredHost().getName()))){
			return true;
		}
		//check if containing same list of provided host names
		if(!equalProvidedHosts(ni.getProvidedHosts(), ni2.getProvidedHosts())){
			return true;					
		}		
		//check provided communication list	
		if(!equalProvidedCommunications(ni.getProvidedCommunications(), ni2.getProvidedCommunications())){
			return true;					
		}			
		//check required communication list
		if(!equalRequiredCommunications(ni.getRequiredCommunications(), ni2.getRequiredCommunications())){
			return true;					
		}
		//check nested component list 
		if(!equalComponents(ni.getCompositeInternalComponents(),ni2.getCompositeInternalComponents())){
			return true;
		}
		//just check the first set of config for now, not clear if all configs are current
		if(!equalConfiguration(ni.getConfigurations().get(0), ni2.getConfigurations().get(0))){
			return true;
		}		
		//required/provided com specifications are considered separately when checking Communication objects
		//required/provided host specifications are considered separately when checking hosting objects
		//nested composite internal components specifications are considered separately when comparing components
		return false;
	}
	/**
	 * Compare if an {@link eu.paasage.camel.deployment.InternalComponentInstance <em>eu.paasage.camel.deployment.InternalComponentInstance</em>} definition 
	 * has been updated.
	 * <p>
	 * @param ni	first {@link eu.paasage.camel.deployment.InternalComponentInstance <em>eu.paasage.camel.deployment.InternalComponentInstance</em>}
	 * @param ni2	second {@link eu.paasage.camel.deployment.InternalComponentInstance <em>eu.paasage.camel.deployment.InternalComponentInstance</em>}
	 * @return	true if the item is considered updated, else false
	 */
	public boolean isUpdatedInternalComponentIns(InternalComponentInstance ni, InternalComponentInstance ni2){			
		//check required host instances name 
		if((!ni.getRequiredHostInstance().getName().equals(ni2.getRequiredHostInstance().getName())) || 
				(!ni.getRequiredHostInstance().getType().getName().equals(ni2.getRequiredHostInstance().getType().getName()))){
			//LOGGER.debug("ni.getRequiredHostInstance() about to return true for ni("+ ni.getName() + "....");
			return true;
		}
		//check provided host instances
		if(!equalProvidedHostInstances(ni.getProvidedHostInstances(),ni2.getProvidedHostInstances())){
			//LOGGER.debug("equalProvidedHostInstances about to return true for ni("+ ni.getName() + "....");
			return true;
		}
		//check required communication instances
		if(!equalRequiredCommunicationInstances(ni.getRequiredCommunicationInstances(), ni2.getRequiredCommunicationInstances())){
			//LOGGER.debug("equalRequiredCommunicationInstances about to return true for ni("+ ni.getName() + "....");
			return true;
		}
		//check provided communication instances
		if(!equalProvidedCommunicationInstances(ni.getProvidedCommunicationInstances(), ni2.getProvidedCommunicationInstances())){
			//LOGGER.debug("equalProvidedCommunicationInstances about to return true for ni("+ ni.getName() + "....");
			return true;
		}
		//no nested internal component instances!  Leave it for the moment as it is not used yet (24July2015)		
		return false;		
	}
	/**
	 * Verify if the two sets of {@link eu.paasage.camel.deployment.Configuration <em>Configuration</em>} are the same.
	 * <p>
	 * @param config1	first {@link eu.paasage.camel.deployment.Configuration <em>Configuration</em>}
	 * @param config2	second {@link eu.paasage.camel.deployment.Configuration <em>Configuration</em>}
	 * @return	true if they are the same, else false
	 */
	private boolean equalConfiguration(Configuration config1, Configuration config2){		
		//
		if(config1 == null && config2 == null){
			return true;
		}
		if((config1 == null && config2 != null) || (config1 != null && config2 == null)){//if 1 side is null
			return false;
		}
		if(!config1.getName().equals(config2.getName())) {
			return false;
		}
		//needs to guard for null
		if(!(config1.getConfigureCommand() == null ? "null" : config1.getConfigureCommand()).equals(config2.getConfigureCommand() == null ? "null" : config1.getConfigureCommand())){
			return false;
		}
		if(!(config1.getDownloadCommand() == null ? "null" : config1.getDownloadCommand()).equals(config2.getDownloadCommand() == null ? "null" : config1.getDownloadCommand())){
			return false;
		}
		if(!(config1.getInstallCommand() == null ? "null" : config1.getInstallCommand()).equals(config2.getInstallCommand() == null ? "null" : config1.getInstallCommand())){
			return false;
		}
		if(!(config1.getStartCommand() == null ? "null" : config1.getStartCommand()).equals(config2.getStartCommand() == null ? "null" : config1.getStartCommand())){
			return false;
		}
		if(!(config1.getStopCommand() == null ? "null" : config1.getStopCommand()).equals(config2.getStopCommand() == null ? "null" : config1.getStopCommand())){
			return false;
		}
		if(!(config1.getUploadCommand() == null ? "null" : config1.getUploadCommand()).equals(config2.getUploadCommand() == null ? "null" : config1.getUploadCommand())){
			return false;
		}	
		//LOGGER.debug(" just before returning true for equalConfigurations");
		return true; // nothing else to check
	}
	/**
	 * Compare if two {@link
	 * eu.paasage.camel.deployment.InternalComponent <em>InternalComponent</em>} are the same 	
	 * @param ni	first {@link eu.paasage.camel.deployment.InternalComponent <em>InternalComponent</em>}
	 * @param ni2 	second {@link eu.paasage.camel.deployment.InternalComponent <em>InternalComponent</em>}
	 * @return	true if considered the same, else false
	 */
	private boolean equalComponent(InternalComponent ni, InternalComponent ni2) {
		//call by compareIntenralComponent
		//check if same name 
		return(ni.getName().equals(ni2.getName()));
	}
	/**
	 * Compare if two {@link
	 * eu.paasage.camel.deployment.InternalComponentInstance <em>InternalComponentInstance</em>} are the same
	 * <p>	 * 
	 * @param ni
	 *            first {@link
	 *            eu.paasage.camel.deployment.InternalComponentInstance}
	 * @param ni2
	 *            second {@link
	 *            eu.paasage.camel.deployment.InternalComponentInstance}
	 * @return true if both are the same, else false
	 */
	private boolean equalComponentInstance(InternalComponentInstance ni,
			InternalComponentInstance ni2) {
		//if same name and same type
		return ((ni.getName().equals(ni2.getName()))
				&& (ni.getType().getName().equals(ni2.getType().getName())));
	}	
	
	/**
	 * Compare if two lists of {@link eu.paasage.camel.deployment.ProvidedHost <em>ProvidedHost</em>} 
	 * types are the same
	 * <p>
	 * @param phs	first {@link eu.paasage.camel.deployment.ProvidedHost <em>ProvidedHost</em>}
	 * @param phs2 	second {@link eu.paasage.camel.deployment.ProvidedHost <em>ProvidedHost</em>}
	 * @return true if the names match, else false
	 */
	private boolean equalProvidedHosts(List<ProvidedHost> phs, List<ProvidedHost> phs2){
		//only compare name, the owner would be the component itself so not compared
		if((phs == null && phs2 == null)){
			return true;
		}
		if(phs.size() != phs2.size()){
			return false;
		}
		for(ProvidedHost ph : phs){
			boolean matched = false;
			for(ProvidedHost ph2 : phs2){
				if(ph.getName().equals(ph2.getName())){	
					matched = true;
					break; //go to next ph
				}
			}//end for phs2
			if(!matched){	//if no match for current ph
				return false;
			}
		}//end for phs
		return true;
	}
	/**
	 * Compare if two lists of {@link eu.paasage.camel.deployment.ProvidedHostInstance <em>ProvidedHostInstance</em>} 
	 * types are the same
	 * <p>
	 * @param phis	first {@link eu.paasage.camel.deployment.ProvidedHostInstance <em>ProvidedHostInstance</em>}
	 * @param phis2 second {@link eu.paasage.camel.deployment.ProvidedHostInstance <em>ProvidedHostInstance</em>}
	 * @return true if the names and types match, else false
	 */
	private boolean equalProvidedHostInstances(List<ProvidedHostInstance> phis, List<ProvidedHostInstance> phis2){
		//only compare name, the owner would be the component itself so not compared
		if((phis == null && phis2 == null)){
			return true;
		}
		if(phis.size() != phis2.size()){
			return false;
		}
		for(ProvidedHostInstance phi : phis){
			
			boolean matched = false;
			for(ProvidedHostInstance phi2 : phis2){
				if(phi.getName().equals(phi2.getName()) && phi.getType().getName().equals(phi2.getType().getName())){
					//no need to test parent as it is the calling EObject itself
					matched = true;
					break; //go to next phi
				}
			}//end for phis2
			if(!matched){	//if no match for current phi
				return false;
			}
		}//end for phis
		return true;
	}
	/**
	 * Compare if two lists of {@link eu.paasage.camel.deployment.ProvidedCommunication <em>ProvidedCommunication</em>} 
	 * types are the same
	 * <p>
	 * @param pcs	first {@link eu.paasage.camel.deployment.ProvidedCommunication <em>ProvidedCommunication</em>}
	 * @param pcs2 	second {@link eu.paasage.camel.deployment.ProvidedCommunication <em>ProvidedCommunication</em>}
	 * @return true if the names match, else false
	 */
	private boolean equalProvidedCommunications(List<ProvidedCommunication> pcs, List<ProvidedCommunication> pcs2){
		//only compare name, the owner would be the component itself so not compared
		if((pcs == null && pcs2 == null)){
			return true;
		}
		if(pcs.size() != pcs2.size()){
			return false;
		}
		for(ProvidedCommunication pc : pcs){
			boolean matched = false;
			for(ProvidedCommunication pc2 : pcs2){
				if(pc.getName().equals(pc2.getName()) &&
						//10Dec15 added port number to guard against orphan provided communication objects which have no communication bindings
						  pc.getPortNumber() == pc2.getPortNumber()){	
					matched = true;
					break; //go to next pc
				}
			}//end for pcs2
			if(!matched){	//if no match for current pc
				return false;
			}
		}//end for pcs
		return true;
	}
	/**
	 * Compare if two lists of {@link eu.paasage.camel.deployment.ProvidedCommunicationInstance <em>ProvidedCommunicationInstance</em>} 
	 * types are the same
	 * <p>
	 * @param pcis	first {@link eu.paasage.camel.deployment.ProvidedCommunicationInstance <em>ProvidedCommunicationInstance</em>}
	 * @param pcis2 second {@link eu.paasage.camel.deployment.ProvidedCommunicationInstance <em>ProvidedCommunicationInstance</em>}
	 * @return true if the names and types match, else false
	 */
	private boolean equalProvidedCommunicationInstances(List<ProvidedCommunicationInstance> pcis, List<ProvidedCommunicationInstance> pcis2){
		//only compare name, the owner would be the component itself so not compared
		if((pcis == null && pcis2 == null)){
			return true;
		}
		if(pcis.size() != pcis2.size()){
			return false;
		}
		for(ProvidedCommunicationInstance pci : pcis){ 
			boolean matched = false;
			for(ProvidedCommunicationInstance pci2 : pcis2){
				if(pci.getName().equals(pci2.getName()) && pci.getType().getName().equals(pci2.getType().getName())){	
					//the parent would be the calling EObject
					matched = true;
					break; //go to next pci
				}
			}//end for pcis2
			if(!matched){	//if no match for current pci
				return false;
			}
		}//end for pcis
		return true;
	}
	/**
	 * Compare if two lists of {@link eu.paasage.camel.deployment.RequiredCommunication <em>RequiredCommunication</em>} 
	 * types are the same
	 * <p>
	 * @param rcs	first {@link eu.paasage.camel.deployment.RequiredCommunication <em>RequiredCommunication</em>}
	 * @param rcs2 	second {@link eu.paasage.camel.deployment.RequiredCommunication <em>RequiredCommunication</em>}
	 * @return true if the names match, else false
	 */
	private boolean equalRequiredCommunications(List<RequiredCommunication> rcs, List<RequiredCommunication> rcs2){
		//only compare name, the owner would be the component itself so not compared
		if((rcs == null && rcs2 == null)){
			return true;
		}
		if(rcs.size() != rcs2.size()){
			return false;
		}
		for(RequiredCommunication pc : rcs){
			boolean matched = false;
			for(RequiredCommunication pc2 : rcs2){
				if(pc.getName().equals(pc2.getName())){	
					matched = true;
					break; //go to next pc
				}
			}//end for rcs2
			if(!matched){	//if no match for current pc
				return false;
			}
		}//end for rcs
		return true;
	}
	/**
	 * Compare if two lists of {@link eu.paasage.camel.deployment.RequiredCommunicationInstance <em>RequiredCommunicationInstance</em>} 
	 * types are the same
	 * <p>
	 * @param rcis	first {@link eu.paasage.camel.deployment.RequiredCommunicationInstance <em>RequiredCommunicationInstance</em>}
	 * @param rcis2 second {@link eu.paasage.camel.deployment.RequiredCommunicationInstance <em>RequiredCommunicationInstance</em>}
	 * @return true if the names and types match, else false
	 */
	private boolean equalRequiredCommunicationInstances(List<RequiredCommunicationInstance> rcis, List<RequiredCommunicationInstance> rcis2){
		//only compare name, the owner would be the component itself so not compared	
		if((rcis == null && rcis2 == null)){
			return true;
		}
		//LOGGER.debug("rics : " + rcis.size() + "rcis2 : " + rcis2.size());
		if(rcis.size() != rcis2.size()){
			return false;
		}
		for(RequiredCommunicationInstance rci : rcis){ 
			boolean matched = false;
			for(RequiredCommunicationInstance rci2 : rcis2){
				//LOGGER.debug(" comparing rci name(" + rci.getName() + ") & rci2 name(" + rci2.getName() + "), rci type(" + rci.getType().getName() + ") & rci2 type(" + rci2.getType().getName() + ")...");
				if(rci.getName().equals(rci2.getName()) && rci.getType().getName().equals(rci2.getType().getName())){	
					//LOGGER.debug("about to set matching required com instances to true .... ");
					matched = true;
					break; //go to next rci
				}
			}//end for rcis2
			if(!matched){	//if no match for current rci
				//LOGGER.debug("about to return false as !matched for rci(" + rci.getName() + " .... ");
				return false;
			}
		}//end for rcis
		return true;
	}
	/**
	 * Compare if two lists of {@link eu.paasage.camel.deployment.InternalComponent <em>InternalComponent</em>} 
	 * types are the same
	 * <p>
	 * @param ics	first {@link eu.paasage.camel.deployment.InternalComponent <em>InternalComponent</em>}
	 * @param ics2 	second {@link eu.paasage.camel.deployment.InternalComponent <em>InternalComponent</em>}
	 * @return true if the names match, else false
	 */
	private boolean equalComponents(List<InternalComponent> ics, List<InternalComponent> ics2){
		//only compare name, the owner would be the component itself so not compared
		//the contents are dealt with elsewhere when each component is compared
		if((ics == null && ics2 == null)){
			return true;
		}
		if(ics.size() != ics2.size()){
			return false;
		}
		for(InternalComponent pc : ics){
			boolean matched = false;
			for(InternalComponent pc2 : ics2){
				if(pc.getName().equals(pc2.getName())){	
					matched = true;
					break; //go to next pc
				}
			}//end for rcs2
			if(!matched){	//if no match for current pc
				return false;
			}
		}//end for rcs
		return true;
	}
	
	/**
	 * Compare if two {@link eu.paasage.camel.deployment.Hosting <em>Hosting</em>} 
	 * types are the same
	 * <p>
	 * @param ni	first  {@link eu.paasage.camel.deployment.Hosting <em>Hosting</em>}
	 * @param ni2	second  {@link eu.paasage.camel.deployment.Hosting <em>Hosting</em>}
	 * @return	true if they are considered the same, else false
	 */
	private boolean equalHosting(Hosting ni, Hosting ni2){
		//test both ends of the binding.  Config is considered in the isUpdated* operation
		return (ni.getName().equals(ni2.getName()) &&
				ni.getRequiredHost().getName().equals(ni2.getRequiredHost().getName()) &&
				ni.getProvidedHost().getName().equals(ni2.getProvidedHost().getName()) 
				);
	}
	
	/**
	 * Verify if a {@link eu.paasage.camel.deployment.Hosting <em>Hosting</em>} definition
	 * has been updated.
	 * <p>
	 * @param ni	first {@link eu.paasage.camel.deployment.Hosting <em>Hosting</em>}
	 * @param ni2	second {@link eu.paasage.camel.deployment.Hosting <em>Hosting</em>}
	 * @return	true if considered updated, else false
	 */
	private boolean isUpdatedHosting(Hosting ni, Hosting ni2){
		//already checked hosting name, provided/requiredHost.name & component owner name
		if(!equalConfiguration(ni.getProvidedHostConfiguration(),ni2.getProvidedHostConfiguration())){
			return true;
		}
		if(!equalConfiguration(ni.getRequiredHostConfiguration(),ni2.getRequiredHostConfiguration())){
			return true;
		}	
		if(!((Component) ni.getProvidedHost().eContainer()).getName().equals(((Component) ni2.getProvidedHost().eContainer()).getName())){
			return true;
		}
		if(!((Component) ni.getRequiredHost().eContainer()).getName().equals(((Component) ni2.getRequiredHost().eContainer()).getName())){
			return true;
		}
		//checked what we can		
		return false;
		
	}
	/**
	 * Verify if a {@link eu.paasage.camel.deployment.HostingInstance <em>HostingInstance</em>} definition
	 * has been updated.
	 * <p> 
	 * @param hi	first {@link eu.paasage.camel.deployment.HostingInstance <em>HostingInstance</em>}
	 * @param hi2	second {@link eu.paasage.camel.deployment.HostingInstance <em>HostingInstance</em>}
	 * @return	true if they are considered the same, else false
	 */
	private boolean isUpdatedHostingInstance(HostingInstance hi, HostingInstance hi2){
		
		return (
				!((ComponentInstance) hi.getRequiredHostInstance().eContainer()).getName().equals(((ComponentInstance) hi2.getRequiredHostInstance().eContainer()).getName()) ||
				!((ComponentInstance) hi.getProvidedHostInstance().eContainer()).getName().equals(((ComponentInstance) hi2.getProvidedHostInstance().eContainer()).getName())
				);
		
	}
	/**
	 * Compare if two {@link eu.paasage.camel.deployment.ProvidedHost <em>ProvidedHost</em>}
	 * are the same.
	 * <p>
	 * @param ni	first {@link eu.paasage.camel.deployment.ProvidedHost <em>ProvidedHost</em>}
	 * @param ni2	second {@link eu.paasage.camel.deployment.ProvidedHost <em>ProvidedHost</em>}
	 * @return	true if they are considered the same, else false
	 
	private boolean equalProvidedHost(ProvidedHost ni, ProvidedHost ni2){
		//same hosting same and provided by same container
		return ((ni.getName().equals(ni2.getName())) && ni.getOwner().getName().equals(ni2.getOwner().getName()));
		
	}*/

	/**
	 * Compare if two {@link eu.paasage.camel.deployment.HostingInstance <em>HostingInstance</em>} are
	 * the same
	 * <p>
	 * @param ni
	 *            first {@link eu.paasage.camel.deployment.HostingInstance <em>HostingInstance</em>}
	 * @param ni2
	 *            second {@link eu.paasage.camel.deployment.HostingInstance <em>HostingInstance</em>}
	 * @return true if they are considered the same, else false
	 */
	private boolean equalHostingInstance(HostingInstance ni, HostingInstance ni2) {

		return (ni.getName().equals(ni2.getName()) && ni.getType().getName().equals(ni2.getType().getName()) &&
				ni.getRequiredHostInstance().getName().equals(ni2.getRequiredHostInstance().getName())  &&
				ni.getProvidedHostInstance().getName().equals(ni2.getProvidedHostInstance().getName())  
				);
	}	
	/**
	 * Compare if two {@link eu.paasage.camel.deployment.Communication <em>Communication</em>} 
	 * objects are the same
	 * <p>
	 * @param ni
	 *            first {@link
	 *            eu.paasage.camel.deployment.Communication <em>Communication</em>}
	 * @param ni2
	 *            second {@link
	 *            eu.paasage.camel.deployment.Communication <em>Communication</em>}
	 * @return true if they are considered the same, else false
	 */
	private boolean equalCommunication(Communication ni, Communication ni2){		
		//port type, port configuration and provided port number are considered in update
		return ((ni.getName().equals(ni2.getName())) &&
				(ni.getProvidedCommunication().getName().equals(ni2.getProvidedCommunication().getName())) &&
				(ni.getRequiredCommunication().getName().equals(ni2.getRequiredCommunication().getName()))
				);
	}
	/**
	 * Verify if a {@link eu.paasage.camel.deployment.Communication <em>Communication</em>} definition
	 * has been updated.
	 * <p>
	 * @param ni	first {@link eu.paasage.camel.deployment.Communication <em>Communication</em>}
	 * @param ni2	second {@link eu.paasage.camel.deployment.Communication <em>Communication</em>}
	 * @return true if some properties are considered to be different, else false
	 */
	private boolean isUpdatedCommunication(Communication ni, Communication ni2){
		//check type, port config and provided port number
		if(ni.getRequiredCommunication().isIsMandatory() != ni2.getRequiredCommunication().isIsMandatory()){
			return true;
		}
		if(!ni.getType().equals(ni2.getType())){ //any, remote, local
			return true;
		}
		if((ni.getProvidedCommunication().getPortNumber() != ni2.getProvidedCommunication().getPortNumber()) ||
				(ni.getRequiredCommunication().getPortNumber() != ni2.getRequiredCommunication().getPortNumber())){
			return true;
		}
		if((!equalConfiguration(ni.getProvidedPortConfiguration(),ni2.getProvidedPortConfiguration())) ||
				(!equalConfiguration(ni.getRequiredPortConfiguration(),ni2.getRequiredPortConfiguration())))	{
			return true;
		}
		if((!((Component) ni.getProvidedCommunication().eContainer()).getName().equals(((Component) ni2.getProvidedCommunication().eContainer()).getName())) ||
				(!((Component) ni.getRequiredCommunication().eContainer()).getName().equals(((Component) ni2.getRequiredCommunication().eContainer()).getName()))){
			return true;
		}
		return false; //guess nothing else to check		
	}
	/**
	 * Compare if two {@link eu.paasage.camel.deployment.CommunicationInstance <em>CommunicationInstance</em>}
	 * are the same
	 * <p>
	 * 
	 * @param ni
	 *            first {@link
	 *            eu.paasage.camel.deployment.CommunicationInstance <em>CommunicationInstance</em>}
	 * @param ni2
	 *            second {@link
	 *            eu.paasage.camel.deployment.CommunicationInstance <em>CommunicationInstance</em>}
	 * @return true if they are considered the same, else false
	 */
	private boolean equalCommunicationInstance(CommunicationInstance ni,
			CommunicationInstance ni2) {
		return (ni.getName().equals(ni2.getName()) && ni.getType().getName().equals(ni2.getType().getName()) &&
				ni.getProvidedCommunicationInstance().getName().equals(ni2.getProvidedCommunicationInstance().getName()) &&
				/*ni.getProvidedCommunicationInstance().getOwner().getName().equals(ni2.getProvidedCommunicationInstance().getOwner().getName()) &&
				ni.getProvidedCommunicationInstance().getOwner().getType().equals(ni2.getProvidedCommunicationInstance().getOwner().getType()) &&*/
				ni.getRequiredCommunicationInstance().getName().equals(ni2.getRequiredCommunicationInstance().getName()) /*&&
				ni.getRequiredCommunicationInstance().getOwner().getName().equals(ni2.getRequiredCommunicationInstance().getOwner().getName()) &&
				ni.getRequiredCommunicationInstance().getOwner().getType().equals(ni2.getRequiredCommunicationInstance().getOwner().getType())*/
				);
	}
	/**
	 * Verify if a {@link eu.paasage.camel.deployment.CommunicationInstance <em>CommunicationInstance</em>} definition
	 * has been updated.
	 * <p>
	  * @param ni
	 *            first {@link
	 *            eu.paasage.camel.deployment.CommunicationInstance <em>CommunicationInstance</em>}
	 * @param ni2
	 *            second {@link
	 *            eu.paasage.camel.deployment.CommunicationInstance <em>CommunicationInstance</em>}
	 * @return true if some properties are considered to be different, else false
	 */
	private boolean isUpdatedCommunicationInstance(CommunicationInstance ni, CommunicationInstance ni2){
		
		return (!((ComponentInstance) ni.getProvidedCommunicationInstance().eContainer()).getName().equals(((ComponentInstance) ni2.getProvidedCommunicationInstance().eContainer()).getName()) ||
				!((ComponentInstance) ni.getRequiredCommunicationInstance().eContainer()).getName().equals(((ComponentInstance)ni2.getRequiredCommunicationInstance().eContainer()).getName())
				);	
	}
	/**
	 * Retrieve the first set of {@link eu.paasage.camel.deployment.Configuration <em>Configuration</em>} from a
	 * {@link eu.paasage.camel.deployment.Component <em>Component</em>} object.  
	 * <p>
	 * @param comp
	 * @return the retrieved {@link eu.paasage.camel.deployment.Configuration <em>Configuration</em>} object or null
	 */
	private Configuration getConfiguration(Component comp){
		Configuration config = null;
    	EList<Configuration> resources = comp.getConfigurations(); //need to do it in 2 steps, you may get an empty EList...
		if(resources != null && !resources.isEmpty()){
			config = resources.get(0);	
		}//end if resources != null
		return config;
	}
	
	/**
	 * Check if the target {@link eu.paasage.camel.deployment.ProvidedCommunication <em>ProvidedCommunication</em>} is an orphan.
	 * <p>
	 * @param commTypes		a {@link java.util.List <em>List</em>} of {@link eu.paasage.camel.deployment.Communication <em>Communication</em>}
	 * 						to use in the comparison.
	 * @param pc			the target {@link eu.paasage.camel.deployment.ProvidedCommunication <em>ProvidedCommunication</em>} object
	 * @return				true if it is an orphan, else false
	 */
	private boolean isOrphanCom(ProvidedCommunication pc, List<Communication> commTypes) {
		//
		if(commTypes != null){
			for(Communication com : commTypes){
				if(com.getProvidedCommunication().equals(pc)){	//if it is the same object
					logger.debug("found match for provided communication(" + pc.getName() + ").  It is not an orphan....");
					return false;  //is defined in com type
				}
			}
		}
		//has gone through all the com type or there is no communication objects in the deployment model
		return true;
	}
	
	/**
	 * Check if the attributes in the target {@link eu.paasage.camel.deployment.ProvidedCommunication <em>ProvidedCommunication</em>} 
	 * has been updated.
	 * <p>
	 * @param pc	the target {@link eu.paasage.camel.deployment.ProvidedCommunication <em>ProvidedCommunication</em>} object
	 * @return		true if it has been updated, else false
	 */
	private boolean isUpdatedOrphanCommunication(ProvidedCommunication pc){
		boolean rc = true;
		//
		List<InternalComponent> currentICs = this.currentDM.getInternalComponents(); //can't be null as update case
		for(InternalComponent ic : currentICs){
			if(ic.getName().equals(((InternalComponent) pc.eContainer()).getName())){//as update, the IC name should be the same
				//found the current version of the internal component object
				List<ProvidedCommunication> currentPCs = ic.getProvidedCommunications();
				if(currentPCs != null){
					for(ProvidedCommunication cpc : currentPCs){
						if(cpc.getName().equals(pc.getName()) && cpc.getPortNumber() == pc.getPortNumber()){
							logger.debug("the ProvidedCommunication(" + pc.getName() + ") has not been updated....");
							rc = false;
						}
					}
				}
			}
		}//end for(InternalComponent ic : currentICs)
		return rc;	//true means: updated attributes or the PC is new	
	}
}
