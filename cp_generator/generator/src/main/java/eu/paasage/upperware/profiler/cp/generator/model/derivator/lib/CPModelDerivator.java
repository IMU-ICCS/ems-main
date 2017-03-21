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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.requirement.OptimisationRequirement;
import eu.paasage.upperware.metamodel.application.ApplicationComponent;
import eu.paasage.upperware.metamodel.application.ApplicationFactory;
import eu.paasage.upperware.metamodel.application.PaaSageGoal;
import eu.paasage.upperware.metamodel.application.PaaSageVariable;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.application.Provider;
import eu.paasage.upperware.metamodel.application.ProviderDimension;
import eu.paasage.upperware.metamodel.application.RequiredFeature;
import eu.paasage.upperware.metamodel.application.VirtualMachine;
import eu.paasage.upperware.metamodel.application.VirtualMachineProfile;
import eu.paasage.upperware.metamodel.cp.BooleanDomain;
import eu.paasage.upperware.metamodel.cp.ComparatorEnum;
import eu.paasage.upperware.metamodel.cp.ComparisonExpression;
import eu.paasage.upperware.metamodel.cp.ComposedExpression;
import eu.paasage.upperware.metamodel.cp.Constant;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpFactory;
import eu.paasage.upperware.metamodel.cp.GoalOperatorEnum;
import eu.paasage.upperware.metamodel.cp.MetricVariable;
import eu.paasage.upperware.metamodel.cp.NumericDomain;
import eu.paasage.upperware.metamodel.cp.NumericExpression;
import eu.paasage.upperware.metamodel.cp.NumericListDomain;
import eu.paasage.upperware.metamodel.cp.OperatorEnum;
import eu.paasage.upperware.metamodel.cp.Variable;
import eu.paasage.upperware.metamodel.types.BasicTypeEnum;
import eu.paasage.upperware.metamodel.types.DoubleValueUpperware;
import eu.paasage.upperware.metamodel.types.IntegerValueUpperware;
import eu.paasage.upperware.metamodel.types.TypesFactory;
import eu.paasage.upperware.metamodel.types.typesPaasage.FunctionType;
import eu.paasage.upperware.metamodel.types.typesPaasage.ProviderType;
import eu.paasage.upperware.metamodel.types.typesPaasage.VariableElementTypeEnum;
import eu.paasage.upperware.profiler.cp.generator.db.api.IDatabaseProxy;
import eu.paasage.upperware.profiler.cp.generator.model.derivator.api.ICPModelDerivator;
import eu.paasage.upperware.profiler.cp.generator.model.derivator.api.IEstimatorFactory;
import eu.paasage.upperware.profiler.cp.generator.model.derivator.api.IFunctionCreator;
import eu.paasage.upperware.profiler.cp.generator.model.lib.GenerationOrchestrator;
import eu.paasage.upperware.profiler.cp.generator.model.tools.CPModelTool;
import eu.paasage.upperware.profiler.cp.generator.model.tools.Constants;
import eu.paasage.upperware.profiler.cp.generator.model.tools.FileTool;
import eu.paasage.upperware.profiler.cp.generator.model.tools.PaasageModelTool;

/**
 * This class provides the functionality to create a constraint problem
 * @author danielromero
 *
 */
public class CPModelDerivator implements ICPModelDerivator 

{
	/*
	 * CONSTANTS
	 */
	//private static final String NUM_VM_FOR_APP_COMPONENT ="num_vm_for_app_component"; 
		
	private static final String MIN_NUM_VM ="min_num_vm"; 
	
	private static final String COST_METRIC_PREFIX= "vm_profile_cost_"; 
	
	private static final String NAME_SEPARATOR= "_"; 
	
	
	
	private static final String CONSTRAINT_PREFIX= "c_"; 
	
	private static final String VM_PROFILE_VAR_PREFIX= "number_vm_"; 
	
	//private static final String MIN_COST_FUNCT_PREFIX= "min_cost_"; 
	
	private static final String COST= "Cost"; 
	
	private static final int MAX_NUMBER_OF_VMS= 128; 
	
	//private static final int MIN_NUMBER_OF_VMS= 0; 
	
	
	
	
	
	
	/*
	 * ATTRIBUTES
	 */
	/*
	 * The logger
	 */
	protected static Logger logger= GenerationOrchestrator.getLogger(); 
	
	/*
	 * Map with the created factories
	 */
	protected Map<String,IEstimatorFactory> factoriesMap; 
	
	protected Map<String,IFunctionCreator> creatorsMap; 
	
	protected DimensionDerivator dimensionsDerivator; 
	
	/*
	 * Factory of Cp objects
	 */
	protected CpFactory cpFactory; 
	

	/*
	 * CONSTRUCTOR
	 */
	
	/**
	 * Creates the CPModelDerivator
	 * @param creatorsFile The file with the different creators
	 * @param database database proxy
	 */
	public CPModelDerivator(InputStream creatorsFile, IDatabaseProxy database)
	{
		cpFactory= CpFactory.eINSTANCE; 
		createFunctionCreators(creatorsFile, database);
		//createFactories(factoryFile); 
		dimensionsDerivator= new DimensionDerivator(); 
	}
	
