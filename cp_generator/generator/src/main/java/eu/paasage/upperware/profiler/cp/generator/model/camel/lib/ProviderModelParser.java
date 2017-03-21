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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.VM;
import eu.paasage.camel.provider.Feature;
import eu.paasage.upperware.metamodel.application.ApplicationComponent;
import eu.paasage.upperware.metamodel.application.ApplicationFactory;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.application.Provider;
import eu.paasage.upperware.metamodel.application.VirtualMachineProfile;
import eu.paasage.upperware.metamodel.types.typesPaasage.LocationUpperware;
import eu.paasage.upperware.metamodel.types.typesPaasage.ProviderType;
import eu.paasage.upperware.profiler.cp.generator.db.api.IDatabaseProxy;
import eu.paasage.upperware.profiler.cp.generator.db.lib.CDODatabaseProxy;
import eu.paasage.upperware.profiler.cp.generator.model.lib.GenerationOrchestrator;
import eu.paasage.upperware.profiler.cp.generator.model.lib.PaaSageConfigurationWrapper;
import eu.paasage.upperware.profiler.cp.generator.model.tools.PaasageModelTool;
import fr.inria.paasage.saloon.camel.ProviderModelDecorator;
import fr.inria.paasage.saloon.camel.SaloonCamelSolver;
import fr.inria.paasage.saloon.camel.ontology.ConceptCamel;
import fr.inria.paasage.saloon.camel.ontology.OntologyCamel;
import fr.inria.paasage.saloon.camel.ontology.QuantifiableElementCamel;

/**
 * This class provides the functionality to deal with the provider models in a camel model
 * @author danielromero
 *
 */
public class ProviderModelParser 
{
	/*
	 * ATTRIBUTES
	 */
	/*
	 * The database proxy
	 */
	protected IDatabaseProxy db; 
	
	/*
	 * The logger
	 */
	private static Logger logger= GenerationOrchestrator.getLogger(); 
	
	
	/*
	 * CONSTRUCTOR
	 */
	/**
	 * Default constructor
	 */
	public ProviderModelParser()
	{
		db= CDODatabaseProxy.getInstance(); 
	}
	
