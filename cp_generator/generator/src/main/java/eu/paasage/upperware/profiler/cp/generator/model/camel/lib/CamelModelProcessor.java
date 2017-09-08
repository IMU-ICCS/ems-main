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

import java.util.*;

import eu.paasage.camel.metric.Property;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.VM;
import eu.paasage.camel.deployment.VMRequirementSet;
import eu.paasage.camel.location.Location;
import eu.paasage.camel.organisation.CloudProvider;
import eu.paasage.camel.provider.Attribute;
import eu.paasage.camel.provider.Feature;
import eu.paasage.camel.requirement.ImageRequirement;
import eu.paasage.camel.requirement.LocationRequirement;
import eu.paasage.camel.requirement.OSOrImageRequirement;
import eu.paasage.camel.requirement.OSRequirement;
import eu.paasage.camel.requirement.OptimisationFunctionType;
import eu.paasage.camel.requirement.OptimisationRequirement;
import eu.paasage.camel.requirement.ProviderRequirement;
import eu.paasage.camel.requirement.QuantitativeHardwareRequirement;
import eu.paasage.camel.requirement.Requirement;
import eu.paasage.camel.requirement.RequirementModel;
import eu.paasage.camel.unit.UnitType;
import eu.paasage.upperware.metamodel.application.ApplicationComponent;
import eu.paasage.upperware.metamodel.application.ApplicationFactory;
import eu.paasage.upperware.metamodel.application.ComponentMetricRelationship;
import eu.paasage.upperware.metamodel.application.PaaSageGoal;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.application.Provider;
import eu.paasage.upperware.metamodel.cp.GoalOperatorEnum;
import eu.paasage.upperware.metamodel.types.typesPaasage.FunctionType;
import eu.paasage.upperware.profiler.cp.generator.db.api.IDatabaseProxy;
import eu.paasage.upperware.profiler.cp.generator.db.lib.CDODatabaseProxy;
import eu.paasage.upperware.profiler.cp.generator.model.lib.GenerationOrchestrator;
import eu.paasage.upperware.profiler.cp.generator.model.lib.PaaSageConfigurationWrapper;
import eu.paasage.upperware.profiler.cp.generator.model.tools.PaasageModelTool;
import fr.inria.paasage.saloon.camel.ProviderModelDecorator;
import fr.inria.paasage.saloon.camel.SaloonCamelSolver;
import fr.inria.paasage.saloon.camel.ontology.BoundedElementCamel;
import fr.inria.paasage.saloon.camel.ontology.ConceptCamel;
import fr.inria.paasage.saloon.camel.ontology.OntologyCamel;
import fr.inria.paasage.saloon.camel.ontology.QuantifiableBoundedElementCamel;

/**
 * This class offers the functionality to deal with camel models
 * @author danielromero
 *
 */
@Slf4j
public class CamelModelProcessor {

	public static final String ATTRIBUTE = "Attribute";

	/*
	 * ATTRIBUTES
	 */
	
	/*
	 * The camel model being processed 
	 */
	protected CamelModel model; 
	
	/*
	 * Parser of the camel models
	 */
	protected DeploymentModelParser deploymentModelParser; 
	
	/*
	 * Parser of the feature models
	 */
	protected ProviderModelParser providerModelParser; 
	
	/*
	 * Proxy of the Database
	 */
	protected static IDatabaseProxy proxy= CDODatabaseProxy.getInstance();
	
	/*
	 * The valid value
	 */
	protected boolean valid;

	/*
	 * CONSTRUCTOR
	 */
	
