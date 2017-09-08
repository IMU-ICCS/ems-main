package eu.paasage.upperware.profiler.cp.generator.model.derivator.lib;

import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EcoreUtil;

import eu.paasage.upperware.metamodel.cp.ComposedExpression;
import eu.paasage.upperware.metamodel.cp.ConfigurationUpperware;
import eu.paasage.upperware.metamodel.cp.Constant;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpFactory;
import eu.paasage.upperware.metamodel.cp.DeltaUtility;
import eu.paasage.upperware.metamodel.cp.Goal;
import eu.paasage.upperware.metamodel.cp.NormalisedUtilityDimension;
import eu.paasage.upperware.metamodel.cp.OperatorEnum;
import eu.paasage.upperware.metamodel.cp.Parameter;
import eu.paasage.upperware.metamodel.types.BasicTypeEnum;
import eu.paasage.upperware.metamodel.types.DoubleValueUpperware;
import eu.paasage.upperware.metamodel.types.TypesFactory;
import eu.paasage.upperware.profiler.cp.generator.model.lib.GenerationOrchestrator;

@Slf4j
public class DeltaFunctionDerivator 
{
	
	private static final String DELTA_UTILITY_NAME= "delta_utility_functions";
	
	private static final String SOLUTION_A_NAME= "solution_a";
	
	private static final String SOLUTION_B_NAME= "solution_b";
	
	private static final String NORMALISED_UTILITY_FUNCTION_SUFFIX= "_nud";
	
	private static final String CONFIGURATION_SUFFIX= "_config";
	
	private static final String CONFIGURATION_A_SUFFIX= CONFIGURATION_SUFFIX+"_a";
	
	private static final String CONFIGURATION_B_SUFFIX= CONFIGURATION_SUFFIX+"_b";
	
	private static final String MAX_SUFFIX= "_max";
	
	private static final String MIN_SUFFIX= "_min";
	
	private static final String SUBSTRACTION_SUFFIX= "_subs";
	
	private static final String MULTIPLICATION_SUFFIX= "_mult";
	
	private static final String DIVISION_SUFFIX= "_div";
	
	private static final String PRIORITY_SUFFIX= "_priority_constant";
	
	private static final String TOTAL_PRIORITY= "total_"+PRIORITY_SUFFIX;
	
	/*
	 * ATTRIBUTES
	 */
	
	/*
	 * Factory of Cp objects
	 */
	protected CpFactory cpFactory; 
	
	
	public DeltaFunctionDerivator()
	{
		cpFactory= CpFactory.eINSTANCE;
	}
	
	public void createDeltaFunction(ConstraintProblem cp)
	{
		DeltaUtility deltaUtility= CpFactory.eINSTANCE.createDeltaUtility();
		deltaUtility.setId(DELTA_UTILITY_NAME);
		cp.setDeltaUtility(deltaUtility);
		
		//Adding the related solutions as parameters. They have to be defined by someone else
		log.debug("DeltaFunctionDerivator - createDeltaFunction - Creating parameters"); 
		Parameter parameterA= cpFactory.createParameter();
		parameterA.setName(SOLUTION_A_NAME);
		
		deltaUtility.getSolutions().add(parameterA);	
		
		Parameter parameterB= cpFactory.createParameter();
		parameterB.setName(SOLUTION_B_NAME);
		
		deltaUtility.getSolutions().add(parameterB);
		
		deltaUtility.setOperator(OperatorEnum.PLUS);
		
		
		//Creation of the total weight constant
		log.debug("DeltaFunctionDerivator - createDeltaFunction - Creating Priority Constant"); 
		double total= computeTotalPriorityWeight(cp.getGoals());
		Constant totalPrioroty= cpFactory.createConstant();
		
		DoubleValueUpperware totalValue= TypesFactory.eINSTANCE.createDoubleValueUpperware();
		totalValue.setValue(total);
		
		totalPrioroty.setId(TOTAL_PRIORITY);
		totalPrioroty.setType(BasicTypeEnum.DOUBLE);
		totalPrioroty.setValue(totalValue);
		
		cp.getConstants().add(totalPrioroty);
		
		
		for(Goal goal:cp.getGoals())
		{
			createNormalisedUtilityDimension(cp, goal, totalPrioroty);
		}
		
		Diagnostic diagnostic = Diagnostician.INSTANCE.validate(deltaUtility);
		
		log.debug("DeltaFunctionDerivator - createDeltaFunction - Ended "+diagnostic); 
		
	}
	
