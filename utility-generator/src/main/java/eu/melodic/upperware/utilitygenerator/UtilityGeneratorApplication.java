package eu.melodic.upperware.utilitygenerator;

import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunction;
import eu.melodic.upperware.utilitygenerator.evaluator.UtilityFunctionEvaluator;
import eu.melodic.upperware.utilitygenerator.evaluator.UtilityFunctionEvaluatorCAS;
import eu.melodic.upperware.utilitygenerator.evaluator.UtilityFunctionEvaluatorCETraffic;
import eu.melodic.upperware.utilitygenerator.evaluator.UtilityFunctionEvaluatorFCR;
import eu.melodic.upperware.utilitygenerator.model.Component;
import eu.melodic.upperware.utilitygenerator.model.Metric;
import eu.melodic.upperware.utilitygenerator.model.MetricType;
import solver.variables.IntVar;

import java.util.Collection;
import java.util.Map;

public class UtilityGeneratorApplication {

  private UtilityFunctionEvaluator utilityFunctionEvaluator;

  public UtilityGeneratorApplication(Map<MetricType, Metric[]> metrics,
      Collection<Component> actConfiguration,
      boolean isReconfig, UtilityFunctionType useCase){

    switch (useCase){

      case FCR:
        this.utilityFunctionEvaluator =
          new UtilityFunctionEvaluatorFCR(metrics, actConfiguration, isReconfig);
        break;

      case CE_TRAFFIC:
        this.utilityFunctionEvaluator =
          new UtilityFunctionEvaluatorCETraffic(actConfiguration, isReconfig);
        break;

      case CAS:
        this.utilityFunctionEvaluator =
          new UtilityFunctionEvaluatorCAS(metrics, actConfiguration, isReconfig);
        break;
    }

  }

  public UtilityGeneratorApplication(Map<MetricType, Metric[]> metrics,
      Collection<Component> actConfiguration, boolean isReconfig,
      UtilityFunctionType useCase, CostUtilityFunction costUtilityFunction){

    switch (useCase){

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

  public double evaluate(IntVar[] newConfiguration){
    return this.utilityFunctionEvaluator.evaluate(newConfiguration, 1);
  }

  public double evaluate(int cardinality) {
    return this.utilityFunctionEvaluator.evaluate(new IntVar[]{}, cardinality);

  }

}
