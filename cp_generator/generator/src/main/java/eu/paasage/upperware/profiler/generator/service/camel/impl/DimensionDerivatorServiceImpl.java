package eu.paasage.upperware.profiler.generator.service.camel.impl;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.InternalComponent;
import eu.paasage.camel.metric.*;
import eu.paasage.camel.requirement.OptimisationFunctionType;
import eu.paasage.camel.requirement.OptimisationRequirement;
import eu.paasage.camel.requirement.RequirementModel;
import eu.paasage.camel.type.*;
import eu.paasage.upperware.metamodel.cp.*;
//import eu.paasage.upperware.profiler.cp.generator.model.tools.CPModelTool;
import eu.paasage.upperware.profiler.generator.service.camel.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.common.util.EList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DimensionDerivatorServiceImpl implements DimensionDerivatorService {

    private static final String GOAL_ID_SEPARATOR = "";
    private static final String NUMBER_OF_VMS_SUBSTRING = "";


    private CpFactory cpFactory;
    private ConstantService constantService;
    private ConstraintService constraintService;
    private VariableService variableService;
    private IdGenerator constantIdGenerator;


    public void createDimensions(CamelModel camel, ConstraintProblem cp, List<OptimisationRequirement> complexOptRequirements) {
        RequirementModel reqs = camel.getRequirementModels().get(0);

        cp.getSolution().add(createSolution());
/*
        for (Requirement req : reqs.getRequirements()) {

            if (req instanceof OptimisationRequirement) {

                OptimisationRequirement optReq = (OptimisationRequirement) req;

                if (optReq.getMetric() != null) {
                    Metric metric = optReq.getMetric();
                    MetricContext metricContext = optReq.getMetricContext();

                    OptimisationFunctionType optimisationType = optReq.getOptimisationFunction();

                    List<NumericExpression> expressions = new ArrayList<>();

                    if (metric instanceof RawMetric) {
                        expressions = processRawMetric((RawMetric) metric, (RawMetricContext) metricContext, camel, cp);
                    } else {
                        complexOptRequirements.add(optReq);

                        NumericExpression expression = processCompositeMetric((CompositeMetric) metric, (CompositeMetricContext) metricContext, camel, cp);

                        if (expression != null) {
                            expressions.add(expression);
                        }
                    }

                    int count = 0;

                    for (NumericExpression exp : expressions) {
                        createDimension(exp, cp, optimisationType, optReq.getPriority(), optReq.getName() + GOAL_ID_SEPARATOR + count);
                        count++;
                    }
                } else {
                    log.warn("DimensionDerivator - createDimensions - The optimisation requirement " + optReq.getName() + "does not have a related metric. The dimension will be not generated!");
                }
            }
        }
        */
    }

    private Solution createSolution() {
        Solution sol = cpFactory.createSolution();
        sol.setTimestamp(System.currentTimeMillis());
        return sol;
    }

    private Solution searchLastSolution(EList<Solution> solutions) {
        Solution result = null;

        if (CollectionUtils.isNotEmpty(solutions)){
            for (Solution solution : solutions) {
                if (result == null || solution.getTimestamp() > result.getTimestamp()) {
                    result = solution;
                }
            }
        }
        return result;
    }

    private void createDimension(NumericExpression exp, ConstraintProblem cp, OptimisationFunctionType optimisationType, double priority, String s) {

    }


//    protected List<NumericExpression> processRawMetric(RawMetric metric, RawMetricContext metricContext, CamelModel camel, ConstraintProblem cp) {
//        List<NumericExpression> expressions = new ArrayList<NumericExpression>();
//        List<InternalComponent> components = new ArrayList<>();
//
//        //log.debug("DimensionDerivator - processRawMetric 1");
//        //The context is a raw context - It is mandatory
//        if (metricContext != null && metricContext.getComponent() != null && metricContext.getComponent() instanceof InternalComponent) {
//            components.add((InternalComponent) metricContext.getComponent());
//        } else {
//            components.addAll(camel.getApplications().get(0).getDeploymentModels().get(0).getInternalComponents());
//        }
//
//        if (metric.isIsVariable() && !metric.getName().toLowerCase().contains(NUMBER_OF_VMS_SUBSTRING.toLowerCase())) {
//
//            for (InternalComponent ic : components) {
//                String varName = CPModelTool.getUserVariableName(metric, ic);
//
//                Variable var = CPModelTool.searchVariableByName(varName, cp.getVariables());
//
//                if (var == null)
//                    var = createVariable(metric, cp, ic);
//
//                if (var != null)
//                    expressions.add(var);
//            }
//
//
//        } else if (metric.isIsVariable()) {
//
//            List<Variable> variables = CPModelTool.getAllComponentInVMVariables(cp.getVariables());
//
//            for (InternalComponent ic : components) {
//                List<Variable> varsRelated = CPModelTool.getVariablesRelatedToAppComponent(ic.getName(), variables);
//                expressions.addAll(varsRelated);
//            }
//        } else //if(!metric.isIsVariable()) // It is a metric
//        {
//
//            Solution sol = searchLastSolution(cp.getSolution());
//
//
//            for (InternalComponent ic : components) {
//                String metricName = CPModelTool.getUserVariableName(metric, ic);
//                MetricVariable metricVar = CPModelTool.searchMetricVariableByName(metricName, cp.getMetricVariables());
//
//                if (metricVar == null) {
//                    metricVar = createMetricVariable(metric, cp, ic);
//
//                    if (metricVar != null) {
//                        //A solution is required
//                        if (sol == null) {
//                            log.debug("Creating solution for metric var {}", metricVar.getId());
//                            sol = createSolution();
//                            cp.getSolution().add(sol);
//                        }
//                        createValueInSolutionForMetricVariable(metric, cp, ic, sol);
//                    }
//                }
//
//                if (metricVar != null) {
//                    expressions.add(metricVar);
//                }
//            }
//        }
//
//        return expressions;
//    }

    private void createValueInSolutionForMetricVariable(RawMetric metric, ConstraintProblem cp, InternalComponent ic, Solution sol) {

    }

    private MetricVariable createMetricVariable(RawMetric metric, ConstraintProblem cp, InternalComponent ic) {
        return null;
    }


//    protected NumericExpression processCompositeMetric(CompositeMetric metric, CompositeMetricContext context, CamelModel camel, ConstraintProblem cp) {
//        MetricFormula formula = metric.getFormula();
//        return processFormula(metric, formula, context, camel, cp);
//    }

//    protected NumericExpression processFormula(CompositeMetric parentMetric, MetricFormula formula, CompositeMetricContext context, CamelModel camel, ConstraintProblem cp) {
//        List<NumericExpression> expressions = new ArrayList<>();
//        NumericExpression exp = null;
//
//        //log.debug("DimensionDerivator - processFormula 1");
//        for (MetricFormulaParameter parameter : formula.getParameters()) {
//
//            if (parameter instanceof Metric) {
//
//                Metric theMetric = (Metric) parameter;
//                MetricContext theContext = null;
//
//                if (context != null) {
//                    EList<MetricContext> contexts = context.getComposingMetricContexts();
//                    for (int i = 0; i < contexts.size() && theContext == null; i++) {
//                        theContext = searchForMetricContext((Metric) parameter, context);
//                    }
//                }
//
//                if (theMetric instanceof RawMetric) {
//                    expressions.addAll(processRawMetric((RawMetric) theMetric, (RawMetricContext) theContext, camel, cp));
//                } else {
//                    expressions.add(processCompositeMetric((CompositeMetric) theMetric, (CompositeMetricContext) theContext, camel, cp));
//                }
//
//            } else if (parameter instanceof MetricFormula) {
//                expressions.add(processFormula(null, (MetricFormula) parameter, context, camel, cp));
//            } else {
//
//                Constant cVal = null;
//
//                if (parameter instanceof BoolValue) {
//                    BoolValue value = (BoolValue) parameter;
//                    int val = value.isValue() ? 0 : 1;
//                    cVal = constantService.createIntegerConstant(val, parameter.getName());
//                } else if (parameter instanceof EnumerateValue) {
//                    EnumerateValue value = (EnumerateValue) parameter;
//                    int val = value.getValue();
//
//                    cVal = constantService.createIntegerConstant(val, parameter.getName());
//                } else if (parameter instanceof IntegerValue) {
//                    IntegerValue value = (IntegerValue) parameter;
//                    int val = value.getValue();
//
//                    cVal = constantService.createIntegerConstant(val, parameter.getName());
//                } else if (parameter instanceof FloatsValue) {
//                    FloatsValue value = (FloatsValue) parameter;
//                    float val = value.getValue();
//
//                    cVal = constantService.createDoubleConstant(val, parameter.getName());
//                } else if (parameter instanceof DoublePrecisionValue) {
//                    DoublePrecisionValue value = (DoublePrecisionValue) parameter;
//                    double val = value.getValue();
//
//                    cVal = constantService.createDoubleConstant(val, parameter.getName());
//                } else {
//                    int val = 1; //Type not processsed, we use 1 as default value
//
//                    cVal = constantService.createIntegerConstant(val, parameter.getName());
//                }
//
//                if (cVal != null) {
//                    cp.getConstants().add(cVal);
//                    expressions.add(cVal);
//                }
//            }
//        }
//
//        OperatorEnum operator = CPModelTool.getOperatorEnumFromMetricFunctionType(formula.getFunction());
//
//        if (operator != null) {
//            String formulaId = CPModelTool.getUserExpressionName(parentMetric, formula);
//            if ((expressions.size() >= 1 && formula.getFunction().getValue() != MetricFunctionType.MEAN_VALUE) || expressions.size() == 1) {
//                ComposedExpression ce = constraintService.createComposedExpression(operator, formulaId);
//
//                cp.getAuxExpressions().add(ce);
//
//                ce.getExpressions().addAll(expressions);
//
//                exp = ce;
//            } else if (expressions.size() > 1) //It is a mean value
//            {
//                ComposedExpression ce = constraintService.createComposedExpression(OperatorEnum.PLUS, formulaId + "_addition");
//
//                cp.getAuxExpressions().add(ce);
//
//                ce.getExpressions().addAll(expressions);
//
//                ComposedExpression ce2 = constraintService.createComposedExpression(OperatorEnum.DIV, formulaId);
//
//                cp.getAuxExpressions().add(ce2);
//
//                ce.getExpressions().add(ce2);
//
//
//                //TODO - napisac ladniej - begin
//                Optional<Constant> cValOpt = constantService.searchConstantByValue(cp.getConstants(), expressions.size());
//                Constant constant = null;
//                if (!cValOpt.isPresent()) {
//                    constant = constantService.createIntegerConstant(expressions.size(), constantIdGenerator.generate());
//                    cp.getConstants().add(constant);
//                } else {
//                    constant = cValOpt.get();
//                }
//                ce.getExpressions().add(constant);
//                //TODO - napisac to ladniej - end
//            } else {
//                log.warn("DimensionDerivator - processFormula - There is not enough arguments for the formula " + formula.getName() + ", It will be not created!");
//            }
//        } else {
//            log.warn("DimensionDerivator - processFormula - The operator " + formula.getFunction().getName() + " is not currently supperted. The formula " + formula.getName() + " will be not created!");
//        }
//
//        return exp;
//    }

    private MetricContext searchForMetricContext(Metric parameter, CompositeMetricContext context) {
        return null;
    }

//    protected Variable createVariable(Metric metric, ConstraintProblem cp, InternalComponent ic) {
//        String varName= CPModelTool.getUserVariableName(metric, ic);
//
//        Variable var= null;
//
//        if(metric.getValueType()!=null && metric.getValueType() instanceof Range) {
//            Range domain= (Range) metric.getValueType();
//
//            if(domain.getPrimitiveType().getValue()==TypeEnum.INT_TYPE_VALUE) {
//                IntegerValue lowerValue= (IntegerValue) domain.getLowerLimit().getValue();
//                IntegerValue upperValue= (IntegerValue) domain.getUpperLimit().getValue();
//
//                var = variableService.createIntegerVariableWithRangeDomain(varName, lowerValue.getValue(), upperValue.getValue(), null, null);
//
//            } else if(domain.getPrimitiveType().getValue()==TypeEnum.DOUBLE_TYPE_VALUE) {
//                DoublePrecisionValue lowerValue= (DoublePrecisionValue) domain.getLowerLimit().getValue();
//                DoublePrecisionValue upperValue= (DoublePrecisionValue) domain.getUpperLimit().getValue();
//
//                var = variableService.createDoubleVariableWithRangeDomain(varName, lowerValue.getValue(), upperValue.getValue(), null, null);
//
//            } else if(domain.getPrimitiveType().getValue()==TypeEnum.FLOAT_TYPE_VALUE) {
//                FloatsValue lowerValue= (FloatsValue) domain.getLowerLimit().getValue();
//                FloatsValue upperValue= (FloatsValue) domain.getUpperLimit().getValue();
//
//                var = variableService.createDoubleVariableWithRangeDomain(varName, lowerValue.getValue(), upperValue.getValue(), null, null);
//            }
//
//            if(var!=null){
//                cp.getVariables().add(var);
//            } else {
//                log.warn("DimensionDerivator - createDimensions - The variable  "+varName+" cannot be created with the provided domain!");
//            }
//        } else {
//            log.warn("DimensionDerivator - createDimensions - The variable  " + varName + " cannot be created with the provided domain!");
//        }
//
//        return var;
//    }
    
}
