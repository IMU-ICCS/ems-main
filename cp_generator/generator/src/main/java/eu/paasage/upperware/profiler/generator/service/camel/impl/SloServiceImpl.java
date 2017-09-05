package eu.paasage.upperware.profiler.generator.service.camel.impl;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.requirement.HorizontalScaleRequirement;
import eu.paasage.camel.requirement.RequirementModel;
import eu.paasage.camel.requirement.ServiceLevelObjective;
import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.metamodel.types.IntegerValueUpperware;
import eu.paasage.upperware.profiler.cp.generator.model.tools.CPModelTool;
import eu.paasage.upperware.profiler.generator.algebra.Algebra;
import eu.paasage.upperware.profiler.generator.algebra.AlgebraVariable;
import eu.paasage.upperware.profiler.generator.algebra.ExpressionUtils;
import eu.paasage.upperware.profiler.generator.algebra.exceptions.MissingVariablesException;
import eu.paasage.upperware.profiler.generator.algebra.exceptions.NotSolvableException;
import eu.paasage.upperware.profiler.generator.algebra.exceptions.WrongStatementException;
import eu.paasage.upperware.profiler.generator.service.camel.SloService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SloServiceImpl implements SloService {

    private Algebra algebra;

    private static final String U_APP_COMPONENT = "U_app_component_";

    public void update(CamelModel cModel, ConstraintProblem cpModel) {

		/* detect available SLOs in the CAMEL model */
        Set<String> metricConditions = getMetricConditions(cModel);
        List<AlgebraVariable> varList = getAlgebraVariables(cModel);

		/* (a) nothing defined */
        if (metricConditions.isEmpty()) {
            printValidatingUserRequirementsHeaderAndMessage("No SLO found. DONE.");
            return;
        }

        if (varList.isEmpty()) {
            printValidatingUserRequirementsHeaderAndMessage("No metric conditions defined. DONE.");
            return;
        }

//		/* (b) */
//        if (!openCDOSession(cdoIdentifier)) {
//            System.out.println("    -> Error: Cloning the model was not successful: " + cdoIdentifier);
//            System.out.println("    -> Please have a look at the output of the previous component.");
////			return SOLUTION_STATUS.ERROR;
//            return; //TODO - moze rzucic exception
//        }
//        List<EObject> objList;
//        try {
//            objList = this.getCloneModel();
//        } catch (Exception e) {
//            System.out.println("    -> Error: Cloning the model was not successful: " + e.getMessage());
//            System.out.println("    -> Please have a look at the output of the previous component.");
//            return GenerationOrchestrator.SOLUTION_STATUS.ERROR;
//        }
//        cloneResId_ = cdoIdentifier + "v2";

        printValidatingUserRequirementsHeader();

        Map<AlgebraVariable, Integer> varLeftMap = new HashMap<AlgebraVariable, Integer>();
        Map<AlgebraVariable, Integer> varRightMap = new HashMap<AlgebraVariable, Integer>();
        Set<String> slos = new HashSet<String>();


//        ConstraintProblem cpModelXXX = null;
//        for (EObject eObject : objList) {
//            if (eObject instanceof ConstraintProblem) {
//                cpModel = (ConstraintProblem) eObject;
        for (ComparisonExpression cExpression : cpModel.getConstraints()) {
            for (String metricCondition : metricConditions) {
                if (cExpression.getId().startsWith(metricCondition)) {
                    slos.add(ExpressionUtils.toString(cExpression, varList, varLeftMap, varRightMap));
                }
            }
        }
//            }
//        }

        if (slos.isEmpty()) {
            log.warn("NO SLOs detected. Continue.");
            return;
        }

        StringBuilder expression = new StringBuilder();
        List<AlgebraVariable> variables = Stream.of(varLeftMap.keySet(), varRightMap.keySet())
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        log.info("GIVEN:");

        for (String slo : slos) {
            log.info("    -> SLO: " + slo);
            expression.append(slo);
        }

        for (AlgebraVariable av : variables){
            createExpressions(expression, av);
        }

        // evaluate expressions
        log.info("EVALUATE:");
        log.info("    -> " + expression.toString());

        Set<Integer> domain = new HashSet<Integer>();
        Map<AlgebraVariable, AlgebraVariable> replaceMap = new HashMap<AlgebraVariable, AlgebraVariable>();

        List<AlgebraVariable> ranges;
        try {
            ranges = algebra.test(expression.toString(), variables);
            log.info("RESULT:");
            log.info("    -> CP model will be updated to comply with new domain ranges.");

            for (AlgebraVariable av : ranges) {
                domain.add(av.getFrom());
                domain.add(av.getTo());

                String from = av.getVariable() + " >= " + av.getFrom();
                String to = av.getVariable() + " <= " + av.getTo();
                log.info("    -> " + from);
                log.info("    -> " + to);
            }

            for (int i = 0; i != variables.size(); ++i) {
                replaceMap.put(variables.get(i), ranges.get(i));
            }

			/* update CP model */
            int nextId = 0;
            Set<Integer> availableDomain = new HashSet<Integer>();
            /* modify/add constants */
            Map<Integer, Constant> newConstants = new HashMap<Integer, Constant>();

            for (Constant c : cpModel.getConstants()) {
                if (c.getValue() instanceof IntegerValueUpperware) {
                    IntegerValueUpperware ivu = (IntegerValueUpperware) c.getValue();
                    availableDomain.add(ivu.getValue());

                    newConstants.put(ivu.getValue(), c);
                }
                if (c.getId().startsWith("constant_")) {
                    nextId = Integer.valueOf(c.getId().split("_")[1]);
                }
            }
            domain.removeAll(availableDomain);
            for (Integer value : domain) {
                Constant c = CPModelTool.createIntegerConstant(value, "constant_" + ++nextId);
                cpModel.getConstants().add(c);
                newConstants.put(value, c);
                log.info("    -> ADDED " + c.getId());
            }

            /* check comparison expressions */
            for (ComparisonExpression comp : cpModel.getConstraints()) {
                Set<String> unique = new HashSet<String>();
                if (comp.getExp1() instanceof ComposedExpression) {
                    ComposedExpression composed = (ComposedExpression) comp.getExp1();
                    for (Expression exp : composed.getExpressions()) {
                        if (exp instanceof Variable) {
                            Variable v = (Variable) exp;
                            if (v.getId().startsWith(U_APP_COMPONENT)) {
                                unique.add(v.getComponentName());
                            }
                        }
                    }
                }

                if (unique.size() != 1) {
                    continue;
                }

                String component = unique.iterator().next();
                AlgebraVariable oldVariable = null;
                for (AlgebraVariable av : variables) {
                    if (av.getVariable().equals(component)) {
                        oldVariable = av;
                        break;
                    }
                }

                if (oldVariable == null) {
                    continue;
                }

                String comparator = ExpressionUtils.comparatorToString(comp.getComparator());

                if (comp.getExp2() instanceof Constant) {
                    Constant old = (Constant) comp.getExp2();
                    if (old.getValue() instanceof IntegerValueUpperware) {
                        IntegerValueUpperware ivu = (IntegerValueUpperware) old.getValue();
                        int value = ivu.getValue();

                        if (comparator.equals(">=")) {
                            if (value == oldVariable.getFrom()) {
                                AlgebraVariable replaceVar = replaceMap.get(oldVariable);
                                if (replaceVar.getFrom() != value) {
                                    log.info("    -> UPDATED " + comp.getId());
                                    Constant replaceConstant = newConstants.get(replaceVar.getFrom());
                                    comp.setExp2(replaceConstant);
                                }
                            }
                        } else if (comparator.equals("<=")) {
                            if (value == oldVariable.getTo()) {
                                AlgebraVariable replaceVar = replaceMap.get(oldVariable);
                                if (replaceVar.getTo() != value) {
                                    log.info("    -> UPDATED " + comp.getId());
                                    Constant replaceConstant = newConstants.get(replaceVar.getTo());
                                    comp.setExp2(replaceConstant);
                                }
                            }
                        }
                    }
                }
            }

            /* update variables */
            for (Variable variable : cpModel.getVariables()) {
                if (variable.getId().startsWith(U_APP_COMPONENT)) {
                    String id = variable.getComponentName();
                    for (AlgebraVariable av : ranges) {
                        if (av.getVariable().equals(id)) {
                            Domain d = variable.getDomain();
                            if (d instanceof RangeDomain) {
                                RangeDomain rd = (RangeDomain) d;
                                // Don't update minInstances, because its set to 0 by CP generator
                                //IntegerValueUpperware from = (IntegerValueUpperware) rd.getFrom();
                                //from.setValue(av.getFrom());
                                IntegerValueUpperware to = (IntegerValueUpperware) rd.getTo();
                                if (to.getValue() != av.getTo()) {
                                    log.info("    -> UPDATED " + variable.getId());
                                    to.setValue(av.getTo());
                                }
                            }
                        }
                    }
                }
            }

        } catch (MissingVariablesException e) {
            log.error("User requirements: Missing variables: ", e);
        } catch (WrongStatementException e) {
            log.error("User requirements: Invalid statement. Please check the model.");
        } catch (NotSolvableException e) {
            log.error("User requirements: Invalid statement. Not Solvable");
        }
    }

    private void createExpressions(StringBuilder expression, AlgebraVariable av) {
        String from = av.getVariable() + " >= " + av.getFrom();
        String to = av.getVariable() + " <= " + av.getTo();

        log.info("    -> " + from);
        log.info("    -> " + to);

        expression.append(" && ");
        expression.append(from);
        expression.append(" && ");
        expression.append(to);
    }

    private void printValidatingUserRequirementsHeader() {
        log.info("*************************************************");
        log.info("VALIDATING USER REQUIREMENTS");
        log.info("*************************************************");
    }

    private void printValidatingUserRequirementsHeaderAndMessage(String message) {
        printValidatingUserRequirementsHeader();
        log.info(message);
        log.info("*************************************************");
    }

    private Set<String> getMetricConditions(CamelModel cModel){
        return cModel.getRequirementModels().stream()
                .map(RequirementModel::getRequirements)
                .flatMap(Collection::stream)
                .filter(requirement -> requirement instanceof ServiceLevelObjective)
                .map(requirement -> ((ServiceLevelObjective) requirement).getCustomServiceLevel().getName())
                .collect(Collectors.toSet());
    }

    private List<AlgebraVariable> getAlgebraVariables(CamelModel cModel){
        return cModel.getRequirementModels().stream()
                .map(RequirementModel::getRequirements)
                .flatMap(Collection::stream)
                .filter(requirement -> requirement instanceof HorizontalScaleRequirement)
                .map(requirement -> (HorizontalScaleRequirement) requirement)
                .map(this::createAlgebraVariable)
                .collect(Collectors.toList());
    }

    private AlgebraVariable createAlgebraVariable(HorizontalScaleRequirement hsr) {
        String name = hsr.getComponent().getName();
        int min = hsr.getMinInstances();
        int max = hsr.getMaxInstances();
        return new AlgebraVariable(name, min, max);
    }

}
