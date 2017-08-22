/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 * <p>
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 * <p>
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.profiler.generator.function.creators.impl;

import eu.paasage.upperware.metamodel.application.ApplicationComponent;
import eu.paasage.upperware.metamodel.application.ComponentMetricRelationship;
import eu.paasage.upperware.metamodel.application.PaaSageGoal;
import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.metamodel.types.BasicTypeEnum;
import eu.paasage.upperware.profiler.cp.generator.model.tools.CPModelTool;
import eu.paasage.upperware.profiler.generator.db.IDatabaseProxy;
import eu.paasage.upperware.profiler.generator.function.creators.FunctionCreator;
import eu.paasage.upperware.profiler.generator.service.camel.ConstraintService;
import eu.paasage.upperware.profiler.generator.service.camel.ConstantService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ResponseTimeFunctionCreator implements FunctionCreator {

    private static final String MINUS_ONE = "minus_one";

    private IDatabaseProxy database;
    private ConstantService constantService;
    private ConstraintService constraintService;

    @Override
    public String getName() {
        return "ResponseTime";
    }

    public void createFunction(ConstraintProblem cp, PaaSageGoal goal) {

        ComposedExpression ce = constraintService.createComposedExpression(OperatorEnum.PLUS);

        cp.getAuxExpressions().add(ce);

        Constant minusOneConstant = constantService.searchOrCreateConstantByValue(cp.getConstants(), -1, MINUS_ONE);

        for (ComponentMetricRelationship cmr : goal.getApplicationComponent()) {
            ApplicationComponent apc = cmr.getComponent();

            List<Variable> vars = CPModelTool.getVariablesRelatedToAppComponent(apc, cp);

            for (Variable var : vars) {
                ComposedExpression aux = constraintService.createComposedExpression(OperatorEnum.TIMES);
                aux.getExpressions().add(minusOneConstant);

                if (cmr.getMetricId() != null) {
                    String metricId = CPModelTool.getMetricId(cmr.getMetricId(), var.getVmId(), var.getProviderId());
                    MetricVariable metric = CPModelTool.createMetricVariable(metricId, BasicTypeEnum.DOUBLE, cp);
                    cp.getMetricVariables().add(metric);

                    //TODO COMPUTE THE METRIC VALUE

                    aux.getExpressions().add(metric);
                }

                aux.getExpressions().add(var);

                cp.getAuxExpressions().add(aux);

                ce.getExpressions().add(aux);

                //TODO WHAT TO DO WITH VARIABLES WITHOUT METRIC??
            }

        }

        Goal goalCP = CPModelTool.createGoal(GoalOperatorEnum.MIN, goal.getId(), ce, 0); //0 is the default priority as here we are not using an optimisation requirement for the creation of the goal

        cp.getGoals().add(goalCP);


    }
}
