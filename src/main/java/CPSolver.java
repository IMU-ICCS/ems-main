/* Copyright (C) 2015 KYRIAKOS KRITIKOS <kritikos@ics.forth.gr> */

/* This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ 
 */

import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import eu.paasage.mddb.cdo.client.CDOClient;
import eu.paasage.upperware.metamodel.cp.ComparatorEnum;
import eu.paasage.upperware.metamodel.cp.ComparisonExpression;
import eu.paasage.upperware.metamodel.cp.ComposedExpression;
import eu.paasage.upperware.metamodel.cp.ComposedUnaryExpression;
import eu.paasage.upperware.metamodel.cp.ComposedUnaryOperatorEnum;
import eu.paasage.upperware.metamodel.cp.Constant;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpFactory;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.cp.Domain;
import eu.paasage.upperware.metamodel.cp.Expression;
import eu.paasage.upperware.metamodel.cp.Goal;
import eu.paasage.upperware.metamodel.cp.GoalOperatorEnum;
import eu.paasage.upperware.metamodel.cp.MetricVariable;
import eu.paasage.upperware.metamodel.cp.MetricVariableValue;
import eu.paasage.upperware.metamodel.cp.NumericDomain;
import eu.paasage.upperware.metamodel.cp.NumericExpression;
import eu.paasage.upperware.metamodel.cp.OperatorEnum;
import eu.paasage.upperware.metamodel.cp.RangeDomain;
import eu.paasage.upperware.metamodel.cp.SimpleUnaryExpression;
import eu.paasage.upperware.metamodel.cp.SimpleUnaryOperatorEnum;
import eu.paasage.upperware.metamodel.cp.Solution;
import eu.paasage.upperware.metamodel.cp.UnaryExpression;
import eu.paasage.upperware.metamodel.cp.Variable;
import eu.paasage.upperware.metamodel.cp.VariableValue;
import eu.paasage.upperware.metamodel.types.BasicTypeEnum;
import eu.paasage.upperware.metamodel.types.DoubleValueUpperware;
import eu.paasage.upperware.metamodel.types.FloatValueUpperware;
import eu.paasage.upperware.metamodel.types.IntegerValueUpperware;
import eu.paasage.upperware.metamodel.types.LongValueUpperware;
import eu.paasage.upperware.metamodel.types.NumericValueUpperware;
import eu.paasage.upperware.metamodel.types.TypesFactory;
import eu.paasage.upperware.metamodel.types.TypesPackage;
import solver.ResolutionPolicy;
import solver.Solver;
import solver.constraints.Constraint;
import solver.constraints.IntConstraintFactory;
import solver.constraints.real.RealConstraint;
import solver.search.strategy.IntStrategyFactory;
import solver.variables.BoolVar;
import solver.variables.IntVar;
import solver.variables.RealVar;
import solver.variables.VariableFactory;
import util.ESat;
import util.tools.ArrayUtils;


public class CPSolver {

	private Solver solver = null;
	private String cdoPath = null;
	private String pathName = null;
	private ResolutionPolicy policy = null;
	private IntVar intGoal = null;
	private RealVar realGoal = null;
	private Hashtable<String,IntVar> idToIntVar;
	private Hashtable<String,RealVar> idToRealVar;
	private Hashtable<String,BoolVar> idToBoolVar;
	private List<Constraint> constraints;
	private ConstraintProblem cp = null;
	private static final double epsilon = 0.000001d;
	private static final int LOW_INT_LIMIT = -10000000;
	private static final int UPPER_INT_LIMIT = 10000000;
	private static final double LOW_REAL_LIMIT = -1000000000.0;
	private static final double UPPER_REAL_LIMIT = 1000000000.0;
	private int intVarNum = 0;
	private int realVarNum = 0;
	private int constNum = 0;
	private boolean cdoMode = false;
	private long timestamp = 0;
	
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(CPSolver.class);
 	
	/* Constructor which also reads the CP Model either from CDO via 
	 * a CDO path given as String or from file system via a String path 
	 */
	public CPSolver(String cdoPath, String pathName){
		solver = new Solver();
		this.cdoPath = cdoPath;
		this.pathName = pathName;
		readCPModel(cdoPath,pathName);
	}
	
	/* Constructor which also reads the CP Model either from CDO via 
	 * a CDO path given as String or from file system via a String path 
	 */
	public CPSolver(String cdoPath, String pathName, long timestamp){
		solver = new Solver();
		this.cdoPath = cdoPath;
		this.pathName = pathName;
		this.timestamp = timestamp;
		readCPModel(cdoPath,pathName);
	}
	
	/* Default Constructor - need to read CP Model by calling the respective method 
	 * with the CDOID of this model as otherwise no solution will be produced by calling 
	 * solve method
	 */
	public CPSolver(){
		solver = new Solver();
	}
	
	/* Processing the model fetched to inform the private variables of this class */
	private void readModel(ConstraintProblem cp){
		EList<Constant> constants = cp.getConstants();
		createConstants(constants);
		EList<Variable> vars = cp.getVariables();
		createVariables(vars);
		EList<MetricVariable> metricVars = cp.getMetricVariables();
		createMetricVariables(metricVars);
		EList<ComparisonExpression> constraints = cp.getConstraints();
		createConstraints(constraints);
		//Checking if metric-based solution exists
		if (timestamp != 0){
			EList<Solution> sols = cp.getSolution();
			if (sols != null && !sols.isEmpty()) checkSolution(sols);
		}
		//Create optimisation goal
		EList<Goal> goals = cp.getGoals();
		createGoals(goals);
	}
	
	//Get solution mapping to the timestamp given
	private void checkSolution(EList<Solution> sols){
			Solution sol = null;
			for (Solution s: sols){
				if (s.getTimestamp() == timestamp){
					sol = s;
					break;
				}
			}
			if (sol != null){
				System.out.println("Found solution with the timestamp given");
				for (MetricVariableValue mvv: sol.getMetricVariableValue()){
					MetricVariable mv = mvv.getVariable();
					NumericValueUpperware val = mvv.getValue();
					String mvName = mv.getId();
					IntVar intVar = idToIntVar.get(mvName);
					if (intVar != null){
						int actualVal = 1;
						if (val instanceof IntegerValueUpperware){
							IntegerValueUpperware intVal = (IntegerValueUpperware)val;
							actualVal = intVal.getValue();
						}
						else if (val instanceof DoubleValueUpperware){
							DoubleValueUpperware doubleVal = (DoubleValueUpperware)val;
							actualVal = (int)doubleVal.getValue();
						}
						else if (val instanceof FloatValueUpperware){
							FloatValueUpperware floatVal = (FloatValueUpperware)val;
							actualVal = (int)floatVal.getValue();
						}
						solver.post(IntConstraintFactory.arithm(intVar, "=", actualVal));
					}
					else{
						RealVar realVar = idToRealVar.get(mvName);
						if (realVar != null){
							double actualVal = 1.0;
							if (val instanceof IntegerValueUpperware){
								IntegerValueUpperware intVal = (IntegerValueUpperware)val;
								actualVal = intVal.getValue();
							}
							else if (val instanceof DoubleValueUpperware){
								DoubleValueUpperware doubleVal = (DoubleValueUpperware)val;
								actualVal = doubleVal.getValue();
							}
							else if (val instanceof FloatValueUpperware){
								FloatValueUpperware floatVal = (FloatValueUpperware)val;
								actualVal = (double)floatVal.getValue();
							}
							try{
								realVar.updateLowerBound(actualVal,null);
								realVar.updateUpperBound(actualVal,null);
							}
							catch(Exception e){
								e.printStackTrace();
							}
						}
					}
				}
			}
	}
	
