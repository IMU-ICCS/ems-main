/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.profiler.cp.generator.model.camel.lib;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.Communication;
import eu.paasage.camel.deployment.CommunicationInstance;
import eu.paasage.camel.deployment.CommunicationType;
import eu.paasage.camel.deployment.Component;
import eu.paasage.camel.deployment.ComponentInstance;
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
import eu.paasage.camel.location.Location;
import eu.paasage.camel.organisation.CloudProvider;
import eu.paasage.camel.requirement.HorizontalScaleRequirement;
import eu.paasage.camel.requirement.ImageRequirement;
import eu.paasage.camel.requirement.OSOrImageRequirement;
import eu.paasage.camel.requirement.OSRequirement;
import eu.paasage.camel.requirement.QuantitativeHardwareRequirement;
import eu.paasage.camel.requirement.RequirementModel;
import eu.paasage.upperware.metamodel.application.ApplicationComponent;
import eu.paasage.upperware.metamodel.application.ApplicationFactory;
import eu.paasage.upperware.metamodel.application.CPU;
import eu.paasage.upperware.metamodel.application.CloudMLElementUpperware;
import eu.paasage.upperware.metamodel.application.ImageUpperware;
import eu.paasage.upperware.metamodel.application.Memory;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.application.Provider;
import eu.paasage.upperware.metamodel.application.ProviderDimension;
import eu.paasage.upperware.metamodel.application.RequiredFeature;
import eu.paasage.upperware.metamodel.application.Storage;
import eu.paasage.upperware.metamodel.application.VirtualMachine;
import eu.paasage.upperware.metamodel.application.VirtualMachineProfile;
import eu.paasage.upperware.metamodel.types.DoubleValueUpperware;
import eu.paasage.upperware.metamodel.types.IntegerValueUpperware;
import eu.paasage.upperware.metamodel.types.TypesFactory;
import eu.paasage.upperware.metamodel.types.typesPaasage.DataUnitEnum;
import eu.paasage.upperware.metamodel.types.typesPaasage.LocationUpperware;
import eu.paasage.upperware.metamodel.types.typesPaasage.OS;
import eu.paasage.upperware.metamodel.types.typesPaasage.ProviderType;
import eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasageFactory;
import eu.paasage.upperware.profiler.cp.generator.db.api.IDatabaseProxy;
import eu.paasage.upperware.profiler.cp.generator.db.lib.CDODatabaseProxy;
import eu.paasage.upperware.profiler.cp.generator.model.lib.GenerationOrchestrator;
import eu.paasage.upperware.profiler.cp.generator.model.lib.PaaSageConfigurationWrapper;
import eu.paasage.upperware.profiler.cp.generator.model.tools.PaasageModelTool;

/**
 * This class provides the functionality to deal with the CloudML model part of Camel
 * @author danielromero
 *
 */
public class DeploymentModelParser 
{
	/*
	 * ATTRIBUTES
	 */
	/*
	 * Logger
	 */
	protected Logger logger= GenerationOrchestrator.getLogger(); 
	
	/*
	 * The application factory instance
	 */
	protected ApplicationFactory applicationFactory= ApplicationFactory.eINSTANCE; 
	
	/*
	 * The type factory instance
	 */
	protected TypesFactory typesFactory= TypesFactory.eINSTANCE; 
	
	/*
	 * The type paasage factory instance
	 */
	protected TypesPaasageFactory typesPaasageFactory= TypesPaasageFactory.eINSTANCE; 
	
	/*
	 * Hash containing for each VM a list with the related profiles
	 */
	protected Map<String, List<VirtualMachineProfile>> vmProfiles; 
	
	/*
	 * Proxy of the Database
	 */
	protected static IDatabaseProxy proxy= CDODatabaseProxy.getInstance(); 
	
	
	private static int MAX_INSTANCE_NUMBER=1; 
	
	private static int MIN_INSTANCE_NUMBER=0; 
	
	
	
	
	/*
	 * METHODS
	 */
	
	public Map<String, List<VirtualMachineProfile>> getVmProfiles() {
		return vmProfiles;
	}

	/**
	 * Parses the provided deployment model 
	 * @param pim The CloudML model
	 * @param configurationWrapper The wrapper that contains the configuration information
	 */
	public void parsePIM(DeploymentModel pim, PaaSageConfigurationWrapper configurationWrapper)
	{
		List<VM> vms= getVMList(pim); 
		List<VMInstance> vmInstances= PaasageModelTool.getVMInstancesList(pim); 
		List<Component> components= PaasageModelTool.getComponentsList(pim); 
		List<ComponentInstance> componentInstances= PaasageModelTool.getComponentInstancesList(pim); 
		vmProfiles= new Hashtable<>();
	
		boolean addNewCandidates= false; 
		
		PaasageConfiguration configuration= configurationWrapper.getPaasageConfiguration(); 
		
		EList<Provider> cloudCandidates= configuration.getProviders(); 
		
		if(cloudCandidates.size()==0)
			addNewCandidates=true; 
		
		logger.info(" **		Parsing VMInstances");
		parseVMInstances(vmInstances, configurationWrapper, addNewCandidates, pim.getGlobalVMRequirementSet());
		
		logger.info(" **		Parsing VMs");
		parseVMs(vms, configurationWrapper, addNewCandidates, pim.getGlobalVMRequirementSet());
		
		logger.info("**		Parsing Component Instances");
		parseComponentInstances(componentInstances, configuration);
		
		logger.info("**		Parsing Components");
		parseComponents(components, componentInstances, configuration, ((CamelModel) pim.eContainer()).getRequirementModels().get(0));
				
		
		//parseExternalComponents(pim.getExternalComponents(), configuration); //TODO HOW TO PROCESS EXTERNAL COMPONENTS
		
		
		resolveContaimentDependencyInstances(pim.getHostingInstances(), configuration);
		
		resolveContaimentDependencies(pim, configuration); //TODO A DEMAND attribute is expected ending in "Host" for considering this relationship
		
		resolveCommunicationDependencyInstances(pim.getCommunicationInstances(), configuration);
		
		resolveCommunicationDependencies(pim, configuration);
		
		checkGivenSolutionByUser(pim, configurationWrapper);
			
	}
		