	/*
	 * METHODS
	 * 
	 */
	/*
	 * (non-Javadoc)
	 * @see eu.paasage.upperware.profiler.cp.generator.model.derivator.api.ICPModelDerivator#derivateConstraintProblem(eu.paasage.upperware.metamodel.application.PaasageConfiguration, eu.paasage.upperware.profiler.cp.generator.db.api.IDatabaseProxy)
	 */
	public ConstraintProblem derivateConstraintProblem(CamelModel camel, PaasageConfiguration configuration, IDatabaseProxy database) 
	{
		logger.debug("CPModelDerivator - derivateConstraintProblem - Deriving CP "+configuration.getGoals().size()); 
		logger.info("** 	Derivating Constraint Problem Model"); 
		CPModelTool.auxExpressionCount= 0; 
		CPModelTool.constantCount= 0; 

		List<OptimisationRequirement> complexOptRequirements= new ArrayList<OptimisationRequirement>(); 
		
		//CP creation
		ConstraintProblem cp= cpFactory.createConstraintProblem(); 
		
		logger.info("** 		Creating constants"); 
		createConstants(cp, configuration); 
		
		logger.info("** 		Creating default variables"); 
		createVariables(cp, configuration); 
		
		logger.info("** 		Creating default constraints"); 
		createConstraints(cp, configuration); 
		
		logger.info("** 		Creating User objective functions "); 
		
		dimensionsDerivator.createDimensions(camel, cp, complexOptRequirements);
		
		if(complexOptRequirements.size()==0) // A default objective function is created
		{
			logger.debug("CPModelDerivator - derivateConstraintProblem - Cost! "); 
			//By default a function the price is created
			
			PaaSageGoal goal= ApplicationFactory.eINSTANCE.createPaaSageGoal();

			GoalOperatorEnum goalType= GoalOperatorEnum.MIN; 
			
			FunctionType ft= PaasageModelTool.getFunctionTypeByName(COST, database); 
			goal.setFunction(ft); 
			goal.setGoal(goalType); 
			goal.setId(goalType.getName()+ft.getId());
			
			configuration.getGoals().add(goal); 
			
			//Create the Cost creator
			CostFunctionCreator costCreator= new CostFunctionCreator(selectExistingPriceFile());
			costCreator.setDatabaseProxy(database);
			costCreator.createFunction(cp, goal);
			logger.debug("CPModelDerivator - derivateConstraintProblem - Cost function created! "); 
		}
		
		logger.info("** 		Creating User constraints "); 
		
		dimensionsDerivator.createConstraints(camel, cp);
		
/*		if(configuration.getGoals().size()>0)
		{	
			logger.debug("CPModelDerivator - derivateConstraintProblem - Goals! "); 
			for(PaaSageGoal goal:configuration.getGoals())
			{
				IFunctionCreator creator= creatorsMap.get(goal.getFunction().getId());
				
				logger.debug("CPModelDerivator - derivateConstraintProblem - Creator "+creator+ " id "+goal.getFunction().getId()); 
				
				creator.createFunction(cp, goal);
				
				logger.debug("CPModelDerivator - derivateConstraintProblem - Function created! "); 
			}
		}
		else
		{
			logger.debug("CPModelDerivator - derivateConstraintProblem - Cost! "); 
			//By default a function the price is created
			
			PaaSageGoal goal= ApplicationFactory.eINSTANCE.createPaaSageGoal();

			GoalOperatorEnum goalType= GoalOperatorEnum.MIN; 
			
			FunctionType ft= PaasageModelTool.getFunctionTypeByName(COST, database); 
			goal.setFunction(ft); 
			goal.setGoal(goalType); 
			goal.setId(goalType.getName()+ft.getId());
			
			configuration.getGoals().add(goal); 
			
			//Create the Cost creator
			CostFunctionCreator costCreator= new CostFunctionCreator(selectExistingPriceFile());
			costCreator.setDatabaseProxy(database);
			costCreator.createFunction(cp, goal);
			logger.debug("CPModelDerivator - derivateConstraintProblem - Cost function created! "); 
			
		}*/
		
		logger.debug("** 		CP Creation ended"); 
		//createObjetiveFunction(cp, configuration); 
		
		return cp;
	}
		
