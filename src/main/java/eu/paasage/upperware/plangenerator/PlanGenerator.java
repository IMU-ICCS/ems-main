/*
 * Copyright (c) 2014-5 UK Science and Technology Facilities Council
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.plangenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import com.eclipsesource.json.JsonObject;

import eu.paasage.camel.Application;
import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.Communication;
import eu.paasage.camel.deployment.CommunicationInstance;
import eu.paasage.camel.deployment.Component;
import eu.paasage.camel.deployment.ComponentInstance;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.Hosting;
import eu.paasage.camel.deployment.HostingInstance;
import eu.paasage.camel.deployment.InternalComponent;
import eu.paasage.camel.deployment.InternalComponentInstance;
import eu.paasage.camel.deployment.ProvidedCommunication;
import eu.paasage.camel.deployment.VM;
import eu.paasage.camel.deployment.VMInstance;
import eu.paasage.upperware.plangenerator.exception.ModelComparatorException;
import eu.paasage.upperware.plangenerator.exception.PlanGenerationException;
import eu.paasage.upperware.plangenerator.model.Plan;
import eu.paasage.upperware.plangenerator.model.task.ApplicationInstanceTask;
import eu.paasage.upperware.plangenerator.model.task.ApplicationTask;
import eu.paasage.upperware.plangenerator.model.task.CommunicationInstanceTask;
import eu.paasage.upperware.plangenerator.model.task.CommunicationTypeTask;
import eu.paasage.upperware.plangenerator.model.task.ComponentInstanceTask;
import eu.paasage.upperware.plangenerator.model.task.ComponentTypeTask;
import eu.paasage.upperware.plangenerator.model.task.ConfigurationTask;
import eu.paasage.upperware.plangenerator.model.task.HostingInstanceTask;
import eu.paasage.upperware.plangenerator.model.task.HostingTypeTask;
import eu.paasage.upperware.plangenerator.model.task.VMInstanceTask;
import eu.paasage.upperware.plangenerator.model.task.VMTypeTask;
import eu.paasage.upperware.plangenerator.type.TaskType;
import eu.paasage.upperware.plangenerator.util.ModelToJsonConverter;

/**
 * The entry point to the plan generator.  This component is used by the Adaptation Manager 
 * to compute configuration plan of a cloud application. 
 * Based on prototype developed by INRIA, INSA Rennes.
 * <p> 
 * @author  Shirley Crompton (shirley.crompton@stfc.ac.uk)
 * org      UK Science and Technology Facilities Council
 * project  PaaSage
 * @version $Name$ $Revision$ $Date$
 */
public class PlanGenerator {
	
	/** Message logger */
	private static final Logger log = Logger.getLogger(PlanGenerator.class.getName());
	/** attribute to set deployment method required, default is false */
	boolean simpleInitialDeployment = false;
	/** The generated plan */
	Plan plan;
	/** the current deployment model */
	DeploymentModel currentDM;
	/** the target deployment model */
	DeploymentModel targetDM;
	
	/**
	 * Default constructor
	 */
	public PlanGenerator() {
	}	
	
	/**
	 * Construct an instance and set deployment mode 
	 * <p>
	 * @param simpleInitialDeployment	true for simple initial deployment, else false
	 */
	public PlanGenerator(boolean simpleInitialDeployment) {
		this.simpleInitialDeployment = simpleInitialDeployment;	//set flag
	}		
	
	/**
	 * Construct an instance
	 * <p>
	 * @param currentModel	the current {@link eu.paasage.camel.deployment.DeploymentModel <em>DeploymentModel</em>}
	 * @param targetModel	the target {@link eu.paasage.camel.deployment.DeploymentModel <em>DeploymentModel</em>}
	 * @return	the computed deployment {@link eu.paasage.upperware.plangenerator.model.Plan <em>Plan</em>} containing 
	 * 			a list of deployment tasks to create/update a cloud application managed by PaaSage.
	 * @throws  ModelComparatorException for error during models comparison
	 * @throws	PlanGenerationException for error during dependencies generation
	 */
	public Plan generate(DeploymentModel currentModel, DeploymentModel targetModel) throws PlanGenerationException, ModelComparatorException {
		//currentModel is nullable but must have a targetModel
		//validate
		if(targetModel == null  ){
			log.error("target deployment deployment is null, cannot proceed.");
			return null;
		}
		this.targetDM = targetModel;
		//create a new Plan with the deployment model name
		this.plan = new Plan(targetDM.getName());
		//Case simple initial deployment
		if(simpleInitialDeployment){ //flag set by AdaptationManager			
			//
			buildSimpleDeploymentPlan();			
			log.info("Generated plan to create " + this.plan.getAppName() + " with " + this.plan.getTasks().size() + " tasks.");			
			return this.plan;			
		}
		//Case re-configuration
		if (currentModel == null) {		
			log.error("current model is null, cannot define re-configuration plan.");
			return null;
		}	
		this.currentDM = currentModel;
		buildReconfigPlan();
		log.info("Generated reconfiguration plan with "+ this.plan.getTasks().size() +" tasks");
		//
		return this.plan;
	}
	