	/**
	 * Processes communication dependencies between internal components
	 * @param pim The deployment model
	 * @param configuration The paasage configuration been created
	 */
	protected void resolveCommunicationDependencies(DeploymentModel pim, PaasageConfiguration configuration)
	{
		
		EList<Communication> communications= pim.getCommunications(); 
		
		EList<CommunicationInstance> communicationInstances= pim.getCommunicationInstances(); 
		
		List<ComponentInstance> componentInstances= PaasageModelTool.getComponentInstancesList(pim); 
		
		for(ComponentInstance ci:componentInstances)
		{
			logger.debug("DeployementModelParser - resolveCommunicationDependencies - component instance "+ci.getName());
		}
		
		for(Communication communication: communications)
		{
			
			List<CommunicationInstance> filtredCommunicationInstances= PaasageModelTool.getCommunicationInstanceByTypeId(communicationInstances, communication.getName()); 
			Component provider= (Component) communication.getProvidedCommunication().eContainer(); 
			Component client= (Component) communication.getRequiredCommunication().eContainer(); 
			//Component provider= communication.getProvidedCommunication().getOwner(); 
			//Component client= communication.getRequiredCommunication().getOwner(); 
			
			List<ComponentInstance> filtredComponentInstances= PaasageModelTool.getComponentInstancesByTypeId(componentInstances, client.getName()); 
			
			logger.debug("DeployementModelParser - resolveCommunicationDependencies - component instances size "+ filtredComponentInstances.size()+" client name "+client.getName());
			
			if(filtredComponentInstances.size()>0)
				for(ComponentInstance ci: filtredComponentInstances)
				{
					//CommunicationInstance communicationInstance= PaasageModelTool.getCommunicationInstanceForClientComponent(filtredCommunicationInstances, ci); 
					if(!PaasageModelTool.existCommunicationInstanceForClientComponent(filtredCommunicationInstances, ci))
					{
						createCommunicationDependency(configuration, provider, ci.getName(), communication);
						/*ApplicationComponent providerAppComponent= PaasageModelTool.searchApplicationComponentById(configuration.getComponents(), provider.getName()); 
						
						logger.debug("DeployementModelParser - resolveCommunicationDependencies - providerAppComponent "+providerAppComponent+ "provider name "+provider.getName());
						
						ApplicationComponent clientAppComponent= PaasageModelTool.searchApplicationComponentById(configuration.getComponents(), ci.getName()); 
						
						logger.debug("DeployementModelParser - resolveCommunicationDependencies - clientAppComponent "+clientAppComponent+" client name "+ci.getName());
						
						RequiredFeature rf= buildRequiredCommunicationPortFeature(communication.getRequiredCommunication(), providerAppComponent); 
						
						clientAppComponent.getRequiredFeatures().add(rf); */
					}
				}
			else
			{
				createCommunicationDependency(configuration, provider, client.getName(), communication);
				/*ApplicationComponent providerAppComponent= PaasageModelTool.searchApplicationComponentById(configuration.getComponents(), provider.getName()); 
				
				logger.debug("DeployementModelParser - resolveCommunicationDependencies - providerAppComponent "+providerAppComponent+ "provider name "+provider.getName());
				
				ApplicationComponent clientAppComponent= PaasageModelTool.searchApplicationComponentById(configuration.getComponents(), client.getName()); 
				
				logger.debug("DeployementModelParser - resolveCommunicationDependencies - clientAppComponent "+clientAppComponent+" client name "+client.getName());
				
				RequiredFeature rf= buildRequiredCommunicationPortFeature(communication.getRequiredCommunication(), providerAppComponent); 
				
				clientAppComponent.getRequiredFeatures().add(rf); */
			}
			

			
			
		}
	}
	
	
	protected void createCommunicationDependency(PaasageConfiguration configuration, Component provider, String clientId, Communication communication)
	{
		ApplicationComponent providerAppComponent= PaasageModelTool.searchApplicationComponentById(configuration.getComponents(), provider.getName()); 
		
		logger.debug("DeployementModelParser - createCommunicationDependency - providerAppComponent "+providerAppComponent+ " provider name "+provider.getName());
		
		ApplicationComponent clientAppComponent= PaasageModelTool.searchApplicationComponentById(configuration.getComponents(), clientId); 
		
		logger.debug("DeployementModelParser - createCommunicationDependency - clientAppComponent "+clientAppComponent+" client name "+clientId);
		
		boolean isLocal= true; 
		
		if(communication.getType().getValue()==CommunicationType.REMOTE_VALUE)
		{
			isLocal= false; 
		}
		
		RequiredFeature rf= buildRequiredCommunicationPortFeature(communication.getRequiredCommunication(), providerAppComponent, isLocal); 
		
		clientAppComponent.getRequiredFeatures().add(rf); 
	}
	
	
	protected void resolveCommunicationDependencyInstances(EList<CommunicationInstance> communications, PaasageConfiguration configuration)
	{
		for(CommunicationInstance communication: communications)
		{
			//ComponentInstance provider= communication.getProvidedCommunicationInstance().getOwner();
			ComponentInstance provider= (ComponentInstance) communication.getProvidedCommunicationInstance().eContainer();
			//ComponentInstance client= communication.getRequiredCommunicationInstance().getOwner();
			ComponentInstance client= (ComponentInstance) communication.getRequiredCommunicationInstance().eContainer();
			
			ApplicationComponent providerAppComponent= PaasageModelTool.searchApplicationComponentById(configuration.getComponents(), provider.getName()); 
			
			logger.debug("DeployementModelParser - resolveCommunicationDependencyInstances - providerAppComponent "+providerAppComponent+ "provider name "+provider.getName());
			
			ApplicationComponent clientAppComponent= PaasageModelTool.searchApplicationComponentById(configuration.getComponents(), client.getName()); 
			
			logger.debug("DeployementModelParser - resolveCommunicationDependencyInstances - clientAppComponent "+clientAppComponent+" client name "+client.getName());
			
			RequiredFeature rf= buildRequiredCommunicationInstancePortFeature(communication.getRequiredCommunicationInstance(), providerAppComponent); 
			
			clientAppComponent.getRequiredFeatures().add(rf); 
			
			
		}
	}
	
	
	/**
	 * Processes hosting dependencies between components in the deployment model
	 * @param pim The deployment model
	 * @param configuration The paasage configuration been created
	 */
	protected void resolveContaimentDependencies(DeploymentModel pim, PaasageConfiguration configuration)
	{
		
		EList<Hosting> hostingRelationships= pim.getHostings(); 
		
		EList<HostingInstance> hostingInstances= pim.getHostingInstances(); 
		
		EList<InternalComponentInstance> componentInstances= pim.getInternalComponentInstances(); 
		
		for(Hosting hosting: hostingRelationships)
		{
			
			List<HostingInstance> filtredHostingInstances= PaasageModelTool.getHostingInstanceByTypeId(hostingInstances, hosting.getName()); 
			
			InternalComponent client= (InternalComponent) hosting.getRequiredHost().eContainer();
			
			List<ComponentInstance> cInstanceList= PaasageModelTool.getComponentInstancesList(pim);
			
			List<ComponentInstance> filtredComponentInstances= PaasageModelTool.getComponentInstancesByTypeId(cInstanceList, client.getName()); 
			
			if(filtredComponentInstances.size()>0)
				for(ComponentInstance instance:filtredComponentInstances)
				{
					if(!PaasageModelTool.existHostingInstanceForComponentInstance(filtredHostingInstances, instance))
					{
						defineContainmentDependency(instance.getName(), configuration, hosting);
						/*ApplicationComponent clientAppComponent= PaasageModelTool.searchApplicationComponentById(configuration.getComponents(), instance.getName());
						
						if(hosting.getProvidedHost().eContainer() instanceof VM)
						{
							VM vm= (VM) hosting.getProvidedHost().eContainer(); 
							
							logger.debug("DeployementModelParser - resolveContaimentDependencies - VM type name "+vm.getName()+"!");
							
							VirtualMachineProfile vmp= PaasageModelTool.searchVMProfileById(configuration.getVmProfiles(), vm.getName()); //TODO CHANGED BY VM NAME 
							logger.debug("DeployementModelParser - resolveContaimentDependencies - Client component "+clientAppComponent+ " Name "+instance.getName());
							clientAppComponent.getRequiredProfile().add(vmp); 
							
							logger.debug("DeployementModelParser - resolveContaimentDependencies - VM dependency add between "+instance.getName()+" and "+vm.getName()+"!");
						}	
						else //I assume that it is a component
						{
							Component provider= (Component) hosting.getProvidedHost().eContainer(); 
							
							ApplicationComponent providerAppComponent= PaasageModelTool.searchApplicationComponentById(configuration.getComponents(), provider.getName()); 
							
							RequiredFeature rf= builProvidedContaimentPortFeature(hosting.getName(), providerAppComponent);
							
							clientAppComponent.getRequiredFeatures().add(rf); 
							
							logger.debug("DeployementModelParser - resolveContaimentDependencies - Dependency between "+instance.getName()+" and "+provider.getName()+" created!");
						}*/
						
					}
				}
			else
			{
				defineContainmentDependency(client.getName(), configuration, hosting);
				/*ApplicationComponent clientAppComponent= PaasageModelTool.searchApplicationComponentById(configuration.getComponents(), client.getName());
				
				if(hosting.getProvidedHost().eContainer() instanceof VM)
				{
					VM vm= (VM) hosting.getProvidedHost().eContainer(); 
					
					logger.debug("DeployementModelParser - resolveContaimentDependencies - VM type name "+vm.getName()+"!");
					
					VirtualMachineProfile vmp= PaasageModelTool.searchVMProfileById(configuration.getVmProfiles(), vm.getName()); //TODO CHANGED BY VM NAME 
					logger.debug("DeployementModelParser - resolveContaimentDependencies - Client component "+clientAppComponent+ " Name "+client.getName());
					clientAppComponent.getRequiredProfile().add(vmp); 
					
					logger.debug("DeployementModelParser - resolveContaimentDependencies - VM dependency add between "+client.getName()+" and "+vm.getName()+"!");
				}	
				else //I assume that it is a component
				{
					Component provider= (Component) hosting.getProvidedHost().eContainer(); 
					
					ApplicationComponent providerAppComponent= PaasageModelTool.searchApplicationComponentById(configuration.getComponents(), provider.getName()); 
					
					RequiredFeature rf= builProvidedContaimentPortFeature(hosting.getName(), providerAppComponent);
					
					clientAppComponent.getRequiredFeatures().add(rf); 
					
					logger.debug("DeployementModelParser - resolveContaimentDependencies - Dependency between "+client.getName()+" and "+provider.getName()+" created!");
				}*/
			}
				
		}
	}
	
	
	protected void defineContainmentDependency(String clientId, PaasageConfiguration configuration, Hosting hosting)
	{
		ApplicationComponent clientAppComponent= PaasageModelTool.searchApplicationComponentById(configuration.getComponents(), clientId);
		
		if(hosting.getProvidedHost().eContainer() instanceof VM)
		{
			VM vm= (VM) hosting.getProvidedHost().eContainer(); 
			
			logger.debug("DeployementModelParser - defineContainmentDependency - VM type name "+vm.getName()+"!");
			
			List<VirtualMachineProfile> profiles= vmProfiles.get(vm.getName()); 
			
			for(VirtualMachineProfile vmp: profiles)
			{	
			
				 //TODO CREATE A MAPPING FOR THE VM AND THEIR RELATED VM PROFILES
				//VirtualMachineProfile vmp= PaasageModelTool.searchVMProfileById(configuration.getVmProfiles(), vm.getName()); //TODO CHANGED BY VM NAME 
				logger.debug("DeployementModelParser - defineContainmentDependency - Client component "+clientAppComponent+ " Name "+clientId);
				clientAppComponent.getRequiredProfile().add(vmp); 
				
				logger.debug("DeployementModelParser - defineContainmentDependency - VM dependency add between "+clientId+" and "+vmp.getCloudMLId()+"!");
			}	
		}	
		else //I assume that it is a component
		{
			Component provider= (Component) hosting.getProvidedHost().eContainer(); 
			
			ApplicationComponent providerAppComponent= PaasageModelTool.searchApplicationComponentById(configuration.getComponents(), provider.getName()); 
			
			RequiredFeature rf= builProvidedContaimentPortFeature(hosting.getName(), providerAppComponent);
			
			rf.setContaiment(true);
			
			logger.debug("DeployementModelParser - resolveContaimentDependencies - Client component "+clientAppComponent+ " Name "+clientId);
			
			clientAppComponent.getRequiredFeatures().add(rf); 
			
			logger.debug("DeployementModelParser - defineContainmentDependency - Dependency between "+clientId+" and "+provider.getName()+" created!");
		}
	}
	