	/**
	 * Creates constraints for the constraint problem model
	 * @param cp The constraint problem being created
	 * @param configuration The paasage configuration
	 */
	protected void createConstraints(ConstraintProblem cp, PaasageConfiguration configuration)
	{
		int constraintsCount=0; 
		logger.debug("CPModelDerivator - createConstraints - 1"); 
		Constant ceroConstant= CPModelTool.searchConstantByName(cp.getConstants(), CPModelTool.CERO_CONSTANT); 
		logger.debug("CPModelDerivator - createConstraints - 2"); 
		logger.debug("CPModelDerivator - createConstraints - 3"); 
		Constant oneConstant= CPModelTool.searchConstantByName(cp.getConstants(), CPModelTool.ONE_CONSTANT); 
		logger.debug("CPModelDerivator - createConstraints - 4"); 
		
		Map<String,List<Variable>> differentLocations= new Hashtable<>(); 
		
		logger.debug("CPModelDerivator - createConstraints - 5"); 
		for(ApplicationComponent ac: configuration.getComponents())
		{
			logger.debug("CPModelDerivator - createConstraints - 6 component name "+ac.getCloudMLId()); 
			List<Variable> vars= CPModelTool.getVariablesRelatedToAppComponent(ac, cp); 
			logger.debug("CPModelDerivator - createConstraints - 7 Max instances "+ac.getMax()); 
			int numOfVMForComponent= ac.getMax(); //ac.getRequiredProfile().size(); 
			logger.debug("CPModelDerivator - createConstraints - 8 Min instances "+ac.getMin()); 
			int minVMNumber= ac.getMin();
			logger.debug("CPModelDerivator - createConstraints - 9"); 
			Constant numOfVMForComponentConstant= oneConstant; //BY DEFAULT, JUST ONE VM BY COMPONENT - IF THERE HOSTING RELATIONSHIP WITH A VM IN CAMEL //TODO HOW TO DETERMINE THE NUMBER OF INSTANCE OF A COMPONENT THAT DOES NOT HAVE HOSTING RELATIOSHIP WITH A VM?? 
			logger.debug("CPModelDerivator - createConstraints - 10"); 
			Constant minVMNumberConstant= oneConstant;
			logger.debug("CPModelDerivator - createConstraints - 11"); 
			if(numOfVMForComponent>1)
				numOfVMForComponentConstant= CPModelTool.searchConstantByValue(cp.getConstants(), numOfVMForComponent);
			logger.debug("CPModelDerivator - createConstraints - 12"); 
			
			if(numOfVMForComponentConstant==null)
			{
				
				numOfVMForComponentConstant=CPModelTool.createIntegerConstant(numOfVMForComponent, CPModelTool.getConstantName());//createIntConstant(numOfVMForComponent, cp);
				cp.getConstants().add(numOfVMForComponentConstant); 
			}
			
			logger.debug("CPModelDerivator - createConstraints - 13"); 
			if(minVMNumber>1)
				minVMNumberConstant= CPModelTool.searchConstantByValue(cp.getConstants(), minVMNumber); 
/*			else if(minVMNumber==1)
				minVMNumberConstant= oneConstant; */
			logger.debug("CPModelDerivator - createConstraints - 14"); 
			if(minVMNumberConstant==null)
			{
				minVMNumberConstant= CPModelTool.createIntegerConstant(minVMNumber, CPModelTool.getConstantName());//createIntConstant(minVMNumber, cp); 
				cp.getConstants().add(minVMNumberConstant); 
			}
			logger.debug("CPModelDerivator - createConstraints - 15"); 
			
			//c1: The number of vms for the component is less or equals to MAX
			ComparisonExpression ce= cpFactory.createComparisonExpression(); 
			ce.setId(CONSTRAINT_PREFIX+constraintsCount); 
			constraintsCount++; 
			logger.debug("CPModelDerivator - createConstraints - 16"); 
			ComparisonExpression ce2= cpFactory.createComparisonExpression(); 
			ce2.setId(CONSTRAINT_PREFIX+constraintsCount); 
			constraintsCount++; 
			logger.debug("CPModelDerivator - createConstraints - 17 "+vars); 
			if(vars.size()>1) //Create a sum with the concerned variables
			{	logger.debug("CPModelDerivator - createConstraints - 18"); 
				ComposedExpression sum= CPModelTool.createComposedExpression(OperatorEnum.PLUS, CPModelTool.getAuxExpressionId());//cpFactory.createComposedExpression(); 
				logger.debug("CPModelDerivator - createConstraints - 19"); 
				
				for(Variable var: vars)
					sum.getExpressions().add(var); 
				logger.debug("CPModelDerivator - createConstraints - 20"); 
				cp.getAuxExpressions().add(sum); 
				logger.debug("CPModelDerivator - createConstraints - 21"); 
				ce.setExp1(sum); 
				logger.debug("CPModelDerivator - createConstraints - 22"); 
				ce2.setExp1(sum);
				logger.debug("CPModelDerivator - createConstraints - 23"); 
				
			}
			else
			{
				logger.debug("CPModelDerivator - createConstraints - 24 "+ce+ " "+vars.get(0)); 
				ce.setExp1(vars.get(0)); 
				logger.debug("CPModelDerivator - createConstraints - 25"); 
				ce2.setExp1(vars.get(0));
				logger.debug("CPModelDerivator - createConstraints - 26"); 
			}
			
			logger.debug("CPModelDerivator - createConstraints - 27"); 
			ce.setExp2(numOfVMForComponentConstant); 
			logger.debug("CPModelDerivator - createConstraints - 28"); 
			ce.setComparator(ComparatorEnum.LESS_OR_EQUAL_TO);
			logger.debug("CPModelDerivator - createConstraints - 29"); 
			cp.getConstraints().add(ce); 
			logger.debug("CPModelDerivator - createConstraints - 30"); 
			
			
			//c2: The number of vms for the component is greater or equals to Min
			ce2.setExp2(minVMNumberConstant);
			logger.debug("CPModelDerivator - createConstraints - 31"); 
			ce2.setComparator(ComparatorEnum.GREATER_OR_EQUAL_TO);
			logger.debug("CPModelDerivator - createConstraints - 32"); 
			cp.getConstraints().add(ce2); 
			logger.debug("CPModelDerivator - createConstraints - 33"); 
			
			/*//c1: Each component is deployed in X VM
			ComparisonExpression ce= cpFactory.createComparisonExpression(); 
			
			ce.setId(CONSTRAINT_PREFIX+constraintsCount); 
			constraintsCount++; 
			
			if(vars.size()>1) //Create a sum with the concerned variables
			{
				ComposedExpression sum= cpFactory.createComposedExpression(); 
				
				sum.setId(getAuxExpressionId()); 
				
				
				
				sum.setOperator(OperatorEnum.PLUS); 
				
				for(Variable var: vars)
					sum.getExpressions().add(var); 
				
				cp.getAuxExpressions().add(sum); 
				
				ce.setExp1(sum); 
				
				
			}
			else
			{
				ce.setExp1(vars.get(0)); 
			}
			
			
			
			ce.setExp2(numOfVMForComponentConstant); 
			
			
			if(ac.getVm()!=null)
				ce.setComparator(ComparatorEnum.EQUAL_TO); 
			else
				ce.setComparator(ComparatorEnum.GREATER_OR_EQUAL_TO);
			
			cp.getConstraints().add(ce); */
			
			//Same and different location
			
			for(RequiredFeature rf:ac.getRequiredFeatures()) 
			{
				logger.debug("CPModelDerivator - createConstraints - 34");
				if(rf.getProvidedBy() instanceof ApplicationComponent)
				{
					logger.debug("CPModelDerivator - createConstraints - 35");
					ApplicationComponent required= (ApplicationComponent) rf.getProvidedBy(); 
					logger.debug("CPModelDerivator - createConstraints - 36");
					List<Variable> requiredVariables= CPModelTool.getVariablesRelatedToAppComponent(required, cp); 
					logger.debug("CPModelDerivator - createConstraints - 37");
					if(!rf.isRemote()) 
					{
						logger.debug("CPModelDerivator - createConstraints - 38");
						List<ComposedExpression> numericExpressions= new ArrayList<>(); 
						logger.debug("CPModelDerivator - createConstraints - 39");
						//SAME LOCATION
						//for(VirtualMachineProfile vmp:configuration.getVmProfiles())
						for(VirtualMachineProfile vmp:ac.getRequiredProfile())
						{
							logger.debug("CPModelDerivator - createConstraints - 40");
							List<VirtualMachine> vmInstances= getVirtualMachineInstancesByProfile(configuration.getVms(), vmp);
							logger.debug("CPModelDerivator - createConstraints - 41");
							for(VirtualMachine instance: vmInstances)
							{
								logger.debug("CPModelDerivator - createConstraints - 42");
								Variable v1= CPModelTool.searchVariableByVMName(vars, instance.getId()); 
								logger.debug("CPModelDerivator - createConstraints - 43");
								Variable v2= CPModelTool.searchVariableByVMName(requiredVariables, instance.getId()); 
								logger.debug("CPModelDerivator - createConstraints - 44");
								if(v1!=null && v2!=null && !v1.getId().equals(v2.getId())) //The last condition is required when the component has self-references 
								{
									
									ComposedExpression ne1= createSubstraction(v2, v1); //(i-j)
								
									cp.getAuxExpressions().add(ne1); 
									numericExpressions.add(ne1); 
								}
							}
							
							logger.debug("CPModelDerivator - createConstraints - 45");
							if(vmInstances.size()==0)
							{	
								
								//We use the type of vm to find the related variables
								logger.debug("CPModelDerivator - createConstraints - 46 "+vmp.getCloudMLId());
								Variable v1= CPModelTool.searchVariableByVMName(vars, vmp.getCloudMLId()); 
								logger.debug("CPModelDerivator - createConstraints - 47");
								Variable v2= CPModelTool.searchVariableByVMName(requiredVariables, vmp.getCloudMLId()); 
								logger.debug("CPModelDerivator - createConstraints - 48");
								
								if(v1!=null || v2!=null) //We use the provider to find the related variables
								{ 
									
									List<Variable> theVars= searchVariablesByProviderId(vmp, vars, requiredVariables); 
									
									if(theVars.size()>0)
									{
										v1= theVars.get(0); 
										v2= theVars.get(1); 
									}
									
								}
								
								if(v1!=null && v2!=null && !v1.getId().equals(v2.getId())) //The last condition is required when the component has self-references 
								{

									logger.debug("CPModelDerivator - createConstraints - 49");
									ComposedExpression ne1= createSubstraction(v2, v1); //(i-j)
									logger.debug("CPModelDerivator - createConstraints - 50");

									
									cp.getAuxExpressions().add(ne1); 
									logger.debug("CPModelDerivator - createConstraints - 51");
									numericExpressions.add(ne1); 
									logger.debug("CPModelDerivator - createConstraints - 52");
									
								}
								
							}	
							
								
							
						}
						
						
						if(numericExpressions.size()>0)
						{	
							logger.debug("CPModelDerivator - createConstraints - 53");
							ComparisonExpression ce1= cpFactory.createComparisonExpression(); 
							logger.debug("CPModelDerivator - createConstraints - 54");
							if(numericExpressions.size()>1)
							{	
								logger.debug("CPModelDerivator - createConstraints - 55");
								ComposedExpression ne2= cpFactory.createComposedExpression(); //(i-j)+(i2-j2...)
								
								for(ComposedExpression cExp: numericExpressions)
								{
									ne2.getExpressions().add(cExp);
								}
								
								ne2.setOperator(OperatorEnum.PLUS);
								ne2.setId(CPModelTool.getAuxExpressionId());
								cp.getAuxExpressions().add(ne2); 
								ce1.setExp1(ne2); 
							}
							else if(numericExpressions.size()>0)
								ce1.setExp1(numericExpressions.get(0));
							logger.debug("CPModelDerivator - createConstraints - 56");

							ce1.setExp2(ceroConstant); //TODO IS IT REALLY CORRECT ?
							ce1.setComparator(ComparatorEnum.EQUAL_TO); //SAME LOCATION
							ce1.setId(CONSTRAINT_PREFIX+constraintsCount); 
							constraintsCount++; 
							
							cp.getConstraints().add(ce1);
						}	
						
					}
					else //DIFFERENT LOCATION - At least one of the VM in variables have a different value
					{
						
						logger.debug("CPModelDerivator - createConstraints - Different location");
						for(VirtualMachineProfile vmp:configuration.getVmProfiles())
						{
							//Variable numberOfVMVar= searchVariableNumberOfByVMName(cp.getVariables(), vmp.getCloudMLId()); 
							
							List<VirtualMachine> instances= getVirtualMachineInstancesByProfile(configuration.getVms(), vmp);
							
							for(VirtualMachine instance:instances)
							{
								Variable v1= CPModelTool.searchVariableByVMName(vars, instance.getId()); 
								Variable v2= CPModelTool.searchVariableByVMName(requiredVariables, instance.getId()); 
								
								//if(numberOfVMVar!=null)
								addVariablesToList(vmp, v1, v2, differentLocations);
								
								
							}
							
							
							if(instances.size()==0)
							{
								logger.debug("CPModelDerivator - createConstraints - Adding different location to list");
								Variable v1= CPModelTool.searchVariableByVMName(vars, vmp.getCloudMLId()); 
								Variable v2= CPModelTool.searchVariableByVMName(requiredVariables, vmp.getCloudMLId()); 
								
								if(v1==null || v2==null)
								{
									List<Variable> theVars= searchVariablesByProviderId(vmp, vars, requiredVariables); 
									
									if(theVars.size()>0)
									{
										v1= theVars.get(0); 
										v2= theVars.get(1); 
									}
								}
								
								//if(numberOfVMVar!=null)
								addVariablesToList(vmp, v1, v2, differentLocations);
							}
							

							
							

								
								
								

						}
					}
					
				}
				
				
			}			
		}
		
		//Different Location consolidated
		Set<String> keys= differentLocations.keySet();
		
		logger.debug("CPModelDerivator - createConstraints - Different locations size "+keys.size());
		
		for(String vmId: keys)
		{
			logger.debug("CPModelDerivator - createConstraints - Creating different Location constraint");
			
			
			Constant numberOfVMConstanst= CPModelTool.searchConstantByName(cp.getConstants(), CPModelTool.getVMProfileConstantName(vmId)); 
			
			List<Variable> variables= differentLocations.get(vmId);
			
			ComposedExpression auxExp= CPModelTool.createComposedExpression(OperatorEnum.MINUS,CPModelTool.getAuxExpressionId());//cpFactory.createComposedExpression(); 


			auxExp.getExpressions().add(numberOfVMConstanst); 
			
			
			for(Variable var: variables)
			{
				auxExp.getExpressions().add(var); 
			}
			

			
			cp.getAuxExpressions().add(auxExp); 
			
			ComparisonExpression ce1= cpFactory.createComparisonExpression(); 
			
			ce1.setExp1(auxExp); 
			ce1.setExp2(ceroConstant); 
			ce1.setComparator(ComparatorEnum.GREATER_OR_EQUAL_TO); 
			ce1.setId(CONSTRAINT_PREFIX+constraintsCount); 
			constraintsCount++; 
			
			cp.getConstraints().add(ce1); 
			
		}
		
		//At least one VM is selected
		List<Variable> vmVars= CPModelTool.getAllComponentInVMVariables(cp.getVariables()); 
		
		NumericExpression auxExp=null;
		
		if(vmVars.size()>1)
		{
			auxExp= CPModelTool.createComposedExpression(OperatorEnum.PLUS, CPModelTool.getAuxExpressionId());//cpFactory.createComposedExpression(); 
			ComposedExpression ceAux= (ComposedExpression) auxExp;


			
			for(Variable var: vmVars)
			{
				ceAux.getExpressions().add(var); 
			}
			
			cp.getAuxExpressions().add(ceAux); 
		}
		else //At least one variable has to exist. Otherwise, there is a problem with the camel model ? 
		{
			auxExp= vmVars.get(0); 
		}
		
		
		ComparisonExpression ce1= cpFactory.createComparisonExpression(); 
		ce1.setId(CONSTRAINT_PREFIX+constraintsCount); 
		constraintsCount++; 
		ce1.setComparator(ComparatorEnum.GREATER_THAN); 
		
		ce1.setExp1(auxExp); 
		ce1.setExp2(ceroConstant); 
		
		cp.getConstraints().add(ce1); 
		
		//Min number of each virtual machine //TODO This constraint is necessary ?
		
/*		for(VirtualMachineProfile vmp:configuration.getVmProfiles())
		{
			Variable numberOfVar= getNumberOfVariable(vmp, cp); 
			
			
			List<VirtualMachine> instances= getVirtualMachineInstancesByProfile(configuration.getVms(), vmp); 
			
			if(instances.size()>0)
			{
				
				Constant numberOfInstaces= CPModelTool.searchConstantByValue(cp.getConstants(), instances.size()); 
				
				if(numberOfInstaces==null)
				{
					numberOfInstaces= CPModelTool.createIntegerConstant(instances.size(), CPModelTool.getConstantName()); //createIntConstant(instances.size(), cp);
					cp.getConstants().add(numberOfInstaces); 
				}
				
				ComparisonExpression ceNumberOf= cpFactory.createComparisonExpression(); 
				ceNumberOf.setId(CONSTRAINT_PREFIX+constraintsCount); 
				constraintsCount++; 
				ceNumberOf.setComparator(ComparatorEnum.GREATER_OR_EQUAL_TO); 
				
				ceNumberOf.setExp1(numberOfVar); 
				ceNumberOf.setExp2(numberOfInstaces); 
				
				cp.getConstraints().add(ceNumberOf); 
			}
			else
			{
				EList<Variable> variables= cp.getVariables();//getVariablesInRelatedToVMProfile(vmp, cp); 
				
				ComposedExpression auxExpPlus= CPModelTool.createComposedExpression(OperatorEnum.PLUS, CPModelTool.getAuxExpressionId()); //cpFactory.createComposedExpression(); 
				//auxExpPlus.setOperator(OperatorEnum.PLUS); 
				//auxExpPlus.setId(CPModelTool.getAuxExpressionId()); 
				
				
				
				for(Variable v: variables)
				{
					auxExpPlus.getExpressions().add(v);
					
				}
				
				cp.getAuxExpressions().add(auxExpPlus); 
				
				ComposedExpression auxExpDiv= cpFactory.createComposedExpression(); 
				auxExpDiv.setOperator(OperatorEnum.DIV); 
				auxExpDiv.setId(CPModelTool.getAuxExpressionId()); 
				
				
				
				Constant numberOfVars= CPModelTool.searchConstantByValue(cp.getConstants(), variables.size()); 
				
				if(numberOfVars==null)
				{	
					numberOfVars= CPModelTool.createIntegerConstant(variables.size(), CPModelTool.getConstantName());//createIntConstant(variables.size(), cp); 
					cp.getConstants().add(numberOfVars); 
				}
				auxExpDiv.getExpressions().add(auxExpPlus); 
				auxExpDiv.getExpressions().add(numberOfVars); 
				
				cp.getAuxExpressions().add(auxExpDiv); 
			
				ComparisonExpression ceNumberOf= cpFactory.createComparisonExpression(); 
				ceNumberOf.setId(CONSTRAINT_PREFIX+constraintsCount); 
				constraintsCount++; 
				ceNumberOf.setComparator(ComparatorEnum.GREATER_OR_EQUAL_TO); 
				
				ceNumberOf.setExp1(auxExpPlus); 
				ceNumberOf.setExp2(ceroConstant); 
				
				cp.getConstraints().add(ceNumberOf); 
			}
			

			
			
			
		}*/
		
		
	}
	
