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

import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.upperware.metamodel.application.PaaSageGoal;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.profiler.cp.generator.model.tools.CPModelTool;
import eu.paasage.upperware.profiler.generator.db.IDatabaseProxy;
import eu.paasage.upperware.profiler.generator.function.creators.FunctionCreator;
import eu.paasage.upperware.profiler.generator.service.camel.ConstantService;
import eu.paasage.upperware.profiler.generator.service.camel.ConstraintService;
import eu.paasage.upperware.profiler.price.model.lib.EstimatorsManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.common.util.EList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class CostFunctionCreator implements FunctionCreator {

    public static final String NAME = "Cost";

    private IDatabaseProxy database;
    private ConstantService constantService;
    private ConstraintService constraintService;
    private EstimatorsManager estimatorsManager;

    @Override
    public String getName() {
        return NAME;
    }

    public void createFunction(ConstraintProblem cp, PaaSageGoal goal) {

        EList<Variable> variables = cp.getVariables();
        PaasageConfiguration pc = (PaasageConfiguration) goal.eContainer();
        String appId = pc.getId();

        NumericExpression goalExpression = null;


        if (variables.size() > 1) {
            ComposedExpression ce = constraintService.createComposedExpression(OperatorEnum.PLUS);
            cp.getAuxExpressions().add(ce);

            goalExpression = ce;

            for (Variable v : variables) {
                ce.getExpressions().add(createMultiplication(cp, v, appId));
            }

        } else {
            Variable v = variables.get(0);

            goalExpression = createMultiplication(cp, v, appId);
        }

        Goal goalCP = CPModelTool.createGoal(GoalOperatorEnum.MIN, goal.getId(), goalExpression, 1); //1 is the default priority as here we are not using an optimisation requirement for the creation of the goal

        cp.getGoals().add(goalCP);

    }


    protected ComposedExpression createMultiplication(ConstraintProblem cp, Variable v, String appId) {

        String providerId = v.getProviderId();
//        String vmId = v.getVmId();
//        ProviderModel pm = database.loadPM(appId, providerId, vmId);
        ProviderModel pm = database.loadPM(appId, providerId);
        double rate = estimatorsManager.estimatePrice(pm);

        Constant price = constantService.createDoubleConstant(rate);
        cp.getConstants().add(price);


        ComposedExpression aux = constraintService.createComposedExpression(OperatorEnum.TIMES, v, price);
        cp.getAuxExpressions().add(aux);

        return aux;
    }
}