	/* Reads the CPModel from CDO provided that a correct CDO path or a file path 
	 * name for this model is provided as input
	 */
	public void readCPModel(String cdoPath, String pathName){
		CDOClient cl = new CDOClient();
		cl.registerPackage(TypesPackage.eINSTANCE);
		cl.registerPackage(CpPackage.eINSTANCE);
		this.cdoPath = cdoPath;
		this.pathName = pathName;
		if (cdoPath != null){
			cdoMode = true;
			CDOView view = cl.openView();
			CDOResource res = view.getResource(cdoPath);
			EList<EObject> objs = res.getContents();
			for (EObject obj: objs){
				if (obj instanceof ConstraintProblem){
					cp = (ConstraintProblem)obj;
					break;
				}
			}
			readModel(cp);
			view.close();
		}
		else if (pathName != null){
			cdoMode = false;
			cp = (ConstraintProblem)cl.loadModel(pathName);
			readModel(cp);
		}
		logger.info("CDO Mode: " + cdoMode);
		cl.closeSession();
	}
	
	/* Solves the CPModel previously read, updates the model if a solution was found 
	 * and returns a boolean value indicating whether a solution has been found or not
	 */
	public boolean solve() throws Exception{
		boolean hasSolutions = false;
		if (idToIntVar != null){
			IntVar[] vars = new IntVar[idToIntVar.size()];
			int i = 0;
			for (Object o: idToIntVar.values().toArray()){
				IntVar var = (IntVar)o;
				vars[i++] = var;
			}
			solver.set(IntStrategyFactory.random(vars, System.currentTimeMillis()));
		}
		if (policy != null){
			if (realGoal != null){
				solver.findOptimalSolution(policy, realGoal);
				logger.info("Optimal value is: " + realGoal.getUB());
			}
			else{
				solver.findOptimalSolution(policy, intGoal);
				logger.info("Optimal value is: " + intGoal.getValue());
			}
			hasSolutions = (solver.isFeasible() == ESat.TRUE);
			if (hasSolutions) saveSolution();
			dispose();
			solver.getIbex().release();
		}
		else{
			hasSolutions = solver.findSolution();
			if (hasSolutions) saveSolution();
			dispose();
		}
		return hasSolutions;
	}
	
	/* Saving the solution in the cp model and storing back the model to its 
	 * initial position, either in CDO repository or the file system
	 */
	private void saveSolution(){
		logger.info("Saving solution .....");
		CDOTransaction trans = null;
		ConstraintProblem cp = null;
		CDOClient cl = new CDOClient();
		cl.registerPackage(TypesPackage.eINSTANCE);
		cl.registerPackage(CpPackage.eINSTANCE);
		//System.out.println("CDOMode: " + cdoMode);
		if (cdoMode){
			trans = cl.openTransaction();
			CDOResource resource = trans.getResource(cdoPath);
			EList<EObject> contents = resource.getContents();
			for (EObject obj: contents){
				if (obj instanceof ConstraintProblem){
					cp = (ConstraintProblem)obj;
					break;
				}
			}
		}
		else{
			cp = (ConstraintProblem)cl.loadModel(pathName);
		}
		Solution solution = null;
		if (timestamp == 0){
			solution = CpFactory.eINSTANCE.createSolution();
			solution.setTimestamp(new Date().getTime());
			cp.getSolution().add(solution);
		}
		else{
			for (Solution s: cp.getSolution()){
				if (s.getTimestamp() == timestamp){
					solution = s;
					break;
				}
			}
		}
		
		EList<VariableValue> varValues = solution.getVariableValue();
		try{
			EList<Variable> vars = cp.getVariables();
			for (Variable var: vars){
				VariableValue varVal = CpFactory.eINSTANCE.createVariableValue();
				varVal.setVariable(var);
				Domain dom = var.getDomain();
				IntVar iv = idToIntVar.get(var.getId());
				if (iv != null){
					int val = iv.getValue();
					logger.info("Discovered value for variable :" + var.getId() + " is: " + val);
					if (dom instanceof NumericDomain){
						NumericDomain nd = (NumericDomain)dom;
						BasicTypeEnum type = nd.getType();
						if (type.equals(BasicTypeEnum.INTEGER)){
							IntegerValueUpperware value = TypesFactory.eINSTANCE.createIntegerValueUpperware();
							value.setValue(val);
							varVal.setValue(value);
						}
						else{
							LongValueUpperware value = TypesFactory.eINSTANCE.createLongValueUpperware();
							value.setValue(val);
							varVal.setValue(value);
						}
					}
					else if (dom instanceof RangeDomain){
						RangeDomain rd = (RangeDomain)dom;
						NumericValueUpperware from = rd.getFrom();
						if (from instanceof IntegerValueUpperware){
							IntegerValueUpperware value = TypesFactory.eINSTANCE.createIntegerValueUpperware();
							value.setValue(val);
							varVal.setValue(value);
						}
						else{
							LongValueUpperware value = TypesFactory.eINSTANCE.createLongValueUpperware();
							value.setValue(val);
							varVal.setValue(value);
						}
					}
				}
				else{
					RealVar rv = idToRealVar.get(var.getId());
					if (rv != null){
						double val = rv.getUB();
						logger.info("Discovered value for variable :" + var.getId() + " is: " + val);
						if (dom instanceof NumericDomain){
							NumericDomain nd = (NumericDomain)dom;
							BasicTypeEnum type = nd.getType();
							if (type.equals(BasicTypeEnum.DOUBLE)){
								DoubleValueUpperware value = TypesFactory.eINSTANCE.createDoubleValueUpperware();
								value.setValue(val);
								varVal.setValue(value);
							}
							else{
								FloatValueUpperware value = TypesFactory.eINSTANCE.createFloatValueUpperware();
								value.setValue((float)val);
								varVal.setValue(value);
							}
						}
						else if (dom instanceof RangeDomain){
							RangeDomain rd = (RangeDomain)dom;
							NumericValueUpperware from = rd.getFrom();
							if (from instanceof DoubleValueUpperware){
								DoubleValueUpperware value = TypesFactory.eINSTANCE.createDoubleValueUpperware();
								value.setValue(val);
								varVal.setValue(value);
							}
							else{
								FloatValueUpperware value = TypesFactory.eINSTANCE.createFloatValueUpperware();
								value.setValue((float)val);
								varVal.setValue(value);
							}
						}
					}
				}
				varValues.add(varVal);
			}
			if (cdoMode){
				trans.commit();
				trans.close();
			}
			else{
				cl.saveModel(cp, pathName);
			}
			logger.info("..... Solution saved");
		}
		catch(Exception e){
			logger.error("Something went wrong while storing the solution",e);
			//e.printStackTrace();
		}
		cl.closeSession();
	}
	