	/**
	 * Search for variables related to the same provider for two given components by using the provided virtual machine profile
	 * @param vmp The profile for searching the variables
	 * @param vars1 List of variables related to the first component 
	 * @param vars2 List of variables related to the second component
	 * @return A list of size 2 containing the two variables or an empty list if there are not variables related to the same provider
	 */
	protected List<Variable> searchVariablesByProviderId(VirtualMachineProfile vmp, List<Variable> vars1, List<Variable> vars2)
	{
		List<Variable> vars= new ArrayList<>(); 
		
		EList<ProviderDimension> pdimensions= vmp.getProviderDimension(); 
		
		Variable v1= null; 
		Variable v2= null; 
		
		for(ProviderDimension pd:pdimensions)
		{
			v1= CPModelTool.searchVariableByVMName(vars1, pd.getProvider().getId()); 
			
			if(v1!=null)
				break; 
			
		}
		
		for(ProviderDimension pd:pdimensions)
		{
			v2= CPModelTool.searchVariableByVMName(vars2, pd.getProvider().getId()); 
			
			if(v2!=null)
				break; 
			
		}
		
		if(v1!=null && v2!=null)
		{
			vars.add(v1); 
			vars.add(v2); 
		}
		
		return vars; 
	}
	
	protected void addVariablesToList(VirtualMachineProfile vmp, Variable v1, Variable v2, Map<String, List<Variable>> differentLocations)
	{
		if(v1!=null && v2!=null)
		{
			
			List<Variable> variables= differentLocations.get(vmp.getCloudMLId()); 
			
			if(variables==null)
			{
				variables= new ArrayList<>();
				differentLocations.put(vmp.getCloudMLId(), variables);
			}
			
			variables.add(v1);
			variables.add(v2);
			
		}
	}
	
