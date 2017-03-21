/*
 * Copyright (c) 2014 INRIA, INSA Rennes
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.adaptationmanager.plangeneration;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.EList;

import eu.paasage.camel.deployment.CommunicationInstance;
import eu.paasage.camel.deployment.ComponentInstance;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.RequiredHostInstance;
import eu.paasage.camel.deployment.VMInstance;
import eu.paasage.camel.deployment.HostingInstance;
import eu.paasage.camel.deployment.InternalComponentInstance;
/**
 * A class to compare two deployment models.
 * Based on CloudMLModelComparator from http://cloudml.org 
 * 
 */
public class ModelComparator {

    private static final Logger LOGGER = Logger.getLogger(ModelComparator.class.getName());

    private Map<VMInstance, VMInstance> matchedVMs = new Hashtable<VMInstance, VMInstance>();
    private List<VMInstance> removedVMs = new ArrayList<VMInstance>();
    private List<VMInstance> addedVMs = new ArrayList<VMInstance>();
    
    private Map<ComponentInstance, ComponentInstance> matchedComponents = new Hashtable<ComponentInstance, ComponentInstance>();
    private List<InternalComponentInstance> removedComponents = new ArrayList<InternalComponentInstance>();
    private List<InternalComponentInstance> addedComponents = new ArrayList<InternalComponentInstance>();
    
    private Map<HostingInstance, HostingInstance> matchedHostings= new Hashtable<HostingInstance, HostingInstance>();
    private List<HostingInstance> addedHostings= new ArrayList<HostingInstance>();
    private List<HostingInstance> removedHostings= new ArrayList<HostingInstance>();
    
    private Map<CommunicationInstance, CommunicationInstance> matchedCommunications= new Hashtable<CommunicationInstance, CommunicationInstance>();
    private List<CommunicationInstance> addedCommunications= new ArrayList<CommunicationInstance>();
    private List<CommunicationInstance> removedCommunications= new ArrayList<CommunicationInstance>();
      
     
    private Set<String> commands = new HashSet<String>();
    
    private DeploymentModel currentDM;
    private DeploymentModel targetDM;

    public ModelComparator(DeploymentModel currentDM, DeploymentModel targetDM) {
        this.currentDM = currentDM;
        this.targetDM = targetDM;
    }

    public void setTargetDeploymentModel(DeploymentModel targetDM) {
        this.targetDM = targetDM;
        clean();
    }

    public void setCurrentDeploymentModel(DeploymentModel currentDM) {
        this.currentDM = currentDM;
        clean();
    }

    public Set<String> getCommands() {
        return this.commands;
    }

    /**
     * Compare the targeted deployment model to the deployment model of the
     * current system
     */
    public void compareModels() {
        compareVMs();
        LOGGER.log(Level.FINE, ">> Removed VMs :" + removedVMs.toString());
        LOGGER.log(Level.FINE, ">> Added VMs  :" + addedVMs.toString());

        compareHostings();
        LOGGER.log(Level.FINE, ">> Removed Hostings :" + removedHostings.toString());
        LOGGER.log(Level.FINE, ">> Added Hostings :" + addedHostings.toString());

        compareCommunications();
        LOGGER.log(Level.FINE, ">> Removed Communications :" + removedCommunications.toString());
        LOGGER.log(Level.FINE, ">> Added Communications :" + addedCommunications.toString());

        compareComponents();
        LOGGER.log(Level.FINE, ">> Removed components: " + removedComponents.toString());
        LOGGER.log(Level.FINE, ">> Added components: " + addedComponents.toString());
    }


	/**
     * Compares the vms between the targeted and the current deployment model
     */
    public void compareVMs() {
        LOGGER.log(Level.INFO, ">> Comparing vms ...");
/*        for (VMInstance ni : ModelUtil.getVMInstances(currentDM)) {
			Boolean match = false;
            secondloop:
            {
                for (VMInstance ni2 : ModelUtil.getVMInstances(targetDM)) {
                    if (equalVMInstance(ni,ni2)) {
                    	match=true;
                        matchedVMs.put(ni, ni2);
                        break secondloop;
                    }
                }
            }
            if (!match) {
            	removedVMs.add(ni);
            }
        }*/
        Boolean match = false;
        
        for (VMInstance ni : currentDM.getVmInstances()) {
            secondloop:
            {
                for (VMInstance ni2 : targetDM.getVmInstances()) {
                    if (equalVMInstance(ni,ni2)) {
                    	match=true;
                        matchedVMs.put(ni, ni2);
                        break secondloop;
                    }
                }
            }
            if (!match) {
            	removedVMs.add(ni);
            }
        }
        //add the rest
        addVMs();
    }

