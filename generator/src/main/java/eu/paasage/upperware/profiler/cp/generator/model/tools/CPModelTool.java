/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.profiler.cp.generator.model.tools;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import eu.paasage.upperware.metamodel.application.ApplicationComponent;
import eu.paasage.upperware.metamodel.cp.BooleanDomain;
import eu.paasage.upperware.metamodel.cp.ComposedExpression;
import eu.paasage.upperware.metamodel.cp.Constant;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpFactory;
import eu.paasage.upperware.metamodel.cp.Goal;
import eu.paasage.upperware.metamodel.cp.GoalOperatorEnum;
import eu.paasage.upperware.metamodel.cp.MetricVariable;
import eu.paasage.upperware.metamodel.cp.MetricVariableValue;
import eu.paasage.upperware.metamodel.cp.NumericDomain;
import eu.paasage.upperware.metamodel.cp.NumericExpression;
import eu.paasage.upperware.metamodel.cp.OperatorEnum;
import eu.paasage.upperware.metamodel.cp.Solution;
import eu.paasage.upperware.metamodel.cp.Variable;
import eu.paasage.upperware.metamodel.cp.VariableValue;
import eu.paasage.upperware.metamodel.tool.UpperwareModelTool;
import eu.paasage.upperware.metamodel.types.BasicTypeEnum;
import eu.paasage.upperware.metamodel.types.DoubleValueUpperware;
import eu.paasage.upperware.metamodel.types.FloatValueUpperware;
import eu.paasage.upperware.metamodel.types.IntegerValueUpperware;
import eu.paasage.upperware.metamodel.types.LongValueUpperware;
import eu.paasage.upperware.metamodel.types.NumericValueUpperware;
import eu.paasage.upperware.metamodel.types.TypesFactory;
import eu.paasage.upperware.profiler.cp.generator.model.derivator.lib.CPModelDerivator;

public class CPModelTool {
	
	private static final String SEPARATOR="_"; 
	
	private static final String CONSTANT_PREFIX ="constant_"; 
	
	private static final String AUX_EXPRESSION_PREFIX= "aux_expression_"; 
	
	public static final String ONE_CONSTANT ="one_constant"; 
	
	public static final String CERO_CONSTANT ="cero_constant"; 
	
	private static final String VM_PROFILE_CONSTANT_PREFIX= "number_vm_"; 
	
	
	
	/*
	 * Counter for aux expressions
	 */
	public static int auxExpressionCount=0; 
	
	/*
	 * Counter for constants 
	 */
	public static int constantCount=0; 
	
	/**
	 * Searches all the variables related to a given application component
	 * @param ac The application component
	 * @param cp The constraint problem for searching the variables
	 * @return A list of variables related to the application component
	 */
	public static List<Variable> getVariablesRelatedToAppComponent(ApplicationComponent ac, ConstraintProblem cp)
	{
		String componentName= ac.getCloudMLId(); 
		
		List<Variable> vars= new ArrayList<Variable>(); 
		
		for(Variable var: cp.getVariables())
		{
			if(var.getId().contains(componentName))
				vars.add(var); 
		}
		
		return vars; 
	}
	
	/**
	 * Searches a constant in a list with a provided name
	 * @param constants The list of constants
	 * @param value The constant value
	 * @return The constant or null if it does not exist
	 */
	public static Constant searchConstantByValue(EList<Constant> constants, int value)
	{
		for(Constant c: constants)
		{
			List<String> info =CPModelTool.getValueFromNumericValue(c.getValue()); 
						
			if(info.get(1).equals(Integer.class.getCanonicalName()) && Integer.parseInt(info.get(0))==value)		
			{	
				
				return c; 
			}	
		}
		
		return null; 
	}
	
	public static Constant createIntegerConstant(int value, String name)
	{
		Constant constant= CpFactory.eINSTANCE.createConstant(); 
		constant.setId(name); 
		constant.setType(BasicTypeEnum.INTEGER); 
		IntegerValueUpperware val= TypesFactory.eINSTANCE.createIntegerValueUpperware(); 
		val.setValue(value); 
		constant.setValue(val); 
		
		return constant; 
	}
	
