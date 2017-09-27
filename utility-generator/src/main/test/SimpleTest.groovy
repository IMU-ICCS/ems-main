import com.google.common.collect.Lists
import eu.melodic.upperware.utilitygenerator.UtilityFunctionEvaluator
import eu.melodic.upperware.utilitygenerator.UtilityFunctionEvaluatorExample
import eu.melodic.upperware.utilitygenerator.model.Metric
import eu.melodic.upperware.utilitygenerator.model.VirtualMachine

import spock.lang.Specification

class SimpleTest extends Specification{

  def "example1"(){
    setup:
    VirtualMachine vm1 = new VirtualMachine("mBig", 3, 2)
    VirtualMachine vm2 = new VirtualMachine("mXXL", 10, 0)

    Collection<VirtualMachine> initialConfiguration = Lists.newArrayList(vm1, vm2)
    Metric metric = new Metric("ResponseTime", 3)

    UtilityFunctionEvaluator evaluator =
      new UtilityFunctionEvaluatorExample(initialConfiguration, metric, 30, 20,  0.5)


    VirtualMachine vm1_1 = new VirtualMachine("mBig", 3, 3)
    VirtualMachine vm2_2 = new VirtualMachine("mXXL", 10, 0)
    Collection<VirtualMachine> newConfiguration = Lists.newArrayList(vm1_1, vm2_2)

    when:
    double result = evaluator.evaluate(initialConfiguration)

    then:
    System.out.println("result = " + result)
  }
}
