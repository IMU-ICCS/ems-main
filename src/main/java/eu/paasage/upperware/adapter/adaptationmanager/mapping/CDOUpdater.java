/*
 * Copyright (c) 2016 INRIA, INSA Rennes
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.adaptationmanager.mapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Interface;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.Communication;
import eu.paasage.camel.deployment.CommunicationInstance;
import eu.paasage.camel.deployment.CommunicationPort;
import eu.paasage.camel.deployment.DeploymentFactory;
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
import eu.paasage.camel.deployment.RequiredHostInstance;
import eu.paasage.camel.deployment.VM;
import eu.paasage.camel.deployment.VMInstance;
import eu.paasage.camel.provider.Attribute;
import eu.paasage.camel.provider.Feature;
import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.upperware.adapter.adaptationmanager.REST.ExecInterfacer;
import eu.paasage.upperware.adapter.adaptationmanager.actions.InternalComponentInstanceAction;
import eu.paasage.upperware.adapter.adaptationmanager.core.Coordinator;
import eu.paasage.upperware.adapter.adaptationmanager.input.ReasonerInterfacer;

public class CDOUpdater {
	
	ReasonerInterfacer reasonerInterfacer;
	String rescname = null;
	DeploymentModel srcDepModel;
	
	DataHolder dataHolder = null;
	
	DeploymentModel targetDepModel = null;
	
	private LinkedList<ExecwareInstance> instances = null;
	private int numInstances;
	
	private final static Logger LOGGER = Logger.getLogger(CDOUpdater.class
			.getName());
	
	private CDOUpdater(String resourceName, DeploymentModel runningModel) {
		// TODO Auto-generated constructor stub
		this.rescname = resourceName;
		System.out.println("The resource name is " + this.rescname);
		this.srcDepModel = runningModel;
		this.dataHolder = new DataHolder();
		this.targetDepModel = null;
	}
	
	
	public CDOUpdater(ReasonerInterfacer interfacer, int dmIndex) {
		// TODO Auto-generated constructor stub
		this.reasonerInterfacer = interfacer;
		reasonerInterfacer.openTransaction();
		this.srcDepModel = reasonerInterfacer.getLiveDeploymentModel(dmIndex);
		this.rescname = reasonerInterfacer.getresourceName();
		System.out.println("The resource name is " + this.rescname);
		this.dataHolder = new DataHolder();
	}
	
	private void commitAndCloseTransaction(){
		reasonerInterfacer.commitAndCloseTransaction();
	}
	
	public void printVMInstances(){
		EList<VMInstance> vmiList = srcDepModel.getVmInstances();
		for (Iterator iterator = vmiList.iterator(); iterator.hasNext();) {
			VMInstance vmInst = (VMInstance) iterator.next();
			//System.out.println(vmInst.getVmType().toString());
			System.out.println(vmInst.getVmType().getName() + " " + vmInst.getVmType().getUnitType() + " " + vmInst.getVmType().getValue() + " " + vmInst.getVmType().getValueType());
			
			System.out.println(getVMTypeFromProvModel(vmInst.getVmType()));
		}
	}
	
	public void testUpdateDM(){
		
		VMInstance vmInstance;
		
	}
	
	//Getters and setters
	public void setExecWareInstances(LinkedList<ExecwareInstance> insts, DeploymentModel newDm){
		this.instances = insts;
		this.numInstances = instances.size();
		
		//setting the VM templates taken as VMType from the Deployment model
		
		EList<InternalComponentInstance> internalComponentInstances = newDm.getInternalComponentInstances();
		for(InternalComponentInstance ici : internalComponentInstances){
			System.out.println(ici.getName() + " " + ici.getType().getName() + " " + ((InternalComponent) ici.getType()).getRequiredHost().getName() + " "  + ici.getRequiredHostInstance().getName() + " " + ici.getProvidedHostInstances().toString());
		}
		
		EList<VMInstance> VmInstances = newDm.getVmInstances();
/*		//dmcopy.getVmInstances().removeAll(VmInstances);*/
		for(VMInstance vmi : VmInstances){
			System.out.println(vmi.getName() + " " + vmi.getType().getName() + " " + vmi.getVmTypeValue().toString() + " " + vmi.getProvidedHostInstances().get(0).getName());
			//dmcopy.getVmInstances().remove(vmi);
/*			VMInstance test = (VMInstance) EcoreUtil.copy(vmi);
			System.out.println("test: " + test.getName() + " " + test.getType().getName() + " " + test.getVmTypeValue().toString());*/
		}
		
		
		for(ExecwareInstance ewi : insts){
			String appCompName = ewi.getApplicationComponentName();
			for(int i = 0; i<internalComponentInstances.size(); i++){
				InternalComponentInstance ici = internalComponentInstances.get(i);
				if(ici.getType().getName().equalsIgnoreCase(appCompName)){
					VMInstance vmi = VmInstances.get(i);
					ewi.setVMTemplateName(vmi.getType().getName());
				}
			}
		}
	}
	
	public int getNumberExecwareInstances(){ return numInstances; }
	
	public void test(){
		ProviderModel providerModel = null;
		CamelModel cm = (CamelModel) srcDepModel.eContainer();
//		log.info("Looking for PM id:"+providerModelId);
		for(ProviderModel p : cm.getProviderModels())
		{
//			log.info("Testing for PM id:"+p.getName());
			/*if (p.getName().equals(providerModelId)) {
				providerModel = p;
				break;
			}*/
			System.out.println("Testing for PM id:"+p.getName());
		}
	}
	
	public String getUniqueId(){return Long.toString((new Date()).getTime());}
	
	public String getVMTypeFromProvModel(Attribute attribute){
		ProviderModel providerModel = null;
		CamelModel cm = (CamelModel) srcDepModel.eContainer();
		System.out.println("Camel Model id: " + cm.toString());
//		log.info("Looking for PM id:"+providerModelId);
		for(ProviderModel p : cm.getProviderModels())
		{
//			log.info("Testing for PM id:"+p.getName());
			/*if (p.getName().equals(providerModelId)) {
				providerModel = p;
				break;
			}*/
			System.out.println("Testing for PM id: " + p.getName());
			
			
			
			EList<Feature> subFeatures = p.getRootFeature().getSubFeatures(); 
			String logTxt = "\n * Start looking vmType for providerModel " + p.getName();
			logTxt+=(" \n  ** Scan SubFeature of provider model. Scanning "+ subFeatures.size() +" elements");
			for (Feature feature : subFeatures) {
				EList<Attribute> attributes = feature.getAttributes();
				logTxt+=(" ** Will scanning all attributes for feature " + feature.getName()+ ". Number of attributes : " + attributes.size());

				for (Attribute attrb : attributes) {	
					logTxt+=("\n    *** Is attribute name equals vmType ? : " + attrb.getName() + " bla " + attrb.getValue());

					/*if(attrb.getName().equals("VMType"))
					{
						result = attrb;
					}*/
					
					if(attrb.equals(attribute))
						return  parsePMName(p.getName());
				}
			}
			
			
			
			
		}
		System.out.println("Attribute not found");
		return "";
	}
	
	private String parsePMName(String name){
		return name.substring(name.indexOf('#')+1);
	}
	
	public void copyDeploymentModelPrint(LinkedList<ExecwareInstance> instances){
		CamelModel cm = (CamelModel) srcDepModel.eContainer();
		DeploymentModel dmcopy = (DeploymentModel) EcoreUtil.copy(srcDepModel);
		
		EList<InternalComponentInstance> internalComponentInstances = dmcopy.getInternalComponentInstances();
		for(InternalComponentInstance ici : internalComponentInstances){
			System.out.println(ici.getName() + " " + ici.getType().getName() + " " + ici.getRequiredHostInstance().getName() + " " + ici.getProvidedHostInstances().toString());
		}
		
		EList<VMInstance> VmInstances = dmcopy.getVmInstances();
		//dmcopy.getVmInstances().removeAll(VmInstances);
		for(VMInstance vmi : VmInstances){
			System.out.println(vmi.getName() + " " + vmi.getType().getName() + " " + vmi.getVmTypeValue().toString());
			//dmcopy.getVmInstances().remove(vmi);
			VMInstance test = (VMInstance) EcoreUtil.copy(vmi);
			System.out.println("test: " + test.getName() + " " + test.getType().getName() + " " + test.getVmTypeValue().toString());
		}
		
		EList<HostingInstance> HInstances = dmcopy.getHostingInstances();
		for(HostingInstance hi : HInstances){
			System.out.println(hi.getName() + " " + hi.getType().getName() + " " + hi.getProvidedHostInstance().getName() + " " + hi.getRequiredHostInstance().getName() );
		}
		dmcopy.getHostingInstances().removeAll(HInstances);
		
		//cm.getDeploymentModels().add(dmcopy);//commented for not add to CDO unnecessarily
		int dmid = cm.getDeploymentModels().size()-1;
		System.out.println("New Deployment Model index is " + dmid);
		
		//for Test
		setExecWareInstances(instances, dmcopy);
		printExecwareInstances(instances);
	}
	
	public int getInstanceCardinality(VMInstance vmInstance){
		Attribute attribute = vmInstance.getVmType();
		//attribute.getValue();
		return 2;
	}
	
	public void updateCDO(LinkedList<ExecwareInstance> instances){
		this.targetDepModel = copyDeploymentModel(instances);
		
		fillDataFromDM(instances, this.targetDepModel);
		
		computeDatasToRegister(instances, this.targetDepModel);
		computeDatasToRemove(instances, this.targetDepModel);
		registerDataHolderToCDO();
	}
	
	public int updateFromMapping(LinkedList<ExecwareInstance> instances, CamelExecwareMapping mapping){
		this.targetDepModel = copyDeploymentModel(instances);
		
		
		//fillDataFromDM(instances, this.targetDepModel);
		fillDataFromDMAndMapping(instances, mapping, this.targetDepModel);
		
		computeDatasToRegister(instances, this.targetDepModel);
		computeDatasToRemove(instances, this.targetDepModel);
		registerDataHolderToCDO();
		addToMapping(instances, mapping);
		return addToCDO();
	}
	
	private void addToMapping(LinkedList<ExecwareInstance> instances, CamelExecwareMapping mapping){
		
		for(ExecwareInstance ewInst : instances){
			boolean found = false;
			if(ewInst.getScaledState()==ExecwareInstance.scaledState.NEW){//scaled VM found - so add in the Mapping
				mapping.addVMI(ewInst.getVirtualMachineName(), ewInst.getVMTemplateName());
				mapping.setVMIID(ewInst.getVirtualMachineName(), ewInst.getVirtualMachine()+"");
				String iCompInstName = ewInst.getVirtualMachineName() + "_Inst_" + getUniqueId();
				mapping.addCompInst(iCompInstName, mapping.getApplicationInstance(ewInst.getApplicationComponentName()), mapping.getEntityLCAppComponent(ewInst.getApplicationComponentName()), mapping.getEntityVMInstance(ewInst.getVirtualMachineName()));
				mapping.setCompInstID(iCompInstName, ewInst.getInstanceId());
			}
		}
		
	}
	
	private int addToCDO(){
		CamelModel cm = (CamelModel) srcDepModel.eContainer();
		cm.getDeploymentModels().add(this.targetDepModel);
		int dmid = cm.getDeploymentModels().size()-1;//New deployment model Id
		System.out.println("New Deployment Model index is " + dmid);
				
		return dmid;
	}
	
	
	private void fillDataFromDMAndMapping(LinkedList<ExecwareInstance> instances, CamelExecwareMapping mapping, DeploymentModel depModel){
		
		Map<String, String> VMInstancesInfo = mapping.getInfoVMIs();//the instances existing in the Mapping - for the updated names
		
		for(ExecwareInstance ewInst : instances){
			
			for(String key: VMInstancesInfo.keySet()){
				
	            int VmiId = ewInst.getVirtualMachine();
	            if(key.equalsIgnoreCase(Integer.toString(VmiId))){
	            	ewInst.setVirtualMachineName(VmiId, VMInstancesInfo.get(key));
	            	//ewInst.setNewVirtualMachineName(VmiId, VMInstancesInfo.get(key));
	            }
	        }
		}
		
		EList<HostingInstance> HInstances = depModel.getHostingInstances();
		EList<InternalComponentInstance> internalComponentInstances = depModel.getInternalComponentInstances();
		EList<VMInstance> VmInstances = depModel.getVmInstances();
		
		//Filling instances that is NEW or EXISTS		
		for(ExecwareInstance ewInst : instances){
			boolean found = false;
			int pointer = 0;
			for( ; pointer < VmInstances.size(); pointer++){
				VMInstance vmInst = VmInstances.get(pointer);
				//if(vmInst.getName().toString().equalsIgnoreCase(ewInst.getNewVirtualMachineName())){
				if(vmInst.getName().toString().equalsIgnoreCase(ewInst.getVirtualMachineName())){
					found = true;
					ewInst.setScaledState(ExecwareInstance.scaledState.EXISTS);
					ewInst.setIciPointerinDm(pointer);
					LOGGER.log(Level.INFO, "Existing VM " + ewInst.getVirtualMachineName());
				}
			}
			
			if(!found){//it is a new VM instance due to scaling
				ewInst.setScaledState(ExecwareInstance.scaledState.NEW);
				int ewInstAC = ewInst.getApplicationComponent();
				boolean flag = false;
				//setting the pointer to a similar InternalComponentInstance in the current Deployment model
				for(ExecwareInstance ewInstCopy : instances){
					if(!ewInst.equals(ewInstCopy) && ewInstAC == ewInstCopy.getApplicationComponent()){
						ewInst.setIciPointerinDm(ewInstCopy.getIciPointerinDm());
						flag = true;
						LOGGER.log(Level.INFO, "Scaled VM " + ewInst.getVirtualMachineName() + " found. To be updated in the CDO.");
					}
				}
				if(!flag){
					ewInst.setScaledState(ExecwareInstance.scaledState.NOTFOUND);
					LOGGER.log(Level.WARNING, "Orphan VM " + ewInst.getVirtualMachineName() + " Please check!");
				}
			}
		}
	}
	