	protected ComposedExpression createSubstraction(Variable v1, Variable v2)
	{
		ComposedExpression ne1= cpFactory.createComposedExpression(); //(i-j)
		ne1.getExpressions().add(v1);
		ne1.getExpressions().add(v2); 
		ne1.setOperator(OperatorEnum.MINUS);
		ne1.setId(CPModelTool.getAuxExpressionId()); 
		
		return ne1; 
	}
	
	/**
	 * Searches in a list a variable related to a given virtual machine profile
	 * @param vars The list of variables
	 * @param profile The virtual machine profile
	 * @return The variable related to the profile or null if it does not exist 
	 */
	protected Variable getComponentInVMByVMName(List<Variable> vars,
			VirtualMachineProfile profile) 
	{
		
		for(Variable var:vars)
		{
			if(var.getId().endsWith(profile.getCloudMLId()))
				return var; 
		}
		
		return null;
	}



	
	/**
	 * Searches in a list all the virtual machine profiles related to a given provider 
	 * @param profiles The list of profiles
	 * @param pt The provider type
	 * @return The list containing all the virtual machine profiles 
	 */
	protected List<VirtualMachineProfile> getVMProfilesByProviderType(EList<VirtualMachineProfile> profiles, ProviderType pt)
	{
		List<VirtualMachineProfile> vmps= new ArrayList<VirtualMachineProfile>(); 
		
		for(VirtualMachineProfile p: profiles)
		{
			for(ProviderDimension pc: p.getProviderDimension()) //TODO Should be one
			{
				//ProviderCost pc= p.getProvider(); 
				if(pc.getProvider().getId().equals(pt.getId()))
					vmps.add(p); 
			}
		}
		
		return vmps; 
	}
	

	
	

	
	/**
	 * Searches in a list a variable indicating the number of instances by using a given name 
	 * @param variables The list of variables
	 * @param name The id of the variable
	 * @return The variable with the given id or null if it does not exist
	 */
	protected Variable searchVariableNumberOfByVMName(EList<Variable> variables, String name)
	{
		String id= getNumberOfVMName(name); 
		
		for(Variable var: variables)
		{
			if(var.getId().equals(id))
			{
				return var; 
			}
		}
		
		return null; 
	}
	

	

	
	
