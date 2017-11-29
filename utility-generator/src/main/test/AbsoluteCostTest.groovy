import com.google.common.collect.Lists
import eu.melodic.upperware.utilitygenerator.CostEvaluator
import eu.melodic.upperware.utilitygenerator.CostEvaluatorWithAbsoluteCost
import eu.melodic.upperware.utilitygenerator.UtilityFunctionEvaluator
import eu.melodic.upperware.utilitygenerator.UtilityFunctionEvaluatorExample
import eu.melodic.upperware.utilitygenerator.model.Metric
import eu.melodic.upperware.utilitygenerator.model.MetricType
import eu.melodic.upperware.utilitygenerator.model.VirtualMachine

import spock.lang.Specification

class AbsoluteCostTest extends Specification{

  VirtualMachine vm1_1inst
  VirtualMachine vm1_2inst
  VirtualMachine vm1_3inst
  VirtualMachine vm2
  Collection<VirtualMachine> initialConfiguration
  Collection<VirtualMachine> newConfigurationWithLessMachines
  Collection<VirtualMachine> newConfigurationWithMoreMachines
  UtilityFunctionEvaluator evaluator
  Map<MetricType, Metric> metrics

  int maxVMs
  CostEvaluator costEvaluator

  def setup(){
    vm1_1inst = new VirtualMachine("mBig", 3, 1)
    vm1_1inst.setCount(1)
    vm1_2inst = new VirtualMachine("mBig", 3, 1)
    vm1_2inst.setCount(2)
    vm1_3inst = new VirtualMachine("mBig", 3, 1)
    vm1_3inst.setCount(3)

    vm2 = new VirtualMachine("mXXL", 10, 1)
    vm2.setCount(0)
    initialConfiguration = Lists.newArrayList(vm1_2inst, vm2)

    metrics = new HashMap<>()
    metrics.put(MetricType.MAX_RESPONSE_TIME, new Metric(MetricType.MAX_RESPONSE_TIME, 30))
    metrics.put(MetricType.NOM_RESPONSE_TIME, new Metric(MetricType.NOM_RESPONSE_TIME, 20))
    metrics.put(MetricType.COST_WEIGHT, new Metric(MetricType.COST_WEIGHT, 0.5))

    newConfigurationWithLessMachines = Lists.newArrayList(vm1_1inst, vm2)
    newConfigurationWithMoreMachines = Lists.newArrayList(vm1_3inst, vm2)

    maxVMs = 10
    costEvaluator = new CostEvaluatorWithAbsoluteCost(maxVMs)
  }

  def "avg response time=3, more machines"(){
    setup:
    metrics.put(MetricType.AVG_RESPONSE_TIME, new Metric(MetricType.AVG_RESPONSE_TIME, 3))
    evaluator = new UtilityFunctionEvaluatorExample(metrics, true, initialConfiguration, costEvaluator)



    when:
    double result = evaluator.evaluate(newConfigurationWithMoreMachines)

    then:
    //Out[40]= 0.33334
    result >= 0.28 && result <= 0.38
  }

  def "avg response time=3, less machines"(){
    setup:
    metrics.put(MetricType.AVG_RESPONSE_TIME, new Metric(MetricType.AVG_RESPONSE_TIME, 3))
    evaluator = new UtilityFunctionEvaluatorExample(metrics, true, initialConfiguration, costEvaluator)
    when:
    double result = evaluator.evaluate(newConfigurationWithLessMachines)

    then:
    //0.503253
    result >= 0.45 && result <= 0.55
  }

  def "avg response time=3, no changes"(){
    setup:
    metrics.put(MetricType.AVG_RESPONSE_TIME, new Metric(MetricType.AVG_RESPONSE_TIME, 3))
    evaluator = new UtilityFunctionEvaluatorExample(metrics, true, initialConfiguration, costEvaluator)

    when:
    double result = evaluator.evaluate(initialConfiguration)

    then:
    //0.500068
    result >= 0.45 && result <= 0.55
  }

  def "avg response time=25, more machines"(){
    setup:
    metrics.put(MetricType.AVG_RESPONSE_TIME, new Metric(MetricType.AVG_RESPONSE_TIME, 25))
    evaluator = new UtilityFunctionEvaluatorExample(metrics, true, initialConfiguration, costEvaluator)

    when:
    double result = evaluator.evaluate(newConfigurationWithMoreMachines)

    then:
    //0.677073
    result >= 0.62 && result <= 0.72
  }

