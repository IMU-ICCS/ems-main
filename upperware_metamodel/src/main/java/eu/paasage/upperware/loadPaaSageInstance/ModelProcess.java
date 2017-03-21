/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.loadPaaSageInstance;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.common.util.EList;

import eu.paasage.upperware.metamodel.application.ApplicationComponent;
import eu.paasage.upperware.metamodel.application.Dimension;
import eu.paasage.upperware.metamodel.application.ElasticityRule;
import eu.paasage.upperware.metamodel.application.PaaSageGoal;
import eu.paasage.upperware.metamodel.application.PaaSageVariable;
import eu.paasage.upperware.metamodel.application.Provider;
import eu.paasage.upperware.metamodel.application.VirtualMachine;
import eu.paasage.upperware.metamodel.application.VirtualMachineProfile;
import eu.paasage.upperware.metamodel.types.NumericValueUpperware;
import eu.paasage.upperware.metamodel.types.typesPaasage.ProviderType;

public class ModelProcess implements ModelProcessFactory {
	private ConfigurationLoad config; // Application Model
	private ConstraintProblemLoad constraintProblem; // CP Model

	public ModelProcess(ConfigurationLoad config,
			ConstraintProblemLoad constraintProblem) {
		super();
		this.config = config;
		this.constraintProblem = constraintProblem;
	}

	public ModelProcess() {
		super();
		this.config = null;
		this.constraintProblem = null;
	}

	public ModelProcess(String configpath, String constraintProblempath) {
		super();
		if (configpath != null){
			this.config = new ConfigurationLoad(configpath);
		}
		
		if (constraintProblempath != null){
			this.constraintProblem = new ConstraintProblemLoad(
					constraintProblempath);
		}
		
		
	}

	public ConfigurationLoad getConfig() {
		return config;
	}

	public void setConfig(ConfigurationLoad config) {
		this.config = config;
	}

	public ConstraintProblemLoad getConstraintProblem() {
		return constraintProblem;
	}

	public void setConstraintProblem(ConstraintProblemLoad constraintProblem) {
		this.constraintProblem = constraintProblem;
	}

	
	public void loadModel(){
		if (config != null) {
			config.load();
		}
		if (constraintProblem !=null){
			constraintProblem.load();
		}
		
	}
	
	
	
	public EList<ApplicationComponent> getApplicationComponents() {
		if (config != null) {
			return config.getConfig().getComponents();
		}
		return null;
	}

	public EList<Provider> getProviderList() {
		if (config != null) {
			return config.getConfig().getProviders();
		}
		return null;
	}

	public EList<PaaSageGoal> getGoals() {
		if (config != null) {
			return config.getConfig().getGoals();

		}
		return null;
	}

	/*public EList<ComparisonExpression> getConstraints() {
		if (config != null) {
			return config.getConfig().getConstraints();

		}
		return null;
	}*/

	public EList<PaaSageVariable> getVariables() {
		if (config != null) {
			return config.getConfig().getVariables();

		}
		return null;
	}

	public EList<VirtualMachineProfile> getVmProfiles() {
		if (config != null) {
			return config.getConfig().getVmProfiles();

		}
		return null;
	}

	public EList<VirtualMachine> getVms() {
		if (config != null) {
			return config.getConfig().getVms();

		}
		return null;
	}

	public EList<Dimension> getMonitoredDimensions() {
		if (config != null) {
			return config.getConfig().getMonitoredDimensions();

		}
		return null;
	}

	public EList<ElasticityRule> getRules() {
		if (config != null) {
			return config.getConfig().getRules();

		}
		return null;
	}

	public EList<ProviderType> getComponentPreferedProviders(String componentId) {
		if (config != null) {
			for (Iterator<ApplicationComponent> iterator = getApplicationComponents()
					.iterator(); iterator.hasNext();) {
				ApplicationComponent comp = iterator.next();
				if (componentId == comp.getCloudMLId()) {
					return comp.getPreferredProviders();
				}
			}
		}
		return null;
	}

	public Map<Provider, Double> getComponentCosts(String componentId) {
		Map<Provider, Double> map = null;
		if (config != null) {

			ApplicationComponent component = null;
			for (Iterator<ApplicationComponent> iteratorc = getApplicationComponents()
					.iterator(); iteratorc.hasNext();) {
				ApplicationComponent comp = iteratorc.next();
				if (componentId == comp.getCloudMLId()) {
					component = comp;
				}
			}
			if (component != null) {
				map = new HashMap<Provider, Double>();
				
				
				for(VirtualMachineProfile profile:component.getRequiredProfile())
				{
					map.put(profile.getProviderDimension().get(0).getProvider(), profile.getProviderDimension().get(0).getValue()); 
				}
				
				//VirtualMachineProfile profile = component.getRequiredProfile();
				
				

				/*for (Iterator<ProviderCost> iteratorp = profile
						.getPotentialProviders().iterator(); iteratorp
						.hasNext();) {

					ProviderCost provCost = iteratorp.next();
					if (component.getPreferredProviders().contains(
							provCost.getProvider().getType())) {
						map.put(provCost.getProvider(), provCost.getCost());
					}

				}*/

			}

		}
		return map;

	}
	
	public EList<ElasticityRule> getComponentElasticityRules(String componentId){
	
		EList<ElasticityRule> rulesList = null;
		EList<ElasticityRule> crulesList = null;
		rulesList = getRules();
		if (rulesList != null){
			for (Iterator<ElasticityRule> iterator = rulesList.iterator(); iterator.hasNext();) {
				//ElasticityRule rule = iterator.next();
				//Introduce a relation between elastic rules and components or component Types
			}
			
			
		}
		return crulesList;
	}
	public Boolean setVariable(String variableId, NumericValueUpperware value){
		
		EList<PaaSageVariable> variableList = getVariables();
		if(variableList != null ){
			for (Iterator<PaaSageVariable> iterator = variableList.iterator(); iterator.hasNext();) {
				PaaSageVariable variable = iterator.next();
				if (variable.getCpVariableId() == variableId){
					//variable.setValue(value);
					return true;
				}
				
			}
			
		}
		
		return false;
		
	}

	

}
