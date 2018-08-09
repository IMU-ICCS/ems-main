package eu.melodic.upperware.cpsolver.lib;

/* Copyright (C) 2015 KYRIAKOS KRITIKOS <kritikos@ics.forth.gr> */

/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication;
import eu.melodic.upperware.utilitygenerator.model.DTO.MetricDTO;
import eu.melodic.upperware.utilitygenerator.model.DTO.MetricDTOFactory;
import eu.melodic.upperware.utilitygenerator.model.DTO.VariableDTO;
import eu.melodic.upperware.utilitygenerator.model.function.Element;
import eu.melodic.upperware.utilitygenerator.model.function.ElementFactory;
import eu.melodic.upperware.utilitygenerator.model.function.RealElement;
import eu.melodic.upperware.utilitygenerator.properties.UtilityGeneratorProperties;
import eu.paasage.mddb.cdo.client.exp.CDOClientX;
import eu.paasage.mddb.cdo.client.exp.CDOClientXImpl;
import eu.paasage.mddb.cdo.client.exp.CDOSessionX;
import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.metamodel.types.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
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
import java.util.stream.Stream;

@Slf4j
public class CPSolver {

    private Solver solver = null;
    private String pathName = null;
    private Hashtable<String, IntVar> idToIntVar = new Hashtable<>();
    private Hashtable<String, RealVar> idToRealVar = new Hashtable<>();
    private static final double epsilon = 0.000001d;
    private static final int LOW_INT_LIMIT = -10000;
    private static final int UPPER_INT_LIMIT = 100000000;
    private static final double LOW_REAL_LIMIT = -1000000000.0;
    private static final double UPPER_REAL_LIMIT = 1000000000.0;
    private static int INITIAL_DEPLOYMENT_ID = -1;
    private int intVarNum = 0;
    private int realVarNum = 0;
    private int constNum = 0;
    private boolean cdoMode = false;
    private long timestamp = 0;
    private UtilityGeneratorApplication utilityGenerator;
    private double maxUtility;
    private double utilityOfDeployedSolution = 0;
    private List<VariableDTO> variablesForUG = new ArrayList<>();
    private Collection<Element> deployedSolution;
    private Collection<MetricDTO> metricsForUG;
    private boolean isReconfig = false;
    private Map<String, Integer> solutionWithMaximumUtilityInt = new HashMap<>();
    private Map<String, Double> solutionWithMaximumUtilityReal = new HashMap<>();


    /* Constructor which also reads the CP Model either from CDO via
     * a CDO path given as String or from file system via a String path
     */
    public CPSolver(String applicationId, String pathName, String camelModelFilePath, Boolean readFromFile, NodeCandidates nodeCandidates, UtilityGeneratorProperties utilityGeneratorProperties) {
        this();
        this.pathName = pathName;

        readCPModel(pathName, readFromFile);

        if (!readFromFile) {
            camelModelFilePath = applicationId;
        }
        if (isReconfig) {
            this.utilityGenerator = new UtilityGeneratorApplication(camelModelFilePath, readFromFile, variablesForUG, metricsForUG, deployedSolution, nodeCandidates);
        } else {
            this.utilityGenerator = new UtilityGeneratorApplication(camelModelFilePath, readFromFile, variablesForUG, metricsForUG, nodeCandidates);
        }
    }

    /* Constructor which also reads the CP Model either from CDO via
     * a CDO path given as String or from file system via a String path
     */
    public CPSolver(String pathName, boolean readFromFile, long timestamp) {
        this();
        this.pathName = pathName;
        this.timestamp = timestamp;
        readCPModel(pathName, readFromFile);
    }

    /* Default Constructor - need to read CP Model by calling the respective method
     * with the CDOID of this model as otherwise no solution will be produced by calling
     * solve method
     */
    public CPSolver() {
        solver = new Solver();
    }

    /* Processing the model fetched to inform the private variables of this class */
    private void readModel(ConstraintProblem cp) {
        createConstants(cp.getConstants());
        createVariables(cp.getCpVariables());
        createMetrics(cp.getCpMetrics());
        createConstraints(cp.getConstraints());
        createVariablesForUG(cp.getCpVariables());
        createMetricsForUG(cp.getCpMetrics());
        getActualConfiguration(cp);
    }

    private void getActualConfiguration(ConstraintProblem cp) {
        int deployedSolutionId = cp.getDeployedSolutionId();
        if (deployedSolutionId != INITIAL_DEPLOYMENT_ID) {
            isReconfig = true;
            deployedSolution = cp.getSolution().get(deployedSolutionId)
                    .getVariableValue().stream()
                    .map(ElementFactory::createElement)
                    .collect(Collectors.toList());
        } else {
            isReconfig = false;
        }
    }

    private void createMetricsForUG(EList<CpMetric> metrics) {
        log.info("Creating metrics for Utility Generator");
        this.metricsForUG = metrics.stream().map(MetricDTOFactory::createMetricDTO).collect(Collectors.toList());
        log.info("Creating metrics for Utility Generator is finished, number of metrics: {}.", metricsForUG);
    }

    private void createVariablesForUG(EList<CpVariable> variables) {
        log.info("Creating variables for Utility Generator");
        this.variablesForUG = variables.stream()
                .map(variable -> new VariableDTO(variable.getId(), variable.getComponentId(), variable.getVariableType()))
                .collect(Collectors.toList());
        log.info("Creating variables for Utility Generator is finished, number of variables: {}", variablesForUG.size());
    }

    /* Reads the CPModel from CDO provided that a correct CDO path or a file path
     * name for this model is provided as input
     */
    public void readCPModel(String pathName, boolean readFromFile) {
        ConstraintProblem cp = null;
        log.info("Reading CP model...");

        CDOClientX clientX = new CDOClientXImpl(Arrays.asList(TypesPackage.eINSTANCE, CpPackage.eINSTANCE));

        if (!readFromFile) {
            CDOSessionX sessionX = clientX.getSession();
            log.info("Loading resource from CDO: {}",pathName);
            cdoMode = true;
            CDOTransaction trans = sessionX.openTransaction();
            cp = getConstraintProblemFromCDO(pathName, trans).orElseThrow(() -> new IllegalStateException("Constraint Problem does not exist in CDO"));
            readModel(cp);
            trans.close();
            sessionX.closeSession();
        } else if (pathName != null) {
            log.info("Loading resource from file: {}",pathName);
            cdoMode = false;
            cp = (ConstraintProblem) clientX.loadModel(pathName);
            readModel(cp);
        }
        log.info("CDO Mode: {}",cdoMode);
    }