private void fillDataFromDM(LinkedList<ExecwareInstance> instances, DeploymentModel depModel){
		
		EList<HostingInstance> HInstances = depModel.getHostingInstances();
		EList<InternalComponentInstance> internalComponentInstances = depModel.getInternalComponentInstances();
		EList<VMInstance> VmInstances = depModel.getVmInstances();
		
		//Filling instances that is NEW or EXISTS		
		for(ExecwareInstance ewInst : instances){
			boolean found = false;
			int pointer = 0;
			for( ; pointer < VmInstances.size(); pointer++){
				VMInstance vmInst = VmInstances.get(pointer);
				//if(vmInst.getName().toString().equalsIgnoreCase(ewInst.getNewVirtualMachineName())){
				if(vmInst.getName().toString().equalsIgnoreCase(ewInst.getVirtualMachineName())){
					found = true;
					ewInst.setScaledState(ExecwareInstance.scaledState.EXISTS);
					ewInst.setIciPointerinDm(pointer);
					LOGGER.log(Level.INFO, "Existing VM " + ewInst.getVirtualMachineName());
				}
			}
			
			if(!found){//it is a new VM instance due to scaling
				ewInst.setScaledState(ExecwareInstance.scaledState.NEW);
				int ewInstAC = ewInst.getApplicationComponent();
				boolean flag = false;
				//setting the pointer to a similar InternalComponentInstance in the current Deployment model
				for(ExecwareInstance ewInstCopy : instances){
					if(!ewInst.equals(ewInstCopy) && ewInstAC == ewInstCopy.getApplicationComponent()){
						ewInst.setIciPointerinDm(ewInstCopy.getIciPointerinDm());
						flag = true;
						LOGGER.log(Level.INFO, "Scaled VM " + ewInst.getVirtualMachineName() + " found. To be updated in the CDO.");
					}
				}
				if(!flag){
					ewInst.setScaledState(ExecwareInstance.scaledState.NOTFOUND);
					LOGGER.log(Level.WARNING, "Orphan VM " + ewInst.getVirtualMachineName() + " Please check!");
				}
			}
		}
	}


	private DeploymentModel copyDeploymentModelOld(LinkedList<ExecwareInstance> instances){
		CamelModel cm = (CamelModel) srcDepModel.eContainer();
		DeploymentModel dmcopy = (DeploymentModel) EcoreUtil.copy(srcDepModel);
		cm.getDeploymentModels().add(dmcopy);
		int dmid = cm.getDeploymentModels().size()-1;//New deployment model Id
		System.out.println("New Deployment Model index is " + dmid);
		DeploymentModel newDm = (DeploymentModel) cm.getDeploymentModels().get(dmid);//New Deployment Model
		
		//this.dataHolder.setDM(newDm);
		//this.dataHolder.setDmId(dmid);
		setExecWareInstances(instances, newDm);
		
		//for Test
		printExecwareInstances(instances);
		
		return newDm;
	}
	
	private DeploymentModel copyDeploymentModel(LinkedList<ExecwareInstance> instances){
		DeploymentModel dmcopy = (DeploymentModel) EcoreUtil.copy(srcDepModel);

		return dmcopy;
	}

	
	public void printExecwareInstances(LinkedList<ExecwareInstance> instances){
		
		for(ExecwareInstance inst : instances){
			System.out.println(inst.getApplicationComponent() + " " + inst.getApplicationComponentName() + " " + inst.getVMTemplateName() + " " + inst.getVirtualMachine() + " " + inst.getVirtualMachineName());
		}
	}
	
	private void computeDatasToRegister(LinkedList<ExecwareInstance> instances, DeploymentModel depModel){
		//int numInstances = getNumberExecwareInstances();
		
		EList<InternalComponentInstance> componentInstancesToRegister = new BasicEList<InternalComponentInstance>();
		EList<VMInstance> vmInstancesToRegister = new BasicEList<VMInstance>();
		EList<HostingInstance> hostingInstancesToRegister = new BasicEList<HostingInstance>();
		EList<CommunicationInstance> communicationInstances = new BasicEList<CommunicationInstance>();
		
		// Create CI Instance
		/*EList<InternalComponentInstance> internalComponentInstanceToRegisters = dataHolder.getDM().getInternalComponentInstances();
		dataHolder.getComponentInstancesToRegister().addAll(internalComponentInstanceToRegisters);*/
		for(ExecwareInstance ewInst : instances){
			if(ewInst.getScaledState()!=ExecwareInstance.scaledState.NEW)
				//break;
				continue;
			
			//Declaring components
			InternalComponent ic = null;
			InternalComponentInstance icInst = null;
			VMInstance vmInst = null;
			HostingInstance hInst = null;
			//CommunicationInstance commInst = null;
			
			//Fetching a copy of the instances which has to be replicated
			int index = ewInst.getIciPointerinDm();
			InternalComponentInstance icInstToBeReplicated = depModel.getInternalComponentInstances().get(index);
			
			//create a copy of the InternalComponentInstance
			//finding the InternalComponent from the Deployment model in CDO using the App Comp Name
			String ACName = ewInst.getApplicationComponentName();
			EList<InternalComponent> components = depModel.getInternalComponents();
			for (InternalComponent internalComponent : components){
				if(internalComponent.getName().toLowerCase().equals(ACName))
					ic = internalComponent;//holding a pointer to the similar InternalComponentInstance
			}
			
			//Now creating an IC Instance using infos from InternalComponent and its replicated instance
			icInst = DeploymentFactory.eINSTANCE.createInternalComponentInstance();
			//icInst.setName(icInstToBeReplicated.getName() + "EWScaled");
			icInst.setName(icInstToBeReplicated.getName() + getUniqueId());
			icInst.setType(ic);
						
			//ProvidedCommunicationInstance

			/*for(int i=0 ;i <ic.getProvidedCommunications().size();i++)
			{	
				ProvidedCommunicationInstance providedCommunicationInstance = DeploymentFactory.eINSTANCE.createProvidedCommunicationInstance();
				{

					ProvidedCommunication providedCommunication = ic.getProvidedCommunications().get(i);
					providedCommunicationInstance.setType(providedCommunication);
					//		providedCommunicationInstance.setOwner(internalComponentInstance);
					providedCommunicationInstance.setName(providedCommunication.getName() + "ProvidedCommunicationInstance_" + "EWScaled");
				}
				icInst.getProvidedCommunicationInstances().add(providedCommunicationInstance);
			}*/
			for(int i=0 ;i <icInstToBeReplicated.getProvidedCommunicationInstances().size();i++)
			{	
				ProvidedCommunicationInstance providedCommunicationInstance = DeploymentFactory.eINSTANCE.createProvidedCommunicationInstance();
				{

					CommunicationPort providedCommunication = icInstToBeReplicated.getProvidedCommunicationInstances().get(i).getType();
					providedCommunicationInstance.setType(providedCommunication);
					//		providedCommunicationInstance.setOwner(internalComponentInstance);
					//providedCommunicationInstance.setName(providedCommunication.getName() + "EWScaled");
					providedCommunicationInstance.setName(providedCommunication.getName() + getUniqueId());
				}
				icInst.getProvidedCommunicationInstances().add(providedCommunicationInstance);
			}
			
			//ProvidedCommunicationInstance

			/*for(int i=0 ;i <ic.getRequiredCommunications().size();i++)
			{	
				RequiredCommunicationInstance requiredCommunicationInstance = DeploymentFactory.eINSTANCE.createRequiredCommunicationInstance(); 
				{
					RequiredCommunication requiredCommunication = ic.getRequiredCommunications().get(i);

					requiredCommunicationInstance.setType(requiredCommunication);
					//	requiredCommunicationInstance.setOwner(internalComponentInstance);
					requiredCommunicationInstance.setName(requiredCommunication.getName() + "ReqCommunicationInstance_" + "EWScaled");
				}
				icInst.getRequiredCommunicationInstances().add(requiredCommunicationInstance);

			}*/	
			for(int i=0 ;i <icInstToBeReplicated.getRequiredCommunicationInstances().size();i++)
			{	
				RequiredCommunicationInstance requiredCommunicationInstance = DeploymentFactory.eINSTANCE.createRequiredCommunicationInstance(); 
				{
					CommunicationPort requiredCommunication = icInstToBeReplicated.getRequiredCommunicationInstances().get(i).getType();

					requiredCommunicationInstance.setType(requiredCommunication);
					//	requiredCommunicationInstance.setOwner(internalComponentInstance);
					//requiredCommunicationInstance.setName(requiredCommunication.getName() + "EWScaled");
					requiredCommunicationInstance.setName(requiredCommunication.getName() + getUniqueId());
				}
				icInst.getRequiredCommunicationInstances().add(requiredCommunicationInstance);

			}
			//RequiredHostInstance
			/*RequiredHostInstance requiredHostInstance = DeploymentFactory.eINSTANCE.createRequiredHostInstance();
			{
				requiredHostInstance.setType(ic.getRequiredHost());
				requiredHostInstance.setName(ic.getName() + "RequiredHostInstance_" + "EWScaled");
				//	requiredHostInstance.setOwner(internalComponentInstance);
			}
			icInst.setRequiredHostInstance(requiredHostInstance);*/
			RequiredHostInstance requiredHostInstance = DeploymentFactory.eINSTANCE.createRequiredHostInstance();
			{
				requiredHostInstance.setType(icInstToBeReplicated.getRequiredHostInstance().getType());
				//requiredHostInstance.setName(icInstToBeReplicated.getRequiredHostInstance().getName() + "EWScaled");
				requiredHostInstance.setName(icInstToBeReplicated.getRequiredHostInstance().getName() + getUniqueId());
				//	requiredHostInstance.setOwner(internalComponentInstance);
			}
			icInst.setRequiredHostInstance(requiredHostInstance);
			
			
			//Adding the ICInstance now
			componentInstancesToRegister.add(icInst);
						
			//InternalComponent internalComponent = depModel.getInternalComponentInstances().get(index).get
			
			
			//create a copy of the VMInstance
			
			VMInstance vmInstToBeReplicated = depModel.getVmInstances().get(index);
			VM vmToBeReplicated = (VM) vmInstToBeReplicated.getType();
			vmInst = DeploymentFactory.eINSTANCE.createVMInstance();
			vmInst.setType(vmToBeReplicated);
			//vmInst.setName(vmInstToBeReplicated.getName() + "EWScaled");
			vmInst.setName(vmInstToBeReplicated.getName() + getUniqueId());
			for(int i=0 ;i < vmInstToBeReplicated.getProvidedHostInstances().size();i++)
			{
				ProvidedHostInstance providedHostInstance = DeploymentFactory.eINSTANCE.createProvidedHostInstance();
				{
					ProvidedHostInstance providedHost = vmInstToBeReplicated.getProvidedHostInstances().get(i);
					//providedHostInstance.setName(providedHost.getName() + "EWScaled");
					providedHostInstance.setName(providedHost.getName() + getUniqueId());
					//providedHostInstance.setOwner(vmInstance);
					providedHostInstance.setType(providedHost.getType());
				}
				vmInst.getProvidedHostInstances().add(providedHostInstance);
			}
			
			vmInstancesToRegister.add(vmInst);
			
			
			//create a copy of the HostingInstance
			HostingInstance hostingInstToBeReplicated = depModel.getHostingInstances().get(index);
			Hosting hostingToBeReplicated = hostingInstToBeReplicated.getType();
			hInst = DeploymentFactory.eINSTANCE.createHostingInstance();
			//hInst.setName(hostingInstToBeReplicated.getName() + "EWScaled");
			hInst.setName(hostingInstToBeReplicated.getName() + getUniqueId());
			EList<ProvidedHostInstance> pis = vmInst.getProvidedHostInstances();
			hInst.setProvidedHostInstance(pis.get(0));
			hInst.setRequiredHostInstance(icInst.getRequiredHostInstance());
			hInst.setType(hostingToBeReplicated);
			
			hostingInstancesToRegister.add(hInst);
			
			
			//create a copy of the CommunicationInstance
			//#CommInstances equals #Comm - so not required to replicate
			/*CommunicationInstance commInstToBeReplicated = depModel.getCommunicationInstances().get(index);
			Communication commToBeReplicated = (Communication) commInstToBeReplicated.getType();*/
			
		}
		
		//Adding the new Instance types to the dataHolder
		this.dataHolder.setComponentInstancesToRegister(componentInstancesToRegister);
		this.dataHolder.setVmInstancesToRegister(vmInstancesToRegister);
		this.dataHolder.setHostingInstancesToRegisters(hostingInstancesToRegister);
		this.dataHolder.setCommunicationInstances(communicationInstances);
		
	}
	
	private void computeDatasToRemove(LinkedList<ExecwareInstance> instances, DeploymentModel depModel){
		
		EList<VMInstance> VmInstances = depModel.getVmInstances();
		
		int size = VmInstances.size();
		
		for(int i=size-1; i>=0; i--){
			VMInstance vmInst = VmInstances.get(i);
			boolean found = false;
			for(ExecwareInstance ewInst : instances){
				//if(vmInst.getName().toString().equalsIgnoreCase(ewInst.getNewVirtualMachineName())){
				if(vmInst.getName().toString().equalsIgnoreCase(ewInst.getVirtualMachineName())){
					found = true;
					break;
				}
			}
			
			if(found == false){
				LOGGER.log(Level.INFO, "Removing VM Instance " + vmInst.getName().toString() + " and others with index " + i + " from Deployment model");
				depModel.getVmInstances().remove(i);
				
				LOGGER.log(Level.INFO, "Removing IC Instance " + depModel.getInternalComponentInstances().get(i).getName() + " from Deployment model");
				depModel.getInternalComponentInstances().remove(i);
				
				LOGGER.log(Level.INFO, "Removing Hosting Instance " + depModel.getHostingInstances().get(i).getName() + " from Deployment model");
				depModel.getHostingInstances().remove(i);
				
			}
		}		
	}
	
	private void registerDataHolderToCDO(){
		
		List<VMInstance> vmInstancesToRegister = dataHolder.getVmInstancesToRegister();
		for (VMInstance vmInstance : vmInstancesToRegister){
			this.targetDepModel.getVmInstances().add(vmInstance);
		}
		
		List<InternalComponentInstance> componentInstancesToRegister = dataHolder.getComponentInstancesToRegister();
		for (InternalComponentInstance internalComponentInstance : componentInstancesToRegister){
			this.targetDepModel.getInternalComponentInstances().add(internalComponentInstance);
		}
		
		List<HostingInstance> hostingInstancesToRegister = dataHolder.getHostingInstancesToRegisters();
		for (HostingInstance hostingInstance : hostingInstancesToRegister){
			this.targetDepModel.getHostingInstances().add(hostingInstance);
		}
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	
	
	private class DataHolder {

		//private static Logger LOGGER = Logger.getLogger(DataHolder.class.getName());
		
		private List<InternalComponentInstance> componentInstancesToRegister = new ArrayList<>();
		private List<VMInstance> vmInstancesToRegister = new ArrayList<>();
		private List<HostingInstance> hostingInstancesToRegister = new ArrayList<>();
		private List<CommunicationInstance> communicationInstances = new ArrayList<>();
		
		/*private DeploymentModel dm;
		private int dmId;*/
		

		
		public List<InternalComponentInstance> getComponentInstancesToRegister() {
			return componentInstancesToRegister;
		}
		
		public boolean setComponentInstancesToRegister(List<InternalComponentInstance> componentInstancesToRegister) {
			return this.componentInstancesToRegister.addAll(componentInstancesToRegister);
		}
		
		public List<VMInstance> getVmInstancesToRegister() {
			return vmInstancesToRegister;
		}
		
		public boolean setVmInstancesToRegister(List<VMInstance> vmInstancesToRegister) {
			return this.vmInstancesToRegister.addAll(vmInstancesToRegister);
		}

		public List<HostingInstance> getHostingInstancesToRegisters() {
			return hostingInstancesToRegister;
		}
		
		public boolean setHostingInstancesToRegisters(List<HostingInstance> hostingInstancesToRegister) {
			return this.hostingInstancesToRegister.addAll(hostingInstancesToRegister);
		}
		
		public List<CommunicationInstance> getCommunicationInstances() {
			return communicationInstances;
		}
		
		public boolean setCommunicationInstances(List<CommunicationInstance> communicationInstances) {
			return this.communicationInstances.addAll(communicationInstances);
		}
		
		/*public void setDM(DeploymentModel d) { dm = d; }
		public DeploymentModel getDM() { return dm; }

		public void setDmId(int d) { dmId = d; }
		public int getDmId() { return dmId; }*/


	}
}