	/* Getting resolution policy from the operator in the goal of the cp model */
	private ResolutionPolicy getPolicy(GoalOperatorEnum type){
		if (type.equals(GoalOperatorEnum.MAX)) return ResolutionPolicy.MAXIMIZE;
		else if (type.equals(GoalOperatorEnum.MIN)) return ResolutionPolicy.MINIMIZE;
		return ResolutionPolicy.MAXIMIZE;
	}
	
	/* Checking if cp's goal operator is MAX or MIN */
	private int isMax(GoalOperatorEnum type){
		if (type.equals(GoalOperatorEnum.MAX)) return 1;
		else if (type.equals(GoalOperatorEnum.MIN)) return 0;
		return 0;
	}
	
	/* Checking if cp's goal operator is MAX or MIN */
	private int optToInt(GoalOperatorEnum type){
		if (type.equals(GoalOperatorEnum.MAX)) return 1;
		else if (type.equals(GoalOperatorEnum.MIN)) return -1;
		return 0;
	}
	
	/* Creating the optimisation objective from the list of goals contained in the cp model */
	private void createGoals(EList<Goal> goals){
		logger.info("--------------- Goals ---------------");
		int size = goals.size();
		if (size == 1){
			Goal goal = goals.get(0);
			NumericExpression expr = goal.getExpression();
			boolean isInt = involvesOnlyInt(expr);
			if (isInt){
				intGoal = parseExpression(expr);
				logger.info("Optimization Variable: " + intGoal.getName());
			}
			else{
				RealConstraint rc = new RealConstraint(solver);
				realGoal = parseRealExpression(expr,rc);
				logger.info("Optimization Variable: " + realGoal.getName());
				solver.post(rc);
			}
			GoalOperatorEnum type = goal.getGoalType();
			policy = getPolicy(type);
		}
		else if (size > 1){
			boolean isInt = true;
			for (Goal goal: goals){
				NumericExpression expr = goal.getExpression();
				if (!involvesOnlyInt(expr)){
					isInt = false;
					break;
				}
			}
			if (isInt){
				IntVar[] vars = new IntVar[size];
				int[] dirs = new int[size];
				for (int i = 0; i < size; i++){
					Goal goal = goals.get(i);
					vars[i] = parseExpression(goal.getExpression());
					dirs[i] = optToInt(goal.getGoalType()) * (int)goal.getPriority();
				}
				intGoal = VariableFactory.bounded("maximize", LOW_INT_LIMIT, UPPER_INT_LIMIT, solver);
				logger.info("Optimization Variable: " + intGoal.getName());
				solver.post(IntConstraintFactory.scalar(vars, dirs, intGoal));
				policy = ResolutionPolicy.MAXIMIZE;
			}
			else{
				//RealConstraint rc = new RealConstraint(solver);
				RealVar[] vars = new RealVar[size];
				int[] dirs = new int[size];
				for (int i = 0; i < size; i++){
					RealConstraint rc = new RealConstraint(solver);
					Goal goal = goals.get(i);
					logger.info("Processing goal: " + goal.getId());
					vars[i] = parseRealExpression(goal.getExpression(),rc);
					logger.info("var created was: " + vars[i]);
					dirs[i] = optToInt(goal.getGoalType()) * (int)goal.getPriority();
					solver.post(rc);
				}
				logger.info("Optimisation goals created successfully");
				RealConstraint rc = new RealConstraint(solver);
				realGoal = VariableFactory.real("maximize", LOW_INT_LIMIT, UPPER_INT_LIMIT, epsilon, solver);
				StringBuilder function = new StringBuilder("(");
				function.append(dirs[0] + " * {0} ");
				for (int i = 1; i < size; i++){
					function.append(" + " + dirs[i] + " * {" + i + "} ");
				}
				function.append(") = { " + size + "}");
				logger.info("Optimisation formula is: " + function.toString());
				RealVar[] finalVars = ArrayUtils.append(vars,new RealVar[]{realGoal});
				rc.addFunction(function.toString(), finalVars);
				solver.post(rc);
				policy = ResolutionPolicy.MAXIMIZE;
				logger.info("Optimization Variable: " + realGoal.getName());
			}
		}
		logger.info("------------------------------------------");
	}
	
	/* Checking whether an expression contains only integer variables */
	private boolean involvesOnlyInt(Expression expr){
		boolean onlyInt = false;
		if (expr instanceof Variable){
			Variable v = (Variable)expr;
			IntVar iv = idToIntVar.get(v.getId());
			if (iv != null) onlyInt = true;
		}
		else if (expr instanceof MetricVariable){
			MetricVariable v = (MetricVariable)expr;
			IntVar iv = idToIntVar.get(v.getId());
			if (iv != null) onlyInt = true;
		}
		else if (expr instanceof Constant){
			Constant c = (Constant)expr;
			BasicTypeEnum type = c.getType();
			if (type.equals(BasicTypeEnum.INTEGER) || type.equals(BasicTypeEnum.LONG)){
				onlyInt = true;
			}
		}
		else if (expr instanceof ComposedExpression){
			ComposedExpression cep = (ComposedExpression)expr;
			boolean res = false;
			for (NumericExpression ne: cep.getExpressions()){
				res = involvesOnlyInt(ne);
				if (res == false) break;
			}
			if (res == true) onlyInt = true;
		}
		else if (expr instanceof ComparisonExpression){
			ComparisonExpression cep = (ComparisonExpression)expr;
			Expression expr1 = cep.getExp1();
			Expression expr2 = cep.getExp2();
			boolean res = involvesOnlyInt(expr1);
			if (res == true){
				res = involvesOnlyInt(expr2);
				if (res == true) onlyInt = true;
			}
		}
		else if (expr instanceof UnaryExpression){
			UnaryExpression ue = (UnaryExpression)expr;
			onlyInt = involvesOnlyInt(ue.getExpression());
		}
		return onlyInt;
	}
	
