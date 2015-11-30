package eu.paasage.upperware.profiler.cp.generator.model.derivator.lib;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.InternalComponent;
import eu.paasage.camel.metric.CompositeMetric;
import eu.paasage.camel.metric.CompositeMetricContext;
import eu.paasage.camel.metric.Metric;
import eu.paasage.camel.metric.MetricCondition;
import eu.paasage.camel.metric.MetricContext;
import eu.paasage.camel.metric.MetricFormula;
import eu.paasage.camel.metric.MetricFormulaParameter;
import eu.paasage.camel.metric.MetricFunctionType;
import eu.paasage.camel.metric.RawMetric;
import eu.paasage.camel.metric.RawMetricContext;
import eu.paasage.camel.requirement.OptimisationFunctionType;
import eu.paasage.camel.requirement.OptimisationRequirement;
import eu.paasage.camel.requirement.Requirement;
import eu.paasage.camel.requirement.RequirementModel;
import eu.paasage.camel.requirement.ServiceLevelObjective;
import eu.paasage.camel.type.DoublePrecisionValue;
import eu.paasage.camel.type.FloatsValue;
import eu.paasage.camel.type.IntegerValue;
import eu.paasage.camel.type.Range;
import eu.paasage.camel.type.TypeEnum;
import eu.paasage.upperware.metamodel.cp.ComparatorEnum;
import eu.paasage.upperware.metamodel.cp.ComparisonExpression;
import eu.paasage.upperware.metamodel.cp.ComposedExpression;
import eu.paasage.upperware.metamodel.cp.Constant;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpFactory;
import eu.paasage.upperware.metamodel.cp.Expression;
import eu.paasage.upperware.metamodel.cp.Goal;
import eu.paasage.upperware.metamodel.cp.GoalOperatorEnum;
import eu.paasage.upperware.metamodel.cp.MetricVariable;
import eu.paasage.upperware.metamodel.cp.MetricVariableValue;
import eu.paasage.upperware.metamodel.cp.NumericExpression;
import eu.paasage.upperware.metamodel.cp.OperatorEnum;
import eu.paasage.upperware.metamodel.cp.Solution;
import eu.paasage.upperware.metamodel.cp.Variable;
import eu.paasage.upperware.metamodel.types.BasicTypeEnum;
import eu.paasage.upperware.metamodel.types.DoubleValueUpperware;
import eu.paasage.upperware.metamodel.types.NumericValueUpperware;
import eu.paasage.upperware.metamodel.types.TypesFactory;
import eu.paasage.upperware.profiler.cp.generator.model.lib.GenerationOrchestrator;
import eu.paasage.upperware.profiler.cp.generator.model.tools.CPModelTool;

public class DimensionDerivator 
{
	
	protected static BasicTypeEnum DEFAULT_METRIC_TYPE= BasicTypeEnum.DOUBLE;
	
	protected static double DEFAULT_METRIC_VALUE= 1; 
	
	/*
	 * The logger
	 */
	protected static Logger logger= GenerationOrchestrator.getLogger(); 
	
	
	protected static String NUMBER_OF_VMS_SUBSTRING= "Instance"; 
	
	protected static String CONSTRAINTS_SUFFIX= "_constraint"; 
	
	public void createDimensions(CamelModel camel, ConstraintProblem cp, List<OptimisationRequirement> complexOptRequirements)
	{
		logger.debug("DimensionDerivator - createDimensions 1");
		RequirementModel reqs= camel.getRequirementModels().get(0); 
		logger.debug("DimensionDerivator - createDimensions 2");
		
		//List<OptimisationRequirement> complexOptRequirements= new ArrayList<>();
		
		CPModelTool.createSolution(cp); //Creating a solution even if is not necessary
		 
		for(Requirement req: reqs.getRequirements())
		{
			logger.debug("DimensionDerivator - createDimensions 4");
			if(req instanceof OptimisationRequirement)
			{
				logger.debug("DimensionDerivator - createDimensions 5");
				OptimisationRequirement optReq= (OptimisationRequirement) req; 
								
				
				if(optReq.getMetric()!=null)
				{
					
					if(optReq.getMetricContext()!=null)
					{
						Metric metric= optReq.getMetric(); 
						MetricContext metricContext= optReq.getMetricContext(); 
						
						OptimisationFunctionType optimisationType= optReq.getOptimisationFunction();
						
						List<NumericExpression> expressions= new ArrayList<>();
						
						if(metric instanceof RawMetric)
						{	
							expressions=processRawMetric((RawMetric)metric, (RawMetricContext) metricContext, camel, cp);
						
							
							
//							if(!metric.getName().toLowerCase().contains(NUMBER_OF_VMS_SUBSTRING)) // The dimension is created only if it is not a "Instace" variable 
							
							/*for(InternalComponent ic:components)
							{
								createDimension((RawMetric) metric, cp, ic, optimisationType);
							}*/
							
							
								
						}
						else
						{
							complexOptRequirements.add(optReq);
							
							NumericExpression expression= processCompositeMetric((CompositeMetric) metric, (CompositeMetricContext) metricContext, camel, cp);
							
							if(expression!=null)
								expressions.add(expression);
							
						}
						
						for(NumericExpression exp: expressions)
						{
							createDimension(exp, cp, optimisationType);
						}
						
					}
					else
					{
						logger.warn("DimensionDerivator - createDimensions - The optimisation requirement "+optReq.getName()+"does not have a related metric context. The dimension will be not generated!");
					}

					
				}
				else
				{	
					logger.warn("DimensionDerivator - createDimensions - The optimisation requirement "+optReq.getName()+"does not have a related metric. The dimension will be not generated!");
				}	
				
				logger.debug("DimensionDerivator - createDimensions 7");
				
				
			}
				
		}
		
		

	}
	
