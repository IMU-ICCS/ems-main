/* * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator;

import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.penaltycalculator.PenaltyFunctionProperties;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;
import eu.melodic.upperware.utilitygenerator.evaluator.UtilityFunctionEvaluator;
import eu.melodic.upperware.utilitygenerator.properties.UtilityGeneratorProperties;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import eu.paasage.upperware.security.authapi.token.JWTService;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;

@Slf4j
public class UtilityGeneratorApplication {

    private UtilityFunctionEvaluator utilityFunctionEvaluator;

    public UtilityGeneratorApplication(String camelModelFilePath, String cpModelFilePath, boolean readFromFile, NodeCandidates nodeCandidates, UtilityGeneratorProperties properties,
                                       MelodicSecurityProperties melodicSecurityProperties, JWTService jwtService, PenaltyFunctionProperties penaltyFunctionProperties) {
        log.info("Creating of the Utility Generator");
        utilityFunctionEvaluator = new UtilityFunctionEvaluator(camelModelFilePath, cpModelFilePath, readFromFile, nodeCandidates, properties, melodicSecurityProperties, penaltyFunctionProperties, jwtService);
    }

    public UtilityGeneratorApplication(String cpModelFilePath, NodeCandidates nodeCandidates, TemplateProvider.AvailableTemplates template) {
        log.info("Creating template Utility Generator");
        utilityFunctionEvaluator = new TemplateUtilityEvaluator(cpModelFilePath, nodeCandidates, type);
    }

    public UtilityGeneratorApplication(String cpModelFilePath, NodeCandidates nodeCandidates,
                                       List<TemplateProvider.AvailableTemplates> templates, List<Double> templateWeights) {
        log.info("Creating template Utility Generator");
        utilityFunctionEvaluator = new TemplateUtilityEvaluator(cpModelFilePath, nodeCandidates, type);
    }

    public double evaluate(Collection<VariableValueDTO> solution) {
        return this.utilityFunctionEvaluator.evaluate(solution);
    }

}
