package eu.melodic.upperware.cpsolver.lib;

/* Copyright (C) 2015 KYRIAKOS KRITIKOS <kritikos@ics.forth.gr> */

/* This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ 
 */

import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.utilitygenerator.UtilityFunctionType;
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication;
import eu.melodic.upperware.utilitygenerator.model.MetricDTO;
import eu.melodic.upperware.utilitygenerator.model.MetricType;
import eu.melodic.upperware.utilitygenerator.model.VariableDTO;
import eu.paasage.mddb.cdo.client.CDOClient;
import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.metamodel.types.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
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

import java.util.*;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

@Slf4j
public class CPSolver {

	private Solver solver = null;
	private String cdoPath = null;
	private String pathName = null;
	private ResolutionPolicy policy = null;
	private IntVar intGoal = null;
	private RealVar realGoal = null;
	private Hashtable<String,IntVar> idToIntVar = new Hashtable<>();
	private Hashtable<String,RealVar> idToRealVar = new Hashtable<>();
	private static final double epsilon = 0.000001d;
	private static final int LOW_INT_LIMIT = -10000;
	private static final int UPPER_INT_LIMIT = 100000000;
	private static final double LOW_REAL_LIMIT = -1000000000.0;
	private static final double UPPER_REAL_LIMIT = 1000000000.0;
	private static UtilityFunctionType utilityFunctionType;
	private static String utilityFunctionTypePrefix = "METRIC_UTILITYTYPE_";
	private int intVarNum = 0;
	private int realVarNum = 0;
	private int constNum = 0;
	private boolean cdoMode = false;
	private long timestamp = 0;
	private boolean useExternalOptimizer = false;
	private UtilityGeneratorApplication utilityGenerator;
	private double maxUtility;
	private List<VariableDTO> variablesForUG = new ArrayList<>();

	/* Constructor which also reads the CP Model either from CDO via
	 * a CDO path given as String or from file system via a String path 
	 */
	public CPSolver(String cdoPath, String pathName, Boolean useExternalOptimizer, NodeCandidates nodeCandidates){
		this();
		this.cdoPath = cdoPath;
		this.pathName = pathName;
		this.useExternalOptimizer = useExternalOptimizer != null && useExternalOptimizer;

		readCPModel(cdoPath,pathName);

		if (this.useExternalOptimizer){
			//FIXME metrics should be from Metric Collector
			Map<MetricType, MetricDTO[]> metrics = new HashMap<>();
			metrics.put(MetricType.MAX_RESPONSE_TIME, new MetricDTO[]{new MetricDTO(MetricType.MAX_RESPONSE_TIME, "", 30)});
			metrics.put(MetricType.NOM_RESPONSE_TIME, new MetricDTO[]{new MetricDTO(MetricType.NOM_RESPONSE_TIME, "", 20)});
			metrics.put(MetricType.AVG_RESPONSE_TIME, new MetricDTO[]{new MetricDTO(MetricType.AVG_RESPONSE_TIME, "",3)});
			metrics.put(MetricType.COST_WEIGHT, new MetricDTO[]{new MetricDTO(MetricType.COST_WEIGHT, "",0.5)});

			this.utilityGenerator = new UtilityGeneratorApplication(variablesForUG, metrics, utilityFunctionType,
					nodeCandidates);
		}
	}
	
	/* Constructor which also reads the CP Model either from CDO via 
	 * a CDO path given as String or from file system via a String path 
	 */
	public CPSolver(String cdoPath, String pathName, long timestamp){
		this();
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
		createConstants(cp.getConstants());
		createVariables(cp.getVariables());
		createMetricVariables(cp.getMetricVariables());
		createConstraints(cp.getConstraints());
		createVariablesForUG(cp.getVariables());
		createUtilityFunctionType(cp);

		//Checking if metric-based solution exists
		if (timestamp != 0){
			EList<Solution> sols = cp.getSolution();
			if (sols != null && !sols.isEmpty()){
				checkSolution(sols);
			}
		} else{
			EList<Solution> sols = cp.getSolution();
			//Initial solution maps to default values for the metric variables 
			if (sols != null && sols.size() == 1){
                timestamp = sols.get(0).getTimestamp();
                checkSolution(sols);
			}
		}
		//Create optimisation goal

	}