	protected Variable createVariable(Metric metric, ConstraintProblem cp, InternalComponent ic)
	{
		String varName= CPModelTool.getUserVariableName(metric, ic);
		
		Variable var= null; 
		
		if(metric.getValueType()!=null && metric.getValueType() instanceof Range)
		{
			Range domain= (Range) metric.getValueType();
			
			
			
			if(domain.getPrimitiveType().getValue()==TypeEnum.INT_TYPE_VALUE)
			{
				IntegerValue lowerValue= (IntegerValue) domain.getLowerLimit().getValue();
				
				IntegerValue upperValue= (IntegerValue) domain.getUpperLimit().getValue();
						
				var= CPModelTool.createIntegerVariableWithRangeDomain(varName, lowerValue.getValue(), upperValue.getValue()); 
			}
			else if(domain.getPrimitiveType().getValue()==TypeEnum.DOUBLE_TYPE_VALUE)
			{
				DoublePrecisionValue lowerValue= (DoublePrecisionValue) domain.getLowerLimit().getValue();
				
				DoublePrecisionValue upperValue= (DoublePrecisionValue) domain.getUpperLimit().getValue();
						
				var= CPModelTool.createDoubleVariableWithRangeDomain(varName, lowerValue.getValue(), upperValue.getValue()); 
			}
			else if(domain.getPrimitiveType().getValue()==TypeEnum.FLOAT_TYPE_VALUE)
			{
				FloatsValue lowerValue= (FloatsValue) domain.getLowerLimit().getValue();
				
				FloatsValue upperValue= (FloatsValue) domain.getUpperLimit().getValue();
						
				var= CPModelTool.createDoubleVariableWithRangeDomain(varName, lowerValue.getValue(), upperValue.getValue()); 
			}
			
			if(var!=null)
				cp.getVariables().add(var); 
			else
				logger.warn("DimensionDerivator - createDimensions - The variable  "+varName+" cannot be created with the provided domain!");	
		}
		else
			logger.warn("DimensionDerivator - createDimensions - The variable  "+varName+" cannot be created with the provided domain!");
		
		return var;
	}
	
	protected MetricVariable createMetricVariable(Metric metric, ConstraintProblem cp, InternalComponent ic)
	{
		String metricName= CPModelTool.getUserVariableName(metric, ic);
		
		MetricVariable metricVar= CPModelTool.createMetricVariable(metricName, DEFAULT_METRIC_TYPE, cp); //Here I assuming that the basic type is always double
		
		//cp.getMetricVariables().add(metricVar);
		
		return metricVar; 
		
	}
	
	
	protected void createDimension(RawMetric metric, ConstraintProblem cp, InternalComponent ic, OptimisationFunctionType goal)
	{
		GoalOperatorEnum theGoal= GoalOperatorEnum.MIN;
		
		if(goal.getValue()==OptimisationFunctionType.MAXIMISE_VALUE)
		{
			theGoal= GoalOperatorEnum.MAX;
		}
			
		String varName= CPModelTool.getUserVariableName(metric, ic); 	
		
		Variable var= CPModelTool.searchVariableByName(varName, cp.getVariables());
		
		if(var!=null)
		{
			Goal goalCP= CPModelTool.createGoal(theGoal, theGoal.getLiteral(), var); 
			
			cp.getGoals().add(goalCP); 
		}
		else
			logger.warn("DimensionDerivator - createDimension - The dimension for the metric  "+varName+" cannot be created!");
	}
	