	protected List<VirtualMachine> getVirtualMachineInstancesByProfile(EList<VirtualMachine> vms, VirtualMachineProfile profile)
	{
		List<VirtualMachine> filtred= new ArrayList<>(); 
		
		
		for(VirtualMachine vm: vms)
		{
			if(vm.getProfile().getCloudMLId().equals(profile.getCloudMLId()))
				filtred.add(vm);
		}
		
		return filtred; 
		
	}
	

	
	/**
	 * Creates the variables for a constraint problem
	 * @param cp The constraint problem
	 * @param configuration The paasage configuration to create the variables
	 */
	protected void createVariables(ConstraintProblem cp, PaasageConfiguration configuration)
	{
		//App components
		createAppComponentVariables(cp, configuration); 
		
	}
	
	/**
	 * Creates variables related to virtual machine profiles
	 * @param cp The constraint problem for creating the variables
	 * @param configuration The configuration used to create the variables
	 */
	protected void createVMProfileVariables(ConstraintProblem cp, PaasageConfiguration configuration)
	{
		for(VirtualMachineProfile vmp:configuration.getVmProfiles())
		{
			logger.debug("CPModelDerivator - createVMProfileVariables  - Creating var "+VM_PROFILE_VAR_PREFIX+vmp.getCloudMLId()); 
			Variable var= CPModelTool.createIntegerVariableWithRangeDomain(getNumberOfVMName(vmp.getCloudMLId()), 0, MAX_NUMBER_OF_VMS); //TODO TO USE AS MAX THE NUMBER OF DEFINED INSTANCES FOR THE GIVEN PROFILE ??
			
			cp.getVariables().add(var); 
			
			PaaSageVariable pVar= ApplicationFactory.eINSTANCE.createPaaSageVariable(); 
			
			pVar.setCpVariableId(var.getId()); 
			
			//pVar.setDomain(createIntegerDomain()); 
			
			pVar.setPaasageType(VariableElementTypeEnum.QUANTITY); 
			
			pVar.setRelatedVirtualMachineProfile(vmp);
			
			configuration.getVariables().add(pVar); 
			
			
			
		}
	}
	
	/**
	 * Returns the name for a NumBerOf Variable
	 * @param vmId The virtual machine id to create the name
	 * @return VM_PROFILE_VAR_PREFIX+vmId
	 */
	protected String getNumberOfVMName(String vmId)
	{
		return VM_PROFILE_VAR_PREFIX+vmId; 
	}
	
	/**
	 * Creation of variables related to appComponents and vm profiles
	 * @param cp The constraint problem to create the component variables
	 * @param configuration The paasage configuration for creating the variables
	 */
	protected void createAppComponentVariables(ConstraintProblem cp, PaasageConfiguration configuration)
	{
		//App components
		
		logger.debug("CPModelDerivator - createAppComponentVariables  - Number of components "+configuration.getComponents().size()); 
		
		for(ApplicationComponent ac:configuration.getComponents())
		{
			logger.debug("CPModelDerivator - createAppComponentVariables  - Component "+ac.getCloudMLId()+ " Required Profiles "+ac.getRequiredProfile().size()); 
			
		
			
			//for(VirtualMachineProfile vmp:configuration.getVmProfiles())
			for(VirtualMachineProfile vmp:ac.getRequiredProfile())
			{
				
				for(ProviderDimension pd: vmp.getProviderDimension())
				{	
				
					createAppComponentVariable(ac, vmp, pd.getProvider(), cp, configuration, 0, ac.getMax()); //The zero value is necessary as several providers are considered now
				
				
				}
			}
		}
	}
	
	protected void createAppComponentVariable(ApplicationComponent ac, VirtualMachineProfile vm, Provider provider, ConstraintProblem cp, PaasageConfiguration configuration, int min, int max)
	{
		//Var creation
		String vmId= vm.getCloudMLId(); 
		
		String providerId= provider.getId(); 
		
		String varName= CPModelTool.generateApplicationComponentVarName(ac.getCloudMLId(), vmId, providerId);//APP_COMPONENT_VAR_PREFIX+appComponentName+APP_COMPONENT_VAR_MID+vmpName+APP_COMPONENT_VAR_SUFFIX+providerId; 
		
		logger.debug("CPModelDerivator - createAppComponentVariable  - Creating var "+varName+ " VM Profile/Instance "+vmId); 
		
		Variable var= CPModelTool.createIntegerVariableWithRangeDomain(varName, min, max);//createBooleanVariable(varName); 
		
		var.setVmId(vmId);
		
		var.setProviderId(providerId);
		
		cp.getVariables().add(var); 
		
		PaaSageVariable pVar= ApplicationFactory.eINSTANCE.createPaaSageVariable(); 
				
		pVar.setCpVariableId(var.getId()); //TODO IS IT REQUIRED??
		
		pVar.setPaasageType(VariableElementTypeEnum.VIRTUAL_LOCATION); 
		
		pVar.setRelatedComponent(ac);
		pVar.setRelatedVirtualMachineProfile(vm);
		pVar.setRelatedProvider(provider); 
		
		configuration.getVariables().add(pVar); 
	}
	