    /* Solves the CPModel previously read, updates the model if a solution was found
     * and returns a boolean value indicating whether a solution has been found or not
     */
    public boolean solve() throws Exception {
        boolean hasSolutions = false;
        if (idToIntVar != null) {
            Collection<IntVar> values = idToIntVar.values();
            IntVar[] vars = values.stream().toArray(value -> new IntVar[values.size()]);
            solver.set(IntStrategyFactory.random(vars, System.currentTimeMillis()));
        }

        log.info("Using Utility Generator for solution space:");

        if (solver.findSolution()) {
            int i = 1;
            maxUtility = 0.0;
            calculateUtility();
            while (solver.nextSolution()) {
                i++;
                calculateUtility();
            }
            log.info("Maximum utility after evaluating {} solutions is {}", i, maxUtility);
            hasSolutions = (solver.isFeasible() == ESat.TRUE) && maxUtility > 0;
            if (hasSolutions) {
                saveBestSolutionInCDO();
            }
        }
        return hasSolutions;
    }

    private void convertAndUpdateBestSolution(double utility) {

        maxUtility = utility;

        solutionWithMaximumUtilityInt = idToIntVar.values().stream()
                .filter(intVar -> variablesForUG.stream().anyMatch(v -> intVar.getName().equals(v.getId())))
                .collect(Collectors.toMap(IntVar::getName, IntVar::getValue));

        solutionWithMaximumUtilityReal = idToRealVar.values().stream()
                .filter(realVar -> variablesForUG.stream().anyMatch(v -> realVar.getName().equals(v.getId())))
                .collect(Collectors.toMap(RealVar::getName, RealVar::getUB));
    }

    /* Saving the solution in the cp model and storing back the model to its
     * initial position, either in CDO repository or the file system
     */

    private Optional<ConstraintProblem> getConstraintProblemFromCDO(String pathName, CDOTransaction trans){

        CDOResource resource = trans.getResource(pathName);
        EList<EObject> contents = resource.getContents();
        for (EObject obj : contents) {
            if (obj instanceof ConstraintProblem) {
                return Optional.of((ConstraintProblem)obj);
            }
        }
        return Optional.empty();

    }

    private void saveBestSolutionInCDO() {

        log.info("Saving best solution in CDO.....");
        CDOTransaction trans = null;
        ConstraintProblem cp = null;

        CDOClientX clientX = new CDOClientXImpl(Arrays.asList(TypesPackage.eINSTANCE, CpPackage.eINSTANCE));
        CDOSessionX sessionX = null;
        if (cdoMode) {
            sessionX = clientX.getSession();
            trans = sessionX.openTransaction();
            cp = getConstraintProblemFromCDO(pathName, trans).orElseThrow(() -> new IllegalStateException("Constraint Problem does not exist in CDO"));
        } else {
            cp = (ConstraintProblem) clientX.loadModel(pathName);
        }
        if (isReconfig) {
            updateUtilityOfDeployedSolution(cp);
        }

        Solution solution = CpFactory.eINSTANCE.createSolution();
        solution.setTimestamp(new Date().getTime());
        cp.getSolution().add(solution);

        DoubleValueUpperware utilityValue = TypesFactory.eINSTANCE.createDoubleValueUpperware();
        utilityValue.setValue(maxUtility);
        solution.setUtilityValue(utilityValue);
        EList<CpVariableValue> varValues = solution.getVariableValue();
        try {
            EList<CpVariable> vars = cp.getCpVariables();
            for (CpVariable var : vars) {
                log.debug("Considering variable: {}", var.getId());
                CpVariableValue varVal = CpFactory.eINSTANCE.createCpVariableValue();
                varVal.setVariable(var);
                Domain dom = var.getDomain();
                if (dom instanceof RangeDomain) {
                    RangeDomain rd = (RangeDomain) dom;
                    NumericValueUpperware from = rd.getFrom();
                    if (from instanceof IntegerValueUpperware) {
                        createIntegerVariableValue(var, varVal);
                    } else if (from instanceof LongValueUpperware){
                        createLongVariableValue(var, varVal);
                    }
                    else if (from instanceof DoubleValueUpperware){
                        createDoubleVariableValue(var, varVal);
                    }
                    else {
                        createFloatVariableValue(var, varVal);
                    }
                } else if (dom instanceof NumericDomain) {
                    NumericDomain nd = (NumericDomain) dom;
                    BasicTypeEnum type = nd.getType();
                    if (type.equals(BasicTypeEnum.INTEGER)) {
                        createIntegerVariableValue(var, varVal);
                    } else if (type.equals(BasicTypeEnum.LONG)){
                        createLongVariableValue(var, varVal);
                    } else if (type.equals(BasicTypeEnum.DOUBLE)){
                        createDoubleVariableValue(var, varVal);
                    } else {
                        createFloatVariableValue(var, varVal);
                    }
                }
                varValues.add(varVal);
            }
            if (cdoMode) {
                trans.commit();
                trans.close();
                sessionX.closeSession();
            } else {
                clientX.saveModel(cp, pathName);
            }
            log.info("..... Solution saved");
        } catch (Exception e) {
            log.error("Something went wrong while storing the solution", e);
        }
    }

    private void createFloatVariableValue(CpVariable var, CpVariableValue varVal) {
        double val = solutionWithMaximumUtilityReal.get(var.getId());
        log.info("Discovered value for variable :{} is: {}", var.getId(), val);
        FloatValueUpperware value = TypesFactory.eINSTANCE.createFloatValueUpperware();
        value.setValue((float) val);
        varVal.setValue(value);
    }

    private void createDoubleVariableValue(CpVariable var, CpVariableValue varVal) {
        double val = solutionWithMaximumUtilityReal.get(var.getId());
        log.info("Discovered value for variable : {} is: {}", var.getId(), val);
        DoubleValueUpperware value = TypesFactory.eINSTANCE.createDoubleValueUpperware();
        value.setValue(val);
        varVal.setValue(value);
    }

