/* * Copyright (C) 2017 7bulls.com
*
* This Source Code Form is subject to the terms of the
* Mozilla Public License, v. 2.0. If a copy of the MPL
* was not distributed with this file, You can obtain one at
* http://mozilla.org/MPL/2.0/.
*/

import com.google.common.collect.Lists
import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunction
import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunctionExample
import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunctionExampleV2
import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunctionWithAbsoluteCost
import eu.melodic.upperware.utilitygenerator.UtilityFunctionEvaluator
import eu.melodic.upperware.utilitygenerator.UtilityFunctionEvaluatorCETraffic
import eu.melodic.upperware.utilitygenerator.model.VirtualMachine
import spock.lang.Shared
import spock.lang.Specification


class CETrafficUseCaseTest extends Specification {

  VirtualMachine vm1_1inst
  VirtualMachine vm1_2inst
  VirtualMachine vm1_3inst
  VirtualMachine vm1_4inst
  VirtualMachine vm1_6inst
  VirtualMachine vm2
  @Shared
  Collection<VirtualMachine> initialConfiguration
  @Shared
  Collection<VirtualMachine> newConfigurationWithLessMachines
  Collection<VirtualMachine> newConfigurationWithMoreMachines
  Collection<VirtualMachine> newConfigurationWith1Machine
  Collection<VirtualMachine> newConfigurationWith6Machines
  @Shared
  UtilityFunctionEvaluator evaluator

  CostUtilityFunction costUtilityFunction
  @Shared
  CostUtilityFunction costUtilityFunction_1 = new CostUtilityFunctionExample(true)
  @Shared
  CostUtilityFunction costUtilityFunction_2 = new CostUtilityFunctionExampleV2(true)
  @Shared
  CostUtilityFunction costUtilityFunctionAbs = new CostUtilityFunctionWithAbsoluteCost(10)

  def setup(){
    vm1_1inst = new VirtualMachine("mBig", 3, 4, 1)
    vm1_2inst = new VirtualMachine("mBig", 3, 4, 2)
    vm1_3inst = new VirtualMachine("mBig", 3, 4, 3)
    vm1_4inst = new VirtualMachine("mBig", 3, 4, 4)
    vm1_6inst = new VirtualMachine("mBig", 3, 4, 6)
    vm2 = new VirtualMachine("mXXL", 10, 16, 0)

    initialConfiguration = Lists.newArrayList(vm1_3inst, vm2)
    newConfigurationWith1Machine = Lists.newArrayList(vm1_1inst, vm2)
    newConfigurationWithLessMachines = Lists.newArrayList(vm1_2inst, vm2)
    newConfigurationWithMoreMachines = Lists.newArrayList(vm1_4inst, vm2)
    newConfigurationWith6Machines = Lists.newArrayList(vm1_6inst, vm2)

  }

  def "more machines"(){
    setup:
    evaluator = new UtilityFunctionEvaluatorCETraffic(initialConfiguration, true, costUtilityFunction)

    when:
    double result = evaluator.evaluate(newConfigurationWithMoreMachines)

    then:
    //Out[40]= 0.33334
    System.out.println(result)
    //result >= 0.28 && result <= 0.38

    where:
    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2, costUtilityFunctionAbs]
  }

  def "more machines - 6 machines"(){
    setup:
    evaluator = new UtilityFunctionEvaluatorCETraffic(initialConfiguration, true, costUtilityFunction)

    when:
    double result = evaluator.evaluate(newConfigurationWith6Machines)

    then:
    //Out[40]= 0.33334
    System.out.println(result)
    //result >= 0.28 && result <= 0.38

    where:
    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2, costUtilityFunctionAbs]
  }


  def "less machines"(){
    setup:
    evaluator = new UtilityFunctionEvaluatorCETraffic(initialConfiguration, true, costUtilityFunction)

    when:
    double result = evaluator.evaluate(newConfigurationWithLessMachines)

    then:
    //0.503253
    System.out.println(result)

    //result >= 0.45 && result <= 0.55

    where:
    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2, costUtilityFunctionAbs]
  }

  def "less machines - one machine"(){
    setup:
    evaluator = new UtilityFunctionEvaluatorCETraffic(initialConfiguration, true, costUtilityFunction)

    when:
    double result = evaluator.evaluate(newConfigurationWith1Machine)

    then:
    //0.503253
    System.out.println(result)

    where:
    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2, costUtilityFunctionAbs]
  }

  def "no changes"(){
    setup:
    evaluator = new UtilityFunctionEvaluatorCETraffic(initialConfiguration, true, costUtilityFunction)

    when:
    double result = evaluator.evaluate(initialConfiguration)

    then:
    //0.500068
    System.out.println(result)

    //result >= 0.45 && result <= 0.55

    where:
    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2, costUtilityFunctionAbs]
  }




}