	public static Constant createDoubleConstant(double value, String name)
	{
		Constant constant= CpFactory.eINSTANCE.createConstant(); 
		constant.setId(name); 
		constant.setType(BasicTypeEnum.DOUBLE); 
		DoubleValueUpperware val= TypesFactory.eINSTANCE.createDoubleValueUpperware(); 
		val.setValue(value); 
		constant.setValue(val); 
		
		return constant; 
	}
	
	/**
	 * Createsa metric variable
	 * @param metricID Metric Id 
	 * @param type The metric type
	 * @param cp The constraint problem
	 * @return The metric variable
	 */
	public static MetricVariable createMetricVariable(String metricID, BasicTypeEnum type, ConstraintProblem cp)
	{
	
		MetricVariable metric= CpFactory.eINSTANCE.createMetricVariable(); 
	
		metric.setId(metricID); 
		metric.setType(type); 
		DoubleValueUpperware value= TypesFactory.eINSTANCE.createDoubleValueUpperware(); //TODO SET THE VALUE OF THE METRIC!!!
		value.setValue(1); 
	
		CPModelTool.assignNumericValue(value, metric, cp);
		
		return metric; 
	}
	
	public static String getMetricId(String camelMetricId, String vmId, String providerId)
	{
		return  camelMetricId+SEPARATOR+vmId+SEPARATOR+providerId; 
	}
	
	/**
	 * Provides the aux expression id  
	 * @return AUX_EXPRESSION_PREFIX+auxExpressionCount
	 */
	public static String getAuxExpressionId()
	{
		String name= AUX_EXPRESSION_PREFIX+auxExpressionCount; 
		
		auxExpressionCount++; 
		return name; 
		
	}
	
	public static ComposedExpression createComposedExpression(OperatorEnum op, String id)
	{
		ComposedExpression exp= CpFactory.eINSTANCE.createComposedExpression(); 
		exp.setOperator(op);
		
		exp.setId(id);
		
		return exp; 
	}
	
	public static Goal createGoal(GoalOperatorEnum type, String goalId, NumericExpression exp)
	{
		Goal goal= CpFactory.eINSTANCE.createGoal(); 
		
		
		goal.setGoalType(type); 
								
		goal.setExpression(exp); 
		
		goal.setId(goalId); 
				
		return goal; 
	}
	
	/**
	 * Provides a constant name
	 * @return CONSTANT_PREFIX+constantCount
	 */
	public static  String getConstantName()
	{
		String name= CONSTANT_PREFIX+constantCount; 
		
		constantCount++; 
		
		return name; 
		
	}
	
	/**
	 * Searches a constant in a list with a provided name
	 * @param constants The list of constants
	 * @param name The variable name
	 * @return The constant or null if it does not exist
	 */
	public static Constant searchConstantByName(EList<Constant> constants, String name)
	{
		for(Constant c: constants)
		{
			if(c.getId().equals(name))
				return c; 
		}
		
		return null; 
	}
	
	/**
	 * Searches a variable in a list considering a given suffix
	 * @param variables The list of variables
	 * @param name The suffix
	 * @return The variable related to the suffix or null if it does not exist
	 */
	public static Variable searchVariableByVMName(List<Variable> variables, String name)
	{
		for(Variable var: variables)
		{
			if(var.getId().contains(name))
			{
				return var; 
			}
		}
		
		return null; 
	}
	
