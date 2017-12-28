///* * Copyright (C) 2017 7bulls.com
//*
//* This Source Code Form is subject to the terms of the
//* Mozilla Public License, v. 2.0. If a copy of the MPL
//* was not distributed with this file, You can obtain one at
//* http://mozilla.org/MPL/2.0/.
//*/
//
//
//import com.google.common.collect.Lists
//import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunction
//import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunctionExample
//import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunctionExampleV2
//import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunctionFraction
//import eu.melodic.upperware.utilitygenerator.costfunction.CostUtilityFunctionWithAbsoluteCost
//import eu.melodic.upperware.utilitygenerator.evaluator.UtilityFunctionEvaluator
//import eu.melodic.upperware.utilitygenerator.evaluator.UtilityFunctionEvaluatorCAS
//import eu.melodic.upperware.utilitygenerator.model.Metric
//import eu.melodic.upperware.utilitygenerator.model.MetricType
//import spock.lang.Ignore
//import spock.lang.Shared
//import spock.lang.Specification
//
//@Ignore
//class CASUseCaseTest extends Specification {
//
//  VirtualMachine vm1_0inst
//  VirtualMachine vm1_1inst
//  VirtualMachine vm1_2inst
//  VirtualMachine vm1_3inst
//  VirtualMachine vm1_4inst
//  VirtualMachine vm1_6inst
//  VirtualMachine vm2_0inst
//  VirtualMachine vm2_2inst
//
//
//  Collection<VirtualMachine> initialConfiguration
//  Collection<VirtualMachine> newConfigurationWithLessMachines
//  Collection<VirtualMachine> newConfigurationWithMoreMachines
//  Collection<VirtualMachine> newConfigurationWith1Machine
//  Collection<VirtualMachine> newConfigurationWith6Machines
//  Collection<VirtualMachine> newConfigurationWithOneXXLMachine
//  @Shared
//  UtilityFunctionEvaluator evaluator
//
//  CostUtilityFunction costUtilityFunction
//  @Shared
//  CostUtilityFunction costUtilityFunction_1 = new CostUtilityFunctionExample(true)
//  @Shared
//  CostUtilityFunction costUtilityFunction_2 = new CostUtilityFunctionExampleV2(true)
//  @Shared
//  CostUtilityFunction costUtilityFunctionAbs = new CostUtilityFunctionWithAbsoluteCost(10)
//  @Shared
//  CostUtilityFunction costUtilityFunctionFraction = new CostUtilityFunctionFraction()
//
//  Map<MetricType, Metric> metrics
//  Metric[] highRamUsage
//  Metric[] mediumRamUsage
//  Metric[] smallRamUsage
//
//  def setup(){
//    vm1_0inst = new VirtualMachine("mBig", 3, 4, 0)
//    vm1_1inst = new VirtualMachine("mBig", 3, 4, 1)
//    vm1_2inst = new VirtualMachine("mBig", 3, 4, 2)
//    vm1_3inst = new VirtualMachine("mBig", 3, 4, 3)
//    vm1_4inst = new VirtualMachine("mBig", 3, 4, 4)
//    vm1_6inst = new VirtualMachine("mBig", 3, 4,6)
//    vm2_0inst = new VirtualMachine("mXXL", 10, 16, 0)
//    vm2_2inst = new VirtualMachine("mXXL", 10, 16, 2)
//
//    initialConfiguration = Lists.newArrayList(vm1_3inst, vm2_0inst)
//    newConfigurationWith1Machine = Lists.newArrayList(vm1_1inst, vm2_0inst)
//    newConfigurationWithLessMachines = Lists.newArrayList(vm1_2inst, vm2_0inst)
//    newConfigurationWithMoreMachines = Lists.newArrayList(vm1_4inst, vm2_0inst)
//    newConfigurationWith6Machines = Lists.newArrayList(vm1_6inst, vm2_0inst)
//    newConfigurationWithOneXXLMachine = Lists.newArrayList(vm1_0inst, vm2_2inst)
//
//    metrics = new HashMap<>()
//    metrics.put(MetricType.MAX_RAM_USAGE, new Metric(MetricType.MAX_RAM_USAGE, "stub", 0.8))
//
//    smallRamUsage = new Metric[3]
//    smallRamUsage[0] = new Metric(MetricType.RAM_USAGE,"mBig", 0.05)
//    smallRamUsage[1] = new Metric(MetricType.RAM_USAGE, "mBig", 0.01)
//    smallRamUsage[2] = new Metric(MetricType.RAM_USAGE,"mBig", 0.06)
//
//    mediumRamUsage = new Metric[3]
//    mediumRamUsage[0] = new Metric(MetricType.RAM_USAGE, "mBig", 0.55)
//    mediumRamUsage[1] = new Metric(MetricType.RAM_USAGE,"mBig",  0.11)
//    mediumRamUsage[2] = new Metric(MetricType.RAM_USAGE,"mBig", 0.16)
//
//    highRamUsage = new Metric[3]
//    highRamUsage[0] = new Metric(MetricType.RAM_USAGE, "mBig", 0.99)
//    highRamUsage[1] = new Metric(MetricType.RAM_USAGE, "mBig", 0.11)
//    highRamUsage[2] = new Metric(MetricType.RAM_USAGE,"mBig", 0.66)
//  }
//  //VAR_cardinality * VAR_ram * METRIC_maxRamUsage >= METRIC_currentRamUsage
//
//
//  def "small ram usage - one machine"(){
//    setup:
//    evaluator = new UtilityFunctionEvaluatorCAS(metrics, smallRamUsage, initialConfiguration, true, costUtilityFunction)
//
//    when:
//    double result = evaluator.evaluate(newConfigurationWith1Machine)
//
//    then:
//    //0.503253
//    System.out.println(result)
//
//    where:
//    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2, costUtilityFunctionAbs, costUtilityFunctionFraction]
//  }
//
//
//  def "small ram usage - less machines - 2"(){
//    setup:
//    evaluator = new UtilityFunctionEvaluatorCAS(metrics, smallRamUsage, initialConfiguration, true, costUtilityFunction)
//
//    when:
//    double result = evaluator.evaluate(newConfigurationWithLessMachines)
//
//    then:
//    //0.503253
//    System.out.println(result)
//
//    where:
//    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2, costUtilityFunctionAbs, costUtilityFunctionFraction]
//  }
//
//  def "small ram usage - no changes"(){
//    setup:
//    evaluator = new UtilityFunctionEvaluatorCAS(metrics, smallRamUsage, initialConfiguration, true, costUtilityFunction)
//
//    when:
//    double result = evaluator.evaluate(initialConfiguration)
//
//    then:
//    //0.503253
//    System.out.println(result)
//
//    where:
//    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2, costUtilityFunctionAbs, costUtilityFunctionFraction]
//  }
//
//  def "small ram usage - more machines - 4"(){
//    setup:
//    evaluator = new UtilityFunctionEvaluatorCAS(metrics, smallRamUsage, initialConfiguration, true, costUtilityFunction)
//
//    when:
//    double result = evaluator.evaluate(newConfigurationWithMoreMachines)
//
//    then:
//    //0.503253
//    System.out.println(result)
//
//    where:
//    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2, costUtilityFunctionAbs, costUtilityFunctionFraction]
//  }
//
//  def "medium ram usage - one machine"(){
//    setup:
//    evaluator = new UtilityFunctionEvaluatorCAS(metrics, mediumRamUsage, initialConfiguration, true,
//      costUtilityFunction)
//
//    when:
//    double result = evaluator.evaluate(newConfigurationWith1Machine)
//
//    then:
//    //0.503253
//    System.out.println(result)
//
//    where:
//    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2, costUtilityFunctionAbs, costUtilityFunctionFraction]
//  }
//
//  def "medium ram usage - less machines - 2"(){
//    setup:
//    evaluator = new UtilityFunctionEvaluatorCAS(metrics, mediumRamUsage, initialConfiguration, true, costUtilityFunction)
//
//    when:
//    double result = evaluator.evaluate(newConfigurationWithLessMachines)
//
//    then:
//    //0.503253
//    System.out.println(result)
//
//    //result >= 0.45 && result <= 0.55
//
//    where:
//    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2, costUtilityFunctionAbs, costUtilityFunctionFraction]
//  }
//
//  def "medium ram usage - no changes"(){
//    setup:
//    evaluator = new UtilityFunctionEvaluatorCAS(metrics, mediumRamUsage, initialConfiguration, true, costUtilityFunction)
//
//    when:
//    double result = evaluator.evaluate(initialConfiguration)
//
//    then:
//    //0.503253
//    System.out.println(result)
//
//    //result >= 0.45 && result <= 0.55
//
//    where:
//    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2, costUtilityFunctionAbs, costUtilityFunctionFraction]
//  }
//
//  def "medium ram usage - more machines - 4"(){
//    setup:
//    evaluator = new UtilityFunctionEvaluatorCAS(metrics, mediumRamUsage, initialConfiguration, true, costUtilityFunction)
//
//    when:
//    double result = evaluator.evaluate(newConfigurationWithMoreMachines)
//
//    then:
//    //Out[40]= 0.33334
//    System.out.println(result)
//    //result >= 0.28 && result <= 0.38
//
//    where:
//    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2, costUtilityFunctionAbs, costUtilityFunctionFraction]
//  }
//
//
//  def "medium ram usage - more machines - 6"(){
//    setup:
//    evaluator = new UtilityFunctionEvaluatorCAS(metrics, mediumRamUsage, initialConfiguration, true, costUtilityFunction)
//
//    when:
//    double result = evaluator.evaluate(newConfigurationWith6Machines)
//
//    then:
//    //Out[40]= 0.33334
//    System.out.println(result)
//    //result >= 0.28 && result <= 0.38
//
//    where:
//    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2, costUtilityFunctionAbs, costUtilityFunctionFraction]
//  }
//
//  def "high ram usage - less machines - 2"(){
//    setup:
//    evaluator = new UtilityFunctionEvaluatorCAS(metrics, highRamUsage, initialConfiguration, true, costUtilityFunction)
//
//    when:
//    double result = evaluator.evaluate(newConfigurationWithLessMachines)
//
//    then:
//    //0.503253
//    System.out.println(result)
//
//    //result >= 0.45 && result <= 0.55
//
//    where:
//    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2, costUtilityFunctionAbs, costUtilityFunctionFraction]
//  }
//
//  def "high ram usage - no changes"(){
//    setup:
//    evaluator = new UtilityFunctionEvaluatorCAS(metrics, highRamUsage, initialConfiguration, true, costUtilityFunction)
//
//    when:
//    double result = evaluator.evaluate(initialConfiguration)
//
//    then:
//    //0.503253
//    System.out.println(result)
//
//    //result >= 0.45 && result <= 0.55
//
//    where:
//    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2, costUtilityFunctionAbs, costUtilityFunctionFraction]
//  }
//
//  def "high ram usage - more machines - 4"(){
//    setup:
//    evaluator = new UtilityFunctionEvaluatorCAS(metrics, highRamUsage, initialConfiguration, true, costUtilityFunction)
//
//    when:
//    double result = evaluator.evaluate(newConfigurationWithMoreMachines)
//
//    then:
//    //Out[40]= 0.33334
//    System.out.println(result)
//    //result >= 0.28 && result <= 0.38
//
//    where:
//    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2, costUtilityFunctionAbs, costUtilityFunctionFraction]
//  }
//
//
//  def "high ram usage - more machines - 6"(){
//    setup:
//    evaluator = new UtilityFunctionEvaluatorCAS(metrics, highRamUsage, initialConfiguration, true, costUtilityFunction)
//
//    when:
//    double result = evaluator.evaluate(newConfigurationWith6Machines)
//
//    then:
//    //Out[40]= 0.33334
//    System.out.println(result)
//    //result >= 0.28 && result <= 0.38
//
//    where:
//    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2, costUtilityFunctionAbs, costUtilityFunctionFraction]
//  }
//
//  def "high ram usage - more machines - 1 XXL"(){
//    setup:
//    evaluator = new UtilityFunctionEvaluatorCAS(metrics, highRamUsage, initialConfiguration, true, costUtilityFunction)
//
//    when:
//    double result = evaluator.evaluate(newConfigurationWithOneXXLMachine)
//
//    then:
//    //Out[40]= 0.33334
//    System.out.println(result)
//    //result >= 0.28 && result <= 0.38
//
//    where:
//    costUtilityFunction << [costUtilityFunction_1, costUtilityFunction_2, costUtilityFunctionAbs, costUtilityFunctionFraction]
//  }
//
//}