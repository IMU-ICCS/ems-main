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
import eu.melodic.upperware.utilitygenerator.utility_function.utility_templates_provider.TemplateProvider;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import eu.paasage.upperware.security.authapi.token.JWTService;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

@Slf4j
public class UtilityGeneratorApplication {

    private UtilityFunctionEvaluator utilityFunctionEvaluator;

    public UtilityGeneratorApplication(String camelModelFilePath, String cpModelFilePath, boolean readFromFile, NodeCandidates nodeCandidates, UtilityGeneratorProperties properties,
                                       MelodicSecurityProperties melodicSecurityProperties, JWTService jwtService, PenaltyFunctionProperties penaltyFunctionProperties) {
        log.info("Creating of the Utility Generator");
        utilityFunctionEvaluator = new UtilityFunctionEvaluator(camelModelFilePath, cpModelFilePath, readFromFile, nodeCandidates, properties, melodicSecurityProperties, penaltyFunctionProperties, jwtService);
    }

    @SafeVarargs
    public UtilityGeneratorApplication(String camelModelFilePath, String cpModelFilePath, boolean readFromFile, NodeCandidates nodeCandidates, UtilityGeneratorProperties properties,
                                       MelodicSecurityProperties melodicSecurityProperties, JWTService jwtService, PenaltyFunctionProperties penaltyFunctionProperties,
                                       Map.Entry<TemplateProvider.AvailableTemplates, Double>... utilityComponents) {
        log.info("Creating template Utility Generator");
        checkWeightsOfUtilityComponents(utilityComponents);
        utilityFunctionEvaluator =                 new UtilityFunctionEvaluator(camelModelFilePath, cpModelFilePath, readFromFile, nodeCandidates, properties,
                melodicSecurityProperties, penaltyFunctionProperties, jwtService, utilityComponents);
    }

    @SafeVarargs
    public UtilityGeneratorApplication(String cpModelFilePath, NodeCandidates nodeCandidates, Map.Entry<TemplateProvider.AvailableTemplates, Double>... utilityComponents) {
        log.info("Creating template Utility Generator");
        checkWeightsOfUtilityComponents(utilityComponents);
        utilityFunctionEvaluator = new UtilityFunctionEvaluator( cpModelFilePath,  nodeCandidates, utilityComponents);
    }

    public double evaluate(Collection<VariableValueDTO> solution) {
        return this.utilityFunctionEvaluator.evaluate(solution);
    }

    @SafeVarargs
    private final void checkWeightsOfUtilityComponents(Map.Entry<TemplateProvider.AvailableTemplates, Double>... utilityComponents) {
        if (Stream.of(utilityComponents).map(Map.Entry::getValue).reduce(0.0, Double::sum) > 1.0
                || Stream.of(utilityComponents).map(Map.Entry::getValue).anyMatch(weight -> weight < 0)) {
            throw new RuntimeException("Sum of weights must be smaller or equal to 1 and non-negative!");
        }
    }

}