	public static void assignNumericValue(String val, Variable var, Solution solution)
	{
		
		VariableValue varValue= CpFactory.eINSTANCE.createVariableValue(); 
		
		varValue.setVariable(var);
		
		NumericValueUpperware theValue= null; 
		
		if(var.getDomain() instanceof BooleanDomain)
		{
			int intVal= Integer.parseInt(val); 
			
			IntegerValueUpperware intValUpperware= TypesFactory.eINSTANCE.createIntegerValueUpperware(); 
			
			intValUpperware.setValue(intVal);
			
			theValue= intValUpperware; 

		}
		else
		{	
			NumericDomain domain= (NumericDomain) var.getDomain(); 
			
			int type= domain.getType().getValue(); 
	
			if(type==BasicTypeEnum.INTEGER_VALUE)
			{
				int intVal= Integer.parseInt(val); 
				
				IntegerValueUpperware intValUpperware= TypesFactory.eINSTANCE.createIntegerValueUpperware(); 
				
				intValUpperware.setValue(intVal);
				
				theValue= intValUpperware; 
				
			}
			else if(type== BasicTypeEnum.LONG_VALUE)
			{
				long longVal= Long.parseLong(val); 
				
				LongValueUpperware longValUpperware= TypesFactory.eINSTANCE.createLongValueUpperware(); 
				
				longValUpperware.setValue(longVal);
				
				theValue= longValUpperware; 
			}
			else if(type== BasicTypeEnum.FLOAT_VALUE)
			{
				float floatVal= Float.parseFloat(val); 
				
				FloatValueUpperware floatValUpperware= TypesFactory.eINSTANCE.createFloatValueUpperware(); 
				
				floatValUpperware.setValue(floatVal);
				
				theValue=floatValUpperware; 
			}
			else if(type== BasicTypeEnum.DOUBLE_VALUE)
			{
				double doubleVal= Double.parseDouble(val); 
				
				DoubleValueUpperware doubleValUpperware=  TypesFactory.eINSTANCE.createDoubleValueUpperware(); 
				
				doubleValUpperware.setValue(doubleVal);
				
				theValue= doubleValUpperware; 
			}
			else
				System.err.println("UpperwareModelTool - assignValue - Unknown type "+type+" The value can not be assigned!");
			
		}	
		
		
		if(theValue!=null)
		{
			varValue.setValue(theValue);
			solution.getVariableValue().add(varValue); 
		}
	}
	
	
	public static void assignNumericValue(NumericValueUpperware val, MetricVariable var, ConstraintProblem cp)
	{
		
		Solution sol= null; 
		
		if(cp.getSolution().size()==0)
		{
			sol= CpFactory.eINSTANCE.createSolution();
			
			sol.setTimestamp(System.currentTimeMillis());
			
			cp.getSolution().add(sol); 
		}
		else
			sol= cp.getSolution().get(cp.getSolution().size()-1);
		
		
		MetricVariableValue varValue= CpFactory.eINSTANCE.createMetricVariableValue(); 
		
		varValue.setVariable(var);
		
		varValue.setValue(val);
		
		sol.getMetricVariableValue().add(varValue); 
		
		
	}
	
	public static String getValueFromVar(Variable var, ConstraintProblem cp)
	{
		//Gets the last solution
		
		if(cp.getSolution().size()>0)
		{	
			Solution sol=searchLastSolution(cp.getSolution());
			
			VariableValue vv= getVariableValueByVariableId(var.getId(), sol); 
			
			if(vv!=null)
			{
				NumericValueUpperware valUpp= vv.getValue(); 
				
				if(valUpp instanceof IntegerValueUpperware)
				{
					IntegerValueUpperware intValUpperware= (IntegerValueUpperware) valUpp; 
					
					return Integer.toString(intValUpperware.getValue()); 
				}
				else if(valUpp instanceof LongValueUpperware)
				{
					LongValueUpperware longValUpperware= (LongValueUpperware) valUpp; 
					
					return Long.toString(longValUpperware.getValue());
				}
				else if(valUpp instanceof FloatValueUpperware)
				{
					FloatValueUpperware floatValUpperware= (FloatValueUpperware) valUpp; 
					
					return Float.toString(floatValUpperware.getValue());
				}
				else if(valUpp instanceof DoubleValueUpperware)
				{
					DoubleValueUpperware doubleValUpperware= (DoubleValueUpperware) valUpp; 
					
					return Double.toString(doubleValUpperware.getValue());
				}
				else
					System.err.println("ModelTool - assignValue - Unknown type "+var.getClass()+" The value can not be retrieved!");
			}
			else
				System.err.println("ModelTool - assignValue - Unknown val "+var.getId()+" The value can not be retrieved!");
		}
		else
		{
			System.err.println("ModelTool - assignValue - Unknown val "+var.getId()+" The value can not be retrieved!");
		}
		
		
		return null; 
	}
	
