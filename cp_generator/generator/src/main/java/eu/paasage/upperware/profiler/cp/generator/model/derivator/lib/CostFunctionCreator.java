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

import java.io.InputStream;

import org.eclipse.emf.common.util.EList;

import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.upperware.metamodel.application.PaaSageGoal;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.cp.ComposedExpression;
import eu.paasage.upperware.metamodel.cp.Constant;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.Goal;
import eu.paasage.upperware.metamodel.cp.GoalOperatorEnum;
import eu.paasage.upperware.metamodel.cp.NumericExpression;
import eu.paasage.upperware.metamodel.cp.OperatorEnum;
import eu.paasage.upperware.metamodel.cp.Variable;
import eu.paasage.upperware.profiler.cp.generator.db.api.IDatabaseProxy;
import eu.paasage.upperware.profiler.cp.generator.model.derivator.api.IFunctionCreator;
import eu.paasage.upperware.profiler.cp.generator.model.tools.CPModelTool;
import fr.inria.paasage.saloon.price.model.lib.EstimatorsManager;

public class CostFunctionCreator implements IFunctionCreator {
	
	private IDatabaseProxy database; 
	
	/*
	 * The manager of estimators
	 */
	protected EstimatorsManager manager; 

	public CostFunctionCreator(InputStream rates) 
	{		
		manager = new EstimatorsManager(rates); 
		
	}
	
	public CostFunctionCreator() 
	{	
		
		manager = new EstimatorsManager(CPModelDerivator.selectExistingPriceFile()); 
		
	}
	public void createFunction(ConstraintProblem cp, PaaSageGoal goal) {
		
		EList<Variable> variables= cp.getVariables();
		PaasageConfiguration pc= (PaasageConfiguration) goal.eContainer(); 
		String appId= pc.getId(); 
		
		NumericExpression goalExpression= null; 
		
		
		if(variables.size()>1)
		{	
			ComposedExpression ce= CPModelTool.createComposedExpression(OperatorEnum.PLUS,CPModelTool.getAuxExpressionId());                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
			
			cp.getAuxExpressions().add(ce); 
			
			goalExpression= ce; 
			
			for(Variable v: variables)
			{
				ComposedExpression multiplication= createMultiplication(cp, v, appId); 
				
				ce.getExpressions().add(multiplication); 
			}
			
		}
		else
		{
			Variable v= variables.get(0); 
			
			goalExpression= createMultiplication(cp, v, appId); 
		}
		
		Goal goalCP= CPModelTool.createGoal(GoalOperatorEnum.MIN, goal.getId(), goalExpression, 0); //0 is the default priority as here we are not using an optimisation requirement for the creation of the goal  
		
		cp.getGoals().add(goalCP); 
			
	}
	
	
	protected ComposedExpression createMultiplication(ConstraintProblem cp, Variable v, String appId)
	{
		
		String providerId= CPModelTool.getProviderRelatedToVariable(v);
		
		String vmId= CPModelTool.getVmProfileRelatedToVariable(v); 
		//System.out.print("appId: "+appId+" providerId "+providerId+" vmId "+vmId);
		ProviderModel pm= database.loadPM(appId, providerId, vmId); 
		
		double rate= manager.estimatePrice(pm); 
		Constant price=CPModelTool.createDoubleConstant(rate, CPModelTool.getConstantName()); 
		cp.getConstants().add(price); 
		
		
		ComposedExpression aux= CPModelTool.createComposedExpression(OperatorEnum.TIMES,CPModelTool.getAuxExpressionId());
		cp.getAuxExpressions().add(aux); 
		
		aux.getExpressions().add(v); 
		
		aux.getExpressions().add(price); 
		
		
		
		return aux; 
		
	}


	@Override
	public void setDatabaseProxy(IDatabaseProxy proxy) 
	{
		this.database= proxy; 
		
		
	}

}