    private void createLongVariableValue(CpVariable var, CpVariableValue varVal) {
        int val = solutionWithMaximumUtilityInt.get(var.getId());
        log.info("Discovered value for variable : {} is: {} ", var.getId(), val);
        LongValueUpperware value = TypesFactory.eINSTANCE.createLongValueUpperware();
        value.setValue(val);
        varVal.setValue(value);
    }

    private void createIntegerVariableValue(CpVariable var, CpVariableValue varVal) {
        int val = solutionWithMaximumUtilityInt.get(var.getId());
        log.info("Discovered value for variable : {} is: {}", var.getId(), val);
        IntegerValueUpperware value = TypesFactory.eINSTANCE.createIntegerValueUpperware();
        value.setValue(val);
        varVal.setValue(value);
    }


    private void updateUtilityOfDeployedSolution(ConstraintProblem cp) {
        log.debug("Updating utility of deployed solution = {}", utilityOfDeployedSolution);
        Solution deployedSolution = cp.getSolution().get(cp.getDeployedSolutionId());
        log.debug("Previous utility of deployed solution was {}", ((DoubleValueUpperware) deployedSolution.getUtilityValue()).getValue());
        DoubleValueUpperware utilityValue = TypesFactory.eINSTANCE.createDoubleValueUpperware();
        utilityValue.setValue(utilityOfDeployedSolution);
        deployedSolution.setUtilityValue(utilityValue);

    }

    /* Checking whether an expression contains only integer variables */
    private boolean involvesOnlyInt(Expression expr) {
        boolean onlyInt = false;
        if (expr instanceof CpVariable) {
            CpVariable v = (CpVariable) expr;
            IntVar iv = idToIntVar.get(v.getId());
            if (iv != null) onlyInt = true;
        } else if (expr instanceof CpMetric) {
            CpMetric v = (CpMetric) expr;
            IntVar iv = idToIntVar.get(v.getId());
            if (iv != null) onlyInt = true;
        } else if (expr instanceof Constant) {
            Constant c = (Constant) expr;
            BasicTypeEnum type = c.getType();
            if (type.equals(BasicTypeEnum.INTEGER) || type.equals(BasicTypeEnum.LONG)) {
                onlyInt = true;
            }
        } else if (expr instanceof ComposedExpression) {
            ComposedExpression cep = (ComposedExpression) expr;
            boolean res = false;
            for (NumericExpression ne : cep.getExpressions()) {
                res = involvesOnlyInt(ne);
                if (!res) break;
            }
            if (res) onlyInt = true;
        } else if (expr instanceof ComparisonExpression) {
            ComparisonExpression cep = (ComparisonExpression) expr;
            Expression expr1 = cep.getExp1();
            Expression expr2 = cep.getExp2();
            boolean res = involvesOnlyInt(expr1);
            if (res) {
                res = involvesOnlyInt(expr2);
                if (res) onlyInt = true;
            }
        } else if (expr instanceof UnaryExpression) {
            UnaryExpression ue = (UnaryExpression) expr;
            onlyInt = involvesOnlyInt(ue.getExpression());
        }
        return onlyInt;
    }