	protected void createDimension(NumericExpression exp, ConstraintProblem cp, OptimisationFunctionType goal)
	{
		GoalOperatorEnum theGoal= GoalOperatorEnum.MIN;
		
		if(goal.getValue()==OptimisationFunctionType.MAXIMISE_VALUE)
		{
			theGoal= GoalOperatorEnum.MAX;
		}
				
		
		Goal goalCP= CPModelTool.createGoal(theGoal, theGoal.getLiteral(), exp); 
		
		cp.getGoals().add(goalCP); 

	}
	
	protected void createValueInSolutionForMetricVariable(RawMetric metric, ConstraintProblem cp, InternalComponent ic, Solution solution)
	{
		
		DoubleValueUpperware actualVal= TypesFactory.eINSTANCE.createDoubleValueUpperware();
		
		actualVal.setValue(DEFAULT_METRIC_VALUE);
		
		createValueInSolutionForMetricVariable(metric, cp, ic, solution, actualVal);
	}
	
	protected void createValueInSolutionForMetricVariable(RawMetric metric, ConstraintProblem cp, InternalComponent ic, Solution solution, NumericValueUpperware actualVal)
	{
		String varName= CPModelTool.getUserVariableName(metric, ic);
		
		MetricVariable var= CPModelTool.searchMetricVariableByName(varName, cp.getMetricVariables());
		
		if(var!=null)
		{
			CPModelTool.assignNumericValue(actualVal, var, solution);
/*			MetricVariableValue value= CpFactory.eINSTANCE.createMetricVariableValue();
			
			value.setVariable(var);
		
			value.setValue(actualVal);
			
			solution.getMetricVariableValue().add(value);*/
		}
		
	}
	
	protected List<NumericExpression> processRawMetric(RawMetric metric, RawMetricContext metricContext, CamelModel camel, ConstraintProblem cp)
	{
		List<NumericExpression> expressions= new ArrayList<NumericExpression>();
		List<InternalComponent> components = new ArrayList<>();
		
		
		//The context is a raw context - It is mandatory
		if(metricContext.getComponent()!=null)
		{
			components.add((InternalComponent) metricContext.getComponent());
		}
		else
		{
			components.addAll(camel.getApplications().get(0).getDeploymentModels().get(0).getInternalComponents());
		}
		
		if(metric.isIsVariable() && !metric.getName().toLowerCase().contains(NUMBER_OF_VMS_SUBSTRING.toLowerCase()))
		{
			for(InternalComponent ic: components)
			{
				String varName= CPModelTool.getUserVariableName(metric, ic); 
				
				Variable var= CPModelTool.searchVariableByName(varName, cp.getVariables());  
				
				if(var==null)
						var= createVariable(metric, cp, ic);
				
				if(var!=null)
					expressions.add(var);
			}
			
			
		}
		else if(metric.isIsVariable())
		{
			List<Variable> variables= CPModelTool.getAllComponentInVMVariables(cp.getVariables());
			
			for(InternalComponent ic: components)
			{	
				List<Variable> varsRelated= CPModelTool.getVariablesRelatedToAppComponent(ic.getName(), variables);
				
				expressions.addAll(varsRelated);
			}	
		}
		else //if(!metric.isIsVariable()) // It is a metric
		{
			Solution sol= CPModelTool.searchLastSolution(cp.getSolution());  
			
			for(InternalComponent ic: components)
			{
				String metricName= CPModelTool.getUserVariableName(metric, ic); 
				
				MetricVariable metricVar= CPModelTool.searchMetricVariableByName(metricName, cp.getMetricVariables()); 
				
				
				if(metricVar==null)
				{	
					metricVar= createMetricVariable(metric, cp, ic);
					
					if(metricVar!=null) //A solution is required
					{						
						if(sol==null)
						{	
							System.out.println("Creating solution for metric var "+metricVar.getId());
							sol= CPModelTool.createSolution(cp);
						}
						createValueInSolutionForMetricVariable((RawMetric) metric, cp, ic, sol);
					}
				}	
				
				if(metricVar!=null)
					expressions.add(metricVar);
			}
		}
		
		return expressions;
	}
	
	protected NumericExpression processCompositeMetric(CompositeMetric metric, CompositeMetricContext context, CamelModel camel, ConstraintProblem cp)
	{
		MetricFormula formula= metric.getFormula();
		
		return processFormula(metric, formula, context, camel, cp);
		
	}
	