	/**
	 * Processes hosting dependencies between components in the deployment model
	 * @param hostingRelationships The hosting dependencies  
	 * @param configuration The paasage configuration been created
	 */
	protected void resolveContaimentDependencyInstances(EList<HostingInstance> hostingRelationships, PaasageConfiguration configuration)
	{
		for(HostingInstance hosting: hostingRelationships)
		{
			InternalComponentInstance client= (InternalComponentInstance) hosting.getRequiredHostInstance().eContainer(); 
			
			ApplicationComponent clientAppComponent= PaasageModelTool.searchApplicationComponentById(configuration.getComponents(), client.getName());
			
			if(hosting.getProvidedHostInstance().eContainer() instanceof VMInstance)
			{
				VMInstance vmInstance= (VMInstance) hosting.getProvidedHostInstance().eContainer(); 
				
				logger.debug("DeployementModelParser - resolveContaimentDependencyInstances - VM Instance name "+vmInstance.getName()+"!");
				
				VirtualMachine vm= PaasageModelTool.searchVMById(configuration.getVms(), vmInstance.getName()); 
				
				logger.debug("DeployementModelParser - resolveContaimentDependencyInstances - VM Instance created "+vm);
				logger.debug("DeployementModelParser - resolveContaimentDependencyInstances - Client component "+clientAppComponent+ " Name "+client.getName());
				clientAppComponent.getRequiredProfile().add(vm.getProfile());
				clientAppComponent.setVm(vm);
				
				logger.debug("DeployementModelParser - resolveContaimentDependencyInstances - VM Instance dependency add between "+client.getName()+" and "+vmInstance.getName()+"!");
			}	
			else //I assume that it is a component
			{
				ComponentInstance provider= (ComponentInstance) hosting.getProvidedHostInstance().eContainer(); 
				
				ApplicationComponent providerAppComponent= PaasageModelTool.searchApplicationComponentById(configuration.getComponents(), provider.getName()); 
				
				RequiredFeature rf= builProvidedContaimentPortFeature(hosting.getName(), providerAppComponent);
				
				rf.setContaiment(true);
				
				clientAppComponent.getRequiredFeatures().add(rf); 
				
				logger.debug("DeployementModelParser - resolveContaimentDependencyInstances - Dependency between "+client.getName()+" and "+provider.getName()+" created!");
			}
		}
	}
	