	/*
	 * METHODS
	 */
	/**
	 * Process a Saloon ontology by considering only a given provider 
	 * @param ontology The ontology to be processed  
	 * @param configurationWrapper The configuration being created
	 * @param pmd The decorator that contains the model provider
	 * @param vm The virtual machine
	 * @param alreadySelectedCandidates List of candidates that have already being selected and therefore have not to be removed
	 */
	public void parseOntology(final OntologyCamel ontology, PaaSageConfigurationWrapper configurationWrapper, ProviderModelDecorator pmd, VM vm, List<Provider> alreadySelectedCandidates)
	{
		
		String vmName= vm.getName(); 
		
		logger.debug("ProviderModelParser- parseOntology 3- Init...");
		List<ConceptCamel> selectedConcepts= getSelectedConcepts(ontology); 
		logger.debug("ProviderModelParser- parseOntology 4- Init...Selected concepts size: "+selectedConcepts.size());
		QuantifiableElementCamel cpuConcept= (QuantifiableElementCamel) ProviderModelParser.getConceptByName("CPU", ontology.getConcepts()); 
		logger.debug("ProviderModelParser - parseOntology - CPU concept unit 2 "+cpuConcept.getUnit().getName());
		
		PaasageConfiguration configuration= configurationWrapper.getPaasageConfiguration(); 
		
		List<VirtualMachineProfile> vmProfiles= PaasageModelTool.searchRelatedVMProfiles(configuration.getVmProfiles(), vmName);
		
		
		List<Provider> candidates= PaasageModelTool.getProvidersFromVirtualMachineProfiles(vmProfiles);//configuration.getProviders(); 
		
		Provider candidate= processCloud(pmd, selectedConcepts, ontology, configurationWrapper); 
		
		if(candidate!=null)
		{
			boolean found= false;
			if(candidate.getLocation()!=null)
			{
				logger.debug("ProviderModelParser - parseOntology - Searching Candidate with Location "+candidate.getLocation().getName());
				Provider aux= PaasageModelTool.searchProviderWithLocationInList(candidates, candidate); 
				
				if(aux!=null)
				{	
					candidate= aux; 
					found= true; 
				}	
			}
			else
			{
				Provider aux= PaasageModelTool.searchProviderInList(candidates, candidate); 
				
				if(aux!=null)
				{	
					candidate= aux; 
					found= true; 
					
				}	
				
			}
			
			if(!found)
				logger.warn("** 		The candidate with ID "+candidate.getId()+" was not found. It will be not considerd in the problem creation");
			
			CamelModel camelModel= (CamelModel) pmd.getPm().eContainer(); 
			camelModel.setName(vmName);
			db.savePM(pmd.getPm(), configuration, candidate);
			
			logger.info("** 		Adding candidate: "+candidate.getId()+" with type: "+candidate.getType().getId());
			
			if(!PaasageModelTool.isProviderInList(candidates, candidate))
			{	
				candidates.add(candidate); 
				logger.info("** 		Candidate added!");
			}	
			else
				logger.info("** 		Candidate already added!");
							
			if(candidates.size()==0)
				logger.warn("ProviderModelParser- parseOntology- There is not cloud provider candidate!");
			
			alreadySelectedCandidates.add(candidate); 
			
		}

/*		else //The candidate has to be deleted
		{
			ProviderType pt= PaasageModelTool.searchProviderTypeById(pmd.getProviderId(), configurationWrapper); 
			
			logger.debug("ProviderModelParser - parseOntology - 3 parameters - Removing candidate with type: "+pt.getId()); 
			
			Provider pv=PaasageModelTool.removeProvidersFromListByType(configuration.getProviders(), pt); 
			
			if(pv!=null)
			{	
				logger.info("** 		Removing candidate: "+pv.getId()+" with type: "+pt.getId()); 
				logger.info("** 		Candidate removed!");
			
			}
			else
				logger.info("** 		Candidate Already removed!");
			
			
			logger.debug("ProviderModelParser - parseOntology -  3 parameters - Profiles before deleting "+configuration.getVmProfiles().size()); 
			
			PaasageModelTool.removeVirtualMahineProfilesByProviderType(configuration.getVmProfiles(), pt, configuration.getComponents()); 
			
			logger.debug("ProviderModelParser - parseOntology -  3 parameters - Profiles after deleting "+configuration.getVmProfiles().size()); 
								
		}*/
	}
	
	
	public void removeNoCandidateProviders(PaasageConfiguration configuration, List<Provider> candidates)
	{	logger.debug("** 		Candidates size: "+candidates.size()); 
	
	
		for(Provider c: candidates)
		{
			logger.debug("** 		Candidates ID: "+c.getId()); 
		}
		
		for(int i=0; i<configuration.getProviders().size(); i++ )
		{
			Provider current= configuration.getProviders().get(i); 
			if(!PaasageModelTool.isProviderInListById(candidates, current))
			{
				PaasageModelTool.removeVirtualMahineProfilesByProvider(configuration.getVmProfiles(), current, configuration.getComponents());
				logger.info("** 		Removing candidate: "+current.getId()+" with type: "+current.getType().getId()); 
				
				configuration.getProviders().remove(current); 
				
				logger.info("** 		Candidate removed!");
				
				i--; 
			}
		}
	}
	