	public static VariableValue getVariableValueByVariableId(String id, Solution sol)
	{
		for(VariableValue vv:sol.getVariableValue())
		{
			if(vv.getVariable().getId().equals(id))
			{
				return vv; 
			}
		}
		
		return null; 
	}
	
	/**
	 * Gets the value from a given Numeric Value as a string
	 * @param value The value
	 * @return A list containing the value (0) and the canonical name of the type class (1)
	 */
	public static List<String> getValueFromNumericValue(NumericValueUpperware value)
	{
		List<String> info= new ArrayList<String>(); 
		
		String val= null; 
		
		if(value instanceof IntegerValueUpperware)
		{
			val= ((IntegerValueUpperware) value).getValue()+""; 
			info.add(val); 
			info.add(Integer.class.getCanonicalName()); 
		}
		else if(value instanceof FloatValueUpperware)
		{
			val= ((FloatValueUpperware) value).getValue()+""; 
			info.add(val); 
			info.add(Float.class.getCanonicalName()); 
		}
		else if(value instanceof DoubleValueUpperware)
		{
			val= ((DoubleValueUpperware) value).getValue()+""; 
			info.add(val); 
			info.add(Double.class.getCanonicalName()); 
		}
		else if(value instanceof LongValueUpperware)
		{
			val= ((LongValueUpperware) value).getValue()+"";
			info.add(val); 
			info.add(Long.class.getCanonicalName()); 
		}
		
		return info; 
	}
	
	public static Solution searchLastSolution(EList<Solution> solutions)
	{
		if(solutions.size()>0)
		{	
			Solution sol= solutions.get(0);
			
			long solDate= sol.getTimestamp(); 
			
			for(int i= 1; i<solutions.size();i++)
			{
				Solution temp= solutions.get(i);
				
				long tempDate= temp.getTimestamp(); 
			
				if(tempDate>solDate)
				{
					solDate= tempDate;
					sol= temp;
				}
						
			}
			return sol;
		
		}
		
		return null;
	}
	
	public static VariableValue searchVariableValue(Solution sol, Variable var)
	{
		if(sol.getVariableValue()!=null)
			for(VariableValue value:sol.getVariableValue())
			{
				if(value.getVariable().getId().equals(var.getId()))
					return value;
			}
		
		return null;
		
	}
	
	public static MetricVariableValue searchMetricValue(Solution sol, MetricVariable var)
	{
		if(sol.getMetricVariableValue()!=null)
			for(MetricVariableValue value:sol.getMetricVariableValue())
			{
				if(value.getVariable().getId().equals(var.getId()))
					return value;
			}
		
		return null;
		
	}
	
	public static MetricVariableValue searchMetricVariableValue(Solution sol, MetricVariable var)
	{

		for(MetricVariableValue value:sol.getMetricVariableValue())
		{
			if(value.getVariable().getId().equals(var.getId()))
				return value;
		}
		
		return null;
		
	}
	
	public static Solution createSolution(ConstraintProblem cp)
	{
		Solution sol= CpFactory.eINSTANCE.createSolution();
		sol.setTimestamp(System.currentTimeMillis());
		cp.getSolution().add(sol);
		
		return sol;
	}
	
	public static String getVMProfileConstantName(String vmpId)
	{
		return VM_PROFILE_CONSTANT_PREFIX+vmpId; 
	}
	
	public static String getProviderRelatedToVariable(Variable v)
	{
		int posSuffix= v.getId().indexOf(CPModelDerivator.APP_COMPONENT_VAR_SUFFIX); 
		
		String providerId= v.getId().substring(posSuffix+CPModelDerivator.APP_COMPONENT_VAR_SUFFIX.length()); 
		
		return providerId; 
	}
	
	public static String getVmProfileRelatedToVariable(Variable v)
	{
		int posPrefix= v.getId().indexOf(CPModelDerivator.APP_COMPONENT_VAR_MID); 
		
		int postSuffix= v.getId().indexOf(CPModelDerivator.APP_COMPONENT_VAR_SUFFIX); 
		
		String vmId= v.getId().substring(posPrefix+CPModelDerivator.APP_COMPONENT_VAR_MID.length(), postSuffix); 
		
		return vmId; 
	}

}