	/**
	 * Builds a camel model processor that will deal with the given model
	 * @param model The camel model to be processed
	 */
	public CamelModelProcessor(CamelModel model) {
		this.model= model; 
		this.deploymentModelParser= new DeploymentModelParser();
		this.providerModelParser= new ProviderModelParser();
	}
	
	
	/**
	 * Gets elements from {@link #model} and fills the specified pc.
	 * @param pc The PaaSage Configuration to be filled
	 * @param preferedProviders
	 */
	public void parseModel(PaaSageConfigurationWrapper pc, Set<String> preferedProviders)
	{
		
		log.debug("CamelModelProcessor - parseModel - Calling DeploymentModelParser!");
		log.info(" ** 	Calling DeploymentModelParser");
		deploymentModelParser.parsePIM(model.getDeploymentModels().get(0), pc);
		
		//Map<String,List<VirtualMachineProfile>> vmProfiles= deploymentModelParser.getVmProfiles(); 
		
		List<VM> vms=  DeploymentModelParser.getVMList(model.getDeploymentModels().get(0));
		log.info(" ** 	Processing Opt Rerqs");
		parseOptimisationRequirements(pc);
		log.info(" ** 	Processing Opt Rerqs ended");
		VMRequirementSet globalRequirements= model.getDeploymentModels().get(0).getGlobalVMRequirementSet(); 
		
		
		List<Provider> candidates= new ArrayList<Provider>();
		
		for(VM vm: vms) {
			log.debug("CamelModelProcessor - parseModel - Processing vm "+vm.getName());
			//Create an ontology representing the requirements of each VM
			OntologyCamel ontology= proxy.getCamelOntologyCopy(); 
			
			log.debug("CamelModelProcessor - parseModel - Ontology Retrieved! ");

			//Units

			//ConceptCamel mghzUnit= ProviderModelParser.getConceptByName(fr.inria.paasage.saloon.camel.tool.Constants.GHZ_UNIT, ontology.getReusedConcept());
			
			log.debug("CamelModelProcessor - parseModel - Unit concepts Retrieved! ");



			QuantitativeHardwareRequirement hardware = extractHardwareRequirement(globalRequirements, vm.getVmRequirementSet());

			log.debug("CamelModelProcessor - parseModel - Hardware reqs: "+hardware);
			if(hardware!=null) {
				checkHardware(ontology, hardware);
			}
				
			//Criteria
			
/*			ConceptCamel criteriaConcept= ProviderModelParser.getConceptByName("Cost", ontology.getConcepts()); 
			criteriaConcept.setSelected(true);
			
			//Goal
			ConceptCamel goalConcept= ProviderModelParser.getConceptByName("Min", ontology.getConcepts()); 
			goalConcept.setSelected(true);
			log.debug("CamelModelProcessor - parseModel - goal selected "+goalConcept.isSelected());*/ //TODO TO CHECK THIS
			
			
			//OS-Image

			//Provider with Image
			ProviderModelDecorator pmWithImage= null;

			OSOrImageRequirement osImageReq = extractOsRequirement(globalRequirements, vm.getVmRequirementSet());
			log.debug("CamelModelProcessor - parseModel - OsImage reqs: "+osImageReq);
			if(osImageReq!=null) {
				pmWithImage = checkOS(ontology, osImageReq);
			}
			
			//Provider
			ProviderRequirement provReq = extractProviderRequirement(globalRequirements, vm.getVmRequirementSet());
			log.debug("CamelModelProcessor - parseModel - Provider reqs: "+provReq);
			
			//Location
			ConceptCamel locationConcept= ProviderModelParser.getConceptByName("Location",  ontology.getConcepts());

			LocationRequirement locationReq = extractLocationRequirement(globalRequirements, vm.getVmRequirementSet());
			log.debug("CamelModelProcessor - parseModel - Location reqs: "+locationReq);
			
			if(locationReq!=null) {
				for(Location loc:locationReq.getLocations()) {
					log.debug("CamelModelProcessor - parseModel - Looking for loc: "+loc.getId());
					ConceptCamel concreteLocationConcept= ProviderModelParser.searchLocation(loc.getId(), locationConcept.getSubConcept());
					log.debug("CamelModelProcessor - parseModel - Loc concept: "+concreteLocationConcept);
					concreteLocationConcept.setSelected(true);
					
					
					log.debug("CamelModelProcessor - parseModel - Loc concept: "+loc.getId()+ " selected!");
					//If there is a Image requirement and the related provider was found, this is the only provider that will be considered
					if(pmWithImage!=null)
					{
						List<Provider> currentCandidates= new ArrayList<>(); 
						log.debug("CamelModelProcessor - parseModel - parseOntology with image");
						providerModelParser.parseOntology(ontology, pc, pmWithImage, vm, currentCandidates); //TODO CLEAN OR RELOAD THE PROVIDER MODELS ?????
						log.debug("CamelModelProcessor - parseModel - parseOntology with image ended");
						
						if(currentCandidates.isEmpty()) //Delete the provider with the given location
						{
							log.debug("CamelModelProcessor - parseModel - Removing candidate with location "+concreteLocationConcept.getName());
							providerModelParser.removeCandidatesWithLocationForVM(vm,pmWithImage.getProviderId(),concreteLocationConcept.getName(),pc);
						}
						else
							candidates.addAll(currentCandidates);
						
					}
					else if(provReq!=null) //If there are provider requirements, only the specified providers are considered  
					{
						log.debug("CamelModelProcessor - parseModel - processProviderRequirements");
						processProviderRequirementsLocation(provReq, ontology, pc, vm, candidates, concreteLocationConcept.getName());
						log.debug("CamelModelProcessor - parseModel - processProviderRequirements ended");
					}
					else //All the providers have to be considered
					{
						log.debug("CamelModelProcessor - parseModel - processAllProviders with loc");
						processAllProvidersLocation(ontology, pc, vm, candidates,concreteLocationConcept.getName());
						log.debug("CamelModelProcessor - parseModel - processAllProviders with loc ended");
					}
					
					concreteLocationConcept.setSelected(false);
					
/*					log.debug("CamelModelProcessor - parseModel - Current candidates size for VM "+vm.getName()+" is "+currentCandidates.size());
					if(currentCandidates.isEmpty()) //Delete the provider with the given location
					{
						log.debug("CamelModelProcessor - parseModel - Removing candidate with location "+concreteLocationConcept.getName());
						providerModelParser.removeCandidatesWithLocationForVM(vm,concreteLocationConcept.getName(),pc);
					}
					else
						candidates.addAll(currentCandidates); */
				}
			
			}
			else if(pmWithImage!=null) //We only consider the provider with the related image
			{
				providerModelParser.parseOntology(ontology, pc, pmWithImage, vm,candidates); //TODO CLEAN OR RELOAD THE PROVIDER MODELS ?????
			}
			else if(provReq!=null) //We only consider the specified providers  
			{
				processProviderRequirements(provReq, ontology, pc, vm, candidates);
			}
			else //We have to process all the providers
			{
				log.debug("CamelModelProcessor - parseModel - processAllProviders");
			
				processAllProviders(ontology, pc, vm, candidates);
			}
							
		}

		providerModelParser.removeNotPreferedProviders(preferedProviders, pc.getPaasageConfiguration());

		providerModelParser.removeNoCandidateProviders(pc.getPaasageConfiguration(), candidates);
		
		providerModelParser.checkExistSolution(pc);
		
		log.debug("CamelModelProcessor - parseModel - Checking solution existency ");
		deploymentModelParser.checkExistencyOfValidUserSolution(model.getDeploymentModels().get(0), pc);
		log.debug("CamelModelProcessor - parseModel - Checking solution existency ended ");
		
		log.debug("CamelModelProcessor - parseModel - Checking hosting relationships existency ");
		deploymentModelParser.checkCorrectHostingRelationships(model.getDeploymentModels().get(0), pc);
		log.debug("CamelModelProcessor - parseModel - Checking hosting relationships existency ended ");

		
	}

