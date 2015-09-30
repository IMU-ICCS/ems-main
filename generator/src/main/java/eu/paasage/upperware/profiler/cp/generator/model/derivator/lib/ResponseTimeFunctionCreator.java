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

import eu.paasage.upperware.metamodel.application.ApplicationComponent;
import eu.paasage.upperware.metamodel.application.ComponentMetricRelationship;
import eu.paasage.upperware.metamodel.application.PaaSageGoal;
import eu.paasage.upperware.metamodel.cp.ComposedExpression;
import eu.paasage.upperware.metamodel.cp.Constant;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.Goal;
import eu.paasage.upperware.metamodel.cp.GoalOperatorEnum;
import eu.paasage.upperware.metamodel.cp.MetricVariable;
import eu.paasage.upperware.metamodel.cp.OperatorEnum;
import eu.paasage.upperware.metamodel.cp.Variable;
import eu.paasage.upperware.metamodel.types.BasicTypeEnum;
import eu.paasage.upperware.profiler.cp.generator.db.api.IDatabaseProxy;
import eu.paasage.upperware.profiler.cp.generator.model.derivator.api.IFunctionCreator;
import eu.paasage.upperware.profiler.cp.generator.model.tools.CPModelTool;

public class ResponseTimeFunctionCreator implements IFunctionCreator {

	private static final String MINUS_ONE="minus_one"; 
	
	private IDatabaseProxy database; 
	
	public ResponseTimeFunctionCreator() 
	{
		
	}

	public void createFunction(ConstraintProblem cp, PaaSageGoal goal) {
		
		
		ComposedExpression ce= CPModelTool.createComposedExpression(OperatorEnum.PLUS,CPModelTool.getAuxExpressionId());                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
	
		cp.getAuxExpressions().add(ce); 
		
		Constant minusOneConstant= CPModelTool.searchConstantByValue(cp.getConstants(), -1);
		
		if(minusOneConstant==null)
		{
			minusOneConstant=CPModelTool.createIntegerConstant(-1, MINUS_ONE); 
			cp.getConstants().add(minusOneConstant); 
		}
		
	
		for(ComponentMetricRelationship cmr:goal.getApplicationComponent())
		{
			ApplicationComponent apc= cmr.getComponent(); 
			
			List<Variable> vars= CPModelTool.getVariablesRelatedToAppComponent(apc, cp); 
			
			for(Variable var: vars)
			{
				ComposedExpression aux= CPModelTool.createComposedExpression(OperatorEnum.TIMES,CPModelTool.getAuxExpressionId()); 
				aux.getExpressions().add(minusOneConstant); 
				
				if(cmr.getMetricId()!=null)
				{
					String metricId= CPModelTool.getMetricId(cmr.getMetricId(), var.getVmId(), var.getProviderId());  
					MetricVariable metric= CPModelTool.createMetricVariable(metricId, BasicTypeEnum.DOUBLE, cp); 
					cp.getMetricVariables().add(metric); 
					
					//TODO COMPUTE THE METRIC VALUE
					
					aux.getExpressions().add(metric); 
				}
				
				aux.getExpressions().add(var); 
				
				cp.getAuxExpressions().add(aux); 
				
				ce.getExpressions().add(aux); 
				
				//TODO WHAT TO DO WITH VARIABLES WITHOUT METRIC??
			}
			
		}
		
		Goal goalCP= CPModelTool.createGoal(GoalOperatorEnum.MIN, goal.getId(), ce); 
		
		cp.getGoals().add(goalCP); 
		
		
	}

	@Override
	public void setDatabaseProxy(IDatabaseProxy proxy) {
		
		this.database= proxy; 
		
	}

}
