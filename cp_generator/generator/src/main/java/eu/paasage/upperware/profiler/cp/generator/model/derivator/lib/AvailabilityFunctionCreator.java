/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.profiler.cp.generator.model.derivator.lib;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import eu.paasage.camel.provider.Attribute;
import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.camel.type.IntegerValue;
import eu.paasage.camel.type.StringsValue;
import eu.paasage.upperware.metamodel.application.ApplicationComponent;
import eu.paasage.upperware.metamodel.application.ComponentMetricRelationship;
import eu.paasage.upperware.metamodel.application.PaaSageGoal;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.cp.ComposedExpression;
import eu.paasage.upperware.metamodel.cp.Constant;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.Goal;
import eu.paasage.upperware.metamodel.cp.GoalOperatorEnum;
import eu.paasage.upperware.metamodel.cp.OperatorEnum;
import eu.paasage.upperware.metamodel.cp.Variable;
import eu.paasage.upperware.profiler.cp.generator.db.api.IDatabaseProxy;
import eu.paasage.upperware.profiler.cp.generator.model.derivator.api.IFunctionCreator;
import eu.paasage.upperware.profiler.cp.generator.model.lib.GenerationOrchestrator;
import eu.paasage.upperware.profiler.cp.generator.model.tools.CPModelTool;
import fr.inria.paasage.saloon.camel.ProviderModelDecorator;

@Slf4j
public class AvailabilityFunctionCreator implements IFunctionCreator {

	private IDatabaseProxy database; 
	
	public static String AVAILABILITY_ATTRIBUTE= "Availability"; 
	
	public AvailabilityFunctionCreator() {
		
	}
	

	public void createFunction(ConstraintProblem cp, PaaSageGoal goal) {
		
		log.debug("AvailabilityFunctionCreator - createFunction - 1");
		ComposedExpression ce= CPModelTool.createComposedExpression(OperatorEnum.PLUS,CPModelTool.getAuxExpressionId());                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
	
		cp.getAuxExpressions().add(ce); 
		log.debug("AvailabilityFunctionCreator - createFunction - 2");

			
		for(ComponentMetricRelationship cmr:goal.getApplicationComponent())
		{
			log.debug("AvailabilityFunctionCreator - createFunction - 3");
			ApplicationComponent apc= cmr.getComponent(); 
			log.debug("AvailabilityFunctionCreator - createFunction - 4");
			List<Variable> vars= CPModelTool.getVariablesRelatedToAppComponent(apc, cp); 
			log.debug("AvailabilityFunctionCreator - createFunction - 5");
			for(Variable var: vars)
			{
				if(cmr.getMetricId()!=null)
				{
					ComposedExpression aux= CPModelTool.createComposedExpression(OperatorEnum.TIMES,CPModelTool.getAuxExpressionId()); 
				
				
					//String metricId= CPModelTool.getMetricId(cmr.getMetricId(), var.getVmId(), var.getProviderId());  
					//MetricVariable metric= CPModelTool.createMetricVariable(metricId, BasicTypeEnum.DOUBLE, cp); 
					//cp.getMetricVariables().add(metric); 
					
					//TODO COMPUTE THE METRIC VALUE
					String providerId= CPModelTool.getProviderRelatedToVariable(var);
					
					PaasageConfiguration pc= (PaasageConfiguration) goal.eContainer(); 
					String appId= pc.getId(); 
					
					log.debug("AvailabilityFunctionCreator - createFunction - 6"); 
					ProviderModel pm= database.loadPM(appId, providerId); 
					log.debug("AvailabilityFunctionCreator - createFunction - 7 "+providerId+" "+pm);
					
					ProviderModelDecorator pmd= new ProviderModelDecorator(providerId, pm); 
					
					log.debug("AvailabilityFunctionCreator - createFunction - 8");
					Attribute av= pmd.getAttributeByName(AVAILABILITY_ATTRIBUTE);
					log.debug("AvailabilityFunctionCreator - createFunction - 9");
					Constant availability= null; 
					
					if(av.getValue() instanceof IntegerValue)
					{	
						IntegerValue intVal= (IntegerValue) av.getValue(); 
						availability=CPModelTool.createIntegerConstant(intVal.getValue(), CPModelTool.getConstantName()); 
					}
					else
					{
						StringsValue stringVal= (StringsValue) av.getValue(); 
						availability=CPModelTool.createIntegerConstant(Integer.parseInt(stringVal.getValue()), CPModelTool.getConstantName()); 
					}
					
					log.debug("AvailabilityFunctionCreator - createFunction - 10");
					cp.getConstants().add(availability); 
					
					log.debug("AvailabilityFunctionCreator - createFunction - 11");
					//aux.getExpressions().add(metric); 
					aux.getExpressions().add(availability); 
					
					aux.getExpressions().add(var); 
					
					cp.getAuxExpressions().add(aux); 
					
					ce.getExpressions().add(aux);
				}
				else
				{
					ce.getExpressions().add(var); 
				}
				
				 
			}
			
		}
		
		Goal goalCP= CPModelTool.createGoal(GoalOperatorEnum.MAX, goal.getId(), ce, 0); //0 is the default priority as here we are not using an optimisation requirement for the creation of the goal 
		
		cp.getGoals().add(goalCP); 
		
		
	}


	@Override
	public void setDatabaseProxy(IDatabaseProxy proxy) {
		
		database= proxy; 
		
	}

}