	private LocationRequirement extractLocationRequirement(VMRequirementSet globalRequirements, VMRequirementSet vmRequirementSet) {
		LocationRequirement locationReq= vmRequirementSet.getLocationRequirement();
		if(locationReq==null && globalRequirements!=null) {
            locationReq= globalRequirements.getLocationRequirement();
        }
		return locationReq;
	}

	private ProviderRequirement extractProviderRequirement(VMRequirementSet globalRequirements, VMRequirementSet vmRequirementSet) {
		ProviderRequirement provReq= vmRequirementSet.getProviderRequirement();
		if(provReq==null && globalRequirements!=null) {
            provReq= globalRequirements.getProviderRequirement();
        }
		return provReq;
	}

	private ProviderModelDecorator checkOS(OntologyCamel ontology, OSOrImageRequirement osImageReq) {
		ProviderModelDecorator result = null;
		if(osImageReq instanceof OSRequirement) {
            log.debug("CamelModelProcessor - parseModel - Dealing with OS Requirement");
            OSRequirement osReq= (OSRequirement) osImageReq;
            //TODO UPDATE THE ONTOLOGY WITH THE CORRECT NAMES
            ConceptCamel osRootConcept= ProviderModelParser.getConceptByName("OS", ontology.getConcepts());

            if(osRootConcept==null)
                osRootConcept= ProviderModelParser.getConceptByName("Os", ontology.getConcepts());

            if(osRootConcept==null)
                osRootConcept= ProviderModelParser.getConceptByName("os", ontology.getConcepts());

            if(osRootConcept!=null) {
                ConceptCamel osConcept= ProviderModelParser.getConcepContainingName(osReq.getOs(), osRootConcept.getSubConcept());
                if(osConcept!=null) {
                    log.debug("CamelModelProcessor - parseModel - OS concept retrieved "+osConcept+ " Name "+osReq.getOs());
                    osConcept.setSelected(true);
                }
            }
        } else {
            ImageRequirement imgReq= (ImageRequirement) osImageReq;
            String imageId= imgReq.getImageId();

            for(String key: proxy.getPMsMap().keySet()) {
                ProviderModelDecorator pm= proxy.getPMsMap().get(key);
                Feature root= pm.getRootFeature();

                Attribute att= SaloonCamelSolver.getEnumAttributeWithValueInDomain(root, imageId);
                if(att!=null) {
                    result = pm;
                }
            }
        }
		return result;
	}