	////////////////////////////////////////////////////////////////private methods////////////////////////////////////////////////////////////
	/**
	 * Build a reconfiguration plan with dependencies.
	 * <p>
	 * @throws	{@link eu.paasage.upperware.plangenerator.exception.PlanGenerationException <em>PlanGenerationException</em>} or
	 * 			{@link eu.paasage.upperware.plangenerator.exception.ModelComparatorException <em>ModelComparatorException</em>}on error
	 */
	private void buildReconfigPlan() throws PlanGenerationException, ModelComparatorException {
		//
		log.debug("....building a reconfiguration plan.....");
		//go ahead and compare target model against current model		
		ModelComparator mc = new ModelComparator(this.currentDM, this.targetDM);
		mc.compareModels();
		//all tasks depends on the applicationTask
		List<VMTypeTask> vmTypeTasks = new ArrayList<VMTypeTask>();
		List<VMInstanceTask> vmInsTasks = new ArrayList<VMInstanceTask>();
		List<ComponentTypeTask> compTypeTasks = new ArrayList<ComponentTypeTask>();
		List<ComponentInstanceTask> compInsTasks = new ArrayList<ComponentInstanceTask>();
		List<HostingTypeTask> hostingTypeTasks = new ArrayList<HostingTypeTask>();
		List<HostingInstanceTask> hostingInsTasks = new ArrayList<HostingInstanceTask>();
		List<CommunicationTypeTask> communicationTypeTasks = new ArrayList<CommunicationTypeTask>();
		List<CommunicationTypeTask> orphanComTypeTasks = new ArrayList<CommunicationTypeTask>();
		List<CommunicationInstanceTask> communicationInsTasks = new ArrayList<CommunicationInstanceTask>();
		ApplicationTask appTask = null;
		ApplicationInstanceTask appInsTask = null;
		//
		//the root application obj only have one attribute - name that matters. 
		CamelModel currentCamel = (CamelModel) currentDM.eContainer();
		CamelModel targetCamel = (CamelModel) targetDM.eContainer();
		//
		if(!currentCamel.getApplications().get(0).getName().equals(targetCamel.getApplications().get(0).getName())){
			appTask = getApplicationTask(targetCamel.getApplications().get(0), TaskType.UPDATE);
			this.plan.getTasks().add(appTask);				
			//app instance is not a camel concept.  InstanceName is built on the model name
			//for Paasage EW, the appInstance is just a wrapper, so update has no meaning.  
			//These 2 update tasks can be done at any time during the deployment
			appInsTask = getAppInstanceTask(targetCamel.getApplications().get(0), TaskType.UPDATE);
			appInsTask.getDependencies().add(appTask);	//add the dependency to app task
			this.plan.getTasks().add(appInsTask);
		}else{
			log.info("Application/Instance name unchanged ....");
		}
		
		/***************************************** REMOVAL TASKS : the objects (id) already exists in EW ********************************************/
		//removing all objects means undeploying the app but cannot assume deleting the app
		//process in this order communication instance, hosting instance, internalComponent instance, VM instance,communication, hosting, 
		//                internalComponent, VM. If EW does not enforce dependency, it is possible to do all these in parallel
		//communication instances
		if(!mc.getRemovedComInstances().isEmpty()){
			log.debug(mc.getRemovedComInstances().size() + " number of communication instances to remove....");
			for(CommunicationInstance comIns : mc.getRemovedComInstances()){
				communicationInsTasks.add(getCommunicationInsTask(comIns, TaskType.DELETE));
			}
		}else{
			log.info("No communication to remove ....");
		}
		//hosting instances	
		if(!mc.getRemovedHostingInstances().isEmpty()){
			log.debug(mc.getRemovedHostingInstances().size() + " number of hosting instances to remove....");
			for(HostingInstance hi : mc.getRemovedHostingInstances()){
				hostingInsTasks.add(getHostingInsTask(hi, TaskType.DELETE));
			}
		}else{
			log.info("No hosting instances to remove ....");
		}
		//internalComponent instances ??these depends on the binding tasks?? 
		if(!mc.getRemovedComInstances().isEmpty()){
			log.debug(mc.getRemovedComInstances().size() + " number of internal component instances to remove....");
			for(InternalComponentInstance ici : mc.getRemovedInternalComponentInstances()){
				compInsTasks.add(getComponentInsTask(ici, TaskType.DELETE));
			}
		}else{
			log.info("No internal component instances to remove ....");
		}
		//vm instances ??the consumer (internal component instance) depends on this
		if(!mc.getRemovedVMInstances().isEmpty()){
			log.debug(mc.getRemovedVMInstances().size() + " number of VM instances to remove....");
			for(VMInstance vmi : mc.getRemovedVMInstances()){
				vmInsTasks.add(getVMInsTask(vmi, TaskType.DELETE));
			}
		}else{
			log.info("No VM instances to remove ....");
		}
		//type tasks, again if EW does not enforce dependency, we can process them all in parallel		
		//communications
		if(!mc.getRemovedCommunications().isEmpty()){
			log.debug(mc.getRemovedCommunications().size() + " number of communication to remove....");
			for(Communication com : mc.getRemovedCommunications()){
				communicationTypeTasks.add(getCommunicationTypeTask(com, TaskType.DELETE));
			}
		}else{
			log.info("No communication to remove ....");
		}
		//12Jan2016 add the orphan coms to the communicationTypeTasks now
		if(!mc.getRemovedOrphanCommunications().isEmpty()){
			log.debug(mc.getRemovedOrphanCommunications().size() + " number of Orphan communication objects to remove....");
			for(ProvidedCommunication orphan : mc.getRemovedOrphanCommunications()){
				orphanComTypeTasks.add(getOrphanComTask(orphan, TaskType.DELETE));
			}
		}
		//hostings
		if(!mc.getRemovedHostings().isEmpty()){
			log.debug(mc.getRemovedHostings().size() + " number of hosting to remove....");
			for(Hosting hosting : mc.getRemovedHostings()){
				hostingTypeTasks.add(getHostingTypeTask(hosting, TaskType.DELETE));
			}
		}else{
			log.info("No hosting to remove ....");
		}
		//internal components
		if(!mc.getRemovedInternalComponents().isEmpty()){
			log.debug(mc.getRemovedInternalComponents().size() + " number of internal components to remove....");
			for(InternalComponent comp : mc.getRemovedInternalComponents()){
				compTypeTasks.add(getComponentTypeTask(comp, TaskType.DELETE));
			}
		}else{
			log.info("No internal component to remove ....");
		}
		//VMs
		if(!mc.getRemovedVMTypes().isEmpty()){
			log.debug(mc.getRemovedVMTypes().size() + " number of VM to remove....");
			for(VM vm : mc.getRemovedVMTypes()){
				vmTypeTasks.add(getVMTypeTask(vm, TaskType.DELETE));
			}
		}else{
			log.info("No VM to remove ....");
		}
		/***************************************** CREATE TASKS - the objects (id) do not exist in EW ************************************/
		//Types
		//VMs
		if(!mc.getAddedVMTypes().isEmpty()){
			log.debug(mc.getAddedVMTypes().size() + " number of VMs to add....");
			for(VM vm : mc.getAddedVMTypes()){
				vmTypeTasks.add(getVMTypeTask(vm, TaskType.CREATE));
			}
		}else{
			log.info("No VM to create ....");
		}
		//internal components
		if(!mc.getAddedInternalComponents().isEmpty()){
			log.debug(mc.getAddedInternalComponents().size() + " number of internal components to add....");
			for(InternalComponent comp : mc.getAddedInternalComponents()){
				compTypeTasks.add(getComponentTypeTask(comp, TaskType.CREATE));
				//app should already exist, app can only be updated, so ignore dependency				
			}//end for(InternalComponent comp : mc.getAddedInternalComponents())
		}else{
			log.info("No internal component to create ....");
		}		
		//communication
		if(!mc.getAddedCommunications().isEmpty()){
			log.debug(mc.getAddedCommunications().size() + " number of communication to add....");
			for(Communication com : mc.getAddedCommunications()){
				communicationTypeTasks.add(getCommunicationTypeTask(com, TaskType.CREATE));
			}
		}else{
			log.info("No communication object to create ....");
		}
		//hosting
		if(!mc.getAddedHostings().isEmpty()){
			log.debug(mc.getAddedHostings().size() + " number of hosting to add....");
			for(Hosting hosting : mc.getAddedHostings()){
				hostingTypeTasks.add(getHostingTypeTask(hosting, TaskType.CREATE));
				//27Nov15 ExecutionWare doesn't care about hosting type, so ignore dependencies
			}
		}else{
			log.info("No hosting object to create ....");
		}
		//Instances
		//VM Instance		
		if(!mc.getAddedVMInstances().isEmpty()){
			log.debug(mc.getAddedVMInstances().size() + " number of VM instances to add ....");
			for(VMInstance avm : mc.getAddedVMInstances()){	
				vmInsTasks.add(getVMInsTask(avm, TaskType.CREATE));	
				//task dependencies added further downstream
			}		
		}else{
			log.info("No VM instances to add ....");
		}
		//Internal Component Instance		
		if(!mc.getAddedInternalComponentInstances().isEmpty()){
			log.debug(mc.getAddedInternalComponentInstances().size() + " number of internal component instances to add ....");
			//
			for(InternalComponentInstance aic : mc.getAddedInternalComponentInstances()){
				ComponentInstanceTask cit = getComponentInsTask(aic, TaskType.CREATE);
				//check if any comp instance task to add
				if(appInsTask != null){
					cit.getDependencies().add(appInsTask);
				}
				compInsTasks.add(cit);
			}
		}else{
			log.info("No internal component instances to add ....");
		}
		//hosting instances		
		if(!mc.getAddedHostingInstances().isEmpty()){
			log.debug(mc.getAddedHostingInstances().size() + " number of hosting instances to add ....");
			//
			for(HostingInstance ahi : mc.getAddedHostingInstances()){
				hostingInsTasks.add(getHostingInsTask(ahi, TaskType.CREATE));
			}	
		}else{
			log.info("No hosting instances to add ....");
		}
		//communication instances
		if(!mc.getAddedComInstances().isEmpty()){
			log.debug(mc.getAddedComInstances().size() + " number of communication instances to add ....");
			for(CommunicationInstance aci : mc.getAddedComInstances()){
				communicationInsTasks.add(getCommunicationInsTask(aci, TaskType.CREATE));	
				//27Nov15 ExecutionWare doesn't care about hosting type, so ignoring dependencies
			}//end for each com instance
		}else{
			log.info("No communication instances to add ....");
		}
		//12Jan2016  orphan communication
		if(!mc.getAddedOrphanCommunications().isEmpty()){
			log.debug(mc.getAddedOrphanCommunications().size() + " number of orphan communication to add ....");
			for(ProvidedCommunication orphan : mc.getAddedOrphanCommunications()){
				orphanComTypeTasks.add(getOrphanComTask(orphan, TaskType.CREATE));
			}
		}
		
		/***************************************** UPDATE TASKS - the objects (id) already exist in EW *********************************************/ 
		//assuming the update tasks can be done simultaneously, i.e. EW does not enforce dependency between objects
		//VMs
		if(!mc.getUpdatedVMTypes().isEmpty()){
			log.debug(mc.getUpdatedVMTypes().size() + " number of VMs to update....");
			for(VM vm : mc.getUpdatedVMTypes()){
				vmTypeTasks.add(getVMTypeTask(vm, TaskType.UPDATE));
			}
		}else{
			log.info("No VM to update ....");
		}
		//internal components
		if(!mc.getUpdatedInternalComponents().isEmpty()){
			log.debug(mc.getUpdatedInternalComponents().size() + " number of internal components to update....");
			for(InternalComponent comp : mc.getUpdatedInternalComponents()){
				compTypeTasks.add(getComponentTypeTask(comp, TaskType.UPDATE));
			}
		}else{
			log.info("No internal component to update ....");
		}		
		//communication
		if(!mc.getUpdatedCommunications().isEmpty()){
			log.debug(mc.getUpdatedCommunications().size() + " number of communication to update....");
			for(Communication com : mc.getUpdatedCommunications()){
				communicationTypeTasks.add(getCommunicationTypeTask(com, TaskType.UPDATE));
			}
		}else{
			log.info("No communication object to update ....");
		}
		//12Jan2016  orphan communication
		if(!mc.getUpdatedOrphanCommunications().isEmpty()){
			log.debug(mc.getUpdatedOrphanCommunications().size() + " number of orphan communication to update ....");
			for(ProvidedCommunication orphan : mc.getUpdatedOrphanCommunications()){
				orphanComTypeTasks.add(getOrphanComTask(orphan, TaskType.UPDATE));
			}
		}
		//hosting
		if(!mc.getUpdatedHostings().isEmpty()){
			log.debug(mc.getUpdatedHostings().size() + " number of hosting to update....");
			for(Hosting hosting : mc.getUpdatedHostings()){
				hostingTypeTasks.add(getHostingTypeTask(hosting, TaskType.UPDATE));
			}
		}else{
			log.info("No hosting object to update ....");
		}
		//
		//instances to update (Execution Was does not care about com instances
		//communication instance
		if(!mc.getUpdatedComInstances().isEmpty()){
			log.debug(mc.getUpdatedComInstances().size() + " number of communication instances to updated....");
			for(CommunicationInstance comIns : mc.getUpdatedComInstances()){
				communicationInsTasks.add(getCommunicationInsTask(comIns, TaskType.UPDATE));
			}
		}else{
			log.info("No communication instance to remove ....");
		}
		//hosting instances	
		if(!mc.getUpdatedHostingInstances().isEmpty()){
			log.debug(mc.getUpdatedHostingInstances().size() + " number of hosting instances to update....");
			for(HostingInstance hi : mc.getUpdatedHostingInstances()){
				hostingInsTasks.add(getHostingInsTask(hi, TaskType.UPDATE));
			}
		}else{
			log.info("No hosting instances to update ....");
		}
		//internalComponent instances ??these depends on the binding tasks
		if(!mc.getUpdatedInternalComponentInstance().isEmpty()){
			log.debug(mc.getUpdatedInternalComponentInstance().size() + " number of internal component instances to update....");
			for(InternalComponentInstance ici : mc.getUpdatedInternalComponentInstance()){
				ComponentInstanceTask cit = getComponentInsTask(ici, TaskType.UPDATE);
				if(appInsTask != null){
					cit.getDependencies().add(appInsTask);
				}
				compInsTasks.add(cit);
			}
		}else{
			log.info("No internal component instances to updated ....");
		}
		//vm instances ?? the consumer (internal component instance) task depends on these
		if(!mc.getUpdatedVMInstances().isEmpty()){
			log.debug(mc.getUpdatedVMInstances().size() + " number of VM instances to update....");
			for(VMInstance vmi : mc.getUpdatedVMInstances()){
				vmInsTasks.add(getVMInsTask(vmi, TaskType.UPDATE));
			}
		}else{
			log.info("No VM instances to update ....");
		}	
		//Process dependencies....
		//Better to do the dependencies after all the tasks have been created as the relationship can be complex
		//basic assumption : 
		//1. delete tasks can be process at any time (assuming EW does not enforce dependency)
		//2. update application/instance tasks can also be done at any time as it only got the name attribute
		//3. update and create tasks can be dependent on each other.  Instances depend on Types (definition), bindings depends on the consumer/dependent.
		//4. update will not change the existing identifier, create will change the identifier.  Therefore update and create tasks do not depend on delete task.
		//5. (18Jan16) delete tasks, the instance depends on the parent type (if the type is also being deleted) 
		//6. (18Jan16) special case for orphan commuication, VMInstances must be deleted BEFORE the communication types
		//Orphan Communications
		if(!orphanComTypeTasks.isEmpty()){
			log.debug("adding task dependencies to orphan communications....");
			for(CommunicationTypeTask orphanCtt : orphanComTypeTasks){
				//
				if(!orphanCtt.getTaskType().equals(TaskType.DELETE)){ //only process update/create
					ConfigurationTask parent = getDepended(compTypeTasks, orphanCtt.getJsonModel().get("providerCompTypeTask").asString(), TaskType.CREATE);
					if(parent == null){//try look in update objects
						parent = getDepended(compTypeTasks, orphanCtt.getJsonModel().get("providerCompTypeTask").asString(), TaskType.UPDATE);
					}
					if(parent != null){
						orphanCtt.getDependencies().add(parent);
						log.debug("...added parent task dependency(" + parent.getName() + ") to orphan communication type task : " + orphanCtt.getName());
					}else{
						//there must be a parent, as we get the orphan communication from the parent.  See ModelComparator.compareOrphanCommunications() method
						throw new PlanGenerationException("Failed to find the parent InternalComponent or orphan communication(" + orphanCtt.getName() + ").....");
					}
				}else{//18Jan16 delete communication type depends on delete VMInstance tasks
					log.debug("..about to find delete VMInstanceTask for orphan communication type(" + orphanCtt.getName() + ")");
					for(VMInstanceTask vmit : vmInsTasks){
						if(vmit.getTaskType().equals(TaskType.DELETE)){	//delete VMInstances first
							log.debug("Added delete VMInstanceTask(" + vmit.getName() + ") as dependency for orphan communication type(" + orphanCtt.getName() + ")");
							orphanCtt.getDependencies().add(vmit);
						}
					}
				}
				this.plan.getTasks().add(orphanCtt);//add task to plan
			}//end for
		}else{
			log.info("No Orphan Communication type tasks to add .....");
		}
		//VM Types
		if(!vmTypeTasks.isEmpty()){
			log.debug(vmTypeTasks.size() + " number of VM type tasks added to the plan");
			this.plan.getTasks().addAll(vmTypeTasks);
		}else{
			log.info("No VM type tasks to add .....");
		}
		//VM Instances
		if(!vmInsTasks.isEmpty()){ 
			log.debug(vmInsTasks.size() + " number of VM instance tasks to added to plan ....");
			for(VMInstanceTask vmit : vmInsTasks){
				if(!vmit.getTaskType().equals(TaskType.DELETE)){	//only process update/create
					//can't be both update and create
					ConfigurationTask parent = getDepended(vmTypeTasks, vmit.getJsonModel().get("type").asString(), TaskType.CREATE);
					if(parent == null){//try look in update objs
						parent = getDepended(vmTypeTasks,vmit.getJsonModel().get("type").asString(), TaskType.UPDATE);
					}
					if(parent != null){
						vmit.getDependencies().add(parent);
						log.debug("...added type dependency(" + parent.getName() + ") to VM intance task : " + vmit.getName());
					}
					//if no parent, the type must be already 'deployed'
					//12Jan16 add dependencies to new/updated comm type
					//if(vmit.getTaskType().equals(TaskType.CREATE)){
						//we are adding obj references, so should be OK if the objs are changed further down stream
						setComTypeDependencies(vmit, communicationTypeTasks, TaskType.CREATE);
						setComTypeDependencies(vmit, communicationTypeTasks, TaskType.UPDATE);
						setComTypeDependencies(vmit, orphanComTypeTasks, TaskType.CREATE);
						setComTypeDependencies(vmit, orphanComTypeTasks, TaskType.DELETE);						
					//}
					//12Jan16 
				}else{//18Jan16 delete VMInstance depends on delete VM Type
					log.debug("..about to find delete VMTypeTask for vm instance type(" + vmit.getName() + ")");
					for(VMTypeTask vm: vmTypeTasks){
						if(vm.getTaskType().equals(TaskType.DELETE)){
							log.debug("Addming vmtype(" + vm.getName() + ") to vm instance(" + vmit.getName() + ")....");
							vmit.getDependencies().add(vm);
						}
					}
				}
				this.plan.getTasks().add(vmit);
			}//end for each vmInsTask				
		}else{
			log.info("No VM instance tasks to add dependencies ....");
		}
		//component type 
		if(!compTypeTasks.isEmpty()){
			log.debug(compTypeTasks.size() + " number of create/update/delete component type tasks to add to plan ....");
			//add the application dependencies, VMType dependency processed in hosting type
			if(appTask != null){
				for(ComponentTypeTask compTypeTask : compTypeTasks){
					compTypeTask.getDependencies().add(appTask);
				}
			}
			this.plan.getTasks().addAll(compTypeTasks);
		}else{
			log.info("No component type tasks to add ....");
		}
		//internal component instances
		//add comp type dependencies, other dependencies are added when processing hosting & communication
		if(!compInsTasks.isEmpty()){ 
			log.debug(compInsTasks.size() + " number of update/create/delete compInsTasks to add to plan ....");
			for(ComponentInstanceTask ctt : compInsTasks){
				if(!ctt.getTaskType().equals(TaskType.DELETE)){	//only process update/create
					ConfigurationTask parent = getDepended(compTypeTasks, ctt.getJsonModel().get("type").asString(), TaskType.CREATE);
					if(parent == null){
						parent = getDepended(compTypeTasks, ctt.getJsonModel().get("type").asString(), TaskType.UPDATE);
					}
					if(parent != null){
						ctt.getDependencies().add(parent);
						log.debug("...added type dependency(" + parent.getName() + ") to component instance task : " + ctt.getName());
					}
					//if no parent, the type must be already 'deployed'
				}else{//18Jan16 delete  componentInstance depends on delete component Type
					log.debug("..about to find delete componentTypeTask for component instance type(" + ctt.getName() + ")");
					for(ComponentTypeTask cm: compTypeTasks){
						if(cm.getTaskType().equals(TaskType.DELETE)){
							log.debug("Adding component type task(" + cm.getName() + ") to component instance(" + ctt.getName() + ")....");
							ctt.getDependencies().add(cm);
						}
					}
					
				}
				this.plan.getTasks().add(ctt); //no other dependencies at this stage
			}//end for component instance task
		}else{
			log.info("No component instance tasks to add ....");
		}
		//hosting types
		if(!hostingTypeTasks.isEmpty()){
			log.debug(hostingTypeTasks.size() + " number of create/update/delete hosting type tasks to add to plan ....");
			List<Hosting> newHostings = new ArrayList<Hosting>();
			newHostings.addAll(mc.getUpdatedHostings());
			newHostings.addAll(mc.getAddedHostings());
			//
			for(HostingTypeTask hTask : hostingTypeTasks){
				if(!hTask.getTaskType().equals(TaskType.DELETE)){
					//hosting depends on components, but not the other way around to avoid cyclic dependency
					Component hostingProvider = null;	//initialised for each hTask
					Component hostingConsumer = null;
					ConfigurationTask hostingProviderTask = null;
					ConfigurationTask hostingConsumerTask = null;
					//need the hosting objects 
					for(Hosting hosting : newHostings){
						if(hTask.getName().equals(hosting.getName())){
							//got the original camel hosting
							hostingProvider = (Component) hosting.getProvidedHost().eContainer();
							hostingConsumer = (Component) hosting.getRequiredHost().eContainer();
							break;
						}					
					}
					//now locate the config tasks for these components
					if(hostingProvider instanceof VM){
						hostingProviderTask = getDepended(vmTypeTasks, hostingProvider.getName(), TaskType.CREATE);
						if(hostingProviderTask == null){
							hostingProviderTask = getDepended(vmTypeTasks, hostingProvider.getName(), TaskType.UPDATE);
						}					
					}else if(hostingProvider instanceof InternalComponent){
						hostingProviderTask = getDepended(compTypeTasks, hostingProvider.getName(), TaskType.CREATE);
						if(hostingProviderTask == null){
							hostingProviderTask = getDepended(compTypeTasks, hostingProvider.getName(), TaskType.UPDATE);
						}
					}
					//if none found, assume already deployed
					if(hostingProviderTask != null){	
						//8July15 : added owner task as requested by Adapter
						hTask.getJsonModel().add("providerCompTypeTask", hostingProviderTask.getName());
						hTask.getDependencies().add(hostingProviderTask);
					}else{
						log.info("...did not locate the hosting provider task(name = " + hostingProvider.getName() + ") for hosting task(" + hTask.getName() + ".  Assume already deployed.");					
					}
					hostingConsumerTask = getDepended(compTypeTasks, hostingConsumer.getName(), TaskType.CREATE);
					if(hostingConsumerTask == null){
						hostingConsumerTask = getDepended(vmTypeTasks, hostingProvider.getName(), TaskType.UPDATE);
					}	
					if(hostingConsumerTask != null){
						//8July15 : added owner task as requested by Adapter
						hTask.getJsonModel().add("consumerCompTypeTask", hostingConsumerTask.getName());
						hTask.getDependencies().add(hostingConsumerTask);
						//the consumer depends on the host
						if(hostingProviderTask != null){
							hostingConsumerTask.getDependencies().add(hostingProviderTask);
						}
					}else{
						log.info("...did not locate the hosting consumer task(name = " + hostingConsumer.getName() + ") for hosting task(" + hTask.getName() + ".  Assume already deployed.");					
					}
				}//end if NOT delete task
				this.plan.getTasks().add(hTask);	
			}//end for hosting task		
		}else{
			log.info("No hosting type tasks to add ....");
		}
		//hosting instances
		if(!hostingInsTasks.isEmpty()){
			log.debug(hostingInsTasks.size() + " number of create/update/delete hosting instance tasks to add to plan ....");
			List<HostingInstance> newHostingIns = new ArrayList<HostingInstance>();
			newHostingIns.addAll(mc.getUpdatedHostingInstances());
			newHostingIns.addAll(mc.getAddedHostingInstances());
			//
			for(HostingInstanceTask hiTask : hostingInsTasks){
				if(!hiTask.getTaskType().equals(TaskType.DELETE)){
					//search for the parent hosting type
					ConfigurationTask parent = getDepended(hostingTypeTasks, hiTask.getJsonModel().get("type").asString(), TaskType.CREATE);
					if(parent == null){
						parent = getDepended(hostingTypeTasks, hiTask.getJsonModel().get("type").asString(), TaskType.UPDATE);
					}
					if(parent != null){ //assume already deployed if none found
						hiTask.getDependencies().add(parent);
						log.debug("...added type dependency(" + parent.getName() + ") to " + hiTask.getName());
					}
					ComponentInstance hiProviderIns = null;
					ComponentInstance hiConsumerIns = null;
					ConfigurationTask hiProviderTask = null;
					ConfigurationTask hiConsumerTask = null;
					//looks for the parent type 
					for(HostingInstance hInstance : newHostingIns){
						if(hiTask.getName().equals(hInstance.getName())){
							//get the original camel hosting instance
							hiProviderIns = (ComponentInstance) hInstance.getProvidedHostInstance().eContainer();
							hiConsumerIns = (ComponentInstance) hInstance.getRequiredHostInstance().eContainer();
							break;
						}					
					}
					//now locate the consumer/provider tasks for these components
					if(hiProviderIns instanceof VMInstance){
						hiProviderTask = getDepended(vmInsTasks, hiProviderIns.getName(), TaskType.CREATE);
						if(hiProviderTask == null){
							hiProviderTask = getDepended(vmInsTasks, hiProviderIns.getName(), TaskType.UPDATE);
						}
					}else if(hiProviderIns instanceof InternalComponent){
						hiProviderTask = getDepended(compInsTasks, hiProviderIns.getName(), TaskType.CREATE);
						if(hiProviderTask == null){
							hiProviderTask = getDepended(compInsTasks, hiProviderIns.getName(), TaskType.UPDATE);
						}
					}
					//if none found, assume already deployed
					if(hiProviderTask != null){
						//8July15 : added owner task as requested by Adapter
						hiTask.getJsonModel().add("providerCompTypeTask", hiProviderTask.getName());
						hiTask.getDependencies().add(hiProviderTask);
					}else{
						log.info("...did not locate the hosting provider instance task(name = " + hiProviderIns.getName() + ") for hosting task(" + hiTask.getName() + ".  Assume already deployed.");
					}
					hiConsumerTask = getDepended(compInsTasks, hiConsumerIns.getName(), TaskType.CREATE);
					if(hiConsumerTask == null){
						hiConsumerTask = getDepended(compInsTasks, hiConsumerIns.getName(), TaskType.UPDATE);
					}
					if(hiConsumerTask != null){ //assume already deployed if none located
						//8July15 : added owner task as requested by Adapter
						hiTask.getJsonModel().add("consumerCompTypeTask", hiConsumerTask.getName());
						hiTask.getDependencies().add(hiConsumerTask);
						//the consumer depends on the host
						if(hiProviderTask != null){
							hiConsumerTask.getDependencies().add(hiProviderTask);
						}
					}else{
						log.info("...did not locate the hosting consumer instance task(name = " + hiConsumerIns.getName() + ") for hosting instance task(" + hiTask.getName() + ".  Assume already deployed.");					//hrow new PlanGenerationException("...failed to locate the hosting consumer instance task(name = " + hiConsumerIns.getName() + ") for hosting task(" + hTask.getName());
					}
				}//end if NOT delete task
				this.plan.getTasks().add(hiTask);	
			}//end for hosting instance task //18Jan16 EW does not recognise Hosting instances		
		}else{
			log.info("No hosting instance tasks to add ....");
		}
		//communication
		if(!communicationTypeTasks.isEmpty()){
			log.debug(communicationTypeTasks.size() + " number of communication type tasks to add ....");
			List<Communication> newCommunications = new ArrayList<Communication>();
			newCommunications.addAll(mc.getUpdatedCommunications());
			newCommunications.addAll(mc.getAddedCommunications());
			//
			for(CommunicationTypeTask commTypeTask : communicationTypeTasks){
				if(!commTypeTask.getTaskType().equals(TaskType.DELETE)){  
					//communication depends on components, but not the other way around to avoid cyclic dependency
					log.debug("...inside communication type task : " + commTypeTask.getName());
					//communication depends on components, but not the other way around to avoid cyclic dependency
					Component commProvider = null;	//initialised for each commTypeTask
					Component commConsumer = null;
					ConfigurationTask commProviderTask = null;
					ConfigurationTask commConsumerTask = null;
					//need the communication type objects 
					for(Communication communication : newCommunications){
						if(commTypeTask.getName().equals(communication.getName())){
							//got the original camel communication type
							commProvider = (Component) communication.getProvidedCommunication().eContainer();
							commConsumer = (Component) communication.getRequiredCommunication().eContainer();
							break;
						}					
					}
					//now locate the config tasks for these components
					if(commProvider instanceof VM){
						commProviderTask = getDepended(vmTypeTasks, commProvider.getName(), TaskType.CREATE);
						if(commProviderTask == null){
							commProviderTask = getDepended(vmTypeTasks, commProvider.getName(), TaskType.UPDATE);
						}
					}else if(commProvider instanceof InternalComponent){
						commProviderTask = getDepended(compTypeTasks, commProvider.getName(), TaskType.CREATE);
						if(commProviderTask == null){
							commProviderTask = getDepended(compTypeTasks, commProvider.getName(), TaskType.UPDATE);
						}
					}
					if(commProviderTask != null){
						//8July15 : added owner task as requested by Adapter
						commTypeTask.getJsonModel().add("providerCompTypeTask", commProviderTask.getName());
						commTypeTask.getDependencies().add(commProviderTask);
					}else{
						log.info("...did not locate the communication provider task(name = " + commProvider.getName() + ") for communication type task(" + commTypeTask.getName() + ".  Assume already deployed.");
					}
					commConsumerTask = getDepended(compTypeTasks, commConsumer.getName(), TaskType.CREATE);
					if(commConsumerTask == null){
						commConsumerTask = getDepended(compTypeTasks, commConsumer.getName(), TaskType.UPDATE);
					}
					if(commConsumerTask != null){
						//8July15 : added owner task as requested by Adapter
						commTypeTask.getJsonModel().add("consumerCompTypeTask", commConsumerTask.getName());
						commTypeTask.getDependencies().add(commConsumerTask);
						//if mandatory, the consumer depends on the provider
						if(commTypeTask.isMandatory() && commProviderTask != null){
							commConsumerTask.getDependencies().add(commProviderTask);
						}else{
							log.info("...did not locate the communication consumer task(name = " + commConsumer.getName() + ") for communication task(" + commTypeTask.getName() + ".  Assume already deployed.");
						}	
					}
				}else{//16Jan16 delete communication type depends on delete vminstances tasks
					log.debug("... about to find vm instance task for deleted communication type task(" + commTypeTask.getName() + ")....");
					for(VMInstanceTask vmit : vmInsTasks){
						if(vmit.getTaskType().equals(TaskType.DELETE)){	//delete VMInstances first
							commTypeTask.getDependencies().add(vmit);
							log.debug("Added vm instance task(" + vmit.getName() + ") to communication type task(" + commTypeTask.getName() + ")");
						}
					}
				}
				this.plan.getTasks().add(commTypeTask);	
			}//end for communication task		
		}else{
			log.info("No communication type tasks to add ....");
		}
		//communication instance
		if(!communicationInsTasks.isEmpty()){
			log.debug(communicationInsTasks.size() + " number of create/update/delete communication instance tasks to add ....");
			List<CommunicationInstance> newCommunicationIns = new ArrayList<CommunicationInstance>();
			newCommunicationIns.addAll(mc.getUpdatedComInstances());
			newCommunicationIns.addAll(mc.getAddedComInstances());
			//
			for(CommunicationInstanceTask ciTask : communicationInsTasks){
				if(!ciTask.getTaskType().equals(TaskType.DELETE)){
					//search for the parent communication type
					ConfigurationTask parent = getDepended(communicationTypeTasks, ciTask.getJsonModel().get("type").asString(), TaskType.CREATE);
					if(parent == null){
						parent = getDepended(communicationTypeTasks, ciTask.getJsonModel().get("type").asString(), TaskType.UPDATE);
					}
					if(parent != null){
						ciTask.getDependencies().add(parent);
						log.debug("...added type dependency(" + parent.getName() + ") to " + ciTask.getName());
					}else{
						log.info("... cannot find parent type task, assume already deployed....");
					}
					//communication depends on components, but not the other way around to avoid cyclic dependency
					ComponentInstance commInsProvider = null;	//initialised for each cTask
					ComponentInstance commInsConsumer = null;
					ConfigurationTask commInsProviderTask = null;
					ConfigurationTask commInsConsumerTask = null;
					//need the communication type objects 
					for(CommunicationInstance commInstance : newCommunicationIns){
						if(ciTask.getName().equals(commInstance.getName())){
							//got the original camel communication owner
							commInsProvider = (ComponentInstance) commInstance.getProvidedCommunicationInstance().eContainer();
							commInsConsumer = (ComponentInstance) commInstance.getRequiredCommunicationInstance().eContainer();
							break;
						}					
					}
					//now locate the config tasks for these components
					if(commInsProvider instanceof VMInstance){
						commInsProviderTask = getDepended(vmInsTasks, commInsProvider.getName(), TaskType.CREATE);
						if(commInsProviderTask == null){
							commInsProviderTask = getDepended(vmInsTasks, commInsProvider.getName(), TaskType.UPDATE);
						}
					}else if(commInsProvider instanceof InternalComponentInstance){
						//debug
						log.debug("... about to find task for commInsProvider(" + commInsProvider.getName() + "...");
						commInsProviderTask = getDepended(compInsTasks, commInsProvider.getName(), TaskType.CREATE);
						if(commInsProviderTask == null){
							commInsProviderTask = getDepended(compInsTasks, commInsProvider.getName(), TaskType.UPDATE);
						}
					}
					if(commInsProviderTask != null){
						//8July15 : added owner task as requested by Adapter
						ciTask.getJsonModel().add("providerCompTypeTask", commInsProviderTask.getName());
						ciTask.getDependencies().add(commInsProviderTask);
					}else{
						log.info("...did not locate the communication instance provider task(name = " + commInsProvider.getName() + ") for communication instance task(" + ciTask.getName() + ".  Assume already deployed.");
					}
					log.debug("... about to find task for commInsConsumer(" + commInsConsumer.getName() + "...");
					commInsConsumerTask = getDepended(compInsTasks, commInsConsumer.getName(), TaskType.CREATE);
					if(commInsConsumerTask == null){
						commInsConsumerTask = getDepended(compInsTasks, commInsConsumer.getName(), TaskType.UPDATE);
					}
					if(commInsConsumerTask != null){
						//8July15 : added owner task as requested by Adapter
						ciTask.getJsonModel().add("consumerCompTypeTask", commInsConsumerTask.getName());
						ciTask.getDependencies().add(commInsConsumerTask);
						boolean mandatory = false;
						if(parent == null){
							//need to find the type and get the isMandatoryValue.....
							@SuppressWarnings("unchecked")
							Collection<Communication> targetComs = (Collection<Communication>) mc.getMatchedComms().values();
							//
							for(Communication ci : targetComs){
								if(ci.getName().equals(ciTask.getJsonModel().get("type").asString())){
									mandatory = ci.getRequiredCommunication().isIsMandatory();
								}
							}
						}else{
							mandatory = ((CommunicationTypeTask) parent).isMandatory(); //get this directly from the parent type task
						}						
						//the consumer depends on the provider
						if(mandatory && commInsProviderTask != null){
							commInsConsumerTask.getDependencies().add(commInsProviderTask);
						}
					}else{
						log.info("...did not locate the communication instance consumer task(name = " + commInsConsumer.getName() + ") for communication instance task(" + ciTask.getName() + ".  Assume already deployed.");
					}
			}//end if NOT delete task 18Jan16 EW does not recognise Communication instances
				this.plan.getTasks().add(ciTask);	
			}//end for communication task		
		}else{
			log.info("No communication instance tasks to add ....");
		}	
	}
	
	

