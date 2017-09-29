import com.google.common.collect.Lists
import eu.melodic.upperware.utilitygenerator.UtilityFunctionEvaluator
import eu.melodic.upperware.utilitygenerator.UtilityFunctionEvaluatorExample
import eu.melodic.upperware.utilitygenerator.model.Metric
import eu.melodic.upperware.utilitygenerator.model.MetricType
import eu.melodic.upperware.utilitygenerator.model.VirtualMachine

import spock.lang.Specification

class SimpleTest extends Specification{

  VirtualMachine vm1
  VirtualMachine vm2
  Collection<VirtualMachine> initialConfiguration
  Collection<VirtualMachine> newConfiguration
  UtilityFunctionEvaluator evaluator
  Map<MetricType, Metric> metrics

  def setup(){
    vm1 = new VirtualMachine("mBig", 3, 2)
    vm2 = new VirtualMachine("mXXL", 10, 0)
    initialConfiguration = Lists.newArrayList(vm1, vm2)
    metrics = new HashMap<>()
    metrics.put(MetricType.MAX_RESPONSE_TIME, new Metric(MetricType.MAX_RESPONSE_TIME, 30))
    metrics.put(MetricType.NOM_RESPONSE_TIME, new Metric(MetricType.NOM_RESPONSE_TIME, 20))
    metrics.put(MetricType.COST_WEIGHT, new Metric(MetricType.COST_WEIGHT, 0.5))
  }

  def "avg response time=3, no changes"(){
    setup:
    metrics.put(MetricType.AVG_RESPONSE_TIME, new Metric(MetricType.AVG_RESPONSE_TIME, 3))
    evaluator = new UtilityFunctionEvaluatorExample(initialConfiguration, metrics)

    when:
    double result = evaluator.evaluate(initialConfiguration)

    then:
    System.out.println("result = " + result)
  }

  def "avg response time=3, more machines"(){
    setup:
    metrics.put(MetricType.AVG_RESPONSE_TIME, new Metric(MetricType.AVG_RESPONSE_TIME, 3))
    evaluator = new UtilityFunctionEvaluatorExample(initialConfiguration, metrics)

    VirtualMachine vm1_1 = new VirtualMachine("mBig", 3, 3)
    VirtualMachine vm2_2 = new VirtualMachine("mXXL", 10, 0)
    newConfiguration = Lists.newArrayList(vm1_1, vm2_2)

    when:
    double result = evaluator.evaluate(newConfiguration)

    then:
    System.out.println("result = " + result)
  }

  def "avg response time=3, less machines"(){
    setup:
    metrics.put(MetricType.AVG_RESPONSE_TIME, new Metric(MetricType.AVG_RESPONSE_TIME, 3))
    evaluator = new UtilityFunctionEvaluatorExample(initialConfiguration, metrics)

    VirtualMachine vm1_1 = new VirtualMachine("mBig", 3, 1)
    VirtualMachine vm2_2 = new VirtualMachine("mXXL", 10, 0)
    newConfiguration = Lists.newArrayList(vm1_1, vm2_2)

    when:
    double result = evaluator.evaluate(newConfiguration)

    then:
    System.out.println("result = " + result)
  }
}
