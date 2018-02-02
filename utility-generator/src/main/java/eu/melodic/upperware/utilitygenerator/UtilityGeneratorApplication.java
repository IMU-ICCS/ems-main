/* * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator;

import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunction;
import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunctionExample;
import eu.melodic.upperware.utilitygenerator.evaluator.UtilityFunctionEvaluator;
import eu.melodic.upperware.utilitygenerator.evaluator.UtilityFunctionEvaluatorCAS;
import eu.melodic.upperware.utilitygenerator.evaluator.UtilityFunctionEvaluatorCETraffic;
import eu.melodic.upperware.utilitygenerator.evaluator.UtilityFunctionEvaluatorFCR;
import eu.melodic.upperware.utilitygenerator.model.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
public class UtilityGeneratorApplication {


    private UtilityFunctionEvaluator utilityFunctionEvaluator;

    //todo: add last solution from ConstraintProblem
    public UtilityGeneratorApplication(List<VariableDTO> variables, Map<MetricType, MetricDTO[]> metrics,
                                       UtilityFunctionType useCase, NodeCandidates nodeCandidates) {
        log.info("Creating of Utility Generator");

        this.utilityFunctionEvaluator = createUtilityEvaluator(variables, metrics, useCase, nodeCandidates);
    }

    //todo - canonical model for real variables
    public double evaluate(Collection<IntVar> newConfigurationInt, Collection<RealVar> newConfigurationReal) {
        return this.utilityFunctionEvaluator.evaluate(newConfigurationInt, newConfigurationReal);
    }

    public void printConfigurationWithMaximumUtility() {
        this.utilityFunctionEvaluator.printConfigurationWithMaximumUtility();
    }

    public double getUtilityForCurrentDeployedSolution() {
        return this.utilityFunctionEvaluator.evaluateActualSolution();
    }


    private UtilityFunctionEvaluator createUtilityEvaluator(List<VariableDTO> variables, Map<MetricType,
            MetricDTO[]> metrics, UtilityFunctionType useCase, NodeCandidates nodeCandidates) {

        switch (useCase) {
            case FCR:
                log.info("Creating utility function for FCR");
                return new UtilityFunctionEvaluatorFCR(variables, nodeCandidates, metrics);
            case CETRAFFIC:
                log.info("Creating utility function for CETraffic");
                return new UtilityFunctionEvaluatorCETraffic(variables, nodeCandidates);
            default: //CAS
                log.info("Creating utility function for CAS");
                return new UtilityFunctionEvaluatorCAS(variables, nodeCandidates);
        }
    }



    /* ------------------------------ only for tests - to delete later ---------------------------------------------*/

    public UtilityGeneratorApplication(Map<MetricType, MetricDTO[]> metrics,
                                       Collection<Component> actConfiguration, boolean isReconfig,
                                       UtilityFunctionType useCase, CostUtilityFunction costUtilityFunction) {

        createUtilityEvaluator(metrics, actConfiguration, isReconfig, useCase, costUtilityFunction);
    }

    public UtilityGeneratorApplication(Map<MetricType, MetricDTO[]> metrics,
                                       Collection<Component> actConfiguration, boolean isReconfig, UtilityFunctionType useCase) {

        createUtilityEvaluator(metrics, actConfiguration, isReconfig, useCase, new CostUtilityFunctionExample(isReconfig));
    }

    public double evaluate(int cardinality) {
        return this.utilityFunctionEvaluator.evaluate(new ArrayList<>(), new ArrayList<>());

    }

    public double evaluate(Collection<IntVar> newConfigurationInt) {
        return this.utilityFunctionEvaluator.evaluate(newConfigurationInt, new ArrayList<>());
    }

    private void createUtilityEvaluator(Map<MetricType, MetricDTO[]> metrics, Collection<Component> actConfiguration,
                                        boolean isReconfig, UtilityFunctionType useCase, CostUtilityFunction costUtilityFunction) {

        switch (useCase) {

            case FCR:
                this.utilityFunctionEvaluator =
                        new UtilityFunctionEvaluatorFCR(metrics, actConfiguration, isReconfig, costUtilityFunction);
                break;

            case CETRAFFIC:
                this.utilityFunctionEvaluator =
                        new UtilityFunctionEvaluatorCETraffic(actConfiguration, isReconfig, costUtilityFunction);
                break;

            case CAS:
                this.utilityFunctionEvaluator =
                        new UtilityFunctionEvaluatorCAS(metrics, actConfiguration, isReconfig, costUtilityFunction);
                break;
        }
    }


}