	protected void createNormalisedUtilityDimension(ConstraintProblem cp, Goal goal, Constant totalPriorityWeight)
	{
		DeltaUtility utility= cp.getDeltaUtility();
		
		NormalisedUtilityDimension nud= cpFactory.createNormalisedUtilityDimension();
		
		nud.setGoal(goal);
		
		nud.getSolutions().add(utility.getSolutions().get(0));
		
		nud.getSolutions().add(utility.getSolutions().get(1));
		
		nud.setOperator(OperatorEnum.DIV);
		
		String goalExpressionID= goal.getExpression().getId();
		
		log.debug("DeltaFunctionDerivator - createNormalisedUtilityDimension - Creating NUD for "+goalExpressionID); 
		
		String nudId= goalExpressionID+NORMALISED_UTILITY_FUNCTION_SUFFIX;
		
		nud.setId(nudId);
		
		cp.getAuxExpressions().add(nud);
		
		//Goal's configurations
		
		ConfigurationUpperware configA= cpFactory.createConfigurationUpperware();
		
		String configAId= goalExpressionID+CONFIGURATION_A_SUFFIX;
		
		configA.setId(configAId);
		
		configA.setSolution(utility.getSolutions().get(0));
		
		configA.setGoal(goal);
		
		cp.getAuxExpressions().add(configA);
		
		ConfigurationUpperware configB= cpFactory.createConfigurationUpperware();
		
		String configBId= goalExpressionID+CONFIGURATION_B_SUFFIX;
		
		configB.setId(configBId);
		
		configB.setSolution(utility.getSolutions().get(1));
		
		configB.setGoal(goal);
		
		cp.getAuxExpressions().add(configB);
		
		
		//Max and Min
		ComposedExpression maxAB= cpFactory.createComposedExpression();
		
		maxAB.setId(configAId+"_"+configBId+MAX_SUFFIX);
		
		maxAB.setOperator(OperatorEnum.MAX);
		
		maxAB.getExpressions().add(configA);
		
		maxAB.getExpressions().add(configB);
		
		cp.getAuxExpressions().add(maxAB);
		
		ComposedExpression minAB= cpFactory.createComposedExpression();
		
		minAB.setId(configAId+"_"+configBId+MIN_SUFFIX);
		
		minAB.setOperator(OperatorEnum.MIN);
		
		minAB.getExpressions().add(configA);
		
		minAB.getExpressions().add(configB);
		
		cp.getAuxExpressions().add(minAB);
		
		ComposedExpression subsAB= cpFactory.createComposedExpression();
		
		subsAB.setId(configAId+"_"+configBId+SUBSTRACTION_SUFFIX);
		
		subsAB.setOperator(OperatorEnum.MINUS);
		
		subsAB.getExpressions().add(maxAB);
		
		subsAB.getExpressions().add(minAB);
		
		cp.getAuxExpressions().add(subsAB);
		
		
		//NUD B.x/(max(A,B)-min(A,B))
		
		nud.getExpressions().add(configB);
		nud.getExpressions().add(subsAB);
		
		//Priority
		
		Constant priority= cpFactory.createConstant();
		
		priority.setId(goalExpressionID+PRIORITY_SUFFIX);
		
		priority.setType(BasicTypeEnum.DOUBLE);
		
		DoubleValueUpperware value= TypesFactory.eINSTANCE.createDoubleValueUpperware();
		
		value.setValue(goal.getPriority());
		
		priority.setValue(value);
		
		cp.getConstants().add(priority);
		
		//NUD*(PRIORITY/total)
		
		ComposedExpression div= cpFactory.createComposedExpression();
		
		div.setId(nudId+DIVISION_SUFFIX);
		
		div.setOperator(OperatorEnum.DIV);
		
		div.getExpressions().add(priority);
		
		div.getExpressions().add(totalPriorityWeight);
		
		cp.getAuxExpressions().add(div);
		
		ComposedExpression mult= cpFactory.createComposedExpression();
		
		mult.setId(nudId+MULTIPLICATION_SUFFIX);
		mult.setOperator(OperatorEnum.TIMES);
		
		mult.getExpressions().add(div);
		
		mult.getExpressions().add(nud);
		
		cp.getAuxExpressions().add(mult);
		
		
		utility.getExpressions().add(mult);
		
	}
	
	
	protected double computeTotalPriorityWeight(EList<Goal> goals)
	{
		double total= 0;
		
		for(Goal goal:goals)
		{
			total+=goal.getPriority();
		}
		
		return total;
	}

}