	private OSOrImageRequirement extractOsRequirement(VMRequirementSet globalRequirements, VMRequirementSet vmRequirementSet) {
		OSOrImageRequirement osImageReq= vmRequirementSet.getOsOrImageRequirement();
		if(osImageReq==null && globalRequirements!=null) {
            log.debug("CamelModelProcessor - parseModel - Considering OsImage global reqs! ");
            osImageReq= globalRequirements.getOsOrImageRequirement();
        }
		return osImageReq;
	}

	private QuantitativeHardwareRequirement extractHardwareRequirement(VMRequirementSet globalRequirements, VMRequirementSet vmRequirementSet) {
		QuantitativeHardwareRequirement hardware= vmRequirementSet.getQuantitativeHardwareRequirement();
		log.debug("CamelModelProcessor - parseModel - Hardware reqs Retrieved! ");
		if(hardware==null && globalRequirements!=null) {
            log.debug("CamelModelProcessor - parseModel - Considering global hardware reqs! ");
            hardware= globalRequirements.getQuantitativeHardwareRequirement();
        }
		return hardware;
	}

	private void checkHardware(OntologyCamel ontology, QuantitativeHardwareRequirement hardware) {
		checkStorage(ontology, hardware);
		checkCores(ontology, hardware);
		checkCPU(ontology, hardware);
		checkRAM(ontology, hardware);
	}

	private void checkRAM(OntologyCamel ontology, QuantitativeHardwareRequirement hardware) {
		if(hardware.getMinRAM()!=0 || hardware.getMaxRAM()!=0) {
            QuantifiableBoundedElementCamel ramConcept= (QuantifiableBoundedElementCamel) ProviderModelParser.getConceptByName("Memory", ontology.getConcepts());
            log.debug("CamelModelProcessor - parseModel - Memory concept retrieved!");
            ramConcept.setSelected(true);

            ramConcept.setUnit(ProviderModelParser.getConceptByName(UnitType.MEGABYTES.getLiteral(), ontology.getReusedConcept()));
			log.debug("CamelModelProcessor - parseModel - Core Number selected! ");
			log.debug("CamelModelProcessor - parseModel - Memory unit: "+ramConcept.getUnit());
			log.debug("CamelModelProcessor - parseModel - Min Memory: "+hardware.getMinRAM());
			log.debug("CamelModelProcessor - parseModel - Max Memory: "+hardware.getMaxRAM());

			ramConcept.setMinValue(hardware.getMinRAM());
			ramConcept.setMaxValue(hardware.getMaxRAM());
            log.debug("CamelModelProcessor - parseModel - vm min ram "+hardware.getMinRAM()+", Memory concept value: "+ramConcept.getValue());
        }
	}