  def "avg response time=25, less machines"(){
    setup:
    metrics.put(MetricType.AVG_RESPONSE_TIME, new Metric(MetricType.AVG_RESPONSE_TIME, 25))
    evaluator = new UtilityFunctionEvaluatorExample(metrics, true, initialConfiguration, costEvaluator)

    when:
    double result = evaluator.evaluate(newConfigurationWithLessMachines)

    then:
    //0.5
    result >= 0.45 && result <= 0.55

  }
  def "avg response time=25, no changes"(){
    setup:
    metrics.put(MetricType.AVG_RESPONSE_TIME, new Metric(MetricType.AVG_RESPONSE_TIME, 25))
    evaluator = new UtilityFunctionEvaluatorExample(metrics, true, initialConfiguration, costEvaluator)

    when:
    double result = evaluator.evaluate(initialConfiguration)

    then:
    //0.837175
    result >= 0.77 && result <= 0.87
  }

  def "avg response time=28, more machines"(){
    setup:
    metrics.put(MetricType.AVG_RESPONSE_TIME, new Metric(MetricType.AVG_RESPONSE_TIME, 28))
    evaluator = new UtilityFunctionEvaluatorExample(metrics, true, initialConfiguration, costEvaluator)

    when:
    double result = evaluator.evaluate(newConfigurationWithMoreMachines)

    then:
    //0.785278
    result >= 0.73 && result <= 0.83
  }

  def "avg response time=28, less machines"(){
    setup:
    metrics.put(MetricType.AVG_RESPONSE_TIME, new Metric(MetricType.AVG_RESPONSE_TIME, 28))
    evaluator = new UtilityFunctionEvaluatorExample(metrics, true, initialConfiguration, costEvaluator)

    when:
    double result = evaluator.evaluate(newConfigurationWithLessMachines)

    then:
    //0.5
    result >= 0.45 && result <= 0.55

  }
  def "avg response time=28, no changes"(){
    setup:
    metrics.put(MetricType.AVG_RESPONSE_TIME, new Metric(MetricType.AVG_RESPONSE_TIME, 28))
    evaluator = new UtilityFunctionEvaluatorExample(metrics, true, initialConfiguration, costEvaluator)

    when:
    double result = evaluator.evaluate(initialConfiguration)

    then:
    //0.567346
    System.out.println(result)

    result >= 0.51 && result <= 0.61
  }

  def "avg response time=25, zeta = 0.05, more machines"(){
    setup:
    metrics.put(MetricType.AVG_RESPONSE_TIME, new Metric(MetricType.AVG_RESPONSE_TIME, 25))
    metrics.put(MetricType.COST_WEIGHT, new Metric(MetricType.COST_WEIGHT, 0.05))
    evaluator = new UtilityFunctionEvaluatorExample(metrics, true, initialConfiguration, costEvaluator)

    when:
    double result = evaluator.evaluate(newConfigurationWithMoreMachines)

    then:
    //0.686438
    System.out.println(result)

    result >= 0.63 && result <= 0.73
  }

  def "avg response time=25, zeta = 0.05, less machines"(){
    setup:
    metrics.put(MetricType.AVG_RESPONSE_TIME, new Metric(MetricType.AVG_RESPONSE_TIME, 25))
    metrics.put(MetricType.COST_WEIGHT, new Metric(MetricType.COST_WEIGHT, 0.05))
    evaluator = new UtilityFunctionEvaluatorExample(metrics, true, initialConfiguration, costEvaluator)

    when:
    double result = evaluator.evaluate(newConfigurationWithLessMachines)

    then:
    //0.05
    System.out.println(result)

    result >= 0.0 && result <= 0.1

  }
  def "avg response time=25, zeta = 0.05, no changes"(){
    setup:
    metrics.put(MetricType.AVG_RESPONSE_TIME, new Metric(MetricType.AVG_RESPONSE_TIME, 25))
    metrics.put(MetricType.COST_WEIGHT, new Metric(MetricType.COST_WEIGHT, 0.05))
    evaluator = new UtilityFunctionEvaluatorExample(metrics, true, initialConfiguration, costEvaluator)

    when:
    double result = evaluator.evaluate(initialConfiguration)

    then:
    //0.690632
    System.out.println(result)
    result >= 0.64 && result <= 0.74
  }


}