	/* Transforming comparator to a String */
	private String getComparator(ComparatorEnum comp, boolean opposite){
		if (!opposite){
			if (comp.equals(ComparatorEnum.DIFFERENT)) return "!=";
			else if (comp.equals(ComparatorEnum.EQUAL_TO)) return "=";
			else if (comp.equals(ComparatorEnum.GREATER_OR_EQUAL_TO)) return ">=";
			else if (comp.equals(ComparatorEnum.GREATER_THAN)) return ">";
			else if (comp.equals(ComparatorEnum.LESS_OR_EQUAL_TO)) return "<=";
			else if (comp.equals(ComparatorEnum.LESS_THAN)) return "<";
		}
		else{
			if (comp.equals(ComparatorEnum.DIFFERENT)) return "!=";
			else if (comp.equals(ComparatorEnum.EQUAL_TO)) return "=";
			else if (comp.equals(ComparatorEnum.GREATER_OR_EQUAL_TO)) return "<";
			else if (comp.equals(ComparatorEnum.GREATER_THAN)) return "<=";
			else if (comp.equals(ComparatorEnum.LESS_OR_EQUAL_TO)) return ">";
			else if (comp.equals(ComparatorEnum.LESS_THAN)) return ">=";
		}
		return "";
	}
	
	/* Creating an integer variable out of an expression possibly comprising other integer variables */
	private IntVar parseExpression(Expression expr){
		if (expr instanceof Variable || expr instanceof MetricVariable || expr instanceof Constant){
			if (expr instanceof Variable) return idToIntVar.get(((Variable)expr).getId());
			else if (expr instanceof MetricVariable) return idToIntVar.get(((MetricVariable)expr).getId());
			else{
				Constant constant = (Constant)expr;
				BasicTypeEnum type = constant.getType();
				if (type.equals(BasicTypeEnum.INTEGER)){
					IntegerValueUpperware intVal = (IntegerValueUpperware)constant.getValue();
					IntVar v = VariableFactory.bounded("Constant" + (constNum++), intVal.getValue(), intVal.getValue(), solver);
					logger.info("Constant: " + v.getName() + ": " + v);
					return v;
				}
				else if (type.equals(BasicTypeEnum.LONG)){
					LongValueUpperware longVal = (LongValueUpperware)constant.getValue();
					IntVar v = VariableFactory.bounded("Constant" + (constNum++), (int)longVal.getValue(), (int)longVal.getValue(), solver);
					logger.info("Constant: " + v.getName() + ": " + v);
					return v;
				}
			}
		}
		else{
			if (expr instanceof ComposedExpression){
				ComposedExpression cep = (ComposedExpression)expr;
				EList<NumericExpression> exprs = cep.getExpressions();
				IntVar[] vars = new IntVar[exprs.size()];
				int i = 0;
				for (NumericExpression ne: exprs){
					vars[i++] = parseExpression(ne);
				}
				OperatorEnum op = cep.getOperator();
				if (op.equals(OperatorEnum.PLUS)){
					IntVar var = VariableFactory.bounded("IntVar" + (intVarNum++), LOW_INT_LIMIT, UPPER_INT_LIMIT, solver);
					logger.info("IntVar: " + var);
					solver.post(IntConstraintFactory.sum(vars,var));
					logger.info("IntConstraint: SUM with int vars:" + printVarArray(vars) + " being equal to int var: " + var);
					return var;
				}
				else if (op.equals(OperatorEnum.MINUS)){
					IntVar var = VariableFactory.bounded("IntVar" + (intVarNum++), LOW_INT_LIMIT, UPPER_INT_LIMIT, solver);
					logger.info("IntVar: " + var);
					int[] coeff = new int[exprs.size()];
					Arrays.fill(coeff, -1);
					coeff[0] = 1;
					solver.post(IntConstraintFactory.scalar(vars, coeff, var));
					logger.info("IntConstraint: NARY_MINUS with int vars:" + printVarArray(vars) + " being equal to int var: " + var);
					return var;
				}
				else if (op.equals(OperatorEnum.TIMES)){
					IntVar prev = VariableFactory.bounded("IntVar" + (intVarNum++), LOW_INT_LIMIT, UPPER_INT_LIMIT, solver);
					solver.post(IntConstraintFactory.times(vars[0], vars[1], prev));
					for (int j = 2; j < exprs.size(); j++){
						IntVar v = VariableFactory.bounded("IntVar" + (intVarNum++), LOW_INT_LIMIT, UPPER_INT_LIMIT, solver);
						solver.post(IntConstraintFactory.times(vars[j], prev, v));
						prev = v;
					}
					logger.info("IntVar: " + prev);
					logger.info("IntConstraint: TIMES with int vars:" + printVarArray(vars) + " being equal to int var: " + prev);
					return prev;
				}
				else if (op.equals(OperatorEnum.DIV)){
					IntVar prev = VariableFactory.bounded("IntVar" + (intVarNum++), LOW_INT_LIMIT, UPPER_INT_LIMIT, solver);
					solver.post(IntConstraintFactory.eucl_div(vars[0], vars[1], prev));
					for (int j = 2; j < exprs.size(); j++){
						IntVar v = VariableFactory.bounded("IntVar" + (intVarNum++), LOW_INT_LIMIT, UPPER_INT_LIMIT, solver);
						solver.post(IntConstraintFactory.eucl_div(vars[j], prev, v));
						prev = v;
					}
					logger.info("IntVar: " + prev);
					logger.info("IntConstraint: DIV with int vars:" + printVarArray(vars) + " being equal to int var: " + prev);
					return prev;
				}
			}
			else if (expr instanceof UnaryExpression){
				UnaryExpression ue = (UnaryExpression)expr;
				IntVar var2 = parseExpression(ue.getExpression());
				if (ue instanceof SimpleUnaryExpression){
					SimpleUnaryExpression sue = (SimpleUnaryExpression)ue;
					SimpleUnaryOperatorEnum op = sue.getOperator();
					if (op.equals(SimpleUnaryOperatorEnum.ABSTRACT_VALUE)){
						IntVar newVar = VariableFactory.bounded("IntVar" + (intVarNum++), LOW_INT_LIMIT, UPPER_INT_LIMIT, solver);
						logger.info("IntVar: " + newVar);
						solver.post(IntConstraintFactory.absolute(newVar, var2));
						logger.info("IntConstraint: ABS with var: " + newVar);
						return newVar;
					}
				}
				else if (ue instanceof ComposedUnaryExpression){
					ComposedUnaryExpression cue = (ComposedUnaryExpression)ue;
					int val = cue.getValue();
					ComposedUnaryOperatorEnum op = cue.getOperator();
					if (op.equals(ComposedUnaryOperatorEnum.EXPONENTIAL_VALUE)){
						IntVar prevVar = VariableFactory.bounded("IntVar" + (intVarNum++), LOW_INT_LIMIT, UPPER_INT_LIMIT, solver);
						if (val == 1){
							logger.info("IntConstraint: EXP with x: " + var2 + " and y: " + val);
							return var2;
						}
						else if (val == 2){
							solver.post(IntConstraintFactory.times(var2,var2,prevVar));
							logger.info("IntVar: " + prevVar);
							logger.info("IntConstraint: EXP with x: " + prevVar + " and y: " + val);
							return prevVar;
						}
						else{
							solver.post(IntConstraintFactory.times(var2,var2,prevVar));
							for (int i = 3; i <= val; i++){
								IntVar varN = VariableFactory.bounded("IntVar" + (intVarNum++), LOW_INT_LIMIT, UPPER_INT_LIMIT, solver);
								solver.post(IntConstraintFactory.times(prevVar,var2,varN));
								prevVar = varN;
								if (i == val){
									logger.info("IntVar: " + varN);
									logger.info("IntConstraint: EXP with x: " + varN + " and y: " + val);
									return varN;
								}
							}
						}
					}
				}
			}
		}
		return null;
	}
	