	/**
	 * Build a simple deployment {@link eu.paasage.upperware.plangenerator.model.Plan <em>Plan</em>}
	 * from a {@link eu.paasage.camel.CamelModel <em>CamelModel</em>}.
	 * <p>
	 * @throws PlanGenerationException  on all build failure
	 */
	private void buildSimpleDeploymentPlan() throws PlanGenerationException {
		//extract the deployment objects.  We will work with the first DeploymentModel until the camel semantic is clear
		List<VM> newVMTypes = this.targetDM.getVms();
		List<VMInstance> newVMInstances = this.targetDM.getVmInstances();
		List<InternalComponent> newInternalComponents = this.targetDM.getInternalComponents();
		List<InternalComponentInstance> newInternalComponentIns = this.targetDM.getInternalComponentInstances();
		List<Hosting> newHostings = this.targetDM.getHostings();
		List<HostingInstance> newHostingIns = this.targetDM.getHostingInstances();
		List<Communication> newCommunications = this.targetDM.getCommunications();
		List<CommunicationInstance> newCommunicationIns = this.targetDM.getCommunicationInstances();
		//all tasks depends on the applicationTask
		List<VMTypeTask> vmTypeTasks = new ArrayList<VMTypeTask>();
		List<VMInstanceTask> vmInsTasks = new ArrayList<VMInstanceTask>();
		List<ComponentTypeTask> compTypeTasks = new ArrayList<ComponentTypeTask>();
		List<ComponentInstanceTask> compInsTasks = new ArrayList<ComponentInstanceTask>();
		List<HostingTypeTask> hostingTypeTasks = new ArrayList<HostingTypeTask>();
		List<HostingInstanceTask> hostingInsTasks = new ArrayList<HostingInstanceTask>();
		List<CommunicationTypeTask> communicationTypeTasks = new ArrayList<CommunicationTypeTask>();
		List<CommunicationInstanceTask> communicationInsTasks = new ArrayList<CommunicationInstanceTask>();
		//1Dec2015
		List<ProvidedCommunication> pcs = new ArrayList<ProvidedCommunication>();
		//end 1Dec2015
		//
		//the root application.  
		CamelModel camelModel = (CamelModel) this.targetDM.eContainer();
		ApplicationTask sdt = getApplicationTask(camelModel.getApplications().get(0), TaskType.CREATE);
		this.plan.getTasks().add(sdt);				
		//app instance is not a camel concept
		ApplicationInstanceTask sdInsT = getAppInstanceTask(camelModel.getApplications().get(0), TaskType.CREATE);
		sdInsT.getDependencies().add(sdt);
		plan.getTasks().add(sdInsT);
		//
		log.debug("..just before create the VMType....");
		//create the VMTypeTasks
		if(newVMTypes != null && !newVMTypes.isEmpty()){
			for(VM vm : newVMTypes){
				vmTypeTasks.add(getVMTypeTask(vm, TaskType.CREATE));
			}			
		}
		log.debug("..just before create the VMI....");
		//create the VMInstanceTasks
		if(newVMInstances != null && !newVMInstances.isEmpty()){
			for(VMInstance vmi : newVMInstances){
				vmInsTasks.add(getVMInsTask(vmi,TaskType.CREATE));				
			}
		}
		//create the ComponentTypeTasks
		if(newInternalComponents != null && !newInternalComponents.isEmpty()){
			for(InternalComponent ic : newInternalComponents){
				compTypeTasks.add(getComponentTypeTask(ic, TaskType.CREATE));
				//1Dec2015 blotch, trap all the provided communications objects from InternalComponents
				pcs.addAll(ic.getProvidedCommunications());
			}
		}		
		//create the ComponentInstanceTasks
		if(newInternalComponentIns != null && !newInternalComponentIns.isEmpty()){
			for(InternalComponentInstance ici : newInternalComponentIns){
				compInsTasks.add(getComponentInsTask(ici, TaskType.CREATE));
			}
		}
		//create the HostingTypeTasks
		if(newHostings != null && !newHostings.isEmpty()){
			for(Hosting hosting : newHostings){
				hostingTypeTasks.add(getHostingTypeTask(hosting, TaskType.CREATE));
			}
		}
		//create the HostingInstanceTasks
		if(newHostingIns != null && !newHostingIns.isEmpty()){
			for(HostingInstance hi : newHostingIns){
				hostingInsTasks.add(getHostingInsTask(hi, TaskType.CREATE));
			}
		}
		//create the CommunicationTypeTasks
		if(newCommunications != null && !newCommunications.isEmpty()){
			for(Communication com : newCommunications){
				//flag if this communication is mandatory
				communicationTypeTasks.add(getCommunicationTypeTask(com, TaskType.CREATE));
				//1Dec2015 blotch to create hanging provided communication which is not explicitly declared in CAMEL
				//apparently InternalComponent can provide a communication which is not explicitly consumed by a CAMEL object but
				//by the abstract public (a concept not included in CAMEL)
				log.debug("the current communication.providedCommunication.name is :" + com.getProvidedCommunication().getName());
				//check if the providedCom matches the cached one 
				for(int i = 0; i < pcs.size(); i++){
					if(ModelComparator.equalProvidedCommunication(pcs.get(i), com.getProvidedCommunication())){
						//get rid of this, so we will end up with only pcs that not linked to any com type
						log.debug("..removing provided communication(" + pcs.get(i).getName() + " from the orphan list...." );
						pcs.remove(i);
						break;
					}
				}
			}
		}
		//1Dec2015, now the blotch.  At this stage, we have processed all the comm types 
		if(!pcs.isEmpty()){
			System.out.println("...there are " + pcs.size() + " orphan provided communication objects....");
			log.debug("...there are " + pcs.size() + " orphan provided communication objects....");
			for(int j = 0; j < pcs.size(); j++){
				//the dependency is processed further downstream
				communicationTypeTasks.add(getOrphanComTask(pcs.get(j), TaskType.CREATE));
			}
		}else{
			System.out.println("...there aren't any orphan provided communication objects....");
			log.info("...there aren't any orphan provided communication objects....");
		}
		//create the CommunicationInsTasks
		if(newCommunicationIns != null && !newCommunicationIns.isEmpty()){
			for(CommunicationInstance ci : newCommunicationIns){
				//flag if this communication is mandatory
				communicationInsTasks.add(getCommunicationInsTask(ci, TaskType.CREATE));
			}
		}
		//TODO: consider putting establish dependency in a private function common to simple deployment and deployment (update) re-configuration???
		
		//add to plan and process dependencies
		//VMs
		if(!vmTypeTasks.isEmpty()){
			log.debug(vmTypeTasks.size() + " number of VM type tasks to add to add to plan ....");
			this.plan.getTasks().addAll(vmTypeTasks);	//add to plan, ExeWare does not associate VM* with an application
		}else{
			log.info("No new VM type tasks to add ....");
		}	
		//VMInstances
		if(!vmInsTasks.isEmpty()){ 
			log.debug(vmInsTasks.size() + " number of VM instance tasks to add to add to plan ....");
			for(VMInstanceTask vmit : vmInsTasks){
				ConfigurationTask parent = getDepended(vmTypeTasks, vmit.getJsonModel().get("type").asString(), TaskType.CREATE);
				if(parent != null){
					vmit.getDependencies().add(parent);
					log.debug("...added type dependency(" + parent.getName() + ") to VM intance task : " + vmit.getName());
				}
				//27Nov2015 - add all communicationType Tasks.  ExecutionWare requires that all com types are processed before VMInstances
				setComTypeDependencies(vmit, communicationTypeTasks, TaskType.CREATE); //only create com tasks in simple plan
				//end 27Nov2015
				this.plan.getTasks().add(vmit);
			}//end for each vmInsTask				
		}else{
			log.info("No new VM instance tasks to add ....");
		}
		//comp type depends on application
		if(!compTypeTasks.isEmpty()){
			log.debug(compTypeTasks.size() + " number of comp type tasks to add to plan ....");
			//add the application dependencies
			for(ComponentTypeTask compTypeTask : compTypeTasks){
				compTypeTask.getDependencies().add(sdt);
				this.plan.getTasks().add(compTypeTask);
			}
		}else{
			log.info("No new component type tasks to add ....");
		}		
		//add comp type and app instance dependencies, other dependencies are added when processing hosting & communication
		if(!compInsTasks.isEmpty()){ 
			log.debug(compInsTasks.size() + " number of compTasks to add to plan ....");
			for(ComponentInstanceTask ctt : compInsTasks){
				ConfigurationTask parent = getDepended(compTypeTasks, ctt.getJsonModel().get("type").asString(), TaskType.CREATE);
				if(parent != null){
					ctt.getDependencies().add(parent);
					log.debug("...added type dependency(" + parent.getName() + ") to component instance task : " + ctt.getName());
				}
				ctt.getDependencies().add(sdInsT);//add the app instance task
				this.plan.getTasks().add(ctt); //no other dependencies at this stage
			}//end for component instance task
		}else{
			log.info("No new component instance tasks to add ....");
		}
		//hosting type
		if(!hostingTypeTasks.isEmpty()){
			log.debug(hostingTypeTasks.size() + " number of hosting type tasks to add to plan ....");
			//
			for(HostingTypeTask hTask : hostingTypeTasks){
				//hosting depends on components, but not the other way around to avoid cyclic dependency
				Component hostingProvider = null;	//initialised for each hTask
				Component hostingConsumer = null;
				ConfigurationTask hostingProviderTask = null;
				ConfigurationTask hostingConsumerTask = null;
				//need the hosting objects 
				for(Hosting hosting : newHostings){
					if(hTask.getName().equals(hosting.getName())){
						//got the original camel hosting
						hostingProvider = (Component) hosting.getProvidedHost().eContainer();
						hostingConsumer = (Component) hosting.getRequiredHost().eContainer();
						break;
					}					
				}
				//now locate the config tasks for these components
				if(hostingProvider instanceof VM){
					hostingProviderTask = getDepended(vmTypeTasks, hostingProvider.getName(), TaskType.CREATE);
					
				}else if(hostingProvider instanceof InternalComponent){
					hostingProviderTask = getDepended(compTypeTasks, hostingProvider.getName(), TaskType.CREATE);
				}
				if(hostingProviderTask != null){	
					//8July15 : added owner task as requested by Adapter
					hTask.getJsonModel().add("providerCompTypeTask", hostingProviderTask.getName());
					hTask.getDependencies().add(hostingProviderTask);
				}else{
					log.error("...failed to locate the hosting provider task(name = " + hostingProvider.getName() + ") for hosting task(" + hTask.getName());
					//throw new PlanGenerationException("...failed to locate the hosting provider task(name = " + hostingProvider.getName() + ") for hosting task(" + hTask.getName());
				}
				hostingConsumerTask = getDepended(compTypeTasks, hostingConsumer.getName(), TaskType.CREATE);
				if(hostingConsumerTask != null){
					//8July15 : added owner task as requested by Adapter
					hTask.getJsonModel().add("consumerCompTypeTask", hostingConsumerTask.getName());
					hTask.getDependencies().add(hostingConsumerTask);
					//the consumer depends on the host
					if(hostingProviderTask != null){
						hostingConsumerTask.getDependencies().add(hostingProviderTask);
					}
				}else{
					log.error("...failed to locate the hosting consumer task(name = " + hostingConsumer.getName() + ") for hosting task(" + hTask.getName());
					//throw new PlanGenerationException("...failed to locate the hosting consumer task(name = " + hostingConsumer.getName() + ") for hosting task(" + hTask.getName());
				}
				//continues anyway for now .....
				this.plan.getTasks().add(hTask);	
			}//end for hosting task		
		}else{
			log.info("No hosting type tasks to add ....");
		}
		//hosting instances
		if(!hostingInsTasks.isEmpty()){
			log.debug(hostingInsTasks.size() + " number of hosting instance tasks to add to plan ....");
			//
			for(HostingInstanceTask hiTask : hostingInsTasks){
				//search for the parent hosting type
				ConfigurationTask parent = getDepended(hostingTypeTasks, hiTask.getJsonModel().get("type").asString(), TaskType.CREATE);
				if(parent != null){
					hiTask.getDependencies().add(parent);
					log.debug("...added type dependency(" + parent.getName() + ") to " + hiTask.getName());
				}
				ComponentInstance hiProviderIns = null;
				ComponentInstance hiConsumerIns = null;
				ConfigurationTask hiProviderTask = null;
				ConfigurationTask hiConsumerTask = null;
				//looks for the parent type 
				for(HostingInstance hInstance : newHostingIns){
					if(hiTask.getName().equals(hInstance.getName())){
						//got the original camel hosting instance
						hiProviderIns = (ComponentInstance) hInstance.getProvidedHostInstance().eContainer();
						hiConsumerIns = (ComponentInstance) hInstance.getRequiredHostInstance().eContainer();
						break;
					}					
				}
				//now locate the consumer/provider tasks for these components
				if(hiProviderIns instanceof VMInstance){
					hiProviderTask = getDepended(vmInsTasks, hiProviderIns.getName(), TaskType.CREATE);
				}else if(hiProviderIns instanceof InternalComponent){
					hiProviderTask = getDepended(compInsTasks, hiProviderIns.getName(), TaskType.CREATE);
				}
				if(hiProviderTask != null){
					//8July15 : added owner task as requested by Adapter
					hiTask.getJsonModel().add("providerCompTypeTask", hiProviderTask.getName());
					hiTask.getDependencies().add(hiProviderTask);
				}else{
					log.error("...failed to locate the hosting provider instance task(name = " + hiProviderIns.getName() + ") for hosting task(" + hiTask.getName());
					//throw new PlanGenerationException("...failed to locate the hosting provider task(name = " + hostingProvider.getName() + ") for hosting task(" + hTask.getName());
				}
				hiConsumerTask = getDepended(compInsTasks, hiConsumerIns.getName(), TaskType.CREATE);
				if(hiConsumerTask != null){
					//8July15 : added owner task as requested by Adapter
					hiTask.getJsonModel().add("consumerCompTypeTask", hiConsumerTask.getName());
					hiTask.getDependencies().add(hiConsumerTask);
					//the consumer depends on the host
					if(hiProviderTask != null){
						hiConsumerTask.getDependencies().add(hiProviderTask);
					}
				}else{
					log.error("...failed to locate the hosting consumer instance task(name = " + hiConsumerIns.getName() + ") for hosting instance task(" + hiTask.getName());
					//throw new PlanGenerationException("...failed to locate the hosting consumer instance task(name = " + hiConsumerIns.getName() + ") for hosting task(" + hTask.getName());
				}				
				this.plan.getTasks().add(hiTask);	
			}//end for hosting instance task		
		}else{
			log.info("No hosting instance tasks to add ....");
		}
		//communication
		if(!communicationTypeTasks.isEmpty()){
			log.debug(communicationTypeTasks.size() + " number of communication type tasks to add ....");
			//			
			for(CommunicationTypeTask commTypeTask : communicationTypeTasks){				
				//communication depends on components, but not the other way around to avoid cyclic dependency
				log.debug("...inside communication type task : " + commTypeTask.getName());
				//communication depends on components, but not the other way around to avoid cyclic dependency
				//1Dec2015 blotch, need to treats the orphan differently
				if(!commTypeTask.getName().startsWith("OrphanCommunication")){
					Component commProvider = null;	//initialised for each commTypeTask
					Component commConsumer = null;
					ConfigurationTask commProviderTask = null;
					ConfigurationTask commConsumerTask = null;
					//need the communication type objects 
					for(Communication communication : newCommunications){
						if(commTypeTask.getName().equals(communication.getName())){
							//got the original camel communication type
							commProvider = (Component) communication.getProvidedCommunication().eContainer();
							commConsumer = (Component) communication.getRequiredCommunication().eContainer();
							break;
						}
					}
					//now locate the config tasks for these components
					if(commProvider instanceof VM){
						commProviderTask = getDepended(vmTypeTasks, commProvider.getName(), TaskType.CREATE);
					}else if(commProvider instanceof InternalComponent){
						commProviderTask = getDepended(compTypeTasks, commProvider.getName(), TaskType.CREATE);
					}
					if(commProviderTask != null){
						//8July15 : added owner task as requested by Adapter
						commTypeTask.getJsonModel().add("providerCompTypeTask", commProviderTask.getName());
						commTypeTask.getDependencies().add(commProviderTask);
					}else{
						log.error("...failed to locate the communication provider task(name = " + commProvider.getName() + ") for communication type task(" + commTypeTask.getName());
						//throw new PlanGenerationException("...failed to locate the communication provider task(name = " + commProvider.getName() + ") for communication type task(" + commTypeTask.getName());
					}
					commConsumerTask = getDepended(compTypeTasks, commConsumer.getName(), TaskType.CREATE);
					if(commConsumerTask != null){
						//8July15 : added owner task as requested by Adapter
						commTypeTask.getJsonModel().add("consumerCompTypeTask", commConsumerTask.getName());
						commTypeTask.getDependencies().add(commConsumerTask);
						//if mandatory, the consumer depends on the provider
						//if(commTypeTask.isMandatory() && commProviderTask != null){
							//4August2015, needs to break this up into two ifs, otherwise returning error for non isMandatory
						if(commTypeTask.isMandatory()){
							if(commProviderTask != null){
								commConsumerTask.getDependencies().add(commProviderTask);
							}else{
								log.error("...failed to locate the communication consumer task(name = " + commConsumer.getName() + ") for communication task(" + commTypeTask.getName());
							//throw new PlanGenerationException("...failed to locate the communication consumer task(name = " + commConsumer.getName() + ") for communication task(" + commTypeTask.getName());
							}
						}
					}
				}else{//find the parent component type task for the orphan
					ConfigurationTask parentCompTask = getDepended(compTypeTasks, commTypeTask.getJsonModel().get("providerCompTypeTask").asString(), TaskType.CREATE);
					if(parentCompTask != null){
						commTypeTask.getDependencies().add(parentCompTask);
					}else{
						System.out.println("Failed to find the parent comp type task for orphan(" + commTypeTask.getName() + "!!!");
						log.error("Failed to find the parent comp type task for orphan(" + commTypeTask.getName() + "!!!");
					}
				}
				this.plan.getTasks().add(commTypeTask);	
			}//end for communication task		
		}else{
			log.info("No communication type tasks to add ....");
		}
		//communication instance
		if(!communicationInsTasks.isEmpty()){
			log.debug(communicationInsTasks.size() + " number of communication instance tasks to add ....");
			//
			for(CommunicationInstanceTask ciTask : communicationInsTasks){
				//search for the parent communication type
				ConfigurationTask parent = getDepended(communicationTypeTasks, ciTask.getJsonModel().get("type").asString(), TaskType.CREATE);
				if(parent != null){
					ciTask.getDependencies().add(parent);
					log.debug("...added type dependency(" + parent.getName() + ") to " + ciTask.getName());
				}
				//communication depends on components, but not the other way around to avoid cyclic dependency
				ComponentInstance commInsProvider = null;	//initialised for each cTask
				ComponentInstance commInsConsumer = null;
				ConfigurationTask commInsProviderTask = null;
				ConfigurationTask commInsConsumerTask = null;
				//need the communication type objects 
				for(CommunicationInstance commInstance : newCommunicationIns){
					if(ciTask.getName().equals(commInstance.getName())){
						//got the original camel communication type
						commInsProvider = (ComponentInstance) commInstance.getProvidedCommunicationInstance().eContainer();
						commInsConsumer = (ComponentInstance) commInstance.getRequiredCommunicationInstance().eContainer();
						break;
					}					
				}
				//now locate the config tasks for these components
				if(commInsProvider instanceof VMInstance){
					commInsProviderTask = getDepended(vmInsTasks, commInsProvider.getName(), TaskType.CREATE);
				}else if(commInsProvider instanceof InternalComponentInstance){
					//debug
					log.debug("... about to find task for commInsProvider(" + commInsProvider.getName() + "...");
					commInsProviderTask = getDepended(compInsTasks, commInsProvider.getName(), TaskType.CREATE);
				}
				if(commInsProviderTask != null){
					//8July15 : added owner task as requested by Adapter
					ciTask.getJsonModel().add("providerCompTypeTask", commInsProviderTask.getName());
					ciTask.getDependencies().add(commInsProviderTask);
				}else{
					log.error("...failed to locate the communication instance provider task(name = " + commInsProvider.getName() + ") for communication instance task(" + ciTask.getName());
					//throw new planGenerationException("...failed to locate the communication instance provider task(name = " + commInsProvider.getName() + ") for communication instance task(" + cTask.getName());
				}
				log.debug("... about to find task for commInsConsumer(" + commInsConsumer.getName() + "...");
				commInsConsumerTask = getDepended(compInsTasks, commInsConsumer.getName(), TaskType.CREATE);
				if(commInsConsumerTask != null){
					//8July15 : added owner task as requested by Adapter
					ciTask.getJsonModel().add("consumerCompTypeTask", commInsConsumerTask.getName());
					ciTask.getDependencies().add(commInsConsumerTask);
					//the consumer depends on the provider
					if(((CommunicationTypeTask) parent).isMandatory() && commInsProviderTask != null){
						commInsConsumerTask.getDependencies().add(commInsProviderTask);
					}
				}else{
					log.error("...failed to locate the communication instance consumer task(name = " + commInsConsumer.getName() + ") for communication instance task(" + ciTask.getName());
				}
				this.plan.getTasks().add(ciTask);	
			}//end for communication task		
		}else{
			log.info("No communication instance tasks to add ....");
		}
	}	
	/**
	 * Create a {@link eu.paasage.upperware.plangenerator.model.task.ApplicationTask <em>ApplicationTask</em>}, set the
	 * the {@link eu.paasage.upperware.plangenerator.type.TaskType <em>TaskType</em>} and populate 
	 * the json model with relevant information.
	 * <p> 
	 * @param app	the source {@link eu.paasage.camel.Application <em>Application</em>} object
	 * @param type	type	the {@link eu.paasage.upperware.plangenerator.type.TaskType <em>TaskType</em>} to set
	 * @return	the created {@link eu.paasage.upperware.plangenerator.model.task.ApplicationTask <em>ApplicationTask</em>}
	 */
	private ApplicationTask getApplicationTask(Application app, TaskType type){
		 ApplicationTask ap = new ApplicationTask(app.getName(), type);
		 //get the nfo
		 ap.setJsonModel(ModelToJsonConverter.convertApp(app));
		 return ap;
		 
	}
	/**
	 * Create a {@link eu.paasage.upperware.plangenerator.model.task.ApplicationInstanceTask <em>ApplicationInstanceTask</em>}, set the
	 * the {@link eu.paasage.upperware.plangenerator.type.TaskType <em>TaskType</em>} and populate 
	 * the json model with relevant information.
	 * <p> 
	 * @param app	the source {@link eu.paasage.camel.Application <em>Application</em>} object
	 * @param type	the {@link eu.paasage.upperware.plangenerator.type.TaskType <em>TaskType</em>} to set
	 * @return	the created {@link eu.paasage.upperware.plangenerator.model.task.ApplicationInstanceTask <em>ApplicationInstanceTask</em>}
	 */
	private ApplicationInstanceTask getAppInstanceTask(Application app, TaskType type){
		 ApplicationInstanceTask ap = new ApplicationInstanceTask(app.getName() + "Instance", type);
		 //get the info
		 ap.setJsonModel(ModelToJsonConverter.convertAppInstance(app));
		 return ap;
		 
	}
	/**
	 * Create a {@link eu.paasage.upperware.plangenerator.model.task.VMTypeTask <em>VMTypeTask</em>}, set the
	 * the {@link eu.paasage.upperware.plangenerator.type.TaskType <em>TaskType</em>} and populate 
	 * the json model with relevant information.
	 * <p> 
	 * @param vm	the source {@link eu.paasage.camel.deployment.VM <em>VM</em>}
	 * @param type	the {@link eu.paasage.upperware.plangenerator.type.TaskType <em>TaskType</em>} to set
	 * @return	the created {@link eu.paasage.upperware.plangenerator.model.task.VMTypeTask <em>VMTypeTask</em>}
	 */
	private VMTypeTask getVMTypeTask(VM vm, TaskType type){
		//
		VMTypeTask vmt = new VMTypeTask(vm.getName(), type);
		//get the info
		if(type.equals(TaskType.DELETE)){
			JsonObject nameObj = new JsonObject();
			nameObj.add("name", vm.getName());
			vmt.setJsonModel(nameObj);	
		}else{//create and update
			vmt.setJsonModel(ModelToJsonConverter.convertVM(vm));	
		}
		//
		return vmt;
	}
	
	
	/**
	 * Create a {@link eu.paasage.upperware.plangenerator.model.task.VMInstanceTask <em>VMInstanceTask</em>}, set the
	 * the {@link eu.paasage.upperware.plangenerator.type.TaskType <em>TaskType</em>} and populate 
	 * the json model with relevant information.
	 * <p> 
	 * @param vmi	the source {@link eu.paasage.camel.deployment.VMInstance <em>VMInstance</em>} 	
	 * @param type	the {@link eu.paasage.upperware.plangenerator.type.TaskType <em>TaskType</em>} to set
	 * @return	the created {@link eu.paasage.upperware.plangenerator.model.task.VMInstanceTask <em>VMInstanceTask</em>}
	 */
	private VMInstanceTask getVMInsTask(VMInstance vmi, TaskType type){
		//
		VMInstanceTask vmit = new VMInstanceTask(vmi.getName(), type);
		//get the info
		if(type.equals(TaskType.DELETE)){
			JsonObject nameObj = new JsonObject();
			nameObj.add("name", vmi.getName());
			vmit.setJsonModel(nameObj);	
		}else{//create and update
			vmit.setJsonModel(ModelToJsonConverter.convertVMInstance(vmi));	
		}
		//
		return vmit;
	}
	/**
	 * Create a {@link eu.paasage.upperware.plangenerator.model.task.ComponentTypeTask <em>ComponentTypeTask</em>}, set the
	 * the {@link eu.paasage.upperware.plangenerator.type.TaskType <em>TaskType</em>} and populate 
	 * the json model with relevant information.
	 * <p> 
	 * @param ic	the source {@link eu.paasage.camel.deployment.InternalComponent <em>InternalComponent</em>} to convert
	 * @param type	the {@link eu.paasage.upperware.plangenerator.type.TaskType <em>TaskType</em>} to set
	 * @return	the created {@link eu.paasage.upperware.plangenerator.model.task.ComponentTypeTask <em>ComponentTypeTask</em>}
	 */
	private ComponentTypeTask getComponentTypeTask(InternalComponent ic, TaskType type){
		ComponentTypeTask ct = new ComponentTypeTask(ic.getName(), type);
		//get the info
		if(type.equals(TaskType.DELETE)){
			JsonObject nameObj = new JsonObject();
			nameObj.add("name", ic.getName());
			ct.setJsonModel(nameObj);	
		}else{//create and update
			ct.setJsonModel(ModelToJsonConverter.convertInternalComponent(ic));
		}		
		//
		return ct;
	}
	/**
	 * Create a {@link eu.paasage.upperware.plangenerator.model.task.ComponentInstanceTask <em>ComponentInstanceTask</em>}, set the
	 * the {@link eu.paasage.upperware.plangenerator.type.TaskType <em>TaskType</em>} and populate 
	 * the json model with relevant information.
	 * <p> 
	 * @param ici	the source {@link eu.paasage.camel.deployment.InternalComponentInstance <em>InternalComponentInstance</em>} to convert
	 * @param type	the {@link eu.paasage.upperware.plangenerator.type.TaskType <em>TaskType</em>} to set
	 * @return the created {@link eu.paasage.upperware.plangenerator.model.task.ComponentInstanceTask <em>ComponentInstanceTask</em>}
	 */
	private ComponentInstanceTask getComponentInsTask(InternalComponentInstance ici, TaskType type){
		ComponentInstanceTask cit = new ComponentInstanceTask(ici.getName(), type);
		//get the info
		if(type.equals(TaskType.DELETE)){
			JsonObject nameObj = new JsonObject();
			nameObj.add("name", ici.getName());
			cit.setJsonModel(nameObj);	
		}else{//create and update
			cit.setJsonModel(ModelToJsonConverter.convertInternalComponentInstance(ici));
		}
			//
		return cit;
	}
	/**
	 * Create a {@link eu.paasage.upperware.plangenerator.model.task.HostingTypeTask <em>HostingTypeTask</em>}, set the
	 * the {@link eu.paasage.upperware.plangenerator.type.TaskType <em>TaskType</em>} and populate 
	 * the json model with relevant information.
	 * <p> 
	 * @param hosting	the source {@link eu.paasage.camel.deployment.Hosting <em>Hosting</em>} object to convert
	 * @param type	the {@link eu.paasage.upperware.plangenerator.type.TaskType <em>TaskType</em>} to set
	 * @return	the created {@link eu.paasage.upperware.plangenerator.model.task.HostingTypeTask <em>HostingTypeTask</em>}
	 */
	private HostingTypeTask getHostingTypeTask(Hosting hosting, TaskType type){
		HostingTypeTask ht = new HostingTypeTask(hosting.getName(), type);
		//get the info
		if(type.equals(TaskType.DELETE)){
			JsonObject nameObj = new JsonObject();
			nameObj.add("name", hosting.getName());
			ht.setJsonModel(nameObj);	
		}else{//create and update
			ht.setJsonModel(ModelToJsonConverter.convertHosting(hosting));
		}		
		//
		return ht;
	}
	/**
	 * Create a {@link eu.paasage.upperware.plangenerator.model.task.HostingInstanceTask <em>HostingInstanceTask</em>}, set the
	 * the {@link eu.paasage.upperware.plangenerator.type.TaskType <em>TaskType</em>} and populate 
	 * the json model with relevant information.
	 * <p> 
	 * @param hi	the source {@link eu.paasage.camel.deployment.HostingInstance <em>HostingInstance</em>} object to convert
	 * @param type	the {@link eu.paasage.upperware.plangenerator.type.TaskType <em>TaskType</em>} to set
	 * @return the created {@link eu.paasage.upperware.plangenerator.model.task.HostingInstanceTask <em>HostingInstanceTask</em>}
	 */
	private HostingInstanceTask getHostingInsTask(HostingInstance hi, TaskType type){
		HostingInstanceTask hit = new HostingInstanceTask(hi.getName(), type);
		//get the info
		if(type.equals(TaskType.DELETE)){
			JsonObject nameObj = new JsonObject();
			nameObj.add("name", hi.getName());
			hit.setJsonModel(nameObj);	
		}else{//create and update
			hit.setJsonModel(ModelToJsonConverter.convertHostingInstance(hi));
		}
		return hit;
	}
	/**
	 * Create a {@link eu.paasage.upperware.plangenerator.model.task.CommunicationTypeTask <em>CommunicationTypeTask</em>}, set the
	 * the {@link eu.paasage.upperware.plangenerator.type.TaskType <em>TaskType</em>} and populate 
	 * the json model with relevant information.
	 * <p>
	 * @param com	the source {@link eu.paasage.camel.deployment.Communication <em>Communication</em>}
	 * @param type	the {@link eu.paasage.upperware.plangenerator.type.TaskType <em>TaskType</em>} to set
	 * @return	the created {@link eu.paasage.upperware.plangenerator.model.task.CommunicationTypeTask <em>CommunicationTypeTask</em>}
	 */
	private CommunicationTypeTask getCommunicationTypeTask(Communication com, TaskType type){
		CommunicationTypeTask ct = new CommunicationTypeTask(com.getName(), type);
		//get the info
		if(type.equals(TaskType.DELETE)){
			JsonObject nameObj = new JsonObject();
			nameObj.add("name", com.getName());
			ct.setJsonModel(nameObj);	
		}else{//create and update
			ct.setJsonModel(ModelToJsonConverter.convertCommunication(com));
			if(ct.getJsonModel().get("isMandatory").asBoolean() == true){
				ct.setMandatory(true);
			}
		}
		return ct;
	}
	/**
	 * Create a {@link eu.paasage.upperware.plangenerator.model.task.CommunicationTypeTask <em>CommunicationTypeTask</em>}
	 * object to hold an orphan {@link eu.paasage.camel.deployment.ProvidedCommunication <em>ProvidedCommunication</em>}
	 * object.  The orphan is not mapped to any concrete {@link eu.paasage.camel.deployment.Communication <em>Communication</em>}
	 * object.
	 * <p>
	 * @param target	the target {@link eu.paasage.camel.deployment.ProvidedCommunication <em>ProvidedCommunication</em>} object
	 * @param type		the {@link eu.paasage.upperware.plangenerator.type.TaskType <em>TaskType</em>} to set	
	 * @return			the created {@link eu.paasage.upperware.plangenerator.model.task.CommunicationTypeTask <em>CommunicationTypeTask</em>}
	 */
	private CommunicationTypeTask getOrphanComTask(ProvidedCommunication target, TaskType type){
		//1Dec2015 blotch
		//11Jan16 need to be able to identify obj by name to support the reconfig scenario
		String name = "OrphanCommunication_" + target.getName();
		log.debug(" new orphan communication task name is :  " + name);
		CommunicationTypeTask oct = new CommunicationTypeTask(name, type);
		JsonObject nameObj = new JsonObject();
		if(type.equals(TaskType.DELETE)){
			nameObj.add("name", name);
		}else{//create and update
			nameObj = ModelToJsonConverter.convertOrphanCommunication(target);
			nameObj.add("name", name);
		}
		oct.setJsonModel(nameObj);
		return oct;		
	}
	