	public void removeCandidatesWithLocationForVM(VM vm, String providerId, String locationId, PaaSageConfigurationWrapper pcw)
	{
		List<VirtualMachineProfile> vmProfiles= PaasageModelTool.searchRelatedVMProfiles(pcw.getPaasageConfiguration().getVmProfiles(), vm.getName());
		
		List<Provider> vmProviders= PaasageModelTool.getProvidersFromVirtualMachineProfiles(vmProfiles);
		
		LocationUpperware location= PaasageModelTool.getLocationFromName(locationId, pcw);
		
		for(Provider current: vmProviders)
		{
			if(current.getType().getId().equals(providerId) && current.getLocation()!=null)
			{
				if(location!=null && location.getName().equals(current.getLocation().getName()))
				{
					logger.debug("ProviderModelParser - removeCandidatesWithLocationForVM - removing candidates with location "+location.getName()+" and provider "+current.getId());
					PaasageModelTool.removeVirtualMahineProfilesByProvider(vmProfiles, current, pcw);
				}
			}
		}
		
		
	}
	
	/**
	 * Processes a Saloon Ontology 
	 * @param ontology The ontology to be created
	 * @param configurationWrapper The configuration being created
	 */
	public void parseOntology(OntologyCamel ontology, PaaSageConfigurationWrapper configurationWrapper)
	{
		Map<String,ProviderModelDecorator> pmsMap= db.getPMsMap(); 
		
		logger.debug("ProviderModelParser - parseOntology - Feature Map keys "+pmsMap.keySet());
		
		List<ConceptCamel> selectedConcepts= getSelectedConcepts(ontology); 
			
		PaasageConfiguration configuration= configurationWrapper.getPaasageConfiguration(); 
		
		EList<Provider> candidates= configuration.getProviders(); 
		
		
		if(candidates.size()==0) //We consider only all the candidates if there are not preferred ones 
		{	
			
			logger.debug("ProviderModelParser - parseOntology - Not preferred candidates preselected! Clouds to consider: "+pmsMap.keySet().size()); 
		
			Iterator<String> it=pmsMap.keySet().iterator();  
			while(it.hasNext())
			{
				String cloud= it.next(); 
				logger.debug("ProviderModelParser - parseOntology - Canditate: "+cloud); 
				ProviderModelDecorator pmw= pmsMap.get(cloud); 
				
				Provider candidate= processCloud(pmw, selectedConcepts, ontology, configurationWrapper); 
				
				if(candidate!=null)
				{	
					candidates.add(candidate); 
					db.savePM(pmw.getPm(), configuration, candidate);
				}
			}
		}	
		else
		{
			List<ProviderType> types= PaasageModelTool.getProvidersTypeFromProviderList(candidates); 
			
			for(ProviderType type: types)
			{
				ProviderModelDecorator fmw= pmsMap.get(type.getId()); 
				
				Provider candidate= processCloud(fmw, selectedConcepts, ontology, configurationWrapper); 
				
				if(candidate!=null)
				{
					if(candidate.getLocation()!=null)
					{
						Provider aux= PaasageModelTool.searchProviderWithLocationInList(candidates, candidate); 
						
						if(aux!=null)
							candidate= aux; 
					}
					else
					{
						Provider aux= PaasageModelTool.searchProviderInList(candidates, candidate); 
						
						if(aux!=null)
							candidate= aux; 
						
					}
					db.savePM(fmw.getPm(), configuration, candidate); 
				}
				else //The candidate has to be deleted
				{
					
					logger.debug("ProviderModelParser - parseOntology - Removing candidate with type: "+type.getId()); 
					
					PaasageModelTool.removeProvidersFromListByType(candidates, type); 
					
					logger.debug("ProviderModelParser - parseOntology - Profiles before deleting "+configuration.getVmProfiles().size()); 
					
					PaasageModelTool.removeVirtualMahineProfilesByProviderType(configuration.getVmProfiles(), type, configuration.getComponents()); 
					
					logger.debug("ProviderModelParser - parseOntology - Profiles after deleting "+configuration.getVmProfiles().size()); 
										
				}
					
			}
		}
				
		if(candidates.size()==0)
			logger.warn("ProviderModelParser- parseOntology- There is not cloud provider candidate!");
		
	}
	