    /* Transforming comparator to a String */
    private String getComparator(ComparatorEnum comp, boolean opposite) {
        if (!opposite) {
            if (comp.equals(ComparatorEnum.DIFFERENT)) return "!=";
            else if (comp.equals(ComparatorEnum.EQUAL_TO)) return "=";
            else if (comp.equals(ComparatorEnum.GREATER_OR_EQUAL_TO)) return ">=";
            else if (comp.equals(ComparatorEnum.GREATER_THAN)) return ">";
            else if (comp.equals(ComparatorEnum.LESS_OR_EQUAL_TO)) return "<=";
            else if (comp.equals(ComparatorEnum.LESS_THAN)) return "<";
        } else {
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
    private IntVar parseExpression(Expression expr) {
        if (expr instanceof CpVariable || expr instanceof CpMetric || expr instanceof Constant) {
            if (expr instanceof CpVariable || expr instanceof CpMetric) {
                return idToIntVar.get(expr.getId());
            } else {
                Constant constant = (Constant) expr;
                BasicTypeEnum type = constant.getType();
                if (type.equals(BasicTypeEnum.INTEGER)) {
                    IntegerValueUpperware intVal = (IntegerValueUpperware) constant.getValue();
                    IntVar v = createIntVar("Constant" + (constNum++), intVal.getValue(), intVal.getValue());
                    log.info("Constant: {}: {}", v.getName(),v);
                    return v;
                } else if (type.equals(BasicTypeEnum.LONG)) {
                    LongValueUpperware longVal = (LongValueUpperware) constant.getValue();
                    IntVar v = createIntVar("Constant" + (constNum++), (int) longVal.getValue(), (int) longVal.getValue());
                    log.info("Constant: {}: {}", v.getName(),v);
                    return v;
                }
            }
        } else {
            if (expr instanceof ComposedExpression) {
                ComposedExpression cep = (ComposedExpression) expr;
                EList<NumericExpression> exprs = cep.getExpressions();

                IntVar[] vars = exprs.stream()
                        .map(this::parseExpression)
                        .toArray(value -> new IntVar[exprs.size()]);

                OperatorEnum op = cep.getOperator();
                if (op.equals(OperatorEnum.PLUS)) {
                    IntVar var = createIntVar(getBounds(vars, OperatorEnum.PLUS));
                    log.info("IntElement: {}",var);
                    solver.post(IntConstraintFactory.sum(vars, var));
                    log.info("IntConstraint: SUM with int vars: {} being equal to int var: {}", printVarArray(vars), var);
                    return var;
                } else if (op.equals(OperatorEnum.MINUS)) {
                    IntVar var = createIntVar(getBounds(vars, OperatorEnum.MINUS));
                    int[] coeff = new int[exprs.size()];
                    Arrays.fill(coeff, -1);
                    coeff[0] = 1;
                    log.info("IntElement: {}",var);

                    solver.post(IntConstraintFactory.scalar(vars, coeff, var));
                    log.info("IntConstraint: NARY_MINUS with int vars:{} being equal to int var: {}", printVarArray(vars), var);
                    return var;
                } else if (op.equals(OperatorEnum.TIMES)) {
                    IntVar prev = createIntVar(getBounds(new IntVar[]{vars[0], vars[1]}, OperatorEnum.TIMES));
                    solver.post(IntConstraintFactory.times(vars[0], vars[1], prev));
                    for (int j = 2; j < exprs.size(); j++) {
                        IntVar v = createIntVar(getBounds(new IntVar[]{vars[j], prev}, OperatorEnum.TIMES));
                        solver.post(IntConstraintFactory.times(vars[j], prev, v));
                        prev = v;
                    }
                    log.info("IntElement: {}", prev);
                    log.info("IntConstraint: TIMES with int vars: {} being equal to int var: {}", printVarArray(vars), prev);
                    return prev;

                } else if (op.equals(OperatorEnum.DIV)) {

                    IntVar prev = createIntVar(getBounds(new IntVar[]{vars[0], vars[1]}, OperatorEnum.DIV));
                    solver.post(IntConstraintFactory.eucl_div(vars[0], vars[1], prev));
                    for (int j = 2; j < exprs.size(); j++) {
                        IntVar v = createIntVar(getBounds(new IntVar[]{vars[j], prev}, OperatorEnum.DIV));
                        solver.post(IntConstraintFactory.eucl_div(vars[j], prev, v));
                        prev = v;
                    }
                    log.info("IntElement: {}", prev);
                    log.info("IntConstraint: DIV with int vars: {} being equal to int var: {}", printVarArray(vars), prev);
                    return prev;
                } else if (op.equals(OperatorEnum.EQ)) {
                    BoolVar prev = createBoolVar();
                    solver.post(IntConstraintFactory.among(prev, new IntVar[]{vars[0]}, new int[]{vars[1].getValue()}));
                    log.info("IntElement: {}", prev);
                    log.info("IntConstraint: EQ with int vars: {} being equal to int var: {}", printVarArray(vars), prev);
                    return prev;
                }
            } else if (expr instanceof UnaryExpression) {
                UnaryExpression ue = (UnaryExpression) expr;
                IntVar var2 = parseExpression(ue.getExpression());
                if (ue instanceof SimpleUnaryExpression) {
                    SimpleUnaryExpression sue = (SimpleUnaryExpression) ue;
                    SimpleUnaryOperatorEnum op = sue.getOperator();
                    if (op.equals(SimpleUnaryOperatorEnum.ABSTRACT_VALUE)) {
                        IntVar newVar = createIntVar();
                        log.info("IntElement: {}", newVar);
                        solver.post(IntConstraintFactory.absolute(newVar, var2));
                        log.info("IntConstraint: ABS with var: {}", newVar);
                        return newVar;
                    }
                } else if (ue instanceof ComposedUnaryExpression) {
                    ComposedUnaryExpression cue = (ComposedUnaryExpression) ue;
                    int val = cue.getValue();
                    ComposedUnaryOperatorEnum op = cue.getOperator();
                    if (op.equals(ComposedUnaryOperatorEnum.EXPONENTIAL_VALUE)) {
                        IntVar prevVar = createIntVar();
                        if (val == 1) {
                            log.info("IntConstraint: EXP with x: {} and y: {}", var2, val);
                            return var2;
                        } else if (val == 2) {
                            solver.post(IntConstraintFactory.times(var2, var2, prevVar));
                            log.info("IntElement: {}", prevVar);
                            log.info("IntConstraint: EXP with x: {} and y: {}", prevVar, val);
                            return prevVar;
                        } else {
                            solver.post(IntConstraintFactory.times(var2, var2, prevVar));
                            for (int i = 3; i <= val; i++) {
                                IntVar varN = createIntVar();
                                solver.post(IntConstraintFactory.times(prevVar, var2, varN));
                                prevVar = varN;
                                if (i == val) {
                                    log.info("IntElement: {}", varN);
                                    log.info("IntConstraint: EXP with x: {} and y: {}", varN, val);
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
    private Pair<Integer, Integer> getBounds(IntVar[] vars, OperatorEnum operatorEnum) {
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
    private String printVarArray(solver.variables.Variable[] vars) {
        return Arrays.stream(vars)
                .map(variable -> variable.getName())
                .collect(Collectors.joining(" , ", "[", "]"));
    }

    /* Creating a real variable out of an expression */
    private RealVar parseRealExpression(Expression expr, RealConstraint rc) {
        if (expr instanceof CpVariable || expr instanceof CpMetric || expr instanceof Constant) {
            if (expr instanceof CpVariable) {
                RealVar v = null;
                v = idToRealVar.get(expr.getId());
                if (v == null) {
                    IntVar iv = idToIntVar.get(expr.getId());
                    if (iv != null) {
                        v = VariableFactory.real(iv, epsilon);
                        log.info("RealElement: " + v + " on top of IntElement: " + iv.getName());
                    }
                }
                return v;
            } else if (expr instanceof CpMetric) {
                RealVar v = null;
                v = idToRealVar.get(expr.getId());
                if (v == null) {
                    IntVar iv = idToIntVar.get(expr.getId());
                    if (iv != null) {
                        v = VariableFactory.real(iv, epsilon);
                        log.info("RealElement: " + v + " on top of IntElement: " + iv.getName());
                    } else {
                        RealVar var = VariableFactory.real("RealElement" + (realVarNum++), LOW_REAL_LIMIT, UPPER_REAL_LIMIT, epsilon, solver);
                        log.info("RealElement: " + var);
                        idToRealVar.put(var.getName(), var);
                        rc.addFunction("{0} >= 0", var);
                        return var;
                    }
                }
                return v;
            } else {
                Constant constant = (Constant) expr;
                BasicTypeEnum type = constant.getType();
                if (type.equals(BasicTypeEnum.INTEGER)) {
                    IntegerValueUpperware intVal = (IntegerValueUpperware) constant.getValue();
                    int val = intVal.getValue();
                    RealVar v = VariableFactory.real("Constant" + (constNum++), val, val, epsilon, solver);
                    log.info("Constant: " + v);
                    return v;
                } else if (type.equals(BasicTypeEnum.LONG)) {
                    LongValueUpperware longVal = (LongValueUpperware) constant.getValue();
                    long val = longVal.getValue();
                    RealVar v = VariableFactory.real("Constant" + (constNum++), val, val, epsilon, solver);
                    log.info("Constant: " + v);
                    return v;
                } else if (type.equals(BasicTypeEnum.FLOAT)) {
                    FloatValueUpperware floatVal = (FloatValueUpperware) constant.getValue();
                    float val = floatVal.getValue();
                    RealVar v = VariableFactory.real("Constant" + (constNum++), val, val, epsilon, solver);
                    log.info("Constant: " + v);
                    return v;
                } else if (type.equals(BasicTypeEnum.DOUBLE)) {
                    DoubleValueUpperware doubleVal = (DoubleValueUpperware) constant.getValue();
                    double val = doubleVal.getValue();
                    RealVar v = VariableFactory.real("Constant" + (constNum++), val, val, epsilon, solver);
                    log.info("Constant: " + v);
                    return v;
                }
            }
        } else {
            RealVar var = VariableFactory.real("RealElement" + (realVarNum++), LOW_REAL_LIMIT, UPPER_REAL_LIMIT, epsilon, solver);
            log.info("RealElement: " + var);
            if (expr instanceof ComposedExpression) {
                ComposedExpression cep = (ComposedExpression) expr;
                EList<NumericExpression> exprs = cep.getExpressions();

                int size = exprs.size();

                RealVar[] vars = exprs.stream()
                        .map(ne -> parseRealExpression(ne, rc))
                        .toArray(value -> new RealVar[size]);

                OperatorEnum op = cep.getOperator();
                if (op.equals(OperatorEnum.PLUS)) {
                    String func = getFunctionPattern("+", size);
                    RealVar[] finalVars = ArrayUtils.append(vars, new RealVar[]{var});
                    rc.addFunction(func, finalVars);
                    log.info("RealConstraint: " + func + " with real vars:" + printVarArray(finalVars));
                } else if (op.equals(OperatorEnum.MINUS)) {
                    String func = getFunctionPattern("-", size);
                    RealVar[] finalVars = ArrayUtils.append(vars, new RealVar[]{var});
                    rc.addFunction(func, finalVars);
                    log.info("RealConstraint: " + func + " with real vars:" + printVarArray(finalVars));
                } else if (op.equals(OperatorEnum.TIMES)) {
                    String func = getFunctionPattern("*", size);
                    RealVar[] finalVars = ArrayUtils.append(vars, new RealVar[]{var});
                    rc.addFunction(func, finalVars);
                    log.info("RealConstraint: " + func + " with real vars:" + printVarArray(finalVars));
                } else if (op.equals(OperatorEnum.DIV)) {
                    String func = getFunctionPattern("/", size);
                    RealVar[] finalVars = ArrayUtils.append(vars, new RealVar[]{var});
                    rc.addFunction(func, finalVars);
                    log.info("RealConstraint: " + func + " with real vars:" + printVarArray(finalVars));
                }
                return var;
            } else if (expr instanceof UnaryExpression) {
                UnaryExpression ue = (UnaryExpression) expr;
                RealVar var2 = parseRealExpression(ue.getExpression(), rc);
                StringBuilder function = new StringBuilder("(");
                if (ue instanceof SimpleUnaryExpression) {
                    SimpleUnaryExpression sue = (SimpleUnaryExpression) ue;
                    SimpleUnaryOperatorEnum op = sue.getOperator();
                    if (op.equals(SimpleUnaryOperatorEnum.ABSTRACT_VALUE)) {
                        function.append(" abs({0}) = {1} )");
                        RealVar[] vars = new RealVar[]{var2, var};
                        String func = function.toString();
                        rc.addFunction(func, vars);
                        log.info("RealConstraint: " + func + " with real vars:" + printVarArray(vars));
                    } else if (op.equals(SimpleUnaryOperatorEnum.LN_VALUE)) {
                        function.append(" ln({0}) = {1} )");
                        RealVar[] vars = new RealVar[]{var2, var};
                        String func = function.toString();
                        rc.addFunction(func, vars);
                        log.info("RealConstraint: " + func + " with real vars:" + printVarArray(vars));
                    }
                    return var;
                } else if (ue instanceof ComposedUnaryExpression) {
                    ComposedUnaryExpression cue = (ComposedUnaryExpression) ue;
                    int val = cue.getValue();
                    ComposedUnaryOperatorEnum op = cue.getOperator();
                    if (op.equals(ComposedUnaryOperatorEnum.EXPONENTIAL_VALUE)) {
						/*if (val == 1) return var2;
						else if (val == 2){
							function = new StringBuilder("(");
							function.append("{0} * {0} = {1} )");
							RealElement[] vars = new RealElement[]{var2,var};
							rc.addFunction(function.toString(),vars);
						}
						else{
							function = new StringBuilder("( {0} ");
							for (int i = 1; i < val; i++){
								function.append("* {" + i + "} ");
							}
							function.append(" )");
							RealElement[] vars = new RealElement[]{var2,var};
							rc.addFunction(function.toString(),vars);
						}*/
                        function = new StringBuilder("( pow( {0}, " + val + " ) = {1} )");
                        RealVar[] vars = new RealVar[]{var2, var};
                        String func = function.toString();
                        rc.addFunction(func, vars);
                        log.info("RealConstraint: " + func + " with real vars:" + printVarArray(vars));
                    } else {
                        //! Is this correct?
                        function = new StringBuilder("( log( {0}, " + val + " ) = {1} )");
                        RealVar[] vars = new RealVar[]{var2, var};
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
    private void createConstraints(EList<ComparisonExpression> constraints) {
        RealConstraint rc = null;
        log.info("--------------- Constraints ---------------");
        for (ComparisonExpression constr : constraints) {
            Expression expr1 = constr.getExp1();
            Expression expr2 = constr.getExp2();
            ComparatorEnum operator = constr.getComparator();
            boolean isInt = involvesOnlyInt(expr1);
            if (isInt) {
                isInt = involvesOnlyInt(expr2);
                log.info("Constraint only involves integer variables");
            } else {
                log.info("Constraint involves real variables");
            }

            Constraint constraint = null;
            if (isInt) {
                if (expr1 instanceof CpVariable) {
                    CpVariable var = (CpVariable) expr1;
                    String id = var.getId();
                    IntVar v = idToIntVar.get(id);
                    IntVar var2 = parseExpression(expr2);
                    String opStr = getComparator(operator, false);
                    constraint = IntConstraintFactory.arithm(v, opStr, var2);
                    log.info("IntConstraint: {} {} {}", v.getId(), opStr,var2.getId());
                } else if (expr1 instanceof CpMetric) {
                    CpMetric var = (CpMetric) expr1;
                    String id = var.getId();
                    IntVar v = idToIntVar.get(id);
                    IntVar var2 = parseExpression(expr2);
                    String opStr = getComparator(operator, false);
                    constraint = IntConstraintFactory.arithm(v, opStr, var2);
                    log.info("IntConstraint: {} {} {}", v.getName(), opStr, var2.getName());
                } else if (expr1 instanceof Constant) {
                    Constant constant = (Constant) expr1;
                    BasicTypeEnum type = constant.getType();
                    if (type.equals(BasicTypeEnum.INTEGER)) {
                        IntegerValueUpperware intVal = (IntegerValueUpperware) constant.getValue();
                        int value = intVal.getValue();
                        IntVar v = parseExpression(expr2);
                        //System.out.println("Parsed expression is: " + parseExpression(expr2));
                        String opStr = getComparator(operator, true);
                        constraint = IntConstraintFactory.arithm(parseExpression(expr2), opStr, value);
                        log.info("IntConstraint: {} {} {}", v.getName(), opStr, value);
                    } else if (type.equals(BasicTypeEnum.LONG)) {
                        LongValueUpperware longVal = (LongValueUpperware) constant.getValue();
                        long value = longVal.getValue();
                        IntVar v = parseExpression(expr2);
                        //System.out.println("Parsed expression is: " + parseExpression(expr2));
                        String opStr = getComparator(operator, true);
                        constraint = IntConstraintFactory.arithm(v, opStr, (int) value);
                        log.info("IntConstraint: {} {} {}", v.getName(), opStr, value);
                    }
                }
                if (expr1 instanceof ComposedExpression || expr1 instanceof UnaryExpression) {
                    if (expr1 instanceof ComposedExpression) {
                        //System.out.println("First expression is: " + parseExpression(expr1) + " and second expression is: " + parseExpression(expr2));
                        IntVar v1 = parseExpression(expr1);
                        IntVar v2 = parseExpression(expr2);
                        String opStr = getComparator(operator, false);
                        constraint = IntConstraintFactory.arithm(v1, opStr, v2);
                        log.info("IntConstraint: {} {} {}", v1.getName(), opStr, v2.getName());
                    }
                }
            } else {
                if (rc == null) rc = new RealConstraint(solver);
                StringBuilder function = new StringBuilder("(");
                if (expr1 instanceof CpVariable) {
                    CpVariable var = (CpVariable) expr1;
                    String id = var.getId();
                    log.info("Checking variable with name: {}", id);
                    RealVar v = idToRealVar.get(id);
                    log.info("RealElement is: {}", v);
                    if (v == null) {
                        IntVar iv = idToIntVar.get(id);
                        //Checking if int var is involved
                        if (iv != null) {
                            v = VariableFactory.real(iv, epsilon);
                            log.info("RealElement: {} on top of IntElement: {}", v, iv.getName());
                        }
                        //If not, then we have a problem
                        else {
                            log.error("Got a new variable not previously parsed");
                        }
                    }
                    function.append(" {0} " + getComparator(operator, false) + " {1} )");
                    log.info("Operator is: {} second expression is: {}", operator, expr2);
                    RealVar[] all_vars = new RealVar[]{v, parseRealExpression(expr2, rc)};
                    String func = function.toString();
                    log.info("RealConstraint: {} with RealVars: {}", func, printVarArray(all_vars));
                    rc.addFunction(func, all_vars);
                } else if (expr1 instanceof CpMetric) {
                    log.debug("CASE 2");
                    CpMetric var = (CpMetric) expr1;
                    String id = var.getId();
                    RealVar v = idToRealVar.get(id);
                    function.append(" {0} " + getComparator(operator, false) + " {1} )");
                    RealVar[] all_vars = new RealVar[]{v, parseRealExpression(expr2, rc)};
                    String func = function.toString();
                    log.info("RealConstraint: {} with RealVars: {}",  func, printVarArray(all_vars));
                    rc.addFunction(func, all_vars);
                } else if (expr1 instanceof Constant) {
                    log.debug("CASE 3");
                    Constant constant = (Constant) expr1;
                    BasicTypeEnum type = constant.getType();
                    if (type.equals(BasicTypeEnum.INTEGER)) {
                        IntegerValueUpperware intVal = (IntegerValueUpperware) constant.getValue();
                        function.append(" " + intVal.getValue() + getComparator(operator, false) + " {0} )");
                        RealVar[] all_vars = new RealVar[]{parseRealExpression(expr2, rc)};
                        String func = function.toString();
                        log.info("RealConstraint: {} with RealVars: {}", func, printVarArray(all_vars));
                        rc.addFunction(func, all_vars);
                    } else if (type.equals(BasicTypeEnum.LONG)) {
                        LongValueUpperware longVal = (LongValueUpperware) constant.getValue();
                        function.append(" " + longVal.getValue() + getComparator(operator, false) + " {0} )");
                        RealVar[] all_vars = new RealVar[]{parseRealExpression(expr2, rc)};
                        String func = function.toString();
                        log.info("RealConstraint: {} with RealVars: {}", func, printVarArray(all_vars));
                        rc.addFunction(func, all_vars);
                    } else if (type.equals(BasicTypeEnum.FLOAT)) {
                        FloatValueUpperware floatVal = (FloatValueUpperware) constant.getValue();
                        function.append(" " + floatVal.getValue() + getComparator(operator, false) + " {0} )");
                        RealVar[] all_vars = new RealVar[]{parseRealExpression(expr2, rc)};
                        String func = function.toString();
                        log.info("RealConstraint: {} with RealVars: {}", func, printVarArray(all_vars));
                        rc.addFunction(func, all_vars);
                    } else if (type.equals(BasicTypeEnum.DOUBLE)) {
                        DoubleValueUpperware doubleVal = (DoubleValueUpperware) constant.getValue();
                        function.append(" " + doubleVal.getValue() + getComparator(operator, false) + " {0} )");
                        RealVar[] all_vars = new RealVar[]{parseRealExpression(expr2, rc)};
                        String func = function.toString();
                        log.info("RealConstraint: {} with RealVars: {}", func, printVarArray(all_vars));
                        rc.addFunction(func, all_vars);
                    }
                }
                if (expr1 instanceof ComposedExpression || expr1 instanceof UnaryExpression) {
                    ComposedExpression expr = (ComposedExpression) expr1;
                    RealVar var1 = parseRealExpression(expr, rc);
                    RealVar var2 = parseRealExpression(expr2, rc);
                    function.append(" {0} " + getComparator(operator, false) + " {1} )");
                    RealVar[] all_vars = new RealVar[]{var1, var2};
                    String func = function.toString();
                    log.info("RealConstraint: {} with RealVars: {}", func, printVarArray(all_vars));
                    rc.addFunction(func, all_vars);
                }
            }
            if (constraint != null) {
                solver.post(constraint);
            }
        }
        if (rc != null) {
            solver.post(rc);
        }
        log.info("------------------------------------------");
    }

    /* Creating the metric variables */
    private void createMetrics(EList<CpMetric> metrics) {
        log.info("--------------- Metric ---------------");
        for (CpMetric metric : metrics) {
            NumericValueUpperware value = metric.getValue();
            if (metric.getType().equals(BasicTypeEnum.DOUBLE)) {
                if (value instanceof DoubleValueUpperware) {
                    double val = ((DoubleValueUpperware) value).getValue();
                    RealVar var = VariableFactory.real(metric.getId(), val, val, epsilon, solver);
                    log.info("Metric {} : {}", metric.getId(), var);
                    idToRealVar.put(metric.getId(), var);
                }
            } else if (metric.getType().equals(BasicTypeEnum.INTEGER)) {
                if (value instanceof IntegerValueUpperware) {
                    int val = ((IntegerValueUpperware) value).getValue();
                    IntVar var = VariableFactory.fixed(val, solver);
                    log.info("Metric {} : {}", metric.getId(), var);
                    idToIntVar.put(metric.getId(), var);
                }
            } else if (metric.getType().equals(BasicTypeEnum.LONG)) {
                if (value instanceof LongValueUpperware) {
                    long val = ((LongValueUpperware) value).getValue();
                    IntVar var = VariableFactory.fixed((int) val, solver);
                    log.info("Metric {} : {}", metric.getId(), var);
                    idToIntVar.put(metric.getId(), var);
                }
            } else if (metric.getType().equals(BasicTypeEnum.FLOAT)) {
                if (value instanceof FloatValueUpperware) {
                    double val = ((FloatValueUpperware) value).getValue();
                    RealVar var = VariableFactory.real(metric.getId(), val, val, epsilon, solver);
                    log.info("Metric {} : {}", metric.getId(), var);
                    idToRealVar.put(metric.getId(), var);
                }
            }
        }
        log.info("------------------------------------------");
    }

    /* Creating the normal variables */
    private void createVariables(EList<CpVariable> vars) {
        log.info("--------------- Variables ---------------");
        for (CpVariable var : vars) {
            Domain dom = var.getDomain();
            if (dom instanceof RangeDomain) {
                RangeDomain rd = (RangeDomain) dom;
                BasicTypeEnum type = rd.getType();

                NumericValueUpperware from = rd.getFrom();
                NumericValueUpperware to = rd.getTo();

                if (type.equals(BasicTypeEnum.INTEGER)) {
                    IntegerValueUpperware int1 = (IntegerValueUpperware) from;
                    IntegerValueUpperware int2 = (IntegerValueUpperware) to;
                    String id = var.getId();
                    IntVar v = createIntVar(id, int1.getValue(), int2.getValue());
                    log.info("IntElement: {}", v);
                    idToIntVar.put(id, v);
                } else if (type.equals(BasicTypeEnum.LONG)) {
                    LongValueUpperware long1 = (LongValueUpperware) from;
                    LongValueUpperware long2 = (LongValueUpperware) to;
                    String id = var.getId();
                    IntVar v = createIntVar(id, (int) long1.getValue(), (int) long2.getValue());
                    log.info("IntElement: {}", v);
                    idToIntVar.put(id, v);
                } else if (type.equals(BasicTypeEnum.DOUBLE)) {
                    DoubleValueUpperware real1 = (DoubleValueUpperware) from;
                    DoubleValueUpperware real2 = (DoubleValueUpperware) to;
                    String id = var.getId();
                    RealVar v = VariableFactory.real(id, real1.getValue(), real2.getValue(), epsilon, solver);
                    log.info("RealElement: {}", v);
                    idToRealVar.put(id, v);
                } else if (type.equals(BasicTypeEnum.FLOAT)) {
                    FloatValueUpperware float1 = (FloatValueUpperware) from;
                    FloatValueUpperware float2 = (FloatValueUpperware) to;
                    String id = var.getId();
                    RealVar v = VariableFactory.real(id, float1.getValue(), float2.getValue(), epsilon, solver);
                    log.info("RealElement: {}", v);
                    idToRealVar.put(id, v);
                }
            } else if (dom instanceof NumericListDomain) {
                NumericListDomain nld = (NumericListDomain) dom;
                BasicTypeEnum type = nld.getType();

                if (type.equals(BasicTypeEnum.INTEGER)) {
                    createEnumeratedDomain(var, nld, IntegerValueUpperware.class, IntegerValueUpperware::getValue);
                } else if (type.equals(BasicTypeEnum.LONG)) {
                    createEnumeratedDomain(var, nld, LongValueUpperware.class, value -> (int) value.getValue());
                } else if (type.equals(BasicTypeEnum.DOUBLE)) {

                    double min = nld.getValues().stream().mapToDouble(value -> ((DoubleValueUpperware) value).getValue()).min().orElse(0d);
                    double max = nld.getValues().stream().mapToDouble(value -> ((DoubleValueUpperware) value).getValue()).max().orElse(Double.MAX_VALUE);

                    String id = var.getId();
                    RealVar v = VariableFactory.real(id, min, max, epsilon, solver);
                    log.info("RealElement: {}", v);
                    idToRealVar.put(id, v);
                } else if (type.equals(BasicTypeEnum.FLOAT)) {

                    double min = nld.getValues().stream().mapToDouble(value -> ((FloatValueUpperware) value).getValue()).min().orElse(0d);
                    double max = nld.getValues().stream().mapToDouble(value -> ((FloatValueUpperware) value).getValue()).max().orElse(Double.MAX_VALUE);

                    String id = var.getId();
                    RealVar v = VariableFactory.real(id, min, max, epsilon, solver);
                    log.info("RealElement: {}", v);
                    idToRealVar.put(id, v);
                }

            } else if (dom instanceof NumericDomain) {
                //System.out.println("Got numeric domain for variable:" + var.getId());
                NumericDomain nd = (NumericDomain) dom;
                BasicTypeEnum type = nd.getType();
                if (type.equals(BasicTypeEnum.INTEGER) || type.equals(BasicTypeEnum.LONG)) {
                    String id = var.getId();
                    IntVar v = createIntVar(id);
                    log.info("IntElement: {}", v);
                    idToIntVar.put(id, v);
                } else if (type.equals(BasicTypeEnum.DOUBLE) || type.equals(BasicTypeEnum.FLOAT)) {
                    String id = var.getId();
                    RealVar v = VariableFactory.real(id, LOW_REAL_LIMIT, UPPER_REAL_LIMIT, epsilon, solver);
                    log.info("RealElement: {}", v);
                    idToRealVar.put(id, v);
                }
            }
        }
        log.info("------------------------------------------");
    }

    private <T> void createEnumeratedDomain(CpVariable var, NumericListDomain nld, Class<T> type, ToIntFunction<? super T> mapper) {
        int[] ints = nld.getValues()
                .stream()
                .filter(type::isInstance)
                .map(type::cast)
                .mapToInt(mapper)
                .toArray();

        String id = var.getId();
        IntVar v = createIntVar(id, ints);
        log.info("IntElement: {}", v);
        idToIntVar.put(id, v);
    }

    /* Creating the constants of the cp problem */
    private void createConstants(EList<Constant> constants) {
        log.info("--------------- Constants ---------------");
        for (Constant constant : constants) {
            NumericValueUpperware value = constant.getValue();
            //System.out.println("Type of constant is: " + constant.getType());
            if (constant.getType().equals(BasicTypeEnum.DOUBLE)) {
                if (value instanceof DoubleValueUpperware) {
                    double val = ((DoubleValueUpperware) value).getValue();
                    RealVar var = VariableFactory.real(constant.getId(), val, val, epsilon, solver);
                    log.info("Constant {} : {}", constant.getId(), var);
                    idToRealVar.put(constant.getId(), var);
                }
            } else if (constant.getType().equals(BasicTypeEnum.INTEGER)) {
                if (value instanceof IntegerValueUpperware) {
                    int val = ((IntegerValueUpperware) value).getValue();
                    IntVar var = VariableFactory.fixed(val, solver);
                    log.info("Constant {} : {}", constant.getId(), var);
                    idToIntVar.put(constant.getId(), var);
                }
            } else if (constant.getType().equals(BasicTypeEnum.LONG)) {
                if (value instanceof LongValueUpperware) {
                    long val = ((LongValueUpperware) value).getValue();
                    IntVar var = VariableFactory.fixed((int) val, solver);
                    log.info("Constant {} : {}", constant.getId(), var);
                    idToIntVar.put(constant.getId(), var);
                }
            } else if (constant.getType().equals(BasicTypeEnum.FLOAT)) {
                if (value instanceof FloatValueUpperware) {
                    double val = ((FloatValueUpperware) value).getValue();
                    RealVar var = VariableFactory.real(constant.getId(), val, val, epsilon, solver);
                    log.info("Constant {} : {}", constant.getId(), var);
                    idToRealVar.put(constant.getId(), var);
                }
            }
        }
        log.info("------------------------------------------");
    }

    /* Re-initializing the main configuration variables of the solver*/
    private void dispose() {
        intVarNum = 0;
        realVarNum = 0;
        constNum = 0;
    }

    private void calculateUtility() {

        Collection<Element> intSolution = Arrays.stream(solver.retrieveIntVars())
                .map(var -> ElementFactory.createElement(var.getName(), var.getValue()))
                .collect(Collectors.toList());

        log.debug("First step: {}", intSolution);

        intSolution = addSingleValueIntVariables(intSolution);
        log.debug("Second step: {}", intSolution);

        Collection<Element> realSolution = Arrays.stream(solver.retrieveRealVars())
                .map(var -> ElementFactory.createElement(var.getName(), var.getUB()))
                .collect(Collectors.toList());

        realSolution = addSingleValueRealVariables(realSolution);

        log.debug("Second step real:{}", realSolution);

        double utility = utilityGenerator.evaluate(Stream.concat(intSolution.stream(), realSolution.stream()).collect(Collectors.toList()));
        if (utility > maxUtility) {
            log.info("New utility value {} is greater than {}", utility, maxUtility);
            convertAndUpdateBestSolution(utility);
        } else {
            log.debug("New utility value {} is NOT greater than {}", utility, maxUtility);
        }
    }

    //fixme: checking if single value should be in idToIntVar or in idToRealVar
    private Collection<Element> addSingleValueIntVariables(Collection<Element> solution) {

        variablesForUG.stream()
                .filter(varDTO -> solution.stream().noneMatch(varSolver -> varDTO.getId().equals(varSolver.getName())))
                .filter(varDTO -> idToIntVar.get(varDTO.getId()) != null)
                .forEach(v -> solution.add(ElementFactory.createElement(v.getId(), idToIntVar.get(v.getId()).getValue())));

        return solution;

    }

    //storage is the only variable real/ hack!
    private Collection<Element> addSingleValueRealVariables(Collection<Element> solution) {
        variablesForUG.stream()
                .filter(varDTO -> solution.stream().noneMatch(varSolver -> varDTO.getId().equals(varSolver.getName())) && varDTO.getType().equals(VariableType.STORAGE))
                .forEach(v -> solution.add(new RealElement(v.getId(), idToRealVar.get(v.getId()).getUB())));

        return solution;
    }


    private BoolVar createBoolVar() {
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

    private String getIntVarName() {
        return "IntElement" + (intVarNum++);
    }

    private String getBoolVarName() {
        return "BoolVar" + (intVarNum++);
    }

}

