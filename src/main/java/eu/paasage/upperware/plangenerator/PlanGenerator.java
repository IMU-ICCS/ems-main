/*
 * Copyright (c) 2014-5 UK Science and Technology Facilities Council
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.plangenerator;

import java.util.ArrayList;
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
import eu.paasage.upperware.plangenerator.exception.ModelUtilException;
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
 * The entry point to the plan generator.  This component is used by the Adapter 
 * to compute configuration plan of a cloud application.
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
		if(simpleInitialDeployment){ //flag set by Adapter			
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
	 * @throws ModelUtilException 
	 * @throws	{@link eu.paasage.upperware.plangenerator.exception.PlanGenerationException <em>PlanGenerationException</em>} or
	 * 			{@link eu.paasage.upperware.plangenerator.exception.ModelComparatorException <em>ModelComparatorException</em>}  on error
	 */
	private void buildReconfigPlan() throws PlanGenerationException, ModelComparatorException {
		/******************************************************************************************
		 * refactored to use DeploymentModelComparator as object names cannot be used as identifier
		 ******************************************************************************************/
		log.debug("....building a reconfiguration plan.....");
		//go ahead and compare target model against current model		
		DeploymentModelComparator dmc = new DeploymentModelComparator(this.currentDM, this.targetDM);
		dmc.compareModels();
		//
		List<VMInstanceTask> vmInsTasks = new ArrayList<VMInstanceTask>();
		List<ComponentInstanceTask> compInsTasks = new ArrayList<ComponentInstanceTask>();
		List<HostingInstanceTask> hostingInsTasks = new ArrayList<HostingInstanceTask>();
		//List<CommunicationInstanceTask> communicationInsTasks = new ArrayList<CommunicationInstanceTask>();
		ApplicationTask appTask = null;
		ApplicationInstanceTask appInsTask = null;
		//
		//the root application obj only have one attribute - name that matters. 
		CamelModel currentCamel = (CamelModel) currentDM.eContainer();
		CamelModel targetCamel = (CamelModel) targetDM.eContainer();
		//
		//4May2016 ExecutionWare does not do update, but Adapter keep a cache of the current names
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
		
		/*********** REMOVAL TASKS : the objects already exists in EW. The names should be the current names****************************/
		//removing all objects means un-deploying the app but cannot assume deleting the app
		//process in this order hosting instance, internalComponent instance, VM instance. 
		//If EW does not enforce dependency, it is possible to do all these in parallel
		//
		//hosting instances	
		if(!dmc.getRemovedHostingInstances().isEmpty()){
			log.debug(dmc.getRemovedHostingInstances().size() + " number of hosting instances to remove....");
			for(HostingInstance hi : dmc.getRemovedHostingInstances()){
				hostingInsTasks.add(getHostingInsTask(hi, TaskType.DELETE));
			}
		}else{
			log.info("No hosting instances to remove ....");
		}
		//InternalComponentInstances - hosting instances depends on these
		if(!dmc.getRemovedInternalComponentInstances().isEmpty()){
			log.debug(dmc.getRemovedInternalComponentInstances().size() + " number of internal component instances to remove....");
			for(InternalComponentInstance ici : dmc.getRemovedInternalComponentInstances()){
				compInsTasks.add(getComponentInsTask(ici, TaskType.DELETE));
			}
		}else{
			log.info("No internal component instances to remove ....");
		}
		//vm instances - the consumer (internal component instance) and hosting instances depend on these
		if(!dmc.getRemovedVMInstances().isEmpty()){
			log.debug(dmc.getRemovedVMInstances().size() + " number of VM instances to remove....");
			for(VMInstance vmi : dmc.getRemovedVMInstances()){
				vmInsTasks.add(getVMInsTask(vmi, TaskType.DELETE));
			}
		}else{
			log.info("No VM instances to remove ....");
		}
		
		/***************************************** CREATE TASKS - the objects do not exist in EW ************************************/		
		//Instances
		//VM Instance		
		if(!dmc.getAddedVMInstances().isEmpty()){
			log.debug(dmc.getAddedVMInstances().size() + " number of VM instances to add ....");
			for(VMInstance avm : dmc.getAddedVMInstances()){	
				vmInsTasks.add(getVMInsTask(avm, TaskType.CREATE));	
				//task dependencies added further downstream
			}		
		}else{
			log.info("No VM instances to add ....");
		}
		//Internal Component Instance		
		if(!dmc.getAddedInternalComponentInstances().isEmpty()){
			log.debug(dmc.getAddedInternalComponentInstances().size() + " number of internal component instances to add ....");
			//
			for(InternalComponentInstance aic : dmc.getAddedInternalComponentInstances()){
				ComponentInstanceTask cit = getComponentInsTask(aic, TaskType.CREATE);
				//check if any app instance task to add, other dependencies added further down
				if(appInsTask != null){
					cit.getDependencies().add(appInsTask);
				}
				compInsTasks.add(cit);
			}
		}else{
			log.info("No internal component instances to add ....");
		}
		//hosting instances		
		if(!dmc.getAddedHostingInstances().isEmpty()){
			log.debug(dmc.getAddedHostingInstances().size() + " number of hosting instances to add ....");
			//
			for(HostingInstance ahi : dmc.getAddedHostingInstances()){
				hostingInsTasks.add(getHostingInsTask(ahi, TaskType.CREATE));
			}	
		}else{
			log.info("No hosting instances to add ....");
		}
		
		/******************** UPDATE TASKS - the objects (id) already exist in Adapter  *********************************************/ 
		//update tasks can be done simultaneously	
		//hosting instances	
		if(!dmc.getMatchedHostingInstances().isEmpty()){
			log.debug(dmc.getMatchedHostingInstances().size() + " number of hosting instances to update....");
			for(HostingInstance hi : dmc.getMatchedHostingInstances().keySet()){
				HostingInstanceTask hit = getHostingInsTask(hi, TaskType.UPDATE);
				//needs to get old name from current object
				String old_name = dmc.getMatchedHostingInstances().get(hi).getName();
				hit.getJsonModel().add("old_name", old_name);	
				hostingInsTasks.add(hit);
			}
		}else{
			log.info("No hosting instances to update ....");
		}
		//internalComponent instances 
		if(!dmc.getMatchedInternalComponentIns().isEmpty()){
			log.debug(dmc.getMatchedInternalComponentIns().size() + " number of internal component instances to update....");
			for(InternalComponentInstance ici : dmc.getMatchedInternalComponentIns().keySet()){
				ComponentInstanceTask cit = getComponentInsTask(ici, TaskType.UPDATE);
				//needs to get old name from current object
				String old_name = dmc.getMatchedInternalComponentIns().get(ici).getName();
				cit.getJsonModel().add("old_name", old_name);	
				//				
				if(appInsTask != null){
					cit.getDependencies().add(appInsTask);
				}
				compInsTasks.add(cit);
			}
		}else{
			log.info("No internal component instances to updated ....");
		}
		//vm instances : the consumer (internal component instance) task depends on these hosting instance providers
		if(!dmc.getMatchedVMInstances().isEmpty()){
		//if(!dmc.getUpdatedVMInstances().isEmpty()){
			log.debug(dmc.getMatchedVMInstances().size() + " number of VM instances to update....");
			//for(VMInstance vmi : dmc.getUpdatedVMInstances()){
			for(VMInstance vmi : dmc.getMatchedVMInstances().keySet()){
				VMInstanceTask vmit = getVMInsTask(vmi, TaskType.UPDATE);
				//needs to get old name from current object
				String old_name = dmc.getMatchedVMInstances().get(vmi).getName();
				vmit.getJsonModel().add("old_name", old_name);
				//				
				vmInsTasks.add(vmit);
			}
		}else{
			log.info("No VM instances to update ....");
		}	
		//Process dependencies....
		//Better to do the dependencies after all the tasks have been created as the relationship can be complex
		//basic assumption : 
		//1. delete tasks can be process at any time (assuming EW does not enforce dependency)
		//2. update application/instance tasks can also be done at any time 
		//3. update and create tasks can be dependent on each other.  HostingInstance depends on the consumer/dependent
		//4. (18Jan16) delete tasks, the type depends on the children instances (if the both are being deleted) 
		//5. (3May16) EW does not support update.  The name attributes are required by Adapter and needs to be synchronised 
		//    with the S2D deployment model.
		
		//VM Instances
		if(!vmInsTasks.isEmpty()){ 
			log.debug(vmInsTasks.size() + " number of VM instance tasks to added to plan ....");
				//the types do not change
				this.plan.getTasks().addAll(vmInsTasks);	
		}else{
			log.info("No VM instance tasks to add dependencies ....");
		}
		
		//internal component instances
		//add comp type dependencies, other dependencies are added when processing hosting & communication
		if(!compInsTasks.isEmpty()){ 
			log.debug(compInsTasks.size() + " number of update/create/delete compInsTasks to add to plan ....");			
				this.plan.getTasks().addAll(compInsTasks); //no other dependencies at this stage
		}else{
			log.info("No component instance tasks to add ....");
		}
		
		//hosting instances
		if(!hostingInsTasks.isEmpty()){
			log.debug(hostingInsTasks.size() + " number of create/update/delete hosting instance tasks to add to plan ....");
			List<HostingInstance> newHostingIns = new ArrayList<HostingInstance>();
			newHostingIns.addAll(dmc.getMatchedHostingInstances().keySet());
			newHostingIns.addAll(dmc.getAddedHostingInstances());
			//consumer depends on provider
			for(HostingInstanceTask hiTask : hostingInsTasks){
				if(!hiTask.getTaskType().equals(TaskType.DELETE)){
					//
					ComponentInstance hiProviderIns = null;
					ComponentInstance hiConsumerIns = null;
					ConfigurationTask hiProviderTask = null;
					ConfigurationTask hiConsumerTask = null;
					//looks for the provider/consumer instance
					for(HostingInstance hInstance : newHostingIns){
						if(hiTask.getName().equals(hInstance.getName())){
							//
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
					}else if(hiProviderIns instanceof InternalComponentInstance){
						hiProviderTask = getDepended(compInsTasks, hiProviderIns.getName(), TaskType.CREATE);
						if(hiProviderTask == null){
							hiProviderTask = getDepended(compInsTasks, hiProviderIns.getName(), TaskType.UPDATE);
						}
					}					
					if(hiProviderTask != null){
						//8July15 : added owner task as requested by Adapter
						hiTask.getJsonModel().add("providerCompTypeTask", hiProviderTask.getName());
						hiTask.getDependencies().add(hiProviderTask);
					}else{//if none found, assume already deployed
						log.info("...did not locate the hosting provider instance task(name = " + 
							hiProviderIns.getName() + ") for hosting task(" + hiTask.getName() + 
							".  Assume already deployed.");
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
						log.info("...did not locate the hosting consumer instance task(name = " + 
							hiConsumerIns.getName() + ") for hosting instance task(" + hiTask.getName() + 
							".  Assume already deployed.");					
							//throw new PlanGenerationException("...failed to locate the hosting consumer instance task(name = " + hiConsumerIns.getName() + ") for hosting task(" + hTask.getName());
					}
				}else{//delete task  19Jan2016 delete vm type depends on delete component type
					for(HostingInstance deletedHI : dmc.getRemovedHostingInstances()){
						log.debug("..processing removed hosting instance(" + deletedHI.getName() + "....");
						if(hiTask.getName().equals(deletedHI.getName())){
						//provider is a VMInstance or a ComponentInstance, consumer is a ComponentInstance
							ConfigurationTask hostingInsProviderTask = null;
							ConfigurationTask hostingInsConsumerTask = null;							
							//find consumer
							hostingInsConsumerTask = getDepended(compInsTasks, ((ComponentInstance) deletedHI.getRequiredHostInstance().eContainer()).getName(), TaskType.DELETE);
							if(hostingInsConsumerTask != null){
								hostingInsConsumerTask.getDependencies().add(hiTask); //delete binding before consumer component instance
							}
							//first tried VM 
							hostingInsProviderTask = getDepended(vmInsTasks, ((ComponentInstance) deletedHI.getProvidedHostInstance().eContainer()).getName(),TaskType.DELETE);
							if(hostingInsProviderTask == null){
								//try component instances
								hostingInsProviderTask = getDepended(compInsTasks, ((ComponentInstance) deletedHI.getProvidedHostInstance().eContainer()).getName(),TaskType.DELETE);
							}
							if(hostingInsProviderTask == null){					
									log.info("...failed to find deleted hosting instance provider for hosting instance(" + deletedHI.getName() + ").....");  //it is legitimate					
							}else{//provider also being deleted, need to first delete the consumer, then the provider
								//delete hosting instance before component instance
								hostingInsProviderTask.getDependencies().add(hiTask); //delete binding first
								if(hostingInsConsumerTask != null){ //consumer also being deleted, so add dependency
									hostingInsProviderTask.getDependencies().add(hostingInsConsumerTask);
								}
							}
						}						
					}
				}//end 19Jan2016
				this.plan.getTasks().add(hiTask);	
			}//end for hosting instance task //18Jan16 EW does not recognise Hosting instances		
		}else{
			log.info("No hosting instance tasks to add ....");
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
					if(DeploymentModelComparator.equalProvidedCommunication(pcs.get(i), com.getProvidedCommunication())){
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
			//JsonObject nameObj = new JsonObject();
			//nameObj.add("name", vmi.getName());
			//19Jan2016
			vmit.setJsonModel(ModelToJsonConverter.convertDeletedObj((ComponentInstance) vmi));	
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
			//19Jan2016
			cit.setJsonModel(ModelToJsonConverter.convertDeletedObj((ComponentInstance) ici));	
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
		ct.setMandatory(com.getRequiredCommunication().isIsMandatory());
		//get the info
		if(type.equals(TaskType.DELETE)){
			//19Jan2016 create a minimal json object			
			ct.setJsonModel(ModelToJsonConverter.convertDeleteCommunication(com));	
		}else{//create and update
			ct.setJsonModel(ModelToJsonConverter.convertCommunication(com));			
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
			nameObj.add("objType","communicationInstance");
			nameObj.add("type", ci.getType().getName()); //to help locate mandatory com
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
	}*/
}