	protected NumericExpression processFormula(CompositeMetric parentMetric, MetricFormula formula, CompositeMetricContext context, CamelModel camel, ConstraintProblem cp)
	{
		List<NumericExpression> expressions= new ArrayList<>();
		NumericExpression exp= null;
		
		for(MetricFormulaParameter parameter:formula.getParameters())
		{
			if(parameter instanceof Metric)
			{
				Metric theMetric= (Metric) parameter;
				MetricContext theContext= null;
				
				EList<MetricContext> contexts= context.getComposingMetricContexts();
				
				for(int i=0; i<contexts.size() && theContext==null; i++)
				{
					theContext= searchForMetricContext((Metric) parameter, context);
				}
						
				
				if(theContext!=null)
				{
					if(theMetric instanceof RawMetric)
						expressions.addAll(processRawMetric((RawMetric) theMetric, (RawMetricContext) theContext, camel, cp));
					else
						expressions.add(processCompositeMetric((CompositeMetric) theMetric, (CompositeMetricContext) theContext, camel, cp));
				}
				else
				{
					logger.warn("DimensionDerivator - processFormula - The metric "+theMetric.getName()+" does not have a context. It will be ignored!");
				}
				
			}
			else
				expressions.add(processFormula(null, (MetricFormula) parameter, context, camel, cp));
		}
		
		OperatorEnum operator= CPModelTool.getOperatorEnumFromMetricFunctionType(formula.getFunction());
		
		if(operator!=null)
		{
			String formulaId= CPModelTool.getUserExpressionName(parentMetric, formula); 
			if((expressions.size()>=1 && formula.getFunction().getValue()!=MetricFunctionType.MEAN_VALUE) || expressions.size()==1)
			{	
				ComposedExpression ce= CPModelTool.createComposedExpression(operator, formulaId);
				
				cp.getAuxExpressions().add(ce);
				
				ce.getExpressions().addAll(expressions);
				
				exp= ce;
			}
			else if(expressions.size()>1) //It is a mean value
			{
				ComposedExpression ce= CPModelTool.createComposedExpression(OperatorEnum.PLUS, formulaId+"_addition");
				
				cp.getAuxExpressions().add(ce);
				
				ce.getExpressions().addAll(expressions);
				
				ComposedExpression ce2= CPModelTool.createComposedExpression(OperatorEnum.DIV, formulaId);
				
				cp.getAuxExpressions().add(ce2); 
				
				ce.getExpressions().add(ce2);
				
				Constant cVal= CPModelTool.searchConstantByValue(cp.getConstants(), expressions.size()); 
				
				if(cVal==null)
				{
					cVal= CPModelTool.createIntegerConstant(expressions.size(), CPModelTool.getConstantName());
					
					cp.getConstants().add(cVal); 
				}
				
				ce.getExpressions().add(cVal); 
				
			}
			else
				logger.warn("DimensionDerivator - processFormula - There is not enough arguments for the formula "+ formula.getName()+", It will be not created!");
		}
		else
			logger.warn("DimensionDerivator - processFormula - The operator "+formula.getFunction().getName()+" is not currently supperted. The formula "+ formula.getName()+" will be not created!");
		
		return exp;
	}
	
	protected MetricContext searchForMetricContext(Metric metric, MetricContext metricContext)
	{
		MetricContext theContext= null;
		
		if(metricContext.getMetric().getName().equals(metric.getName()))
		{
			theContext= metricContext;
		}
		else if(metricContext instanceof CompositeMetricContext)
		{
			CompositeMetricContext compositeContext= (CompositeMetricContext) metricContext;
			
			for(int i= 0; i<compositeContext.getComposingMetricContexts().size() && theContext==null; i++)
			{
				theContext= searchForMetricContext(metric, compositeContext.getComposingMetricContexts().get(i));
			}
		}
		
		return theContext;
	}
	
	public void createConstraints(CamelModel camel, ConstraintProblem cp)
	{
		RequirementModel reqs= camel.getRequirementModels().get(0); 
		
		for(Requirement req:reqs.getRequirements())
		{
			if(req instanceof ServiceLevelObjective)
			{
				ServiceLevelObjective slo= (ServiceLevelObjective) req; 
				
				if(slo.getCustomServiceLevel() instanceof MetricCondition) //Only conditions related to metrics are considered
				{
					MetricCondition condition= (MetricCondition) slo.getCustomServiceLevel(); 
					
					createConstraint(camel,condition, cp);
				}
			}
		}
	}
	