	private void checkCPU(OntologyCamel ontology, QuantitativeHardwareRequirement hardware) {
		if(hardware.getMaxCPU()!=0 || hardware.getMinCPU()!=0) {
            //DO NOTHING FOR THE TIME BEING. THE CODE WORKS BUT PROVIDER MODELS HAVE TO INCLUDE THE FREQUENCY INFOMRATION
/*					QuantifiableElementCamel cpuConcept= (QuantifiableElementCamel) ProviderModelParser.getConceptByName("CPU", ontology.getConcepts());

            log.debug("CamelModelProcessor - parseModel - CPU concept retrieved!");
            cpuConcept.setSelected(true);
            cpuConcept.setUnit(mghzUnit);
            log.debug("CamelModelProcessor - parseModel - CPU concept unit "+cpuConcept.getUnit().getName());
            if(hardware.getMinCPU()!=0) //TODO TO USE BOUNDED ELEMENT
                cpuConcept.setValue((float) hardware.getMinCPU());
            else
                cpuConcept.setValue((float) hardware.getMaxCPU());

            log.debug("CamelModelProcessor - parseModel - CPU frequency defined in ontology! ");*/
        }
	}

	private void checkCores(OntologyCamel ontology, QuantitativeHardwareRequirement hardware) {
		if(hardware.getMinCores()!=0 || hardware.getMaxCores()!=0) {
            BoundedElementCamel coreConcept= (BoundedElementCamel) ProviderModelParser.getConceptByName("Core Number", ontology.getConcepts());
            log.debug("CamelModelProcessor - parseModel - Core Number concept retrieved! "+coreConcept);
            coreConcept.setSelected(true);

            log.debug("CamelModelProcessor - parseModel - Core Number selected! ");
            log.debug("CamelModelProcessor - parseModel - Min Core Number: "+hardware.getMinCores());
            log.debug("CamelModelProcessor - parseModel - Max Core Number: "+hardware.getMaxCores());

            coreConcept.setMinValue(hardware.getMinCores());
            coreConcept.setMaxValue(hardware.getMaxCores());

            log.debug("CamelModelProcessor - parseModel - number of cores defined in ontology! ");
        }
	}

	private void checkStorage(OntologyCamel ontology, QuantitativeHardwareRequirement hardware) {
		if(hardware.getMaxStorage()!=0 || hardware.getMinStorage()!=0) {
            QuantifiableBoundedElementCamel storageConcept= (QuantifiableBoundedElementCamel) ProviderModelParser.getConceptByName("Disk", ontology.getConcepts());
            log.debug("CamelModelProcessor - parseModel - Disk concept retrieved!");
            storageConcept.setSelected(true);

            storageConcept.setUnit(ProviderModelParser.getConceptByName(UnitType.GIGABYTES.getLiteral(), ontology.getReusedConcept()));
			log.debug("CamelModelProcessor - parseModel - Storage selected! ");
			log.debug("CamelModelProcessor - parseModel - Disk unit "+storageConcept.getUnit().getName());
			log.debug("CamelModelProcessor - parseModel - Min Storage: "+hardware.getMinStorage());
			log.debug("CamelModelProcessor - parseModel - Max Storage: "+hardware.getMaxStorage());

            storageConcept.setMinValue(hardware.getMinStorage());
            storageConcept.setMaxValue(hardware.getMaxStorage());
            log.debug("CamelModelProcessor - parseModel - storage defined in ontology! ");
        }
	}