	/**
	 * Creates a boolean variable with the given name
	 * @param varName The variable name
	 * @return The boolean variable
	 */
	protected Variable createBooleanVariable(String varName)
	{
		Variable var= cpFactory.createVariable(); 
		
		var.setId(varName); 
		

		
		BooleanDomain domain= createBooleanDomain();
		
		var.setDomain(domain); 
		
		
		return var; 
	}
	
	/**
	 * Creates a numeric list domain by using the list of given values for the domain
	 * @param numbers The list of values
	 * @return The numeric list domain
	 */	
	protected NumericListDomain createNumericListIntDomain(List<Integer> numbers)
	{
		NumericListDomain domain= cpFactory.createNumericListDomain();
		
		domain.setType(BasicTypeEnum.INTEGER); 
		
		for(Integer num:numbers)
		{
			IntegerValueUpperware current= TypesFactory.eINSTANCE.createIntegerValueUpperware(); 
			
			current.setValue(num.intValue());
			
			domain.getValues().add(current); 
		}
		
		
		domain.setValue(domain.getValues().get(0));
		
		return domain; 
	}
	

	
	/**
	 * Creates a boolean domain
	 * @return The boolean domain
	 */
	protected BooleanDomain createBooleanDomain()
	{
		BooleanDomain bd= cpFactory.createBooleanDomain();

		return bd; 
	}
	
	/**
	 * Creates a integer variable with a given name
	 * @param varName The variable name
	 * @return The integer variable
	 */
	protected Variable createIntegerVariable(String varName)
	{
		Variable var= cpFactory.createVariable(); 
		
		var.setId(varName); 
		
		NumericDomain nd= createIntegerDomain(); 
		
		var.setDomain(nd); 
		
		
		return var; 
	}
		
	/**
	 * Creates an integer domain
	 * @return The integer domain
	 */
	protected NumericDomain createIntegerDomain()
	{
		NumericDomain nd= cpFactory.createNumericDomain(); 
		
		nd.setType(BasicTypeEnum.INTEGER); 
		
		return nd;         
	}
	
	/**
	 * Creates constants for a constraint problem  
	 * @param cp The constraint problem
	 * @param configuration The paasage configuration
	 */
	protected void createConstants(ConstraintProblem cp, PaasageConfiguration configuration)
	{
		
		

		
		//Minimal number of VM profiles selected
		Constant minNumberOfVM= CPModelTool.createIntegerConstant(1, MIN_NUM_VM);//cpFactory.createConstant(); 
		
	
		cp.getConstants().add(minNumberOfVM); 
		
		
		//Max number of VMs per VM type
		
		for(VirtualMachineProfile vmp:configuration.getVmProfiles())
		{
			Constant vmConstant= CPModelTool.createIntegerConstant(MAX_NUMBER_OF_VMS, CPModelTool.getVMProfileConstantName(vmp.getCloudMLId()));
			
			cp.getConstants().add(vmConstant); 
		}
		
		
		//One constant
		
		Constant oneConstant= CPModelTool.createIntegerConstant(1, CPModelTool.ONE_CONSTANT);//cpFactory.createConstant(); 
		
		cp.getConstants().add(oneConstant); 
		
		
		//Cero constant
		
		Constant ceroConstant= CPModelTool.createIntegerConstant(0, CPModelTool.CERO_CONSTANT); //cpFactory.createConstant(); 

		
		cp.getConstants().add(ceroConstant); 
				
		
	}
	
	/**
	 * Creates a cost constant for the constraint problem 
	 * @param cp The constraint problem 
	 * @param configuration The paasage configuration
	 */
	protected void createCostMetricVariables(ConstraintProblem cp, PaasageConfiguration configuration)
	{
		for(VirtualMachineProfile vmp:configuration.getVmProfiles())
		{
			int i=0; 
			for(ProviderDimension pCost:vmp.getProviderDimension())//TODO TO CHECK THIS
			{
			
				MetricVariable cost= cpFactory.createMetricVariable(); 
			
				cost.setId(getCostMetricVariableName(vmp.getCloudMLId()+NAME_SEPARATOR+i)); 
				i++; 
				cost.setType(BasicTypeEnum.DOUBLE); 
				DoubleValueUpperware value= TypesFactory.eINSTANCE.createDoubleValueUpperware(); 
				value.setValue(pCost.getValue()); 
			
				CPModelTool.assignNumericValue(value, cost, cp);
			
				//cost.setValue(value); 
			
				//cp.getConstants().add(cost); 
				cp.getMetricVariables().add(cost); 
			}
		}
	}
		