	/**
	 * Determines if a given provider satisfies the requirements defined by selected concepts in a given ontology
	 * @param pmw The provider model
	 * @param selectedConcepts The selected concepts in the ontology
	 * @param ontology The saloon ontology
	 * @param pcw The configuration being created
	 * @return the provider or null if if requirements are not satisfied
	 */
	protected Provider processCloud(ProviderModelDecorator pmw, List<ConceptCamel> selectedConcepts, OntologyCamel ontology, PaaSageConfigurationWrapper pcw)
	{
		logger.debug("ProviderModelParser- processCloud - Processing cloud "+pmw.getProviderId());
		
		QuantifiableElementCamel cpuConcept= (QuantifiableElementCamel) ProviderModelParser.getConceptByName("CPU", ontology.getConcepts()); 
		logger.debug("ProviderModelParser - processCloud - CPU concept unit 2 "+cpuConcept.getUnit().getName());
		
		SaloonCamelSolver solver= new SaloonCamelSolver(pmw); 
		//SaloonCamelSolver.logger.setLevel(Level.ALL);
		boolean allRespected= solver.verifyConceptsMapping(selectedConcepts); 
		
		cpuConcept= (QuantifiableElementCamel) ProviderModelParser.getConceptByName("CPU", ontology.getConcepts()); 
		logger.debug("ProviderModelParser - processCloud - CPU concept unit 3 "+cpuConcept.getUnit().getName());
		
		logger.debug("ProviderModelParser- processCloud - Mapping respected "+allRespected);
		
		if(allRespected)
		{
			solver.processMappingRules(selectedConcepts);
			
			solver.processAssigmentFunctions(ontology.getObjectiveFunctions()); //TODO is selected for the related concept in the assignment function?
			
			solver.buildCspModel(); 
			
			solver.solve(); 
			
			if(solver.existsSolution())
			{	
				//candidates.add(cloud); 
				logger.debug("ProviderModelParser- processCloud - Candidate accepted: "+pmw.getProviderId());
				
				solver.defineConfigurationOnFeatureModel(); 
				
				Provider provider= ApplicationFactory.eINSTANCE.createProvider(); 
				
				ProviderType pt= PaasageModelTool.searchProviderTypeById(pmw.getProviderId(), pcw); 
				
				if(pt==null)
					logger.warn("ProviderModelParser- processCloud- The type "+pmw.getProviderId()+" does not exist. The provider type will be not set!");
				
				provider.setType(pt);
				
				Feature locationFeature= searchLocationFeatureInList(solver.getSelectedFeatures()); 
				
				String locationName= ""; 
				
				if(locationFeature!=null)
				{
					LocationUpperware location= PaasageModelTool.getLocationFromName(locationFeature.getName(), pcw); 
					
					
					if(location==null)
						logger.debug("ProviderModelParser- processCloud- The location "+ locationFeature.getName() + " for the provider "+pmw.getProviderId()+" does not exist. The location for this provider will be not set!");
					else
					{	
						provider.setLocation(location);
						locationName= location.getName(); 
						
					}	
					
					
				}
				else
					logger.debug("ProviderModelParser- processCloud- The location for the provider "+pmw.getProviderId()+" has not been selected. The location for this provider will be not set!");
				
				String id= PaasageModelTool.buildProviderId(pt.getId(), locationName); 
				
				provider.setId(id);
				
				return provider; 
			}	
		}
		
		
		logger.debug("ProviderModelParser- processCloud - Solution not found!!");
		
		return null; 
	}
	
	
	/**
	 * Retrieves all the selected concepts in a given ontology
	 * @param onto The saloon ontology
	 * @return The list of selected concepts
	 */
	public List<ConceptCamel> getSelectedConcepts(OntologyCamel onto)
	{
		List<ConceptCamel> selected= getSelectedConcepts(onto.getConcepts()); 
		
		return selected; 
	}
	