	protected void processProviderRequirements(ProviderRequirement provReq, OntologyCamel ontology, PaaSageConfigurationWrapper pc, VM vm, List<Provider> candidates)
	{
		for(CloudProvider prov:provReq.getProviders())
		{
			ProviderModelDecorator pm= proxy.getPMsMap().get(prov.getName()); 
			
			if(pm!=null)
				providerModelParser.parseOntology(ontology, pc, pm, vm, candidates);
			else
				log.error("CamelModelProcessor - processProviderRequirements - Thre is not a Provider Model for "+prov.getName()+ "The provider will be not considered");
		}
	}
	
	protected void processProviderRequirementsLocation(ProviderRequirement provReq, OntologyCamel ontology, PaaSageConfigurationWrapper pc, VM vm, List<Provider> candidates, String locationId)
	{
		for(CloudProvider prov:provReq.getProviders())
		{
			List<Provider> currentCandidates= new ArrayList<Provider>(); 
			
			ProviderModelDecorator pm= proxy.getPMsMap().get(prov.getName()); 
			
			if(pm!=null)
			{
				providerModelParser.parseOntology(ontology, pc, pm, vm, currentCandidates);
				
				log.debug("CamelModelProcessor - processProviderRequirementsLocation - Current candidates size for VM "+vm.getName()+" is "+currentCandidates.size());
				
				if(currentCandidates.isEmpty()) //Delete the provider with the given location
				{
					log.debug("CamelModelProcessor - processProviderRequirementsLocation - Removing candidate with location "+locationId);
					providerModelParser.removeCandidatesWithLocationForVM(vm,pm.getProviderId(),locationId,pc);
				}
				else
					candidates.addAll(currentCandidates); 
			}
			else
				log.error("CamelModelProcessor - processProviderRequirementsLocation - Thre is not a Provider Model for "+prov.getName()+ "The provider will be not considered");
		}
	}
	
	protected void processAllProviders(final OntologyCamel ontology, PaaSageConfigurationWrapper pc, VM vm, List<Provider> candidates)
	{
		log.debug("CamelModelProcessor - processAllProviders - Processing ontology ");
		for(String key: proxy.getPMsMap().keySet()) 
		{

			ProviderModelDecorator pm= proxy.getPMsMap().get(key); 
			log.debug("CamelModelProcessor - processAllProviders - Selected concepts size "+ProviderModelParser.getSelectedConcepts(ontology.getConcepts()).size());
			providerModelParser.parseOntology(ontology, pc, pm, vm, candidates);
		}
		
		log.debug("CamelModelProcessor - processAllProviders - Ended ");
	}

	
	protected void processAllProvidersLocation(final OntologyCamel ontology, PaaSageConfigurationWrapper pc, VM vm, List<Provider> candidates, String locationId)
	{
		log.debug("CamelModelProcessor - processAllProvidersLocation - Processing ontology ");
		for(String key: proxy.getPMsMap().keySet()) 
		{
			
			List<Provider> currentCandidates= new ArrayList<Provider>(); 
			
			ProviderModelDecorator pm= proxy.getPMsMap().get(key); 
			log.debug("CamelModelProcessor - processAllProvidersLocation - Selected concepts size "+ProviderModelParser.getSelectedConcepts(ontology.getConcepts()).size());
			providerModelParser.parseOntology(ontology, pc, pm, vm, currentCandidates);
			
			log.debug("CamelModelProcessor - processAllProvidersLocation - Current candidates size for VM "+vm.getName()+" is "+currentCandidates.size());
			
			if(currentCandidates.isEmpty()) //Delete the provider with the given location
			{
				log.debug("CamelModelProcessor - processAllProvidersLocation - Removing candidate with location "+locationId);
				providerModelParser.removeCandidatesWithLocationForVM(vm,pm.getProviderId(),locationId,pc);
			}
			else
				candidates.addAll(currentCandidates); 
		}
		
		log.debug("CamelModelProcessor - processAllProvidersLocation - Ended ");
	}
	