	/**
	 * Provides the cost constant name using a given id
	 * @param vmId The id
	 * @return COST_CONSTANT_PREFIX+vmId
	 */
	protected String getCostMetricVariableName(String vmId)
	{
		return COST_METRIC_PREFIX+vmId; 
	}
	
	
	/**
	 * Creates the factories by using the given input stream 
	 * @param file The input stream that contains the factory classes 
	 */
	protected void createFactories(InputStream file)
	{
		
		if(file!=null)
		{
			Properties properties= new Properties(); 
			factoriesMap= new Hashtable<String, IEstimatorFactory>(); 
			
			try {
				
				properties.load(file);
				
				Iterator<Object> it=properties.keySet().iterator(); 
				
				while(it.hasNext())
				{
					String key= (String) it.next();
					String className= properties.getProperty(key); 
					
					Class clazz= Class.forName(className); 
					
					Constructor defaultConstructor= clazz.getConstructor(new Class[0]);
					
					logger.debug("CPModelDerivator - createFactories - Creating factory "+className+" "+defaultConstructor.getParameterTypes().length ); 
					
					IEstimatorFactory factory= (IEstimatorFactory) defaultConstructor.newInstance(new Object[0]); 
					
					factoriesMap.put(key, factory); 
					
				}
				logger.debug("CPModelDerivator - createFactories - Factories initialized!"); 
			} catch (FileNotFoundException e) {
				logger.warn("CPModelDerivator - createFactories - Problems loading the file. The loaders are not initialized!"); 
				e.printStackTrace();
			} catch (IOException e) {
				logger.warn("CPModelDerivator - createFactories - Problems loading the file. The loaders are not initialized!"); 
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				logger.warn("CPModelDerivator - createFactories - Problems loading the class of one of factories. All the factories are not initialized!"); 
				e.printStackTrace();
			} catch (SecurityException e) {
				logger.warn("CPModelDerivator - createFactories - Problems loading the constructors of one of factories. All the factories are not initialized!"); 
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				logger.warn("CPModelDerivator - createFactories - Problems loading the constructors of one of factories. All the factories are not initialized!"); 
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				logger.warn("CPModelDerivator - createFactories - Problems loading the constructors of one of factories. All the factories are not initialized!"); 
				e.printStackTrace();
			} catch (InstantiationException e) {
				logger.warn("CPModelDerivator - createFactories - Problems loading the constructors of one of factories. All the factories are not initialized!"); 
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				logger.warn("CPModelDerivator - createFactories - Problems loading the constructors of one of factories. All the factories are not initialized!"); 
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				logger.warn("CPModelDerivator - createFactories - Problems loading the constructors of one of factories. All the factories are not initialized!"); 
				e.printStackTrace();
			} 
			
			
		}
		else
		{
			logger.warn("CPModelDerivator - createFactories - The properties file does not exist. The loaders are not initialized!"); 
		}
	}
	

	

	
	
	protected Variable getNumberOfVariable(VirtualMachineProfile profile, ConstraintProblem cp)
	{
		String varName= getNumberOfVMName(profile.getCloudMLId()); 
		
		for(Variable v:cp.getVariables())
		{
			if(varName.equals(v.getId()))
				return v; 
		}
		
		return null; 
	}
	
	protected List<Variable> getVariablesInRelatedToVMProfile(VirtualMachineProfile profile, ConstraintProblem cp)
	{
		List<Variable> list= new ArrayList<Variable>();
		
		String numberOfvarName= getNumberOfVMName(profile.getCloudMLId()); 
		
		String id= profile.getCloudMLId(); 
		

		for(Variable v:cp.getVariables())
		{
			if(v.getId().contains(id) && !v.getId().equals(numberOfvarName))
			{
				list.add(v); 
			}
				 
		}
		
		return list; 
	}
	
	protected List<Variable> getVariablesInRelatedToVM(VirtualMachine instance, ConstraintProblem cp)
	{
		List<Variable> list= new ArrayList<Variable>();
		
		String id= instance.getId(); 
		

		for(Variable v:cp.getVariables())
		{
			if(v.getId().contains(id))
			{
				list.add(v); 
			}
				 
		}
		
		return list; 
	}
	
	/**
	 * Creates the function creators by using the given input stream 
	 * @param file The input stream that contains the function creator classes 
	 * @param database The database proxy
	 */
	protected void createFunctionCreators(InputStream file, IDatabaseProxy database)
	{		
		if(file!=null)
		{
			Properties properties= new Properties(); 
			creatorsMap= new Hashtable<String, IFunctionCreator>(); 
			
			try {
				
				properties.load(file);
				
				Iterator<Object> it=properties.keySet().iterator(); 
				
				while(it.hasNext())
				{
					String key= (String) it.next();
					String className= properties.getProperty(key); 
					
					Class clazz= Class.forName(className); 
					
					Constructor defaultConstructor= clazz.getConstructor(new Class[0]);
					
					logger.debug("CPModelDerivator - createFunctionCreators - Creating function creator "+className+" "+defaultConstructor.getParameterTypes().length ); 
					
					IFunctionCreator creator= (IFunctionCreator) defaultConstructor.newInstance(new Object[0]); 
					
					creator.setDatabaseProxy(database);
					
					creatorsMap.put(key, creator); 
					
				}
				
				logger.debug("CPModelDerivator - createFunctionCreators - Creators initialized!"); 
			} catch (FileNotFoundException e) {
				logger.warn("CPModelDerivator - createFunctionCreators - Problems loading the file. The loaders are not initialized!"); 
				e.printStackTrace();
			} catch (IOException e) {
				logger.warn("CPModelDerivator - createFunctionCreators - Problems loading the file. The loaders are not initialized!"); 
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				logger.warn("CPModelDerivator - createFunctionCreators - Problems loading the class of one of creators. All the creators are not initialized!"); 
				e.printStackTrace();
			} catch (SecurityException e) {
				logger.warn("CPModelDerivator - createFunctionCreators - Problems loading the constructors of one of creators. All the creators are not initialized!"); 
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				logger.warn("CPModelDerivator - createFunctionCreators - Problems loading the constructors of one of creators. All the creators are not initialized!"); 
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				logger.warn("CPModelDerivator - createFunctionCreators - Problems loading the constructors of one of creators. All the creators are not initialized!"); 
				e.printStackTrace();
			} catch (InstantiationException e) {
				logger.warn("CPModelDerivator - createFunctionCreators - Problems loading the constructors of one of creators. All the creators are not initialized!"); 
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				logger.warn("CPModelDerivator - createFunctionCreators - Problems loading the constructors of one of creators. All the creators are not initialized!"); 
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				logger.warn("CPModelDerivator - createFunctionCreators - Problems loading the constructors of one of creators. All the creators are not initialized!"); 
				e.printStackTrace();
			} 
			
			
		}
		else
		{
			logger.warn("CPModelDerivator - createFactories - The properties file does not exist. The loaders are not initialized!"); 
		}
	}
	
	
	public static InputStream selectExistingPriceFile()
	{
		File cloudPricingFile= new File(Constants.CONFIG_FILES_DEFAULT_PATH,"cloudPricing.txt"); 
		
		InputStream is= null; 
		
		if(!cloudPricingFile.isFile())
		{	
			is= FileTool.getInputStreamFromFileName(Constants.WAR_CONFIG_PATH+"cloudPricing.txt"); 
						
		} else
			try {
				is= new FileInputStream(cloudPricingFile);
			} 
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}
		
		return is; 
	}

}