	/**
	 * Processes virtual machines in the camel model
	 * @param vms The list of VMs
	 * @param configurationWrapper Wrapper containing the configuration being created
	 * @param addNewCandidates Boolean value indicating if new candidates should be considered in the creation of the paasage configuration
	 * @param globalVMRequirements Global requirements
	 */
	protected void parseVMs(List<VM> vms, PaaSageConfigurationWrapper configurationWrapper, boolean addNewCandidates, VMRequirementSet globalVMRequirements)
	{
		for(VM vm:vms)
		{
			logger.info("parseVMs -	Parsing VM Type: "+vm.getName());
			
			parseVM(vm, configurationWrapper, addNewCandidates, globalVMRequirements);
				
		}
	}
	
	protected VirtualMachineProfile parseVM(VM vm, PaaSageConfigurationWrapper configurationWrapper, boolean addNewCandidates, VMRequirementSet globalVMRequirements)
	{
		
		PaasageConfiguration configuration= configurationWrapper.getPaasageConfiguration(); 
		
		List<VirtualMachineProfile> profiles= new ArrayList<>();
		
		vmProfiles.put(vm.getName(), profiles);

		logger.info("**			Parsing VM Type: "+vm.getName());
			
		EList<Location> locations=null;  
		String idLocations=	""; 	
		
		
		EList<CloudProvider> providers= null; 
		String idProviders= ""; 
		
		
		QuantitativeHardwareRequirement hardware= null; 
		String hardwareId= ""; 
		
		OSOrImageRequirement osImage= null; 
		String osImageId= ""; 
		
		if(vm.getVmRequirementSet()!=null)
		{
			//Location reqs		
			if(vm.getVmRequirementSet().getLocationRequirement()!=null && vm.getVmRequirementSet().getLocationRequirement().getLocations().size()>0)
			{	
				logger.debug("**			Considering Location requirements");
				locations= vm.getVmRequirementSet().getLocationRequirement().getLocations(); 
				idLocations= vm.getVmRequirementSet().getLocationRequirement().getName(); 
			}
			
			

			//Provider reqs
			if(vm.getVmRequirementSet().getProviderRequirement()!=null && vm.getVmRequirementSet().getProviderRequirement().getProviders().size()>0)
			{
				logger.debug("**			Considering Provider requirements");
				providers= vm.getVmRequirementSet().getProviderRequirement().getProviders(); 
				idProviders= vm.getVmRequirementSet().getProviderRequirement().getName(); 
			}
			
			//Hardware reqs
			if(vm.getVmRequirementSet().getQuantitativeHardwareRequirement()!=null)
			{
				logger.debug("**			Considering Hardware requirements");
				hardware= vm.getVmRequirementSet().getQuantitativeHardwareRequirement(); 
				hardwareId= vm.getVmRequirementSet().getQuantitativeHardwareRequirement().getName(); 
			}
			
			//Os Image reqs
			if(vm.getVmRequirementSet().getOsOrImageRequirement()!=null)
			{
				logger.debug("**			Considering OS-Image requirements");
				osImage= vm.getVmRequirementSet().getOsOrImageRequirement(); 
				osImageId= vm.getVmRequirementSet().getOsOrImageRequirement().getName(); 
			}
			
			//TODO QUALITATIVE HARDWARE ??
		}
		
		if(globalVMRequirements!=null)
		{	
			logger.debug("**			Considering Global requirements");
			//Global Location reqs, only considered if there are not local ones 	
			if(locations==null && globalVMRequirements.getLocationRequirement()!=null && globalVMRequirements.getLocationRequirement().getLocations().size()>0)
			{
				logger.debug("**			Considering Global Location requirement");
				locations= globalVMRequirements.getLocationRequirement().getLocations(); 
				idLocations= globalVMRequirements.getLocationRequirement().getName(); 
			}
		
			//Global Provider reqs, only considered if there are not local ones
			if(providers==null && globalVMRequirements.getProviderRequirement()!=null && globalVMRequirements.getProviderRequirement().getProviders().size()>0)
			{
				providers= globalVMRequirements.getProviderRequirement().getProviders(); 
				idProviders= globalVMRequirements.getProviderRequirement().getName();
			}
			
			//Global Hardware reqs, only considered if there are not local ones
			if(hardware==null && globalVMRequirements.getQuantitativeHardwareRequirement()!=null)
			{
				hardware= globalVMRequirements.getQuantitativeHardwareRequirement(); 
				hardwareId= globalVMRequirements.getQuantitativeHardwareRequirement().getName();
			}
			
			//Global Os Image reqs, only considered if there are not local ones
			if(osImage==null && globalVMRequirements.getOsOrImageRequirement()!=null)
			{
				logger.debug("**			Considering Global OS/Image requirement");
				osImage= globalVMRequirements.getOsOrImageRequirement(); 
				osImageId= globalVMRequirements.getOsOrImageRequirement().getName(); 
			}
		}	
		
		String vmTypeId= null; 
		
		VirtualMachineProfile vmp= null; 
		logger.debug("**			Locations: "+locations);
		if(locations!=null)
		{
			logger.debug("**			Analysing locations: "+locations.size());
			for(Location location:locations)
			{
				if(providers!=null)
				{
					for(CloudProvider provider: providers)
					{
						//VirtualMachineProfile vmp= PaasageModelTool.searchVMProfileById(configuration.getVmProfiles(), vm.getName()); 
						
						vmTypeId= PaasageModelTool.getVMProfileId(location.getId(), provider.getName(), hardwareId, osImageId, vm.getName()); 
						
						vmp= PaasageModelTool.searchVMProfileById(configuration.getVmProfiles(), vmTypeId); 
						
						if(vmp==null)
						{	
							logger.debug("DeployementModelParser - parseVMs - Adding Vm Type "+vmTypeId+"!");
							
							String providerTypeId= provider.getName(); 
							
							ProviderType pt= PaasageModelTool.searchProviderTypeById(providerTypeId, configurationWrapper);
							
							vmp= buildVMProfile(vm,location, pt, hardware, osImage, vmTypeId, configurationWrapper, addNewCandidates); 
							
							if(vmp!=null)
							{	
								configuration.getVmProfiles().add(vmp); 
								
								profiles.add(vmp);
								
							}	
						}
					}
				}
				else
				{
					
					for(ProviderType pt: proxy.getProviderTypes().getTypes())
					{	
						vmTypeId= PaasageModelTool.getVMProfileId(location.getId(),pt.getId(), hardwareId, osImageId, vm.getName()); 
						
						logger.debug("DeployementModelParser - parseVMs - Vm Type Id "+vmTypeId+"!");
						
						vmp= PaasageModelTool.searchVMProfileById(configuration.getVmProfiles(), vmTypeId); 
						
						logger.debug("DeployementModelParser - parseVMs - Vm Type "+vmp+"!");
						
						if(vmp==null)
						{	
							logger.debug("DeployementModelParser - parseVMs - Adding Vm Type "+vmTypeId+" with provider: "+pt.getId());
							//vmp= buildVMProfile(location, null, hardware, osImage, vmTypeId, configurationWrapper, addNewCandidates); 
							
							vmp= buildVMProfile(vm,location, pt, hardware, osImage, vmTypeId, configurationWrapper, addNewCandidates); 
							
							if(vmp!=null)
							{	
								configuration.getVmProfiles().add(vmp);
								profiles.add(vmp);
							}	
						}
					}	
				}
			}
		}
		else if(providers!=null)
		{
			for(CloudProvider provider: providers)
			{
				//VirtualMachineProfile vmp= PaasageModelTool.searchVMProfileById(configuration.getVmProfiles(), vm.getName()); 
				
				vmTypeId= PaasageModelTool.getVMProfileId("", provider.getName(), hardwareId, osImageId, vm.getName()); 
				
				vmp= PaasageModelTool.searchVMProfileById(configuration.getVmProfiles(), vmTypeId); 
				
				if(vmp==null)
				{	
					logger.debug("DeployementModelParser - parseVMs - Adding Vm Type "+vmTypeId+" without location!");
					String providerTypeId= provider.getName(); 
					ProviderType pt= PaasageModelTool.searchProviderTypeById(providerTypeId, configurationWrapper);
					
					vmp= buildVMProfile(vm,null, pt, hardware, osImage, vmTypeId, configurationWrapper, addNewCandidates); 
					
					if(vmp!=null)
					{
						configuration.getVmProfiles().add(vmp);
						profiles.add(vmp);
					}
				}
			}
		}
		else
		{
			logger.debug("**			Creating VM ID ");
			
			for(ProviderType pt:proxy.getProviderTypes().getTypes())
			{	
				vmTypeId= PaasageModelTool.getVMProfileId("", pt.getId(), hardwareId, osImageId, vm.getName()); 
				logger.debug("**			VM ID "+vmTypeId);
				vmp= PaasageModelTool.searchVMProfileById(configuration.getVmProfiles(), vmTypeId); 
				
				if(vmp==null)
				{	
					logger.debug("DeployementModelParser - parseVMs - Adding Vm Type "+vmTypeId+" without location!");
					//vmp= buildVMProfile(null, null, hardware, osImage, vmTypeId, configurationWrapper, addNewCandidates); 
					vmp= buildVMProfile(vm,null, pt, hardware, osImage, vmTypeId, configurationWrapper, addNewCandidates); 
					
					if(vmp!=null)
					{	
						configuration.getVmProfiles().add(vmp); 
						profiles.add(vmp);
					}
				}
			}	
		}
		
		return vmp; 
	}
	