	/**
	 * Create a {@link eu.paasage.upperware.plangenerator.model.task.CommunicationInstanceTask <em>CommunicationInstanceTask</em>}, set the
	 * the {@link eu.paasage.upperware.plangenerator.type.TaskType <em>TaskType</em>} and populate 
	 * the json model with relevant information.
	 * <p> 
	 * @param ci	the source {@link eu.paasage.camel.deployment.CommunicationInstance <em>CommunicationInstance</em>}
	 * @param type	the {@link eu.paasage.upperware.plangenerator.type.TaskType <em>TaskType</em>} to set
	 * @return 	the created {@link eu.paasage.upperware.plangenerator.model.task.CommunicationInstanceTask <em>CommunicationInstanceTask</em>}
	 */
	private CommunicationInstanceTask getCommunicationInsTask(CommunicationInstance ci, TaskType type){
		CommunicationInstanceTask ct = new CommunicationInstanceTask(ci.getName(), type);
		//get the info
		if(type.equals(TaskType.DELETE)){
			JsonObject nameObj = new JsonObject();
			nameObj.add("name", ci.getName());
			ct.setJsonModel(nameObj);	
		}else{//create and update
			ct.setJsonModel(ModelToJsonConverter.convertCommunicationInstance(ci));
		}
		//
		return ct;
	}
	/**
	 * Find a {@link eu.paasage.upperware.plangenerator.model.task.ConfigurationTask <em>ConfigurationTask</em>}
	 * by name.
	 * <p>
	 * @param candidates	The {@link java.util.List <em>List</em>} of {@link eu.paasage.upperware.plangenerator.model.task.ConfigurationTask <em>ConfigurationTask</em>}
	 * 						to search
	 * @param target		The target name to match
	 * @param type			The {@link eu.paasage.upperware.plangenerator.type.TaskType <em>TaskType</em>} to find
	 * @return				The matching {@link eu.paasage.upperware.plangenerator.model.task.ConfigurationTask <em>ConfigurationTask</em>} object
	 */
	private ConfigurationTask getDepended(List<? extends ConfigurationTask> candidates, String target, TaskType type){
		//
		if(candidates != null && !candidates.isEmpty()){
			for(ConfigurationTask task : candidates){
				//debug
				log.debug(" comparing task(" + task.getName() + ") against target(" + target + ")");
				if(task.getTaskType().equals(type) && task.getName().equals(target)){
					return task;
				}
			}
		}
		return null;
	}
	/**
	 * Add the provided {@link java.util.List <em>List</em>} of {@link eu.paasage.upperware.plangenerator.model.task.ConfigurationTask <em>ConfigurationTask</em>}
	 * as dependencies to the target {@link eu.paasage.upperware.plangenerator.model.task.ConfigurationTask <em>ConfigurationTask</em>}.
	 * <p>
	 * @param targetTask	a generic {@link eu.paasage.upperware.plangenerator.model.task.ConfigurationTask <em>ConfigurationTask</em>}.
	 * @param tasks  the {@link java.util.List <em>List</em>} of generic {@link eu.paasage.upperware.plangenerator.model.task.ConfigurationTask <em>ConfigurationTask</em>}.
	 * @param type	the target {@link eu.paasage.upperware.plangenerator.type.TaskType <em>TaskType</em>}
	 * @return	the target {@link eu.paasage.upperware.plangenerator.model.task.ConfigurationTask <em>ConfigurationTask</em>}.
	 */
	private ConfigurationTask setComTypeDependencies(ConfigurationTask targetTask, List<? extends ConfigurationTask> tasks, TaskType type){
		//
		if(!tasks.isEmpty()){
			for(ConfigurationTask ctt : tasks){
				//11Jan16 added check for task type 
				if(ctt.getTaskType().equals(type)){					
					targetTask.getDependencies().add(ctt);
					log.debug("...added task dependency(" + ctt.getName() + ") to the target " + type + " task : " + targetTask.getName());
				}//11Jan16
			}
		}else{
			log.debug("...No task dependencies to add to target task(" + targetTask.getName() + ")");
		}		
		return targetTask;
	}
	/**
	 * Extract new {@link java.util.List <em>List</em>} of {@link eu.paasage.upperware.plangenerator.model.task.ConfigurationTask <em>ConfigurationTask</em>}
	 * from a generic {@link java.util.List <em>List</em>} of {@link eu.paasage.upperware.plangenerator.model.task.ConfigurationTask <em>ConfigurationTask</em>}.
	 * <p>
	 * @param allTask	the {@link java.util.List <em>List</em>} of {@link eu.paasage.upperware.plangenerator.model.task.ConfigurationTask <em>ConfigurationTask</em>} to process.
	 * @return	a {@link java.util.List <em>List</em>} of selected {@link eu.paasage.upperware.plangenerator.model.task.ConfigurationTask <em>ConfigurationTask</em>}.
	 */
	private List<? extends ConfigurationTask> getNewTask(List<? extends ConfigurationTask> allTask){
		List<ConfigurationTask> newTasks = new ArrayList<ConfigurationTask>();
		if(!allTask.isEmpty()){
			for(ConfigurationTask ctt : allTask){
				if(ctt.getTaskType().equals(TaskType.CREATE)){
					newTasks.add(ctt);
					log.debug("... added " + ctt.getName() + "  to new task list....");
				}
			}
		}
		return newTasks;
	}
}