	/* Printing the array of variables */
	private String printVarArray(solver.variables.Variable[] vars){
		String toRet = "[";
		if (vars.length > 0){
			toRet += vars[0].getName();
			for (int i = 1; i < vars.length; i++)
				toRet += " , " + vars[i].getName();
		}
		toRet += "]";
		return toRet;
	}
	
	/* Creating a real variable out of an expression */
	private RealVar parseRealExpression(Expression expr, RealConstraint rc){
		if (expr instanceof Variable || expr instanceof MetricVariable || expr instanceof Constant){
			if (expr instanceof Variable){
				RealVar v = null;
				v = idToRealVar.get(((Variable)expr).getId());
				if (v == null){
					IntVar iv = idToIntVar.get(((Variable)expr).getId());
					if (iv != null){
						v = VariableFactory.real(iv,epsilon);
						logger.info("RealVar: " + v + " on top of IntVar: " + iv.getName());
					}
				}
				return v;
			}
			else if (expr instanceof MetricVariable){
				RealVar v = null;
				v = idToRealVar.get(((MetricVariable)expr).getId());
				if (v == null){
					IntVar iv = idToIntVar.get(((MetricVariable)expr).getId());
					if (iv != null){
						v = VariableFactory.real(iv,epsilon);
						logger.info("RealVar: " + v + " on top of IntVar: " + iv.getName());
					}
					else{
						MetricVariable mv = (MetricVariable)expr;
						BasicTypeEnum type = mv.getType();
						RealVar var = VariableFactory.real("RealVar" + (realVarNum++), LOW_REAL_LIMIT, UPPER_REAL_LIMIT, epsilon, solver);
						logger.info("RealVar: " + var);
						idToRealVar.put(var.getName(), var);
						rc.addFunction("{0} >= 0",var);
						return var;
					}
				}
				return v;
			}
			else{
				Constant constant = (Constant)expr;
				BasicTypeEnum type = constant.getType();
				if (type.equals(BasicTypeEnum.INTEGER)){
					IntegerValueUpperware intVal = (IntegerValueUpperware)constant.getValue();
					int val = intVal.getValue();
					RealVar v = VariableFactory.real("Constant" + (constNum++), val, val, epsilon, solver);
					logger.info("Constant: " + v);
					return v;
				}
				else if (type.equals(BasicTypeEnum.LONG)){
					LongValueUpperware longVal = (LongValueUpperware)constant.getValue();
					long val = longVal.getValue();
					RealVar v = VariableFactory.real("Constant" + (constNum++), val, val, epsilon, solver);
					logger.info("Constant: " + v);
					return v;
				}
				else if (type.equals(BasicTypeEnum.FLOAT)){
					FloatValueUpperware floatVal = (FloatValueUpperware)constant.getValue();
					float val = floatVal.getValue();
					RealVar v = VariableFactory.real("Constant" + (constNum++), val, val, epsilon, solver);
					logger.info("Constant: " + v);
					return v;
				}
				else if (type.equals(BasicTypeEnum.DOUBLE)){
					DoubleValueUpperware doubleVal = (DoubleValueUpperware)constant.getValue();
					double val = doubleVal.getValue();
					RealVar v = VariableFactory.real("Constant" + (constNum++), val, val, epsilon, solver);
					logger.info("Constant: " + v);
					return v;
				}
			}
		}
		else{
			RealVar var = VariableFactory.real("RealVar" + (realVarNum++), LOW_REAL_LIMIT, UPPER_REAL_LIMIT, epsilon, solver);
			logger.info("RealVar: " + var);
			if (expr instanceof ComposedExpression){
				ComposedExpression cep = (ComposedExpression)expr;
				EList<NumericExpression> exprs = cep.getExpressions();
				int size = exprs.size();
				RealVar[] vars = new RealVar[size];
				int i = 0;
				for (NumericExpression ne: exprs){
					vars[i++] = parseRealExpression(ne,rc);
				}
				OperatorEnum op = cep.getOperator();
				StringBuilder function = new StringBuilder("(");
				if (op.equals(OperatorEnum.PLUS)){
					function.append(" {0} ");
					for (int j = 1; j < size; j++)
						function.append("+ {" + j + "} ");
					function.append(") = {" + size + "}");
					RealVar[] finalVars = ArrayUtils.append(vars,new RealVar[]{var});
					String func = function.toString();
					rc.addFunction(func, finalVars);
					logger.info("RealConstraint: " + func + " with real vars:" + printVarArray(finalVars));
				}
				else if (op.equals(OperatorEnum.MINUS)){
					function.append(" {0} ");
					for (int j = 1; j < size; j++)
						function.append("- {" + j + "} ");
					function.append(") = {" + size + "} ");
					RealVar[] finalVars = ArrayUtils.append(vars,new RealVar[]{var});
					String func = function.toString();
					rc.addFunction(func, finalVars);
					logger.info("RealConstraint: " + func + " with real vars:" + printVarArray(finalVars));
				}
				else if (op.equals(OperatorEnum.TIMES)){
					function.append(" {0} ");
					for (int j = 1; j < size; j++)
						function.append("* {" + j + "} ");
					function.append(") = {" + size + "} ");
					RealVar[] finalVars = ArrayUtils.append(vars,new RealVar[]{var});
					String func = function.toString();
					rc.addFunction(func, finalVars);
					logger.info("RealConstraint: " + func + " with real vars:" + printVarArray(finalVars));
				}
				else if (op.equals(OperatorEnum.DIV)){
					function.append(" {0} ");
					for (int j = 1; j < size; j++)
						function.append("/ {" + j + "} ");
					function.append(") = {" + size + "} ");
					RealVar[] finalVars = ArrayUtils.append(vars,new RealVar[]{var});
					String func = function.toString();
					rc.addFunction(func, finalVars);
					logger.info("RealConstraint: " + func + " with real vars:" + printVarArray(finalVars));
				}
				return var;
			}
			else if (expr instanceof UnaryExpression){
				UnaryExpression ue = (UnaryExpression)expr;
				RealVar var2 = parseRealExpression(ue.getExpression(),rc);
				StringBuilder function = new StringBuilder("(");
				if (ue instanceof SimpleUnaryExpression){
					SimpleUnaryExpression sue = (SimpleUnaryExpression)ue;
					SimpleUnaryOperatorEnum op = sue.getOperator();
					if (op.equals(SimpleUnaryOperatorEnum.ABSTRACT_VALUE)){
						function.append(" abs({0}) = {1} )");
						RealVar[] vars = new RealVar[]{var2,var};
						String func = function.toString();
						rc.addFunction(func, vars);
						logger.info("RealConstraint: " + func + " with real vars:" + printVarArray(vars));
					}
					else if (op.equals(SimpleUnaryOperatorEnum.LN_VALUE)){
						function.append(" ln({0}) = {1} )");
						RealVar[] vars = new RealVar[]{var2,var};
						String func = function.toString();
						rc.addFunction(func, vars);
						logger.info("RealConstraint: " + func + " with real vars:" + printVarArray(vars));
					}
					return var;
				}
				else if (ue instanceof ComposedUnaryExpression){
					ComposedUnaryExpression cue = (ComposedUnaryExpression)ue;
					int val = cue.getValue();
					ComposedUnaryOperatorEnum op = cue.getOperator();
					if (op.equals(ComposedUnaryOperatorEnum.EXPONENTIAL_VALUE)){
						/*if (val == 1) return var2;
						else if (val == 2){
							function = new StringBuilder("(");
							function.append("{0} * {0} = {1} )");
							RealVar[] vars = new RealVar[]{var2,var};
							rc.addFunction(function.toString(),vars);
						}
						else{
							function = new StringBuilder("( {0} ");
							for (int i = 1; i < val; i++){
								function.append("* {" + i + "} ");
							}
							function.append(" )");
							RealVar[] vars = new RealVar[]{var2,var};
							rc.addFunction(function.toString(),vars);
						}*/
						function = new StringBuilder("( pow( {0}, " + val + " ) = {1} )");
						RealVar[] vars = new RealVar[]{var2,var};
						String func = function.toString();
						rc.addFunction(func, vars);
						logger.info("RealConstraint: " + func + " with real vars:" + printVarArray(vars));
					}
					else{
						//! Is this correct?
						function = new StringBuilder("( log( {0}, " + val + " ) = {1} )");
						RealVar[] vars = new RealVar[]{var2,var};
						String func = function.toString();
						rc.addFunction(func, vars);
						logger.info("RealConstraint: " + func + " with real vars:" + printVarArray(vars));
					}
					return var;
				}
			}
		}
		return null;
	}
	