	/**
	 * Processes virtual machines in the camel model
	 * @param vms The list of VMs
	 * @param configurationWrapper Wrapper containing the configuration being created
	 * @param addNewCandidates Boolean value indicating if new candidates should be considered in the creation of the paasage configuration
	 * @param globalVMRequirements Global requirements 
	 */
	protected void parseVMInstances(List<VMInstance> vms, PaaSageConfigurationWrapper configurationWrapper, boolean addNewCandidates, VMRequirementSet globalVMRequirements)
	{
		
		PaasageConfiguration configuration= configurationWrapper.getPaasageConfiguration(); 
		
		for(VMInstance vm:vms)
		{
			logger.info("**			Parsing VM Instance: "+vm.getName());
			logger.debug("DeployementModelParser - parseVMInstances - Adding Vm Instance "+vm.getName()+"!");
			VirtualMachineProfile vmp= PaasageModelTool.searchVMProfileById(configuration.getVmProfiles(), vm.getName()); 
			if(vmp==null)
			{	
				vmp=parseVM((VM)vm.getType(), configurationWrapper, addNewCandidates, globalVMRequirements);
				//buildVMProfile((VM) vm.getType(), configurationWrapper, addNewCandidates); 
				/*if(vmp!=null)
					configuration.getVmProfiles().add(vmp);*/
			}
			
			if(vmp!=null)
			{
				VirtualMachine instance= buildVM(vmp, vm); 
				
				configuration.getVms().add(instance); 
				
				logger.debug("DeployementModelParser - parseVMInstances - Vm Instance "+instance.getId()+" added!");
			}
			

			
			
		}
	}
	
	/*protected VirtualMachineProfile searchVMProfile(VM vm, EList<VirtualMachineProfile> vmps)
	{
		for(VirtualMachineProfile vmp: vmps)
		{
			if(vmp.getCloudMLId().equals(vm.getName()))
			{
				return vmp; 
			}
		}
		
		return null; 
	}*/
	
	public static List<VM> getVMs(EList<Component> components)
	{
		List<VM> vms= new ArrayList<VM>(); 
		for(Component c: components)
		{
			if(c instanceof VM)
				vms.add((VM) c); 
		}
		
		return vms; 
	}
	
	
	protected VirtualMachine buildVM(VirtualMachineProfile vmp, VMInstance vmInstace)
	{
		VirtualMachine virtualMachine= applicationFactory.createVirtualMachine(); 
		
		virtualMachine.setId(vmInstace.getName());
		virtualMachine.setProfile(vmp);
		
		return virtualMachine; 
		
		
	}
	