	/**
	 * Retrieves all the selected concepts in a given list of concepts
	 * @param concepts The list of concepts
	 * @return The list of concepts
	 */
	public static List<ConceptCamel> getSelectedConcepts(EList<ConceptCamel> concepts)
	{
		List<ConceptCamel> selected= new ArrayList<ConceptCamel>(); 
		
		for(ConceptCamel c: concepts)
		{
			logger.debug("ProviderModelParser- getSelectedConcepts - concept "+c.getName()+" selected: "+c.isSelected());
			if(c.isSelected() && !isOptimisationConcept(c))
			{	
				selected.add(c); 
				
			}
			
			if(c.getSubConcept()!=null)
			{	
				List<ConceptCamel> aux= getSelectedConcepts(c.getSubConcept()); 
				selected.addAll(aux); 
			}	
		}
		
		return selected; 
	}
	
	/**
	 * Searches for the "Location" feature in a given feature list
	 * @param features The list of features
	 * @return The "Location" feature or null if it does not exist 
	 */
	protected Feature searchLocationFeatureInList(List<Feature> features)
	{
		for(Feature feature:features)
		{
			Feature parent= (Feature) feature.eContainer(); 
			if(parent!=null && parent.getName().equals("Location"))
				return feature; 
		}
		
		return null; 
	}
	

	/**
	 * Indicates if a given concept is related to an optimization 
	 * @param c The concept
	 * @return true if c.name=="Max" || c.name=="Min" || c.name=="Cost" || c.name=="Response Time"
	 */
	protected static boolean isOptimisationConcept(ConceptCamel c)
	{
		return c.getName().equals("Max") || c.getName().equals("Min") || c.getName().equals("Cost") || c.getName().equals("Response Time"); //TODO IMPROVE THE LIST MANAGEMENT
	}
		
	
	
	
	/**
	 * Searches a concept in a list with a given name
	 * @param name The name
	 * @param concepts List of concepts
	 * @return The concept with name name or null if it does not exist
	 */
	public static ConceptCamel getConceptByName(String name, EList<ConceptCamel> concepts)
	{
		
		for(ConceptCamel concept:concepts)
		{
			if(concept.getName().equals(name))
			{	
				return concept; 
			}
			else if(concept.getSubConcept()!=null && concept.getSubConcept().size()>0)
			{
				
				ConceptCamel aux= getConceptByName(name, concept.getSubConcept()); 
				
				if(aux!=null)
					return aux; 
			}
		}
		
		return null; 
		
		
	}
	
	/**
	 * Searches a concept in a list with a given name
	 * @param name The name
	 * @param concepts List of concepts
	 * @return The concept with name name or null if it does not exist
	 */
	public static ConceptCamel getConcepContainingName(String name, EList<ConceptCamel> concepts)
	{
		
		for(ConceptCamel concept:concepts)
		{
			if(concept.getName().contains(name) || name.contains(concept.getName()))
				return concept; 
			else if(concept.getSubConcept()!=null && concept.getSubConcept().size()>0)
			{
				
				ConceptCamel aux= getConcepContainingName(name, concept.getSubConcept()); 
				
				if(aux!=null)
					return aux; 
			}
		}
		
		return null; 
		
		
	}
	
	public static ConceptCamel searchLocation(String locationName, EList<ConceptCamel> concepts)
	{
		for(ConceptCamel loc: concepts)
		{
			if(loc.getName().equals(locationName))
				return loc; 
		}
		
		return null; 
	}
	
	public void checkExistSolution(PaaSageConfigurationWrapper pcw)
	{
		List<ApplicationComponent> componentsWithoutSolution= new ArrayList<>();
		
		for(ApplicationComponent ac:pcw.getPaasageConfiguration().getComponents())
		{
			if(ac.getRequiredProfile().size()==0)
			{
				ApplicationComponent copyAC= ApplicationFactory.eINSTANCE.createApplicationComponent(); 
				copyAC.setCloudMLId(ac.getCloudMLId());
				
				componentsWithoutSolution.add(copyAC); 
			}
		}
		
		pcw.setComponentsWithoutVM(componentsWithoutSolution);
	}

}
