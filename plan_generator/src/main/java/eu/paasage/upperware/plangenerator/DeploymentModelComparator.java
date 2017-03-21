/*
 * Copyright (c) 2014 UK Science and Technology Facilities Council
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.plangenerator;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.util.EcoreUtil;

import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.HostingInstance;
import eu.paasage.camel.deployment.InternalComponent;
import eu.paasage.camel.deployment.InternalComponentInstance;
import eu.paasage.camel.deployment.ProvidedCommunication;
import eu.paasage.camel.deployment.VMInstance;
import eu.paasage.upperware.plangenerator.exception.ModelComparatorException;
import eu.paasage.upperware.plangenerator.exception.ModelUtilException;

/**
 * A class to compare two {@link eu.paasage.camel.deployment.DeploymentModel <em>DeploymentModel</em>}s
 * focusing on instances.  Please note that for reconfiguration, the type objects are not updated
 * by the Solver&#45;to&#45;Deployment component, only the instances are changed.  Also note 
 * {@link eu.paasage.camel.deployment.CommunicationInstance <em>CommunicationInstance</em>}s are not 
 * compared as the Execution Ware automatically generates them from the supplied
 * {@link eu.paasage.camel.deployment.Communication <em>Communication</em>} types.
 * <p> * 
 * @author Shirley Crompton 
 * org UK Science and Technology Facilities Council
 */
public class DeploymentModelComparator {	
	/********************************************************************************************
	 * The comparator has been rewritten as S2D generates instance names randomly and names can
	 * no longer be used as an object's key identifier during comparison.  Consequently,
	 * in a reconfiguration scenario, all instances of the same type are treated as functionally
	 * the same.   As type objects are not updated, Plan Generator does not need to consider the
	 * artificially generated Orphan Communication objects. The Orphan Communication instances
	 * should have been automatically created by EW during first deployment.
	 * As name can longer be used as identifier, it is not possible to guarantee that the same
	 * object will be matched, e.g. if there are 2 VMInstances (VMIa, VMIb) of the same type in 
	 * both the current and target model, we may can VMIa->VMIb' rather than VMIa->VMIa'
	 ********************************************************************************************/

	/** Message logger */
	private static final Logger logger = Logger.getLogger(DeploymentModelComparator.class.getName());	
	
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
	
	/** A {@link java.util.Map <em>map</em>} of instance count by type in the current 
	 * {@link eu.paasage.camel.deployment.DeploymentModel <em>DeploymentModel</em>} 
	 
	private Map<String, Integer> currentInstanceCounts = new HashMap<String, Integer>();*/
	
	/** A {@link java.util.Map <em>map</em>} of instance count by type in the target 
	 * {@link eu.paasage.camel.deployment.DeploymentModel <em>DeploymentModel</em>} 
	 
	private Map<String, Integer> targetInstanceCounts = new HashMap<String, Integer>();	*/
	
