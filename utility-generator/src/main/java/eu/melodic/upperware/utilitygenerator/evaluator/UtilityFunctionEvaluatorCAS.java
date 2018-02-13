/* * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.evaluator;

import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunction;
import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunctionFraction;
import eu.melodic.upperware.utilitygenerator.model.ConfigurationElement;
import eu.melodic.upperware.utilitygenerator.model.Var;
import eu.melodic.upperware.utilitygenerator.model.VariableDTO;

import java.util.Collection;
import java.util.List;

public class UtilityFunctionEvaluatorCAS extends UtilityFunctionEvaluator {

    private CostUtilityFunction costUtilityFunction;


    public UtilityFunctionEvaluatorCAS(List<VariableDTO> variables, List<Var> deployedSolution, NodeCandidates nodeCandidates) {
        this(variables, deployedSolution, nodeCandidates, new CostUtilityFunctionFraction());
    }

    public UtilityFunctionEvaluatorCAS(List<VariableDTO> variables, List<Var> deployedSolution, NodeCandidates nodeCandidates, CostUtilityFunction costUtilityFunction) {
        super(variables, deployedSolution, nodeCandidates);
        this.costUtilityFunction = costUtilityFunction;
    }

    @Override
    public double evaluate(Collection<ConfigurationElement> newConfiguration) {

        return costUtilityFunction.evaluateCostUtilityFunction(actConfiguration, newConfiguration);
    }

    /* for tests */

    public UtilityFunctionEvaluatorCAS(Collection<ConfigurationElement> actConfiguration, boolean isReconfig,
            CostUtilityFunction costUtilityFunction) {

        super(actConfiguration, isReconfig);
        this.costUtilityFunction = costUtilityFunction;
    }

}