	//protected VirtualMachineProfile buildVMProfile(VM vm, PaaSageConfigurationWrapper configurationWrapper, boolean addNewCandidates)
	protected VirtualMachineProfile buildVMProfile(VM vm,Location location, ProviderType pt, QuantitativeHardwareRequirement hardware, OSOrImageRequirement osImagerReq, String vmTypeId, PaaSageConfigurationWrapper configurationWrapper, boolean addNewCandidates)
	{
		VirtualMachineProfile vmp= null; 
		
		LocationUpperware locationUpperware= null; 
		

		//Location
		//Location loc= vm.getLocation(); 
		
		logger.debug("DeploymentModelParser- buildVMProfile- The VM type: "+vmTypeId);
		
		if(location!=null)
		{
			logger.debug("DeploymentModelParser- buildVMProfile- The Location name: "+location.getId());
			
			locationUpperware= PaasageModelTool.getLocation(location, configurationWrapper); 
			
		}
		

		
		if(location!=null && locationUpperware==null)
		{
			logger.warn("DeploymentModelParser- - buildVMProfile - The Location "+location.getId()+" does not exist in the DB. TheVM profile can not be created!");
			return null;
		}
		else
			logger.debug("DeploymentModelParser- buildVMProfile- Location found!");
		
		
		vmp= applicationFactory.createVirtualMachineProfile(); 
		vmp.setCloudMLId(vmTypeId);
		vmp.setRelatedCloudVMId(vm.getName());
		
		logger.debug("DeploymentModelParser- VM id "+vmTypeId); 
		
		if(locationUpperware!=null)
			vmp.setLocation(locationUpperware); 
		
		//Provider
		//if(provider!=null)
		//{	
/*			String providerTypeId= provider.getName(); 
			
			ProviderType pt= PaasageModelTool.searchProviderTypeById(providerTypeId, configurationWrapper); */
			
			if(pt!=null)
			{
				String providerTypeId= pt.getId(); 
				PaasageConfiguration configuration= configurationWrapper.getPaasageConfiguration(); 
				
				//Look for the provider
				Provider providerVO=  applicationFactory.createProvider(); 
				providerVO.setType(pt);
				
				Provider providerUpperware= null; 
				
				if(locationUpperware!=null)
				{	
					providerVO.setLocation(locationUpperware);
					providerUpperware= PaasageModelTool.searchProviderWithLocationInList(configuration.getProviders(), providerVO); 
				}
			
				if(providerUpperware==null && !addNewCandidates) //We don't need to create the VM, the related provider is not a candidate
					return null; 
				else if(providerUpperware==null)
				{
					providerUpperware= providerVO; 
					
					String locationId=""; 
					
					if(location!=null)
						locationId= location.getId(); 
					
					
					String providerId= PaasageModelTool.buildProviderId(providerTypeId, locationId); 
					
					providerUpperware.setId(providerId);
					
					configuration.getProviders().add(providerUpperware); 
					
					logger.debug("DeploymentModelParser- buildVMProfile -Provider "+providerId+" created!");
				}
				
				
				
				ProviderDimension pd= applicationFactory.createProviderDimension(); //TODO THE METRIC ID ???
				
				pd.setValue(-1); //TODO THIS VALUE HAS TO BE DEFINED WITH THE PRICE CALCULATION
				
				pd.setProvider(providerUpperware);
				
				vmp.getProviderDimension().add(pd); 
				
			}
			
			else
				logger.error("DeploymentModelProcessor- buildVMProfile- The Provider does not exist in the DB. The VM profile can not be created!");
		//}	
		
		//OS
		if(osImagerReq!=null && osImagerReq instanceof OSRequirement)
		{
			OSRequirement osReq= (OSRequirement) osImagerReq; 
			
			String os= osReq.getOs(); 
			
			OS theOs= PaasageModelTool.getOSFromNameAndArchitecture(os, osReq.isIs64os(), configurationWrapper); 
			
			if(theOs!=null)
			{
				theOs= PaasageModelTool.cloneOS(theOs); 
				vmp.setOs(theOs);
			}	
			else
				logger.warn("DeploymentModelParser- The OS "+os+" does not exist in the DB. The os of the VM with id "+vmTypeId+" will be not set");
		}
		else if(osImagerReq!=null)//Is an image
		{
			ImageRequirement imageReq= (ImageRequirement) osImagerReq; 
			
			String imageId= imageReq.getImageId(); 
			
			ImageUpperware image= ApplicationFactory.eINSTANCE.createImageUpperware(); 
			
			image.setId(imageId);
			vmp.setImage(image);
		}
		

		if(hardware!=null)
		{
			//Cores
			int cores= hardware.getMinCores(); //TODO MAX CORES???
			
			CPU cpu= applicationFactory.createCPU(); 
			
			cpu.setCores(cores); //TODO  MAX_CPU?, ID?
			
			vmp.setCpu(cpu);
			
			DoubleValueUpperware frequencyDouble= typesFactory.createDoubleValueUpperware(); 
			frequencyDouble.setValue(hardware.getMinCPU());
			
			cpu.setValue(frequencyDouble);
			
			//Storage
			
			int disk= hardware.getMinStorage(); //TODO MAX STORAGE??
			
			Storage storage= applicationFactory.createStorage(); 
			
			IntegerValueUpperware diskInt= typesFactory.createIntegerValueUpperware(); 
			
			diskInt.setValue(disk);
			
			storage.setValue(diskInt);
			storage.setUnit(DataUnitEnum.GB); //TODO CHECK THE UNIT, TYPEID? MAX_STORAGE?
			
			vmp.setStorage(storage);
			
			
			//RAM
			int mem= hardware.getMinRAM(); 
			
			Memory memory= applicationFactory.createMemory(); 
			
			IntegerValueUpperware memoryInt= typesFactory.createIntegerValueUpperware(); 
			
			memoryInt.setValue(mem);
			
			memory.setValue(memoryInt);
			
			memory.setUnit(DataUnitEnum.MB); //TODO CHECK THE UNIT, TYPEID? MAX_MEMORY?
			
			vmp.setMemory(memory);
		}
		
		return vmp; 
		
	}
	