	protected void parseOptimisationRequirements(PaaSageConfigurationWrapper pc)
	{
		log.debug("CamelModelProcessor - parseOptimisationRequirements 1");
		RequirementModel reqs= model.getRequirementModels().get(0); 
		log.debug("CamelModelProcessor - parseOptimisationRequirements 2");
		PaasageConfiguration configuration= pc.getPaasageConfiguration(); 
		log.debug("CamelModelProcessor - parseOptimisationRequirements 3");
		Map<String,PaaSageGoal> goalMap= new Hashtable<>(); 
		
		for(Requirement req: reqs.getRequirements())
		{
			log.debug("CamelModelProcessor - parseOptimisationRequirements 4");
			if(req instanceof OptimisationRequirement)
			{
				log.debug("CamelModelProcessor - parseOptimisationRequirements 5");
				OptimisationRequirement optReq= (OptimisationRequirement) req;

				log.debug("CamelModelProcessor - parseOptimisationRequirements 6 "+optReq.getName());

				String functionName = getFunctionName(optReq);
				log.debug("CamelModelProcessor - parseOptimisationRequirements 6.1 "+functionName);

				log.debug("CamelModelProcessor - parseOptimisationRequirements 6.2 " + PaasageModelTool.getFunctionNames(proxy));

				FunctionType ft= PaasageModelTool.getFunctionTypeByName(functionName, proxy);
				log.debug("CamelModelProcessor - parseOptimisationRequirements 7");

				if(ft!=null)
				{
					String key = getKey(ft, optReq);
					log.debug("CamelModelProcessor - parseOptimisationRequirements 8");
					PaaSageGoal goal= goalMap.get(key);
					log.debug("CamelModelProcessor - parseOptimisationRequirements 9");
					GoalOperatorEnum goalType= getSelectedGoal(optReq.getOptimisationFunction());
					if(goal==null)
					{	
						log.debug("CamelModelProcessor - parseOptimisationRequirements 10");
						goal= ApplicationFactory.eINSTANCE.createPaaSageGoal();

						if (ATTRIBUTE.equalsIgnoreCase(ft.getId())){
							String pathToAttribute = getPathToAttribute(optReq);
							goal.setOptimisationAttribute(pathToAttribute);
						}

						log.debug("CamelModelProcessor - parseOptimisationRequirements 11");
						goalMap.put(key, goal);
						log.debug("CamelModelProcessor - parseOptimisationRequirements 12");
						log.debug("CamelModelProcessor - parseOptimisationRequirements 13");
						goal.setFunction(ft); 
						log.debug("CamelModelProcessor - parseOptimisationRequirements 14");
						goal.setGoal(goalType); 
						log.debug("CamelModelProcessor - parseOptimisationRequirements 15");
						goal.setId(goalType.getName()+ft.getId());
						log.debug("CamelModelProcessor - parseOptimisationRequirements 16");
						configuration.getGoals().add(goal); 
						log.debug("CamelModelProcessor - parseOptimisationRequirements 17");
					}
					
					log.debug("CamelModelProcessor - parseOptimisationRequirements 18");
					if(optReq.getComponent()!=null)
					{
						log.debug("CamelModelProcessor - parseOptimisationRequirements 19");
						ApplicationComponent appc= PaasageModelTool.searchApplicationComponentById(configuration.getComponents(), optReq.getComponent().getName()); 
												
						log.debug("CamelModelProcessor - parseOptimisationRequirements 20");
						ComponentMetricRelationship cmr= createComponentMetricRelationship(appc, optReq.getMetric().getName());//ApplicationFactory.eINSTANCE.createComponentMetricRelationship(); 
						log.debug("CamelModelProcessor - parseOptimisationRequirements 21");
						
						goal.getApplicationComponent().add(cmr); 
						log.debug("CamelModelProcessor - parseOptimisationRequirements 22");
					}
					else //All the components are involved!
					{
						log.debug("CamelModelProcessor - parseOptimisationRequirements 23");
						for(ApplicationComponent appc: pc.getPaasageConfiguration().getComponents())
						{
							log.debug("CamelModelProcessor - parseOptimisationRequirements 24");
							ComponentMetricRelationship cmr= createComponentMetricRelationship(appc, null); 
							log.debug("CamelModelProcessor - parseOptimisationRequirements 25");
							//cmr.setComponent(appc); 
							
							goal.getApplicationComponent().add(cmr); 
							log.debug("CamelModelProcessor - parseOptimisationRequirements 26");
						}
						
						log.debug("CamelModelProcessor - parseOptimisationRequirements 27");
						goal.setApplicationMetric(functionName);
						log.debug("CamelModelProcessor - parseOptimisationRequirements 28");
					}
				} else {
					log.warn("CamelModelProcessor- parseOptimisationRequirements- The property "+optReq.getMetric().getProperty().getName() + "is not in the set {cost, response time, availability}!");
				}
			}
		}
		
		
		//Even if all the components are not involved in optimisation requirement, we have to include them
		log.debug("CamelModelProcessor - parseOptimisationRequirements 29");
		for(PaaSageGoal goal: configuration.getGoals())
		{
			log.debug("CamelModelProcessor - parseOptimisationRequirements 30");
			for(ApplicationComponent apc: configuration.getComponents())
			{
				log.debug("CamelModelProcessor - parseOptimisationRequirements 31 "+goal.getApplicationComponent()+ " "+apc.getCloudMLId());
				
				for(ComponentMetricRelationship cmr:goal.getApplicationComponent())
				{
					log.debug("CamelModelProcessor - parseOptimisationRequirements 311 "+cmr.getMetricId()+ " "+cmr.getComponent());
				}
				
				if(PaasageModelTool.searchApplicationComponentByIdInRel(goal.getApplicationComponent(), apc.getCloudMLId())==null)
				{
					log.debug("CamelModelProcessor - parseOptimisationRequirements 32");
					ComponentMetricRelationship cmr= createComponentMetricRelationship(apc, null); 
					log.debug("CamelModelProcessor - parseOptimisationRequirements 33");
					goal.getApplicationComponent().add(cmr); 
					log.debug("CamelModelProcessor - parseOptimisationRequirements 34");
				}
			}
		}
		log.debug("CamelModelProcessor - parseOptimisationRequirements 35");
	}