	/* Creating the constraints of the problem to solve */
	private void createConstraints(EList<ComparisonExpression> constraints){
		RealConstraint rc = null;
		logger.info("--------------- Constraints ---------------");
		for (ComparisonExpression constr: constraints){
			Expression expr1 = constr.getExp1();
			Expression expr2 = constr.getExp2();
			ComparatorEnum operator = constr.getComparator();
			boolean isInt = involvesOnlyInt(expr1);
			if (isInt){
				isInt = involvesOnlyInt(expr2);
				logger.info("Constraint only involves integer variables");
			}
			else{
				logger.info("Constraint involves real variables");
			}
			
			Constraint constraint = null;
			if (isInt){
				if (expr1 instanceof Variable){
					Variable var = (Variable)expr1;
					String id = var.getId();
					IntVar v = idToIntVar.get(id);
					IntVar var2 = parseExpression(expr2);
					String opStr = getComparator(operator,false);
					constraint = IntConstraintFactory.arithm(v, opStr, var2);
					logger.info("IntConstraint: " + v.getId() + " " + opStr + " " + var2.getId());
				}
				else if (expr1 instanceof MetricVariable){
					MetricVariable var = (MetricVariable)expr1;
					String id = var.getId();
					IntVar v = idToIntVar.get(id);
					IntVar var2 = parseExpression(expr2);
					String opStr = getComparator(operator,false);
					constraint = IntConstraintFactory.arithm(v, opStr, var2);
					logger.info("IntConstraint: " + v.getName() + " " + opStr + " " + var2.getName());
				}
				else if (expr1 instanceof Constant){
					Constant constant = (Constant)expr1;
					BasicTypeEnum type = constant.getType();
					if (type.equals(BasicTypeEnum.INTEGER)){
						IntegerValueUpperware intVal = (IntegerValueUpperware)constant.getValue();
						int value = intVal.getValue();
						IntVar v = parseExpression(expr2);
						//System.out.println("Parsed expression is: " + parseExpression(expr2));
						String opStr = getComparator(operator,true);
						constraint = IntConstraintFactory.arithm(parseExpression(expr2), opStr, value);
						logger.info("IntConstraint: " + v.getName() + " " + opStr + " " + value);
					}
					else if (type.equals(BasicTypeEnum.LONG)){
						LongValueUpperware longVal = (LongValueUpperware)constant.getValue();
						long value = longVal.getValue();
						IntVar v = parseExpression(expr2);
						//System.out.println("Parsed expression is: " + parseExpression(expr2));
						String opStr = getComparator(operator,true);
						constraint = IntConstraintFactory.arithm(v, opStr, (int)value);
						logger.info("IntConstraint: " + v.getName() + " " + opStr + " " + value);
					}
				}
				if (expr1 instanceof ComposedExpression || expr1 instanceof UnaryExpression){
					if (expr1 instanceof ComposedExpression){
						//System.out.println("First expression is: " + parseExpression(expr1) + " and second expression is: " + parseExpression(expr2));
						IntVar v1 = parseExpression(expr1);
						IntVar v2 = parseExpression(expr2);
						String opStr = getComparator(operator,false);
						constraint = IntConstraintFactory.arithm(v1, opStr, v2);
						logger.info("IntConstraint: " + v1.getName() + " " + opStr + " " + v2.getName());
					}
				}
			}
			else{
				if (rc == null) rc = new RealConstraint(solver);
				StringBuilder function = new StringBuilder("(");
				if (expr1 instanceof Variable){
					Variable var = (Variable)expr1;
					String id = var.getId();
					logger.info("Checking variable with name: " + id);
					RealVar v = idToRealVar.get(id);
					logger.info("RealVar is: " + v);
					if (v == null){
						IntVar iv = idToIntVar.get(id);
						//Checking if int var is involved
						if (iv != null){
							v = VariableFactory.real(iv,epsilon);
							logger.info("RealVar: " + v + " on top of IntVar: " + iv.getName());
						}
						//If not, then we have a problem
						else{
							logger.error("Got a new variable not previously parsed");
						}
					}
					function.append(" {0} " + getComparator(operator,false) + " {1} )");
					logger.info("Operator is: " + operator + " second expression is: " + expr2);
					RealVar[] all_vars = new RealVar[]{v, parseRealExpression(expr2,rc)};
					String func = function.toString();
					logger.info("RealConstraint: " + func + " with RealVars:" + printVarArray(all_vars));
			        rc.addFunction(func, all_vars);
				}
				else if (expr1 instanceof MetricVariable){
					System.out.println("CASE 2");
					MetricVariable var = (MetricVariable)expr1;
					String id = var.getId();
					RealVar v = idToRealVar.get(id);
					function.append(" {0} " + getComparator(operator,false) + " {1} )");
					RealVar[] all_vars = new RealVar[]{v, parseRealExpression(expr2,rc)};
					String func = function.toString();
					logger.info("RealConstraint: " + func + " with RealVars:" + printVarArray(all_vars));
			        rc.addFunction(func, all_vars);
				}
				else if (expr1 instanceof Constant){
					System.out.println("CASE 3");
					Constant constant = (Constant)expr1;
					BasicTypeEnum type = constant.getType();
					if (type.equals(BasicTypeEnum.INTEGER)){
						IntegerValueUpperware intVal = (IntegerValueUpperware)constant.getValue();
						function.append(" " + intVal.getValue() + getComparator(operator,false) + " {0} )");
						RealVar[] all_vars = new RealVar[]{parseRealExpression(expr2,rc)};
						String func = function.toString();
						logger.info("RealConstraint: " + func + " with RealVars:" + printVarArray(all_vars));
				        rc.addFunction(func, all_vars);
					}
					else if (type.equals(BasicTypeEnum.LONG)){
						LongValueUpperware longVal = (LongValueUpperware)constant.getValue();
						function.append(" " + longVal.getValue() + getComparator(operator,false) + " {0} )");
						RealVar[] all_vars = new RealVar[]{parseRealExpression(expr2,rc)};
						String func = function.toString();
						logger.info("RealConstraint: " + func + " with RealVars:" + printVarArray(all_vars));
				        rc.addFunction(func, all_vars);
					}
					else if (type.equals(BasicTypeEnum.FLOAT)){
						FloatValueUpperware floatVal = (FloatValueUpperware)constant.getValue();
						function.append(" " + floatVal.getValue() + getComparator(operator,false) + " {0} )");
						RealVar[] all_vars = new RealVar[]{parseRealExpression(expr2,rc)};
						String func = function.toString();
						logger.info("RealConstraint: " + func + " with RealVars:" + printVarArray(all_vars));
				        rc.addFunction(func, all_vars);
					}
					else if (type.equals(BasicTypeEnum.DOUBLE)){
						DoubleValueUpperware doubleVal = (DoubleValueUpperware)constant.getValue();
						function.append(" " + doubleVal.getValue() + getComparator(operator,false) + " {0} )");
						RealVar[] all_vars = new RealVar[]{parseRealExpression(expr2,rc)};
						String func = function.toString();
						logger.info("RealConstraint: " + func + " with RealVars:" + printVarArray(all_vars));
				        rc.addFunction(func, all_vars);
					}
				}
				if (expr1 instanceof ComposedExpression || expr1 instanceof UnaryExpression){
					ComposedExpression expr = (ComposedExpression)expr1;
					RealVar var1 = parseRealExpression(expr, rc);
					RealVar var2 = parseRealExpression(expr2, rc);
					function.append(" {0} " + getComparator(operator,false) + " {1} )");
					RealVar[] all_vars = new RealVar[]{var1,var2};
					String func = function.toString();
					logger.info("RealConstraint: " + func + " with RealVars:" + printVarArray(all_vars));
			        rc.addFunction(func, all_vars);
				}
			}
			if (constraint != null){
				solver.post(constraint);
			}
		}
		if (rc != null){
			solver.post(rc);
		}
		logger.info("------------------------------------------");
	}
	