	protected List<VirtualMachineProfile> buildVMProfile(VM vm, Location location, QuantitativeHardwareRequirement hardware, OSOrImageRequirement osImagerReq, String vmTypeId, PaaSageConfigurationWrapper configurationWrapper, boolean addNewCandidates)
	{
		List<VirtualMachineProfile> vmps= new ArrayList<>(); 
		//Providers
		List<ProviderType> types= proxy.getProviderTypes().getTypes();
		
		for(ProviderType pt:types)
		{
			VirtualMachineProfile vmp=buildVMProfile(vm, location, pt, hardware, osImagerReq, vmTypeId, configurationWrapper, addNewCandidates); 
			
			vmps.add(vmp); 
		}
		
		
		return vmps; 
		/*VirtualMachineProfile vmp= null; 
		
		LocationUpperware locationUpperware= null; 
		

		//Location
		//Location loc= vm.getLocation(); 
		
		logger.debug("DeploymentModelParser- buildVMProfile- The VM type: "+vmTypeId);
		
		if(location!=null)
		{
			logger.debug("DeploymentModelParser- buildVMProfile- The Location name: "+location.getName());
			
			locationUpperware= PaasageModelTool.getLocation(location, configurationWrapper); 
			
		}
		

		
		if(location!=null && locationUpperware==null)
		{
			logger.warn("DeploymentModelParser- - buildVMProfile - The Location "+location.getName()+" does not exist in the DB. TheVM profile can not be created!");
			return null;
		}
		else
			logger.debug("DeploymentModelParser- buildVMProfile- Location found!");
		
		
		vmp= applicationFactory.createVirtualMachineProfile(); 
		vmp.setCloudMLId(vmTypeId);
		
		logger.debug("DeploymentModelParser- VM id "+vmTypeId); 
		
		if(locationUpperware!=null)
			vmp.setLocation(locationUpperware); 
		
		//Providers
		List<ProviderType> types= proxy.getProviderTypes().getTypes(); 
		
		for(ProviderType pt:types)
		{	
			
			PaasageConfiguration configuration= configurationWrapper.getPaasageConfiguration(); 
			
			//Look for the provider
			Provider providerVO=  applicationFactory.createProvider(); 
			providerVO.setType(pt);
			
			Provider providerUpperware= null; 
			
			if(locationUpperware!=null)
			{	
				providerVO.setLocation(locationUpperware);
				providerUpperware= PaasageModelTool.searchProviderWithLocationInList(configuration.getProviders(), providerVO); 
			}
		
			if(providerUpperware==null && !addNewCandidates) //We don't need to create the VM, the related provider is not a candidate
				return null; 
			else if(providerUpperware==null)
			{
				providerUpperware= providerVO; 
				
				String locationId=""; 
				
				if(location!=null)
					locationId= location.getName(); 
				
				
				String providerId= PaasageModelTool.buildProviderId(pt.getId(), locationId); 
				
				providerUpperware.setId(providerId);
				
				configuration.getProviders().add(providerUpperware); 
				
				logger.debug("DeploymentModelParser- buildVMProfile -Provider "+providerId+" created!");
			}
			
			
			
			ProviderDImension pd= applicationFactory.createProviderDImension(); 
			
			pd.setValue(-1); //TODO THIS VALUE HAS TO BE DEFINED WITH THE PRICE CALCULATION
			
			pd.setProvider(providerUpperware);
			
			vmp.getProviderDimension().add(pd); 
				
		}	
		
		//OS
		if(osImagerReq!=null && osImagerReq instanceof OSRequirement)
		{
			OSRequirement osReq= (OSRequirement) osImagerReq; 
			
			String os= osReq.getOs(); 
			
			OS theOs= PaasageModelTool.getOSFromNameAndArchitecture(os, osReq.isIs64os(), configurationWrapper); 
			
			if(theOs!=null)
				vmp.setOs(theOs);
			else
				logger.warn("DeploymentModelParser- The OS "+os+" does not exist in the DB. The os of the VM with id "+vmTypeId+" will be not set");
		}
		else if(osImagerReq!=null)//Is an image
		{
			ImageRequirement imageReq= (ImageRequirement) osImagerReq; 
			
			String imageId= imageReq.getImageId(); 
			
			ImageUpperware image= ApplicationFactory.eINSTANCE.createImageUpperware(); 
			
			image.setId(imageId);
			vmp.setImage(image);
		}
		

		if(hardware!=null)
		{
			//Cores
			int cores= hardware.getMinCores(); //TODO MAX CORES???
			
			CPU cpu= applicationFactory.createCPU(); 
			
			cpu.setCores(cores); //TODO  MAX_CPU?, ID?
			
			vmp.setCpu(cpu);
			
			DoubleValueUpperware frequencyDouble= typesFactory.createDoubleValueUpperware(); 
			frequencyDouble.setValue(hardware.getMinCPU());
			
			cpu.setValue(frequencyDouble);
			
			//Storage
			
			int disk= hardware.getMinStorage(); //TODO MAX STORAGE??
			
			Storage storage= applicationFactory.createStorage(); 
			
			IntegerValueUpperware diskInt= typesFactory.createIntegerValueUpperware(); 
			
			diskInt.setValue(disk);
			
			storage.setValue(diskInt);
			storage.setUnit(DataUnitEnum.GB); //TODO CHECK THE UNIT, TYPEID? MAX_STORAGE?
			
			vmp.setStorage(storage);
			
			
			//RAM
			int mem= hardware.getMinRAM(); 
			
			Memory memory= applicationFactory.createMemory(); 
			
			IntegerValueUpperware memoryInt= typesFactory.createIntegerValueUpperware(); 
			
			memoryInt.setValue(mem);
			
			memory.setValue(memoryInt);
			
			memory.setUnit(DataUnitEnum.MB); //TODO CHECK THE UNIT, TYPEID? MAX_MEMORY?
			
			vmp.setMemory(memory);
		}
		
		return vmp; */
		
	}	
	
	
	/**
	 * Processes component instances in the deployment model. 
	 * @param components List of component instances
	 * @param configuration The paasage configuration
	 */
	protected void parseComponentInstances(List<ComponentInstance> components, PaasageConfiguration configuration)
	{
		logger.debug("DeploymentModelProcessor - parseComponentInstances - component instace size "+components.size());
		for(ComponentInstance component:components)
		{	

			logger.info("**			Parsing component instance: "+component.getName());
			logger.debug("DeploymentModelProcessor - parseComponentInstances - components name "+component.getName());
			
			ApplicationComponent apc= buildApplicationComponent(component); 
				
			configuration.getComponents().add(apc); 
			
		}	
	}
	
	
	/**
	 * Processes internal and external components in the deployment model. 
	 * @param components List of components
	 * @param instances list of component instances
	 * @param requirements The requirments
	 * @param configuration The paasage configuration
	 */
	protected void parseComponents(List<Component> components, List<ComponentInstance> instances, PaasageConfiguration configuration, RequirementModel requirements)
	{
		logger.debug("DeploymentModelProcessor - parseComponents - components size "+components.size());
		for(Component component:components)
		{
			//Only processes the Component if there are not instances
			if(!PaasageModelTool.existComponentInstance(component, instances))
			{	
				if(!(component instanceof VM))
				{	
					logger.info("**			Parsing component: "+component.getName());
					logger.debug("DeploymentModelProcessor - parseComponents - components name "+component.getName());
					
					ApplicationComponent apc= buildApplicationComponent(component, requirements); 
						
					configuration.getComponents().add(apc); 
				
				}
			}	
		}	
	}
	
		
	/**
	 * Processes internal components in the deployment model
	 * @param components The list of internal components to be processed
	 * @param configuration The paasage configuration being created
	 * @param requirements The requirement model
	 */
	protected void parseInternalComponents(EList<InternalComponent> components, PaasageConfiguration configuration, RequirementModel requirements)
	{
		logger.debug("DeploymentModelProcessor - parseInternalComponents - internalComponents size "+components.size());
		for(InternalComponent component:components)
		{	
			logger.debug("DeploymentModelProcessor - parseInternalComponents - internalComponents name "+component.getName());
			
			ApplicationComponent apc= buildApplicationComponent(component, requirements); 
				
			configuration.getComponents().add(apc); 
		}	
	}
	
	
	/**
	 * Creates an application component from a camel component
	 * @param component The camel component
	 * @param requirements The requirements 
	 * @return The application component
	 */
	protected ApplicationComponent buildApplicationComponent(Component component, RequirementModel requirements)
	{
		ApplicationComponent apc= applicationFactory.createApplicationComponent(); 
		logger.debug("DeploymentModelParser- buildApplicationComponent- The component "+component.getName());
		String id= component.getName(); 
		
		apc.setCloudMLId(id);

		apc.setMin(MIN_INSTANCE_NUMBER);
		
		apc.setMax(MAX_INSTANCE_NUMBER);
		
		HorizontalScaleRequirement horScaleReq= PaasageModelTool.getScaleRequirementForComponent(requirements.getRequirements(), component); 
		
		if(horScaleReq!=null)
		{
			apc.setMin(horScaleReq.getMinInstances());
			
			apc.setMax(horScaleReq.getMaxInstances());
		}
		
		
		for(ProvidedCommunication pc:component.getProvidedCommunications())
		{
			apc.getFeatures().add(pc.getName()); 
		}
		
		
		for(ProvidedHost ph:component.getProvidedHosts())
		{
			apc.getFeatures().add(ph.getName());
		}
				
		return apc; 
	}
	
	/**
	 * Creates an application component from a camel component
	 * @param component The camel component
	 * @return The application component
	 */
	protected ApplicationComponent buildApplicationComponent(ComponentInstance component)
	{
		ApplicationComponent apc= applicationFactory.createApplicationComponent(); 
		logger.debug("DeploymentModelParser- buildApplicationComponent- The component instance "+component.getName());
		String id= component.getName(); 
		
		apc.setCloudMLId(id);
		
				
		for(ProvidedCommunicationInstance pc:component.getProvidedCommunicationInstances())
		{
			apc.getFeatures().add(pc.getName()); 
		}
		
		
		for(ProvidedHostInstance ph:component.getProvidedHostInstances())
		{
			apc.getFeatures().add(ph.getName());
		}
				
		return apc; 
	}
	