	private boolean equalVMInstance(VMInstance ni, VMInstance ni2) {
		return ni.getName().equals(ni2.getName()) && ni.getType().getName().equals(ni2.getType().getName());
	}
	
    private void addVMs() {
//        addedVMs = new ArrayList<VMInstance>(ModelUtil.getVMInstances(targetDM));
        addedVMs = new ArrayList<VMInstance>(targetDM.getVmInstances());
        addedVMs.removeAll(matchedVMs.values());
    }

    /**
     * Compares the Hostings between the targeted and the current
     * deployment model
     */
    public void compareHostings() {
        LOGGER.log(Level.INFO, ">> Comparing Hostings ...");
        for (HostingInstance ni : currentDM.getHostingInstances()) {
			Boolean match = false;
            secondloop:
            {
                for (HostingInstance ni2 : targetDM.getHostingInstances()) {
                    if (equalHostingInstance(ni,ni2)) {
                    	match=true;
                        matchedHostings.put(ni, ni2);
                        break secondloop;
                    }   
                }
            }
            if (!match) {
                removedHostings.add(ni);
            }
        }
        //add the rest
        addHostings();
    }

    private boolean equalHostingInstance(HostingInstance ni, HostingInstance ni2) {
		return ni.getName().equals(ni2.getName()) && ni.getType().getName().equals(ni2.getType().getName());
	}

    private void addHostings() {
        addedHostings= new ArrayList<HostingInstance>(targetDM.getHostingInstances());
        addedHostings.removeAll(matchedHostings.values());
    }
    /**
     * Compares the Communications between the targeted and the current
     * deployment model
     */
    public void compareCommunications() {
        LOGGER.log(Level.INFO, ">> Comparing Communications ...");
        for (CommunicationInstance ni : currentDM.getCommunicationInstances()) {
			Boolean match = false;
            secondloop:
            {
                for (CommunicationInstance ni2 : targetDM.getCommunicationInstances()) {
                    if (equalCommunicationInstance(ni, ni2)) {
                    	match=true;
                    	matchedCommunications.put(ni, ni2);
                        break secondloop;
                    }
                }
            }
            if (!match) {
                removedCommunications.add(ni);
            }
        }
        //add the rest
        addCommunications();
    }

    private boolean equalCommunicationInstance(CommunicationInstance ni,
			CommunicationInstance ni2) {
    	//Changed to match Camel 2.0.0 - taken plan_generator (Shirley)
/*    	boolean b1=equalComponentInstance((InternalComponentInstance)ni.getRequiredCommunicationInstance().getOwner(), (InternalComponentInstance)ni2.getRequiredCommunicationInstance().getOwner());
    	boolean b2=equalComponentInstance((InternalComponentInstance)ni.getProvidedCommunicationInstance().getOwner(), (InternalComponentInstance)ni2.getProvidedCommunicationInstance().getOwner());
    	
        return b1&&b2;*/
    	
		return (ni.getName().equals(ni2.getName()) && ni.getType().equals(ni2.getType()) &&
				ni.getProvidedCommunicationInstance().getName().equals(ni2.getProvidedCommunicationInstance().getName()) &&
				/*ni.getProvidedCommunicationInstance().getOwner().getName().equals(ni2.getProvidedCommunicationInstance().getOwner().getName()) &&
				ni.getProvidedCommunicationInstance().getOwner().getType().equals(ni2.getProvidedCommunicationInstance().getOwner().getType()) &&*/
				ni.getRequiredCommunicationInstance().getName().equals(ni2.getRequiredCommunicationInstance().getName()) /*&&
				ni.getRequiredCommunicationInstance().getOwner().getName().equals(ni2.getRequiredCommunicationInstance().getOwner().getName()) &&
				ni.getRequiredCommunicationInstance().getOwner().getType().equals(ni2.getRequiredCommunicationInstance().getOwner().getType())*/
				);
	}

	private void addCommunications() {
        addedCommunications = new ArrayList<CommunicationInstance>(targetDM.getCommunicationInstances());
        addedCommunications.removeAll(matchedCommunications.values());
    }
    
