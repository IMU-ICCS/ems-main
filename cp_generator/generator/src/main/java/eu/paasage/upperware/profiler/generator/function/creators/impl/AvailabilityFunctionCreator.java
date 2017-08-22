/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.profiler.generator.function.creators.impl;

import eu.paasage.camel.provider.Attribute;
import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.camel.type.IntegerValue;
import eu.paasage.camel.type.SingleValue;
import eu.paasage.camel.type.StringsValue;
import eu.paasage.upperware.metamodel.application.ApplicationComponent;
import eu.paasage.upperware.metamodel.application.ComponentMetricRelationship;
import eu.paasage.upperware.metamodel.application.PaaSageGoal;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.cp.*;

import eu.paasage.upperware.profiler.cp.generator.model.tools.CPModelTool;
import eu.paasage.upperware.profiler.generator.function.creators.FunctionCreator;
import eu.paasage.upperware.profiler.generator.db.IDatabaseProxy;
import eu.paasage.upperware.profiler.generator.service.camel.ConstantService;
import eu.paasage.upperware.profiler.generator.service.camel.ConstraintService;
import fr.inria.paasage.saloon.camel.ProviderModelDecorator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AvailabilityFunctionCreator implements FunctionCreator {

	private IDatabaseProxy database;
	private ConstantService constantService;
	private ConstraintService constraintService;
	
	public static String AVAILABILITY_ATTRIBUTE= "Availability"; 

	@Override
	public String getName() {
		return "Availability";
	}

	public void createFunction(ConstraintProblem cp, PaaSageGoal goal) {
		
		log.debug("AvailabilityFunctionCreator - createFunction - 1");
		ComposedExpression ce= constraintService.createComposedExpression(OperatorEnum.PLUS);
	
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
					ComposedExpression aux= constraintService.createComposedExpression(OperatorEnum.TIMES);;

					//TODO COMPUTE THE METRIC VALUE
					String providerId= var.getProviderId();
					
					PaasageConfiguration pc= (PaasageConfiguration) goal.eContainer(); 
					String appId= pc.getId(); 
					
					log.debug("AvailabilityFunctionCreator - createFunction - 6"); 
					ProviderModel pm= database.loadPM(appId, providerId); 
					log.debug("AvailabilityFunctionCreator - createFunction - 7 "+providerId+" "+pm);
					
					ProviderModelDecorator pmd= new ProviderModelDecorator(providerId, pm); 

					log.debug("AvailabilityFunctionCreator - createFunction - 8");
					Attribute av= pmd.getAttributeByName(AVAILABILITY_ATTRIBUTE);
					log.debug("AvailabilityFunctionCreator - createFunction - 9");
					Constant availability = constantService.createIntegerConstant(getAttributeValue(av.getValue()));
					
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

	private int getAttributeValue(SingleValue value) {
		int result;
		if (value instanceof IntegerValue) {
			IntegerValue intVal = (IntegerValue) value;
			result = intVal.getValue();
		} else {
			StringsValue stringVal = (StringsValue) value;
			result = Integer.parseInt(stringVal.getValue());
		}
		return result;
	}


}