	/** The current {@link eu.paasage.camel.deployment.DeploymentModel <em>DeploymentModel</em>} */
	private DeploymentModel currentDM = null;
	/** The target {@link eu.paasage.camel.deployment.DeploymentModel <em>DeploymentModel</em>} */
	private DeploymentModel targetDM = null;

	
	///////////////////////methods
	/** Create an instance 
	 * @param current {@link eu.paasage.camel.deployment.DeploymentModel <em>DeploymentModel</em>}
	 * @param target {@link eu.paasage.camel.deployment.DeploymentModel <em>DeploymentModel</em>}
	 * */
	public DeploymentModelComparator(DeploymentModel current, DeploymentModel target) {
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
	 * @throws ModelComparatorException on processing error
	 */
	public void compareModels() throws ModelComparatorException {
		// compareModels is only called when there are current and target models
		// debug
		logger.debug("Comparing current(" + currentDM.getName()
				+ ") and target(" + targetDM.getName() + ")");		
		//compare the VMInstance
		try{
			//4May2016, first compare the hosting instances, we need to ensure that the pairings of provider/consumerInstances are preserved
			compareHostingInstances();
			logger.debug("Removed HostingInstances size :" + removedHostingInstances.size());
			logger.debug("Added HostingInstances size :" + addedHostingInstances.size());
			logger.debug("Matched HostingInstances size : " + matchedHostingInstances.size());
			compareVMInstances();
			logger.debug("Removed VMInstances size :" + removedVMInstances.size());
			logger.debug("Added VMInstances  size :" + addedVMInstances.size());
			logger.debug("Matched VMInstances size : " + matchedVMInstances.size());		
			//compare the ComponentInstance
			compareInternalComponentInstances();
			logger.debug("Removed internal component instances size : " + removedInternalComponentIns.size());
			logger.debug("Added internal component instances size : " + addedInternalComponentIns.size());
			logger.debug("Matched internal component instances size : " + matchedInternalComponentIns.size());	
			//no need to do communication instances, EW handles these itself
		}catch(Exception e){
			throw new ModelComparatorException("Exception comparing models : " + e.getMessage());
		}
	}

	/**
	 * Compares the {@link eu.paasage.camel.deployment.VMInstance <em>VMInstance</em>} in the
	 * target and the current deployment models
	 * 
	 * @throws ModelComparatorException on processing error
	 */
	public void compareVMInstances() throws ModelComparatorException {
		logger.info(">> Comparing vm instances ...");
		Boolean match = false;		
		List<VMInstance> currentVMInstances = this.currentDM.getVmInstances(); 
		List<VMInstance> targetVMInstances = this.targetDM.getVmInstances();
		//
		if((targetVMInstances == null || targetVMInstances.isEmpty())){
			logger.debug("No target VMInstances!");
			// the current ones, if any, are all obsolete
			removedVMInstances.addAll(currentVMInstances);
			return;	//no need to do anymore as all current VMInstances are to be removed
		}
		//
		if((currentVMInstances == null || currentVMInstances.isEmpty())){
			logger.debug("No current VMInstances!");
			// all the target ones are new
			addedVMInstances.addAll(targetVMInstances);
			return;	//no need to do anymore as we just need to add all target VMInstances
		}	
		//both lists got items
		logger.debug("currentDM has " + currentVMInstances.size() + " VMInstances");
		logger.debug("targetDM has " + targetVMInstances.size() + " VMInstances");
		// for each target VM instance, match with the current instances
		for (VMInstance target : targetVMInstances) {  
				//debug this NPE issue with VMType
//				Attribute vmType1 = ni.getVmType(); //you will always get an instance but its attributes may not be populated!
//				String vmTypeName1 = (vmType1.getName() == null ? "null" : vmType1.getName());
//				LOGGER.debug("...debug NPE in VMInstance : ni vmType is : " + vmTypeName1 + "....");
			//
			//first check if the target has already been matched during comparing hosting instances
			if(!matchedVMInstances.containsKey(target)){
				match = false; //re-initialise
				for (VMInstance current : currentVMInstances) {
					//
					if (!matchedVMInstances.containsValue(current) && equalVMInstance(target, current)) {	//the current one is found in the target	
						match = true;	
						logger.debug("adding matched VMinstances: " + target.getName() + "/" + current.getName() + "......");
						matchedVMInstances.put(target, current);	//key = target, value = current
						break; // go to next target VM Instance
					}					
				}// end for each current VM Instance				
				if (!match) {	 
					// if target one not matched in current
					//LOGGER.debug("adding unmatched target VMinstance: " + target.getName() + " to addedVMInstances");
					addedVMInstances.add(target); //target is new
				}
			}//end if target is matched
		}// end for each targetVMInstance
		//now find what need to be deleted
		removedVMInstances.addAll(currentVMInstances); // first add all current instances
		removedVMInstances.removeAll(matchedVMInstances.values()); //subtract the matched instances
	}

	/**
	 * Compares the {@link eu.paasage.camel.deployment.HostingInstance} in
	 * the target and the current deployment models
	 * <p>
	 * @exception {@link eu.paasage.upperware.plangenerator.exception.ModelUtilException <em>ModelUtilException</em>} 
	 * 				
	 */
	public void compareHostingInstances() throws ModelUtilException {
		logger.info(">> Comparing Hosting Instances ...");
		Boolean match = false;
		//
		List<HostingInstance> currentHostingIns = this.currentDM.getHostingInstances();
		List<HostingInstance> targetHostingIns = this.targetDM.getHostingInstances();
		//
		if((targetHostingIns == null || targetHostingIns.isEmpty())){
			logger.debug("No target HostingInstances!");
			// the current ones, if any, are all obsolete
			removedHostingInstances.addAll(currentHostingIns);
			return;	//no need to do anymore as all current hosting Instances are to be removed
		}
		//
		if((currentHostingIns == null || currentHostingIns.isEmpty())){
			logger.debug("No current Hosting Instances!");
			// all the target ones are new
			addedHostingInstances.addAll(targetHostingIns);
			return;	//no need to do anymore as we just need to add all target hosting instances
		}		
		//both lists got items
		logger.debug("currentDM has " + currentHostingIns.size() + " HostingInstances");
		logger.debug("targetDM has " + targetHostingIns.size() + " HostingInstances");
		//
		//for(HostingInstance hi : targetHostingIns){
		//	System.out.println("current name " + hi.getName() );
		//}
		// now compare
		for (HostingInstance target : targetHostingIns) {
			// for each target Hosting instance, match with the current instances
			match = false; //re-initialise
			for (HostingInstance current : currentHostingIns) {
				if (!matchedHostingInstances.containsValue(current) && equalHostingInstance(target, current)) {
					match = true;
					matchedHostingInstances.put(target, current);//key = target, value = value
					//4May2016 we need to preserve the existing pairing of provider/consumerInstances
					//so need to find them and make sure that their names are mapped correctly
					mapLinkedInstances(target,current);					
					break; // go to next target Hosting instance
				}
			}// end for each current Hosting instance
			if (!match) {
				// if target not matched
				addedHostingInstances.add(target); 
			}
		}//end for target
		removedHostingInstances.addAll(currentHostingIns); // first add all current instances
		removedHostingInstances.removeAll(matchedHostingInstances.values());//subtract all the matched ones		
		
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
		if((targetCompIns == null || targetCompIns.isEmpty())){
			logger.debug("No target IntComponentInstances!");
			// the current ones, if any, are all obsolete
			removedInternalComponentIns.addAll(currentCompIns);
			return;	//no need to do anymore as all current Instances are to be removed
		}
		//
		if((currentCompIns == null || currentCompIns.isEmpty())){
			logger.debug("No current Internal Component Instances!");
			// all the target ones are new
			addedInternalComponentIns.addAll(targetCompIns);
			return;	//no need to do anymore as we just need to add all target Instances
		}	
		//both lists got items
		logger.debug("currentDM has " + currentCompIns.size() + " Internal Component Instances");
		logger.debug("targetDM has " + targetCompIns.size() + " Internal Component Instances");
		// for each target Internal Component instance, match with the current instances
		for (InternalComponentInstance target : targetCompIns) { 
			if(!matchedInternalComponentIns.containsKey(target)){			
				for (InternalComponentInstance current : currentCompIns) {			
						if (!matchedInternalComponentIns.values().contains(current) && equalComponentInstance(target, current)) {	
							match = true;
							logger.debug("adding matched internal component instance: " + target.getName() + "/" + current.getName() + " .....");
							matchedInternalComponentIns.put(target, current);	//key = target, value = value							
							break; // go to next target Instance
						}
				}// end for each current Instance				
				if (!match) {	 
					// if target one not matched in current
					//LOGGER.debug("adding unmatched target internal component instance: " + target.getName() + " to addedInternalComponentIns");
					addedInternalComponentIns.add(target); //target is new
				}
			}//end if target has been matched
		}// end for each target Instance
		//now find what need to be deleted
		removedInternalComponentIns.addAll(currentCompIns); // first add all current instances
		removedInternalComponentIns.removeAll(matchedInternalComponentIns.values()); //subtract the matched instances
	}	
	////////////////////////////////////////GETTERS/////////////////////////////////////////////////
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
	 * Getter for the {@link java.util.Map <em>Map</em>} of matching current and target {@link
	 * eu.paasage.camel.deployment.VMInstance <em>VMInstance</em>}s
	 * <p>
	 * @return the matchedVMInstances
	 */
	public Map<VMInstance, VMInstance> getMatchedVMInstances() {
		return matchedVMInstances;
	}

	/**
	 * Getter for the {@link java.util.Map <em>Map</em>} of matching current and target {@link
	 * eu.paasage.camel.deployment.InternalComponentInstance <em>InternalComponentInstance</em>}s
	 * <p>
	 * @return the matchedInternalComponentIns
	 */
	public Map<InternalComponentInstance, InternalComponentInstance> getMatchedInternalComponentIns() {
		return matchedInternalComponentIns;
	}

	/**
	 * Getter for the {@link java.util.Map <em>Map</em>} of matching current and target {@link
	 * eu.paasage.camel.deployment.HostingInstance <em>HostingInstance</em>}s
	 * <p>
	 * @return the matchedHostingInstances
	 */
	public Map<HostingInstance, HostingInstance> getMatchedHostingInstances() {
		return matchedHostingInstances;
	}

	/**
	 * A utility to check if two {@link eu.paasage.camel.deployment.ProvidedCommunication <em>ProvidedCommunication</em>}
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
		
		matchedVMInstances.clear();
		removedVMInstances.clear();
		addedVMInstances.clear();
		
		matchedInternalComponentIns.clear();
		removedInternalComponentIns.clear();
		addedInternalComponentIns.clear();

		matchedHostingInstances.clear();
		removedHostingInstances.clear();
		addedHostingInstances.clear();
	}

	/**
	 * Compare if two {@link eu.paasage.camel.deployment.VMInstance <em>VMInstance</em>} are equal.  
	 * They are considered the same if the parent VM type and provider VMType are same.
	 * <p>
	 * @param ni
	 *            first {@link eu.paasage.camel.deployment.VMInstance <em>VMInstance</em>}
	 * @param ni2
	 *            second {@link eu.paasage.camel.deployment.VMInstance <em>VMInstance</em>}
	 * @return true if they are the same, else false
	 */
	private boolean equalVMInstance(VMInstance ni, VMInstance ni2) {
		boolean sameVMType = EcoreUtil.equals(ni.getVmTypeValue(), ni2.getVmTypeValue());
		boolean sameType = EcoreUtil.equals(ni.getType(), ni2.getType());
		logger.debug("comparing VMInstances (" + ni.getName() + " and " + ni2.getName() + ") : VMType is " + sameVMType);
		//logger.debug("comparing VMInstance type name : " + ni.getType().getName() + " and " + ni2.getType().getName());
		//
		return (sameType && sameVMType);
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
		boolean sameType = EcoreUtil.equals(ni.getType(), ni2.getType());
		logger.debug("comparing InternalComponentInstances(" + ni.getName() + " and " + ni2.getName() + ") : parent type is " + sameType);
		//if same parent type, apart from instance names all the other attributes come from parent type
		return sameType;
	}	
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
		//
		boolean sameType = EcoreUtil.equals(ni.getType(), ni2.getType());
		logger.debug("comparing Hosting Instances(" + ni.getName() + " and " + ni2.getName() + ") : parent type is " + sameType);
		//if same parent type, apart from instance names all the other attributes come from parent type
		return sameType;

	}
	/**
	 * Identify the hosting provider and consumer instances and create a matched pairs for them
	 * to ensure that the pairing is preserved in the new deployment.
	 * <p>
	 * @param target	target {@link eu.paasage.camel.deployment.HostingInstance <em>HostingInstance</em>}
	 * @param current	current {@link eu.paasage.camel.deployment.HostingInstance <em>HostingInstance</em>}
	 */
	private void mapLinkedInstances(HostingInstance target, HostingInstance current){
		//consumer - InternalComponentInstance
		this.matchedInternalComponentIns.put((InternalComponentInstance) target.getRequiredHostInstance().eContainer(), 
				(InternalComponentInstance) current.getRequiredHostInstance().eContainer());
		logger.debug("target hosting instance(" + target.getName() + ") matched consumer, target: " + 
				((InternalComponentInstance) target.getRequiredHostInstance().eContainer()).getName() +
				" current: " + ((InternalComponentInstance) current.getRequiredHostInstance().eContainer()).getName() + "....");
		//provider - VMInstance or InternalComponentInstance
		if(target.getProvidedHostInstance().eContainer() instanceof VMInstance){
			//
			this.matchedVMInstances.put((VMInstance) target.getProvidedHostInstance().eContainer(),
					(VMInstance) current.getProvidedHostInstance().eContainer());
			logger.debug("target hosting instance(" + target.getName() + ") matched provider, target: " + 
					((VMInstance) target.getProvidedHostInstance().eContainer()).getName() +
					" current: " + ((VMInstance) current.getProvidedHostInstance().eContainer()).getName() + "....");
		}else if(target.getProvidedHostInstance().eContainer() instanceof InternalComponentInstance){
			//
			this.matchedInternalComponentIns.put((InternalComponentInstance) target.getProvidedHostInstance().eContainer(),
					(InternalComponentInstance) current.getProvidedHostInstance().eContainer());
			logger.debug("target hosting instance(" + target.getName() + ") matched provider, target: " + 
					((InternalComponentInstance) target.getProvidedHostInstance().eContainer()).getName() +
					" current: " + ((InternalComponentInstance) current.getProvidedHostInstance().eContainer()).getName() + "....");
		}
		
		
		
	}

}