	/* Creating the metric variables */
	private void createMetricVariables(EList<MetricVariable> vars){
		logger.info("--------------- MetricVariables ---------------");
		for (MetricVariable var: vars){
			BasicTypeEnum type = var.getType();
			if (type.equals(BasicTypeEnum.INTEGER) || type.equals(BasicTypeEnum.LONG)){
				String id = var.getId();
				IntVar v = VariableFactory.bounded(id, LOW_INT_LIMIT, UPPER_INT_LIMIT, solver);
				logger.info("IntVar: " + v);
				if (idToIntVar == null) idToIntVar = new Hashtable<String,IntVar>();
				idToIntVar.put(id,v);
			}
			else if (type.equals(BasicTypeEnum.DOUBLE) || type.equals(BasicTypeEnum.FLOAT)){
				String id = var.getId();
				RealVar v = VariableFactory.real(id, LOW_REAL_LIMIT, UPPER_REAL_LIMIT, epsilon, solver);
				logger.info("RealVar: " + v);
				if (idToRealVar == null) idToRealVar = new Hashtable<String,RealVar>();
				idToRealVar.put(id,v);
			}
		}
		logger.info("------------------------------------------");
	}
	
	/* Creating the normal variables */
	private void createVariables(EList<Variable> vars){
		logger.info("--------------- Variables ---------------");
		for (Variable var: vars){
			Domain dom = var.getDomain();
			if (dom instanceof NumericDomain && ! (dom instanceof RangeDomain)){
				//System.out.println("Got numeric domain for variable:" + var.getId());
				NumericDomain nd = (NumericDomain)dom;
				BasicTypeEnum type = nd.getType();
				if (type.equals(BasicTypeEnum.INTEGER) || type.equals(BasicTypeEnum.LONG)){
					String id = var.getId();
					IntVar v = VariableFactory.bounded(id, LOW_INT_LIMIT, UPPER_INT_LIMIT, solver);
					logger.info("IntVar: " + v);
					if (idToIntVar == null) idToIntVar = new Hashtable<String,IntVar>();
					idToIntVar.put(id,v);
				}
				else if (type.equals(BasicTypeEnum.DOUBLE) || type.equals(BasicTypeEnum.FLOAT)){
					String id = var.getId();
					RealVar v = VariableFactory.real(id, LOW_REAL_LIMIT, UPPER_REAL_LIMIT, epsilon, solver);
					if (idToRealVar == null) idToRealVar = new Hashtable<String,RealVar>();
					idToRealVar.put(id,v);
				}
			}
			else if (dom instanceof RangeDomain){
				RangeDomain rd = (RangeDomain)dom;
				//System.out.println("Got range domain for variable:" + var.getId());
				BasicTypeEnum type = rd.getType();
				//System.out.println("Type of variable: " + var.getId() + " is: " + type);
				NumericValueUpperware val1 = rd.getFrom();
				NumericValueUpperware val2 = rd.getTo();
				if (type.equals(BasicTypeEnum.INTEGER)){
					IntegerValueUpperware int1 = (IntegerValueUpperware)val1;
					IntegerValueUpperware int2 = (IntegerValueUpperware)val2;
					String id = var.getId();
					//System.out.println("Integer variable has the values: " + int1.getValue() + " " + int2.getValue());
					IntVar v = VariableFactory.bounded(id, int1.getValue(), int2.getValue(), solver);
					logger.info("IntVar: " + v);
					if (idToIntVar == null) idToIntVar = new Hashtable<String,IntVar>();
					idToIntVar.put(id,v);
				}
				else if(type.equals(BasicTypeEnum.LONG)){
					LongValueUpperware long1 = (LongValueUpperware)val1;
					LongValueUpperware long2 = (LongValueUpperware)val2;
					String id = var.getId();
					IntVar v = VariableFactory.bounded(id, (int)long1.getValue(), (int)long2.getValue(), solver);
					logger.info("IntVar: " + v);
					if (idToIntVar == null) idToIntVar = new Hashtable<String,IntVar>();
					idToIntVar.put(id,v);
				}
				else if (type.equals(BasicTypeEnum.DOUBLE)){
					DoubleValueUpperware real1 = (DoubleValueUpperware)val1;
					DoubleValueUpperware real2 = (DoubleValueUpperware)val2;
					String id = var.getId();
					RealVar v = VariableFactory.real(id, real1.getValue(), real2.getValue(), epsilon, solver);
					logger.info("RealVar: " + v);
					if (idToRealVar == null) idToRealVar = new Hashtable<String,RealVar>();
					idToRealVar.put(id,v);
				}
				else if (type.equals(BasicTypeEnum.FLOAT)){
					FloatValueUpperware float1 = (FloatValueUpperware)val1;
					FloatValueUpperware float2 = (FloatValueUpperware)val2;
					String id = var.getId();
					RealVar v = VariableFactory.real(id, float1.getValue(), float2.getValue(), epsilon, solver);
					logger.info("RealVar: " + v);
					if (idToRealVar == null) idToRealVar = new Hashtable<String,RealVar>();
					idToRealVar.put(id,v);
				}
			}
		}
		logger.info("------------------------------------------");
	}
	