	private void createVariablesForUG(EList<Variable> variables) {
		log.info("Creating variables for Utility Generator");
		this.variablesForUG = variables.stream()
				.map(variable -> new VariableDTO(variable.getId(), variable.getComponentId(), variable.getVariableType()))
				.collect(Collectors.toList());
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
				log.info("Found solution with the timestamp given");
				for (MetricVariableValue mvv: sol.getMetricVariableValue()){
					MetricVariable mv = mvv.getVariable();
					NumericValueUpperware val = mvv.getValue();
					String mvName = mv.getId();
					IntVar intVar = idToIntVar.get(mvName);
					log.info("CHECKING METRIC VARIABLE TO ASSIGN IT A CONSTANT VALUE");
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
						//solver.post(IntConstraintFactory.arithm(intVar, "=", actualVal));
						try{
							intVar.updateLowerBound(actualVal,null);
							intVar.updateUpperBound(actualVal,null);
							log.info("UPDATING INTEGER VARIABLE!!!: " + intVar);
						}
						catch(Exception e){
							e.printStackTrace();
						}
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
								log.info("UPDATING REAL VARIABLE!!!: " + realVar);
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
		ConstraintProblem cp = null;
		log.info("Reading CP model...");
		CDOClient cl = new CDOClient();
		cl.registerPackage(TypesPackage.eINSTANCE);
		cl.registerPackage(CpPackage.eINSTANCE);
		this.cdoPath = cdoPath;
		this.pathName = pathName;
		if (cdoPath != null){
			log.info("Loading resource from CDO: " +cdoPath);
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
			log.info("Loading resource from file: " +pathName);
			cdoMode = false;
			cp = (ConstraintProblem)cl.loadModel(pathName);
			readModel(cp);
		}
		log.info("CDO Mode: " + cdoMode);
		cl.closeSession();
	}
	
	/* Solves the CPModel previously read, updates the model if a solution was found 
	 * and returns a boolean value indicating whether a solution has been found or not
	 */
	public boolean solve() throws Exception{
		boolean hasSolutions = false;
		if (idToIntVar != null){
			Collection<IntVar> values = idToIntVar.values();
			IntVar[] vars = values.stream().toArray(value -> new IntVar[values.size()]);
			solver.set(IntStrategyFactory.random(vars, System.currentTimeMillis()));
		}

		//if useExternalOptimizer is set - use Utility Generator
		if(useExternalOptimizer){
			log.info("Using Utility Generator for solution space:");

			if(solver.findSolution()) {
				log.info("Checking utility of #1 solution.");

				int i=1;
				maxUtility = 0.0;
				calculateUtility();
				while(solver.nextSolution()){
					log.info("Checking utility of: #{} solution.", i++ );
					calculateUtility();
				}
				log.info("max Utility = {}", maxUtility);
				utilityGenerator.printConfigurationWithMaximumUtility();
				hasSolutions = (solver.isFeasible() == ESat.TRUE);
			}
		} else {
			if (policy != null) {
				if (realGoal != null) {
					solver.findOptimalSolution(policy, realGoal);
					log.info("1. Optimal value is: " + realGoal.getUB());
				} else {
					solver.findOptimalSolution(policy, intGoal);
					log.info("2. Optimal value is: " + intGoal.getValue());
				}
				log.info("1. Checking if solver has solutions");
				hasSolutions = (solver.isFeasible() == ESat.TRUE);
				log.info("1. Does solver has solutions? " + hasSolutions);
				if (hasSolutions) saveSolution();
				try {
					dispose();
					solver.getIbex().release();
				} catch (Exception e) {
					log.error("1. Something went wrong while disposing the solver", e);
				}
			} else {
				log.info("2. Checking if solver has solutions");
				hasSolutions = solver.findSolution();
				log.info("2. Does solver has solutions? " + hasSolutions);
				if (hasSolutions) saveSolution();
				try {
					dispose();
					solver.getIbex().release();
				} catch (Exception e) {
					log.error("2. Something went wrong while disposing the solver", e);
				}
			}
		}
		return hasSolutions;
	}
	
	/* Saving the solution in the cp model and storing back the model to its 
	 * initial position, either in CDO repository or the file system
	 */
	private void saveSolution(){
		log.info("Saving solution .....");
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
		} else{
			cp = (ConstraintProblem)CDOClient.loadModel(pathName);
		}
		Solution solution = null;
		if (timestamp == 0){
			solution = CpFactory.eINSTANCE.createSolution();
			solution.setTimestamp(new Date().getTime());
			cp.getSolution().add(solution);
		} else {
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
					log.info("Discovered value for variable :" + var.getId() + " is: " + val);
					if (dom instanceof RangeDomain){
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
					} else if (dom instanceof NumericDomain){
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
				} else {
					RealVar rv = idToRealVar.get(var.getId());
					if (rv != null){
						double val = rv.getUB();
						log.info("Discovered value for variable :" + var.getId() + " is: " + val);
						if (dom instanceof RangeDomain){
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
						} else if (dom instanceof NumericDomain){
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
			log.info("..... Solution saved");
		}
		catch(Exception e){
			log.error("Something went wrong while storing the solution",e);
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
				if (!res) break;
			}
			if (res) onlyInt = true;
		}
		else if (expr instanceof ComparisonExpression){
			ComparisonExpression cep = (ComparisonExpression)expr;
			Expression expr1 = cep.getExp1();
			Expression expr2 = cep.getExp2();
			boolean res = involvesOnlyInt(expr1);
			if (res){
				res = involvesOnlyInt(expr2);
				if (res) onlyInt = true;
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
			if (expr instanceof Variable || expr instanceof MetricVariable) {
				return idToIntVar.get(expr.getId());
			} else {
				Constant constant = (Constant)expr;
				BasicTypeEnum type = constant.getType();
				if (type.equals(BasicTypeEnum.INTEGER)){
					IntegerValueUpperware intVal = (IntegerValueUpperware)constant.getValue();
					IntVar v = createIntVar("Constant" + (constNum++), intVal.getValue(), intVal.getValue());
					log.info("Constant: " + v.getName() + ": " + v);
					return v;
				}
				else if (type.equals(BasicTypeEnum.LONG)){
					LongValueUpperware longVal = (LongValueUpperware)constant.getValue();
					IntVar v = createIntVar("Constant" + (constNum++), (int)longVal.getValue(), (int)longVal.getValue());
					log.info("Constant: " + v.getName() + ": " + v);
					return v;
				}
			}
		} else {
			if (expr instanceof ComposedExpression){
				ComposedExpression cep = (ComposedExpression)expr;
				EList<NumericExpression> exprs = cep.getExpressions();

				IntVar[] vars = exprs.stream()
						.map(this::parseExpression)
						.toArray(value -> new IntVar[exprs.size()]);

				OperatorEnum op = cep.getOperator();
				if (op.equals(OperatorEnum.PLUS)){
					IntVar var = createIntVar(getBounds(vars, OperatorEnum.PLUS));
					log.info("IntVar: " + var);
					solver.post(IntConstraintFactory.sum(vars,var));
					log.info("IntConstraint: SUM with int vars:" + printVarArray(vars) + " being equal to int var: " + var);
					return var;
				}
				else if (op.equals(OperatorEnum.MINUS)){
					IntVar var = createIntVar(getBounds(vars, OperatorEnum.MINUS));
					int[] coeff = new int[exprs.size()];
					Arrays.fill(coeff, -1);
					coeff[0] = 1;
					log.info("IntVar: " + var);

					solver.post(IntConstraintFactory.scalar(vars, coeff, var));
					log.info("IntConstraint: NARY_MINUS with int vars:" + printVarArray(vars) + " being equal to int var: " + var);
					return var;
				}
				else if (op.equals(OperatorEnum.TIMES)){
					IntVar prev = createIntVar(getBounds(new IntVar[]{vars[0], vars[1]}, OperatorEnum.TIMES));
					solver.post(IntConstraintFactory.times(vars[0], vars[1], prev));
					for (int j = 2; j < exprs.size(); j++){
						IntVar v = createIntVar(getBounds(new IntVar[]{vars[j], prev}, OperatorEnum.TIMES));
						solver.post(IntConstraintFactory.times(vars[j], prev, v));
						prev = v;
					}
					log.info("IntVar: " + prev);
					log.info("IntConstraint: TIMES with int vars:" + printVarArray(vars) + " being equal to int var: " + prev);
					return prev;

				}
				else if (op.equals(OperatorEnum.DIV)){

					IntVar prev = createIntVar(getBounds(new IntVar[]{vars[0], vars[1]}, OperatorEnum.DIV));
					solver.post(IntConstraintFactory.eucl_div(vars[0], vars[1], prev));
					for (int j = 2; j < exprs.size(); j++){
						IntVar v = createIntVar(getBounds(new IntVar[]{vars[j], prev}, OperatorEnum.DIV));
						solver.post(IntConstraintFactory.eucl_div(vars[j], prev, v));
						prev = v;
					}
					log.info("IntVar: " + prev);
					log.info("IntConstraint: DIV with int vars:" + printVarArray(vars) + " being equal to int var: " + prev);
					return prev;
				}
				else if (op.equals(OperatorEnum.EQ)){
					BoolVar prev = createBoolVar();
					solver.post(IntConstraintFactory.among(prev, new IntVar[]{vars[0]}, new int[]{vars[1].getValue()}));
					log.info("IntVar: " + prev);
					log.info("IntConstraint: EQ with int vars:" + printVarArray(vars) + " being equal to int var: " + prev);
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
						IntVar newVar = createIntVar();
						log.info("IntVar: " + newVar);
						solver.post(IntConstraintFactory.absolute(newVar, var2));
						log.info("IntConstraint: ABS with var: " + newVar);
						return newVar;
					}
				}
				else if (ue instanceof ComposedUnaryExpression){
					ComposedUnaryExpression cue = (ComposedUnaryExpression)ue;
					int val = cue.getValue();
					ComposedUnaryOperatorEnum op = cue.getOperator();
					if (op.equals(ComposedUnaryOperatorEnum.EXPONENTIAL_VALUE)){
						IntVar prevVar = createIntVar();
						if (val == 1){
							log.info("IntConstraint: EXP with x: " + var2 + " and y: " + val);
							return var2;
						}
						else if (val == 2){
							solver.post(IntConstraintFactory.times(var2,var2,prevVar));
							log.info("IntVar: " + prevVar);
							log.info("IntConstraint: EXP with x: " + prevVar + " and y: " + val);
							return prevVar;
						}
						else{
							solver.post(IntConstraintFactory.times(var2,var2,prevVar));
							for (int i = 3; i <= val; i++){
								IntVar varN = createIntVar();
								solver.post(IntConstraintFactory.times(prevVar,var2,varN));
								prevVar = varN;
								if (i == val){
									log.info("IntVar: " + varN);
									log.info("IntConstraint: EXP with x: " + varN + " and y: " + val);
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

	//TODO - bounds should be prepared for all operations
	private Pair<Integer, Integer> getBounds(IntVar[] vars, OperatorEnum operatorEnum){
		switch (operatorEnum) {
			case MINUS:
				int minMinus = vars[0].getLB();
				int maxMinus = vars[0].getUB();

				for (int i = 1; i < vars.length; i++) {
					minMinus = minMinus - vars[i].getUB();
					maxMinus = maxMinus - vars[i].getLB();
				}
				return Pair.of(minMinus, maxMinus);
			case TIMES:
				int minTimes = vars[0].getLB() * vars[1].getLB();
				int maxTimes = vars[0].getUB() * vars[1].getUB();
				return Pair.of(minTimes, maxTimes);
			default:
				return Pair.of(LOW_INT_LIMIT, UPPER_INT_LIMIT);
		}
	}

	/* Printing the array of variables */
	private String printVarArray(solver.variables.Variable[] vars){
		return Arrays.stream(vars)
				.map(variable -> variable.getName())
				.collect(Collectors.joining(" , ", "[", "]"));
	}
	
	/* Creating a real variable out of an expression */
	private RealVar parseRealExpression(Expression expr, RealConstraint rc){
		if (expr instanceof Variable || expr instanceof MetricVariable || expr instanceof Constant){
			if (expr instanceof Variable){
				RealVar v = null;
				v = idToRealVar.get(expr.getId());
				if (v == null){
					IntVar iv = idToIntVar.get(expr.getId());
					if (iv != null){
						v = VariableFactory.real(iv,epsilon);
						log.info("RealVar: " + v + " on top of IntVar: " + iv.getName());
					}
				}
				return v;
			}
			else if (expr instanceof MetricVariable){
				RealVar v = null;
				v = idToRealVar.get(expr.getId());
				if (v == null){
					IntVar iv = idToIntVar.get(expr.getId());
					if (iv != null){
						v = VariableFactory.real(iv,epsilon);
						log.info("RealVar: " + v + " on top of IntVar: " + iv.getName());
					} else{
						RealVar var = VariableFactory.real("RealVar" + (realVarNum++), LOW_REAL_LIMIT, UPPER_REAL_LIMIT, epsilon, solver);
						log.info("RealVar: " + var);
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
					log.info("Constant: " + v);
					return v;
				}
				else if (type.equals(BasicTypeEnum.LONG)){
					LongValueUpperware longVal = (LongValueUpperware)constant.getValue();
					long val = longVal.getValue();
					RealVar v = VariableFactory.real("Constant" + (constNum++), val, val, epsilon, solver);
					log.info("Constant: " + v);
					return v;
				}
				else if (type.equals(BasicTypeEnum.FLOAT)){
					FloatValueUpperware floatVal = (FloatValueUpperware)constant.getValue();
					float val = floatVal.getValue();
					RealVar v = VariableFactory.real("Constant" + (constNum++), val, val, epsilon, solver);
					log.info("Constant: " + v);
					return v;
				}
				else if (type.equals(BasicTypeEnum.DOUBLE)){
					DoubleValueUpperware doubleVal = (DoubleValueUpperware)constant.getValue();
					double val = doubleVal.getValue();
					RealVar v = VariableFactory.real("Constant" + (constNum++), val, val, epsilon, solver);
					log.info("Constant: " + v);
					return v;
				}
			}
		}
		else{
			RealVar var = VariableFactory.real("RealVar" + (realVarNum++), LOW_REAL_LIMIT, UPPER_REAL_LIMIT, epsilon, solver);
			log.info("RealVar: " + var);
			if (expr instanceof ComposedExpression){
				ComposedExpression cep = (ComposedExpression)expr;
				EList<NumericExpression> exprs = cep.getExpressions();

				int size = exprs.size();

				RealVar[] vars = exprs.stream()
						.map(ne -> parseRealExpression(ne, rc))
						.toArray(value -> new RealVar[size]);

				OperatorEnum op = cep.getOperator();
				if (op.equals(OperatorEnum.PLUS)){
					String func = getFunctionPattern("+", size);
					RealVar[] finalVars = ArrayUtils.append(vars,new RealVar[]{var});
					rc.addFunction(func, finalVars);
					log.info("RealConstraint: " + func + " with real vars:" + printVarArray(finalVars));
				}
				else if (op.equals(OperatorEnum.MINUS)){
					String func = getFunctionPattern("-", size);
					RealVar[] finalVars = ArrayUtils.append(vars,new RealVar[]{var});
					rc.addFunction(func, finalVars);
					log.info("RealConstraint: " + func + " with real vars:" + printVarArray(finalVars));
				}
				else if (op.equals(OperatorEnum.TIMES)){
					String func = getFunctionPattern("*", size);
					RealVar[] finalVars = ArrayUtils.append(vars,new RealVar[]{var});
					rc.addFunction(func, finalVars);
					log.info("RealConstraint: " + func + " with real vars:" + printVarArray(finalVars));
				}
				else if (op.equals(OperatorEnum.DIV)){
					String func = getFunctionPattern("/", size);
					RealVar[] finalVars = ArrayUtils.append(vars,new RealVar[]{var});
					rc.addFunction(func, finalVars);
					log.info("RealConstraint: " + func + " with real vars:" + printVarArray(finalVars));
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
						log.info("RealConstraint: " + func + " with real vars:" + printVarArray(vars));
					}
					else if (op.equals(SimpleUnaryOperatorEnum.LN_VALUE)){
						function.append(" ln({0}) = {1} )");
						RealVar[] vars = new RealVar[]{var2,var};
						String func = function.toString();
						rc.addFunction(func, vars);
						log.info("RealConstraint: " + func + " with real vars:" + printVarArray(vars));
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
						log.info("RealConstraint: " + func + " with real vars:" + printVarArray(vars));
					}
					else{
						//! Is this correct?
						function = new StringBuilder("( log( {0}, " + val + " ) = {1} )");
						RealVar[] vars = new RealVar[]{var2,var};
						String func = function.toString();
						rc.addFunction(func, vars);
						log.info("RealConstraint: " + func + " with real vars:" + printVarArray(vars));
					}
					return var;
				}
			}
		}
		return null;
	}

	private String getFunctionPattern(String operator, int size) {
		StringBuilder function = new StringBuilder("( {0} ");
		for (int j = 1; j < size; j++)
            function.append(operator).append(" {").append(j).append("} ");
		function.append(") = {").append(size).append("}");
		return function.toString();
	}

	/* Creating the constraints of the problem to solve */
	private void createConstraints(EList<ComparisonExpression> constraints){
		RealConstraint rc = null;
		log.info("--------------- Constraints ---------------");
		for (ComparisonExpression constr: constraints){
			Expression expr1 = constr.getExp1();
			Expression expr2 = constr.getExp2();
			ComparatorEnum operator = constr.getComparator();
			boolean isInt = involvesOnlyInt(expr1);
			if (isInt){
				isInt = involvesOnlyInt(expr2);
				log.info("Constraint only involves integer variables");
			} else{
				log.info("Constraint involves real variables");
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
					log.info("IntConstraint: " + v.getId() + " " + opStr + " " + var2.getId());
				}
				else if (expr1 instanceof MetricVariable){
					MetricVariable var = (MetricVariable)expr1;
					String id = var.getId();
					IntVar v = idToIntVar.get(id);
					IntVar var2 = parseExpression(expr2);
					String opStr = getComparator(operator,false);
					constraint = IntConstraintFactory.arithm(v, opStr, var2);
					log.info("IntConstraint: " + v.getName() + " " + opStr + " " + var2.getName());
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
						log.info("IntConstraint: " + v.getName() + " " + opStr + " " + value);
					}
					else if (type.equals(BasicTypeEnum.LONG)){
						LongValueUpperware longVal = (LongValueUpperware)constant.getValue();
						long value = longVal.getValue();
						IntVar v = parseExpression(expr2);
						//System.out.println("Parsed expression is: " + parseExpression(expr2));
						String opStr = getComparator(operator,true);
						constraint = IntConstraintFactory.arithm(v, opStr, (int)value);
						log.info("IntConstraint: " + v.getName() + " " + opStr + " " + value);
					}
				}
				if (expr1 instanceof ComposedExpression || expr1 instanceof UnaryExpression){
					if (expr1 instanceof ComposedExpression){
						//System.out.println("First expression is: " + parseExpression(expr1) + " and second expression is: " + parseExpression(expr2));
						IntVar v1 = parseExpression(expr1);
						IntVar v2 = parseExpression(expr2);
						String opStr = getComparator(operator,false);
						constraint = IntConstraintFactory.arithm(v1, opStr, v2);
						log.info("IntConstraint: " + v1.getName() + " " + opStr + " " + v2.getName());
					}
				}
			}
			else{
				if (rc == null) rc = new RealConstraint(solver);
				StringBuilder function = new StringBuilder("(");
				if (expr1 instanceof Variable){
					Variable var = (Variable)expr1;
					String id = var.getId();
					log.info("Checking variable with name: " + id);
					RealVar v = idToRealVar.get(id);
					log.info("RealVar is: " + v);
					if (v == null){
						IntVar iv = idToIntVar.get(id);
						//Checking if int var is involved
						if (iv != null){
							v = VariableFactory.real(iv,epsilon);
							log.info("RealVar: " + v + " on top of IntVar: " + iv.getName());
						}
						//If not, then we have a problem
						else{
							log.error("Got a new variable not previously parsed");
						}
					}
					function.append(" {0} " + getComparator(operator,false) + " {1} )");
					log.info("Operator is: " + operator + " second expression is: " + expr2);
					RealVar[] all_vars = new RealVar[]{v, parseRealExpression(expr2,rc)};
					String func = function.toString();
					log.info("RealConstraint: " + func + " with RealVars:" + printVarArray(all_vars));
			        rc.addFunction(func, all_vars);
				}
				else if (expr1 instanceof MetricVariable){
					log.debug("CASE 2");
					MetricVariable var = (MetricVariable)expr1;
					String id = var.getId();
					RealVar v = idToRealVar.get(id);
					function.append(" {0} " + getComparator(operator,false) + " {1} )");
					RealVar[] all_vars = new RealVar[]{v, parseRealExpression(expr2,rc)};
					String func = function.toString();
					log.info("RealConstraint: " + func + " with RealVars:" + printVarArray(all_vars));
			        rc.addFunction(func, all_vars);
				}
				else if (expr1 instanceof Constant){
					log.debug("CASE 3");
					Constant constant = (Constant)expr1;
					BasicTypeEnum type = constant.getType();
					if (type.equals(BasicTypeEnum.INTEGER)){
						IntegerValueUpperware intVal = (IntegerValueUpperware)constant.getValue();
						function.append(" " + intVal.getValue() + getComparator(operator,false) + " {0} )");
						RealVar[] all_vars = new RealVar[]{parseRealExpression(expr2,rc)};
						String func = function.toString();
						log.info("RealConstraint: " + func + " with RealVars:" + printVarArray(all_vars));
				        rc.addFunction(func, all_vars);
					}
					else if (type.equals(BasicTypeEnum.LONG)){
						LongValueUpperware longVal = (LongValueUpperware)constant.getValue();
						function.append(" " + longVal.getValue() + getComparator(operator,false) + " {0} )");
						RealVar[] all_vars = new RealVar[]{parseRealExpression(expr2,rc)};
						String func = function.toString();
						log.info("RealConstraint: " + func + " with RealVars:" + printVarArray(all_vars));
				        rc.addFunction(func, all_vars);
					}
					else if (type.equals(BasicTypeEnum.FLOAT)){
						FloatValueUpperware floatVal = (FloatValueUpperware)constant.getValue();
						function.append(" " + floatVal.getValue() + getComparator(operator,false) + " {0} )");
						RealVar[] all_vars = new RealVar[]{parseRealExpression(expr2,rc)};
						String func = function.toString();
						log.info("RealConstraint: " + func + " with RealVars:" + printVarArray(all_vars));
				        rc.addFunction(func, all_vars);
					}
					else if (type.equals(BasicTypeEnum.DOUBLE)){
						DoubleValueUpperware doubleVal = (DoubleValueUpperware)constant.getValue();
						function.append(" " + doubleVal.getValue() + getComparator(operator,false) + " {0} )");
						RealVar[] all_vars = new RealVar[]{parseRealExpression(expr2,rc)};
						String func = function.toString();
						log.info("RealConstraint: " + func + " with RealVars:" + printVarArray(all_vars));
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
					log.info("RealConstraint: " + func + " with RealVars:" + printVarArray(all_vars));
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
		log.info("------------------------------------------");
	}
	
	/* Creating the metric variables */
	private void createMetricVariables(EList<MetricVariable> vars){
		log.info("--------------- MetricVariables ---------------");
		for (MetricVariable var: vars){
			BasicTypeEnum type = var.getType();
			if (type.equals(BasicTypeEnum.INTEGER) || type.equals(BasicTypeEnum.LONG)){
				String id = var.getId();
				IntVar v = createIntVar(id);
				log.info("IntVar: " + v);
				idToIntVar.put(id,v);
			}
			else if (type.equals(BasicTypeEnum.DOUBLE) || type.equals(BasicTypeEnum.FLOAT)){
				String id = var.getId();
				RealVar v = VariableFactory.real(id, LOW_REAL_LIMIT, UPPER_REAL_LIMIT, epsilon, solver);
				log.info("RealVar: " + v);
				idToRealVar.put(id,v);
			}
		}
		log.info("------------------------------------------");
	}
	
	/* Creating the normal variables */
	private void createVariables(EList<Variable> vars){
		log.info("--------------- Variables ---------------");
		for (Variable var: vars){
			Domain dom = var.getDomain();
			if (dom instanceof RangeDomain){
				RangeDomain rd = (RangeDomain)dom;
				BasicTypeEnum type = rd.getType();

				NumericValueUpperware from = rd.getFrom();
				NumericValueUpperware to = rd.getTo();

				if (type.equals(BasicTypeEnum.INTEGER)){
					IntegerValueUpperware int1 = (IntegerValueUpperware)from;
					IntegerValueUpperware int2 = (IntegerValueUpperware)to;
					String id = var.getId();
					IntVar v = createIntVar(id, int1.getValue(), int2.getValue());
					log.info("IntVar: " + v);
					idToIntVar.put(id,v);
				}
				else if(type.equals(BasicTypeEnum.LONG)){
					LongValueUpperware long1 = (LongValueUpperware)from;
					LongValueUpperware long2 = (LongValueUpperware)to;
					String id = var.getId();
					IntVar v = createIntVar(id, (int)long1.getValue(), (int)long2.getValue());
					log.info("IntVar: " + v);
					idToIntVar.put(id,v);
				}
				else if (type.equals(BasicTypeEnum.DOUBLE)){
					DoubleValueUpperware real1 = (DoubleValueUpperware)from;
					DoubleValueUpperware real2 = (DoubleValueUpperware)to;
					String id = var.getId();
					RealVar v = VariableFactory.real(id, real1.getValue(), real2.getValue(), epsilon, solver);
					log.info("RealVar: " + v);
					idToRealVar.put(id,v);
				}
				else if (type.equals(BasicTypeEnum.FLOAT)){
					FloatValueUpperware float1 = (FloatValueUpperware)from;
					FloatValueUpperware float2 = (FloatValueUpperware)to;
					String id = var.getId();
					RealVar v = VariableFactory.real(id, float1.getValue(), float2.getValue(), epsilon, solver);
					log.info("RealVar: " + v);
					idToRealVar.put(id,v);
				}
			} else if (dom instanceof NumericListDomain) {
				NumericListDomain nld = (NumericListDomain) dom;
				BasicTypeEnum type = nld.getType();

				if (type.equals(BasicTypeEnum.INTEGER)){
					createEnumeratedDomain(var, nld, IntegerValueUpperware.class, IntegerValueUpperware::getValue);
				} else if(type.equals(BasicTypeEnum.LONG)){
					createEnumeratedDomain(var, nld, LongValueUpperware.class, value -> (int)value.getValue());
				}
				else if (type.equals(BasicTypeEnum.DOUBLE)){

					double min = nld.getValues().stream().mapToDouble(value -> ((DoubleValueUpperware) value).getValue()).min().orElse(0d);
					double max = nld.getValues().stream().mapToDouble(value -> ((DoubleValueUpperware) value).getValue()).max().orElse(Double.MAX_VALUE);

					String id = var.getId();
					RealVar v = VariableFactory.real(id, min, max, epsilon, solver);
					log.info("RealVar: " + v);
					idToRealVar.put(id,v);
				}
				else if (type.equals(BasicTypeEnum.FLOAT)){

					double min = nld.getValues().stream().mapToDouble(value -> ((FloatValueUpperware) value).getValue()).min().orElse(0d);
					double max = nld.getValues().stream().mapToDouble(value -> ((FloatValueUpperware) value).getValue()).max().orElse(Double.MAX_VALUE);

					String id = var.getId();
					RealVar v = VariableFactory.real(id, min, max, epsilon, solver);
					log.info("RealVar: " + v);
					idToRealVar.put(id,v);
				}

			} else if (dom instanceof NumericDomain){
				//System.out.println("Got numeric domain for variable:" + var.getId());
				NumericDomain nd = (NumericDomain)dom;
				BasicTypeEnum type = nd.getType();
				if (type.equals(BasicTypeEnum.INTEGER) || type.equals(BasicTypeEnum.LONG)){
					String id = var.getId();
					IntVar v = createIntVar(id);
					log.info("IntVar: " + v);
					idToIntVar.put(id,v);
				}
				else if (type.equals(BasicTypeEnum.DOUBLE) || type.equals(BasicTypeEnum.FLOAT)){
					String id = var.getId();
					RealVar v = VariableFactory.real(id, LOW_REAL_LIMIT, UPPER_REAL_LIMIT, epsilon, solver);
					log.info("RealVar: " + v);
					idToRealVar.put(id,v);
				}
			}
		}
		log.info("------------------------------------------");
	}

	private <T> void createEnumeratedDomain(Variable var, NumericListDomain nld, Class<T> type, ToIntFunction<? super T> mapper) {
		int[] ints = nld.getValues()
                .stream()
                .filter(type::isInstance)
				.map(type::cast)
                .mapToInt(mapper)
                .toArray();

		String id = var.getId();
		IntVar v = createIntVar(id, ints);
		log.info("IntVar: " + v);
		idToIntVar.put(id,v);
	}

	/* Creating the constants of the cp problem */
	private void createConstants(EList<Constant> constants){
		log.info("--------------- Constants ---------------");
		for (Constant constant: constants){
			NumericValueUpperware value = constant.getValue();
			//System.out.println("Type of constant is: " + constant.getType());
			if (constant.getType().equals(BasicTypeEnum.DOUBLE)){
				if (value instanceof DoubleValueUpperware){
					double val = ((DoubleValueUpperware)value).getValue();
					RealVar var = VariableFactory.real(constant.getId(), val, val, epsilon, solver);
					log.info("Constant " + constant.getId() + ": " + var);
					idToRealVar.put(constant.getId(),var);
				} 
			}
			else if (constant.getType().equals(BasicTypeEnum.INTEGER)){
				if (value instanceof IntegerValueUpperware){
					int val = ((IntegerValueUpperware)value).getValue();
					IntVar var = VariableFactory.fixed(val, solver);
					log.info("Constant: " + constant.getId() + ": " + var);
					idToIntVar.put(constant.getId(),var);
				}
			}
			else if (constant.getType().equals(BasicTypeEnum.LONG)){
				if (value instanceof LongValueUpperware){
					long val = ((LongValueUpperware)value).getValue();
					IntVar var = VariableFactory.fixed((int)val, solver);
					log.info("Constant " + constant.getId() + ": " + var);
					idToIntVar.put(constant.getId(),var);
				}
			}
			else if (constant.getType().equals(BasicTypeEnum.FLOAT)){
				if (value instanceof FloatValueUpperware){
					double val = ((FloatValueUpperware)value).getValue();
					RealVar var = VariableFactory.real(constant.getId(), val, val, epsilon, solver);
					log.info("Constant " + constant.getId() + ": " + var);
					idToRealVar.put(constant.getId(),var);
				}
			}
		}
		log.info("------------------------------------------");
	}

	/* Re-initializing the main configuration variables of the solver*/
	private void dispose(){
		intVarNum = 0;
		realVarNum = 0;
		constNum = 0;
	}

	private double calculateUtility(){

		double utility = utilityGenerator.evaluate(convertToUtilityIntVariable(solver.retrieveIntVars())); //TODO
		log.info("Utility = {}", utility);
		if (utility > maxUtility){
			maxUtility = utility;
			log.info("Find max utility: {}", maxUtility);
			saveSolution();
		}
		return utility;
	}

	private eu.melodic.upperware.utilitygenerator.model.IntVar[] convertToUtilityIntVariable(IntVar[] intVars) {
		return Arrays.stream(intVars)
				.map(intVar -> new eu.melodic.upperware.utilitygenerator.model.IntVar(intVar.getName(), intVar.getValue()))
				.toArray(eu.melodic.upperware.utilitygenerator.model.IntVar[]::new);
	}

	private eu.melodic.upperware.utilitygenerator.model.RealVar[] convertToUtilityRealVariable(RealVar[] realVars) {
		return Arrays.stream(realVars)
				.map(realVar -> new eu.melodic.upperware.utilitygenerator.model.RealVar(realVar.getName(), realVar.getUB()))
				.toArray(eu.melodic.upperware.utilitygenerator.model.RealVar[]::new);
	}

	private void createUtilityFunctionType(ConstraintProblem cp){
		utilityFunctionType = cp.getConstants().stream()
				.map(CPElement::getId)
				.filter(s -> s.startsWith(utilityFunctionTypePrefix))
				.map(String::toUpperCase)
				.map(s -> s.replace(utilityFunctionTypePrefix, ""))
				.map(UtilityFunctionType::valueOf)
				.findFirst().orElse(null);
	}

	private BoolVar createBoolVar(){
		return VariableFactory.bool(getBoolVarName(), solver);
	}

	private IntVar createIntVar() {
		return createIntVar(getIntVarName());
	}

	private IntVar createIntVar(String name) {
		return createIntVar(name, LOW_INT_LIMIT, UPPER_INT_LIMIT);
	}

	private IntVar createIntVar(Pair<Integer, Integer> range) {
		return createIntVar(getIntVarName(), range.getLeft(), range.getRight());
	}

	private IntVar createIntVar(String name, int min, int max) {
		return VariableFactory.bounded(name, min, max, solver);
	}

	private IntVar createIntVar(String name, int[] values) {
		return VariableFactory.enumerated(name, values, solver);
	}

	private String getIntVarName(){
		return "IntVar" + (intVarNum++);
	}

	private String getBoolVarName(){
		return "BoolVar" + (intVarNum++);
	}

}