    /**
     * Compares the components between the targeted and the current deployment
     * model
     */
    public void compareComponents() {
        LOGGER.log(Level.INFO, ">> Comparing components ...");
/*        for (InternalComponentInstance ni : ModelUtil.getInternalComponentInstances(currentDM)) {
			Boolean match = false;
            secondloop:
            {
                for (InternalComponentInstance ni2 : ModelUtil.getInternalComponentInstances(targetDM)) {
                    if (equalComponentInstance(ni, ni2)) {
                    	match=true;
                        matchedComponents.put(ni, ni2);
                        break secondloop;
                    }
                }
            }
            if (!match) {
                removedComponents.add(ni);
            }
        }*/
        Boolean match = false;
        
        for (InternalComponentInstance ni : currentDM.getInternalComponentInstances()) {

            secondloop:
            {
                for (InternalComponentInstance ni2 : targetDM.getInternalComponentInstances()) {
                    if (equalComponentInstance(ni, ni2)) {
                    	match=true;
                        matchedComponents.put(ni, ni2);
                        break secondloop;
                    }
                }
            }
            if (!match) {
                removedComponents.add(ni);
            }
        }
        //add the rest
        addComponents();
    }

    private boolean equalComponentInstance(InternalComponentInstance ni,
			InternalComponentInstance ni2) {
    	//Changed to match Camel 2.0.0 - there's more comparisons in plan_generator (Shirley)
        Boolean match = ni.getName().equals(ni2.getName()) && ni.getType().getName().equals(ni2.getType().getName());
        if (!match) return false;
/*        ComponentInstance c = ni.getRequiredHostInstance().getOwner();
  		ComponentInstance c2=ni2.getRequiredHostInstance().getOwner();
  		if (c==null) return (c2==null);
        if (c instanceof InternalComponentInstance) {
        	return (c2 instanceof InternalComponentInstance) && c.getName().equals(c2.getName()) && c.getType().getName().equals(c2.getType().getName());
        }
        if (c instanceof VMInstance) {
        	return (c2 instanceof VMInstance) && equalVMInstance((VMInstance)c,(VMInstance)c2 );
        }
       return false;*/
        
		//check required host instances name & type
		if((!ni.getRequiredHostInstance().getName().equals(ni2.getRequiredHostInstance().getName())) || 
				(!ni.getRequiredHostInstance().getType().getName().equals(ni2.getRequiredHostInstance().getType().getName()))){
			return true;
		}
		//no nested internal component instances!
		return true;
	}

	private void addComponents() {
        //addedComponents = new ArrayList<InternalComponentInstance>(ModelUtil.getInternalComponentInstances(targetDM));
		addedComponents = new ArrayList<InternalComponentInstance>(targetDM.getInternalComponentInstances());
        addedComponents.removeAll(matchedComponents.values());
    }

    /**
     * Clean all the lists resulting from the previous comparison
     */
    private void clean() {
        matchedVMs.clear();
        removedVMs.clear();
        addedVMs.clear();

        matchedComponents.clear();
        removedComponents.clear();
        addedComponents.clear();

        matchedHostings.clear();
        removedHostings.clear();
        addedHostings.clear();

        commands.clear();
    }

    public List<InternalComponentInstance> getRemovedComponents() {
        return this.removedComponents;
    }

    public List<InternalComponentInstance> getAddedComponents() {
        return this.addedComponents;
    }

    public List<VMInstance> getRemovedVMs() {
        return this.removedVMs;
    }

    public List<VMInstance> getAddedVMs() {
        return this.addedVMs;
    }

    public List<HostingInstance> getRemovedHostings() {
        return this.removedHostings;
    }

    public List<HostingInstance> getAddedHostings() {
        return this.addedHostings;
    }

    public Map<HostingInstance, HostingInstance> getMatchedHostings() {
        return this.matchedHostings;
    }

    public Map<ComponentInstance, ComponentInstance> getMatchedComponents() {
        return this.matchedComponents;
    }

    public Map<VMInstance, VMInstance> getMatchedVMs() {
        return this.matchedVMs;
    }
    
        public List<CommunicationInstance> getRemovedCommmunications() {
        return this.removedCommunications;
    }

    public List<CommunicationInstance> getAddedCommunications() {
        return this.addedCommunications;
    }
}

