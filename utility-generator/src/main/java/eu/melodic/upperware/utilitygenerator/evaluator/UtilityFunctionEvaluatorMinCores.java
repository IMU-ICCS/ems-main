/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.evaluator;

import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.utilitygenerator.model.ConfigurationElement;
import eu.melodic.upperware.utilitygenerator.model.Var;
import eu.melodic.upperware.utilitygenerator.model.VariableDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;

@Slf4j
public class UtilityFunctionEvaluatorMinCores extends UtilityFunctionEvaluator{

    public UtilityFunctionEvaluatorMinCores(List<VariableDTO> variables, List<Var> deployedSolution, NodeCandidates nodeCandidates) {
        super(variables, deployedSolution, nodeCandidates);
    }

    public UtilityFunctionEvaluatorMinCores(Collection<ConfigurationElement> actConfiguration, boolean isReconfig) {
        super(actConfiguration, isReconfig);
    }

    @Override
    public double evaluate(Collection<ConfigurationElement> newConfiguration) {
        double totalNumberOfCores = countTotalNumberOfCores(newConfiguration);
        log.debug("Total number of cores is {}", totalNumberOfCores);
        double result = 1/totalNumberOfCores;
        return result;
    }

    private double countTotalNumberOfCores(Collection<ConfigurationElement> newConfiguration){
        return newConfiguration.stream()
                .mapToDouble(ConfigurationElement::getTotalNumberOfCores).sum();
    }
}