	public void createConstraint(CamelModel camel, MetricCondition condition, ConstraintProblem cp)
	{
		double threshold= condition.getThreshold(); 
		
		
	
		ComparatorEnum op= CPModelTool.getComparatorEnumFromComparisonOperatorType(condition.getComparisonOperator()); 
		
		if(op!=null)
		{
			MetricContext context= condition.getMetricContext(); 
			Metric metric= context.getMetric(); 
			
			List<InternalComponent> components = new ArrayList<>();
			
			List<NumericExpression> expressions= new ArrayList<>(); 
			
			if(context instanceof RawMetricContext)
			{	
				//The context is a raw context - It is mandatory
				if(context.getComponent()!=null)
				{
					components.add((InternalComponent) context.getComponent());
				}
				else
				{
					components.addAll(camel.getApplications().get(0).getDeploymentModels().get(0).getInternalComponents());
				}
				
				for(InternalComponent ic:components)
				{	
					
					
					if(metric.isIsVariable() && metric.getName().toLowerCase().contains(NUMBER_OF_VMS_SUBSTRING.toLowerCase())) //Search for the number of vms variable
					{	
						List<Variable> variables= CPModelTool.getAllComponentInVMVariables(cp.getVariables());
						
						List<Variable> varsRelated= CPModelTool.getVariablesRelatedToAppComponent(ic.getName(), variables);
						
						expressions.addAll(varsRelated);
						
						if(varsRelated.size()==0)
						{
							logger.warn("DimensionDerivator - createConstraint - VM variables does not exist for the component "+ic.getName()+". The condition "+condition.getName()+" will be no used to create a constraint related to it!");
						}
							
						
					}
					else if(metric.isIsVariable()) //It is a normal variable
					{
						String varName= CPModelTool.getUserVariableName(context.getMetric(), ic); 
						
						Variable var= CPModelTool.searchVariableByName(varName, cp.getVariables());
						
						if(var!=null)
						{
							expressions.add(var); 
						}
						else
							logger.warn("DimensionDerivator - createConstraint - The variable  "+varName+" does not exist. The condition "+condition.getName()+" will be no used to create a constraint related to it!");
					}
					else //It is a metric
					{
						String metricName= CPModelTool.getUserVariableName(context.getMetric(), ic); 
						
						MetricVariable metricVar= CPModelTool.searchMetricVariableByName(metricName, cp.getMetricVariables());
						
						if(metricVar!=null)
						{
							expressions.add(metricVar); 
						}
						else
							logger.warn("DimensionDerivator - createConstraint - The metric  "+metricName+" does not exist. The condition "+condition.getName()+" will be no used to create a constraint related to it!");
					}
					
				}	
				
			}
			else //It's a composite metric
			{
				CompositeMetric theMetric= (CompositeMetric) metric; 
				
				String expId= CPModelTool.getUserExpressionName(theMetric, theMetric.getFormula());  
				
				Expression exp= CPModelTool.searchExpressionByName(expId, cp.getAuxExpressions());
				
				if(exp==null)
				{
					exp= CPModelTool.searchExpressionByNameInGoals(expId, cp.getGoals()); 
				}
				
				if(exp==null)
				{
					exp= processCompositeMetric(theMetric, (CompositeMetricContext) context, camel, cp); 
				}
				
				if(exp!=null)
				{
					expressions.add((NumericExpression) exp);
				}
				else
					logger.warn("DimensionDerivator - createConstraint - The composite metric with formula  "+expId+" does not exist. The condition "+condition.getName()+" will be no used to create a constraint related to it!");
			}
			
			if(expressions.size()>0)
			{
				Constant theConstant= CPModelTool.searchConstantByValue(cp.getConstants(), threshold); 
				
				if(theConstant==null)
				{
					theConstant= CPModelTool.createDoubleConstant(threshold, CPModelTool.getConstantName()); 
					
					cp.getConstants().add(theConstant); 
				}
				
				for(NumericExpression exp: expressions)
				{
					String constraintId= CPModelTool.getUserConstraintName(condition.getName(), exp.getId(), CONSTRAINTS_SUFFIX); 
					ComparisonExpression constraint= CPModelTool.createComparisonExpression(op, exp, theConstant, constraintId); 
					
					cp.getConstraints().add(constraint); 
				}
				
			}
			
		}
		else
			logger.warn("DimensionDerivator - createConstraint - The operator "+condition.getComparisonOperator().getName()+" is not currently supperted. The metric condition "+ condition.getName()+" cannot be translated into a constraint!");
		
	}
		

}