	/**
	 * Build a required feature by using a required communication camel dependency 
	 * @param port The required communication 
	 * @param providedBy The Cloud element providing the required port
	 * @param isLocal Indicates if the communication is local
	 * @return The required feature
	 */
	protected RequiredFeature buildRequiredCommunicationPortFeature(RequiredCommunication port, CloudMLElementUpperware providedBy, boolean isLocal)
	{
		
		RequiredFeature rf= applicationFactory.createRequiredFeature(); 
		
		String id= port.getName(); 
		
		rf.setFeature(id);
		
		rf.setProvidedBy(providedBy);
					
		//boolean isLocal= port.isIsLocal(); 
		//rf.setRemote(!isLocal);
		rf.setRemote(!isLocal);//TODO VERIFY THIS VALUE
			
		rf.setOptional(!port.isIsMandatory());
		
		return rf; 
		
	}
	
	protected RequiredFeature buildRequiredCommunicationInstancePortFeature(RequiredCommunicationInstance port, CloudMLElementUpperware providedBy)
	{
		
		RequiredFeature rf= applicationFactory.createRequiredFeature(); 
		
		String id= port.getName(); 
		
		rf.setFeature(id);
		
		rf.setProvidedBy(providedBy);
					
		//boolean isLocal= port.getType().isIsLocal(); 
		//rf.setRemote(!isLocal);
		rf.setRemote(true); //TODO TO CHECK THIS
			
		rf.setOptional(!((RequiredCommunication)port.getType()).isIsMandatory());
		
		return rf; 
		
	}
	
	/**
	 * Build a required feature by using a containment relation id 
	 * @param contaimentRelationId The containment id
	 * @param providedBy The Cloud element that is host
	 * @return The required feature
	 */
	protected RequiredFeature builProvidedContaimentPortFeature(String contaimentRelationId, CloudMLElementUpperware providedBy)
	{
		RequiredFeature rf= applicationFactory.createRequiredFeature(); 
		
		rf.setFeature(contaimentRelationId);
		
		rf.setProvidedBy(providedBy);
					
		rf.setRemote(false);
		
		rf.setOptional(false);
					
		return rf; 
		
	}
	
	/**
	 * Returns the list of VMs from a given deployment model
	 * @param dm The deployment model
	 * @return The list of VMs
	 */
	public static List<VM> getVMList(DeploymentModel dm)
	{
		List<VM> vms= new ArrayList<VM>(); 
		
		vms.addAll(dm.getVms()); 
		
		//for(Component c: dm.getVms())
		//{
			//if(c instanceof VM)
		//		vms.add((VM) c); 
		//}
		
		/*for(Component c: dm.getExternalComponents())
		{
			if(c instanceof VM)
				vms.add((VM) c); 
		}*/
		
		return vms; 
		
	}
	

	
	
	/**
	 * Returns the list of internal components from a given deployment model
	 * @param dm The deployment model 
	 * @return The list of internal components 
	 */
	protected List<InternalComponent> getInternalComponentsList(DeploymentModel dm)
	{
		List<InternalComponent> components= new ArrayList<InternalComponent>(); 
		
		components.addAll(dm.getInternalComponents()); 
		
		/*for(Component c: dm.getComponents())
		{
			if(c instanceof InternalComponent)
				components.add((InternalComponent) c); 
		}*/
		
		return components; 
		
	}
	
	protected void checkGivenSolutionByUser(DeploymentModel pim, PaaSageConfigurationWrapper configurationWrapper)
	{
		logger.debug("DeploymentModelParser - checkGivenSolutionByUser - Checking solution");
		List<Component> componentTypes= PaasageModelTool.getComponentsList(pim); 
		List<ComponentInstance> componentInstances= PaasageModelTool.getComponentInstancesList(pim);
		
		boolean instacesOk= true; 
		
		for(int i=0; i<componentTypes.size() && instacesOk; i++) //Check that there is at least one instance by component type
		{
			if(!PaasageModelTool.existComponentInstance(componentTypes.get(i), componentInstances))
			{	
				logger.debug("DeploymentModelParser - checkGivenSolutionByUser - The component type "+componentTypes.get(i).getName()+" does not have a related instance");
				instacesOk= false; 
				
			}	
		}
		
		if(instacesOk) //Check if there is hosting relationship by each instance
		{
			EList<HostingInstance> hostingInstances= pim.getHostingInstances();
			boolean hostingOk= true; 
			
			for(int i=0; i<componentInstances.size() && hostingOk; i++)
			{
				if(!PaasageModelTool.existHostingInstanceForComponentInstance(hostingInstances, componentInstances.get(i)))
				{	
					logger.debug("DeploymentModelParser - checkGivenSolutionByUser - The component type "+componentTypes.get(i).getName()+" does not have a related hosting instance");
					hostingOk= false; 
				}	
			}
			
			configurationWrapper.setHasUserSolution(hostingOk);
		}
		
	}
	
	
	public void checkExistencyOfValidUserSolution(DeploymentModel pim, PaaSageConfigurationWrapper configurationWrapper)
	{
		if(configurationWrapper.hasUserSolution())
		{	
			EList<VMInstance> vmInstances= pim.getVmInstances(); 
			
			EList<Provider> providers= configurationWrapper.getPaasageConfiguration().getProviders(); 
			
			boolean providersOk=true; 
			
			for(int i=0; vmInstances.size()>i && providersOk; i++)
			{
				VM vm= (VM) vmInstances.get(i).getType();
								
				if(vm.getVmRequirementSet().getProviderRequirement()!=null && !PaasageModelTool.existProviderOfVMInList(vmInstances.get(i), providers))
					providersOk= false; 
			}
			
			configurationWrapper.setValidUserSolution(providersOk);
		}	
		
	}
	
	public void checkCorrectHostingRelationships(DeploymentModel pim, PaaSageConfigurationWrapper configurationWrapper)
	{
		logger.debug("DeploymentModelParser - checkCorrectHostingRelationships - Checking hosting relationships");
		List<Component> componentTypes= PaasageModelTool.getComponentsList(pim); 
		EList<Hosting> hostingRelationships= pim.getHostings(); 
		
		boolean relsOk=true; 
		
		for(Component ct: componentTypes)
		{
			boolean hostingOk= false; 
			
			for(int i=0; i<hostingRelationships.size() && !hostingOk; i++)
			{
				Hosting hosting= hostingRelationships.get(i); 
				
				if(((Component)hosting.getRequiredHost().eContainer()).getName().equals(ct.getName()))
				{	
					hostingOk=true; 
					logger.debug("DeploymentModelParser - checkCorrectHostingRelationships - There is at least a hosting relationship for "+ct.getName()+" component");
				}	
			}
			
			if(!hostingOk)
			{
				logger.error("DeploymentModelParser - checkCorrectHostingRelationships - There is not hosting relationship for "+ct.getName()+" component");
				relsOk= false; 
			}
		}
		
		
		configurationWrapper.setHasCorrectHostingRelationships(relsOk);
	}

}
