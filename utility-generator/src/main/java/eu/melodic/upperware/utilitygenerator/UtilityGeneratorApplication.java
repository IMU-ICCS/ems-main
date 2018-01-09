/* * Copyright (C) 2017 7bulls.com
*
* This Source Code Form is subject to the terms of the
* Mozilla Public License, v. 2.0. If a copy of the MPL
* was not distributed with this file, You can obtain one at
* http://mozilla.org/MPL/2.0/.
*/

package eu.melodic.upperware.utilitygenerator;

import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunction;
import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunctionExample;
import eu.melodic.upperware.utilitygenerator.evaluator.UtilityFunctionEvaluator;
import eu.melodic.upperware.utilitygenerator.evaluator.UtilityFunctionEvaluatorCAS;
import eu.melodic.upperware.utilitygenerator.evaluator.UtilityFunctionEvaluatorCETraffic;
import eu.melodic.upperware.utilitygenerator.evaluator.UtilityFunctionEvaluatorFCR;
import eu.melodic.upperware.utilitygenerator.model.Component;
import eu.melodic.upperware.utilitygenerator.model.Metric;
import eu.melodic.upperware.utilitygenerator.model.MetricType;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import solver.variables.IntVar;
import solver.variables.RealVar;

import java.util.Collection;
import java.util.Map;

public class UtilityGeneratorApplication {


    private UtilityFunctionEvaluator utilityFunctionEvaluator;


    //todo: constructor only with ConstraintProblem
    public UtilityGeneratorApplication(ConstraintProblem cp, Map<MetricType, Metric[]> metrics,
            UtilityFunctionType useCase) {
        this.utilityFunctionEvaluator = createUtilityEvaluator(cp, metrics, useCase);
    }


    //todo - better objects with solution from solver?
    public double evaluate(IntVar[] newConfigurationInt, RealVar[] newConfigurationReal) {
        return this.utilityFunctionEvaluator.evaluate(newConfigurationInt, newConfigurationReal);
    }


    private UtilityFunctionEvaluator createUtilityEvaluator(ConstraintProblem cp, Map<MetricType,
            Metric[]> metrics, UtilityFunctionType useCase) {

        switch (useCase) {
            case FCR:
                return new UtilityFunctionEvaluatorFCR(cp, metrics);
            case CE_TRAFFIC:
                return new UtilityFunctionEvaluatorCETraffic(cp);
            default: //CAS
                return new UtilityFunctionEvaluatorCAS(cp);
        }
    }



  /* ------------------------------ only for tests - to delete later ---------------------------------------------*/

    public UtilityGeneratorApplication(Map<MetricType, Metric[]> metrics,
            Collection<Component> actConfiguration, boolean isReconfig,
            UtilityFunctionType useCase, CostUtilityFunction costUtilityFunction) {

        createUtilityEvaluator(metrics, actConfiguration, isReconfig, useCase, costUtilityFunction);
    }

    public UtilityGeneratorApplication(Map<MetricType, Metric[]> metrics,
            Collection<Component> actConfiguration, boolean isReconfig, UtilityFunctionType useCase) {

        createUtilityEvaluator(metrics, actConfiguration, isReconfig, useCase, new CostUtilityFunctionExample(isReconfig));
    }

    public double evaluate(int cardinality) {
        return this.utilityFunctionEvaluator.evaluate(new IntVar[]{}, new RealVar[]{});

    }

    public double evaluate(IntVar[] newConfigurationInt) {
        return this.utilityFunctionEvaluator.evaluate(newConfigurationInt, new RealVar[]{});
    }


    private void createUtilityEvaluator(Map<MetricType, Metric[]> metrics, Collection<Component> actConfiguration,
            boolean isReconfig, UtilityFunctionType useCase, CostUtilityFunction costUtilityFunction) {

        switch (useCase) {

            case FCR:
                this.utilityFunctionEvaluator =
                        new UtilityFunctionEvaluatorFCR(metrics, actConfiguration, isReconfig, costUtilityFunction);
                break;

            case CE_TRAFFIC:
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
