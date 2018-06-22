/* * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator;

import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.utilitygenerator.evaluator.UtilityFunctionEvaluator;
import eu.melodic.upperware.utilitygenerator.model.*;
import eu.melodic.upperware.utilitygenerator.properties.UtilityGeneratorProperties;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class UtilityGeneratorApplication {


    private UtilityFunctionEvaluator utilityFunctionEvaluator;

    public UtilityGeneratorApplication(String path, List<VariableDTO> variables, List<MetricDTO> metrics, UtilityGeneratorProperties properties, UtilityFunctionType useCase,
            NodeCandidates nodeCandidates) {
        this(path, variables, metrics, null, nodeCandidates);
    }

    private UtilityGeneratorApplication(String path, List<VariableDTO> variables, List<MetricDTO> metrics, List<Var> deployedSolution, NodeCandidates nodeCandidates) {
        log.info("Creating of Utility Generator");
        utilityFunctionEvaluator = new UtilityFunctionEvaluator(path, variables, metrics, deployedSolution, nodeCandidates);
    }

    public double evaluate(Collection<IntVar> newConfigurationInt, Collection<RealVar> newConfigurationReal) {
        return this.utilityFunctionEvaluator.evaluate(newConfigurationInt, newConfigurationReal);
    }

    public double evaluate(Collection<IntVar> newConfigurationInt) {
        return evaluate(newConfigurationInt, new ArrayList<>());
    }

    public void printConfigurationWithMaximumUtility() {
        this.utilityFunctionEvaluator.printConfigurationWithMaximumUtility();
    }

    public double getUtilityForCurrentDeployedSolution() {
        return this.utilityFunctionEvaluator.evaluateActualSolution();
    }

}