	private String getKey(FunctionType ft, OptimisationRequirement optReq){
		String id = ft.getId();
		if (!ATTRIBUTE.equalsIgnoreCase(id)){
			return id;
		}
		return id + "_" + getPathToAttribute(optReq);
	}

	private String getFunctionName(OptimisationRequirement optReq) {
		return getProperty(optReq).getName();
	}

	private String getPathToAttribute(OptimisationRequirement optReq) {
		return getProperty(optReq).getDescription();
	}

	private Property getProperty(OptimisationRequirement optReq){
		return optReq.getMetric() != null ? optReq.getMetric().getProperty() : optReq.getProperty();
	}

	protected ComponentMetricRelationship createComponentMetricRelationship(ApplicationComponent appc, String metricId)
	{
		ComponentMetricRelationship cmr= ApplicationFactory.eINSTANCE.createComponentMetricRelationship(); 
		
		cmr.setComponent(appc);
		
		if(metricId!=null)
			cmr.setMetricId(metricId);
		
		return cmr; 
	}
	
	/**
	 * Finds the selected goal for a given ontology
	 * @param type The optimisation function type 
	 * @return The selected goal
	 */
	protected GoalOperatorEnum getSelectedGoal(OptimisationFunctionType type) {
		return type.getValue()==OptimisationFunctionType.MAXIMISE_VALUE ? GoalOperatorEnum.MAX : GoalOperatorEnum.MIN;
	}
	
	/**
	 * Returns the Camel Model
	 * @return The Camel Model
	 */
	public CamelModel getCamelModel()
	{
		return model; 
	}
	
	/**
	 * Indicates if the processor is valid
	 * @return The valid value
	 */
	public boolean isValid() {
		return valid;
	}

	
	/**
	 * Sets the valid value
	 * @param valid The new valid value
	 */
	public void setValid(boolean valid) {
		this.valid = valid;
	} 
}
