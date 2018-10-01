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
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;

@Slf4j
public class UtilityGeneratorApplication {

    private UtilityFunctionEvaluator utilityFunctionEvaluator;

    public UtilityGeneratorApplication(String camelModelFilePath, boolean readFromFile, List<VariableDTO> variables, Collection<MetricDTO> metrics, NodeCandidates nodeCandidates) {
        this(camelModelFilePath, readFromFile, variables, metrics, null, nodeCandidates);
    }

    public UtilityGeneratorApplication(String camelModelFilePath, boolean readFromFile, List<VariableDTO> variables, Collection<MetricDTO> metrics, Collection<Element> deployedSolution, NodeCandidates nodeCandidates) {
        log.info("Creating of the Utility Generator");
        utilityFunctionEvaluator = new UtilityFunctionEvaluator(camelModelFilePath, readFromFile, variables, metrics, deployedSolution, nodeCandidates);
    }

    public double evaluate(Collection<Element> solution) {
        return this.utilityFunctionEvaluator.evaluate(solution);
    }

}
