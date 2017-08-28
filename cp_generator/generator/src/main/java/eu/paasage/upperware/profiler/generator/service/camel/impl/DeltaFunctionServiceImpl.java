package eu.paasage.upperware.profiler.generator.service.camel.impl;

import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.profiler.generator.service.camel.ConstantService;
import eu.paasage.upperware.profiler.generator.service.camel.ConstraintService;
import eu.paasage.upperware.profiler.generator.service.camel.DeltaFunctionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DeltaFunctionServiceImpl implements DeltaFunctionService {


    private static final String DELTA_UTILITY_NAME = "delta_utility_functions";
    private static final String SOLUTION_A_NAME = "solution_a";
    private static final String SOLUTION_B_NAME = "solution_b";

    private static final String CONFIGURATION_SUFFIX= "_config";
    private static final String CONFIGURATION_A_SUFFIX= CONFIGURATION_SUFFIX+"_a";
    private static final String CONFIGURATION_B_SUFFIX= CONFIGURATION_SUFFIX+"_b";

    private static final String MAX_SUFFIX= "_max";
    private static final String MIN_SUFFIX= "_min";
    private static final String SUBSTRACTION_SUFFIX= "_subs";
    private static final String MULTIPLICATION_SUFFIX= "_mult";
    private static final String DIVISION_SUFFIX= "_div";
    private static final String PRIORITY_SUFFIX= "_priority_constant";
    private static final String NORMALISED_UTILITY_FUNCTION_SUFFIX= "_nud";

    private static final String TOTAL_PRIORITY = "total_" + PRIORITY_SUFFIX;

    private CpFactory cpFactory;
    private ConstantService constantService;
    private ConstraintService constraintService;


    @Override
    public void createDeltaFunction(ConstraintProblem cp) {
        DeltaUtility deltaUtility = createDeltaUtility();
        cp.setDeltaUtility(deltaUtility);

        //Creation of the total weight constant
        log.debug("DeltaFunctionDerivator - createDeltaFunction - Creating Priority Constant");
        double total = computeTotalPriorityWeight(cp.getGoals());

        Constant totalPrioroty = constantService.createDoubleConstant(total, TOTAL_PRIORITY);
        cp.getConstants().add(totalPrioroty);

        for (Goal goal : cp.getGoals()) {
            createNormalisedUtilityDimension(cp, goal, totalPrioroty);
        }

        Diagnostic diagnostic = Diagnostician.INSTANCE.validate(deltaUtility);
        log.debug("DeltaFunctionDerivator - createDeltaFunction - Ended " + diagnostic);
    }

    private DeltaUtility createDeltaUtility() {
        DeltaUtility deltaUtility = cpFactory.createDeltaUtility();
        deltaUtility.setId(DELTA_UTILITY_NAME);

        //Adding the related solutions as parameters. They have to be defined by someone else
        log.debug("DeltaFunctionDerivator - createDeltaFunction - Creating parameters");
        deltaUtility.getSolutions().add(createParameter(SOLUTION_A_NAME));
        deltaUtility.getSolutions().add(createParameter(SOLUTION_B_NAME));
        deltaUtility.setOperator(OperatorEnum.PLUS);
        return deltaUtility;
    }

    private Parameter createParameter(String parameterName) {
        Parameter parameter = cpFactory.createParameter();
        parameter.setName(parameterName);
        return parameter;
    }


    private double computeTotalPriorityWeight(EList<Goal> goals) {
        double total = 0;

        for (Goal goal : goals) {
            total += goal.getPriority();
        }

        return total;
    }

    private void createNormalisedUtilityDimension(ConstraintProblem cp, Goal goal, Constant totalPriorityWeight) {
        DeltaUtility utility = cp.getDeltaUtility();
        Parameter parameterSolutionA = utility.getSolutions().get(0);
        Parameter parameterSolutionB = utility.getSolutions().get(1);

        NormalisedUtilityDimension nud = cpFactory.createNormalisedUtilityDimension();
        nud.setGoal(goal);
        nud.getSolutions().add(parameterSolutionA);
        nud.getSolutions().add(parameterSolutionB);
        nud.setOperator(OperatorEnum.DIV);

        String goalExpressionID = goal.getExpression().getId();

        log.debug("DeltaFunctionDerivator - createNormalisedUtilityDimension - Creating NUD for " + goalExpressionID);

        String nudId = goalExpressionID + NORMALISED_UTILITY_FUNCTION_SUFFIX;

        nud.setId(nudId);

        cp.getAuxExpressions().add(nud);

        //Goal's configurations

        String configAId = goalExpressionID + CONFIGURATION_A_SUFFIX;
        String configBId = goalExpressionID + CONFIGURATION_B_SUFFIX;

        ConfigurationUpperware configA = createConfigurationUpperware(configAId, parameterSolutionA, goal);
        cp.getAuxExpressions().add(configA);

        ConfigurationUpperware configB = createConfigurationUpperware(configBId, parameterSolutionB, goal);
        cp.getAuxExpressions().add(configB);

        //Max and Min
        ComposedExpression maxAB = constraintService.createComposedExpression(OperatorEnum.MAX, configAId + "_" + configBId + MAX_SUFFIX, configA, configB);
        cp.getAuxExpressions().add(maxAB);

        ComposedExpression minAB = constraintService.createComposedExpression(OperatorEnum.MIN, configAId + "_" + configBId + MIN_SUFFIX, configA, configB);
        cp.getAuxExpressions().add(minAB);

        ComposedExpression subsAB = constraintService.createComposedExpression(OperatorEnum.MINUS, configAId + "_" + configBId + SUBSTRACTION_SUFFIX, maxAB, minAB);
        cp.getAuxExpressions().add(subsAB);


        //NUD B.x/(max(A,B)-min(A,B))

        nud.getExpressions().add(configB);
        nud.getExpressions().add(subsAB);

        //Priority
        Constant priority = constantService.createDoubleConstant(goal.getPriority(), goalExpressionID + PRIORITY_SUFFIX);
        cp.getConstants().add(priority);

        //NUD*(PRIORITY/total)

        ComposedExpression div = constraintService.createComposedExpression(OperatorEnum.DIV, nudId + DIVISION_SUFFIX, priority, totalPriorityWeight);
        cp.getAuxExpressions().add(div);

        ComposedExpression mult = constraintService.createComposedExpression(OperatorEnum.TIMES, nudId + MULTIPLICATION_SUFFIX, div, nud);
        cp.getAuxExpressions().add(mult);

        utility.getExpressions().add(mult);
    }

    private ConfigurationUpperware createConfigurationUpperware(String id, Parameter parameterSolution, Goal goal) {
        ConfigurationUpperware configurationUpperware = cpFactory.createConfigurationUpperware();
        configurationUpperware.setId(id);
        configurationUpperware.setSolution(parameterSolution);
        configurationUpperware.setGoal(goal);
        return configurationUpperware;
    }

}
