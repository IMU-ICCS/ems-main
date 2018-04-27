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
import eu.melodic.upperware.utilitygenerator.evaluator.*;
import eu.melodic.upperware.utilitygenerator.model.*;
import eu.melodic.upperware.utilitygenerator.properties.UtilityGeneratorProperties;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class UtilityGeneratorApplication {


    private UtilityFunctionEvaluator utilityFunctionEvaluator;

    public UtilityGeneratorApplication(List<VariableDTO> variables, List<MetricDTO> metrics, UtilityGeneratorProperties properties, UtilityFunctionType useCase,
            NodeCandidates nodeCandidates) {
        this(variables, metrics, properties, null, useCase, nodeCandidates);
    }

    public UtilityGeneratorApplication(List<VariableDTO> variables, List<MetricDTO> metrics, UtilityGeneratorProperties properties, List<Var> deployedSolution,
            UtilityFunctionType useCase, NodeCandidates nodeCandidates) {
        log.info("Creating of Utility Generator");
        this.utilityFunctionEvaluator = createUtilityEvaluator(variables, metrics, properties, deployedSolution, useCase, nodeCandidates);
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


    private UtilityFunctionEvaluator createUtilityEvaluator(List<VariableDTO> variables, List<MetricDTO> metrics,
            UtilityGeneratorProperties properties, List<Var> deployedSolution, UtilityFunctionType useCase, NodeCandidates nodeCandidates) {

        switch (useCase) {
            case FCR:
                log.info("Creating utility function for FCR");
                return new UtilityFunctionEvaluatorFCR(variables, properties, deployedSolution, nodeCandidates, metrics);
            case CETRAFFIC:
                log.info("Creating utility function for CETraffic");
                return new UtilityFunctionEvaluatorCETraffic(variables, properties, deployedSolution, nodeCandidates, metrics);
            case CAS:
                log.info("Creating utility function for CAS");
                return new UtilityFunctionEvaluatorCAS(variables, properties, deployedSolution, nodeCandidates);
            case MIN_CORES:
                log.info("Creating utility function which minimise total number of cores");
                return new UtilityFunctionEvaluatorMinCores(variables, properties, deployedSolution, nodeCandidates);
            case GENOM:
                log.info("Creating utility function for Genom - the same as for CETraffic");
                return new UtilityFunctionEvaluatorCETraffic(variables, properties, deployedSolution, nodeCandidates, metrics);
            case COST:
            case DEFAULT:
            default:
                log.info("Creating default utility function which minimise total cost");
                return new UtilityFunctionEvaluatorCost(variables, properties, deployedSolution, nodeCandidates);
        }
    }



    /* ------------------------------ only for tests - to delete later ---------------------------------------------*/

    public UtilityGeneratorApplication(List<MetricDTO> metrics,
            Collection<ConfigurationElement> actConfiguration, boolean isReconfig,
            UtilityFunctionType useCase, CostUtilityFunction costUtilityFunction) {

        createUtilityEvaluator(metrics, actConfiguration, isReconfig, useCase, costUtilityFunction);
    }

    public UtilityGeneratorApplication(List<MetricDTO> metrics,
            Collection<ConfigurationElement> actConfiguration, boolean isReconfig, UtilityFunctionType useCase) {

        createUtilityEvaluator(metrics, actConfiguration, isReconfig, useCase, new CostUtilityFunctionExample(isReconfig));
    }

    public double evaluateToTest(Collection<ConfigurationElement> configuration) {
        return this.utilityFunctionEvaluator.evaluate(configuration);
    }

    private void createUtilityEvaluator(List<MetricDTO> metrics, Collection<ConfigurationElement> actConfiguration,
            boolean isReconfig, UtilityFunctionType useCase, CostUtilityFunction costUtilityFunction) {

        switch (useCase) {

            case FCR:
                this.utilityFunctionEvaluator =
                        new UtilityFunctionEvaluatorFCR(actConfiguration, isReconfig, costUtilityFunction, metrics);
                break;

            case CETRAFFIC:
                this.utilityFunctionEvaluator =
                        new UtilityFunctionEvaluatorCETraffic(actConfiguration, isReconfig, costUtilityFunction, metrics);
                break;

            case CAS:
                this.utilityFunctionEvaluator =
                        new UtilityFunctionEvaluatorCAS(actConfiguration, isReconfig, costUtilityFunction);
                break;

            case MIN_CORES:
                this.utilityFunctionEvaluator =
                        new UtilityFunctionEvaluatorMinCores(actConfiguration, isReconfig);
                break;

            default:
                this.utilityFunctionEvaluator =
                        new UtilityFunctionEvaluatorCost(actConfiguration, isReconfig);
                break;
        }
    }


}
