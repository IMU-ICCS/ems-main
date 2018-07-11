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
import eu.melodic.upperware.utilitygenerator.model.DTO.MetricDTO;
import eu.melodic.upperware.utilitygenerator.model.DTO.VariableDTO;
import eu.melodic.upperware.utilitygenerator.model.function.Element;
import eu.melodic.upperware.utilitygenerator.model.function.IntElement;
import eu.melodic.upperware.utilitygenerator.model.function.RealElement;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class UtilityGeneratorApplication {


    private UtilityFunctionEvaluator utilityFunctionEvaluator;

    public UtilityGeneratorApplication(String cdoPath, String path, List<VariableDTO> variables, List<MetricDTO> metrics, NodeCandidates nodeCandidates) {
        this(cdoPath, path, variables, metrics, null, nodeCandidates);
    }

    public UtilityGeneratorApplication(String cdoPath, String path, List<VariableDTO> variables, List<MetricDTO> metrics, List<Element> deployedSolution, NodeCandidates nodeCandidates) {
        log.info("Creating of Utility Generator");
        utilityFunctionEvaluator = new UtilityFunctionEvaluator(cdoPath, path, variables, metrics, deployedSolution, nodeCandidates);
    }

    public double evaluate(Collection<IntElement> newConfigurationInt, Collection<RealElement> newConfigurationReal) {
        return this.utilityFunctionEvaluator.evaluate(newConfigurationInt, newConfigurationReal);
    }

    public double evaluate(Collection<IntElement> newConfigurationInt) {
        return evaluate(newConfigurationInt, new ArrayList<>());
    }

    public void printConfigurationWithMaximumUtility() {
        this.utilityFunctionEvaluator.printConfigurationWithMaximumUtility();
    }

    public double getUtilityForCurrentDeployedSolution() {
        return this.utilityFunctionEvaluator.evaluateActualSolution();
    }

}