	/* Creating the constants of the cp problem */
	private void createConstants(EList<Constant> constants){
		logger.info("--------------- Constants ---------------");
		for (Constant constant: constants){
			NumericValueUpperware value = constant.getValue();
			//System.out.println("Type of constant is: " + constant.getType());
			if (constant.getType().equals(BasicTypeEnum.DOUBLE)){
				if (value instanceof DoubleValueUpperware){
					double val = ((DoubleValueUpperware)value).getValue();
					RealVar var = VariableFactory.real(constant.getId(), val, val, epsilon, solver);
					logger.info("Constant " + constant.getId() + ": " + var);
					if (idToRealVar == null) idToRealVar = new Hashtable<String,RealVar>();
					idToRealVar.put(constant.getId(),var);
				} 
			}
			else if (constant.getType().equals(BasicTypeEnum.INTEGER)){
				if (value instanceof IntegerValueUpperware){
					int val = ((IntegerValueUpperware)value).getValue();
					IntVar var = VariableFactory.fixed(val, solver);
					logger.info("Constant: " + constant.getId() + ": " + var);
					if (idToIntVar == null) idToIntVar = new Hashtable<String,IntVar>();
					idToIntVar.put(constant.getId(),var);
				}
			}
			else if (constant.getType().equals(BasicTypeEnum.LONG)){
				if (value instanceof LongValueUpperware){
					long val = ((IntegerValueUpperware)value).getValue();
					IntVar var = VariableFactory.fixed((int)val, solver);
					logger.info("Constant " + constant.getId() + ": " + var);
					if (idToIntVar == null) idToIntVar = new Hashtable<String,IntVar>();
					idToIntVar.put(constant.getId(),var);
				}
			}
			else if (constant.getType().equals(BasicTypeEnum.FLOAT)){
				if (value instanceof FloatValueUpperware){
					double val = ((FloatValueUpperware)value).getValue();
					RealVar var = VariableFactory.real(constant.getId(), val, val, epsilon, solver);
					logger.info("Constant " + constant.getId() + ": " + var);
					if (idToRealVar == null) idToRealVar = new Hashtable<String,RealVar>();
					idToRealVar.put(constant.getId(),var);
				}
			}
		}
		logger.info("------------------------------------------");
	}
	
	/* Re-initializing the main configuration variables of the solver*/
	private void dispose(){
		intVarNum = 0;
		realVarNum = 0;
		constNum = 0;
	}
	
	public static void main(String[] args){
		//Running as daemon
		if (args.length == 0){
			logger.info("Running the solver as a daemon");
			Thread t = new Thread(new CPSolverDaemon());
			t.setDaemon(false);
			t.start();
			//System.exit(1);
			return;
		}
		//Running test to check that solver runs as a daemon and functions properly
		else if (args.length == 1){
			logger.info("Testing the solver as a daemon");
			CDOClient cl = new CDOClient();
			cl.registerPackage(CpPackage.eINSTANCE);
			cl.registerPackage(TypesPackage.eINSTANCE);
			cl.registerPackage(eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage.eINSTANCE);
			cl.importModel("input/LsyCP.xmi", "cps/model2", false);
			cl.closeSession();
			ExecutorService thr = Executors.newFixedThreadPool(2);
			thr.submit(new CPSolverDaemon());
			thr.submit(new FakeAdapterPublisher("cps/model2"));
			try{
				Thread.sleep(20000);
			}
			catch(Exception e){
				logger.error("Thread interrupted from sleep", e);
				//e.printStackTrace();
			}
			thr.shutdownNow();
			System.exit(1);
		}
		CDOClient cl = new CDOClient();
		cl.registerPackage(TypesPackage.eINSTANCE);
		cl.registerPackage(CpPackage.eINSTANCE);
		//Read/write from CDOServer or from file system
		String mode = args[0];
		//CDO or file path
		String path = args[1];
		String timestampStr = null;
		if (args.length == 3)
			timestampStr = args[2];
		long timestamp = 0;
		if (timestampStr != null){
			timestamp = new Long(timestampStr);
		}
		CPSolver solver = null;
		try{
			if (mode.toLowerCase().equals("cdo")){
				solver = new CPSolver(path,null,timestamp);
			}
			else{
				solver = new CPSolver(null,path,timestamp);
			}
			solver.solve();
		}
		catch(Exception e){
			logger.error("Something went wrong while solving the problem",e);
			e.printStackTrace();
		}
		cl.closeSession();
	}
	
}
